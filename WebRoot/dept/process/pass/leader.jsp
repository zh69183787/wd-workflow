<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.dept.workflow.process.pass.constants.*"%>
<%@page import="com.wonders.util.*"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<% 
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
String path = request.getContextPath();
%>

<script>
function selectDeptUser(zoneId){
	var url = treepath+"/organTree/deptUsersTree.jsp?zoneId="+zoneId+"&random="+Math.random();
	window.open(url);
}

function createSuggest(personNames,textarea){
	//if($(textarea).val()==""){
		var str = "";
		
		if(personNames!=""){
			str+='请' + personNames + '提出意见。';		
		}
		
		$(textarea).val(str);
	//}
	
	return false;
}


$(function(){
	$("#handleSubmit").click(function(){
		if(confirm("确认提交吗？")){
			$(this).attr("disabled",true);
			options.formId = "formUpdate";
			<%if(PassMainStepConstants.STEPNAME_DEPT_LEADER.equals(steplabel)){%>
				options.url = "<%=path %>/dept-passMain/deptLeader.action";
			<%} else{%>
				options.url = "<%=path %>/dept-passMain/register.action";
			<%}%>
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
	var choice1 = $(choices).get(0);
	var choice2 = $(choices).get(1);
	var choice3 = $(choices).get(2);
	
	$(choice1).click(function(){
		$("#dealPersonTr").hide();
		$("#suggest").val("阅。");
		$("#createSuggest").hide();
	});
	$(choice2).click(function(){
		$("#dealPersonTr").show();
		$("#suggest").val("阅。");
		$("#createSuggest").show();
	});
	$(choice3).click(function(){
		$("#dealPersonTr").show();
		$("#suggest").val("阅。");
		$("#createSuggest").show();
	});
	
	$(choice1).click();
	
	$("#createSuggest").click(function(){
		var textareaId = $(this).parent("span").parent("td").attr("textareaId");
		var peopleId = $(this).parent("span").parent("td").attr("peopleId");

		var textareaZone = $("#"+textareaId);
		var peopleZone = $("#"+peopleId);
		
		var textarea = $(textareaZone).find("textarea");
		var personNames = $(peopleZone).find(":text[name='dealPersonNames']");
		createSuggest($(personNames).val(),textarea);
		

		return false;
	});
})

</script>

<div id="handle_zone" style="display:none;" title="<%=(steplabel=="detail"?"传阅":steplabel)%>">
<font id="dealPersonTreeZone" class="dealPersonTreeZone" 
	  				root='<s:property value="params.userInfo.deptId"/>' 
					checkNode="dealPersonStr" dealPersonLoginName="dealPersonStr"
					dealPersonName="dealPersonNames"
					></font>
      	<div class="con">
        	<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
        	  	<tr height="30">
	  				<td class="td_1">
	  					<input type="radio" name="choice" value="<%=PassMainConstants.CHOICE_FINISH_FLOW %>"/>
	  				</td>
	  				<td style="width:99%;">无需传阅，直接提交办结。</td>
  				</tr>
  				<tr height="30">
	  				<td class="td_1">
	  					<input type="radio" name="choice" value="<%=PassMainConstants.CHOICE_SELECT_DEPT_PERSON_FINISH %>"/>
	  				</td>
	  				<td style="width:99%;">需内部传阅，不用反馈意见。</td>
  				</tr>
  				<tr height="30">
	  				<td class="td_1">
	  					<input type="radio" name="choice" value="<%=PassMainConstants.CHOICE_SELECT_DEPT_PERSON_SELF_CHECK %>"/>
	  				</td>
	  				<td style="width:99%;">内部阅研并反馈意见。</td>
  				</tr>
  				<tr id="dealPersonTr" height="30">
		           	<td style="text-align:right;"><font style="color:red;">*</font></td>
		  			<td>
		  				选择人员：
		  				<input type="text" id="dealPersonNames" name="dealPersonNames" value="" readonly class="inputLine" style="width:250px;height:22px"/>
		  				<input type="hidden" id="dealPersonStr" name="dealPersonStr" value="">
		  				<input type="button" name="choosePerson" value="..." class="btn choosePerson">
		  				<input type="button" name="clearPerson" value="清除" class="btn clearPerson"/>
		  				
		  			</td>
				</tr>
  				<tr>
	  			<td colspan=2>
			  		<table id="suggest1_1" width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
				  			<td class="td_1" style="text-align:right;">&nbsp;</td>
		                	<td textareaId="suggest1_1" peopleId="choiceZone1">
		                	<span class="fl">
		                	备注：<font style="color:red;display:inline;">*</font>
		                	<input type="button" id="createSuggest" name="createSuggest" value="生成备注"></span>
		                	<span class="fr" style="display:inline;">
		                	<a name="suggest_attach" class="suggest_attach" target="#">
			  				<input type="hidden" name="attachId" id="attachId" value=""/>
			  				上传备注附件(<span style="display:inline;" id="fjcount" class="fjcount">0</span>)
			  				</a>
		                	</span>
		                	<!--  
		                	<span class="fr" style="display:inline;"><a class="previewSuggest">意见预览</a></span>
		                	-->
		                	</td>
		              	</tr>
		              	<tr>
		              		<td class="td_1">&nbsp;</td>
		                	<td><textarea id="suggest" name="suggest" rows="5">阅</textarea></td>
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
		      <p style="width:auto;display:inline;"><img src="<%=path%>/dept/css/default/images/loading.gif" style="display:inline;"/>
		      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
		    </div>
		</div>
</div>


    