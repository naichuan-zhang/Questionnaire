<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <title>明日科技</title>
    <!-- Bootstrap -->
    <link href="<%=basePath %>bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/ityks.css" rel="stylesheet">
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link href='<%=basePath %>js/fullcalendar2_7/dist/fullcalendar.css' rel='stylesheet' />
	<link href='<%=basePath %>js/fullcalendar2_7/dist/fullcalendar.print.css' rel='stylesheet' media='print' />
  </head>
<body style="background-color: #fefefe;">
	<!-- 这是导航条 -->
	<jsp:include page="./menu.jsp"></jsp:include>


	<div class="container">
		<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
			  <!-- Indicators -->
			  <ol class="carousel-indicators">
			    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
			    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
			  </ol>
			
			  <!-- Wrapper for slides -->
			  <div class="carousel-inner" role="listbox">
			    <div class="item active">
			      <img src="<%=basePath %>photo/focus01.jpg" alt="...">
			      <div class="carousel-caption">
			        
			      </div>
			    </div>
			    <div class="item">
			      <img src="<%=basePath %>photo/focus02.jpg" alt="...">
			      <div class="carousel-caption">
			        
			      </div>
			    </div>
			    
			  </div>
			
			  <!-- Controls -->
			  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
			    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
			    <span class="sr-only">Previous</span>
			  </a>
			  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
			    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			    <span class="sr-only">Next</span>
			  </a>
		</div>
	</div>	
	<!-- 问卷调查 -->
	<div class="container" style="padding-bottom: 70px;">
		<div class="row">
			<div  class="col-lg-12 col-md-12 col-sm-12">
						<table class="table table-bordered table-hover">
							<tr class="danger">
								<th>
									<h5 >序号:</h5>
								</th>
								<th>
									<h5 >问卷标题:</h5>
								</th>
								<th>
									<h5 >创建时间:</h5>
								</th>
								<th>
									<h5 >截止时间:</h5>
								</th>
								<th width="256px">
									<h5 >操作:</h5>
								</th>
							</tr>
							<c:choose>
								<c:when test="${not empty mainList }">
									<c:forEach items="${mainList }" var="item" varStatus="vs">
										<tr class="success">	
											<td>
												<span>${vs.index }</span>
											</td>
											<td>
												<span><a href="<%=basePath %>question/go/${item.mainId }" target="_blank">${item.mainTitle }</a></span>
											</td>
											<td>
												<span><fmt:formatDate pattern="yyyy-MM-dd" value="${item.mainCreat }"/></span>
											</td>
											<td>
												<span><fmt:formatDate pattern="yyyy-MM-dd" value="${item.mainEndtime }"/></span>
											</td>
											<td class="text-center">
													<div class="input-group-btn" id="ssdiv">
														<shiro:hasPermission name="wjdctoupiao">
															<a class="btn btn-info" href="<%=basePath %>question/go/${item.mainId }" target="_blank" >参与投票</a>
														</shiro:hasPermission>
														<shiro:hasPermission name="wjdcchakan">
															<a class="btn btn-success" href="<%=basePath %>question/statistics/${item.mainId }" target="_blank" >查看结果</a>
														</shiro:hasPermission>
													</div>
											</td>
										</tr>	
									</c:forEach>
								</c:when>
							</c:choose>
								<tr>
									<td colspan="6">
					 					${currentPage }
					 				</td>
					 			</tr>
						</table>
			</div>
			<div  class="col-lg-12 col-md-12 col-sm-12" style="display: none;">
				<div class="panel panel-success">
					<div class="panel-heading">
						工作日历
					</div>
					<div class="panel-body">
						<div id='calendar'></div>
					</div>
				</div>
			</div>
		</div>
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
						<p id="alertOKText">您的意见我们已经收到，非常感谢您。</p>
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 悬浮广告位置 -->
	<!-- <div id="showAD" style="position:absolute;z-index:100;width: 350px;" >
		<div style="width: 350px;height: 18px;font-size: 14px;font-weight: bold;text-align: left;cursor: hand;" >
			<font color="ff0000"><span onclick="closed()" id="spanADClose">点击关闭</span></font>
			<font color="00ff00"><span onclick="opened()" id="spanADOpen">点击客服</span></font>
		</div>
		<div class="panel panel-default" style="display: none;" id="panelAD">
			<div class="panel-heading">
				客服小易
			</div>
			<div class="panel-body">
				<div id = "messages" style="position:relative; height:254px; overflow:auto">
					<dl id="solo">
					</dl>
				</div>
				<span id="msgEnd" style="overflow: hidden;"></span>
			</div>
			<div class="panel-footer">
				<div id = "send" class="input-group">
					<input id="sendmessage" class="form-control" placeholder="有什么话想告诉我呢？">
					<span class="input-group-btn"><button onclick="send()" class="btn btn-primary">发送</button></span>
				</div>
			</div>
		</div>
	</div> -->

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
    <script type="text/javascript" src="<%=basePath %>bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src='<%=basePath %>js/fullcalendar2_7/moment/moment.js'></script>
    <script type="text/javascript" src='<%=basePath %>js/fullcalendar2_7/dist/fullcalendar.js'></script>
    <script type="text/javascript" src='<%=basePath %>js/fullcalendar2_7/dist/zh-cn.js'></script>
    <script type="text/javascript" src="<%=basePath %>js/question.js?v=3.1"></script>
<%-- 	<script type="text/javascript" src="<%=basePath %>js/ad.js?v=1.4"></script> --%>
	<script type="text/javascript" src="<%=basePath %>js/home.js"></script>
	<%-- <script type="text/javascript" src="<%=basePath %>js/websocket.js"></script> --%>
</body>
</html>