<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>基本信息管理</title>
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
			<a href="${ctx}/bm/bmBase/">基本信息列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/bm/bmBase/form?id=${bmBase.id}">基本信息<shiro:hasPermission name="bm:bmBase:edit">${not empty bmBase.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="bm:bmBase:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bmBase" action="${ctx}/bm/bmBase/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">分类:</label>
			<div class="controls">
				<form:select path="kind" items="${fns:getDictList('bm_base_type')}" itemLabel="label" itemValue="value" />
				<%--<form:input path="kind" htmlEscape="false" maxlength="200" class="required"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图文详情:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="false" path="content" rows="4" maxlength="200" class="input-xxlarge"/>
				<tags:ckeditor replace="content" uploadPath="/bm/bmHelp" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="bm:bmBase:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
