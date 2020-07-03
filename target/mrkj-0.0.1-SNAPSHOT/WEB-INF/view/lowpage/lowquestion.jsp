<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String socketPath = request.getServerName()+":"+request.getServerPort()+path+"/";
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
  </head>
<body style="background-color: #fefefe;">
	<!-- 这是导航条 -->
	<jsp:include page="../menu.jsp"></jsp:include>
	<div class="container" style="padding-bottom: 70px;">
		
		<form id="actionSub" >
			<table class="table">
				<tr class="danger">
					<th>
						<div class="row">
							<div class="col-xs-6">
								<h4 style="padding-left: 40px">${questionnaire.main.mainTitle}</h4>
							</div>
							<div class="col-xs-2">
								<h4><cite title="发布人">${questionnaire.main.mainCreatuser}</cite></h4>
							</div>
							<div class="col-xs-2">
								<h4><cite title="发布时间"><fmt:formatDate pattern="yyyy-MM-dd" value="${questionnaire.main.mainCreat }"/></cite></h4>
							</div>
							<div class="col-xs-2">
								<h5><cite title="统计">总人数/答题人数/百分比</cite></h5>
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
										<div class="col-xs-12">
											<h4 style="padding-left: 40px"><small>问题 ${vs.index+1 }：${question.questionTitle }</small></h4>
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
													<c:if test="${answer.answerType eq 'y'}"><%//这里是图片类答案 %>
														<div class="row">
															<div class="col-xs-5">
																<label>
																	<input type="radio" value="${answer.answerId }" name="${question.questionId }" ><span style="padding-right: 45px"><a href="<%=basePath %>${answer.answerText }" target="_blank" >${answer.answerDestype }</a></span>
																</label>
															</div>
															<div class="col-xs-5">
																<div class="progress">
																	<c:if test="${answer.answerValue==0}">
																		<c:set var="count" value="${answer.answerValue }"></c:set>
																	</c:if>
																	<c:if test="${answer.answerValue!=0}">
																		<c:set var="count" value="${answer.answerValue*100/questionnaire.counts[question.questionId] }"></c:set>
																	</c:if>
																	<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="${answer.answerValue }" aria-valuemin="0" aria-valuemax="100" style="width: <fmt:formatNumber type="number" value="${count }" pattern="0.00" minFractionDigits="0"></fmt:formatNumber>% ">
																		<span class="sr-only">${answer.answerValue }</span>
																	</div>
																</div>
															</div>
															<div class="col-xs-2">
																<h5><cite title="">${questionnaire.main.mainCreatdep}人/${answer.answerValue }人/<fmt:formatNumber type="number" value="${count }" pattern="0.00" minFractionDigits="2"></fmt:formatNumber>%</cite></h5>
															</div>
														</div>
														<br>
													</c:if>
													<c:if test="${answer.answerType eq 'n'}">
														<div class="row">
															<div class="col-xs-5">
																<label>
																	<input type="radio" name="${question.questionId }" value="${answer.answerId }"><span style="padding-right: 45px">${answer.answerDestype }</span>
																</label>
															</div>
															<div class="col-xs-5">
																<div class="progress">
																	<c:if test="${answer.answerValue==0}">
																		<c:set var="count" value="${answer.answerValue }"></c:set>
																	</c:if>
																	<c:if test="${answer.answerValue!=0}">
																		<c:set var="count" value="${answer.answerValue*100/questionnaire.counts[question.questionId] }"></c:set>
																	</c:if>
																	<div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="${answer.answerValue }" aria-valuemin="0" aria-valuemax="100" style="width: <fmt:formatNumber type="number" value="${count }" pattern="0.00" minFractionDigits="0"></fmt:formatNumber>% " >
																		<span class="sr-only">${answer.answerValue }</span>
																	</div>
																</div>
															</div>
															<div class="col-xs-2">
																<h5><cite title="">${questionnaire.main.mainCreatdep}人/${answer.answerValue }人/<fmt:formatNumber type="number" value="${count }" pattern="0.00" minFractionDigits="2"></fmt:formatNumber>%</cite></h5>
															</div>
														</div>
														<br>
													</c:if>
												</c:if>
												<c:if test="${question.questionType eq 'check'}"><%//这里是多选 %>
													<c:if test="${answer.answerType eq 'y'}"><%//这里是图片类答案 %>
														<div class="row">
															<div class="col-xs-5">
																<label>
																	<input type="checkbox" name="${question.questionId }" value="${answer.answerId }"><span style="padding-right: 45px"><a href="<%=basePath %>${answer.answerText }" target="_blank" >${answer.answerDestype }</a></span>
																</label>
															</div>
															<div class="col-xs-5">
																<div class="progress">
																	<c:if test="${answer.answerValue==0}">
																		<c:set var="count" value="${answer.answerValue }"></c:set>
																	</c:if>
																	<c:if test="${answer.answerValue!=0}">
																		<c:set var="count" value="${answer.answerValue*100/questionnaire.counts[question.questionId] }"></c:set>
																	</c:if>
																	<div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="${answer.answerValue }" aria-valuemin="0" aria-valuemax="100" style="width: <fmt:formatNumber type="number" value="${count }" pattern="0.00" minFractionDigits="0"></fmt:formatNumber>% ">
																		<span class="sr-only">${answer.answerValue }</span>
																	</div>
																</div>
															</div>
															<div class="col-xs-2">
																<h5><cite title="">${questionnaire.main.mainCreatdep}人/${answer.answerValue }人/<fmt:formatNumber type="number" value="${count }" pattern="0.00" minFractionDigits="2"></fmt:formatNumber>%</cite></h5>
															</div>
														</div>
														<br>
													</c:if>
													<c:if test="${answer.answerType eq 'n'}">
														<div class="row">
															<div class="col-xs-5">
																<label>
																	<input type="checkbox" name="${question.questionId }" value="${answer.answerId }"><span style="padding-right: 45px">${answer.answerDestype }</span>
																</label>
															</div>
															<div class="col-xs-5">
																<div class="progress">
																	<c:if test="${answer.answerValue==0}">
																		<c:set var="count" value="${answer.answerValue }"></c:set>
																	</c:if>
																	<c:if test="${answer.answerValue!=0}">
																		<c:set var="count" value="${answer.answerValue*100/questionnaire.counts[question.questionId] }"></c:set>
																	</c:if>
																	<div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="${answer.answerValue }" aria-valuemin="0" aria-valuemax="100" style="width: <fmt:formatNumber type="number" value="${count }" pattern="0.00" minFractionDigits="0"></fmt:formatNumber>% ">
																		<span class="sr-only">${answer.answerValue }</span>
																	</div>
																</div>
															</div>
															<div class="col-xs-2">
																<h5><cite title="">${questionnaire.main.mainCreatdep}人/${answer.answerValue }人/<fmt:formatNumber type="number" value="${count }" pattern="0.00" minFractionDigits="2"></fmt:formatNumber>%</cite></h5>
															</div>
														</div>
														<br>
													</c:if>
												</c:if>
											</c:if>
										</c:forEach>
											<br />
										</td>
									</c:when>
									<c:otherwise>
										<td style="padding-left: 40px">
											<span>暂无任何答案</span>
										</td>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="warning">
							<td>
								<span>暂无任何问题</span>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
			<input type="hidden" name="mainId" value="${questionnaire.main.mainId}">
			<div id = "send" class="input-group">
				<input id="message" class="form-control" placeholder="还有什么补充吗?" name="message" value="">
				<span class="input-group-btn"><button type="button" class="btn btn-primary" onclick="submitAnswer('#actionSub','<%=basePath %>question/actionSub')">交卷</button></span>
			</div>
		</form>
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
						<button class="btn btn-default" data-dismiss="modal" type="button" onclick="closepage();">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="container">
		<div class="modal fade bs-example-modal-lg" id="showImgModal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span>
							<span class="sr-only">Close</span>
						</button>
					</div>
					<div class="modal-body" id="showImgModalBody">
						<img id="imgshow" alt="" src="" width="525" height="737" />
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal" type="button" >关闭</button>
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
    <script src="<%=basePath %>js/showImg.js?v=6.0"></script>
</body>
</html>