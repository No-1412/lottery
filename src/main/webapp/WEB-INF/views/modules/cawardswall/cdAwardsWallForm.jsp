<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>大奖墙管理</title>
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
			<a href="${ctx}/cawardswall/cdAwardsWall/">大奖墙列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/cawardswall/cdAwardsWall/form?id=${cdAwardsWall.id}">大奖墙<shiro:hasPermission name="cawardswall:cdAwardsWall:edit">${not empty cdAwardsWall.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cawardswall:cdAwardsWall:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cdAwardsWall" action="${ctx}/cawardswall/cdAwardsWall/save" method="post" class="form-horizontal">
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
			<label class="control-label">奖金:</label>
			<div class="controls">
				<form:input path="prize" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">期次:</label>
			<div class="controls">
				<form:input path="issue" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简介:</label>
			<div class="controls">
				<form:input path="intro" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="cawardswall:cdAwardsWall:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
