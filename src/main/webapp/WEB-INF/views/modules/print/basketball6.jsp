<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!-- saved from url=(0014)about:internet -->
<head>
  <meta charset="UTF-8">
  <title>彩票打印</title>
  <style>
    *{padding:0;margin:0;}
    body{width:79.16mm;height:175.68mm;position: relative;}
    .lBox{width:4mm;float: left;}
    .rBOx{
      width: 64.2mm;
      float: left;
      border-top:1px solid transparent;
      border-left:1px solid transparent;
    }
    .rTop{
      width: 64.2mm;
      border-collapse: collapse;
      border-spacing: 0;
      border-right:1px solid transparent;
      border-bottom:1px solid transparent;
    }
    .rTop tr{
      border-bottom:1px solid transparent;
    }
    .rTop td{
      width:5.2mm;
      height:5mm;
    }
    .rTop td p{
      width:3.5mm;
      height:1.1mm;
      margin: 1.9mm 0.85mm;
    }
    .rContent{
      width:64.2mm;
      overflow: hidden;
    }
    #match1,#match2,#match3,#match4,#match5,#match6{
      width:21.4mm;
      float: left;
    }
    .match_title{
      border-right:1px solid transparent;
      border-bottom:1px solid transparent;
      width:21.2mm;
      height: 4.9mm;
    }
    /*过关方式的头*/
    .match_title1{
      border-right:1px solid transparent;
      border-bottom:1px solid transparent;
      width:42.5mm;
      height: 4.9mm;
    }
    #match1 table,#match2 table,#match3 table,#match4 table,#match5 table,#match6 table,.pass_method table,.multiple_table{
      border-right:1px solid transparent;
      border-bottom:1px solid transparent;
      border-collapse: collapse;
      border-spacing: 0;
    }
    #match1 table td,#match2 table td,#match3 table td,#match4 table td,#match5 table td,#match6 table td,.pass_method table td,.multiple_table td{
      width:5.1mm;
      height:5mm;
    }
    #match1 table td p,#match2 table td p,#match3 table td p,#match4 table td p,#match5 table td p,#match6 table td p,.pass_method table td p,.multiple_table td p{
      margin: 1.9mm 0.8mm;
      width:3.5mm;
      height:1.2mm;
    }

    .rBottom{
      width:64.2mm;
      overflow: hidden;
      border-top:1px solid transparent;
    }
    /*过关方式*/
    .pass_method{
      float:left;
      width:42.8mm;
    }
    .pass_method tr{
      border-bottom:1px solid transparent;
    }
    .multiple{
      width:21.4mm;
      float:left;
    }
    .multiple_table tr{
      border-bottom:1px solid transparent;
    }

    /*结束标识*/
    .over_tip{width:3.7mm;height:2mm;background: #000;position: absolute;bottom:13.2mm;right:1.2mm;}
    /*左边的黑点*/
    .big_p{width:3.7mm;height:1.5mm;background: #000;margin-bottom: 3.65mm;}
    .big_p:last-child{margin-bottom: 0;}
    /*中间的黑点*/
    .small_p{background: #000;}
    #print{
      width:20mm;
      height: 10mm;
      border:1px solid #000;
      line-height: 10mm;
      text-align: center;
      margin-left: 5mm;
      margin-top: 5mm;
      cursor:pointer;
    }
  </style>
  <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
  <script type="text/javascript" src="${ctxStatic}/print/jianrong.js"></script>
</head>
<body>
<div id="print" >打印</div>
<!--startprint-->
<div style="height:12.4mm"><p>${orderNumber}</p><p>${returnStr}</p></div>
<div class="content" style="overflow:hidden;margin-left:2mm;">
  <ul class="lBox">

  </ul>
  <div class="rBOx">
    <table class="rTop">
      <tr>
        <td><p class="small_p"></p></td>
        <td><p></p></td>
        <td><p class="small_p"></p></td>
        <td><p></p></td>
        <td><p class="small_p"></p></td>
        <td><p></p></td>
        <td><p class="small_p"></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p class="small_p"></p></td>
        <td><p class="small_p"></p></td>
        <td><p class="small_p"></p></td>
      </tr>
      <tr>
        <td><p class="small_p"></p></td>
        <td><p></p></td>
        <td><p class="small_p"></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
      </tr>
      <!-- 过关类型 -->
      <tr class="pass_type">
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
        <td><p></p></td>
      </tr>
    </table>
    <div class="rContent">
      <!-- 赛事编号1 -->
      <div id="match1">
        <div class="match_title"></div>
        <!-- 星期几 -->
        <table class="week_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
        <!-- 场次 -->
        <table class="playnum_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
        <!-- 竞猜选项1 -->
        <div class="match_title"></div>
        <table class="guess_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <!-- 大小分 -->
          <tr class="big_small">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <!-- 让分胜负 -->
          <tr class="win_fail">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <!-- 胜负 -->
          <tr class="outcome_table">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>

      </div>
      <!-- 赛事编号2 -->
      <div id="match2">
        <div class="match_title"></div>
        <!-- 星期几 -->
        <table class="week_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
        <!-- 场次 -->
        <table class="playnum_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
        <!-- 竞猜选项2 -->
        <div class="match_title"></div>
        <table class="guess_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <!-- 大小分 -->
          <tr class="big_small">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <!-- 让分胜负 -->
          <tr class="win_fail">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <!-- 胜负 -->
          <tr class="outcome_table">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
      </div>
      <!-- 赛事编号3 -->
      <div id="match3">
        <div class="match_title"></div>
        <!-- 星期几 -->
        <table class="week_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
        <!-- 场次 -->
        <table class="playnum_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
        <!-- 竞猜选项3 -->
        <div class="match_title"></div>
        <table class="guess_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <!-- 大小分 -->
          <tr class="big_small">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <!-- 让分胜负 -->
          <tr class="win_fail">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <!-- 胜负 -->
          <tr class="outcome_table">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
      </div>
      <!-- 赛事编号4 -->
      <div id="match4">
        <div class="match_title"></div>
        <!-- 星期几 -->
        <table class="week_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
        <!-- 场次 -->
        <table class="playnum_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
        <!-- 竞猜选项5 -->
        <div class="match_title"></div>
        <table class="guess_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <!-- 大小分 -->
          <tr class="big_small">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <!-- 让分胜负 -->
          <tr class="win_fail">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <!-- 胜负 -->
          <tr class="outcome_table">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
      </div>
      <!-- 赛事编号5 -->
      <div id="match5">
        <div class="match_title"></div>
        <!-- 星期几 -->
        <table class="week_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
        <!-- 场次 -->
        <table class="playnum_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
        <!-- 竞猜选项5 -->
        <div class="match_title"></div>
        <table class="guess_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <!-- 大小分 -->
          <tr class="big_small">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <!-- 让分胜负 -->
          <tr class="win_fail">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <!-- 胜负 -->
          <tr class="outcome_table">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
      </div>
      <!-- 赛事编号6 -->
      <div id="match6">
        <div class="match_title"></div>
        <!-- 星期几 -->
        <table class="week_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
        <!-- 场次 -->
        <table class="playnum_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
        <!-- 竞猜选项6 -->
        <div class="match_title"></div>
        <table class="guess_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <!-- 大小分 -->
          <tr class="big_small">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <!-- 让分胜负 -->
          <tr class="win_fail">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <!-- 胜负 -->
          <tr class="outcome_table">
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
      </div>
    </div>
    <div class='rBottom'>
      <!-- 过关方式 -->
      <div class="pass_method">
        <div class="match_title"></div>
        <table cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
      </div>
      <!-- 倍数 -->
      <div class="multiple">
        <div class="match_title"></div>
        <table class="multiple_table" cellspacing="0" cellpadding="0" style='width:100%;text-align:center;'>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
          <tr>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
            <td><p></p></td>
          </tr>
        </table>
      </div>
    </div>
  </div>

</div>
<div class="over_tip"></div>
<!--endprint-->
<input type="hidden" id="orderNumber" value="${orderNumber}">
</body>
<script>
  var ul = document.getElementsByClassName('lBox')[0];
  for (var i = 0; i < 29; i++){
    var li = document.createElement('li');
    li.setAttribute("class","big_p");
    ul.appendChild(li);
  }
  var url = '${ctx}/cdoptionpass/cdOptionPassController/printAll.do';
  var orderNumber = $("#orderNumber").val();
  var optionNumber = "1010101001111010";
</script>
<script type="text/javascript" src="${ctxStatic}/print/ieCookie.js"></script>
</html>