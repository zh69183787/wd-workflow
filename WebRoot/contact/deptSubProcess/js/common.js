var optionsCheck = {
	formId:'',
	cache:false,
	type:'post',
	url:'',
    success:function(data){
		var obj = JSON.parse(data);
		if(obj){
			if(obj.checkFlag){
				//console.log("效验成功");
				if(confirm("确认提交吗？")){
					if(typeof(defaultSuggest)!='undefined'&&typeof(defaultSuggest)=="function"){
						//alert("type:"+typeof(defaultSuggest));
						defaultSuggest();
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
	url:'',
    success:function(data){
		var obj = JSON.parse(data);
		if(obj){
			if(obj.checkFlag){
				alert("操作成功");
				//console.log("效验成功");
				if(obj.url!=""){
					window.location.href = contextpath+obj.url+"&rand="+Math.random();
				}
			}else{
				alert("操作失败");
				var errorLog = showError(obj.errors);
				$("#"+options.submitZone+"_loading").hide();
				submitZoneControl(options.submitZone,false);
			}
		}else{
			alert("操作失败！");
			$("#"+options.submitZone+"_loading").hide();
			submitZoneControl(options.submitZone,false);
		}
		//closeMessageBox();
		/**/
		return false;
    }
};



function selectDeptUser(zoneId){
	var url = treepath+"/organTree/deptUserTree.jsp?zoneId="+zoneId+"&random="+Math.random();
	window.open(url);
}

function selectDeptLeader(zoneId){
	var url = treepath+"/organTree/deptLeaderTree.jsp?zoneId="+zoneId+"&random="+Math.random();
	window.open(url);
}

/**/
function deptLeader(id,callback){
	if($("#"+id).length==0) return false;
	var url = contextpath+"/contact-deptcontact/signLeader.action";
	var str = "";
	$.getJSON(
		url,{
		},
		function(json){
			if(json){
				if(json.length>0){
					var str = "";
					for(i=0;i<json.length;i++){
						str+="<option value='"+json[i].loginName+"'>"+json[i].name+"</option>";
					}
					if(str!="") $("#"+id).append(str);

					if(typeof(callback)=="function") callback();
				}
			}
		}
	);
}

