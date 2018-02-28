<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>大乐透开奖结果管理</title>
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
		<li class="active"><a href="${ctx}/clottoreward/cdLottoReward/">大乐透开奖结果列表</a></li>
		<shiro:hasPermission name="clottoreward:cdLottoReward:edit">
			<li><a href="${ctx}/clottoreward/cdLottoReward/form">大乐透开奖结果添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdLottoReward" action="${ctx}/clottoreward/cdLottoReward/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="clottoreward:cdLottoReward:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdLottoReward">
			<tr>
				<td><a href="${ctx}/clottoreward/cdLottoReward/form?id=${cdLottoReward.id}">${cdLottoReward.name}</a></td>
				<td>${cdLottoReward.remarks}</td>
				<shiro:hasPermission name="clottoreward:cdLottoReward:edit">
					<td>
	    				<a href="${ctx}/clottoreward/cdLottoReward/form?id=${cdLottoReward.id}">修改</a>
						<a href="${ctx}/clottoreward/cdLottoReward/delete?id=${cdLottoReward.id}" onclick="return confirmx('确认要删除该大乐透开奖结果吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
