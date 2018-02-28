<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收藏记录管理</title>
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
			<a href="${ctx}/lom/lomCollect/">收藏记录列表</a>
		</li>
		<%--<li class="active">--%>
			<%--<a href="${ctx}/lom/lomCollect/form?id=${lomCollect.id}">收藏记录<shiro:hasPermission name="lom:lomCollect:edit">${not empty lomCollect.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="lom:lomCollect:edit">查看</shiro:lacksPermission></a>--%>
		<%--</li>--%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="lomCollect" action="${ctx}/lom/lomCollect/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">用户id:</label>
			<div class="controls">
				<form:input path="userId" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收藏id:</label>
			<div class="controls">
				<form:input path="belongId" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分类:</label>
			<div class="controls">
				<form:select path="kind" items="${fns:getDictList('lom_collect_type')}" itemLabel="label" itemValue="value" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="lom:lomCollect:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
