<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="x-ua-compatible" content="IE=8">
	<title>
		<s:if test="params.processParam['steplabel']=='Begin'">新增</s:if>
		<s:else><s:property value="params.processParam['steplabel']"/></s:else>
	</title>
	<link href="<%=path %>/contact/css/formalize.css" rel="stylesheet">
	<link href="<%=path %>/contact/css/page.css" rel="stylesheet">
	<link href="<%=path %>/contact/css/imgs.css" rel="stylesheet">
	<link href="<%=path %>/contact/css/reset.css" rel="stylesheet">
	
	<link type="text/css" href="<%=path %>/contact/js/datepicker/css/flick/jquery-ui-1.8.18.custom.css" rel="stylesheet"/>
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
	<!--  -->
	
	<script type="text/javascript" src="<%=path %>/contact/js/datepicker/js/jquery-ui-1.8.18.custom.min.js"></script>
	<script type="text/javascript" src="<%=path %>/contact/js/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
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
	});
	function goExit(){
		if(confirm("确认关闭？")){
			self.close();
		}
	}
	</script>
	<%-- 测试所需JS
	<script type="text/javascript" src="<%=path %>/deptSubProcess/js/test.js"></script>
	--%>
	<%-- 所需JS--%>
	<s:include value="zone/js.jsp">
	</s:include>
	
	<style>
	.deptTreeZone{display:none;}
	.redMark{color:red;display:inline;}
	</style>
	
</head>
<body class="Flow">
<form action="<%=path %>/contact-deptContact/update.action" name="formUpdate" id="formUpdate" method="post">
	<!-- 待办项时增加此变量 对应  DeptContactOperateVo。taskId = todoid-->
	<input type="hidden" name="taskId" value=""<s:property value="#parameters.taskId"/>>
	<div class=" transparent" id="maskDiv" style="display:none;" style="filter:alpha(opacity=30);opacity:0.3;"></div>

	
	<%-- 操作页面--%>
	<s:include value="zone/handle.jsp">
		<s:param name="flowType" value="flowType"></s:param>
	</s:include>
	
	<div class="f_bg">
     	<jsp:include page="zone/todo.jsp" flush="true"/>

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
                            <s:if test="params.processParam['steplabel']!='Begin'">
	                            <div class="mb10 Step">编号：
	                            	<s:property value="params.param['mainBo.serial']"/>
	                            </div>
                            </s:if>
							<div class="mb10">
	                            <input type="hidden" id="id" name="id" value="<s:property value="mainVo.id"/>"/>
								<input type="hidden" name="pname" value="<s:property value="params.processParam['pname']"/>"/>
								<input type="hidden" name="pincident" value="<s:property value="params.processParam['pincident']"/>"/>
								<input type="hidden" name="cname" value="<s:property value="params.processParam['cname']"/>"/>
								<input type="hidden" name="cincident" value="<s:property value="params.processParam['cincident']"/>"/>
								<input type="hidden" name="steplabel" value="<s:property value="params.processParam['steplabel']"/>"/>
								<input type="hidden" name="taskuser"  id="taskuser" value="<s:property value="params.processParam['taskuser']"/>"/>
								<input type="hidden" name="flowType" value="<s:property value="flowType"/>"/>
                              	<input type="hidden" name="checkOnly" value=""/>
                              	<input type="hidden" id="modify" name="modify" value=""/>
								<input type="hidden" id="mainUnitId" name="mainUnitId" value="<s:property value="mainVo.mainUnitId"/>"/>
								<input type="hidden" id="copyUnitId" name="copyUnitId" value="<s:property value="mainVo.copyUnitId"/>"/>

								<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									<thead>
									<th colspan="4" class="lableTd6"><h5>基本信息</h5>
										<span class="fr pt5 mr5"><a href="#" class="" name="zone_expand" style="cursor:hand;">展开</a> </span>
									</th>
									</thead>
								  
								  	<tbody>
								  	<tr class="content6">
										<td class="lableTd t_r">
										<s:if test="flowType!=2"><font class="redMark">*&nbsp;</font></s:if>主送部门</td>
										<td class="pl18" colspan=3>
											<input type="text" id="mainUnit" name="mainUnit" readonly style="width:80%;" value="<s:property value="mainVo.mainUnit"/>">
											&nbsp;
											<input type="button" id="selectMainUnit" value="..."/>&nbsp;<input id="clearmain" type="button" value="清除"/>
											<font id="mainUnitTreeZone" class="deptTreeZone" root="<s:property value="params.treeBo.mainUnitId"/>" checkNode="mainUnitId" nodeId="mainUnitId" nodeName="mainUnit"/>
										</td>
								  	</tr>
								  
								  	<tr class="content6">
										<td class="lableTd t_r">抄送部门</td>
										<td class="pl18" colspan=3>
										<input type="text" id="copyUnit" name="copyUnit" readonly style="width:80%;" value="<s:property value="mainVo.copyUnit"/>">
										&nbsp;
										<input type="button" id="selectCopyUnit" value="..."/>&nbsp;<input id="clearcopy" type="button" value="清除"/>
										<font id="copyUnitTreeZone" class="deptTreeZone" root="<s:property value="params.treeBo.mainUnitId"/>" checkNode="copyUnitId" nodeId="copyUnitId" nodeName="copyUnit"/>
										</td>
								  	</tr>
								  
                                  	<tr class="content6">
	                                    <td class="lableTd t_r"><font class="redMark">*&nbsp;</font>主题</td>
	                                    <td colspan="3" class="content6">
	                                    <input name="theme" type="text" class="input_large" id="theme" style="width:100%;" maxlength="200" value="<s:property value="mainVo.theme"/>"/></td>
                                  	</tr>
                              
									<tr class="content7">
	                                    <td class="lableTd t_r"><font class="redMark">*&nbsp;</font>联系时间</td>
	                            	    <td><input type="text" class="input_large date" name="contactDate" id="contactDate" readonly="readonly" maxlength="20" value="<s:property value="mainVo.contactDate"/>"/></td>
	                                    <td class="lableTd t_r"><font class="redMark">*&nbsp;</font>要求回复时间</td>
	                            	    <td><input type="text" class="input_large date" name="replyDate" id="replyDate" readonly="readonly" maxlength="20" value="<s:property value="mainVo.replyDate"/>"/></td>
	                          	    </tr>
								
									<tr class="content7">
										<td class="lableTd t_r"><font class="redMark">*&nbsp;</font>联系内容</td>
										<td colspan="3">
										<!-- onKeyPress='value=value.substr(0,1000);' -->
										<textarea id="content" name="content" rows="7" ><s:property value="mainVo.content"/></textarea></td>
	                            	</tr>
									
	                          	    <tr class="content7">
	                          	     	<td class="lableTd t_r">附件内容</td>
	                                    <td colspan="3">
	                                    	<input type="hidden" name="contentAttachmentId" id='contentAttachmentId' value="<s:property value="mainVo.contentAttachmentId"/>"/>
										  	<%-- --%>
										  	<iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="600" id="contentAttachmentFrame" name="contentAttachmentFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=contentAttachmentId&fileGroupName=projectAttachIdGroup&fileGroupId=<s:property value="mainVo.contentAttachmentId"/>&userName=<s:property value="params.userInfo.userName"/>&loginName=<s:property value="params.userInfo.login_Name"/>&procType=edit&targetType=frame&type=1"></iframe>
										</td>
	                          	    </tr>
	                          	    
	                          	    <s:if test="params.processParam['steplabel']=='Begin'">
	                          	    <tr class="content7">
										<td class="lableTd t_r" ><font class="redMark">*&nbsp;</font>签发领导</td>
										<td colspan=3>
											<select name="SIGN_LEADER" id="SIGN_LEADER" class="input_large">
												<option value="">---请选择---</option>
											</select>
										</td>
	                            	</tr>
                          	    	</s:if>
                          	    
									<tr class="content7" id="ref_zone">
	                          	     	<td class="lableTd t_r">相关资料</td>
	                                    <td colspan="3">
	                                    <div style="display:block;white-space:nowrap;width:82%;float:left;">
											<div id="ref_div_zone" width="100%" style="display:block;">&nbsp;</div>
										</div>
										&nbsp;
	                                    <input id="choice_ref" type="button" value="..." onclick="selectRef('ref_zone')"/>&nbsp;
	                                    <input id="clean_ref" type="button" value="清空" onclick="clearRef('ref_zone')"/>
	                                    <input type="hidden" id="ref_id_zone" name="ref_id_zone" value="<s:property value="params.param['ref_id_zone'].toString()"/>"/>
	                                    <input type="hidden" id="ref_type_zone" name="ref_type_zone" value="update"/>
	                                    </td>
	                          	    </tr>
                          	    
	                          	    <s:if test="params.processParam['steplabel']=='Begin'">
										<tr class="content7">
		                                    <td class="lableTd t_r">发起人</td>
		                            	    <td><s:property value="#session.user_name"/></td>
		                                    <td class="lableTd t_r">发起部门</td>
		                            	    <td><s:property value="#session.dept_name"/></td>
		                          	    </tr>
									</s:if>
									<s:else>
										<tr class="content7">
		                                   	<td class="lableTd t_r">发起人</td>
											<td><s:property value="params.param['mainBo.initiatorName']"/></td>
											<td class="lableTd t_r">发起部门</td>
											<td><s:property value="params.param['mainBo.createDeptname']"/></td>
		                          	    </tr>
									</s:else>
									</tbody>
                          	  	</table>
                        	</div>
	                        <%-- --%>
	                        <s:if test="params.processParam['steplabel']!='Begin'">
	                        <s:action name="deptContactApproveInfo" namespace="/contact-approvedInfo" executeResult="true">
								<s:param name="mainBoId" value="mainVo.id"></s:param>
							</s:action>
							</s:if>
							<div class="mb10 t_c">
								<input type="button" id="formSubmit" value="保存修改"/>&nbsp;
								<input type="button" id="closeBtn" value="关闭"/>&nbsp;
								<div id="formUpdate_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
							      <p style="width:auto;display:inline;"><img src="<%=path %>/contact/images/loading.gif" style="display:inline;"/>
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