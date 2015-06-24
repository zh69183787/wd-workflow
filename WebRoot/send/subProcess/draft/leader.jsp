<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();

//王立俊 2010-07-28
//发文审稿时添加办公室审核时的处理人及其秘书信息 
String deptshldId1="ST/G001000000152502";
String deptshldName1="吴昕毅";
String deptshldDeptId1="2502";
String deptshldmsId1="ST/G020106000032502";
String deptshldmsName1="朱岳方";
String deptshldmsDeptId1="2502";

String deptshldId2="ST/G001000001362510";
String deptshldName2="蔡伟东";
String deptshldDeptId2="2510";
String deptshldmsId2="ST/G001000001512510";
String deptshldmsName2="董晓蕾";
String deptshldmsDeptId2="2510";
 %>
<script>
//王立俊 2010-08-03 	
 //判断是否勾选办公室审核
 //默认显示签发领导，若勾选办公室签发单选框，则隐藏，反之亦然


function isShlded(a){
	var div1=document.getElementById('div1');
	var div2=document.getElementById('div2');
	//alert(a.checked);
	if(a.checked){
		div1.style.display="none";
		div2.style.display="";
		//alert(document.getElementById('deptldName').options[0].selected);
		document.getElementById('deptldName').options[0].selected=true;
		document.formUpdate.shldId.value="";
		document.formUpdate.shldName.value="";
		document.formUpdate.shldDeptId.value="";
		document.formUpdate.shldmsId.value="";
		document.formUpdate.shldmsName.value="";
		document.formUpdate.shldmsDeptId.value="";
	}else{
		div1.style.display="";
		div2.style.display="none";
		document.getElementById('deptldName').options[0].selected=true;
		document.formUpdate.shldId.value="";
		document.formUpdate.shldName.value="";
		document.formUpdate.shldDeptId.value="";
		document.formUpdate.shldmsId.value="";
		document.formUpdate.shldmsName.value="";
		document.formUpdate.shldmsDeptId.value="";
	}
}


//王立俊 2010-07-28
//勾选办公室审核时给审核领导、秘书赋值 
//办公室签发时：金嘉模的秘书为俞楝 蔡伟东的秘书为董晓蕾
function toShldId(a){
	var a=a.value;
	//alert(a);
	if(a==0){//没有选中任何1个时，重置


		document.formUpdate.shldId.value="";
		document.formUpdate.shldName.value="";
		document.formUpdate.shldDeptId.value="";
		document.formUpdate.shldmsId.value="";
		document.formUpdate.shldmsName.value="";
		document.formUpdate.shldmsDeptId.value="";
		//alert("is 0");
	}else if(a==1){//选择金嘉模


		document.formUpdate.shldId.value='<%=deptshldId1%>';
		document.formUpdate.shldName.value='<%=deptshldName1%>';
		document.formUpdate.shldDeptId.value='<%=deptshldDeptId1%>';
		document.formUpdate.shldmsId.value='<%=deptshldmsId1%>';
		document.formUpdate.shldmsName.value='<%=deptshldmsName1%>';
		document.formUpdate.shldmsDeptId.value='<%=deptshldmsDeptId1%>';
		//alert("is 1");
	}else if(a==2){//选择蔡伟东


		document.formUpdate.shldId.value = '<%=deptshldId2%>';
		document.formUpdate.shldName.value='<%=deptshldName2%>';
		document.formUpdate.shldDeptId.value='<%=deptshldDeptId2%>';
		document.formUpdate.shldmsId.value='<%=deptshldmsId2%>';
		document.formUpdate.shldmsName.value='<%=deptshldmsName2%>';
		document.formUpdate.shldmsDeptId.value='<%=deptshldmsDeptId2%>';
		//alert("is 2");
	}
	//alert(a.value);
}

function handleFunc(obj){
	if(checkForm()){
		if($("[name=choice]:checked").val()=="1"){
			if($("#shldName").val()==""){
				alert("请选择签发领导！");
				return false;
			}
			if($.trim($("[name=suggest]").val())==""){
				alert("请输入意见！");
				$("[name=suggest]").focus();
				return false;
			}
			if($("#hqldName").val().indexOf($("#shldName").val())>-1){
				alert("会签领导和签发领导重复，请重新选择！");
				return false;
			}
		}
		
		if(confirm("确定处理完成？")){
			var formOptions1 = {
				cache:false,
				type:'post',
				callback:null,
				dataType :'json',
				url:contextpath+"/send-draftSubAction/leaderUpdate.action",
			    success:function(data){
					//var obj = JSON.parse(data);
					//$("#formUpdate").submit();
					//$("#formUpdate").ajaxSubmit(formOptions); 
					if(data!=null&&data.if_success=="yes"){
						$("#formUpdate").ajaxSubmit(formOptions); 
						
					}else{
						alert("提交失败，请联系管理员！");
					}
					
					return false;
			    }
			};
			
			var formOptions = {
				cache:false,
				type:'post',
				callback:null,
				dataType :'json',
				url:contextpath+"/send-tDocSend/updateForm.action",
			    success:function(){
					alert("提交成功！");
					window.location.href = contextpath+"/send-tDocSend/toFormPage.action?incidentNo="+$("[name=incidentNo]").val()
											+"&modelName="+encodeURI($("[name=modelName]").val())
											+"&processName="+encodeURI($("[name=processName]").val())
											+"&pinstanceId="+$("[name=pinstanceId]").val()
											+"&taskId="+$("[name=taskId]").val()
											+"&taskUserName="+encodeURI($("[name=taskUserName]").val())
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

function showSuggest(){
	var helpUnitText = $("#helpUnit").val();
	var hqldNameText = $("#hqldName").val();
	var shldNameText = $("#shldName").val();
	var showText = "";
	if(helpUnitText!=""){
		showText += "请"+helpUnitText+"阅处。";
	}
	if(hqldNameText!=""){
		showText += "请"+hqldNameText+"阅示。";
	}
	if(shldNameText!=""){
		showText += "请"+shldNameText+"签发。";
	}
	$("#suggest").val(showText);
}

$(function(){
	initButton();
})
</script>

<div id="handle_zone" class="f_window" style="width:600px;display:none;">
	
	<h3 class="clearfix mb10">
		<span class="fl">领导审核</span>
		<div class="fr close" id="handleDivClose">关闭窗口</div>
	</h3>
	<div class="con">
     	<font id="shldTreeZone" class="deptTreeZone" root="2500" checkNode="shldUserId" nodeId="shldUserId"
     	nodeLoginName="shldId"	nodeDeptId="shldDeptId" tnodeName="shldName" 
		nodeMsLoginName="shldmsId" nodeMsDeptId="shldmsDeptId" nodeMsName="shldmsName"></font>
		<font id="hqldTreeZone" class="deptTreeZone" root="2500" checkNode="hqldUserId" nodeId="hqldUserId"
     	nodeLoginName="hqldId"	nodeDeptId="hqldDeptId" tnodeName="hqldName" 
		nodeMsLoginName="hqldmsId" nodeMsDeptId="hqldmsDeptId" nodeMsName="hqldmsName"></font>
		
		<jsp:include page="../../mainProcess/send/normative.jsp"/>
		
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			
			<tr height="30">
				<td class="td_1">
					<input type="radio" checked="checked" name="choice" value="1"/>
				</td>
				<td>业务转发（不进行具体业务处理）</td>
			</tr>
			<tr height="30">
				<td class="td_1">
					
				</td>
				<td style="color:red">注：提交之前请确认发文表单中数据已修改完成</td>
			</tr>
	           	<tr height="30">
	           		<td></td>
	  			<td>
	  				协办部门：
	  				<input type="text" id="helpUnit" name="xbdeptName" readonly class="inputLine" style="width:250px;height:22px"/>
					<input type="button" id="selectHelpUnit" value="..."/>&nbsp;<input id="clearhelp" type="button" value="清除"/>
					<input type="hidden" id="helpUnitId" name="xbdeptId"/>
					<font id="helpUnitTreeZone" class="deptTreeZone" root="" checkNode="helpUnitId" nodeId="helpUnitId" tnodeName="helpUnit"/>
	  			</td>
					</tr>
	  		<tr height="30">
	  			<td ></td>
	  			<td>
	  				会签领导：
	  				<input type="text" id="hqldName" name="hqldName" readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="button" name="chooseHqld" value="..." class="btn"/>
	  				<input type="button" name="clearHqld" value="清除" class="btn"/>
	  				<input type="hidden" id="hqldUserId" name="hqldUserId" value="">
	  				<input type="hidden" id="hqldId" name="hqldId" value="">
	  				<input type="hidden" id="hqldDeptId" name="hqldDeptId" value="">
	  				<input type="hidden" id="hqldmsName" name="hqldmsName" value="">
	  				<input type="hidden" id="hqldmsId" name="hqldmsId" value="">
	  				<input type="hidden" id="hqldmsDeptId" name="hqldmsDeptId" value="">
	  			</td>
	  		</tr>
	  		<tr height="30">
	  			<td ></td>
	  			<td>
	  				<input type="checkbox" name="isShld" value="1" onclick="isShlded(this)">
	  				集团领导已批示同意，由办公室签发  <!--  待补-->
	  				
	  			</td>
	  		</tr>
	  		<tr height="30" id="div1">
	  			<td style="text-align:right;"><font style="color:red;">*</font></td>
	  			<td>
	  				签发领导：
	  				<input type="text" id="shldName" name="shldName"  readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="button" name="chooseShld" value="..." class="btn"/>
	  				<input type="button" name="clearShld" value="清除" class="btn"/>
	  				<input type="hidden" id="shldUserId" name="shldUserId" value="">
	  				<input type="hidden" id="shldId" name="shldId" value="">
	  				<input type="hidden" id="shldDeptId" name="shldDeptId" value="">
	  				<input type="hidden" id="shldmsName" name="shldmsName" value="">
	  				<input type="hidden" id="shldmsId" name="shldmsId" value="">
	  				<input type="hidden" id="shldmsDeptId" name="shldmsDeptId" value="">
	  			</td>
	  		</tr>
	  		<tr height="30" style="display: none;"  id="div2">
	  			<td style="text-align:right;"><font style="color:red;">*</font></td>
	  			<td>
	  				办公室：
	  				<select id="deptldName" name="deptldName" onchange="toShldId(this)">
						<option value="0">请选择</option>
						<option value="1"><%=deptshldName1%></option>
						<option value="2"><%=deptshldName2%></option>
					</select>
	  			</td>
	  		</tr>
	  		<tr>
	  			<td colspan=2>
			  		<table id="suggest1" width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
				  			<td class="td_1" style="text-align:right;">&nbsp;<font style="color:red;display:inline;">*</font></td>
		                	<td textareaId="suggest1" peopleId="choiceZone1">
		                	<span class="fl">
		                	意见：</span>
		                	<span class="fr" style="display:inline;"><a class="createSuggest" href="javascript:void(0)" onclick="showSuggest();">生成意见</a></span>
		                	<!--  
		                	<span class="fr" style="display:inline;"><a class="previewSuggest">意见预览</a></span>
		                	-->
		                	</td>
		              	</tr>
		              	<tr>
		              		<td class="td_1">&nbsp;</td>
		                	<td><textarea id="suggest" name="suggest" rows="3"></textarea></td>
		              	</tr>
	              	</table>
           		</td>
           	</tr>
		</table>
		
		<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice"  value="0"/>
				</td>
				<td>返回修改</td>
			</tr>
			
			<tr height="30" >
				<td class="td_1">
					
				</td>
				<td>
					备注：<br/>
					<textarea rows="3" name="suggest2"></textarea>
				</td>
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

