<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<script>
function handleFunc(obj){
	if(confirm("确定处理完成？")){
		var formOptions1 = {
			cache:false,
			type:'post',
			callback:null,
			dataType :'json',
			url:contextpath+"/send-tDocSend/deal.action",
		    success:function(data){
				//var obj = JSON.parse(data);
				//$("#formUpdate").submit();
				//$("#formUpdate").ajaxSubmit(formOptions);
				if(data!=null&&data.if_success=="yes"){
					alert("提交成功！");
					window.location.href = contextpath+"/send-tDocSend/toFormPage.action?incidentNo="+$("[name=incidentNo]").val()
											+"&modelName="+encodeURI($("[name=modelName]").val())
											+"&processName="+encodeURI($("[name=processName]").val())
											+"&pinstanceId="+$("[name=pinstanceId]").val()
											+"&taskId="+$("[name=taskId]").val()
											+"&taskUserName="+encodeURI($("[name=taskUserName]").val())
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
		$("#form").ajaxSubmit(formOptions1);  
	}
}
</script>
<div id="handle_zone" class="f_window" style="display:none;">
	
	<h3 class="clearfix mb10">
		<span class="fl">校稿</span>
		<div class="fr close" id="handleDivClose">关闭窗口</div>
	</h3>
	<div class="con">


		<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					
				</td>
				<td>
					备注：
					<span class="fr" style="display:inline;">
	                	<a name="suggest_attach" class="suggest_attach" target="#" >
						<input type="hidden" name="attachId" id="attachId_1" value=""/>
	                	上传意见附件(<span style="display:inline;" id="fjcount_1" class="fjcount">0</span>)</a>
	               	</span>
					<br/>
					<textarea rows="3" name="suggest" id="suggest"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<div class="button t_c">
		<input id="handleSubmit" type="button" value="确 认" onclick="handleFunc(this);">
		<input id="handleClose" type="button" value="取 消">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path %>/send/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>

