<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>

<head>
    <title>业绩管理</title>
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
    <style>
        .bigImg{
            clear: both;
            position: absolute;
            padding: 10px;
            display: inline-block;
            max-height: 300px;
            background-color:rgba(255,255,255,1);
            border:#555555 solid 1px;
            overflow: scroll;
        }
        .bigImg span{
            margin: auto;
            width: 500px;
            background-color: white;
            display: block;
            background-color:rgba(255,200,200,1);
            border:brown solid 1px;
        }
    </style>
    <script type="text/javascript">
        function imgClisk(index) {
            $("#big").show();

        }
        function showDis(number) {
            $.ajax({
                url: "${ctx}/erp/erpOrder/look?number=" + number,
                success: function (data) {
                    var info = "";
                    var dis = new Array;
                    dis = data.detail;
                    console.log(data);
                    console.log(dis);
                    if (dis.length > 0) {
                        for (var i = 0; i < dis.length; i++) {
                            info += '<span>' + 'aaaaa' + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + 'zzzzzzz' + '</span>';
                            if (i == dis.length - 1) {
                                info += ' <input id="ddd" style="margin-left: auto" class="btn btn-primary" type="button" value="关 闭"/>'
                            }
                        }
                        $("#big").html(info);
                        var winheight = $(document).height();
                        var winwidth = $(document).width();
                        var left = (winwidth - 520 - 100) / 2;
                        var top = (winheight - 300) / 2;
                        $("#big").css('top', top);
                        $("#big").css('left', left);
                        $("#big").show();
                        $("#ddd").click(function () {
                            $("#big").hide()
                        });
                    }
                }
            });


        }
        $("#big").click(function () {
            $("#big").hide()
        });
    </script>
</head>
<body>
<div class="bigImg" style="display: none" id="big">
</div>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/erp/erpOrder/">业绩列表</a></li>
    <%--<shiro:hasPermission name="erp:erpOrder:edit">--%>
    <%--<li><a href="${ctx}/erp/erpOrder/form">业绩添加</a></li>--%>
    <%--</shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="erpOrder" action="${ctx}/erp/erpOrder/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<tags:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>客户名称</th>
        <th>购买彩种</th>
        <th>购彩金额</th>
        <th>所属销售</th>
        <th>购买时间</th>
        <th>备注</th>
        <shiro:hasPermission name="erp:erpOrder:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="erpOrder">
        <tr>
            <td><a href="${ctx}/erp/erpOrder/form?id=${erpOrder.id}">${erpOrder.userId.name}</a></td>
            <%--<td><a href="javaScript:;" onclick="showDis('${erpOrder.number}',0)">${erpOrder.number}查看详情</a></td>--%>
            <td><a href="${ctx}/erp/erpOrder/orderForm?id=${erpOrder.id}">${erpOrder.number}查看详情</a></td>
            <td>${erpOrder.totalPrice}</td>
            <td>${erpOrder.userId.saleId.name}</td>
            <td>${erpOrder.createDate}</td>
            <td>
                <c:if test="${empty erpOrder.outTime}">  未出票</c:if>
                <%--<c:if test="${not empty erpOrder.createDate}">  ${erpOrder.remarks}</c:if>--%>
                    </td>
            <shiro:hasPermission name="erp:erpOrder:edit">
                <td>
                    <a href="${ctx}/erp/erpOrder/form?id=${erpOrder.id}">修改备注</a>
                        <%--<a href="${ctx}/erp/erpOrder/delete?id=${erpOrder.id}" onclick="return confirmx('确认要删除该业绩吗？', this.href)">删除</a>--%>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>

<%@ include file="/WEB-INF/views/modules/erp/loopWall.jsp"%>
</body>

</html>
