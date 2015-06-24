<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date" %>
<%@ page import="com.wonders.receive.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.receive.workflow.process.redv.constants.RedvMainStepConstants" %>
<%@ page import="com.wonders.receive.workflow.process.redv.constants.RedvMainConstants" %>
<%@ page import="com.wonders.receive.workflow.constants.LoginConstants" %>
<%@ page import="com.wonders.util.StringUtil" %>

<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String username = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERNAME));
String dept = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTNAME));
String deptId = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTID));

Date today = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String dateTime = sdf.format(today);
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>呈批件登记</title>
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
	<script src="<%=path %>/receive/js/jquery-form/jquery.form.min.js"></script>
	<script src="<%=path %>/receive/js/jquery-ui/jquery-ui.js"></script>
	<script src="<%=path %>/receive/js/jquery-ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="<%=path %>/receive/js/common/elementControl.js"></script>
	<script src="<%=path %>/receive/js/jquery-qtip/jquery.qtip-1.0.0-rc3.js"></script>
	
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

	function loadDocLevel(){
		$.post(
				'<%=path%>/receive-redvUtil/getCodeList.action?random='+Math.random(),
				{
					"type": 	'<%=RedvMainConstants.REDV_DICTIONARY%>',
					"code": 	'<%=RedvMainConstants.REDV_DOC_LEVEL_CODE%>'
				},
				function(obj, textStatus, jqXHR){
					var temp = "";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							if(i==0){
								$("#docLevel").val(obj[i].NAME);
							}
							temp +="<option value='"+obj[i].NAME+"'>"+obj[i].NAME+"</option>";
						}
						
					}
					$("#docLevel").html(temp);
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	function loadDeptSerial(){
		$.post(
				'<%=path%>/receive-redvUtil/getCodeByName.action?random='+Math.random(),
				{
					"type": 	'<%=RedvMainConstants.REDV_DICTIONARY%>',
					"name": 	'<%=dept%>'
				},
				function(obj, textStatus, jqXHR){
					if(obj !=null && obj.length>0){
						$("#zleaderTd").html(obj);
						$("#zleader").val(obj);
					}
				},
				"text"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	function loadDeptLeader(){
		$.post(
				'<%=path%>/receive-redvUtil/getDeptLeader.action?random='+Math.random(),
				{
					"deptId": 	'<%=deptId%>'
				},
				function(obj, textStatus, jqXHR){
					var temp = "";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							if(i==0){
								$("#nbOpinion").val(obj[i].loginName);
							}
							temp +="<option value='"+obj[i].loginName+"'>"+obj[i].name+"</option>";
						}
						
					}
					$("#nbOpinion").html(temp);
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	$(function(){
		loadDocLevel();
		loadDeptSerial();
		loadDeptLeader();
		$('#submitDate').datepicker({
			//inline: true								
			"changeYear":true,
			"showButtonPanel":true,	
			"closeText":'清除',	
			"currentText":'submitDate'//仅作为“清除”按钮的判断条件						
		});
		
		
		//datepicker的“清除”功能
	    $(".ui-datepicker-close").live("click", function (){              
	      if($(this).parent("div").children("button:eq(0)").text()=="submitDate") $("#submitDate").val("");                      
	    });
		
		$("#closeBtn").click(function(){
			window.opener=null;
			window.open("","_self");
			window.close();
		});
		
		$("#formSubmit").click(function(){
			if(confirm("确认提交吗？")){
				$(this).attr("disabled",true);
				
				options.formId = "formUpdate";
				options.url = "<%=path %>/receive-redvMain/register.action";
				options.submitZone = "formUpdate";
				submitZoneControl(options.submitZone,true);
				$("#"+options.submitZone+"_loading").show();
				clearError();
				
				$("#"+options.submitZone).ajaxSubmit(options);
				//$(this).removeAttr("disabled");
				return false;
			}
		});
	})	;
	
	
	
	</script>
	
</head>

<body class="Flow">
<form action="" name="formUpdate" id="formUpdate" method="post">
	<div class="f_bg">
     
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;">上海申通地铁集团有限公司</b><br><b>呈批文件单</b></h1>
                        	
							<div class="mb10">
								<input type="hidden" name="chiefPerson" value="<%=UltimusConstants.REDV_DICTIONARY_CODE%>"/>
								<input type="hidden" name="steplabel" value="<%=RedvMainStepConstants.STEPNAME_REGISTER%>"/>
								
								<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									
								  	<tbody>
									
									<tr class="content7">
	                                    <td class="b0"  colspan="1">呈报部门：</td>
	                            	    <td class="b0" colspan="1">
	                            	    <%=dept %>
	                            	   	<input value="<%=dept %>" name="submitDept" type="hidden" id="submitDept"/>
	                            	   	<input name="zleader" type="hidden" id="zleader"/>
	                            	   	</td>
	                            	    <td class="b0" colspan="1">呈报日期：</td>
	                            	    <td class="b0" colspan="1" >
	                            	   <input type="text" class="input_medium date" name="submitDate" id="submitDate" readonly="readonly" value="<%=dateTime %>" />
	                            	    <font class="redMark">*&nbsp;</font>
	                            	    </td>
	                            	    <td class="b0" colspan="1">部门编号：</td>
	                            	    <td class="b0" colspan="1" id="zleaderTd">

	                            	    </td>
	                          	    </tr>
	                          	    <!--
									<tr class="content7" id="ref_zone">
	                          	     	<td class="lableTd t_v">引用流程：</td>
	                                    <td colspan="5">
	                                    <div style="display:block;white-space:nowrap;width:82%;float:left;">
											<div id="ref_div_zone" width="100%" style="display:block;">&nbsp;</div>
										</div>
										&nbsp;
	                                    <input id="choice_ref" type="button" value="..." />&nbsp;
	                                    <input id="clean_ref" type="button" value="清空" />
	                                    
	                                    </td>
	                          	    </tr>
	                          	    -->
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">文件标题：</td>
	                                    <td colspan="5" class="content6">
	                                    <input name="title" type="text" class="input_large" id="title" style="width:90%;"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>
                              
									
								
	                          	    
	                          	    <tr class="content6">
	                                    <td class="lableTd t_v">关键词：</td>
	                                    <td colspan="3" class="content6">
	                                    <input name="keyword" type="text" class="input_large" id="keyword" style="width:100%;" />
	                                   	</td>
	                                   	<td colspan="1" class="lableTd t_v">缓急：</td>
	                            	    <td colspan="1">
	                            	    <select id="docLevel" name="docLevel">
	                            	    	
	                            	    </select>
	                            	    </td>
                                  	</tr>
                                  	
                                  	<tr class="content7">
										<td class="lableTd t_v">主要内容：</td>
										<td colspan="5">
										<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
										<tr>
										<td style="padding-left:0px;">
											<textarea id="content" name="content" rows="10" ></textarea>
										</td>
										</tr>
										<tr>
										<td class="t_r">
											经办人：<%=username %>&nbsp;
											部门负责人：
											<select id="nbOpinion" name="nbOpinion">
											
											</select>
										</td>
										</tr>
										</table>
										</td>
	                            	</tr>
                                  	
                                  	<tr class="content7">
	                          	     	<td class="lableTd t_v">相关附件：</td>
	                                    <td colspan="5">
	                                    	<input type="hidden" name="attach" id="attach"/>
										  	<%-- --%>
										  	<iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="600" id="attachFrame" name="attachFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=&userName=<s:property value="#session.user_name"/>&loginName=<s:property value="#session.login_name"/>&procType=edit&targetType=frame&type=1"></iframe>
										</td>
	                          	    </tr>
	                          	    
									
									
	                          	  
	                          	   <tr class="content7">
	                          	   		<td class="lableTd t_v">&nbsp;</td>
	                                    <td colspan="5">
	                                    	 提醒：“<font class="redMark">*&nbsp;</font>”为必填项。
	                                    </td>                      	    
	                                </tr>
									</tbody>
                          	  	</table>
                        	</div>
	                        <%-- --%>
	                        
							<div class="mb10 t_c">
								<input type="button" id="formSubmit" value="提交"/>&nbsp;		
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