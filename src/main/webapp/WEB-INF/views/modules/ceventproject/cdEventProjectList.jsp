<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>赛事专题管理</title>
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
		<li class="active"><a href="${ctx}/ceventproject/cdEventProject/">赛事专题列表</a></li>
		<shiro:hasPermission name="ceventproject:cdEventProject:edit">
			<li><a href="${ctx}/ceventproject/cdEventProject/form">赛事专题添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdEventProject" action="${ctx}/ceventproject/cdEventProject/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>发布时间</th>
				<shiro:hasPermission name="ceventproject:cdEventProject:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdEventProject">
			<tr>
				<td><a href="${ctx}/ceventproject/cdEventProject/form?id=${cdEventProject.id}">${cdEventProject.name}</a></td>
				<td>${fn:substring(cdEventProject.createDate,0 ,19 )}</td>
				<shiro:hasPermission name="ceventproject:cdEventProject:edit">
					<td>
	    				<a href="${ctx}/ceventproject/cdEventProject/form?id=${cdEventProject.id}">修改</a>
						<a href="${ctx}/ceventproject/cdEventProject/delete?id=${cdEventProject.id}" onclick="return confirmx('确认要删除该赛事专题吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
