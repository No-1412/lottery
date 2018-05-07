<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>竞彩蓝球管理</title>
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
			<a href="${ctx}/cbasketballmixed/cdBasketballMixed/">竞彩蓝球列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/cbasketballmixed/cdBasketballMixed/form?id=${cdBasketballMixed.id}">竞彩蓝球<shiro:hasPermission name="cbasketballmixed:cdBasketballMixed:edit">${not empty cdBasketballMixed.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cbasketballmixed:cdBasketballMixed:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cdBasketballMixed" action="${ctx}/cbasketballmixed/cdBasketballMixed/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">期号:</label>
			<div class="controls">
				<form:input path="remarks" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场次:</label>
			<div class="controls">
				<form:input path="matchId" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赛事名称:</label>
			<div class="controls">
				<form:input path="eventName" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主队:</label>
			<div class="controls">
				<form:input path="winningName" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客队:</label>
			<div class="controls">
				<form:input path="defeatedName" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">让分:</label>
			<div class="controls">
				<form:input path="close" htmlEscape="false" maxlength="200" class="required" readonly="true"/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">大小分:</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="zclose" htmlEscape="false" maxlength="200" class="required" readonly="true"/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">截止时间:</label>
			<div class="controls">
				<form:input path="timeEndsale" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">胜负赔率:</label>
			<div class="controls">

				<input type="text" readonly="readonly" value="主负，主胜"/>
				<br>
				<form:input path="victoryordefeatOdds" htmlEscape="false" maxlength="200" class="required"/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">让球胜负赔率:</label>
			<div class="controls">
				<input type="text" readonly="readonly" value="主负，主胜"/>
				<br>
				<form:input path="spreadOdds" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">大小分赔率:</label>
			<div class="controls">
				<input type="text" readonly="readonly" value="大于${cdBasketballMixed.zclose}，小于${cdBasketballMixed.zclose}" />
				<br>
				<form:input path="sizeOdds" htmlEscape="false" maxlength="200" class="required" />
					<%--<form:textarea path="allOdds" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">胜分差赔率:</label>
			<%--<div class="controls">
				<form:textarea path="surpassScoreGap" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
				<div>
					<font size="2" color="#a9a9a9"> 1-5， 6-10， 11-15， 16-20， 21-25， 26+，1-5， 6-10， 11-15， 16-20， 21-25， 26+
						</font>
					<br>
				</div>
			</div>--%>
			<div class="controls">
				<input type="text" readonly="readonly" value="主负:1-5,6-10,11-15,16-20,21-25,26+ 主胜:1-5,6-10,11-15,16-20,21-25,26+" style="width: 500px"/>
				<br>
				<form:input path="surpassScoreGap" htmlEscape="false" maxlength="200" class="required" cssStyle="width: 500px"/>
					<%--<form:textarea path="allOdds" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>--%>
			</div>
		</div>


		<div class="form-actions">
			<shiro:hasPermission name="cbasketballmixed:cdBasketballMixed:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
