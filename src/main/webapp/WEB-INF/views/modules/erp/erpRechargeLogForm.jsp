<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>销售后台充值记录管理</title>
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
			<a href="${ctx}/erp/erpRechargeLog/">销售后台充值记录列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/erp/erpRechargeLog/form?id=${erpRechargeLog.id}">销售后台充值记录<shiro:hasPermission name="erp:erpRechargeLog:edit">${not empty erpRechargeLog.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="erp:erpRechargeLog:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="erpRechargeLog" action="${ctx}/erp/erpRechargeLog/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">用户名:</label>
			<div class="controls">
				<tags:treeselect id="userId" name="userId.id" value="${erpRechargeLog.userId.id}" labelName="userId.name" labelValue="${erpUser.userId.name}"
								 title="用户" url="/erp/user/treeDatas" cssClass="required" disabled="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">充值金额:</label>
			<div class="controls">
				<form:input id="money" path="money" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认金额:</label>
			<div class="controls">
				<input id="confirmMoney" htmlEscape="false" maxlength="200" class="required" style="border: 1px solid #cccccc;border-radius: 4px;height: 20px;padding: 4px 6px"/>
				<label id="errormessage" for="confirmMoney" style="color: red">*两次金额不正确</label>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="erp:erpRechargeLog:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>

	<%@ include file="/WEB-INF/views/modules/erp/loopWall.jsp"%>
</body>
</html>
