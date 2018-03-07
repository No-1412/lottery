<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>任选九订单管理</title>
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
        <a href="${ctx}/cchoosenine/cdChooseNineOrder/">任选九订单列表</a>
    </li>
    <li class="active">
        <a href="${ctx}/cchoosenine/cdChooseNineOrder/form?id=${cdChooseNineOrder.id}">任选九订单<shiro:hasPermission
                name="cchoosenine:cdChooseNineOrder:edit">${not empty cdChooseNineOrder.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
                name="cchoosenine:cdChooseNineOrder:edit">查看</shiro:lacksPermission></a>
    </li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="cdChooseNineOrder" action="${ctx}/cchoosenine/cdChooseNineOrder/save"
           method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <tags:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">订单号:</label>
        <div class="controls">
            <form:input path="orderNumber" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
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
        <label class="control-label">倍数:</label>
        <div class="controls">
            <form:input path="times" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
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
        <label class="control-label">订单详情: </label>
        <div class="controls">
                <%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
            <p><font size="3"> <b> 场次+主客队+胜(3)/平(1)/负(0)+胆1(是)/0(否) </b> </font></p>
                <%-- <c:forEach items="${list}" var="list" >
                   <p> <b> ${list}; </b> </p>
                 </c:forEach>--%>
            <font size="3">
                <c:forEach items="${list}" var="list">
                    <p>
                        <c:forTokens items=" ${list}" delims="+" var="alist" varStatus="j">

                            <c:if test="${j.count==3}">
                                <font color="red"> <b> ${alist}</b></font>+
                            </c:if>
                            <c:if test="${j.count!=3 && j.count!=4}">
                                ${alist}+
                            </c:if>
                            <c:if test="${j.count==4}">
                                <font color="red"> <b> ${alist}</b></font>
                            </c:if>

                        </c:forTokens>
                        <br>
                    </p>
                </c:forEach>
            </font>
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
        <shiro:hasPermission name="cchoosenine:cdChooseNineOrder:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
