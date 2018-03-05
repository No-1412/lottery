<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>大乐透订单管理</title>
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
		<li class="active"><a href="${ctx}/clottoreward/cdLottoOrder/">大乐透订单列表</a></li>
		<%--<shiro:hasPermission name="clottoreward:cdLottoOrder:edit">--%>
			<%--<li><a href="${ctx}/clottoreward/cdLottoOrder/form">大乐透订单添加</a></li>--%>
		<%--</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cdLottoOrder" action="${ctx}/clottoreward/cdLottoOrder/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>订单号 ：</label><form:input path="orderNum" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单号</th>
				<th>前区(胆|拖)</th>
				<th>后区(胆|拖)</th>
				<th>期数</th>
				<th>注数</th>
				<th>金额</th>
				<th>玩法</th>
				<%--<th>用户</th>--%>
				<th>下单时间</th>
				<th>订单状态</th>
				<shiro:hasPermission name="clottoreward:cdLottoOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdLottoOrder">
			<tr>
				<td><a href="${ctx}/clottoreward/cdLottoOrder/form?id=${cdLottoOrder.id}">${cdLottoOrder.orderNum}</a></td>
				<td>${cdLottoOrder.redNums}</td>
				<td>${cdLottoOrder.blueNums}</td>
				<td>${cdLottoOrder.weekday}</td>
				<td>${cdLottoOrder.acount}</td>
				<td>${cdLottoOrder.price}</td>
				<td><%--${cdLottoOrder.type}--%>
					<c:choose>
						<c:when test="${cdLottoOrder.type==1}">
							普通
						</c:when>
						<c:when test="${cdLottoOrder.type==2}">
							胆拖
						</c:when>

						<c:otherwise>
							订单异常
						</c:otherwise>
					</c:choose>
				</td>
			<%--	<td>${cdLottoOrder.uid}</td>--%>
				<td>${cdLottoOrder.createDate}</td>
				<td><%--${cdLottoOrder.stauts}--%>
					<c:choose>
						<c:when test="${cdLottoOrder.status==1}">
							未付款
						</c:when>
						<c:when test="${cdLottoOrder.status==2}">
							已付款
						</c:when>
						<c:when test="${cdLottoOrder.status==3}">
							已出票
						</c:when>

						<c:otherwise>
							订单异常
						</c:otherwise>
					</c:choose></td>
				<shiro:hasPermission name="clottoreward:cdLottoOrder:edit">
					<td>
	    				<a href="${ctx}/clottoreward/cdLottoOrder/form?id=${cdLottoOrder.id}">查看/出票</a>
						<a href="${ctx}/clottoreward/cdLottoOrder/delete?id=${cdLottoOrder.id}" onclick="return confirmx('确认要删除该大乐透订单吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
