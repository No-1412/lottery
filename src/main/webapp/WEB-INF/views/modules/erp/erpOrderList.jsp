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
        .bigImg {
            clear: both;
            position: absolute;
            padding: 10px;
            display: inline-block;
            max-height: 300px;
            background-color: rgba(255, 255, 255, 1);
            border: #555555 solid 1px;
            overflow: scroll;
        }

        .bigImg span {
            margin: auto;
            width: 500px;
            background-color: white;
            display: block;
            background-color: rgba(255, 200, 200, 1);
            border: brown solid 1px;
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

        function tipsOut(erpOrderId,input) {
            //var winPrice = $("#winPrice").val();
            var newWinPrice = $(input).prev().val();
            var winForm = $("#winForm");
            var r = confirm("确定派发奖金" + newWinPrice + "元？");
            if (r == true) {
                $("#winPrice").val(newWinPrice);
                $("#erpOrderId").val(erpOrderId);
                winForm.submit();
            }
        }


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
    &nbsp;
    <label>开奖状态 ：</label><form:select id="status" path="status">
    <form:option value="" label="全部"/>
    <form:option value="1" label="待开奖"/>
    <form:option value="2" label="已开奖"/>
    <form:option value="3" label="中奖"/>
</form:select> &nbsp;
    <label>订单号：</label><form:input path="number" htmlEscape="false" maxlength="50" class="input-small"/>
    &nbsp;
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<tags:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>用户昵称</th>
        <th>购买彩种</th>
        <th>购彩金额</th>
        <th>所属销售</th>
        <th>购买时间</th>
        <th>备注</th>
        <th>中奖金额</th>
        <th>派奖状态</th>
        <th>出票状态</th>
        <shiro:hasPermission name="erp:erpOrder:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <form action="${ctx}/erp/erpOrder/addAward" method="post" style="margin: 0 0 0 0" id="winForm">
        <input type="hidden" value="" style="" name="erpOrderId" id="erpOrderId">
        <input type="hidden" value="" style="" name="winPrice" id="winPrice">
    </form>
    <c:forEach items="${page.list}" var="erpOrder">

        <tr <c:if test="${erpOrder.bestType=='2'}">style="color: #c66d12;" </c:if>>
            <td><a href="${ctx}/erp/erpOrder/form?id=${erpOrder.id}">${erpOrder.userId.name}</a></td>
                <%--<td><a href="javaScript:;" onclick="showDis('${erpOrder.number}',0)">${erpOrder.number}查看详情</a></td>--%>
            <td><a href="${ctx}/erp/erpOrder/orderForm?id=${erpOrder.id}">${erpOrder.number} 查看详情</a></td>
            <td>${erpOrder.totalPrice}</td>
            <td>${erpOrder.userId.saleId.name}</td>
            <td>${erpOrder.createDate}</td>
            <td>${erpOrder.remarks}</td>
            <td><c:choose>
                <c:when test="${erpOrder.winPrice eq '0'}">

                </c:when>
                <c:otherwise>
                    <%--<font color="red">${erpOrder.winPrice}</font>--%>
                    <form action="${ctx}/erp/erpOrder/addAward" method="post" style="margin: 0 0 0 0" id="winForm">

                        <c:choose>
                            <c:when test="${erpOrder.winStatus eq '0'}">
                                <input type="text" value="${erpOrder.winPrice}"
                                       style="width: 88px; color: red;height: 15px;margin-top: 10px">
                                <%--<a href="#">点击派奖</a>--%>
                                <shiro:hasPermission name="erp:addAward:edit">
                                    <input type="button" style="color: red" value="派奖"
                                           onclick="tipsOut('${erpOrder.id}',this);">
                                </shiro:hasPermission>
                            </c:when>
                            <c:otherwise>
                                <font color="red">${erpOrder.winPrice}</font>
                            </c:otherwise>
                        </c:choose>
                    </form>

                </c:otherwise>
            </c:choose>
            </td>

            <td>

                <c:choose>
                    <c:when test="${erpOrder.winPrice eq '0'}">

                    </c:when>
                    <c:otherwise>
                        <c:if test="${erpOrder.winStatus eq '0'}"> 未派奖</c:if>
                        <c:if test="${erpOrder.winStatus eq '1'}"> <font color="red">已派奖</font> </c:if>
                    </c:otherwise>
                </c:choose>
                    <%--<c:if test="${not empty erpOrder.createDate}">  ${erpOrder.remarks}</c:if>--%>
            </td>
            <td>
                <c:if test="${empty erpOrder.outTime}"> 未出票</c:if>
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

<%@ include file="/WEB-INF/views/modules/erp/loopWall.jsp" %>
</body>

</html>
