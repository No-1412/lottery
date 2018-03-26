<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>用户管理</title>
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
			<a href="${ctx}/erp/user/">用户列表</a>
		</li>
		<li class="active">
			<a href="javascript:void(0)">用户<shiro:hasPermission name="erp:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="erp:user:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="erpUser" action="${ctx}/erp/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">昵称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="200" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">余额:</label>
			<div class="controls">
				<form:input path="balance" htmlEscape="false" maxlength="200" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间:</label>
			<div class="controls">
				<form:input path="createDate" htmlEscape="false" maxlength="200" class="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">销售员:</label>
			<div class="controls">
				<tags:treeselect id="saleId" name="saleId.id" value="${erpUser.saleId.id}" labelName="saleId.name" labelValue="${erpUser.saleId.name}"
								 title="销售员" url="/erp/user/treeData" cssClass="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="erp:user:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>

	<%@ include file="/WEB-INF/views/modules/erp/loopWall.jsp"%>
</body>
</html>
