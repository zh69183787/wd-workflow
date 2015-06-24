<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.Calendar" %>
<%@page import="com.wonders.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date" %>
<%@ page import="com.wonders.project.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.project.workflow.process.discard.constants.DiscardMainStepConstants" %>
<%@page import="com.wonders.project.workflow.process.discard.constants.DiscardMainConstants" %>
<%@page import="com.wonders.project.workflow.process.dept.constants.DeptSubStepConstants" %>
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
	<title>项目销项表单</title>
	<link href="<%=path%>/project/css/formalize.css" rel="stylesheet">
	<link href="<%=path%>/project/css/page.css" rel="stylesheet">
	<link href="<%=path%>/project/css/default/imgs.css" rel="stylesheet">
	<link href="<%=path%>/project/css/reset.css" rel="stylesheet">
	<link href="<%=path%>/project/css/jquery.ui.css" rel="stylesheet">
	<link type="text/css" href="<%=path%>/project/css/flick/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
	<!--[if IE 6.0]>
	<script src="js/iepng.js" type="text/javascript"></script>
	<script type="text/javascript">
	     EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
	</script>
	<![endif]-->
	<style>
	.redMark{color:red;display:inline;}
	</style>
	<script src="<%=path%>/project/js/contextpath.js"></script>
	<script src="<%=path%>/project/js/html5.js"></script>   
	<!--<script src="<%=path%>/project/js/jquery.formalize.js"></script>  -->
	<script src="<%=path%>/project/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path%>/project/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path%>/project/js/attach.js"></script>
	<script src="<%=path%>/project/js/jquery-form/jquery.form.min.js"></script>
	<script src="<%=path%>/project/js/jquery-ui/jquery-ui.js"></script>
	<script src="<%=path%>/project/js/jquery-ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="<%=path%>/project/js/common/elementControl.js"></script>
	<script src="<%=path%>/project/js/jquery-qtip/jquery.qtip-1.0.0-rc3.js"></script>
	
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
		
		$('a[name=asset_view_a]').click(function(){
			$("#assetContent").text($(this).prev().val());
			$("#asset_content_zone").dialog("open");
		})
		
		$("#asset_content_zone").dialog({
			modal: true,
			autoOpen: false,
			width: 400,
			height: 240,
			zIndex: 9999,	
			close: function(event, ui) {}
		});
		
		$('#assetContentSubmit').click(function(){
			$("#asset_content_zone").dialog("close");
		}).val('关闭');
		
		$('#assetContent').attr("readonly","readonly");
	})	;
	
		
	
	</script>
	
</head>

<body class="Flow">
	<div class="f_bg">
		<s:include value="%{#request.getContextPath()}/project/process/common/todo.jsp"></s:include>
		<s:include value="%{#request.getContextPath()}/project/process/discard/assetModify.jsp"></s:include>
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;">上海申通地铁集团有限公司</b><br><b>项目销项单</b></h1>
                        	
                        	
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
	                          	    
                                  	<tr class="content6">
	                                    <td colspan="1" class="lableTd t_v ">项目名称：</td>
	                                    <td colspan="3" class="content6">
	                                    <s:property value="vo.projectName"/>
	                                    </td>
                                  	</tr>
                                  	
									<tr class="content6">
	                                    <td colspan="1" class="lableTd t_v ">立项批文号：</td>
	                                    <td colspan="3" class="content6">
	                                    <s:property value="vo.dispatchNo"/>
	                                    </td>
                                  	</tr>

									<tr class="content6">
	                                    <td class="lableTd t_v " width="15%">实际开工日期：</td>
	                                    <td class="content6" width="35%">
	                                    <s:property value="vo.startDate"/>
	                                    </td>
	                                    <td class="lableTd t_v " width="15%">实际竣工日期：</td>
	                                    <td class="content6" width="35%">
	                                    <s:property value="vo.finishDate"/>
	                                    </td>	                                    
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v " width="15%">批复概算：</td>
	                                    <td class="content6" width="35%">
	                                    <s:property value="vo.approvalBudget"/>
	                                    </td>
	                                    <td class="lableTd t_v " width="15%">竣工结算价：</td>
	                                    <td class="content6" width="35%">
	                                    <s:property value="vo.finishPrice"/>
	                                    </td>	                                    
                                  	</tr>

                                  	<tr class="content6">
	                                    <td colspan="1" class="lableTd t_v ">出资主体：</td>
	                                    <td colspan="3" class="content6">
	                                    ${vo.moneySource}
	                                    </td>
                                  	</tr>
                                  	<tr class="content6">
	                                    <td colspan="1" class="lableTd t_v ">成本属性：</td>
	                                    <td colspan="3" class="content6">
	                                    ${vo.costType}
	                                    </td>
                                  	</tr>                                              	
                                  	<tr class="content6">
	                                    <td colspan="4" class="lableTd t_v ">待审核资产：</td>
                                  	</tr>                                  	
                                  	<tr>
                                  		<td colspan="4">
                                  			<div style="width:705px;overflow:scroll;">
											<table id="assetTable" width="1200px;">
											<thead>
					                        <tr>
					                            <td style="width: 10%;">资产编码</td>
					                            <td style="width: 15%;">资产名称</td>
					                            <td style="width: 10%;">资产标识</td>
					                            <td style="width: 10%;">资产项目属性</td>
					                            <td style="width: 5%;">原值</td>
					                            <!-- <td style="width: 10%;">成本属性</td> -->
					                            <td style="width: 5%;">当前净值</td>
					                            <td style="width: 10%;">入账原值/费用</td>
					                            <td style="width: 8%;">剩余使用年限</td>
					                            <td style="width: 8%;">是否需延长年限</td>
					                            <td style="width: 8%;">操作</td>
					                        </tr>											
											</thead>
											<tbody>
											<s:iterator value="vo.assets" var="asset">
											<tr>
												<td><s:property value="assetNo"/></td>
												<td><s:property value="assetName"/></td>
												<td><s:property value="operateProjectAsset"/></td>
												<td><s:property value="assetType"/></td>
												<td><s:property value="originalValue"/></td>
												<%-- <td><s:property value="costType"/></td> --%>
												<td><s:property value="netValue"/></td>
												<td><s:property value="maintainCost"/></td>
												<td><s:property value="lifeLeft"/></td>
												<td>
													<s:if test="lifeExtend!=null && lifeExtend!=''">
														<s:property value="lifeExtend"/>
													</s:if>
													<s:else>
														无需延长
													</s:else>
												</td>		
												<td><input type="hidden" value="<s:property value="content"/>"/>
													<s:if test="content!=null">
														<a name='asset_view_a' href='javascript:void(0)'>查看资产履历</a>
													</s:if>
												</td>										
											</tr>
											</s:iterator>
<%-- 											<tr>
												<td colspan="11">资本性合计：<s:property value="vo.assetCapitalSum"/>&nbsp;费用性合计：<s:property value="vo.assetCostSum"/></td>
											</tr> --%>											
											</tbody>
					                        </table>
					                        </div>
                                  		</td>
                                  	</tr>                                  	
									<tr class="content6">
	                                    <td class="lableTd t_v ">申请人：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.operatorName"/>
	                                    </td>
	                                    <td class="lableTd t_v ">申请人联系电话：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.operatorMobile"/>
	                                    </td>	                                    
                                  	</tr>                                         	                                  	                                  	
                                  	<tr class="content7">
	                          	     	<td colspan="1" class="lableTd t_v ">附件：</td>
	                                    <td colspan="3">
	                                    	<input type="hidden" name="attach" id='attach' value="<s:property value="vo.attach"/>"/>
										  	<%-- --%>
										  	<iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="100%" id="attachFrame" name="attachFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=<s:property value="vo.attach"/>&userName=<s:property value="#session.login_name"/>&loginName=<s:property value="#session.login_name"/>&procType=view&targetType=frame&type=1"></iframe>
										</td>
	                          	    </tr>
	                                
	                              <tr class="content7">
										<td class="lableTd t_v ">申报部门意见：</td>
										<td colspan="3">
										<s:action name="approve" namespace="/project-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'返回修改','发起部门领导'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">投资部初审意见：</td>
										<td colspan="3">
										<s:action name="approve" namespace="/project-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'投资部收发员','投资部经办人'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">会签部门意见：</td>
										<td colspan="3">
										<s:action name="dept" namespace="/project-approve" executeResult="true">
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
										<td class="lableTd t_v ">投资部办结意见：</td>
										<td colspan="3">
										<s:action name="approve" namespace="/project-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'投资部领导'
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