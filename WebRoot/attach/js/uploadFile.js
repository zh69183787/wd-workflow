document.writeln("<STYLE type=text/css>");
document
		.writeln(".cMenu {FILTER: alpha(opacity=0);BACKGROUND-COLOR: #D6D3CE;BORDER-BOTTOM: #666666 2px solid;z-index:2; BORDER-LEFT: #E4E4E4 2px solid; BORDER-RIGHT: #666666 2px solid; BORDER-TOP: #E4E4E4 2px solid; COLOR: #000000; CURSOR: default; FONT-SIZE: 9pt; color:#000000;FONT-WEIGHT: normal; LINE-HEIGHT: 20px; POSITION: absolute; VISIBILITY: hidden; WIDTH: 200px}");
document
		.writeln(".menuitems {font-size:9pt;MARGIN: 2px;PADDING-BOTTOM: 0px;PADDING-LEFT: 15px;PADDING-RIGHT: 3px;PADDING-TOP: 0px;}");
document
		.writeln(".menuitems1 {font-size:9pt;MARGIN: 2px;PADDING-BOTTOM: 0px;PADDING-LEFT: 15px;PADDING-RIGHT: 3px;PADDING-TOP: 0px;}</STYLE>");
document
		.writeln("<DIV class=cMenu id=ie5menu onclick=jumptoie5() onmouseout=lowlightie5() onmouseover=highlightie5()></DIV>");
document.writeln("<input type=\"hidden\" name=\"linkDele\" value=\"\">");
document.writeln("<input type=\"hidden\" name=\"divid\" value=\"\">");
document.writeln("<div id=\"linkDiv\" style=\"display:none;\"></div>");
document
		.writeln("<div style=\"text-align:left;background-color:white; border: solid 1px blue; display: none;position:absolute;word-break:break-all;width:60%\" id=\"versionDiv\"></div>");
var link111;

var intDelay = 1; // 设置菜单显示速度，越大越慢
var intInterval = 20; // 每次更改的透明度

/*显示右键菜单*/
function showmenuie5() {
	var rightedge = document.body.clientWidth - event.clientX
	var bottomedge = document.body.clientHeight - event.clientY
	if (rightedge < ie5menu.offsetWidth)
		ie5menu.style.left = document.body.scrollLeft + event.clientX
				- ie5menu.offsetWidth
	else
		ie5menu.style.left = document.body.scrollLeft + event.clientX
	if (bottomedge < ie5menu.offsetHeight)
		ie5menu.style.top = document.body.scrollTop + event.clientY
				- ie5menu.offsetHeight
	else
		ie5menu.style.top = document.body.scrollTop + event.clientY
	ie5menu.style.visibility = "visible"
	ie5menu.filters.alpha.opacity = 0
	GradientShow()
	return false
}

/*隐藏右键菜单*/
function hidemenuie5() {
	GradientClose()
}

/*高亮右键菜单*/
function highlightie5() {
	if (event.srcElement.className == "menuitems") {
		event.srcElement.style.backgroundColor = "highlight"
		event.srcElement.style.color = "white"
	}
}

/*转暗右键菜单*/
function lowlightie5() {
	if (event.srcElement.className == "menuitems") {
		event.srcElement.style.backgroundColor = ""
		event.srcElement.style.color = "#000000"
	}
}

/*弹出右键菜单*/
function jumptoie5() {
	if (event.srcElement.className == "menuitems") {
		if (event.srcElement.url != '') {
			if (event.srcElement.getAttribute("target") != null)
				window.open(event.srcElement.url, event.srcElement
						.getAttribute("target"))
			else
				window.location = event.srcElement.url
		}
	}
}

/*实现淡入的函数*/
function GradientShow() 
{
	ie5menu.filters.alpha.opacity += intInterval
	if (ie5menu.filters.alpha.opacity < 100)
		setTimeout("GradientShow()", intDelay)
}

/*实现淡入的函数*/
function GradientClose() 
{
	ie5menu.filters.alpha.opacity -= intInterval
	if (ie5menu.filters.alpha.opacity > 0) {
		setTimeout("GradientClose()", intDelay)
	} else {
		ie5menu.style.visibility = "hidden"
	}
}

/*改变菜单项的背景颜色，这里的两种颜色值可以改为你需要的*/
function ChangeBG()  
{
	oEl = event.srcElement
	if (oEl.style.background != "navy") {
		oEl.style.background = "navy"
	} else {
		oEl.style.background = "#cccccc"
	}
}

/*
 * 菜单点击事件
 * @param id 附件编号
 */
function click_obj(id) {
	switch (id) {
	case 0:
		window.open(link111, "_self");
		break
	case 1:
		deleteFile(document.forms[0].linkDele.value);
		break
	case 2:
		window.location.reload()
		break
	case 3:
		window.external.AddFavorite(location.href, document.title)
		break
	case 4:
		window.location = "view-source:" + window.location.href
		break
	case 5:
		document.all.WebBrowser.ExecWB(10, 2)
		break
	}
}

/*
 * 添加右键菜单事件
 * @param obj 添加右键菜单的对象
 * @param open 下载文件链接
 * @param dele 删除文件链接
 * @param scr  上传人
 * @param sj 上传时间
 * @param dx 文件大小
 * @param bb 版本号
 * @param bz 备注
 * @param divid divid
 * @param isdele 是否删除
 * */
function fsclick(obj, open, dele, scr, sj, dx, bb, bz, divid, isdele) {
	link111 = open;
	document.forms[0].divid.value = divid;
	document.forms[0].linkDele.value = dele;
	ie5menu.innerHTML = "<DIV class=menuitems url='javascript:click_obj(0)'>打开</DIV>";
	if (isdele == '1') {
		ie5menu.innerHTML += "<DIV class=menuitems url='javascript:click_obj(1)'>删除</DIV><DIV class=menuhr><hr style='width:100%'></DIV>";
	}
	if (isdele == '0') {
		ie5menu.innerHTML += "<DIV class=menuhr><hr style='width:100%'></DIV>";
	}
	ie5menu.innerHTML += "<DIV class=menuitems1>上传人:" + scr + "</DIV>"
			+ "<DIV class=menuitems1>时间:" + sj + "</DIV>"
			+ "<DIV class=menuitems1>大小:" + dx + "</DIV>"
			+ "<DIV class=menuitems1>版本:" + bb + "</DIV>"
			+ "<DIV class=menuitems1>备注:" + bz + "</DIV>";
	ie5menu.className = "cMenu";
	obj.oncontextmenu = showmenuie5;
}

/*
 * 查看版本添加右键菜单事件
 * @param obj 添加右键菜单的对象
 * @param open 下载文件链接
 * @param dele 删除文件链接
 * @param scr  上传人
 * @param sj 上传时间
 * @param dx 文件大小
 * @param bb 版本号
 * @param bz 备注
 * */
function fsclick1(obj, open, scr, sj, dx, bb, bz) {
	link111 = open;
	ie5menu.innerHTML = "<DIV class=menuitems url='javascript:click_obj(0)'>打开</DIV><DIV class=menuhr><hr style='width:100%'></DIV>"
			+ "<DIV class=menuitems1>上传人:"
			+ scr
			+ "</DIV>"
			+ "<DIV class=menuitems1>时间:"
			+ sj
			+ "</DIV>"
			+ "<DIV class=menuitems1>大小:"
			+ dx
			+ "</DIV>"
			+ "<DIV class=menuitems1>版本:"
			+ bb
			+ "</DIV>"
			+ "<DIV class=menuitems1>备注:" + bz + "</DIV>";
	ie5menu.className = "cMenu";
	obj.oncontextmenu = showmenuie5;
}

/*
 * 删除附件
 * @param url 删除链接
 * @param id 上传文件编号
 * */
function deleteFile(url, id) {
	if (confirm("您确认要删除所选附件吗？")) {
		var tyuu = "contentAtt2" + document.forms[0].divid.value;
		document.getElementById(tyuu).contentWindow.deleteFile(url,
				document.forms[0].divid.value);
	}
}

if (document.all && window.print) {
	ie5menu.className = "cMenu"
	document.body.onclick = hidemenuie5
}
