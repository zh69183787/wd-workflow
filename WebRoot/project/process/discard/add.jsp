<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.Calendar" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date" %>
<%@page import="com.wonders.util.DateUtil" %>
<%@page import="com.wonders.util.PropertyUtil" %>
<%@ page import="com.wonders.project.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.project.workflow.process.discard.constants.DiscardMainStepConstants" %>
<%@page import="com.wonders.project.workflow.process.discard.constants.DiscardMainConstants" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String thisYear = DateUtil.getCurrDate("yyyy");
	String projectCancelsQueryUrl = PropertyUtil.getValueByKey("config.properties", "projectCancelsQueryUrl");
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>项目销项表单</title>
	<link href="<%=path%>/project/css/formalize.css" rel="stylesheet">
	<link href="<%=path%>/project/css/page.css" rel="stylesheet">
	<link href="<%=path%>/project/css/default/imgs.css" rel="stylesheet">
	<link href="<%=path%>/project/css/reset.css" rel="stylesheet">
	<link href="<%=path%>/project/css/jquery.ui.css" rel="stylesheet">
	<link type="text/css" href="<%=path%>/project/css/flick/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
	<style>
	.redMark{color:red;display:inline;}
	#assetTable td{text-align:center;}
	</style>
	<script src="<%=path %>/project/js/contextpath.js"></script>
	<script src="<%=path %>/project/js/html5.js"></script>   
	<!--<script src="<%=path %>/project/js/jquery.formalize.js"></script>  -->
	<script src="<%=path %>/project/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path %>/project/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path %>/project/js/jquery-form/jquery.form.min.js"></script>
	<script src="<%=path%>/project/js/jquery-ui/jquery-ui.js"></script>
	<script src="<%=path %>/project/js/jquery-ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="<%=path %>/project/js/common/elementControl.js"></script>
	<script src="<%=path %>/project/js/jquery-qtip/jquery.qtip-1.0.0-rc3.js"></script>
	
	<script type="text/javascript">
	var settingContentObj;
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
						submitZoneControl(options.submitZone,false);	
						window.opener=null;
						window.open("","_self");
						window.close();
						
			    	}else{
			    		var errorLog = showError(obj.errors);
						alert("操作失败！"+errorLog);
						
						$("#"+options.submitZone+"_loading").hide();
						submitZoneControl(options.formId,false);
			    	}
		    	}else{
						alert("操作失败！");
						$("#"+options.submitZone+"_loading").hide();
						submitZoneControl(options.formId,false);
				}		
				return false;
		    }
		};
	function loadDeptLeader(){
		$.post(
				'<%=path%>/project-organTree/getDeptLeaders.action?id=${sessionScope.dept_id}&random='+Math.random(),
				{},
				function(obj, textStatus, jqXHR){
					var temp = "";
					if(obj !=null && obj.length>0){
						for(var i=0;i<obj.length;i++){
	 						if(i==0){
								$("#deptLeader").val(obj[i].name);
							}
							temp +="<option value='"+obj[i].loginName+"'>"+obj[i].name+"</option>";
						}
						
					}
					$("#deptLeader").html(temp);
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
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
	
	function loadAssetsByDispatchNo(dispatchNo){
		$('#assets_loading').show();
		$.post(
				'<%=path%>/project-discardUtil/getAssetInfo.action?random='+Math.random(),
				{
					"dispatchNo": dispatchNo
				},
				function(obj, textStatus, jqXHR){
					var html = '';
					var assets = obj.assetRecords;
					if(assets !=null && assets.length>0){
						for(var i=0;i<assets.length;i++){
							var lifeLeft = '';
							var thisYear = '<%=thisYear%>'*1;
							if($.isNumeric(assets[i].userLife) && assets[i].operateProjectAssetDate.length > 3 && $.isNumeric(assets[i].operateProjectAssetDate.substr(0,4))){
								var transferYear = assets[i].operateProjectAssetDate.substr(0,4)*1;
								lifeLeft = assets[i].userLife*1 - (thisYear-transferYear);
							}
							html +="<tr><td><input type='hidden' name='assets["+i+"].removed' value='0'/>"+
								"<input type='hidden' name='assets["+i+"].assetRecordId' value='"+assets[i].id+"'/>"+
								assets[i].assetNo+"<input type='hidden' name='assets["+i+"].assetNo' value='"+assets[i].assetNo+"'/></td><td>"+
								assets[i].assetName+"<input type='hidden' name='assets["+i+"].assetName' value='"+assets[i].assetName+"'/></td><td>"+
								assets[i].completeTransAssetType+"<input type='hidden' name='assets["+i+"].operateProjectAsset' value='"+assets[i].completeTransAssetType+"'/></td><td>"+
								"<select name='assets["+i+"].assetType'><option selected>"+assets[i].assetType+"</option><option>计划报废</option></select>"+
								"<input type='hidden' name='assets["+i+"].assetTypeOriginal' value='"+assets[i].assetType+"'/></td><td>"+
								assets[i].originalValue+"<input type='hidden' name='assets["+i+"].originalValue' value='"+assets[i].originalValue+"'/></td><td>"+
								//"<select name='assets["+i+"].costType'><option selected>资本性</option><option>费用性</option></select>"+"</td><td>"+
								assets[i].netValue+"<input type='hidden' name='assets["+i+"].netValue' value='"+assets[i].netValue+"'/></td><td>"+
								assets[i].maintainCost+"<input type='hidden' name='assets["+i+"].maintainCost' value='"+assets[i].maintainCost+"'/></td><td>"+
								lifeLeft+"<input type='hidden' name='assets["+i+"].lifeLeft' value='"+lifeLeft+"'/></td><td>"+
								(assets[i].assetCodeType=="2"?
										"<select name='assets["+i+"].lifeExtend'>"+leftExtendOptions()+"</select>":"无需延长")+"</td>"+
								"<td><input type='hidden' name='assets["+i+"].content' value=''/><a name='asset_setting_a' href='javascript:void(0)'>补充资产履历</a></td></tr>";
						}
						
					}
					$('#assets_loading').hide();
					$('#assetTable tbody').html(html);
					
					$('a[name=asset_setting_a]').click(function(){
						settingContentObj = $(this).prev();
						$("#asset_content_zone").dialog("open");
					})
				},
				"json"
			).error(function() { alert("服务器连接失败，请稍后再试!"); });
	}
	
	function leftExtendOptions(){
		var html = "";
		for(var i=0;i <= 10;i++){
			html += "<option value='"+i+"'>" + i + "</option>";
		};
		return html;
	}
	
	$(function(){
		loadDeptLeader();
		
		$('#startDate').datepicker({
			//inline: true								
			"changeYear":true,
			"changeMonth":true,
			"showButtonPanel":true,	
			"closeText":'清除',	
			"currentText":'startDate'//仅作为“清除”按钮的判断条件						
		});
		$('#finishDate').datepicker({
			//inline: true								
			"changeYear":true,
			"changeMonth":true,
			"showButtonPanel":true,	
			"closeText":'清除',	
			"currentText":'finishDate'//仅作为“清除”按钮的判断条件						
		});		
		//datepicker的“清除”功能
	    $(".ui-datepicker-close").live("click", function (){              
	      if($(this).parent("div").children("button:eq(0)").text()=="startDate") $("#startDate").val("");                      
	      if($(this).parent("div").children("button:eq(0)").text()=="finishDate") $("#finishDate").val("");                      
		   
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
				options.url = "<%=path %>/project-discardMain/register.action";
				options.submitZone = "formUpdate";
				submitZoneControl(options.submitZone,true);
				$("#"+options.submitZone+"_loading").show();
				clearError();
			
				$("#"+options.submitZone).ajaxSubmit(options);
				return false;
			}
			
		});
		
		$('#assetImport').click(function(){
			var dispatchNo = $('#dispatchNo').val();
			if(dispatchNo == ''){
				alert('请先输入项目名称！');
				return;
			}
			loadAssetsByDispatchNo(dispatchNo);
		});
		
		$("#asset_content_zone").dialog({
			modal: true,
			autoOpen: false,
			width: 400,
			height: 240,
			zIndex: 9999,	
			open : function(){
				$('#assetContent').val(settingContentObj.val());
			},
			close: function(event, ui) {}
		});
		
		$('#assetContentSubmit').click(function(){
			settingContentObj.val($('#assetContent').val());
			$("#asset_content_zone").dialog("close");
		});
		
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
				$.ajax({
			        url: "<%=projectCancelsQueryUrl%>",
			        dataType: "jsonp",
			        jsonp: "callback",
			        data: {projectName:request.term},
			        success: function (data) {
						 cacheData = $.map( data.projects, function( item ) {
								return {
									label: 		item.projectName,
									value: 		item.projectName,
									id: 		item.id,
									projectNo: 	item.projectNo,
									dispatchNo: 	item.dispatchNo,
									projectMoneysource :		item.projectMoneysource,
									approvalBudget:		item.projectBudgetAll,
									finishPrice:		item.settlementPrice,
									payType:		item.payType
								};
							});
							cache[ term ] = cacheData;
							response(cacheData);
			        }
			    })
				 			   <%--lastXhr = $.post(
						'<%=path%>/project-discardUtil/getProjectInfo.action?random='+Math.random(),
						{
							maxrows: 5,
							projectName: request.term
						},
						function(data, textStatus, jqXHR){
							 cacheData = $.map( data.projects, function( item ) {
									return {
										label: 		item.projectName,
										value: 		item.projectName,
										id: 		item.id,
										dispatchNo: 	item.dispatchNo,
										projectMoneysource :		item.projectMoneysource,
										approvalBudget:		item.projectBudgetAll,
										finishPrice:		item.settlementPrice
									};
								});
								cache[ term ] = cacheData;
								if ( jqXHR === lastXhr ) {
									response(cacheData);
								}
						},
						"json"
					) --%>
			},  
	        minLength: 1,  
	        search: function() {  
	        	$("#projectId").val("");
	        	$("#projectNo").val("");
	            $("#dispatchNo").val("");
	            $("#moneySource").val("");
	            $("#costType").val("");
	            $("#approvalBudget").val("");
	            $("#finishPrice").val("");
	            saveStatus = false;  
	        },  
	        select: function( event, ui ) {  
	        	$("#projectId").val(ui.item.id);
	            $("#projectName").val(ui.item.label);
	            $("#projectNo").val(ui.item.projectNo);
	            $("#dispatchNo").val(ui.item.dispatchNo);
	            $("#approvalBudget").val(ui.item.approvalBudget);
	            $("#finishPrice").val(ui.item.finishPrice);
	            var costType = ui.item.payType=='1'?'资本性':(ui.item.payType=='2'?'费用性':'');
	            $("#costType").val(costType);
	            var moneySourceMap = eval('('+ui.item.projectMoneysource+')');
	            if(moneySourceMap && moneySourceMap.moneySource && $.isArray(moneySourceMap.moneySource)){
	            	var html = '';
	            	$.each(moneySourceMap.moneySource,function(n,obj){
	            		html += '<br>' + obj.unitName + ' ' + obj.lineName;
	            	})
	            	if(html.length > 0){
	            		html = html.substr(4);
	            	}
	            	$("#moneySource").val(html);
	            	$("#moneysource_setting_td").html(html);	
	            }
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
	        	$("#projectNo").val("");
	            $("#dispatchNo").val("");
	            $("#moneySource").val("");
	            $("#costType").val("");
	            $("#approvalBudget").val("");
	            $("#finishPrice").val("");
                return false;  
	        },  
	        change : function(){  
	            if(!saveStatus){  
		        	$("#projectId").val("");
		        	$("#projectNo").val("");
		            $("#dispatchNo").val("");
		            $("#moneySource").val("");
		            $("#costType").val("");
		            $("#approvalBudget").val("");
		            $("#finishPrice").val("");
	            }  
	        }  
	        });  
		
	})	;
	
	
	
	</script>
	
</head>

<body class="Flow">
<form action="" name="formUpdate" id="formUpdate" method="post">
<s:include value="%{#request.getContextPath()}/project/process/discard/assetModify.jsp"></s:include>
	<div class="f_bg">
     
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;">上海申通地铁集团有限公司</b><br><b>项目销项单</b></h1>
                        	
                        	
							<div class="mb10">
	                            <input type="hidden" name="chiefPerson" value="<%=UltimusConstants.PROJECT_DISCARD_DICTIONARY_CODE%>"/>
								<input type="hidden" name="steplabel" value="<%=DiscardMainStepConstants.STEPNAME_REGISTER%>"/>
								<input type="hidden" name="projectId" id="projectId" value=""/>
								<input type="hidden" name="projectNo" id="projectNo" value=""/>
								<input type="hidden" name="moneySource" id="moneySource" value=""/>
								
								<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									
								  	<tbody>
									
									<tr class="content7">
	                                    <td class="lableTd t_v" width="15%">项目名称：</td>
	                            	    <td colspan="3"><input type="text" class="input_large" style="width:90%;" name="projectName" id="projectName"/>
	                            	 	<font class="redMark">*&nbsp;</font>
	                            	 	</td>
	                                </tr>
									<tr class="content7">
 	                            	    <td class="lableTd t_v" width="15%">立项批文号：</td>
	                            	    <td colspan="3"><input type="text" class="input_large" name="dispatchNo" id="dispatchNo" readonly="readonly" style="border:0px;"/>
	                            	    </td>									
	                                </tr>	                                
									<tr class="content7">
	                                    <td class="lableTd t_v" width="15%">实际开工日期：</td>
	                            	    <td width="35%"><input type="text" class="input_large date" name="startDate" id="startDate" readonly="readonly"/>
	                            	 	<font class="redMark">*&nbsp;</font>
	                            	 	</td>
 	                            	    <td width="15%" class="lableTd t_v">批复概算：</td>
	                            	    <td width="35%"><input type="text" class="input_large" name="approvalBudget" id="approvalBudget" readonly="readonly" style="border:0px;"/>
	                            	    </td>
	                                </tr>	                                
									<tr class="content7">
	                                    <td class="lableTd t_v">实际竣工日期：</td>
	                            	    <td><input type="text" class="input_large date" name="finishDate" id="finishDate" readonly="readonly"/>
	                            	 	<font class="redMark">*&nbsp;</font>
	                            	 	</td>
 	                            	    <td class="lableTd t_v">竣工决算价：</td>
	                            	    <td><input type="text" class="input_large" name="finishPrice" id="finishPrice" readonly="readonly" style="border:0px;"/>
	                            	    </td>
	                                </tr>	            
									<tr class="content7">				
	                                    <td class="lableTd t_v">出资主体：</td>
	                            	    <td colspan="3" id="moneysource_setting_td">
	                            	 	</td>
	                                </tr>		            
									<tr class="content7">				
	                                    <td class="lableTd t_v">成本属性：</td>
	                            	    <td colspan="3">
	                            	    	<input type="text" class="input_large" name="costType" id="costType" readonly="readonly" style="border:0px;"/>
	                            	 	</td>
	                                </tr>		                                                          
	                          	   <tr class="content7">
	                          	   		<td colspan="1" class="lableTd t_v">&nbsp;</td>
	                                    <td colspan="3">
	                                    	 提醒：“<font class="redMark">*&nbsp;</font>”为必填项。
	                                    </td>                      	    
	                                </tr>
	                          	   <tr class="content7">
	                                    <td colspan="4">
	                                    	<input type="button" id="assetImport" value="待审核资产数据导入"/>
											<div id="assets_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
										      <p style="width:auto;display:inline;"><img src="<%=path%>/project/css/default/images/loading.gif" style="display:inline;"/>
										      <b style="color:green;display:inline;">&nbsp;正在加载</b></p>
										    </div>	                                    	
	                                    </td>                      	    
	                                </tr>	            
                                  	<tr>
                                  		<td colspan="4">
                                  			<div style="width:705px;overflow:scroll;">
											<table id="assetTable" width="1200px;">
											<thead>
					                        <tr>
					                            <td style="width: 10%;">资产编码</td>
					                            <td style="width: 15%;">资产名称</td>
					                            <td style="width: 10%;">资产标识</td>
					                            <td style="width: 10%;">资产项目属性</td>
					                            <td style="width: 5%;">原值</td>
					                            <!-- <td style="width: 10%;">成本属性</td> -->
					                            <td style="width: 5%;">当前净值</td>
					                            <td style="width: 10%;">入账原值/费用</td>
					                            <td style="width: 8%;">剩余使用年限</td>
					                            <td style="width: 8%;">是否需延长年限</td>
					                            <td style="width: 8%;">操作</td>
					                        </tr>											
											</thead>
											<tbody></tbody>
					                        </table>
					                        </div>
                                  		</td>
                                  	</tr>      	                                
                                  	<tr class="content7">
	                          	     	<td class="lableTd t_v">相关附件：</td>
	                                    <td colspan="3">
	                                    	<input type="hidden" name="attach" id="attach"/>
										  	<%-- --%>
										  	<iframe style="display:inline;" scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="590" id="attachFrame" name="attachFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=&userName=<s:property value="#session.user_name"/>&loginName=<s:property value="#session.login_name"/>&procType=edit&targetType=frame&type=1"></iframe>
										<font class="redMark">*&nbsp;</font>
										</td>
	                          	    </tr>	     
                                  	<tr class="content7">
	                                    <td class="lableTd t_v">项目销项申请人：</td>
	                                    <td class="content7">
											${sessionScope.user_name}                           
	                                    </td>
	                                    <td class="lableTd t_v">申请人联系电话：</td>
	                                    <td class="content7">
	                                    <input name="operatorMobile" type="text" class="input_large" id="operatorMobile" maxlength="20" />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>	        
									<tr class="content7">
	                                    <td class="lableTd t_v">申报部门领导：</td>
	                            	    <td colspan="3">
						                <select id="deptLeader" name="deptLeader">
											
										</select>	                            	    
	                            	 	<font class="redMark">*&nbsp;</font>
	                            	 	</td>
	                                </tr>	                                  	
									</tbody>
                          	  	</table>
                        	</div>
	                        <%-- --%>
	                        
							<div class="mb10 t_c">
								<input type="button" id="formSubmit" value="提交"/>&nbsp;
								<div id="formUpdate_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
							      <p style="width:auto;display:inline;"><img src="<%=path%>/project/css/default/images/loading.gif" style="display:inline;"/>
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