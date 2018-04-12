<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>竞彩足球订单管理</title>
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
        <a href="${ctx}/cfootballorder/cdFootballFollowOrder/">竞彩足球订单列表</a>
    </li>
    <li class="active">
        <a href="${ctx}/cfootballorder/cdFootballFollowOrder/form?id=${cdFootballFollowOrder.id}">竞彩足球订单<shiro:hasPermission
                name="cfootballorder:cdFootballFollowOrder:edit">${not empty cdFootballFollowOrder.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
                name="cfootballorder:cdFootballFollowOrder:edit">查看</shiro:lacksPermission></a>
    </li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="cdFootballFollowOrder"
           action="${ctx}/cfootballorder/cdFootballFollowOrder/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <tags:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">订单号:</label>
        <div class="controls">
            <form:input path="orderNum" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <%--<div class="control-group">
        <label class="control-label">期数:</label>
        <div class="controls">
            <form:input path="weekday" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>--%>
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
        <label class="control-label">相关让球数:</label>
        <div class="controls">
            <form:input path="letBalls" htmlEscape="false" maxlength="200" class="" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">串关数:</label>
        <div class="controls">
            <form:input path="followNum" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">比分详情: </label>
        <div class="controls">
                <%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
            <p><font size="3"> <b>胆是(1)/否(0)+场次+主客队+比分/赔率</b></font></p>
            <font size="3">
                <c:forEach items="${sList}" var="sList">
                    <p>
                        <c:forTokens items=" ${sList}" delims="+" var="alist" varStatus="j">
                            <c:if test="${j.count==4}">
                                <c:forTokens items=" ${alist}" delims="," var="detail">
                                    <c:forTokens items=" ${detail}" delims="/" var="score" varStatus="i">
                                        <c:if test="${i.count==1 }">
                                            <font color="red"> <b> ${score}</b></font>/
                                        </c:if>
                                        <c:if test="${i.count!=1}">
                                            ${score},
                                        </c:if>

                                    </c:forTokens>
                                </c:forTokens>
                            </c:if>
                            <c:if test="${j.count==1}">
                                <font color="red"> <b> ${alist}</b></font>+
                            </c:if>
                            <c:if test="${j.count!=4 && j.count!=1}">
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
        <label class="control-label">总进球详情: </label>
        <div class="controls">
                <%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
            <p><font size="3"> <b>胆是(1)/否(0)+场次+主客队+进球数/赔率</b></font></p>
                <%-- <c:forEach items="${gList}" var="gList">
                     <p><font size="2"> ${gList}</font></p>
                 </c:forEach>--%>
            <font size="3">
                <c:forEach items="${gList}" var="gList">
                    <p>
                        <c:forTokens items=" ${gList}" delims="+" var="alist" varStatus="j">
                            <c:if test="${j.count==4}">
                                <c:forTokens items=" ${alist}" delims="," var="detail">
                                    <c:forTokens items=" ${detail}" delims="/" var="score" varStatus="i">
                                        <c:if test="${i.count==1 }">
                                            <font color="red"> <b> ${score}</b></font>/
                                        </c:if>
                                        <c:if test="${i.count!=1}">
                                            ${score},
                                        </c:if>

                                    </c:forTokens>
                                </c:forTokens>
                            </c:if>
                            <c:if test="${j.count==1}">
                                <font color="red"> <b> ${alist}</b></font>+
                            </c:if>
                            <c:if test="${j.count!=4 && j.count!=1}">
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
        <label class="control-label">半全场详情: </label>
        <div class="controls">
                <%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
            <p><font size="3"> <b>胆是(1)/否(0)+场次+主客队+胜负平/赔率</b></font></p>
                <%--<c:forEach items="${hList}" var="hList">
                    <p><font size="2"> ${hList}</font></p>
                </c:forEach>--%>
            <font size="3">
                <c:forEach items="${hList}" var="hList">
                    <p>
                        <c:forTokens items=" ${hList}" delims="+" var="alist" varStatus="j">
                            <c:if test="${j.count==4}">
                                <c:forTokens items=" ${alist}" delims="," var="detail">
                                    <c:forTokens items=" ${detail}" delims="/" var="score" varStatus="i">
                                        <c:if test="${i.count==1 }">
                                            <font color="red"> <b> ${score}</b></font>/
                                        </c:if>
                                        <c:if test="${i.count!=1}">
                                            ${score},
                                        </c:if>

                                    </c:forTokens>
                                </c:forTokens>
                            </c:if>
                            <c:if test="${j.count==1}">
                                <font color="red"> <b> ${alist}</b></font>+
                            </c:if>
                            <c:if test="${j.count!=4 && j.count!=1}">
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
        <label class="control-label">胜负平详情: </label>
        <div class="controls">
                <%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
            <p><font size="3"> <b>胆是(1)/否(0)+场次+主客队+胜负平/赔率</b></font></p>
                <%--<c:forEach items="${bList}" var="bList">
                    <p><font size="2"> ${bList}</font></p>
                </c:forEach>--%>
            <font size="3">
                <c:forEach items="${bList}" var="bList">
                    <p>
                        <c:forTokens items=" ${bList}" delims="+" var="alist" varStatus="j">
                            <c:if test="${j.count==4}">
                                <c:forTokens items=" ${alist}" delims="," var="detail">
                                    <c:forTokens items=" ${detail}" delims="/" var="score" varStatus="i">
                                        <c:if test="${i.count==1 }">
                                            <font color="red"> <b> ${score}</b></font>/
                                        </c:if>
                                        <c:if test="${i.count!=1}">
                                            ${score},
                                        </c:if>

                                    </c:forTokens>
                                </c:forTokens>
                            </c:if>
                            <c:if test="${j.count==1}">
                                <font color="red"> <b> ${alist}</b></font>+
                            </c:if>
                            <c:if test="${j.count!=4 && j.count!=1}">
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
        <label class="control-label">让球胜负平详情: </label>
        <div class="controls">
                <%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
            <p><font size="3"> <b>胆是(1)/否(0)+场次+主客队+胜负平/赔率</b></font></p>
                <%-- <c:forEach items="${tList}" var="tList">
                     <c:forTokens items=" ${tList}" delims="+" var="ceshi">
                         &lt;%&ndash;<p><font size="2"> ${ceshi}</font></p>&ndash;%&gt;
                         <c:out value="${ceshi}"></c:out>
                     </c:forTokens>
                 </c:forEach>--%>
            <font size="3">
                <c:forEach items="${tList}" var="tList">
                    <p>
                        <c:forTokens items=" ${tList}" delims="+" var="alist" varStatus="j">
                            <c:if test="${j.count==4}">
                                <c:forTokens items=" ${alist}" delims="," var="detail">
                                    <c:forTokens items=" ${detail}" delims="/" var="score" varStatus="i">
                                        <c:if test="${i.count==1 }">
                                            <font color="red"> <b> ${score}</b></font>/
                                        </c:if>
                                        <c:if test="${i.count!=1}">
                                            ${score},
                                        </c:if>

                                    </c:forTokens>
                                </c:forTokens>
                            </c:if>
                            <c:if test="${j.count==1}">
                                <font color="red"> <b> ${alist}</b></font>+
                            </c:if>
                            <c:if test="${j.count!=4 && j.count!=1}">
                                ${alist}+
                            </c:if>
                        </c:forTokens>
                        <br>
                    </p>
                </c:forEach>
            </font>
        </div>
    </div>

    <%--<div class="control-group">--%>
    <%--<label class="control-label">备注:</label>--%>
    <%--<div class="controls">--%>
    <%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
    <%--</div>--%>
    <%--</div>--%>
    <div class="control-group">
        <label class="control-label">状态:</label>
        <div class="controls">
            <div><p><font color="red" size="1">将以当前时间(${today})赔率出票 </font></p></div>
            <form:select id="status" path="status">
                <form:option value="3" label="出票"/>
            </form:select>
            <br>

        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="cfootballorder:cdFootballFollowOrder:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
