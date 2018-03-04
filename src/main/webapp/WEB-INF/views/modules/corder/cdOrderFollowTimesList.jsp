<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>跟单倍数管理</title>
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
		<li class="active"><a href="${ctx}/corder/cdOrderFollowTimes/">>跟单倍数列表</a></li>
		<%--<shiro:hasPermission name="corder:cdOrderFollowTimes:edit">
			<li><a href="${ctx}/corder/cdOrderFollowTimes/form">>跟单倍数添加</a></li>
		</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cdOrderFollowTimes" action="${ctx}/corder/cdOrderFollowTimes/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>--%>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>倍数</th>
				<shiro:hasPermission name="corder:cdOrderFollowTimes:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdOrderFollowTimes">
			<tr>
				<td><a href="${ctx}/corder/cdOrderFollowTimes/form?id=${cdOrderFollowTimes.id}">${cdOrderFollowTimes.times}倍</a></td>

				<shiro:hasPermission name="corder:cdOrderFollowTimes:edit">
					<td>
	    				<a href="${ctx}/corder/cdOrderFollowTimes/form?id=${cdOrderFollowTimes.id}">修改</a>
						<%--<a href="${ctx}/corder/cdOrderFollowTimes/delete?id=${cdOrderFollowTimes.id}" onclick="return confirmx('确认要删除该竞彩篮球订单吗？', this.href)">删除</a>--%>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
