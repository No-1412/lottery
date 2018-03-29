package com.youge.yogee.interfaces.lottery.basketball;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cbbalreadyfinsh.entity.CdBbAlreadyFinsh;
import com.youge.yogee.modules.cbbalreadyfinsh.service.CdBbAlreadyFinshService;
import com.youge.yogee.modules.cbblive.entity.CdBbLive;
import com.youge.yogee.modules.cbbnotfinsh.entity.CdBbNotFinishCollection;
import com.youge.yogee.modules.cbbnotfinsh.entity.CdBbNotFinsh;
import com.youge.yogee.modules.cbbnotfinsh.service.CdBbNotFinishCollectionService;
import com.youge.yogee.modules.cbbnotfinsh.service.CdBbNotFinshService;
import com.youge.yogee.modules.cbbstrengthpk.entity.CdBbStrengthpk;
import com.youge.yogee.modules.cbbstrengthpk.entity.CdBbStrengthpkAverage;
import com.youge.yogee.modules.cbbstrengthpk.entity.CdBbStrengthpkInjury;
import com.youge.yogee.modules.cbbstrengthpk.service.CdBbStrengthpkAverageService;
import com.youge.yogee.modules.cbbstrengthpk.service.CdBbStrengthpkInjuryService;
import com.youge.yogee.modules.cbbstrengthpk.service.CdBbStrengthpkService;
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
 * Created by Liyuan on 2018-1-23 0023.
 */
@Controller
@RequestMapping("${frontPath}")
public class BasketballMatchInterface {
    private static final Logger logger = LoggerFactory.getLogger(BasketballMatchInterface.class);
    @Autowired
    private CdBbStrengthpkService cdBbStrengthpkService;
    @Autowired
    private CdBbAlreadyFinshService cdBbAlreadyFinshService;
    @Autowired
    private CdBbNotFinshService cdBbNotFinshService;
    @Autowired
    private CdBbStrengthpkAverageService cdBbStrengthpkAverageService;
    @Autowired
    private CdBbStrengthpkInjuryService cdBbStrengthpkInjuryService;
    @Autowired
    private CdBbNotFinishCollectionService cdBbNotFinishCollectionService;

    /**
     * 篮球实力PK数据接口
     */
    @RequestMapping(value = "bbStrengthPk", method = RequestMethod.POST)
    @ResponseBody
    public String bbStrengthPk(HttpServletRequest request) {
        logger.info("bbStrengthPk  篮球实力PK数据---------Start---------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String itemId = (String) jsonData.get("itemid");
        if (StringUtils.isEmpty(itemId)) {
            logger.error("iteamid为空");
            return HttpResultUtil.errorJson("iteamid为空");
        }

        //球队实力
        CdBbStrengthpk cdBbStrengthpk = cdBbStrengthpkService.getStrengthPk(itemId);
        if (cdBbStrengthpk == null) {
            return HttpResultUtil.errorJson("数据未更新");
        }
        Map<String, Object> pkMap = new HashMap<>();
        pkMap.put("hn", cdBbStrengthpk.getHn());
        pkMap.put("vn", cdBbStrengthpk.getVn());

        List<String> leftList = new ArrayList<>();
        List<String> rightList = new ArrayList<>();
        String[] statisticsArray = cdBbStrengthpk.getAveragingStatistics().split(",");
        for (String aStatisticsArray : statisticsArray) {
            String[] split = aStatisticsArray.split("/");
            leftList.add(split[0]);
            rightList.add(split[1]);
        }

        //球员场均数据
        List<Map<String, Object>> averageHnList = new ArrayList<>();
        List<Map<String, Object>> averageVnList = new ArrayList<>();
        List<CdBbStrengthpkAverage> cdBbStrengthpkAverageList = cdBbStrengthpkAverageService.getStrengthPk(itemId);
        for (CdBbStrengthpkAverage cdBbStrengthpkAverage : cdBbStrengthpkAverageList) {
            Map<String, Object> averageMap = new HashMap<>();
            averageMap.put("name", cdBbStrengthpkAverage.getName());//球员名字
            averageMap.put("location", cdBbStrengthpkAverage.getLocation());//位置
            averageMap.put("stage", cdBbStrengthpkAverage.getStage());//出场
            averageMap.put("time", cdBbStrengthpkAverage.getTime());//时间
            averageMap.put("score", cdBbStrengthpkAverage.getScore());//得分
            averageMap.put("backboard", cdBbStrengthpkAverage.getBackboard());//篮板
            averageMap.put("assist", cdBbStrengthpkAverage.getAssist());//助攻
            averageMap.put("steal", cdBbStrengthpkAverage.getSteal());//抢断
            averageMap.put("shoot", cdBbStrengthpkAverage.getShoot());//投篮
            averageMap.put("trisection", cdBbStrengthpkAverage.getTrisection());//三分
            averageMap.put("penalty", cdBbStrengthpkAverage.getPenalty());//罚球
            averageMap.put("close", cdBbStrengthpkAverage.getClose());//封盖

            if (cdBbStrengthpkAverage.getTeam().equals("1")) {
                averageHnList.add(averageMap);
            } else {
                averageVnList.add(averageMap);
            }
        }

        //伤停
        List<Map<String, Object>> injuryList = new ArrayList<>();
        List<CdBbStrengthpkInjury> cdBbStrengthpkInjuryList = cdBbStrengthpkInjuryService.getStrengthPk(itemId);
        for (CdBbStrengthpkInjury cdBbStrengthpkInjury : cdBbStrengthpkInjuryList) {
            Map<String, Object> injuryMap = new HashMap<>();
            injuryMap.put("team", cdBbStrengthpkInjury.getTeam());//球队
            injuryMap.put("player", cdBbStrengthpkInjury.getPlayer());//球员
            injuryMap.put("position", cdBbStrengthpkInjury.getPosition());//位置
            injuryMap.put("reason", cdBbStrengthpkInjury.getReason());//原因
            injuryMap.put("date", cdBbStrengthpkInjury.getDate());//日期
            injuryMap.put("remark", cdBbStrengthpkInjury.getRemarks());//备注

            injuryList.add(injuryMap);
        }


        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("pkMap", pkMap);
        dataMap.put("leftList", leftList);
        dataMap.put("rightList", rightList);
        dataMap.put("averageHnList", averageHnList);
        dataMap.put("averageVnList", averageVnList);
        dataMap.put("injuryList", injuryList);

        logger.info("bbStrengthPk  篮球实力PK---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 篮球已完赛接口
     */
    @RequestMapping(value = "bbAlreadyFinsh", method = RequestMethod.POST)
    @ResponseBody
    public String bbAlreadyFinsh(HttpServletRequest request) {
        logger.info("bbAlreadyFinsh  篮球已完赛---------start---------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            logger.error("total为空");
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            logger.error("count为空");
            return HttpResultUtil.errorJson("count为空");
        }
        List<CdBbAlreadyFinsh> dataList = cdBbAlreadyFinshService.getBbFinshed(total, count);
        List list = new ArrayList();
        for (CdBbAlreadyFinsh str : dataList) {
            Map map = new HashMap();
            map.put("hn", str.getHn());//主队名
            map.put("zid", str.getZid());//队Id
            map.put("type", str.getType());//赛事类型
            map.put("hf", str.getHf());//主队分数
            map.put("hn", str.getHn());//主队
            map.put("gn", str.getGn());//客队
            map.put("gf", str.getGf());//客队分数
            map.put("day", str.getDay());//日期
            map.put("matchId", str.getMatchId());//场次id
            map.put("hnImg", str.getHnImg());//主队LOGO
            map.put("gnImg", str.getGnImg());//客队LOGO
            map.put("itemid", str.getItemid());
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("bbAlreadyFinsh  篮球已完赛---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 篮球未完赛接口
     */
    @RequestMapping(value = "bbNotFinsh", method = RequestMethod.POST)
    @ResponseBody
    public String bbNotFinsh(HttpServletRequest request) {
        logger.info("bbNotFinsh  篮球未完赛---------start---------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            logger.error("total为空");
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            logger.error("count为空");
            return HttpResultUtil.errorJson("count为空");
        }
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            uid = "";
        }

        List<CdBbNotFinsh> dataList = cdBbNotFinshService.getBbFinshed(total, count);
        List list = new ArrayList();
        for (CdBbNotFinsh str : dataList) {
            Map map = new HashMap();
            CdBbNotFinishCollection cbnfc = cdBbNotFinishCollectionService.findByMatIdAndUid(str.getZid(), uid);
            if (cbnfc != null) {
                map.put("col", "1");
            } else {
                map.put("col", "0");
            }

            map.put("hn", str.getHn());//主队名
            map.put("zid", str.getZid());//队Id
            map.put("type", str.getType());//赛事类型
            map.put("hn", str.getHn());//主队
            map.put("gn", str.getGn());//客队
            map.put("day", str.getDay());//日期
            map.put("matchId", str.getMatchId());//场次id
            map.put("hnImg", str.getHnImg());//主队LOGO
            map.put("gnImg", str.getGnImg());//客队LOGO
            map.put("hf",str.getHf());//主队分数
            map.put("gf",str.getGf());//客队分数
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("bbNotFinsh  篮球未完赛---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 未完赛比赛收藏
     *
     * @param
     */
    @RequestMapping(value = "basketNotFinishedCollection", method = RequestMethod.POST)
    @ResponseBody
    public String basketNotFinishedCollection(HttpServletRequest request) {
        logger.info("basketNotFinishedCollection---------Start---------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }

        String zid = (String) jsonData.get("zid");
        if (StringUtils.isEmpty(zid)) {
            logger.error("zid为空");
            return HttpResultUtil.errorJson("zid为空");
        }

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }


        CdBbNotFinishCollection cbnfc = cdBbNotFinishCollectionService.findByMatIdAndUid(zid, uid);
        if (cbnfc == null) {
            CdBbNotFinishCollection cdBbNotFinishCollection = new CdBbNotFinishCollection();
            cdBbNotFinishCollection.setZid(zid);
            cdBbNotFinishCollection.setUid(uid);
            cdBbNotFinishCollectionService.save(cdBbNotFinishCollection);
        } else {
            cdBbNotFinishCollectionService.delete(cbnfc.getId());
        }
        Map dataMap = new HashMap();
        logger.info("basketNotFinishedCollection---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 篮球已关注列表
     */
    @RequestMapping(value = "bbNotFinshHasCol", method = RequestMethod.POST)
    @ResponseBody
    public String bbNotFinshHasCol(HttpServletRequest request) {
        logger.info("bbNotFinshHasCol ---------start---------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            logger.error("total为空");
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            logger.error("count为空");
            return HttpResultUtil.errorJson("count为空");
        }
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            uid = "";
        }

        List<CdBbNotFinsh> dataList = cdBbNotFinshService.getBbFinshed(total, count);
        List list = new ArrayList();
        for (CdBbNotFinsh str : dataList) {
            Map map = new HashMap();
            CdBbNotFinishCollection cbnfc = cdBbNotFinishCollectionService.findByMatIdAndUid(str.getZid(), uid);
            if (cbnfc != null) {
                map.put("col", "1");
                map.put("hn", str.getHn());//主队名
                map.put("zid", str.getZid());//队Id
                map.put("type", str.getType());//赛事类型
                map.put("hn", str.getHn());//主队
                map.put("gn", str.getGn());//客队
                map.put("day", str.getDay());//日期
                map.put("matchId", str.getMatchId());//场次id
                map.put("hnImg", str.getHnImg());//主队LOGO
                map.put("gnImg", str.getGnImg());//客队LOGO
                list.add(map);
            }
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("bbNotFinshHasCol---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

//    /**
//     * 通过itemid查询篮球实况
//     *
//     * @param request
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "getBasketballMatchTitle", method = RequestMethod.POST)
//    public String getBasketballMatchTitle(HttpServletRequest request) {
//        logger.info("getBasketballMatchTitle--------Start---------");
//
//        Map jsonData = HttpServletRequestUtils.readJsonData(request);
//        if (jsonData == null) {
//            return HttpResultUtil.errorJson("json格式错误");
//        }
//        String itemid = (String) jsonData.get("itemid");
//        if (StringUtils.isEmpty(itemid)) {
//            logger.error("itemid为空");
//            return HttpResultUtil.errorJson("itemid为空");
//        }
//        //已完赛
//        String type = (String) jsonData.get("type");
//        if (StringUtils.isEmpty(type)) {
//            logger.error("type为空");
//            return HttpResultUtil.errorJson("type为空");
//        }
//
//        CdBbLive cdBbLive = cdBbLiveService.findByMatchId(itemid);
//        if (cdBbLive == null) {
//            return HttpResultUtil.errorJson("比赛不存在");
//        }
//
//
//        Map<String, Object> dataMap = new HashMap<>();
//        dataMap.put("hnScore", cdBbLive.getHnScore().split(","));
//        dataMap.put("gnScore", cdBbLive.getGnScore().split(","));
//        dataMap.put("hnSkill", StringUtils.split(cdBbLive.getHnSkill(), ","));
//        dataMap.put("gnSkill", StringUtils.split(cdBbLive.getGnSkill(), ","));
//        dataMap.put("hnPlayer", StringUtils.split(cdBbLive.getHnPlayer(), "|"));
//        dataMap.put("gnPlayer", StringUtils.split(cdBbLive.getGnPlayer(), "|"));
//
//
//        logger.info("getBtMatchDetailById 获取篮球详情---------End---------");
//        return HttpResultUtil.successJson(dataMap);
//    }


}
