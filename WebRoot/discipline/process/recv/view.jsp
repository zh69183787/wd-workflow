<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.Calendar" %>
<%@page import="com.wonders.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date" %>
<%@ page import="com.wonders.discipline.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.discipline.workflow.process.recv.constants.DcpRecvMainStepConstants" %>
<%@page import="com.wonders.discipline.workflow.process.recv.constants.DcpRecvMainConstants" %>
<%@page import="com.wonders.discipline.workflow.process.dept.constants.DeptSubStepConstants" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>纪委收文表单</title>
	<link href="<%=path%>/discipline/css/formalize.css" rel="stylesheet">
	<link href="<%=path%>/discipline/css/page.css" rel="stylesheet">
	<link href="<%=path%>/discipline/css/default/imgs.css" rel="stylesheet">
	<link href="<%=path%>/discipline/css/reset.css" rel="stylesheet">
	<link href="<%=path%>/discipline/css/jquery.ui.css" rel="stylesheet">
	<link type="text/css" href="<%=path%>/discipline/css/flick/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
	<!--[if IE 6.0]>
	<script src="js/iepng.js" type="text/javascript"></script>
	<script type="text/javascript">
	     EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
	</script>
	<![endif]-->
	<style>
	.redMark{color:red;display:inline;}
	</style>
	<script src="<%=path%>/discipline/js/contextpath.js"></script>
	<script src="<%=path%>/discipline/js/html5.js"></script>   
	<!--<script src="<%=path%>/discipline/js/jquery.formalize.js"></script>  -->
	<script src="<%=path%>/discipline/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path%>/discipline/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path%>/discipline/js/attach.js"></script>
	<script src="<%=path%>/discipline/js/jquery-form/jquery.form.min.js"></script>
	<script src="<%=path%>/discipline/js/jquery-ui/jquery-ui.js"></script>
	<script src="<%=path%>/discipline/js/jquery-ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="<%=path%>/discipline/js/common/elementControl.js"></script>
	<script src="<%=path%>/discipline/js/jquery-qtip/jquery.qtip-1.0.0-rc3.js"></script>
	
	<script type="text/javascript">
	
	$(function(){
		$("#todo_handle").parent("li").hide();
		$("#closeBtn").click(function(){
			window.opener=null;
			window.open("","_self");
			window.close();
		});
		
		$(".showDetail").on("click",function(){
			$(this).parent("td").next("td").find(".noleader").toggle();
		});
	})	;
	
		
	
	</script>
	
</head>

<body class="Flow">
	<div class="f_bg">
		<s:include value="%{#request.getContextPath()}/discipline/process/common/todo.jsp"></s:include>
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;">中共上海申通地铁集团有限公司纪律检查委员会</b><br><b>收文处理单</b></h1>
                        	
                        	
							<div class="mb10">
							<input type="hidden" id="id" name="id" value="<s:property value="vo.id"/>"/>
								<input type="hidden" name="pname" value="<s:property value="params.processParam['pname']"/>"/>
								<input type="hidden" name="pincident" value="<s:property value="params.processParam['pincident']"/>"/>
								<input type="hidden" name="cname" value="<s:property value="params.processParam['cname']"/>"/>
								<input type="hidden" name="cincident" value="<s:property value="params.processParam['cincident']"/>"/>
								<input type="hidden" name="steplabel" value="<s:property value="params.processParam['steplabel']"/>"/>
								<input type="hidden" name="chiefPerson" value="<s:property value="vo.chiefPerson"/>"/>
								<input type="hidden" id="taskuser" name="taskuser" value="<s:property value="params.processParam['taskuser']"/>"/>
								<input type="hidden" name="taskid" value="<s:property value="params.processParam['taskid']"/>"/>
								
								<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									
								  	<tbody>
									
									<tr class="content7">
	                                    <td class="b0" style="width:15%;" colspan="1">收文编号：</td>
	                            	    <td class="b0" colspan="5">
	                            	   	<s:property value="vo.swId"/>
	                            	   	</td>
	                            	    <td class="b0" colspan="1">收文日期：</td>
	                            	    <td class="b0" colspan="5">
	                            	    <s:property value="vo.swDate"/>
	                            	    </td>
	                          	    </tr>
								
									<tr class="content7">
	                                    <td colspan="1" class="lableTd t_v ">来文单位：</td>
	                            	    <td colspan="5">
	                            	   	 <s:property value="vo.swUnit"/>
	                            	    </td>
	                            	    <td colspan="1" class="lableTd t_v ">文件日期：</td>
	                            	    <td colspan="5"><s:property value="vo.fileDate"/></td>
	                                </tr>
	                                
	                                <tr class="content7">
	                                    <td colspan="1" class="lableTd t_v ">密级：</td>
	                            	    <td colspan="1">
	                            	    <s:property value="vo.secretClass"/>
	                            	    </td>
	                            	    <td colspan="1" class="lableTd t_v ">份数：</td>
	                            	    <td colspan="1">
	                            	    <s:property value="vo.num"/>
	                            	    </td>
	                            	    <td colspan="1" class="lableTd t_v ">缓急：</td>
	                            	    <td colspan="1">
	                            	    <s:property value="vo.priorities"/>
	                            	    </td>    
	                            	     <td colspan="1" class="lableTd t_v ">文件字号：</td>
	                            	    <td colspan="5">
	                            	    <s:property value="vo.filezh"/>
	                            	    </td>
	                                </tr>
	                          	 
	                          	    
                                  	<tr class="content6">
	                                    <td colspan="1" class="lableTd t_v ">文件标题：</td>
	                                    <td colspan="11" colspan="3" class="content6">
	                                    <s:property value="vo.title"/>
	                                    </td>
                                  	</tr>
                              
   
	                          	    <tr class="content6">
	                                    <td colspan="1" class="lableTd t_v ">关键词：</td>
	                                    <td colspan="11" class="content6">
	                                   	<s:property value="vo.keyword"/>
	                                   	</td>
                                  	</tr>
                                  	
                                  	
                                  	<tr class="content7">
	                          	     	<td colspan="1" class="lableTd t_v ">文件内容：</td>
	                                    <td colspan="11">
	                                    	<input type="hidden" name="attach" id='attach' value="<s:property value="vo.attach"/>"/>
										  	<%-- --%>
										  	<iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="600" id="attachFrame" name="attachFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=<s:property value="vo.attach"/>&userName=<s:property value="#session.login_name"/>&loginName=<s:property value="#session.login_name"/>&procType=view&targetType=frame&type=1"></iframe>
										</td>
	                          	    </tr>
	                          	    
									<tr class="content7">
										<td colspan="1" class="lableTd t_v ">备注：</td>
										<td colspan="11">
										<pre style="width: 100%; white-space: pre-wrap !important; word-wrap: break-word;"><s:property escape="0" value="vo.remark"/></pre>
										</td>	
	                            	</tr>
	                                
	                              <tr class="content7">
										<td class="lableTd t_v ">拟办意见：</td>
										<td colspan="11">
										<s:action name="approve" namespace="/discipline-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'部门领导'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">领导审阅意见：</td>
										<td colspan="11">
										<s:action name="approve" namespace="/discipline-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'集团领导（并）','集团领导（串）'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">委员审阅意见：</td>
										<td colspan="11">
										<s:action name="approve" namespace="/discipline-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'纪委委员（并）','纪委委员（串）'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">部门意见：</td>
										<td colspan="11">
										<s:action name="dept" namespace="/discipline-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'部门接受人工作分发',
											'部门业务人员处理',
											'部门领导审核'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">经办意见：</td>
										<td colspan="11">
										<s:action name="approve" namespace="/discipline-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'汇总人','汇总人办结'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">办结意见：</td>
										<td colspan="11">
										<s:action name="approve" namespace="/discipline-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'办结'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									</tbody>
                          	  	</table>
                        	</div>
	                        <%-- --%>
	                        
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
</body>
</html>