
$(document).ready(function(){
	var css = "<style>"+
		".ref_single{float:left;height:auto;display:block;margin-left:0px;BORDER-BOTTOM: 1px inset #E1E1E1;}"+
		".ref_single a{float:left}"+
		".ref_single img{float:left}" +
		"</style>";
	
	$("head").append(css)
	
	
	generateRefZone("ref_zone");
});

function selectRef(zoneId){
	var ids = $("#"+zoneId).find("#ref_id_zone").val();
	var url = contextpath+"/contact-deptContact/refCheckList.action?zoneId="+zoneId+"&ids="+ids+"&random="+Math.random();
	window.open(url);
}

function clearRef(zoneId){
	if(confirm("确认清空吗？")){
		$("#"+zoneId).find("#ref_id_zone").val("");
		generateRefZoneWithRefresh(zoneId);
	}
}

function openRef(obj){
	var div = $(obj);
	var url = contextpath+"/contact-deptContact/viewForward.action?"
		+"id="+$(div).attr("id")+"&"
		+"rand="+Math.random();
	window.open(url);
	return false;
}

function delRef(obj){
	var div = $(obj).parent("div");
	$(div).remove();
	refreshIds("ref_zone");
	//console.log(div);
}

function refreshIds(zoneId){
	var ids = "";
	$("#"+zoneId).find(".ref_single").each(function(i,n){
		//console.log(n);
		ids+=$(n).children("a").attr("id")+",";
	});
	if(ids.startWith(",")){
		ids = ids.substring(1,ids.length);
	}
	
	$("#ref_id_zone").attr("value",ids).trigger('change');
}

function generateRefZone(zoneId){
	var div = $("#ref_div_zone");
	//console.log();
	//if($(div).length==0) return;
	
	if($("#"+zoneId).find("#ref_id_zone").val()==""){
		$(div).html("&nbsp;");
	}else{
		ajaxLoadRef(zoneId,div);
	}
	//console.log(zoneId);
}

function generateRefZoneWithRefresh(zoneId){
	var div = $("#ref_div_zone");
	//console.log();
	//if($(div).length==0) return;
	$("#ref_id_zone").trigger('change');
	if($("#"+zoneId).find("#ref_id_zone").val()==""){
		$(div).html("&nbsp;");
	}else{
		ajaxLoadRef(zoneId,div);
	}
	//console.log(zoneId);
}

function ajaxLoadRef(zoneId,div){
	
	var type = $("#"+zoneId).find("#ref_type_zone").val();
	
	var url = contextpath+"/contact-deptContact/getRef.action";
	var str = "";
	$.getJSON(
		url,{
			limit:18,
			ids:$("#"+zoneId).find("#ref_id_zone").val()
		},
		function(json){
			if(json){
				var resultDiv = "";
				if(json.length>0){
					var str = "";
					for(i=0;i<json.length;i++){
						var str = "<div class=ref_single title=\""+json[i].theme+"\">" 
						+"<a href=\"javascript:void(0)\" onclick=\"openRef(this)\" " 
						+"id=\""+json[i].id+"\" " 
						+">"
						+json[i].theme_short+"</a>";
						
						if(type=="update"){
							str+="<img height=15 width=15 onclick=\""+"delRef(this)"+"\" src=\""+contextpath+"/contact/images/delete.gif"+"\" />";
						}
						
						str+="；</div>";
						resultDiv += str;
					}
					
					$(div).html(resultDiv);
				}
			}
		}
	);
}

function submit_ref(){
	var zoneId = $("#zoneId");
	if(zoneId==null||zoneId.val()=="") return;
	
	if(self.opener==null) return;
	
	var zone = $("#"+zoneId.val(),self.opener.document);
	
	$(zone).find("#ref_id_zone").val($("#ids").val());
	
	//console.log($(zone).find("#ref_id_zone"));
	$(zone).find("#ref_id_zone").trigger('change');
	
	self.opener.generateRefZoneWithRefresh(zoneId.val());
	
	self.close();
	//console.log(zone);
}

function cancel_ref(){
	
}



String.prototype.endWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
		return false;
	if(this.substring(this.length-str.length)==str)
		return true;
	else
		return false;
	return true;
}

String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
		return false;
	if(this.substr(0,str.length)==str)
		return true;
	else
		return false;
	return true;
}