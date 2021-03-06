<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>竞彩篮球订单管理</title>
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
			<a href="${ctx}/corder/cdOrderFollowTimes/">竞彩篮球订单列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/corder/cdOrderFollowTimes/form?id=${cdOrderFollowTimes.id}">竞彩篮球订单<shiro:hasPermission name="corder:cdOrderFollowTimes:edit">${not empty cdOrderFollowTimes.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="corder:cdOrderFollowTimes:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cdOrderFollowTimes" action="${ctx}/corder/cdOrderFollowTimes/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">倍数:</label>
			<div class="controls">
				<form:input path="times" htmlEscape="false" maxlength="200" class="required"/>倍
			</div>
		</div>
	<%--	<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="corder:cdOrderFollowTimes:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
