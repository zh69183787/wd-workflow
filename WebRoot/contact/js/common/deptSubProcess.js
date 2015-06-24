function clearPeople(idObj,nameObj,loginNameObj){
	if(confirm("确认清除吗？")){
		$(idObj).attr("value","");
		$(nameObj).attr("value","");
		$(loginNameObj).attr("value","");
	}
}

function createSuggest(personNames,leaderNames,textarea){
	if($(textarea).val()==""){
		var str = "";
		
		if(personNames!=""){
			str+='请' + personNames + '提出意见';
			if(leaderNames!=""){
				str+="，";
			}else{
				str+="。";
			}
		}
		if(leaderNames!=""){
			
			str+='请' + leaderNames + '阅示。';
		}

		$(textarea).val(str);
	}
	
	return false;
}

function createSuggestText(text,textarea){
	if($(textarea).val()==""){
		$(textarea).val(text);
	}
	return false;
}

/*
function previewSuggest(){
	alert("未实现");
	return false;
}
*/


