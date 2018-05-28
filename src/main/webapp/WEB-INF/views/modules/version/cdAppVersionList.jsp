<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>版本信息管理</title>
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
		<li class="active"><a href="${ctx}/version/cdAppVersion/">版本信息列表</a></li>
		<shiro:hasPermission name="version:cdAppVersion:edit">
			<li><a href="${ctx}/version/cdAppVersion/form">版本信息添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cdAppVersion" action="${ctx}/version/cdAppVersion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

		<label>系统渠道 ：</label>
		<form:select path="versionChannel" class="input-medium">
		<form:option value="">请选择</form:option>
		<form:options items="${fns:getDictList('channel_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</form:select>
		<label>版本标识 ：</label><form:input path="versionCode" htmlEscape="false" maxlength="50" class="input-small"/>
		<label>名称 ：</label><form:input path="versionName" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>系统渠道</th>
				<th>版本标识</th>
				<th>版本名称</th>
				<th>是否升级</th>
				<th>是否强制升级</th>
				<th>下载地址</th>
				<th>升级描述</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="version:cdAppVersion:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdAppVersion">
			<tr>
				<td>${fns:getDictLabel(cdAppVersion.versionChannel,'channel_type','未知')}</td>
				<td>${cdAppVersion.versionCode}</td>
				<td><a href="${ctx}/version/cdAppVersion/form?id=${cdAppVersion.id}">${cdAppVersion.versionName}</a></td>
				<td>${fns:getDictLabel(cdAppVersion.versionIsUpgrade,'upgrade_flag','未知')}</td>
				<td>${fns:getDictLabel(cdAppVersion.versionIsEnforcement,'enforcement_flag','未知')}</td>
				<td>${cdAppVersion.versionDownloadUrl}</td>
				<td>${cdAppVersion.versionDescription}</td>
				<td>${cdAppVersion.createDate}</td>
				<td>${cdAppVersion.updateDate}</td>
				<shiro:hasPermission name="version:cdAppVersion:edit">
					<td>
	    				<a href="${ctx}/version/cdAppVersion/form?id=${cdAppVersion.id}">修改</a>
						<a href="${ctx}/version/cdAppVersion/delete?id=${cdAppVersion.id}" onclick="return confirmx('确认要删除该版本信息吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
