<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>基本信息管理</title>
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
		<li class="active"><a href="${ctx}/bm/bmBase/">基本信息列表</a></li>
		<shiro:hasPermission name="bm:bmBase:edit">
			<li><a href="${ctx}/bm/bmBase/form">基本信息添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bmBase" action="${ctx}/bm/bmBase/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>类型：</label>
		<form:select path="kind" id="type" cssClass="input-large" >
			<form:options  items="${fns:getDictListFind('bm_base_type')}" itemLabel="label" itemValue="value"  htmlEscape="false"/>
		</form:select>
		<label>标题 ：</label>
		<form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>分类</th>
				<%--<th>图文详情</th>--%>
				<shiro:hasPermission name="bm:bmBase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bmBase">
			<tr>
				<td><a href="${ctx}/bm/bmBase/form?id=${bmBase.id}">${bmBase.name}</a></td>
				<td>${fns:getDictLabel(bmBase.kind,"bm_base_type","未知")}</td>
				<%--<td>${fn:substring(bmBase.content,0,fn:indexOf(bmBase.content,"&" ))}</td>--%>
				<shiro:hasPermission name="bm:bmBase:edit">
					<td>
	    				<a href="${ctx}/bm/bmBase/form?id=${bmBase.id}">修改</a>
						<a href="${ctx}/bm/bmBase/delete?id=${bmBase.id}" onclick="return confirmx('确认要删除该基本信息吗？', this.href)">删除</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
