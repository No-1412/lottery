<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户邀请管理</title>
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
		<li class="active"><a href="${ctx}/usm/usmInvite/">用户邀请列表</a></li>
		<shiro:hasPermission name="usm:usmInvite:edit">
			<li><a href="${ctx}/usm/usmInvite/form">用户邀请添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="usmInvite" action="${ctx}/usm/usmInvite/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>--%>
		<%--&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>--%>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>邀请信息</th>
				<th>邀请时间</th>
				<shiro:hasPermission name="usm:usmInvite:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="usmInvite">
			<tr>
				<td><a href="${ctx}/usm/usmInvite/form?id=${usmInvite.id}">${usmInvite.inviter.name} 邀请 ${usmInvite.invitee.name}</a></td>
				<td>${usmInvite.createDate}</td>
				<shiro:hasPermission name="usm:usmInvite:edit">
					<td>
	    				<a href="${ctx}/usm/usmInvite/form?id=${usmInvite.id}">修改</a>
						<a href="${ctx}/usm/usmInvite/delete?id=${usmInvite.id}" onclick="return confirmx('确认要删除该用户邀请吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
