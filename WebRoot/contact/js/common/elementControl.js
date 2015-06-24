function elementEnable(zoneId){
	elementControl(zoneId,true);
}

function elementDisable(zoneId){
	elementControl(zoneId,false);
}

function elementControl(zoneId,flag){
	var zone = $("#"+zoneId);
	
	$(zone).find("input[type=hidden],:text,:button,:radio,textarea,select").each(function(i,n){
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

function submitZoneControl(zoneId,flag){
	if(zoneId==null||zoneId=="") return false;
	var zone = $("#"+zoneId);
	//console.log(flag);
	zone.find("a,:button").attr("disabled",flag);
}


function showRedMark(id){
	changeRedMark(id,'1');
}

function hideRedMark(id){
	changeRedMark(id,'');
}

function changeRedMark(id,status){
	var redMark = $("#"+id);
	if(status=='1'){
		$(redMark).show();
	}else{
		$(redMark).hide();
	}
}





function clearError(){
	if(errorTargets!=null&&errorTargets.length>0){
		
		//console.log(errorTargets);
		
		$(errorTargets).each(function(i,n){
			if($(n).length>0){
				$(n).qtip('destroy');
				
				if($(n).hasClass("date")){
					$(n).removeClass("fieldErrorDate");
				}else{
					$(n).removeClass("fieldError");
				}
			}
		});
	}
	errorTargets = new Array();
}

function showError(errors){
	var errorLog = "";
	if(errors!=null&&errors.length>0){
		//console.log(obj.errors);
		$(errors).each(function(i,n){
			var textCn = n.textCn;
			var field = n.actionField;
			
			errorLog += "\n"+(i+1)+"、"+textCn;
			
			var target = $("[name='"+field+"']:not(:disabled)");
			setError(target,textCn);
			//console.log($("[name='"+field+"']"));
		});
	}
	return errorLog;
	
}



var errorTargets = new Array();

function setError(target,textCn){
	if($(target).length>0){
		
		errorTargets.push(target);
		
		var tagName = $(target)[0].tagName;
		//console.log($(target).css("background"));
		
		if($(target).hasClass("date")){
			//console.log("date");
			$(target).addClass("fieldErrorDate");
			//$(target).css("background-image","url('../images/icon_1.png');");
		}else{
			$(target).addClass("fieldError");
		}
		
		/**/
		$(target).qtip({
			content:textCn,
			position: {
                corner: {
                   tooltip: "bottomMiddle", 
                   target: "topMiddle" 
                }
             },
             show: {
                //when: true, 
                //ready: true 
             },
             //hide: false, 
             style: {
                border: {
                   width: 2,
                   radius: 3
                },
                padding: 10, 
                textAlign: 'center',
                tip: true, 
                name: 'red' 
             }
		});
		
	}
}


