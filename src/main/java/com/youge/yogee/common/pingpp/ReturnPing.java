package com.youge.yogee.common.pingpp;

import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeRefundCollection;
import com.pingplusplus.model.Refund;
import com.youge.yogee.common.config.Global;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ab on 2017/11/14.
 */
public class ReturnPing {

    private String charge;
    public static String apiKey = Global.getConfig("pingpp.api.key");
    public static String appId = Global.getConfig("pingpp.api.id");


    /**
     * 退款
     *
     * 创建退款，需要先获得 charge ,然后调用 charge.getRefunds().create();
     * 参数具体说明参考：https://www.pingxx.com/api#api-r-new
     *
     * 可以一次退款，也可以分批退款。
     *
     * @param amount
     * @return
     */
    public static Refund refund(Integer amount, Charge charge,String description) {
        if (charge == null) {
            return null;
        }
        Refund refund = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("description", description);
        params.put("amount", amount);// 退款的金额, 单位为对应币种的最小货币单位，例如：人民币为分（如退款金额为 1 元，此处请填 100）。
                                     // 必须小于等于可退款金额，默认为全额退款

        try {
            refund = charge.getRefunds().create(params);
            System.out.println(refund);
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
        return refund;
    }

    /**
     * 查询退款
     *
     * 根据 Id 查询退款记录。需要传递 charge。
     * 参考文档：https://www.pingxx.com/api#api-r-inquiry
     *
     * @param id
     */
    public void retrieve(String id) {
        if (charge == null) {
            return;
        }
        try {
            Refund refund = Refund.retrieve(this.charge, id);
            System.out.println(refund);
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

    /**
     * 分页查询退款
     *
     * 批量查询退款。默认一次 10 条，用户可以通过 limit 自定义查询数目，但是最多不超过 100 条。
     * 参考文档：https://www.pingxx.com/api#api-r-list
     *
     */
    public void all() {
        if (charge == null) {
            return;
        }
        Map<String, Object> refundParams = new HashMap<String, Object>();
        refundParams.put("limit", 3);
        try {
            ChargeRefundCollection refunds = Refund.list(this.charge, refundParams);
            System.out.println(refunds);
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


    public static void runDemos() {

//        String chargeId = "ch_5CWrz5rnz9GS84arXHLiPOqL";

//        RefundsExample refundExample = new RefundsExample(chargeId);
//        System.out.println("------- 创建 refund -------");
//        Refund refund = refundExample.refund(1);
//        System.out.println("------- 查询 refund -------");
//        refundExample.retrieve(refund.getId());
//        System.out.println("------- 查询 refund 列表 -------");
//        refundExample.all();
    }

}
