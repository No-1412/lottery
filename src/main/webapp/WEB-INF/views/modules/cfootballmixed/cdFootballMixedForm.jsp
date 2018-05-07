<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>竞彩足球管理</title>
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
        <a href="${ctx}/cfootballmixed/cdFootballMixed/">竞彩足球列表</a>
    </li>
    <li class="active">
        <a href="${ctx}/cfootballmixed/cdFootballMixed/form?id=${cdFootballMixed.id}">竞彩足球<shiro:hasPermission
                name="cfootballmixed:cdFootballMixed:edit">${not empty cdFootballMixed.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
                name="cfootballmixed:cdFootballMixed:edit">查看</shiro:lacksPermission></a>
    </li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="cdFootballMixed" action="${ctx}/cfootballmixed/cdFootballMixed/save"
           method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <tags:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">期号:</label>
        <div class="controls">
            <form:input path="remarks" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">场次:</label>
        <div class="controls">
            <form:input path="matchId" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">赛事名称:</label>
        <div class="controls">
            <form:input path="eventName" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">主队:</label>
        <div class="controls">
            <form:input path="winningName" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">客队:</label>
        <div class="controls">
            <form:input path="defeatedName" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">让球:</label>
        <div class="controls">
            <form:input path="close" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">截止时间:</label>
        <div class="controls">
            <form:input path="timeEndsale" htmlEscape="false" maxlength="200" class="required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">非让球赔率:</label>
        <div class="controls">

            <input type="text" readonly="readonly" value="胜，  平，  负"/>
            <br>
            <form:input path="notConcedepointsOdds" htmlEscape="false" maxlength="200" class="required"/>

        </div>
    </div>
    <div class="control-group">
        <label class="control-label">让球赔率:</label>
        <div class="controls">
            <input type="text" readonly="readonly" value="胜，  平，  负"/>
            <br>
            <form:input path="concedepointsOdds" htmlEscape="false" maxlength="200" class="required"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">总进球赔率:</label>
        <div class="controls">
            <input type="text" readonly="readonly" value="0球，1球，2球，3球，4球，5球，7球，7+球" style="width: 330px"/>
            <br>
            <form:input path="allOdds" htmlEscape="false" maxlength="200" class="required" cssStyle="width: 330px"/>
                <%--<form:textarea path="allOdds" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">半全场赔率:</label>
        <div class="controls">
            <input type="text" readonly="readonly" value="胜胜，胜平，胜负，平平，平负，负胜，负平，负负" style="width: 330px"/>
            <br>
            <form:input path="halfOdds" htmlEscape="false" maxlength="200" class="required" cssStyle="width: 330px"/>
                <%--<form:textarea path="halfOdds" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">比分赔率:</label>
        <div class="controls">
            <form:textarea path="scoreOdds" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
            <div>
                <font size="2" color="#a9a9a9"> 1:0， 2:0， 2:1， 3:0， 3:1， 3:2， 4:0， 4:1， 4:2， 5:0， 5:1， 5:2， 胜其它， <br>
                    0:0， 1:1， 2:2， 3:3， 平其它， 0:1， 0:2， 1:2， 0:3， 1:3， 2:3， 0:4， 1:4， <br>
                    2:4， 0:5， 1:5， 2:5， 负其它</font>
                <br>
            </div>
        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="cfootballmixed:cdFootballMixed:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
