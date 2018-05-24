<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>竞彩足球订单管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
        var testajax = function(){
            $.ajax({
                url:'http://192.168.51.229:8088/f/cdoptionpass/cdOptionPassController/printFootballSingleOrder',
                type:'get',
                data:{
                    id : 'ca612b266dbf40c2a7d81f0f55df698d',
                    optionNumber : '100011001110'
                },

                async : false, //默认为true 异步
                error:function(data, XMLHttpRequest, textStatus, errorThrown){

                    /*alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);*/
                    alert(0)
                } ,
                success:function(data){
                    // var res = JSON.parse(data);

                    // var match01 = document.querySelectorAll("#match1 p");
                    // var list01 = res.saishiList[0];
                    // console.log(match01)
                    // console.log(list01);
                    // addBlackPot(match01,list01);
                    alert(1)
                }
            });
        }
    </script>
</head>
<body>
${caipiao}
<button onclick="testajax()">testw</button>
</body>
</html>
