var maxsize = 1*1024*1024;//512KB  
    var errMsg = "上传的附件文件不能超过1M！！！";  
    var tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过1M，建议使用IE、FireFox、Chrome浏览器。";  
    var  browserCfg = {};  
    var submitFlag = true;
    var ua = window.navigator.userAgent;  
    if (ua.indexOf("MSIE")>=1){
        browserCfg.ie = true;  
    }else if(ua.indexOf("Firefox")>=1){
        browserCfg.firefox = true;  
    }else if(ua.indexOf("Chrome")>=1){
        browserCfg.chrome = true;  
    }
    
    
    
    function checkfile(fileDom){  

        try{  
            var obj_file = fileDom;  
            if(obj_file.value==""){  
                alert("请先选择上传文件");  
                submitFlag = false;
                return false;  
            } 
            var filesize = 0;  
            if(browserCfg.firefox || browserCfg.chrome ){  
                filesize = obj_file.files[0].size;  
            }else{
                alert(tipMsg);  
                submitFlag = false;
                return false;
            }
            if(filesize==-1){
                alert(tipMsg);  
                submitFlag = false;
                return false;
            }else if(filesize>maxsize){
                alert(errMsg);  
                submitFlag = false;
                return false;
            }else{
            	submitFlag = true;
                return true;
            }  
        }catch(e){  
            alert(e);  
        }  
    }
    
    
    
    function submitAnswer (){
    	var fileArr = $("input[name = 'answerFile']");
    	
    	for (var i =0;i<fileArr.length;i++){
    		if (fileArr[i].value==""||!checkfile(fileArr[i])){
    			submitFlag = false;
    			break;
    		}
    	}
    	
    	if (submitFlag){
    		$("#AnswerForm").submit();
    	}else{
    		alert ("表单信息不合法！！！");
    	}
    }