<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.contract.workflow.process.review.constants.*"%>

<% 
String pname = StringUtil.getNotNullValueString(request.getParameter("pname"));
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
String path = request.getContextPath();
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

function signLeaderSuggest(){
	var temp = "";
	if($("#signLdsName").val()!=""){
		temp += $("#signLdsName").val();
	}
	
	if(temp != ""){
		temp = "报"+temp+"阅示。";
	}
	return temp;
}


$(function(){
	$("#handleSubmit").click(function(){
		if(confirm("确认提交吗？")){
			$(this).attr("disabled",true);
			options.formId = "formUpdate";
			options.url = "<%=path %>/contract-reviewMain/contractFinish.action";
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
		$("#suggest").val("合同办结，结束流转。");
		$("#deptTr").hide();
		$("#comLeaderTr").hide();
		$("#createSuggest").hide();
	});
	$(choice2).click(function(){
		$("#suggest").val("");
		$("#deptTr").show();
		$("#comLeaderTr").show();
		$("#createSuggest").show();
	});
	
	$("#createSuggest").click(function(){
		var suggest = "";
		suggest = deptSuggest()+signLeaderSuggest();
		$("#suggest").val(suggest);
	});
	$("#deptTr").hide();
	$("#comLeaderTr").hide();
	$("#createSuggest").hide();
});
</script>
<div id="handle_zone" style="display:none;" title="<%=steplabel%>">
	
	<div class="con">
     	
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" checked="checked" name="choice" value="<%=ReviewMainConstants.CHOICE_FINISH_END_PROCESS%>"/>
				</td>
				<td width="93%">合同办结，结束流转</td>
			</tr>
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=ReviewMainConstants.CHOICE_FINISH_RE_SIMULATE%>"/>
				</td>
				<td width="93%">重新拟办，提交部门及集团领导会签</td>
			</tr>
			
				<tr id="deptTr" height="30">
	           		<td style="text-align:right;"><!-- <font style="color:red;">*</font> --></td>
	  			<td>
	  				会签部门：
	  				<input type="text" id="depsName" name="depsName" readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="hidden" id="depsId" name="depsId" >
	  				<input type="hidden" id="depRecvsId" name="depRecvsId" >
	  				<input type="hidden" id="depLdsId" name="depLdsId" >
	  				<input type="button" name="chooseDept" value="..." class="btn chooseDept">
	  				<input type="button" name="clearDept" value="清除" class="btn clearDept"/>
	  				<font id="depTreeZone" class="depTreeZone" root="" 
					checkNode="depsId" deptIds="depsId" 
					deptNames="depsName" 
					deptLdsId="depLdsId"  deptRecvsId="depRecvsId"
					></font>
	  			</td>
				</tr>
			 	<tr id="comLeaderTr" height="30">
	           		<td style="text-align:right;"><!-- <font style="color:red;">*</font> --></td>
	  			<td>
	  				会签领导：
	  				<input type="text" id="signLdsName" name="signLdsName"  readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="hidden" id="signLdsId" name="signLdsId" >
	  				<input type="hidden" id="signLdsDepId" name="signLdsDepId">
	  				<input type="hidden" id="signSecsId" name="signSecsId" >
	  				<input type="hidden" id="signSecsName" name="signSecsName" >
	  				<input type="hidden" id="signSecsDepId" name="signSecsDepId">
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
		                	<td><textarea id="suggest" name="suggest" rows="5">合同办结，结束流转。</textarea></td>
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


    