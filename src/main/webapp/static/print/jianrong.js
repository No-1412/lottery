if (!document.querySelectorAll) {
    document.querySelectorAll = function (selectors) {
        var style = document.createElement('style'), elements = [], element;
        document.documentElement.firstChild.appendChild(style);
        document._qsa = [];

        style.styleSheet.cssText = selectors + '{x-qsa:expression(document._qsa && document._qsa.push(this))}';
        window.scrollBy(0, 0);
        style.parentNode.removeChild(style);

        while (document._qsa.length) {
            element = document._qsa.shift();
            element.style.removeAttribute('x-qsa');
            elements.push(element);
        }
        document._qsa = null;
        return elements;
    };
}

if (!document.querySelector) {
    document.querySelector = function (selectors) {
        var elements = document.querySelectorAll(selectors);
        return (elements.length) ? elements[0] : null;
    };
}

// 用于在IE6和IE7浏览器中，支持Element.querySelectorAll方法
var qsaWorker = (function () {
    var idAllocator = 10000;

    function qsaWorkerShim(element, selector) {
        var needsID = element.id === "";
        if (needsID) {
            ++idAllocator;
            element.id = "__qsa" + idAllocator;
        }
        try {
            return document.querySelectorAll("#" + element.id + " " + selector);
        }
        finally {
            if (needsID) {
                element.id = "";
            }
        }
    }

    function qsaWorkerWrap(element, selector) {
        return element.querySelectorAll(selector);
    }

    // Return the one this browser wants to use
    return document.createElement('div').querySelectorAll ? qsaWorkerWrap : qsaWorkerShim;
})();

//解决ie浏览器不能使用sessionStorage
var storageObj = window.sessionStorage || new cookieStorage(); 
function cookieStorage(){
    //设置cookie
    this.setItem = function(name, value){   

        /* iDay 表示过期时间   
    
        cookie中 = 号表示添加，不是赋值 */   
    
        // var oDate=new Date();   
    
        // oDate.setDate(oDate.getDate()+iDay);       
        document.cookie=name+'='+value;
    
    }
    //获取cookie
    this.getItem=function(name){
        /* 获取浏览器所有cookie将其拆分成数组 */   
    
        var arr=document.cookie.split('; ');  
        
        for(var i=0;i<arr.length;i++)    {
    
            /* 将cookie名称和值拆分进行判断 */       
    
            var arr2=arr[i].split('=');               
    
            if(arr2[0]==name){           
    
                return arr2[1];       
    
            }   
    
        }       
    
        return '';
    
    }
}

