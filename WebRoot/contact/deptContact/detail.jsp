<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<meta http-equiv="x-ua-compatible" content="IE=8">
		<title>工作联系单</title>
		<link href="<%=path %>/contact/css/formalize.css" rel="stylesheet">
		<link href="<%=path %>/contact/css/page.css" rel="stylesheet">
		<link href="<%=path %>/contact/css/imgs.css" rel="stylesheet">
		<link href="<%=path %>/contact/css/reset.css" rel="stylesheet">

		<link type="text/css" href="<%=path %>/contact/js/datepicker/css/flick/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
		<!--[if IE 6.0]>
		<script src="js/iepng.js" type="text/javascript"></script>
		<script type="text/javascript">
		     EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
		</script>
		<![endif]-->
		<style>
			pre {
				white-space: pre-wrap;       /* css-3 */
				white-space: -moz-pre-wrap;  /* Mozilla, since 1999 */
				white-space: -pre-wrap;      /* Opera 4-6 */
				white-space: -o-pre-wrap;    /* Opera 7 */
				word-wrap: break-word;       /* Internet Explorer 5.5+ */
				word-break:break-all;
				overflow:hidden;
			}
		</style>
		<script src="<%=path %>/contact/js/html5.js"></script>
		<script src="<%=path %>/contact/js/jquery-1.7.1.js"></script>
		<script src="<%=path %>/contact/js/jquery.formalize.js"></script>
		<script src="<%=path %>/contact/js/Player.js"></script>
		<!--  
		<script type="text/javascript" src="<%=path %>/js/datepicker/js/jquery-ui-1.8.18.custom.min.js"></script>
		<script type="text/javascript" src="<%=path %>/js/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
		-->
		<script type="text/javascript" src="<%=path %>/contact/js/form/jquery.form.js"></script>
		<script type="text/javascript" src="<%=path %>/contact/js/json2.js"></script>
		<script type="text/javascript" src="<%=path %>/contact/js/contextpath.js"></script>
		<script type="text/javascript" src="<%=path %>/contact/js/attach.js"></script>
		<script type="text/javascript" src="<%=path %>/contact/deptContact/js/ref.js"></script>

		<script>
		$().ready(function(){
			$("#closeBtn").click(function(){
				return goExit();
			});

			$("#todo_handle").parent("li").hide();
		});
		function goExit(){
			if(confirm("确认关闭？")){
				self.close();
			}
		}
		</script>
		<%-- 所需JS
		<jsp:include page="zone/js.jsp" flush="true">
			<jsp:param name="steplabel" value="<%=steplabel%>" />
		</jsp:include>

		<jsp:include page="../deptSubProcess/zone/js.jsp" flush="true">
			<jsp:param name="steplabel" value="<%=steplabel%>" />
		</jsp:include>
		--%>
	</head>
	
	<body class="Flow">
		<form action="" name="formOperate" id="formOperate" method="post">
			<div class="transparent" id="maskDiv" style="display: none;" style="filter:alpha(opacity=30);opacity:0.3;"></div>

			<%-- 操作页面
			<s:include value="zone/handle.jsp">
				<s:param name="flowType" value="flowType"></s:param>
			</s:include>
			<s:include value="../deptSubProcess/zone/handle.jsp">
			</s:include>
			--%>
			<div class="f_bg">
				<jsp:include page="zone/todo.jsp" flush="true" />

				<div class="logo_1"></div>
				<div class="gray_bg">
					<div class="gray_bg2">
						<div class="w_bg">
							<div class="Bottom">
								<div class="Top">
									<h1 class="t_c">上海申通地铁集团有限公司<br>工作联系单</h1>
									<!--<s:include value="zone/process.jsp">
										<s:param name="id" value="mainVo.id"></s:param>
									</s:include>-->
									<div class="mb10 Step">编号：<s:property value="params.param['mainBo.serial']"/></div>
									<div class="mb10">
										<input type="hidden" id="id" name="id" value="<s:property value="mainVo.id"/>"/>
										<input type="hidden" name="pname" value="<s:property value="params.processParam['pname']"/>"/>
										<input type="hidden" name="pincident" value="<s:property value="params.processParam['pincident']"/>"/>
										<input type="hidden" name="cname" value="<s:property value="params.processParam['cname']"/>"/>
										<input type="hidden" name="cincident" value="<s:property value="params.processParam['cincident']"/>"/>
										<input type="hidden" name="steplabel" value="<s:property value="params.processParam['steplabel']"/>"/>
										<input type="hidden" name="flowType" value="<s:property value="flowType"/>"/>
										<input type="hidden" name="taskuser"  id="taskuser" value="<s:property value="params.processParam['taskuser']"/>"/>
								
										<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
											<thead>
												<th colspan="4" class="lableTd6">
													<h5>工作联系单基本信息栏</h5>
													<span class="fr pt5 mr5"><a href="#" class="" name="suggest_zone_expand" style="cursor: hand;">展开</a> </span>
												</th>
											</thead>
											<tbody>
											<tr class="content6">
												<td class="lableTd t_r">主送部门</td>
												<td class="pl18" colspan=3 ><s:property value="mainVo.mainUnit" />&nbsp;</td>
											</tr>

											<tr class="content6">
												<td class="lableTd t_r">抄送部门</td>
												<td class="pl18" colspan=3 ><s:property value="mainVo.copyUnit" />&nbsp;</td>
											</tr>

											<tr class="content6">
												<td class="lableTd t_r">主题</td>
												<td class="pl18" colspan="3" class="content6"><s:property value="mainVo.theme" /></td>
											</tr>

											<tr class="content7">
												<td class="lableTd t_r">联系时间</td>
												<td width="35%"><s:property value="mainVo.contactDate"/></td>
												<td width="15%" class="lableTd t_r">要求回复时间</td>
												<td><s:property value="mainVo.replyDate"/></td>
											</tr>

											<tr class="content7">
												<td class="lableTd t_r">联系内容</td>
												<td colspan="3">
												<!-- <pre style="word-wrap:break-word;width:100%;white-space: normal;"><s:property value="mainVo.content" escape="0"/></pre>-->
												<pre style="width: 100%; white-space: pre-wrap !important; word-wrap: break-word;"><s:property value="mainVo.content" escape="0"/></pre> 
												</td>
											</tr>

											<tr class="content7">
												<td class="lableTd t_r">附件内容</td>
												<td colspan="3">
													<!--  -->
													<input type="hidden" name="contentAttachmentId" id="contentAttachmentId" value="<s:property value="mainVo.contentAttachmentId"/>" />
													
													<%-- --%>
													<iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="600" id="contentAttachmentFrame"
														name="contentAttachmentFrame"
														src="<%=path%>/attach/loadFileList.action?fileGroup=contentAttachmentId&fileGroupName=projectAttachIdGroup&fileGroupId=<s:property value="mainVo.contentAttachmentId"/>&userName=<s:property value="params.userInfo.userName"/>&loginName=<s:property value="params.userInfo.login_Name"/>&procType=view&targetType=frame&type=1"></iframe>
													
												</td>
											</tr>

											<tr class="content7" id="ref_zone">
												<td class="lableTd t_r">相关资料</td>
												<td colspan="3">
													<div style="display: block; white-space: nowrap; width:100%; float: left;">
														<div id="ref_div_zone" width="100%" style="display: block; BORDER-BOTTOM: 1px inset #E1E1E1;">&nbsp;</div>
													</div>
													<input type="hidden" id="ref_id_zone" name="ref_id_zone" value="<s:property value="params.param['ref_id_zone']"/>"/>
	                                    			<input type="hidden" id="ref_type_zone" name="ref_type_zone" value="view"/>
												</td>
											</tr>

											<tr class="content7">
												<td class="lableTd t_r">发起人</td>
												<td><s:property value="params.param['mainBo.initiatorName']"/></td>
												<td class="lableTd t_r">发起部门</td>
												<td><s:property value="params.param['mainBo.createDeptname']"/></td>
											</tr>
											</tbody>
										</table>
									</div>
									
									<%-- 意见模块 --%>
									<s:action name="deptContactApproveInfo" namespace="/contact-approvedInfo" executeResult="true">
										<s:param name="mainBoId" value="mainVo.id"></s:param>
									</s:action>
									<div class="mb10 t_c">
										<input type="button" id="closeBtn" value="关闭"/>&nbsp;
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