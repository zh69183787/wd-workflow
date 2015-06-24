<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date" %>
<%@ page import="com.wonders.contract.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.contract.workflow.process.review.constants.ReviewMainStepConstants" %>
<%@ page import="com.wonders.util.StringUtil" %>
<%@ page import="com.wonders.contract.workflow.constants.LoginConstants" %>

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
	<title>合同编码</title>
	<link href="<%=path%>/contract/css/formalize.css" rel="stylesheet">
	<link href="<%=path%>/contract/css/page.css" rel="stylesheet">
	<link href="<%=path%>/contract/css/default/imgs.css" rel="stylesheet">
	<link href="<%=path%>/contract/css/reset.css" rel="stylesheet">
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
	<script src="<%=path %>/contract/js/jquery-form/jquery.form.min.js"></script>
	<script src="<%=path%>/contract/js/jquery-ui/jquery-ui.js"></script>
	<script src="<%=path %>/contract/js/jquery-ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="<%=path %>/contract/js/common/elementControl.js"></script>
	<script src="<%=path %>/contract/js/jquery-qtip/jquery.qtip-1.0.0-rc3.js"></script>
	
	<script type="text/javascript">
	var options = {
			submitZone:'',
			cache:false,
			type:'post',
			callback:null,
			url:'',
			dataType : 'text',
		    success:function(obj){
		    	if(obj !=null && obj.length > 0){
		    		alert("生成成功！");
		    		$("#contractNum").html(obj);
			    	$("#"+options.submitZone+"_loading").hide();
					submitZoneControl(options.submitZone,false);	
					
		    	}else{
					alert("操作失败！");
					$("#contractNum").html("");
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

	function loadCodeInfo(code,o){
		$.post(
				'<%=path%>/contract-reviewUtil/getCodeInfo.action?random='+Math.random(),
				{
					"typeCode": 	"<%=UltimusConstants.CONTRACT_NUM_CODE%>",
					"code": 	code
				},
				function(obj, textStatus, jqXHR){
					var temp = "<option value=''>--请选择--</option>";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							temp +="<option code='"+obj[i].CODE+"'"
							+" value='"+obj[i].NAME+"'>"+obj[i].DESCRIPTION+"</option>";
						}
						
					}
					o.html(temp);
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	$(function(){
		loadCodeInfo("<%=UltimusConstants.CONTRACT_NUM_SIGN_COMPANY%>",$("#signCompany"));
		loadCodeInfo("<%=UltimusConstants.CONTRACT_NUM_LINE%>",$("#line"));
		loadCodeInfo("<%=UltimusConstants.CONTRACT_NUM_DIVISION%>",$("#division"));
		loadCodeInfo("<%=UltimusConstants.CONTRACT_NUM_EXEC_COMPANY%>",$("#execCompany"));
		loadCodeInfo("<%=UltimusConstants.CONTRACT_NUM_YEAR%>",$("#year"));
		
		
		$(document).on("change","#division",function(){
			var co = $(this).find("option:selected").attr("code");
			loadCodeInfo(co,$("#section"));
		});
				
		$("#closeBtn").click(function(){
			window.opener=null;
			window.open("","_self");
			window.close();
		});
		
		$("#formSubmit").click(function(){
			if(confirm("确认提交吗？")){
				if($("#contractName").val()==""||
						$("#projectNo").val()==""||$("#subProjectNo").val()==""||
						$("#signCompany").val()==""||$("#line").val()==""||
						$("#division").val()==""||$("#section").val()==""||
						$("#year").val()==""||$("#execCompany").val()==""){
					alert("必填字段不能为空！");
					return false;
				}
				$(this).attr("disabled",true);		
				options.formId = "formUpdate";
				options.url = "<%=path %>/contract-reviewUtil/generateNo.action";
				options.submitZone = "formUpdate";
				submitZoneControl(options.submitZone,true);
				$("#"+options.submitZone+"_loading").show();
				clearError();		
			
				$("#"+options.submitZone).ajaxSubmit(options);
				return false;
			}
			//$(this).removeAttr("disabled");
			
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
                        	<h1 class="t_c"><b>合同编码生成</b></h1>
                        	
							<div class="mb10">
								
								<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									
								  	<tbody>
	                          	    <tr class="content6">
	                                    <td style="width:15%;" class="lableTd t_v">合同名称：</td>
	                                    <td colspan="3" class="content6">
	                                    <input name="contractName" type="text" id="contractName" class="input_large" style="width:90%;" />
	                                    <font class="redMark">*&nbsp;</font>
	                                   	</td>
                                  	</tr>
	                          	    <tr class="content6">
	                                    <td style="width:15%;" class="lableTd t_v">项目编号：</td>
	                                    <td colspan="3" class="content6">
	                                    <input name="projectNo" type="text" id="projectNo" class="input_large" style="width:90%;" />
	                                    <font class="redMark">*&nbsp;</font>
	                                   	</td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">子项目编号：</td>
	                                    <td colspan="3" class="content6">
	                                    <input name="subProjectNo" type="text" style="width:90%;" class="input_large" id="subProjectNo"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
	                                    
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td style="width:15%;" class="lableTd t_v">合同签约主体：</td>
	                                    <td style="width:35%;" class="content6">
	                                    <select id="signCompany" name="signCompany">
										
										</select><font class="redMark">*&nbsp;</font>
	                                    </td>
	                                    <td style="width:15%;" class="lableTd t_v">线路名称：</td>
	                                    <td style="width:35%;" class="content6">
	                                    <select id="line" name="line">
										
										</select><font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">合同大类：</td>
	                                    <td class="content6">
	                                    <select id="division" name="division">
										
										</select><font class="redMark">*&nbsp;</font>
	                                    
	                                    </td>
	                                    <td class="lableTd t_v">合同小类：</td>
	                                    <td class="content6">
	                                    <select id="section" name="section">
										
										</select><font class="redMark">*&nbsp;</font>
	                                    
	                                    </td>
                                  	</tr>
                              
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">年份：</td>
	                                    <td class="content6">
	                                    <select id="year" name="year">
										
										</select><font class="redMark">*&nbsp;</font>
	                                    
	                                    </td>
	                                     <td class="lableTd t_v">执行主体：</td>
	                                    <td class="content6">
	                                     <select id="execCompany" name="execCompany">
										
										</select><font class="redMark">*&nbsp;</font>
	                                    
	                                    </td>
                                  	</tr>
                                  
									</tbody>
                          	  	</table>
                        	</div>
	                        <%-- --%>
	                        
							<div class="mb10 t_c">
								<input type="button" id="formSubmit" value="生成"/>&nbsp;	
								<input type="button" id="closeBtn" value="关闭"/>&nbsp;	
								<div id="formUpdate_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
							      <p style="width:auto;display:inline;"><img src="<%=path%>/receive/css/default/images/loading.gif" style="display:inline;"/>
							      <b style="color:green;display:inline;">&nbsp;正在生成</b></p>
							    </div>
							</div>
							
							<div class="mb10">
								
								<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									
								  	<tbody>
	                          	    
	                          	    <tr class="content6">
	                                    <td style="width:15%;" class="lableTd t_v">合同编号：</td>
	                                    <td class="content6" id="contractNum"></td>
                                  	</tr>
                                  	</tbody>
                                  	</table>
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