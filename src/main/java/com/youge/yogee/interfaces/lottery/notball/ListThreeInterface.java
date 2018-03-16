package com.youge.yogee.interfaces.lottery.notball;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.ListThreeWays;
import com.youge.yogee.interfaces.util.util;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyifeng on 2018-02-07.
 */
@Controller
@RequestMapping("${frontPath}")
public class ListThreeInterface {
    private static final Logger logger = LoggerFactory.getLogger(ListThreeInterface.class);

    @Autowired
    private CdThreeOrderService cdThreeOrderService;

    /**
     * 排列三 提交订单
     */
    @RequestMapping(value = "listThreeOrderCommit", method = RequestMethod.POST)
    @ResponseBody
    public String listThreeOrderCommit(HttpServletRequest request) {
        logger.info("排列三订单提交 listThreeOrderCommit--------Start-------------------");
        logger.debug("interface 请求--replacementDelOrder-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }

        String nums = (String) jsonData.get("nums");
        if (StringUtils.isEmpty(nums)) {
            logger.error("订单详情nums为空");
            return HttpResultUtil.errorJson("订单详情nums为空");
        }
        //1直选 2和值 3组三单式 4组三复式 5组六单式 6组六复式
        String buyWays = (String) jsonData.get("buyWays");
        if (StringUtils.isEmpty(buyWays)) {
            logger.error("玩法buyWays为空");
            return HttpResultUtil.errorJson("玩法buyWays为空");
        }
        //期数
        String weekday = (String) jsonData.get("weekday");
        if (StringUtils.isEmpty(weekday)) {
            logger.error("期数weekday为空");
            return HttpResultUtil.errorJson("期数weekday为空");
        }
        //连续购买期数
        String continuity = (String) jsonData.get("continuity");
        if (StringUtils.isEmpty(continuity)) {
            logger.error("continuity为空");
            return HttpResultUtil.errorJson("continuity为空");
        }
        //倍数
        String times = (String) jsonData.get("times");
        if (StringUtils.isEmpty(times)) {
            logger.error("times为空");
            return HttpResultUtil.errorJson("times为空");
        }
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }
        //1自购 2追号
        String type = "";
        String weekContinue = "";
        if ("1".equals(continuity)) {
            type = "1";
        } else {
            type = "2";
            int week = Integer.parseInt(weekday);
            int con = Integer.parseInt(continuity);
            for (int i = 1; i < con; i++) {
                week++;
                weekContinue += String.valueOf(week) + ",";
            }
        }

        //所有可能
        Map<String, String> dataMap = ListThreeWays.listThree(nums, buyWays);
        String allPerhaps = dataMap.get("allPerhaps");
        //注数
        String acount = dataMap.get("count");
        //生成订单号
        String orderNum = util.genOrderNo("PLS", util.getFourRandom());
        //计算金额
        double money = 2.00;
        double acountDouble = Double.parseDouble(acount);
        double timesDouble = Double.parseDouble(times);
        String price = String.valueOf(money * acountDouble * timesDouble);
        //奖金
        String award = dataMap.get("award");
        CdThreeOrder cto = new CdThreeOrder();
        cto.setOrderNum(orderNum);//订单号
        cto.setNums(nums);//订单详情
        cto.setBuyWays(buyWays);//玩法
        cto.setWeekday(weekday);//期数
        cto.setAcount(acount);//注数
        cto.setPrice(price);//金额
        cto.setAward(award);//奖金
        cto.setUid(uid);//用户
        cto.setAllPerhaps(allPerhaps);//所有可能
        cto.setStatus("1");//已提交
        cto.setTimes(times);//倍数
        cto.setContinuity(continuity);//连续期数
        cto.setType(type);//类型
        cto.setWeekContinue(weekContinue);//连续期数详情
        cto.setFollowType("1");//原始订单
        try {
            cdThreeOrderService.save(cto);
            map.put("flag", "1");
        } catch (Exception e) {
            return HttpResultUtil.errorJson("提交失败");
        }

        logger.info("排列三订单提交 listThreeOrderCommit---------End---------------------");
        return HttpResultUtil.successJson(map);
    }
}
