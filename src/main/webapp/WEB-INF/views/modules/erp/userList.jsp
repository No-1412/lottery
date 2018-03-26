<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
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
		<li class="active"><a href="${ctx}/erp/user/">用户列表</a></li>
		<%--<shiro:hasPermission name="erp:user:edit">--%>
			<%--<li><a href="${ctx}/erp/user/form">用户添加</a></li>--%>
		<%--</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="erpUser" action="${ctx}/erp/user/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>客户姓名</th>
				<th>手机号</th>
				<th>余额</th>
				<th>注册时间</th>
				<th>所属销售</th>
				<th>备注</th>
				<shiro:hasPermission name="erp:user:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td><a href="${ctx}/erp/user/form?id=${user.id}">${user.name}</a></td>
				<td>${user.mobile}</td>
				<td>
				<c:if test="${user.balance == null || user.balance eq ''}">
					0.00
				</c:if>
				<c:if test="${user.balance != null || user.balance != ''}">
					${user.balance}
				</c:if>
				</td>
				<td>${user.createDate}</td>
				<td>${user.saleId.name}</td>
				<td>${user.remarks}</td>
				<shiro:hasPermission name="erp:user:edit">
					<td>
	    				<a href="${ctx}/erp/user/form?id=${user.id}">修改备注</a>
						<%--<a href="${ctx}/erp/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>--%>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

	<%@ include file="/WEB-INF/views/modules/erp/loopWall.jsp"%>
</body>
</html>
