package com.youge.yogee.interfaces.lottery.football;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.util;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFail;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFailOrder;
import com.youge.yogee.modules.csuccessfail.service.CdSuccessFailOrderService;
import com.youge.yogee.modules.csuccessfail.service.CdSuccessFailService;
import net.sf.json.JSONArray;
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
 * Created by zhaoyifeng on 2018-02-23.
 */
@Controller
@RequestMapping("${frontPath}")
public class FootballSuccessFailInterface {
    private static final Logger logger = LoggerFactory.getLogger(FootballSuccessFailInterface.class);

    @Autowired
    private CdSuccessFailService cdSuccessFailService;
    @Autowired
    private CdSuccessFailOrderService cdSuccessFailOrderService;

    /**
     * 胜负彩 提交订单
     */
    @RequestMapping(value = "successFailOrderCommit", method = RequestMethod.POST)
    @ResponseBody
    public String successFailOrderCommit(HttpServletRequest request) {
        logger.info(" interface successFailOrderCommit--------Start-------------------");
        logger.debug("interface 请求--successFailOrderCommit-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        //期数
        String weekday = (String) jsonData.get("weekday");
        if (StringUtils.isEmpty(weekday)) {
            logger.error("期数weekday为空");
            return HttpResultUtil.errorJson("期数weekday为空");
        }

        //倍数
        String times = (String) jsonData.get("times");
        if (StringUtils.isEmpty(times)) {
            logger.error("times为空");
            return HttpResultUtil.errorJson("times为空");
        }

        //订单详情
//        List<Map<String, Object>> detail = (List<Map<String, Object>>) jsonData.get("detail");
        Object jsonString = jsonData.get("detail");
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        List<Map<String, Object>> detail = (List<Map<String, Object>>) jsonArray.toCollection(jsonArray, Map.class);
        List<String> resultList = new ArrayList<>();
        String orderDetail = "";

        if (detail.size() != 0) {
            for (Map<String, Object> d : detail) {
                String matchId = (String) d.get("matchId");
                String wantResult = (String) d.get("wantResult");
                //结果集
                resultList.add(wantResult);
                CdSuccessFail csf = cdSuccessFailService.getSuccessFailDetail(matchId, weekday);
                if (csf == null) {
                    return HttpResultUtil.errorJson("数据迷路,请稍后再试");
                }
//                //赔率
//                String odds = "";
//                String[] resultStr = wantResult.split(",");
//                for (String s : resultStr) {
//                    if ("3".equals(s)) {
//                        odds += csf.getWinningOdds() + ",";
//                    }
//                    if ("1".equals(s)) {
//                        odds += csf.getFlatOdds() + ",";
//                    }
//                    if ("0".equals(s)) {
//                        odds += csf.getFlatOdds() + ",";
//                    }
//                }
                //主客队
                String beat = csf.getHomeTeam() + "vs" + csf.getAwayTeam();
                //单场详情
                String partOfResult = matchId + "+" + beat + "+" + wantResult;
                orderDetail += partOfResult + "|";
            }
        }

        //用户id
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }
        //注数
        int acount = BallGameCals.countOfFootBall(resultList, 14, 2);
        String acountStr = String.valueOf(acount);
        //生成订单号
        String orderNum = util.genOrderNo("SFC", util.getFourRandom());
        //计算金额
        BigDecimal money = new BigDecimal(2);
        BigDecimal acountBig = new BigDecimal(acountStr);
        BigDecimal timesDouble = new BigDecimal(times);
        //double money = 2.00;
        //double acountDouble = Double.parseDouble(acountStr);
        //double timesDouble = Double.parseDouble(times);
        //String price = String.valueOf(money * acountDouble * timesDouble);
        BigDecimal price = money.multiply(acountBig).multiply(timesDouble).setScale(2, BigDecimal.ROUND_DOWN);

        CdSuccessFailOrder csfo = new CdSuccessFailOrder();
        csfo.setOrderNumber(orderNum); //订单号
        csfo.setAcount(acountStr);//注数
        csfo.setAward("0"); //奖金
        csfo.setOrderDetail(orderDetail); //订单详情
        csfo.setPrice(String.valueOf(price));//金额
        csfo.setWeekday(weekday);//期数
        csfo.setStatus("1");//已提交
        csfo.setUid(uid);//用户
        csfo.setTimes(times);//倍数
        try {
            cdSuccessFailOrderService.save(csfo);
            map.put("flag", "1");
        } catch (Exception e) {
            return HttpResultUtil.errorJson("保存失败");
        }

        logger.info("successFailOrderCommit---------End---------------------");
        return HttpResultUtil.successJson(map);
    }

}
