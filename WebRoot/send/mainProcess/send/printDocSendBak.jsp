<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wonders.send.mainProcess.send.model.bo.TDocSend"%>
<%@page import="com.wonders.send.future.service.FutureService"%>
<%@page import="org.springframework.web.context.WebApplicationContext" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.wonders.common.model.vo.TaskUserVo" %>
<%@ page import="com.wonders.constants.LoginConstants" %>
<%@page import="com.wonders.util.PWSProperties"%>
<%@page import="java.util.*" %>

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

String ultimusIp = PWSProperties.getValueByKey("pws_server_ip");
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
			initMessagebox();
			initMessageboxClose();
			$("#title1").html($("#typeTitle").val()+"<br>发文稿纸");
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
				selectDeptLeader("deptLeaderTreeZone");
				
				return false;
			})
			
			
		}
	</script>
	
</head>
<body class="Flow" style="background:none">
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
     	<s:if test="#request.print!=1">
		<div class="Divab1">
			<!--1st-->
			<div class="panel_6">
				<div class="divT">
					<div class="mb10 icon icon_1"></div>
					<div class="more_4">
						<a title="更多" href="#">更多</a>
					</div>
				</div>
				<div class="divH">
					<div class="divB">
						<h5 class="clearfix">
							业务办理
						</h5>
						<div class="con">
							<ul class="button clearfix">
								<%--<s:if test="#request.stepName!=''&&#request.ifDbx=='yes'">--%>
								<s:if test="#request.stepName!=''">
								<li>	
									<a id="todo_handle" class="ywbl" href="javascript:void(0);">办理</a>
								</li>
								</s:if>
								
								<li>
									<a id="todo_print" class="print"  href="javascript:void(0);" onclick="toPrintPage();">打印</a>
								</li>
								<s:if test="#request.taskId!=''">
								<li>
									<a id="todo_scan" class="jk"  href="http://<%=ultimusIp%>/sLogin/workflow/TaskStatus.aspx?TaskID=<s:property value='#request.taskId'/>" target="_blank">监控</a>
								</li>
								</s:if>
								<!--
								<li>
									<a id="todo_tips" class="tips" href="javascript:void(0);">小提示</a>
								</li>
								-->
								<!--  
				               	<li><a class="imp" href="#">公文导入</a></li>
				               	<li><a class="exp" href="#">公文导出</a></li>
				               	-->
							</ul>
						</div>
					</div>
					<div class="divF"></div>
				</div>
			</div>
			
		</div>
		</s:if>
     	

    	<!-- <div class="logo_1"></div> -->
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top_fw">
                        	<h1 class="t_c" id="title1"></h1>
                        		 
                        		<s:if test="#request.print!=1">
                        		<!-- 	<jsp:include page="inprogressImg.jsp"/> -->
                        		</s:if>
                        		
	                            <!-- 
	                            <div class="mb10 Step"><b style="display:inline">发文字号:</b><span style="display:inline"><s:property value='tDocSend.sendId'/></span>
	                            <span style="float:right;display:inline">
									<b style="display:inline">文别：</b><span style="display:inline"><s:property value='tDocSend.docClass'/></span>
									<b style="display:inline">&nbsp;&nbsp;密别：</b><span style="display:inline"><s:property value='tDocSend.secretClass'/></span>
									<b style="display:inline">&nbsp;&nbsp;缓急：</b><span style="display:inline"><s:property value='tDocSend.hj'/></span>
								</span>
	                            </div>
	                           -->  
							<div class="mb10">

								<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									<thead>
									<th colspan="4" class="lableTd6"><h5><b style="display:inline">发文字号:</b></h5>
										<h5 style="display:inline;font-weight:normal;background-image:none;"><s:property value='tDocSend.sendId'/></h5>
			                            <h5 style="float:right;display:inline;background-image:none;">
											<b style="display:inline">文别：</b><span style="display:inline;font-weight:normal;"><s:property value='tDocSend.docClass'/></span>
											<b style="display:inline">&nbsp;&nbsp;&nbsp;&nbsp;密别：</b><span style="display:inline;font-weight:normal;"><s:property value='tDocSend.secretClass'/></span>
											<b style="display:inline">&nbsp;&nbsp;&nbsp;&nbsp;缓急：</b><span style="display:inline;font-weight:normal;"><s:property value='tDocSend.hj'/>&nbsp;&nbsp;</span>
										</h5>
									</th>
									</thead>
								  
								  	<tbody>
								  	<tr class="content7">
	                                    <td class="lableTd t_r" style="width:15%">文件类型</td>
	                            	    <td style="width:35%">
		                            	    <s:property value='tDocSend.fileType'/>
		                            	</td>
	                                    <td class="lableTd t_r" style="width:15%">保密期限</td>
	                            	    <td><s:property value='tDocSend.secretLimit'/>
	                            	    <s:if test="tDocSend.secretLimit!=''&&tDocSend.secretLimit!=null">
	                            	    	年
	                            	    </s:if>
	                            	    </td>
	                          	    </tr>
	                          	   </tbody>
	                          	  </table>
	                          	  <jsp:include page="suggest.jsp"/>
	                          	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2"> 
	                          	    <tr class="content6">
	                                    <td class="lableTd t_r">标题</td>
	                                    <td colspan="3" class="content6">
	                                    	<s:property value='tDocSend.docTitle' escape="0"/>
	                                    	<input type="hidden" name="docTitle" value="<s:property value='tDocSend.docTitle'/>"/>
	                                    </td>
                                  	</tr>
                                  	<tr class="content6">
										<td class="lableTd t_r">主送单位</td>
										<td class="pl18" colspan="3">
											<s:property value='tDocSend.sendMainW'/>
											<br><br>
											<s:property value='tDocSend.sendMain'/>
										</td>
								  	</tr>
								  	<tr class="content6">
										<td class="lableTd t_r">抄送单位</td>
										<td class="pl18" colspan="3">
										<s:property value='tDocSend.snedCopy'/>
										<br><br>
										<s:property value='tDocSend.sendInside'/>
										</td>
								  	</tr>
								  	<tr class="content6">
										<td class="lableTd t_r">内发</td>
										<td class="pl18" colspan="3">
										<s:property value='tDocSend.sendReportW'/>
										</td>
								  	</tr>
                                  	<s:if test="#request.print!=1">
									<tr class="content7">
										<td class="lableTd t_r">相关附件</td>
										<td colspan="3">
											<!--  -->
											<input type="hidden" name="contentAtt" id="contentAttachmentId" value="<s:property value="tDocSend.contentAtt"/>" />
											
											<s:if test="#request.stepName=='核稿'||#request.stepName=='办结'||#request.stepName=='秘书'||#request.stepName=='领导'||#request.stepName=='签发秘书'||#request.stepName=='签发领导'">
												<iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="100%" id="contentAttachmentFrame" name="contentAttachmentFrame" 
										  		src="<%=path %>/attach/loadFileList.action?fileGroup=contentAttachmentId&fileGroupName=projectAttachIdGroup&fileGroupId=<s:property value="tDocSend.contentAtt"/>&userName=<%=loginName %>&loginName=<%=loginName %>&procType=edit&targetType=frame&type=1"></iframe>
											</s:if>
											<s:else>
												<iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="100%" id="contentAttachmentFrame"
												name="contentAttachmentFrame"
												src="<%=path%>/attach/loadFileList.action?fileGroup=contentAttachmentId&fileGroupName=projectAttachIdGroup&fileGroupId=<s:property value="tDocSend.contentAtt"/>&userName=<%=loginName %>&loginName=<%=loginName %>&procType=view&targetType=frame&type=1"></iframe>
											</s:else>	
										</td>
									</tr>
									</s:if>
                          	    	<jsp:include page="suggest1.jsp"/>
									<tr class="content7">
	                                    <td style="width:50%;border-right:#000 1px solid;" colspan="2" >
	                                    	印制份数：&nbsp;<s:property value='tDocSend.docCount'/>份</td>
	                                    <td style="width:50%" colspan="2">
	                                    	印发日期：&nbsp;<s:property value="tDocSend.sendDate"/></td>
	                          	    </tr>	
									<jsp:include page="suggest2.jsp"/>
                          	  	</table>
                        	</div>
                        	<s:if test="#request.print==1">
                        	<div class="mb10 t_l">
                        	注：<br>
                        	1.纸质影印件仅供参考，实际内容以综合业务协同平台网上为准。<br>
							2.纸质归档件需加盖本单位电子文件打印专用章。
                        	</div>
                        	</s:if>
							<div class="mb10 t_c">
								<s:if test="#request.print==1">
								<input type="button"  value="打印" onclick="printFunc();"/>&nbsp;
								</s:if>
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
 	
 	<div class="transparent" id="maskDiv" style="display: none;" style="filter:alpha(opacity=30);opacity:0.3;"></div>
	<s:if test="#request.stepName=='核稿'">
		<jsp:include page="verifyDraft.jsp"/>
	</s:if>
	<s:elseif test="#request.stepName=='校稿'">
		<jsp:include page="proofreading.jsp"/>
	</s:elseif>
	<s:elseif test="#request.stepName=='办结'">
		<jsp:include page="dealFinish.jsp"/>
	</s:elseif>
	<s:elseif test="#request.stepName=='部门接受人工作分发'">
		<jsp:include page="../../subProcess/dept/receiver.jsp"/>
	</s:elseif>
	<s:elseif test="#request.stepName=='部门业务人员处理'">
		<jsp:include page="../../subProcess/dept/deal.jsp"/>
	</s:elseif>
	<s:elseif test="#request.stepName=='部门领导审核'">
		<jsp:include page="../../subProcess/dept/leader.jsp"/>
	</s:elseif>
	<s:elseif test="#request.stepName=='领导'">
		<jsp:include page="../../subProcess/sign/leader.jsp"/>
	</s:elseif>
	<s:elseif test="#request.stepName=='秘书'">
		<jsp:include page="../../subProcess/sign/secretary.jsp"/>
	</s:elseif>
	<s:elseif test="#request.stepName=='签发领导'">
		<jsp:include page="../../subProcess/issue/leader.jsp"/>
	</s:elseif>
	<s:elseif test="#request.stepName=='签发秘书'">
		<jsp:include page="../../subProcess/issue/secretary.jsp"/>
	</s:elseif>
 	</form>
</body>
</html>