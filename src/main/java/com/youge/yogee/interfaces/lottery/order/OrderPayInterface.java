package com.youge.yogee.interfaces.lottery.order;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballSingleOrderService;
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNineOrder;
import com.youge.yogee.modules.cchoosenine.service.CdChooseNineOrderService;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveOrder;
import com.youge.yogee.modules.cfiveawards.service.CdFiveOrderService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.clottoreward.entity.CdLottoOrder;
import com.youge.yogee.modules.clottoreward.service.CdLottoOrderService;
import com.youge.yogee.modules.corder.service.CdOrderService;
import com.youge.yogee.modules.crecord.entity.CdRecordRebate;
import com.youge.yogee.modules.crecord.service.CdRecordRebateService;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFailOrder;
import com.youge.yogee.modules.csuccessfail.service.CdSuccessFailOrderService;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeOrder;
import com.youge.yogee.modules.cthreeawards.service.CdThreeOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("${frontPath}")
public class OrderPayInterface {
    private static final Logger logger = LoggerFactory.getLogger(OrderPayInterface.class);
    @Autowired
    private CdBasketballSingleOrderService cdBasketballSingleOrderService;  //篮球单关
    @Autowired
    private CdBasketballFollowOrderService cdBasketballFollowOrderService;//篮球串关
    @Autowired
    private CdFootballFollowOrderService cdFootballFollowOrderService;//足球串关
    @Autowired
    private CdFootballSingleOrderService cdFootballSingleOrderService;//足球单关
    @Autowired
    private CdThreeOrderService cdThreeOrderService;
    @Autowired
    private CdFiveOrderService cdFiveOrderService;
    @Autowired
    private CdLottoOrderService cdLottoOrderService;
    @Autowired
    private CdChooseNineOrderService cdChooseNineOrderService;
    @Autowired
    private CdSuccessFailOrderService cdSuccessFailOrderService;
    @Autowired
    private CdOrderService cdOrderService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdRecordRebateService cdRecordRebateService;

    /**
     * 彩票订单支付
     */
    @RequestMapping(value = "orderPay", method = RequestMethod.POST)
    @ResponseBody
    public String orderPay(HttpServletRequest request) throws ParseException {
        logger.info("=orderPay--------Start-------------------");
        logger.debug("interface 请求--orderPay-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }

        String orderNum = (String) jsonData.get("orderNum");
        if (StringUtils.isEmpty(orderNum)) {
            logger.error("orderNum为空");
            return HttpResultUtil.errorJson("orderNum为空");
        }
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户信息错误");
        }
        String balance = clu.getBalance().setScale(2, 2).toString();
        String price = "";
        if (orderNum.startsWith("ZDG")) {
            CdFootballSingleOrder cfs = cdFootballSingleOrderService.findOrderByOrderNum(orderNum);
            price = cfs.getPrice();
            if (canPay(price, balance)) {
                clu = getLeftMoney(price, balance, clu);
                cfs.setStatus("2");//已付款

            }
        } else if (orderNum.startsWith("ZCG")) {
            CdFootballFollowOrder cff = cdFootballFollowOrderService.findOrderByOrderNum(orderNum);
            price = cff.getPrice();
            if (canPay(price, balance)) {

            }
        } else if (orderNum.startsWith("LDG")) {
            CdBasketballSingleOrder cbs = cdBasketballSingleOrderService.findOrderByOrderNum(orderNum);
            price = cbs.getPrice();
            if (canPay(price, balance)) {

            }
        } else if (orderNum.startsWith("LCG")) {
            CdBasketballFollowOrder cbf = cdBasketballFollowOrderService.findOrderByOrderNum(orderNum);
            price = cbf.getPrice();
            if (canPay(price, balance)) {

            }
        } else if (orderNum.startsWith("RXJ")) {
            CdChooseNineOrder ccno = cdChooseNineOrderService.findOrderByOrderNum(orderNum);
            price = ccno.getPrice();
            if (canPay(price, balance)) {

            }
        } else if (orderNum.startsWith("SFC")) {
            CdSuccessFailOrder csfo = cdSuccessFailOrderService.findOrderByOrderNum(orderNum);
            price = csfo.getPrice();
            if (canPay(price, balance)) {

            }
        } else if (orderNum.startsWith("PLS")) {
            CdThreeOrder dto = cdThreeOrderService.findOrderByOrderNum(orderNum);
            price = dto.getPrice();
            if (canPay(price, balance)) {

            }
        } else if (orderNum.startsWith("PLW")) {
            CdFiveOrder cfo = cdFiveOrderService.findOrderByOrderNum(orderNum);
            price = cfo.getPrice();
            if (canPay(price, balance)) {

            }
        } else if (orderNum.startsWith("DLT")) {
            CdLottoOrder clo = cdLottoOrderService.findOrderByOrderNum(orderNum);
            price = clo.getPrice();
            if (canPay(price, balance)) {

            }
        }
        return HttpResultUtil.successJson(map);
    }

    private boolean canPay(String price, String leftMoney) {
        boolean flag = false;
        double priDouble = Double.parseDouble(price);
        double leftDouble = Double.parseDouble(leftMoney);
        if (priDouble >= leftDouble) {
            flag = true;
        }
        return flag;
    }

    private void saveRebate(String price, String uid) {
        double priceDouble = Double.parseDouble(price);
        boolean flag = false;
        String rebate = "";
        if (priceDouble > 1000.0) {

        } else if (priceDouble > 5000.0) {
            flag = true;
            rebate = String.valueOf(priceDouble * 0.02);
        } else if (priceDouble > 10000.0) {
            flag = true;
            rebate = String.valueOf(priceDouble * 0.03);
        }
        if (flag) {
            CdRecordRebate crr = new CdRecordRebate();
            crr.setRebate(rebate);
            crr.setUid(uid);
            cdRecordRebateService.save(crr);
        }
    }

    private CdLotteryUser getLeftMoney(String price, String balance, CdLotteryUser clu) {
        BigDecimal priceBig = new BigDecimal(price);
        BigDecimal balanceBig = new BigDecimal(balance);
        BigDecimal left = balanceBig.subtract(priceBig).setScale(2, 2);
        clu.setBalance(left);//更新余额
        return clu;
    }
}