package com.youge.yogee.interfaces.lottery.user;

import com.google.common.collect.Maps;
import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.util;
import com.youge.yogee.interfaces.yeepay.YeepayService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.crecord.entity.CdRecordRecharge;
import com.youge.yogee.modules.crecord.service.CdRecordRechargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("${frontPath}")
public class UserRechargeInterface {
    private static final Logger logger = LoggerFactory.getLogger(UserRechargeInterface.class);
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdRecordRechargeService cdRecordRechargeService;

    /**
     * 用户充值获取易宝支付地址
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getRechargeUrl", method = RequestMethod.POST)
    @ResponseBody
    public String getRechargeUrl(HttpServletRequest request) {
        logger.info("getRechargeUrl---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map dataMap = Maps.newHashMap();
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空!");
            return HttpResultUtil.errorJson("uid为空!");
        }
        String price = (String) jsonData.get("price");
        if (StringUtils.isEmpty(price)) {
            logger.error("price为空!");
            return HttpResultUtil.errorJson("price为空!");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        //充值单号
        String orderNum = util.genOrderNo("", util.getFourRandom());
        //请求时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date today = new Date();
        String todayStr = sdf.format(today);
        //回调地址
        String notifyUrl = Global.getConfig("callback.url");
        //充值描述
        String goodsParamExt = "{\"goodsName\":\"" + "用户充值" + "\",\"goodsDesc\":\"" + "用户充值" + "\"}";
        //经营主体信息
        String industryParamExt = "{\"bizSource\":\"" + "" + "\",\"bizEntity\":\"" + "凯旋彩票" + "\"}";
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderNum); //充值单号
        params.put("orderAmount", price);//充值金额
        params.put("timeoutExpress", "");//订单有效期
        params.put("requestDate", todayStr);//请求时间
        params.put("redirectUrl", "");//页面回调地址
        params.put("notifyUrl", notifyUrl);//服务器回调地址
        params.put("goodsParamExt", goodsParamExt);//支付描述
        params.put("paymentParamExt", "");//扩展参数
        params.put("industryParamExt", industryParamExt);//经营主体信息
        params.put("memo", "");
        params.put("riskParamExt", "");
        params.put("csUrl", "");
        params.put("fundProcessType", "REAL_TIME");
        params.put("divideDetail", "");
        params.put("divideNotifyUrl", "");
        //System.out.println(params);
        //String url = YeepayService.TRADEORDER_URL;
        //String uri = Global.getConfig("tradeOrderURI");
        String uri = "/rest/v1.0/std/trade/order";
        Map<String, String> result = YeepayService.requestYOP(params, uri, YeepayService.TRADEORDER);
        //dataMap.put("token", result.get("token"));
        String token = result.get("token");//请求的token
        String timestamp = String.valueOf(System.currentTimeMillis());//时间戳
        String userType = "USER_ID";//用户标示类型
        String parentMerchantNo = result.get("parentMerchantNo");
        String merchantNo = result.get("merchantNo");
        //String ext = "{ &quot;appId &quot;:&quot;&quot;,&quot; openId &quot;:&quot;&quot;,&quot; clientId &quot;:&quot;&quot;}";
        //String ext = " {\"appId\":\"" + "" + "\",\"openId\":\"" + "" + "\",\"clientId\":\"" + "" + "\"} ";
        Map map = new HashMap();
        map.put("appId", "");
        map.put("openId", "");
        map.put("clientId", "");
        //String ext = JsonMapper.nonDefaultMapper().toJson(map);

        Map<String, String> param = new HashMap<String, String>();
        param.put("parentMerchantNo", parentMerchantNo);
        param.put("merchantNo", merchantNo);
        param.put("token", token);
        param.put("timestamp", timestamp);
//        param.put("directPayType", "YJZF");
        param.put("directPayType", "");
        param.put("cardType", "");
        param.put("userNo", uid);
        param.put("userType", userType);
        //param.put("ext", ext);
        String url = "";
        try {
            url = YeepayService.getUrl(param);
            dataMap.put("url", url);

        } catch (UnsupportedEncodingException e) {
            dataMap.put("url", "");
            e.printStackTrace();

        }

        CdRecordRecharge crr = new CdRecordRecharge();
        crr.setName("用户充值");
        crr.setUid(uid);//用户id
        crr.setOrderNum(orderNum);//订单号
        crr.setStatus("1");//发起支付
        crr.setPrice(price);
        cdRecordRechargeService.save(crr);

        //return url;
        return HttpResultUtil.successJson(dataMap);
        //return JsonMapper.nonDefaultMapper().toJson(dataMap);
    }


    @RequestMapping(value = "callBack")
    @ResponseBody
    public String getRechargeUrl(HttpServletRequest request, HttpServletResponse response) {
        logger.info("getRechargeUrl---------- Start--------");

        String responseMsg = request.getParameter("response");
        String customerId = request.getParameter("customerIdentification");

        Map<String, String> result = YeepayService.callback(responseMsg);

        String parentMerchantNo = formatString(result.get("parentMerchantNo"));
        String merchantNo = formatString(result.get("merchantNo"));
        String orderId = formatString(result.get("orderId"));
        String uniqueOrderNo = formatString(result.get("uniqueOrderNo"));
        String status = formatString(result.get("status"));
        String orderAmount = formatString(result.get("orderAmount"));
        String payAmount = formatString(result.get("payAmount"));
        String requestDate = formatString(result.get("requestDate"));
        String paySuccessDate = formatString(result.get("paySuccessDate"));
        String backResult = "";
        if ("SUCCESS".equals(status)) {
            CdRecordRecharge crr = cdRecordRechargeService.findByOrderNum(orderId);
            if (crr != null) {
                if ("1".equals(crr.getStatus())) {
                    crr.setPrice(payAmount);
                    crr.setStatus("2");
                    cdRecordRechargeService.save(crr);
                    String uid = crr.getUid();
                    CdLotteryUser clu = cdLotteryUserService.get(uid);
                    BigDecimal balance = clu.getBalance();
                    BigDecimal newBalance = balance.add(new BigDecimal(payAmount));
                    BigDecimal totalMoney = clu.getTotalMoney();
                    BigDecimal newTotalMoney = totalMoney.add(new BigDecimal(payAmount));
                    clu.setBalance(newBalance);
                    clu.setTotalMoney(newTotalMoney);
                    String memberLevel = getMemberLevel(newTotalMoney);
                    if (StringUtils.isNotEmpty(memberLevel)) {
                        clu.setMemberLevel(memberLevel);
                    }
                    cdLotteryUserService.save(clu);
                    backResult = "SUCCESS";
                }
            }
        }
        return backResult;
    }


    private String formatString(String text) {
        return text == null ? "" : text.trim();
    }

    private String getMemberLevel(BigDecimal totalMoney) {
        String memLevel = "";
        if (totalMoney.compareTo(new BigDecimal("2.00")) >= 0) {
            memLevel = "1";
        }
        if (totalMoney.compareTo(new BigDecimal("100.00")) >= 0) {
            memLevel = "2";
        }
        if (totalMoney.compareTo(new BigDecimal("500.00")) >= 0) {
            memLevel = "3";
        }
        if (totalMoney.compareTo(new BigDecimal("1000.00")) >= 0) {
            memLevel = "4";
        }
        if (totalMoney.compareTo(new BigDecimal("5000.00")) >= 0) {
            memLevel = "5";
        }
        if (totalMoney.compareTo(new BigDecimal("10000.00")) >= 0) {
            memLevel = "6";
        }
        if (totalMoney.compareTo(new BigDecimal("100000.00")) >= 0) {
            memLevel = "7";
        }
        if (totalMoney.compareTo(new BigDecimal("200000.00")) >= 0) {
            memLevel = "8";
        }
        if (totalMoney.compareTo(new BigDecimal("500000.00")) >= 0) {
            memLevel = "9";
        }
        if (totalMoney.compareTo(new BigDecimal("1000000.00")) >= 0) {
            memLevel = "10";
        }
        return memLevel;

    }

}
