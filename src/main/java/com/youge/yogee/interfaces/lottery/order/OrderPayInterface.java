package com.youge.yogee.interfaces.lottery.order;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.lottery.util.SelOrderUtil;
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
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.entity.CdOrderCatch;
import com.youge.yogee.modules.corder.service.CdOrderCatchService;
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
    @Autowired
    private CdOrderCatchService cdOrderCatchService;

    /**
     * 彩票订单支付
     */
    @RequestMapping(value = "orderPay", method = RequestMethod.POST)
    @ResponseBody
    public String orderPay(HttpServletRequest request) throws ParseException {
        logger.info("orderPay--------Start-------------------");
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
                //更该订单信息
                cfs.setStatus("2");//已付款
                cfs.setUid(uid);//预约单是写死的 支付后更新
                cdFootballSingleOrderService.save(cfs);
                saveAllChange(price, balance, clu, orderNum, "1", "1");
            } else {
                return HttpResultUtil.errorJson("余额不足");
            }
        } else if (orderNum.startsWith("ZCG")) {
            CdFootballFollowOrder cff = cdFootballFollowOrderService.findOrderByOrderNum(orderNum);
            price = cff.getPrice();
            String bestType = cff.getBestType();
            if (canPay(price, balance)) {
                //更该订单信息
                cff.setStatus("2");//已付款
                cff.setUid(uid);//预约单是写死的 支付后更新
                cdFootballFollowOrderService.save(cff);
                saveAllChange(price, balance, clu, orderNum, "2", bestType);
            } else {
                return HttpResultUtil.errorJson("余额不足");
            }
        } else if (orderNum.startsWith("LDG")) {
            CdBasketballSingleOrder cbs = cdBasketballSingleOrderService.findOrderByOrderNum(orderNum);
            price = cbs.getPrice();
            if (canPay(price, balance)) {
                //更该订单信息
                cbs.setStatus("2");//已付款
                cbs.setUid(uid);//预约单是写死的 支付后更新
                cdBasketballSingleOrderService.save(cbs);
                saveAllChange(price, balance, clu, orderNum, "3", "1");
            } else {
                return HttpResultUtil.errorJson("余额不足");
            }
        } else if (orderNum.startsWith("LCG")) {
            CdBasketballFollowOrder cbf = cdBasketballFollowOrderService.findOrderByOrderNum(orderNum);
            price = cbf.getPrice();
            String bestType = cbf.getBestType();
            if (canPay(price, balance)) {
                //更该订单信息
                cbf.setStatus("2");//已付款
                cbf.setUid(uid);//预约单是写死的 支付后更新
                cdBasketballFollowOrderService.save(cbf);
                saveAllChange(price, balance, clu, orderNum, "4", bestType);
            } else {
                return HttpResultUtil.errorJson("余额不足");
            }
        } else if (orderNum.startsWith("RXJ")) {
            CdChooseNineOrder ccno = cdChooseNineOrderService.findOrderByOrderNum(orderNum);
            price = ccno.getPrice();
            if (canPay(price, balance)) {
                //更该订单信息
                ccno.setStatus("2");//已付款
                ccno.setUid(uid);//预约单是写死的 支付后更新
                cdChooseNineOrderService.save(ccno);
                saveAllChange(price, balance, clu, orderNum, "5", "1");
            } else {
                return HttpResultUtil.errorJson("余额不足");
            }
        } else if (orderNum.startsWith("SFC")) {
            CdSuccessFailOrder csfo = cdSuccessFailOrderService.findOrderByOrderNum(orderNum);
            price = csfo.getPrice();
            if (canPay(price, balance)) {
                //更该订单信息
                csfo.setStatus("2");//已付款
                csfo.setUid(uid);//预约单是写死的 支付后更新
                cdSuccessFailOrderService.save(csfo);
                saveAllChange(price, balance, clu, orderNum, "6", "1");
            } else {
                return HttpResultUtil.errorJson("余额不足");
            }
        } else if (orderNum.startsWith("PLS")) {
            CdThreeOrder dto = cdThreeOrderService.findOrderByOrderNum(orderNum);
            price = dto.getPrice();
            if (canPay(price, balance)) {
                //更该订单信息
                dto.setStatus("2");//已付款
                dto.setUid(uid);//预约单是写死的 支付后更新
                cdThreeOrderService.save(dto);
                saveAllChange(price, balance, clu, orderNum, "7", "1");
                if ("2".equals(dto.getType())) {
                    //保存追号信息
                    CdOrderCatch coc = new CdOrderCatch();
                    coc.setContinuity(dto.getContinuity());//追的期数
                    coc.setHasContinue("1");//已追的期数
                    coc.setOrderNum(dto.getOrderNum());//订单号
                    coc.setPrice(dto.getPrice());//价格
                    coc.setStatus("1");//进心中
                    coc.setType("1");
                    coc.setUid(dto.getUid());
                    cdOrderCatchService.save(coc);
                }
            } else {
                return HttpResultUtil.errorJson("余额不足");
            }
        } else if (orderNum.startsWith("PLW")) {
            CdFiveOrder cfo = cdFiveOrderService.findOrderByOrderNum(orderNum);
            price = cfo.getPrice();
            if (canPay(price, balance)) {
                //更该订单信息
                cfo.setStatus("2");//已付款
                cfo.setUid(uid);//预约单是写死的 支付后更新
                cdFiveOrderService.save(cfo);
                saveAllChange(price, balance, clu, orderNum, "8", "1");
                if ("2".equals(cfo.getType())) {
                    //保存追号信息
                    CdOrderCatch coc = new CdOrderCatch();
                    coc.setContinuity(cfo.getContinuity());//追的期数
                    coc.setHasContinue("1");//已追的期数
                    coc.setOrderNum(cfo.getOrderNum());//订单号
                    coc.setPrice(cfo.getPrice());//价格
                    coc.setStatus("1");//进心中
                    coc.setType("2");
                    coc.setUid(cfo.getUid());
                    cdOrderCatchService.save(coc);
                }

            } else {
                return HttpResultUtil.errorJson("余额不足");
            }
        } else if (orderNum.startsWith("DLT")) {
            CdLottoOrder clo = cdLottoOrderService.findOrderByOrderNum(orderNum);
            price = clo.getPrice();
            if (canPay(price, balance)) {
                //更该订单信息
                clo.setStatus("2");//已付款
                clo.setUid(uid);//预约单是写死的 支付后更新
                cdLottoOrderService.save(clo);
                saveAllChange(price, balance, clu, orderNum, "9", "1");
                if ("2".equals(clo.getConType())) {
                    //保存追号信息
                    CdOrderCatch coc = new CdOrderCatch();
                    coc.setContinuity(clo.getContinuity());//追的期数
                    coc.setHasContinue("1");//已追的期数
                    coc.setOrderNum(clo.getOrderNum());//订单号
                    coc.setPrice(clo.getPrice());//价格
                    coc.setStatus("1");//进心中
                    coc.setType("3");
                    coc.setUid(clo.getUid());
                    cdOrderCatchService.save(coc);
                }
            } else {
                return HttpResultUtil.errorJson("余额不足");
            }
        }
        map.put("balance", clu.getBalance().toString());
        return HttpResultUtil.successJson(map);
    }


    /**
     * 订单详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getOrderToPay")
    @ResponseBody
    public String getOrderToPay(HttpServletRequest request) {
        logger.info("getOrderToPay--------------Start-----");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map<String, Object> map = new HashMap();
        //orderNum
        String orderNum = (String) jsonData.get("orderNum");
        if (StringUtils.isEmpty(orderNum)) {
            logger.error("orderNum为空！");
            return HttpResultUtil.errorJson("orderNum为空!");
        }
        map = SelOrderUtil.getOrderDetailMapToPay(orderNum, map);
        logger.info("getOrderToPay--------------End--------");
        return HttpResultUtil.successJson(map);
    }


    //判断余额是否充足
    private boolean canPay(String price, String leftMoney) {
        boolean flag = false;
        double priDouble = Double.parseDouble(price);
        double leftDouble = Double.parseDouble(leftMoney);
        if (leftDouble >= priDouble) {
            flag = true;
        }
        return flag;
    }

    //保存返利
    private void saveRebate(String price, String uid, String type, CdLotteryUser cdLotteryUser) {
        double priceDouble = Double.parseDouble(price);
        BigDecimal priceBig = new BigDecimal(price);
        boolean flag = false;
        String rebate = "";
        if (priceDouble > 0.0 && priceDouble < 1000.0) {
            flag = true;
            BigDecimal result = priceBig.multiply(new BigDecimal(0.01));
            rebate = String.valueOf(result.setScale(2, 1));
        } else if (priceDouble >= 1000.0 && priceDouble < 10000.0) {
            flag = true;
            BigDecimal result = priceBig.multiply(new BigDecimal(0.02));
            rebate = String.valueOf(result.setScale(2, 1));
        } else if (priceDouble >= 10000.0) {
            flag = true;
            BigDecimal result = priceBig.multiply(new BigDecimal(0.03));
            rebate = String.valueOf(result.setScale(2, 1));
        }
        if (flag) {
            CdRecordRebate crr = new CdRecordRebate();
            crr.setRebate(rebate);
            crr.setUid(uid);
            crr.setType(type);
            cdRecordRebateService.save(crr);
            //保存用户表返利字段
            String userRebate = cdLotteryUser.getRebate();
            BigDecimal newRebate = new BigDecimal(userRebate).add(new BigDecimal(rebate));
//            BigDecimal balance = cdLotteryUser.getBalance();
//            BigDecimal newBalance = balance.add(new BigDecimal(rebate));
//            cdLotteryUser.setBalance(newBalance);//更新余额
            cdLotteryUser.setRebate(String.valueOf(newRebate));//更新返利
            cdLotteryUserService.save(cdLotteryUser);
        }
    }

    //更新余额
    private CdLotteryUser getLeftMoney(String price, String balance, CdLotteryUser clu) {
        BigDecimal priceBig = new BigDecimal(price);
        BigDecimal balanceBig = new BigDecimal(balance);
        BigDecimal left = balanceBig.subtract(priceBig).setScale(2, 2);
        clu.setBalance(left);
        return clu;
    }

    //保存到订单总表
    private void saveOrder(CdLotteryUser clu, String number, String price, String type, String bestType) {
        CdOrder co = new CdOrder();
        co.setIssue("0");//自购
        co.setSaleId(clu.getSaleId());//销售id
        co.setTotalPrice(price);//金额
        co.setNumber(number);//订单号
        co.setType(type);//彩种
        co.setUserName(clu.getName());
        co.setStatus("1");//待开奖
        co.setWinPrice("0");//中奖金额
        co.setUserId(clu.getId());
        co.setFollowNum("");
        co.setBestType(bestType);
        co.setWinStatus("0");//未派奖
        cdOrderService.save(co);
    }

    private void saveAllChange(String price, String balance, CdLotteryUser clu, String orderNum, String type, String bestType) {
        //更新用户信息
        clu = getLeftMoney(price, balance, clu);
        cdLotteryUserService.save(clu);
        //保存返利
        //saveRebate(price, clu.getId(), type, clu);
        //保存订单总表
        saveOrder(clu, orderNum, price, type, bestType);

    }
}