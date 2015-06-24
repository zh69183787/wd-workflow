<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@page import="org.springframework.web.context.WebApplicationContext" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.wonders.send.approve.service.TApprovedinfoService" %>
<%@ page import="com.wonders.send.approve.model.bo.SendApprovedinfo" %>

<%
String path = request.getContextPath();

WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());	
TApprovedinfoService tApprovedinfoService=(TApprovedinfoService)context.getBean("send-tApprovedInfoService");
String pinstanceId = request.getParameter("pinstanceId");
String processName = request.getParameter("processName");
String hql11 = "from SendApprovedinfo t where t.process='"+processName+"' and t.incidentno='"+pinstanceId+"' order by t.upddate desc";
List<SendApprovedinfo> list11 = tApprovedinfoService.findByHQL(hql11);
String signName = "";
String defaultRemark = "";
String defaultFileGroupId = "";
if(list11!=null&&list11.size()>0){
	signName = list11.get(0).getSign();
	//System.out.println(signName);
	defaultRemark = list11.get(0).getRemark();
	if(defaultRemark==null){
		defaultRemark = "";
	}
	defaultFileGroupId = list11.get(0).getFileGroupId();
}
int defaultNumber = 0;
if(defaultFileGroupId==null){
	defaultFileGroupId = "";
}
if(!"".equals(defaultFileGroupId)){
	defaultNumber = 1;
}
 %>
<script>
function handleFunc(obj){
	if($.trim($("[name=suggest]").val())==""){
		if($("[name=choice]:checked").val()=="1"){
			$("[name=suggest]").val("拟同意。");
		}else if($("[name=choice]:checked").val()=="4"){
			alert("请输入备注！");
			$("[name=suggest]").focus();
			return false;
		}
	}
	
	if(confirm("确定处理完成？")){
		var formOptions1 = {
			cache:false,
			type:'post',
			callback:null,
			dataType :'json',
			url:contextpath+"/send-sign/leaderDeal.action",
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
		
		$(obj).attr("disabled",true);
		$("#handleClose").attr("disabled",true);
		$("#handle_zone_loading").show();
		if(newwindow&&newwindow.open&&!newwindow.closed){
			newwindow.close();
		}
		$("#form").ajaxSubmit(formOptions1);  
	}
}

$(function(){
	if($("#defaultSign").val()=="退回拟稿部门"){
		$("[name=choice]:eq(2)").attr("checked",true);
	}else if($("#defaultSign").val()=="其他意见"){
		$("[name=choice]:eq(3)").attr("checked",true);
	}else {
		$("[name=choice]:eq(0)").attr("checked",true);
	}
});

</script>
<div id="handle_zone" class="f_window" style="display:none;">
	<input type="hidden" id="defaultSign" value="<%=signName %>">
	<h3 class="clearfix mb10">
		<span class="fl">领导会签</span>
		<div class="fr close" id="handleDivClose">关闭窗口</div>
	</h3>
	<div class="con">
     	
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="1" checked="checked"/>
				</td>
				<td><b style="display:inline">拟同意</b>（对发文没有意见）</td>
			</tr>
	           	<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="2"/>
				</td>
				<td><b style="display:inline">退回秘书</b>（对秘书草拟的意见不认可,让其重新填入）</td>
			</tr>
	           	<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="3"/>
				</td>
				<td><b style="display:inline">退回拟稿部门</b>（对发文有意见，退回拟稿部门。可在备注中批注）</td>
			</tr>
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="4"/>
				</td>
				<td><b style="display:inline">其他意见</b>（对发文流程或下级的拟稿等有其他意见。请在备注中注明） </td>
			</tr>
	  		<tr height="30">
				<td class="td_1">
					
				</td>
				<td>备注
				<span class="fr" style="display:inline;">
                	<a name="suggest_attach" class="suggest_attach" target="#" >
					<input type="hidden" name="attachId" id="attachId_1" value="<%=defaultFileGroupId %>"/>
                	上传意见附件(<span style="display:inline;" id="fjcount_1" class="fjcount"><%=defaultNumber %></span>)</a>
                	<!--<a class="previewSuggest">备注预览</a>-->
               	</span>
				<br/>
				<textarea rows="3" name="suggest" id="suggest"><%=defaultRemark %></textarea>
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

