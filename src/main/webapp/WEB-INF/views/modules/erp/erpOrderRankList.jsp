<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>排行管理</title>
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
        //首页公告

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/erp/erpOrderRank/findRank">销售业绩排行列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="erpOrderRank" action="${ctx}/erp/erpOrderRank/findRank" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>开始日期：</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
								   value="${beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'endDate\')}'});"/>
		<label>结束日期：</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
								   value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'beginDate\')}'});"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名次</th>
				<th>销售人</th>
				<th>自购业绩</th>
				<th>复制业绩</th>
				<th>总业绩</th>
				<th>被跟单量</th>
				<th>入职时间</th>
				<th>角色名称</th>
				<%--<shiro:hasPermission name="erp:erpOrderRank:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="mapRank" varStatus="index">
			<tr>
				<%--<c:choose>
					<c:when test="${page.pageNo gt 1}">
						<td>${page.pageSize+index.index+1}</td>
					</c:when>
					<c:otherwise>
						<td>${index.index+1}</td>
					</c:otherwise>
				</c:choose>--%>
                <td>${mapRank.rowNo}</td>
				<td>${mapRank.salename}</td>
				<td>${mapRank.moneywithoutgendan}</td>
				<td>${mapRank.moneygendan}</td>
				<td>${mapRank.moneytotal}</td>
				<td>${mapRank.num}</td>
				<td>${mapRank.createdate}</td>
				<td>${mapRank.role_name}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

	<%@ include file="/WEB-INF/views/modules/erp/loopWall.jsp"%>
</body>
</html>
