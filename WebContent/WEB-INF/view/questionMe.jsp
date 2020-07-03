<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String socketPath = request.getServerName()+":"+request.getServerPort()+path+"/";
java.util.Date nowDate = new java.util.Date();
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
String spdfDate = sdf.format(nowDate);
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>明日科技-问卷调查后台维护</title>
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
		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-12 " >
				<shiro:hasPermission name="wjdcshanchu">
					<button class="btn btn-success" onclick="delCheckModal()">删除</button>
				</shiro:hasPermission>	
				<shiro:hasPermission name="wjdcsousuo">
					<button class="btn btn-primary" data-toggle="collapse" data-target="#collapseFind" aria-expanded="false" aria-controls="collapseFind">搜索</button>
				</shiro:hasPermission>
			</div>
		</div>
		<shiro:hasPermission name="wjdcsousuo">
			<div class="collapse" id="collapseFind">
				<div class="well" >
					<form action="" class="form-inline" method="get">
							<label for="mainTitleSS">问卷标题：</label>
							<input name="mainTitle" id="mainTitleSS" type="text" class="form-control" placeholder=""  />
							<label for="mainCreatSS">创建时间：</label>
							<input name="mainStartTime" id="mainCreatSS" type="text" class="form-control" placeholder=""  />
							<label for="mainEndtimeSS">截止时间：</label>
							<input name="mainOverTime" id="mainEndtimeSS" type="text" class="form-control" placeholder=""  />
							<button class="btn btn-primary" type="submit">搜索</button>
					</form>
					<script type="text/javascript">
							jeDate({
								dateCell:"#mainCreatSS",
								format:"YYYY-MM-DD",
								isinitVal:true,
								isTime:false, //isClear:false,
								minDate:"2000-01-01",
								okfun:function(val){alert(val)}
							})
							jeDate({
								dateCell:"#mainEndtimeSS",
								format:"YYYY-MM-DD",
								isinitVal:true,
								isTime:false, //isClear:false,
								minDate:"2000-01-01",
								okfun:function(val){alert(val)}
							})
					</script>
				</div>
			</div>
		</shiro:hasPermission>
		
		<form id="delCheckForm" method="post">
			<table class="table table-bordered table-hover">
			<tr class="danger">
				<th>
					<h5 >序号:</h5>
				</th>
				<th>
					<h5 >选择:</h5>
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
				<th>
					<h5 >是否发布:</h5>
				</th>
				<th>
					<h5 >发布人:</h5>
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
								<input type="checkbox" name="id" value="${item.mainId }">
							</td>
							<td>
								<span>${item.mainTitle }</span>
							</td>
							<td>
								<span><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${item.mainCreat }"/></span>
							</td>
							<td>
								<span><fmt:formatDate pattern="yyyy-MM-dd" value="${item.mainEndtime }"/></span>
							</td>
							<td>
								<span>
									<c:if test="${item.mainIsuse eq 'y' }">
										已发布
									</c:if>
									<c:if test="${item.mainIsuse eq 'n' }">
										未发布
									</c:if>
								</span>
							</td>
							<td>
								<span>${item.mainCreatuser }</span>
							</td>
							<td class="text-center">
								<div class="input-group-btn" id="ssdiv">
									<shiro:hasPermission name="wjdcshanchu">
										<button class="btn btn-primary" type="button" onclick="delModal('${item.mainId }')">删除</button>
									</shiro:hasPermission>
									<shiro:hasPermission name="wjdcbianji">
										<a href="<%=basePath %>question/edit/${item.mainId }" class="btn btn-info">编辑</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="wjdcfabu">
										<button type="button" class="btn btn-warning" onclick="actionStart('${item.mainId }')">发布</button>
									</shiro:hasPermission>
									<shiro:hasPermission name="wjdcfuzhi">
										<button type="button" class="btn btn-danger" onclick="copyQuestion('${item.mainId }')">复制</button>
									</shiro:hasPermission>
									<shiro:hasPermission name="wjdcchakan">
										<a class="btn btn-success" href="<%=basePath %>question/statistics/${item.mainId }" >查看结果</a>
									</shiro:hasPermission>
								</div>
							</td>
						</tr>	
					</c:forEach>
				</c:when>
			</c:choose>
				<tr>
					<td colspan="8">
	 					${currentPage }
	 				</td>
	 			</tr>
			</table>
		</form>
	</div>
	
	<div class="container">
		<div class="modal fade" id="delModal">
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
						<button class="btn btn-primary" onclick="deleteQyestion()">确定</button>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" id="tempId" value="">
	</div>
	
	<div class="container">
		<div class="modal fade" id="delCheckModal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span>
							<span class="sr-only">Close</span>
						</button>
					</div>
					<div class="modal-body">
						<p>您确定要删除这些数据吗？</p>
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal">关闭</button>
						<button class="btn btn-primary" onclick="submitForm('#delCheckForm','<%=basePath%>question/delCheck')">确定</button>
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
		var socketPath = '<%=socketPath%>';
	</script>
    <script src="<%=basePath %>bootstrap-3.3.5-dist/js/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%=basePath %>bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
    <script src="<%=basePath %>js/question.js?v=3.1"></script>
</body>
</html>