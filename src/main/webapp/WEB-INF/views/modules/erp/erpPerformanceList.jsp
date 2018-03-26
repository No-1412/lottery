<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>当日业绩明细</title>
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
		<li class="active"><a href="${ctx}/erp/erpOrder/performanceList">当日业绩明细</a></li>
		<%--<shiro:hasPermission name="erp:erpOrder:edit">--%>
			<%--<li><a href="${ctx}/erp/erpOrder/form">业绩添加</a></li>--%>
		<%--</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="erpOrder" action="${ctx}/erp/erpOrder/performanceList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>--%>
		<%--&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>--%>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>自购量（元）</th>
				<th>复制量（元）</th>
				<th>今日收入（元）</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="performance">
			<tr>
				<td>${performance.zigou}</td>
				<td>${performance.fuzhi}</td>
				<td>${performance.shouru}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

	<%@ include file="/WEB-INF/views/modules/erp/loopWall.jsp"%>
</body>
</html>
