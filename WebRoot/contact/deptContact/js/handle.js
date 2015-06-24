function elementEnable(zoneId){
	elementControl(zoneId,true);
}

function elementDisable(zoneId){
	elementControl(zoneId,false);
}

function elementControl(zoneId,flag){
	var zone = $("#"+zoneId);
	
	$(zone).find(":hidden,:text,:button,:radio,textarea,select").each(function(i,n){
		$(n).attr("disabled",!flag);
	})
	if(flag){
		$(zone).find("a").each(function(i,n){
			$(n).css("display","");
		})
	}else{
		$(zone).find("a").each(function(i,n){
			$(n).css("display","none");
		})
	}
}
