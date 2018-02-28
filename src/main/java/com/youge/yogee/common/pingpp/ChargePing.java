package com.youge.yogee.common.pingpp;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;
import com.youge.yogee.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ab on 2017/11/14.
 */
public class ChargePing {

    private static Logger logger = LoggerFactory.getLogger(ChargePing.class);
    private static boolean isDebugLogger = logger.isDebugEnabled();

    /**
     * pingpp 管理平台对应的 API key sk_test_anb1SSmbD4uLOWnT0Kvnv1eT
     */

    public static String apiKey = Global.getConfig("pingpp.api.key");

    /**
     * pingpp 管理平台对应的应用 ID
     */

    public static String appId = Global.getConfig("pingpp.api.id");


    public static void main(String[] args) {
        Pingpp.apiKey = apiKey;
        ChargePing ce = new ChargePing();
//        System.out.println("---------创建 charge");
//         Charge charge = ce.charge();
//          System.out.println("---------查询 charge");
//          ce.retrieve(charge.getId());
//            System.out.println("---------查询 charge列表");
//            ce.all();
    }


    /**
     * 查询 Charge
     * <p/>
     * 该接口根据 charge Id 查询对应的 charge 。
     * 参考文档：https://pingxx.com/document/api#api-c-inquiry
     * <p/>
     * 该接口可以传递一个 expand ， 返回的 charge 中的 app 会变成 app 对象。
     * 参考文档： https://pingxx.com/document/api#api-expanding
     *
     * @param id
     */

    public static Charge retrieve(String id) {
        try {
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
            Map<String, Object> param = new HashMap<String, Object>();
            List<String> expande = new ArrayList<String>();
            expande.add("app");
            param.put("expand", expande);
            Charge charge = Charge.retrieve(id, param);
            return charge;

        } catch (PingppException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 分页查询Charge
     * <p/>
     * 该接口为批量查询接口，默认一次查询10条。
     * 用户可以通过添加 limit 参数自行设置查询数目，最多一次不能超过 100 条。
     * <p/>
     * 该接口同样可以使用 expand 参数。
     *
     * @return
     */

//    public ChargeCollection all() {
//        ChargeCollection chargeCollection = null;
//        Map<String, Object> chargeParams = new HashMap<String, Object>();
//        chargeParams.put("limit", 10);
//
////增加此处设施，刻意获取app expande
////        List<String> expande = new ArrayList<String>();
////        expande.add("app");
////        chargeParams.put("expand", expande);
//
//        try {
//            chargeCollection = Charge.all(chargeParams);
//            System.out.println(chargeCollection);
//        } catch (AuthenticationException e) {
//            e.printStackTrace();
//        } catch (InvalidRequestException e) {
//            e.printStackTrace();
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//        } catch (APIException e) {
//            e.printStackTrace();
//        } catch (ChannelException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return chargeCollection;
//    }



    public Charge postComment(Integer amount ,String channel,String subject,String body,String uuid,String userid,String str,String msg) {

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
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>();

        //订单总金额, 单位为对应币种的最小货币单位，
        // 例如：人民币为分（如订单总金额为 1 元，此处请填 100）。
        chargeMap.put("amount", amount);
        //三位 ISO 货币代码，目前仅支持人民币 cny。
        chargeMap.put("currency", "cny");
        //商品的标题
        chargeMap.put("subject", subject);
        //商品的描述信息，该参数最长为 128 个 Unicode 字符，
        chargeMap.put("body", body);
        //商户订单号order_no
        chargeMap.put("order_no", uuid);

        Map<String, String> initialMetadata = new HashMap<String, String>();
        initialMetadata.put("user_id",userid);
        initialMetadata.put("phone_number",msg);//phone_number：代替区分是普通充值，还是注册充值，回调时候处理不同的业务
        chargeMap.put("metadata", initialMetadata);

        chargeMap.put("channel", channel);
        chargeMap.put("client_ip", "127.0.0.1");

        Map<String, String> app = new HashMap<String, String>();
        app.put("id",appId);
        chargeMap.put("app", app);

        if("5".equals(str)){
            Map<String, String> extra = new HashMap<String, String>();
            extra.put("success_url","http://www.shouyuan999.com/index.html#/login");
            chargeMap.put("extra", extra);
        }
        if("6".equals(str)){
            Map<String, String> extra = new HashMap<String, String>();
            extra.put("success_url","http://www.shouyuan999.com/index.html#/home");
            chargeMap.put("extra", extra);
        }

        try {
            //发起交易请求
            charge = Charge.create(chargeMap);

            System.out.println("---------------charge:"+charge+"-----------------");
        } catch (PingppException e) {
            e.printStackTrace();
        }
        return charge;
    }



}
