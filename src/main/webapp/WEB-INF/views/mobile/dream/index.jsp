<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>index</title>
  <meta name="decorator" content="default"/>

</head>
<body class="home-template">
  <div class="navbar navbar-inner navbar-fixed-top">
    <div class="container">

      <div class="navbar-header">
        <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand hidden-sm" href="./Bootstrap中文网_files/Bootstrap中文网.html" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-首页&#39;])">Bootstrap中文网</a>
      </div>
      <div class="navbar-collapse collapse" role="navigation">
        <ul class="nav navbar-nav">
          <li class="dropdown hidden-sm hidden-md">
            <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">Bootstrap2中文文档<b class="caret"></b></a>
            <ul class="dropdown-menu">
              <li>
                <a href="http://v2.bootcss.com/getting-started.html" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-v2doc-起步&#39;])">起步</a>
              </li>
              <li>
                <a href="http://v2.bootcss.com/scaffolding.html" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-v2doc-脚手架&#39;])">脚手架</a>
              </li>
              <li>
                <a href="http://v2.bootcss.com/base-css.html" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-v2doc-基本CSS样式&#39;])">基本CSS样式</a>
              </li>
              <li>
                <a href="http://v2.bootcss.com/components.html" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-v2doc-组件&#39;])">组件</a>
              </li>
              <li>
                <a href="http://v2.bootcss.com/javascript.html" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-v2doc-JavaScript插件&#39;])">JavaScript插件</a>
              </li>
              <li>
                <a href="http://v2.bootcss.com/customize.html" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-v2doc-定制&#39;])">定制</a>
              </li>
            </ul>
          </li>
          <li class="dropdown">
            <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">Bootstrap3中文文档<b class="caret"></b></a>
            <ul class="dropdown-menu">
              <li>
                <a href="http://v3.bootcss.com/getting-started/" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-v3doc-起步&#39;])">起步</a>
              </li>
              <li>
                <a href="http://v3.bootcss.com/css/" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-v3doc-CSS&#39;])">CSS</a>
              </li>
              <li>
                <a href="http://v3.bootcss.com/components/" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-v3doc-组件&#39;])">组件</a>
              </li>
              <li>
                <a href="http://v3.bootcss.com/javascript/" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-v3doc-JavaScript插件&#39;])">JavaScript插件</a>
              </li>
              <li>
                <a href="http://v3.bootcss.com/customize/" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-v3doc-定制&#39;])">定制</a>
              </li>
            </ul>
          </li>
          <li><a id="btn-jike-video" href="http://e.jikexueyuan.com/bootstrap.html?hmsr=bootstrap_guide_bsevent_e" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;Bootstrap视频教程&#39;])">Bootstrap视频教程</a></li>
          <li><a href="http://www.bootcss.com/p/lesscss/" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;less&#39;])">Less 教程</a></li>
          <li><a href="http://www.jquery123.com/" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;jquery&#39;])">jQuery API</a></li>
          <li><a href="http://expo.bootcss.com/" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;expo&#39;])">网站实例</a></li>
          <li><i class="fa fa-fire job-hot"></i><a href="http://www.liepin.com/event/bootstrap/index.shtml?mscid=b_o_0001" target="_blank" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;navbar-高薪工作&#39;])">高薪工作</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right hidden-sm">
          <li><a href="http://www.bootcss.com/about/" onclick="_hmt.push([&#39;_trackEvent&#39;, &#39;navbar&#39;, &#39;click&#39;, &#39;about&#39;])">关于</a></li>
        </ul>
      </div>
    </div>
  </div>
      </div>

  </div>

</body>
</html>