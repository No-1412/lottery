<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>提现记录管理</title>
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
        <a href="${ctx}/crecord/cdRecordCash/">提现记录列表</a>
    </li>
    <li class="active">
        <a href="${ctx}/crecord/cdRecordCash/form?id=${cdRecordCash.id}">提现记录<shiro:hasPermission
                name="crecord:cdRecordCash:edit">${not empty cdRecordCash.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
                name="crecord:cdRecordCash:edit">查看</shiro:lacksPermission></a>
    </li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="cdRecordCash" action="${ctx}/crecord/cdRecordCash/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <tags:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">提现单号:</label>
        <div class="controls">
            <form:input path="orderNum" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">提现卡号:</label>
        <div class="controls">
            <form:input path="cardNum" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">持卡人姓名:</label>
        <div class="controls">
            <form:input path="cardName" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请时间:</label>
        <div class="controls">
            <form:input path="createDate" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">提现金额:</label>
        <div class="controls">
            <form:input path="price" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户名:</label>
        <div class="controls">
            <form:input path="uname" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">审核:</label>
        <div class="controls">
            <form:select id="status" path="status">
                <form:option value="2" label="申请处理中"/>
                <form:option value="3" label="通过"/>
                <form:option value="4" label="不通过"/>
            </form:select>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="crecord:cdRecordCash:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
