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
	<title>收文表单</title>
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
	<script src="<%=path %>/receive/js/contextpath.js"></script>
	<script src="<%=path %>/receive/js/html5.js"></script>   
	<!--<script src="<%=path %>/receive/js/jquery.formalize.js"></script>  -->
	<script src="<%=path %>/receive/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path %>/receive/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path %>/receive/js/attach.js"></script>
	<script src="<%=path %>/receive/js/jquery-form/jquery.form.min.js"></script>
	<script src="<%=path %>/receive/js/jquery-ui/jquery-ui.js"></script>
	<script src="<%=path %>/receive/js/jquery-ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="<%=path %>/receive/js/common/elementControl.js"></script>
	<script src="<%=path %>/receive/js/jquery-qtip/jquery.qtip-1.0.0-rc3.js"></script>
	
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
			//height: 680,
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
<%if(SimulateSubStepConstants.STEPNAME_SIMULATE.equals(steplabel) || 
	SimulateSubStepConstants.STEPNAME_SIMULATE_SUGGEST_COLLECT.equals(steplabel) ||
	SimulateSubStepConstants.STEPNAME_SIMULATE_LEADER.equals(steplabel) ){%>
	<s:include value="%{#request.getContextPath()}/receive/process/simulate/officer.jsp">
		<s:param name="steplabel" value="params.processParam['steplabel']"></s:param>
		<s:param name="pname" value="params.processParam['pname']"></s:param>
		<s:param name="pincident" value="params.processParam['pincident']"></s:param>
	</s:include>
<%}else if(SimulateSubStepConstants.STEPNAME_SIMULATE_SUGGEST.equals(steplabel) ||
		RecvMainStepConstants.STEPNAME_RECORD.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/receive/process/recv/record.jsp"></s:include>
<%}else if(InstructSubStepConstants.STEPNAME_SECRETARY_DEAL.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/receive/process/instruct/secretary.jsp"></s:include>
<%}else if(InstructSubStepConstants.STEPNAME_LEADER_DEAL.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/receive/process/instruct/leader.jsp">
		<s:param name="cname" value="params.processParam['cname']"></s:param>
		<s:param name="cincident" value="params.processParam['cincident']"></s:param>
		<s:param name="steplabel" value="params.processParam['steplabel']"></s:param>
	</s:include>
<%}else if(SignSubStepConstants.STEPNAME_SECRETARY_DEAL.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/receive/process/sign/secretary.jsp"></s:include>
<%}else if(SignSubStepConstants.STEPNAME_LEADER_DEAL.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/receive/process/sign/leader.jsp">
		<s:param name="cname" value="params.processParam['cname']"></s:param>
		<s:param name="cincident" value="params.processParam['cincident']"></s:param>
		<s:param name="steplabel" value="params.processParam['steplabel']"></s:param>
	</s:include>
<%}else if(DeptSubStepConstants.STEPNAME_DISPATCHER.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/receive/process/dept/receiver.jsp"></s:include>
<%}else if(DeptSubStepConstants.STEPNAME_DEAL.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/receive/process/dept/officer.jsp"></s:include>
<%}else if(DeptSubStepConstants.STEPNAME_LEADER.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/receive/process/dept/leader.jsp"></s:include>
<%}else if(FinishSubStepConstants.STEPNAME_FINISH.equals(steplabel)){%>
	<s:include value="%{#request.getContextPath()}/receive/process/finish/officer.jsp">
		<s:param name="pname" value="params.processParam['pname']"></s:param>
		<s:param name="pincident" value="params.processParam['pincident']"></s:param>
	</s:include>
<%} %>
<!--  -->
	<div class="f_bg">
     <s:include value="%{#request.getContextPath()}/receive/process/common/todo.jsp"></s:include>
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;"><s:property value="params.userInfo.test"/>上海申通地铁集团有限公司</b><br><b>收文处理单</b></h1>
                        	<s:if test='vo.flag=="0" && vo.priorities=="急件"'>
                        	<div class="mb10" style="color:red;">本件为急件，请各部门在3日内完成。</div>
                        	</s:if>
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
										<s:action name="simulateRounds" namespace="/receive-approve" executeResult="true">
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
									
									<tr class="content7">
										<td class="lableTd t_v ">领导批示：</td>
										<td colspan="11">
										<s:action name="leaderRounds" namespace="/receive-approve" executeResult="true">
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
									
									<tr class="content7">
										<td class="lableTd t_v ">部门意见：</td>
										<td colspan="11">
										<s:action name="deptRounds" namespace="/receive-approve" executeResult="true">
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
										<td class="lableTd t_v ">办结人意见：</td>
										<td colspan="11">
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
									
									<tr class="content7">
										<td class="lableTd t_v ">备案情况：</td>
										<td colspan="11">
										<s:action name="record" namespace="/receive-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'备案'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">办理情况：</td>
										<td colspan="11">
										<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
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
											<s:action name="dealResult" namespace="/oa" executeResult="true">
											<s:param name="pname" value="params.processParam['pname']"></s:param>
											<s:param name="pincident" value="params.processParam['pincident']"></s:param>
											<s:param name="type">DocRe_notice</s:param>
											</s:action>
											</td>
											</tr>
										</table>
										</td>
									</tr>
									
									</tbody>
                          	  	</table>
                        	</div>
	                        <%-- --%>
	                        
							<div class="mb10 t_c">
								<input type="button" id="closeBtn" value="关闭"/>&nbsp;
								<div id="formUpdate_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
							      <p style="width:auto;display:inline;"><img src="<%=path%>/receive/css/default/images/loading.gif" style="display:inline;"/>
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