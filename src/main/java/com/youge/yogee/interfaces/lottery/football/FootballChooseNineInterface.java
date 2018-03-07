package com.youge.yogee.interfaces.lottery.football;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.util;
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNineOrder;
import com.youge.yogee.modules.cchoosenine.service.CdChooseNineOrderService;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFail;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyifeng on 2018-02-22.
 */
@Controller
@RequestMapping("${frontPath}")
public class FootballChooseNineInterface {
    private static final Logger logger = LoggerFactory.getLogger(FootballChooseNineInterface.class);

    @Autowired
    private CdChooseNineOrderService cdChooseNineOrderService;
    @Autowired
    private CdSuccessFailService cdSuccessFailService;

    /**
     * 任选九 提交订单
     */
    @RequestMapping(value = "chooseNineOrderCommit", method = RequestMethod.POST)
    @ResponseBody
    public String chooseNineOrderCommit(HttpServletRequest request) {
        logger.info(" interface chooseNineOrderCommit--------Start-------------------");
        logger.debug("interface 请求--chooseNineOrderCommit-------- Start--------");
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
        Object jsonString = jsonData.get("detail");
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        List<Map<String, Object>> detail = (List<Map<String, Object>>) jsonArray.toCollection(jsonArray, Map.class);
        List<String> resultList = new ArrayList<>();
        String orderDetail = "";
        int mustCount = 1;
        String matchIds = "";
        if (detail.size() != 0) {
            for (Map<String, Object> d : detail) {
                String matchId = (String) d.get("matchId");
                String wantResult = (String) d.get("wantResult");
                String isMust = (String) d.get("isMust");
                if ("0".equals(isMust)) {
                    resultList.add(wantResult);
                }
                //如果有胆 算出基本注数 再乘每场胆的个数积
                if ("1".equals(isMust)) {
                    String[] mustStr = wantResult.split(",");
                    mustCount = mustCount * mustStr.length;
                }

                CdSuccessFail csf = cdSuccessFailService.getSuccessFailDetail(matchId, weekday);
                if (csf == null) {
                    return HttpResultUtil.errorJson("数据迷路，请稍后再试");
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
                String partOfResult = matchId + "+" + beat + "+" + wantResult + "+" + isMust;
                orderDetail += partOfResult + "|";
                if ("1".equals(isMust)) {
                    matchIds += "胆" + "+" + matchId + ",";
                } else {
                    matchIds += "非" + "+" + matchId + ",";
                }

            }
        }

        //用户id
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }
        //注数
        int count = detail.size() - resultList.size();
        int acount = BallGameCals.countOfFootBall(resultList, 9 - count, 2) * mustCount;
        String acountStr = String.valueOf(acount);
        //生成订单号
        String orderNum = util.genOrderNo("RXJ", util.getFourRandom());
        //计算金额
        double money = 2.00;
        double acountDouble = Double.parseDouble(acountStr);
        double timesDouble = Double.parseDouble(times);
        String price = String.valueOf(money * acountDouble * timesDouble);

        CdChooseNineOrder ccno = new CdChooseNineOrder();
        ccno.setOrderNumber(orderNum); //订单号
        ccno.setAcount(acountStr);//注数
        ccno.setAward("0"); //奖金
        ccno.setOrderDetail(orderDetail); //订单详情
        ccno.setPrice(price);//金额
        ccno.setWeekday(weekday);//期数
        ccno.setStatus("1");//已提交
        ccno.setUid(uid);//用户
        ccno.setTimes(times);//倍数
        ccno.setMatchIds(matchIds);//购买具体期数
        try {
            cdChooseNineOrderService.save(ccno);
            map.put("flag", "1");
        } catch (Exception e) {
            return HttpResultUtil.errorJson("保存失败");
        }

        logger.info("chooseNineOrderCommit---------End---------------------");
        return HttpResultUtil.successJson(map);
    }
}
