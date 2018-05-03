package com.youge.yogee.interfaces.lottery.order;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.util;
import com.youge.yogee.modules.bm.entity.BmEventBelong;
import com.youge.yogee.modules.bm.service.BmEventBelongService;
import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinish;
import com.youge.yogee.modules.cfbnotfinish.service.CdFbNotFinishService;
import com.youge.yogee.modules.cfootballmixed.entity.CdFootballMixed;
import com.youge.yogee.modules.cfootballmixed.service.CdFootballMixedService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballBestFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballBestFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhaoyifeng on 2018-02-24.
 */
@Controller
@RequestMapping("${frontPath}")
public class FootballFollowOrderInterface {
    private static final Logger logger = LoggerFactory.getLogger(FootballSingleOrderInterface.class);

    @Autowired
    private CdFootballFollowOrderService cdFootballFollowOrderService;
    @Autowired
    private BmEventBelongService bmEventBelongService;
    @Autowired
    private CdFootballMixedService cdFootballMixedService;
    @Autowired
    private CdFootballBestFollowOrderService cdFootballBestFollowOrderService;
    @Autowired
    private CdFbNotFinishService cdFbNotFinishService;

    /**
     * 足球串关 提交订单
     */
    @RequestMapping(value = "footballFollowOrderCommit", method = RequestMethod.POST)
    @ResponseBody
    public String footballFollowOrderCommit(HttpServletRequest request) throws ParseException {
        logger.info(" interface footballFollowOrderCommit--------Start-------------------");
        logger.debug("interface 请求--footballFollowOrderCommit-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        //玩法 1混投 2胜负平 3猜比分 4总进球 5半全场 6让球 旧 不要了
        //1混投 2胜平负 3让球胜平负 4比分 5总进球 6半全场 2018年3月13日16:10:07
        String buyWays = (String) jsonData.get("buyWays");
        if (StringUtils.isEmpty(buyWays)) {
            logger.error("buyWays为空");
            return HttpResultUtil.errorJson("buyWays为空");
        }
        //串关数
        String followNum = (String) jsonData.get("followNum");
        if (StringUtils.isEmpty(followNum)) {
            logger.error("followNum为空");
            return HttpResultUtil.errorJson("followNum为空");
        }

        //用户id
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }

        //倍数
        String times = (String) jsonData.get("times");
        if (StringUtils.isEmpty(times)) {
            logger.error("times为空");
            return HttpResultUtil.errorJson("times为空");
        }


        //订单详情
        Object jsonString = jsonData.get("detail");
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        List<Map<String, Object>> detail = (List<Map<String, Object>>) jsonArray.toCollection(jsonArray, Map.class);
        List<String> resultList = new ArrayList<>();
        //String orderDetail = "";
        String scoreDetail = "";
        String goalDetail = "";
        String halfDetail = "";
        String beatDetail = "";
        String letDetail = "";
        //让球详情
        String letBalls = "";
        //大洲
        String continent = "";
        int acount = 0;//注数
        int danCount = 0;//胆数
        int danTimes = 1;//胆注数
        String danMatchIds = "";//胆场次
        String matchTimes="";//所有比赛时间
        if (detail.size() != 0) {

            for (Map<String, Object> d : detail) {
                String results = "";
                int mixCount = 0;//混投胆数
                String matchId = (String) d.get("matchId");
                CdFootballMixed sfm = cdFootballMixedService.findByMatchId(matchId);
                if (sfm == null) {
                    return HttpResultUtil.errorJson("比赛不存在");
                }
               String shutDownTime=sfm.getTimeEndsale();
                Date day=new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(df.parse(shutDownTime).getTime()<day.getTime()){
                    return HttpResultUtil.errorJson("超过比赛截止时间");
                }

                //是不是胆 0不是 1是
                String isMust = (String) d.get("isMust");
                if ("1".equals(buyWays)) {
                    if ("1".equals(isMust)) {
                        danCount++;
                    }
                }
                //记录所有场次
                if ("1".equals(isMust)) {
                    danMatchIds += "胆" + "+" + matchId + ",";
                } else {
                    danMatchIds += "非" + "+" + matchId + ",";
                }
                //记录所有比赛时间
                CdFbNotFinish cfnf=cdFbNotFinishService.findByJn(matchId);
                if(cfnf!=null){
                    matchTimes+=cfnf.getTime()+",";
                }else {
                    matchTimes+="2000-01-01 00:00:00"+",";
                }
                //比赛详情
                String partDetail = isMust + "+" + matchId + "+" + sfm.getWinningName() + "vs" + sfm.getDefeatedName();
                //查询赛事名称 获取大洲
                BmEventBelong beb = bmEventBelongService.findByEventName(sfm.getEventName());
                if (beb != null) {
                    String aContinent = beb.getContinentName();
                    continent += aContinent + ",";
                }
                //胜负平所有押注结果 2胜负平
                String beat = (String) d.get("beat");
                if (StringUtils.isNotEmpty(beat)) {
                    String beatArry[] = beat.split(",");
                    //String[] sStr = beatArry[0].split("/");
                    if ("0".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            for (String s : beatArry) {
                                results += s + ";";
                            }
                        } else {
                            resultList.add(beat);
                        }
                    }
                    if ("1".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            if (beatArry.length > 0) {
                                mixCount = mixCount + beatArry.length;
                            }
                        } else {
                            danCount++;
                            danTimes = danTimes * beatArry.length;
                        }

                    }
                    beatDetail += partDetail + "+" + beat + "|";
                }


                //比分所有押注结果  3猜比分
                String score = (String) d.get("score");
                if (StringUtils.isNotEmpty(score)) {
                    String scoreArry[] = score.split(",");
                    //String[] sStr = scoreArry[0].split("/");
                    if ("0".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            for (String s : scoreArry) {
                                results += s + ";";
                            }
                        } else {
                            resultList.add(score);
                        }
                    }
                    if ("1".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            if (scoreArry.length > 0) {
                                mixCount = mixCount + scoreArry.length;
                            }
                        } else {
                            danCount++;
                            danTimes = danTimes * scoreArry.length;
                        }

//                        if (!"1".equals(buyWays)) {
//                            danCount++;
//                        }
//                        //String scoreArry[] = score.split(",");
//                        danTimes = danTimes * scoreArry.length;
                    }
                    scoreDetail += partDetail + "+" + score + "|";
                }

                //总进球所有押注结果 4总进球
                String goal = (String) d.get("goal");
                if (StringUtils.isNotEmpty(goal)) {
                    String goalArry[] = goal.split(",");
                    //String[] sStr = goalArry[0].split("/");
                    if ("0".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            for (String s : goalArry) {
                                results += s + ";";
                            }
                        } else {
                            resultList.add(goal);
                        }
                    }
                    if ("1".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            if (goalArry.length > 0) {
                                mixCount = mixCount + goalArry.length;
                            }
                        } else {
                            danCount++;
                            danTimes = danTimes * goalArry.length;
                        }
                    }
                    goalDetail += partDetail + "+" + goal + "|";
                }

                //半全场所有押注结果  5半全场
                String half = (String) d.get("half");
                if (StringUtils.isNotEmpty(half)) {
                    String halfArry[] = half.split(",");
//                    String[] sStr = halfArry[0].split("/");
                    if ("0".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            for (String s : halfArry) {
                                results += s + ";";
                            }
                        } else {
                            resultList.add(half);
                        }
                    }
                    if ("1".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            if (halfArry.length > 0) {
                                mixCount = mixCount + halfArry.length;
                            }
                        } else {
                            danCount++;
                            danTimes = danTimes * halfArry.length;
                        }
                    }
                    halfDetail += partDetail + "+" + half + "|";
                }


                //让球所有押注结果  6让球
                String let = (String) d.get("let");
                if (StringUtils.isNotEmpty(let)) {
                    String letArry[] = let.split(",");
//                    String[] sStr = letArry[0].split("/");
                    if ("0".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            for (String s : letArry) {
                                results += s + ";";
                            }
                        } else {
                            resultList.add(let);
                        }
                    }
                    if ("1".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            if (letArry.length > 0) {
                                mixCount = mixCount + letArry.length;
                            }
                        } else {
                            danCount++;
                            danTimes = danTimes * letArry.length;
                        }
                    }
                    letDetail += partDetail + "+" + let + "|";
                    letBalls += sfm.getClose() + ",";
                }

                if ("1".equals(buyWays)) {
                    if ("0".equals(isMust)) {
                        resultList.add(results);
                    }
                    if ("1".equals(isMust)) {
                        danTimes = danTimes * mixCount;
                    }
                }
                //orderDetail += partDetail + "+" + isMust + "|";

            }

            String[] followStr = followNum.split(",");
            for (int i = 0; i < followStr.length; i++) {
                int count = Integer.valueOf(followStr[i]);
                //减掉胆
                int partCount = 1;
                if ("1".equals(buyWays)) {
                    //混投
                    partCount = BallGameCals.countOfFootBall(resultList, count - danCount, 1);
                } else {
                    //其它
                    partCount = BallGameCals.countOfFootBall(resultList, count - danCount, 2);
                }
                //最终注数乘上胆的注数
                int finalCount = partCount * danTimes;
                acount += finalCount;
            }
        }


        //注数
        String acountStr = String.valueOf(acount);
        //生成订单号
        String orderNum = util.genOrderNo("ZCG", util.getFourRandom());
        //计算金额
        double money = 2.00;
        double acountDouble = Double.parseDouble(acountStr);
        double timesDouble = Double.parseDouble(times);
        String price = String.valueOf(money * acountDouble * timesDouble);

        CdFootballFollowOrder cffo = new CdFootballFollowOrder();

        cffo.setOrderNum(orderNum); //订单号
        cffo.setAcount(acountStr);//注数
        cffo.setAward("0"); //奖金
        //cffo.setOrderDetail(orderDetail); //订单详情
        cffo.setScore(scoreDetail);//比分详情
        cffo.setHalf(halfDetail);//半全场
        cffo.setGoal(goalDetail);//总进球
        cffo.setBeat(beatDetail);//胜负平
        cffo.setLet(letDetail);//让球胜负平
        cffo.setLetBalls(letBalls);//让球数

        cffo.setPrice(price);//金额
        cffo.setStatus("1");//已提交
        cffo.setUid(uid);//用户
        cffo.setBuyWays(buyWays);//玩法 1混投 2胜平负 3让球胜平负 4比分 5总进球 6半全场
        cffo.setFollowNum(followNum);//串关数
        cffo.setTimes(times); //倍数
        cffo.setDanMatchIds(danMatchIds);//胆场次
        cffo.setType("0"); //0普通订单 1发起的 2跟单的
        cffo.setBestType("1");//1普通单 2优化的
        cffo.setAllMatchTimes(matchTimes);//所有比赛时间
        try {
            cdFootballFollowOrderService.save(cffo);
            map.put("orderNum", orderNum);
            map.put("orderName", "竞猜足球订单支付");
            map.put("time", cffo.getCreateDate());
            map.put("price", price);
            map.put("acountStr", acountStr);//注数
            map.put("times", times);//倍数
            map.put("followNum", followNum);//串关
        } catch (Exception e) {
            return HttpResultUtil.errorJson("保存失败");
        }

        logger.info("footballFollowOrderCommit---------End---------------------");
        return HttpResultUtil.successJson(map);
    }


    /**
     * 足球串关 奖金优化 订单提交
     */
    @RequestMapping(value = "footballBestFollowOrderCommit", method = RequestMethod.POST)
    @ResponseBody
    public String footballBestFollowOrderCommit(HttpServletRequest request) {
        logger.info("footballBestFollowOrderCommit--------Start-------------------");
        logger.debug("footballBestFollowOrderCommit-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        //1混投 2胜平负 3让球胜平负 4比分 5总进球 6半全场 2018年3月13日16:10:07
        String buyWays = (String) jsonData.get("buyWays");
        if (StringUtils.isEmpty(buyWays)) {
            logger.error("buyWays为空");
            return HttpResultUtil.errorJson("buyWays为空");
        }
        //串关数
        String followNum = (String) jsonData.get("followNum");
        if (StringUtils.isEmpty(followNum)) {
            logger.error("followNum为空");
            return HttpResultUtil.errorJson("followNum为空");
        }
        //用户id
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }
        //订单整体的list
        Object jsonString = jsonData.get("detail");
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        List<Map<String, Object>> detailList = (List<Map<String, Object>>) jsonArray.toCollection(jsonArray, Map.class);

        //生成订单号
        String orderNum = util.genOrderNo("ZCG", util.getFourRandom());
        int acount = 0; //总注数


        //遍历整体
        if (detailList.size() != 0) {
            Set<String> matchSet = new HashSet<>();
            //所有比赛
            for (Map<String, Object> objectMap : detailList) {
                //一条里的下单详情
                Object portfolioChilVOList = objectMap.get("portfolioChilVOList");
                JSONArray jsonaDetailMap = JSONArray.fromObject(portfolioChilVOList);
                List<Map<String, Object>> aDetailMap = (List<Map<String, Object>>) jsonArray.toCollection(jsonaDetailMap, Map.class);
                for (Map<String, Object> aBestDeatil : aDetailMap) {
                    String matchId = (String) aBestDeatil.get("matchId");//比赛matchids
                    matchSet.add(matchId);
                }
            }

            //用于总订单的map
            Map<String, Map<String, String>> detailMap = new HashMap<>();

            //保证比赛存在
            String danMatchIds = "";
            String letBalls = "";
            String matchTimes="";//所有比赛时间
            for (String s : matchSet) {
                CdFootballMixed cbm = cdFootballMixedService.findByMatchId(s);
                if (cbm == null) {
                    return HttpResultUtil.errorJson("比赛不存在！");
                }
                danMatchIds += "非+" + s + ",";
                letBalls += cbm.getClose() + ",";
                //记录所有比赛时间
                CdFbNotFinish cfnf=cdFbNotFinishService.findByJn(s);
                if(cfnf!=null){
                    matchTimes+=cfnf.getTime()+",";
                }else {
                    matchTimes+="2000-01-01 00:00:00"+",";
                }

                Map<String, String> aDetailMap = new HashMap<>();
                aDetailMap.put("score", "");
                aDetailMap.put("goal", "");
                aDetailMap.put("half", "");
                aDetailMap.put("beat", "");
                aDetailMap.put("let", "");
                detailMap.put(s, aDetailMap);
            }
            //遍历整体
            for (Map<String, Object> aDetail : detailList) {
                String matchIds = "";//分表里的所有比赛
                String perTimes = (String) aDetail.get("beishu").toString();//倍数
                acount += Integer.parseInt(perTimes);//总注数
                String perAward = (String) aDetail.get("jiangjin").toString();//奖金
                String bestDetail = "";//优化的一条
                //List<Map<String, Object>> aDetailMap = (List<Map<String, Object>>) aDetail.get("portfolioChilVOList");
                //一条里的下单详情
                Object portfolioChilVOList = aDetail.get("portfolioChilVOList");
                JSONArray jsonaDetailMap = JSONArray.fromObject(portfolioChilVOList);
                List<Map<String, Object>> aDetailMap = (List<Map<String, Object>>) jsonArray.toCollection(jsonaDetailMap, Map.class);

                for (Map<String, Object> aBestDeatil : aDetailMap) {
                    String matchId = (String) aBestDeatil.get("matchId");//比赛matchids
                    CdFootballMixed cfm = cdFootballMixedService.findByMatchId(matchId);
                    if (cfm == null) {
                        return HttpResultUtil.errorJson("比赛不存在！");
                    }
                    String close = cfm.getClose();//让分
                    //String zclose = cbm.getZclose();//大小分分数
                    matchIds += matchId + ",";
                    String odds = (String) aBestDeatil.get("odds");//赔率
                    String name = (String) aBestDeatil.get("name");//球队
                    String sf = (String) aBestDeatil.get("sf");//具体投注
                    String sm = BallGameCals.changeFootballSf(sf);//主表字段统一
                    String buyWay = (String) aBestDeatil.get("buyWays");//玩法
                    //场次  + 玩法 + 投注内容/赔率  + 队名/让分/大小分
                    String minDetail = matchId + "+" + buyWay + "+" + sf + "/" + odds + "+" + name + "/" + close + "|";
                    bestDetail += minDetail;//拼出一条优化详情
                    //处理数据给订单总表-----------*-----贼------*------他------*------妈-------*精妙*-------------------
                    String newSf = detailMap.get(matchId).get(buyWay);
                    String str = sm + "/" + odds;
                    if (!newSf.contains(str)) {
                        newSf += str + ",";
                    }
                    detailMap.get(matchId).put(buyWay, newSf);
                }
                //保存优化订单一条
                CdFootballBestFollowOrder cfbfo = new CdFootballBestFollowOrder();
                cfbfo.setOrderNum(orderNum);//归属订单号
                cfbfo.setMatchIds(matchIds);//所有比赛
                cfbfo.setOrderDetail(bestDetail);//具体详情
                cfbfo.setPerAward(perAward);//奖金
                cfbfo.setPerTimes(perTimes);//倍数
                cdFootballBestFollowOrderService.save(cfbfo);
            }
            //订单总表的字段
            String score = "";
            String goal = "";
            String half = "";
            String beat = "";
            String let = "";
            for (String s : matchSet) {
                Map<String, String> aMap = detailMap.get(s);
                CdFootballMixed cfm = cdFootballMixedService.findByMatchId(s);
                String head = "0+" + s + "+" + cfm.getWinningName() + "vs" + cfm.getDefeatedName() + "+";
                if (StringUtils.isNotEmpty(aMap.get("score"))) {
                    score += head + aMap.get("score") + "|";
                }
                if (StringUtils.isNotEmpty(aMap.get("goal"))) {
                    goal += head + aMap.get("goal") + "|";
                }
                if (StringUtils.isNotEmpty(aMap.get("half"))) {
                    half += head + aMap.get("half") + "|";
                }
                if (StringUtils.isNotEmpty(aMap.get("beat"))) {
                    beat += head + aMap.get("beat") + "|";
                }
                if (StringUtils.isNotEmpty(aMap.get("let"))) {
                    let += head + aMap.get("let") + "|";
                }
            }
            //保存到订单主表
            CdFootballFollowOrder cffo = new CdFootballFollowOrder();
            cffo.setOrderNum(orderNum); //订单号
            cffo.setAcount(String.valueOf(acount));//注数
            cffo.setAward("0"); //奖金
            BigDecimal countBig = new BigDecimal(acount);
            BigDecimal price = countBig.multiply(new BigDecimal(2)).setScale(2, 1);
            cffo.setPrice(String.valueOf(price));//金额
            cffo.setStatus("1");//已提交
            cffo.setUid(uid);//用户
            cffo.setBuyWays(buyWays);//玩法 1混投 2胜平负 3让球胜平负 4比分 5总进球 6半全场
            cffo.setFollowNum(followNum);//串关数
            cffo.setTimes("1"); //倍数
            cffo.setDanMatchIds(danMatchIds);//胆场次
            cffo.setType("0"); //0普通订单 1发起的 2跟单的
            cffo.setBestType("2");//1普通单 2优化的

            cffo.setScore(score);//比分
            cffo.setHalf(half);//半全场
            cffo.setGoal(goal);//总进球
            cffo.setLet(let);//让球胜负平
            cffo.setBeat(beat);//胜负平
            cffo.setAllMatchTimes(matchTimes);//所有比赛时间

            cffo.setLetBalls(letBalls);
            cdFootballFollowOrderService.save(cffo);
            map.put("orderNum", orderNum);
            map.put("orderName", "竞猜足球订单支付");
            map.put("time", cffo.getCreateDate());
            map.put("price", price);
            map.put("acountStr", String.valueOf(acount));//注数
            map.put("times", "1");//倍数
            map.put("followNum", followNum);//串关


        }
        logger.info("basketballFollowOrderCommit---------End---------------------");
        return HttpResultUtil.successJson(map);

    }

}
