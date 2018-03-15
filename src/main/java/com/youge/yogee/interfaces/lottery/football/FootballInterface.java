package com.youge.yogee.interfaces.lottery.football;

import com.youge.yogee.common.utils.FundModel;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.lottery.help.HelpCenterInterface;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cbasketballmixed.entity.CdBasketballMixed;
import com.youge.yogee.modules.cfbfuture.entity.CdFbFuture;
import com.youge.yogee.modules.cfbfuture.service.CdFbFutureService;
import com.youge.yogee.modules.cfboutcome.entity.CdFbOutcome;
import com.youge.yogee.modules.cfboutcome.service.CdFbOutcomeService;
import com.youge.yogee.modules.cfbscoer.entity.CdFbScoer;
import com.youge.yogee.modules.cfbscoer.service.CdFbScoerService;
import com.youge.yogee.modules.cfootballmixed.entity.CdFootballMixed;
import com.youge.yogee.modules.cfootballmixed.service.CdFootballMixedService;
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
 * Created by Administrator on 2018/1/8.
 */
@Controller
@RequestMapping("${frontPath}")
public class FootballInterface {

    private static final Logger logger = LoggerFactory.getLogger(HelpCenterInterface.class);

    @Autowired
    private CdFootballMixedService cdFootballMixedService;
    @Autowired
    private CdFbOutcomeService cdFbOutcomeService;
    @Autowired
    private CdFbScoerService cdFbScoerService;
    @Autowired
    private CdFbFutureService cdFbFutureService;


    //region Description

    /**
     * 查询足球混投
     *
     * @return
     */
    @RequestMapping(value = "listFb", method = RequestMethod.POST)
    @ResponseBody
    public String listFb(HttpServletRequest request) {
        logger.info("listFb  足彩混投---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdFootballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdFootballMixedList = cdFootballMixedService.findByALL(ListSize.get(i).toString());//按时间进行查询数据
            CdFootballMixed cdFootball = (CdFootballMixed) cdFootballMixedList.get(0);//获取比赛日期
            maps.put("addesc", cdFootball.getMatchsDate());//时间日期
            maps.put("allCount", cdFootballMixedList.size());//比赛总场次
            dataList.add(maps);
            List listbytime = new ArrayList();
            for (int y = 0; y < cdFootballMixedList.size(); y++) {//当天比赛场次List
                CdFootballMixed cdFootballMixed = (CdFootballMixed) cdFootballMixedList.get(y);
                Map map = new HashMap();
                map.put("mt", cdFootballMixed.getMatchId());//赛事场次
                map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
                map.put("mname", cdFootballMixed.getEventName());//赛事名称
                map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                map.put("hn", cdFootballMixed.getWinningName());//主队名称
                map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
                map.put("matchId", cdFootballMixed.getMatchId());//期次

                map.put("spf", cdFootballMixed.getNotConcedepointsOdds());//非让球赔率
                map.put("rpf", cdFootballMixed.getConcedepointsOdds());//让球赔率
                map.put("sod", cdFootballMixed.getScoreOdds());//比分赔率
                map.put("aod", cdFootballMixed.getAllOdds());//总进球赔率
                map.put("hod", cdFootballMixed.getHalfOdds());//半全场赔率
                map.put("close", cdFootballMixed.getClose());//让球

                map.put("hm", cdFootballMixed.getWinningRank());//主队排名
                map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
                map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
                map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
                map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
                map.put("spfscale", cdFootballMixed.getNotConcedepointsRatio());//非让球投注比例
                if ("991".equals(cdFootballMixed.getIsale())) {
                    map.put("isSingle", "1");//是否单关 1是 0不是
                } else {
                    map.put("isSingle", "0");//是否单关 1是 0不是
                }
                listbytime.add(map);
            }
            list.add(listbytime);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listFb  足彩混投---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 查询足彩胜平负
     * wangsong
     * 2017_12_20
     *
     * @return
     */
    @RequestMapping(value = "listSurpassFlatDefeat", method = RequestMethod.POST)
    @ResponseBody
    public String listSurpassFlatDefeat(HttpServletRequest request) {
        logger.info("listSurpassFlatDefeat  足彩胜负平---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdFootballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdFootballMixedList = cdFootballMixedService.findByName(ListSize.get(i).toString(), "notConcedepointsRatio", "--,--,--");//按时间进行查询数据
            CdFootballMixed cdFootball = (CdFootballMixed) cdFootballMixedList.get(0);//获取比赛日期
            maps.put("addesc", cdFootball.getMatchsDate());//时间日期
            maps.put("allCount", cdFootballMixedList.size());//比赛总场次
            dataList.add(maps);
            List listbytime = new ArrayList();
            for (int y = 0; y < cdFootballMixedList.size(); y++) {//当天比赛场次List
                CdFootballMixed cdFootballMixed = (CdFootballMixed) cdFootballMixedList.get(y);
                Map map = new HashMap();
                map.put("mt", cdFootballMixed.getMatchId());//赛事场次
                map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
                map.put("mname", cdFootballMixed.getEventName());//赛事名称
                map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                map.put("hn", cdFootballMixed.getWinningName());//主队名称
                map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
                map.put("spf", cdFootballMixed.getNotConcedepointsOdds());//非让球赔率
                map.put("hm", cdFootballMixed.getWinningRank());//主队排名
                map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
                map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
                map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
                map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
                map.put("spfscale", cdFootballMixed.getNotConcedepointsRatio());//非让球投注比例
                listbytime.add(map);
            }
            list.add(listbytime);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listSurpassFlatDefeat  足彩胜负平---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 查询足彩让球胜平负
     * wangsong
     * 2018_01_10
     *
     * @return
     */
    @RequestMapping(value = "listRqSurpassFlatDefeat", method = RequestMethod.POST)
    @ResponseBody
    public String listRqSurpassFlatDefeat(HttpServletRequest request) {
        logger.info("listRqSurpassFlatDefeat  足彩让球胜平负---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdFootballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdFootballMixedList = cdFootballMixedService.findByName(ListSize.get(i).toString(), "concedepointsOdds", "--,--,--");//按时间进行查询数据
            CdFootballMixed cdFootball = (CdFootballMixed) cdFootballMixedList.get(0);//获取比赛日期
            maps.put("addesc", cdFootball.getMatchsDate());//时间日期
            maps.put("allCount", cdFootballMixedList.size());//比赛总场次
            dataList.add(maps);
            List listbytime = new ArrayList();
            for (int y = 0; y < cdFootballMixedList.size(); y++) {//当天比赛场次List
                CdFootballMixed cdFootballMixed = (CdFootballMixed) cdFootballMixedList.get(y);
                Map map = new HashMap();
                map.put("mt", cdFootballMixed.getMatchId());//赛事场次
                map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
                map.put("mname", cdFootballMixed.getEventName());//赛事名称
                map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                map.put("hn", cdFootballMixed.getWinningName());//主队名称
                map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
                map.put("rqspf", cdFootballMixed.getConcedepointsOdds());//让球赔率
                map.put("hm", cdFootballMixed.getWinningRank());//主队排名
                map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
                map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
                map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
                map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
                map.put("rqspfscale", cdFootballMixed.getConcedepointsRatio());//让球投注比例
                map.put("close", cdFootballMixed.getClose());//加分
                listbytime.add(map);
            }
            list.add(listbytime);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listRqSurpassFlatDefeat  足彩让球胜平负---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 查询足彩半全场
     * wangsong
     * 2018_01_10
     *
     * @return
     */
    @RequestMapping(value = "listHalf", method = RequestMethod.POST)
    @ResponseBody
    public String listHalf(HttpServletRequest request) {
        logger.info("listHalf  足彩半全场---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdFootballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdFootballMixedList = cdFootballMixedService.findByName(ListSize.get(i).toString(), "halfOdds", "");//按时间进行查询数据
            CdFootballMixed cdFootball = (CdFootballMixed) cdFootballMixedList.get(0);//获取比赛日期
            maps.put("addesc", cdFootball.getMatchsDate());//时间日期
            maps.put("allCount", cdFootballMixedList.size());//比赛总场次
            dataList.add(maps);
            List listbytime = new ArrayList();
            for (int y = 0; y < cdFootballMixedList.size(); y++) {//当天比赛场次List
                CdFootballMixed cdFootballMixed = (CdFootballMixed) cdFootballMixedList.get(y);
                Map map = new HashMap();
                map.put("mt", cdFootballMixed.getMatchId());//赛事场次
                map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
                map.put("mname", cdFootballMixed.getEventName());//赛事名称
                map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                map.put("hn", cdFootballMixed.getWinningName());//主队名称
                map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
                map.put("hm", cdFootballMixed.getWinningRank());//主队排名
                map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
                map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
                map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
                map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
                map.put("rqspfscale", "--,--,--");//让球投注比例
                map.put("bqc", cdFootballMixed.getHalfOdds());//半全场赔率
                listbytime.add(map);
            }
            list.add(listbytime);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listHalf  足彩半全场---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 查询足彩比分
     * wangsong
     * 2018_01_10
     *
     * @return
     */
    @RequestMapping(value = "listFootbalScore", method = RequestMethod.POST)
    @ResponseBody
    public String listFootbalScore(HttpServletRequest request) {
        logger.info("listFootbalScore  足彩比分---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdFootballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdFootballMixedList = cdFootballMixedService.findByName(ListSize.get(i).toString(), "scoreOdds", "");//按时间进行查询数据
            CdFootballMixed cdFootball = (CdFootballMixed) cdFootballMixedList.get(0);//获取比赛日期
            maps.put("addesc", cdFootball.getMatchsDate());//时间日期
            maps.put("allCount", cdFootballMixedList.size());//比赛总场次
            dataList.add(maps);
            List listbytime = new ArrayList();
            for (int y = 0; y < cdFootballMixedList.size(); y++) {//当天比赛场次List
                CdFootballMixed cdFootballMixed = (CdFootballMixed) cdFootballMixedList.get(y);
                Map map = new HashMap();
                map.put("mt", cdFootballMixed.getMatchId());//赛事场次
                map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
                map.put("mname", cdFootballMixed.getEventName());//赛事名称
                map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                map.put("hn", cdFootballMixed.getWinningName());//主队名称
                map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
                map.put("hm", cdFootballMixed.getWinningRank());//主队排名
                map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
                map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
                map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
                map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
                map.put("rqspfscale", "--,--,--");//让球投注比例
                map.put("cbf", cdFootballMixed.getScoreOdds());//比分赔率
                listbytime.add(map);
            }
            list.add(listbytime);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listFootbalScore  足彩比分---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 查询足彩进球数
     * wangsong
     * 2018_01_10
     *
     * @return
     */
    @RequestMapping(value = "listFootbalAllOdds", method = RequestMethod.POST)
    @ResponseBody
    public String listFootbalAllOdds(HttpServletRequest request) {
        logger.info("listFootbalAllOdds  足彩进球数---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdFootballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdFootballMixedList = cdFootballMixedService.findByName(ListSize.get(i).toString(), "allOdds", "");//按时间进行查询数据
            CdFootballMixed cdFootball = (CdFootballMixed) cdFootballMixedList.get(0);//获取比赛日期
            maps.put("addesc", cdFootball.getMatchsDate());//时间日期
            maps.put("allCount", cdFootballMixedList.size());//比赛总场次
            dataList.add(maps);
            List listbytime = new ArrayList();
            for (int y = 0; y < cdFootballMixedList.size(); y++) {//当天比赛场次List
                CdFootballMixed cdFootballMixed = (CdFootballMixed) cdFootballMixedList.get(y);
                Map map = new HashMap();
                map.put("mt", cdFootballMixed.getMatchId());//赛事场次
                map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
                map.put("mname", cdFootballMixed.getEventName());//赛事名称
                map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                map.put("hn", cdFootballMixed.getWinningName());//主队名称
                map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
                map.put("hm", cdFootballMixed.getWinningRank());//主队排名
                map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
                map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
                map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
                map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
                map.put("rqspfscale", "--,--,--");//让球投注比例
                map.put("cbf", cdFootballMixed.getAllOdds());//总进球赔率
                listbytime.add(map);
            }


            list.add(listbytime);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listFootbalAllOdds  足彩进球数---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

//endregion

    //单关
    //region Description

    /**
     * 查询足球单关胜平负
     * wangsong
     * 2018_1_23
     *
     * @return
     */
    @RequestMapping(value = "listDgSurpassFlatDefeat", method = RequestMethod.POST)
    @ResponseBody
    public String listDgSurpassFlatDefeat(HttpServletRequest request) {
        logger.info("listDgSurpassFlatDefeat  足球单关胜负平---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdFootballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            int all = 0;//标记场次
            Map maps = new HashMap();
            List cdFootballMixedList = cdFootballMixedService.findByName(ListSize.get(i).toString(), "notConcedepointsRatio", "--,--,--");//按时间进行查询数据
            CdFootballMixed cdFootball = (CdFootballMixed) cdFootballMixedList.get(0);//获取比赛日期
            List listbytime = new ArrayList();
            for (int y = 0; y < cdFootballMixedList.size(); y++) {//当天比赛场次List
                CdFootballMixed cdFootballMixed = (CdFootballMixed) cdFootballMixedList.get(y);
                if (StringUtils.isNotEmpty(cdFootballMixed.getIsale())) {
                    int dgs = FundModel.FT_DG_SPF.getFundNum() & Integer.parseInt(cdFootballMixed.getIsale());
                    if (dgs > 0) {//单关判断
                        Map map = new HashMap();
                        maps.put("addesc", cdFootball.getMatchsDate());//时间日期
                        map.put("mt", cdFootballMixed.getMatchId());//赛事场次
                        map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
                        map.put("mname", cdFootballMixed.getEventName());//赛事名称
                        map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                        map.put("hn", cdFootballMixed.getWinningName());//主队名称
                        map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
                        map.put("spf", cdFootballMixed.getNotConcedepointsOdds());//非让球赔率
                        map.put("hm", cdFootballMixed.getWinningRank());//主队排名
                        map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
                        map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
                        map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
                        map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
                        map.put("spfscale", cdFootballMixed.getNotConcedepointsRatio());//非让球投注比例
                        listbytime.add(map);
                    }
                }
            }
            if (listbytime.size() > 0) {//小于0说明没有比赛
                list.add(listbytime);
                maps.put("allCount", listbytime.size());//比赛总场次
                dataList.add(maps);
            }
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listDgSurpassFlatDefeat  足球单关胜负平---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 查询足球单关让球胜平负
     * wangsong
     * 2018_01_10
     *
     * @return
     */
    @RequestMapping(value = "listDgRqSurpassFlatDefeat", method = RequestMethod.POST)
    @ResponseBody
    public String listDgRqSurpassFlatDefeat(HttpServletRequest request) {
        logger.info("listRqSurpassFlatDefeat  足球单关让球胜平负---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdFootballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdFootballMixedList = cdFootballMixedService.findByName(ListSize.get(i).toString(), "concedepointsOdds", "--,--,--");//按时间进行查询数据
            CdFootballMixed cdFootball = (CdFootballMixed) cdFootballMixedList.get(0);//获取比赛日期
            List listbytime = new ArrayList();
            for (int y = 0; y < cdFootballMixedList.size(); y++) {//当天比赛场次List
                CdFootballMixed cdFootballMixed = (CdFootballMixed) cdFootballMixedList.get(y);
                if (StringUtils.isNotEmpty(cdFootballMixed.getIsale())) {
                    int dgs = FundModel.FT_DG_RQSPF.getFundNum() & Integer.parseInt(cdFootballMixed.getIsale());
                    if (dgs > 0) {//单关判断
                        Map map = new HashMap();
                        maps.put("addesc", cdFootball.getMatchsDate());//时间日期
                        map.put("mt", cdFootballMixed.getMatchId());//赛事场次
                        map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
                        map.put("mname", cdFootballMixed.getEventName());//赛事名称
                        map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                        map.put("hn", cdFootballMixed.getWinningName());//主队名称
                        map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
                        map.put("rqspf", cdFootballMixed.getConcedepointsOdds());//让球赔率
                        map.put("hm", cdFootballMixed.getWinningRank());//主队排名
                        map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
                        map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
                        map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
                        map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
                        map.put("rqspfscale", cdFootballMixed.getConcedepointsRatio());//让球投注比例
                        map.put("close", cdFootballMixed.getClose());//加分
                        listbytime.add(map);
                    }
                }
            }
            if (listbytime.size() > 0) {//小于0说明没有比赛
                list.add(listbytime);
                maps.put("allCount", listbytime.size());//比赛总场次
                dataList.add(maps);
            }

        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listDgRqSurpassFlatDefeat  足球单关让球胜平负---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 查询足球单关半全场
     * wangsong
     * 2018_01_10
     *
     * @return
     */
    @RequestMapping(value = "listDgHalf", method = RequestMethod.POST)
    @ResponseBody
    public String listDgHalf(HttpServletRequest request) {
        logger.info("listHalf  足球单关半全场---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdFootballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            int all = 0;//标记场次
            Map maps = new HashMap();
            List cdFootballMixedList = cdFootballMixedService.findByName(ListSize.get(i).toString(), "halfOdds", "");//按时间进行查询数据
            CdFootballMixed cdFootball = (CdFootballMixed) cdFootballMixedList.get(0);//获取比赛日期
            List listbytime = new ArrayList();
            for (int y = 0; y < cdFootballMixedList.size(); y++) {//当天比赛场次List
                CdFootballMixed cdFootballMixed = (CdFootballMixed) cdFootballMixedList.get(y);
                int dgs = FundModel.FT_DG_BQC.getFundNum() & Integer.parseInt(cdFootballMixed.getIsale());
                if (dgs > 0) {//单关判断
                    Map map = new HashMap();
                    maps.put("addesc", cdFootball.getMatchsDate());//时间日期
                    map.put("mt", cdFootballMixed.getMatchId());//赛事场次
                    map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
                    map.put("mname", cdFootballMixed.getEventName());//赛事名称
                    map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                    map.put("hn", cdFootballMixed.getWinningName());//主队名称
                    map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
                    map.put("hm", cdFootballMixed.getWinningRank());//主队排名
                    map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
                    map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
                    map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
                    map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
                    map.put("rqspfscale", "--,--,--");//让球投注比例
                    map.put("bqc", cdFootballMixed.getHalfOdds());//半全场赔率
                    listbytime.add(map);
                }
            }
            if (listbytime.size() > 0) {//小于0说明没有比赛
                list.add(listbytime);
                maps.put("allCount", listbytime.size());//比赛总场次
                dataList.add(maps);
            }
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listDgHalf  足球单关半全场---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 查询足球单关比分
     * wangsong
     * 2018_01_23
     *
     * @return
     */
    @RequestMapping(value = "listDgFootbalScore", method = RequestMethod.POST)
    @ResponseBody
    public String listDgFootbalScore(HttpServletRequest request) {
        logger.info("listDgFootbalScore  足球单关比分---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdFootballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdFootballMixedList = cdFootballMixedService.findByName(ListSize.get(i).toString(), "scoreOdds", "");//按时间进行查询数据
            CdFootballMixed cdFootball = (CdFootballMixed) cdFootballMixedList.get(0);//获取比赛日期
            List listbytime = new ArrayList();
            for (int y = 0; y < cdFootballMixedList.size(); y++) {//当天比赛场次List
                CdFootballMixed cdFootballMixed = (CdFootballMixed) cdFootballMixedList.get(y);
                int dgs = FundModel.FT_DG_BF.getFundNum() & Integer.parseInt(cdFootballMixed.getIsale());
                if (dgs > 0) {//单关判断
                    Map map = new HashMap();
                    maps.put("addesc", cdFootball.getMatchsDate());//时间日期
                    map.put("mt", cdFootballMixed.getMatchId());//赛事场次
                    map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
                    map.put("mname", cdFootballMixed.getEventName());//赛事名称
                    map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                    map.put("hn", cdFootballMixed.getWinningName());//主队名称
                    map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
                    map.put("hm", cdFootballMixed.getWinningRank());//主队排名
                    map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
                    map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
                    map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
                    map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
                    map.put("rqspfscale", "--,--,--");//让球投注比例
                    map.put("cbf", cdFootballMixed.getScoreOdds());//比分赔率
                    listbytime.add(map);
                }
            }
            if (listbytime.size() > 0) {//小于0说明没有比赛
                list.add(listbytime);
                maps.put("allCount", listbytime.size());//比赛总场次
                dataList.add(maps);
            }
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listDgFootbalScore  足球单关比分---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 查询足球单关总进球
     * wangsong
     * 2018_01_10
     *
     * @return
     */
    @RequestMapping(value = "listDgFootbalAllOdds", method = RequestMethod.POST)
    @ResponseBody
    public String listDgFootbalAllOdds(HttpServletRequest request) {
        logger.info("listDgFootbalAllOdds  足球单关总进球---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdFootballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdFootballMixedList = cdFootballMixedService.findByName(ListSize.get(i).toString(), "allOdds", "");//按时间进行查询数据
            CdFootballMixed cdFootball = (CdFootballMixed) cdFootballMixedList.get(0);//获取比赛日期
            List listbytime = new ArrayList();
            for (int y = 0; y < cdFootballMixedList.size(); y++) {//当天比赛场次List
                CdFootballMixed cdFootballMixed = (CdFootballMixed) cdFootballMixedList.get(y);
                int dgs = FundModel.FT_DG_ZJQ.getFundNum() & Integer.parseInt(cdFootballMixed.getIsale());
                if (dgs > 0) {//单关判断
                    Map map = new HashMap();
                    maps.put("addesc", cdFootball.getMatchsDate());//时间日期
                    map.put("mt", cdFootballMixed.getMatchId());//赛事场次
                    map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
                    map.put("mname", cdFootballMixed.getEventName());//赛事名称
                    map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                    map.put("hn", cdFootballMixed.getWinningName());//主队名称
                    map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
                    map.put("hm", cdFootballMixed.getWinningRank());//主队排名
                    map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
                    map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
                    map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
                    map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
                    map.put("rqspfscale", "--,--,--");//让球投注比例
                    map.put("cbf", cdFootballMixed.getAllOdds());//总进球赔率
                    listbytime.add(map);
                }
            }
            if (listbytime.size() > 0) {//小于0说明没有比赛
                list.add(listbytime);
                maps.put("allCount", listbytime.size());//比赛总场次
                dataList.add(maps);
            }
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listDgFootbalAllOdds  足球单关总进球---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }
    //endregion

    /**
     * 足球详情
     * wangsong
     * 20180124
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "listFtDetail", method = RequestMethod.POST)
    @ResponseBody
    public String listFtDetail(HttpServletRequest request) {
        logger.info("listFtDetail  足球详情---------Start---------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String itemid = (String) jsonData.get("itemid");//比赛id
        Map dataMap = new HashMap();
        List hlist = new ArrayList();
        List glist = new ArrayList();
        List dataList = new ArrayList();
        List historyList = new ArrayList();
        List scoerList = new ArrayList();
        List hFutureList = new ArrayList();
        List gFutureList = new ArrayList();
        Map map = new HashMap();
        int hs = 0;//主队主场胜
        int hp = 0;//主队主场平
        int hf = 0;//主队主场负
        int gs = 0;//客队主场胜
        int gp = 0;//客队主场平
        int gf = 0;//客队主场负
        logger.info("listFtDetail  足球近期战绩详情---------Start---------");
        //region Description
        CdFootballMixed cdFootballMixed = cdFootballMixedService.getByItem(itemid);
        if (cdFootballMixed == null) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        String hn = cdFootballMixed.getWinningName();//主队名称
        String gn = cdFootballMixed.getDefeatedName();//客队名称

        //主队近期战绩
        List<CdFbOutcome> hnList = cdFbOutcomeService.findById(itemid, hn, "0");
        if (hnList.size() > 0) {
            for (CdFbOutcome cd : hnList) {
                map = new HashMap();
                map.put("mname", cd.getEventName());//赛事名称
                map.put("mt", cd.getMt());//时间
                map.put("hn", cd.getHn());//主队名称
                map.put("gn", cd.getGn());//客队名称
                map.put("score", cd.getHsc()+":"+cd.getGsc());//比分

                //主队胜
                if (hn.equals(cd.getHn())) {
                    //主队胜
                    if (Integer.parseInt(cd.getHsc()) > Integer.parseInt(cd.getGsc())) {
                        hs++;
                        map.put("result","胜");//胜负
                    } else if (Integer.parseInt(cd.getHsc()) == Integer.parseInt(cd.getGsc())) {//平
                        hp++;
                        map.put("result","平");//胜负
                    } else {
                        hf++;
                        map.put("result","负");//胜负
                    }
                } else {
                    if (Integer.parseInt(cd.getGsc()) > Integer.parseInt(cd.getHsc())) {
                        gs++;
                        map.put("result","胜");//胜负
                    } else if (Integer.parseInt(cd.getGsc()) == Integer.parseInt(cd.getHsc())) {//平
                        gp++;
                        map.put("result","平");//胜负
                    } else {
                        gf++;
                        map.put("result","负");//胜负
                    }
                }
                hlist.add(map);
            }
            int zs = hs + gs;//球队胜
            int zp = hp + gp;//球队平
            int zf = hf + gf;//球队负
            map = new HashMap();
            map.put("hs", hs);
            map.put("hp", hp);
            map.put("hf", hf);
            map.put("zs", zs);
            map.put("zp", zp);
            map.put("zf", zf);
            dataList.add(map);
        }

        //客场近期战绩
        List<CdFbOutcome> gnList = cdFbOutcomeService.findById(itemid, gn, "0");
        if (gnList.size()>0) {
            hs = 0;//记录主队主场胜
            hp = 0;//记录主队主场平
            hf = 0;//记录主队主场负
            gs = 0;//客队主场胜
            gp = 0;//客队主场平
            gf = 0;//客队主场负
            for (CdFbOutcome cd : gnList) {
                map = new HashMap();
                map.put("mname", cd.getEventName());//赛事名称
                map.put("mt", cd.getMt());//时间
                map.put("hn", cd.getHn());//主队名称
                map.put("gn", cd.getGn());//客队名称
                map.put("score", cd.getHsc()+":"+cd.getGsc());//比分

                if (gn.equals(cd.getGn())) {//判断主场
                    //客队胜
                    if (Integer.parseInt(cd.getGsc()) > Integer.parseInt(cd.getHsc())) {
                        gs++;
                        map.put("result","胜");//胜负
                    } else if (Integer.parseInt(cd.getGsc()) == Integer.parseInt(cd.getHsc())) {//平
                        gp++;
                        map.put("result","平");//胜负
                    } else {
                        gf++;
                        map.put("result","负");//胜负
                    }
                } else {
                    //主队胜
                    if (Integer.parseInt(cd.getHsc()) > Integer.parseInt(cd.getGsc())) {
                        hs++;
                        map.put("result","胜");//胜负
                    } else if (Integer.parseInt(cd.getHsc()) == Integer.parseInt(cd.getGsc())) {//平
                        hp++;
                        map.put("result","平");//胜负
                    } else {
                        hf++;
                        map.put("result","负");//胜负
                    }
                }
                glist.add(map);
            }
            int ks = hs + gs;//球队胜
            int kp = hp + gp;//球队平
            int kf = hf + gf;//球队负
            map = new HashMap();
            map.put("gs", gs);
            map.put("gp", gp);
            map.put("gf", gf);
            map.put("ks", ks);
            map.put("kp", kp);
            map.put("kf", kf);
            dataList.add(map);
        }
        logger.info("listFtDetail  足球近期战绩详情---------End---------");


        logger.info("listFtDetail  足球历史战绩详情---------Start---------");
        //历史交锋
        List<CdFbOutcome> cdHList = cdFbOutcomeService.findByOldTime(itemid);
        if (cdHList != null) {
            hs = 0;//记录主队主场胜
            hp = 0;//记录主队主场平
            hf = 0;//记录主队主场负
            gs = 0;//客队主场胜
            gp = 0;//客队主场平
            gf = 0;//客队主场负
            for (CdFbOutcome cd : cdHList) {
                map = new HashMap();
                map.put("mname", cd.getEventName());//赛事名称
                map.put("mt", cd.getMt());//时间
                map.put("hn", cd.getHn());//主队名称
                map.put("gn", cd.getGn());//客队名称
                map.put("score", cd.getHsc()+":"+cd.getGsc());//比分
                //主队胜
                if (hn.equals(cd.getHn())) {
                    //主队胜
                    if (Integer.parseInt(cd.getHsc()) > Integer.parseInt(cd.getGsc())) {
                        hs++;
                        map.put("result","胜");//胜负
                    } else if (Integer.parseInt(cd.getHsc()) == Integer.parseInt(cd.getGsc())) {//平
                        hp++;
                        map.put("result","平");//胜负
                    } else {
                        hf++;
                        map.put("result","负");//胜负
                    }
                } else {
                    if (Integer.parseInt(cd.getGsc()) > Integer.parseInt(cd.getHsc())) {
                        gs++;
                        map.put("result","胜");//胜负
                    } else if (Integer.parseInt(cd.getGsc()) == Integer.parseInt(cd.getHsc())) {//平
                        gp++;
                        map.put("result","平");//胜负
                    } else {
                        gf++;
                        map.put("result","负");//胜负
                    }
                }
                historyList.add(map);
                int lzs = hs + gs;//球队胜
                int lzp = hp + gp;//球队平
                int lzf = hf + gf;//球队负
                map = new HashMap();
                map.put("hs", hs);
                map.put("hp", hp);
                map.put("hf", hf);
                map.put("lzs", lzs);
                map.put("lzp", lzp);
                map.put("lzf", lzf);
                dataList.add(map);
            }
        }
        logger.info("listFtDetail  足球历史战绩详情---------End---------");

        logger.info("listFtDetail  足球积分详情---------Start---------");
        //region Description
        List<CdFbScoer> cdScoerList = cdFbScoerService.findById(itemid);//历史战绩
        for (CdFbScoer cd : cdScoerList) {
            map = new HashMap();
            map.put("rank", cd.getRank());//球队排名
            map.put("teamname", cd.getTeamname());//球队名称
            map.put("points", cd.getPoints());//球队积分
            map.put("scoerAll", cd.getScoerAll());//球队总成绩
            map.put("host", cd.getHost());//球队主场成绩
            map.put("guest", cd.getGuest());//球队客场成绩
            scoerList.add(map);
        }
        //endregion
        logger.info("listFtDetail  足球积分详情---------End---------");

        logger.info("listFtDetail  足球未来赛事详情---------Start---------");
        //region Description
//        CdFootballMixed cdFootballMixed = cdFootballMixedService.getByItem(itemid);
        //查询队id
        List<CdFbFuture> cdFutureName = cdFbFutureService.findByName(cdFootballMixed.getWinningName());
        String teamId = cdFutureName.get(0).getTeamHid();
        List<CdFbFuture> cdFutureList = cdFbFutureService.findById(itemid);
        for (CdFbFuture cd : cdFutureList) {
            map = new HashMap();
            if (teamId.equals(cd.getTeamHid()) || teamId.equals(cd.getTeamAid())) {
                if (hFutureList.size() == 3) {//查询3条未来赛事
                    continue;
                }
                map.put("match_name", cd.getMatchName());//赛事名称
                map.put("time", cd.getTime());//比赛日期
                map.put("host", cd.getHost());//主队
                map.put("guest", cd.getGuest());//客队
                map.put("later", cd.getLater());//相隔天数
                hFutureList.add(map);
            } else {
                if (gFutureList.size() == 3) {
                    continue;
                }
                map.put("match_name", cd.getMatchName());//赛事名称
                map.put("time", cd.getTime());//比赛日期
                map.put("host", cd.getHost());//主队
                map.put("guest", cd.getGuest());//客队
                map.put("later", cd.getLater());//相隔天数
                gFutureList.add(map);
            }

        }
        //endregion
        logger.info("listFtDetail  足球未来赛事详情---------End---------");

        dataMap.put("glist", glist);//客队近期
        dataMap.put("hlist", hlist);//主队近期
        dataMap.put("historyList", historyList);//历史战绩
        dataMap.put("dataList", dataList);//比赛成绩
        dataMap.put("scoerList", scoerList);//战绩分数
        dataMap.put("hFutureList", hFutureList);//主队未来赛事
        dataMap.put("gFutureList", gFutureList);//客队未来赛事
        logger.info("listFtDetail  足球详情---------Start---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 通过matchId查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getFtMatchDetailById", method = RequestMethod.POST)
    public String getMatchDetailById(HttpServletRequest request) {
        logger.info("getFtMatchDetailById  获取足球详情---------Start---------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String matchId = (String) jsonData.get("matchId");
        if (StringUtils.isEmpty(matchId)) {
            logger.error("matchId为空");
            return HttpResultUtil.errorJson("buyWays为空");
        }

        CdFootballMixed cdFootballMixed = cdFootballMixedService.findByMatchId(matchId);
        if (cdFootballMixed == null) {
            return HttpResultUtil.errorJson("比赛不存在");
        }

        Map map = new HashMap();
        map.put("mt", cdFootballMixed.getMatchId());//赛事场次
        map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
        map.put("mname", cdFootballMixed.getEventName());//赛事名称
        map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
        map.put("hn", cdFootballMixed.getWinningName());//主队名称
        map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
        map.put("matchId", cdFootballMixed.getMatchId());//期次

        map.put("spf", cdFootballMixed.getNotConcedepointsOdds());//非让球赔率
        map.put("rpf", cdFootballMixed.getConcedepointsOdds());//让球赔率
        map.put("sod", cdFootballMixed.getScoreOdds());//比分赔率
        map.put("aod", cdFootballMixed.getAllOdds());//总进球赔率
        map.put("hod", cdFootballMixed.getHalfOdds());//半全场赔率
        map.put("close", cdFootballMixed.getClose());//让球

        map.put("hm", cdFootballMixed.getWinningRank());//主队排名
        map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
        map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
        map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
        map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
        map.put("spfscale", cdFootballMixed.getNotConcedepointsRatio());//非让球投注比例

        logger.info("getFtMatchDetailById 获取足球详情---------End---------");
        return HttpResultUtil.successJson(map);
    }


}
