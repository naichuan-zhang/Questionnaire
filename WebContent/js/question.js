function newQuestion (){
	alert ('新建一份问卷调查，先填写标题。')
}

function deleteQyestion(){
	var mainId=$("#tempId").val();				//获取Id为tempId的input值
	$.ajax({
		url:basePath+"question/del/"+mainId,	//请求地址，把mainId加入路径当中
		type:'DELETE',							//使用delete请求方式，rest风格
		success:function (data){
			if (data.success){					//删除成功刷新页面
				window.location.reload();
			}
		}
	});
}

function delModal (mainId){
	$("#tempId").val(mainId);		//设置Id为tempId的input值为主表Id
	$("#delModal").modal();			//显示友好提示框
}

function delCheckModal (){
	$("#delCheckModal").modal();
}

function submitForm(formid,urlparm){
	//var formData = new FormData($(formid)[0]);
	$.ajax({
	url: urlparm,
	type: 'POST',
	data: {username:'username',password:'password'},
	async: false,
	cache: false,
	contentType: 'text/xml',
	processData: false,
	success: function(data){
		if(data.success) {
			window.location.reload();
		}
	},
	error: function(){
		alert ('服务器问题。');
	}
	});
}

function submitFormDel(formid,urlparm){
	
	var names = $("input[name='id']:checked").serialize();
	
	$.ajax({
	url: urlparm+"?"+names,
	type: 'POST',
	async: false,
	cache: false,
	contentType: 'text/xml',
	processData: false,
	success: function(data){
		if(data.success) {
			window.location.reload();
		}
	},
	error: function(){
		alert ('服务器问题。');
	}
	});
}

function delQuestionModal (QuestionId){
	
	$("#tempQuestionId").val(QuestionId);
	$("#delQuestionModal").modal();
	
}

//删除一个问题
function delWt (questionId){
	
	$.ajax({
		url:basePath+"question/delWt/"+$("#tempQuestionId").val(),
		type:'DELETE',
		success:function (data){
			if (data.success){
				window.location.reload();
			}
		}
	});
	
}

function deleteQuestion (){
	
	var questionId = $("#tempQuestionId").val();
	$.ajax({
		url:basePath+"question/delQuestion/"+questionId,
		type:'DELETE',
		success:function (data){
			if (data.success){
				window.location.reload();
			}
		}
	});
}

function pauseModel (mainId){
	
	$("#temppauseMainId").val(mainId);
	
	$("#pauseModel").modal();
}

function pause (){
	$.ajax({
		url:basePath+"question/pause/"+$("#temppauseMainId").val(),
		type:'PUT',
		success:function (data){
			if (data.success){
				window.location.reload();
			}else{
				var pText = document.getElementById("alertOKText");
				pText.innerText = "暂停失败";
				$("#alertOK").modal();
			}
		}
	});
}

//发布问卷
function actionModel (mainId){
	$("#tempActionMainId").val(mainId);
	$("#actionModal").modal();
}


function actionStart (){
	$.ajax({
		url:basePath+"question/action/"+$("#tempActionMainId").val(),
		type:'PUT',
		success:function (data){
			if (data.success){
				$("#alertOK").modal();
				window.location.reload();
			}else{
				var pText = document.getElementById("alertOKText");
				pText.innerText = "发布失败";
				$("#alertOK").modal();
			}
		}
	});
}

function submitAnswer(formid,urlparm){
	var formData = new FormData($(formid)[0]);
	$.ajax({
	url: urlparm,
	type: 'POST',
	data: formData,
	async: false,
	cache: false,
	contentType: false,
	processData: false,
	success: function(data){
		if(data.success) {
			var pText = document.getElementById("alertOKText");
			pText.innerText = "非常感谢您的认真回答";
			$("#alertOK").modal();
		}else{
			window.location.href=basePath+"question/goLowError";
		}
	},
	error: function(){
		alert ('服务器问题。');
	}
	});
}

function submitFormById (formId){
	$('#'+formId).submit();		//表单提交
}

function copyQuestion (mainId){
	$.ajax({
		url:basePath+"question/copy/"+mainId,
		type:'GET',
		success:function (data){
			if (data.success){
				var pText = document.getElementById("alertOKText");
				pText.innerText = "拷贝成功，页面于三秒内刷新";
				$("#alertOK").modal();
				setTimeout("window.location.reload()",3000);
			}else{
				var pText = document.getElementById("alertOKText");
				pText.innerText = "拷贝失败";
				$("#alertOK").modal();
				
			}
		}
	});
}

function showEdit(){
	
	$("#editform").show();
	$("#h5maintitle").hide();
	$("#btnxg").hide();
	$("#btnbc").show();
	$("#btnqx").show();
	
}

function cancelEdit(){

	$("#editform").hide();
	$("#h5maintitle").show();
	$("#btnxg").show();
	$("#btnbc").hide();
	$("#btnqx").hide();
}

function editQuestionMainTitle (mainId){
	
	var MainTitle = $("#mainTitleInput").val();
	var MainEndtime = $("#mainEndtimeInput").val();
	
	$.ajax({
		url:basePath+"question/editMainTitle/",
		type:'POST',
		data:{mainId:mainId,
			mainTitle:MainTitle,
			mainEndtime:MainEndtime
		},
		success:function (data){
			if (data.success){
				$("#alertOK").modal();
				$("#h5maintitle").html(MainTitle);
				cancelEdit();
			}else{
				var pText = document.getElementById("alertOKText");
				pText.innerText = "保存失败";
				$("#alertOK").modal();
			}
		}
	});
}

function closepage (){
	
	window.opener = null;
	window.open("","_self");
	window.close();
	
}

function openEditModel (){
	$("#editModal").modal('show');
	$("div[name='div_answers']").remove();
	answerPro.num = 1;
}

var answerPro = {
		"num":1
}

function addAnswer() {
	var guidr = guid();		//生成guid作为
	$("#editAnswer").append("<div class='form-group' name='div_answers' id='div_answer"
			+guidr+"'><label>答案"+answerPro.num+":</label>"
			+"<div class='input-group'>"
			+"<input class='form-control' name='"+guidr+"' type='text' />"
			+"<div class='input-group-btn'>"
			+"<buttion class='btn btn-primary' type='button' onclick=\"delAddAnswer('"
			+guidr+"')\">删除</bution></div></div>"
			+"<input type='file' name='"+guidr+"'  /></div>");
	answerPro.num++;		//答案编号自加1
}

function delAddAnswer(num) {
	//根据传递过来的guid获取div，并将其移除
	$('#'+('div_answer'+num)).remove();
	var i = 1;
	//重新排序
	var elementLabel = $("div[name='div_answers']").find("label").each(function (){
		$(this).html("答案"+i+":");
		i++;
	});
	answerPro.num = i;
}

function openEditModelxg (questionId){
	$("#editModalxg").modal('show');
	$("div[name='div_answersxg']").remove();
	
	$.ajax({
		url:basePath+"question/editQuestionnaire/"+questionId,
		type:'GET',
		success:function (data){
			var answers = data.answers;
			var question = data.question;
			$("#questionIdxg").val(question.questionId);
			$("#questionTitlexg").val(question.questionTitle);
			$("#questionTypexg option[value="+question.questionType+"]").attr("selected","selected");
			var i=0;
			for (i;i<answers.length;i++){
				var guidr = guid();
				var tempAnswerDestype = answers[i].answerDestype;
				if (answers[i].answerType == 'y'){
					var lastIF = answers[i].answerDestype.lastIndexOf("(");
					var temp = answers[i].answerDestype.substring(0,lastIF);
					tempAnswerDestype = temp;
				}
				$("#editAnswerxg").append("<div class='form-group' name='div_answersxg' id='div_answerxg"+guidr+"'><label>答案"+(i+1)+":</label>"
						+"<div class='input-group'>"
						+"<input class='form-control' name='"+answers[i].answerId+"' type='text' value='"+tempAnswerDestype+"' />"
						+"<div class='input-group-btn'><buttion class='btn btn-primary' type='button' onclick=\"delAddAnswerxg('"+guidr+"')\">删除</bution></div></div>"
						+"<input type='file' name='"+answers[i].answerId+"' /></div>");
			}
			answerPro.num = i+1;
		}
	});
	
}

function addAnswerxg() {
	var guidr = guid();
	$("#editAnswerxg").append("<div class='form-group' name='div_answersxg' id='div_answerxg"+guidr+"'><label>答案"+answerPro.num+":</label>"
			+"<div class='input-group'>"
			+"<input class='form-control' name='"+guidr+"' type='text' />"
			+"<div class='input-group-btn'><buttion class='btn btn-primary' type='button' onclick=\"delAddAnswerxg('"+guidr+"')\">删除</bution></div></div>"
			+"<input type='file' name='"+guidr+"'  /></div>");
	answerPro.num++;
}

function delAddAnswerxg(num) {
	$('#'+('div_answerxg'+num)).remove();
	var i = 1;
	var elementLabel = $("div[name='div_answersxg']").find("label").each(function (){
		$(this).html("答案"+i+":");
		i++;
	});
	answerPro.num = i;
}