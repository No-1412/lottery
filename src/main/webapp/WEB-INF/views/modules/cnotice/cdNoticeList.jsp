<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公告信息管理</title>
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
		<li class="active"><a href="${ctx}/cnotice/cdNotice/">公告信息列表</a></li>
		<shiro:hasPermission name="cnotice:cdNotice:edit">
			<li><a href="${ctx}/cnotice/cdNotice/form">公告信息添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdNotice" action="${ctx}/cnotice/cdNotice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>公告名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>公告名称</th>
				<th>创建时间</th>
				<th>备注</th>
				<shiro:hasPermission name="cnotice:cdNotice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdNotice">
			<tr>
				<td><a href="${ctx}/cnotice/cdNotice/form?id=${cdNotice.id}">${cdNotice.name}</a></td>
				<td>${fn:substring(cdNotice.createDate,0 ,19 )}</td>
				<td>${cdNotice.remarks}</td>
				<shiro:hasPermission name="cnotice:cdNotice:edit">
					<td>
	    				<a href="${ctx}/cnotice/cdNotice/form?id=${cdNotice.id}">修改</a>
						<a href="${ctx}/cnotice/cdNotice/delete?id=${cdNotice.id}" onclick="return confirmx('确认要删除该公告信息吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
