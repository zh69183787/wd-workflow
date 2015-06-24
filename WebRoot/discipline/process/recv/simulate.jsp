<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.discipline.workflow.constants.*"%>
<%@page import="com.wonders.discipline.workflow.util.*"%>
<%@page import="com.wonders.discipline.workflow.process.recv.constants.*"%>

<% 
String choice = "";
String pname = StringUtil.getNotNullValueString(request.getParameter("pname"));
String pincident = StringUtil.getNotNullValueString(request.getParameter("pincident"));
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="选择流转的领导和委员";
String path = request.getContextPath();
Map<String,Object> map = FlowUtil.getSimulateFuture(pname, pincident);
choice = StringUtil.getNotNullValueString(map.get("choice"));
%>
<script>
function loadSimulater(){
	$.post(
			'<%=path%>/discipline-dcpRecvUtil/getSimulateLeader.action?random='+Math.random(),
			{},
			function(obj, textStatus, jqXHR){
				var temp = "";
				if(obj !=null && obj.length>0){
					for(var i=0;i<obj.length;i++){
						if(i==0){
							$("#simulateName").val(obj[i].USERNAME);
							$("#simulateDeptId").val(obj[i].DEPTID);
						}
						temp +="<option deptId='"+obj[i].DEPTID+"' value='"+obj[i].LOGINNAME+"'>"+obj[i].USERNAME+"</option>";
					}
					
				}
				$("#simulateLoginName").html(temp);
			},
			"json"
		).error(function() { alert("服务器连接失败，请稍后再试!"); });
}

function loadChiefDep(){
	$.post(
			'<%=path%>/discipline-dcpRecvUtil/getCodeList.action?random='+Math.random(),
			{
				"type": 	'<%=UltimusConstants.ULTIMUS_DICTIONARY%>',
				"code": 	'<%=UltimusConstants.DISCIPLINE_RECV_DICTIONARY_CODE%>'+'sw'
			},
			function(obj, textStatus, jqXHR){
				var temp = "";
				if(obj !=null && obj.length>0){
					for(var i=0;i<obj.length;i++){
						if(i==0){
							$("#swId").val(obj[i].NAME);
						}
						temp +="<option value='"+obj[i].CODE+"'>"+obj[i].NAME+"</option>";
					}
					
				}
				$("#chiefDep").html(temp);
			},
			"json"
		).error(function() { alert("服务器连接失败，请稍后再试!"); });
}
function selectComLeader(zoneId){
	var url = treepath+"/organTree/comLeaderTree.jsp?zoneId="+zoneId+"&random="+Math.random();
	window.open(url);
}
function selectDcp(zoneId){
	var url = treepath+"/organTree/groupUserTree.jsp?zoneId="+zoneId+"&random="+Math.random();
	window.open(url);
}

function allSuggest(){
	var temp = "";
	if($("#comLeaderName").val()!=""){
		temp += $("#comLeaderName").val();
	}
	
	//if($("#dcpName").val()!=""){
	//	temp += ",";
	//}
	
	//if($("#dcpName").val()!=""){
	//	temp += $("#dcpName").val();
	//}
	
	if(temp != ""){
		temp = "请"+temp+"阅。";
	}
	return temp;
}


$(function(){
	
	<%if(DcpRecvMainStepConstants.STEPNAME_RETURN_MODIFY.equals(steplabel) 
			||"选择流转的领导和委员".equals(steplabel)) {%>
		loadSimulater();
	<%}%>
	$("#leaderTr").on("change","#simulateLoginName",function(){
		$("#simulateName").val($(this).find("option:selected").text());
		$("#simulateDeptId").val($(this).find("option:selected").attr("deptId"));
	});
	
	$("#handleClose").on("click",function(){
        $("#handle_zone").dialog("close");
        return false;
    });
	
	$("#handleSubmit").click(function(){
		if(confirm("确认提交吗？")){
			$(this).attr("disabled",true);
			<%if(DcpRecvMainStepConstants.STEPNAME_DEPT_LEADER.equals(steplabel)){%>
				options.url = "<%=path%>/discipline-dcpRecvMain/deptLeader.action";
			<%}else if(DcpRecvMainStepConstants.STEPNAME_RETURN_MODIFY.equals(steplabel)){%>
				options.url = "<%=path%>/discipline-dcpRecvMain/registerModify.action";
			<%}else{%>
				options.url = "<%=path%>/discipline-dcpRecvMain/register.action";
			<%}%>
			options.formId = "formUpdate";
			options.submitZone = "handle_zone";
			options.handleZone = "handle_submit_zone";
			submitZoneControl(options.formId,true);
			handleZoneControl(options.handleZone,true);
			$("#"+options.submitZone+"_loading").show();
			clearError();
			
			$("#formUpdate").ajaxSubmit(options);
			return false;
		}
		//$(this).removeAttr("disabled");
		
	});
	
	$(".chooseComLeader").click(function(){
		var td = $(this).parent("td");
		selectComLeader(td.find("font").attr("id"));
		return false;
	});
	
	$(".clearComLeader").click(function(){
		var td = $(this).parent("td");
		td.find("input[type=hidden],:text").each(function(i,n){
			$(n).val("");
		});
	});
	
	$(".chooseDcp").click(function(){
		var td = $(this).parent("td");
		selectDcp(td.find("font").attr("id"));
		return false;
	});
	
	$(".clearDcp").click(function(){
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
		$("#suggest").val("");
		$("#comLeaderTr").show();
		$("#leaderTr").show();
		$("#dcpTr").show();
		$("#createSuggest").show();
	});
	$(choice2).click(function(){
		$("#suggest").val("");
		$("#comLeaderTr").show();
		$("#dcpTr").show();
		$("#createSuggest").show();
		$("#leaderTr").show();
	});
	$(choice3).click(function(){
		$("#suggest").val("");
		$("#comLeaderTr").hide();
		$("#dcpTr").hide();
		$("#createSuggest").hide();
		$("#leaderTr").hide();
	});
	
	$("#createSuggest").click(function(){
		var suggest = "";
		suggest = allSuggest();
		$("#suggest").val(suggest);
	});

	<%if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_ONE.equals(choice)){%>
		$(choice1).click();
	<%}else if(DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_TWO.equals(choice)){%>
		$(choice2).click();
	<%}else {%> 
		$(choice1).click();
	<%}%>
	
	<%if(StringUtil.getNotNullValueString(map.get("suggest")).length() > 0){%>
		$("#suggest").val("<%=StringUtil.getNotNullValueString(map.get("suggest"))%>");
	<%}%>
	
});
</script>
<div id="handle_zone" style="display:none;" title="<%=steplabel%>">
	
	<div class="con">
     	
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_ONE%>"/>
				</td>
				<td width="93%">集团领导、纪委委员并行处理</td>
			</tr>
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=DcpRecvMainConstants.CHOICE_DEPT_LEADER_SUBMIT_TWO%>"/>
				</td>
				<td width="93%">集团领导、纪委委员串行处理</td>
			</tr>
			<tr id="comLeaderTr" height="30">
	           		<td style="text-align:right;"><!-- <font style="color:red;">*</font> --></td>
	  			<td>
	  				集团领导：
	  				<input type="text" id="comLeaderName" name="comLeaderName"  
	  				value="<%=StringUtil.getNotNullValueString(map.get("insSignLdsName"))%>"
	  				readonly class="inputLine" style="width:200px;height:22px"/>
	  				<input type="hidden" id="comLeaderLoginName" name="comLeaderLoginName" 
	  				value="<%=StringUtil.getNotNullValueString(map.get("insSignLdsId"))%>"/>
	  				<input type="hidden" id="comLeaderDeptId" name="comLeaderDeptId"
	  				value="<%=StringUtil.getNotNullValueString(map.get("insSignLdsDepId"))%>"/>
	  				<input type="button" name="chooseComLeader" value="..." class="btn chooseComLeader">
	  				<input type="button" name="clearComLeader" value="清除" class="btn clearComLeader"/>
	  				<font id="comLeaderTreeZone" class="comLeaderTreeZone" root="2501" 
					checkNode="comLeaderLoginName" comLeaderLoginName="comLeaderLoginName"
					 comLeaderName="comLeaderName"
					comLeaderDeptId="comLeaderDeptId"></font>
	  			</td>
			</tr>
			<tr id="dcpTr" height="30">
	           		<td style="text-align:right;"><!-- <font style="color:red;">*</font> --></td>
	  			<td>
	  				纪委委员：
	  				<input type="text" id="dcpName" name="dcpName"  
	  				value="<%=StringUtil.getNotNullValueString(map.get("insSignSecsName"))%>" 
	  				readonly class="inputLine" style="width:200px;height:22px"/>
	  				<input type="hidden" id="dcpLoginName" name="dcpLoginName" 
	  				value="<%=StringUtil.getNotNullValueString(map.get("insSignSecsId"))%>"/>
	  				<input type="hidden" id="dcpDeptId" name="dcpDeptId" 
	  				value="<%=StringUtil.getNotNullValueString(map.get("insSignSecsDepId"))%>"/>
	  				<input type="button" name="chooseDcp" value="..." class="btn chooseDcp">
	  				<input type="button" name="clearDcp" value="清除" class="btn clearDcp"/>
	  				<font id="dcpTreeZone" class="dcpTreeZone" root="cdic" 
					checkNode="dcpLoginName" dcpLoginName="dcpLoginName"
					 dcpName="dcpName"
					dcpDeptId="dcpDeptId"></font>
	  			</td>
			</tr>
			<%if(DcpRecvMainStepConstants.STEPNAME_RETURN_MODIFY.equals(steplabel) 
			||"选择流转的领导和委员".equals(steplabel)) {%>
				<tr id="leaderTr" height="30">
	               <td style="text-align:right;"><!-- <font style="color:red;">*</font> --></td>
	               <td>拟办领导：
	               	<input type="hidden" id="simulateName" name="simulateName"/>
	               	<input type="hidden" id="simulateDeptId" name="simulateDeptId"/>
	                <select id="simulateLoginName" name="simulateLoginName">
						
					</select>
	               </td>
	            </tr>
            <%} %>
			<%
				if(DcpRecvMainStepConstants.STEPNAME_DEPT_LEADER.equals(steplabel)){
						%>
			<tr height="30">
				<td class="td_1">
					<input type="radio"  name="choice" value="<%=DcpRecvMainConstants.CHOICE_DEPT_LEADER_BACK%>"/>
				</td>
				<td width="93%">返回修改</td>
			</tr>
			<%
				}else if(DcpRecvMainStepConstants.STEPNAME_RETURN_MODIFY.equals(steplabel)){
			%>
			<tr height="30">
				<td class="td_1">
					<input type="radio"  name="choice" value="<%=DcpRecvMainConstants.CHOICE_MODIFY_CANCEL_RECV%>"/>
				</td>
				<td width="93%">取消收文</td>
			</tr>
			<%} %>
				<tr>
	  			<td colspan=2>
			  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
				  			<td class="td_1" style="text-align:right;">&nbsp;</td>
		                	<td textareaId="suggest">
		                	<span class="fl">
		                	意见：<font style="color:red;display:inline;">*</font>
		                	<input type="button" id="createSuggest" value="生成意见"></span>
		                	<%
			                if(DcpRecvMainStepConstants.STEPNAME_DEPT_LEADER.equals(steplabel)){
			                 %>
		                	<span class="fr" style="display:inline;">
		                	<a name="suggest_attach" class="suggest_attach" target="#">
			  				<input type="hidden" name="attachId" id="attachId" value=""/>
			  				上传意见附件(<span style="display:inline;" id="fjcount" class="fjcount">0</span>)
			  				</a>
		                	</span>
		                	<%} %>
		                	<!--  
		                	<span class="fr" style="display:inline;"><a class="previewSuggest">意见预览</a></span>
		                	-->
		                	</td>
		              	</tr>
		              	<tr>
		              		<td class="td_1">&nbsp;</td>
		                	<td><textarea id="suggest" name="suggest" rows="5"></textarea>
		                	</td>
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
	      <p style="width:auto;display:inline;"><img src="<%=path%>/discipline/css/default/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>


    