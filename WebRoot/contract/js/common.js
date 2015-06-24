/*
 * 扩展字符串去前后空格功能
 */
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

/*
 * 写入cookie
 * exp: 有效天数，不填表示浏览器关闭则失效
 */
function addCookie(k, v, exp) {
	var s = k + '=' + escape(v) + ';';
	if (exp != undefined && !isNaN(exp)) {
		var today = new Date();
		var expDt = new Date(today.getYear(), today.getMonth(), today.getDate() + parseInt(exp));
		s += ' expires=' + expDt.toGMTString() + ';';
	}
	document.cookie = s;
}

/*
 * 查询cookie
 */
function getCookie(k) {
	var allCookie = document.cookie;
	var arrCookies = allCookie.split(';');
	if (arrCookies != null) {
		for (var i=0; i<arrCookies.length; i++) {
			var cookie = arrCookies[i].split('=');
//			alert(cookie[0] + ', ' + k + ', ' + unescape(cookie[1]) + (cookie[0].trim() == k.trim()));
			if (cookie[0].trim() == k.trim()) return unescape(cookie[1]);
		}
	}
	return '';
}


//js print

var hkey_root,hkey_path,hkey_key;
hkey_root="HKEY_CURRENT_USER";
hkey_path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";

function pageHeaderFooter(){
         try{
            var RegWsh = new ActiveXObject("WScript.Shell");
            hkey_key="header";         
            RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
            hkey_key="footer";
            RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
         }catch(e){}
}
 
function printPage()
{
     pageHeaderFooter();
     window.print();
}
function ieVersion(){
		var browser=navigator.appName 
		var b_version=navigator.appVersion 
		var version=b_version.split(";"); 
		var trim_Version=version[1].replace(/[ ]/g,""); 
		if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0") 
		{ 
		//alert("IE 7.0"); 
		return '7';
		} 
		else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0") 
		{ 
		//alert("IE 6.0"); 
		return '6';
		} 
	}
//
function scDivLeftUp(){
	if(ieVersion == '7'){
	 	document.getElementById("ToolsKit").style.top=(document.documentElement.scrollTop)+ 80+"px";
	 	var obj = document.getElementById('clzt');
	 	if(obj != null){
	 		obj.style.top=(document.documentElement.scrollTop)+ 80+"px";
	 	}
	 	obj = document.getElementById('menuPos');
	 	if(obj != null){
	 		obj.style.top=(document.documentElement.scrollTop)+ 80+"px";
	 	}
	}else{
		document.getElementById("ToolsKit").style.top=(document.body.scrollTop)+80+"px";
		var obj = document.getElementById('clzt');
	 	if(obj != null){
	 		obj.style.top=(document.body.scrollTop)+80+"px";
	 	}
	 	obj = document.getElementById('menuPos');
	 	if(obj != null){
	 		obj.style.top=(document.body.scrollTop)+80+"px";
	 	}
	}
}

// 检查输入字符串是否包含单引号或双引号
function check_quotes(sin) {
	if (sin == undefined) return false;
	return (sin.indexOf('"') >= 0 || sin.indexOf('\'') >= 0);
}

function HTMLEncode(str)
{
        if(str!=""){
                str=strReplace(">","&gt;",str);
                str=strReplace("<","&lt;",str);
                str=strReplace(" ","&nbsp;",str);
                str=strReplace("\t","&quto;",str);
                str=strReplace("'","&#39;",str);
                //str=strReplace(chr(13),"",str);
                //str=strReplace(chr(10).chr(10),"</p><p>",str);
                str=strReplace('\r\n',"<br>",str);
                return str;
        }
}

function strReplace(search, replace, str) {
 var regex = new RegExp(search, "g");
 return str.replace(regex, replace);
}
