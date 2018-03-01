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
    <div class="control-group">
        <label class="control-label">订单号:</label>
        <div class="controls">
            <form:input path="orderNum" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
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
        <label class="control-label">主负分差详情: </label>
        <div class="controls">
                <%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
            <p><font size="3"> <b>场次+主客队+分差/赔率+注数</b></font></p>
                <%-- <c:forEach items="${tList}" var="tList">
                     <c:forTokens items=" ${tList}" delims="+" var="ceshi">
                         &lt;%&ndash;<p><font size="2"> ${ceshi}</font></p>&ndash;%&gt;
                         <c:out value="${ceshi}"></c:out>
                     </c:forTokens>
                 </c:forEach>--%>
            <font size="3">
                <c:forEach items="${fList}" var="fList">
                    <p>
                        <c:forTokens items=" ${fList}" delims="+" var="alist" varStatus="j">
                            <c:if test="${j.count==3}">
                                <c:forTokens items=" ${alist}" delims="," var="detail">
                                    <c:forTokens items=" ${detail}" delims="/" var="score" varStatus="i">
                                        <c:if test="${i.count==1 }">
                                            <font color="red"> <b> ${score}</b></font>/
                                        </c:if>
                                        <c:if test="${i.count==3}">
                                            <font color="red"> <b> ${score}</b></font>,
                                        </c:if>
                                        <c:if test="${i.count==2}">
                                            ${score}+
                                        </c:if>
                                    </c:forTokens>
                                </c:forTokens>
                            </c:if>
                            <c:if test="${j.count!=3}">
                                ${alist}+
                            </c:if>
                        </c:forTokens>
                        <br>
                    </p>
                </c:forEach>
            </font>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">主胜分差详情: </label>
        <div class="controls">
                <%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
            <p><font size="3"> <b>场次+主客队+分差/赔率+注数</b></font></p>
                <%-- <c:forEach items="${tList}" var="tList">
                     <c:forTokens items=" ${tList}" delims="+" var="ceshi">
                         &lt;%&ndash;<p><font size="2"> ${ceshi}</font></p>&ndash;%&gt;
                         <c:out value="${ceshi}"></c:out>
                     </c:forTokens>
                 </c:forEach>--%>
            <font size="3">
                <c:forEach items="${wList}" var="wList">
                    <p>
                        <c:forTokens items=" ${wList}" delims="+" var="alist" varStatus="j">
                            <c:if test="${j.count==3}">
                                <c:forTokens items=" ${alist}" delims="," var="detail">
                                    <c:forTokens items=" ${detail}" delims="/" var="score" varStatus="i">
                                        <c:if test="${i.count==1 }">
                                            <font color="red"> <b> ${score}</b></font>/
                                        </c:if>
                                        <c:if test="${i.count==3}">
                                            <font color="red"> <b> ${score}</b></font>,
                                        </c:if>
                                        <c:if test="${i.count==2}">
                                            ${score}+
                                        </c:if>
                                    </c:forTokens>
                                </c:forTokens>
                            </c:if>
                            <c:if test="${j.count!=3}">
                                ${alist}+
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
            <div><p><font color="red" size="1">将以当前时间(${today})赔率出票 </font></p></div>
            <form:select id="status" path="status">
                <form:option value="3" label="出票"/>
            </form:select>

        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="cbasketballorder:cdBasketballSingleOrder:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
