<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提现记录管理</title>
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
		<li class="active"><a href="${ctx}/cwithdrawal/cdWithdrawal/">提现记录列表</a></li>
		<%--<shiro:hasPermission name="cwithdrawal:cdWithdrawal:edit">
			<li><a href="${ctx}/cwithdrawal/cdWithdrawal/form">提现记录添加</a></li>
		</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cdWithdrawal" action="${ctx}/cwithdrawal/cdWithdrawal/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>提现金额</th>
				<th>剩余金额</th>
				<th>提现状态</th>
				<th>银行名称</th>
				<th>银行卡号</th>
				<th>创建时间</th>
				<%--<shiro:hasPermission name="cwithdrawal:cdWithdrawal:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdWithdrawal">
			<tr>
				<td>${cdWithdrawal.name}</td>
				<td>${cdWithdrawal.txMoney}</td>
				<th>${cdWithdrawal.syMoney}</th>
				<th>
					<c:if test="${cdWithdrawal.txStatus==1}">未审核</c:if>
					<c:if test="${cdWithdrawal.txStatus==2}">已提现</c:if>
					<c:if test="${cdWithdrawal.txStatus==3}">已审核</c:if>
					<c:if test="${cdWithdrawal.txStatus==4}">转账中</c:if>
					<c:if test="${cdWithdrawal.txStatus==5}">提现金额返还</c:if>
					<c:if test="${cdWithdrawal.txStatus==6}">提现撤销</c:if>
					<c:if test="${cdWithdrawal.txStatus==7}">退票</c:if>
				</th>
				<th>${cdWithdrawal.bankName}</th>
				<th>${cdWithdrawal.txCard}</th>
				<td>${fn:substring(cdWithdrawal.createDate,0 ,19)}</td>
				<%--<shiro:hasPermission name="cwithdrawal:cdWithdrawal:edit">
					<td>
	    				<a href="${ctx}/cwithdrawal/cdWithdrawal/form?id=${cdWithdrawal.id}">修改</a>
						<a href="${ctx}/cwithdrawal/cdWithdrawal/delete?id=${cdWithdrawal.id}" onclick="return confirmx('确认要删除该提现记录吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
