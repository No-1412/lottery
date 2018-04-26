<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>竞彩篮球订单管理</title>
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
    <li class="active"><a href="${ctx}/cbasketballorder/cdBasketballSingleOrder/">竞彩篮球订单列表</a></li>
    <%--<shiro:hasPermission name="cbasketballorder:cdBasketballSingleOrder:edit">
        <li><a href="${ctx}/cbasketballorder/cdBasketballSingleOrder/form">竞彩篮球订单添加</a></li>
    </shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="cdBasketballSingleOrder"
           action="${ctx}/cbasketballorder/cdBasketballSingleOrder/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>订单号 ：</label><form:input path="orderNum" htmlEscape="false" maxlength="50" class="input-small"/>
    <label class="control-label">投注方式:</label>
    <form:select id="buyWays" path="buyWays">
        <form:option value="" label="全部"/>
        <form:option value="1" label="混投"/>
        <form:option value="2" label="主负分差"/>
        <form:option value="3" label="主胜分差"/>
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
        <shiro:hasPermission name="cbasketballorder:cdBasketballSingleOrder:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="cdBasketballSingleOrder">
        <tr>
            <td>
                <a href="${ctx}/cbasketballorder/cdBasketballSingleOrder/form?id=${cdBasketballSingleOrder.id}">${cdBasketballSingleOrder.orderNum}</a>
            </td>
            <td>
                <c:choose>
                    <c:when test="${cdBasketballSingleOrder.buyWays==1}">
                        混投
                    </c:when>
                    <c:when test="${cdBasketballSingleOrder.buyWays==2}">
                        主负分差单关
                    </c:when>
                    <c:when test="${cdBasketballSingleOrder.buyWays==3}">
                        主胜分差单关
                    </c:when>

                    <c:otherwise>
                        订单异常
                    </c:otherwise>
                </c:choose></td>
            <td>${cdBasketballSingleOrder.acount}</td>
            <td>${cdBasketballSingleOrder.price}</td>
            <td><c:choose>
                <c:when test="${cdBasketballSingleOrder.type==0}">
                    普通订单
                </c:when>
                <c:when test="${cdBasketballSingleOrder.type==1}">
                    已发起跟单
                </c:when>
                <c:when test="${cdBasketballSingleOrder.type==2}">
                    跟单订单
                </c:when>
                <c:otherwise>
                    订单异常
                </c:otherwise>
            </c:choose></td>
            <td>${cdBasketballSingleOrder.createDate}</td>
            <td>
                <c:choose>
                    <c:when test="${cdBasketballSingleOrder.status==1}">
                        未付款
                    </c:when>
                    <c:when test="${cdBasketballSingleOrder.status==2}">
                        已付款
                    </c:when>
                    <c:when test="${cdBasketballSingleOrder.status==3}">
                        已出票
                    </c:when>
                    <c:when test="${cdBasketballSingleOrder.status==4}">
                        中奖
                    </c:when>
                    <c:when test="${cdBasketballSingleOrder.status==5}">
                        未中奖
                    </c:when>

                    <c:otherwise>
                        订单异常
                    </c:otherwise>
                </c:choose></td>
            <shiro:hasPermission name="cbasketballorder:cdBasketballSingleOrder:edit">
                <td>
                    <a href="${ctx}/cbasketballorder/cdBasketballSingleOrder/form?id=${cdBasketballSingleOrder.id}">查看/出票</a>
                    <%--<a href="${ctx}/cbasketballorder/cdBasketballSingleOrder/delete?id=${cdBasketballSingleOrder.id}"--%>
                       <%--onclick="return confirmx('确认要删除该竞彩篮球订单吗？', this.href)">删除</a>--%>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
