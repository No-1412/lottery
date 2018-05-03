package com.youge.yogee.interfaces.lottery.order;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.util;
import com.youge.yogee.modules.bm.entity.BmEventBelong;
import com.youge.yogee.modules.bm.service.BmEventBelongService;
import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinish;
import com.youge.yogee.modules.cfbnotfinish.service.CdFbNotFinishService;
import com.youge.yogee.modules.cfootballmixed.entity.CdFootballMixed;
import com.youge.yogee.modules.cfootballmixed.service.CdFootballMixedService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyifeng on 2018-02-24.
 */
@Controller
@RequestMapping("${frontPath}")
public class FootballSingleOrderInterface {
    private static final Logger logger = LoggerFactory.getLogger(FootballSingleOrderInterface.class);

    @Autowired
    private CdFootballSingleOrderService cdFootballSingleOrderService;
    @Autowired
    private CdFootballMixedService cdFootballMixedService;
    @Autowired
    private BmEventBelongService bmEventBelongService;
    @Autowired
    private CdFbNotFinishService cdFbNotFinishService;

    /**
     * 足球单关 提交订单
     */
    @RequestMapping(value = "footballSingleOrderCommit", method = RequestMethod.POST)
    @ResponseBody
    public String footballSingleOrderCommit(HttpServletRequest request) throws ParseException {
        logger.info(" interface footballSingleOrderCommit--------Start-------------------");
        logger.debug("interface 请求--footballSingleOrderCommit-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        //玩法 1混投 2胜负平 3猜比分 4总进球 5半全场 6让球
        String buyWays = (String) jsonData.get("buyWays");
        if (StringUtils.isEmpty(buyWays)) {
            logger.error("buyWays为空");
            return HttpResultUtil.errorJson("buyWays为空");
        }
        //用户id
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }

        //订单详情
        Object jsonString = jsonData.get("detail");
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        List<Map<String, Object>> detail = (List<Map<String, Object>>) jsonArray.toCollection(jsonArray, Map.class);
        //List<String> resultList = new ArrayList<>();
        //String orderDetail = "";
        String scoreDetail = "";
        String goalDetail = "";
        String halfDetail = "";
        String beatDetail = "";
        String letDetail = "";
        //让球详情
        String letBalls = "";
        String continent = "";
        int acount = 0;//注数
        String matchIds = "";
        String allMatchTimes = "";
        if (detail.size() != 0) {
            for (Map<String, Object> d : detail) {
                int partCount = 0;
                String matchId = (String) d.get("matchId");
                CdFootballMixed sfm = cdFootballMixedService.findByMatchId(matchId);
                if (sfm == null) {
                    return HttpResultUtil.errorJson("比赛不存在");
                }
                String shutDownTime = sfm.getTimeEndsale();
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (df.parse(shutDownTime).getTime() < day.getTime()) {
                    return HttpResultUtil.errorJson("超过比赛截止时间");
                }

                matchIds += matchId + ",";
                //比赛详情
                String partDetail = matchId + "+" + sfm.getWinningName() + "vs" + sfm.getDefeatedName();
                //记录所有比赛时间
                CdFbNotFinish cfnf = cdFbNotFinishService.findByJn(matchId);
                if (cfnf != null) {
                    allMatchTimes += cfnf.getTime() + ",";
                } else {
                    allMatchTimes += "2000-01-01 00:00:00" + ",";
                }
                //查询赛事名称 获取大洲
                BmEventBelong beb = bmEventBelongService.findByEventName(sfm.getEventName());
                if (beb != null) {
                    String aContinent = beb.getContinentName();
                    continent += aContinent + ",";
                }
                //比分所有押注结果
                String score = (String) d.get("score");
                if (StringUtils.isNotEmpty(score)) {
                    String scoreArry[] = score.split(",");
                    for (String s : scoreArry) {
                        String aScore[] = s.split("/");
                        String count = aScore[2];
                        int countInt = Integer.parseInt(count);
                        partCount += countInt;
                    }
                    scoreDetail += partDetail + "+" + score + "|";
                    //cfso.setScore(partDetail+"+"+score);
                }

                //总进球所有押注结果
                String goal = (String) d.get("goal");
                if (StringUtils.isNotEmpty(goal)) {
                    String goalArry[] = goal.split(",");
                    for (String s : goalArry) {
                        String aGoal[] = s.split("/");
                        String count = aGoal[2];
                        int countInt = Integer.parseInt(count);
                        partCount += countInt;
                    }
                    //partDetail += "+" + goal;
                    goalDetail += partDetail + "+" + goal + "|";
                }
                //半全场所有押注结果
                String half = (String) d.get("half");
                if (StringUtils.isNotEmpty(half)) {
                    String halfArry[] = half.split(",");
                    for (String s : halfArry) {
                        String aHalf[] = s.split("/");
                        String count = aHalf[2];
                        int countInt = Integer.parseInt(count);
                        partCount += countInt;
                    }
//                    partDetail += "+" + half;
                    halfDetail += partDetail + "+" + half + "|";
                }

                //胜负平所有押注结果
                String beat = (String) d.get("beat");
                if (StringUtils.isNotEmpty(beat)) {
                    String beatArry[] = beat.split(",");
                    for (String s : beatArry) {
                        String aBeat[] = s.split("/");
                        String count = aBeat[2];
                        int countInt = Integer.parseInt(count);
                        partCount += countInt;
                    }
                    //partDetail += "+" + beat;
                    beatDetail += partDetail + "+" + beat + "|";
                }


                //让球胜负平所有押注结果
                String let = (String) d.get("let");
                if (StringUtils.isNotEmpty(let)) {
                    String letArry[] = let.split(",");
                    for (String s : letArry) {
                        String aLet[] = s.split("/");
                        String count = aLet[2];
                        int countInt = Integer.parseInt(count);
                        partCount += countInt;
                    }
                    //partDetail += "+" + beat;
                    letDetail += partDetail + "+" + let + "|";
                    letBalls += sfm.getClose() + ",";
                }


                acount += partCount;
                //orderDetail += partDetail + "|";

            }
        }


        //注数
        String acountStr = String.valueOf(acount);
        //生成订单号
        String orderNum = util.genOrderNo("ZDG", util.getFourRandom());
        //计算金额
        double money = 2.00;
        double acountDouble = Double.parseDouble(acountStr);
        String price = String.valueOf(money * acountDouble);

        CdFootballSingleOrder cfso = new CdFootballSingleOrder();
        cfso.setOrderNum(orderNum); //订单号
        cfso.setAcount(acountStr);//注数
        cfso.setAward("0"); //奖金
        //cfso.setOrderDetail(orderDetail); //订单详情
        cfso.setScore(scoreDetail);//比分详情
        cfso.setHalf(halfDetail);//半全场
        cfso.setGoal(goalDetail);//总进球
        cfso.setBeat(beatDetail);//胜负平
        cfso.setLet(letDetail);//让球胜负平

        cfso.setLetBalls(letBalls);//让球数

        cfso.setPrice(price);//金额
        cfso.setStatus("1");//已提交
        cfso.setUid(uid);//用户
        cfso.setBuyWays(buyWays); //玩法 1混投 2胜负平 3猜比分 4总进球 5半全场 6让球
        cfso.setType("0");//0普通订单 1发起的 2跟单的
        cfso.setMatchIds(matchIds);//所有场次
        cfso.setContinent(continent);//大洲
        cfso.setAllMatchTimes(allMatchTimes);
        try {
            cdFootballSingleOrderService.save(cfso);
            map.put("orderNum", orderNum);
            map.put("orderName", "竞猜足球订单支付");
            map.put("time", cfso.getCreateDate());
            map.put("price", price);
            map.put("acountStr", acountStr);//注数
            map.put("times", "1");//倍数
            map.put("followNum", "");//串关
        } catch (Exception e) {
            return HttpResultUtil.errorJson("保存失败");
        }

        logger.info("footballSingleOrderCommit---------End---------------------");
        return HttpResultUtil.successJson(map);
    }

}
