<%@ page contentType="text/html;charset=UTF-8" %>
<%--swiper--%>
<div id='register' style="height: 50px;overflow: hidden; position: absolute;bottom: 25px; left: 185px">
</div>
<div id="recharge" style="height: 50px;overflow: hidden; position: absolute;bottom: 25px; right: 185px">
</div>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.0.2/css/swiper.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.0.2/js/swiper.min.js"></script>

<script type="text/javascript">

    var d = setInterval(function () {
        $.ajax({
            url: "${ctx}/erp/erpOrder/indexa",
            data: {

            },
            success: function (data) {

                var registdata="<div class='swiper-container'>" +
                    "<div  class='swiper-wrapper'>";
                var rechargedata="<div class='swiper-container'>" +
                    "<div  class='swiper-wrapper'>";
                //zhu ce ji lu
                var registerList = data.registerList;
                var rechargeList = data.rechargeList; // chong zhi ji lu

                for (var i=0;i<registerList.length;i++){
                    registdata+= "<div class='swiper-slide '>销售员："+registerList[i].salename+","+
                        "客户："+registerList[i].uname+",开户时间："+registerList[i].createdate+"</div>";
                }
                registdata += "</div></div>";
                for (var i=0;i<rechargeList.length;i++){
                    rechargedata+="<div class='swiper-slide '>销售员："+rechargeList[i].salename+","+
                        "客户："+rechargeList[i].uname+",充值金额："+rechargeList[i].money+",充值时间："+rechargeList[i].createdate+"</div>";
                }
                rechargedata += "</div></div>";
//                        $(".swiper-slide").find();
//                        $(".swiper-slide").html(data);
                $("#register").html(registdata);
                $("#recharge").html(rechargedata);
                var mySwiper = new Swiper('.swiper-container', {
                    autoplay: {
                        delay: 1500,//1秒切换一次
                    },//可选选项，自动滑动
                    direction : 'vertical',
                    loop : true
                })
            }
        });
    },2000);
</script>