<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@ page import="com.wonders.contract.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.contract.workflow.process.review.constants.ReviewMainStepConstants" %>
<%@ page import="com.wonders.contract.workflow.process.dept.constants.DeptSubStepConstants" %>



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
	<link href="<%=path%>/contract/css/formalize.css" rel="stylesheet">
	<link href="<%=path%>/contract/css/page.css" rel="stylesheet">
	<link href="<%=path%>/contract/css/default/imgs.css" rel="stylesheet">
	<link href="<%=path%>/contract/css/reset.css" rel="stylesheet">
	<link href="<%=path%>/contract/css/jquery.ui.css" rel="stylesheet">
	<link type="text/css" href="<%=path%>/contract/css/flick/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
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
	<script src="<%=path %>/contract/js/contextpath.js"></script>
	<script src="<%=path %>/contract/js/html5.js"></script>   
	<!--<script src="<%=path %>/contract/js/jquery.formalize.js"></script>  -->
	<script src="<%=path %>/contract/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path %>/contract/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path %>/contract/js/jquery-print/jquery.jqprint-0.3.js"></script>
	
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
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;">上海申通地铁集团有限公司</b><br><b>合同备案单</b></h1>

                        	<div class="mb10" >
	                          <table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									
								  	<tbody>
	                          	    
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">项目名称：</td>
	                                    <td  colspan="3" class="content6">
	                                    <s:property value="vo.projectName"/>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">项目编号：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.projectIdentifier"/>
	                                    </td>
	                                    <td class="lableTd t_v">项目批文号：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.projectNum"/>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">项目公司：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.projectCompany"/>
	                                    </td>
	                                    <td class="lableTd t_v">项目公司负责人：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.projectCharge"/>
	                                    </td>
                                  	</tr>
                              
                              	<tr class="content6">
	                                    <td class="lableTd t_v">合同编号：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.contractIdentifier"/>
	                                    </td>
	                                    <td class="lableTd t_v">自有编号：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.contractSelfNum"/>
	                                    </td>
                                  	</tr>
                              	<tr class="content6">
	                                    <td class="lableTd t_v">合同名称：</td>
	                                    <td colspan="3" class="content6">
	                                    <s:property value="vo.contractName"/>
	                                    </td>
	                                </tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">合同金额(万元)：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.contractMoney"/>
	                                    </td>
	                                    <td class="lableTd t_v">合同金额分类：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.contractMoneyType"/>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">合同预算(万元)：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.contractBudget"/>
	                                    </td>
	                                    <td class="lableTd t_v">采购方式：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.purchaseType"/>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">合同分类：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.contractType"/>
	                                    </td>
	                                    <td class="lableTd t_v">合同分组：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.contractGroup"/>
	                                    </td>
                                  	</tr>
                                  	
                                  <tr class="content6">
	                                    <td class="lableTd t_v">对方公司：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.oppositeCompany"/>
	                                    </td>
	                                    <td class="lableTd t_v">经办人：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.dealPerson"/>
	                                    </td>
                                  	</tr>
                                  	
                                  		<tr class="content6">
	                                    <td class="lableTd t_v">登记人：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.regPerson"/>
	                                    </td>
	                                    <td class="lableTd t_v">审批通过时间：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.passTime"/>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">签约时间：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.signTime"/>
	                                    </td>
	                                     <td class="lableTd t_v">负责单位/部门：</td>
	                                    <td class="content6">
	                                    <s:property value="vo.projectChargeDept"/>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                   <td class="lableTd t_v">履行期限：</td>
	                                    <td colspan="3" class="content6">
	                                    <s:property value="vo.execPeriodStart"/> 至  <s:property value="vo.execPeriodEnd"/>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content7">
										<td class="lableTd t_v">经办部门意见：</td>
										<td colspan="3">
										<!-- onKeyPress='value=value.substr(0,1000);' -->
										<pre style="width: 100%; white-space: pre-wrap !important; word-wrap: break-word;"><s:property escape="0" value="vo.dealDeptSuggest"/></pre>
										</td>
	                            	</tr>
	                            	
                                    <tr class="content7">
										<td class="lableTd t_v">备注：</td>
										<td colspan="3">
										<pre style="width: 100%; white-space: pre-wrap !important; word-wrap: break-word;"><s:property escape="0" value="vo.remark"/></pre>
										</td>
									</tr>
	                            	
                                  	<tr class="content7">
	                          	     	<td class="lableTd t_v">相关附件：</td>
	                                    <td colspan="3">
	                                    	<input value="<s:property value="vo.attach"/>" type="hidden" name="attach" id="attach"/>
										  	<%-- --%>
										  	<iframe style="display:inline;" scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="600" id="attachFrame" name="attachFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=<s:property value="vo.attach"/>&userName=&loginName=&procType=view&targetType=frame&type=1&listType=HT"></iframe>
										</td>
	                          	    </tr>
									
	                          	  <tr class="content7">
										<td class="lableTd t_v ">申报部门意见：</td>
										<td colspan="3">
										<s:action name="approve" namespace="/contract-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'返回修改', 
											'申报部门领导'}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">合约部初审意见：</td>
										<td colspan="3">
										<s:action name="approve" namespace="/contract-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'合约部初审',
											'合约部经办人'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">合约部拟办意见：</td>
										<td colspan="3">
										<s:action name="approve" namespace="/contract-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'合约部拟办'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">会签部门意见：</td>
										<td colspan="3">
										<s:action name="dept" namespace="/contract-approve" executeResult="true">
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
										<td class="lableTd t_v ">集团领导意见：</td>
										<td colspan="3">
										<s:action name="approve" namespace="/contract-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'集团领导'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									<tr class="content7">
										<td class="lableTd t_v ">合约部办结意见：</td>
										<td colspan="3">
										<s:action name="approve" namespace="/contract-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'合约部办结'
											}">
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