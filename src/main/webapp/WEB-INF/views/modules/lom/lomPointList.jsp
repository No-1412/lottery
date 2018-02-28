<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>积分记录管理</title>
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
		<li class="active"><a href="${ctx}/lom/lomPoint/">积分记录列表</a></li>
		<shiro:hasPermission name="lom:lomPoint:edit">
			<li><a href="${ctx}/lom/lomPoint/form">积分记录添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="lomPoint" action="${ctx}/lom/lomPoint/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户</th>
				<th>获得积分</th>
				<th>积分来源</th>
				<th>积分说明</th>
				<th>获取时间</th>
				<shiro:hasPermission name="lom:lomPoint:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="lomPoint">
			<tr>
				<td><a href="${ctx}/lom/lomPoint/form?id=${lomPoint.id}">${lomPoint.userId.name}</a></td>
				<td>${lomPoint.points}</td>
				<td>${fns:getDictLabel(lomCollect.kind, "	lom_point_type", "未知")}</td>
				<td>${fn:replace(lomPoint.name,"用户名称",lomPoint.invitee.name)}</td>
				<td>${lomPoint.createDate}</td>
				<shiro:hasPermission name="lom:lomPoint:edit">
					<td>
	    			<a href="${ctx}/lom/lomPoint/form?id=${lomPoint.id}">修改</a>
						<a href="${ctx}/lom/lomPoint/delete?id=${lomPoint.id}" onclick="return confirmx('确认要删除该积分记录吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
