<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wonders.send.util.CodeUtil"%>
<%@page import="java.util.*" %>
<%@page import="com.wonders.send.constant.Constants"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.wonders.send.approve.model.bo.SendApprovedinfo"%>
<%@page import="com.wonders.send.approve.model.vo.TApprovedinfoVo"%>
<%@page import="com.wonders.send.approve.service.TApprovedinfoService"%>
<%@page import="com.wonders.send.util.Donull"%>
<%@page import="com.wonders.util.DateUtil"%>
<%@page import="com.wonders.util.StringUtil"%>
<%@page import="com.wonders.util.PWSProperties"%>
<%@page import="com.wonders.send.mainProcess.send.model.bo.TDocSend"%>
<%@page import="com.wonders.send.model.bo.SendTasks"%>
<%@page import="org.springframework.web.context.WebApplicationContext" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.wonders.common.model.vo.TaskUserVo" %>
<%@ page import="com.wonders.constants.LoginConstants" %>
<%
String path = request.getContextPath();
String stepName = StringUtil.getNotNullValueString(request.getAttribute("stepName"));
String sendType = request.getParameter("sendType");
String ngDept = request.getParameter("ngDept");
TDocSend model =(TDocSend)request.getAttribute("model");
String process=StringUtil.getNotNullValueString(model.getModelid());
Donull donull = new Donull();
java.sql.Timestamp today=new java.sql.Timestamp(new java.util.Date().getTime());
int year=Integer.parseInt((today+"").substring(0,4));
String loginName = (String)session.getAttribute("login_name");
//System.out.println("loginName==="+loginName);

String deptId = (String)session.getAttribute("dept_id");
String deptName = (String)session.getAttribute("dept_name");
//System.out.println("====="+deptId+deptName);
String assignedtouser = request.getParameter("assignedtouser");
if(assignedtouser==null){
	assignedtouser = request.getParameter("taskuser");
}
Map<String,TaskUserVo> userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
if(userMap!=null){
	TaskUserVo taskUserVo = userMap.get(assignedtouser);
	if(taskUserVo!=null){
		deptId = taskUserVo.getDeptId();
		deptName = taskUserVo.getDeptName();
		loginName = taskUserVo.getLoginName().replace("ST/","");
	}
}

java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
String qfDate= df.format(new java.util.Date());

String ultimusIp = PWSProperties.getValueByKey("pws_server_ip");
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>

	</title>
	<link href="<%=path %>/send/css/formalize.css" rel="stylesheet">
	<link href="<%=path %>/send/css/page.css" rel="stylesheet">
	<link href="<%=path %>/send/css/default/imgs.css" rel="stylesheet">
	<link href="<%=path %>/send/css/reset.css" rel="stylesheet">

	<script src="<%=path %>/send/js/html5.js"></script>
	<script src="<%=path %>/send/js/jquery-1.4.2.min.js"></script>
	<script src="<%=path %>/send/js/jquery-1.7.1.js"></script>
	<script src="<%=path %>/send/js/jquery.formalize.js"></script>
	<script src="<%=path %>/send/js/form/jquery.form.js"></script>
	<script type="text/javascript" src="<%=path %>/send/js/tDocSend/common.js"></script>
	<script type="text/javascript" src="<%=path %>/send/js/contextpath.js"></script>
	<script type="text/javascript" src="<%=path %>/send/js/messagebox.js"></script>
	<script type="text/javascript" src="<%=path %>/send/js/attach.js"></script>
	<script type="text/javascript" src="<%=path %>/send/js/tDocSend/print.js"></script>
	<script type="text/javascript" src="<%=path %>/send/js/tDocSend/normative.js"></script>
	<style>
	.deptTreeZone{display:none;}
	.redMark{color:red;display:inline;}
	.r_bor{border-right:#000 1px solid}
	</style>

<script>

$(document).ready(function(){
	initMessagebox();
	initMessageboxClose();

	signLeader("dept_leader",null);

	if($("#stepName").val()=="审稿"||$("#stepName").val()=="拟稿人修改"||$("#stepName").val()=="起草"||$("#stepName").val()=="排版"||$("#stepName").val()=="套头"){//修改页面
		$("#title1").html($("[name=typeTitle]").val()+"<br>发文稿纸");

		var secretClass_value = $("#secretClass_value");
		if(secretClass_value.val()!=""){
	          var secretClass=$("#secretClass");
			  for(var i=0;i<secretClass.children("option").length;i++){
			    if(secretClass.children("option:eq("+i+")").val()==secretClass_value.val())
			    secretClass.children("option:eq("+i+")").attr("selected",true);
			  }
		}

		var hj_value = $("#hj_value");
		if(hj_value.val()!=""){
	          var hj=$("#hj");
			  for(var i=0;i<hj.children("option").length;i++){
			    if(hj.children("option:eq("+i+")").val()==hj_value.val())
			    hj.children("option:eq("+i+")").attr("selected",true);
			  }
		}

		var fileType_value = $("#fileType_value");
		if(fileType_value.val()!=""){
	          var fileType=$("#fileType");
			  for(var i=0;i<fileType.children("option").length;i++){
			    if(fileType.children("option:eq("+i+")").val()==fileType_value.val())
			    fileType.children("option:eq("+i+")").attr("selected",true);
			  }
		}


		$("#fileType").parent().append($("#fileType_value").val());
		$("#fileType").hide();
		$("#formSubmit").remove();
		if($("#stepName").val()=="审稿"||$("#stepName").val()=="排版"||$("#stepName").val()=="套头"){
			$("#leaderTr").hide();
		}else if($("#stepName").val()=="起草"){
			//$("#leaderTr").children("option:eq(0)").attr("selected",true);
		}



	}else{//新增页面
		$(".Divab1").hide();
		//$("[id=showUpdate]").hide();

		$("[name=sendReportW]").val("<%=deptName%>");
		$("[name=sendReportId]").val("<%=deptId%>");

		<%if("120411".equals(sendType)){%>
			MessageBox("handle_zone1","maskDiv1");
			$("#normativeForm").hide();
		<%}else if("120444".equals(sendType)){%>
			$("#title1").html("中共上海申通地铁集团有限公司纪律检查委员会<br>发文稿纸");
			$("#normativeForm").hide();
		<%}else if("office".equals(ngDept)){%>
			$("#title1").html("上海申通地铁集团有限公司办公室<br>发文稿纸");
		<%}%>
	}
});

	function changeType(obj){
		if($(obj).val()=="党委文"){
			MessageBox("handle_zone1","maskDiv1");
			$("#normativeForm").hide();
		}else if($(obj).val()=="行政文"){
			$("#title1").html("上海申通地铁集团有限公司<br>发文稿纸");
			$("#normativeForm").show();
		}
	}

	function selectTypeFunc(){
		if($("[name=type_choice]:checked").length>0){
			closeMessageBox("handle_zone1","maskDiv1");
			$("#title1").html($("[name=type_choice]:checked").val().replaceAll("br","<br>")+"<br>发文稿纸");
			//alert($("#title1").html());
		}else{
			alert("请选择党委发文类型！");
		}
	}

	function closeType(){
		closeMessageBox("handle_zone1","maskDiv1");
		$("#title1").html("上海申通地铁集团有限公司<br>发文稿纸");
		$("#fileType").val("行政文");
		$("#normativeForm").show();
	}

	function check_quotes(sin) {
		if (sin == undefined) return false;
		return (sin.indexOf('"') >= 0 || sin.indexOf('\'') >= 0);
	}

	function checkForm(){
		if($.trim($("#docTitle").val())==""){
			$("#docTitle").focus();
			alert("请输入标题!");
			return false;
		}

		var secretLimit_value = $("[name=secretLimit]").val();
		 if(secretLimit_value!=""&&(!(/^(\+|-)?\d+$/.test( secretLimit_value )) || secretLimit_value < 0)){
			 $("[name=secretLimit]").focus();
		    alert("保密期限必须是正整数！");
		    return false;
		 }

		if($.trim($("[name=sendMainW]").val())==""&&$("[name=sendMain]").val()==""){
			alert("请选择主送单位!");
			return false;
		}

		if($.trim($("[name=sendMainW]").val())!=""&&$("[name=sendMainW]").val().length>2000){
			alert("您输入的外部主送单位名称过长！");
			return false;
		}

		if($.trim($("[name=snedCopy]").val())!=""&&$("[name=snedCopy]").val().length>2000){
			alert("您输入的外部抄送单位名称过长！");
			return false;
		}
		//check附件待补

		if(check_quotes($("[name=secretLimit]").val())){
			alert('保密期限请勿输入英文单引号或英文双引号。');
			$("[name=secretLimit]").focus();
			return false;
		}
		if(check_quotes($("#docTitle").val())){
			alert('标题请勿输入英文单引号或英文双引号。');
			$("#docTitle").focus();
			return false;
		}
		//if(!sendTitleType())   待补
		//return false;

		if($("[name=stepName]").val()=="拟稿人修改"||$("[name=stepName]").val()=="起草"){
			if($("[name=choice]:checked").val()==1&&$("#dept_leader").val()==""){
				alert("请在表单中选择部门领导!");
				$("#dept_leader").focus();
				return false;
			}
		}else if($("[name=stepName]").val()!="审稿"&&$("[name=stepName]").val()!="排版"&&$("[name=stepName]").val()!="套头"&&$("#dept_leader").val()==""){
			alert("请在表单中选择部门领导!");
			$("#dept_leader").focus();
			return false;
		}

		//规范性设置
		if(!checkNormative()){
			return false;
		}

		//规范性设置套头确认
		if($("[name=modelid]").val()=="新发文流程" &&
				$("[name=stepName]").val()=="套头" && $("#ttConfirm").val()!="1"){
			alert("请确认规范性文件设置!");
			return false;
		}
		return true;
	}

	function submitForm(obj){
		if(checkForm()){
			if($("[name=contentAtt]").val()==""){
				alert("请上传附件！");
				return false;
			}
			if (confirm('确定提交此发文登记?')) {
				/*待补
				if($("[name=sendInsideId]").val()!= ""&&$("[name=reportId]").val()!= ""){
					$("[name=sendInsideId]").val( document.frmAdd.sendInsideId.value + "," + document.frmAdd.reportId.value);
				}else if(document.frmAdd.reportId.value != ""){
					document.frmAdd.sendInsideId.value = document.frmAdd.reportId.value;
				}
				if(document.frmAdd.sendInside.value != ""&&document.frmAdd.report.value != ""){
					document.frmAdd.sendInside.value = document.frmAdd.sendInside.value + "," + document.frmAdd.report.value;
				}else if(document.frmAdd.report.value != ""){
					document.frmAdd.sendInside.value = document.frmAdd.report.value;
				}
				*/

				$("[name=typeTitle]").val($("#title1").html().replaceAll("<br>发文稿纸","").replaceAll("<BR>发文稿纸",""));

				var formOptions2 = {
					cache:false,
					type:'post',
					callback:null,
					dataType :'json',
					url:contextpath+"/send-tDocSend/startFlow.action",
				    success:function(data){
						//var obj = JSON.parse(data);
						//$("#formUpdate").submit();
						//$("#formUpdate").ajaxSubmit(formOptions);
						if(data!=null&&data.oincidentNo!=null){
							alert("提交成功！");
							window.location.href = "<%=path %>/send-tDocSend/toFormPage.action?incidentNo="+data.oincidentNo
												+"&modelName="+encodeURI(data.modelName)
						}else{
							alert("error");
						}

						return false;
				    }
				};

				$(obj).attr("disabled",true);
				$("#closeBtn").attr("disabled",true);
				$("#formUpdate_loading").show();
				$("#formUpdate").ajaxSubmit(formOptions2);
			}
		}
	}

	function chooseYL(){
		var fwYear = $("[name=code2]").val();
		var drFwtype = $("[name=code1]").val();
		var url = '<%=path%>/send/common/swbhx.jsp?swYear='+fwYear + '&drSwtype=' + drFwtype+"&parentCode=<%=Constants.DocSend__Dictionary%>&typeId=<%=model.getSendType()%>";
		showModalDialog(url, window, "dialogWidth:400px;dialogHeight:600px");
	}

	function setNo(){
		$("[name=code3]").val(noSe);
	}

	function getNo(){
		var drFwtype = $("[name=code1]").val();
		var fwYear = $("[name=code2]").val();
		var drFwid="";
		var url = "<%=path%>/send-tDocSend/getSerialNo.action?drSwtype="+drFwtype+"&swYear="+fwYear+"&drSwid=" + drFwid+"&parentCode=<%=Constants.DocSend__Dictionary%>&typeId=<%=model.getSendType()%>"+"&jdjdjd="+new Date().getTime();
        var result=doRequest(url);
        document.all.code3.value = result;
	}

	function changeZH(){
		var zh = document.getElementById("code1");
		var tmp = "";
		for(i=0;i<zh.length;i++){
	   		if(zh[i].selected==true){
	    		tmp = zh[i].innerText;
	    		break;
	   		}
		}
		if("沪地铁"!=tmp){
			if(document.getElementById("code4")){
				document.getElementById("code4").disabled = true;
				document.getElementById("code4").style.display = "none";
			}
		}else{
			if(document.getElementById("code4")){
				document.getElementById("code4").disabled = false;
				document.getElementById("code4").style.display = "";
			}
		}
	}

	function doRequest(url){
		var req=getXMLHttpRequest();
		req.open('get',url,false);//true 异步  false 同步
		req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		req.send(null);//发送参数如果有参数req.send("username="+user_name);用request取得
		return req.responseText;
	}

	function getXMLHttpRequest(){
		var req=false;
		if(window.XMLHttpRequest){
			req=new XMLHttpRequest();
			if(req.overrideMimeType)
				req.overrideMimeType("text/xml");
		}else if(window.ActiveXObject){
			try{
				req=new ActiveXObject("Msxml2.XMLHTTP");
			}catch(e){
				try{
					req=new ActiveXObject("Microsoft.XMLHTTP");
				}catch(e){}
			}
		}
	  return req;
	}

	function initButton(){
		//签发领导选择
		$(":button[name='chooseShld']").click(function(){
			//var td = $(this).parent("td");
			//var idObj = $(td).find(":hidden[name='dealLeaderStr']");
			//var nameObj = $(td).find(":text[name='dealLeaderNames']");
			//alert("未实现");
			//人员选择树(未实现)
			selectShld($("input:hidden[name=modelName]").val(),"shldTreeZone");

			return false;
		})

		$(":button[name='clearShld']").click(function(){
			var td = $(this).parent("td");
			if(confirm("确认清除吗？")){
				$(td).find("[name=shldName]").val("");
				$(td).find("[name=shldId]").val("");
				$(td).find("[name=shldDeptId]").val("");
				$(td).find("[name=shldmsName]").val("");
				$(td).find("[name=shldmsId]").val("");
				$(td).find("[name=shldmsDeptId]").val("");
				$(td).find("[name=shldUserId]").val("");
			}
			return false;
		})

		//会签领导选择
		$(":button[name='chooseHqld']").click(function(){
			//var td = $(this).parent("td");
			//var idObj = $(td).find(":hidden[name='dealLeaderStr']");
			//var nameObj = $(td).find(":text[name='dealLeaderNames']");
			//alert("未实现");
			//人员选择树(未实现)
			selectHqld($("input:hidden[name=modelName]").val(),"hqldTreeZone");

			return false;
		})

		$(":button[name='clearHqld']").click(function(){
			var td = $(this).parent("td");
			if(confirm("确认清除吗？")){
				$(td).find("[name=hqldName]").val("");
				$(td).find("[name=hqldId]").val("");
				$(td).find("[name=hqldDeptId]").val("");
				$(td).find("[name=hqldmsName]").val("");
				$(td).find("[name=hqldmsId]").val("");
				$(td).find("[name=hqldmsDeptId]").val("");
				$(td).find("[name=hqldUserId]").val("");
			}
			return false;
		})
	}

	//replaceall方法
	String.prototype.replaceAll  = function(s1,s2){
	  return this.replace(new RegExp(s1,"gm"),s2);
	}

</script>
</head>
<body class="Flow" style="background:none">
<div id="bt"  class=" transparent" style="display:none;"></div>
<form action="<%=path %>/send-tDocSend/startFlow.action" id="formUpdate" name="formUpdate" method="post">
	<!-- 待办项时增加此变量 对应  DeptContactOperateVo。taskId = todoid-->
	<input type="hidden" id="modify" name="modify" value=""/>
	<input type="hidden" id="mainUnitId" name="sendMainId" value="<s:property value="tDocSend.sendMainId"/>"/>
	<input type="hidden" id="copyUnitId" name="sendInsideId" value="<s:property value="tDocSend.sendInsideId"/>"/>
	<input type="hidden" id="reportUnitId" name="sendReportId" value="<s:property value="tDocSend.sendReportId"/>"/>
	<input type="hidden" value="<s:property value='tDocSend.typeTitle'/>" name="typeTitle"/>
	<input type="hidden" value="" name="sendTitleType"/>
	<input type="hidden" value="<s:property value='tDocSend.id'/>" name="id"/>
	<input type="hidden" value="<s:property value='#request.taskId'/>" name="taskId"/>
	<input type="hidden" value="<s:property value='#request.stepName'/>" name="stepName" id="stepName"/>
	<input type="hidden" value="<s:property value='#request.modelName'/>" name="modelName"/>
	<input type="hidden" value="<s:property value='#request.processName'/>" name="processName"/>
	<input type="hidden" value="<s:property value='#request.incidentNo'/>" name="incidentNo"/>
	<input type="hidden" value="<s:property value='#request.pinstanceId'/>" name="pinstanceId"/>
	<input type="hidden" value="<s:property value='#request.taskUserName'/>" name="taskUserName"/>
	<input type="hidden" value="<s:property value='#request.assignedtouser'/>" name="assignedtouser"/>

	<input type="hidden" value="<s:property value='tDocSend.attFlag'/>" name="attFlag"/>

	<input type="hidden" value="<s:property value='tDocSend.modelid'/>" name="modelid"/>
	<input type="hidden" value="<s:property value='tDocSend.operator'/>" name="operator"/>
	<input type="hidden" value="<s:property value='tDocSend.sendUser'/>" name="sendUser"/>
	<input type="hidden" value="<s:property value='tDocSend.sendUserdept'/>" name="sendUserdept"/>
	<input type="hidden" value="<s:property value='tDocSend.operateTime'/>" name="operateTime"/>
	<input type="hidden" value="<s:property value='tDocSend.removed'/>" name="removed"/>
	<input type="hidden" value="<s:property value='tDocSend.lineType'/>" name="lineType"/>
	<input type="hidden" value="<s:property value='tDocSend.flag'/>" name="flag"/>
	<input type="hidden" value="<s:property value='tDocSend.autScanFlag'/>" name="autScanFlag"/>
	<input type="hidden" value="<s:property value='tDocSend.sendType'/>" name="sendType"/>
	<input type="hidden" value="<s:property value='#request.incidentNo'/>" name="pinstanceid"/>
	<div class=" transparent" id="maskDiv" style="display:none;" style="filter:alpha(opacity=30);opacity:0.3;"></div>

	<!-- <div class="f_bg_fw"> -->
	<div class="f_bg_fw" style="background:none">
     	<div class="Divab1">
			<!--1st-->
			<div class="panel_6">
				<div class="divT">
					<div class="mb10 icon icon_1"></div>
					<div class="more_4">
						<a title="更多" href="#">更多</a>
					</div>
				</div>
				<div class="divH">
					<div class="divB">
						<h5 class="clearfix">
							业务办理
						</h5>
						<div class="con">
							<ul class="button clearfix">
								<%--
									<s:if test="#request.ifDbx=='yes'">
								--%>
								<li>
									<a id="todo_handle" class="ywbl" href="javascript:void(0);">办理</a>
								</li>

								<li>
									<a id="todo_print" class="print"  href="javascript:void(0);" onclick="toPrintPage();">打印</a>
								</li>
								<li>
									<a id="todo_scan" class="jk"  href="http://<%=ultimusIp%>/sLogin/workflow/TaskStatus.aspx?TaskID=<s:property value='#request.taskId'/>" target="_blank">监控</a>
								</li>
							</ul>
						</div>
					</div>
					<div class="divF"></div>
				</div>
			</div>

		</div>

    	<!-- <div class="logo_1"></div> -->
        <div class="gray_bg" <%if(model.getId()==null){ %>style="text-align:center;margin:0 auto;"<%} %>>
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top_fw">
                        	<h1 class="t_c" id="title1">上海申通地铁集团有限公司<br>发文稿纸</h1>
	                            <!--<jsp:include page="inprogressImg.jsp"/>-->
							<div class="mb10">

								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									<thead>
									<th colspan="4" class="lableTd6" style="padding-left:10px;padding-right:0;">
									<s:if test="#request.stepName!='排版'">
									<h5><b style="display:inline">发文字号:</b></h5>
									</s:if>
									<s:if test="#request.stepName=='排版'">
									<h5 style="display:inline;background-image:none;padding-left:0;padding-right:0">
									<select id="code1" name="code1" onchange="changeZH();" style="display:inline;">
									<%
									List list=new ArrayList();
									list=CodeUtil.findCodeInfoByCode(Constants.DocSend__Dictionary,model.getSendType()+"_ZH");
									if(list!=null&&list.size()>0){
										if("上海申通地铁集团有限公司办公室".equals(model.getTypeTitle())){
											Iterator iter=list.iterator();
											while(iter.hasNext()){
											String temp=(String)iter.next();
											String temps[]=temp.split(":");
												if("沪地铁办发".equals(temps[1])){
												%>
													<option value="<%=temps[0]%>"><%=temps[1] %></option>
												<%
													break;
												}%>
											<%}%>

										<%}else{
											Iterator iter=list.iterator();
											while(iter.hasNext()){
											String temp=(String)iter.next();
											String temps[]=temp.split(":");
											if(!"沪地铁办发".equals(temps[1])){
											%>
												<option value="<%=temps[0]%>"><%=temps[1] %></option>
											<%}%>
											<%}%>
										<%}
									}%>



									</select>
									<%
									if("上海申通地铁集团有限公司办公室".equals(model.getTypeTitle())){

									}else{
									%>
									<%
									List<String> listDept=CodeUtil.findCodeInfoByCode("fwdept",model.getSendType()+"_BM");
									if(listDept!=null&&listDept.size()>0){
									%>
									<select name="code4" id="code4" style="display:inline;">
										<option value=""></option>
										<%
											//List<String> listDept=CodeUtil.findCodeInfoByCode("fwdept",sendType+"_BM");
											Iterator iterDept=listDept.iterator();
											while(iterDept.hasNext()){
											String temp=(String)iterDept.next();
											String temps[]=temp.split(":");
											%>
												<option <%if(donull.dealNull(model.getLineType()).equals(temps[1])){%>
												selected="selected"
												<%} %>
												value="<%=temps[1]%>"><%=temps[1] %></option>
											<%} %>
											</select>
									<%} %>
									<%} %>

									（<select name="code2"  style="display:inline;">
									<%for(int i=0;i<3;i++){ %>
									<option value="<%=(year-1)+i %>"
									<%if(((year-1)+i)==year){ %>
									selected="selected"
									<%} %>
									><%=(year-1)+i %></option>
									<%} %>
									</select>）

									<input id="code3" name="code3" type="text" style="width:30px;display:inline;" size="4">号
									<input name="lwdw" type="button"  onclick="getNo()" value="取"  style="display:inline;"/>
									<input name="lwdw" type="button"  onclick="chooseYL()" value="选" style="display:inline;">
									</h5>
									</s:if>
									<s:else>
									<h5 style="display:inline;background-image:none;">
									<s:property value='tDocSend.sendId'/>&nbsp;
									<input type="hidden" name="sendId" value="<s:property value='tDocSend.sendId'/>">
									<input type="hidden" name="code1" value="<s:property value='tDocSend.code1'/>">
									<input type="hidden" name="code2" value="<s:property value='tDocSend.code2'/>">
									<input type="hidden" name="code3" value="<s:property value='tDocSend.code3'/>">
									</h5>
									</s:else>

	                            <h5 style="float:right;display:inline;background-image:none;padding-left:0;padding-right:0;">
									<b style="display:inline">文别:</b><span style="display:inline">
									<s:if test="#request.stepName=='排版'">
									<select name="docClass" >
									<%
									List list2=CodeUtil.findCodeInfoByCode(Constants.DocSend__Dictionary,Constants.DocSend__Dictionary_docClass_CODE);
									Iterator iter2=list2.iterator();
									while(iter2.hasNext()){
									String temp=(String)iter2.next();
									String temps[]=temp.split(":");
									%>
										<option value="<%=temps[1]%>"><%=temps[1] %></option>
										<%} %>
									</select>
									</s:if>
									<s:else>
									<s:if test="#request.stepName!='排版'">&nbsp;</s:if><s:property value='tDocSend.docClass'/>
									<input type="hidden" name="docClass" value="<s:property value='tDocSend.docClass'/>">
									</s:else>
									<s:if test="#request.stepName!='排版'">&nbsp;&nbsp;</s:if>
									</span>
									<b style="display:inline">&nbsp;&nbsp;密别:</b>
										<input type="hidden" id="secretClass_value" value="<s:property value='tDocSend.secretClass'/>"/>
										<select name="secretClass" id="secretClass" style="display:inline">
											<%
											List list3=CodeUtil.findCodeInfoByCode(Constants.DocSend__Dictionary,Constants.DocSend__Dictionary_secretClass_CODE);
											Iterator iter3=list3.iterator();
											while(iter3.hasNext()){
											String temp=(String)iter3.next();
											String temps[]=temp.split(":");
											%>
											<option value="<%=temps[1]%>"><%=temps[1] %></option>
											<%} %>
										</select>
									<b style="display:inline">&nbsp;&nbsp;缓急:</b>
									<input type="hidden" id="hj_value" value="<s:property value='tDocSend.hj'/>"/>
									<select name="hj" id="hj" style="display:inline">
										<%
										List list4=CodeUtil.findCodeInfoByCode(Constants.DocSend__Dictionary,Constants.DocSend__Dictionary_hj_CODE);
										Iterator iter4=list4.iterator();
										while(iter4.hasNext()){
										String temp=(String)iter4.next();
										String temps[]=temp.split(":");
										%>
										<option value="<%=temps[1]%>"><%=temps[1] %></option>
										<%} %>
									</select>
								</h5>


									</th>
									</thead>

								  	<tbody>
								  	<tr class="content7">
	                                    <td class="lableTd t_r" style="width:15%"><font class="redMark">*&nbsp;</font>文件类型</td>
	                            	    <td style="width:35%">
		                            	    <input type="hidden" id="fileType_value" value="<s:property value='tDocSend.fileType'/>"/>
		                            	    <select name="fileType" id="fileType" onblur="changeType(this)">
		                            	    	<%--
		                            	    	<%
												List list5=CodeUtil.findCodeInfoByCode(Constants.DocSend__Dictionary,Constants.DocSend__Dictionary_fileType_CODE);
												Iterator iter5=list5.iterator();
												while(iter5.hasNext()){
												String temp=(String)iter5.next();
												String temps[]=temp.split(":");
												%>
												<option value="<%=temps[1]%>"
												<%if(Constants.DocSend__Dictionary_CODE.equals(sendType)&& "行政文".equals(temps[1])){ %>
												selected="selected"

												<%}else if(Constants.DocSend_DW_Dictionary_CODE.equals(sendType)&& "党委文".equals(temps[1])){ %>
												selected="selected"
												<%} %>
												><%=temps[1] %></option>
												<%} %>
												 --%>
												<%if(sendType==null||"120403".equals(sendType)){ %>
													<option value="行政文" selected="selected">行政文</option>
													<!-- <option value="党委文">党委文</option> -->
												<%}else if("120411".equals(sendType)){ %>
													<option value="行政文">行政文</option>
													<option value="党委文" selected="selected">党委文</option>
												<%}else if("120444".equals(sendType)){ %>
													<option value="纪委文">纪委文</option>
												<%} %>
		                            	    </select>
		                            	</td>
	                                    <td class="lableTd t_r" style="width:15%">保密期限</td>
	                            	    <td><input type="text" name="secretLimit" class="input_tiny" value="<s:property value='tDocSend.secretLimit'/>"/>年</td>
	                          	    </tr>
	                          	    </tbody>
	                          	  </table>
	                          	  <s:if test="#request.stepName!=null&&#request.stepName!=''">
	                          	  	<jsp:include page="suggest.jsp"/>
	                          	  </s:if>
	                          	  <s:else>
	                          	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
                                <tr>
                                  <td width="33%" class="r_bor">
                                    <div>签 发：</div>
                                    <div class="clearfix">
                                      <div>&nbsp;</div>
                                      <div class="fr">&nbsp;</div>
                                      <div class="clear"></div>
                                      <div class="fr">&nbsp;</div>
                                    </div>
                                  </td>
                                  <td width="33%" class="r_bor">
                                    <div>审 阅：</div>
                                    <div class="clearfix">
                                      <div></div>
                                      <div class="fr"></div>
                                      <div class="clear"></div>
                                      <div class="fr"></div>
                                    </div>
                                  </td>
                                  <td>
                                    <div>会 签：</div>
                                    <div class="clearfix">
                                      <div></div>
                                      <div class="fr"></div>
                                      <div class="clear"></div>
                                      <div class="fr"></div>
                                    </div>
                                  </td>
                                </tr>
                                <tr>
                                  <td width="33%" class="lableTd t_c r_bor">拟稿部门</td>
                                  <td width="33%" class="lableTd t_c r_bor">会签部门</td>
                                  <td class="lableTd t_c">核稿部门</td>
                                </tr>
                                <tr>
                                  <td width="33%" class="r_bor">
                                    <div>拟稿人：</div>
                                    <div class="clearfix">
                                      <div>&nbsp;</div>
                                      <div class="fr">&nbsp;</div>
                                      <div class="fr mr8">&nbsp;</div>
                                    </div>
                                    <div>部门领导：</div>
                                    <div class="clearfix">
                                      <div>&nbsp;</div>
                                      <div class="fr">&nbsp;</div>
                                      <div class="fr mr8">&nbsp;</div>
                                    </div>
                                  </td>
                                  <td width="33%" class="r_bor"></td>
                                  <td></td>
                                </tr>
                              </table>
                              </s:else>
	                          	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
	                          	    <tr class="content6">
	                                    <td class="lableTd t_r" style="width:15%"><font class="redMark">*&nbsp;</font>标题</td>
	                                    <td colspan="3" class="content6">
	                                    <input name="docTitle" type="text" class="input_large" id="docTitle" style="width:100%;" maxlength="200" value="<s:property value='tDocSend.docTitle'/>"/></td>
                                  	</tr>
								  	<tr class="content6">
										<td class="lableTd t_c" style="line-height:120px;width:15%"><font class="redMark">*&nbsp;</font>主送单位</td>
										<td class="pl18" colspan="3">
											<div style="width: 45%; margin-right: 2px; float: left;">
											请输入外部单位名称:
											<br><textarea rows="6" name="sendMainW" style="width:270px;overflow:visible"><s:property value='tDocSend.sendMainW'/></textarea>
											</div>

											<div style="width: 45%; margin-right: 2px; float: left;">
											请选择内部单位名称:
											<br><textarea rows="6" id="mainUnit" name="sendMain" readonly="readonly" style="width:270px;overflow:visible"><s:property value='tDocSend.sendMain'/></textarea>
											</div>
											<div style="width: 5%;padding-top: 70px; float: left;">
											<input type="button" id="selectMainUnit" value="选择"/>
											<input id="clearmain" type="button" value="清除"/>
											<font id="mainUnitTreeZone" class="deptTreeZone" root="" checkNode="mainUnitId" nodeId="mainUnitId" tnodeName="mainUnit"/>
											</div>

										</td>
								  	</tr>
								  	<tr class="content6">
										<td class="lableTd t_c" style="line-height:120px;">抄送单位</td>
										<td class="pl18" colspan="3">
											<div style="width: 45%; margin-right: 2px; float: left;">
											请输入外部单位名称:
											<br><textarea rows="6" name="snedCopy" style="width:270px;overflow:visible"><s:property value='tDocSend.snedCopy'/></textarea>
											</div>

											<div style="width: 45%; margin-right: 2px; float: left;">
											请选择内部单位名称:
											<br><textarea rows="6" id="copyUnit" name="sendInside" readonly="readonly" style="width:270px;overflow:visible"><s:property value='tDocSend.sendInside'/></textarea>
											</div>
											<div style="width: 5%;padding-top: 70px; float: left;">
											<input type="button" id="selectCopyUnit" value="选择"/>
											<input id="clearcopy" type="button" value="清除"/>
											<font id="copyUnitTreeZone" class="deptTreeZone" root="" checkNode="copyUnitId" nodeId="copyUnitId" tnodeName="copyUnit"/>
											</div>

										</td>
								  	</tr>

								  	<tr class="content6">
										<td class="lableTd t_c" style="line-height:120px;">内发</td>
										<td class="pl18" colspan="3">
											<div style="width: 90%; margin-right: 2px; float: left;">
											请选择内部单位名称:
											<br><textarea rows="6" id="reportUnit" name="sendReportW" readonly="readonly" style="width:98%;overflow:visible"><s:property value='tDocSend.sendReportW'/></textarea>
											</div>
											<div style="width: 5%;padding-top: 70px; float: left;">
											<input type="button" id="selectReportUnit" value="选择"/>
											<input id="clearreport" type="button" value="清除"/>
											<font id="reportUnitTreeZone" class="deptTreeZone" root="" checkNode="reportUnitId" nodeId="reportUnitId" tnodeName="reportUnit"/>
											</div>

										</td>
								  	</tr>
								  	<tr class="content7">
								  		<td class="lableTd t_c"><font class="redMark">*&nbsp;</font>相关附件</td>
								  		<td colspan="3">
								  			<input type="hidden" name="contentAtt" id='contentAttachmentId' value="<s:property value="tDocSend.contentAtt"/>"/>
										  	<iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="100%" id="contentAttachmentFrame" name="contentAttachmentFrame"
										  	src="<%=path %>/attach/loadFileList.action?fileGroup=contentAttachmentId&fileGroupName=projectAttachIdGroup&fileGroupId=<s:property value="tDocSend.contentAtt"/>&userName=<%=loginName %>&loginName=<%=loginName %>&procType=edit&targetType=frame&type=1"></iframe>
								  		</td>
								  	</tr>
									<!--
									<tr class="content7" id="ref_zone">
	                          	     	<td class="lableTd t_r"><font class="redMark">*&nbsp;</font>相关附件</td>
	                                    <td colspan="3">
	                                    <div style="display:block;white-space:nowrap;width:82%;float:left;">
										</div>
										&nbsp;
	                                    <input id="choice_ref" type="button" value="..." onclick="selectRef('ref_zone')"/>&nbsp;
	                                    <input id="clean_ref" type="button" value="清空" onclick="clearRef('ref_zone')"/>
	                                    <input type="hidden" id="ref_id_zone" name="ref_id_zone" />
	                                    <input type="hidden" id="ref_type_zone" name="ref_type_zone" value="update"/>
	                                    </td>
	                          	    </tr>
	                          	    -->
                          	    	<s:if test="#request.stepName!=null&&#request.stepName!=''">
									<jsp:include page="suggest1.jsp"/>
									</s:if>
									<s:else>
									<tr class="content7">
										<td class="lableTd t_r" >校对</td>
										<td colspan="3"></td>
									</tr>
									<tr class="content7">
										<td class="lableTd t_r" >正式文件</td>
										<td colspan="3"></td>
									</tr>
									</s:else>
									<!-- 规范性文件设置  仅行政发文-->

                                    <tr id="normativeForm" class="content6"
                                    <%if( model.getModelid()!=null && !"新发文流程".equals(model.getModelid())) {%>
                                            style="display:none;" <%} %>>
                                        <td class="lableTd t_r">规范性文件设置</td>
                                        <td class="pl18" colspan="3">
                                        <s:if test="#request.stepName!=null&&#request.stepName!=''">
                                            <jsp:include page="normative.jsp">
                                               <jsp:param name="viewFlag" value="1"/>
                                            </jsp:include>
                                        </s:if>
                                        <s:else>
                                            <jsp:include page="normative.jsp">
                                                <jsp:param name="isBegin" value="1"/>
                                            </jsp:include>
                                        </s:else>
                                       </td>
                                    </tr>

                                    <!-- 规范性文件设置 -->
	                          	    <tr class="content7">
	                                    <td style="width:50%;border-right:#000 1px solid;" colspan="2" >
	                                    	印制份数：&nbsp;
											<s:if test="#request.stepName=='排版'">
		                            	    	<input type="text" class="input_tiny" name="docCount" value="<s:property value='tDocSend.docCount'/>">份
		                            	    </s:if>
		                            	    <s:elseif test="tDocSend.docCount!=null">
		                            	    	<s:property value='tDocSend.docCount'/>份
		                            	    	<input type="hidden" value="<s:property value='tDocSend.docCount'/>" name="docCount"/>
		                            	    </s:elseif>
										</td>
	                                    <td style="width:50%" colspan="2">
	                                    	印发日期：&nbsp;
	                                    	<s:if test="#request.stepName=='套头'">
	                                    		<input type="hidden" id="sendDate" name="sendDate" value="<%=qfDate %>">
												<%=qfDate %>
	                                    	</s:if>
	                                    	<s:else>
	                                    		<input type="hidden" name="sendDate" value="<s:property value='tDocSend.sendDate'/>">
	                                    		<s:property value="tDocSend.sendDate"/>
	                                    	</s:else>
	                                    	</td>
	                          	    </tr>
	                          	    <s:if test="#request.stepName!=null&&#request.stepName!=''">
									<jsp:include page="suggest2.jsp"/>
									</s:if>

                          	  	</table>
                        	</div>
	                        <%-- --%>

							<div class="mb10 t_c">
								<div id="leaderTr" style="display:inline">
								部门领导：
								<select name="dept_leader" id="dept_leader" class="input_small">
									<option value="">---请选择---</option>
								</select>
								<font class="redMark">*&nbsp;</font>&nbsp;
								</div>
								<input type="button" id="formSubmit" value="提交" onclick="submitForm(this);"/>&nbsp;
								<input type="button" id="closeBtn" value="关闭" onclick="window.close()"/>&nbsp;
								<div id="formUpdate_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
							      <p style="width:auto;display:inline;"><img src="<%=path %>/send/images/loading.gif" style="display:inline;"/>
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


 	<div class="transparent" id="maskDiv" style="display: none;" style="filter:alpha(opacity=30);opacity:0.3;"></div>
 	<s:if test="#request.stepName=='审稿'">
		<jsp:include page="../../subProcess/draft/leader.jsp"/>
	</s:if>
	<s:elseif test="#request.stepName=='起草'||#request.stepName=='拟稿人修改'">
		<jsp:include page="../../subProcess/draft/update.jsp"/>
	</s:elseif>
	<s:elseif test="#request.stepName=='排版'">
		<jsp:include page="compose.jsp"/>
	</s:elseif>
	<s:elseif test="#request.stepName=='套头'">
		<jsp:include page="formalize.jsp"/>
	</s:elseif>
	</form>

	<div class="transparent" id="maskDiv1" style="display: none;" style="filter:alpha(opacity=30);opacity:0.3;"></div>
	<div id="handle_zone1" class="f_window" style="display: none;" >

		<h3 class="clearfix mb10">
			<span class="fl">发文类型</span>
			<div class="fr close" onclick="closeType()">关闭窗口</div>
		</h3>
		<div class="con">
			<table  width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr height="30">
					<td class="td_1">
						<input type="radio" name="type_choice" value="中共上海申通地铁集团有限公司委员会"/>
					</td>
					<td>党委发文</td>
				</tr>
				<tr height="30">
					<td class="td_1">
						<input type="radio" name="type_choice" value="中共上海申通地铁集团有限公司委员会办公室"/>
					</td>
					<td>党办发文</td>
				</tr>
				<tr height="30">
					<td class="td_1">
						<input type="radio" name="type_choice" value="中共上海申通地铁集团有限公司委员会br上海申通地铁集团有限公司"/>
					</td>
					<td>党政发文</td>
				</tr>
				<tr height="30">
					<td class="td_1">
						<input type="radio" name="type_choice" value="中共上海申通地铁集团有限公司委员会办公室br上海申通地铁集团有限公司办公室"/>
					</td>
					<td>两办发文</td>
				</tr>
				<tr height="30">
					<td class="td_1">
						<input type="radio" name="type_choice" value="上海申通地铁集团有限公司精神文明建设委员会"/>
					</td>
					<td>文明委发文</td>
				</tr>
				<tr height="30">
					<td class="td_1">
						<input type="radio" name="type_choice" value="上海申通地铁集团有限公司精神文明建设委员会办公室"/>
					</td>
					<td>文明办发文</td>
				</tr>
				<tr height="30">
					<td class="td_1">
						<input type="radio" name="type_choice" value="上海地铁志愿者服务总队"/>
					</td>
					<td>志愿者总队发文</td>
				</tr>
				<tr height="30">
					<td class="td_1">
						<input type="radio" name="type_choice" value="中共上海申通地铁集团有限公司机关委员会"/>
					</td>
					<td>机关党委发文</td>
				</tr>
				<tr height="30">
					<td class="td_1">
						<input type="radio" name="type_choice" value="中共上海申通地铁集团有限公司委员会br上海申通地铁集团有限公司br上海申通地铁集团有限公司工会委员会"/>
					</td>
					<td>党政工发文</td>
				</tr>
				<tr height="30">
					<td class="td_1">
						<input type="radio" name="type_choice" value="中共上海申通地铁集团有限公司委员会br党的群众路线教育实践活动领导小组文件"/>
					</td>
					<td>群众路线</td>
				</tr>
				<tr height="30">
					<td class="td_1">
						<input type="radio" name="type_choice" value="中共上海申通地铁集团有限公司委员会br党的群众路线教育实践活动领导小组办公室文件"/>
					</td>
					<td>群众路线办</td>
				</tr>
			</table>
		</div>
		<div class="button t_c">
			<input type="button" onclick="selectTypeFunc();" value="确 认">
		</div>
	</div>
</body>
</html>