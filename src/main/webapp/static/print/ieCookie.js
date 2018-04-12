$('#print').on('click',function(){
    printpage();
});
jQuery.support.cors = true;
//访问接口

$.ajax({
    //url:'http://192.168.52.233:8088/f/cdoptionpass/cdOptionPassController/printAll.do',
    url:url,
    contentType: "application/x-www-form-urlencoded",
    type:'get', 
    data:{
        // orderNumber : 'ZCG152271580503756136',
        orderNumber : orderNumber,
        // optionNumber : '010000110101' //足球比分3关
        // optionNumber : '100011001110' //足球胜平负3关
        // optionNumber : '010011000001' //足球半全场3关
        // optionNumber : '110010011110' //足球总进球3关
        // optionNumber : '1010101101111000' //足球3关
        // optionNumber : '1010101001110100' //足球6关
        // optionNumber : '000111000011' //足球总进球6关
        // optionNumber : '1010101110000101' //足球8关

        // optionNumber : '1010101011010010' //篮球3关
        // optionNumber : '1010101001111010' //篮球6关
        // optionNumber : '1010101011000110' //篮球8关
        //optionNumber : '110010101111' //篮球大小分8关
        // optionNumber : '010010101111' //篮球让分胜负8关
        // optionNumber : '011111001110' //篮球胜负3关
        optionNumber:optionNumber
    },   
    async : false, //默认为true 异步    
    error:function(XMLHttpRequest, textStatus, errorThrown){  
        
    } ,    
    success:function(data){
        var res = JSON.parse(data);
        console.log(res)
        var pass_type = document.querySelectorAll('.pass_type p');
        if(pass_type.length){
            var lists = res.leixing;
            addBlackPot(pass_type,lists);
        }
        var len = res.saishiList.length;
        if(len >= 1){
            var match01 = document.querySelectorAll("#match1 p");
            var list01 = res.saishiList[0];
            addBlackPot(match01,list01);
            if(len >= 2){
                var match02 = document.querySelectorAll("#match2 p");
                var list02 = res.saishiList[1];
                addBlackPot(match02,list02);
                if(len >= 3){
                    var match03 = document.querySelectorAll("#match3 p");
                    var list03 = res.saishiList[2];
                    addBlackPot(match03,list03);
                    if(len >= 4){
                        var match04 = document.querySelectorAll("#match4 p");
                        var list04 = res.saishiList[3];
                        addBlackPot(match04,list04);
                        if(len >= 5){
                            var match05 = document.querySelectorAll("#match5 p");
                            var list05 = res.saishiList[4];
                            addBlackPot(match05,list05);
                            if(len >= 6){
                                var match06 = document.querySelectorAll("#match6 p");
                                var list06 = res.saishiList[5];
                                addBlackPot(match06,list06);
                                if(len >= 7){
                                    var match07 = document.querySelectorAll("#match7 p");
                                    var list07 = res.saishiList[6];
                                    addBlackPot(match07,list07);
                                    if(len == 8){
                                        var match08 = document.querySelectorAll("#match8 p");
                                        var list08 = res.saishiList[7];
                                        addBlackPot(match08,list08);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        var pass_method = document.querySelectorAll(".pass_method p");
        var fangshi = res.fangshi;
        var multiple = document.querySelectorAll(".multiple p");
        var beishu = res.beishu;
        addBlackPot(pass_method,fangshi);
        addBlackPot(multiple,beishu);
        
    }  
});
function addBlackPot(match,list){
    for(var i = 0;i<list.length;i++){
        if(list[i]==1){
            match[i].setAttribute("class","small_p");
        }
    }
}
function printpage(){
    bdhtml=window.document.body.innerHTML;   
    sprnstr="<!--startprint-->";   
    eprnstr="<!--endprint-->";   
    prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);   
    prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));   
    window.document.body.innerHTML=prnhtml;  
    window.print();
    // window.close();
}