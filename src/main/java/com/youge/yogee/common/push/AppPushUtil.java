package com.youge.yogee.common.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.APNTemplate;
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
    static String host = "http://sdk.open.api.igexin.com/apiex.htm"; //单
    static String hostToApp = "http://sdk.open.api.igexin.com/serviceex"; //多


    static String devicetoken = "78110b2db35ff75a346cf53ef4b58e738d476dbfcf4728977b7113095ebeeb5f";

    public static void main(String[] args) throws Exception {
        pubshtoSingle("302999baff2044229f7ee892a22a155c","内容","内容");
    }


    //单人推
    public static void pubshtoSingle(String userid,String title,String content) {

        IGtPush push = new IGtPush(appkey, masterSecret);

        TransmissionTemplate template = getTemplate(title,content);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
//        target.setClientId("17d60b4bb6e91679f8df2b1ff2d65f36");//ios
//        target.setClientId("aab81546d109dc440cff8bffeea06c9d");//s6
//        target.setClientId(cid);//zhanghao
        target.setAlias(userid);
        IPushResult ret = null;
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
    public static LinkTemplate linkTemplateDemo(String text , String text1) {
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
    public static void apppushs() throws Exception {
        IGtPush push = new IGtPush(url, appkey, masterSecret);
        APNTemplate t = new APNTemplate();
        APNPayload apnpayload = new APNPayload();
        apnpayload.setSound("");
        //apn高级推送
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        ////通知文本消息标题
        alertMsg.setTitle("aaaaaa");
        //通知文本消息字符串
        alertMsg.setBody("bbbb");
        //对于标题指定执行按钮所使用的Localizable.strings,仅支持IOS8.2以上版本
        alertMsg.setTitleLocKey("ccccc");
        //指定执行按钮所使用的Localizable.strings
        alertMsg.setActionLocKey("ddddd");
        apnpayload.setAlertMsg(alertMsg);

        t.setAPNInfo(apnpayload);
        SingleMessage sm = new SingleMessage();
        sm.setData(t);
        IPushResult ret0 = push.pushAPNMessageToSingle(appId, devicetoken, sm);
        System.out.println(ret0.getResponse());

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
    protected static APNPayload.DictionaryAlertMsg getDictionaryAlertMsgs(){
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody("hello");                          //setBody 	通知文本消息字符串
        alertMsg.setActionLocKey("ActionLockey");	       //setActionLocKey 	(用于多语言支持）指定执行按钮所使用的Localizable.strings
        alertMsg.setLocKey("LocKey");	                   //setLocKey 	(用于多语言支持）指定Localizable.strings文件中相应的key
        alertMsg.addLocArg("loc-args");	                   //addLocArg 	如果loc-key中使用的占位符，则在loc-args中指定各参数
        alertMsg.setLaunchImage("launch-image");	       //setLaunchImage 	指定启动界面图片名
        alertMsg.setTitle("您有新的消息");	                       // IOS8.2以上版本支持	setTitle 	通知标题
        alertMsg.setTitleLocKey("TitleLocKey");	           //setTitleLocKey 	(用于多语言支持）对于标题指定执行按钮所使用的Localizable.strings,仅支持IOS8.2以上版本
        alertMsg.addTitleLocArg("TitleLocArg");	           //addTitleLocArg 	对于标题, 如果loc-key中使用的占位符，则在loc-args中指定各参数,仅支持IOS8.2以上版本
        return alertMsg;
    }
    //多人
    public static void pushtoAPP( String text,String text1) {


        IGtPush push = new IGtPush(hostToApp, appkey, masterSecret);
        //  LinkTemplate template = linkTemplateDemo(title, text, logo, logoUrl, url);
        TransmissionTemplate template = getTemplate(text, text1);

        AppMessage message = new AppMessage();
        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 1000 * 3600);
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);

        IPushResult ret = push.pushMessageToApp(message, "toapp");
        System.out.println(ret.getResponse().toString());
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

    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(){
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody("body");
        alertMsg.setActionLocKey("ActionLockey");
        alertMsg.setLocKey("LocKey");
        alertMsg.addLocArg("loc-args");
        alertMsg.setLaunchImage("launch-image");
        // IOS8.2以上版本支持
        alertMsg.setTitle("Title1111");
        alertMsg.setTitleLocKey("TitleLocKey");
        alertMsg.addTitleLocArg("TitleLocArg");
        return alertMsg;
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




    public static void apnpush() throws Exception {
        IGtPush push = new IGtPush(hostToApp, appkey, masterSecret);

        APNTemplate t = new APNTemplate();
        APNPayload apnpayload = new APNPayload();
        apnpayload.setSound("");
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setTitle("aaaaaa&0&0");
        alertMsg.setBody("bbbb&0&0");
        alertMsg.setTitleLocKey("ccccc&0&0");
        alertMsg.setActionLocKey("ddddd&0&0");
        apnpayload.setAlertMsg(alertMsg);
        t.setAPNInfo(apnpayload);

        ListMessage message = new ListMessage();
        message.setData(t);
        String contentId = push.getAPNContentId(appId, message);
        System.out.println(contentId);
        List<String> dtl = new ArrayList<String>();
        dtl.add(devicetoken);
        System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
        IPushResult ret = push.pushAPNMessageToList(appId, contentId, dtl);
        System.out.println(ret.getResponse());
    }
}
