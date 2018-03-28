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
    @Autowired
    private CdBtOutcomeService cdBtOutcomeService;
    @Autowired
    private CdBtFutureService cdBtFutureService;
    @Autowired
    private CdBbLiveService cdBbLiveService;

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
     * wangsong
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
     * 篮球详情
     * wangsong
     * 20180126
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "listBtDetail", method = RequestMethod.POST)
    @ResponseBody
    public String listBtDetail(HttpServletRequest request) {
        logger.info("listBtDetail  篮球详情---------Start---------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String itemid = (String) jsonData.get("itemid");//比赛id
        Map dataMap = new HashMap();
        List hlist = new ArrayList();
        List glist = new ArrayList();
        List historyList = new ArrayList();
        List hFutureList = new ArrayList();
        List gFutureList = new ArrayList();
        Map map = new HashMap();
        int hs = 0;//主队主场胜
//        int hp = 0;//主队主场平
        int hf = 0;//主队主场负
        int gs = 0;//客队主场胜
//        int gp = 0;//客队主场平
        int gf = 0;//客队主场负
        int hhs = 0;//历史主队胜
        int hhp = 0;//历史主队平
        int hhf = 0;//历史主队负
        List<CdBasketballMixed> cdft = cdBasketballMixedService.getByItem(itemid);
        if (cdft.size() == 0) {
            return HttpResultUtil.errorJson("请更新数据");
        }
        if ("美职篮".equals(cdft.get(0).getEventName()) || "欧篮联".equals(cdft.get(0).getEventName())) {

            String hn = cdft.get(0).getWinningName();//主队名称
            String gn = cdft.get(0).getDefeatedName();//客队名称
            logger.info("listBtDetail  篮球近期战绩详情---------Start---------");
            //region Description


            List<CdBtOutcome> hnList = cdBtOutcomeService.findById(itemid, hn, "0");//主队近期战绩
            if (hnList != null) {
                for (CdBtOutcome cd : hnList) {
                    map = new HashMap();
                    map.put("mname", cd.getEventName());//赛事名称
                    map.put("hn", cd.getHn());//主队
                    map.put("gn", cd.getGn());//客队
                    map.put("hgsc", cd.getHgsc());//主客队比分
                    map.put("allscSize", cd.getAllscSize());//大小分
                    map.put("mt", cd.getMt());//时间
                    map.put("letsc", cd.getLetsc());//让分
                    map.put("result", cd.getResult());//胜负
                    //主队胜
                    if ("胜".equals(cd.getResult())) {
                        hs++;
                    } else if ("负".equals(cd.getResult())) {
                        hf++;
                    }

                    hlist.add(map);
                }

                int[] scoreArray = {hs, hf};
                dataMap.put("hnScoreArray", scoreArray);

            }
            List<CdBtOutcome> gnList = cdBtOutcomeService.findById(itemid, gn, "0");//客队近期战绩
            if (gnList != null) {

                for (CdBtOutcome cd : gnList) {
                    map = new HashMap();
                    map.put("mname", cd.getEventName());//赛事名称
                    map.put("hn", cd.getHn());//主队
                    map.put("gn", cd.getGn());//客队
                    map.put("hgsc", cd.getHgsc());//主客队比分
                    map.put("allscSize", cd.getAllscSize());//大小分
                    map.put("mt", cd.getMt());//时间
                    map.put("letsc", cd.getLetsc());//让分
                    map.put("result", cd.getResult());//胜负
                    //客队胜
                    if ("胜".equals(cd.getResult())) {
                        gs++;
                    } else if ("负".equals(cd.getResult())) {
                        gf++;
                    }
                    glist.add(map);
                }

                int[] scoreArray = {gs, gf};
                dataMap.put("gnScoreArray", scoreArray);
            }
            //endregion
            logger.info("listBtDetail  篮球近期战绩详情---------End---------");

            logger.info("listBtDetail  篮球历史战绩详情---------Start---------");
            //region Description
            List<CdBtOutcome> cdHList = cdBtOutcomeService.findById(itemid, gn, "1");//历史战绩
            if (cdHList != null) {


                for (CdBtOutcome cd : cdHList) {
                    map = new HashMap();
                    map.put("mname", cd.getEventName());//赛事名称
                    map.put("hn", cd.getHn());//主队
                    map.put("gn", cd.getGn());//客队
                    map.put("hgsc", cd.getHgsc());//主客队比分
                    map.put("allscSize", cd.getAllscSize());//大小分
                    map.put("mt", cd.getMt());//时间
                    map.put("letsc", cd.getLetsc());//让分
                    map.put("result", cd.getResult());//胜负
                    //主队胜
                    if ("胜".equals(cd.getResult())) {
                        hhs++;
                    } else if ("负".equals(cd.getResult())) {
                        hhf++;
                    } else {
                        hhp++;
                    }
                    historyList.add(map);

                    int[] scoreArray = {hhs, hhp, hhf};
                    dataMap.put("historyScoreArray", scoreArray);

                }

            }
            //endregion
            logger.info("listBtDetail  篮球历史战绩详情---------End---------");

            logger.info("listBtDetail  篮球未来赛事详情---------Start---------");
            //region Description

            List<CdBtFuture> cdFutureList = cdBtFutureService.findById(itemid);
            for (CdBtFuture cd : cdFutureList) {
                map = new HashMap();
                if ("0".equals(cd.getOutcomeType())) {//赛事 0主队1客队
                    if (hFutureList.size() == 3) {//查询3条未来赛事
                        continue;
                    }
                    map.put("mname", cd.getEventName());//赛事名称
                    map.put("hn", cd.getHn());//主队
                    map.put("gn", cd.getGn());//客队
                    map.put("time", cd.getMt());//比赛日期
                    map.put("host", cd.getLater());//相隔天数
                    hFutureList.add(map);
                } else {
                    if (gFutureList.size() == 3) {
                        continue;
                    }
                    map.put("mname", cd.getEventName());//赛事名称
                    map.put("hn", cd.getHn());//主队
                    map.put("gn", cd.getGn());//客队
                    map.put("time", cd.getMt());//比赛日期
                    map.put("host", cd.getLater());//相隔天数
                    gFutureList.add(map);
                }

            }

            //endregion
            logger.info("listBtDetail  篮球未来赛事详情---------End---------");
        } else {
            return HttpResultUtil.errorJson("无数据");
        }
        dataMap.put("glist", glist);//客队近期
        dataMap.put("hlist", hlist);//主队近期
        dataMap.put("historyList", historyList);//历史战绩
        dataMap.put("hFutureList", hFutureList);//主队未来赛事
        dataMap.put("gFutureList", gFutureList);//客队未来赛事

        logger.info("listBtDetail  篮球详情---------Start---------");
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


    /**
     * 通过itemid查询篮球实况
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getBtLiveById", method = RequestMethod.POST)
    public String getBtLiveById(HttpServletRequest request) {
        logger.info("getBtLiveById  获取篮球实况---------Start---------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String itemid = (String) jsonData.get("itemid");
        if (StringUtils.isEmpty(itemid)) {
            logger.error("itemid为空");
            return HttpResultUtil.errorJson("itemid为空");
        }

        CdBbLive cdBbLive = cdBbLiveService.findByMatchId(itemid);
        if (cdBbLive == null) {
            return HttpResultUtil.errorJson("比赛不存在");
        }


        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("hnScore", cdBbLive.getHnScore().split(","));
        dataMap.put("gnScore", cdBbLive.getGnScore().split(","));
        dataMap.put("hnSkill", StringUtils.split(cdBbLive.getHnSkill(), ","));
        dataMap.put("gnSkill", StringUtils.split(cdBbLive.getGnSkill(), ","));
        dataMap.put("hnPlayer", StringUtils.split(cdBbLive.getHnPlayer(), "|"));
        dataMap.put("gnPlayer", StringUtils.split(cdBbLive.getGnPlayer(), "|"));


        logger.info("getBtMatchDetailById 获取篮球详情---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

}
