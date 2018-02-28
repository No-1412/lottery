<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>国际化配置</title>
    <meta id="viewport" name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1; user-scalable=no;">
    <link rel="stylesheet" href="${ctxStatic}/share/css/base.css"/>
    <link rel="stylesheet" href="${ctxStatic}/share/css/Safari.css"/>
    <script src="${ctxStatic}/share/js/jquery_1.12.4.js"></script>
    <script src="${ctxStatic}/share/js/jquery.event.drag.js"></script>
    <script src="${ctxStatic}/share/js/jquery.touchSlider.js"></script>
    <script src="${ctxStatic}/share/js/slider.js"></script>

</head>

<body>
<fmt:message key="welcome"/>
<br/>
<a href="?language=zh_CN">简体中文</a>
<a href="?language=en">English</a>
</body>
</html>