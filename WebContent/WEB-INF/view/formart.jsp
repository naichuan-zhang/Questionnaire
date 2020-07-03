<%@page import="com.mrkj.ygl.entity.questionnaire.QuestionnaireMain"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
java.util.Date nowDate = new java.util.Date();
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
String spdfDate = sdf.format(nowDate);
/* java.util.Map questionnaireMap = (java.util.Map) request.getAttribute("questionnaire");
QuestionnaireMain mainMap = (QuestionnaireMain) questionnaireMap.get("main");
 */

%>


<button class="btn btn-primary" type="button" onclick="delModal('${item.mainId }')">
删除
</button>

<script type="text/javascript">
function openEditModelxg (questionId){
	$("#editModalxg").modal('show');			//显示模态窗口
	$("div[name='div_answersxg']").remove();	//移除上一次问题内容
	
	$.ajax({
		url:basePath+"question/editQuestionnaire/"+questionId,	//ajax请求路径
		type:'GET',												//ajax请求方法
		success:function (data){								//请求返回参数
			var answers = data.answers;							//获取答案
			var question = data.question;						//获取问题
			$("#questionIdxg").val(question.questionId);		//设置问题Id
			$("#questionTitlexg").val(question.questionTitle);	//设置问题描述
			//设置单选多选
			$("#questionTypexg option[value="+question.questionType+"]")
														.attr("selected","selected");
			var i=0;
			for (i;i<answers.length;i++){
				var guidr = guid();
				var tempAnswerDestype = answers[i].answerDestype;
				if (answers[i].answerType == 'y'){				//判断是否拥有附件
					var lastIF = answers[i].answerDestype.lastIndexOf("(");
					var temp = answers[i].answerDestype.substring(0,lastIF);
					tempAnswerDestype = temp;
				}
				//显示答案
				$("#editAnswerxg").append("<div class='form-group' name='div_answersxg'"
						+" id='div_answerxg"+guidr+"'><label>答案"+(i+1)+":</label>"
						+"<div class='input-group'>"
						+"<input class='form-control' name='"+answers[i].answerId
						+"' type='text' value='"+tempAnswerDestype+"' />"
						+"<div class='input-group-btn'><buttion class='btn btn-primary'"
						+" type='button' onclick=\"delAddAnswerxg('"
						+guidr+"')\">删除</bution></div></div>"
						+"<input type='file' name='"
						+answers[i].answerId+"' /></div>");
			}
			answerPro.num = i+1;
		}
	});
}
</script>


<div class="container">
	<!-- Bootstrap 模态窗 -->
	<div class="modal fade" id="editModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- 模态窗头部-->
				<div class="modal-header">
					<!-- 增加答案按钮 -->
					<button class="btn btn-success" type="button" 
					onclick="addAnswer()" >增加答案</button>
					<!-- 关闭窗体按钮 -->
					<button class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
						<span class="sr-only">Close</span>
					</button>
				</div>
				<!-- 模态窗体 -->
				<div class="modal-body" id="editModalBody">
					<!-- 表单-->
					<form id="editAnswer" 
					action="<%=basePath %>question/addQuestionTestAndFile" 
					method="post" enctype="multipart/form-data" >
						<!-- 隐藏的mainId，维护关系链 -->
						<input type="hidden" id="mainId" name="mainId" 
						value="${questionnaire.main.mainId}">
						<div class="form-group">
							<label>问题:</label>
							<!-- 问题标题 -->
							<input type="text" class="form-control" 
							name="questionTitle" />
						</div>
						<div class="form-group">
							<label for="questionType">问题类型：</label>
							<!-- 单选或多选 -->
							<select class="form-control" 
							name="questionType" id="questionType">
								<option selected="selected" value="radio">单选</option>
								<option value="check">多选</option>
							</select>
						</div>
					</form>
				</div>
				<!-- 模态窗底部 -->
				<div class="modal-footer">
					<!-- 关闭按钮 -->
					<button class="btn btn-default" data-dismiss="modal">关闭</button>
					<!-- 确定按钮即保存按钮 -->
					<button class="btn btn-primary" type="button" 
					onclick="submitFormById('editAnswer')">确定</button>
				</div>
			</div>
		</div>
	</div>
</div>

