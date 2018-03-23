<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>返利记录管理</title>
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
		<li class="active"><a href="${ctx}/crecord/cdRecordRebate/">返利记录列表</a></li>
		<shiro:hasPermission name="crecord:cdRecordRebate:edit">
			<li><a href="${ctx}/crecord/cdRecordRebate/form">返利记录添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdRecordRebate" action="${ctx}/crecord/cdRecordRebate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>备注</th>
				<shiro:hasPermission name="crecord:cdRecordRebate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdRecordRebate">
			<tr>
				<td><a href="${ctx}/crecord/cdRecordRebate/form?id=${cdRecordRebate.id}">${cdRecordRebate.name}</a></td>
				<td>${cdRecordRebate.remarks}</td>
				<shiro:hasPermission name="crecord:cdRecordRebate:edit">
					<td>
	    				<a href="${ctx}/crecord/cdRecordRebate/form?id=${cdRecordRebate.id}">修改</a>
						<a href="${ctx}/crecord/cdRecordRebate/delete?id=${cdRecordRebate.id}" onclick="return confirmx('确认要删除该返利记录吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
