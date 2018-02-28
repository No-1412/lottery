<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台账户积分流水管理</title>
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
		<li class="active"><a href="${ctx}/csysaccountrecord/cdSysAccountRecord/">平台账户积分流水列表</a></li>
		<%--<shiro:hasPermission name="csysaccountrecord:cdSysAccountRecord:edit">
			<li><a href="${ctx}/csysaccountrecord/cdSysAccountRecord/form">平台账户积分流水添加</a></li>
		</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cdSysAccountRecord" action="${ctx}/csysaccountrecord/cdSysAccountRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>操作名称</th>
				<th>操作积分</th>
				<th>剩余可用积分</th>
				<th>剩余冻结积分</th>
				<th>操作类别</th>
				<th>创建时间</th>
				<%--<shiro:hasPermission name="csysaccountrecord:cdSysAccountRecord:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdSysAccountRecord">
			<tr>
				<td>${cdSysAccountRecord.name}</td>
				<td>${cdSysAccountRecord.operationScore}</td>
				<td>${cdSysAccountRecord.balanceScore}</td>
				<td>${cdSysAccountRecord.frzeeScore}</td>
				<td>${cdSysAccountRecord.operationType=='0'?"获得积分":"消耗积分"}</td>
				<td>${fn:substring(cdSysAccountRecord.createDate,0 ,19)}</td>
				<%--<shiro:hasPermission name="csysaccountrecord:cdSysAccountRecord:edit">
					<td>
	    				<a href="${ctx}/csysaccountrecord/cdSysAccountRecord/form?id=${cdSysAccountRecord.id}">修改</a>
						<a href="${ctx}/csysaccountrecord/cdSysAccountRecord/delete?id=${cdSysAccountRecord.id}" onclick="return confirmx('确认要删除该平台账户积分流水吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
