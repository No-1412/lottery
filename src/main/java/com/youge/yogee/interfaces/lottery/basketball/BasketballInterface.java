package com.youge.yogee.interfaces.lottery.basketball;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cbasketballmixed.entity.CdBasketballMixed;
import com.youge.yogee.modules.cbasketballmixed.service.CdBasketballMixedService;
import com.youge.yogee.modules.cbblive.entity.CdBbLive;
import com.youge.yogee.modules.cbblive.service.CdBbLiveService;
import com.youge.yogee.modules.cbtfuture.entity.CdBtFuture;
import com.youge.yogee.modules.cbtfuture.service.CdBtFutureService;
import com.youge.yogee.modules.cbtoutcome.entity.CdBtOutcome;
import com.youge.yogee.modules.cbtoutcome.service.CdBtOutcomeService;
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
 * Created by Administrator on 2018/1/22.
 */
@Controller
@RequestMapping("${frontPath}")
public class BasketballInterface {
    private static final Logger logger = LoggerFactory.getLogger(BasketballInterface.class);
    @Autowired
    private CdBasketballMixedService cdBasketballMixedService;



    /**
     * 篮球混投
     * 180122
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listBt", method = RequestMethod.POST)
    public String listBt(HttpServletRequest request) {
        logger.info("listBt  蓝球混投---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdBasketballMixedService.getRqSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdList = cdBasketballMixedService.findDgMixed(ListSize.get(i).toString());//按时间进行查询数据
            CdBasketballMixed cds = (CdBasketballMixed) cdList.get(0);//获取比赛日期
            maps.put("addesc", cds.getMatchsDate());//时间日期
            maps.put("allCount", cdList.size());//比赛总场次
            dataList.add(maps);
            List listbytime = new ArrayList();
            for (int y = 0; y < cdList.size(); y++) {//当天比赛场次List
                CdBasketballMixed cd = (CdBasketballMixed) cdList.get(y);
                Map map = new HashMap();
                map.put("name", cd.getMatchId());//赛事场次
                map.put("mt", cd.getMatchDate());//比赛时间
                map.put("itemid", cd.getItemid());//比赛时间ID
                map.put("zid", cd.getZid());//比赛详细信息传的参数
                map.put("mname", cd.getEventName());//赛事名称
                map.put("et", cd.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                map.put("hn", cd.getWinningName());//主队名称
                map.put("gn", cd.getDefeatedName());//客队名称
                map.put("matchId", cd.getMatchId());//期次

                map.put("sf", cd.getVictoryordefeatOdds());//胜负:主负主胜赔率',
                map.put("spo", cd.getSpreadOdds());//让分胜负:主负主胜赔率
                map.put("dxo", cd.getSizeOdds());//大小分赔率
                map.put("sfc", cd.getSurpassScoreGap());//胜分差主负主胜
                map.put("close", cd.getClose());//让分
                map.put("value", cd.getZclose());//大小分分值

                map.put("hm", cd.getWinningRank());//主队排名
                map.put("gm", cd.getDefeatedRank());//客队排名
                if (StringUtils.isNotEmpty(cd.getRecentWinningSurpass())) {
                    map.put("hrn", cd.getRecentWinningSurpass() + "胜" + cd.getRecentWinningDefeat() + "负");//主队近期战绩
                    map.put("grn", cd.getRecentDefeatedSurpass() + "胜" + cd.getRecentDefeatedDefeat() + "负");//客队近期战绩
                    map.put("history", "主队" + cd.getHistoryWinningSurpass() + "胜" + cd.getHistoryWinningDefeat() + "负");//历史交锋
                } else {
                    map.put("hrn", "-");//主队近期战绩
                    map.put("grn", "-");//客队近期战绩
                    map.put("history", "-");//历史交锋
                }
                if ("79".equals(cd.getIsale())) {
                    map.put("isSingle", "0");//是否单关 1是 0不是
                } else {
                    map.put("isSingle", "1");//是否单关 1是 0不是
                }
                listbytime.add(map);
            }
            list.add(listbytime);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);

        logger.info("listBt  蓝球混投---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     *
     * 篮球胜负
     * 180122
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listBtSurpassFlatDefeat", method = RequestMethod.POST)
    public String listBtSurpassFlatDefeat(HttpServletRequest request) {
        logger.info("listBtSurpassFlatDefeat  蓝球胜负平---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdBasketballMixedService.getRqSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdList = cdBasketballMixedService.findListByTime(ListSize.get(i).toString());//按时间进行查询数据
            CdBasketballMixed cds = (CdBasketballMixed) cdList.get(0);//获取比赛日期
            maps.put("addesc", cds.getMatchsDate());//时间日期
            maps.put("allCount", cdList.size());//比赛总场次
            dataList.add(maps);
            List listbytime = new ArrayList();
            for (int y = 0; y < cdList.size(); y++) {//当天比赛场次List
                CdBasketballMixed cd = (CdBasketballMixed) cdList.get(y);
                Map map = new HashMap();
                map.put("name", cd.getMatchId());//赛事场次
                map.put("mt", cd.getMatchDate());//比赛时间
                map.put("itemid", cd.getItemid());//比赛时间ID
                map.put("zid", cd.getZid());//比赛详细信息传的参数
                map.put("mname", cd.getEventName());//赛事名称
                map.put("et", cd.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                map.put("hn", cd.getWinningName());//主队名称
                map.put("gn", cd.getDefeatedName());//客队名称
                map.put("sf", cd.getVictoryordefeatOdds());//胜负:主负主胜赔率',
                map.put("hm", cd.getWinningRank());//主队排名
                map.put("gm", cd.getDefeatedRank());//客队排名
                if (StringUtils.isNotEmpty(cd.getRecentWinningSurpass())) {
                    map.put("hrn", cd.getRecentWinningSurpass() + "胜" + cd.getRecentWinningDefeat() + "负");//主队近期战绩
                    map.put("grn", cd.getRecentDefeatedSurpass() + "胜" + cd.getRecentDefeatedDefeat() + "负");//客队近期战绩
                    map.put("history", "主队" + cd.getHistoryWinningSurpass() + "胜" + cd.getHistoryWinningDefeat() + "负");//历史交锋
                } else {
                    map.put("hrn", "-");//主队近期战绩
                    map.put("grn", "-");//客队近期战绩
                    map.put("history", "-");//历史交锋
                }
                map.put("close", cd.getClose());//让球
                listbytime.add(map);
            }
            list.add(listbytime);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);

        logger.info("listBtSurpassFlatDefeat  篮球胜负平---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 查询篮球让分
     * wangsong
     * 2018_01_10
     *
     * @return
     */
    @RequestMapping(value = "listBtRScoer", method = RequestMethod.POST)
    @ResponseBody
    public String listBtRScoer(HttpServletRequest request) {
        logger.info("listBtRScoer  篮球让分---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdBasketballMixedService.getRqSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdList = cdBasketballMixedService.findByName(ListSize.get(i).toString(), "spreadOdds", ",");//按时间进行查询数据
            CdBasketballMixed cdball = (CdBasketballMixed) cdList.get(0);//获取比赛日期
            maps.put("addesc", cdball.getMatchsDate());//时间日期
            maps.put("allCount", cdList.size());//比赛总场次
            dataList.add(maps);
            List listbytime = new ArrayList();
            for (int y = 0; y < cdList.size(); y++) {//当天比赛场次List
                CdBasketballMixed cd = (CdBasketballMixed) cdList.get(y);
                Map map = new HashMap();
                map.put("name", cd.getMatchId());//赛事场次
                map.put("mt", cd.getMatchDate());//比赛时间
                map.put("itemid", cd.getItemid());//比赛时间ID
                map.put("zid", cd.getZid());//比赛详细信息传的参数
                map.put("mname", cd.getEventName());//赛事名称
                map.put("et", cd.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                map.put("hn", cd.getWinningName());//主队名称
                map.put("gn", cd.getDefeatedName());//客队名称
                map.put("dxf", cd.getSizeOdds());//胜负:主负主胜赔率',
                map.put("hm", cd.getWinningRank());//主队排名
                map.put("gm", cd.getDefeatedRank());//客队排名
                map.put("htn", cd.getRecentWinningSurpass());//主队近期战绩
                map.put("gtn", cd.getRecentDefeatedSurpass());//客队近期战绩
                map.put("htf", cd.getRecentWinningDefeat());//主队近期战绩败
                map.put("gtf", cd.getRecentDefeatedDefeat());//客队近期战绩败
                map.put("ghistory", cd.getHistoryWinningDefeat());//客队历史交锋
                map.put("hhistory", cd.getHistoryWinningSurpass());//主队历史交锋
                map.put("close", cd.getClose());//加分
                listbytime.add(map);
            }
            list.add(listbytime);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listBtRScoer  篮球让分---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 查询篮球大小分
     * wangsong
     * 2018_01_10
     *
     * @return
     */
    @RequestMapping(value = "listBtSizeScoer", method = RequestMethod.POST)
    @ResponseBody
    public String listBtSizeScoer(HttpServletRequest request) {
        logger.info("listBtSizeScoer  篮球大小分---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdBasketballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdList = cdBasketballMixedService.findByName(ListSize.get(i).toString(), "sizeOdds", ",");//按时间进行查询数据
            CdBasketballMixed cdball = (CdBasketballMixed) cdList.get(0);//获取比赛日期
            maps.put("addesc", cdball.getMatchsDate());//时间日期
            maps.put("allCount", cdList.size());//比赛总场次
            dataList.add(maps);
            List listbytime = new ArrayList();
            for (int y = 0; y < cdList.size(); y++) {//当天比赛场次List
                CdBasketballMixed cd = (CdBasketballMixed) cdList.get(y);
                Map map = new HashMap();
                map.put("name", cd.getMatchId());//赛事场次
                map.put("mt", cd.getMatchDate());//比赛时间
                map.put("itemid", cd.getItemid());//比赛时间ID
                map.put("zid", cd.getZid());//比赛详细信息传的参数
                map.put("mname", cd.getEventName());//赛事名称
                map.put("et", cd.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                map.put("hn", cd.getWinningName());//主队名称
                map.put("gn", cd.getDefeatedName());//客队名称
                map.put("hm", cd.getWinningRank());//主队排名
                map.put("gm", cd.getDefeatedRank());//客队排名
                map.put("htn", cd.getRecentWinningSurpass());//主队近期战绩
                map.put("gtn", cd.getRecentDefeatedSurpass());//客队近期战绩
                map.put("htf", cd.getRecentWinningDefeat());//主队近期战绩败
                map.put("gtf", cd.getRecentDefeatedDefeat());//客队近期战绩败
                map.put("ghistory", cd.getHistoryWinningDefeat());//客队历史交锋
                map.put("hhistory", cd.getHistoryWinningSurpass());//主队历史交锋
                map.put("zclose", cd.getZclose());//分数
                map.put("dxf", cd.getSizeOdds());////大小赔率
                listbytime.add(map);
            }
            list.add(listbytime);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listBtSizeScoer  篮球大小分---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 查询篮球胜分差
     * wangsong
     * 2018_01_10
     *
     * @return
     */
    @RequestMapping(value = "listBtScoreGap", method = RequestMethod.POST)
    @ResponseBody
    public String listBtScoreGap(HttpServletRequest request) {
        logger.info("listBtScoreGap  篮球胜分差---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdBasketballMixedService.getSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdList = cdBasketballMixedService.findByName(ListSize.get(i).toString(), "surpassScoreGap", ",,,,,,,,,,,");//按时间进行查询数据
            if (cdList.size() > 0) {
                CdBasketballMixed cdball = (CdBasketballMixed) cdList.get(0);//获取比赛日期
                maps.put("addesc", cdball.getMatchsDate());//时间日期
                maps.put("allCount", cdList.size());//比赛总场次
                dataList.add(maps);
                List listbytime = new ArrayList();
                for (int y = 0; y < cdList.size(); y++) {//当天比赛场次List
                    CdBasketballMixed cd = (CdBasketballMixed) cdList.get(y);
                    Map map = new HashMap();
                    map.put("name", cd.getMatchId());//赛事场次
                    map.put("mt", cd.getMatchDate());//比赛时间
                    map.put("itemid", cd.getItemid());//比赛时间ID
                    map.put("zid", cd.getZid());//比赛详细信息传的参数
                    map.put("mname", cd.getEventName());//赛事名称
                    map.put("et", cd.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                    map.put("hn", cd.getWinningName());//主队名称
                    map.put("gn", cd.getDefeatedName());//客队名称
                    map.put("hm", cd.getWinningRank());//主队排名
                    map.put("gm", cd.getDefeatedRank());//客队排名
                    if (StringUtils.isNotEmpty(cd.getRecentWinningSurpass())) {
                        map.put("hrn", cd.getRecentWinningSurpass() + "胜" + cd.getRecentWinningDefeat() + "负");//主队近期战绩
                        map.put("grn", cd.getRecentDefeatedSurpass() + "胜" + cd.getRecentDefeatedDefeat() + "负");//客队近期战绩
                        map.put("history", "主队" + cd.getHistoryWinningSurpass() + "胜" + cd.getHistoryWinningDefeat() + "负");//历史交锋
                    } else {
                        map.put("hrn", "-");//主队近期战绩
                        map.put("grn", "-");//客队近期战绩
                        map.put("history", "-");//历史交锋
                    }

                    map.put("sfc", cd.getSurpassScoreGap());//胜分差主负主胜
                    map.put("close", cd.getClose());//让球
                    listbytime.add(map);
                }
                list.add(listbytime);
            }
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);
        logger.info("listBtScoreGap  篮球胜分差---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * wangsong
     * 篮球单关混投
     * 180124
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listBtDgMixed", method = RequestMethod.POST)
    public String listBtDgMixed(HttpServletRequest request) {
        logger.info("listBtDgMixed  篮球单关混投---------Start---------");
        List list = new ArrayList();
        List dataList = new ArrayList();
        List ListSize = cdBasketballMixedService.getRqSize();//去重复查询时间
        if (ListSize.size() == 0) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        for (int i = 0; i < ListSize.size(); i++) {
            Map maps = new HashMap();
            List cdList = cdBasketballMixedService.findDgMixed(ListSize.get(i).toString());//按时间进行查询数据
            CdBasketballMixed cds = (CdBasketballMixed) cdList.get(0);//获取比赛日期
            maps.put("addesc", cds.getMatchsDate());//时间日期
            maps.put("allCount", cdList.size());//比赛总场次
            dataList.add(maps);
            List listbytime = new ArrayList();
            for (int y = 0; y < cdList.size(); y++) {//当天比赛场次List
                CdBasketballMixed cd = (CdBasketballMixed) cdList.get(y);
                Map map = new HashMap();
                map.put("name", cd.getMatchId());//赛事场次
                map.put("mt", cd.getMatchDate());//比赛时间
                map.put("itemid", cd.getItemid());//比赛时间ID
                map.put("zid", cd.getZid());//比赛详细信息传的参数
                map.put("mname", cd.getEventName());//赛事名称
                map.put("et", cd.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
                map.put("hn", cd.getWinningName());//主队名称
                map.put("gn", cd.getDefeatedName());//客队名称
                map.put("sf", "未开售");//胜负:主负主胜赔率',
                map.put("dxf", "未开售");//大小赔率
                map.put("rfsf", "未开售");//让分:主负主胜赔率
                map.put("sfc", cd.getSurpassScoreGap());////胜分差主负主胜
                map.put("hm", cd.getWinningRank());//主队排名
                map.put("gm", cd.getDefeatedRank());//客队排名
                map.put("htn", cd.getRecentWinningSurpass());//主队近期战绩
                map.put("gtn", cd.getRecentDefeatedSurpass());//客队近期战绩
                map.put("htf", cd.getRecentWinningDefeat());//主队近期战绩败
                map.put("gtf", cd.getRecentDefeatedDefeat());//客队近期战绩败
                map.put("ghistory", cd.getHistoryWinningDefeat());//客队历史交锋
                map.put("hhistory", cd.getHistoryWinningSurpass());//主队历史交锋
                listbytime.add(map);
            }
            list.add(listbytime);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        dataMap.put("dataList", dataList);

        logger.info("listBtDgMixed  篮球单关混投---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }



    /**
     * 通过matchId查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getBtMatchDetailById", method = RequestMethod.POST)
    public String getBtMatchDetailById(HttpServletRequest request) {
        logger.info("getBtMatchDetailById  获取篮球详情---------Start---------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String matchId = (String) jsonData.get("matchId");
        if (StringUtils.isEmpty(matchId)) {
            logger.error("matchId为空");
            return HttpResultUtil.errorJson("buyWays为空");
        }

        CdBasketballMixed cd = cdBasketballMixedService.findByMatchId(matchId);
        if (cd == null) {
            return HttpResultUtil.errorJson("比赛不存在");
        }


        Map map = new HashMap();
        map.put("name", cd.getMatchId());//赛事场次
        map.put("mt", cd.getMatchDate());//比赛时间
        map.put("itemid", cd.getItemid());//比赛时间ID
        map.put("zid", cd.getZid());//比赛详细信息传的参数
        map.put("mname", cd.getEventName());//赛事名称
        map.put("et", cd.getTimeEndsale().substring(11, 16));//截止时间2018-01-09 16:35:00
        map.put("hn", cd.getWinningName());//主队名称
        map.put("gn", cd.getDefeatedName());//客队名称
        map.put("sf", "未开售");//胜负:主负主胜赔率',
        map.put("dxf", "未开售");//大小赔率
        map.put("rfsf", "未开售");//让分:主负主胜赔率
        map.put("sfc", cd.getSurpassScoreGap());////胜分差主负主胜
        map.put("hm", cd.getWinningRank());//主队排名
        map.put("gm", cd.getDefeatedRank());//客队排名
        map.put("htn", cd.getRecentWinningSurpass());//主队近期战绩
        map.put("gtn", cd.getRecentDefeatedSurpass());//客队近期战绩
        map.put("htf", cd.getRecentWinningDefeat());//主队近期战绩败
        map.put("gtf", cd.getRecentDefeatedDefeat());//客队近期战绩败
        map.put("ghistory", cd.getHistoryWinningDefeat());//客队历史交锋
        map.put("hhistory", cd.getHistoryWinningSurpass());//主队历史交锋


        logger.info("getBtMatchDetailById 获取篮球详情---------End---------");
        return HttpResultUtil.successJson(map);
    }


}
