<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@ page import="com.wonders.receive.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.receive.workflow.process.recv.constants.RecvMainStepConstants" %>
<%@ page import="com.wonders.receive.workflow.process.simulate.constants.SimulateSubStepConstants" %>
<%@ page import="com.wonders.receive.workflow.process.finish.constants.FinishSubStepConstants" %>
<%@ page import="com.wonders.receive.workflow.process.instruct.constants.InstructSubStepConstants" %>
<%@ page import="com.wonders.receive.workflow.process.sign.constants.SignSubStepConstants" %>
<%@ page import="com.wonders.receive.workflow.process.dept.constants.DeptSubStepConstants" %>



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
	<title>打印</title>
	<link href="<%=path%>/receive/css/formalize.css" rel="stylesheet">
	<link href="<%=path%>/receive/css/page.css" rel="stylesheet">
	<link href="<%=path%>/receive/css/default/imgs.css" rel="stylesheet">
	<link href="<%=path%>/receive/css/reset.css" rel="stylesheet">
	<link href="<%=path%>/receive/css/jquery.ui.css" rel="stylesheet">
	<link type="text/css" href="<%=path%>/receive/css/flick/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
	<!--[if IE 6.0]>
	<script src="js/iepng.js" type="text/javascript"></script>
	<script type="text/javascript">
	     EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
	</script>
	<![endif]-->
	<style>
	.redMark{color:red;display:inline;}
	</style>
	<style>
		@media print{
		.print{display:block;}
		.nprint{display:none;}
		}
	</style>
	<script src="<%=path %>/receive/js/contextpath.js"></script>
	<script src="<%=path %>/receive/js/html5.js"></script>   
	<!--<script src="<%=path %>/receive/js/jquery.formalize.js"></script>  -->
	<script src="<%=path %>/receive/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path %>/receive/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path %>/receive/js/jquery-print/jquery.jqprint-0.3.js"></script>
	
	<script type="text/javascript">
	$(function(){
		$("#printBtn").click(function(){
			$("#main").jqprint();  
			//window.print(); 
		});
		
		$("#closeBtn").click(function(){
			window.opener=null;
			window.open("","_self");
			window.close();
		});
		
		$(".noleader").show();
		$(".showDetail").parent("td").remove();
		
	})	;
	
	
	
	</script>
	
</head>
<body id="printBody" class="Flow">

<!--startprint-->
	<div class="f_bg" >
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2" id="main">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;">上海申通地铁集团有限公司</b><br><b>呈批文件单</b></h1>

                        	<div class="mb10" >
	                          <table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									
								  	<tbody>
									
									<tr class="content7">
	                                    <td class="b0"  colspan="1">呈报部门：</td>
	                            	    <td class="b0" colspan="3">
	                            	    <s:property value="vo.submitDept"/>     
	                            	   	</td>
	                            	    <td class="b0" colspan="1">呈报日期：</td>
	                            	    <td class="b0" colspan="3" >
	                            	   <s:property value="vo.submitDate"/>     
	                            	    </td>
	                            	    <td class="b0" colspan="1">部门编号：</td>
	                            	    <td class="b0" colspan="3" id="zleaderTd">
										<s:property value="vo.deptId"/>  
	                            	    </td>
	                          	    </tr>
								 
                                  	<tr class="content6">
	                                    <td style="width:15%;" class="lableTd t_v">文件标题：</td>
	                                    <td colspan="11" class="content6">
	                                    <s:property value="vo.title"  escape="0"/>     
	                                    </td>
                                  	</tr>
                              
									
								
	                          	    
	                          	    <tr class="content6">
	                                    <td class="lableTd t_v">关键词：</td>
	                                    <td colspan="7" class="content6">
	                                    <s:property value="vo.keyword"/>     
	                                    </td>
	                                   	<td colspan="1" class="lableTd t_v">缓急：</td>
	                            	    <td colspan="3">
	                            	   <s:property value="vo.docLevel"/>     
	                            	    </td>
                                  	</tr>
	                          	    
	                          	    <tr class="content7">
										<td class="lableTd t_v">主要内容：</td>
										<td colspan="11">
										<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
										<tr>
										<td style="padding-left:0px;">
											<pre style="width: 100%; white-space: pre-wrap !important; word-wrap: break-word;"><s:property escape="0" value="vo.content"/></pre>
										</td>
										</tr>
										<tr>
										<td class="t_r">
											经办人：<s:property value="vo.operator"/> &nbsp;    
											部门负责人：
											<s:property value="vo.deptMaster"/>     
										</td>
										</tr>
										</table>
										</td>
	                            	</tr>
	                          	  
	                          	  <tr class="content7"  height="150">
										<td colspan="12">
										<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
										<tr>
										<td>
										拟办意见：
										</td>
										</tr>
										<tr>
										<td>
										<s:action name="simulate" namespace="/receive-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'拟办人', 
											'拟办意见汇总',
											'拟办领导批示',
											'拟办建议',
											'拟办办结'}">
										</s:param>
										</s:action>
										</td>
										</tr>
										</table>
										</td>
									</tr>
									
									<tr class="content7"  height="250">
										<td colspan="12">
										<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
										<tr>
										<td>
										会稿部门意见：
										</td>
										</tr>
										<tr>
										<td>
										<s:action name="dept" namespace="/receive-approve" executeResult="true">
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
										</table>
										</td>
									</tr>
									
									<tr class="content7"  height="250">
										<td colspan="12">
										<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
										<tr>
										<td>
										公司分管领导批示：
										</td>
										</tr>
										<tr>
										<td>
										<s:action name="leader" namespace="/receive-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'领导', 
											'批示领导'
											}">
										</s:param>
										</s:action>
										</td>
										</tr>
										</table>
										</td>
									</tr>
									
									
									
									<tr class="content7"  height="150">
										
										<td colspan="12">
										<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
											<tr>
											<td>
											办理情况：
											</td>
											</tr>
											
											<tr>
											<td>
											<s:action name="secretary" namespace="/receive-approve" executeResult="true">
											<s:param name="pname" value="params.processParam['pname']"></s:param>
											<s:param name="pincident" value="params.processParam['pincident']"></s:param>
											<s:param name="stepname" 
												value="new java.lang.String[]{
												'秘书',
												'批示秘书'
												}">
											</s:param>
											</s:action>
											</td>
											</tr>
											<tr>
											<td>
											<s:action name="finish" namespace="/receive-approve" executeResult="true">
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
										</table>
										</td>
									
									</tbody>
                          	  	</table>
                        	</div>
                        	
                        	<div>
								注：<br>1.纸质影印件仅供参考，实际内容以综合业务协同平台网上为准。<br>2.纸质归档件需加盖本单位电子文件打印专用章。
							</div>
							
	                      <!--endprint-->
	                       
							
							
						
						</div>
                    </div>
                </div>
            </div>
             <div class="mb10 t_c">
				<input type="button" id="printBtn" value="打印"/>&nbsp;
				<input type="button" id="closeBtn" value="关闭"/>&nbsp;				
			</div>	
        </div>
       
 	</div>
</div>	
		 
</body>
</html>