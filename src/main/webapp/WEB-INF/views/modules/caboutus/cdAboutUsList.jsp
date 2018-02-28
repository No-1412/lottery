<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关于我们管理</title>
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
		<li class="active"><a href="${ctx}/caboutus/cdAboutUs/">关于我们列表</a></li>
		<shiro:hasPermission name="caboutus:cdAboutUs:edit">
			<li><a href="${ctx}/caboutus/cdAboutUs/form">关于我们添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdAboutUs" action="${ctx}/caboutus/cdAboutUs/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>app名称</th>
				<th>logo图片</th>
				<th>客服电话</th>
				<th>官网链接</th>
				<th>创建时间</th>
				<shiro:hasPermission name="caboutus:cdAboutUs:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdAboutUs">
			<tr>
				<td><a href="${ctx}/caboutus/cdAboutUs/form?id=${cdAboutUs.id}">${cdAboutUs.name}</a></td>
				<td><img src="${cdAboutUs.logoImg}" style="width: 90px;height:50px;" ></td>
				<td>${cdAboutUs.kefuTel}</td>
				<td>${cdAboutUs.guanwangHref}</td>
				<td>${fn:substring(cdAboutUs.createDate,0 ,19 )}</td>
				<shiro:hasPermission name="caboutus:cdAboutUs:edit">
					<td>
	    				<a href="${ctx}/caboutus/cdAboutUs/form?id=${cdAboutUs.id}">修改</a>
						<a href="${ctx}/caboutus/cdAboutUs/delete?id=${cdAboutUs.id}" onclick="return confirmx('确认要删除该关于我们吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
