package com.youge.yogee.interfaces.lottery.order;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.util;
import com.youge.yogee.modules.cbasketballmixed.entity.CdBasketballMixed;
import com.youge.yogee.modules.cbasketballmixed.service.CdBasketballMixedService;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballBestFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballBestFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
import com.youge.yogee.modules.cbbnotfinsh.entity.CdBbNotFinsh;
import com.youge.yogee.modules.cbbnotfinsh.service.CdBbNotFinshService;
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
 * Created by zhaoyifeng on 2018-02-26.
 */
@Controller
@RequestMapping("${frontPath}")
public class BasketballFollowOrderInterface {
    private static final Logger logger = LoggerFactory.getLogger(FootballSingleOrderInterface.class);
    @Autowired
    private CdBasketballMixedService cdBasketballMixedService;
    @Autowired
    private CdBasketballFollowOrderService cdBasketballFollowOrderService;
    @Autowired
    private CdBasketballBestFollowOrderService cdBasketballBestFollowOrderService;
    @Autowired
    private CdBbNotFinshService cdBbNotFinshService;


    /**
     * 篮球串关 非奖金优化 订单提交
     */
    @RequestMapping(value = "basketballFollowOrderCommit", method = RequestMethod.POST)
    @ResponseBody
    public String basketballFollowOrderCommit(HttpServletRequest request) throws ParseException {
        logger.info(" interface basketballFollowOrderCommit--------Start-------------------");
        logger.debug("interface 请求--basketballFollowOrderCommit-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        //玩法 1混投 2胜负 3让分胜负 4大小分 5胜分差
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


        if (times.compareTo(OrderTimes.minTimes) < 0) {
            logger.error("最少" + OrderTimes.minTimes + "注");
            return HttpResultUtil.errorJson("最少" + OrderTimes.minTimes + "注");
        }


        //订单详情
        Object jsonString = jsonData.get("detail");
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        List<Map<String, Object>> detail = (List<Map<String, Object>>) jsonArray.toCollection(jsonArray, Map.class);
        List<String> resultList = new ArrayList<>();
        //String orderDetail = "";
        String winDetail = "";
        String failDetail = "";
        String sizeDetail = "";
        String beatDetail = "";
        String letDetail = "";
        //大小分
        String sizeCount = "";
        //让分数
        String letScore = "";

        int acount = 0;//注数
        int danCount = 0;//胆数
        int danTimes = 1;//胆注数
        String danMatchIds = "";//胆场次
        String allMatchTimes = "";//所有比赛时间
        if (detail.size() != 0) {

            for (Map<String, Object> d : detail) {
                String results = "";
                int mixCount = 0;//混投胆数
                String matchId = (String) d.get("matchId");
                CdBasketballMixed cbm = cdBasketballMixedService.findByMatchId(matchId);
                if (cbm == null) {
                    return HttpResultUtil.errorJson("比赛不存在");
                }

                String shutDownTime = cbm.getTimeEndsale();
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (df.parse(shutDownTime).getTime() < day.getTime()) {
                    return HttpResultUtil.errorJson("超过比赛截止时间");
                }

                //是不是胆 0不是 1是
                String isMust = (String) d.get("isMust");
                if ("1".equals(buyWays)) {
                    if ("1".equals(isMust)) {
                        danCount++;
                    }
                }

                if ("1".equals(isMust)) {
                    danMatchIds += "胆" + "+" + matchId + ",";
                } else {
                    danMatchIds += "非" + "+" + matchId + ",";
                }


                //记录所有比赛时间
                CdBbNotFinsh cbnf = cdBbNotFinshService.getMatchByMatchId(matchId);
                if (cbnf != null) {
                    String time = cbnf.getDay();
                    allMatchTimes += time + ",";
                } else {
                    allMatchTimes += "2000-01-01 00:00:00" + ",";
                }

                String partDetail = isMust + "+" + matchId + "+" + cbm.getWinningName() + "vs" + cbm.getDefeatedName();

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


                //让球所有押注结果  3让分
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
                    letScore += cbm.getClose() + ",";
                    letDetail += partDetail + "+" + let + "+" + cbm.getClose().replaceAll("\\+", "") + "|";
                }


                //比分所有押注结果  4大小分
                String size = (String) d.get("size");
                if (StringUtils.isNotEmpty(size)) {
                    String sizeArry[] = size.split(",");
                    //String[] sStr = scoreArry[0].split("/");
                    if ("0".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            for (String s : sizeArry) {
                                results += s + ";";
                            }
                        } else {
                            resultList.add(size);
                        }
                    }
                    if ("1".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            if (sizeArry.length > 0) {
                                mixCount = mixCount + sizeArry.length;
                            }
                        } else {
                            danCount++;
                            danTimes = danTimes * sizeArry.length;
                        }

//                        if (!"1".equals(buyWays)) {
//                            danCount++;
//                        }
//                        //String scoreArry[] = score.split(",");
//                        danTimes = danTimes * scoreArry.length;
                    }
                    sizeCount += cbm.getZclose() + ",";
                    sizeDetail += partDetail + "+" + size + "|";
                }

                //主胜所有押注结果 5胜分差

                String hostWin = (String) d.get("hostWin");
                if (StringUtils.isNotEmpty(hostWin)) {
//                    String winArry[] = hostWin.split(",");
//                    //String[] sStr = goalArry[0].split("/");
//                    if ("0".equals(isMust)) {
//                        if ("1".equals(buyWays)) {
//                            for (String s : winArry) {
//                                results += s + ";";
//                            }
//                        } else {
//                            resultList.add(hostWin);
//                        }
//                    }
//                    if ("1".equals(isMust)) {
//                        if ("1".equals(buyWays)) {
//                            if (winArry.length > 0) {
//                                mixCount = mixCount + winArry.length;
//                            }
//                        } else {
//                            danCount++;
//                            danTimes = danTimes * winArry.length;
//                        }
//                    }
                    winDetail += partDetail + "+" + hostWin + "|";
                }
//
                //主负所有押注结果  5胜分差
                String hostFail = (String) d.get("hostFail");
                if (StringUtils.isNotEmpty(hostFail)) {
//                    String failArry[] = hostFail.split(",");
////                    String[] sStr = halfArry[0].split("/");
//                    if ("0".equals(isMust)) {
//                        if ("1".equals(buyWays)) {
//                            for (String s : failArry) {
//                                results += s + ";";
//                            }
//                        } else {
//                            resultList.add(hostFail);
//                        }
//                    }
//                    if ("1".equals(isMust)) {
//                        if ("1".equals(buyWays)) {
//                            if (failArry.length > 0) {
//                                mixCount = mixCount + failArry.length;
//                            }
//                        } else {
//                            danCount++;
//                            danTimes = danTimes * failArry.length;
//                        }
//                    }
                    failDetail += partDetail + "+" + hostFail + "|";
                }

//                if ("1".equals(buyWays)) {
//                    if ("0".equals(isMust)) {
//                        resultList.add(results);
//                    }
//                    if ("1".equals(isMust)) {
//                        danTimes = danTimes * mixCount;
//                    }
//                }
//                orderDetail += partDetail + "+" + isMust + "|";

                //胜负所有押注结果  5胜分差
//                String hostFailForAcount = (String) d.get("hostFail");
//                String hostWinAcount = (String) d.get("hostWin");
                String hostWinFail = hostFail + hostWin;
                if (StringUtils.isNotEmpty(hostWinFail)) {
                    String winFailArry[] = hostWinFail.split(",");
//                    String[] sStr = halfArry[0].split("/");
                    if ("0".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            for (String s : winFailArry) {
                                results += s + ";";
                            }
                        } else {
                            resultList.add(hostWinFail);
                        }
                    }
                    if ("1".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            if (winFailArry.length > 0) {
                                mixCount = mixCount + winFailArry.length;
                            }
                        } else {
                            danCount++;
                            danTimes = danTimes * winFailArry.length;
                        }
                    }
                    //failDetail += partDetail + "+" + hostFail + "|";
                }

                if ("1".equals(buyWays)) {
                    if ("0".equals(isMust)) {
                        resultList.add(results);
                    }
                    if ("1".equals(isMust)) {
                        danTimes = danTimes * mixCount;
                    }
                }

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
        String orderNum = util.genOrderNo("LCG", util.getFourRandom());
        //计算金额
        double money = 2.00;
        double acountDouble = Double.parseDouble(acountStr);
        double timesDouble = Double.parseDouble(times);
        String price = String.valueOf(money * acountDouble * timesDouble);

        CdBasketballFollowOrder cbfo = new CdBasketballFollowOrder();

        cbfo.setOrderNum(orderNum); //订单号
        cbfo.setAcount(acountStr);//注数
        cbfo.setAward("0"); //奖金
        //cffo.setOrderDetail(orderDetail); //订单详情
        cbfo.setHostFail(failDetail);//主负
        cbfo.setHostWin(winDetail);//主胜
        cbfo.setSize(sizeDetail);//大小分
        cbfo.setBeat(beatDetail);//胜负
        cbfo.setLet(letDetail);//让球胜负

        cbfo.setLetScore(letScore);//让分
        cbfo.setSizeCount(sizeCount);//大小分

        cbfo.setPrice(price);//金额
        cbfo.setStatus("1");//已提交
        cbfo.setUid(uid);//用户
        cbfo.setBuyWays(buyWays);//玩法 1混投 2胜负平 3猜比分 4总进球 5半全场 6让球
        cbfo.setFollowNums(followNum);//串关数
        cbfo.setTimes(times); //倍数
        cbfo.setDanMatchIds(danMatchIds);//胆场次
        cbfo.setType("0"); // 0普通订单 1发起的 2跟单的
        cbfo.setBestType("1");//1普通单 2优化单
        cbfo.setAllMatchTimes(allMatchTimes);//所有比赛时间
        try {
            cdBasketballFollowOrderService.save(cbfo);
            map.put("orderNum", orderNum);
            map.put("orderName", "竞彩篮球订单支付");
            map.put("time", cbfo.getCreateDate());
            map.put("price", price);
            map.put("acountStr", acountStr);//注数
            map.put("times", times);//倍数
            map.put("followNum", followNum);//串关
        } catch (Exception e) {
            return HttpResultUtil.errorJson("保存失败");
        }

        logger.info("basketballFollowOrderCommit---------End---------------------");
        return HttpResultUtil.successJson(map);
    }


    /**
     * 篮球串关 奖金优化 订单提交
     */
    @RequestMapping(value = "basketballBestFollowOrderCommit", method = RequestMethod.POST)
    @ResponseBody
    public String basketballBestFollowOrderCommit(HttpServletRequest request) {
        logger.info("basketballBestFollowOrderCommit--------Start-------------------");
        logger.debug("basketballBestFollowOrderCommit-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        //玩法 1混投 2胜负 3让分胜负 4大小分 5胜分差
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
        String orderNum = util.genOrderNo("LCG", util.getFourRandom());
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
            String letScore = "";

            String allMatchTimes = "";//所有比赛时间
            for (String s : matchSet) {
                CdBasketballMixed cbm = cdBasketballMixedService.findByMatchId(s);
                if (cbm == null) {
                    return HttpResultUtil.errorJson("比赛不存在！");
                }
                danMatchIds += "非+" + s + ",";
                //记录所有比赛时间
                CdBbNotFinsh cbnf = cdBbNotFinshService.getMatchByMatchId(s);
                if (cbnf != null) {
                    String time = cbnf.getDay();
                    allMatchTimes += time + ",";
                } else {
                    allMatchTimes += "2000-01-01 00:00:00" + ",";
                }


                letScore += cbm.getClose() + ",";

                Map<String, String> aDetailMap = new HashMap<>();
                aDetailMap.put("hostWin", "");
                aDetailMap.put("hostFail", "");
                aDetailMap.put("beat", "");
                aDetailMap.put("size", "");
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
                    CdBasketballMixed cbm = cdBasketballMixedService.findByMatchId(matchId);
                    if (cbm == null) {
                        return HttpResultUtil.errorJson("比赛不存在！");
                    }
                    String close = cbm.getClose().replaceAll("\\+", "");//让分
                    String zclose = cbm.getZclose();//大小分分数
                    matchIds += matchId + ",";
                    String odds = (String) aBestDeatil.get("odds");//赔率
                    String name = (String) aBestDeatil.get("name");//球队
                    String sf = (String) aBestDeatil.get("sf");//具体投注 副表
                    String sm = BallGameCals.changeBasketballSf(sf);//主表字段统一
                    String buyWay = (String) aBestDeatil.get("buyWays");//玩法
                    //场次  + 玩法 + 投注内容/赔率  + 队名/让分/大小分
                    String minDetail = matchId + "+" + buyWay + "+" + sf + "/" + odds + "+" + name + "/" + close + "/" + zclose + "|";
                    bestDetail += minDetail;//拼出一条优化详情
                    //处理数据给订单总表-----------*-----贼------*------他------*------妈-------*精妙*-------------------
                    String newSf = detailMap.get(matchId).get(buyWay);
//                    String[] newSfArray = newSf.split(",");
//                    for (String s : newSfArray) {
//                        if (!s.split("/")[0].equals(sm)) {
//                            newSf += sm + "/" + odds + ",";
//                        }
//                    }
                    String str = sm + "/" + odds;
                    if (!newSf.contains(str)) {
                        newSf += str + ",";
                    }
                    detailMap.get(matchId).put(buyWay, newSf);
                }
                //保存优化订单一条
                CdBasketballBestFollowOrder cbbfo = new CdBasketballBestFollowOrder();
                cbbfo.setOrderNum(orderNum);//归属订单号
                cbbfo.setMatchIds(matchIds);//所有比赛
                cbbfo.setOrderDetail(bestDetail);//具体详情
                cbbfo.setPerAward(perAward);//奖金
                cbbfo.setPerTimes(perTimes);//倍数
                cdBasketballBestFollowOrderService.save(cbbfo);
            }

            //订单总表的字段
            String hostWin = "";
            String hostFail = "";
            String beat = "";
            String size = "";
            String let = "";
            String sizeCount = "";
            for (String s : matchSet) {
                Map<String, String> aMap = detailMap.get(s);
                CdBasketballMixed cbm = cdBasketballMixedService.findByMatchId(s);
                String head = "0+" + s + "+" + cbm.getWinningName() + "vs" + cbm.getDefeatedName() + "+";
                if (StringUtils.isNotEmpty(aMap.get("hostWin"))) {
                    hostWin += head + aMap.get("hostWin") + "|";
                }
                if (StringUtils.isNotEmpty(aMap.get("hostFail"))) {
                    hostFail += head + aMap.get("hostFail") + "|";
                }
                if (StringUtils.isNotEmpty(aMap.get("beat"))) {
                    beat += head + aMap.get("beat") + "|";
                }
                if (StringUtils.isNotEmpty(aMap.get("size"))) {
                    sizeCount += cbm.getZclose() + ",";
                    size += head + aMap.get("size") + "|";
                }
                if (StringUtils.isNotEmpty(aMap.get("let"))) {
                    let += head + aMap.get("let") + "|";
                }
            }
            //保存订单主表
            CdBasketballFollowOrder cbfo = new CdBasketballFollowOrder();
            cbfo.setOrderNum(orderNum); //订单号
            cbfo.setAcount(String.valueOf(acount));//注数
            cbfo.setAward("0"); //奖金
            BigDecimal countBig = new BigDecimal(acount);
            BigDecimal price = countBig.multiply(new BigDecimal(2)).setScale(2, 1);
            cbfo.setPrice(String.valueOf(price));//金额
            cbfo.setStatus("1");//已提交
            cbfo.setUid(uid);//用户
            cbfo.setBuyWays(buyWays);//玩法 1混投 2胜负平 3猜比分 4总进球 5半全场 6让球
            cbfo.setFollowNums(followNum);//串关数
            cbfo.setTimes("1"); //倍数
            cbfo.setDanMatchIds(danMatchIds);//胆场次
            cbfo.setType("0"); // 0普通订单 1发起的 2跟单的
            cbfo.setBestType("2");//1普通单 2优化单

            cbfo.setHostWin(hostWin);//主胜
            cbfo.setHostFail(hostFail);//主负

            cbfo.setBeat(beat);//胜负
            cbfo.setLet(let);//让球胜负
            cbfo.setSize(size);//大小

//            String letScore = "";//大小让分
//            String[] sizeArray=size.split("\\|");
//            for(String s:sizeArray){
//                String[] sArray=s.split("\\+");
//                String matchId=sArray[1];
//                CdBasketballMixed cbm=cdBasketballMixedService.findByMatchId(matchId);
//                letScore+=cbm.getClose();
//            }

            cbfo.setSizeCount(sizeCount);//比大小的分
            cbfo.setLetScore(letScore);//让分

            cbfo.setAllMatchTimes(allMatchTimes);
            cdBasketballFollowOrderService.save(cbfo);


            map.put("orderNum", orderNum);
            map.put("orderName", "竞彩篮球订单支付");
            map.put("time", cbfo.getCreateDate());
            map.put("price", String.valueOf(price));
            map.put("acountStr", String.valueOf(acount));//注数
            map.put("times", "1");//倍数
            map.put("followNum", followNum);//串关


        }
        logger.info("basketballFollowOrderCommit---------End---------------------");
        return HttpResultUtil.successJson(map);

    }


}
