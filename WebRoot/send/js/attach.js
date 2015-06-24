var newwindow;
function openwindow(url,name,iWidth,iHeight)  
{  
// url 转向网页的地址  
// name 网页名称，可为空  
// iWidth 弹出窗口的宽度  
// iHeight 弹出窗口的高度  
//window.screen.height获得屏幕的高，window.screen.width获得屏幕的宽  
var iTop = (window.screen.height-30-iHeight)/2; //获得窗口的垂直位置;  
var iLeft = (window.screen.width-10-iWidth)/2; //获得窗口的水平位置;  
newwindow = window.open(url,'t','height='+iHeight+',width='+iWidth+',top='+iTop+',left='+iLeft);  
}  

function uploadfile(){
	var url="attach_cf/common/attach_upload.jsp?max_file_num="+max_file_num+"&max_file_size_single="+max_file_size_single+"&form_name="+formname+"&attach_dir_type="+attach_dir_type+"&hiddenFormName="+hiddenFormName+"&resourceid="+eval("document."+formname+"."+hiddenFormName+".value");
	window.showModalDialog(url,window,'help=no;dialogWidth=750px;dialogHeight='+(max_file_num*100+30)+'px;dialogLeft=100px;dialogTop=150px;status=no;scroll=no;resizable=no');

	onloadFile();
}

/*
 * 显示文件上传页面(流程逐条意见中使用)
 * 使用示例：

 * FileGroupId:<input type="text" id="fg"/>
 * 有<span id="mycnt"></span>个附件

 * <input type="button" value="click" onclick="drawAttachDiv(500, 300, 'fg', 'fgn', document.getElementById('fg').value, '', '', 'frame', 'mycnt')" />
 */

function drawAttachDiv(divWidth, divHeight, fileGroup, fileGroupName, fileGroupId, newGroupId, procType, targetType, fileCntObjId) {
	var divId = 'attachDiv';
	if (isNaN(divWidth) || isNaN(divHeight)) return;
	if (document.getElementById(divId) != null) return;
	if (fileGroup == undefined) fileGroup = '';
	if (fileGroupName == undefined) fileGroupName = '';
	if (fileGroupId == undefined) fileGroupId = '';
	if (newGroupId == undefined) newGroupId = '';
	if (procType == undefined) procType = '';
	if (targetType == undefined) targetType = '';
	if (fileCntObjId == undefined) fileCntObjId = ''; // 用于保存附件个数的控件id
	
	var buttonX = window.event == null ? 0 : window.event.clientX;
	var buttonY = window.event == null ? 0 : window.event.clientY;
	var bodyWidth = document.body.clientWidth - 5;
	var bodyHeight = document.body.clientHeight - 26;
	var x = buttonX;
	var y = buttonY;
	//alert(bodyWidth + ',' + bodyHeight);
	//alert(x + ',' + y);
	if (bodyWidth - buttonX <= divWidth) {
		x = bodyWidth - divWidth;
		if (x <= 0) {
			x = 0;
			divWidth = bodyWidth;
		}
	}
	if (bodyHeight - buttonY <= divHeight) {
		y = bodyHeight - divHeight;
		if (y <= 0) {
			y = 0;
			divHeight = bodyHeight;
		}
	}

	//alert(x + ',' + y);
	//alert(divWidth + ',' + divHeight);
	//var url = '/workflow/attach/loadFileList.action?fileGroup=' + fileGroup + '&fileGroupName=' + fileGroupName + '&fileGroupId=' + fileGroupId + '&newGroupId=' + newGroupId + '&procType=' + procType + '&targetType=' + targetType + '&fileCntObjId=' + fileCntObjId + '" style="width:' + divWidth + 'px;height:' + divHeight + 'px;';
	//var result = window.showModalDialog(url, window, 'dialogWidth:600px;dialogHeight:450px');
	//alert('11');
	//if(result!=null){
	//	var id=result[0];
	//	var name=result[1];  
	//	alert(id+name);
    ///}
	var str = '<div id="' + divId + '" style="border:1pxsolid #000000;position:absolute;width:' + divWidth + 'px;height:' + divHeight + 'px;top:' + y + 'px;left:' + x + 'px;z-index:10000"><table width="100%" height="100%" border="0" cellpading="0" cellspacing="0"><tr><td style="background-color:#cccccc;width:' + (divWidth - 40) + 'px;">&nbsp;</td><td style="background-color:#ffffff;width:40px;padding-top:0px;text-align:center;vertical-align:middle;cursor:hand;font-size:12px;" onclick="this.parentNode.parentNode.parentNode.parentNode.removeNode(true)">[关闭]</td></tr><tr><td style="border-top:1px solid #000000;" colspan="2"><iframe frameborder="0" id="" src="/workflow/attach/loadFileList.action?fileGroup=' + fileGroup + '&fileGroupName=' + fileGroupName + '&fileGroupId=' + fileGroupId + '&newGroupId=' + newGroupId + '&procType=' + procType + '&targetType=' + targetType + '&fileCntObjId=' + fileCntObjId + '" style="width:' + divWidth + 'px;height:' + divHeight + 'px;"></iframe></td></tr></table></div>';
	document.body.innerHTML += str;
}


function drawAttach(fileGroup, fileGroupName, fileGroupId, newGroupId, procType, targetType, fileCntObjId) {
	var url = ''+contextpath+'/attach/loadFileList.action?fileGroup='
			+ fileGroup + '&fileGroupName=' + fileGroupName + '&fileGroupId='
			+ fileGroupId + '&newGroupId=' + newGroupId + '&procType='
			+ procType + '&targetType=' + targetType + '&fileCntObjId='
			+ fileCntObjId + '&rand='+Math.random()+ '&approve=1';
	openwindow(url,'',600,200);
	//alert(window.returnValue);
	/*if (typeof (result) != "undefined") {
		var result_fileGroup = result[0];
		var result_fileGroupId = result[1];
		var result_fileGroupName = result[2];
		var result_procType = result[3];
		var result_targetType = result[4];
		var result_fileType = result[5];
		var result_fileCntObjId = result[6];
		var result_attachMemo1 = result[7];
		var result_count = result[8];
		$("#" + fileGroup).val(result_fileGroupId);
		$("#" + fileCntObjId).html(result_count);
		// $("."+fileCntObjId).html(result_count);
	}else{
		result = window.returnValue;
		var result_fileGroup = result[0];
		var result_fileGroupId = result[1];
		var result_fileGroupName = result[2];
		var result_procType = result[3];
		var result_targetType = result[4];
		var result_fileType = result[5];
		var result_fileCntObjId = result[6];
		var result_attachMemo1 = result[7];
		var result_count = result[8];
		$("#" + fileGroup).val(result_fileGroupId);
		$("#" + fileCntObjId).html(result_count);
	}*/
}

function initSuggestAttach(){
	$("a.suggest_attach[name='suggest_attach']").each(function(i,n){
		var obj = $(this);
		$(obj).click(function(){
			var attachId = $(obj).find("[name='attachId']");
			var attachCount = $(obj).find(".fjcount");
			var groupId = $("#"+$(attachId).attr("id")).val();
			drawAttach($(attachId).attr("id"), "", groupId, "", "", "frame", $(attachCount).attr("id"));
		});
	});
}

function initSuggestAttachShow(){
	$(":hidden[name='suggest_attachId']").each(function(i,n){
		//console.log(n);
		if($(n).attr("value")!=""){
			var id = $(n).attr("value");
			var p = $(n).parent().children("p");
			$(p).append($("<img class=\"attach_icon\" title=\"查看意见附件\" style=\"display:inline;\" src=\""+contextpath+"/images/new_images/fj.gif\"></img>"));
			var img = $(p).children("img.attach_icon");
			$(img).css("cursor","pointer").click(function(){
				drawAttach("", "", id, "", "view", "frame", "");
			});
		}
	});
}

$(document).ready(function(){
	initSuggestAttachShow();
	initSuggestAttach();
});
