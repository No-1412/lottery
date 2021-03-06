<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>竞彩篮球订单管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    //loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li>
        <a href="${ctx}/cbasketballorder/cdBasketballSingleOrder/">竞彩篮球订单列表</a>
    </li>
    <li class="active">
        <a href="${ctx}/cbasketballorder/cdBasketballSingleOrder/form?id=${cdBasketballSingleOrder.id}">竞彩篮球订单<shiro:hasPermission
                name="cbasketballorder:cdBasketballSingleOrder:edit">${not empty cdBasketballSingleOrder.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
                name="cbasketballorder:cdBasketballSingleOrder:edit">查看</shiro:lacksPermission></a>
    </li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="cdBasketballSingleOrder"
           action="${ctx}/cbasketballorder/cdBasketballSingleOrder/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <tags:message content="${message}"/>
    <div class="control-group" style="display: inline-block;">
        <label class="control-label">订单号:</label>
        <div class="controls">
            <form:input path="orderNum" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
   <%-- <div class="control-group">
        <label class="control-label">投注方式:</label>
        <div class="controls">
            <form:input path="buyWays" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">注数:</label>
        <div class="controls">
            <form:input path="acount" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>--%>
    <div class="control-group" style="display: inline-block;">
        <label class="control-label">金额:</label>
        <div class="controls">
            <form:input path="price" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
   <%-- <div class="control-group">
        <label class="control-label">用户:</label>
        <div class="controls">
                &lt;%&ndash;<form:input path="uid" htmlEscape="false" maxlength="200" class="required" readonly="true"/>&ndash;%&gt;
            <input type="text" readonly="readonly" value="${uName}">
        </div>
    </div>--%>

    <div class="control-group" style="display: inline-block;">
        <label class="control-label">状态:</label>
        <div class="controls">
            <div><p><font color="red" size="1">将以当前时间(${today})赔率出票 </font></p></div>
            <form:select id="status" path="status">
                <form:option value="3" label="出票"/>
            </form:select>

        </div>
    </div>


    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr><td colspan="6">选号方案</td></tr>
        <tr><td colspan="6" style="font-weight: bold;font-size: xx-large">选择场次：${fn:length(detailList)}场,&nbsp;过关方案：单关</td></tr>
        <tr>
            <th>场次</th>
                <%--<th>主队</th>--%>
            <th>对阵</th>
                <%--<th>客队</th>--%>
           <th>全场比分</th>
                <%-- <th>半场比分</th>--%>
            <th>投注方案</th>
            <th>胆码</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${detailList}" var="detailList">
            <tr>
                <td> ${detailList.matId}</td>
                    <%--<td>${detailList.matId}</td>--%>
                <td>${detailList.vs}</td>
                    <%--<td>${detailList.matId}</td>--%>
                <td>${detailList.result}</td>
               <%--  <td></td>--%>
                <td style="color:red;">
                        ${detailList.size}${detailList.win}${detailList.fail}${detailList.beat}${detailList.let}
                </td>
                <td></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-actions">
        <shiro:hasPermission name="cbasketballorder:cdBasketballSingleOrder:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
