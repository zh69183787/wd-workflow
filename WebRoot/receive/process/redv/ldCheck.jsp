<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.receive.workflow.process.redv.constants.*"%>
<% 
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
String path = request.getContextPath();
%>
<script src="<%=path %>/receive/js/contextpath.js"></script>
<script src="<%=path %>/receive/js/attach.js"></script>
<script>
$(function(){
	
	$("#handleSubmit").on("click", function(){
		if(confirm("确认提交吗？")){
			$(this).attr("disabled",true);
			options.formId = "formUpdate";
			options.url = "<%=path %>/receive-redvMain/leaderCheck.action";
			options.submitZone = "handle_zone";
			options.handleZone = "handle_submit_zone";
			handleZoneControl(options.handleZone,true);
			$("#"+options.submitZone+"_loading").show();
			clearError();
			//$("#swId").val($("#swId").val()+"-"+$("#swSerial").val())
			$("#formUpdate").ajaxSubmit(options);
			//$(this).removeAttr("disabled");
			
			return false;
		}
	});
	
	$("#handleSubmit2").on("click", function(){
		if(confirm("确认提交吗？")){
			$(this).attr("disabled",true);
			options.formId = "formUpdate";
			options.url = "<%=path %>/receive-redvMain/leaderCheck.action";
			options.submitZone = "handle_zone";
			options.handleZone = "handle_submit_zone";
			handleZoneControl(options.handleZone,true);
			$("#"+options.submitZone+"_loading").show();
			clearError();
			var choices = $("input[name='choice']");
			var choice1 = $(choices).get(0);
			var choice2 = $(choices).get(1);
			var choice3 = $(choices).get(2);
			$(choice1).attr("checked",false);
			$(choice2).attr("checked",false);
			$(choice3).attr("checked",true);
			$("#formUpdate").ajaxSubmit(options);
			//$(this).removeAttr("disabled");
			return false;
		}
	});
	
	var choices = $("input[name='choice']");
	var choice1 = $(choices).get(0);
	$(choice1).click();
});
</script>
<div id="handle_zone" style="display:none;" title="呈报部门领导审核">
	
	<div class="con">
     	
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" checked="checked" name="choice" value="<%=RedvMainConstants.CHOICE_LEADER_SUBMIT_SIMULATE%>"/>
				</td>
				<td width="93%">已阅</td>
			</tr>
	  		<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=RedvMainConstants.CHOICE_LEADER_REGISTER_MODIFY%>"/>
				</td>
				<td>返回呈报人修改</td>
			</tr>
			<tr height="30" style="display:none;">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=RedvMainConstants.CHOICE_LEADER_CANCEL_REDV%>"/>
				</td>
				<td>取消申报</td>
			</tr>
	  		<tr>
	  			<td colspan=2>
			  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
				  			<td class="td_1" style="text-align:right;">&nbsp;</td>
		                	<td textareaId="suggest">
		                	<span class="fl">
		                	意见：<font style="color:red;display:inline;">*</font>
		                	</span>
		                	<span class="fr" style="display:inline;">
		                	<a name="suggest_attach" class="suggest_attach" target="#">
			  				<input type="hidden" name="attachId" id="attachId" value=""/>
			  				上传意见附件(<span style="display:inline;" id="fjcount" class="fjcount">0</span>)
			  				</a>
		                	</span>
		                	<!--  
		                	<span class="fr" style="display:inline;"><a class="previewSuggest">意见预览</a></span>
		                	-->
		                	</td>
		              	</tr>
		              	<tr>
		              		<td class="td_1">&nbsp;</td>
		                	<td><textarea id="suggest" name="suggest" rows="5"></textarea></td>
		              	</tr>
	              	</table>
           		</td>
           	</tr>
		</table>
	</div>
	<br>
	<div id="handle_submit_zone" class="button t_c">
		<input id="handleSubmit" type="button" value="提交">&nbsp;
		<input id="handleSubmit2" type="button" value="取消呈报">&nbsp;
		<input id="handleClose" type="button" value="关闭">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path%>/receive/css/default/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>


    