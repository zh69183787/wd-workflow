<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wonders.receive.workflow.constants.UltimusConstants" %>
<%@page import="com.wonders.receive.workflow.process.recv.constants.RecvMainConstants" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>登记人修改</title>
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

	function loadChiefDep(){
		$.post(
				'<%=path%>/receive-recvUtil/getCodeList.action?random='+Math.random(),
				{
					"type": 	'<%=UltimusConstants.ULTIMUS_DICTIONARY%>',
					"code": 	'<%=UltimusConstants.RECV_DICTIONARY_CODE%>'+'sw'
				},
				function(obj, textStatus, jqXHR){
					var temp = "";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							temp +="<option value='"+obj[i].CODE+"'>"+obj[i].NAME+"</option>";
						}
						
					}
					$("#chiefDep").html(temp);
					$("#chiefDep").val($("#t_chiefDep").val());
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	function loadSwUnit(){
		$.post(
				'<%=path%>/receive-recvUtil/getCodeList.action?random='+Math.random(),
				{
					"type": 	'<%=UltimusConstants.ULTIMUS_DICTIONARY%>',
					"code": 	$("#chiefDep").val()
				},
				function(obj, textStatus, jqXHR){
					var temp = "";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							temp +='<tr height="30"><td class="td_1">'+
							'<input type="radio" name="swUnitRadio" id="swUnitRadio"'+ 
							' value="'+i+'"/></td>'+
							'<td>'+obj[i].NAME+
							'<input type="hidden" name="swUnits" value="'+obj[i].NAME+'">'+
							'<input type="hidden" name="filezhs" value="'+obj[i].DESCRIPTION+'">'+
							'</td></tr>';
						}
						$("#swUnitTab").html(temp);
					}else{
						$("#swUnitTab").html("");
					}
					$( "#swUnit_zone" ).dialog( "open" );
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	
	function querySwUnit(){
		$.post(
				'<%=path%>/receive-recvUtil/getCodeList.action?random='+Math.random(),
				{
					"type": 	'<%=UltimusConstants.ULTIMUS_DICTIONARY%>',
					"code": 	$("#chiefDep").val(),
					"name":		$("#swUnitDig").val()
				},
				function(obj, textStatus, jqXHR){
					var temp = "";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							temp +='<tr height="30"><td class="td_1">'+
							'<input type="radio" name="swUnitRadio" id="swUnitRadio"'+ 
							' value="'+i+'"/></td>'+
							'<td>'+obj[i].NAME+
							'<input type="hidden" name="swUnits" value="'+obj[i].NAME+'">'+
							'<input type="hidden" name="filezhs" value="'+obj[i].DESCRIPTION+'">'+
							'</td></tr>';
						}
						$("#swUnitTab").html(temp);
					}else{
						$("#swUnitTab").html("");
					}
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	
	$(function(){
		loadChiefDep();
		$("#blMode").val($("#t_blMode").val());
		$('#fileDate').datepicker({
			//inline: true								
			"changeYear":true,
			"showButtonPanel":true,	
			"closeText":'清除',	
			"currentText":'fileDate'//仅作为“清除”按钮的判断条件						
		});
		
		
		$("#swUnitSel").click(function(){
			loadSwUnit();
		});
	
		
		$("#swUnit_zone").dialog({
			modal: true,
			autoOpen: false,
			width: 400,
			//height: 650,
			zIndex: 9999,	
			close: function(event, ui) {}
		});
		
		
		$("#swUnitQuy").on("click", function(){
			querySwUnit();
		});
		
		$("#swUnitClr").on("click", function(){
			$("#swUnitDig").val("");
		});
		
		$("#swUnitSubmit").on("click", function(){
			$("#swUnit").val($("input[name=swUnitRadio]:checked").parent("td").next("td").find("input[name=swUnits]").val());
			$("#filezh").val($("input[name=swUnitRadio]:checked").parent("td").next("td").find("input[name=filezhs]").val());
			$( "#swUnit_zone" ).dialog( "close" );
		});
		
		
		$("#chiefDep").on("change",function(){
			$("#swId").val($("#chiefDep").find("option:selected").text());
			$("#swUnit").val("");
			$("#filezh").val("");
			loadSwUnit();
		});
		
		
		//datepicker的“清除”功能
	    $(".ui-datepicker-close").live("click", function (){              
	      if($(this).parent("div").children("button:eq(0)").text()=="fileDate") $("#fileDate").val("");                      
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
				options.url = "<%=path %>/receive-recvMain/registerModify.action";
				options.submitZone = "formUpdate";
				submitZoneControl(options.submitZone,true);
				$("#"+options.submitZone+"_loading").show();
				$("#choice").val("1");
				clearError();
				//$("#swId").val($("#swId").val()+"-"+$("#swSerial").val())
				$("#"+options.submitZone).ajaxSubmit(options);
				//$(this).removeAttr("disabled");
				return false;
			}
		});
		
		$("#formCancel").click(function(){
			if(confirm("确认提交吗？")){
				$(this).attr("disabled",true);
				options.formId = "formUpdate";
				options.url = "<%=path %>/receive-recvMain/registerModify.action";
				options.submitZone = "formUpdate";
				submitZoneControl(options.submitZone,true);
				$("#"+options.submitZone+"_loading").show();
				$("#choice").val("2");
				clearError();
				//$("#swId").val($("#swId").val()+"-"+$("#swSerial").val())
				$("#"+options.submitZone).ajaxSubmit(options);
				//$(this).removeAttr("disabled");
				return false;
			}
		});

		
	})	;
	
	
	
	</script>
	
</head>
<body class="Flow">
<s:include value="%{#request.getContextPath()}/receive/process/recv/swUnit.jsp"></s:include>
<form action="" name="formUpdate" id="formUpdate" method="post">
	<div class="f_bg">
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;">上海申通地铁集团有限公司</b><br><b>收文处理单</b></h1>
                        	
							<div class="mb10">
	                            <input type="hidden" id="id" name="id" value="<s:property value="vo.id"/>"/>
								<input type="hidden" name="pname" value="<s:property value="params.processParam['pname']"/>"/>
								<input type="hidden" name="pincident" value="<s:property value="params.processParam['pincident']"/>"/>
								<input type="hidden" name="cname" value="<s:property value="params.processParam['cname']"/>"/>
								<input type="hidden" name="cincident" value="<s:property value="params.processParam['cincident']"/>"/>
								<input type="hidden" name="steplabel" value="<s:property value="params.processParam['steplabel']"/>"/>
								<input type="hidden" name="chiefPerson" value="<s:property value="vo.chiefPerson"/>"/>
								<input type="hidden" id="choice" name="choice" value="1"/>
								
								<input type="hidden" id="taskuser" name="taskuser" value="<s:property value="params.processParam['taskuser']"/>"/>
								
								<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
								
								  	<tbody>    
                                  	<tr class="content6">
	                                    <td colspan="1" class="lableTd t_v">文件标题：</td>
	                                    <td colspan="3" colspan="3" class="content6">
	                                    <input name="title" type="text" class="input_large" id="title" style="width:90%;"  value="<s:property value="vo.title"/>"/>
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>
                              
									<tr class="content7">
	                                    <td colspan="1" class="lableTd t_v">收文编号：</td>
	                            	    <td colspan="1">
	                            	    <select id="chiefDep" name="chiefDep">
	                            	    	
	                            	    	
	                            	    </select>
	                            	    <font class="redMark">*&nbsp;</font>
	                            	    <input type="hidden" id="t_chiefDep" name="t_chiefDep" value="<s:property value="vo.chiefDep"/>">                          	    
	                            	    <input type="hidden" name="swId" id="swId" value="<s:property value="vo.swId"/>">
	                            	     <td colspan="1" class="lableTd t_v">文件日期：</td>
	                            	    <td colspan="1"><input type="text" class="input_large date" name="fileDate" id="fileDate" readonly="readonly"  value="<s:property value="vo.fileDate"/>"/>
	                            	    <font class="redMark">*&nbsp;</font>
	                            	    </td>
	                          	    </tr>
								
									<tr class="content7">
	                                    <td colspan="1" class="lableTd t_v">来文单位：</td>
	                            	    <td colspan="1"><input type="text" class="input_large" name="swUnit" id="swUnit" value="<s:property value="vo.swUnit"/>"/>
	                            	    <input type="button" value="选择" name="swUnitSel" id="swUnitSel"/>
	                            	    <font class="redMark">*&nbsp;</font>
	                            	    </td>
	                                    <td colspan="1" class="lableTd t_v">文件字号：</td>
	                            	    <td colspan="1"><input type="text" class="input_large" name="filezh" id="filezh" value="<s:property value="vo.filezh"/>"/>
	                            	    <font class="redMark">*&nbsp;</font>
	                            	    </td>
	                          	 	</tr>
	                          	    
	                          	    <tr class="content6">
	                                    <td colspan="1" class="lableTd t_v">关键词：</td>
	                                    <td colspan="3" class="content6">
	                                    <input name="keyword" type="text" class="input_large" id="keyword" style="width:100%;"  value="<s:property value="vo.keyword"/>"/></td>
                                  	</tr>
                                  	
                                  	
                                  	<tr class="content7">
	                          	     	<td colspan="1" class="lableTd t_v">文件内容：</td>
	                                    <td colspan="3">
	                                    	<input type="hidden" name="attach" id='attach' value="<s:property value="vo.attach"/>"/>
										  	<%-- --%>
										  	<iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="600" id="contentAttachmentFrame" name="contentAttachmentFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=<s:property value="vo.attach"/>&userName=<s:property value="params.userInfo.userName"/>&loginName=<s:property value="params.userInfo.login_Name"/>&procType=edit&targetType=frame&type=1"></iframe>
										</td>
	                          	    </tr>
	                          	    
									<tr class="content7">
										<td colspan="1" class="lableTd t_v">备注：</td>
										<td colspan="3">
										<!-- onKeyPress='value=value.substr(0,1000);' -->
										<textarea id="remark" name="remark" rows="10" ><s:property escape="0" value="vo.remark"/></textarea>
										</td>	
	                            	</tr>
	                            	
	                            	 
									<tr class="content7 tr_height">
										<td class="lableTd t_v">文件审核意见：</td>
										<td colspan="3">
										<s:action name="serial" namespace="/receive-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'文件审核及编号'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
	                            	
	                          	    <tr class="content7">
	                                    <td colspan="1" class="lableTd t_v">办理方式：</td>
	                            	    <td colspan="3">
	                            	    <select id="blMode" name="blMode">
	                            	    	<option value="网上流转">网上流转</option>
	                            	    	<option value="书面流转">书面流转</option>
	                            	    </select>
	                            	    <font class="redMark">*&nbsp;</font>
	                            	    <input type="hidden" id="t_blMode" name="t_blMode" value="<s:property value="vo.blMode"/>">
	                            	    </td>
	                               </tr>
	                          	   <tr class="content7">
	                          	   		<td colspan="1" class="lableTd t_v">&nbsp;</td>
	                                    <td colspan="3">
	                                    	 提醒：“<font class="redMark">*&nbsp;</font>”为必填项。
	                                    </td>                      	    
	                                </tr>
									</tbody>
                          	  	</table>
                        	</div>
	                        <%-- --%>
	                        
							<div class="mb10 t_c">
								<input type="button" id="formSubmit" value="提交"/>&nbsp;
								<input type="button" id="formCancel" value="取消申报"/>&nbsp;
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