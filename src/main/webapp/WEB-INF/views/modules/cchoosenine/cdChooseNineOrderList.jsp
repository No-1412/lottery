<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任选九订单管理</title>
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
		<li class="active"><a href="${ctx}/cchoosenine/cdChooseNineOrder/">任选九订单列表</a></li>
		<%--<shiro:hasPermission name="cchoosenine:cdChooseNineOrder:edit">
			<li><a href="${ctx}/cchoosenine/cdChooseNineOrder/form">任选九订单添加</a></li>
		</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cdChooseNineOrder" action="${ctx}/cchoosenine/cdChooseNineOrder/" method="post" class="breadcrumb form-search">
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
				<th>金额</th>
				<th>用户</th>
				<th>下单时间</th>
				<th>订单状态</th>
				<shiro:hasPermission name="cchoosenine:cdChooseNineOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdChooseNineOrder">
			<tr>
				<td><a href="${ctx}/cchoosenine/cdChooseNineOrder/form?id=${cdChooseNineOrder.id}">${cdChooseNineOrder.orderNumber}</a></td>
				<td>${cdChooseNineOrder.weekday}</td>
				<td>${cdChooseNineOrder.acount}</td>
				<td>${cdChooseNineOrder.price}</td>
				<td>${cdChooseNineOrder.uid}</td>
				<td>${cdChooseNineOrder.createDate}</td>
				<td><c:choose>
					<c:when test="${cdChooseNineOrder.status==1}">
						未付款
					</c:when>
					<c:when test="${cdChooseNineOrder.status==2}">
						已付款
					</c:when>
					<c:when test="${cdChooseNineOrder.status==3}">
						已出票
					</c:when>
					<c:otherwise>
						订单异常
					</c:otherwise>
				</c:choose></td>
				<%--<td>${cdChooseNineOrder.status}</td>--%>
				<shiro:hasPermission name="cchoosenine:cdChooseNineOrder:edit">
					<td>
	    				<a href="${ctx}/cchoosenine/cdChooseNineOrder/form?id=${cdChooseNineOrder.id}">查看/出票</a>
						<a href="${ctx}/cchoosenine/cdChooseNineOrder/delete?id=${cdChooseNineOrder.id}" onclick="return confirmx('确认要删除该任选九订单吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
