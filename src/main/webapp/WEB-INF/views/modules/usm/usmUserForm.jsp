<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户表管理</title>
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
			<a href="${ctx}/usm/usmUser/">用户表列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/usm/usmUser/form?id=${usmUser.id}">用户表<shiro:hasPermission name="usm:usmUser:edit">${not empty usmUser.id?'查看':'添加'}</shiro:hasPermission><shiro:lacksPermission name="usm:usmUser:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="usmUser" action="${ctx}/usm/usmUser/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">头像:</label>
			<div class="controls">
				<img src="${usmUser.img}" style="width: 30%;">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号:</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邀请码:</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">积分:</label>
			<div class="controls">
				<form:input path="point" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">余额:</label>
			<div class="controls">
				<form:input path="money" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册时间:</label>
			<div class="controls">
				<form:input path="createDate" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间:</label>
			<div class="controls">
				<form:input path="updateDate" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册方式:</label>
			<div class="controls">
					${fns:getDictLabel(usmUser.regType,"bm_user_reg" ,"未知" )}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冻结标识:</label>
			<div class="controls">
				${fns:getDictLabel(usmUser.freeze,"no_yes" ,"否" )}
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">签名:</label>
			<div class="controls">
				<form:textarea path="autograph" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge" readonly="true"/>
			</div>
		</div>
		<%--<div class="form-actions">--%>
			<%--<shiro:hasPermission name="usm:usmUser:edit">--%>
				<%--<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;--%>
			<%--</shiro:hasPermission>--%>
			<%--<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>--%>
		<%--</div>--%>
	</form:form>
</body>
</html>
