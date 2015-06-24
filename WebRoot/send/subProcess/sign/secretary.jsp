<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<script>
function handleFunc(obj,type){
	$("[name=type]").val(type);
	if(confirm("确定处理完成？")){
		var formOptions1 = {
			cache:false,
			type:'post',
			callback:null,
			dataType :'json',
			url:contextpath+"/send-sign/secretaryDeal.action",
		    success:function(data){
				//var obj = JSON.parse(data);
				//$("#formUpdate").submit();
				//$("#formUpdate").ajaxSubmit(formOptions); 
				if(data!=null&&data.if_success=="yes"){
					alert("提交成功!");
					window.location.href = contextpath+"/send-tDocSend/toFormPage.action?incidentNo="+$("[name=incidentNo]").val()
											+"&modelName="+encodeURI($("[name=modelName]").val())
											+"&processName="+encodeURI($("[name=processName]").val())
											+"&pinstanceId="+$("[name=pinstanceId]").val()
											+"&taskId="+$("[name=taskId]").val()
											+"&taskUserName="+encodeURI($("[name=taskUserName]").val());
				}else{
					alert("提交失败，请联系管理员");
				}
				
				return false;
		    }
		};
		
		$("#leaderDeal").attr("disabled",true);
		$("#handleSubmit").attr("disabled",true);
		$("#handleClose").attr("disabled",true);
		$("#handle_zone_loading").show();
		if(newwindow&&newwindow.open&&!newwindow.closed){
			newwindow.close();
		}
		$("#form").ajaxSubmit(formOptions1);  
	}
}

function chooseRadioFunc(num){
	if(num==4){
		$("#leaderDeal").hide();
	}else{
		$("#leaderDeal").show();
	}
}


</script>
<div id="handle_zone" class="f_window" style="display:none;">
	<input type="hidden" name="type">
	<h3 class="clearfix mb10">
		<span class="fl">会签领导秘书</span>
		<div class="fr close" id="handleDivClose">关闭窗口</div>
	</h3>
	<div class="con">
     	
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" checked="checked" value="1" onclick="chooseRadioFunc(1);"/>
				</td>
				<td><b style="display:inline">拟同意</b>（对发文没有意见）</td>
			</tr>
	           	<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice"  value="2" onclick="chooseRadioFunc(2);"/>
				</td>
				<td><b style="display:inline">退回拟稿部门</b>（对发文有意见，退回拟稿部门。可在备注中批注）</td>
			</tr>
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice"  value="3" onclick="chooseRadioFunc(3);"/>
				</td>
				<td><b style="display:inline">其他意见</b>（对发文流程或下级的拟稿等有其他意见。请在备注中注明） </td>
			</tr>
				<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice"  value="4" onclick="chooseRadioFunc(4);"/>
				</td>
				<td>领导暂时不能处理，<b style="display:inline">退回办公室</b>（请在备注中注明原因）
				</td>
			</tr>
	  		<tr height="30">
				<td class="td_1">
					
				</td>
				<td><span class="fl" style="display:inline;">备注</span>
				<span class="fr" style="display:inline;">
                	<a name="suggest_attach" class="suggest_attach" target="#">
					<input type="hidden" name="attachId" id="attachId_1"  value=""/>
                	上传意见附件(<span style="display:inline;" id="fjcount_1" class="fjcount">0</span>)</a>
                	
               	</span>
				<br/>
				<textarea rows="3" id="suggest" name="suggest"></textarea>
				</td>
			</tr>
	  		
	  		
		</table>
		
		
	</div>
	<div class="button t_c">
		<input type="button" id="leaderDeal" value="代领导处理" onclick="handleFunc(this,'1');">
		<input id="handleSubmit" type="button" value="确 认" onclick="handleFunc(this,'0');">
		<input id="handleClose" type="button" value="取 消">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path %>/send/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>

