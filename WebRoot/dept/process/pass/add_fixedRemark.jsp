<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date" %>
<%@ page import="com.wonders.dept.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.dept.workflow.process.pass.constants.PassMainStepConstants" %>
<%@ page import="com.wonders.util.StringUtil" %>
<%@ page import="com.wonders.dept.workflow.constants.LoginConstants" %>

<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
Date today = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String dateTime = sdf.format(today);
String userName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERNAME));
String deptId = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTID));
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
	<script src="<%=path%>/dept/js/contextpath.js"></script>
	<script src="<%=path%>/dept/js/html5.js"></script>   
	<!--<script src="<%=path%>/dept/js/jquery.formalize.js"></script>  -->
	<script src="<%=path%>/dept/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path%>/dept/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path %>/dept/js/attach.js"></script>
	<script src="<%=path%>/dept/js/jquery-form/jquery.form.min.js"></script>
	<script src="<%=path%>/dept/js/jquery-ui/jquery-ui.js"></script>
	<script src="<%=path%>/dept/js/jquery-ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="<%=path%>/dept/js/common/elementControl.js"></script>
	<script src="<%=path%>/dept/js/jquery-qtip/jquery.qtip-1.0.0-rc3.js"></script>
	
	<script type="text/javascript">
	var options = {
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
						submitZoneControl(options.submitZone,false);	
						window.opener=null;
						window.open("","_self");
						window.close();
			    	}else{
			    		var errorLog = showError(obj.errors);
						alert("操作失败！"+errorLog);
						
						$("#"+options.submitZone+"_loading").hide();
						submitZoneControl(options.submitZone,false);
			    	}
					//console.log(options);
		    	}else{
						alert("操作失败！");
						
						$("#"+options.submitZone+"_loading").hide();
						submitZoneControl(options.submitZone,false);
				}		
				return false;
		    }
		};
	
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
		$('#td_remark').html($('#remark').val());
		
		$("#handle_zone").dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			//height: 350,
			zIndex: 9999,	
			open: function () {
				$("#handle_zone").parent().appendTo("#formUpdate");
				$("#handle_zone").parent().css("zIndex","9999");
			},
			close: function(event, ui) {}
		});
		
		$("#closeBtn").click(function(){
			window.opener=null;
			window.open("","_self");
			window.close();
		});
		
		$("#handleClose").on("click",function(){
			$("#handle_zone").dialog("close");
			return false;
		});
		
		
		$("#formDeal").click(function(){
			$("#handle_zone").dialog("open");
			return false;
		});
		
	})	;
	
	
	
	</script>
	
</head>

<body class="Flow">
<form action="" name="formUpdate" id="formUpdate" method="post">
<s:include value="%{#request.getContextPath()}/dept/process/pass/leader.jsp"></s:include>
	<div class="f_bg">
     
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top" style="min-height:400px;">
                        	<h1 class="t_c">
                        	<b style="display:inline;">上海申通地铁集团有限公司</b><br>
                        	<b style="display:inline;"><s:property value="params.userInfo.deptName"/></b><br>
                        	<b>内部传阅单</b></h1>
                        	
							<div class="mb10">
								<input type="hidden" name="steplabel" value="<%=PassMainStepConstants.STEPNAME_REGISTER%>"/>
								<input type="hidden" name="mainId" value="<s:property value='#parameters.mainId'/>"/>
								<input type="hidden" name="mainTable" value="<s:property value='#parameters.mainTable'/>"/>
								<input type="hidden" name="codeId" value="<s:property value='#parameters.codeId'/>"/>
								<input type="hidden" name="todoId" value="<s:property value='#parameters.todoId'/>"/>
								
								<input type="hidden" id="taskuser" name="taskuser" value="<s:property value="params.processParam['taskuser']"/>"/>
								<input type="hidden" id="remark" name="remark" value="<s:property escape="0" value='#parameters.remark'/>"/>
								<table style="table-layout:fixed;" id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									
								  	<tbody>
	                          	    
                                  	<tr class="content6">
	                                    <td style="width:15%;" class="lableTd t_v">标题内容：</td>
	                                    <td>
	                                    <input value="<s:property value='#parameters.title'/>" name="title" type="text" class="input_large" id="title" style="width:90%;"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
	                                 </tr>
                                  	
                                  
                                  	
                                    <tr class="content7">
										<td class="lableTd t_v">备注：</td>
										<td id="td_remark">
										</td>
	                            	</tr>
	                            	
                                  	<tr class="content7">
	                          	     	<td class="lableTd t_v">相关附件：</td>
	                                    <td>
	                                    	<input type="hidden" name="attach" id="attach" value="<s:property value='#parameters.attach'/>"/>
										  	<%-- --%>
										  	<s:if test="#parameters.mainid == null && #parameters.attach == null && #parameters.codeId == null">
										  	<iframe style="display:inline;" scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="590" id="attachFrame" name="attachFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=&userName=<s:property value="#session.user_name"/>&loginName=<s:property value="#session.login_name"/>&procType=edit&targetType=frame&type=1"></iframe>
											</s:if>
											<s:else>
											<iframe style="display:inline;" scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="590" id="attachFrame" name="attachFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=<s:property value='#parameters.attach'/>&userName=<s:property value="#session.user_name"/>&loginName=<s:property value="#session.login_name"/>&procType=edit&targetType=frame&type=1"></iframe>			
											</s:else>
										<!-- <font class="redMark">*&nbsp;</font> -->
										</td>
	                          	    </tr>
	                          	   
									</tbody>
                          	  	</table>
                        	</div>
	                        <%-- --%>
	                        
							<div class="mb10 t_c">
								<input type="button" id="formDeal" value="办理"/>&nbsp;		
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