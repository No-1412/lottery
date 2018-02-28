<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>竞彩足球管理</title>
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
		<li class="active"><a href="${ctx}/cfootballmixed/cdFootballMixed/">竞彩足球列表</a></li>
		<%--<shiro:hasPermission name="cfootballmixed:cdFootballMixed:edit">
			<li><a href="${ctx}/cfootballmixed/cdFootballMixed/form">竞彩足球添加</a></li>
		</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cdFootballMixed" action="${ctx}/cfootballmixed/cdFootballMixed/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>队伍名称 ：</label><form:input path="winningName" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>期号</th>
				<th>场次</th>
				<th>赛事名称</th>
				<%--<th>比赛时间</th>--%>
				<th>主队VS客队</th>
				<th>让球</th>
				<th>截止时间</th>
				<shiro:hasPermission name="cfootballmixed:cdFootballMixed:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdFootballMixed">
			<tr>
				<td><a href="${ctx}/cfootballmixed/cdFootballMixed/form?id=${cdFootballMixed.id}">${cdFootballMixed.remarks}</a></td>
				<td>${cdFootballMixed.matchId}</td>
				<td>${cdFootballMixed.eventName}</td>
				<%--<td>${cdFootballMixed.matchDate}</td>--%>
				<td>${cdFootballMixed.winningName}VS${cdFootballMixed.defeatedName}</td>
				<td>${cdFootballMixed.close}</td>
				<td>${cdFootballMixed.timeEndsale}</td>
				<shiro:hasPermission name="cfootballmixed:cdFootballMixed:edit">
					<td>
	    				<a href="${ctx}/cfootballmixed/cdFootballMixed/form?id=${cdFootballMixed.id}">修改</a>
						<a href="${ctx}/cfootballmixed/cdFootballMixed/delete?id=${cdFootballMixed.id}" onclick="return confirmx('确认要删除该竞彩足球吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
