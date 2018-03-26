<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售后台充值记录管理</title>
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
		<li class="active"><a href="${ctx}/erp/erpRechargeLog/">销售后台充值记录列表</a></li>
		<shiro:hasPermission name="erp:erpRechargeLog:edit">
			<li><a href="${ctx}/erp/erpRechargeLog/form">充值</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="erpRechargeLog" action="${ctx}/erp/erpRechargeLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="userId.name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>操作人员</th>
				<th>充值用户</th>
				<th>充值金额（元）</th>
				<th>充值时间</th>
				<%--<shiro:hasPermission name="erp:erpRechargeLog:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="erpRechargeLog">
			<tr>
				<td>${erpRechargeLog.saleId.name}</td>
				<td>${erpRechargeLog.userId.name}</td>
				<td>${erpRechargeLog.money}</td>
				<td>${erpRechargeLog.createDate}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<%@ include file="/WEB-INF/views/modules/erp/loopWall.jsp"%>
</body>
</html>
