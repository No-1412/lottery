<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>排行信息管理</title>
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
		<li class="active"><a href="${ctx}/erp/cdTopUser/">排行信息列表</a></li>
		<shiro:hasPermission name="erp:cdTopUser:edit">
			<li><a href="${ctx}/erp/cdTopUser/form">排行信息添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdTopUser" action="${ctx}/erp/cdTopUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>排序</th>
				<shiro:hasPermission name="erp:cdTopUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdTopUser">
			<tr>
				<td><a href="${ctx}/erp/cdTopUser/form?id=${cdTopUser.id}">${cdTopUser.user_id}</a></td>
				<td>${cdTopUser.sort}</td>
				<shiro:hasPermission name="erp:cdTopUser:edit">
					<td>
	    				<a href="${ctx}/erp/cdTopUser/form?id=${cdTopUser.id}">修改</a>
						<a href="${ctx}/erp/cdTopUser/delete?id=${cdTopUser.id}" onclick="return confirmx('确认要删除该排行信息吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
