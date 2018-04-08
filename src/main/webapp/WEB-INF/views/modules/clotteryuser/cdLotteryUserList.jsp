<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户注册管理</title>
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
		<li class="active"><a href="${ctx}/clotteryuser/cdLotteryUser/">用户注册列表</a></li>
		<%--<shiro:hasPermission name="clotteryuser:cdLotteryUser:edit">
			<li><a href="${ctx}/clotteryuser/cdLotteryUser/form">用户注册添加</a></li>
		</shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cdLotteryUser" action="${ctx}/clotteryuser/cdLotteryUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>昵称</th>
				<th>手机号</th>
				<th>会员类型</th>
				<th>会员级别</th>
				<th>账号余额</th>
				<th>冻结余额</th>
				<th>积分</th>
				<th>注册时间</th>
				<th>是否锁定</th>
				<shiro:hasPermission name="clotteryuser:cdLotteryUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cdLotteryUser">
			<tr>
			<%--	<td><a href="${ctx}/clotteryuser/cdLotteryUser/form?id=${cdLotteryUser.id}">${cdLotteryUser.name}</a></td>
				<td>${cdLotteryUser.remarks}</td>--%>
				<input type="hidden" name="xm" value="${cdLotteryUser.id}">
				<td>${cdLotteryUser.name}</td>
				<td>${cdLotteryUser.mobile}</td>
				<td>${cdLotteryUser.memberType=='0'?"普通":"永久"}</td>
				<td>${cdLotteryUser.memberLevel}级会员</td>
				<td>${cdLotteryUser.balance}</td>
				<td>${cdLotteryUser.freeze}</td>
				<td>${cdLotteryUser.score}</td>
				<td>${fn:substring(cdLotteryUser.createDate,0 ,19)}</td>
				<td>${cdLotteryUser.isBlock=='0'?"未锁定":"已锁定"}</td>
				<shiro:hasPermission name="clotteryuser:cdLotteryUser:edit">
					<td>
	    				<a href="${ctx}/clotteryuser/cdLotteryUser/form?id=${cdLotteryUser.id}">修改</a>
						<%--<a href="${ctx}/clotteryuser/cdLotteryUser/delete?id=${cdLotteryUser.id}" onclick="return confirmx('确认要删除该用户注册吗？', this.href)">删除</a>--%>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
