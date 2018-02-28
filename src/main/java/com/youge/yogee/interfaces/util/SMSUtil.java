package com.youge.yogee.interfaces.util;

import com.youge.yogee.common.cloopen.sdk.CCPRestSDK;
import com.youge.yogee.common.config.Global;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Administrator on 2016/12/29 0029.
 */
@Service
public class SMSUtil {


    /**
     * 发送短信
     * @param telephone 手机号
     * @param template 短信模板
     * @param code 验证码
     * @param minute 有效时间
     */
    public static boolean sendSMS(String telephone, String template, String code, String minute){

        HashMap<String, Object> result = null;
        boolean flag = false;

        CCPRestSDK restAPI = new CCPRestSDK();

        // 各项配置详见SDKTestSendTemplateSMS
        restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount(Global.getConfig("cloopen.account"), Global.getConfig("cloopen.token"));// 初始化主帐号和主帐号TOKEN
        restAPI.setAppId(Global.getConfig("cloopen.apply.id"));// 应用ID
        result = restAPI.sendTemplateSMS(telephone, "161317", new String[]{code, minute+"分钟"});

//        System.out.println("SDKTestSendTemplateSMS result=" + result);

        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
//                System.out.println(key +" = "+object);
            }
            flag = true;
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            flag = false;
        }

        return flag;
    }

    public static void main(String[] args) {
//        System.out.println((int)((Math.random()*9+1)*100000));
//        SMSUtil.sendSMS("13144868088", "1", "66666","5");
    }

}