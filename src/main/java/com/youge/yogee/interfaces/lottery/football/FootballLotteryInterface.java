package com.youge.yogee.interfaces.lottery.football;

import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cfootballdouble.entity.CdFootBallDouble;
import com.youge.yogee.modules.cfootballdouble.service.CdFootBallDoubleService;
import com.youge.yogee.modules.cfootballgoal.entity.CdFootBallGoal;
import com.youge.yogee.modules.cfootballgoal.service.CdFootBallGoalService;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFail;
import com.youge.yogee.modules.csuccessfail.service.CdSuccessFailService;
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
public class FootballLotteryInterface {
    private static final Logger logger = LoggerFactory.getLogger(FootballLotteryInterface.class);

    @Autowired
    private CdSuccessFailService cdSuccessFailService;
    @Autowired
    private CdFootBallGoalService cdFootBallGoalService;
    @Autowired
    private CdFootBallDoubleService cdFootBallDoubleService;

    /**
     * 足彩--胜负彩,任选九数据接口(非竞彩足球)
     */
    @RequestMapping(value = "successFail", method = RequestMethod.POST)
    @ResponseBody
    public String successFail(HttpServletRequest request) {
        logger.info("successFail  足彩胜负彩,任选九(非竞彩足球)---------Start---------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }


        List<CdSuccessFail> dataList = cdSuccessFailService.getSuccessFail();
        List<String> dateList = new ArrayList<>();
        String weekDay = "";
        for (int i = 0; i < dataList.size(); i++) {
            if (i == 0) {
                weekDay = dataList.get(i).getWeekday();
                dateList.add(weekDay);
            }
            if (i > 0) {
                if (!weekDay.equals(dataList.get(i).getWeekday())) {
                    weekDay = dataList.get(i).getWeekday();
                    dateList.add(weekDay);
                }
            }
        }

        List lastList = new ArrayList();
        for (String s : dateList) {
            List<CdSuccessFail> sList = cdSuccessFailService.getSuccessFailByWeekDay(s);
            List list = new ArrayList();
            for (CdSuccessFail str : sList) {
                Map map = new HashMap();
                map.put("matchId", str.getMatchId());//场次id
                map.put("eventName", str.getEventName());//赛事名称
                map.put("matchDate", str.getMatchDate());//比赛时间
                map.put("homeTeam", str.getHomeTeam());//主队
                map.put("awayTeam", str.getAwayTeam());//客队
                map.put("winningOdds", str.getWinningOdds());//主胜赔率
                map.put("flatOdds", str.getFlatOdds());//平赔率
                map.put("defeatedOdds", str.getDefeatedOdds());//负赔率
                map.put("timeEndSale", str.getTimeEndSale());//截止时间
                map.put("weekday", str.getWeekday());//期号
                list.add(map);
            }

            lastList.add(list);
        }
//        List<CdSuccessFail> sList = cdSuccessFailService.getSuccessFailByWeekDay(weekday);

        Map dataMap = new HashMap();
        dataMap.put("list", lastList);
        dataMap.put("dateList", dateList);
        logger.info("successFail  足彩胜负彩,任选九(非竞彩足球)---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 四场进球彩赔率数据(非竞彩足球)
     *
     * @param
     */

    @RequestMapping(value = "footballGoal", method = RequestMethod.POST)
    @ResponseBody
    public String footballGoal(HttpServletRequest request) {
        logger.info("footballGoal  足彩进球彩---------Start---------");

        List<CdFootBallGoal> dataList = cdFootBallGoalService.getfootGoal();
        List<String> dateList = new ArrayList<>();
        String weekDay = "";
        for (int i = 0; i < dataList.size(); i++) {
            if (i == 0) {
                weekDay = dataList.get(i).getWeekday();
                dateList.add(weekDay);
            }
            if (i > 0) {
                if (!weekDay.equals(dataList.get(i).getWeekday())) {
                    weekDay = dataList.get(i).getWeekday();
                    dateList.add(weekDay);
                }
            }
        }

        List lastList = new ArrayList();
        for (String s : dateList) {
            List<CdFootBallGoal> sList = cdFootBallGoalService.getFootBallGoalByWeekDay(s);
            List list = new ArrayList();
            for (CdFootBallGoal str : sList) {
                Map map = new HashMap();
                map.put("matchId", str.getMatchId());//场次id
                map.put("eventName", str.getEventName());//赛事名称
                map.put("matchDate", str.getMatchDate());//比赛时间
                map.put("homeTeam", str.getHomeTeam());//主队
                map.put("awayTeam", str.getAwayTeam());//客队
                map.put("winningOdds", str.getWinningOdds());//主胜赔率
                map.put("flatOdds", str.getFlatOdds());//平赔率
                map.put("defeatedOdds", str.getDefeatedOdds());//负赔率
                map.put("timeEndSale", str.getTimeEndSale());//截止时间
                map.put("weekday", str.getWeekday());//期号
                list.add(map);
            }
            lastList.add(list);
        }


        Map dataMap = new HashMap();
        dataMap.put("list", lastList);
        dataMap.put("dateList", dateList);
        logger.info("footballGoal  足彩进球彩---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 六场半全场赔率数据
     *
     * @param
     */
    @RequestMapping(value = "footDoubleResult", method = RequestMethod.POST)
    @ResponseBody
    public String footDoubleResult(HttpServletRequest request) {
        logger.info("footDoubleResult  足彩半全场---------Start---------");
        //List list = new ArrayList();
        List<CdFootBallDouble> dataList = cdFootBallDoubleService.getFootDouble();
        List<String> dateList = new ArrayList<>();
        String weekDay = "";
        for (int i = 0; i < dataList.size(); i++) {
            if (i == 0) {
                weekDay = dataList.get(i).getWeekday();
                dateList.add(weekDay);
            }
            if (i > 0) {
                if (!weekDay.equals(dataList.get(i).getWeekday())) {
                    weekDay = dataList.get(i).getWeekday();
                    dateList.add(weekDay);
                }
            }
        }


        List lastList = new ArrayList();
        for (String s : dateList) {
            List<CdFootBallDouble> sList = cdFootBallDoubleService.getFootBallDoubleByWeekDay(s);
            List list = new ArrayList();
            for (CdFootBallDouble str : sList) {
                Map map = new HashMap();
                map.put("matchId", str.getMatchId());//场次id
                map.put("eventName", str.getEventName());//赛事名称
                map.put("matchDate", str.getMatchDate());//比赛时间
                map.put("homeTeam", str.getHomeTeam());//主队
                map.put("awayTeam", str.getAwayTeam());//客队
                map.put("winningOdds", str.getWinningOdds());//主胜赔率
                map.put("flatOdds", str.getFlatOdds());//平赔率
                map.put("defeatedOdds", str.getDefeatedOdds());//负赔率
                map.put("timeEndSale", str.getTimeEndSale());//截止时间
                map.put("weekday", str.getWeekday());//期号
                list.add(map);
            }
            lastList.add(list);
        }

        Map dataMap = new HashMap();
        dataMap.put("list", lastList);
        dataMap.put("dateList", dateList);
        logger.info("footDoubleResult  足彩半全场---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


}
