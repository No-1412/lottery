<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关于我们管理</title>
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
			<a href="${ctx}/caboutus/cdAboutUs/">关于我们列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/caboutus/cdAboutUs/form?id=${cdAboutUs.id}">关于我们<shiro:hasPermission name="caboutus:cdAboutUs:edit">${not empty cdAboutUs.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="caboutus:cdAboutUs:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cdAboutUs" action="${ctx}/caboutus/cdAboutUs/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">app名称:</label>
			<div class="controls">
				<form:textarea path="name" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">logo图片:</label>
			<div class="controls">
				<input type="hidden" id="img" name="logoImg" value="${cdAboutUs.logoImg}"/>
				<tags:ckfinder input="img" type="images" uploadPath="/caboutus/cdAboutUs" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">功能介绍:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="false" path="function" rows="3" maxlength="200" class="input-xxlarge"/>
				<tags:ckeditor replace="content" uploadPath="/report/content" />
			</div>
		</div>

		<div class="control-group">
		<label class="control-label">客服电话:</label>
		<div class="controls">
			<form:textarea path="kefuTel" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge required"/>
		</div>
		</div>
		<div class="control-group">
		<label class="control-label">官网链接:</label>
		<div class="controls">
			<form:textarea path="guanwangHref" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge required"/>
		</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="caboutus:cdAboutUs:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
