<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>大奖墙管理</title>
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
		<li class="active"><a href="${ctx}/cawardswall/cdAwardsWall/">大奖墙列表</a></li>
		<shiro:hasPermission name="cawardswall:cdAwardsWall:edit">
			<li><a href="${ctx}/cawardswall/cdAwardsWall/form">大奖墙添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdAwardsWall" action="${ctx}/cawardswall/cdAwardsWall/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>标题 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>奖金</th>
				<th>期次</th>
				<th>简介</th>
				<th>点赞数量</th>
				<th>创建时间</th>
				<shiro:hasPermission name="cawardswall:cdAwardsWall:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdAwardsWall">
			<tr>
				<td><a href="${ctx}/cawardswall/cdAwardsWall/form?id=${cdAwardsWall.id}">${cdAwardsWall.name}</a></td>
				<td>${cdAwardsWall.prize}</td>
				<td>${cdAwardsWall.issue}</td>
				<td>${cdAwardsWall.intro}</td>
				<td>${cdAwardsWall.dianzanCount}</td>
				<td>${fn:substring(cdAwardsWall.createDate,0 ,19 )}</td>
				<shiro:hasPermission name="cawardswall:cdAwardsWall:edit">
					<td>
	    				<a href="${ctx}/cawardswall/cdAwardsWall/form?id=${cdAwardsWall.id}">修改</a>
						<a href="${ctx}/cawardswall/cdAwardsWall/delete?id=${cdAwardsWall.id}" onclick="return confirmx('确认要删除该大奖墙吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
