<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.contract.workflow.process.dept.constants.*"%>
<%@page import="com.wonders.util.*"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<% 
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
String path = request.getContextPath();
%>

<script>
function selectDeptUser(zoneId){
	var url = treepath+"/organTree/deptUserTree.jsp?zoneId="+zoneId+"&random="+Math.random();
	window.open(url);
}
function selectDeptLeader(zoneId){
	var url = treepath+"/organTree/deptLeaderTree.jsp?zoneId="+zoneId+"&random="+Math.random();
	window.open(url);
}

function createSuggest(personNames,leaderNames,textarea){
	//if($(textarea).val()==""){
		var str = "";
		
		if(personNames!=""){
			str+='请' + personNames + '提出意见';
			if(leaderNames!=""){
				str+=",";
			}else{
				str+="。";
			}
		}
		if(leaderNames!=""){
			
			str+='请' + leaderNames + '阅示。';
		}
		$(textarea).val(str);
	//}
	
	return false;
}

function createSuggestText(text,textarea){
	if($(textarea).val()==""){
		$(textarea).val(text);
	}
	return false;
}

$(function(){
	$("#handleSubmit").click(function(){
		if(confirm("确认提交吗？")){
			$(this).attr("disabled",true);
			options.formId = "formUpdate";
			options.url = "<%=path %>/contract-deptSub/leaderDeal.action";
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
	
	
	
	$(".chooseLeader").click(function(){
		//var td = $(this).parent("td");
		//selectDeptLeader(td.find("font").attr("id"));
		selectDeptLeader("dealLeaderTreeZone");
		return false;
	});
	
	$(".clearLeader").click(function(){
		var td = $(this).parent("td");
		td.find("input[type=hidden],:text").each(function(i,n){
			$(n).val("");
		});
	});
	
	$(".choosePerson").click(function(){
		//var td = $(this).parent("td");
		//selectDeptUser(td.find("font").attr("id"));
		selectDeptUser("dealPersonTreeZone");
		
		return false;
	});
	
	$(".clearPerson").click(function(){
		var td = $(this).parent("td");
		td.find("input[type=hidden],:text").each(function(i,n){
			$(n).val("");
		});
	});
	
	
	var choices = $("input[name='choice']");
	var choices2 = $("input[name='choice2']");
	var choice1 = $(choices).get(0);
	var choice2 = $(choices).get(1);
	var choice3 = $(choices).get(2);
	var choice3_1 = $(choices2).get(0);
	var choice3_2 = $(choices2).get(1);
	$(choice1).click(function(){
		elementEnable("choiceZone1");
		elementDisable("choiceZone2");
		elementDisable("choiceZone3");
		$(choice2).attr("disabled",false);
		$(choice3).attr("disabled",false);
		$("#choiceZone1 textarea").val("审核通过。");
		$("#choiceZone2 textarea").val("");
		$("#choiceZone3 textarea").val("");
	});
	$(choice2).click(function(){
		elementEnable("choiceZone2");
		elementDisable("choiceZone1");
		elementDisable("choiceZone3");
		$(choice1).attr("disabled",false);
		$(choice3).attr("disabled",false);
		$("#choiceZone1 textarea").val("");
		$("#choiceZone2 textarea").val("审核不通过。");
		$("#choiceZone3 textarea").val("");
	});
	
	$(choice3).click(function(){
		elementEnable("choiceZone3");
		elementDisable("choiceZone1");
		elementDisable("choiceZone2");
		$(choice1).attr("disabled",false);
		$(choice2).attr("disabled",false);
		
		if($(choice3_1).attr("checked")) $(choice3_1).click();
		if($(choice3_2).attr("checked")) $(choice3_2).click();
		
		$("#choiceZone1 textarea").val("");
		$("#choiceZone2 textarea").val("");
	});
	
	$(choice3_1).click(function(){
		elementEnable("choiceZone3_1");
		elementDisable("choiceZone3_2");
		//$(":button[name=createSuggest]").hide();
		$(choice3_2).attr("disabled",false);
	});
	$(choice3_2).click(function(){
		elementDisable("choiceZone3_1");
		elementEnable("choiceZone3_2");

		$(choice3_1).attr("disabled",false);
		//$(":button[name=createSuggest]").show();
	});
	
	$(choice1).click();
	
	$(":button[name=createSuggest]").click(function(){
		var textareaId = $(this).parent("span").parent("td").attr("textareaId");
		var peopleId = $(this).parent("span").parent("td").attr("peopleId");

		var textareaZone = $("#"+textareaId);
		var peopleZone = $("#"+peopleId);
		
		var textarea = $(textareaZone).find("textarea");
		var personNames = $(peopleZone).find(":text[name='dealPersonNames']");
		var leaderNames = $(peopleZone).find(":text[name='dealLeaderNames']");

		if($("input[name='choice']:checked").val()=='<%=DeptSubConstants.CHOICE_LEADER_CONTINUE %>'&&
				$("input[name='choice2']:checked").val()=='<%=DeptSubConstants.CHOICE_LEADER_CONTINUE_DEAL %>'){
			createSuggestText("需本部门其他人员继续处理，转业务经办人。",textarea);
		}
		
		if($("input[name='choice']:checked").val()=='<%=DeptSubConstants.CHOICE_LEADER_CONTINUE %>'&&
				$("input[name='choice2']:checked").val()=='<%=DeptSubConstants.CHOICE_LEADER_CONTINUE_CHOICE_PERSON %>'){
			createSuggest($(personNames).val(),$(leaderNames).val(),textarea);
		}

		return false;
	});
})

</script>

<div id="handle_zone" style="display:none;" title="部门领导审核">
<font id="dealPersonTreeZone" class="dealPersonTreeZone" 
	  				root='<s:property value="params.userInfo.deptId"/>' 
					checkNode="dealPersonStr" dealPersonLoginName="dealPersonStr"
					dealPersonName="dealPersonNames"
					></font>
<font id="dealLeaderTreeZone" class="dealLeaderTreeZone" 
	  				root='<s:property value="params.userInfo.deptId"/>' 
					checkNode="dealLeaderStr" dealLeaderLoginName="dealLeaderStr"
					dealLeaderName="dealLeaderNames"
					treeType="radio"
					></font>
      <div class="con">
        	<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
        	  	<tr height="30">
	  				<td class="td_1">
	  					<input type="radio" name="choice" checked="checked" value="<%=DeptSubConstants.CHOICE_LEADER_PASS %>"/>
	  				</td>
	  				<td style="width:99%;">审核通过(形成部门意见)<!-- 部门意见：同意 --></td>
  				</tr>
  				
  				<tr>
	  				<td colspan=2><table id="suggest1" width="100%" border="0" cellspacing="0" cellpadding="0"><tbody>
		  				<tr>
		                	<td>
		                		备注：
			                	<span class="fr" style="display:inline;">
				                	<a name="suggest_attach" class="suggest_attach" target="#">
									<input type="hidden" name="attachId" id="attachId_<%=DeptSubConstants.CHOICE_LEADER_PASS %>" value=""/>
				                	上传意见附件(<span style="display:inline;" id="fjcount_<%=DeptSubConstants.CHOICE_LEADER_PASS %>" class="fjcount">0</span>)</a>
				                	<!--<a class="previewSuggest">备注预览</a>-->
			                	</span>
		                		
		                	</td>
		              	</tr>
		              	<tr>
		                	<td><textarea id="suggest" name="suggest" rows="3"></textarea></td>
		              	</tr>
	              	</tbody></table>
	              	</td>
              	</tr>
              	
       	    </table>
       	    <br>
       	    <table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
	  			<tr height="30">
	  				<td class="td_1">
	  					<input type="radio" name="choice" value="<%=DeptSubConstants.CHOICE_LEADER_FAILED %>"/>
	  				</td>
	  				<td style="width:99%;">审核不通过,有新意见(形成部门意见)<!-- 部门意见：修改意见 --> </td>
	  			</tr>
	  			
	  			<tr>
  					<td colspan=2>
	  					<table id="suggest2" width="100%" border="0" cellspacing="0" cellpadding="0">
		  					<tbody>
				  				<tr>
				                	<td>
					                	
					                	意见：<font style="color: red;display:inline;">*</font>
					                	
					                	<span  class="fr" style="display:inline;">
					                	<a name="suggest_attach" class="suggest_attach" target="#">
										<input type="hidden" name="attachId" id="attachId_<%=DeptSubConstants.CHOICE_LEADER_FAILED %>" value=""/>
					                	上传意见附件(<span style="display:inline;" id="fjcount_<%=DeptSubConstants.CHOICE_LEADER_FAILED %>" class="fjcount">0</span>)</a>
					                	<!--<a class="previewSuggest">意见预览</a>-->
					                	</span>
					                	
				                	</td>
				              	</tr>
					  			<tr>
				                	<td><textarea id="suggest" name="suggest" rows="3"></textarea></td>
				              	</tr>
			              	</tbody>
		              	</table>
	              	</td>
				</tr>
			</table>
              	<br>
			<table id="choiceZone3" width="100%" border="0" cellspacing="0" cellpadding="0">
		  		<tr>
		  			<td class="td_1">
		  				<input type="radio" name="choice" value="<%=DeptSubConstants.CHOICE_LEADER_CONTINUE %>"/>
		  			</td>
		  			<td style="width:99%;">还需要本部门业务人员继续处理 </td>
		  		</tr>
		  		<tr>
	  			<td colspan=2>
			  		<table id="suggest3" width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
				  			<td class="td_1" style="text-align:right;">&nbsp;</td>
		                	<td textareaId="suggest3" peopleId="choiceZone3">
		                	<span class="fl">
		                	意见：<font style="color:red;display:inline;">*</font>
		                	<input type="button" name="createSuggest" value="生成意见"></span>
		                	<span  class="fr" style="display:inline;">
		                	<a name="suggest_attach" class="suggest_attach" target="#">
							<input type="hidden" name="attachId" id="attachId_<%=DeptSubConstants.CHOICE_LEADER_CONTINUE %>" value=""/>
		                	上传意见附件(<span style="display:inline;" id="fjcount_<%=DeptSubConstants.CHOICE_LEADER_CONTINUE %>" class="fjcount">0</span>)</a>
		                	<!--<a class="previewSuggest">意见预览</a>-->
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
		  		
		  		<tr>
		  			<td></td>
			  		<td>
				  		<table id="choiceZone3_1" width="100%" border="0" cellspacing="0" cellpadding="0">
					  		<tr>
					  			<td class="td_1">
					  				<input type="radio" checked="checked" name="choice2" value="<%=DeptSubConstants.CHOICE_LEADER_CONTINUE_DEAL %>"/>
					  			</td>
					  			<td style="width:99%;">需本部门其他人员继续处理,转业务经办人<!-- 注释掉 --> </td>
					  		</tr>
					  		</table>
					  		
					  		<table id="choiceZone3_2" width="100%" border="0" cellspacing="0" cellpadding="0">
					  		<tr>
					  			<td class="td_1">
					  				<input type="radio" name="choice2" value="<%=DeptSubConstants.CHOICE_LEADER_CONTINUE_CHOICE_PERSON %>"/>
					  			</td>
					  			<td style="width:99%;">直接选择处理人员</td>
					  		</tr>
					  		<tr>
					  			<td></td>
					  			<td>
					  				<table width="100%" border="0" cellspacing="0" cellpadding="0">
								  		<tr height="30">
								  			<td>
								  				选择人员：
								  				<input type="text" id="dealPersonNames" name="dealPersonNames" value="" readonly class="inputLine" style="width:250px;height:22px"/>
								  				<input type="hidden" id="dealPersonStr" name="dealPersonStr" value="">
								  				<input type="button" name="choosePerson" value="..." class="btn choosePerson">
								  				<input type="button" name="clearPerson" value="清除" class="btn clearPerson"/>
								  				
											</td>
						  				</tr>
								  		<tr height="30">
								  			<td> 
												选择领导：
												<input type="text" id="dealLeaderNames" name="dealLeaderNames" value="" readonly class="inputLine" value="" style="width:250px;height:22px"/>
								  				<input type="hidden" id="dealLeaderStr" name="dealLeaderStr" value="">
								  				<input type="button" name="chooseLeader" value="..." class="btn chooseLeader"/>
								  				<input type="button" name="clearLeader" value="清除" class="btn clearLeader" />
								  				
											</td>
								  		</tr>
								  		<tr><td><a href="" onclick="return false;"><font style="color:red;display:inline;">注：人员和领导不能同时为空！</font></a></td></tr>
								  	</table>
					  			</td>
					  		</tr>
				  		</table>
			  		</td>
		  		</tr>
		  		<!--  
		  		<tr><td>操作栏</td></tr>
	            <tr><td><textarea id="operate_msg" name="operate_msg" rows="2" readonly>提交至主管部门经办人汇总。</textarea></td></tr>
	       	  	-->
			</table>
		</div>
		<br>
		<div id="handle_submit_zone" class="button t_c">
			<input id="handleSubmit" type="button" value="提交">&nbsp;
			<input id="handleClose" type="button" value="关闭">
			
			<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
		      <p style="width:auto;display:inline;"><img src="<%=path%>/contract/css/default/images/loading.gif" style="display:inline;"/>
		      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
		    </div>
		</div>
</div>


    