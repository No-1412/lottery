package com.youge.yogee.interfaces.lottery.award;

import com.youge.yogee.interfaces.lottery.football.FootballLotteryInterface;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNine;
import com.youge.yogee.modules.cchoosenine.service.CdChooseNineService;
import com.youge.yogee.modules.ccolorreward.entity.CdColorReward;
import com.youge.yogee.modules.ccolorreward.service.CdColorRewardService;
import com.youge.yogee.modules.cdoubleresult.entity.CdDoubleResult;
import com.youge.yogee.modules.cdoubleresult.service.CdDoubleResultService;
import com.youge.yogee.modules.cfootballawards.entity.CdFootballAwards;
import com.youge.yogee.modules.cfootballawards.service.CdFootballAwardsService;
import com.youge.yogee.modules.cfootballgoalresult.entity.CdFootballGoalResult;
import com.youge.yogee.modules.cfootballgoalresult.service.CdFootballGoalResultService;
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
 * Created by wjc on 2018-1-22 0022.
 */
@Controller
@RequestMapping("${frontPath}")
public class BallRewardInterface {
    private static final Logger logger = LoggerFactory.getLogger(FootballLotteryInterface.class);

    @Autowired
    private CdChooseNineService cdChooseNineService;
    @Autowired
    private CdDoubleResultService cdDoubleResultService;
    @Autowired
    private CdFootballGoalResultService cdFootballGoalResultService;
    @Autowired
    private CdColorRewardService cdColorRewardService;
    @Autowired
    private CdFootballAwardsService cdFootballAwardsService;

    /**
     * 任选九开奖
     */
    @RequestMapping(value = "chooseNine", method = RequestMethod.POST)
    @ResponseBody
    public String chooseNine(HttpServletRequest request) {
        logger.info("任选九开奖信息 chooseNine--------Start-------------------");
        List list = new ArrayList();
        List<CdChooseNine> chooseNineList = cdChooseNineService.getChooseNine();
        for (CdChooseNine str : chooseNineList) {
            Map map = new HashMap();
            map.put("matchId", str.getMatchId()); //期次
            map.put("openingTime", str.getOpeningTime()); //开奖时间
            map.put("number", str.getNumber()); //中奖号码
            map.put("currentSales", str.getCurrentSales()); //本期销量
            map.put("jackpot", str.getJackpot()); //累计奖池
            map.put("notesNum", str.getNotesNum()); //注数
            map.put("perNoteMoney", str.getPerNoteMoney()); //每注金额
            map.put("hostName", str.getHostName()); //主队名称
            map.put("visitorName", str.getVisitorName()); //客队名称
            map.put("hs", str.getHs()); //主队进球
            map.put("vs", str.getVs()); //客队进球
            map.put("result", str.getResult()); //结果
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("任选九开奖信息 chooseNine---------End---------------------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 六场半全开奖
     */
    @RequestMapping(value = "footDoubleAward", method = RequestMethod.POST)
    @ResponseBody
    public String footDoubleAward(HttpServletRequest request) {
        logger.info("六场半全开奖信息 footDoubleAward--------Start-------------------");
        List list = new ArrayList();
        List<CdDoubleResult> doubleResults = cdDoubleResultService.getDoubleResult();
        for (CdDoubleResult str : doubleResults) {
            Map map = new HashMap();
            map.put("homeTeam", str.getHomeTeam()); //主队
            map.put("awayTeam", str.getAwayTeam()); //客队
            map.put("weekday", str.getWeekday()); //星期
            map.put("currentSales", str.getCurrentSales()); //本期销量
            map.put("jackpot", str.getJackpot()); //累计奖池
            map.put("hs", str.getHs()); //主队
            map.put("vs", str.getVs()); //客队
            map.put("notesNum", str.getNotesNum()); //中奖注数
            map.put("perNoteMoney", str.getPerNoteMoney()); //每注奖金
            map.put("winNum", str.getWinNum()); //开奖号
            map.put("result", str.getResult()); //进球数
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("六场半全开奖 footDoubleAward---------End---------------------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 四场进球开奖
     */
    @RequestMapping(value = "fbGoalResultAward", method = RequestMethod.POST)
    @ResponseBody
    public String fbGoalResultAward(HttpServletRequest request) {
        logger.info("四场进球开奖信息 fbGoalResultAward--------Start-------------------");
        List list = new ArrayList();
        List<CdFootballGoalResult> doubleResults = cdFootballGoalResultService.getGoalResultAward();
        for (CdFootballGoalResult str : doubleResults) {
            Map map = new HashMap();
            map.put("homeTeam", str.getHomeTeam()); //主队
            map.put("awayTeam", str.getAwayTeam()); //客队
            map.put("weekday", str.getWeekday()); //星期
            map.put("currentSales", str.getCurrentSales()); //本期销量
            map.put("jackpot", str.getJackpot()); //累计奖池
            map.put("hs", str.getHs()); //主队
            map.put("vs", str.getVs()); //客队
            map.put("notesNum", str.getNotesNum()); //中奖注数
            map.put("perNoteMoney", str.getPerNoteMoney()); //每注奖金
            map.put("winNum", str.getWinNum()); //开奖号
            map.put("result", str.getResult()); //进球数
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("四场进球开奖 fbGoalResultAward---------End---------------------");
        return HttpResultUtil.successJson(dataMap);
    }
    /**
     * 胜负彩开奖
     */
    @RequestMapping(value = "fbColorReward", method = RequestMethod.POST)
    @ResponseBody
    public String fbColorReward(HttpServletRequest request) {
        logger.info("胜负彩开奖信息 fbColorReward--------Start-------------------");
        List list = new ArrayList();
        List<CdColorReward> doubleResults = cdColorRewardService.getColorReward();
        for (CdColorReward str : doubleResults) {
            Map map = new HashMap();
            map.put("homeTeam", str.getHostName()); //主队
            map.put("awayTeam", str.getVisitorName()); //客队
            map.put("matchId", str.getMatchId()); //星期
            map.put("currentSales", str.getCurrentSales()); //本期销量
            map.put("jackpot", str.getJackpot()); //累计奖池
            map.put("hs", str.getHs()); //主队
            map.put("vs", str.getVs()); //客队
            map.put("notesNum", str.getNotesNum()); //中奖注数
            map.put("perNoteMoney", str.getPerNoteMoney()); //每注奖金
            map.put("winNum", str.getNumber()); //开奖号
            map.put("result", str.getResult()); //进球数
            map.put("openingTime", str.getOpeningTime()); //开奖时间
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("胜负彩开奖信息 fbColorReward---------End---------------------");
        return HttpResultUtil.successJson(dataMap);
    }
    /**
     * 竞彩足球开奖信息
     */
    @RequestMapping(value = "footballAwards", method = RequestMethod.POST)
    @ResponseBody
    public String footballAwards(HttpServletRequest request) {
        logger.info("竞彩足球开奖信息 footballAwards--------Start-------------------");
        List list = new ArrayList();
        List<CdFootballAwards> doubleResults = cdFootballAwardsService.getFootBallAwards();
        for (CdFootballAwards str : doubleResults) {
            Map map = new HashMap();
            map.put("homeTeam", str.getHomeTeam()); //主队
            map.put("awayTeam", str.getAwayTeam()); //客队
            map.put("matchId", str.getMatchId()); //星期
            map.put("eventName", str.getEventName()); //赛事名称
            map.put("winning", str.getWinning()); //胜负
            map.put("hs", str.getHs()); //主队
            map.put("vs", str.getVs()); //客队
            map.put("spread", str.getSpread()); //让分
            map.put("winGrap", str.getWinGrap()); //半全场
            map.put("spreadNum", str.getSpreadNum()); //让分数
            map.put("mt", str.getMt()); //赛事时间
            map.put("totalNum", str.getTotalNum()); //总进球
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("竞彩足球开奖信息 footballAwards---------End---------------------");
        return HttpResultUtil.successJson(dataMap);
    }
}
