function toPrintPage(){
	var processname = $("[name=modelName]").val();
	var incident = $("[name=incidentNo]").val();
	var url = contextpath+"/send-tDocSend/toFormPage.action?modelName="+encodeURI(processname)+"&incidentNo="+incident+"&print=1";
	window.showModalDialog(url,window,'dialogWidth:870px;dialogHeight:800px;scroll:auto');
}

function printFunc(){
	pageHeaderFooter();
    window.print();
}

function pageHeaderFooter(){
	var hkey_root,hkey_path,hkey_key;
	hkey_root="HKEY_CURRENT_USER";
	hkey_path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
	try{
       var RegWsh = new ActiveXObject("WScript.Shell");
       hkey_key="header";         
       RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
       hkey_key="footer";
       RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
    }catch(e){}
}