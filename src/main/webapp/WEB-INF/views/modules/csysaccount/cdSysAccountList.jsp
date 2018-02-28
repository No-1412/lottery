<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台系统账户管理</title>
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
		<li class="active"><a href="${ctx}/csysaccount/cdSysAccount/">平台系统账户列表</a></li>
		<%--<shiro:hasPermission name="csysaccount:cdSysAccount:edit">
			<li><a href="${ctx}/csysaccount/cdSysAccount/form">平台系统账户添加</a></li>
		</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cdSysAccount" action="${ctx}/csysaccount/cdSysAccount/" method="post" class="breadcrumb form-search">
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
			<th>账号</th>
			<th>可以余额</th>
			<th>冻结余额</th>
			<th>可用积分</th>
			<th>冻结积分</th>
			<%--<shiro:hasPermission name="csysaccount:cdSysAccount:edit"><th>操作</th></shiro:hasPermission>--%>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdSysAccount">
			<tr>
				<td>${cdSysAccount.name}</td>
				<td>${cdSysAccount.account}</td>
				<td>${cdSysAccount.balance}</td>
				<td>${cdSysAccount.frzeeBalance}</td>
				<td>${cdSysAccount.balanceScore}</td>
				<td>${cdSysAccount.frzeeScore}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
