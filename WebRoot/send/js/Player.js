var play=1;
$(function(){
function scrollAD(parent,objImg,objNum,title){
	var Timeout;
	var index = 0;
	
	function showImg(i){
		var adH = $(parent).height();
		$(objImg+' > li').stop(false,true).fadeOut(300).eq(i).fadeIn(350); 
		$(objNum + ' > li').eq(i).addClass('current').siblings().removeClass('current'); 
		$(title + ' > li').stop(true,true).eq(i).animate({top:0},400).siblings().css("top","80px")
	};
	function autoPlay(){
		if(play===0){return;}
		var length=$(objImg + ' > li').length;
		if(index>=length){index= 0};
		showImg(index);
		index++;
		Timeout = setTimeout(autoPlay,2000);
		
	};
	autoPlay();
	$(objNum + '>li').hover(function(){
	    play=0;
		clearTimeout(Timeout);
		index = $(objNum + ' > li').index($(this));
		//alert(index);
		//alert($(this).text());
	    var index1=parseInt($(this).text());
		index1--;
		index=index1;
		//alert(index);
		showImg(index);
	},
	function(){	
		play=1;
		autoPlay();
	});
}
scrollAD('.focusPic','.fp_list','.scrollnav','.word');
})