<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="author" content="Administrator" />
  <%--<meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
  <title>官方梦想详情</title>
  <link href="${ctxStatic}/cc/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${ctxStatic}/cc/jquery-ui.css" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="${ctxStatic}/cc/style.css" />
  <script type="text/javascript" src="${ctxStatic}/cc/jquery-1.7.1.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/cc/jquery.slideBox.js"></script>

  <!-- Date: 2015-10-12 -->
</head>
<body>
<div class="banner">
  <div class="slider">
  <ul>
    <c:forEach items="${lists}" var="lists">
      <li><a href="#" title=""><img src="${lists}"></a></li>
    </c:forEach>
    <%-- <li><a href="#" title=""><img src="../cc/img/1.jpg" class="img-responsive"></a></li>
     <li><a href="#" title=""><img src="../cc/img/2.jpg" class="img-responsive"></a></li>
     <li><a href="#" title=""><img src="../cc/img/3.jpg" class="img-responsive"></a></li>
     <li><a href="#" title=""><img src="../cc/img/4.jpg" class="img-responsive"></a></li>
     <li><a href="#" title=""><img src="../cc/img/5.jpg" class="img-responsive"></a></li>--%>
  </ul>
  </div>
  <script>
    $(".slider").yxMobileSlider({width:640,height:325,during:3000});
  </script>
</div>


<div class="header bgc mab pd">

  <div class="msg">
    <ul>
      <li><img src="${ctxStatic}/cc/img/8.png" />已筹金额<span>${dream.currentValue}</span></li>
      <li><img src="${ctxStatic}/cc/img/7.png"/>目标金额<span >${dream.expectedValue}</span></li>
      <li><img src="${ctxStatic}/cc/img/9.png"/>获奖人数<span >${extraction}</span></li>
      <li><img src="${ctxStatic}/cc/img/6.png"/>截止日期<span >${data}</span></li>
    </ul>
  </div>
  <div class="jindu">
    <div id="progressbar"  style="height: 20px;display:inline-block;">

    </div>
    <span id="value"> ${percent}</span>
  </div>
</div>
<div class="author bgc mab pd">
			<span class="touxiang_d">
				<img src="${dream.merchantImg}" />
			</span>
			<span class="name">
				${dream.activityTitle}<br />
              <span>${dream.merchantName}</span>
			</span>
</div>
<div class="content bgc mab pd">
  <h4>${dream.name} </h4>

  ${dream.remarks}
</div>
<div class="tip bgc mab pd">
  <h4>活动说明</h4>

  ${dream.dreamExplain}
</div>
<script src="${ctxStatic}/cc/jquery-ui.js"></script>
<script>
  $( "#progressbar" ).progressbar({
    value: ${jindu}
  });
</script>
</body>
</html>

