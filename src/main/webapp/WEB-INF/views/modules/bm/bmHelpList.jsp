<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>新手教程管理</title>
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
		<li class="active"><a href="${ctx}/bm/bmHelp/">新手教程列表</a></li>
		<shiro:hasPermission name="bm:bmHelp:edit">
			<li><a href="${ctx}/bm/bmHelp/form">新手教程添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bmHelp" action="${ctx}/bm/bmHelp/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-large"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<%--<th>图文详情</th>--%>
				<shiro:hasPermission name="bm:bmHelp:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bmHelp">
			<tr>
				<td><a href="${ctx}/bm/bmHelp/form?id=${bmHelp.id}">${bmHelp.name}</a></td>
				<%--<td>${fn:substring(bmHelp.content,0,fn:indexOf(bmHelp.content,"&" ))}</td>--%>
				<shiro:hasPermission name="bm:bmHelp:edit">
					<td>
	    				<a href="${ctx}/bm/bmHelp/form?id=${bmHelp.id}">修改</a>
						<a href="${ctx}/bm/bmHelp/delete?id=${bmHelp.id}" onclick="return confirmx('确认要删除该新手教程吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
