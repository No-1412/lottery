<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>竞彩足球订单管理</title>
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
    <li class="active"><a href="${ctx}/cfootballorder/cdFootballSingleOrder/">竞彩足球订单列表</a></li>
    <%--<shiro:hasPermission name="cfootballorder:cdFootballSingleOrder:edit">
        <li><a href="${ctx}/cfootballorder/cdFootballSingleOrder/form">竞彩足球订单添加</a></li>
    </shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="cdFootballSingleOrder" action="${ctx}/cfootballorder/cdFootballSingleOrder/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>订单号 ：</label><form:input path="orderNum" htmlEscape="false" maxlength="50" class="input-small"/>
    <label class="control-label">投注方式:</label>
    <form:select id="buyWays" path="buyWays">
        <form:option value="" label="全部"/>
        <form:option value="1" label="混投"/>
        <form:option value="2" label="胜平负"/>
        <form:option value="4" label="猜比分"/>
        <form:option value="5" label="总进球"/>
        <form:option value="6" label="半全场"/>
        <form:option value="3" label="让球胜负平"/>

    </form:select>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<tags:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>订单号</th>
        <th>投注方式</th>
        <th>注数</th>
        <th>金额</th>
        <th>订单类型</th>
        <th>下单时间</th>
        <th>订单状态</th>
        <shiro:hasPermission name="cfootballorder:cdFootballSingleOrder:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="cdFootballSingleOrder">
        <tr>
            <td>
                <a href="${ctx}/cfootballorder/cdFootballSingleOrder/form?id=${cdFootballSingleOrder.id}">${cdFootballSingleOrder.orderNum}</a>
            </td>
            <td><%--${cdFootballSingleOrder.buyWays}--%>
                <c:choose>
                    <c:when test="${cdFootballSingleOrder.buyWays==1}">
                        混投
                    </c:when>
                    <c:when test="${cdFootballSingleOrder.buyWays==2}">
                        胜平负
                    </c:when>
                    <c:when test="${cdFootballSingleOrder.buyWays==4}">
                        猜比分
                    </c:when>
                    <c:when test="${cdFootballSingleOrder.buyWays==5}">
                        总进球
                    </c:when>
                    <c:when test="${cdFootballSingleOrder.buyWays==6}">
                        半全场
                    </c:when>
                    <c:when test="${cdFootballSingleOrder.buyWays==3}">
                        让球胜负平
                    </c:when>
                    <c:otherwise>
                        订单异常
                    </c:otherwise>
                </c:choose></td>
            <td>${cdFootballSingleOrder.acount}</td>
            <td>${cdFootballSingleOrder.price}</td>
            <td><c:choose>
                <c:when test="${cdFootballSingleOrder.type==0}">
                    普通订单
                </c:when>
                <c:when test="${cdFootballSingleOrder.type==1}">
                    已发起跟单
                </c:when>
                <c:when test="${cdFootballSingleOrder.type==2}">
                    跟单订单
                </c:when>
                <c:otherwise>
                    订单异常
                </c:otherwise>
            </c:choose></td>
            <td>${cdFootballSingleOrder.createDate}</td>
            <td><%--${cdFootballSingleOrder.status}--%>
                <c:choose>
                    <c:when test="${cdFootballSingleOrder.status==1}">
                        未付款
                    </c:when>
                    <c:when test="${cdFootballSingleOrder.status==2}">
                        已付款
                    </c:when>
                    <c:when test="${cdFootballSingleOrder.status==3}">
                        已出票
                    </c:when>
                    <c:when test="${cdFootballSingleOrder.status==4}">
                        中奖
                    </c:when>
                    <c:when test="${cdFootballSingleOrder.status==5}">
                        未中奖
                    </c:when>
                    <c:otherwise>
                        订单异常
                    </c:otherwise>
                </c:choose>
            </td>
            <shiro:hasPermission name="cfootballorder:cdFootballSingleOrder:edit">
                <td>
                    <a href="${ctx}/cfootballorder/cdFootballSingleOrder/form?id=${cdFootballSingleOrder.id}">查看/出票</a>
                    <a href="${ctx}/cfootballorder/cdFootballSingleOrder/delete?id=${cdFootballSingleOrder.id}"
                       onclick="return confirmx('确认要删除该竞彩足球订单吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
