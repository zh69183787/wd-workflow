<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@ page import="com.wonders.dept.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.dept.workflow.process.pass.constants.PassMainStepConstants" %>



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
	<title>部门内部传阅单</title>
	<link href="<%=path%>/dept/css/formalize.css" rel="stylesheet">
	<link href="<%=path%>/dept/css/page.css" rel="stylesheet">
	<link href="<%=path%>/dept/css/default/imgs.css" rel="stylesheet">
	<link href="<%=path%>/dept/css/reset.css" rel="stylesheet">
	<link href="<%=path%>/dept/css/jquery.ui.css" rel="stylesheet">
	<link type="text/css" href="<%=path%>/dept/css/flick/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
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
	<script src="<%=path %>/dept/js/contextpath.js"></script>
	<script src="<%=path %>/dept/js/html5.js"></script>   
	<!--<script src="<%=path %>/dept/js/jquery.formalize.js"></script>  -->
	<script src="<%=path %>/dept/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path %>/dept/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path %>/dept/js/jquery-print/jquery.jqprint-0.3.js"></script>
	
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
		$(".showDetail").hide();
		
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
                    	<div class="Top" style="min-height:400px;">
                        	<h1 class="t_c">
                        	<b style="display:inline;">上海申通地铁集团有限公司</b><br>
                        	<b style="display:inline;"><s:property value="vo.regDeptName"/></b><br>
                        	<b>内部传阅单</b></h1>
                        	<div class="mb10" >
	                          <table style="table-layout:fixed;" id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									
								  	<tbody>
	                          	    
                                  	<tr class="content6">
	                                    <td style="width:15%;" class="lableTd t_v">标题内容：</td>
	                                    <td>
	                                    <s:property value="vo.title"/>
	                                    </td>
	                                 </tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">备注：</td>
	                                    <td>
	                                   <pre style="width: 100%; white-space: pre-wrap !important; word-wrap: break-word;"><s:property escape="0" value="vo.remark"/></pre>
										
	                                    </td>
                                  	</tr>
                                  	                                	
	                            	
                                  	<tr class="content7">
	                          	     	<td class="lableTd t_v">相关附件：</td>
	                                    <td>
	                                    	<input value="<s:property value="vo.attach"/>" type="hidden" name="attach" id="attach"/>
										  	<%-- --%>
										  	<s:if test="vo.codeId == null">
											  	<iframe style="display:inline;" scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="600" id="attachFrame" name="attachFrame" 
											  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=<s:property value="vo.attach"/>&userName=&loginName=&procType=view&targetType=frame&type=1"></iframe>
												</s:if>
											<s:else>
												<iframe style="display:inline;" scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="600" id="attachFrame" name="attachFrame" 
											  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=<s:property value="vo.attach"/>&userName=&loginName=&procType=view&targetType=frame&listType=2&codeId=<s:property value='vo.codeId'/>"></iframe>
											</s:else>
										</td>
	                          	    </tr>
									
	                          	  <tr class="content7">
										<td class="lableTd t_v ">部门内部意见：</td>
										<td>
										<s:action name="approve" namespace="/dept-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'Begin', 
											'部门业务人员',
											'部门领导'}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
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