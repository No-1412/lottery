<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>帮助中心管理</title>
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
		<li class="active"><a href="${ctx}/chelpcenter/cdHelpCenter/">帮助中心列表</a></li>
		<shiro:hasPermission name="chelpcenter:cdHelpCenter:edit">
			<li><a href="${ctx}/chelpcenter/cdHelpCenter/form">帮助中心添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdHelpCenter" action="${ctx}/chelpcenter/cdHelpCenter/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>问题名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>问题名称</th>
				<th>问题答案</th>
				<th>创建时间</th>
				<th>是否启用</th>
				<shiro:hasPermission name="chelpcenter:cdHelpCenter:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdHelpCenter">
			<tr>
				<td><a href="${ctx}/chelpcenter/cdHelpCenter/form?id=${cdHelpCenter.id}">${cdHelpCenter.questionContent}</a></td>
				<td>${cdHelpCenter.questionAnswer}</td>
				<td>${fn:substring(cdHelpCenter.createDate,0 ,19 )}</td>
				<td>${cdHelpCenter.isUse=='0'?"未启用":"已启用"}</td>
				<shiro:hasPermission name="chelpcenter:cdHelpCenter:edit">
					<td>
	    				<a href="${ctx}/chelpcenter/cdHelpCenter/form?id=${cdHelpCenter.id}">修改</a>
						<a href="${ctx}/chelpcenter/cdHelpCenter/delete?id=${cdHelpCenter.id}" onclick="return confirmx('确认要删除该帮助中心吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
