package com.youge.yogee.common.push;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.mapper.JsonMapper;
import com.youge.yogee.common.utils.StringUtils;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IphonePushUtil {
    private static PushNotificationManager pushManager;

    static {
        pushManager = new PushNotificationManager();
    }

    /**
     * @param userToken Drive Token
     * @param title     推送标题
     * @param content   发送内容
     * @param badge     角标数量值
     * @param datas     透传内容(没有传NULL)
     */
    public static void toUser(List<String> userToken, String title, String content, Integer badge, Map<String, Object> datas) {
        IphonePushUtil iphonePush = new IphonePushUtil();
        iphonePush.pushto(userToken, title, content, badge, datas);
        System.out.println("-------IOS推送成功!-----");
    }


    /***
     *
     * @param userToken Drive Token
     * @param content 发送内容
     * @param badge 角标数量值
     * @param datas 透传内容(没有传NULL)
     */
    public static void toUser(List<String> userToken, String content, Integer badge, Map<String, Object> datas) {
        IphonePushUtil iphonePush = new IphonePushUtil();
        iphonePush.pushto(userToken, "", content, badge, datas);
        System.out.println("-------IOS推送成功!-----");
    }





    private void pushto(List<String> deviceTokens, String title, String content, Integer badge, Map<String, Object> datas) {
        //铃音
        String sound = "default";

        String certificatePath;
        Boolean isTest;
        String ipPath="ipPath";
        if ("http://www.ahqyc.xyz".equals(Global.getConfig(ipPath))) {
            certificatePath = "/home/tomcat/apache-tomcat-8.5.8/webapps/qingyouhuiPush.p12";
            isTest = true;
        } else {
            certificatePath = "D:\\qingyouhuiDevelopePush.p12";
            isTest = false;
        }

        //此处注意导出的证书密码不能为空因为空密码会报错
        String certificatePassword = "qingyouhui";

        try {
            PushNotificationPayload payLoad = new PushNotificationPayload();
            if (StringUtils.isEmpty(title)) {
                // 消息内容
                payLoad.addAlert(content);
            }else {
                payLoad.addCustomAlertTitle(title);
                payLoad.addCustomAlertBody(content);
            }
            // iphone应用图标上小红圈上的数值
            payLoad.addBadge(badge);
            if (null != datas) {
                payLoad.addCustomDictionary("datas", JsonMapper.toJsonString(datas));
            }
            if (!StringUtils.isBlank(sound)) {
                //铃音
                payLoad.addSound(sound);
            }

            //isTest    true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, isTest));
            List<PushedNotification> notifications = new ArrayList<>();
            // 发送push消息
            if (deviceTokens.size() == 1) {
                Device device = new BasicDevice();
                device.setToken(deviceTokens.get(0));
                PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
                notifications.add(notification);
            } else {
                List<Device> device = new ArrayList<>();
                for (String token : deviceTokens) {
                    device.add(new BasicDevice(token));
                }
                notifications = pushManager.sendNotifications(payLoad, device);
            }
            List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
            List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
            int failed = failedNotifications.size();
            int successful = successfulNotifications.size();
            pushManager.stopConnection();
            System.out.println("-------成功"+successful+"条-----");
            System.out.println("-------失败"+failed+"条-----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        toUser("0e958c85774c0f35a354a21a7f74d5466dfe68a9cb896b677e15fd487f4e5651","您申请的活动没有通过审核",1,null);
//    }
}
