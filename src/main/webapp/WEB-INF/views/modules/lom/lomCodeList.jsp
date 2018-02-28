<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>验证码记录管理</title>
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
		<li class="active"><a href="${ctx}/lom/lomCode/">验证码记录列表</a></li>
		<shiro:hasPermission name="lom:lomCode:edit">
			<li><a href="${ctx}/lom/lomCode/form">验证码记录添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="lomCode" action="${ctx}/lom/lomCode/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>手机号 ：</label><form:input path="phone" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>验证码 ：</label><form:input path="code" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>手机号-验证码</th>
				<th>类别</th>
				<th>发送时间</th>
				<th>失效时间</th>
				<shiro:hasPermission name="lom:lomCode:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="lomCode">
			<tr>
				<td><a href="${ctx}/lom/lomCode/form?id=${lomCode.id}">手机号：${lomCode.phone} 验证码：${lomCode.code}</a></td>
				<td>${fns:getDictLabel(lomCode.kind, "lom_code_type", "未知")}</td>
				<td>${lomCode.createDate}</td>
				<td>${lomCode.invalidDate}</td>
				<shiro:hasPermission name="lom:lomCode:edit">
					<td>
	    				<a href="${ctx}/lom/lomCode/form?id=${lomCode.id}">修改</a>
						<a href="${ctx}/lom/lomCode/delete?id=${lomCode.id}" onclick="return confirmx('确认要删除该验证码记录吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
