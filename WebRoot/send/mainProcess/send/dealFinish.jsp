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
<script type="text/javascript" src="<%=path %>/send/js/tDocSend/common.js"></script>
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
		document.form.shldId.value="";
		document.form.shldName.value="";
		document.form.shldDeptId.value="";
		document.form.shldmsId.value="";
		document.form.shldmsName.value="";
		document.form.shldmsDeptId.value="";
	}else{
		div1.style.display="";
		div2.style.display="none";
		document.getElementById('deptldName').options[0].selected=true;
		document.form.shldId.value="";
		document.form.shldName.value="";
		document.form.shldDeptId.value="";
		document.form.shldmsId.value="";
		document.form.shldmsName.value="";
		document.form.shldmsDeptId.value="";
	}
}


//王立俊 2010-07-28
//勾选办公室审核时给审核领导、秘书赋值 
//办公室签发时：金嘉模的秘书为俞楝 蔡伟东的秘书为董晓蕾
function toShldId(a){
	var a=a.value;
	//alert(a);
	if(a==0){//没有选中任何1个时，重置


		document.form.shldId.value="";
		document.form.shldName.value="";
		document.form.shldDeptId.value="";
		document.form.shldmsId.value="";
		document.form.shldmsName.value="";
		document.form.shldmsDeptId.value="";
		//alert("is 0");
	}else if(a==1){//选择金嘉模


		document.form.shldId.value='<%=deptshldId1%>';
		document.form.shldName.value='<%=deptshldName1%>';
		document.form.shldDeptId.value='<%=deptshldDeptId1%>';
		document.form.shldmsId.value='<%=deptshldmsId1%>';
		document.form.shldmsName.value='<%=deptshldmsName1%>';
		document.form.shldmsDeptId.value='<%=deptshldmsDeptId1%>';
		//alert("is 1");
	}else if(a==2){//选择蔡伟东


		document.form.shldId.value = '<%=deptshldId2%>';
		document.form.shldName.value='<%=deptshldName2%>';
		document.form.shldDeptId.value='<%=deptshldDeptId2%>';
		document.form.shldmsId.value='<%=deptshldmsId2%>';
		document.form.shldmsName.value='<%=deptshldmsName2%>';
		document.form.shldmsDeptId.value='<%=deptshldmsDeptId2%>';
		//alert("is 2");
	}
	//alert(a.value);
}

function handleFunc(obj){
	if($("[name=choice]:checked").val()=="2"&&$("[name=shldName]").val()==""){
		alert("请选择签发领导！");
		return false;
	}
	if(confirm("确定处理完成？")){
		var formOptions1 = {
			cache:false,
			type:'post',
			callback:null,
			dataType :'json',
			url:contextpath+"/send-tDocSend/dealFinish.action",
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

$(function(){
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
})

function chooseRadioFunc(num){
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
	<font id="shldTreeZone" class="deptTreeZone" root="2500" checkNode="shldUserId" nodeId="shldUserId"
     	nodeLoginName="shldId"	nodeDeptId="shldDeptId" tnodeName="shldName" 
		nodeMsLoginName="shldmsId" nodeMsDeptId="shldmsDeptId" nodeMsName="shldmsName"></font>
		<font id="hqldTreeZone" class="deptTreeZone" root="2500" checkNode="hqldUserId" nodeId="hqldUserId"
     	nodeLoginName="hqldId"	nodeDeptId="hqldDeptId" tnodeName="hqldName" 
		nodeMsLoginName="hqldmsId" nodeMsDeptId="hqldmsDeptId" nodeMsName="hqldmsName"></font>
	<h3 class="clearfix mb10">
		<span class="fl">办结</span>
		<div class="fr close" id="handleDivClose">关闭窗口</div>
	</h3>
	<div class="con">
     	<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice"  checked="checked" value="1" onclick="chooseRadioFunc(1);"/>
				</td>
				<td>同意签发</td>
			</tr>
			<tr height="30">
				<td class="td_1">
					
				</td>
				<td>
					<span class="fl" >备注：</span>
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
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="2" onclick="chooseRadioFunc(2);"/>
				</td>
				<td>重新拟办</td>
			</tr>
	           	<tr height="30">
	           		<td></td>
	  			<td>
	  				协办部门：
	  				<input type="text" id="helpUnit" name="xbdeptName" readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="hidden" id="helpUnitId" name="xbdeptId"/>
					<input type="button" id="selectHelpUnit" value="..."/>&nbsp;<input id="clearhelp" type="button" value="清除"/>
					<font id="helpUnitTreeZone" class="deptTreeZone" root="" checkNode="helpUnitId" nodeId="helpUnitId" tnodeName="helpUnit"/>
	  			</td>
					</tr>
	  		<tr height="30">
	  			<td ></td>
	  			<td>
	  				会签领导：
	  				<input type="text" id="hqldName" name="hqldName" readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="hidden" id="hqldUserId" name="hqldUserId" value="">
	  				<input type="hidden" id="hqldId" name="hqldId" value="">
	  				<input type="hidden" id="hqldDeptId" name="hqldDeptId" value="">
	  				<input type="hidden" id="hqldmsName" name="hqldmsName" value="">
	  				<input type="hidden" id="hqldmsId" name="hqldmsId" value="">
	  				<input type="hidden" id="hqldmsDeptId" name="hqldmsDeptId" value="">
	  				<input type="button" name="chooseHqld" value="..." class="btn"/>
	  				<input type="button" name="clearHqld" value="清除" class="btn"/>
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
	  				<input type="hidden" id="shldUserId" name="shldUserId" value="">
	  				<input type="hidden" id="shldId" name="shldId" value="">
	  				<input type="hidden" id="shldDeptId" name="shldDeptId" value="">
	  				<input type="hidden" id="shldmsName" name="shldmsName" value="">
	  				<input type="hidden" id="shldmsId" name="shldmsId" value="">
	  				<input type="hidden" id="shldmsDeptId" name="shldmsDeptId" value="">
	  				<input type="button" name="chooseShld" value="..." class="btn"/>
	  				<input type="button" name="clearShld" value="清除" class="btn"/>
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
				  			<td class="td_1" style="text-align:right;">&nbsp;</td>
		                	<td textareaId="suggest1" peopleId="choiceZone1">
		                	<span class="fl">
		                	意见：<input type="hidden" name="attachId" value=""></span>
		                	<!--  
		                	<span class="fr" style="display:inline;"><a class="previewSuggest">意见预览</a></span>
		                	-->
		                	</td>
		              	</tr>
		              	<tr>
		              		<td class="td_1">&nbsp;</td>
		                	<td><textarea name="suggest2" rows="3"></textarea></td>
		              	</tr>
	              	</table>
           		</td>
           	</tr>
		</table>
		
		<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="3" onclick="chooseRadioFunc(3);"/>
				</td>
				<td>返回修改</td>
			</tr>
			<tr height="30">
				<td class="td_1">
					
				</td>
				<td>
					备注：
	  				<span class="fr" style="display:inline;" id="fontId_1">
	                	<a name="suggest_attach" class="suggest_attach" target="#" >
						<input type="hidden" name="attachId" id="attachId_2" value=""/>
	                	上传意见附件(<span style="display:inline;" id="fjcount_2" class="fjcount">0</span>)</a>
	                	<!--<a class="previewSuggest">备注预览</a>-->
                	</span>
					<br/>
					<textarea rows="3" name="suggest3"></textarea>
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

