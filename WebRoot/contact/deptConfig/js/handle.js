var handleOptions = {
	cache:false,
	type:'post',
	callback:null,
	url:'',
    success:function(data){
			if(data=="1"){
				alert("操作成功");
				$('#handleZone').dialog("close");
				sub();
			}else{
				alert("操作失败！");
				$('#handleZone').dialog("close");
			}
		return false;
    }
};


function afterSubmit(){
	
}

function initDialog(){
	// Dialog
	$('#handleZone').dialog({
		autoOpen: false,
		height: 500,
		width: 550,
		modal: true,
		buttons: {
			"Ok": function() {
				$("#handleForm").ajaxSubmit(handleOptions);
			},
			"Cancel": function() {
				$(this).dialog("close");
			}
		}
	});
}


function initSelect(){
	
	$.getJSON(contextpath+"/contact-deptConfig/queryRelation.action",{
			},
			function(json){
				if(json){
					if(json.length>0){
						var s = "";
						for(i=0;i<json.length;i++){
							s += "<option value='"+json[i].nodeId+"'>"+json[i].name+"</option>";
						}
						$("#configDeptId").html(s);
					}
				}
			}
		);
}

function clearPeople(idObj,nameObj,loginNameObj){
	if(confirm("确认清除吗？")){
		$(idObj).attr("value","");
		$(nameObj).attr("value","");
		$(loginNameObj).attr("value","");
	}
}


function selectDeptUser(zoneId){
	var url = treepath+"/organTree/deptUserTree.jsp?zoneId="+zoneId+"&random="+Math.random();
	window.open(url);
}

$(document).ready(function(){
	$("#configDeptName").change(function(){
		$("#configDeptName").val($("#configDeptId").find("option:selected").text());
	})
	$("#create").click(function() {
		$("#handleZone").dialog("open");
		$("#configDeptName").val($("#configDeptId").find("option:first").text());
		handleOptions.url = contextpath+"/contact-deptContact/saveConfig.action";
	});
	$(".edit").live("click",function() {
		$("#handleZone").dialog("open");
		$("#configId").val($(this).parent().parent("tr").attr("id"));
		$("#configDeptId").val($(this).parent().parent("tr").attr("deptId"));
		$("#configDeptName").val($(this).parent().parent("tr").attr("deptName"));
		$("#configLoginName").val($(this).parent().parent("tr").attr("loginName"));
		$("#configUserName").val($(this).parent().parent("tr").attr("userName"));
		handleOptions.url = contextpath+"/contact-deptContact/updateConfig.action";
	});
	$(".switch").live("click",function() {
		$("#configId").val($(this).parent().parent("tr").attr("id"));
		$("#flag").val($(this).parent().parent("tr").attr("flag"));
		handleOptions.url = contextpath+"/contact-deptContact/switchConfig.action";
		$("#handleForm").ajaxSubmit(handleOptions);
	});
	initDialog();
	initSelect();
})

	