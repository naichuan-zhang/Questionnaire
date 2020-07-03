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
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>明日科技-问卷维护</title>
    <!-- Bootstrap -->
    <link href="<%=basePath %>bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/ityks.css" rel="stylesheet">
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="<%=basePath %>js/jedate/jedate.js"></script>
  </head>
<body style="background-color: #fefefe;">
	<!-- 这是导航条 -->
	<jsp:include page="./menu.jsp"></jsp:include>

	<div class="container">
		<a class="btn btn-primary" href="<%=basePath %>question/add/${questionnaire.main.mainId}" >增加问题</a>
		<table class="table">
			<tr class="danger">
				<th>
					<div class="row">
						<div class="col-xs-6">
							<h5 style="padding-left: 40px" id="h5maintitle">${questionnaire.main.mainTitle}</h5>
							<form class="form-inline" id="editform" style="display: none">
							<div class="form-group">
								<label for="mainTitleInput">问卷标题:</label>
								<input class="form-control" id="mainTitleInput" name="mainTitleInput" type="text" value="${questionnaire.main.mainTitle}"  />
							</div>
							<div class="form-group">
								<label for="mainEmdtimeInput">截止时间:</label>
								<input class="form-control" id="mainEndtimeInput" name="mainEndtime" type="text" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${questionnaire.main.mainEndtime }"/>' />
							</div>
							</form>
							<script type="text/javascript">
								jeDate({
									dateCell:"#mainEndtimeInput",
									format:"YYYY-MM-DD",
									isinitVal:true,
									isTime:false, //isClear:false,
									minDate:"<%=spdfDate%>",
									okfun:function(val){alert(val)}
								})
							</script>
						</div>
						<div class="col-xs-6">
							<p class="text-right">
							<a href="javascript:void(0)" class="btn btn-info" onclick="showEdit()" id="btnxg" >修改</a>
							<a href="javascript:void(0)" class="btn btn-success" style="display:none; " id="btnqx" onclick="cancelEdit()">取消</a>
							<a href="javascript:void(0)" class="btn btn-warning" style="display:none; " id="btnbc" onclick="editQuestionMainTitle('${questionnaire.main.mainId}')">保存</a>
							</p>
						</div>
					</div>
				</th>
			</tr>
			<c:choose>
				<c:when test="${not empty questionnaire.question }">
					<c:forEach items="${questionnaire.question }" var="question" varStatus="vs">
						<tr class="info">
							<th >
								<div class="row">
									<div class="col-xs-6">
										<h4 style="padding-left: 40px"><small>Question ${vs.index+1 }：${question.questionTitle }</small></h4>
									</div>
									<div class="col-xs-6 text-right">
										<a href="<%=basePath %>question/editAnswer/${questionnaire.main.mainId }/${question.questionId }" class="btn btn-warning">修改</a>
										<button type="button" class="btn btn-info" onclick="delWt('${question.questionId }')">删除</button>
									</div>
								</div>
							</th>
						</tr>
						<tr class="success">
							<c:choose>
								<c:when test="${not empty questionnaire.answer }">
									<td style="padding-left: 40px"><%//答案开始咯 %>
									<br />
									<c:forEach items="${questionnaire.answer}" var="answer" varStatus="vs">
										<c:if test="${question.questionId eq answer.questionId }">
											<c:if test="${question.questionType eq 'radio'}"><%//这里是单选 %>
												<c:if test="${question.questionDestype eq 'text'}"><%//这里是文字类答案 %>
													<label>
														<input type="radio" name="${question.questionId }" value="${answer.answerId }"><span style="padding-right: 40px">${answer.answerText }</span>
													</label>
												</c:if>
												<c:if test="${question.questionDestype eq 'img'}"><%//这里是图片类答案 %>
													<label>
														<input type="radio" name="${question.questionId }"><span style="padding-right: 40px"><img width="128px" height="128px" alt="" src="<%=basePath %>${answer.answerText }" /></span>
													</label>
												</c:if>
												<c:if test="${question.questionDestype eq 'file'}"><%//这里是图片类答案 %>
													<label>
														<input type="radio" name="${question.questionId }"><span style="padding-right: 40px"><a href="<%=basePath %>${answer.answerText }" target="_blank" >${answer.answerDestype }</a></span>
													</label>
												</c:if>
											</c:if>
											<c:if test="${question.questionType eq 'check'}"><%//这里是多选 %>
												<c:if test="${question.questionDestype eq 'text'}"><%//这里是文字类答案 %>
													<label>
														<input type="checkbox" name="${question.questionId }" value="${answer.answerId }"><span style="padding-right: 40px">${answer.answerText }</span>
													</label>
												</c:if>
												<c:if test="${question.questionDestype eq 'img'}"><%//这里是图片类答案 %>
													<label>
														<input type="checkbox" name="${question.questionId }"><span style="padding-right: 40px"><img width="128px" height="128px" alt="" src="<%=basePath %>${answer.answerText }" /></span>
													</label>
												</c:if>
												<c:if test="${question.questionDestype eq 'file'}"><%//这里是图片类答案 %>
													<label>
														<input type="checkbox" name="${question.questionId }"><span style="padding-right: 40px"><a href="<%=basePath %>${answer.answerText }" target="_blank" >${answer.answerDestype }</a></span>
													</label>
												</c:if>
											</c:if>
										</c:if>
									</c:forEach>
										<br />
									</td>
								</c:when>
								<c:otherwise>
									<td style="padding-left: 40px">
										<span>暂无任何数据</span>
									</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="warning">
						<td>
							<span>暂无任何数据</span>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
	
	<div class="container">
		<div class="modal fade" id="delQuestionModal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span>
							<span class="sr-only">Close</span>
						</button>
					</div>
					<div class="modal-body">
						<p>您确定要删除该条数据吗？</p>
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal">关闭</button>
						<button class="btn btn-primary" onclick="deleteQuestion()">确定</button>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" id="tempQuestionId" value="">
	</div>
	
	<div class="container">
		<div class="modal fade" id="alertOK">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span>
							<span class="sr-only">Close</span>
						</button>
					</div>
					<div class="modal-body">
						<p id="alertOKText">成功</p>
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<footer class="footer navbar-fixed-bottom ">
	    <div class="container" style="background-color: #000000">
	    	<div class="row">
	                <div class="col-sm-12">
	                    <span><a href="http://www.mrkj.com/">明日科技</a></span> | 
	                    <span>Copyright &copy; <a href="http://www.mrkj.com/">吉林省明日科技有限公司</a></span> | 
	                    <span>吉ICP备16003039号-1</span>
	                    <span>站长QQ:80303857</span>
	                </div>
	        </div>
	    </div>
	</footer>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script type="text/javascript">
		var basePath = '<%=basePath%>';
	</script>
    <script src="<%=basePath %>bootstrap-3.3.5-dist/js/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%=basePath %>bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
    <script src="<%=basePath %>js/question.js?v=3.0"></script>
</body>
</html>