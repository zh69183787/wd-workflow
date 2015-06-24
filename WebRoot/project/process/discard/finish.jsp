<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.project.workflow.util.*"%>
<%@page import="com.wonders.project.workflow.process.discard.constants.*"%>

<% 
String pname = StringUtil.getNotNullValueString(request.getParameter("pname"));
String pincident = StringUtil.getNotNullValueString(request.getParameter("pincident"));
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="办结";
String path = request.getContextPath();
Map<String,Object> map = FlowUtil.getSimulateFuture(pname, pincident);
%>
<script>
function loadInitCheckedDepts(){
	$.post(
			'<%=path%>/project-organTree/getNodesFullInfo.action?id=2550&random='+Math.random(),
			{},
			function(obj, textStatus, jqXHR){
				if(obj !=null && obj.length>0){
					$('#depsName').val(obj[0].name);
					$('#depsId').val(obj[0].id);
					$('#depRecvsId').val(obj[0].recvLoginName);
					$('#depLdsId').val(obj[0].ldLoginName);
				}
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
	loadInitCheckedDepts();
	$("#handleSubmit").click(function(){
		if(confirm("确认提交吗？")){
			$(this).attr("disabled",true);
			options.formId = "formUpdate";
			options.url = "<%=path %>/project-discardMain/dealFinish.action";
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
	var choice3 = $(choices).get(2);
	
	$(choice1).click(function(){
		$("#suggest").val("办结。");
		$("#deptTr").hide();
		$("#createSuggest").hide();
	});

	$(choice2).click(function(){
		$("#suggest").val("");
		$("#deptTr").show();
		$("#createSuggest").show();
	});
	
	$(choice3).click(function(){
		$("#suggest").val("");
		$("#deptTr").hide();
		$("#createSuggest").hide();
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
					<input type="radio"  name="choice" value="<%=DiscardMainConstants.CHOICE_DEAL_FINISH_OVER%>"/>
				</td>
				<td width="93%">结束流程，办结</td>
			</tr>
			
 			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=DiscardMainConstants.CHOICE_DEAL_FINISH_SUMBIT_DEPT%>"/>
				</td>
				<td width="93%">重新会签</td>
			</tr>
			
				<tr id="deptTr" height="30">
	           		<td style="text-align:right;"><font style="color:red;">*</font></td>
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
					<input type="radio"  name="choice" value="<%=DiscardMainConstants.CHOICE_DEAL_FINISH_RETURN%>"/>
				</td>
				<td width="93%">退回发起人</td>
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
		                	<td><textarea id="suggest" name="suggest" rows="5">办结。</textarea></td>
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


    