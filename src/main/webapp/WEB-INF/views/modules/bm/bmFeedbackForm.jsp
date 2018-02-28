<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>意见反馈管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
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
			<a href="${ctx}/bm/bmFeedback/">意见反馈列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/bm/bmFeedback/form?id=${bmFeedback.id}">意见反馈<shiro:hasPermission name="bm:bmFeedback:edit">${not empty bmFeedback.id?'查看':'添加'}</shiro:hasPermission><shiro:lacksPermission name="bm:bmFeedback:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bmFeedback" action="${ctx}/bm/bmFeedback/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">所属用户:</label>
			<div class="controls">
				<form:input path="userId.name" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详情:</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">截图:</label>
			<div class="controls">
				<c:forEach items="${fn:split(bmFeedback.imgs, '|')}" var="img" varStatus="cur" >
					<img src="${img}" style="width: 20%" >
					<c:if test="${cur.index eq 3 }"><br/></c:if>
				</c:forEach>
			</div>
		</div>
		<%--<div class="form-actions">--%>
			<%--<shiro:hasPermission name="bm:bmFeedback:edit">--%>
				<%--<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;--%>
			<%--</shiro:hasPermission>--%>
			<%--<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>--%>
		<%--</div>--%>
	</form:form>
</body>
</html>
