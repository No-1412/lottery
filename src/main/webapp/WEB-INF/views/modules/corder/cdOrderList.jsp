<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>彩票订单表管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/corder/cdOrder/">彩票订单表列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="cdOrder" action="${ctx}/corder/cdOrder/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>用户名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<tags:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>用户名称</th>
        <th>期号</th>
        <th>购买时间</th>
        <th>彩票号码</th>
        <th>彩票种类</th>
        <th>购买数量</th>
        <th>彩票单价</th>
        <th>彩票总价</th>
        <th>中奖状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="cdOrder">
        <tr>
            <td>${cdOrder.userName}</td>
            <td>${cdOrder.issue}</td>
            <td>${fn:substring(cdOrder.createDate,0 ,19)}</td>
            <td>${cdOrder.number}</td>
            <td>${cdOrder.type}</td>
            <td>${cdOrder.count}</td>
            <td>${cdOrder.unitPrice}</td>
            <td>${cdOrder.totalPrice}</td>
            <td>
                <c:choose>
                    <c:when test="${cdOrder.win== '0'}">
                        待开奖
                    </c:when>
                    <c:when test="${cdOrder.win== '1'}">
                        中奖
                    </c:when>
                    <c:otherwise>
                        未中奖
                    </c:otherwise>
                </c:choose></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
