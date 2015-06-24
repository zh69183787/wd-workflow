<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wonders.common.model.vo.TaskUserVo"%>
<%@page import="com.wonders.constants.LoginConstants"%>
<%@page import="com.wonders.send.mainProcess.send.model.bo.TDocSend" %>
<%@page import="com.wonders.send.model.bo.ValidFile" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@page import="java.util.Map" %>

<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();

WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());	
TDocSend model =(TDocSend)request.getAttribute("model");
String dept_id = (String)session.getAttribute("dept_id");
String loginName = (String)session.getAttribute("login_name");
String assignedtouser = request.getParameter("assignedtouser");
if(assignedtouser==null){
	assignedtouser = request.getParameter("taskuser");
}
Map<String,TaskUserVo> userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
if(userMap!=null){
	TaskUserVo taskUserVo = userMap.get(assignedtouser);
	if(taskUserVo!=null){
		dept_id = taskUserVo.getDeptId();
		loginName = taskUserVo.getLoginName().replace("ST/","");
	}
}

String codeId = request.getParameter("codeId");
if(codeId==null){
	codeId = "";
}
String addParam = "";
if("新发文流程".equals(model.getModelid())){
	addParam = "&listType=2&codeId="+codeId;
}
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>
		
	</title>
	<link href="<%=path %>/send/css/formalize.css" rel="stylesheet">
	<link href="<%=path %>/send/css/page.css" rel="stylesheet">
	<link href="<%=path %>/send/css/default/imgs.css" rel="stylesheet">
	<link href="<%=path %>/send/css/reset.css" rel="stylesheet">
	<script src="<%=path %>/send/js/html5.js"></script>
	<script src="<%=path %>/send/js/jquery-1.4.2.min.js"></script>
	<script src="<%=path %>/send/js/jquery-1.7.1.js"></script>
	<script src="<%=path %>/send/js/jquery.formalize.js"></script>
	<script src="<%=path %>/send/js/form/jquery.form.js"></script>
	<script type="text/javascript" src="<%=path %>/send/js/common/deptSubProcess.js"></script>
	<script type="text/javascript" src="<%=path %>/send/js/common/common.js"></script>
	<script type="text/javascript" src="<%=path %>/send/js/contextpath.js"></script>
	<script type="text/javascript" src="<%=path %>/send/js/messagebox.js"></script>
	<script type="text/javascript" src="<%=path %>/send/js/attach.js"></script>
	<script type="text/javascript" src="<%=path %>/send/js/tDocSend/print.js"></script>
	<style>
	.deptTreeZone{display:none;}
	.redMark{color:red;display:inline;}
	.r_bor{border-right:#000 1px solid}
	</style>
	
	<script>
		
		$(document).ready(function(){
			initButton();
			$("#title1").html($("#typeTitle").val()+"<br>发文稿纸");

            $("#vrk").click(function(){
                window.open("<%=path%>/attach/loadFileList.action?fileGroup=contentAttachmentId&fileGroupName=projectAttachIdGroup&fileGroupId="+$(this).attr("mainId")+"&procType=view");
            });
		});
		
		function initButton(){
			//清除
			$(":button[name=clearPerson]").click(function(){
				var td = $(this).parent("td");
				var idObj = $(td).find(":hidden[name='dealPersonStr']");
				var nameObj = $(td).find(":text[name='dealPersonNames']");
				var loginNameObj = $(td).find(":hidden[name='dealPersonIdStr']");
				clearPeople(idObj,nameObj,loginNameObj);
				return false;
			})
		
			$(":button[name='clearLeader']").click(function(){
				var td = $(this).parent("td");
				var idObj = $(td).find(":hidden[name='dealLeaderStr']");
				var nameObj = $(td).find(":text[name='dealLeaderNames']");
				var loginNameObj = $(td).find(":hidden[name='dealLeaderIdStr']");
				clearPeople(idObj,nameObj,loginNameObj);
				return false;
			})
		
			
			//部门人员选择
			$(":button[name=choosePerson]").click(function(){
				//var td = $(this).parent("td");
				//var idObj = $(td).find(":hidden[name='dealPersonStr']");
				//var nameObj = $(td).find(":text[name='dealPersonNames']");
				//alert("未实现");
	
				selectDeptUser("deptUserTreeZone");
				
				//人员选择树(未实现)
				return false;
			})
			
			//部门领导选择
			$(":button[name='chooseLeader']").click(function(){
				//var td = $(this).parent("td");
				//var idObj = $(td).find(":hidden[name='dealLeaderStr']");
				//var nameObj = $(td).find(":text[name='dealLeaderNames']");
				//alert("未实现");
				//人员选择树(未实现)
				selectDeptLeaders("deptLeaderTreeZone");
				
				return false;
			})
			
			
		}
		
		function transmitNotice(obj){
			if($("[name=dealPersonStr]").val()==""&&$("[name=dealLeaderStr]").val()==""){
				alert("请选择传阅人员或传阅领导！");
				return false;
			}
			var formOptions1 = {
				cache:false,
				type:'post',
				callback:null,
				dataType :'json',
				url:contextpath+"/send-tDocSend/transmitNotice.action",
			    success:function(data){
					//var obj = JSON.parse(data);
					//$("#formUpdate").submit();
					//$("#formUpdate").ajaxSubmit(formOptions); 
					if(data!=null&&data.if_success=="yes"){
						alert("传阅成功！");
						window.opener=null;	
					    window.open("","_self");
					    window.close();
					}else{
						alert("传阅失败，请联系管理员！");
					}
					
					return false;
			    }
			};
			
			$(obj).attr("disabled",true);
			$("#form").ajaxSubmit(formOptions1); 
		}
	</script>
	
</head>
<body class="Flow" style="background:none">
<jsp:include page="validRemark.jsp"/>
<form action="<%=path %>/send-tDocSend/deal.action" id="form" name="form" method="post">
	<input type="hidden" value="<s:property value='tDocSend.id'/>" name="id"/>
	<input type="hidden" value="<s:property value='tDocSend.typeTitle'/>" id="typeTitle"/>
	<input type="hidden" value="<s:property value='#request.taskId'/>" name="taskId"/>
	<input type="hidden" value="<s:property value='#request.stepName'/>" name="stepName" id="stepName"/>
	<input type="hidden" value="<s:property value='#request.modelName'/>" name="modelName"/>
	<input type="hidden" value="<s:property value='#request.processName'/>" name="processName"/>
	<input type="hidden" value="<s:property value='#request.incidentNo'/>" name="incidentNo"/>
	<input type="hidden" value="<s:property value='#request.pinstanceId'/>" name="pinstanceId"/>
	<input type="hidden" value="<s:property value='#request.taskUserName'/>" name="taskUserName"/>
	<input type="hidden" value="<s:property value='#request.assignedtouser'/>" name="assignedtouser"/>
	<input type="hidden" value="<s:property value='#request.print'/>" id="print"/>
	
	<input type="hidden" value="<s:property value='#request.processName'/>" name="cname"/>
	<input type="hidden" value="<s:property value='#request.pinstanceId'/>" name="cincident"/>
	<input type="hidden" value="<s:property value='#request.stepName'/>" name="steplabel"/>
	<input type="hidden" value="<s:property value='#request.modelName'/>" name="pname"/>
	<input type="hidden" value="<s:property value='#request.incidentNo'/>" name="pincident"/>
	<div class=" transparent" id="maskDiv" style="display:none;" style="filter:alpha(opacity=30);opacity:0.3;"></div>
	
	<div class="f_bg_fw" style="background:none">
		
     	

    	<!-- <div class="logo_1"></div> -->
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top_fw" style="min-height:400px;">
                        	<h1 class="t_c" id="title1"></h1>
							<div class="mb10">

								<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									<thead>
									<th colspan="4" class="lableTd6">
									</th>
									</thead>
								  
	                          	  </table>
	                          	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2"> 
	                          	    <tr class="content6">
	                                    <td class="lableTd t_r">发文字号：</td>
	                                    <td colspan="3" class="content6">
	                                    	<span style="display:inline"><s:property value='tDocSend.sendId'/></span>
	                                    </td>
                                  	</tr>
	                          	    <tr class="content6">
	                                    <td class="lableTd t_r">标题</td>
	                                    <td colspan="3" class="content6">
	                                    	<s:property value='tDocSend.docTitle'/>
	                                    	<input type="hidden" name="docTitle" value="<s:property value='tDocSend.docTitle'/>"/>
	                                    </td>
                                  	</tr>
									<tr class="content7">
										<td class="lableTd t_r">正式附件</td>
										<td colspan="3">
											<input type="hidden" name="contentAtt" id="contentAttachmentId" value="<s:property value="tDocSend.contentAttMain"/>" />
											<%--
												<iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="100%" id="contentAttachmentFrame"
												name="contentAttachmentFrame"
												src="<%=path%>/attach/loadFileList.action?fileGroup=contentAttachmentId&fileGroupName=projectAttachIdGroup&fileGroupId=<s:property value="tDocSend.contentAttMain"/>&userName=<%=loginName %>&loginName=<%=loginName %>&procType=view&targetType=frame&type=1"></iframe>
											 --%>	
												<iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="100%" id="contentAttachmentFrame"
												name="contentAttachmentFrame"
												src="<%=path%>/attach/loadFileList.action?fileGroup=contentAttachmentId&fileGroupName=projectAttachIdGroup&fileGroupId=<s:property value="tDocSend.contentAttMain"/>&userName=<%=loginName %>&loginName=<%=loginName %>&procType=view&targetType=frame<%=addParam %>"></iframe>
										</td>
									</tr>
									<tr class="content7" style="display:none;">
										<td class="lableTd t_r">传阅</td>
										<td colspan="3">
							  				<font id="deptUserTreeZone" class="deptTreeZone" root="<%=dept_id %>" checkNode="dealPersonIdStr" nodeLoginName="dealPersonStr"
											nodeId="dealPersonIdStr" tnodeName="dealPersonNames"></font>
											<font id="deptLeaderTreeZone" class="deptTreeZone" root="<%=dept_id %>" checkNode="dealLeaderIdStr" nodeLoginName="dealLeaderStr"
											nodeId="dealLeaderIdStr" tnodeName="dealLeaderNames"></font>
							  				
							  				选择人员：
							  				<input type="text" id="dealPersonNames" name="dealPersonNames" value="" readonly class="inputLine" style="width:240px;height: 22px"/>
							  				<input type="hidden" id="dealPersonStr" name="dealPersonStr" value="">
							  				<input type="hidden" id="dealPersonIdStr" name="dealPersonIdStr" value="">
							  				<input name="choosePerson" type="button" value="..." class="btn"/>
							  				<input type="button" name="clearPerson" value="清除" class="btn" />
							  				<br>
							  				选择领导：
											<input type="text" id="dealLeaderNames" name="dealLeaderNames" value="" readonly class="inputLine" style="width:240px;height: 22px"/>
							  				<input type="hidden" id="dealLeaderStr" name="dealLeaderStr" value="">
							  				<input type="hidden" id="dealLeaderIdStr" name="dealLeaderIdStr" value="">
							  				<input name="chooseLeader" type="button" value="..." class="btn"/>
							  				<input type="button" name="clearLeader" value="清除" class="btn"/>
										</td>
									</tr>
                          	  	</table>
                        	</div>
							<div class="mb10 t_c">
								<!-- <input type="button" id="closeBtn" value="传阅" onclick="transmitNotice(this);"/>&nbsp; -->
								<input type="button" id="closeBtn" value="关闭" onclick="window.close();"/>&nbsp;
								<div id="formUpdate_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
							      <p style="width:auto;display:inline;"><img src="<%=path %>/images/loading.gif" style="display:inline;"/>
							      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
							    </div>
							</div>
							
							<div class="footer"></div>
						</div>
                    </div>
                </div>
            </div>
        </div>
 	</div>
 	
 	</form>
</body>
</html>