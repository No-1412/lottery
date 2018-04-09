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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
        String notifyUrl = "http://104.224.186.152:8080/FengTools_war/ServiceNotice";
        //充值描述
        String goodsParamExt = "{\"goodsName\":\"" + "用户充值" + "\",\"goodsDesc\":\"" + "用户充值" + "\"}";
        //经营主体信息
        String industryParamExt = "{\"bizSource\":\"" + "" + "\",\"bizEntity\":\"" + "凯旋彩票" + "\"}";
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderNum); //充值单号
        params.put("orderAmount", price);//充值金额
        params.put("timeoutExpress", "");//订单有效期
        params.put("requestDate", todayStr);//请求时间
        params.put("redirectUrl", "");//服务器回调地址
        params.put("notifyUrl", notifyUrl);//回调地址
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
        dataMap.put("token", result.get("token"));
        return HttpResultUtil.successJson(dataMap);
    }


}
