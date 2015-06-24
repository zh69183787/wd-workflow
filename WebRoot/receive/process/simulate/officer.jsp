<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.receive.workflow.process.simulate.constants.*"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.receive.workflow.util.*"%>
<%@page import="com.wonders.receive.workflow.constants.*"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<% 
	String pname = StringUtil.getNotNullValueString(request.getParameter("pname"));
	String pincident = StringUtil.getNotNullValueString(request.getParameter("pincident"));
	String choice = "";
	String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
	if(steplabel.length()==0) steplabel="detail";
	String path = request.getContextPath();
	Map<String,Object> map = FlowUtil.getSimulateFuture(pname, pincident);
	choice = StringUtil.getNotNullValueString(map.get("choice"));
%>
<script>
function selectComLeader(zoneId){
	var url = treepath+"/organTree/comLeaderTree.jsp?zoneId="+zoneId+"&random="+Math.random();
	window.open(url);
}
function selectDept(zoneId){
	var url = treepath+"/organTree/deptTree.jsp?zoneId="+zoneId+"&processName="+encodeURI("<%=pname%>")+"&random="+Math.random();
	window.open(url);
}
function selectDeptLeader(zoneId){
	var url = treepath+"/organTree/deptLeaderTree.jsp?zoneId="+zoneId+"&random="+Math.random();
	window.open(url);
}

function insLeaderSuggest(){
	var temp = "";
	temp = $("#insSignLdsName").val();

	if($("#insInsLdsName").val()!=""&& temp != ""){
		temp += ","+ $("#insInsLdsName").val();
	}else if($("#insInsLdsName").val()!=""&& temp == ""){
		temp += $("#insInsLdsName").val();
	}
	if(temp != ""){
		temp = "呈"+temp+"批示。拟";
	}
	return temp;
}

function deptLeaderSuggest(){
	var temp = "";
	temp = $("#sugDepsName").val();
	if(temp != ""){
		temp = "请"+temp+"部门领导提出建议。拟";
	}
	return temp;
}

function deptSuggest(){
	var temp = "";
	if($("#mainDepName").val()!=""){
		temp += "请"+ $("#mainDepName").val();
	}
	if($("#depsName").val()!="" && temp != ""){
		temp += ",会"+ $("#depsName").val();
	}else if($("#depsName").val()!="" && temp == ""){
		temp += "会"+ $("#depsName").val();
	}
	if(temp != ""){
		temp = temp+"阅处。";
	}
	return temp;
}

function signLeaderSuggest(){
	var temp = "";
	temp = $("#signLdsName").val();
	if($("#chkLdsName").val()!=""&& temp != ""){
		temp += ","+ $("#chkLdsName").val();
	}else if($("#chkLdsName").val()!=""&& temp == ""){
		temp += $("#chkLdsName").val();
	}
	
	if(temp != ""){
		temp = "报"+temp+"阅示。";
	}
	return temp;
}

function deptCommonLeader(){
	var temp = "";
	if($("#depsName").val()!=""){
		temp += "会"+ $("#depsName").val()+"阅处。";;
	}
	if($("#signLdsName").val()!=""){
		temp += "报"+ $("#signLdsName").val()+"阅示。";
	}
	return temp;
}

$(function(){
	$("#handleSubmit").click(function(){
		closeWindow();
		if(confirm("确认提交吗？")){
			$(this).attr("disabled",true);
			options.formId = "formUpdate";
			<%if(SimulateSubStepConstants.STEPNAME_SIMULATE.equals(steplabel) || 
						SimulateSubStepConstants.STEPNAME_SIMULATE_SUGGEST_COLLECT.equals(steplabel) ) {%>
			options.url = "<%=path %>/receive-simulateSub/simulate.action";
			<%}else if(SimulateSubStepConstants.STEPNAME_SIMULATE_LEADER.equals(steplabel)){%>
			options.url = "<%=path %>/receive-simulateSub/leaderSimulate.action";
			<%}%>
			options.submitZone = "handle_zone";
			options.handleZone = "handle_submit_zone";
			handleZoneControl(options.handleZone,true);
			$("#"+options.submitZone+"_loading").show();
			clearError();
			
			$("#formUpdate").ajaxSubmit(options);
			//$(this).removeAttr("disabled");
			return false;
		}
	});
	
	
	
	$(".chooseLeader").click(function(){
		var td = $(this).parent("td");
		selectComLeader(td.find("font").attr("id"));
		return false;
	});
	
	$(".clearLeader").click(function(){
		var td = $(this).parent("td");
		td.find("input[type=hidden],:text").each(function(i,n){
			$(n).val("");
		});
	});
	
	$(".chooseDeptLeader").click(function(){
		var td = $(this).parent("td");
		selectDeptLeader(td.find("font").attr("id"));
		return false;
	});
	
	$(".clearDeptLeader").click(function(){
		var td = $(this).parent("td");
		td.find("input[type=hidden],:text").each(function(i,n){
			$(n).val("");
		});
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
	var choice4 = $(choices).get(3);
	$(choice1).click(function(){
		elementEnable("choiceZone1");
		elementEnable("choiceZone3");
		elementDisable("choiceZone2");
		$("#type").attr("disabled",false);
		$(choice2).attr("disabled",false);
		$(choice3).attr("disabled",false);
		$(choice4).attr("disabled",false);
		$("#suggest").val("");
	});
	$(choice2).click(function(){
		elementEnable("choiceZone2");
		elementEnable("choiceZone3");
		elementDisable("choiceZone1");
		elementEnable("choiceZone3");
		$("#type").attr("disabled",true);
		$(choice1).attr("disabled",false);
		$(choice3).attr("disabled",false);
		$(choice4).attr("disabled",false);
		$("#suggest").val("");
	});
	$(choice3).click(function(){
		elementEnable("choiceZone3");
		elementDisable("choiceZone1");
		elementDisable("choiceZone2");
		$("#type").attr("disabled",false);
		$(choice1).attr("disabled",false);
		$(choice2).attr("disabled",false);
		$(choice4).attr("disabled",false);
		$("#suggest").val("");
	});
	$(choice4).click(function(){
		elementDisable("choiceZone1");
		elementDisable("choiceZone2");
		elementEnable("choiceZone3");
		elementDisable("mainDeptTr");
		elementDisable("chkLeaderTr");
		$("#type").attr("disabled",false);
		$(choice1).attr("disabled",false);
		$(choice2).attr("disabled",false);
		$(choice3).attr("disabled",false);
		$("#suggest").val("");
	});
	
	<%if(SimulateSubConstants.CHOICE_LEADER_INSTRUCT.equals(choice)){%>
		$(choice1).click();
	<%}else if(SimulateSubConstants.CHOICE_DEPT_SUGGEST.equals(choice)){%>
		$(choice2).click();
	<%}else if (SimulateSubConstants.CHOICE_SIMULATE_DIRECT.equals(choice)){%>
		$(choice3).click();
	<%}else if (SimulateSubConstants.CHOICE_DEPT_LEADER_CONCURRENCE.equals(choice)){%>
		$(choice4).click();
	<%}else {%> 
		$(choice3).click();
	<%}%>
	
	$("#createSuggest").click(function(){
		var suggest = "";
		var chkValue = $("input[name='choice']:checked").val();
		if(chkValue == '<%=SimulateSubConstants.CHOICE_LEADER_INSTRUCT%>'){
			suggest = insLeaderSuggest()+deptSuggest()+signLeaderSuggest();
		}else if(chkValue == '<%=SimulateSubConstants.CHOICE_DEPT_SUGGEST%>'){
			suggest = deptLeaderSuggest()+deptSuggest()+signLeaderSuggest();
		}else if(chkValue == '<%=SimulateSubConstants.CHOICE_SIMULATE_DIRECT%>'){
			suggest = deptSuggest()+signLeaderSuggest();
		}else if(chkValue == '<%=SimulateSubConstants.CHOICE_DEPT_LEADER_CONCURRENCE%>'){
			suggest = deptCommonLeader();
		}
		$("#suggest").val(suggest);
	});
});

</script>
<div id="handle_zone" style="display:none;" title="<%=steplabel%>">							
	<div class="con">
     	
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=SimulateSubConstants.CHOICE_LEADER_INSTRUCT%>"/>
				</td>
				<td>先请领导批示</td>
			</tr>
	        <tr height="30">
	           		<td></td>
	  			<td>
	  				请
	  				<input type="text" id="insSignLdsName" name="insSignLdsName" value="<%=StringUtil.getNotNullValueString(map.get("insSignLdsName")) %>" readonly class="inputLine" style="width:250px;height:22px"/>
	  				领导会签
	  				<input type="hidden" id="insSignLdsId" name="insSignLdsId" value="<%=StringUtil.getNotNullValueString(map.get("insSignLdsId")) %>">
	  				<input type="hidden" id="insSignLdsDepId" name="insSignLdsDepId" value="<%=StringUtil.getNotNullValueString(map.get("insSignLdsDepId")) %>">
	  				<input type="hidden" id="insSignSecsId" name="insSignSecsId" value="<%=StringUtil.getNotNullValueString(map.get("insSignSecsId")) %>">
	  				<input type="hidden" id="insSignSecsName" name="insSignSecsName" value="<%=StringUtil.getNotNullValueString(map.get("insSignSecsName")) %>">
	  				<input type="hidden" id="insSignSecsDepId" name="insSignSecsDepId" value="<%=StringUtil.getNotNullValueString(map.get("insSignSecsDepId")) %>">
	  				<input type="button" name="chooseLeader" value="..." class="btn chooseLeader">
	  				<input type="button" name="clearLeader" value="清除" class="btn clearLeader"/>
	  				<font id="insSignLeaderTreeZone" class="insSignLeaderTreeZone" root="2501" 
					checkNode="insSignLdsId" comLeaderLoginName="insSignLdsId"
					 comLeaderName="insSignLdsName"
					comLeaderDeptId="insSignLdsDepId" comSecLoginName="insSignSecsId"
					comSecName="insSignSecsName" comSecDeptId="insSignSecsDepId"
					></font>
	  			</td>
			</tr>
	  		<tr height="30">
	  			<td style="text-align:right;"></td>
	  			<td>
	  				请
	  				<input type="text" id="insInsLdsName" name="insInsLdsName" value="<%=StringUtil.getNotNullValueString(map.get("insInsLdsName")) %>" readonly class="inputLine" style="width:250px;height:22px"/>
	  				领导批示
	  				<input type="hidden" id="insInsLdsId" name="insInsLdsId" value="<%=StringUtil.getNotNullValueString(map.get("insInsLdsId")) %>">
	  				<input type="hidden" id="insInsLdsDepId" name="insInsLdsDepId" value="<%=StringUtil.getNotNullValueString(map.get("insInsLdsDepId")) %>">
	  				<input type="hidden" id="insInsSecsId" name="insInsSecsId" value="<%=StringUtil.getNotNullValueString(map.get("insInsSecsId")) %>">
	  				<input type="hidden" id="insInsSecsName" name="insInsSecsName" value="<%=StringUtil.getNotNullValueString(map.get("insInsSecsName")) %>">
	  				<input type="hidden" id="insInsSecsDepId" name="insInsSecsDepId" value="<%=StringUtil.getNotNullValueString(map.get("insInsSecsDepId")) %>">
	  				<input type="button" name="chooseLeader" value="..." class="btn chooseLeader">
	  				<input type="button" name="clearLeader" value="清除" class="btn clearLeader"/>
	  				<font id="insInsLeaderTreeZone" class="insInsLeaderTreeZone" root="2501" 
					checkNode="insInsLdsId" comLeaderLoginName="insInsLdsId"
					 comLeaderName="insInsLdsName"
					comLeaderDeptId="insInsLdsDepId" comSecLoginName="insInsSecsId"
					comSecName="insInsSecsName" comSecDeptId="insInsSecsDepId"
					></font>
				</td>
	  		</tr>
	  	</table>
	  	<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
		
	  		<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=SimulateSubConstants.CHOICE_DEPT_SUGGEST%>"/>
				</td>
				<td>先请业务部门对拟办提出建议</td>
			</tr>
	        <tr height="30">
	           		<td></td>
	  			<td>
	  				建议部门：
	  				<input type="text" id="sugDepsName" name="sugDepsName" value="<%=StringUtil.getNotNullValueString(map.get("sugDepsName")) %>" readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="hidden" id="sugDepLdsId" name="sugDepLdsId" value="<%=StringUtil.getNotNullValueString(map.get("sugDepLdsId")) %>">
	  				<input type="hidden" id="sugDepLdsDepId" name="sugDepLdsDepId" value="<%=StringUtil.getNotNullValueString(map.get("sugDepLdsDepId")) %>">
	  				<input type="button" name="chooseDept" value="..." class="btn chooseDept">
	  				<input type="button" name="chooseDept" value="清除" class="btn clearDept"/>
	  				<font id="sugDepTreeZone" class="sugDepTreeZone" root="" 
					checkNode="sugDepLdsDepId" deptIds="sugDepLdsDepId" deptNames="sugDepsName" 
					deptLdsId="sugDepLdsId" 
					></font>
	  			</td>
			</tr>
		</table>
		<table id="choiceZone3" width="100%" border="0" cellspacing="0" cellpadding="0">
		
			<tr height="30">
				<td class="td_1">
					<input type="radio" checked="checked" name="choice" value="<%=SimulateSubConstants.CHOICE_SIMULATE_DIRECT%>"/>
				</td>
				<td>直接拟办</td>
			</tr>
			<%if(SimulateSubStepConstants.STEPNAME_SIMULATE.equals(steplabel) || 
					SimulateSubStepConstants.STEPNAME_SIMULATE_SUGGEST_COLLECT.equals(steplabel) ) {%>
			<tr height="30">
				<td class="td_1">
					<input type="checkbox" id="type" name="type" value="1"/>
				</td>
				<td>提交办公室主任审核</td>
			</tr>
			<%} %>
			<tr height="30">
				<td class="td_1">
					<input type="checkbox" name="urge" value=""/>
				</td>
				<td>不需全员催办</td>
			</tr>
			
			<tr <%if(!UltimusConstants.RECV_DICTIONARY_NAME.equals(pname)){ %> style="display:none;"	<%} %>height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=SimulateSubConstants.CHOICE_DEPT_LEADER_CONCURRENCE%>"/>
				</td>
				<td>加急件（部门和领导并行处理）</td>
			</tr>
		
	        <tr id="mainDeptTr" height="30">
	           		<td></td>
	  			<td>
	  				主办部门：
	  				<input type="text" id="mainDepName" name="mainDepName" value="<%=StringUtil.getNotNullValueString(map.get("mainDepName")) %>" readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="hidden" id="mainDepId" name="mainDepId" value="<%=StringUtil.getNotNullValueString(map.get("mainDepId")) %>">
	  				<input type="hidden" id="mainDepRecvId" name="mainDepRecvId" value="<%=StringUtil.getNotNullValueString(map.get("mainDepRecvId")) %>">
	  				<input type="hidden" id="mainDepLdId" name="mainDepLdId" value="<%=StringUtil.getNotNullValueString(map.get("mainDepLdId")) %>">
	  				<input type="button" name="chooseDept" value="..." class="btn chooseDept">
	  				<input type="button" name="clearDept" value="清除" class="btn clearDept"/>
	  				<font id="mainDepTreeZone" class="mainDepTreeZone" root="" 
					checkNode="mainDepId" deptIds="mainDepId" 
					deptNames="mainDepName" treeType="radio" 
					deptLdsId="mainDepLdId"  deptRecvsId="mainDepRecvId"
					></font>
	  			</td>
			</tr>
			
			 <tr height="30">
	           		<td></td>
	  			<td>
	  				协办部门：
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
	           		<td></td>
	  			<td>
	  				会签领导：
	  				<input type="text" id="signLdsName" name="signLdsName" value="<%=StringUtil.getNotNullValueString(map.get("signLdsName")) %>" readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="hidden" id="signLdsId" name="signLdsId" value="<%=StringUtil.getNotNullValueString(map.get("signLdsId")) %>">
	  				<input type="hidden" id="signLdsDepId" name="signLdsDepId" value="<%=StringUtil.getNotNullValueString(map.get("signLdsDepId")) %>">
	  				<input type="hidden" id="signSecsId" name="signSecsId" value="<%=StringUtil.getNotNullValueString(map.get("signSecsId")) %>">
	  				<input type="hidden" id="signSecsName" name="signSecsName" value="<%=StringUtil.getNotNullValueString(map.get("signSecsName")) %>">
	  				<input type="hidden" id="signSecsDepId" name="signSecsDepId" value="<%=StringUtil.getNotNullValueString(map.get("signSecsDepId")) %>">
	  				<input type="button" name="chooseLeader" value="..." class="btn chooseLeader">
	  				<input type="button" name="clearLeader" value="清除" class="btn clearLeader"/>
	  				<font id="signLeaderTreeZone" class="signLeaderTreeZone" root="2501" 
					checkNode="signLdsId" comLeaderLoginName="signLdsId"
					 comLeaderName="signLdsName"
					comLeaderDeptId="signLdsDepId" comSecLoginName="signSecsId"
					comSecName="signSecsName" comSecDeptId="signSecsDepId"
					></font>
	  			</td>
			</tr>
			 <tr id="chkLeaderTr" height="60">
	           		<td></td>
	  			<td>
	  				审核领导：
	  				<input type="text" id="chkLdsName" name="chkLdsName" value="<%=StringUtil.getNotNullValueString(map.get("chkLdsName")) %>" readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="hidden" id="chkLdsId" name="chkLdsId" value="<%=StringUtil.getNotNullValueString(map.get("chkLdsId")) %>">
	  				<input type="hidden" id="chkLdsDepId" name="chkLdsDepId" value="<%=StringUtil.getNotNullValueString(map.get("chkLdsDepId")) %>">
	  				<input type="hidden" id="chkSecsId" name="chkSecsId" value="<%=StringUtil.getNotNullValueString(map.get("chkSecsId")) %>">
	  				<input type="hidden" id="chkSecsName" name="chkSecsName" value="<%=StringUtil.getNotNullValueString(map.get("chkSecsName")) %>">
	  				<input type="hidden" id="chkSecsDepId" name="chkSecsDepId" value="<%=StringUtil.getNotNullValueString(map.get("chkSecsDepId")) %>">
	  				<input type="button" name="chooseLeader" value="..." class="btn chooseLeader">
	  				<input type="button" name="clearLeader" value="清除" class="btn clearLeader"/>
	  				<font id="chkLeaderTreeZone" class="chkLeaderTreeZone" root="2501" 
					checkNode="chkLdsId" comLeaderLoginName="chkLdsId"
					 comLeaderName="chkLdsName"
					comLeaderDeptId="chkLdsDepId" comSecLoginName="chkSecsId"
					comSecName="chkSecsName" comSecDeptId="chkSecsDepId"
					></font>
	  			</td>
			</tr>
			
			
			<tr height="30" style="display:none;">
	           		<td></td>
	  			<td>
	  				跟踪人：
	  				<input type="text" id="finPersonName" name="finPersonName" value='<s:property value="params.userInfo.userName"/>' readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="hidden" id="finPersonId" name="finPersonId" value='<s:property value="params.userInfo.loginName"/>'>
	  				<input type="hidden" id="finPersonDepId" name="finPersonDepId" value='<s:property value="params.userInfo.deptId"/>'>
	  				<input type="button" name="chooseDeptLeader" value="..." class="btn chooseDeptLeader">
	  				<input type="button" name="clearDeptLeader" value="清除" class="btn clearDeptLeader"/>
	  				<font id="finPersonTreeZone" class="finPersonTreeZone" 
	  				root='<s:property value="params.userInfo.deptId"/>'
					checkNode="finPersonId" dealLeaderLoginName="finPersonId"
					dealLeaderName="finPersonName"
					dealLeaderDeptId="finPersonDepId"
					treeType="radio"
					></font>
	  			</td>
			</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
	      <p style="width:auto;display:inline;"><img src="<%=path%>/receive/css/default/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>


    