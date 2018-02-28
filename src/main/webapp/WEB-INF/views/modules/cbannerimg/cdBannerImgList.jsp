<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>轮播图片管理</title>
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
		<li class="active"><a href="${ctx}/cbannerimg/cdBannerImg/">轮播图片列表</a></li>
		<shiro:hasPermission name="cbannerimg:cdBannerImg:edit">
			<li><a href="${ctx}/cbannerimg/cdBannerImg/form">轮播图片添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdBannerImg" action="${ctx}/cbannerimg/cdBannerImg/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>图片</th>
				<th>图片类型</th>
				<th>排序（数字越大越靠前）</th>
				<th>是否启用</th>
				<shiro:hasPermission name="cbannerimg:cdBannerImg:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdBannerImg">
			<tr>
				<td><a href="${ctx}/cbannerimg/cdBannerImg/form?id=${cdBannerImg.id}">${cdBannerImg.title}</a></td>
				<td><img src="${cdBannerImg.img}" style="width: 90px;height:50px;" ></td>
				<td>${cdBannerImg.imgType=='1'?"Banner广告":""}</td>
				<td>${cdBannerImg.sort}</td>
				<td>${cdBannerImg.isUse=='1'?"已启用":"未启用"}</td>
				<shiro:hasPermission name="cbannerimg:cdBannerImg:edit">
					<td>
	    				<a href="${ctx}/cbannerimg/cdBannerImg/form?id=${cdBannerImg.id}">修改</a>
						<a href="${ctx}/cbannerimg/cdBannerImg/delete?id=${cdBannerImg.id}" onclick="return confirmx('确认要删除该轮播图片吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
