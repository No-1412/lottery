<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>胜负彩订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/csuccessfail/cdSuccessFailOrder/">胜负彩订单列表</a></li>
		<%--<shiro:hasPermission name="csuccessfail:cdSuccessFailOrder:edit">
			<li><a href="${ctx}/csuccessfail/cdSuccessFailOrder/form">胜负彩订单添加</a></li>
		</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cdSuccessFailOrder" action="${ctx}/csuccessfail/cdSuccessFailOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>订单号 ：</label><form:input path="orderNumber" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单号</th>
				<th>期数</th>
				<th>注数</th>
				<th>倍数</th>
				<th>金额</th>
				<th>用户</th>
				<th>下单时间</th>
				<th>订单状态</th>
				<shiro:hasPermission name="csuccessfail:cdSuccessFailOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdSuccessFailOrder">
			<tr>
				<td><a href="${ctx}/csuccessfail/cdSuccessFailOrder/form?id=${cdSuccessFailOrder.id}">${cdSuccessFailOrder.orderNumber}</a></td>
				<td>${cdSuccessFailOrder.weekday}</td>
				<td>${cdSuccessFailOrder.acount}</td>
				<td>${cdSuccessFailOrder.times}倍</td>
				<td>${cdSuccessFailOrder.price}</td>
				<td>${cdSuccessFailOrder.uid}</td>
				<td>${cdSuccessFailOrder.createDate}</td>
				<td><c:choose>
					<c:when test="${cdSuccessFailOrder.status==1}">
						未付款
					</c:when>
					<c:when test="${cdSuccessFailOrder.status==2}">
						已付款
					</c:when>
					<c:when test="${cdSuccessFailOrder.status==3}">
						已出票
					</c:when>
					<c:otherwise>
						订单异常
					</c:otherwise>
				</c:choose></td>
				<shiro:hasPermission name="csuccessfail:cdSuccessFailOrder:edit">
					<td>
	    				<a href="${ctx}/csuccessfail/cdSuccessFailOrder/form?id=${cdSuccessFailOrder.id}">查看/出票</a>
						<a href="${ctx}/csuccessfail/cdSuccessFailOrder/delete?id=${cdSuccessFailOrder.id}" onclick="return confirmx('确认要删除该胜负彩订单吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
