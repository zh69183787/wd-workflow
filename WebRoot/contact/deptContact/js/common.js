var optionsCheck = {
	formId:'',
	needSuggest:true,
	cache:false,
	type:'post',
	url:'',
    success:function(data){
		var obj = JSON.parse(data);
		if(obj){
			if(obj.checkFlag){
				if(confirm("确认提交吗？")){
					if(this.needSuggest){
						if(typeof defaultSuggest!='undefined'&&typeof(defaultSuggest)=="function"){
							defaultSuggest();
						}
					}
					$("#"+this.formId).find("input[name='checkOnly']").attr("value","");
					options.url = this.url;
					submitZoneControl(options.submitZone,true);
					$("#"+options.submitZone+"_loading").show();
				
					$("#"+this.formId).ajaxSubmit(options);
				}else{
					return false;
				}
			}else{
				var errorLog = showError(obj.errors);
				alert("操作失败！"+errorLog);
				if(obj.refresh){
					window.location.reload();
				}
			}
		}else{
			alert("操作失败！");
		}
		//closeMessageBox();
		/**/
		return false;
    }
};

var options = {
	submitZone:'',
	cache:false,
	type:'post',
	callback:null,
	url:'',
    success:function(data){
		var obj = JSON.parse(data);
		if(obj){
			//console.log(options);
			if(obj.checkFlag){
				alert("操作成功");
				$("#modify").attr("value","");
				$("#"+"formUpdate"+"_loading").hide();
				submitZoneControl("formUpdate",false);
				if(obj.url!=""){
					window.location.href = contextpath+obj.url+"&rand="+Math.random();
				}
				if(typeof(this.callback)=="function") this.callback();
			}else{
				var errorLog = showError(obj.errors);
				alert("操作失败！"+errorLog);
				
				$("#"+options.submitZone+"_loading").hide();
				submitZoneControl(options.submitZone,false);
			}
		}else{
			alert("操作失败！");
			$("#"+options.submitZone+"_loading").hide();
			submitZoneControl(options.submitZone,false);
		}
		return false;
    }
};

$(document).ready(function(){
	
	$("#selectMainUnit").click(function(){
		selectDeptMain("mainUnitTreeZone");
	})

	$("#selectCopyUnit").click(function(){
		selectDeptCopy("copyUnitTreeZone");
	})

	$('#contactDate').datepicker({
		changeYear:true,
		changeMonth:true
	});
	
	$('#replyDate').datepicker({
		changeYear:true,
		changeMonth:true
	});
	
	$("#clearmain").click(function(){
		return clearMainUnit();
	})
	
	$("#clearcopy").click(function(){
		return clearCopyUnit();
	})
});

function signLeader(id,callback){
	if($("#"+id).length==0) return false;
	var url = contextpath+"/contact-deptContact/signLeader.action";
	//var url = "/organTree/getDeptLeaders.action";
	var str = "";
	$.getJSON(
		url,{taskuser:$("#taskuser").val() ,rand:Math.random()},
		function(json){
			if(json){
				if(json.length>0){
					var str = "";
					for(i=0;i<json.length;i++){
						str+="<option value='"+json[i].loginName+"'>"+json[i].name+"</option>";
					}
					if(str!="") $("#"+id).append(str);

					if(typeof callback!='undefined'&&typeof(callback)=="function") callback();
				}
			}
		}
	);
}

function selectDeptMain(zoneId){
	selectDept(zoneId);
}

function selectDeptCopy(zoneId){
	selectDept(zoneId);
}

function selectDept(zoneId){
	var url = treepath+"/organTree/deptTree.jsp?zoneId="+zoneId+"&random="+Math.random();
	window.open(url);
}

function clearMainUnit(){
	if(confirm("是否清除主送部门？")){
		$("#mainUnitId").attr("value","").trigger('change');
		$("#mainUnit").attr("value","");
	}
	return false;
}

function clearCopyUnit(){
	if(confirm("是否清除抄送部门？")){
		$("#copyUnitId").attr("value","").trigger('change');
		$("#copyUnit").attr("value","");
	}
	return false;
}

