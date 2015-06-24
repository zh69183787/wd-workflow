<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.Calendar" %>
<%@page import="com.wonders.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date" %>
<%@page import="com.wonders.util.DateUtil" %>
<%@ page import="com.wonders.project.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.project.workflow.process.discard.constants.DiscardMainStepConstants" %>
<%@page import="com.wonders.project.workflow.process.discard.constants.DiscardMainConstants" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
String thisYear = DateUtil.getCurrDate("yyyy");
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>项目销项单</title>
	<link href="<%=path%>/project/css/formalize.css" rel="stylesheet">
	<link href="<%=path%>/project/css/page.css" rel="stylesheet">
	<link href="<%=path%>/project/css/default/imgs.css" rel="stylesheet">
	<link href="<%=path%>/project/css/reset.css" rel="stylesheet">
	<link href="<%=path%>/project/css/jquery.ui.css" rel="stylesheet">
	<link type="text/css" href="<%=path%>/project/css/flick/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
	<!--[if IE 6.0]>
	<script src="js/iepng.js" type="text/javascript"></script>
	<script type="text/javascript">
	     EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
	</script>
	<![endif]-->
	<style>
	.redMark{color:red;display:inline;}
	</style>
	<script src="<%=path%>/project/js/contextpath.js"></script>
	<script src="<%=path%>/project/js/html5.js"></script>   
	<!--<script src="<%=path%>/project/js/jquery.formalize.js"></script>  -->
	<script src="<%=path%>/project/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path%>/project/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path%>/project/js/attach.js"></script>
	<script src="<%=path%>/project/js/jquery-form/jquery.form.min.js"></script>
	<script src="<%=path%>/project/js/jquery-ui/jquery-ui.js"></script>
	<script src="<%=path%>/project/js/jquery-ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="<%=path%>/project/js/common/elementControl.js"></script>
	<script src="<%=path%>/project/js/jquery-qtip/jquery.qtip-1.0.0-rc3.js"></script>
	
	<script type="text/javascript">
	var settingContentObj,cntAssets = 0;
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
						submitZoneControl(options.formId,false);
						handleZoneControl(options.handleZone,false);
						window.opener=null;
						window.open("","_self");
						window.close();
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
							var seq = cntAssets + i;
							var lifeLeft = '';
							var thisYear = '<%=thisYear%>'*1;
							if($.isNumeric(assets[i].userLife) && assets[i].operateProjectAssetDate.length > 3 && $.isNumeric(assets[i].operateProjectAssetDate.substr(0,4))){
								var transferYear = assets[i].operateProjectAssetDate.substr(0,4)*1;
								lifeLeft = assets[i].userLife*1 - (thisYear-transferYear);
							}
							html +="<tr><td><input type='hidden' name='assets["+seq+"].mainId' value='${vo.id}'/><input type='hidden' name='assets["+seq+"].removed' value='0'/>"+
								"<input type='hidden' name='assets["+seq+"].assetRecordId' value='"+assets[i].id+"'/>"+
								assets[i].assetNo+"<input type='hidden' name='assets["+seq+"].assetNo' value='"+assets[i].assetNo+"'/></td><td>"+
								assets[i].assetName+"<input type='hidden' name='assets["+seq+"].assetName' value='"+assets[i].assetName+"'/></td><td>"+
								assets[i].completeTransAssetType+"<input type='hidden' name='assets["+seq+"].operateProjectAsset' value='"+assets[i].completeTransAssetType+"'/></td><td>"+
								"<select name='assets["+seq+"].assetType'><option selected>"+assets[i].assetType+"</option><option>计划报废</option></select>"+
								"<input type='hidden' name='assets["+seq+"].assetTypeOriginal' value='"+assets[i].assetType+"'/></td><td>"+
								assets[i].originalValue+"<input type='hidden' name='assets["+seq+"].originalValue' value='"+assets[i].originalValue+"'/></td><td>"+
								//"<select name='assets["+seq+"].costType'><option selected>资本性</option><option>费用性</option></select>"+"</td><td>"+
								assets[i].netValue+"<input type='hidden' name='assets["+seq+"].netValue' value='"+assets[i].netValue+"'/></td><td>"+
								assets[i].maintainCost+"<input type='hidden' name='assets["+seq+"].maintainCost' value='"+assets[i].maintainCost+"'/></td><td>"+
								lifeLeft+"<input type='hidden' name='assets["+seq+"].lifeLeft' value='"+lifeLeft+"'/></td><td>"+
								(assets[i].assetCodeType=="2"?
										"<select name='assets["+seq+"].lifeExtend'>"+leftExtendOptions()+"</select>":"无需延长")+"</td>"+
								"<td><input type='hidden' name='assets["+seq+"].content' value=''/><a name='asset_setting_a' href='javascript:void(0)'>补充资产履历</a></td></tr>";
						}
						
					}
					$('#assets_loading').hide();
					$('#assetTable tbody').append(html);
					
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
		
		$("#todo_handle").on("click",function(){
			$("#handle_zone").dialog("open");
		});
		
		$("#handleClose").on("click",function(){
			$("#handle_zone").dialog("close");
			return false;
		});
		
		$(".showDetail").on("click",function(){
			$(this).parent("td").next("td").find(".noleader").toggle();
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
		
		$('#assetImport').click(function(){
			if(confirm('确定要重新导入资产数据吗？')){
				$('#assetTable tbody tr').each(function(){
					var tr = $(this);
					var id = tr.find("input[name$='.id']").val();
					if(id){
						tr.hide();
						tr.find("input[name$='.removed']").val('1');
					}else{
						tr.remove();
					}
				})
				var dispatchNo = $('input[name=dispatchNo]').val();
				loadAssetsByDispatchNo(dispatchNo);
			}
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
		
		$('a[name=asset_setting_a]').click(function(){
			settingContentObj = $(this).prev();
			$("#asset_content_zone").dialog("open");
		}) 		
		
	})	;
	
	
	
	</script>
	
</head>

<body class="Flow">
<form action="" name="formUpdate" id="formUpdate" method="post">
<s:include value="%{#request.getContextPath()}/project/process/discard/assetModify.jsp"></s:include>
<s:include value="%{#request.getContextPath()}/project/process/discard/regModify.jsp"></s:include>

	<div class="f_bg">
	<%
		if(DiscardMainStepConstants.STEPNAME_RETURN_MODIFY.equals(steplabel)){
	%>
     	<s:include value="%{#request.getContextPath()}/project/process/common/todo.jsp"></s:include>
    <%} %>
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;">上海申通地铁集团有限公司</b><br><b>项目销项单</b></h1>
                        	
                        	
							<div class="mb10">
								<input type="hidden" name="pname" value="<s:property value="params.processParam['pname']"/>"/>
								<input type="hidden" name="pincident" value="<s:property value="params.processParam['pincident']"/>"/>
								<input type="hidden" name="cname" value="<s:property value="params.processParam['cname']"/>"/>
								<input type="hidden" name="cincident" value="<s:property value="params.processParam['cincident']"/>"/>
								<input type="hidden" name="steplabel" value="<s:property value="params.processParam['steplabel']"/>"/>
								<input type="hidden" name="chiefPerson" value="<s:property value="vo.chiefPerson"/>"/>
								<input type="hidden" id="taskuser" name="taskuser" value="<s:property value="params.processParam['taskuser']"/>"/>
								<input type="hidden" name="taskid" value="<s:property value="params.processParam['taskid']"/>"/>
								
								<input type="hidden" id="id" name="id" value="${vo.id}"/>
								<input type="hidden" name="projectId" value="${vo.projectId}"/>
								<input type="hidden" name="projectNo" value="${vo.projectNo}"/>
								<input type="hidden" name="projectName" value="${vo.projectName}"/>
								<input type="hidden" name="dispatchNo" value="${vo.dispatchNo}"/>
								<input type="hidden" name="moneySource" value="${vo.moneySource}"/>
								<input type="hidden" name="approvalBudget" value="${vo.approvalBudget}"/>
								<input type="hidden" name="finishPrice" value="${vo.finishPrice}"/>
								<input type="hidden" name="costType" value="${vo.costType}"/>
								
								<table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
								<tbody>
									
									<tr class="content7">
	                                    <td class="lableTd t_v" width="15%">项目名称：</td>
	                            	    <td colspan="3">${vo.projectName}</td>
	                                </tr>
									<tr class="content7">
 	                            	    <td class="lableTd t_v" width="15%">立项批文号：</td>
	                            	    <td colspan="3">${vo.dispatchNo}</td>                           	    
	                                </tr>	                                
									<tr class="content7">
	                                    <td class="lableTd t_v" width="15%">实际开工日期：</td>
	                            	    <td width="35%"><input type="text" class="input_large date" name="startDate" id="startDate" value="${vo.startDate}"/>
	                            	 	<font class="redMark">*&nbsp;</font>
	                            	 	</td>
 	                            	    <td width="15%" class="lableTd t_v">批复概算：</td>
	                            	    <td width="35%">${vo.approvalBudget}</td>
	                                </tr>	                                
									<tr class="content7">
	                                    <td class="lableTd t_v">实际竣工日期：</td>
	                            	    <td><input type="text" class="input_large date" name="finishDate" id="finishDate" value="${vo.finishDate}"/>
	                            	 	<font class="redMark">*&nbsp;</font>
	                            	 	</td>
 	                            	    <td class="lableTd t_v">竣工决算价：</td>
	                            	    <td>${vo.finishPrice}</td>
	                                </tr>	        
									<tr class="content7">
	                                    <td class="lableTd t_v">出资主体：</td>
	                            	    <td colspan="3">${vo.moneySource}</td>	                            	    
	                                </tr>	                   
									<tr class="content7">
	                                    <td class="lableTd t_v">成本属性：</td>
	                            	    <td colspan="3">${vo.costType}</td>	                            	    
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
											<tbody>
											<s:iterator value="vo.assets" var="asset" status="s">
											<tr>
												<td>
													<input type='hidden' name='assets[<s:property value="#s.index"/>].id' value='<s:property value="id"/>'/>
													<input type='hidden' name='assets[<s:property value="#s.index"/>].removed' value='<s:property value="removed"/>'/>
													<input type='hidden' name='assets[<s:property value="#s.index"/>].mainId' value='<s:property value="mainId"/>'/>
													<input type='hidden' name='assets[<s:property value="#s.index"/>].assetRecordId' value='<s:property value="assetRecordId"/>'/>
													<s:property value="assetNo"/>
													<input type='hidden' name='assets[<s:property value="#s.index"/>].assetNo' value='<s:property value="assetNo"/>'/>
													<input type='hidden' name='assets[<s:property value="#s.index"/>].assetTypeOriginal' value='<s:property value="assetTypeOriginal"/>'/>
												</td>
												<td>
													<s:property value="assetName"/>
													<input type='hidden' name='assets[<s:property value="#s.index"/>].assetName' value='<s:property value="assetName"/>'/>
												</td>
												<td>
													<s:property value="operateProjectAsset"/>
													<input type='hidden' name='assets[<s:property value="#s.index"/>].operateProjectAsset' value='<s:property value="operateProjectAsset"/>'/>
												</td>
												<td>
													<select name='assets[<s:property value="#s.index"/>].assetType'>
														<option <s:if test="assetType==assetTypeOriginal">selected</s:if>><s:property value="assetTypeOriginal"/></option>
														<option <s:if test="assetType=='计划报废'">selected</s:if>>计划报废</option>
													</select>
												</td>
												<td>
													<s:property value="originalValue"/>
													<input type='hidden' name='assets[<s:property value="#s.index"/>].originalValue' value='<s:property value="originalValue"/>'/>
												</td>
<%-- 											<td>
													<select name='assets[<s:property value="#s.index"/>].costType'>
														<option <s:if test="costType=='资本性'">selected</s:if>>资本性</option>
														<option <s:if test="costType=='费用性'">selected</s:if>>费用性</option>
													</select>
												</td> --%>
												<td>
													<s:property value="netValue"/>
													<input type='hidden' name='assets[<s:property value="#s.index"/>].netValue' value='<s:property value="netValue"/>'/>
												</td>
												<td>
													<s:property value="maintainCost"/>
													<input type='hidden' name='assets[<s:property value="#s.index"/>].maintainCost' value='<s:property value="maintainCost"/>'/>
												</td>
												<td><s:property value="lifeLeft"/></td>
												<td>
													<s:if test="lifeExtend!=null && lifeExtend!=''">
													<select name='assets[<s:property value="#s.index"/>].lifeExtend'>
														<s:iterator value="new int[10]" status="i"> 
															<option <s:if test="lifeExtend==#i.index">selected</s:if>><s:property value="#i.index"/></option> 
														</s:iterator>										
													</select>		
													</s:if>		
													<s:else>
														无需延长
													</s:else>								
												</td>							
												<td>
													<input type='hidden' name='assets[<s:property value="#s.index"/>].content' value='<s:property value="content"/>'/>
													<a name="asset_setting_a" href="javascript:void(0)">补充资产履历</a>
												</td>					
												<script>
													cntAssets++;
												</script>														
											</tr>
											</s:iterator>
											</tbody>
					                        </table>
					                        </div>
                                  		</td>
                                  	</tr>                                 
                                  	<tr class="content7">
	                          	     	<td class="lableTd t_v">相关附件：</td>
	                                    <td colspan="3">
	                                    	<input type="hidden" name="attach" id="attach" value="<s:property value="vo.attach"/>"/>
										  	<%-- --%>
										  	<iframe style="display:inline;" scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="590" id="attachFrame" name="attachFrame" 
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=attach&fileGroupName=attachGroup&fileGroupId=<s:property value="vo.attach"/>&userName=<s:property value="#session.user_name"/>&loginName=<s:property value="#session.login_name"/>&procType=edit&targetType=frame&type=1"></iframe>
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
	                                    <input name="operatorMobile" type="text" class="input_large" id="operatorMobile" value="${vo.operatorMobile}" />
	                                    <font class="redMark">*&nbsp;</font>
	                                    </td>
                                  	</tr>	        
                                  	
	                              <tr class="content7">
										<td class="lableTd t_v ">申报部门意见：</td>
										<td colspan="3">
										<s:action name="approve" namespace="/project-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'返回修改','发起部门领导'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">投资部初审意见：</td>
										<td colspan="3">
										<s:action name="approve" namespace="/project-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'投资部收发员','投资部经办人'
											}">
										</s:param>
										</s:action>
										</td>
									</tr>
									
									<tr class="content7">
										<td class="lableTd t_v ">会签部门意见：</td>
										<td colspan="3">
										<s:action name="dept" namespace="/project-approve" executeResult="true">
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
										<td class="lableTd t_v ">投资部办结意见：</td>
										<td colspan="3">
										<s:action name="approve" namespace="/project-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'投资部领导'
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