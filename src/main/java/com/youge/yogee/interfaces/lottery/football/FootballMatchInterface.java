package com.youge.yogee.interfaces.lottery.football;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinishCollection;
import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinish;
import com.youge.yogee.modules.cfbnotfinish.service.CdFbNotFinishCollectionService;
import com.youge.yogee.modules.cfbnotfinish.service.CdFbNotFinishService;
import com.youge.yogee.modules.cfootballawards.entity.CdFootballAwards;
import com.youge.yogee.modules.cfootballawards.service.CdFootballAwardsService;
import com.youge.yogee.modules.cftlogo.service.CdFtLogoService;
import com.youge.yogee.modules.cftskill.entity.CdFtSkill;
import com.youge.yogee.modules.cftskill.service.CdFtSkillService;
import com.youge.yogee.modules.csceneecharts.entity.CdSceneEcharts;
import com.youge.yogee.modules.csceneecharts.service.CdSceneEchartsService;
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
 * @author liyuan
 * @ Created by wjc on 2018-1-23 0023.
 */
@Controller
@RequestMapping("${frontPath}")
public class FootballMatchInterface {
    private static final Logger logger = LoggerFactory.getLogger(FootballMatchInterface.class);

    @Autowired
    private CdFbNotFinishService cdFbNotFinishService;
    @Autowired
    private CdSceneEchartsService cdSceneEchartsService;
    @Autowired
    private CdFtSkillService cdFtSkillService;
    @Autowired
    private CdFtLogoService cdFtLogoService;
    @Autowired
    private CdFbNotFinishCollectionService cdFbNotFinishCollectionService;
    @Autowired
    private CdFootballAwardsService cdFootballAwardsService;


    /**
     * 未完赛数据接口
     *
     * @param
     */
    @RequestMapping(value = "notFinshedData", method = RequestMethod.POST)
    @ResponseBody
    public String notFinshedData(HttpServletRequest request) {
        logger.info("notFinshedData  未完赛数据---------Start---------");

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

        List list = new ArrayList();
        List<CdFbNotFinish> dataList = cdFbNotFinishService.getNotFinish(total, count);
        for (CdFbNotFinish str : dataList) {
            Map<String, Object> map = new HashMap<>();
            CdFbNotFinishCollection cffc = cdFbNotFinishCollectionService.findByMatIdAndUid(str.getSort(), uid);
            if (cffc != null) {
                map.put("col", "1");
            } else {
                map.put("col", "0");
            }
            map.put("qc", str.getQc());
            map.put("sort", str.getSort());//赛事名称
            map.put("type", str.getType());//比赛时间
            map.put("ln", str.getLn());//赛事类型
            map.put("hn", str.getHn());//主队
            map.put("gn", str.getGn());//客队
            map.put("hf", str.getHf());//主队比分
            map.put("gf", str.getGf());//客队比分
            map.put("hnLogo", cdFtLogoService.findLogo(str.getHn())); //主队图标
            map.put("gnLogo", cdFtLogoService.findLogo(str.getGn())); //客队图标
            map.put("jn", str.getJn());//平赔率
            map.put("time", str.getTime().substring(10, 16));//比赛时间
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("notFinshedData  未完赛数据---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 已完赛数据接口
     *
     * @param
     */
    @RequestMapping(value = "finshedData", method = RequestMethod.POST)
    @ResponseBody
    public String finshedData(HttpServletRequest request) {
        logger.info("finshedData  已完赛数据---------Start---------");

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

        List list = new ArrayList();
        //List<CdFbAlreadyFinish> dataList = cdFbAlreadyFinshService.getAlreadyFinsh(total, count);
        List<CdFootballAwards> dataList = cdFootballAwardsService.findALL(total, count);
        for (CdFootballAwards str : dataList) {
            Map<String, Object> map = new HashMap<>();
//            map.put("qc", str.getQc());
            map.put("sort", str.getMatchDate());//标示
//            map.put("type", str.getType());//比赛时间
            map.put("ln", str.getEventName());//赛事类型
            map.put("hn", str.getHomeTeam());//主队
            map.put("gn", str.getAwayTeam());//客队
            map.put("hf", str.getHs());//主队分数
            map.put("gf", str.getVs());//客队分数
            map.put("hnLogo", cdFtLogoService.findLogo(str.getHomeTeam())); //主队图标
            map.put("gnLogo", cdFtLogoService.findLogo(str.getAwayTeam())); //客队图标
            map.put("jn", str.getMatchId());//平赔率
            map.put("time", str.getMt());//比赛时间
            //map.put("score", str.getHs() + "-" + );//比分

            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("finshedData  已完赛数据---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 足球实况(比赛事件echarts图表)
     */
    @RequestMapping(value = "ftEcharts", method = RequestMethod.POST)
    @ResponseBody
    public String ftEcharts(HttpServletRequest request) {
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String itemId = (String) jsonData.get("itemid");
        logger.info("ftEcharts  比赛事件echarts图表---------Start---------");
        List<Map> list = new ArrayList<>();
        List<CdSceneEcharts> dataList = cdSceneEchartsService.getEcharts(itemId);
        for (CdSceneEcharts str : dataList) {
            Map<String, Object> map = new HashMap<>();
            map.put("pn", str.getPn()); //
            map.put("eventType", str.getEventType());//类型(0进球3黄牌5红牌)
            map.put("teamType", str.getTeamType());
            map.put("itemId", str.getItemId());
            map.put("hn", str.getHn());//主队
            map.put("gn", str.getGn());//客队
            map.put("isFinshed", str.getIsFinshed());//是否完赛
            map.put("time", str.getTime());//比赛时间
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("ftEcharts  比赛事件echarts图表---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 足球实况(技术统计 首发名单和替补名单)
     */
    @RequestMapping(value = "ftBallSkill", method = RequestMethod.POST)
    @ResponseBody
    public String ftBallSkill(HttpServletRequest request) {
        logger.info("ftBallSkill  足球实况(技术统计 首发名单和替补名单)---------Start---------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }

        String itemId = (String) jsonData.get("itemid");
        if (StringUtils.isEmpty(itemId)) {
            logger.error("itemid为空");
            return HttpResultUtil.errorJson("itemid为空");
        }
        List<CdFtSkill> dataList = cdFtSkillService.getFtSkill(itemId);
        CdFtSkill str = new CdFtSkill();
        if (dataList.size() > 0) {
            str = dataList.get(0);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", str.getItemId());
        map.put("shootnuma", str.getShootnuma()); //射门a
        map.put("shotsongoala", str.getShotsongoala()); //射正a
        map.put("foulnuma", str.getFoulnuma()); //犯规A
        map.put("hn", str.getHn());//主队
        map.put("gn", str.getGn());//客队
        map.put("isFinshed", str.getIsFinshed());//是否完赛
        map.put("cornerkicknuma", str.getCornerkicknuma());//角球A
        map.put("offsidenuma", str.getOffsidenuma());//越位A
        map.put("yellowcardnuma", str.getYellowcardnuma());//黄牌A
        map.put("savesa", str.getSavesa());//救球
        map.put("shootnumb", str.getShootnumb()); //射门b
        map.put("shotsongoalb", str.getShotsongoalb()); //射正b
        map.put("foulnumb", str.getFoulnumb()); //犯规b
        map.put("cornerkicknumb", str.getCornerkicknumb());//角球b
        map.put("offsidenumb", str.getOffsidenumb());//越位b
        map.put("yellowcardnumb", str.getYellowcardnumb());//黄牌b
        map.put("savesb", str.getSavesb());//救球b

        List<String> paList = getList(str.getPlayera());
        List<String> pbList = getList(str.getPlayerb());
        List<String> tpaList = getList(str.getTbplayera());
        List<String> tpbList = getList(str.getTbplayerb());

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", map);
        dataMap.put("paList", paList);
        dataMap.put("pbList", pbList);
        dataMap.put("tpaList", tpaList);
        dataMap.put("tpbList", tpbList);
        logger.info("ftBallSkill  足球实况(技术统计 首发名单和替补名单)---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 未完赛比赛收藏
     */
    @RequestMapping(value = "footNotFinishedCollection", method = RequestMethod.POST)
    @ResponseBody
    public String footNotFinishedCollection(HttpServletRequest request) {
        logger.info("footNotFinishedCollection---------Start---------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }


        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }
        String sort = (String) jsonData.get("sort");
        if (StringUtils.isEmpty(sort)) {
            logger.error("sort为空");
            return HttpResultUtil.errorJson("sort为空");
        }

        CdFbNotFinishCollection cffc = cdFbNotFinishCollectionService.findByMatIdAndUid(sort, uid);
        if (cffc == null) {
            CdFbNotFinishCollection cdFbNotFinishCollection = new CdFbNotFinishCollection();
            cdFbNotFinishCollection.setSort(sort);
            cdFbNotFinishCollection.setUid(uid);

            cdFbNotFinishCollectionService.save(cdFbNotFinishCollection);
        } else {
            cdFbNotFinishCollectionService.delete(cffc.getId());
        }
        Map dataMap = new HashMap();
        logger.info("footNotFinishedCollection---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 用户已关注未完赛列表
     */
    @RequestMapping(value = "notFinishedHasCol", method = RequestMethod.POST)
    @ResponseBody
    public String notFinishedHasCol(HttpServletRequest request) {
        logger.info("notFinishedHasCol---------Start---------");

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
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }

        List<Map> list = new ArrayList<>();
        List<CdFbNotFinish> dataList = cdFbNotFinishService.getNotFinish(total, count);
        for (CdFbNotFinish str : dataList) {
            Map<String, Object> map = new HashMap<>();
            CdFbNotFinishCollection cffc = cdFbNotFinishCollectionService.findByMatIdAndUid(str.getSort(), uid);
            if (cffc != null) {
                map.put("col", "1");
                map.put("qc", str.getQc());
                map.put("sort", str.getSort());//赛事名称
                map.put("type", str.getType());//比赛时间
                map.put("ln", str.getLn());//赛事类型
                map.put("hn", str.getHn());//主队
                map.put("gn", str.getGn());//客队
                map.put("hnLogo", cdFtLogoService.findLogo(str.getHn())); //主队图标
                map.put("gnLogo", cdFtLogoService.findLogo(str.getGn())); //客队图标
                map.put("jn", str.getJn());//平赔率
                map.put("time", str.getTime());//比赛时间
                map.put("hf", str.getHf());//主队分数
                map.put("gf", str.getGf());//客队分数
                list.add(map);
            }
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", list);
        logger.info("notFinishedHasCol  未完赛数据---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 获取比赛主队客队比分或开赛时间
     */
    @RequestMapping(value = "getFootBallMatchTitle", method = RequestMethod.POST)
    @ResponseBody
    public String getFootBallMatchTitle(HttpServletRequest request) {
        logger.info("getFootBallMatchTitle---------Start---------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map<String, Object> dataMap = new HashMap<>();
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }

        String sort = (String) jsonData.get("sort");
        if (StringUtils.isEmpty(sort)) {
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
            CdFootballAwards cfa = cdFootballAwardsService.findBymatchDate(sort);
            if (cfa == null) {
                return HttpResultUtil.errorJson("数据不存在");
            }
            dataMap.put("host", cfa.getHomeTeam());//主队
            dataMap.put("guest", cfa.getAwayTeam());//客队
            dataMap.put("middle", cfa.getHs() + "-" + cfa.getVs());//别分
        } else {
            CdFbNotFinish cff = cdFbNotFinishService.findBySort(sort);
            if (cff == null) {
                return HttpResultUtil.errorJson("数据不存在");
            }
            dataMap.put("host", cff.getHn());//主队
            dataMap.put("guest", cff.getGn());//客队
            dataMap.put("middle", cff.getTime().substring(0, 16));//开赛时间
        }


        return HttpResultUtil.successJson(dataMap);
    }


    public static List<String> getList(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        String[] strArray = str.split(",");
        List<String> list = new ArrayList<>();
        for (String s : strArray) {
            list.add(s.split("-")[1]);
        }
        return list;
    }
}
