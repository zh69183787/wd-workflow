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
	<title>合同后审表单</title>
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
	<script src="<%=path %>/contract/js/contextpath.js"></script>
	<script src="<%=path %>/contract/js/html5.js"></script>   
	<!--<script src="<%=path %>/contract/js/jquery.formalize.js"></script>  -->
	<script src="<%=path %>/contract/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path %>/contract/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path %>/contract/js/attach.js"></script>
	<script src="<%=path %>/contract/js/json2.js"></script>
	<script src="<%=path %>/contract/js/jquery-form/jquery.form.min.js"></script>
	<script src="<%=path%>/contract/js/jquery-ui/jquery-ui.js"></script>
	<script src="<%=path %>/contract/js/jquery-ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="<%=path %>/contract/js/common/elementControl.js"></script>
	<script src="<%=path %>/contract/js/jquery-qtip/jquery.qtip-1.0.0-rc3.js"></script>
	
	<script type="text/javascript">
	var options = {
			handleZone:'',
			submitZone:'',
			cache:false,
			type:'post',
			callback:null,
			url:'',
			dataType : 'json',
		    success:function(obj){
		    	if(obj){
			    	if(obj.checkFlag){
			    		alert("操作成功");
			    		
						$("#"+options.submitZone+"_loading").hide();
						handleZoneControl(options.handleZone,false);	
						if(obj.url!=""){
							$("#handle_zone").dialog("close");
							window.location.href = contextpath+obj.url+"&rand="+Math.random();
						}else{
							$("#handle_zone").dialog("close");
							window.opener=null;
							window.open("","_self");
							window.close();
						}
			    	}else{
			    		var errorLog = showError(obj.errors);
						alert("操作失败！"+errorLog);
						$("#"+options.submitZone+"_loading").hide();
						handleZoneControl(options.handleZone,false);
			    	}
					//console.log(options);
		    	}else{
						alert("操作失败！");
						
						$("#"+options.submitZone+"_loading").hide();
						handleZoneControl(options.handleZone,false);
				}
		    	return false;
		    }
		};
	
	function elementEnable(zoneId){
		elementControl(zoneId,true);
	}

	function elementDisable(zoneId){
		elementControl(zoneId,false);
	}

	
	function elementControl(zoneId,flag){
		var zone = $("#"+zoneId);
		
		$(zone).find("input[type=hidden],:text,:button,:radio,textarea,select").each(function(i,n){
			$(n).attr("disabled",!flag);
		});
		if(flag){
			$(zone).find("a").each(function(i,n){
				$(n).css("display","");
			});
		}else{
			$(zone).find("a").each(function(i,n){
				$(n).css("display","none");
			});
		}
	}

	function submitZoneControl(zoneId,flag){
		if(zoneId==null||zoneId=="") return false;
		var zone = $("#"+zoneId);
		//console.log(flag);
		zone.find("a,:button").attr("disabled",flag);
	}
	
	function handleZoneControl(zoneId,flag){
		if(zoneId==null||zoneId=="") return false;
		var zone = $("#"+zoneId);
		//console.log(flag);
		zone.find(":button").attr("disabled",flag);
	}

	
	$(function(){
		$("#handle_zone").dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			//height: 500,
			zIndex: 9999,	
			open: function () {
				$("#handle_zone").parent().appendTo("#formUpdate");
				$("#handle_zone").parent().css("zIndex","9999");
			},
			close: function(event, ui) {}
		});
		
		$("#todo_handle").on("click",function(){
			$("#handle_zone").dialog("open");
		});
		
		$("#handleClose").on("click",function(){
			$("#handle_zone").dialog("close");
			return false;
		});
		
		$("#closeBtn").click(function(){
			window.opener=null;
			window.open("","_self");
			window.close();
		});
		
		$(".showDetail").on("click",function(){
			$(this).parent("td").next("td").find(".noleader").toggle();
		});
	});
	
	
	
	</script>
	
</head>
<body class="Flow">

<form action="" name="formUpdate" id="formUpdate" method="post">
<!-- 判断区域 -->
<%if(ReviewMainStepConstants.STEPNAME_DEPT_LEADER.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/contract/process/review/regLeader.jsp"></s:include>
<%}else if(ReviewMainStepConstants.STEPNAME_PRE_TRIAL.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/contract/process/review/preTrial.jsp"></s:include>
<%}else if(ReviewMainStepConstants.STEPNAME_CONTRACT_DEAL.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/contract/process/review/deal.jsp">
	<s:param name="purchaseType" value="vo.purchaseType"></s:param>
	</s:include>
<%}else if(ReviewMainStepConstants.STEPNAME_CONTRACT_SIMULATE.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/contract/process/review/simulate.jsp">
	<s:param name="pname" value="params.processParam['pname']"></s:param>
	</s:include>
<%}else if(ReviewMainStepConstants.STEPNAME_COM_LEADER.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/contract/process/review/comLeader.jsp"></s:include>
<%}else if(ReviewMainStepConstants.STEPNAME_CONTRACT_FINISH.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/contract/process/review/finish.jsp"></s:include>
<%}else if(ReviewMainStepConstants.STEPNAME_MODIFY.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/contract/process/review/regModify.jsp"></s:include>

<%}else if(DeptSubStepConstants.STEPNAME_DISPATCHER.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/contract/process/dept/receiver.jsp"></s:include>
<%}else if(DeptSubStepConstants.STEPNAME_DEAL.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/contract/process/dept/officer.jsp"></s:include>
<%}else if(DeptSubStepConstants.STEPNAME_LEADER.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/contract/process/dept/leader.jsp"></s:include>
<%}%>
<!--  -->
	<div class="f_bg">
     <s:include value="%{#request.getContextPath()}/contract/process/common/todo.jsp"></s:include>
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;"><s:property value="params.userInfo.test"/>上海申通地铁集团有限公司</b><br><b>合同备案单</b></h1>
                        	
							<div class="mb10">
	                           <input type="hidden" id="id" name="id" value="<s:property value="vo.id"/>"/>
								<input type="hidden" name="pname" value="<s:property value="params.processParam['pname']"/>"/>
								<input type="hidden" name="pincident" value="<s:property value="params.processParam['pincident']"/>"/>
								<input type="hidden" name="cname" value="<s:property value="params.processParam['cname']"/>"/>
								<input type="hidden" name="cincident" value="<s:property value="params.processParam['cincident']"/>"/>
								<input type="hidden" name="steplabel" value="<s:property value="params.processParam['steplabel']"/>"/>
								<input type="hidden" name="taskid" value="<s:property value="params.processParam['taskid']"/>"/>
									
								<input type="hidden" id="taskuser" name="taskuser" value="<s:property value="params.processParam['taskuser']"/>"/>
													
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
										<td class="lableTd t_v ">KPI信息：</td>
										<td colspan="3">
										<s:action name="kpiControl" namespace="/contract-kpi" executeResult="true">
											<s:param name="kpiControl" value="vo.kpiControl"></s:param>
										</s:action>
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
	                        <%-- --%>
	                        
							<div class="mb10 t_c">
								<input type="button" id="closeBtn" value="关闭"/>&nbsp;
								<div id="formUpdate_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
							      <p style="width:auto;display:inline;"><img src="<%=path%>/contract/css/default/images/loading.gif" style="display:inline;"/>
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