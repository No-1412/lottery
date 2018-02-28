<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统银行卡管理</title>
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
		<li class="active"><a href="${ctx}/csysbank/cdSysBank/">系统银行卡列表</a></li>
		<shiro:hasPermission name="csysbank:cdSysBank:edit">
			<li><a href="${ctx}/csysbank/cdSysBank/form">系统银行卡添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdSysBank" action="${ctx}/csysbank/cdSysBank/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>银行名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>银行名称</th>
				<th>银行图标</th>
				<th>银行编号</th>
				<th>是否启用</th>
				<shiro:hasPermission name="csysbank:cdSysBank:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdSysBank">
			<tr>
				<td><a href="${ctx}/csysbank/cdSysBank/form?id=${cdSysBank.id}">${cdSysBank.name}</a></td>
				<td><img src="${cdSysBank.bankImg}"  ></td>
				<td>${cdSysBank.bankNo}</td>
				<td>${cdSysBank.isUse=='0'?"未启用":"已启用"}</td>
				<shiro:hasPermission name="csysbank:cdSysBank:edit">
					<td>
	    				<a href="${ctx}/csysbank/cdSysBank/form?id=${cdSysBank.id}">修改</a>
						<a href="${ctx}/csysbank/cdSysBank/delete?id=${cdSysBank.id}" onclick="return confirmx('确认要删除该系统银行卡吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
