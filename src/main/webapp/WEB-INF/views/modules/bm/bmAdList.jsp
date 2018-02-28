<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//页面初始化操作
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
		<li class="active"><a href="${ctx}/bm/bmAd/">广告列表</a></li>
		<shiro:hasPermission name="bm:bmAd:edit">
			<li><a href="${ctx}/bm/bmAd/form">广告添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bmAd" action="${ctx}/bm/bmAd/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>类型：</label>
		<form:select path="kind" items="${fns:getDictListFind('bm_ad_type')}" itemLabel="label" itemValue="value" />
		<label>标题：</label>
		<form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>分类</th>
				<th>备注</th>
				<shiro:hasPermission name="bm:bmAd:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bmAd">
			<tr>
				<td><a href="${ctx}/bm/bmAd/form?id=${bmAd.id}">${bmAd.name}</a></td>
				<td>${fns:getDictLabel(bmAd.kind,"bm_ad_type","未知")}</td>
				<td>${bmAd.remarks}</td>
				<shiro:hasPermission name="bm:bmAd:edit">
					<td>
	    			<a href="${ctx}/bm/bmAd/form?id=${bmAd.id}">修改</a>
						<a href="${ctx}/bm/bmAd/delete?id=${bmAd.id}" onclick="return confirmx('确认要删除该广告吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
