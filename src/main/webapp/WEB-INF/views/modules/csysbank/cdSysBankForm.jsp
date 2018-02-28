<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统银行卡管理</title>
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
			<a href="${ctx}/csysbank/cdSysBank/">系统银行卡列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/csysbank/cdSysBank/form?id=${cdSysBank.id}">系统银行卡<shiro:hasPermission name="csysbank:cdSysBank:edit">${not empty cdSysBank.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="csysbank:cdSysBank:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cdSysBank" action="${ctx}/csysbank/cdSysBank/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">银行名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">银行图标:</label>
			<div class="controls">
				<input type="hidden" id="bankImg" name="bankImg" value="${cdSysBank.bankImg}"/>
				<tags:ckfinder input="bankImg" type="images" uploadPath="/csysbank/cdSysBank" selectMultiple="false"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">客服电话:</label>
			<div class="controls">
				<form:input path="kefuTel" htmlEscape="false" maxlength="200" class=""/>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">是否启用:</label>
			<div class="controls">
				<form:select path="isUse" id="isUse">
					<form:option value="" label="请选择"/>
					<form:option value="0" label="未启用"/>
					<form:option value="1" label="已启用"/>
				</form:select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">银行编号:</label>
			<div class="controls">
				<form:input path="bankNo" htmlEscape="false" maxlength="200" class=""/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="csysbank:cdSysBank:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
