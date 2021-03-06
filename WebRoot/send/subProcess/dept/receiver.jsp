<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.wonders.send.external.service.ExternalService" %>
<%@ page import="com.wonders.send.organTree.model.bo.OrganUserBo" %>
<%@ page import="com.wonders.common.model.vo.TaskUserVo" %>
<%@ page import="com.wonders.constants.LoginConstants" %>
<%
String path = request.getContextPath();

String deptId = (String)session.getAttribute("dept_id");
String assignedtouser = request.getParameter("assignedtouser");
if(assignedtouser==null){
	assignedtouser = request.getParameter("taskuser");
}
Map<String,TaskUserVo> userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
if(userMap!=null){
	TaskUserVo taskUserVo = userMap.get(assignedtouser);
	if(taskUserVo!=null){
		deptId = taskUserVo.getDeptId();
	}
}

WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
ExternalService externalService=(ExternalService)context.getBean("send-externalService");
externalService.setToken((String)session.getAttribute(LoginConstants.TOKEN));
List<OrganUserBo> listDeptLeaders =  externalService.getDeptSingleLeader(deptId);
String dealLeaderNames = "";
String dealLeaderStr = "";
String dealLeaderIdStr = "";
if(listDeptLeaders!=null&&listDeptLeaders.size()>0){
	OrganUserBo bo = listDeptLeaders.get(0);
	dealLeaderNames = bo.name;
	dealLeaderStr = bo.loginName;
	dealLeaderIdStr = bo.id;
}
 %>
<script>
function handleFunc(obj){
	if($("[name=dealLeaderNames]:enabled").val()==""){
		alert("请选择领导！");
		return false;
	}
	
	if($("[name=choice]:checked").val()=="1"||($("[name=choice]:checked").val()=="2"&&$("[name=choice2]:checked").val()=="2")){
		if($.trim($("[name=suggest]:enabled").val())==""){
			alert("请填写意见！");
			$("[name=suggest]:enabled").focus();
			return false;
		}
	}
	
	if(confirm("确定处理完成？")){
		var formOptions1 = {
			cache:false,
			type:'post',
			callback:null,
			dataType :'json',
			url:contextpath+"/send-deptSub/dispatcher.action",
		    success:function(data){
				//var obj = JSON.parse(data);
				//$("#formUpdate").submit();
				//$("#formUpdate").ajaxSubmit(formOptions); 
				if(data!=null&&data.if_success=="yes"){
					alert("提交成功！");
					window.location.href = contextpath+"/send-tDocSend/toFormPage.action?incidentNo="+$("[name=incidentNo]").val()
											+"&modelName="+encodeURI($("[name=modelName]").val())
											+"&processName="+encodeURI($("[name=processName]").val())
											+"&pinstanceId="+$("[name=pinstanceId]").val()
											+"&taskId="+$("[name=taskId]").val()
											+"&taskUserName="+encodeURI($("[name=taskUserName]").val())
				}else{
					alert("提交失败，请联系管理员！");
				}
				
				return false;
		    }
		};
		
		$(obj).attr("disabled",true);
		$("#handleClose").attr("disabled",true);
		$("#handle_zone_loading").show();
		if(newwindow&&newwindow.open&&!newwindow.closed){
			newwindow.close();
		}
		$("#form").ajaxSubmit(formOptions1);  
	}
}

$(function(){
	initButton();
	chooseRadioFunc(1);
})

function chooseRadioFunc(num){
	$("[name=suggest]").val("");
	
	if(num==1){
		$("[name=suggest]:eq(0)").attr("disabled",false);
		$("[name=suggest]:eq(1)").attr("disabled",true);
		$("[name=suggest]:eq(2)").attr("disabled",true);
		
		$("[name=attachId]:eq(0)").attr("disabled",false);
		$("[name=attachId]:eq(1)").attr("disabled",true);
		$("[name=attachId]:eq(2)").attr("disabled",true);
		
		$("[id=fontId_1]").show();
		$("[id=fontId_2]").hide();
		$("[id=fontId_3]").hide();
		$("[id=fontId_4]").hide();
		
		$("[name=choice2]").attr("disabled",true);
		
		$("[name=choosePerson]:eq(0)").attr("disabled",false);
		$("[name=clearPerson]:eq(0)").attr("disabled",false);
		$("[name=chooseLeader]:eq(0)").attr("disabled",false);
		$("[name=clearLeader]:eq(0)").attr("disabled",false);
		$("[name=dealPersonNames]:eq(0)").attr("disabled",false);
		$("[name=dealPersonStr]:eq(0)").attr("disabled",false);
		$("[name=dealPersonIdStr]:eq(0)").attr("disabled",false);
		$("[name=dealLeaderNames]:eq(0)").attr("disabled",false);
		$("[name=dealLeaderStr]:eq(0)").attr("disabled",false);
		$("[name=dealLeaderIdStr]:eq(0)").attr("disabled",false);
		
		$("[name=choosePerson]:eq(1)").attr("disabled",true);
		$("[name=clearPerson]:eq(1)").attr("disabled",true);
		$("[name=chooseLeader]:eq(1)").attr("disabled",true);
		$("[name=clearLeader]:eq(1)").attr("disabled",true);
		$("[name=dealPersonNames]:eq(1)").attr("disabled",true);
		$("[name=dealPersonStr]:eq(1)").attr("disabled",true);
		$("[name=dealPersonIdStr]:eq(1)").attr("disabled",true);
		$("[name=dealLeaderNames]:eq(1)").attr("disabled",true);
		$("[name=dealLeaderStr]:eq(1)").attr("disabled",true);
		$("[name=dealLeaderIdStr]:eq(1)").attr("disabled",true);
	}else{
		$("[name=suggest]:eq(0)").attr("disabled",true);
		$("[name=attachId]:eq(0)").attr("disabled",true);
		$("[id=fontId_1]").hide();
		$("[id=fontId_4]").show();
		chooseRadioFunc2(1);
		$("[name=choice2]").attr("disabled",false);
		$("[name=choice2]:eq(0)").attr("checked",true);
		
		$("[name=choosePerson]:eq(1)").attr("disabled",false);
		$("[name=clearPerson]:eq(1)").attr("disabled",false);
		$("[name=chooseLeader]:eq(1)").attr("disabled",false);
		$("[name=clearLeader]:eq(1)").attr("disabled",false);
		$("[name=dealPersonNames]:eq(1)").attr("disabled",false);
		$("[name=dealPersonStr]:eq(1)").attr("disabled",false);
		$("[name=dealPersonIdStr]:eq(1)").attr("disabled",false);
		$("[name=dealLeaderNames]:eq(1)").attr("disabled",false);
		$("[name=dealLeaderStr]:eq(1)").attr("disabled",false);
		$("[name=dealLeaderIdStr]:eq(1)").attr("disabled",false);
		
		$("[name=choosePerson]:eq(0)").attr("disabled",true);
		$("[name=clearPerson]:eq(0)").attr("disabled",true);
		$("[name=chooseLeader]:eq(0)").attr("disabled",true);
		$("[name=clearLeader]:eq(0)").attr("disabled",true);
		$("[name=dealPersonNames]:eq(0)").attr("disabled",true);
		$("[name=dealPersonStr]:eq(0)").attr("disabled",true);
		$("[name=dealPersonIdStr]:eq(0)").attr("disabled",true);
		$("[name=dealLeaderNames]:eq(0)").attr("disabled",true);
		$("[name=dealLeaderStr]:eq(0)").attr("disabled",true);
		$("[name=dealLeaderIdStr]:eq(0)").attr("disabled",true);
	}
	$("[name=dealPersonNames]:disabled").val("");
	$("[name=dealPersonStr]:disabled").val("");
	$("[name=dealPersonIdStr]:disabled").val("");
	$("[name=dealLeaderNames]:disabled").val("");
	$("[name=dealLeaderStr]:disabled").val("");
	$("[name=dealLeaderIdStr]:disabled").val("");
	$("[name=dealLeaderNames]:enabled").val("<%=dealLeaderNames%>");
	$("[name=dealLeaderStr]:enabled").val("<%=dealLeaderStr%>");
	$("[name=dealLeaderIdStr]:enabled").val("<%=dealLeaderIdStr%>");
}

function chooseRadioFunc2(num){
	$("[name=suggest]").val("");
	if(num==1){
		$("[name=suggest]:eq(1)").attr("disabled",false);
		$("[name=suggest]:eq(2)").attr("disabled",true);
		$("[name=attachId]:eq(1)").attr("disabled",false);
		$("[name=attachId]:eq(2)").attr("disabled",true);
		$("[id=fontId_2]").show();
		$("[id=fontId_3]").hide();
	}else{
		$("[name=suggest]:eq(1)").attr("disabled",true);
		$("[name=suggest]:eq(2)").attr("disabled",false);
		$("[name=attachId]:eq(1)").attr("disabled",true);
		$("[name=attachId]:eq(2)").attr("disabled",false);
		$("[id=fontId_2]").hide();
		$("[id=fontId_3]").show();
	}
}

function showSuggest(){
	var dealPersonNamesText = $("[name=dealPersonNames]:enabled").val();
	var dealLeaderNamesText = $("[name=dealLeaderNames]:enabled").val();
	var showText = "";
	if(dealPersonNamesText!=""){
		showText += "请"+dealPersonNamesText+"提出意见。";
	}
	if(dealLeaderNamesText!=""){
		showText += "请"+dealLeaderNamesText+"阅示。";
	}
	$("[name=suggest]:enabled").val(showText);
}
</script>
<div id="handle_zone" class="f_window" style="display:none;">
	<font id="deptUserTreeZone" class="deptTreeZone" root="<%=deptId %>" checkNode="dealPersonIdStr" nodeLoginName="dealPersonStr"
		nodeId="dealPersonIdStr" tnodeName="dealPersonNames"></font>
		<font id="deptLeaderTreeZone" class="deptTreeZone" root="<%=deptId %>" checkNode="dealLeaderIdStr" nodeLoginName="dealLeaderStr"
		nodeId="dealLeaderIdStr" tnodeName="dealLeaderNames"></font>
	<h3 class="clearfix mb10">
		<span class="fl">部门业务处理员/收发员</span>
		<div class="fr close" id="handleDivClose">关闭窗口</div>
	</h3>
	<div class="con">
     	
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" checked="checked" name="choice" value="1" onclick="chooseRadioFunc(1);"/>
				</td>
				<td>业务转发（不进行具体业务处理）</td>
			</tr>
	           	<tr height="30">
	           		<td></td>
	  			<td>
	  				选择人员：
	  				<input type="text" id="dealPersonNames" name="dealPersonNames" value="" readonly class="inputLine" style="width:240px;height: 22px"/>
	  				<input type="hidden" id="dealPersonStr" name="dealPersonStr" value="">
	  				<input type="hidden" id="dealPersonIdStr" name="dealPersonIdStr" value="">
	  				<input name="choosePerson" type="button" value="..." class="btn"/>
	  				<input type="button" name="clearPerson" value="清除" class="btn" />
	  			</td>
					</tr>
	  		<tr height="30">
	  			<td style="text-align:right;"><font style="color:red;" id="fontId_1">*</font></td>
	  			<td>
	  				选择领导：
					<input type="text" id="dealLeaderNames" name="dealLeaderNames" value="<%=dealLeaderNames %>" readonly class="inputLine" style="width:240px;height: 22px"/>
	  				<input type="hidden" id="dealLeaderStr" name="dealLeaderStr" value="<%=dealLeaderStr %>">
	  				<input type="hidden" id="dealLeaderIdStr" name="dealLeaderIdStr" value="<%=dealLeaderIdStr %>">
	  				<input name="chooseLeader" type="button" value="..." class="btn"/>
	  				<input type="button" name="clearLeader" value="清除" class="btn"/>
	  			</td>
	  		</tr>
	  		
	  		<tr>
	  			<td colspan=2>
			  		<table id="suggest1" width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
				  			<td class="td_1" style="text-align:right;">&nbsp;<font style="color:red;display:inline;" id="fontId_1">*</font></td>
		                	<td textareaId="suggest1" peopleId="choiceZone1">
		                	<span class="fl">
		                	意见：<input type="hidden" name="attachId" value=""/></span>
		                	<span  style="display:inline;"  id="fontId_1"><a class="createSuggest" href="javascript:void(0)" onclick="showSuggest();">生成意见</a></span>
		                	<!--  
		                	<span class="fr" style="display:inline;"><a class="previewSuggest">意见预览</a></span>
		                	-->
		                	</td>
		              	</tr>
		              	<tr>
		              		<td class="td_1">&nbsp;</td>
		                	<td><textarea id="suggest" name="suggest" rows="3"></textarea></td>
		              	</tr>
	              	</table>
           		</td>
           	</tr>
		</table>
		
		<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="2" onclick="chooseRadioFunc(2);"/>
				</td>
				<td>业务处理</td>
			</tr>
			<tr>
				<td class="td_1">&nbsp;</td>
				<td>
					<table id="choiceZone2_1" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr height="30">
							<td class="td_1">
								<input type="radio" name="choice2" value="1" onclick="chooseRadioFunc2(1);"/></td>
				  			<td style="width:99%;">本人没有意见</td>
				  		</tr>
				  		<tr>
					  		<td colspan=2>
						  		<table id="suggest2_1" width="100%" border="0" cellspacing="0" cellpadding="0">
							  		<tr>
							  			<td class="td_1">&nbsp;</td>
							  			<td textareaId="suggest2_2" peopleId="choiceZone2">
							  				<span class="fl" >备注：</span>
							  				<span class="fr" style="display:inline;" id="fontId_2">
							                	<a name="suggest_attach" class="suggest_attach" target="#" >
												<input type="hidden" name="attachId" id="attachId_1" value=""/>
							                	上传意见附件(<span style="display:inline;" id="fjcount_1" class="fjcount">0</span>)</a>
							                	<!--<a class="previewSuggest">备注预览</a>-->
						                	</span>
							  			</td>
							  		</tr>
							  		<tr>
							  			<td class="td_1">&nbsp;</td>
					                	<td>
					                	<textarea id="suggest" name="suggest" rows="3"></textarea></td>
					              	</tr>
				              	</table>
			              	</td>
		              	</tr>
				  	</table>
				  	
				  	<table id="choiceZone2_2" width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr height="30">
				  			<td class="td_1">
				  				<input type="radio" name="choice2" value="2" onclick="chooseRadioFunc2(2);"/></td>
				  			<td style="width:99%;">本人有新意见</td>
		  				</tr>
		  				<tr><td colspan=2>
			  				<table id="suggest2_2" width="100%" border="0" cellspacing="0" cellpadding="0">
				  				<tr>
				  					<td class="td_1" style="text-align:right;">&nbsp;<font style="color:red;display:inline;" id="fontId_3">*</font></td>
						  			<td textareaId="suggest2_2" peopleId="choiceZone2">
						  				<span class="fl" >
						  				意见：
						  				</span>
						  				<span  style="display:inline;" id="fontId_3"><a class="createSuggest" href="javascript:void(0)" onclick="showSuggest();">生成意见</a></span>
						  				<span class="fr" style="display:inline;" id="fontId_3">
							                	<a name="suggest_attach" class="suggest_attach" target="#" >
												<input type="hidden" name="attachId" id="attachId_2" value=""/>
							                	上传意见附件(<span style="display:inline;" id="fjcount_2" class="fjcount">0</span>)</a>
							                	<!--<a class="previewSuggest">备注预览</a>-->
						                	</span>
						  			</td>
						  		</tr>
						  		<tr>
						  			<td class="td_1">&nbsp;</td>
				                	<td>
				                	<textarea id="suggest" name="suggest" rows="3"></textarea></td>
				              	</tr>
			              	</table>
		              	</td></tr>
				  	</table>
				  	
				  	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr height="30">
				  			<td></td>
				  			<td>下一步处理人员： </td>
		  				</tr>
		  				
				  		<tr height="30">
				  			<td></td>
				  			<td>
				  				选择人员：
				  				<input type="text" id="dealPersonNames" name="dealPersonNames" value="" readonly class="inputLine" style="width:240px;height: 22px"/>
				  				<input type="hidden" id="dealPersonStr" name="dealPersonStr" value="">
				  				<input type="hidden" id="dealPersonIdStr" name="dealPersonIdStr" value="">
				  				<input name="choosePerson" type="button" value="..." class="btn"/>
				  				<input type="button" name="clearPerson" value="清除" class="btn" />
				  			</td>
				  		</tr>
				  		
				  		<tr height="30">
				  			<td style="text-align:right;"><font style="color:red;" id="fontId_4">*</font></td>
				  			<td> 
								选择领导：
								<input type="text" id="dealLeaderNames" name="dealLeaderNames" value="<%=dealLeaderNames %>" readonly class="inputLine" style="width:240px;height: 22px"/>
				  				<input type="hidden" id="dealLeaderStr" name="dealLeaderStr" value="<%=dealLeaderStr %>">
				  				<input type="hidden" id="dealLeaderIdStr" name="dealLeaderIdStr" value="<%=dealLeaderIdStr %>">
				  				<input name="chooseLeader" type="button" value="..." class="btn"/>
				  				<input type="button" name="clearLeader" value="清除" class="btn"/>
				  			</td>
				  		</tr>
		  			</table>
		  		</td>
		  	</tr>
	  		<!--  
            <tr><td class="td_1" >操作栏</td></tr>
           	<tr><td><textarea id="operate_msg" name="operate_msg" rows="2" readonly>(会签)提交至部门领导处理或转发部门业务人员处理。</textarea></td></tr>
      	  	-->
		</table>
	</div>
	<div class="button t_c">
		<input id="handleSubmit" type="button" onclick="handleFunc(this);" value="确 认">
		<input id="handleClose" type="button" value="取 消">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path %>/send/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>

