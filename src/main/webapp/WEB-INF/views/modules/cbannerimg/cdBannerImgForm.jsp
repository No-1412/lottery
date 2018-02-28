<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>轮播图片管理</title>
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
			<a href="${ctx}/cbannerimg/cdBannerImg/">轮播图片列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/cbannerimg/cdBannerImg/form?id=${cdBannerImg.id}">轮播图片<shiro:hasPermission name="cbannerimg:cdBannerImg:edit">${not empty cdBannerImg.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cbannerimg:cdBannerImg:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cdBannerImg" action="${ctx}/cbannerimg/cdBannerImg/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">图片:</label>
			<div class="controls">
				<input type="hidden" id="img" name="img" value="${cdBannerImg.img}"/>
				<tags:ckfinder input="img" type="images" uploadPath="/cbanner/cdBannerImg" selectMultiple="false"/>
			</div>
		</div>

		<form:hidden path="imgType" value="1"/>


		<div class="control-group">
			<label class="control-label">排序（数字越大越靠前）:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="200" class="required digits"/>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">是否启用:</label>
			<div class="controls">
				<form:select path="isUse" id="isUse">
					<form:option value="" label="请选择"/>
					<form:option value="1" label="已启用"/>
					<form:option value="0" label="未启用"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cbannerimg:cdBannerImg:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
