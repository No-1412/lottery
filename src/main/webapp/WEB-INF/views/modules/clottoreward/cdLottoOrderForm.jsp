<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>大乐透订单管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
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
        <a href="${ctx}/clottoreward/cdLottoOrder/">大乐透订单列表</a>
    </li>
    <li class="active">
        <a href="${ctx}/clottoreward/cdLottoOrder/form?id=${cdLottoOrder.id}">大乐透订单<shiro:hasPermission
                name="clottoreward:cdLottoOrder:edit">${not empty cdLottoOrder.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
                name="clottoreward:cdLottoOrder:edit">查看</shiro:lacksPermission></a>
    </li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="cdLottoOrder" action="${ctx}/clottoreward/cdLottoOrder/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <tags:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">订单号:</label>
        <div class="controls">
            <form:input path="orderNum" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">期数:</label>
        <div class="controls">
            <form:input path="weekday" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">注数:</label>
        <div class="controls">
            <form:input path="acount" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">金额:</label>
        <div class="controls">
            <form:input path="price" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">用户:</label>
        <div class="controls">
                <%--<form:input path="uid" htmlEscape="false" maxlength="200" class="required" readonly="true"/>--%>
            <input type="text" readonly="readonly" value="${uName}">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">下单时间:</label>
        <div class="controls">
            <form:input path="createDate" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">玩法:</label>
        <div class="controls">
                <%--<form:input path="uid" htmlEscape="false" maxlength="200" class="required" readonly="true"/>--%>
            <input type="text" readonly="readonly" value="${typeStr}">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">前区(胆|拖):</label>
        <div class="controls">
            <form:input path="redNums" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">后区(胆|拖):</label>
        <div class="controls">
            <form:input path="blueNums" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">倍数:</label>
        <div class="controls">
            <form:input path="times" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">追号期数:</label>
        <div class="controls">
            <form:input path="continuity" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">是否追加投注:</label>
        <div class="controls">
            <c:if test="${cdLottoOrder.isPlus == 0}"><input type="text" value="否" readonly="readonly"></c:if>
            <c:if test="${cdLottoOrder.isPlus == 1}"><input type="text" value="是" readonly="readonly"></c:if>
            <form:hidden path="isPlus" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">状态:</label>
        <div class="controls">
            <form:select id="status" path="status">
                <form:option value="3" label="出票"/>
            </form:select>

        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="clottoreward:cdLottoOrder:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
