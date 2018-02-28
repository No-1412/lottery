<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>胜负彩管理</title>
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
		<li class="active"><a href="${ctx}/csuccessfail/cdSuccessFail/">胜负彩列表</a></li>
		<%--<shiro:hasPermission name="csuccessfail:cdSuccessFail:edit">
			<li><a href="${ctx}/csuccessfail/cdSuccessFail/form">胜负彩添加</a></li>
		</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cdSuccessFail" action="${ctx}/csuccessfail/cdSuccessFail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>队伍名称 ：</label><form:input path="eventName" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>期号</th>
				<th>场次</th>
				<th>赛事名称</th>
				<th>比赛时间</th>
				<th>主队VS客队</th>
				<th>主胜赔率</th>
				<th>平赔率</th>
				<th>负赔率</th>
				<th>截止时间</th>

				<%--<shiro:hasPermission name="csuccessfail:cdSuccessFail:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdSuccessFail">
			<tr>
				<td><a href="${ctx}/csuccessfail/cdSuccessFail/form?id=${cdSuccessFail.id}">${cdSuccessFail.weekday}</a></td>
				<td>${cdSuccessFail.matchId}</td>
				<td>${cdSuccessFail.eventName}</td>
				<td>${cdSuccessFail.matchDate}</td>
				<td>${cdSuccessFail.homeTeam}VS${cdSuccessFail.awayTeam}</td>
				<td>${cdSuccessFail.winningOdds}</td>
				<td>${cdSuccessFail.flatOdds}</td>
				<td>${cdSuccessFail.defeatedOdds}</td>
				<td>${cdSuccessFail.timeEndSale}</td>

				<%--<shiro:hasPermission name="csuccessfail:cdSuccessFail:edit">
					<td>
	    				<a href="${ctx}/csuccessfail/cdSuccessFail/form?id=${cdSuccessFail.id}">修改</a>
						<a href="${ctx}/csuccessfail/cdSuccessFail/delete?id=${cdSuccessFail.id}" onclick="return confirmx('确认要删除该胜负彩吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
