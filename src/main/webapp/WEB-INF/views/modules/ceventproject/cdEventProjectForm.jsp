<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>赛事专题管理</title>
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
			<a href="${ctx}/ceventproject/cdEventProject/">赛事专题列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/ceventproject/cdEventProject/form?id=${cdEventProject.id}">赛事专题<shiro:hasPermission name="ceventproject:cdEventProject:edit">${not empty cdEventProject.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ceventproject:cdEventProject:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cdEventProject" action="${ctx}/ceventproject/cdEventProject/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">标题:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="false" path="content" rows="4" maxlength="200" class="input-xxlarge"/>
				<tags:ckeditor replace="content" uploadPath="/report/content" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片:</label>
			<div class="controls">
				<input type="hidden" id="img" name="img" value="${cdEventProject.img}"/>
				<tags:ckfinder input="img" type="images" uploadPath="/ceventproject/cdEventProject" selectMultiple="false"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="ceventproject:cdEventProject:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
