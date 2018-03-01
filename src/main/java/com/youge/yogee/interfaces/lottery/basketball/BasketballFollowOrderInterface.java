package com.youge.yogee.interfaces.lottery.basketball;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.lottery.football.FootballSingleOrderInterface;
import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.util;
import com.youge.yogee.modules.cbasketballmixed.entity.CdBasketballMixed;
import com.youge.yogee.modules.cbasketballmixed.service.CdBasketballMixedService;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
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

    /**
     * 篮球串关 提交订单
     */
    @RequestMapping(value = "basketballFollowOrderCommit", method = RequestMethod.POST)
    @ResponseBody
    public String basketballFollowOrderCommit(HttpServletRequest request) {
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
        if (detail.size() != 0) {

            for (Map<String, Object> d : detail) {
                String results = "";
                int mixCount = 0;//混投胆数
                String matchId = (String) d.get("matchId");
                CdBasketballMixed cbm = cdBasketballMixedService.findByMatchId(matchId);
                if (cbm == null) {
                    return HttpResultUtil.errorJson("比赛不存在");
                }

                //是不是胆 0不是 1是
                String isMust = (String) d.get("isMust");
                if ("1".equals(buyWays)) {
                    if ("1".equals(isMust)) {
                        danCount++;
                    }
                }

                if ("1".equals(isMust)) {
                    danMatchIds += matchId + ",";
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
                    letDetail += partDetail + "+" + let + "|";
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
                    String winArry[] = hostWin.split(",");
                    //String[] sStr = goalArry[0].split("/");
                    if ("0".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            for (String s : winArry) {
                                results += s + ";";
                            }
                        } else {
                            resultList.add(hostWin);
                        }
                    }
                    if ("1".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            if (winArry.length > 0) {
                                mixCount = mixCount + winArry.length;
                            }
                        } else {
                            danCount++;
                            danTimes = danTimes * winArry.length;
                        }
                    }
                    winDetail += partDetail + "+" + hostWin + "|";
                }

                //主负所有押注结果  5胜分差
                String hostFail = (String) d.get("hostFail");
                if (StringUtils.isNotEmpty(hostFail)) {
                    String failArry[] = hostFail.split(",");
//                    String[] sStr = halfArry[0].split("/");
                    if ("0".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            for (String s : failArry) {
                                results += s + ";";
                            }
                        } else {
                            resultList.add(hostFail);
                        }
                    }
                    if ("1".equals(isMust)) {
                        if ("1".equals(buyWays)) {
                            if (failArry.length > 0) {
                                mixCount = mixCount + failArry.length;
                            }
                        } else {
                            danCount++;
                            danTimes = danTimes * failArry.length;
                        }
                    }
                    failDetail += partDetail + "+" + hostFail + "|";
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

        try {
            cdBasketballFollowOrderService.save(cbfo);
            map.put("flag", "1");
        } catch (Exception e) {
            return HttpResultUtil.errorJson("保存失败");
        }

        logger.info("basketballFollowOrderCommit---------End---------------------");
        return HttpResultUtil.successJson(map);
    }


}
