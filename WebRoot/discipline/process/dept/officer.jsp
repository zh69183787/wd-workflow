<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.discipline.workflow.process.dept.constants.*"%>
<%@page import="com.wonders.util.*"%>
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
			options.url = "<%=path %>/discipline-deptSub/deal.action";
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

	$(choice1).click(function(){
		elementEnable("choiceZone1");
		elementDisable("choiceZone2");
		$(choice2).attr("disabled",false);
		$("#choiceZone2 textarea").val("");
		
	});
	$(choice2).click(function(){
		elementEnable("choiceZone2");
		elementDisable("choiceZone1");
		$(choice1).attr("disabled",false);
		$("#choiceZone1 textarea").val("");
	});
	
	$(choice1).click();
	
});
</script>
<div id="handle_zone" style="display:none;" title="部门业务人员处理">
	<div class="con">
        
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<!--<tr><td class="td_1" >业务栏</td></tr>-->
			<tr height="30">
				<td class="td_1"><input type="radio" checked="checked" name="choice" value="<%=DeptSubConstants.CHOICE_DEAL_NO_SUGGEST %>"/></td>
	  			<td style="width:99%;">本人没有意见 </td>
	  		</tr>
	  		<tr>
	  			<td colspan=2><table id="suggest1" width="100%" border="0" cellspacing="0" cellpadding="0" border=1><tbody>
			  		<tr>
			  			<td class="td_1">&nbsp;</td>
			        	<td textareaId="suggest1">
			        		<span style="display:inline;">备注：<font style="color:red;display:inline;">*</font></span>
			        		
				        	<span class="fr" style="display:inline;">
				        	<a name="suggest_attach" class="suggest_attach" target="#">
							<input type="hidden" name="attachId" id="attachId_<%=DeptSubConstants.CHOICE_DEAL_NO_SUGGEST %>" value=""/>
				        	上传意见附件(<span  style="display:inline;" id="fjcount_<%=DeptSubConstants.CHOICE_DEAL_NO_SUGGEST %>" class="fjcount">0</span>)</a>
				        	<!--
				        	<a class="previewSuggest">意见预览</a></span>
				        	-->
			        	</td>
			        </tr>
			        <tr>
			        	<td class="td_1">&nbsp;</td>
			         	<td><textarea id="suggest" name="suggest" rows="5"></textarea></td>
			       	</tr>
				</tbody></table></td>
           	</tr>
		</table>
		<br>
		<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">

	  		<tr height="30">
	  			<td class="td_1"><input type="radio" name="choice" value="<%=DeptSubConstants.CHOICE_DEAL_HAS_SUGGEST %>"/></td>
	  			<td style="width:99%;">本人有意见</td>
	  		</tr>
	  		
	  		
	  		<tr>
	  			<td colspan=2><table id="suggest2" width="100%" border="0" cellspacing="0" cellpadding="0"><tbody>
			  		<tr>
			  			<td class="td_1">&nbsp;</td>
		                <td textareaId="suggest2">
		                
		                	意见：<font style="color: red;display:inline;">*</font>
		                	
		                	<span class="fr" style="display:inline;">
			                	<a name="suggest_attach" class="suggest_attach" target="#">
								<input type="hidden" name="attachId" id="attachId_<%=DeptSubConstants.CHOICE_DEAL_HAS_SUGGEST %>" value=""/>
			                	上传意见附件(<span style="display:inline;" id="fjcount_<%=DeptSubConstants.CHOICE_DEAL_HAS_SUGGEST %>" class="fjcount">0</span>)</a>
			                	<!--<a class="previewSuggest">意见预览</a>-->
		                	</span>
		                	
		                </td>
		            </tr>
		  			<tr>
		  				<td class="td_1">&nbsp;</td>
		               	<td><textarea id="suggest" name="suggest" rows="5"></textarea>
		               	</td>
		            </tr>
            	</tbody></table></td>
           	</tr>
            
		</table>
		
		
	</div>
	<br>
	<div id="handle_submit_zone"  class="button t_c">
		<input id="handleSubmit" type="button" value="提交">&nbsp;
		<input id="handleClose" type="button" value="关闭">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path%>/discipline/css/default/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>


    