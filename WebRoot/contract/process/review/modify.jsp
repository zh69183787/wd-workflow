<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.contract.workflow.process.review.constants.ReviewMainConstants" %>
<%@page import="com.wonders.contract.workflow.process.review.constants.ReviewMainStepConstants" %>
<%@ page import="com.wonders.contract.workflow.constants.LoginConstants" %>
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
	<title>合同后审返回修改</title>
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
	
	<script src="<%=path %>/contract/js/html5.js"></script>   
	<!--<script src="<%=path %>/contract/js/jquery.formalize.js"></script>  -->
	<script src="<%=path %>/contract/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path %>/contract/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path %>/contract/js/jquery-form/jquery.form.min.js"></script>
	<script src="<%=path%>/contract/js/jquery-ui/jquery-ui.js"></script>
	<script src="<%=path %>/contract/js/jquery-ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="<%=path %>/contract/js/common/elementControl.js"></script>
	<script src="<%=path %>/contract/js/jquery-qtip/jquery.qtip-1.0.0-rc3.js"></script>
    <script src="<%=path %>/contract/js/purchase.js"></script>
	
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


	function loadReviewByPid(pid,element){
		$.post(
				'<%=path%>/contract-reviewUtil/getTypeByPid.action?random='+Math.random(),
				{
					"pid": 	pid
				},
				function(obj, textStatus, jqXHR){
					var temp = "<option value=''>请选择</option>";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							temp +="<option value='"+obj[i].ID+"'>"+obj[i].NAME+"</option>";
						}
						
					}
					
					element.html(temp);
					if(codeFlag==5){
						$("#contractType2Id").val('<s:property value="vo.contractType2Id"/>');
					}
					codeFlag ++ ;
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	var codeFlag = 1;
	function loadReviewByCode(code,element){
		$.post(
				'<%=path%>/contract-reviewUtil/getTypeByCode.action?random='+Math.random(),
				{
					"code": 	code
				},
				function(obj, textStatus, jqXHR){
					var temp = "<option value=''>请选择</option>";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							temp +="<option value='"+obj[i].ID+"'>"+obj[i].NAME+"</option>";
						}
						
					}
					element.html(temp);
					codeFlag++;
					//alert(codeFlag)
					if(codeFlag == 5){
						$("#contractMoneyTypeId").val('<s:property value="vo.contractMoneyTypeId"/>');
						<%--$("#purchaseTypeId").val('<s:property value="vo.purchaseTypeId"/>');--%>
						$("#contractGroupId").val('<s:property value="vo.contractGroupId"/>');
						$("#contractType1Id").val('<s:property value="vo.contractType1Id"/>');
						loadReviewByPid($("#contractType1Id").val(),$("#contractType2Id"));
					}
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	
	function loadDeptLeader(){
		$.post(
				'<%=path%>/contract-reviewUtil/getDeptLeader.action?random='+Math.random(),
				{
					"deptId": 	'<%=deptId%>'
				},
				function(obj, textStatus, jqXHR){
					var temp = "";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
							if(i==0){
								$("#deptLeader").val(obj[i].loginName);
							}
							temp +="<option value='"+obj[i].loginName+"'>"+obj[i].name+"</option>";
						}
						
					}
					$("#deptLeader").html(temp);
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	$(function(){
		loadReviewByCode("mtype",$("#contractMoneyTypeId"));
//		loadReviewByCode("purchase",$("#purchaseTypeId"));
        $("#purchaseTypeDiv").wrapSelect({
            url:"<%=path%>/contract-reviewUtil/dictionary.action",
            param:{"random":Math.random(),"code":"purchase"},
            selectVal:"<s:property value="vo.purchaseTypeId"/>",//默认选中对象
            name:"purchaseType",
            label:"NAME",
            value:"ID"
        });
		loadReviewByCode("group",$("#contractGroupId"));
		loadReviewByCode("type1",$("#contractType1Id"));
		
		loadDeptLeader();
		 var cache = {};
		 var lastXhr;
		 var cacheData;
		 var saveStatus = false;  
	        $("#projectName").autocomplete({  
	        	autoFocus : true,  
	        source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}
				lastXhr = $.post(
						'<%=path%>/contract-reviewUtil/getProjectInfo.action?random='+Math.random(),
						{
							maxrows: 5,
							keywords: request.term
						},
						function(data, textStatus, jqXHR){
							 cacheData = $.map( data, function( item ) {
									return {
										label: 		item.PROJECT_NAME,
										value: 		item.PROJECT_NAME,
										id: 		item.ID,
										projectname: 		item.PROJECT_NAME,
										projectidentifier :		item.PROJECT_IDENTIFIER,
										projectnum:		item.PROJECT_NUM,
										projectcompany:	item.PROJECT_COMPANY,
										projectcharge:	item.PROJECT_CHARGE,
										projectchargedept:	item.PROJECT_CHARGE_DEPT
									};
								});
							cache[ term ] = cacheData;
							if ( jqXHR === lastXhr ) {
								response(cacheData);
							}
						},
						"json"
					)
			},  
	        minLength: 1,  
	        search: function() {  
	        	$("#projectId").val("");
	            $("#projectIdentifier").val("");
	            $("#projectNum").val("");
	            $("#projectCompany").val("");
	            $("#projectCharge").val("");
	            $("#projectChargeDept").val("");
	            saveStatus = false;  
	        },  
	        select: function( event, ui ) {  
	        	$("#projectId").val(ui.item.id);
	            $("#projectName").val(ui.item.projectname);
	            $("#projectIdentifier").val(ui.item.projectidentifier);
	            $("#projectNum").val(ui.item.projectnum);
	            $("#projectCompany").val(ui.item.projectcompany);
	            $("#projectCharge").val(ui.item.projectcharge);
	            $("#projectChargeDept").val(ui.item.projectchargedept);
	            saveStatus = true;  
	            return false;  
	        },  
	        open: function() {  
	            $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );  
	        },  
	        close: function() {  
	            $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );  
	        },  
	        focus: function( event, ui ) {  
	        	$("#projectId").val("");
	            $("#projectIdentifier").val("");
	            $("#projectNum").val("");
	            $("#projectCompany").val("");
	            $("#projectCharge").val("");
	            $("#projectChargeDept").val("");
                return false;  
	        },  
	        change : function(){  
	            if(!saveStatus){  
	            	$("#projectId").val("");
		            $("#projectIdentifier").val("");
		            $("#projectNum").val("");
		            $("#projectCompany").val("");
		            $("#projectCharge").val("");
		            $("#projectChargeDept").val("");
	            }  
	        }  
	        });  
	        
		
		
		$(document).on("change","select:not('#deptLeader')",function(){
			if($(this).attr("id")=="contractType1Id" && $(this).val() != ""){
				loadReviewByPid($(this).val(),$("#contractType2Id"));
			}
			$(this).parent("td").find("#"+$(this).attr("id").replace("Id","")).val($(this).find("option:selected").text());
		});
		
		$('#passTime').datepicker({
			//inline: true								
			"changeYear":true,
			"changeMonth":true,
			"showButtonPanel":true,	
			"closeText":'清除',	
			"currentText":'passTime'//仅作为“清除”按钮的判断条件						
		});
		$('#signTime').datepicker({
			//inline: true								
			"changeYear":true,
			"changeMonth":true,
			"showButtonPanel":true,	
			"closeText":'清除',	
			"currentText":'signTime'//仅作为“清除”按钮的判断条件						
		});
		$('#execPeriodStart').datepicker({
			//inline: true								
			"changeYear":true,
			"changeMonth":true,
			"showButtonPanel":true,	
			"closeText":'清除',	
			"currentText":'execPeriodStart'//仅作为“清除”按钮的判断条件						
		});
		$('#execPeriodEnd').datepicker({
			//inline: true								
			"changeYear":true,
			"changeMonth":true,
			"showButtonPanel":true,	
			"closeText":'清除',	
			"currentText":'execPeriodEnd'//仅作为“清除”按钮的判断条件						
		});
		
		//datepicker的“清除”功能
	    $(".ui-datepicker-close").live("click", function (){              
	      if($(this).parent("div").children("button:eq(0)").text()=="passTime") $("#passTime").val("");                      
	    });
	  //datepicker的“清除”功能
	    $(".ui-datepicker-close").live("click", function (){              
	      if($(this).parent("div").children("button:eq(0)").text()=="signTime") $("#signTime").val("");                      
	    });
	  //datepicker的“清除”功能
	    $(".ui-datepicker-close").live("click", function (){              
	      if($(this).parent("div").children("button:eq(0)").text()=="execPeriodStart") $("#execPeriodStart").val("");                      
	    });
	  //datepicker的“清除”功能
	    $(".ui-datepicker-close").live("click", function (){              
	      if($(this).parent("div").children("button:eq(0)").text()=="execPeriodEnd") $("#execPeriodEnd").val("");                      
	    });
		
		$("#closeBtn").click(function(){
			window.opener=null;
			window.open("","_self");
			window.close();
		});
		
		$("#handle_zone").dialog({
			modal: true,
			autoOpen: false,
			width: 500,
			//height: 400,
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
	})	;
	
	
	
	
	</script>
	
</head>

<body class="Flow">
<form action="" name="formUpdate" id="formUpdate" method="post">
<s:include value="%{#request.getContextPath()}/contract/process/review/regModify.jsp"></s:include>
	<div class="f_bg">
      <s:include value="%{#request.getContextPath()}/contract/process/common/todo.jsp"></s:include>
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;">上海申通地铁集团有限公司</b><br><b>合同备案单</b></h1>
                        	
                        	
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
	                                    <input value="<s:property value="vo.projectName"/>" name="projectName" type="text"  id="projectName" style="width:90%;" />
	                                    <font class="redMark">*&nbsp;</font>
	                                    <input type="hidden" name="projectId" id="projectId" value="<s:property value="vo.projectId"/>">
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">项目编号：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.projectIdentifier"/>" readonly="readonly" name="projectIdentifier" type="text" class="input_large" id="projectIdentifier"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
	                                    <td class="lableTd t_v">项目批文号：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.projectNum"/>" readonly="readonly" name="projectNum" type="text" class="input_large" id="projectNum"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">项目公司：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.projectCompany"/>" readonly="readonly" name="projectCompany" type="text" class="input_large" id="projectCompany"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
	                                    <td class="lableTd t_v">项目公司负责人：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.projectCharge"/>" readonly="readonly" name="projectCharge" type="text" class="input_large" id="projectCharge"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">合同编号：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.contractIdentifier"/>" name="contractIdentifier" type="text" class="input_large" id="contractIdentifier"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
	                                    <td class="lableTd t_v">自有编号：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.contractSelfNum"/>" name="contractSelfNum" type="text" class="input_large" id="contractSelfNum"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>
                              
                              		<tr class="content6">
	                                    <td class="lableTd t_v">合同名称：</td>
	                                    <td colspan="3" class="content6">
	                                    <input value="<s:property value="vo.contractName"/>" name="contractName" type="text" class="input_xxlarge" id="contractName"  style="width:90%;"/>
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
	                                </tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">合同金额(万元)：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.contractMoney"/>" name="contractMoney" type="text" class="input_large" id="contractMoney"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
	                                    <td class="lableTd t_v">合同金额分类：</td>
	                                    <td class="content6">
	                                    <select id="contractMoneyTypeId" name="contractMoneyTypeId">
										
										</select><font class="redMark">*&nbsp;</font>
										<input value="<s:property value="vo.contractMoneyType"/>" type="hidden" name="contractMoneyType" id="contractMoneyType">
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">合同预算(万元)：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.contractBudget"/>" name="contractBudget" type="text" class="input_large" id="contractBudget"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
	                                    <td class="lableTd t_v">采购方式：</td>
	                                    <td class="content6">
                                            <div id="purchaseTypeDiv">
                                                <font class="redMark">*&nbsp;</font>

                                            </div>

	                                    <%--<select id="purchaseTypeId" name="purchaseTypeId">--%>
										<%----%>
										<%--</select><font class="redMark">*&nbsp;</font>--%>
										<%--<input value="<s:property value="vo.purchaseType"/>" type="hidden" name="purchaseType" id="purchaseType">--%>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">合同分类：</td>
	                                    <td class="content6">
	                                     <select id="contractType1Id" name="contractType1Id">
										
										</select>
										-
										 <select id="contractType2Id" name="contractType2Id">
										
										</select>
										<font class="redMark">*&nbsp;</font>
										<input value="<s:property value="vo.contractType1"/>" type="hidden" name="contractType1" id="contractType1">
										<input value="<s:property value="vo.contractType2"/>" type="hidden" name="contractType2" id="contractType2">
	                                    </td>
	                                    <td class="lableTd t_v">合同分组：</td>
	                                    <td class="content6">
	                                    <select id="contractGroupId" name="contractGroupId">
										
										</select><font class="redMark">*&nbsp;</font>
										<input value="<s:property value="vo.contractGroup"/>" type="hidden" name="contractGroup" id="contractGroup">
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">对方公司：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.oppositeCompany"/>" name="oppositeCompany" type="text" class="input_large" id="oppositeCompany"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
	                                    <td class="lableTd t_v">经办人：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.dealPerson"/>" name="dealPerson" type="text" class="input_large" id="dealPerson"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">登记人：</td>
	                                    <td class="content6">
	                                    <input readonly="readonly" value="<s:property value="vo.regPerson"/>" name="regPerson" type="text" class="input_large" id="regPerson"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
	                                    <td class="lableTd t_v">审批通过时间：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.passTime"/>" readonly="readonly" name="passTime" type="text" class="input_large" id="passTime"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                    <td class="lableTd t_v">签约时间：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.signTime"/>" readonly="readonly" name="signTime" type="text" class="input_large" id="signTime"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
	                                    <td class="lableTd t_v">负责单位/部门：</td>
	                                    <td class="content6">
	                                    <input value="<s:property value="vo.projectChargeDept"/>" id="projectChargeDept" name="projectChargeDept" readonly="readonly"  type="text" class="input_large"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>
                                  	
                                  	<tr class="content6">
	                                   <td class="lableTd t_v">履行期限：</td>
	                                    <td colspan="3" class="content6">
	                                    <input value="<s:property value="vo.execPeriodStart"/>" readonly="readonly" name="execPeriodStart" type="text" class="input_large" id="execPeriodStart"  />
	                                     至  
	                                    <input value="<s:property value="vo.execPeriodEnd"/>" readonly="readonly" name="execPeriodEnd" type="text" class="input_large" id="execPeriodEnd"  />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>
                                  	
                                   <tr class="content7">
										<td class="lableTd t_v">经办部门意见：</td>
										<td colspan="3">
										<!-- onKeyPress='value=value.substr(0,1000);' -->
										<textarea id="dealDeptSuggest" name="dealDeptSuggest" rows="5" ><s:property value="vo.dealDeptSuggest" escape="0"/></textarea></td>
	                            	</tr>
                                  	
                                    <tr class="content7">
										<td class="lableTd t_v">备注：</td>
										<td colspan="3">
										<!-- onKeyPress='value=value.substr(0,1000);' -->
										<textarea id="remark" name="remark" rows="5" ><s:property value="vo.remark" escape="0"/></textarea></td>
	                            	</tr>
	                            	
                                  	<tr class="content7">
	                          	     	<td class="lableTd t_v">相关附件：</td>
	                                    <td colspan="3">
	                                    	<input type="hidden" name="attach" id="attach" value="<s:property value="vo.attach"/>"/>
										  	<%-- --%>
										  	<iframe style="display:inline;" scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="590" id="attachFrame" name="attachFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=<s:property value="vo.attach"/>&userName=<s:property value="#session.user_name"/>&loginName=<s:property value="#session.login_name"/>&procType=edit&targetType=frame&type=1&listType=HT"></iframe>
										<font class="redMark">*&nbsp;</font>
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