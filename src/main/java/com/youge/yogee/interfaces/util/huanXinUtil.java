package com.youge.yogee.interfaces.util;


import com.youge.yogee.common.mapper.JsonMapper;
import com.youge.yogee.common.utils.HttpKit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/7/22.
 */
public class huanXinUtil {



    /**
     *  环信
     *  https://console.easemob.com/
     *
     *  账号: ccyouge@163.com   密码:1q2w3e4r
     *
     *  client_id: YXA6sc4aELgQEeWR2rkptBP5HA
     *  client_secret ：YXA6fkKRltN269NEADCFKmzxWwr0Kro
     *
     * @return
     */
    public static String getToken (){
        String json = "{\"grant_type\":\"client_credentials\",\"client_id\":\"YXA6sc4aELgQEeWR2rkptBP5HA\",\"client_secret\":\"YXA6fkKRltN269NEADCFKmzxWwr0Kro\"}";
        String jsonResult = HttpKit.post("http://a1.easemob.com/youge/zhaoshi/token", json);
        Map<String ,String> access_token = JsonMapper.getInstance().fromJson(jsonResult, Map.class );
        String toke = access_token.get("access_token");
        return toke;
    }


    public static void postUser (String toke,String username,String password){
        Map<String, String> map = new HashMap<String, String>();
        map.put("Authorization","Bearer "+toke+"");
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put("username",username);
        userMap.put("password",password);
        //userMap.put("nickname",nickname);

        String userjson = JsonMapper.getInstance().toJson(userMap);
        String jsonResult = HttpKit.post("http://a1.easemob.com/youge/zhaoshi/users", userjson,map);
        System.out.println(jsonResult);
    }
}
