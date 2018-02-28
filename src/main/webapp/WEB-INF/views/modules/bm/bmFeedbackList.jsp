<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>意见反馈管理</title>
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
		<li class="active"><a href="${ctx}/bm/bmFeedback/">意见反馈列表</a></li>
		<%--<shiro:hasPermission name="bm:bmFeedback:edit">--%>
			<%--<li><a href="${ctx}/bm/bmFeedback/form">意见反馈查看</a></li>--%>
		<%--</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="bmFeedback" action="${ctx}/bm/bmFeedback/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>所属用户 ：</label><form:input path="delFlag" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>反馈内容 ：</label><form:input path="content" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>所属用户</th>
				<th>详情</th>
				<shiro:hasPermission name="bm:bmFeedback:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bmFeedback">
			<tr>
				<td><a href="${ctx}/bm/bmFeedback/form?id=${bmFeedback.id}">${bmFeedback.userId.name}</a></td>
				<td>${fn:substring(bmFeedback.content,0,50)}</td>
				<shiro:hasPermission name="bm:bmFeedback:edit">
					<td>
	    				<a href="${ctx}/bm/bmFeedback/form?id=${bmFeedback.id}">查看</a>
						<a href="${ctx}/bm/bmFeedback/delete?id=${bmFeedback.id}" onclick="return confirmx('确认要删除该意见反馈吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
