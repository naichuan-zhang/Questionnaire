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
  </head>
<body style="background-color: #fefefe;">
	<!-- 这是导航条 -->
	<jsp:include page="./menu.jsp"></jsp:include>

	<div class="container">
		<form action="<%=basePath %>question/addAnswer" method="post" class="form-horizontal">
			<input type="hidden" name="mainId" value="${mainId }">
			<input type="hidden" name="questionId" value="${questionId }">
			<div class="form-group">
				<label for="questionTitle">问题描述：</label>
				<input id="questionTitle" value="${questionnaireQuestion.questionTitle }" name="questionTitle" class="form-control" placeholder="输入一个问题" maxlength="255">
			</div>
			<div class="form-group">
				<label for="questionNum">答案数量：</label>
				<select class="form-control" name="questionNum" id="questionNum">
					<option value="1">一个答案</option>
					<option selected="selected" value="2">两个答案</option>
					<option value="3">三个答案</option>
					<option value="4">四个答案</option>
					<option value="5">五个答案</option>
					<option value="6">六个答案</option>
					<option value="7">七个答案</option>
					<option value="8">八个答案</option>
				</select>
			</div>
			<div class="form-group">
				<label for="questionType">问题类型：</label>
				<select class="form-control" name="questionType" id="questionType">
					<option selected="selected" value="radio">单选</option>
					<option value="check">多选</option>
				</select>
			</div>
			<div class="form-group">
				<label for="questionDestype">答案类型：</label>
				<select class="form-control" name="questionDestype" id="questionType">
					<option selected="selected" value="text">文字</option>
					<option value="img">图片(不得大于1M)</option>
					<option value="file">附件(不得大于1M)</option>
				</select>
			</div>
			<p class="text-right"><button type="submit" class="btn btn-primary" ><span class="text-right">下一步</span></button></p>
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
</body>
</html>