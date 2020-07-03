
	var bodyfrm = (document.compatMode.toLowerCase()=="css1compat")?document.documentElement:document.body;
	var abst = document.getElementById("showAD").style;
	//abst.top = (bodyfrm.clientHeight -400-22)+"px";
	//abst.left = (bodyfrm.clientWidth -365)+"px";
	var adHeight = 50;
	var adWodth = 365;
	function moveR (adHeight,adWidth){
		abst.top = (bodyfrm.scrollTop + bodyfrm.clientHeight -adHeight-22)+"px";
		abst.left = (bodyfrm.scrollLeft + bodyfrm.clientWidth -adWidth)+"px";
	}
	
	$(function (){
		closed ();
	})
	
	var time1;
	var time2;
	
	function closed (){
		adHeight = 50;
		adWodth = 365;
		document.getElementById("panelAD").style.display = 'none';
		document.getElementById("spanADClose").style.display = 'none';
		document.getElementById("spanADOpen").style.display = 'block';
		clearInterval(time2);
		time1 = setInterval("moveR("+adHeight+","+adWodth+");",800);
		
	}
	function opened (){
		adHeight = 400;
		adWodth = 365;
		document.getElementById("panelAD").style.display = 'block';
		document.getElementById("spanADClose").style.display = 'block';
		document.getElementById("spanADOpen").style.display = 'none';
		clearInterval(time1);
		time2 = setInterval("moveR("+adHeight+","+adWodth+");",800);
	}