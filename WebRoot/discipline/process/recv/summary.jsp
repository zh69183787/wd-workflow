<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.discipline.workflow.util.*"%>
<%@page import="com.wonders.discipline.workflow.process.recv.constants.*"%>

<% 
String pname = StringUtil.getNotNullValueString(request.getParameter("pname"));
String pincident = StringUtil.getNotNullValueString(request.getParameter("pincident"));
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="汇总人";
String path = request.getContextPath();
Map<String,Object> map = FlowUtil.getSimulateFuture(pname, pincident);
Map<String,Object> smap = FlowUtil.getSimulatePerson(pname, pincident, DcpRecvMainStepConstants.STEPNAME_DEPT_LEADER);
%>
<script>
function loadFinisher(){
	var sel = "<%=StringUtil.getNotNullValueString(smap.get("loginName"))%>";
	$.post(
			'<%=path%>/discipline-dcpRecvUtil/getFinishLeader.action?random='+Math.random(),
			{},
			function(obj, textStatus, jqXHR){
				var temp = "";
				if(obj !=null && obj.length>0){
					if(sel==""){
						for(var i=0;i<obj.length;i++){
							if(i==0){
								$("#finishName").val(obj[i].USERNAME);
								$("#finishDeptId").val(obj[i].DEPTID);
							}
							temp +="<option deptId='"+obj[i].DEPTID+"' value='"+obj[i].LOGINNAME+"'>"+obj[i].USERNAME+"</option>";
					
						}
					}else{
						for(var i=0;i<obj.length;i++){
							if(sel == obj[i].LOGINNAME){
								temp +="<option selected deptId='"+obj[i].DEPTID+"' value='"+obj[i].LOGINNAME+"'>"+obj[i].USERNAME+"</option>";
								$("#finishName").val(obj[i].USERNAME);
								$("#finishDeptId").val(obj[i].DEPTID);
							}else{
								temp +="<option deptId='"+obj[i].DEPTID+"' value='"+obj[i].LOGINNAME+"'>"+obj[i].USERNAME+"</option>";
							}
						}
					}
					
					
				}
				$("#finishLoginName").html(temp);
			},
			"json"
		).error(function() { alert("服务器连接失败，请稍后再试!"); });
}

function selectDept(zoneId){
	var url = treepath+"/organTree/deptTree.jsp?zoneId="+zoneId+"&processName="+encodeURI("<%=pname%>")+"&random="+Math.random();
	window.open(url);
}
function deptSuggest(){
	var temp = "";
	if($("#depsName").val()!=""){
		temp += "请"+ $("#depsName").val();
	}
	if(temp != ""){
		temp = temp+"阅处。";
	}
	return temp;
}

$(function(){
	loadFinisher();
	
	$("#leaderTr").on("change","#finishLoginName",function(){
		$("#finishName").val($(this).find("option:selected").text());
		$("#finishDeptId").val($(this).find("option:selected").attr("deptId"));
	});
	$("#handleSubmit").click(function(){
		closeWindow();
		if(confirm("确认提交吗？")){
			$(this).attr("disabled",true);
			options.formId = "formUpdate";
			options.url = "<%=path %>/discipline-dcpRecvMain/summary.action";
			options.submitZone = "handle_zone";
			options.handleZone = "handle_submit_zone";
			handleZoneControl(options.handleZone,true);
			$("#"+options.submitZone+"_loading").show();
			clearError();
			
			$("#formUpdate").ajaxSubmit(options);
			return false;
		}
		//$(this).removeAttr("disabled");
		
	});
	
	$(".chooseDept").click(function(){
		var td = $(this).parent("td");
		selectDept(td.find("font").attr("id"));
		return false;
	});
	
	$(".clearDept").click(function(){
		var td = $(this).parent("td");
		td.find("input[type=hidden],:text").each(function(i,n){
			$(n).val("");
		});
	});
	
	var choices = $("input[name='choice']");
	var choice1 = $(choices).get(0);
	var choice2 = $(choices).get(1);

	$(choice1).click(function(){
		$("#suggest").val("");
		$("#deptTr").show();
		$("#createSuggest").show();
		$("#leaderTr").hide();
	});
	$(choice2).click(function(){
		$("#suggest").val("");
		$("#deptTr").hide();
		$("#createSuggest").hide();
		$("#leaderTr").show();
	});
	
	
	$("#createSuggest").click(function(){
		var suggest = deptSuggest();
		$("#suggest").val(suggest);
	});
	
	$(choice1).click();
});
</script>
<div id="handle_zone" style="display:none;" title="<%=steplabel%>">
	
	<div class="con">
     	
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=DcpRecvMainConstants.CHOICE_SUMMARY_PERSON_SUMBIT_DEPT%>"/>
				</td>
				<td width="93%">提交部门会签</td>
			</tr>
			
				<tr id="deptTr" height="30">
	           		<td style="text-align:right;"><!-- <font style="color:red;">*</font>--></td>
	  			<td>
	  				会签部门：
	  				<input type="text" id="depsName" name="depsName" value="<%=StringUtil.getNotNullValueString(map.get("depsName")) %>" readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="hidden" id="depsId" name="depsId" value="<%=StringUtil.getNotNullValueString(map.get("depsId")) %>">
	  				<input type="hidden" id="depRecvsId" name="depRecvsId" value="<%=StringUtil.getNotNullValueString(map.get("depRecvsId")) %>">
	  				<input type="hidden" id="depLdsId" name="depLdsId" value="<%=StringUtil.getNotNullValueString(map.get("depLdsId")) %>">
	  				<input type="button" name="chooseDept" value="..." class="btn chooseDept">
	  				<input type="button" name="clearDept" value="清除" class="btn clearDept"/>
	  				<font id="depTreeZone" class="depTreeZone" root="" 
					checkNode="depsId" deptIds="depsId" 
					deptNames="depsName" 
					deptLdsId="depLdsId"  deptRecvsId="depRecvsId"
					></font>
	  			</td>
				</tr>
			 	
			<tr height="30">
				<td class="td_1">
					<input type="radio"  name="choice" value="<%=DcpRecvMainConstants.CHOICE_SUMMARY_PERSON_SUMBIT_FINISH%>"/>
				</td>
				<td width="93%">提交办结人</td>
			</tr>
			<tr id="leaderTr" height="30">
               <td style="text-align:right;"><!-- <font style="color:red;">*</font> --></td>
               <td>办结人：
               	<input type="hidden" id="finishName" name="finishName"/>
               	<input type="hidden" id="finishDeptId" name="finishDeptId"/>
                <select id="finishLoginName" name="finishLoginName">
					
				</select>
               </td>
            </tr>
				<tr>
	  			<td colspan=2>
			  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
				  			<td class="td_1" style="text-align:right;">&nbsp;</td>
		                	<td textareaId="suggest">
		                	<span class="fl">
		                	意见：<font style="color:red;display:inline;">*</font>
		                	<input type="button" id="createSuggest" value="生成意见"></span>
		                	<span class="fr" style="display:inline;">
		                	<a name="suggest_attach" class="suggest_attach" target="#">
			  				<input type="hidden" name="attachId" id="attachId" value=""/>
			  				上传意见附件(<span style="display:inline;" id="fjcount" class="fjcount">0</span>)
			  				</a>
		                	</span>
		                	<!--  
		                	<span class="fr" style="display:inline;"><a class="previewSuggest">意见预览</a></span>
		                	-->
		                	</td>
		              	</tr>
		              	<tr>
		              		<td class="td_1">&nbsp;</td>
		                	<td><textarea id="suggest" name="suggest" rows="5"></textarea></td>
		              	</tr>
	              	</table>
           		</td>
           	</tr>
		</table>
	</div>
	<br>
	<div id="handle_submit_zone" class="button t_c">
		<input id="handleSubmit" type="button" value="提交">&nbsp;
		<input id="handleClose" type="button" value="关闭">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path%>/contract/css/default/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>


    