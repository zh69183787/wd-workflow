<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.receive.workflow.process.redv.constants.RedvMainConstants" %>
<%@page import="com.wonders.receive.workflow.process.redv.constants.RedvMainStepConstants" %>
<%@ page import="com.wonders.receive.workflow.constants.LoginConstants" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String deptId = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTID));
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>呈报部门领导审核</title>
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
						$( "#ldCheck_zone" ).dialog( "close" );
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
							temp +="<option value='"+obj[i].NAME+"'>"+obj[i].NAME+"</option>";
						}
						
					}
					$("#docLevel").html(temp);
					$("#docLevel").val('<s:property value="vo.docLevel" escape="0"/>');
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	function loadDeptLeader(){
		$.post(
				'<%=path%>/receive-redvUtil/getDeptLeader.action?random='+Math.random(),
				{
					"deptId": 	'<s:property value="params.userInfo.deptId"/>'
				},
				function(obj, textStatus, jqXHR){
					var temp = "";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							temp +="<option value='"+obj[i].loginName+"'>"+obj[i].name+"</option>";
						}
						
					}
					$("#nbOpinion").html(temp);
					$("#nbOpinion").val('<s:property value="vo.nbOpinion" escape="0"/>');
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	$(function(){
		loadDocLevel();
		<%if(RedvMainStepConstants.STEPNAME_MODIFY.equals(steplabel)){%>
			loadDeptLeader();
		<%}%>
		$('#submitDate').datepicker({
			//inline: true								
			"changeYear":true,
			"showButtonPanel":true,	
			"closeText":'清除',	
			"currentText":'submitDate'//仅作为“清除”按钮的判断条件						
		});
			
		
		$("#handle_zone").dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			//height: 300,
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
		
		//datepicker的“清除”功能
	    $(".ui-datepicker-close").live("click", function (){              
	      if($(this).parent("div").children("button:eq(0)").text()=="submitDate") $("#submitDate").val("");                      
	     });
		
		$("#closeBtn").click(function(){
			window.opener=null;
			window.open("","_self");
			window.close();
		});
		
	
		
	})	;
	
	
	
	</script>
	
</head>

<body class="Flow">

<form action="" name="formUpdate" id="formUpdate" method="post">
<%if(RedvMainStepConstants.STEPNAME_LEADER_CHECK.equals(steplabel)) {%>
	<s:include value="%{#request.getContextPath()}/receive/process/redv/ldCheck.jsp"></s:include>
<%}else if(RedvMainStepConstants.STEPNAME_MODIFY.equals(steplabel)) {%>
	<s:include value="%{#request.getContextPath()}/receive/process/redv/modify.jsp"></s:include>
<%} %>
	<div class="f_bg">
      <s:include value="%{#request.getContextPath()}/receive/process/common/todo.jsp"></s:include>
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;">上海申通地铁集团有限公司</b><br><b>呈批文件单</b></h1>
                        	
                        	
							<div class="mb10">
	                            <input type="hidden" id="id" name="id" value="<s:property value="vo.id"/>"/>
								<input type="hidden" name="pname" value="<s:property value="params.processParam['pname']"/>"/>
								<input type="hidden" name="pincident" value="<s:property value="params.processParam['pincident']"/>"/>
								<input type="hidden" name="cname" value="<s:property value="params.processParam['cname']"/>"/>
								<input type="hidden" name="cincident" value="<s:property value="params.processParam['cincident']"/>"/>
								<input type="hidden" name="steplabel" value="<s:property value="params.processParam['steplabel']"/>"/>
								<input type="hidden" name="taskid" value="<s:property value="params.processParam['taskid']"/>"/>
								
								<input type="hidden" name="chiefPerson" value="<s:property value="vo.chiefPerson"/>"/>
								<%if(RedvMainStepConstants.STEPNAME_LEADER_CHECK.equals(steplabel)){%>
								<input type="hidden" name="nbOpinion" value="<s:property value="vo.nbOpinion"/>"/>
								<input type="hidden" name="operator" value="<s:property value="vo.operator"/>"/>
								<%} %>
								<input type="hidden" id="taskuser" name="taskuser" value="<s:property value="params.processParam['taskuser']"/>"/>
								
								<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									
								  	<tbody>
									
									<tr class="content7">
	                                    <td class="b0"  colspan="1">呈报部门：</td>
	                            	    <td class="b0" colspan="1">
	                            	    <s:property value="vo.submitDept"/>
	                            	   	<input value="<s:property value="vo.submitDept"/>" name="submitDept" type="hidden" id="submitDept"/>
	                            	   	<input name="zleader" type="hidden" id="zleader" value="<s:property value="vo.zleader"/>"/>
	                            	   	</td>
	                            	    <td class="b0" colspan="1">呈报日期：</td>
	                            	    <td class="b0" colspan="1" >
	                            	   <input type="text" class="input_medium date" name="submitDate" id="submitDate" readonly="readonly" value="<s:property value="vo.submitDate"/>" />
	                            	    <font class="redMark">*&nbsp;</font>
	                            	    </td>
	                            	    <td class="b0" colspan="1">部门编号：</td>
	                            	    <td class="b0" colspan="1" id="zleaderTd">
										&nbsp;
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
	                                    <td style="width:15%;" class="lableTd t_v">文件标题：</td>
	                                    <td colspan="5" class="content6">
	                                    <input value="<s:property value="vo.title"/>" name="title" type="text" class="input_large" id="title" style="width:90%;"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>
                              
									
								
	                          	    
	                          	    <tr class="content6">
	                                    <td class="lableTd t_v">关键词：</td>
	                                    <td colspan="3" class="content6">
	                                    <input value="<s:property value="vo.keyword"/>" name="keyword" type="text" class="input_large" id="keyword" style="width:100%;" />
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
											<textarea id="content" name="content" rows="10" ><s:property value="vo.content" escape="0"/></textarea>
										</td>
										</tr>
										<tr>
										<td class="t_r">
											经办人：<s:property value="vo.operator"/>&nbsp;
											部门负责人：
											<%if(RedvMainStepConstants.STEPNAME_MODIFY.equals(steplabel)){%>
												<select id="nbOpinion" name="nbOpinion">
												
												</select>
											<%}else{ %>
												<s:property value="vo.deptMaster"/>
											<%} %>
											<input type="hidden" name="deptMaster" value="<s:property value="vo.deptMaster"/>"/>								
										</td>
										</tr>
										</table>
										</td>
	                            	</tr>
                                  	
                                  	<tr class="content7">
	                          	     	<td class="lableTd t_v">相关附件：</td>
	                                    <td colspan="5">
	                                    	<input type="hidden" name="attach" id="attach" value="<s:property value="vo.attach"/>"/>
										  	<%-- --%>
										  	<iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="600" id="attachFrame" name="attachFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=<s:property value="vo.attach"/>&userName=<s:property value="params.userInfo.userName"/>&loginName=<s:property value="params.userInfo.login_Name"/>&procType=edit&targetType=frame&type=1"></iframe>
										</td>
	                          	    </tr>
	                          	    
									
									<tr class="content7 tr_height">
										<td class="lableTd t_v">部门审核意见：</td>
										<td colspan="5">
										<s:action name="serial" namespace="/receive-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'呈报部门领导审核','呈报人修改'
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