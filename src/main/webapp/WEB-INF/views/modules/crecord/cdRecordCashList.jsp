<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>提现记录管理</title>
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
    <li class="active"><a href="${ctx}/crecord/cdRecordCash/">提现记录列表</a></li>
    <%--<shiro:hasPermission name="crecord:cdRecordCash:edit">--%>
    <%--<li><a href="${ctx}/crecord/cdRecordCash/form">提现记录添加</a></li>--%>
    <%--</shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="cdRecordCash" action="${ctx}/crecord/cdRecordCash/" method="post"
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
        <th>提现单号</th>
        <th>申请时间</th>
        <th>处理时间</th>
        <th>用户名</th>
        <th>银行卡号</th>
        <th>持卡人姓名</th>
        <th>提款金额</th>
        <th>处理状态</th>
        <shiro:hasPermission name="crecord:cdRecordCash:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="cdRecordCash">
        <tr>
            <td><a href="${ctx}/crecord/cdRecordCash/form?id=${cdRecordCash.id}">${cdRecordCash.orderNum}</a></td>
            <td>${cdRecordCash.createDate}</td>
            <td>${cdRecordCash.dealTime}</td>
            <td>${cdRecordCash.uname}</td>
            <td>${cdRecordCash.cardNum}</td>
            <td>${cdRecordCash.cardName}</td>
            <td>${cdRecordCash.price}</td>
            <td>
                <c:choose>
                    <c:when test="${cdRecordCash.status==1}">
                        提出申请
                    </c:when>
                    <c:when test="${cdRecordCash.status==2}">
                        申请处理中
                    </c:when>
                    <c:when test="${cdRecordCash.status==3}">
                        审核通过
                    </c:when>
                    <c:when test="${cdRecordCash.status==4}">
                        审核不通过
                    </c:when>
                    <c:otherwise>
                        订单异常
                    </c:otherwise>
                </c:choose>
            </td>
            <shiro:hasPermission name="crecord:cdRecordCash:edit">
                <td>
                    <a href="${ctx}/crecord/cdRecordCash/form?id=${cdRecordCash.id}">修改</a>
                    <a href="${ctx}/crecord/cdRecordCash/delete?id=${cdRecordCash.id}"
                       onclick="return confirmx('确认要删除该提现记录吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
