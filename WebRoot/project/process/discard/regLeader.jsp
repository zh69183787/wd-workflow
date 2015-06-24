<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.project.workflow.process.discard.constants.*"%>

<% 
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
String path = request.getContextPath();
%>
<script>
$(function(){
	$("#handleSubmit").click(function(){
		if(confirm("确认提交吗？")){
			$(this).attr("disabled",true);
			options.formId = "formUpdate";
			options.url = "<%=path %>/project-discardMain/deptLeader.action";
			options.submitZone = "handle_zone";
			options.handleZone = "handle_submit_zone";
			handleZoneControl(options.handleZone,true);
			$("#"+options.submitZone+"_loading").show();
			clearError();
		
			$("#formUpdate").ajaxSubmit(options);
			return false;
		}
		
		//$(this).removeAttr("disabled");
		
	});
	
	var choices = $("input[name='choice']");
	var choice1 = $(choices).get(0);
	var choice2 = $(choices).get(1);
	//var choice3 = $(choices).get(2);
	$(choice1).click(function(){
		$("#suggest").val("已阅。");
	});
	
	$(choice2).click(function(){
		$("#suggest").val("");
	});
	
/* 	$(choice3).click(function(){
		$("#suggest").val("取消申报。");
	}); */
});
</script>
<div id="handle_zone" style="display:none;" title="<%=steplabel%>">
	
	<div class="con">
     	
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" checked="checked" name="choice" value="<%=DiscardMainConstants.CHOICE_DEPT_LEADER_REGISTER_SUBMIT%>"/>
				</td>
				<td width="93%">提交投资部初审</td>
			</tr>
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=DiscardMainConstants.CHOICE_DEPT_LEADER_REGISTER_BACK%>"/>
				</td>
				<td>返回修改</td>
			</tr>
<%-- 			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=DiscardMainConstants.CHOICE_DEPT_LEADER_REGISTER_CANCEL%>"/>
				</td>
				<td>取消申报</td>
			</tr>	 --%>		
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
		                	<span class="fr" style="display:inline;"><a class="pdiscardSuggest">意见预览</a></span>
		                	-->
		                	</td>
		              	</tr>
		              	<tr>
		              		<td class="td_1">&nbsp;</td>
		                	<td><textarea id="suggest" name="suggest" rows="5">已阅。</textarea></td>
		              	</tr>
	              	</table>
           		</td>
           	</tr>
		</table>
	</div>
	<br>
	<div id="handle_submit_zone" class="button t_c">
		<input id="handleSubmit" type="button" value="提交">&nbsp;
		<input id="handleClose" type="button" value="关闭">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path%>/project/css/default/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>


    