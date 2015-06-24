<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.contact.deptContact.constant.*"%>
<%@page import="com.wonders.contact.common.util.*"%>
<%@page import="com.wonders.util.*"%>
<% 
String path = request.getContextPath();
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));

if(steplabel.length()==0) steplabel="detail";
%>

<%if(steplabel.equals(DeptContactFlowConstants.STEPNAME_BEGIN)||CommonUtil.targetIsInArray(steplabel,DeptContactFlowConstants.TYPE_OPERATE_UPDATE_STEP)){ %>
<script type="text/javascript" src="<%=path %>/contact/js/messagebox.js"></script>
<script type="text/javascript" src="<%=path %>/contact/deptContact/js/common.js"></script>
<script type="text/javascript" src="<%=path %>/contact/js/common/elementControl.js"></script>
<script type="text/javascript" src="<%=path %>/contact/js/jquery-plugin/jquery.qtip-1.0.0-rc3.js"></script>
<link href="<%=path %>/contact/css/qtip.css" rel="stylesheet">
<%} %>

<%if(steplabel.equals(DeptContactFlowConstants.STEPNAME_BEGIN)){ %>
<script>
$(document).ready(function(){
	$("#"+"formUpdate"+"_loading").hide();
	submitZoneControl("formUpdate",false);

	$("#"+"handle_zone"+"_loading").hide();
	submitZoneControl("handle_zone",false);
	
	$("#formSubmit").attr("value","提交");
	
	$("#formSubmit").click(function(){
		$(this).attr("disabled",true);
		clearError();
		optionsCheck.formId = "formUpdate";
		optionsCheck.url = "<%=path %>/contact-deptContact/add.action";
		options.submitZone = "formUpdate";
		$("#formUpdate").find("input[name='checkOnly']").attr("value","1");
		$("#formUpdate").ajaxSubmit(optionsCheck);
		$(this).removeAttr("disabled");
		return false;
	})
	
	signLeader("SIGN_LEADER",null);
});
</script>
<%} %>

<%if(steplabel.equals(DeptContactFlowConstants.STEPNAME_APPLY)){ %>
<script>
$(document).ready(function(){
	$("#"+"formUpdate"+"_loading").hide();
	submitZoneControl("formUpdate",false);

	$("#"+"handle_zone"+"_loading").hide();
	submitZoneControl("handle_zone",false);
	
	
	$("#formSubmit").click(function(){
		clearError();

		options.submitZone = "formUpdate";
		optionsCheck.formId = "formUpdate";
		optionsCheck.url = "<%=path %>/contact-deptContact/update.action";
		optionsCheck.needSuggest = false;
		$("#formUpdate").find("input[name='checkOnly']").attr("value","1");
		$("#formUpdate").ajaxSubmit(optionsCheck);
		
		return false;
	})
	
	$("#handleSubmit").click(function(){
		clearError();

		optionsCheck.formId = "formUpdate";
		optionsCheck.needSuggest = true;
		optionsCheck.url = "<%=path %>/contact-deptContact/operateApply.action";
		options.submitZone = "handle_zone";
		options.callback = null;
		$("#formUpdate").find("input[name='checkOnly']").attr("value","1");
		$("#formUpdate").ajaxSubmit(optionsCheck);

		return false;
	})
	
	var choices = $("input[name='choice']");
	var choice1 = $(choices).get(0);
	var choice2 = $(choices).get(1);
	
	$(choice1).click(function(){
		elementEnable("choiceZone");
		$(choice2).attr("disabled",false);
	})
	
	$(choice2).click(function(){
		elementDisable("choiceZone");
		$(choice1).attr("disabled",false);
	})
	
	$(choice1).click();
	
	//initMessagebox();
	initMessageboxClose();

	$("#mainUnitId,#copyUnitId,#theme,#contactDate,#replyDate,#content,#contentAttachmentId,#ref_id_zone").change(function(){
		//console.log("changed");
		$("#modify").attr("value","1");
	})

	$("#todo_handle").click(function(){
		if($("#modify").val()=="1"){
			if(confirm("表单已修改，是否保存？")){
				options.callback=function(){
					$("#modify").attr("value","");
					MessageBox("handle_zone","maskDiv");
				};
				$("#formSubmit").click();
			}else{
				return false;
			}
		}else{
			MessageBox("handle_zone","maskDiv");
		}
		return false;
	});
});
</script>

<script>
//默认意见
function defaultSuggest(){
	var str = "";
	var choice = $("input[name='choice']:checked").val();
	var suggest = $("#suggest");
	if($(suggest).val()==""){
		if(choice=="<%=DeptContactConstants.CHOICE_APPLY_TO_SIGN%>"){
			str="请"+$("#SIGN_LEADER").find("option:selected").html()+"签发。";
		}
		if(choice=="<%=DeptContactConstants.CHOICE_APPLY_TO_CANCEL%>"){
			str="取消工作联系单。";
		}
		$(suggest).val(str);
	}
}
</script>
<%} %>

<%if(steplabel.equals(DeptContactFlowConstants.STEPNAME_SIGN)){ %>
<script>
$(document).ready(function(){
	$("#"+"formUpdate"+"_loading").hide();
	submitZoneControl("formUpdate",false);

	$("#"+"handle_zone"+"_loading").hide();
	submitZoneControl("handle_zone",false);
	
	$("#formSubmit").click(function(){
		clearError();
		options.submitZone = "formUpdate";
		optionsCheck.formId = "formUpdate";
		optionsCheck.needSuggest = false;
		optionsCheck.url = "<%=path %>/contact-deptContact/update.action";
		$("#formUpdate").find("input[name='checkOnly']").attr("value","1");
		$("#formUpdate").ajaxSubmit(optionsCheck);
		
		return false;
	})
	
	$("#handleSubmit").click(function(){
		clearError();
		
		optionsCheck.formId = "formUpdate";
		optionsCheck.needSuggest = true;
		optionsCheck.url = "<%=path %>/contact-deptContact/operateSign.action";
		options.submitZone = "handle_zone";
		$("#formUpdate").find("input[name='checkOnly']").attr("value","1");
		$("#formUpdate").ajaxSubmit(optionsCheck);
		
		return false;
	})
	
	//initMessagebox();
	initMessageboxClose();

	$("#mainUnitId,#copyUnitId,#theme,#contactDate,#replyDate,#content,#contentAttachmentId,#ref_id_zone").change(function(){
		//console.log("changed");
		$("#modify").attr("value","1");
	})
	
	$("#todo_handle").click(function(){
		if($("#modify").val()=="1"){
			if(confirm("表单已修改，是否保存？")){
				options.callback=function(){
					$("#modify").attr("value","");
					MessageBox("handle_zone","maskDiv");
				};
				$("#formSubmit").click();
			}else{
				return false;
			}
		}else{
			MessageBox("handle_zone","maskDiv");
		}
		return false;
	});
});
</script>

<script>
//默认意见
function defaultSuggest(){
	var str = "";
	var choice = $("input[name='choice']:checked").val();
	var suggest = $("#suggest");
	if($(suggest).val()==""){
		if(choice=="<%=DeptContactConstants.CHOICE_SIGN_AGREE%>"){
			str="同意签发。";
		}
		if(choice=="<%=DeptContactConstants.CHOICE_SIGN_TO_APPLY%>"){
			str="返回修改。";
		}
		
		$(suggest).val(str);
	}
}
</script>
<%} %>
