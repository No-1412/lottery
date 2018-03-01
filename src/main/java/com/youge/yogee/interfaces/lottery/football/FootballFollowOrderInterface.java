package com.youge.yogee.interfaces.lottery.football;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.util;
import com.youge.yogee.modules.cfootballmixed.entity.CdFootballMixed;
import com.youge.yogee.modules.cfootballmixed.service.CdFootballMixedService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;
import net.sf.json.JSONArray;
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
 * Created by zhaoyifeng on 2018-02-24.
 */
@Controller
@RequestMapping("${frontPath}")
public class FootballFollowOrderInterface {
    private static final Logger logger = LoggerFactory.getLogger(FootballSingleOrderInterface.class);

    @Autowired
    private CdFootballFollowOrderService cdFootballFollowOrderService;

    @Autowired
    private CdFootballMixedService cdFootballMixedService;

    /**
     * 足球串关 提交订单
     */
    @RequestMapping(value = "footballFollowOrderCommit", method = RequestMethod.POST)
    @ResponseBody
    public String footballFollowOrderCommit(HttpServletRequest request) {
        logger.info(" interface footballFollowOrderCommit--------Start-------------------");
        logger.debug("interface 请求--footballFollowOrderCommit-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        //玩法 1混投 2胜负平 3猜比分 4总进球 5半全场 6让球
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
        String letBalls="";

        int acount = 0;//注数
        int danCount = 0;//胆数
        int danTimes = 1;//胆注数
        if (detail.size() != 0) {

            for (Map<String, Object> d : detail) {
                String results = "";
                int mixCount = 0;//混投胆数
                String matchId = (String) d.get("matchId");
                CdFootballMixed sfm = cdFootballMixedService.findByMatchId(matchId);
                if (sfm == null) {
                    return HttpResultUtil.errorJson("比赛不存在");
                }

                //是不是胆 0不是 1是
                String isMust = (String) d.get("isMust");
                if ("1".equals(buyWays)) {
                    if ("1".equals(isMust)) {
                        danCount++;
                    }
                }
                String partDetail = isMust + "+" + matchId + "+" + sfm.getWinningName() + "vs" + sfm.getDefeatedName();
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
                    letBalls+=sfm.getClose()+",";
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
        cffo.setStauts("1");//已提交
        cffo.setUid(uid);//用户
        cffo.setBuyWays(buyWays);//玩法 1混投 2胜负平 3猜比分 4总进球 5半全场 6让球
        cffo.setFollowNum(followNum);//串关数
        cffo.setTimes(times); //倍数

        try {
            cdFootballFollowOrderService.save(cffo);
            map.put("flag", "1");
        } catch (Exception e) {
            return HttpResultUtil.errorJson("保存失败");
        }

        logger.info("footballFollowOrderCommit---------End---------------------");
        return HttpResultUtil.successJson(map);
    }


}
