<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收藏记录管理</title>
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
		<li class="active"><a href="${ctx}/lom/lomCollect/">收藏记录列表</a></li>
		<shiro:hasPermission name="lom:lomCollect:edit">
			<li><a href="${ctx}/lom/lomCollect/form">收藏记录添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="lomCollect" action="${ctx}/lom/lomCollect/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>此功能用于开发时查看数据，如客户需要则必须按要求调整。</label>
		<%--<form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>--%>
		<%--&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>--%>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户id</th>
				<th>收藏id</th>
				<th>分类</th>
				<shiro:hasPermission name="lom:lomCollect:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="lomCollect">
			<tr>
				<td><a href="${ctx}/lom/lomCollect/form?id=${lomCollect.id}">${lomCollect.userId}</a></td>
				<td>${belongId}</td>
				<td>${fns:getDictLabel(lomCollect.kind, "lom_collect_type", "未知")}</td>
				<shiro:hasPermission name="lom:lomCollect:edit">
					<td>
	    				<a href="${ctx}/lom/lomCollect/form?id=${lomCollect.id}">修改</a>
						<a href="${ctx}/lom/lomCollect/delete?id=${lomCollect.id}" onclick="return confirmx('确认要删除该收藏记录吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
