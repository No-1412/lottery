package com.youge.yogee.interfaces.lottery.basketball;

import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cbbalreadyfinsh.entity.CdBbAlreadyFinsh;
import com.youge.yogee.modules.cbbalreadyfinsh.service.CdBbAlreadyFinshService;
import com.youge.yogee.modules.cbbnotfinsh.entity.CdBbNotFinsh;
import com.youge.yogee.modules.cbbnotfinsh.service.CdBbNotFinshService;
import com.youge.yogee.modules.cbbstrengthpk.entity.CdBbStrengthpk;
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
 * Created by wjc on 2018-1-23 0023.
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

    /**
     * 篮球实力PK数据接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "bbStrengthPk", method = RequestMethod.POST)
    @ResponseBody
    public String bbStrengthPk(HttpServletRequest request) {
        logger.info("bbStrengthPk  篮球实力PK---------Start---------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String itemId = (String)jsonData.get("itemId");
        List list = new ArrayList();
        List<CdBbStrengthpk> dataList = cdBbStrengthpkService.getStrengthPk(itemId);
        for (CdBbStrengthpk str : dataList) {
            Map map = new HashMap();
            map.put("averagingStatistics", str.getAveragingStatistics());//场均统计
            map.put("nameHa", str.getNameHa());//球员名字主队
            map.put("locationHa", str.getLocationHa());//位置主队
            map.put("stageHa", str.getStageHa());//出场主队
            map.put("timeHa", str.getTimeHa());//时间主队
            map.put("scoreHa", str.getScoreHa());//得分主队
            map.put("backboardHa", str.getBackboardHa());//篮板主队
            map.put("assitha", str.getAssistHa());//助攻主队
            map.put("stealHa", str.getStealHa());//抢断主队
            map.put("shootHa", str.getShootHa());//投篮主队
            map.put("trisectionHa", str.getTrisectionHa());//三分主队
            map.put("penaltyHa", str.getPenaltyHa());//罚球主队
            map.put("closeHa", str.getCloseHa());//封盖主队
            map.put("nameHb", str.getNameHb());//球员名字主队
            map.put("locationHb", str.getLocationHb());//位置客队
            map.put("stageHb", str.getStageHb());//出场客队
            map.put("timeHb", str.getTimeHb());//时间客队
            map.put("scoreHb", str.getScoreHb());//得分客队
            map.put("backboardHb", str.getBackboardHb());//篮板客队
            map.put("assitha", str.getAssistHb());//助攻客队
            map.put("stealHb", str.getStealHb());//抢断客队
            map.put("shootHb", str.getShootHb());//投篮客队
            map.put("trisectionHb", str.getTrisectionHb());//三分客队
            map.put("penaltyHb", str.getPenaltyHb());//罚球客队
            map.put("closeHb", str.getCloseHb());//封盖客队
            map.put("hn", str.getHn());//主队名
            map.put("vn", str.getVn());//客队名
            map.put("itemId", str.getItemId());//itemId
            map.put("zid", str.getZid());//队Id
            map.put("jstj", str.getJstj());//技术统计
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
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
        List<CdBbAlreadyFinsh> dataList = cdBbAlreadyFinshService.getBbFinshed();
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
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list",list);
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
        List<CdBbNotFinsh> dataList = cdBbNotFinshService.getBbFinshed();
        List list = new ArrayList();
        for (CdBbNotFinsh str : dataList) {
            Map map = new HashMap();
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
        Map dataMap = new HashMap();
        dataMap.put("list",list);
        logger.info("bbNotFinsh  篮球未完赛---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }
}
