<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.contact.deptSubProcess.constant.*"%>
<%@page import="com.wonders.contact.common.util.*"%>
<%@page import="com.wonders.util.*"%>

<% 
String path = request.getContextPath();
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));

if(steplabel.length()==0) steplabel="detail";
%>

<%if(CommonUtil.targetIsInArray(steplabel,DeptSubProcessFlowConstants.FLOW_OPERATE_STEP)){ %>
<script type="text/javascript" src="<%=path %>/contact/js/messagebox.js"></script>
<script type="text/javascript" src="<%=path %>/contact/js/common/elementControl.js"></script>
<script type="text/javascript" src="<%=path %>/contact/js/common/deptSubProcess.js"></script>
<script type="text/javascript" src="<%=path %>/contact/deptSubProcess/js/common.js"></script>
<script type="text/javascript" src="<%=path %>/contact/js/jquery-plugin/jquery.qtip-1.0.0-rc3.js"></script>

<link href="<%=path %>/contact/css/qtip.css" rel="stylesheet">
<%} %>

<style>
.createSuggest,.previewSuggest{cursor:pointer}
a{cursor:pointer}
</style>

<%if(steplabel.equals(DeptSubProcessFlowConstants.STEPNAME_DISPATCHER)){%>
	<!-- 自定义初始化方法 -->
	<script>
	function initFormElement(){
		var choices = $("input[name='choice']");
		var choice1 = $(choices).get(0);
		var choice2 = $(choices).get(1);
	
		var choices2 = $("input[name='choice2']");
		var choice2_1 = $(choices2).get(0);
		var choice2_2 = $(choices2).get(1);
		
		
		$(choice1).click(function(){
			elementEnable("choiceZone1");
			elementDisable("choiceZone2");
			
			$(choice2).attr("disabled",false);
		})
		/**/
		$(choice2).click(function(){
			elementDisable("choiceZone1");
			elementEnable("choiceZone2");
			
			$(choice1).attr("disabled",false);
			
			if($(choice2_1).attr("checked")) $(choice2_1).click();
			if($(choice2_2).attr("checked")) $(choice2_2).click();
		})
	
		$(choice2_1).click(function(){
			elementEnable("choiceZone2_1");
			elementDisable("choiceZone2_2");
			
			$(choice2_2).attr("disabled",false);
		})
		
		$(choice2_2).click(function(){
			elementDisable("choiceZone2_1");
			elementEnable("choiceZone2_2");
			
			$(choice2_1).attr("disabled",false);
		})
		
		$(choice1).click();
	}
	
	function initButton(){
		//清除
		$(":button[name=clearPerson]").click(function(){
			var td = $(this).parent("td");
			var idObj = $(td).find(":hidden[name='dealPersonStr']");
			var nameObj = $(td).find(":text[name='dealPersonNames']");
			var loginNameObj = $(td).find(":hidden[name='dealPersonIdStr']");
			clearPeople(idObj,nameObj,loginNameObj);
			return false;
		})
	
		$(":button[name='clearLeader']").click(function(){
			var td = $(this).parent("td");
			var idObj = $(td).find(":hidden[name='dealLeaderStr']");
			var nameObj = $(td).find(":text[name='dealLeaderNames']");
			var loginNameObj = $(td).find(":hidden[name='dealLeaderIdStr']");
			clearPeople(idObj,nameObj,loginNameObj);
			return false;
		})
	
		
		//人员选择
		$(":button[name=choosePerson]").click(function(){
			var td = $(this).parent("td");
			var idObj = $(td).find(":hidden[name='dealPersonStr']");
			var nameObj = $(td).find(":text[name='dealPersonNames']");
			//alert("未实现");

			selectDeptUser("deptUserTreeZone");
			
			//人员选择树(未实现)
			return false;
		})
	
		$(":button[name='chooseLeader']").click(function(){
			var td = $(this).parent("td");
			var idObj = $(td).find(":hidden[name='dealLeaderStr']");
			var nameObj = $(td).find(":text[name='dealLeaderNames']");
			//alert("未实现");
			//人员选择树(未实现)
			selectDeptLeader("deptLeaderTreeZone");
			
			return false;
		})
	}
	
	function initSuggest(){
		$("a.createSuggest").click(function(){
			var textareaId = $(this).parent("span").parent("td").attr("textareaId");
			var peopleId = $(this).parent("span").parent("td").attr("peopleId");
	
			var textareaZone = $("#"+textareaId);
			var peopleZone = $("#"+peopleId);
			
			var textarea = $(textareaZone).find("textarea");
			var personNames = $(peopleZone).find(":text[name='dealPersonNames']");
			var leaderNames = $(peopleZone).find(":text[name='dealLeaderNames']");
	
			createSuggest($(personNames).attr("value"),$(leaderNames).attr("value"),textarea);
			return false;
		});
		/*
		$("a.previewSuggest").click(function(){
			alert("未实现");
			return false;
		});
		*/
	}

	//默认意见
	function defaultSuggest(){
//console.log("defaultSuggest");
		var str = "";
		var choice = $("input[name='choice']:checked").val();
		var choice2 = $("input[name='choice2']:checked").val();
		
		var suggest = $("textarea[name='suggest']:not(:disabled)");
		
		if($(suggest).val()==""){
			//console.log($(".previewSuggest:visible"));
			if(choice=="<%=DeptSubProcessConstants.CHOICE_DISPATCHER_SEND%>"){
				//str="请"+$("#SIGN_LEADER").find("option:selected").html()+"签发";
			}
			if(choice=="<%=DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL%>"){
				//str="取消工作联系单";
				if(choice2=="<%=DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL_NO_SUGGEST%>"){
					str = "本人没有意见";
				}
			}
			$(suggest).val(str);
		}
	}
	
	
	</script>
	
	<!-- 初始化方法 -->
	<script>
	$(document).ready(function(){
		$("#"+"handle_zone"+"_loading").hide();
		submitZoneControl("handle_zone",false);
		
		initFormElement();
		initButton();
		initSuggest();
		
		$("#handleSubmit").click(function(){
			clearError();
			
			optionsCheck.formId = "formOperate";
			optionsCheck.url = "<%=path %>/contact-deptSubProcess/dispatcher.action";
			options.submitZone = "handle_zone";
			$("#formOperate").find("input[name='checkOnly']").attr("value","1");
			$("#formOperate").ajaxSubmit(optionsCheck);
			
			//options.url = "/deptSubProcess/dispatcher.action";
			//$("#formOperate").ajaxSubmit(options);
			
			return false;
		})
		
		//testInit();
		
		initMessagebox();
		initMessageboxClose();
	});
	</script>

<%} %>

<%if(steplabel.equals(DeptSubProcessFlowConstants.STEPNAME_DEAL)){%>
	<!-- 自定义初始化方法 -->
	<script>
		function initFormElement(){
		var choices = $("input[name='choice']");
		var choice1 = $(choices).get(0);
		var choice2 = $(choices).get(1);
		var choice3 = $(choices).get(2);

	
		$(choice1).click(function(){
			elementEnable("choiceZone1");
			elementDisable("choiceZone2");
			elementDisable("choiceZone3");
			
			$(choice2).attr("disabled",false);
			$(choice3).attr("disabled",false);
		})
		
		$(choice2).click(function(){
			elementDisable("choiceZone1");
			elementEnable("choiceZone2");
			elementDisable("choiceZone3");
			
			$(choice1).attr("disabled",false);
			$(choice3).attr("disabled",false);
		})
		
		$(choice3).click(function(){
			elementDisable("choiceZone1");
			elementDisable("choiceZone2");
			elementEnable("choiceZone3");
			
			$(choice1).attr("disabled",false);
			$(choice2).attr("disabled",false);
		})
		
		$(choice1).click();
	}

	//默认意见
	function defaultSuggest(){
		var str = "";
		var choice = $("input[name='choice']:checked").val();
		
		var suggest = $("textarea[name='suggest']:not(:disabled)");
		//console.log(suggest);
		if($(suggest).val()==""){
			//console.log($(".previewSuggest:visible"));
			if(choice=="<%=DeptSubProcessConstants.CHOICE_DEAL_NO_SUGGEST%>"){
				str = "本人没有意见 ";
			}
			if(choice=="<%=DeptSubProcessConstants.CHOICE_DEAL_NOT_MY_BUSINESS%>"){
				str = "非本人处理业务";
			}
			$(suggest).val(str);
		}
	}
	</script>
	
	<!-- 初始化方法 -->
	<script>
	$(document).ready(function(){
		$("#"+"handle_zone"+"_loading").hide();
		submitZoneControl("handle_zone",false);

		initFormElement();
	
		//initButton();
		
		//initSuggest();
		
		/**/
		$("#handleSubmit").click(function(){
			clearError();

			optionsCheck.formId = "formOperate";
			optionsCheck.url = "<%=path %>/contact-deptSubProcess/deal.action";
			options.submitZone = "handle_zone";
			$("#formOperate").find("input[name='checkOnly']").attr("value","1");
			$("#formOperate").ajaxSubmit(optionsCheck);

			//options.url = "/deptSubProcess/deal.action";
			//$("#formOperate").ajaxSubmit(options);
			
			return false;
		})
		
		//testInit();
		
		
		initMessagebox();
		initMessageboxClose();
		
	});
	</script>
<%} %>

<%if(steplabel.equals(DeptSubProcessFlowConstants.STEPNAME_LEADER)){%>
	<!-- 自定义初始化方法 -->
	<script>
		function initFormElement(){
		var choices = $("input[name='choice']");
		var choice1 = $(choices).get(0);
		var choice2 = $(choices).get(1);
		var choice3 = $(choices).get(2);

		var choices2 = $("input[name='choice2']");
		var choice2_1 = $(choices2).get(0);
		var choice2_2 = $(choices2).get(1);
		
	
		$(choice1).click(function(){
			elementEnable("choiceZone1");
			elementDisable("choiceZone2");
			elementDisable("choiceZone3");
			
			$(choice2).attr("disabled",false);
			$(choice3).attr("disabled",false);
		})
		
		$(choice2).click(function(){
			elementDisable("choiceZone1");
			elementEnable("choiceZone2");
			elementDisable("choiceZone3");
			
			$(choice1).attr("disabled",false);
			$(choice3).attr("disabled",false);
		})
		
		$(choice3).click(function(){
			elementDisable("choiceZone1");
			elementDisable("choiceZone2");
			elementEnable("choiceZone3");
			
			$(choice1).attr("disabled",false);
			$(choice2).attr("disabled",false);

			if($(choice2_1).attr("checked")) $(choice2_1).click();
			if($(choice2_2).attr("checked")) $(choice2_2).click();
		})
		
		$(choice2_1).click(function(){
			elementEnable("choiceZone3_1");
			elementDisable("choiceZone3_2");
			
			$(choice2_2).attr("disabled",false);
		})
		
		$(choice2_2).click(function(){
			elementDisable("choiceZone3_1");
			elementEnable("choiceZone3_2");
			
			$(choice2_1).attr("disabled",false);
		})
		
		
		$(choice1).click();
	
	}

	function initButton(){
		//清除
		$(":button[name=clearPerson]").click(function(){
			var td = $(this).parent("td");
			var idObj = $(td).find(":hidden[name='dealPersonStr']");
			var nameObj = $(td).find(":text[name='dealPersonNames']");
			var loginNameObj = $(td).find(":hidden[name='dealPersonIdStr']");
			clearPeople(idObj,nameObj,loginNameObj);
			return false;
		})
	
		$(":button[name='clearLeader']").click(function(){
			var td = $(this).parent("td");
			var idObj = $(td).find(":hidden[name='dealLeaderStr']");
			var nameObj = $(td).find(":text[name='dealLeaderNames']");
			var loginNameObj = $(td).find(":hidden[name='dealLeaderIdStr']");
			clearPeople(idObj,nameObj,loginNameObj);
			return false;
		})
		
		//人员选择
		$(":button[name=choosePerson]").click(function(){
			var td = $(this).parent("td");
			var idObj = $(td).find(":hidden[name='dealPersonStr']");
			var nameObj = $(td).find(":text[name='dealPersonNames']");

			selectDeptUser("deptUserTreeZone");
			
			return false;
		})
	
		$(":button[name='chooseLeader']").click(function(){
			var td = $(this).parent("td");
			var idObj = $(td).find(":hidden[name='dealLeaderStr']");
			var nameObj = $(td).find(":text[name='dealLeaderNames']");

			selectDeptLeader("deptLeaderTreeZone");
			
			return false;
		})
	}

	function initSuggest(){
		$("a.createSuggest").click(function(){

			var textareaId = $(this).parent("span").parent("td").attr("textareaId");
			var peopleId = $(this).parent("span").parent("td").attr("peopleId");
	
			var textareaZone = $("#"+textareaId);
			var peopleZone = $("#"+peopleId);
			
			var textarea = $(textareaZone).find("textarea");
			var personNames = $(peopleZone).find(":text[name='dealPersonNames']");
			var leaderNames = $(peopleZone).find(":text[name='dealLeaderNames']");

			if($("input[name='choice']:checked").val()=='<%=DeptSubProcessConstants.CHOICE_LEADER_CONTINUE %>'&&
					$("input[name='choice2']:checked").val()=='<%=DeptSubProcessConstants.CHOICE_LEADER_CONTINUE_DEAL %>'){
				createSuggestText("需本部门其他人员继续处理，转业务经办人。",textarea);
			}
			
			if($("input[name='choice']:checked").val()=='<%=DeptSubProcessConstants.CHOICE_LEADER_CONTINUE %>'&&
					$("input[name='choice2']:checked").val()=='<%=DeptSubProcessConstants.CHOICE_LEADER_CONTINUE_CHOICE_PERSON %>'){
				createSuggest($(personNames).attr("value"),$(leaderNames).attr("value"),textarea);
			}
			return false;
		});
		/*
		$("a.previewSuggest").click(function(){
			alert("未实现");
			return false;
		});
		*/
	}
	
	//默认意见
	function defaultSuggest(){
		var str = "";
		var choice = $("input[name='choice']:checked").val();
		
		var suggest = $("textarea[name='suggest']:not(:disabled)");
		//console.log(suggest);
		if($(suggest).val()==""){
			//console.log($(".previewSuggest:visible"));
			if(choice=="<%=DeptSubProcessConstants.CHOICE_LEADER_PASS%>"){
				str = "审核通过 ";
			}
			$(suggest).val(str);
		}
	}
		
	</script>
	
	<!-- 初始化方法 -->
	<script>
	$(document).ready(function(){
		$("#"+"handle_zone"+"_loading").hide();
		submitZoneControl("handle_zone",false);
	
		initFormElement();
	
		initButton();
		
		initSuggest();
		
		/**/
		$("#handleSubmit").click(function(){
			
			clearError();

			optionsCheck.formId = "formOperate";
			optionsCheck.url = "<%=path %>/contact-deptSubProcess/leaderDeal.action";
			options.submitZone = "handle_zone";
			$("#formOperate").find("input[name='checkOnly']").attr("value","1");
			$("#formOperate").ajaxSubmit(optionsCheck);

			//options.url = "/deptSubProcess/leaderDeal.action";
			//$("#formOperate").ajaxSubmit(options);
			return false;
		})
		
		//testInit();
		
		
		initMessagebox();
		initMessageboxClose();
		
	});
	</script>
<%} %>