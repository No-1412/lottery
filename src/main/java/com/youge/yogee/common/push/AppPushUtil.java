package com.youge.yogee.common.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 个推
 * Created by Administrator on 2015/10/22.
 */
public class AppPushUtil {


    static String appId = "BcEEEQunN87mmPPCNQpsc6";
    static String appkey = "GJzhoayflq7VCTAAUVwoC4";
    static String masterSecret = "jzIVS4tjtf8bEqvLfXZNi6";

    static String url = "http://sdk.open.api.igexin.com/apiex.htm"; //单
    static String hostToApp = "http://sdk.open.api.igexin.com/serviceex"; //多


    public static void main(String[] args) throws Exception {
        pushToSingle("302999baff2044229f7ee892a22a155c","内容","内容");
    }


    //单人推
    public static void pushToSingle(String userid, String title, String content) {

        IGtPush push = new IGtPush(appkey, masterSecret);
        LinkTemplate linkTemplate = linkTemplate(title, content);
//        TransmissionTemplate template = getTemplate(title,content);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(linkTemplate);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setAlias(userid);
        IPushResult ret;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }

    }

    //多人
    public static void pushToAPP(String title, String content) {


        IGtPush push = new IGtPush(hostToApp, appkey, masterSecret);
        LinkTemplate linkTemplate = linkTemplate(title, content);
//        TransmissionTemplate template = getTemplate(title,content);
        AppMessage message = new AppMessage();
        message.setData(linkTemplate);
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 1000 * 3600);
        List<String> appIdList = new ArrayList<>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);

        IPushResult ret = push.pushMessageToApp(message, "HotToApp");
        System.out.println(ret.getResponse().toString());
    }

    //正常推送模版
    public static LinkTemplate linkTemplate(String text , String text1) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 设置通知栏标题与内容
        template.setTitle(text);//请输入通知栏标题
        template.setText(text1);//请输入通知栏内容
        // 配置通知栏图标
//        template.setLogo("icon.png");
        // 配置通知栏网络图标，填写图标URL地址
//        template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 设置打开的网址地址
        template.setUrl("http://www.baidu.com");
        return template;
    }
    public static TransmissionTemplate getTemplate() {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        template.setTransmissionContent("订单1214rerd13222222222===&0&008af4fb46014dd2815cfdbe9745f82e&1&1");
        template.setTransmissionType(2);
        APNPayload payload = new APNPayload();
        payload.setContentAvailable(1);   //        setContentAvailable 	推送直接带有透传数据
        payload.setSound("default");      //        setSound 	通知铃声文件名
//        payload.setCategory("$由客户端定义");
        payload.setCategory("333333333");//         setCategory 	在客户端通知栏触发特定的action和button显示
        //简单模式APNPayload.SimpleMsg    //         addCustomMsg 	增加自定义的数据
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));//        setAlertMsg 	通知消息体
        //字典模式使用下者
//        payload.setAlertMsg(getDictionaryAlertMsgs());
//        template.setAPNInfo(payload);
        return template;
    }





    public static TransmissionTemplate getTemplate(String text, String text1) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        template.setTransmissionContent(text);
        template.setTransmissionType(2);

        APNPayload payload = new APNPayload();
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setCategory("333333333");
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(text));
        template.setAPNInfo(payload);

        //payload.addCustomMsg("key", text);
        //字典模式使用下???
        //payload.setAlertMsg(getDictionaryAlertMsg());

        return template;
    }





    public static TransmissionTemplate transmissionTemplateDemo(Map dateMap) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent(dateMap.toString());
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }


}
