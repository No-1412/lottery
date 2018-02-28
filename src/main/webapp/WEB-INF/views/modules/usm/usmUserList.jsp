<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户表管理</title>
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
		<li class="active"><a href="${ctx}/usm/usmUser/">用户表列表</a></li>
		<%--<shiro:hasPermission name="usm:usmUser:edit">--%>
			<%--<li><a href="${ctx}/usm/usmUser/form">用户表查看</a></li>--%>
		<%--</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="usmUser" action="${ctx}/usm/usmUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>姓名 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>手机号</th>
				<th>积分</th>
				<th>余额</th>
				<th>注册方式</th>
				<th>冻结状态</th>
				<th>注册时间</th>
				<shiro:hasPermission name="usm:usmUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="usmUser">
			<tr>
				<td><a href="${ctx}/usm/usmUser/form?id=${usmUser.id}">${usmUser.name}</a></td>
				<td>${usmUser.telephone}</td>
				<td>${usmUser.point}</td>
				<td>${usmUser.money/100}</td>
				<td>${fns:getDictLabel(usmUser.regType,"bm_user_reg" ,"未知" )}</td>
				<td>${fns:getDictLabel(usmUser.freeze,"no_yes" ,"否" )}</td>
				<td>${usmUser.createDate}</td>
				<shiro:hasPermission name="usm:usmUser:edit">
					<td>
	    				<a href="${ctx}/usm/usmUser/form?id=${usmUser.id}">查看</a>
						<c:if test="${usmUser.freeze eq '0'}"><a href="${ctx}/usm/usmUser/freeze?id=${usmUser.id}" onclick="return confirmx('确认要冻结该用户吗？', this.href)">冻结</a></c:if>
						<c:if test="${usmUser.freeze eq '1'}"><a href="${ctx}/usm/usmUser/freeze?id=${usmUser.id}" onclick="return confirmx('确认要解冻该用户吗？', this.href)">解除</a></c:if>
						<a href="${ctx}/usm/usmUser/delete?id=${usmUser.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
