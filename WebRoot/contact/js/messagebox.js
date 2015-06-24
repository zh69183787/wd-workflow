function MessageBox(divId,maskId){
	pageWidth = ($.browser.version=="6.0")?$(document).width()-21:$(document).width();
	pageHeight = $(document).height();
	
	var div=$("#"+divId);
	var mask = $("#"+maskId);
	
	$(mask).animate({
			height :pageHeight,
			width :pageWidth,
			opacity :'show'
		},{
			duration :500,
			complete : function() {
				$(mask).css("filter","alpha(opacity=30)");
				divPosition(div);
				$(div).animate({
						opacity :'show'
					},{
						duration :500,
						complete : function() {
							divMaskPosition(mask);
							var offsetHeight = (document.body.clientHeight - $(div).height()) / 2
							if (offsetHeight < 0) offsetHeight = 0;
							ScrollTop = $(div).offset().top	- offsetHeight;
							$(document).scrollTop(ScrollTop);
							
						}
					}
				);
				/**/
			}
		}
	);
}

function divPosition(div){
	var top = ($(document).height() - $(div).height())/2;
	var left = ($(document).width() - $(div).width())/2;
	
	$(div).css("left",left+"px");
	$(div).css("top",top+"px");
}

function divMaskPosition(mask){
	pageWidth = ($.browser.version=="6.0")?$(document).width()-21:$(document).width();
	pageHeight = $(document).height();
	$(mask).css("height",pageHeight).css("width",pageWidth);
}

function closeMessageBox(divId,maskId){
	$("#"+divId).animate({
		  opacity:'hide'
	},{duration:1000,complete:function(){}});
	
	$("#"+maskId).animate({
		  opacity:'hide'
	},{duration:1000,complete:function(){}});
}

function initMessagebox(){
	$("#todo_handle").unbind("click");
	$("#todo_handle").click(function(){
		MessageBox("handle_zone","maskDiv");
	});
}

function initMessageboxClose(){
	$("#handleDivClose").css("cursor","pointer");
	$("#handleDivClose").click(function(){
		closeMessageBox("handle_zone","maskDiv");
	});

	$("#handleClose").click(function(){
		closeMessageBox("handle_zone","maskDiv");
	});
}



