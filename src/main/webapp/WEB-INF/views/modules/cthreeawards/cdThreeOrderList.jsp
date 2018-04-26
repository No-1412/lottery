<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>排列三订单管理</title>
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
    <li class="active"><a href="${ctx}/cthreeawards/cdThreeOrder/">排列三订单列表</a></li>
    <%-- <shiro:hasPermission name="cthreeawards:cdThreeOrder:edit">
         <li><a href="${ctx}/cthreeawards/cdThreeOrder/form">排列三订单添加</a></li>
     </shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="cdThreeOrder" action="${ctx}/cthreeawards/cdThreeOrder/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>订单号 ：</label><form:input path="orderNum" htmlEscape="false" maxlength="50" class="input-small"/>
    <label class="control-label">投注方式:</label>
    <form:select id="buyWays" path="buyWays">
        <form:option value="" label="全部"/>
        <form:option value="1" label="直选"/>
        <form:option value="2" label="和值"/>
        <form:option value="3" label="组三单式"/>
        <form:option value="4" label="组三复式"/>
        <form:option value="5" label="组六单式"/>
        <form:option value="6" label="组六复试"/>
    </form:select>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<tags:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>订单号</th>
        <th>订单详情</th>
        <th>玩法</th>
        <th>期数</th>
        <th>注数</th>
        <th>倍数</th>
        <th>金额</th>
        <th>追号</th>
        <th>下单时间</th>
        <th>订单状态</th>
        <shiro:hasPermission name="cthreeawards:cdThreeOrder:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="cdThreeOrder">
        <tr>
            <td><a href="${ctx}/cthreeawards/cdThreeOrder/form?id=${cdThreeOrder.id}">${cdThreeOrder.orderNum}</a></td>
            <td>${cdThreeOrder.nums}</td>
            <td>
                <c:choose>
                    <c:when test="${cdThreeOrder.buyWays==1}">
                        直选
                    </c:when>
                    <c:when test="${cdThreeOrder.buyWays==2}">
                        和值
                    </c:when>
                    <c:when test="${cdThreeOrder.buyWays==3}">
                        组三单式
                    </c:when>
                    <c:when test="${cdThreeOrder.buyWays==4}">
                        组三复式
                    </c:when>
                    <c:when test="${cdThreeOrder.buyWays==5}">
                        组六单式
                    </c:when>
                    <c:when test="${cdThreeOrder.buyWays==6}">
                        组六复式
                    </c:when>
                    <c:otherwise>
                        订单异常
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${cdThreeOrder.weekday}</td>
            <td>${cdThreeOrder.acount}</td>
            <td>${cdThreeOrder.times}倍</td>
            <td>${cdThreeOrder.price}</td>
            <td>${cdThreeOrder.continuity}期</td>
            <td>${cdThreeOrder.createDate}</td>
            <td><c:choose>
                <c:when test="${cdThreeOrder.status==1}">
                    未付款
                </c:when>
                <c:when test="${cdThreeOrder.status==2}">
                    已付款
                </c:when>
                <c:when test="${cdThreeOrder.status==3}">
                    已出票
                </c:when>
                <c:when test="${cdThreeOrder.status==4}">
                    中奖
                </c:when>
                <c:when test="${cdThreeOrder.status==5}">
                    未中奖
                </c:when>

                <c:otherwise>
                    订单异常
                </c:otherwise>
            </c:choose></td>

            <shiro:hasPermission name="cthreeawards:cdThreeOrder:edit">
                <td>
                    <a href="${ctx}/cthreeawards/cdThreeOrder/form?id=${cdThreeOrder.id}">查看/出票</a>
                    <%--<a href="${ctx}/cthreeawards/cdThreeOrder/delete?id=${cdThreeOrder.id}"--%>
                       <%--onclick="return confirmx('确认要删除该排列三订单吗？', this.href)">删除</a>--%>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
