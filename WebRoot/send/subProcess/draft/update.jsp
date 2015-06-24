<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
%>
<script>
var if_cancel = "no";

function handleFunc(obj){
	if(checkForm()){
		if(confirm("确定处理完成？")){
			var formOptions = {
				cache:false,
				type:'post',
				callback:null,
				dataType :'json',
				url:contextpath+"/send-tDocSend/updateForm.action",
			    success:function(){
					alert("提交成功！");
					if(if_cancel=="no"){
						window.location.href = contextpath+"/send-tDocSend/toFormPage.action?incidentNo="+$("[name=incidentNo]").val()
												+"&modelName="+encodeURI($("[name=modelName]").val())
												+"&processName="+encodeURI($("[name=processName]").val())
												+"&pinstanceId="+$("[name=pinstanceId]").val()
												+"&taskId="+$("[name=taskId]").val()
												+"&taskUserName="+encodeURI($("[name=taskUserName]").val());
						//$("#formUpdate").ajaxSubmit(formOptions1); 
					}else{
						window.close();
					}
					return false;
			    }
			};
			
			var formOptions1 = {
				cache:false,
				type:'post',
				callback:null,
				dataType :'json',
				url:contextpath+"/send-draftSubAction/draftUpdate.action",
			    success:function(data){
					//var obj = JSON.parse(data);
					//$("#formUpdate").submit();
					//$("#formUpdate").ajaxSubmit(formOptions); 
					if(data!=null&&data.if_success=="yes"){
						if(data.if_cancel=="yes"){
							if_cancel = "yes";
						}
						$("#formUpdate").ajaxSubmit(formOptions); 
					}else{
						alert("提交失败，请联系管理员！");
					}
					return false;
			    }
			};
			$(obj).attr("disabled",true);
			$("#handleClose").attr("disabled",true);
			$("#handle_zone_loading").show();
			if(newwindow&&newwindow.open&&!newwindow.closed){
				newwindow.close();
			}
			$("#formUpdate").ajaxSubmit(formOptions1); 
		}
	}
}
</script>
<div id="handle_zone" class="f_window" style="width:600px;display:none;">
	
	<h3 class="clearfix mb10">
		<span class="fl"><s:property value='#request.stepName'/></span>
		<div class="fr close" id="handleDivClose">关闭窗口</div>
	</h3>
	<div class="con">
     	<jsp:include page="../../mainProcess/send/normative.jsp"/>
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" checked="checked" name="choice" value="1"/>
				</td>
				<td>根据意见修改发文</td>
			</tr>
			<tr height="30">
				<td class="td_1">
					
				</td>
				<td style="color:red">注：提交之前请确认发文表单中数据已修改完成</td>
			</tr>
	           	<tr height="30">
	           		<td></td>
	  			<td>
	  				备注：<br/>
	  				<textarea rows="3" name="suggest"></textarea>
	  			</td>
					</tr>
	  		
		</table>
		
		<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="0"/>
				</td>
				<td>取消本次发文</td>
			</tr>
			
		</table>
	</div>
	<div class="button t_c">
		<input id="handleSubmit" type="button" onclick="handleFunc(this);" value="确 认">
		<input id="handleClose" type="button" value="取 消">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path %>/send/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>

