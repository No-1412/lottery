<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中奖订单管理</title>
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
		<li class="active"><a href="${ctx}/corder/cdOrderWinners/">中奖订单列表</a></li>
		<shiro:hasPermission name="corder:cdOrderWinners:edit">
			<li><a href="${ctx}/corder/cdOrderWinners/form">中奖订单添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdOrderWinners" action="${ctx}/corder/cdOrderWinners/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>备注</th>
				<shiro:hasPermission name="corder:cdOrderWinners:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdOrderWinners">
			<tr>
				<td><a href="${ctx}/corder/cdOrderWinners/form?id=${cdOrderWinners.id}">${cdOrderWinners.name}</a></td>
				<td>${cdOrderWinners.remarks}</td>
				<shiro:hasPermission name="corder:cdOrderWinners:edit">
					<td>
	    				<a href="${ctx}/corder/cdOrderWinners/form?id=${cdOrderWinners.id}">修改</a>
						<a href="${ctx}/corder/cdOrderWinners/delete?id=${cdOrderWinners.id}" onclick="return confirmx('确认要删除该中奖订单吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
