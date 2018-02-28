<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>论坛管理</title>
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
		<li class="active"><a href="${ctx}/cforum/cdForum/">论坛列表</a></li>
		<shiro:hasPermission name="cforum:cdForum:edit">
			<li><a href="${ctx}/cforum/cdForum/form">论坛添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdForum" action="${ctx}/cforum/cdForum/" method="post" class="breadcrumb form-search">
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
				<th>发布人</th>
				<th>点赞数量</th>
				<%--<th>内容</th>--%>
				<th>是否可用</th>
				<th>发布时间</th>
				<th>是否帖子</th>
				<shiro:hasPermission name="cforum:cdForum:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdForum">
			<tr>
				<td><a href="${ctx}/cforum/cdForum/form?id=${cdForum.id}">${cdForum.name}</a></td>
				<td>${cdForum.userName}</td>
				<td>${cdForum.dianzanCount}</td>
				<%--<td>${cdForum.content}</td>--%>
				<td>${cdForum.isUse == '0' ? "未启用":"已启用"}</td>
				<td>${fn:substring(cdForum.createDate,0 ,19 )}</td>
				<td>${cdForum.isPosts== '1' ? "帖子":"回复"}</td>
				<shiro:hasPermission name="cforum:cdForum:edit">
					<td>
	    				<a href="${ctx}/cforum/cdForum/form?id=${cdForum.id}">修改</a>
						<a href="${ctx}/cforum/cdForum/delete?id=${cdForum.id}" onclick="return confirmx('确认要删除该论坛吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
