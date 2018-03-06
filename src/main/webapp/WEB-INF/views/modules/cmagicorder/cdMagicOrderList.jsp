<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>神单订单管理</title>
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
    <li class="active"><a href="${ctx}/cmagicorder/cdMagicOrder/">神单订单列表</a></li>
    <%--<shiro:hasPermission name="cmagicorder:cdMagicOrder:edit">
        <li><a href="${ctx}/cmagicorder/cdMagicOrder/form">神单订单添加</a></li>
    </shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="cdMagicOrder" action="${ctx}/cmagicorder/cdMagicOrder/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>订单号 ：</label><form:input path="orderNum" htmlEscape="false" maxlength="50" class="input-small"/>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<tags:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>订单号</th>
        <th>彩种</th>
        <th>神单金额</th>
        <th>佣金百分比</th>

        <th>跟单人数</th>
        <th>用户名</th>
        <th>发起时间</th>
        <shiro:hasPermission name="cmagicorder:cdMagicOrder:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="cdMagicOrder">
        <tr>
            <td><a href="${ctx}/cmagicorder/cdMagicOrder/form?id=${cdMagicOrder.id}">${cdMagicOrder.orderNum}</a></td>
                <%-- <td>${cdMagicOrder.type}</td>--%>
            <td><c:choose>
                <c:when test="${cdMagicOrder.type==1}">
                    足球单关
                </c:when>
                <c:when test="${cdMagicOrder.type==2}">
                    足球串关
                </c:when>
                <c:when test="${cdMagicOrder.type==3}">
                    篮球单关
                </c:when>
                <c:when test="${cdMagicOrder.type==4}">
                    篮球串关
                </c:when>
                <c:otherwise>
                    订单异常
                </c:otherwise>
            </c:choose></td>
            <td>${cdMagicOrder.price}</td>
            <td>${cdMagicOrder.charges}%</td>
            <td>${cdMagicOrder.followCounts}</td>
            <td>${cdMagicOrder.uName}</td>
            <td>${cdMagicOrder.createDate}</td>

            <shiro:hasPermission name="cmagicorder:cdMagicOrder:edit">
                <td>
                    <a href="${ctx}/cmagicorder/cdMagicOrder/form?id=${cdMagicOrder.id}">修改</a>
                    <a href="${ctx}/cmagicorder/cdMagicOrder/delete?id=${cdMagicOrder.id}"
                       onclick="return confirmx('确认要删除该神单订单吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
