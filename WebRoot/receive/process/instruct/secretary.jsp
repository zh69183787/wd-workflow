<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.receive.workflow.process.instruct.constants.*"%>
<% 
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
String path = request.getContextPath();
%>
<script>
$(function(){
	$("#handleSubmit").click(function(){
		closeWindow();
		if(confirm("确认提交吗？")){
			$(this).attr("disabled",true);
			options.formId = "formUpdate";
			options.url = "<%=path %>/receive-instructSub/secretaryDeal.action";
			options.submitZone = "handle_zone";
			options.handleZone = "handle_submit_zone";
			handleZoneControl(options.handleZone,true);
			$("#"+options.submitZone+"_loading").show();
			clearError();
			$("#type").val("0");
			$("#formUpdate").ajaxSubmit(options);
			//$(this).removeAttr("disabled");
			return false;
		}
	});
	
	$("#handleSubmit2").click(function(){
		closeWindow();
		if(confirm("确认提交吗？")){
			$(this).attr("disabled",true);
			options.formId = "formUpdate";
			options.url = "<%=path %>/receive-instructSub/secretaryDeal.action";
			options.submitZone = "handle_zone";
			options.handleZone = "handle_submit_zone";
			handleZoneControl(options.handleZone,true);
			$("#"+options.submitZone+"_loading").show();
			clearError();
			$("#type").val("1");
			$("#formUpdate").ajaxSubmit(options);
			//$(this).removeAttr("disabled");
			return false;
		}
	});
	
	var choices = $("input[name='choice']");
	var choice1 = $(choices).get(0);
	var choice2 = $(choices).get(1);
	var choice3 = $(choices).get(2);
	$(choice1).click(function(){
		$("#handleSubmit2").show();
	});
	$(choice2).click(function(){
		$("#handleSubmit2").show();
	});
	$(choice3).click(function(){
		$("#handleSubmit2").hide();
	});
	
	
	$(choice1).click();
});

</script>
<div id="handle_zone" style="display:none;" title="批示领导秘书">	
	<input type="hidden" id="type" name="type" value="0"/>							
	<div class="con">  	
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" checked="checked" name="choice" value="<%=InstructSubConstants.CHOICE_SECRETARY_READ%>"/>
				</td>
				<td>已阅</td>
			</tr>
	  		<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=InstructSubConstants.CHOICE_SECRETARY_OTHER_SUGGEST%>"/>
				</td>
				<td>其他意见（对拟办有异议，需重新拟办）</td>
			</tr>
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=InstructSubConstants.CHOICE_SECRETARY_BACK_OFFICE%>"/>
				</td>
				<td>领导暂时不能处理，退回办公室（请在备注中注明原因）</td>
			</tr>
	  		<tr>
	  			<td colspan=2>
			  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
				  			<td class="td_1" style="text-align:right;">&nbsp;</td>
		                	<td textareaId="suggest">
		                	<span class="fl">
		                	意见：
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
	<div id="handle_submit_zone"  class="button t_c">
		<input id="handleSubmit2" type="button" value="代领导处理">&nbsp;
		<input id="handleSubmit" type="button" value="提交">&nbsp;
		<input id="handleClose" type="button" value="关闭">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path%>/receive/css/default/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>


    