package com.youge.yogee.interfaces.lottery.basketball;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cbbalreadyfinsh.entity.CdBbAlreadyFinsh;
import com.youge.yogee.modules.cbbalreadyfinsh.service.CdBbAlreadyFinshService;
import com.youge.yogee.modules.cbblive.entity.CdBbLive;
import com.youge.yogee.modules.cbblive.service.CdBbLiveService;
import com.youge.yogee.modules.cbblogo.service.CdBbLogoService;
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
import com.youge.yogee.modules.cbtfuture.entity.CdBtFuture;
import com.youge.yogee.modules.cbtfuture.service.CdBtFutureService;
import com.youge.yogee.modules.cbtoutcome.entity.CdBtOutcome;
import com.youge.yogee.modules.cbtoutcome.service.CdBtOutcomeService;
import com.youge.yogee.modules.cfbalreadyfinish.entity.CdFbAlreadyFinish;
import com.youge.yogee.modules.cfbalreadyfinish.service.CdFbAlreadyFinishService;
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
    @Autowired
    private CdBbLogoService cdBbLogoService;
    @Autowired
    private CdBtOutcomeService cdBtOutcomeService;
    @Autowired
    private CdBtFutureService cdBtFutureService;
    @Autowired
    private CdBbLiveService cdBbLiveService;
    @Autowired
    private CdFbAlreadyFinishService cdFbAlreadyFinishService;

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


        List<Map> list = new ArrayList<>();
        for (CdBbAlreadyFinsh str : dataList) {
            Map<String, Object> map = new HashMap<>();
            map.put("zid", str.getZid());//队Id
            map.put("type", str.getType());
            map.put("ln", str.getLn());//赛事类型
            map.put("hf", str.getHf());//主队分数
            map.put("hn", str.getHn());//主队
            map.put("gn", str.getGn());//客队
            map.put("gf", str.getGf());//客队分数
            map.put("day", str.getDay());//日期
            map.put("matchId", str.getMatchId());//场次id
            map.put("hnImg", cdBbLogoService.findLogo(str.getHn()));//主队LOGO
            map.put("gnImg", cdBbLogoService.findLogo(str.getGn()));//客队LOGO
            map.put("itemid", str.getItemid());
            list.add(map);
        }
        Map<String, Object> dataMap = new HashMap<>();
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
        List<Map> list = new ArrayList<>();
        for (CdBbNotFinsh str : dataList) {
            Map<String, Object> map = new HashMap<>();
            map.put("zid", str.getZid());//队Id
            map.put("type", str.getType());
            map.put("ln", str.getLn());//赛事类型
            map.put("hn", str.getHn());//主队
            map.put("gn", str.getGn());//客队
            map.put("day", str.getDay());//日期
            map.put("matchId", str.getMatchId());//场次id
            map.put("hnImg", cdBbLogoService.findLogo(str.getHn()));//主队LOGO
            map.put("gnImg", cdBbLogoService.findLogo(str.getGn()));//客队LOGO
            map.put("itemid", str.getItemid());
            map.put("hf", str.getHf());//主队分数
            map.put("gf", str.getGf());//客队分数

            //查询是否收藏
            CdBbNotFinishCollection cbnfc = cdBbNotFinishCollectionService.findByMatIdAndUid(str.getZid(), uid);
            if (cbnfc != null) {
                map.put("col", "1");
            } else {
                map.put("col", "0");
            }
            list.add(map);
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", list);
        logger.info("bbNotFinsh  篮球未完赛---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

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

        CdBbNotFinsh cdBbNotFinsh = cdBbNotFinshService.getMatchByItemId(itemId);
        if (cdBbNotFinsh == null) {
            CdFbAlreadyFinish cdFbAlreadyFinish = cdFbAlreadyFinishService.getMatchByItemId(itemId);
            if (cdFbAlreadyFinish == null) {
                return HttpResultUtil.errorJson("数据未更新");
            } else {
                pkMap.put("hn", cdFbAlreadyFinish.getHn());
                pkMap.put("vn", cdFbAlreadyFinish.getGn());
            }

        } else {
            pkMap.put("hn", cdBbNotFinsh.getHn());
            pkMap.put("vn", cdBbNotFinsh.getGn());
        }

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

            if (cdBbStrengthpkAverage.getTeam().equals("1")) {//主队
                averageHnList.add(averageMap);
            } else {
                averageVnList.add(averageMap);//客队
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
     * 通过itemid查询篮球实况
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


    /**
     * 篮球详情(分析)
     * 20180126
     */
    @RequestMapping(value = "listBtDetail", method = RequestMethod.POST)
    @ResponseBody
    public String listBtDetail(HttpServletRequest request) {
        logger.info("listBtDetail  篮球详情---------Start---------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }

        String itemid = (String) jsonData.get("itemid");//比赛id
        if (StringUtils.isEmpty(itemid)) {
            logger.error("itemid为空");
            return HttpResultUtil.errorJson("itemid为空");
        }
        Map<String, Object> dataMap = new HashMap<>();

        int hs = 0;//主队主场胜
        int hf = 0;//主队主场负
        int gs = 0;//客队主场胜
        int gf = 0;//客队主场负
        int hhs = 0;//历史主队胜
        int hhp = 0;//历史主队平
        int hhf = 0;//历史主队负

        String hn;//主队名称
        String gn;//客队名称
        CdBbNotFinsh cdBbNotFinsh = cdBbNotFinshService.getMatchByItemId(itemid);
        if (cdBbNotFinsh == null) {
            CdFbAlreadyFinish cdFbAlreadyFinish = cdFbAlreadyFinishService.getMatchByItemId(itemid);
            if (cdFbAlreadyFinish == null) {
                return HttpResultUtil.errorJson("数据未更新");
            } else {
                hn = cdFbAlreadyFinish.getHn();
                gn = cdFbAlreadyFinish.getGn();
            }
        } else {
            hn = cdBbNotFinsh.getHn();
            gn = cdBbNotFinsh.getGn();
        }

        logger.info("listBtDetail  篮球近期战绩详情---------Start---------");

        //主队近期战绩
        List<Map> hlist = new ArrayList<>();
        List<CdBtOutcome> hnList = cdBtOutcomeService.findById(itemid, hn, "0");
        if (hnList != null) {
            for (CdBtOutcome cd : hnList) {
                Map<String, Object> map = new HashMap<>();
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

        //客队近期战绩
        List<Map> glist = new ArrayList<>();
        List<CdBtOutcome> gnList = cdBtOutcomeService.findById(itemid, gn, "0");
        if (gnList != null) {
            for (CdBtOutcome cd : gnList) {
                Map<String, Object> map = new HashMap<>();
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
        logger.info("listBtDetail  篮球近期战绩详情---------End---------");

        logger.info("listBtDetail  篮球历史战绩详情---------Start---------");
        //历史战绩
        List<CdBtOutcome> cdHList = cdBtOutcomeService.findById(itemid, gn, "1");
        List<Map> historyList = new ArrayList<>();
        if (cdHList != null) {
            for (CdBtOutcome cd : cdHList) {
                Map<String, Object> map = new HashMap<>();
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
        logger.info("listBtDetail  篮球历史战绩详情---------End---------");

        logger.info("listBtDetail  篮球未来赛事详情---------Start---------");
        List<Map> hFutureList = new ArrayList<>();
        List<Map> gFutureList = new ArrayList<>();
        List<CdBtFuture> cdFutureList = cdBtFutureService.findById(itemid);
        for (CdBtFuture cd : cdFutureList) {
            Map<String, Object> map = new HashMap<>();
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
        dataMap.put("glist", glist);//客队近期
        dataMap.put("hlist", hlist);//主队近期
        dataMap.put("historyList", historyList);//历史战绩
        dataMap.put("hFutureList", hFutureList);//主队未来赛事
        dataMap.put("gFutureList", gFutureList);//客队未来赛事

        logger.info("listBtDetail  篮球详情---------Start---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 未完赛比赛关注
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
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            uid = "";
        }
        List<CdBbNotFinsh> list = cdBbNotFinishCollectionService.findColByUid(uid);
        List cList = new ArrayList<>();
        if (list.size() > 0) {
            for (CdBbNotFinsh str : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("col", "1");
                map.put("zid", str.getZid());//队Id
                map.put("type", str.getType());//赛事类型
                map.put("hn", str.getHn());//主队
                map.put("gn", str.getGn());//客队
                map.put("day", str.getDay());//日期
                map.put("matchId", str.getMatchId());//场次id
                map.put("hnImg", cdBbLogoService.findLogo(str.getHn()));//主队LOGO
                map.put("gnImg", cdBbLogoService.findLogo(str.getGn()));//客队LOGO
                map.put("itemid", str.getItemid());
                cList.add(map);
            }
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", list);
        logger.info("bbNotFinshHasCol---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 通过itemid查询篮球主队客队比分 开赛时间
     */
    @ResponseBody
    @RequestMapping(value = "getBasketballMatchTitle", method = RequestMethod.POST)
    public String getBasketballMatchTitle(HttpServletRequest request) {
        logger.info("getBasketballMatchTitle--------Start---------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        Map<String, Object> dataMap = new HashMap<>();
        String itemid = (String) jsonData.get("itemid");
        if (StringUtils.isEmpty(itemid)) {
            logger.error("itemid为空");
            return HttpResultUtil.errorJson("itemid为空");
        }
        //1未完赛 2已完赛
        String type = (String) jsonData.get("type");
        if (StringUtils.isEmpty(type)) {
            logger.error("type为空");
            return HttpResultUtil.errorJson("type为空");
        }

        if ("2".equals(type)) {
            CdBbAlreadyFinsh cbaf = cdBbAlreadyFinshService.getMatchByItemId(itemid);
            if (cbaf != null) {
                dataMap.put("host", cbaf.getHn());//主队
                dataMap.put("guest", cbaf.getGn());//客队
                dataMap.put("middle", cbaf.getHf() + "-" + cbaf.getGf());//比分
            }
        } else {
            CdBbNotFinsh cbnf = cdBbNotFinshService.getMatchByItemId(itemid);
            if (cbnf != null) {
                dataMap.put("host", cbnf.getHn());//主队
                dataMap.put("guest", cbnf.getGn());//客队
                dataMap.put("middle", cbnf.getDay());//时间
            }
        }

        logger.info("getBasketballMatchTitle-------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


}
