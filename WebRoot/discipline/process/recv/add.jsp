<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.Calendar" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date" %>
<%@ page import="com.wonders.discipline.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.discipline.workflow.process.recv.constants.DcpRecvMainStepConstants" %>
<%@page import="com.wonders.discipline.workflow.process.recv.constants.DcpRecvMainConstants" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>纪委收文表单</title>
	<link href="<%=path%>/discipline/css/formalize.css" rel="stylesheet">
	<link href="<%=path%>/discipline/css/page.css" rel="stylesheet">
	<link href="<%=path%>/discipline/css/default/imgs.css" rel="stylesheet">
	<link href="<%=path%>/discipline/css/reset.css" rel="stylesheet">
	<link href="<%=path%>/discipline/css/jquery.ui.css" rel="stylesheet">
	<link type="text/css" href="<%=path%>/discipline/css/flick/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
	<!--[if IE 6.0]>
	<script src="js/iepng.js" type="text/javascript"></script>
	<script type="text/javascript">
	     EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
	</script>
	<![endif]-->
	<style>
	.redMark{color:red;display:inline;}
	</style>
	<script src="<%=path%>/discipline/js/contextpath.js"></script>
	<script src="<%=path%>/discipline/js/html5.js"></script>   
	<!--<script src="<%=path%>/discipline/js/jquery.formalize.js"></script>  -->
	<script src="<%=path%>/discipline/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path%>/discipline/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path%>/discipline/js/attach.js"></script>
	<script src="<%=path%>/discipline/js/jquery-form/jquery.form.min.js"></script>
	<script src="<%=path%>/discipline/js/jquery-ui/jquery-ui.js"></script>
	<script src="<%=path%>/discipline/js/jquery-ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="<%=path%>/discipline/js/common/elementControl.js"></script>
	<script src="<%=path%>/discipline/js/jquery-qtip/jquery.qtip-1.0.0-rc3.js"></script>
	
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
			    		submitZoneControl(options.formId,false);
						handleZoneControl(options.handleZone,false);
						window.location.href = contextpath+"/discipline/process/recv/add.jsp";
						//window.opener=null;
						//window.open("","_self");
						//window.close();
			    	}else{
			    		var errorLog = showError(obj.errors);
						alert("操作失败！"+errorLog);
						
						$("#"+options.submitZone+"_loading").hide();
						submitZoneControl(options.formId,false);
						handleZoneControl(options.handleZone,false);
			    	}
					//console.log(options);
		    	}else{
						alert("操作失败！");
						$("#"+options.submitZone+"_loading").hide();
						submitZoneControl(options.formId,false);
						handleZoneControl(options.handleZone,false);
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

	function loadChiefDep(){
		$.post(
				'<%=path%>/discipline-dcpRecvUtil/getCodeList.action?random='+Math.random(),
				{
					"type": 	'<%=UltimusConstants.ULTIMUS_DICTIONARY%>',
					"code": 	'<%=UltimusConstants.DISCIPLINE_RECV_DICTIONARY_CODE%>'+'sw'
				},
				function(obj, textStatus, jqXHR){
					var temp = "";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							if(i==0){
								$("#swId").val(obj[i].NAME);
							}
							temp +="<option value='"+obj[i].CODE+"'>"+obj[i].NAME+"</option>";
						}
						
					}
					$("#chiefDep").html(temp);
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	function loadSwUnit(){
		$.post(
				'<%=path%>/discipline-dcpRecvUtil/getCodeList.action?random='+Math.random(),
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
				'<%=path%>/discipline-dcpRecvUtil/getCodeList.action?random='+Math.random(),
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
	
	function getSwSerial(){
		$.post(
				'<%=path%>/discipline-dcpRecvUtil/getSerial.action?random='+Math.random(),
				{
					"prefix":	$("#swId").val(),
					"postfix": 	$("#swType").val(),
					"typeid": 	'<%=UltimusConstants.DISCIPLINE_RECV_DICTIONARY_CODE%>'
				},
				function(obj, textStatus, jqXHR){
					if(obj !=null){
						$("#swSerial").val(obj);
					}
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	
	function selSwSerial(){
		$.post(
				'<%=path%>/discipline-dcpRecvUtil/selSerial.action?random='+Math.random(),
				{
					"prefix":	$("#swId").val(),
					"postfix": 	$("#swType").val(),
					"typeid": 	'<%=UltimusConstants.DISCIPLINE_RECV_DICTIONARY_CODE%>'
				},
				function(obj, textStatus, jqXHR){
					var temp = "";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							temp +='<tr height="30"><td style="width:15%" class="td_1">'+
							'<input type="radio" name="swSerialRadio" id="swSerialRadio"'+ 
							' value="'+i+'"/></td>'+
							'<td>'+obj[i]+
							'<input type="hidden" name="swSerials" value="'+obj[i]+'">'+
							'</td></tr>';
						}
						$("#swSerialTab").html(temp);
					}else{
						$("#swSerialTab").html("");
					}
					$( "#swSerial_zone" ).dialog( "open" );
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	function loadPriorities(){
		$.post(
				'<%=path%>/discipline-dcpRecvUtil/getStatusList.action?random='+Math.random(),
				{
					"code": 	'<%=DcpRecvMainConstants.DISCIPLINE_RECV_PRIORITIES%>'
				},
				function(obj, textStatus, jqXHR){
					var temp = "";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							temp +="<option value='"+obj[i]+"'>"+obj[i]+"</option>";
						}
						
					}
					$("#priorities").html(temp);
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	
	$(function(){
		loadChiefDep();
		loadPriorities();
		$('#fileDate').datepicker({
			//inline: true		
			//"beforeShowDay": $.datepicker.noWeekends ,
			"changeYear":true,
			"showButtonPanel":true,	
			"closeText":'清除',	
			"defaultDate": "<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>",
			"currentText":'fileDate'//仅作为“清除”按钮的判断条件						
		});
		
		$('#swDate').datepicker({
			//inline: true								
			"changeYear":true,
			"showButtonPanel":true,	
			"closeText":'清除',	
			"defaultDate": "<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>",
			"currentText":'swDate'//仅作为“清除”按钮的判断条件,
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
		

		$("#swSerialGet").click(function(){
			getSwSerial();
		});
		
		$("#swSerialSel").click(function(){
			selSwSerial();
		});
		
		$("#swSerial_zone").dialog({
			modal: true,
			autoOpen: false,
			width: 400,
			height: 650,
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
			if($("input[name=swUnitRadio]:checked").length > 0){
				$("#swUnit").val($("input[name=swUnitRadio]:checked").parent("td").next("td").find("input[name=swUnits]").val());
				$("#filezh").val($("input[name=swUnitRadio]:checked").parent("td").next("td").find("input[name=filezhs]").val());
				$( "#swUnit_zone" ).dialog( "close" );
			}else{
				alert("请选择来文单位！");
			}
		});
		
		$("#chiefDep").on("change",function(){
			$("#swId").val($("#chiefDep").find("option:selected").text());
			$("#swUnit").val("");
			$("#filezh").val("");
			loadSwUnit();
		});
		
		$("#swSerialSelSubmit").on("click", function(){
			$("#swSerial").val($("input[name=swSerialRadio]:checked").parent("td").next("td").find("input[name=swSerials]").val());
			$( "#swSerial_zone" ).dialog( "close" );
		});
		
		//datepicker的“清除”功能
	    $(".ui-datepicker-close").live("click", function (){              
	      if($(this).parent("div").children("button:eq(0)").text()=="fileDate") $("#fileDate").val("");                      
	      if($(this).parent("div").children("button:eq(0)").text()=="swDate") $("#swDate").val("");                      
		   
	    });
		
		$("#closeBtn").click(function(){
			window.opener=null;
			window.open("","_self");
			window.close();
		});
		
		
		$("#formSubmit").click(function(){
			$("#handle_zone").dialog("open");
			return false;
		});
		
		$("#handle_zone").dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			zIndex: 9999,	
			open: function () {
				$("#handle_zone").parent().appendTo("#formUpdate");
				$("#handle_zone").parent().css("zIndex","9999");
			},
			close: function(event, ui) {}
		});
		
		$("#secretBut").click(function(){
			$("#remark").val("此件以机密件处理。");
		});
		$("#superBut").click(function(){
			$("#remark").val("此件以特大件处理。");
		});
	})	;
	
	
	
	</script>
	
</head>

<body class="Flow">
<form action="" name="formUpdate" id="formUpdate" method="post">
<s:include value="%{#request.getContextPath()}/discipline/process/recv/swUnit.jsp"></s:include>
<s:include value="%{#request.getContextPath()}/discipline/process/recv/swSerial.jsp"></s:include>
<s:include value="%{#request.getContextPath()}/discipline/process/recv/simulate.jsp"></s:include>

	<div class="f_bg">
     
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;">中共上海申通地铁集团有限公司纪律检查委员会</b><br><b>收文处理单</b></h1>
                        	
                        	
							<div class="mb10">
	                            <input type="hidden" name="chiefPerson" value="<%=UltimusConstants.DISCIPLINE_RECV_DICTIONARY_CODE%>"/>
								<input type="hidden" name="steplabel" value="<%=DcpRecvMainStepConstants.STEPNAME_REGISTER%>"/>
								
								<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									
								  	<tbody>
									
									<tr class="content7">
	                                    <td colspan="1" class="b0" >收文编号：</td>
	                            	    <td colspan="5" class="b0" >
	                            	    <select id="chiefDep" name="chiefDep">
	                            	    	
	                            	    	
	                            	    </select>
	                            	   <input type="hidden" name="swId" id="swId">
	                            	    -
	                            	     <select id="swType" name="swType">
	                            	     	<%
												Calendar c = Calendar.getInstance();
												int year = c.get(Calendar.YEAR);
												for(int i=0;i<3;i++){ %>
													<option value="<%=(year-1)+i %>"
												<%if(((year-1)+i)==year){ %>selected=true
												<%} %>><%=(year-1)+i %></option>
											<%} %>
	                            	    </select>
	                            	    -
	                            	    <input type="text" class="input_none" id="swSerial" name="swSerial" readonly="readonly"/>
	                            	    &nbsp;
	                            	    <input type="button" id="swSerialGet" value="取"/>
	                            	    &nbsp;
	                            	    <input type="button" id="swSerialSel" value="选"/>
	                            	    <font class="redMark">*&nbsp;</font>
	                            	    </td>
	                            	    <td colspan="1" class="b0" >收文日期：</td>
	                            	    <td colspan="5" class="b0" ><input type="text" class="input_large date" name="swDate" id="swDate" readonly="readonly"/>
	                            	    <font class="redMark">*&nbsp;</font>
	                            	    </td>
	                          	    </tr>
								
									<tr class="content7">
	                                    <td colspan="1" class="lableTd t_v">来文单位：</td>
	                            	    <td colspan="5"><input type="text" class="input_xlarge" name="swUnit" id="swUnit"/>
	                            	    <input type="button" value="选择" name="swUnitSel" id="swUnitSel"/>
	                            	 	<font class="redMark">*&nbsp;</font>
	                            	 	</td>
	                            	    <td colspan="1" class="lableTd t_v">文件日期：</td>
	                            	    <td colspan="5"><input type="text" class="input_large date" name="fileDate" id="fileDate" readonly="readonly"/>
	                            	    <font class="redMark">*&nbsp;</font>
	                            	    </td>
	                          	    
	                                </tr>
	                                
	                                <tr class="content7">
	                                    <td colspan="1" class="lableTd t_v">密级：</td>
	                            	    <td colspan="1">
	                            	    <select id="secretClass" name="secretClass">
	                            	    	<option value="普通">普通</option>
											<option value="秘密">秘密</option>
											<option value="机密">机密</option>
											<option value="绝密">绝密</option>
	                            	    </select>
	                            	    <font class="redMark">*&nbsp;</font>
	                            	    </td>
	                            	    <td colspan="1" class="lableTd t_v">份数：</td>
	                            	    <td colspan="1">
	                            	    	<input type="text" class="input_tiny" name="num" id="num" value="1"/>
	                            	    	<font class="redMark">*&nbsp;</font>
	                            	    </td>
	                            	    <td colspan="1" class="lableTd t_v">缓急：</td>
	                            	    <td colspan="1">
	                            	    <select id="priorities" name="priorities">
	                            	    	
	                            	    </select>
	                            	    <font class="redMark">*&nbsp;</font>
	                            	    </td>
	                            	     <td colspan="1" class="lableTd t_v">文件字号：</td>
	                            	    <td colspan="5"><input type="text" class="input_large" name="filezh" id="filezh"/>
	                            	    </td>
	                            	    
	                                </tr>
	                          	    
	                          	    
	                          	    
                                  	<tr class="content6">
	                                    <td colspan="1" class="lableTd t_v">文件标题：</td>
	                                    <td colspan="11" colspan="3" class="content6">
	                                    <input name="title" type="text" class="input_large" id="title" style="width:90%;"/>
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>
                              
   
	                          	    <tr class="content6">
	                                    <td colspan="1" class="lableTd t_v">关键词：</td>
	                                    <td colspan="11" class="content6">
	                                    <input name="keyword" type="text" class="input_large" id="keyword" style="width:90%;"/></td>
                                  	</tr>
                                  	
                                  	
                                  <tr class="content7">
	                          	     	<td class="lableTd t_v">文件内容：</td>
	                                    <td colspan="11">
	                                    	<input type="hidden" name="attach" id="attach"/>
										  	<%-- --%>
										  	<iframe style="display:inline;" scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="600" id="attachFrame" name="attachFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=&userName=<s:property value="#session.user_name"/>&loginName=<s:property value="#session.login_name"/>&procType=edit&targetType=frame&type=1"></iframe>
										<font class="redMark">*&nbsp;</font>
										</td>
	                          	    </tr>
	                          	    
									<tr class="content7">
										<td colspan="1" class="lableTd t_v">备注：</td>
										<td colspan="11">
										<!-- onKeyPress='value=value.substr(0,1000);' -->
										<textarea id="remark" name="remark" rows="10" ></textarea>
										<input type="button" id="secretBut" value="机密件"/>&nbsp;
										<input type="button" id="superBut" value="特大件"/>&nbsp;
										</td>	
	                            	</tr>
									
	                          	    <tr class="content7">
	                                    <td colspan="1" class="lableTd t_v">办理方式：</td>
	                            	    <td colspan="11">
	                            	    <select id="blMode" name="blMode">
	                            	    	<option value="网上流转">网上流转</option>
	                            	    	<option value="书面流转">书面流转</option>
	                            	    </select>
	                            	    <font class="redMark">*&nbsp;</font>
	                            	   	</td>
	                                </tr>
	                          	   <tr class="content7">
	                          	   		<td colspan="1" class="lableTd t_v">&nbsp;</td>
	                                    <td colspan="11">
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
							      <p style="width:auto;display:inline;"><img src="<%=path%>/discipline/css/default/images/loading.gif" style="display:inline;"/>
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