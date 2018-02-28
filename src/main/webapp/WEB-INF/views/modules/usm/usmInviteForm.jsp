<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户邀请管理</title>
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
			<a href="${ctx}/usm/usmInvite/">用户邀请列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/usm/usmInvite/form?id=${usmInvite.id}">用户邀请<shiro:hasPermission name="usm:usmInvite:edit">${not empty usmInvite.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="usm:usmInvite:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="usmInvite" action="${ctx}/usm/usmInvite/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">邀请人:</label>
			<div class="controls">
				<form:input path="inviter.name" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">受邀人:</label>
			<div class="controls">
				<form:input path="invitee.name" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邀请时间:</label>
			<div class="controls">
				<form:input path="createDate" htmlEscape="false" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="usm:usmInvite:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
