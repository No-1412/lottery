<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中奖攻略管理</title>
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
			<a href="${ctx}/cwinmethod/cdWinMethod/">中奖攻略列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/cwinmethod/cdWinMethod/form?id=${cdWinMethod.id}">中奖攻略<shiro:hasPermission name="cwinmethod:cdWinMethod:edit">${not empty cdWinMethod.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cwinmethod:cdWinMethod:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cdWinMethod" action="${ctx}/cwinmethod/cdWinMethod/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简介:</label>
			<div class="controls">
				<form:textarea path="issue" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片:</label>
			<div class="controls">
				<input type="hidden" id="img" name="img" value="${cdWinMethod.img}"/>
				<tags:ckfinder input="img" type="images" uploadPath="/cwinMethod/cdWinMethod" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详情图片:</label>
			<div class="controls">
				<input type="hidden" id="imgBig" name="imgBig" value="${cdWinMethod.imgBig}"/>
				<tags:ckfinder input="imgBig" type="images" uploadPath="/cwinMethod/cdWinMethod" selectMultiple="false"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cwinmethod:cdWinMethod:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
