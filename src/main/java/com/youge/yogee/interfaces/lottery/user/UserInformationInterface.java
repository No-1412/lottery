package com.youge.yogee.interfaces.lottery.user;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
import com.youge.yogee.modules.corder.service.CdOrderService;
import com.youge.yogee.modules.corder.service.CdOrderWinnersService;
import com.youge.yogee.modules.crecord.entity.CdRecordCash;
import com.youge.yogee.modules.crecord.entity.CdRecordRebate;
import com.youge.yogee.modules.crecord.service.CdRecordCashService;
import com.youge.yogee.modules.crecord.service.CdRecordRebateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的模块
 */
@Controller
@RequestMapping("${frontPath}")
public class UserInformationInterface {
    private static final Logger logger = LoggerFactory.getLogger(UserInformationInterface.class);
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdOrderService cdOrderService;
    @Autowired
    private CdRecordRebateService cdRecordRebateService;
    @Autowired
    private CdRecordCashService cdRecordCashService;
    @Autowired
    private CdOrderWinnersService cdOrderWinnersService;

    /**
     * 我的首页
     */

    @RequestMapping(value = "myHomePage", method = RequestMethod.POST)
    @ResponseBody
    public String myHomePage(HttpServletRequest request) {
        logger.info("myHomePage---------- Start-----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map dataMap = new HashMap();
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        String recharge = clu.getTotalRecharge();//充值总金额
        String percent = getLevelPercent(recharge);
        dataMap.put("uid", clu.getId());
        dataMap.put("img", clu.getImg());//头像
        dataMap.put("level", clu.getMemberLevel());//等级
        dataMap.put("name", clu.getName());//昵称
        dataMap.put("balance", clu.getBalance().setScale(2).toString());//余额
        dataMap.put("rebate", clu.getRebate());//返利金额
        dataMap.put("percent", percent);//等级百分比
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 我的订单
     */

    @RequestMapping(value = "myOrderList", method = RequestMethod.POST)
    @ResponseBody
    public String myOrderList(HttpServletRequest request) {
        logger.info("myOrderList---------- Start-----------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map dataMap = new HashMap();
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        String status = (String) jsonData.get("status");
        if (StringUtils.isEmpty(uid)) {
            status = "";
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("count为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        List<CdOrder> list = cdOrderService.listCdOrder(uid, status, total, count);
        List<Map<String, String>> cList = new ArrayList<>();
        for (CdOrder c : list) {
            Map<String, String> map = new HashMap();
            map.put("id", c.getId());//id
            map.put("type", c.getType());//彩票种类 1足球单关 2足球串关 3篮球单关 4篮球串关 5任选九 6胜负彩 7排列三 8排列五 9大乐透
            map.put("price", c.getTotalPrice());//金额
            map.put("win", c.getWinPrice());//中奖金额
            map.put("time", c.getCreateDate());//时间
            String quality = c.getIssue();
            map.put("quality", quality);//0自购 1跟单 2神单
            cList.add(map);
        }
        dataMap.put("cList", cList);
        return HttpResultUtil.successJson(dataMap);
    }


    @RequestMapping(value = "getRecordRebate", method = RequestMethod.POST)
    @ResponseBody
    public String getRecordCash(HttpServletRequest request) {
        logger.info("getRecordCash---------- Start-----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("count为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }

        List<CdRecordRebate> cdRecordRebateList = cdRecordRebateService.findByUid(uid, total, count);
        List<Map> recordRebateList = new ArrayList<>();
        for (CdRecordRebate cdRecordRebate : cdRecordRebateList) {
            Map<String,Object> cdRecordRebateMap = new HashMap<>();
            cdRecordRebateMap.put("rebate",cdRecordRebate.getRebate());
            cdRecordRebateMap.put("createDate",cdRecordRebate.getCreateDate());
            cdRecordRebateMap.put("type",cdRecordRebate.getType());
            recordRebateList.add(cdRecordRebateMap);
        }

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("recordRebateList", recordRebateList);//等级百分比
        return HttpResultUtil.successJson(dataMap);
    }

    /***
     * 提现记录
     * @param request
     * @return
     */
    @RequestMapping(value = "getRecordCash", method = RequestMethod.POST)
    @ResponseBody
    public String getRecordRebate(HttpServletRequest request) {
        logger.info("getRecordRebate---------- Start-----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("count为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }

        List<CdRecordCash> list = cdRecordCashService.findByUid(uid, total, count);
        List<Map> recordCashList = new ArrayList<>();
        for (CdRecordCash cdRecordCash : list) {
            Map<String,Object> cdRecordCashMap = new HashMap<>();
            cdRecordCashMap.put("price",cdRecordCash.getPrice());
            cdRecordCashMap.put("orderNum",cdRecordCash.getOrderNum());
            cdRecordCashMap.put("cardNum",cdRecordCash.getCreateDate());
            cdRecordCashMap.put("dealTime",cdRecordCash.getDealTime());
            cdRecordCashMap.put("status",cdRecordCash.getStatus());
            recordCashList.add(cdRecordCashMap);
        }

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("recordCashList", recordCashList);
        return HttpResultUtil.successJson(dataMap);
    }


    /***
     * 中奖记录
     * @param request
     * @return
     */
    @RequestMapping(value = "getOrderWinners", method = RequestMethod.POST)
    @ResponseBody
    public String getOrderWinners(HttpServletRequest request) {
        logger.info("getOrderWinners---------- Start-----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("count为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }

        List<CdOrderWinners> list = cdOrderWinnersService.findByUid(uid, total, count);
        List<Map> orderWinnersList = new ArrayList<>();
        for (CdOrderWinners cdOrderWinners : list) {
            Map<String,Object> orderWinnersMap = new HashMap<>();
            orderWinnersMap.put("type ",cdOrderWinners.getType());
            orderWinnersMap.put("orderNum",cdOrderWinners.getWinOrderNum());
            orderWinnersMap.put("price",cdOrderWinners.getWinPrice());
            orderWinnersMap.put("createDate",cdOrderWinners.getCreateDate());
            orderWinnersList.add(orderWinnersMap);
        }

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("orderWinnersList", orderWinnersList);
        return HttpResultUtil.successJson(dataMap);
    }


    private String getLevelPercent(String totalRecharge) {
        List<Double> level = new ArrayList<>();
        level.add(2.00);
        level.add(100.00);
        level.add(500.00);
        level.add(1000.00);
        level.add(5000.00);
        level.add(10000.00);
        level.add(100000.00);
        level.add(200000.00);
        level.add(500000.00);
        level.add(1000000.00);
        double recharge = Double.parseDouble(totalRecharge);
        String percent = "0";
        for (int i = 1; i < level.size(); i++) {
            double charge = level.get(i);
            double theLast = level.get(i - 1);
            if (recharge > theLast & recharge < charge) {
                BigDecimal total = new BigDecimal(recharge);
                BigDecimal nextLevel = new BigDecimal(charge);
                int per = total.divide(nextLevel, 2, 2).multiply(new BigDecimal(100)).intValue();
                percent = String.valueOf(per);
                break;
            }
        }
        return percent + "%";
    }





}
