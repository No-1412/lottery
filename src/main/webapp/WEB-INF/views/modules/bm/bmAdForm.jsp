<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告管理</title>
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
			<a href="${ctx}/bm/bmAd/">广告列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/bm/bmAd/form?id=${bmAd.id}">广告<shiro:hasPermission name="bm:bmAd:edit">${not empty bmAd.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="bm:bmAd:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bmAd" action="${ctx}/bm/bmAd/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">分类:</label>
			<div class="controls">
				<form:select path="kind" items="${fns:getDictList('bm_ad_type')}" itemLabel="label" itemValue="value" />
					<%--<form:input path="kind" htmlEscape="false" maxlength="200" class="required"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关联信息类型:</label>
			<div class="controls">
				<form:select path="related" items="${fns:getDictList('bm_ad_related')}" itemLabel="label" itemValue="value" onchange="changeSub(this.value)" />
					<%--<form:input path="kind" htmlEscape="false" maxlength="200" class="required"/>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required input-xxlarge measure-input"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片:</label>
			<div class="controls">
				<input type="hidden" id="img" name="img" value="${bmAd.img}"/>
				<tags:ckfinder input="img" type="images" uploadPath="/bm/ad" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接:</label>
			<div class="controls">
				<form:input path="link" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">备注:</label>--%>
			<%--<div class="controls">--%>
				<%--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="bm:bmAd:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
