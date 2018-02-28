package com.youge.yogee.interfaces.util;

import com.youge.yogee.common.config.Global;

import java.util.HashMap;
import java.util.Map;

public class SubImage {
    //截取
    public static Map getEditor(String editor) {
        Map<String,String> map = new HashMap<String,String>();

        int beginIndex0 = editor.indexOf("<img");

        while (beginIndex0 != -1) {
            String str0 = editor.substring(beginIndex0, editor.length());
            //去除&quot;位置
            int endIndex1 = str0.indexOf("/");
            // 地址开始位置
            int beginIndex = beginIndex0 + endIndex1;
            //获取图片地址之前的内容
            String beginstr = editor.substring(0, beginIndex);
            //图片地址到最后位置
            String str1 = editor.substring(beginIndex, editor.length());
            //结束位置
            int endIndex = str1.indexOf("style");
            //获取img标签结束以后的内容
            String endstr = editor.substring(beginIndex + endIndex);
            //截取
            String str = editor.substring(beginIndex, beginIndex + endIndex);
            String replace = Global.getConfig("domain.name")  + str;
            editor = beginstr + replace + endstr;
            beginIndex0 = editor.indexOf("<img", beginIndex + endIndex);
        }
        map.put("editor", editor);
        return map;
    }

}
