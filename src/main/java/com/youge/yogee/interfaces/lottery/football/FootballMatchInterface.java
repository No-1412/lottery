package com.youge.yogee.interfaces.lottery.football;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cfbfuture.entity.CdFbFuture;
import com.youge.yogee.modules.cfbfuture.service.CdFbFutureService;
import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinish;
import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinishCollection;
import com.youge.yogee.modules.cfbnotfinish.service.CdFbNotFinishCollectionService;
import com.youge.yogee.modules.cfbnotfinish.service.CdFbNotFinishService;
import com.youge.yogee.modules.cfboutcome.entity.CdFbOutcome;
import com.youge.yogee.modules.cfboutcome.service.CdFbOutcomeService;
import com.youge.yogee.modules.cfbscoer.entity.CdFbScoer;
import com.youge.yogee.modules.cfbscoer.service.CdFbScoerService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private CdFbScoerService cdFbScoerService;
    @Autowired
    private CdFbFutureService cdFbFutureService;
    @Autowired
    private CdFbOutcomeService cdFbOutcomeService;


    /**
     * 未完赛数据接口
     *
     * @param
     */
    @RequestMapping(value = "notFinshedData", method = RequestMethod.POST)
    @ResponseBody
    public String notFinshedData(HttpServletRequest request) throws ParseException {
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

        List<Map> list = new ArrayList<>();
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
            String time = str.getTime();
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date timeDate = sdf.parse(time);
            if (today.getTime() < timeDate.getTime()) {
                map.put("startTime", "");//比赛开始时间
            } else {
                long between = today.getTime() - timeDate.getTime();
                long startTime = between / (1000 * 60);
                if (startTime > 45 && startTime < 60) {
                    startTime = 45;
                }
                if (startTime > 60) {
                    startTime = startTime - 15;
                }
                map.put("startTime", String.valueOf(startTime) + "’");//比赛开始时间
            }
            map.put("time", str.getTime().substring(10, 16));//比赛时间
            list.add(map);
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", list);
        logger.info("notFinshedData  未完赛数据---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 已完赛数据接口
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

        List<Map> list = new ArrayList<>();
        List<CdFootballAwards> dataList = cdFootballAwardsService.findALL(total, count);
        for (CdFootballAwards str : dataList) {
            Map<String, Object> map = new HashMap<>();
            map.put("sort", str.getMatchDate());//标示
            map.put("ln", str.getEventName());//赛事类型
            map.put("hn", str.getHomeTeam());//主队
            map.put("gn", str.getAwayTeam());//客队
            map.put("hf", str.getHs());//主队分数
            map.put("gf", str.getVs());//客队分数
            map.put("hnLogo", cdFtLogoService.findLogo(str.getHomeTeam())); //主队图标
            map.put("gnLogo", cdFtLogoService.findLogo(str.getAwayTeam())); //客队图标
            map.put("jn", str.getMatchId());//平赔率
            map.put("time", str.getMt());//比赛时间
            list.add(map);
        }
        Map<String, Object> dataMap = new HashMap<>();
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
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String itemid = (String) jsonData.get("itemid");
        if (StringUtils.isEmpty(itemid)) {
            logger.error("itemid为空");
            return HttpResultUtil.errorJson("itemid为空");
        }

        logger.info("ftEcharts  比赛事件echarts图表---------Start---------");
        List<Map> list = new ArrayList<>();
        List<CdSceneEcharts> dataList = cdSceneEchartsService.getEcharts(itemid);
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
        Map<String, Object> dataMap = new HashMap<>();
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

        String itemid = (String) jsonData.get("itemid");
        if (StringUtils.isEmpty(itemid)) {
            logger.error("itemid为空");
            return HttpResultUtil.errorJson("itemid为空");
        }
        List<CdFtSkill> dataList = cdFtSkillService.getFtSkill(itemid);
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
     * 足球详情(分析)
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
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String itemid = (String) jsonData.get("itemid");//比赛id
        if (StringUtils.isEmpty(itemid)) {
            logger.error("itemid为空");
            return HttpResultUtil.errorJson("itemid为空");
        }

        Map<String, Object> dataMap = new HashMap<>();
        List scoerList = new ArrayList();
        List hFutureList = new ArrayList();
        List gFutureList = new ArrayList();
        String hostName = "";//主队名称
        String guestName = "";//客队名称
        CdFootballAwards cfa = cdFootballAwardsService.findBymatchDate(itemid);
        if (cfa != null) {
            hostName = cfa.getHomeTeam();//主队名称
            guestName = cfa.getAwayTeam();//客队名称
        } else {
            CdFbNotFinish cfnf = cdFbNotFinishService.findBySort(itemid);
            hostName = cfnf.getHn();
            guestName = cfnf.getGn();
        }


        //主队近期战绩
        List<CdFbOutcome> hnList = cdFbOutcomeService.findById(itemid, hostName, "0");
        if (hnList.size() > 0) {
            int win = 0;//胜
            int dogfall = 0;//平
            int lost = 0;//负

            List<Map> competitionList = new ArrayList<>();
            for (CdFbOutcome cdFbOutcome : hnList) {
                Map<String, Object> hnMap = new HashMap<>();
                hnMap.put("mname", cdFbOutcome.getEventName());//赛事名称
                hnMap.put("mt", cdFbOutcome.getMt());//时间
                hnMap.put("hn", cdFbOutcome.getHn());//主队名称
                hnMap.put("gn", cdFbOutcome.getGn());//客队名称
                hnMap.put("score", cdFbOutcome.getHsc() + ":" + cdFbOutcome.getGsc());//比分

                //判断主场
                if (hostName.equals(cdFbOutcome.getHn())) {//作为主队
                    if (Integer.parseInt(cdFbOutcome.getHsc()) > Integer.parseInt(cdFbOutcome.getGsc())) {
                        win++;
                        hnMap.put("result", "胜");
                    } else if (Integer.parseInt(cdFbOutcome.getHsc()) == Integer.parseInt(cdFbOutcome.getGsc())) {//平
                        dogfall++;
                        hnMap.put("result", "平");
                    } else {
                        lost++;
                        hnMap.put("result", "负");
                    }
                } else {//作为客队
                    if (Integer.parseInt(cdFbOutcome.getGsc()) > Integer.parseInt(cdFbOutcome.getHsc())) {
                        win++;
                        hnMap.put("result", "胜");
                    } else if (Integer.parseInt(cdFbOutcome.getGsc()) == Integer.parseInt(cdFbOutcome.getHsc())) {//平
                        dogfall++;
                        hnMap.put("result", "平");
                    } else {
                        lost++;
                        hnMap.put("result", "负");
                    }
                }
                competitionList.add(hnMap);
            }

            int[] scoreArray = {win, dogfall, lost};

            dataMap.put("hnCompetitionList", competitionList);
            dataMap.put("hnScoreArray", scoreArray);
        }

        //客场近期战绩
        List<CdFbOutcome> gnList = cdFbOutcomeService.findById(itemid, hostName, "0");
        if (gnList.size() > 0) {
            int win = 0;//胜
            int dogfall = 0;//平
            int lost = 0;//负

            List<Map> competitionList = new ArrayList<>();
            for (CdFbOutcome cdFbOutcome : gnList) {
                Map<String, Object> gnMap = new HashMap<>();
                gnMap.put("mname", cdFbOutcome.getEventName());//赛事名称
                gnMap.put("mt", cdFbOutcome.getMt());//时间
                gnMap.put("hn", cdFbOutcome.getHn());//主队名称
                gnMap.put("gn", cdFbOutcome.getGn());//客队名称
                gnMap.put("score", cdFbOutcome.getHsc() + ":" + cdFbOutcome.getGsc());//比分

                //判断主场
                if (guestName.equals(cdFbOutcome.getGn())) {
                    //客队胜
                    if (Integer.parseInt(cdFbOutcome.getGsc()) > Integer.parseInt(cdFbOutcome.getHsc())) {
                        win++;
                        gnMap.put("result", "胜");
                    } else if (Integer.parseInt(cdFbOutcome.getGsc()) == Integer.parseInt(cdFbOutcome.getHsc())) {//平
                        dogfall++;
                        gnMap.put("result", "平");
                    } else {
                        lost++;
                        gnMap.put("result", "负");
                    }
                } else {
                    //主队胜
                    if (Integer.parseInt(cdFbOutcome.getHsc()) > Integer.parseInt(cdFbOutcome.getGsc())) {
                        win++;
                        gnMap.put("result", "胜");//胜负
                    } else if (Integer.parseInt(cdFbOutcome.getHsc()) == Integer.parseInt(cdFbOutcome.getGsc())) {//平
                        dogfall++;
                        gnMap.put("result", "平");//胜负
                    } else {
                        lost++;
                        gnMap.put("result", "负");//胜负
                    }
                }
                competitionList.add(gnMap);
            }

            int[] scoreArray = {win, dogfall, lost};

            dataMap.put("gnCompetitionList", competitionList);
            dataMap.put("gnScoreArray", scoreArray);
        }
        logger.info("listFtDetail  足球近期战绩详情---------End---------");


        logger.info("listFtDetail  足球历史战绩详情---------Start---------");
        //历史交锋
        List<CdFbOutcome> cdHList = cdFbOutcomeService.findByOldTime(itemid);
        if (cdHList != null) {
            int win = 0;//胜
            int dogfall = 0;//平
            int lost = 0;//负

            List<Map> competitionList = new ArrayList<>();
            for (CdFbOutcome cdFbOutcome : cdHList) {
                Map<String, Object> map = new HashMap<>();
                map.put("mname", cdFbOutcome.getEventName());//赛事名称
                map.put("mt", cdFbOutcome.getMt());//时间
                map.put("hn", cdFbOutcome.getHn());//主队名称
                map.put("gn", cdFbOutcome.getGn());//客队名称
                map.put("score", cdFbOutcome.getHsc() + ":" + cdFbOutcome.getGsc());//比分

                //判断主场
                if (hostName.equals(cdFbOutcome.getHn())) {//作为主队
                    if (Integer.parseInt(cdFbOutcome.getHsc()) > Integer.parseInt(cdFbOutcome.getGsc())) {
                        win++;
                        map.put("result", "胜");
                    } else if (Integer.parseInt(cdFbOutcome.getHsc()) == Integer.parseInt(cdFbOutcome.getGsc())) {//平
                        dogfall++;
                        map.put("result", "平");
                    } else {
                        lost++;
                        map.put("result", "负");
                    }
                } else {//作为客队
                    if (Integer.parseInt(cdFbOutcome.getGsc()) > Integer.parseInt(cdFbOutcome.getHsc())) {
                        win++;
                        map.put("result", "胜");
                    } else if (Integer.parseInt(cdFbOutcome.getGsc()) == Integer.parseInt(cdFbOutcome.getHsc())) {//平
                        dogfall++;
                        map.put("result", "平");
                    } else {
                        lost++;
                        map.put("result", "负");
                    }
                    competitionList.add(map);
                }

                int[] scoreArray = {win, dogfall, lost};

                dataMap.put("historyCompetitionList", competitionList);
                dataMap.put("historyScoreArray", scoreArray);
            }
        }
        logger.info("listFtDetail  足球历史战绩详情---------End---------");

        logger.info("listFtDetail  足球积分详情---------Start---------");
        //region Description
        List<CdFbScoer> cdScoerList = cdFbScoerService.findById(itemid);
        for (CdFbScoer cdFbScoer : cdScoerList) {
            Map<String, Object> map = new HashMap();
            map.put("rank", cdFbScoer.getRank());//球队排名
            map.put("teamname", cdFbScoer.getTeamname());//球队名称
            map.put("points", cdFbScoer.getPoints());//球队积分
            map.put("scoerAll", cdFbScoer.getScoerAll());//球队总成绩
            map.put("host", cdFbScoer.getHost());//球队主场成绩
            map.put("guest", cdFbScoer.getGuest());//球队客场成绩
            scoerList.add(map);
        }
        //endregion
        logger.info("listFtDetail  足球积分详情---------End---------");

        logger.info("listFtDetail  足球未来赛事详情---------Start---------");
        //region Description
//        CdFootballMixed cdFootballMixed = cdFootballMixedService.getByItem(itemid);
        //查询队id
        List<CdFbFuture> cdFutureName = cdFbFutureService.findByName(hostName);
        String teamId = cdFutureName.get(0).getTeamHid();
        List<CdFbFuture> cdFutureList = cdFbFutureService.findById(itemid);
        for (CdFbFuture cd : cdFutureList) {
            Map<String, Object> map = new HashMap();
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

        dataMap.put("scoerList", scoerList);//战绩分数
        dataMap.put("hFutureList", hFutureList);//主队未来赛事
        dataMap.put("gFutureList", gFutureList);//客队未来赛事
        logger.info("listFtDetail  足球详情---------Start---------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 未完赛比赛关注
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

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
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

        List<CdFbNotFinish> list = cdFbNotFinishCollectionService.findColByUid(uid, Integer.parseInt(total), Integer.parseInt(count));
        List cList = new ArrayList();
        if (list.size() > 0) {
            for (CdFbNotFinish str : list) {
                Map map = new HashMap();
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
                cList.add(map);
            }

        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", cList);
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
