
var offset = {
		xOffset:10,
		yOffset:30
}

/*function showImg (){
	
	$("#actionSub").find("img").hover(function (e){
		$("<img id='imgshow' src='"+imgObj.src+"' />").appendTo("body");
		$("#mgshow").css("top",(e.pageY - offset.xOffset)+"px")
					.css("left",(e.pageX - offset.yOffset)+"px").fadeIn("fast");
	},function (){
		$("#imgshow").remove();
	})
	
}*/

$(function (){
	
	$("#actionSub").find("img").dblclick(function (){
		
		$("#imgshow").attr("src",this.src);
		$("#showImgModal").modal('show');
		
	})
	
})