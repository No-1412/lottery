<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易记录管理</title>
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
		<li class="active"><a href="${ctx}/ctradingrecord/cdTradingRecord/">交易记录列表</a></li>
		<%--<shiro:hasPermission name="ctradingrecord:cdTradingRecord:edit">
			<li><a href="${ctx}/ctradingrecord/cdTradingRecord/form">交易记录添加</a></li>
		</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cdTradingRecord" action="${ctx}/ctradingrecord/cdTradingRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>用户名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名称</th>
				<th>操作金额</th>
				<th>可用金额</th>
				<th>交易类型</th>
				<th>创建时间</th>
				<%--<shiro:hasPermission name="ctradingrecord:cdTradingRecord:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdTradingRecord">
			<tr>
				<td>${cdTradingRecord.name}</td>
				<td>${cdTradingRecord.handleSum}</td>
				<td>${cdTradingRecord.usableSum}</td>
				<td>${cdTradingRecord.fundMode}</td>
				<td>${fn:substring(cdTradingRecord.createDate,0 ,19 )}</td>
				<%--<shiro:hasPermission name="ctradingrecord:cdTradingRecord:edit">
					<td>
	    				<a href="${ctx}/ctradingrecord/cdTradingRecord/form?id=${cdTradingRecord.id}">修改</a>
						<a href="${ctx}/ctradingrecord/cdTradingRecord/delete?id=${cdTradingRecord.id}" onclick="return confirmx('确认要删除该交易记录吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
