<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>明日科技-增加答案</title>
    <!-- Bootstrap -->
    <link href="<%=basePath %>bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/ityks.css" rel="stylesheet">
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body style="background-color: #fefefe;">
	<!-- 这是导航条 -->
	<jsp:include page="./menu.jsp"></jsp:include>

	<div class="container">
	
		<c:choose>
			<c:when test="${questionDestype eq 'text' }">
				<form id="AnswerForm" action="<%=basePath %>question/addAnswerText" method="post" class="form-horizontal">
			</c:when>
			<c:otherwise>
				<form id="AnswerForm" action="<%=basePath %>question/addAnswerFile" method="post" class="form-horizontal" enctype="multipart/form-data">
			</c:otherwise>
		</c:choose>
			<input type="hidden" name="questionId" value="${questionId }">
			<input type="hidden" name="questionNum" value="${questionNum }">
			<input type="hidden" name="mainId" value="${mainId }">
			<input type="hidden" name="questionDestype" value="${questionDestype }">
			<div class="form-group">
				<label for="questionTitle">问题描述：</label>
				<input id="questionTitle" name="questionTitle" value="${questionTitle }" class="form-control" disabled="disabled" readonly="readonly">
			</div>
			<c:choose>
				<c:when test="${not empty questionNum }">
					<c:if test="${questionDestype eq 'text'}">
						<c:forEach begin="1" end="${questionNum }" varStatus="vs">
							<div class="form-group">
								<label for="question${vs.index }">答案${vs.index }：</label>
								<input id="question${vs.index }" name="answerText" class="form-control" placeholder="输入一个答案">
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${questionDestype eq 'img' or questionDestype eq 'file'}">
						<c:forEach begin="1" end="${questionNum }" varStatus="vs">
							<div class="form-group">
								<label for="question${vs.index }">答案${vs.index }：</label>
								<input id="question${vs.index }" name="answerFile" type="file" onchange="checkfile(this)">
							</div>
						</c:forEach>
					</c:if>
				</c:when>
			</c:choose>
			<p class="text-right"><button type="button" class="btn btn-primary" onclick="submitAnswer()"><span class="text-right">完成</span></button></p>
		</form>	
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
    <script src="<%=basePath %>js/validat.js?v=0.9"></script>
</body>
</html>