var listOptions = {
	cache:false,
	type:'post',
	callback:null,
	dataType :'json',
	url:contextpath+"/contact-deptConfig/queryList.action",
    success:function(data){
		//var obj = JSON.parse(data);
		if(data){
			$("#configTotal").html("共"+data.totalRows+"条记录"+"&nbsp;"+"每页显示15条");
			$("#pageShow").html(data.currentPage+"/"+data.totalPages);
			$("#pageCnt").val(data.currentPage);
			$("#currentPage").val(data.currentPage);
			var r = data.result;
			var list = "";
			if(r&&r.length>0){
				for(var i=0;i<r.length;i++){
					var t = r[i];
					list += generateList(t[0],t[1],t[2],t[3],t[4],t[7],t[8],t[9])
				}		
			}
			$(".data_tr").remove();
			$("#first").after(list);
			$("#formUpdate_loading").hide();
		}
		return false;
    }
};


function goPage(type) {
    $("#pageMethod").val(type);
    $("#rand").val(Math.random());

	if(type=="go"){
		$("#currentPage").val($("#pageCnt").val());
	}
	sub();
	
}

//清除数据
function clearData() {
    var form = $("form:first");
    $(form).find("input:text").val("");
    //$(form).find("select>option:first").attr("selected",true);
}

function sub(){
	$("#listForm").ajaxSubmit(listOptions);
	$("#formUpdate_loading").show();
}
function generateList(item0,item1,item2,item3,item4,item7,item8,item9){
	var tmp = '<tr class="data_tr" deptId="'+item3+'" deptName="'+item4+'" loginName="'+item7+'" userName="'+item8+'" id="'+item0+'" flag="'+item9+'" >';
	tmp += generateTd(item1,item2,item4,item8,item9);
	tmp += generateOp();
	tmp += '</tr>';
	return tmp;
}
function generateTd(item1,item2,item4,item8,item9){
	var tdTmp = '<td class="t_c">'+item1+'</td>';
	tdTmp += '<td class="t_c">'+item2+'</td>';
	tdTmp += '<td class="t_c">'+item4+'</td>';
	tdTmp += '<td class="t_c">'+item8+'</td>';
	if(item9==1){
		tdTmp += '<td class="t_c">'+'禁用'+'</td>';
	}else{
		tdTmp += '<td class="t_c">'+'启用'+'</td>';
	}
	return tdTmp;
}
function generateOp(){
	var opTmp = '<td class="t_c"><input style="padding-bottom:0px;padding-top:0px;" type="button" class="edit" value="edit"/>&nbsp;';
		opTmp += '<input style="padding-bottom:0px;padding-top:0px;" type="button" class="switch" value="switch"/></td>';
	return opTmp;
}

$(document).ready(function() {
    var $tbInfo = $(".filter .query input:text");
    $tbInfo.each(function() {
        $(this).focus(function() {
            $(this).attr("placeholder", "");
        });
    });
    $(".table_1 tbody tr:even").css("background", "#fafafa");
    $("#search").click(function(){
    	sub();
    })
	sub();
});
	
   