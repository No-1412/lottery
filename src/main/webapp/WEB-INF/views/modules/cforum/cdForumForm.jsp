<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>论坛管理</title>
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
			<a href="${ctx}/cforum/cdForum/">论坛列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/cforum/cdForum/form?id=${cdForum.id}">论坛<shiro:hasPermission name="cforum:cdForum:edit">${not empty cdForum.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cforum:cdForum:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cdForum" action="${ctx}/cforum/cdForum/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">论坛标题:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">发布人:</label>
			<div class="controls">
				<form:input path="userName" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片:</label>
			<div class="controls">
				<input type="hidden" id="imgList" name="imgList" value="${cdForum.imgList}"/>
				<tags:ckfinder input="imgList" type="images" uploadPath="/afornum/cdFornum" selectMultiple="false"/>
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
			<label class="control-label">是否帖子:</label>
			<div class="controls">
				<form:select path="isPosts" id="isUse">
					<form:option value="" label="请选择"/>
					<form:option value="0" label="帖子"/>
					<form:option value="1" label="回复"/>
				</form:select>
			</div>
		</div>


		<div class="form-actions">
			<shiro:hasPermission name="cforum:cdForum:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
