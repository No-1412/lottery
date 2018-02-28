<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户积分信息管理</title>
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
		<li class="active"><a href="${ctx}/cscore/cdScore/">用户积分信息列表</a></li>
		<shiro:hasPermission name="cscore:cdScore:edit">
			<li><a href="${ctx}/cscore/cdScore/form">用户积分信息添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdScore" action="${ctx}/cscore/cdScore/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="cscore:cdScore:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdScore">
			<tr>
				<td><a href="${ctx}/cscore/cdScore/form?id=${cdScore.id}">${cdScore.name}</a></td>
				<td>${cdScore.remarks}</td>
				<shiro:hasPermission name="cscore:cdScore:edit">
					<td>
	    				<a href="${ctx}/cscore/cdScore/form?id=${cdScore.id}">修改</a>
						<a href="${ctx}/cscore/cdScore/delete?id=${cdScore.id}" onclick="return confirmx('确认要删除该用户积分信息吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
