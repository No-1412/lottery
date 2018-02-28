<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中奖攻略管理</title>
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
		<li class="active"><a href="${ctx}/cwinmethod/cdWinMethod/">中奖攻略列表</a></li>
		<shiro:hasPermission name="cwinmethod:cdWinMethod:edit">
			<li><a href="${ctx}/cwinmethod/cdWinMethod/form">中奖攻略添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdWinMethod" action="${ctx}/cwinmethod/cdWinMethod/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>创建时间</th>
				<shiro:hasPermission name="cwinmethod:cdWinMethod:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdWinMethod">
			<tr>
				<td><a href="${ctx}/cwinmethod/cdWinMethod/form?id=${cdWinMethod.id}">${cdWinMethod.name}</a></td>
				<td>${fn:substring(cdWinMethod.createDate,0 ,19 )}</td>
				<shiro:hasPermission name="cwinmethod:cdWinMethod:edit">
					<td>
	    				<a href="${ctx}/cwinmethod/cdWinMethod/form?id=${cdWinMethod.id}">修改</a>
						<a href="${ctx}/cwinmethod/cdWinMethod/delete?id=${cdWinMethod.id}" onclick="return confirmx('确认要删除该中奖攻略吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
