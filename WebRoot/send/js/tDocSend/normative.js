//提交判断
function checkNormative(){
	var flag = true;
	//1：radio
		if($(":radio[name='normative']:visible").length > 0 &&
				 $(":radio[name='normative']:checked").length == 0){
			alert("请选择规范性设置!");
			return false;
		}
		//2：备注设置
		else if($("#override").prop("checked")){
			var $listElement = $("input[name^='normative'][name!='normativeAttach'][name!='normativeAttachCount'][name!='normativeId'],select[name='normativeStatus']");
			if($("#normativeMemoTable").find($listElement).length == 0){alert("请填写修改内容或者删除空白行!");return false;}
			$("#normativeMemoTable").find($listElement).each(function(i,n){
				if($(n).val() == ""){
					//alert($(n).attr("name"))
					alert("请填写修改内容或者删除空白行!");
					flag = false;
					return false;
				}
			});
            $("select[name='normativeStatus']").each(function (i, n) {
                if ($(n).parent().parent("tr").find("input[type='hidden'][name='normativeAttach']").val() == "") {
                    alert("请上传修改内容!");
                    flag = false;
                    return false;
                }
            })

		}
	return flag;
}

//附件
var normativeOpw = null;

function closeNormativeAttach()
{
	if(normativeOpw && normativeOpw.open && !normativeOpw.closed)
	{
		normativeOpw.close();
	}
}

function openNormativeAttach(url,name,iWidth,iHeight)
{
	var iTop = (window.screen.height-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.width-10-iWidth)/2; //获得窗口的水平位置;
	normativeOpw = window.open(url,name,'height='+iHeight+',width='+iWidth+',top='+iTop+',left='+iLeft);
}

function drawAttachNormative(fileGroup, fileGroupName, fileGroupId, newGroupId, procType, targetType, fileCntObjId) {
	var url = ''+contextpath+'/attach/loadFileList.action?fileGroup='
			+ fileGroup + '&fileGroupName=' + fileGroupName + '&fileGroupId='
			+ fileGroupId + '&newGroupId=' + newGroupId + '&procType='
			+ procType + '&targetType=' + targetType + '&fileCntObjId='
			+ fileCntObjId + '&rand='+Math.random()+ '&approve=1&listType=normative';
	openNormativeAttach(url,'attachNormativeOPT',600,200);
}

//是否checked
function ifChecked(question,answer){
	if(question == answer){
		return " selected ";
	}
	return "";
}

$(function(){
	$("#normativeTable").on("click",":radio[name='normative']",function(){
		if($(":radio[name='normative']:checked").val()=="1"){

			$("#overrideSpan").show();
			$("#overrideSpan").css("display","inline");
			//$("#normativeAddTr").show();
			//$("#normativeShowTr").show();
		}else{
			$("#overrideSpan").hide();
			$("#normativeAddTr").hide();
			$("#normativeShowTr").hide();
			$("#override").prop("checked",false);

		}
	});

	$("#normativeTable").on("click","#override",function(){
		if($(this).prop("checked")){
			$("#normativeAddTr").show();
			$("#normativeShowTr").show();

		}else{

			$("#normativeAddTr").hide();
			$("#normativeShowTr").hide();
		}

	});

	/**$("#normativeMemoTable").on("change","input[name=normativeAttachCount]",function(){
		alert(1);
		if($(this).val()==0 || $(this).val()=="" || $(this).val()==null){
			$(this).prev().val("");
			$(this).next().html("");
		}else{
			$(this).next().html("");
		}
	})**/

	$("#normativeMemoTable").on("click",".arrow",function(){
		//√
		if($(this).html()!=""){
            drawAttachNormative("", "", $(this).parent().parent("td").find("input[name=normativeAttach]").val(),
			"", "view", "frame", "");
		}
	});

	$("#ttButton").click(function(){
		$("#ttConfirm").val("1");
		$("#normativeTable").hide();
		$(this).hide();
	});

//    $("#normativeMemoTable").on("change","select[name='normativeStatus']",function(){
//
//    })

	$("#normativeMemoTable").on("click",".attachWin",function(){
		//alert($(this).parent().parent().parent("tr").index());
        drawAttachNormative($(this).parent().parent().parent("tr").index(), "",
		$(this).parent().parent().parent("tr").find("input[name=normativeAttach]").val(),
		"", "", "frame", "");
	});

	$("#normativeMemoTable").on("click",".deleteWin",function(){
		if($(this).parent().parent().parent("tr").find("#normativeId").val()!=""){
			$(this).parent().parent().parent("tr").find("#normativeRemoved").val("1");
			$(this).parent().parent().parent("tr").hide();

		}else{
			$(this).parent().parent().parent("tr").remove();
		}
		$("#normativeMemoTable tr:visible:not(:first)").each(function(i,n){
			$(n).find("td:first").find("strong[name=num]").val(parseInt(i+1));
		})
	});

	$("#normativeMemoTable").on("click",".editWin",function(){
			var n1 = $(this).parent().parent().parent("tr").find("td:eq(1)");
			var n2 = $(this).parent().parent().parent("tr").find("td:eq(2)");
			var n3 = $(this).parent().parent().parent("tr").find("td:eq(3)");
			n1.html("<input type=\"text\" name=\"normativeTitle\" value=\""+n1.find("input[name=normativeTitle]").val()+"\">");
			n2.html("<input type=\"text\" name=\"normativeCode\" value=\""+n2.find("input[name=normativeCode]").val()+"\">");
			n3.html("<select name=\"normativeStatus\">"+
									"<option "+ifChecked("",n3.find("input[name=normativeStatus]").val())+" value=\"\">请选择</option>"+
									"<option "+ifChecked("2",n3.find("input[name=normativeStatus]").val())+" value=\"2\">部分有效</option>"+
									"<option "+ifChecked("3",n3.find("input[name=normativeStatus]").val())+" value=\"3\">失效</option>"+
									"<option "+ifChecked("4",n3.find("input[name=normativeStatus]").val())+" value=\"4\">废止</option>"+
							"</select>");
                $(this).parent("span").find(".attachWin").show();
			$(this).hide();
	});


	$("#normativeTable").on("click","#normativeAddButton",function(){
		var rowsLength = $("#normativeMemoTable tr:visible:not(:first)").length;
		var insertObj = "<tr>"+
							"<td><input type=\"hidden\" id=\"normativeId\" name=\"normativeId\" value=\"\">"+
							"<input type=\"hidden\" id=\"normativeRemoved\" name=\"normativeRemoved\" value=\"0\">"+
							"<strong name=\"num\">"+parseInt(rowsLength+1)+"</strong></td>"+
							"<td><font style=\"color:red;display:inline;\">*</font><input style=\"width:120px;\" type=\"text\" name=\"normativeTitle\"></td>"+
							"<td><font style=\"color:red;display:inline;\">*</font><input style=\"width:120px;\" type=\"text\" name=\"normativeCode\"></td>"+
							"<td><font style=\"color:red;display:inline;\">*</font><select name=\"normativeStatus\">"+
									"<option value=\"\">请选择</option>"+
									"<option value=\"2\">部分有效</option>"+
									"<option value=\"3\">失效</option>"+
									"<option value=\"4\">废止</option>"+
							"</select></td>"+
            "<td><input type=\"hidden\" name=\"normativeAttach\">" +
            "<input type=\"hidden\" name=\"normativeAttachCount\">" +
            "<center><span class=\"arrow\"><font style=\"color:red;display:inline;\">*</font></span></center></td>" +
            "<td><span> <a class=\"attachWin\" href=\"javascript:void(0);\">附件</a>" +
            "<a class=\"deleteWin\" href=\"javascript:void(0);\">删除</a>" +
            "</span></td>" +
						"</tr>";
		$("#normativeMemoTable").append(insertObj);
		rowsLength ++ ;
	})
});