// JavaScript Document

function getXMLHttpRequest(){
		var req=false;
		if(window.XMLHttpRequest){
			req=new XMLHttpRequest();
			if(req.overrideMimeType)
				req.overrideMimeType("text/xml");
		}else if(window.ActiveXObject){
			try{
				req=new ActiveXObject("Msxml2.XMLHTTP");
			}catch(e){
				try{
					req=new ActiveXObject("Microsoft.XMLHTTP");
				}catch(e){}
			}
		}
	  return req;
}
function doRequest(url){
	var req=getXMLHttpRequest();
	req.open('get',url,false);//true 异步  false 同步
	req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	req.send(null);//发送参数如果有参数req.send("username="+user_name);用request取得
	return req.responseText;
}
function toJOSNObject(str){
	var josn;
	try{
		var func = new Function("return "+str);
		josn=func( );
	}catch(e){
		alert("转换josn对象时出错"+e);
	}
	return josn;
}

//*********************************************************************************
function doPostRequest(url,parameters,obj){

	var req=getXMLHttpRequest();
	 if (req==null)
	{
	    alert ("您的浏览器不支持AJAX！");
	    return;
	}
if(obj!=""){
req.onreadystatechange=stateChangedNew;
}
req.open("POST",url,false);//true 异步  false 同步
req.setRequestHeader('Content-type','application/x-www-form-urlencoded');
req.send(parameters);//发送参数如果有参数req.send("username="+user_name);用request取得
if(obj==""){
return req.responseText;
}
function stateChangedNew() 
{ 
    if (req.readyState==4)
    {   
    	if(req.status==200){
	      var content =req.responseText; 
	      obj.value=content;
      	}else{
     		obj.value="";
     	}      
    }
}
}