<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品建议管理</title>
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
		<li class="active"><a href="${ctx}/cproductproposal/cdProductProposal/">产品建议列表</a></li>
		<shiro:hasPermission name="cproductproposal:cdProductProposal:edit">
			<li><a href="${ctx}/cproductproposal/cdProductProposal/form">产品建议添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdProductProposal" action="${ctx}/cproductproposal/cdProductProposal/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>内容</th>
				<th>日期</th>
				<th>用户名称</th>
				<%--后台图片备用--%>
				<%--<th>图片</th>--%>
				<shiro:hasPermission name="cproductproposal:cdProductProposal:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdProductProposal">
			<tr>
				<td><a href="${ctx}/cproductproposal/cdProductProposal/form?id=${cdProductProposal.id}">${cdProductProposal.content}</a></td>
				<td>${fn:substring(cdProductProposal.createDate,0 ,19 )}</td>
				<td><a href="${ctx}/cproductproposal/cdProductProposal/form?id=${cdProductProposal.id}">${cdProductProposal.name}</a></td>
				<%--图片备用--%>
					<%--<td>--%>
					<%--<img src="${cdProductProposal.img} style="width: 90px;height:50px;"">--%>
				<%--</td>--%>
				<shiro:hasPermission name="cproductproposal:cdProductProposal:edit">
					<td>
	    				<a href="${ctx}/cproductproposal/cdProductProposal/form?id=${cdProductProposal.id}">修改</a>
						<a href="${ctx}/cproductproposal/cdProductProposal/delete?id=${cdProductProposal.id}" onclick="return confirmx('确认要删除该产品建议吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
