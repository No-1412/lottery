package com.youge.yogee.interfaces.lottery.order;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.Calculations;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.util;
import com.youge.yogee.modules.clottoreward.entity.CdLottoOrder;
import com.youge.yogee.modules.clottoreward.service.CdLottoOrderService;
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
 * Created by zhaoyifeng on 2018-02-08.
 */
@Controller
@RequestMapping("${frontPath}")
public class LottoInterface {
    private static final Logger logger = LoggerFactory.getLogger(LottoInterface.class);

    @Autowired
    private CdLottoOrderService cdLottoOrderService;

    /**
     * 大乐透 提交订单
     */
    @RequestMapping(value = "lottorOrderCommit", method = RequestMethod.POST)
    @ResponseBody
    public String lottorOrderCommit(HttpServletRequest request) {

        logger.info("大乐透订单提交 lottorOrderCommit--------Start-------------------");
        logger.debug("interface 请求--lottorOrderCommit-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }

        String redNums = (String) jsonData.get("redNums");
        if (StringUtils.isEmpty(redNums)) {
            logger.error("redNums为空");
            return HttpResultUtil.errorJson("redNums为空");
        }
        String blueNums = (String) jsonData.get("blueNums");
        if (StringUtils.isEmpty(blueNums)) {
            logger.error("blueNums为空");
            return HttpResultUtil.errorJson("blueNums为空");
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

        String type = (String) jsonData.get("type");
        if (StringUtils.isEmpty(type)) {
            logger.error("type为空");
            return HttpResultUtil.errorJson("type为空");
        }

        String weekday = (String) jsonData.get("weekday");
        if (StringUtils.isEmpty(weekday)) {
            logger.error("期数weekday为空");
            return HttpResultUtil.errorJson("期数weekday为空");
        }

        String isPlus = (String) jsonData.get("isPlus");
        if (StringUtils.isEmpty(isPlus)) {
            logger.error("isPlus为空");
            return HttpResultUtil.errorJson("isPlus为空");
        }

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }


        //1自购 2追号
        String conType = "";
        String weekContinue = "";
        if ("1".equals(continuity)) {
            conType = "1";
        } else {
            conType = "2";
            int week = Integer.parseInt(weekday);
            int con = Integer.parseInt(continuity);
            for (int i = 1; i < con; i++) {
                week++;
                weekContinue += String.valueOf(week) + ",";
            }
        }


        //注数
        String acount = "0";
        if ("1".equals(type)) {
            String[] redStr = redNums.split(",");
            String[] blueStr = blueNums.split(",");
            int redInt = redStr.length;
            int blueInt = blueStr.length;
            int redCount = Calculations.combine(0, 5, redInt, 0);
            int blueCount = Calculations.combine(0, 2, blueInt, 0);
            acount = String.valueOf(redCount * blueCount);
        }

        if ("2".equals(type)) {
            String[] redStr = redNums.split("\\|"); //胆+拖
            String[] blueStr = blueNums.split("\\|");//胆+拖
            //红球
            String[] redBefor = redStr[0].split(",");//胆
            String[] redAfter = redStr[1].split(",");//拖
            int redBeforInt = redBefor.length; //胆长度
            int redAfterInt = redAfter.length;//拖长度
            int redCount = Calculations.combine(0, 5 - redBeforInt, redAfterInt, 0);
            //篮球
            int blueBeforInt = 0;
            int blueAfterInt = 0;
            //篮球选胆
            if (blueStr.length > 1) {
                String[] blueBefor = blueStr[0].split(",");//胆
                String[] blueAfter = blueStr[1].split(",");//拖
                blueBeforInt = blueBefor.length; //胆长度
                blueAfterInt = blueAfter.length;//拖长度
            } else {
                String[] blue = blueStr[0].split(",");//所有篮球
                blueAfterInt = blue.length;//拖长度
            }

            int blueCount = Calculations.combine(0, 2 - blueBeforInt, blueAfterInt, 0);
            acount = String.valueOf(redCount * blueCount);
        }

        //生成订单号
        String orderNum = util.genOrderNo("DLT", util.getFourRandom());
        //计算金额
        double money = 0;
        if ("1".equals(isPlus)) {
            money = 3.00;
        } else {
            money = 2.00;
        }

        double acountDouble = Double.parseDouble(acount);
        double timesDouble = Double.parseDouble(times);
        String price = String.valueOf(money * acountDouble * timesDouble);
        //奖金 未中奖为0
        String award = "0";

        CdLottoOrder clo = new CdLottoOrder();

        clo.setOrderNum(orderNum);//订单号
        clo.setRedNums(redNums);//红球号
        clo.setBlueNums(blueNums);//篮球号
        clo.setWeekday(weekday);//期数
        clo.setAcount(acount);//注数
        clo.setPrice(price);//金额
        clo.setAward(award);//奖金
        clo.setUid(uid);//用户
        clo.setStatus("1");//已提交
        clo.setType(type);//玩法
        clo.setTimes(times);//倍数
        clo.setContinuity(continuity);//连续期数
        clo.setConType(conType);//类型
        clo.setWeekContinue(weekContinue);//连续期数详情
        clo.setIsPlus(isPlus);//追加投注
        try {
            cdLottoOrderService.save(clo);
            map.put("orderNum", orderNum);
            map.put("orderName", "大乐透订单支付");
            map.put("time", clo.getCreateDate());
            map.put("price", price);
            map.put("acountStr", acount);//注数
            map.put("times", times);//倍数
            map.put("followNum", "");//串关
        } catch (Exception e) {
            return HttpResultUtil.errorJson("提交失败");
        }

        logger.info("大乐透订单提交 lottorOrderCommit---------End---------------------");
        return HttpResultUtil.successJson(map);
    }

}
