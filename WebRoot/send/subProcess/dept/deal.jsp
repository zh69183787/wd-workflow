<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>

<script>
function handleFunc(obj){
	if($("[name=choice]:checked").val()=="2"&&$.trim($("[name=suggest]:enabled").val())==""){
		alert("请填写意见！");
		$("[name=suggest]:enabled").focus();
		return false;
	}
	if(confirm("确定处理完成？")){
		var formOptions1 = {
			cache:false,
			type:'post',
			callback:null,
			dataType :'json',
			url:contextpath+"/send-deptSub/deal.action",
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

function chooseRadioFunc(num){
	$("[name=suggest]").val("");
	$("[name=suggest]").each(function(i){
		if((i+1)==num){
			$(this).attr("disabled",false);
		}else{
			$(this).attr("disabled",true);
		}
	});
	$("[name=attachId]").each(function(i){
		if((i+1)==num){
			$(this).attr("disabled",false);
		}else{
			$(this).attr("disabled",true);
		}
	});
}

$(function(){
	chooseRadioFunc(1);
});
</script>
<div id="handle_zone" class="f_window" style="display:none;">
	
	<h3 class="clearfix mb10">
		<span class="fl">部门业务人员处理</span>
		<div class="fr close" id="handleDivClose">关闭窗口</div>
	</h3>
	<div class="con">
     	
		<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice"  checked="checked" value="1" onclick="chooseRadioFunc(1)"/>
				</td>
				<td>本人没有意见</td>
			</tr>
			<tr height="30">
				<td class="td_1">
					
				</td>
				<td>
					备注：
					<span class="fr" style="display:inline;" id="fontId_1">
	                	<a name="suggest_attach" class="suggest_attach" target="#" >
						<input type="hidden" name="attachId" id="attachId_1" value=""/>
	                	上传意见附件(<span style="display:inline;" id="fjcount_1" class="fjcount">0</span>)</a>
	                	<!--<a class="previewSuggest">备注预览</a>-->
                	</span>
					<br/>
					<textarea rows="3" name="suggest"></textarea>
				</td>
			</tr>
		</table>
		
		<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice"  value="2" onclick="chooseRadioFunc(2)"/>
				</td>
				<td>本人有意见</td>
			</tr>
			<tr height="30">
				
				<td style="text-align:right;"><font style="color:red;">*</font></td>
				<td>
					意见：
					<span class="fr" style="display:inline;" id="fontId_2">
	                	<a name="suggest_attach" class="suggest_attach" target="#" >
						<input type="hidden" name="attachId" id="attachId_2" value=""/>
	                	上传意见附件(<span style="display:inline;" id="fjcount_2" class="fjcount">0</span>)</a>
	                	<!--<a class="previewSuggest">备注预览</a>-->
                	</span>
					<br/>
					<textarea rows="3" name="suggest" ></textarea>
				</td>
			</tr>
		</table>
		<!-- 
		<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice"  value="3"/>
				</td>
				<td>非本人处理业务</td>
			</tr>
			<tr height="30">
				<td class="td_1">
					
				</td>
				<td>
					备注：<span class="fr" style="display:inline;">
							  				<a name="suggest_attach" class="suggest_attach" target="#">
							  				<input type="hidden" name="attachId" id=" value=""/>
							  				上传意见附件(<span style="display:inline;" id="" class="fjcount">0</span>)
							  				</a>
							  				</span>
					<br/>
					<textarea rows="3" name="suggest" disabled="disabled"></textarea>
				</td>
			</tr>
		</table>
		 -->
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

