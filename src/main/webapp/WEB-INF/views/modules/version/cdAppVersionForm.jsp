<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>版本信息管理</title>
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
			<a href="${ctx}/version/cdAppVersion/">版本信息列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/version/cdAppVersion/form?id=${cdAppVersion.id}">版本信息<shiro:hasPermission name="version:cdAppVersion:edit">${not empty cdAppVersion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="version:cdAppVersion:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cdAppVersion" action="${ctx}/version/cdAppVersion/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">系统渠道:</label>
			<div class="controls">
				<form:select path="versionChannel" class="input-medium required">
					<form:options items="${fns:getDictList('channel_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本标识:</label>
			<div class="controls">
				<form:input path="versionCode" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本名称:</label>
			<div class="controls">
				<form:input path="versionName" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否升级:</label>
			<div class="controls">
				<form:select path="versionIsUpgrade" class="input-medium required">
					<form:options items="${fns:getDictList('upgrade_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否强制升级:</label>
			<div class="controls">
				<form:select path="versionIsEnforcement" class="input-medium required">
					<form:options items="${fns:getDictList('enforcement_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下载地址:</label>
			<div class="controls">
				<form:textarea path="versionDownloadUrl" htmlEscape="false" cssStyle="resize: none;" rows="3" cols="30" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">升级描述:</label>
			<div class="controls">
				<form:textarea path="versionDescription" htmlEscape="false" cssStyle="resize: none;" rows="10" cols="20" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="version:cdAppVersion:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
