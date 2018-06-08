package com.youge.yogee.interfaces.lottery.award;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveAwards;
import com.youge.yogee.modules.cfiveawards.service.CdFiveAwardsService;
import com.youge.yogee.modules.clottoreward.entity.CdLottoReward;
import com.youge.yogee.modules.clottoreward.service.CdLottoRewardService;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeAwards;
import com.youge.yogee.modules.cthreeawards.service.CdThreeAwardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wjc on 2018-1-22 0022.
 */
@Controller
@RequestMapping("${frontPath}")
public class NotBallAwardsInterface {
    private static final Logger logger = LoggerFactory.getLogger(NotBallAwardsInterface.class);
    @Autowired
    private CdLottoRewardService cdLottoRewardService;
    @Autowired
    private CdThreeAwardsService cdThreeAwardsService;
    @Autowired
    private CdFiveAwardsService cdFiveAwardsService;

    /**
     * 大乐透开奖
     */
    @RequestMapping(value = "lottoReward", method = RequestMethod.POST)
    @ResponseBody
    public String lottoReward(HttpServletRequest request) {
        logger.info("大乐透开奖信息 lottoReward--------Start-------------------");
        List list = new ArrayList();
        List<CdLottoReward> lottoRewardList = cdLottoRewardService.getLottoReward();

        for (CdLottoReward str : lottoRewardList) {
            Map map = new HashMap();
            map.put("matchId", str.getMatchId()); //期次
            map.put("openingTime", str.getOpeningTime()); //开奖时间
            map.put("number", str.getNumber()); //中奖号码
            map.put("currentSales", str.getCurrentSales()); //本期销量
            map.put("jackpot", str.getJackpot()); //累计奖池
            map.put("notesNum", str.getNotesNum()); //注数
            map.put("perNoteMoney", str.getPerNoteMoney()); //每注金额
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("大乐透开奖信息 lottoReward---------End---------------------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 大乐透详情
     */
    @RequestMapping(value = "lottoRewardDetail", method = RequestMethod.POST)
    @ResponseBody
    public String lottoRewardDetail(HttpServletRequest request) {
        logger.info("大乐透详情 lottoRewardDetail--------Start-------------------");
        Map<String, Object> map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        //期次
        String matchId = (String) jsonData.get("matchId");
        if (StringUtils.isEmpty(matchId)) {
            logger.error("matchId为空");
            return HttpResultUtil.errorJson("matchId为空");
        }
        CdLottoReward clr = cdLottoRewardService.findByMatchId(matchId);
        if (clr == null) {
            return HttpResultUtil.errorJson("信息错误");
        }
        map.put("matchId", clr.getMatchId()); //期次
        map.put("openingTime", clr.getOpeningTime()); //开奖时间
        map.put("number", clr.getNumber()); //中奖号码
        map.put("currentSales", clr.getCurrentSales()); //本期销量
        map.put("jackpot", clr.getJackpot()); //累计奖池
        //String noteNums = clr.getNotesNum().replaceAll("--,", ""); //注数
        String allNoteNums = clr.getNotesNum().replaceAll("--,", "");
        String noteNums = allNoteNums.replaceAll(",--", "");
        String[] noteNumsArrray = noteNums.split(",");

        //没注金额
        String allAward = clr.getPerNoteMoney().replaceAll("--,", "");
        String perNoteMoney = allAward.replaceAll(",--", "");
        String[] perNoteMoneyArrray = perNoteMoney.split(",");
        int count = 0;
        List<Map<String, String>> list = new ArrayList();
        for (int i = 0; i < perNoteMoneyArrray.length; i++) {
            Map<String, String> noteMap = new HashMap();
            if(i%2 == 0){
                noteMap.put("per", perNoteMoneyArrray[i/2]); //每注金额
                noteMap.put("noteNum", noteNumsArrray[i/2]);//注数
            }else {
                noteMap.put("per",perNoteMoneyArrray[6+count]);
                noteMap.put("noteNum",noteNumsArrray[6+count]);
                count++;
            }
            list.add(noteMap);
        }

        map.put("detail", list);
        return HttpResultUtil.successJson(map);
    }


    /**
     * 排列三开奖信息
     */
    @RequestMapping(value = "threeAwards", method = RequestMethod.POST)
    @ResponseBody
    public String threeAwards(HttpServletRequest request) {
        logger.info("排列三开奖信息 threeAwards--------Start-------------------");
        List list = new ArrayList();
        List<CdThreeAwards> lottoRewardList = cdThreeAwardsService.getThreeAwards();
        for (CdThreeAwards str : lottoRewardList) {
            Map map = new HashMap();
            map.put("weekDay", str.getWeekday()); //期次
            map.put("aTime", str.getAtime()); //开奖时间
            map.put("aCode", str.getAcode()); //开奖号码
            map.put("currentSales", str.getCurrentSales()); //本期销量
            map.put("jackpot", str.getJackpot()); //累计奖池
            map.put("notesNum", str.getNotesNum()); //注数
            map.put("perNoteMoney", str.getPerNoteMoney()); //每注金额
            map.put("awardName", str.getAwardName()); //奖项名称
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("排列三开奖信息 threeAwards---------End---------------------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 排列五开奖信息
     */
    @RequestMapping(value = "fiveAwards", method = RequestMethod.POST)
    @ResponseBody
    public String fiveAwards(HttpServletRequest request) {
        logger.info("排列五开奖信息 fiveAwards--------Start-------------------");
        List list = new ArrayList();
        List<CdFiveAwards> fiveList = cdFiveAwardsService.getFiveAwards();
        for (CdFiveAwards str : fiveList) {
            Map map = new HashMap();
            map.put("weekDay", str.getWeekday()); //期次
            map.put("aTime", str.getAtime()); //开奖时间
            map.put("aCode", str.getAcode()); //开奖号码
            map.put("currentSales", str.getCurrentSales()); //本期销量
            map.put("jackpot", str.getJackpot()); //累计奖池
            map.put("notesNum", str.getNotesNum()); //注数
            map.put("perNoteMoney", str.getPerNoteMoney()); //每注金额
            map.put("awardName", str.getAwardName()); //奖项名称
            list.add(map);
        }

        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("排列五开奖信息 fiveAwards---------End---------------------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 往期排列三开奖信息
     */
    @RequestMapping(value = "historyThreeAwards", method = RequestMethod.POST)
    @ResponseBody
    public String historyThreeAwards(HttpServletRequest request) {
        logger.info("往期排列三开奖信息 historyThreeAwards--------Start-------------------");
        List list = new ArrayList();
        List<CdThreeAwards> threeRewardList = cdThreeAwardsService.getThreeAwards();
        for (CdThreeAwards str : threeRewardList) {
            Map map = new HashMap();
            map.put("weekDay", str.getWeekday()); //期次
            map.put("aCode", str.getAcode()); //开奖号码

            list.add(map);
        }
        Map dataMap = new HashMap();
//        Map theLast = new HashMap();
        CdThreeAwards last = threeRewardList.get(threeRewardList.size() - 1);
        int weekDayInt = Integer.parseInt(last.getWeekday()) + 1;
        String lastWeekDay = String.valueOf(weekDayInt);
        dataMap.put("weekDay", lastWeekDay); //期次
        //获取当前时间
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(day);
        dataMap.put("aCode", today + " 19:30截止"); //开奖号码


        dataMap.put("list", list);
        logger.info("往期排列三开奖信息 threeAwards---------End---------------------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 往期排列五开奖信息
     */
    @RequestMapping(value = "historyFiveAwards", method = RequestMethod.POST)
    @ResponseBody
    public String historyFiveAwards(HttpServletRequest request) {
        logger.info("往期排列五开奖信息 historyFiveAwards--------Start-------------------");
        List list = new ArrayList();
        List<CdFiveAwards> fiveList = cdFiveAwardsService.getFiveAwards();
        for (CdFiveAwards str : fiveList) {
            Map map = new HashMap();
            map.put("weekDay", str.getWeekday()); //期次
            map.put("aCode", str.getAcode()); //开奖号码
            list.add(map);
        }
        Map dataMap = new HashMap();
        //Map theLast = new HashMap();
        CdFiveAwards last = fiveList.get(fiveList.size() - 1);
        int weekDayInt = Integer.parseInt(last.getWeekday()) + 1;
        String lastWeekDay = String.valueOf(weekDayInt);
        dataMap.put("weekDay", lastWeekDay); //期次
        //获取当前时间
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(day);
        dataMap.put("aCode", today + " 19:30截止"); //开奖号码
        //list.add(theLast);


        dataMap.put("list", list);
        logger.info("往期排列五开奖信息 historyFiveAwards---------End---------------------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 大乐透往期开奖
     */
    @RequestMapping(value = "historyLottoReward", method = RequestMethod.POST)
    @ResponseBody
    public String historyLottoReward(HttpServletRequest request) {
        logger.info("大乐透往期开奖信息 historyLottoReward--------Start-------------------");
        List list = new ArrayList();
        List<CdLottoReward> lottoRewardList = cdLottoRewardService.getLottoReward();
        List<String> dateList = new ArrayList<>();
        for (CdLottoReward str : lottoRewardList) {
            Map map = new HashMap();
            map.put("matchId", str.getMatchId()); //期次
            map.put("number", str.getNumber()); //中奖号码
            dateList.add(str.getMatchId());
            list.add(map);
        }
        String lastMatch = dateList.get(0);
        String lastMatchId = lastMatch.substring(1, 8);
        int newMatchId = Integer.parseInt(lastMatchId) + 1;
        String newMatchIdStr = String.valueOf(newMatchId);
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("weekDay", newMatchIdStr);
        //获取当前时间
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(day);
        String[] days = today.split("-");
        int y = Integer.parseInt(days[0]);
        int m = Integer.parseInt(days[1]);
        int d = Integer.parseInt(days[2]);
        if (m == 1 || m == 2) {
            m += 12;
            y--;
        }
        int iWeek = (d + 2 * m + 3 * (m + 1) / 5 + y + y / 4 - y / 100 + y / 400) % 7;
        Calendar c = Calendar.getInstance();
        if (iWeek == 1 || iWeek == 4 || iWeek == 6) {
            c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
            day = c.getTime();
            today = df.format(day);
        } else if (iWeek == 3) {
            c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
            c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
            day = c.getTime();
            today = df.format(day);
        }
        dataMap.put("aCode", today + " 19:30截止"); //开奖号码
        logger.info("大乐透往期开奖信息 historyLottoReward---------End---------------------");
        return HttpResultUtil.successJson(dataMap);
    }

}