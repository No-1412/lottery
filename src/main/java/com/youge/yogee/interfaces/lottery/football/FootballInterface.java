package com.youge.yogee.interfaces.lottery.football;

import com.youge.yogee.common.utils.FundModel;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cfootballdouble.entity.CdFootBallDouble;
import com.youge.yogee.modules.cfootballdouble.service.CdFootBallDoubleService;
import com.youge.yogee.modules.cfootballgoal.entity.CdFootBallGoal;
import com.youge.yogee.modules.cfootballgoal.service.CdFootBallGoalService;
import com.youge.yogee.modules.cfootballmixed.entity.CdFootballMixed;
import com.youge.yogee.modules.cfootballmixed.service.CdFootballMixedService;
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
 * Created by Administrator on 2018/1/8.
 */
@Controller
@RequestMapping("${frontPath}")
public class FootballInterface {

    private static final Logger logger = LoggerFactory.getLogger(FootballInterface.class);

    @Autowired
    private CdFootballMixedService cdFootballMixedService;
    @Autowired
    private CdSuccessFailService cdSuccessFailService;
    @Autowired
    private CdFootBallGoalService cdFootBallGoalService;
    @Autowired
    private CdFootBallDoubleService cdFootBallDoubleService;

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
            Map<String, Object> maps = new HashMap<>();
            List cdFootballMixedList = cdFootballMixedService.findByALL(ListSize.get(i).toString());//按时间进行查询数据
            CdFootballMixed cdFootball = (CdFootballMixed) cdFootballMixedList.get(0);//获取比赛日期
            maps.put("addesc", cdFootball.getMatchsDate());//时间日期
            maps.put("allCount", cdFootballMixedList.size());//比赛总场次
            dataList.add(maps);
            List listbytime = new ArrayList();
            for (int y = 0; y < cdFootballMixedList.size(); y++) {//当天比赛场次List
                CdFootballMixed cdFootballMixed = (CdFootballMixed) cdFootballMixedList.get(y);
                Map<String, Object> map = new HashMap<>();
                map.put("mt", cdFootballMixed.getMatchId());//赛事场次
                map.put("itemid", cdFootballMixed.getMatchDate());//比赛时间
                map.put("mname", cdFootballMixed.getEventName());//赛事名称
                map.put("et", cdFootballMixed.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                map.put("hn", cdFootballMixed.getWinningName());//主队名称
                map.put("gn", cdFootballMixed.getDefeatedName());//客队名称
                map.put("matchId", cdFootballMixed.getMatchId());//期次

                //让球赔率
                if (StringUtils.isEmpty(cdFootballMixed.getNotConcedepointsOdds().replaceAll(",", ""))) {
                    map.put("spf", "-,-,-");//让球赔率
                } else {
                    map.put("spf", cdFootballMixed.getNotConcedepointsOdds());//非让球赔率
                }
                //让球赔率
                if (StringUtils.isEmpty(cdFootballMixed.getConcedepointsOdds().replaceAll(",", ""))) {
                    map.put("rpf", "-,-,-");//让球赔率
                } else {
                    map.put("rpf", cdFootballMixed.getConcedepointsOdds());
                }

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
                int dgs = FundModel.FT_DG_RQSPF.getFundNum() & Integer.parseInt(cdFootballMixed.getIsale());
                if (dgs > 0) {
                    map.put("isSingleLet", "1");//是否单关让球 1是 0不是
                } else {
                    map.put("isSingleLet", "0");//是否单关让球 1是 0不是
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
                    int rqs = FundModel.FT_DG_RQSPF.getFundNum() & Integer.parseInt(cdFootballMixed.getIsale());
                    if (dgs > 0) {//单关判断
                        Map map = new HashMap();
                        maps.put("addesc", cdFootball.getMatchsDate());//时间日期
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
                        // map.put("spfscale", cdFootballMixed.getNotConcedepointsRatio());//非让球投注比例

                        //让球赔率
                        if (StringUtils.isEmpty(cdFootballMixed.getNotConcedepointsOdds())) {
                            map.put("spfscale", "-,-,-");//让球赔率
                        } else {
                            map.put("spfscale", cdFootballMixed.getNotConcedepointsOdds());//非让球赔率
                        }


                        if (rqs > 1) {
                            map.put("isSingleLet", "1");//是否单关让球 1是 0不是
                        } else {
                            map.put("isSingleLet", "0");//是否单关让球 1是 0不是
                        }
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
                        map.put("rpf", cdFootballMixed.getConcedepointsOdds());//让球赔率
                        map.put("hm", cdFootballMixed.getWinningRank());//主队排名
                        map.put("gm", cdFootballMixed.getDefeatedRank());//客队排名
                        map.put("history", cdFootballMixed.getHistoryWinningSurpass());//主队历史交锋
                        map.put("htn", cdFootballMixed.getRecentWinningSurpass());//主队近期战绩
                        map.put("gtn", cdFootballMixed.getRecentDefeatedSurpass());//客队近期战绩
                        //map.put("rqspfscale", cdFootballMixed.getConcedepointsRatio());//让球投注比例

                        //让球赔率
                        if (StringUtils.isEmpty(cdFootballMixed.getConcedepointsRatio())) {
                            map.put("rqspfscale", "-,-,-");//让球赔率
                        } else {
                            map.put("rqspfscale", cdFootballMixed.getNotConcedepointsOdds());//非让球赔率
                        }

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

    /**
     * 足彩--胜负彩,任选九数据接口(非竞彩足球)
     */
    @RequestMapping(value = "successFail", method = RequestMethod.POST)
    @ResponseBody
    public String successFail(HttpServletRequest request) {
        logger.info("successFail  足彩胜负彩,任选九(非竞彩足球)---------Start---------");

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
