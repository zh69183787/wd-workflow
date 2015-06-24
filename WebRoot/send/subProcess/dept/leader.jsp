<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.wonders.util.StringUtil" %>
<%@page import="org.springframework.web.context.WebApplicationContext" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.wonders.send.common.service.CommonService" %>
<%@ page import="com.wonders.send.external.service.ExternalService" %>
<%@ page import="com.wonders.send.organTree.model.bo.OrganUserBo" %>
<%@ page import="com.wonders.common.model.vo.TaskUserVo" %>
<%@ page import="com.wonders.constants.LoginConstants" %>
<%
String path = request.getContextPath();

//String deptId = StringUtil.getNotNullValueString(request.getParameter("deptId"));
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
CommonService commonService=(CommonService)context.getBean("send-commonService");
String pinstanceId = request.getParameter("pinstanceId");
String processName = request.getParameter("processName");
//String hql11 = "from SendTasks t where t.incident = '"+pinstanceId+"' and t.processName = '"+processName+"' and t.stepLabel like '%部门%'";
String hql11 = "from SendTasks t,Subprocess s where t.processName = '部门内部子流程' and t.incident = '"+pinstanceId+"' and s.cname = t.processName and s.cincident = t.incident and s.pname = '新发文流程' and t.stepLabel like '%部门%'";
List<Object> list11 = commonService.findByHQL(hql11);
String showFlag = "";
if(list11!=null&&list11.size()==1){
	showFlag = "yes";
}

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
var hiddenLeaderName = "";

function handleFunc(obj){
	if($("[name=choice]:checked").val()=="2"){
		if($.trim($("[name=suggest]:enabled").val())==""){
			alert("请填写意见！");
			$("[name=suggest]:enabled").focus();
			return false;
		}
	}else if($("[name=choice]:checked").val()=="3"&&$("[name=choice2]:checked").val()=="2"){
		if($("[name=dealPersonNames]").val()==""){
			alert("请选择处理人员！");
			return false;
		}
		if($("[name=dealLeaderNames]").val()==""){
			alert("请选择领导！");
			return false;
		}
		if($.trim($("[name=suggest]:enabled").val())==""){
			alert("请生成备注！");
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
			url:contextpath+"/send-deptSub/leaderDeal.action",
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
	$("[name=suggest]").each(function(i){
		if((i+1)==num){
			$(this).attr("disabled",false);
		}else{
			$(this).attr("disabled",true);
		}
	});
	$("[name=attachId]").each(function(i){
		if((i+1)==num){
			$(this).attr("disabled",false);
		}else{
			$(this).attr("disabled",true);
		}
	});
	$("[id^=fontId_]").each(function(i){
		if((i+1)==num){
			$(this).show();
		}else{
			$(this).hide();
		}
	});
	if(num==3){
		$("[name=choice2]:first").attr("checked",true);
		if($("#showFlag").val()=="yes"){
			ifDisabled(false);
		}else{
			ifDisabled(true);
		}
		$("[name=choice2]").attr("disabled",false);
	}else{
		$("[name=choice2]").attr("disabled",true);
		ifDisabled(true);
		if(num==1){
			if($("#showFlag").val()=="yes"){
				$("[name=suggest]:enabled").val("同意。");
			}else{
				$("[name=suggest]:enabled").val("审核通过。");
			}
		}else if(num==2){
			if($("#showFlag").val()=="yes"){
				$("[name=suggest]:enabled").val("修改意见。");
			}else{
				$("[name=suggest]:enabled").val("审核不通过。");
			}
		}
	}
}

function chooseRadioFunc2(num){
	if(num==1){
		ifDisabled(true);
	}else if(num==2){
		ifDisabled(false)
	}
}

function ifDisabled(flag){
	$("[name=choosePerson]").attr("disabled",flag);
	$("[name=clearPerson]").attr("disabled",flag);
	$("[name=chooseLeader]").attr("disabled",flag);
	$("[name=clearLeader]").attr("disabled",flag);
	if(flag){
		if($("[name=dealLeaderNames]").val()!=""){
			hiddenLeaderName = $("[name=dealLeaderNames]").val();
			$("[name=dealLeaderNames]").val("");
		}
		$("[id=fontId]").hide();
		$("#spanId").hide();
		$("[name=dealPersonNames]").val("");
	}else{
		if(hiddenLeaderName!=""){
			$("[name=dealLeaderNames]").val(hiddenLeaderName);
			hiddenLeaderName = "";
		}
		$("[id=fontId]").show();
		$("#spanId").show();
	}
}

function showSuggest(){
	var dealPersonNamesText = $("[name=dealPersonNames]").val();
	var dealLeaderNamesText = $("[name=dealLeaderNames]").val();
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
	<input type="hidden" id="showFlag" value="<%=showFlag %>">
	<font id="deptUserTreeZone" class="deptTreeZone" root="<%=deptId %>" checkNode="dealPersonIdStr" nodeLoginName="dealPersonStr"
		nodeId="dealPersonIdStr" tnodeName="dealPersonNames"></font>
		<font id="deptLeaderTreeZone" class="deptTreeZone" root="<%=deptId %>" checkNode="dealLeaderIdStr" nodeLoginName="dealLeaderStr"
		nodeId="dealLeaderIdStr" tnodeName="dealLeaderNames"></font>
	<h3 class="clearfix mb10">
		<span class="fl">部门领导处理</span>
		<div class="fr close" id="handleDivClose">关闭窗口</div>
	</h3>
	<div class="con">
     	
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1" style="width:5%;">
					<input type="radio" checked="checked" name="choice" value="1" onclick="chooseRadioFunc(1);"/>
				</td>
				<td>
				<%if("yes".equals(showFlag)){ %>
				部门意见：同意
				<%}else{ %>
				审核通过（形成部门意见）
				<%} %>
				</td>
			</tr>
	  		
	  		<tr>
	  			<td colspan=2>
			  		<table id="suggest1" width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
							  			<td class="td_1">&nbsp;</td>
							  			<td textareaId="suggest2_2" peopleId="choiceZone2">
							  				<span class="fl" >备注：</span>
							  				<span class="fr" style="display:inline;" id="fontId_1">
							                	<a name="suggest_attach" class="suggest_attach" target="#" >
												<input type="hidden" name="attachId" id="attachId_1" value=""/>
							                	上传意见附件(<span style="display:inline;" id="fjcount_1" class="fjcount">0</span>)</a>
							                	<!--<a class="previewSuggest">备注预览</a>-->
						                	</span>
							  				
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
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1" style="width:5%;">
					<input type="radio" name="choice" value="2" onclick="chooseRadioFunc(2);"/>
				</td>
				<td>
				
				<%if("yes".equals(showFlag)){ %>
				部门意见：修改意见
				<%}else{ %>
				审核不通过,有新意见(形成部门意见)
				<%} %>
				</td>
			</tr>
	  		
	  		<tr>
	  			<td colspan=2>
			  		<table id="suggest1" width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
							  			<td class="td_1" style="text-align:right;">&nbsp;<font style="color:red;display:inline;">*</font></td>
							  			<td textareaId="suggest2_2" peopleId="choiceZone2">
							  				<span class="fl" >意见：</span>
							  				
							  				<span class="fr" style="display:inline;" id="fontId_2">
							                	<a name="suggest_attach" class="suggest_attach" target="#">
												<input type="hidden" name="attachId" id="attachId_2" value=""/>
							                	上传意见附件(<span style="display:inline;" id="fjcount_2" class="fjcount">0</span>)</a>
							                	<!--<a class="previewSuggest">备注预览</a>-->
						                	</span>
							  			</td>
							  		</tr>
		              	<tr>
		              		<td class="td_1">&nbsp;</td>
		                	<td><textarea id="suggest" name="suggest" rows="3" ></textarea></td>
		              	</tr>
	              	</table>
           		</td>
           	</tr>
		</table>
		<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="3" onclick="chooseRadioFunc(3);"/>
				</td>
				<td>还需要本部门业务人员继续处理</td>
			</tr>
			<tr>
	  			<td class="td_1" style="text-align:right;">&nbsp;<font style="color:red;display:inline;" id="fontId">*</font></td>
	  			<td textareaId="suggest2_2" peopleId="choiceZone2">
	  				<span class="fl" >备注：</span>
	  				<span  style="display:inline;" id="spanId"><a class="createSuggest" href="javascript:void(0)" onclick="showSuggest();">生成备注</a></span>
	  				<span class="fr" style="display:inline;" id="fontId_3">
	                	<a name="suggest_attach" class="suggest_attach" target="#" >
						<input type="hidden" name="attachId" id="attachId_3" value=""/>
	                	上传意见附件(<span style="display:inline;" id="fjcount_3" class="fjcount">0</span>)</a>
	                	<!--<a class="previewSuggest">备注预览</a>-->
                	</span>
	  			</td>
	  		</tr>
			<tr>
		              		<td class="td_1">&nbsp;</td>
		                	<td><textarea id="suggest" name="suggest" rows="3" ></textarea></td>
		              	</tr>
			<tr>
				<td class="td_1">&nbsp;</td>
				<td>
					<table id="choiceZone2_1" width="100%" border="0" cellspacing="0" cellpadding="0">
						<%if(!"yes".equals(showFlag)){ %>
						<tr height="30">
							<td class="td_1">
								<input type="radio" name="choice2" value="1" onclick="chooseRadioFunc2(1);"/></td>
				  			<td style="width:99%;">须本部门其他人员继续处理,转业务经办人 </td>
				  		</tr>
				  		<%} %>
				  		<tr height="30">
							<td class="td_1">
								<input type="radio" name="choice2" value="2" onclick="chooseRadioFunc2(2);"/></td>
				  			<td style="width:99%;">直接选择处理人员 </td>
				  		</tr>
				  		<tr>
					  		<td colspan=2>
						  		<table id="suggest2_1" width="100%" border="0" cellspacing="0" cellpadding="0">
							  		<tr height="30">
				  			<td style="text-align:right;"><font style="color:red;" id="fontId">*</font></td>
				  			<td>
				  				选择人员：
				  				<input type="text" id="dealPersonNames" name="dealPersonNames" value="" readonly class="inputLine" style="width:240px;height: 22px"/>
				  				<input name="choosePerson" type="button" value="..." class="btn"/>
				  				<input type="button" name="clearPerson" value="清除" class="btn" />
				  				<input type="hidden" id="dealPersonStr" name="dealPersonStr" value="">
				  				<input type="hidden" id="dealPersonIdStr" name="dealPersonIdStr" value="">
				  			</td>
				  		</tr>
				  		
				  		<tr height="30">
				  			<td style="text-align:right;"><font style="color:red;" id="fontId">*</font></td>
				  			<td> 
								选择领导：
								<input type="text" id="dealLeaderNames" name="dealLeaderNames" value="<%=dealLeaderNames %>" readonly class="inputLine" style="width:240px;height: 22px"/>
				  				<input name="chooseLeader" type="button" value="..." class="btn"/>
				  				<input type="button" name="clearLeader" value="清除" class="btn"/>
				  				<input type="hidden" id="dealLeaderStr" name="dealLeaderStr" value="<%=dealLeaderStr %>">
				  				<input type="hidden" id="dealLeaderIdStr" name="dealLeaderIdStr" value="<%=dealLeaderIdStr %>">
				  			</td>
				  		</tr>
				              	</table>
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

