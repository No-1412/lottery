package com.youge.yogee.common.pingpp;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Transfer;
import com.pingplusplus.model.TransferCollection;
import com.youge.yogee.common.config.Global;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 该实例演示如何使用 Ping++ 进行企业转账。
 *
 * 开发者需要填写 apiKey ，openid 和 appId ,
 *
 * apiKey 有 TestKey 和 LiveKey 两种。
 *
 * TestKey 模式下不会产生真实的交易。
 *
 * openid 是发送红包的对象在公共平台下的 openid ,获得 openid 的方法可以参考微信文档：http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
 *
 */
public class TransferPing {

    public static String apiKey = Global.getConfig("pingpp.api.key");
    public static String appId = Global.getConfig("pingpp.api.id");
    /**
     * 接收者的 openid
     */
    public static String openid ="USER_OPENID";// 用户在商户微信公众号下的唯一标识，获取方式可参考 WxPubOAuthExample.java

    public static void runDemos(String appId) {

//        TransferExample transferExample = new TransferExample(appId);
//        System.out.println("------- 创建 Transfer -------");
//        Transfer transfer = transferExample.transfer();
//        System.out.println("------- 查询 Transfer -------");
//        transferExample.retrieve(transfer.getId());
//        System.out.println("------- 查询 Transfer 列表 -------");
//        transferExample.list();

    }

//    TransferExample(String appId) {
//        this.appId = appId;
//    }

    /**
     * 创建企业转账
     *
     * 创建企业转账需要传递一个 map 给 Transfer.create();
     * map 填写的具体介绍可以参考：https://www.pingxx.com/api#api-t-new
     *
     * @return
     */
    public static Transfer transfer(String channel,String openid,Integer momey,String description, String userid,
                                    String card_number,String userName,String bank_code,String uuid) {

        Pingpp.apiKey = apiKey;
        Pingpp.privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIICXAIBAAKBgQDFvX0R3Pcz9vmP7JJuzB1Te4ttrdU4KKmhKUfTRU2Wt6UI6IvI\n" +
                "yBH6caepyN6MdRJvyffz0N0K1SrCqAkik/kC5tKTGp6SY/ogO0Qq0aeIL2N2U8RI\n" +
                "5OrJlY3MShKHVlmniRz+jPHqNvhcaDi17+7ErVz1h7rMb5cqR0Rgz3XBaQIDAQAB\n" +
                "AoGBAILPGV+pxzZvGSSmoS/+gx/oyJGjvztJWv2r60HNxMgBcIF2JJKMF6iPaxSc\n" +
                "Nuy/vZSKDScTckZqQcFHm9NuA/lHsX8+UBfyRlqc6HJxuxja6KnprUEwS4YIzSYQ\n" +
                "24awBXiRuOqGU+8Oez5yF9p9YS4JSrGlB9uZpeVZW9C2Ze5pAkEA5VGUezaaI2JI\n" +
                "Cm0fESpEkltKnAzLLIm91W3bjDfR6z8+E2AKhDLunkktGxAHS7767eD+kerXPVwk\n" +
                "tD2bbz6hawJBANy/Uip+Vfvw96sKhEHwT67VAY4Igz/2b/ROGX89zpZupb3dAOM8\n" +
                "TLoaqOwRtXLDypVs5tdtxI4E2DSl2RLAWXsCQG0Y+cYAvSY2XMWxEoWtEUB79Cv3\n" +
                "njWLNmgywq+b4t/Dj7nSCE7KmGFvuOjWIFJ2ApVnZFVxX4NOshuZ77fmGQ8CQApw\n" +
                "NZdyz+gQ9fUtkyF3Fp2CzwcDWgl9Qr//rW754jn003uN5svX/xzRw+kd0UGzBnJY\n" +
                "avYG58N7cSnUKdacJ98CQEEVPJp5bwZYehJRmt7RUIdwUtiOScbt8J2gCoDWpzlW\n" +
                "AYhUxxo6IhdRU3jVuiD+tpLYjzpf/UwCXUO3ZWhJmyU=\n" +
                "-----END RSA PRIVATE KEY-----";
        Transfer transfer = null;
//        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        Map<String, Object> transferMap = new HashMap<String, Object>();
        transferMap.put("channel", channel);
        transferMap.put("amount", momey);
        transferMap.put("type", "b2c");
        transferMap.put("order_no", uuid);
        transferMap.put("currency", "cny");
//        transferMap.put("user_fee", 0);//手续费暂时为0
//        transferMap.put("user", userid);//user对象的id
//        transferMap.put("recipient", openid);
        transferMap.put("description", description);
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appId);
        transferMap.put("app", app);

        Map<String, Object> extra = new HashMap<String, Object>();
        extra.put("card_number", card_number);
        extra.put("user_name", userName);
        extra.put("open_bank", userid);
        extra.put("open_bank_code", bank_code);

        transferMap.put("extra", extra);

        try {
            transfer = Transfer.create(transferMap);
            System.out.println("------------------transfer:"+transfer+"---------------------");
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
        return transfer;
    }

    /**
     * 根据 ID 查询
     *
     * 根据 ID 查询企业转账记录。
     * 参考文档：https://www.pingxx.com/api#api-t-inquiry
     * @param id
     */
    public static Transfer retrieve(String id) {
        Transfer transfer=null;
        Map<String, Object> param = new HashMap<String, Object>();
        try {
             transfer = Transfer.retrieve(id, param);
            System.out.println(transfer);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
        return transfer;
    }

    /**
     * 批量查询
     *
     * 批量查询企业转账记录，默认一次查询 10 条，用户可以使用 limit 自定义查询数目，但是最多不超过 100 条。
     */
    public void list() {
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("limit", 3);

        try {
            TransferCollection transferCollection = Transfer.list(parm);
            System.out.println(transferCollection);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
    }

}
