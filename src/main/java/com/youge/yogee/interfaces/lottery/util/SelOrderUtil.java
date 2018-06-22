package com.youge.yogee.interfaces.lottery.util;

import com.youge.yogee.common.utils.SpringContextHolder;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.modules.cbasketballawards.entity.CdBasketballAwards;
import com.youge.yogee.modules.cbasketballawards.service.CdBasketballAwardsService;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballSingleOrderService;
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNineOrder;
import com.youge.yogee.modules.cchoosenine.service.CdChooseNineOrderService;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveOrder;
import com.youge.yogee.modules.cfiveawards.service.CdFiveOrderService;
import com.youge.yogee.modules.cfootballawards.entity.CdFootballAwards;
import com.youge.yogee.modules.cfootballawards.service.CdFootballAwardsService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.clottoreward.entity.CdLottoOrder;
import com.youge.yogee.modules.clottoreward.service.CdLottoOrderService;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFailOrder;
import com.youge.yogee.modules.csuccessfail.service.CdSuccessFailOrderService;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeOrder;
import com.youge.yogee.modules.cthreeawards.service.CdThreeOrderService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class SelOrderUtil {


    private static CdBasketballSingleOrderService cdBasketballSingleOrderService = SpringContextHolder.getBean(CdBasketballSingleOrderService.class);

    private static CdBasketballFollowOrderService cdBasketballFollowOrderService = SpringContextHolder.getBean(CdBasketballFollowOrderService.class);

    private static CdFootballFollowOrderService cdFootballFollowOrderService = SpringContextHolder.getBean(CdFootballFollowOrderService.class);

    private static CdFootballSingleOrderService cdFootballSingleOrderService = SpringContextHolder.getBean(CdFootballSingleOrderService.class);

    private static CdThreeOrderService cdThreeOrderService = SpringContextHolder.getBean(CdThreeOrderService.class);

    private static CdFiveOrderService cdFiveOrderService = SpringContextHolder.getBean(CdFiveOrderService.class);

    private static CdLottoOrderService cdLottoOrderService = SpringContextHolder.getBean(CdLottoOrderService.class);

    private static CdChooseNineOrderService cdChooseNineOrderService = SpringContextHolder.getBean(CdChooseNineOrderService.class);

    private static CdSuccessFailOrderService cdSuccessFailOrderService = SpringContextHolder.getBean(CdSuccessFailOrderService.class);

    private static CdLotteryUserService cdLotteryUserService = SpringContextHolder.getBean(CdLotteryUserService.class);

    private static CdFootballAwardsService cdFootballAwardsService = SpringContextHolder.getBean(CdFootballAwardsService.class);

    private static CdBasketballAwardsService cdBasketballAwardsService = SpringContextHolder.getBean(CdBasketballAwardsService.class);

    /**
     * 订单详情
     *
     * @param orderNum
     * @return
     */
    public static Map getOrderDetailMap(String orderNum, Map map) throws ParseException {
        //Map<String, Object> map = new HashMap();
        if (orderNum.startsWith("ZDG")) {
            CdFootballSingleOrder cfs = cdFootballSingleOrderService.findOrderByOrderNum(orderNum);
            List detailList = getFbSingleList(cfs);
            map.put("buyWays", cfs.getBuyWays());
            map.put("followNums", "0");
            map.put("detail", detailList);
            map.put("price", cfs.getPrice());
            map.put("award", cfs.getAward());

        } else if (orderNum.startsWith("ZCG")) {
            CdFootballFollowOrder cff = cdFootballFollowOrderService.findOrderByOrderNum(orderNum);
            List detailList = getFbFollowList(cff);
            map.put("buyWays", cff.getBuyWays());
            map.put("followNums", cff.getFollowNum());
            map.put("detail", detailList);
            map.put("price", cff.getPrice());
            map.put("award", cff.getAward());
        } else if (orderNum.startsWith("LDG")) {
            CdBasketballSingleOrder cbs = cdBasketballSingleOrderService.findOrderByOrderNum(orderNum);
            List detailList = getBbSingleList(cbs);
            map.put("followNums", "0");
            map.put("buyWays", cbs.getBuyWays());
            map.put("detail", detailList);
            map.put("price", cbs.getPrice());
            map.put("award", cbs.getAward());
        } else if (orderNum.startsWith("LCG")) {
            CdBasketballFollowOrder cbf = cdBasketballFollowOrderService.findOrderByOrderNum(orderNum);
            List detailList = getBbFollowList(cbf);
            map.put("buyWays", cbf.getBuyWays());
            map.put("followNums", cbf.getFollowNums());
            map.put("detail", detailList);
            map.put("price", cbf.getPrice());
            map.put("award", cbf.getAward());
        } else if (orderNum.startsWith("RXJ")) {
            CdChooseNineOrder ccno = cdChooseNineOrderService.findOrderByOrderNum(orderNum);
            map.put("price", ccno.getPrice());//投注金额
            String orderDetail = ccno.getOrderDetail(); //投注内容
            String[] orderDetailArray = orderDetail.split("\\|");
            String result = ccno.getResult();  //比赛结果
            List<String> rList = new ArrayList<>();
            if (StringUtils.isNotEmpty(result)) {
                for (String s : result.split(",")) {
                    rList.add(s);
                }
            }
            List detailList = new ArrayList();
            for (String aOrderDetail : orderDetailArray) {
                Map detailMap = getDetailMap(aOrderDetail, rList);
                detailList.add(detailMap);
            }
            map.put("detail", detailList);
            map.put("award", ccno.getAward());
        } else if (orderNum.startsWith("SFC")) {
            CdSuccessFailOrder csfo = cdSuccessFailOrderService.findOrderByOrderNum(orderNum);
            map.put("price", csfo.getPrice());//投注金额
            String orderDetail = csfo.getOrderDetail(); //投注内容
            String[] orderDetailArray = orderDetail.split("\\|");
            String result = csfo.getResult();  //比赛结果
            List<String> rList = new ArrayList<>();
            if (StringUtils.isNotEmpty(result)) {
                for (String s : result.split(",")) {
                    rList.add(s);
                }
            }
            List detailList = new ArrayList();
            for (String aOrderDetail : orderDetailArray) {
                Map detailMap = getDetailMap(aOrderDetail, rList);
                detailList.add(detailMap);
            }
            map.put("award", csfo.getAward());
            map.put("detail", detailList);
        } else if (orderNum.startsWith("PLS")) {
            CdThreeOrder dto = cdThreeOrderService.findOrderByOrderNum(orderNum);
            map.put("buyWays", dto.getBuyWays());//排列三玩法
            map.put("codes", dto.getNums()); //押注详情
            map.put("mResult", dto.getResult());//中奖结果
            map.put("price", dto.getPrice());//投注金额
            map.put("award", dto.getAward());
        } else if (orderNum.startsWith("PLW")) {
            CdFiveOrder cfo = cdFiveOrderService.findOrderByOrderNum(orderNum);
            map.put("buyWays", cfo.getBuyWays());//排列五玩法
            map.put("codes", cfo.getNums()); //押注详情
            map.put("mResult", cfo.getResult());//中奖结果
            map.put("price", cfo.getPrice());//投注金额
            map.put("award", cfo.getAward());
        } else if (orderNum.startsWith("DLT")) {
            CdLottoOrder clo = cdLottoOrderService.findOrderByOrderNum(orderNum);
            String nums = clo.getRedNums() + "|" + clo.getBlueNums();
            map.put("buyWays", clo.getType());//大乐透方式胆拖/普通
            map.put("codes", nums); //押注详情
            map.put("mResult", clo.getResult());//中奖结果
            map.put("price", clo.getPrice());//投注金额
            map.put("award", clo.getAward());
        }
        return map;
    }


    public static Map getOrderDetailMapToPay(String orderNum, Map map) {
        //Map<String, Object> map = new HashMap();
        if (orderNum.startsWith("ZDG")) {
            CdFootballSingleOrder cfs = cdFootballSingleOrderService.findOrderByOrderNum(orderNum);
            map.put("price", cfs.getPrice()); //价格
            map.put("acount", cfs.getAcount()); //注数
            map.put("times", "1"); //倍数
            map.put("followNums", "单关");// 过关方式
            map.put("buyWays", cfs.getBuyWays()); //1混投 2胜负平 3猜比分 4总进球 5半全场 6让球
        } else if (orderNum.startsWith("ZCG")) {
            CdFootballFollowOrder cff = cdFootballFollowOrderService.findOrderByOrderNum(orderNum);
            map.put("price", cff.getPrice());
            map.put("acount", cff.getAcount());
            map.put("times", cff.getTimes());
            map.put("followNums", cff.getFollowNum());
            map.put("buyWays", cff.getBuyWays()); //1混投 2胜负平 3猜比分 4总进球 5半全场 6让球
        } else if (orderNum.startsWith("LDG")) {
            CdBasketballSingleOrder cbs = cdBasketballSingleOrderService.findOrderByOrderNum(orderNum);
            map.put("price", cbs.getPrice());
            map.put("acount", cbs.getAcount());
            map.put("times", "1");
            map.put("followNums", "单关");
            map.put("buyWays", cbs.getBuyWays()); //1混投 2胜负 3让分胜负 4大小分 5胜分差
        } else if (orderNum.startsWith("LCG")) {
            CdBasketballFollowOrder cbf = cdBasketballFollowOrderService.findOrderByOrderNum(orderNum);
            map.put("price", cbf.getPrice());
            map.put("acount", cbf.getAcount());
            map.put("times", cbf.getTimes());
            map.put("followNums", cbf.getFollowNums());
            map.put("buyWays", cbf.getBuyWays()); //1混投 2胜负 3让分胜负 4大小分 5胜分差
        }
        return map;
    }


    /**
     * 胜负平转换
     *
     * @param aOrderDetail
     * @param rList
     * @return
     */
    private static Map getDetailMap(String aOrderDetail, List<String> rList) {
        int i = 0;
        String[] aOrderDetailArray = aOrderDetail.split("\\+");
        Map detailMap = new HashMap();
        detailMap.put("no", aOrderDetailArray[0]);//序号
        detailMap.put("vs", aOrderDetailArray[1]);//比赛队伍
        //detailMap.put("codes", aOrderDetailArray[2]);//投注详情
        String aDetail = aOrderDetailArray[2];
        String newAdetail = aDetail.replace("3", "胜");
        newAdetail = newAdetail.replace("1", "平");
        newAdetail = newAdetail.replace("0", "负");
        detailMap.put("codes", newAdetail);//投注详情
        String mResult = "";
        if (rList.size() > 0) {
            mResult = rList.get(i);
            if ("3".equals(mResult)) {
                mResult = "胜";
            } else if ("1".equals(mResult)) {
                mResult = "平";
            } else if ("0".equals(mResult)) {
                mResult = "负";
            } else {
                mResult = "*";
            }
            i++;
        }
        detailMap.put("mResult", mResult);//投注详情

        return detailMap;
    }

    /**
     * @param s
     * @param strArray
     * @return
     */
    private static Map<String, String> getSingleMap(String s, String[] strArray) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotEmpty(strArray[0])) {
            for (String aStr : strArray) {
                String[] aStrArray = aStr.split("\\+");
                if (s.equals(aStrArray[0])) {
                    map.put("vs", aStrArray[1]);
                    map.put("result", aStrArray[2]);
                }
            }
        }
        return map;
    }


    private static Map<String, String> getSingleMap2(String s, String[] strArray, String[] letBalls) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotEmpty(strArray[0])) {
            int i = 0;
            for (String aStr : strArray) {
                String[] aStrArray = aStr.split("\\+");
                if (s.equals(aStrArray[0])) {
                    map.put("vs", aStrArray[1]);
                    map.put("result", aStrArray[2]);
                    if (!"".equals(letBalls[0])) {
                        map.put("letBall", letBalls[i]);
                        i++;
                    }
                }
            }
        }
        return map;
    }

    private static Map<String, String> getFollowMap(String s, String[] strArray) {
        Map<String, String> map = new HashMap<>();
        if (!"".equals(strArray[0])) {
            for (String aStr : strArray) {
                String[] aStrArray = aStr.split("\\+");
                if (s.equals(aStrArray[1])) {
                    map.put("vs", aStrArray[2]);
                    map.put("result", aStrArray[3]);

                }
            }
        }
        return map;
    }


//    private static Map<String, String> getFollowMap2(String s, String[] strArray,String[] letBall) {
//        Map<String, String> map = new HashMap<>();
//        if (!"".equals(strArray[0])) {
//            for (String aStr : strArray) {
//                String[] aStrArray = aStr.split("\\+");
//                if (s.equals(aStrArray[1])) {
//                    map.put("vs", aStrArray[2]);
//                    map.put("result", aStrArray[3]);
//
//                }
//            }
//        }
//        return map;
//    }


//    private static Map<String, String> getFollowMap2(String s, String[] strArray, String[] letScore) {
//        Map<String, String> map = new HashMap<>();
//        if (!"".equals(strArray[0])) {
//            int i = 0;
//            for (String aStr : strArray) {
//                String[] aStrArray = aStr.split("\\+");
//                if (s.equals(aStrArray[1])) {
//                    map.put("vs", aStrArray[2]);
//                    map.put("result", aStrArray[3]);
//                    if (!"".equals(letScore[0])) {
//                        map.put("letScore", letScore[i]);
//                        i++;
//                    }
//                }
//            }
//        }
//        return map;
//    }


    public static List getFbFollowList(CdFootballFollowOrder cff) throws ParseException {
        List detailList = new ArrayList();
        //比分
        String score = cff.getScore();
        String[] scoreArray = score.split("\\|");
        //进球
        String goal = cff.getGoal();
        String[] goalArray = goal.split("\\|");
        //半全场
        String half = cff.getHalf();
        String[] halfArray = half.split("\\|");
        //胜负
        String beat = cff.getBeat();
        String[] beatArray = beat.split("\\|");
        //让球
        String let = cff.getLet();
        String[] letArray = let.split("\\|");
        //所有比赛
        String matchIds = cff.getDanMatchIds();
        String[] matchIdsArray = matchIds.split(",");
        String vs = "";
        String matchResult = cff.getResult();//比赛结果

        String createDate = cff.getCreateDate();


        int i = 0;
        String matchTimes = cff.getAllMatchTimes();//所有比赛时间
        String[] matchTimesArray = matchTimes.split(",");
        int j = 0;

        for (String s : matchIdsArray) {
            List<String> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(matchResult)) {
                String[] mathchResultArray = matchResult.split(",");
                for (String rs : mathchResultArray) {
                    resultList.add(rs);
                }
            }
//           ------------------------又一次贼他妈精妙-------------------------
            //跟单订单 未开赛直接跳出循环
            if ("2".equals(cff.getType())) {
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String aTime = matchTimesArray[j];
                j++;
                if (df.parse(aTime).getTime() > day.getTime()) {
                    continue;
                }
            }

            String match = s.split("\\+")[1];
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("matchId", match);

            //比赛结果
            if (resultList.size() > 0) {
                orderMap.put("result", resultList.get(i));
                i++;
            } else {
                orderMap.put("result", "");
            }

            //比分
            Map<String, String> scoreMap = getFollowMap(match, scoreArray);
            if (scoreMap.size() > 0) {
                vs = scoreMap.get("vs");
            }
            orderMap.put("score", scoreMap.get("result"));
            //进球
            Map<String, String> goalMap = getFollowMap(match, goalArray);
            if (goalMap.size() > 0) {
                vs = goalMap.get("vs");
            }
            orderMap.put("goal", goalMap.get("result"));

            //2018.5.28 通过 matchId 获取 AwardsService 信息
            CdFootballAwards byMatchId = cdFootballAwardsService.findByMatchId(match);

            //判断时间 超过3天 则不处理结果
            Date nowDate = new Date();
            long nowTime = nowDate.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            long orderTime = dateFormat.parse(createDate).getTime();

            if (byMatchId == null || nowTime - orderTime > 259200000) {
                //半全场
                String halfName = "";
                Map<String, String> halfMap = getFollowMap(match, halfArray);
                if (halfMap.size() > 0) {
                    vs = halfMap.get("vs");
                    Map<String, String> nameMap = BallGameCals.getHalfWholeNames();
                    String result = halfMap.get("result");
                    String resultArray[] = result.split(",");
                    for (String r : resultArray) {
                        String wholeResult = "";
                        String[] rArray = r.split("/");
                        wholeResult = nameMap.get(rArray[0]) + "/" + rArray[1];
                        if (StringUtils.isNotEmpty(result)) {
                            halfName += wholeResult + ",";
                        }
                    }
                }
                //胜负平
                Map<String, String> beatMap = getFollowMap(match, beatArray);
                if (beatMap.size() > 0) {
                    vs = beatMap.get("vs");
                }

                String trueBeat = beatMap.get("result");
                String realBeat = "";
                if (StringUtils.isNotEmpty(trueBeat)) {
                    //********
                    String finalBeat1 = trueBeat.replaceAll("3/", "主胜/");
                    String finalBeat2 = finalBeat1.replaceAll("1/", "平/");
                    String finalBeat3 = finalBeat2.replaceAll("0/", "主负/");
                    realBeat = finalBeat3;
                }
                //让球
                Map<String, String> letMap = getFollowMap(match, letArray);
                if (letMap.size() > 0) {
                    vs = letMap.get("vs");
                }
                String trueLet = letMap.get("result");
                String realLet = "";
                if (StringUtils.isNotEmpty(trueLet)) {
                    String finalLet1 = trueLet.replaceAll("3/", "让主胜/");
                    String finalLet2 = finalLet1.replaceAll("1/", "平/");
                    String finalLet3 = finalLet2.replaceAll("0/", "让主负/");
                    realLet = finalLet3;
                }
                orderMap.put("half", halfName); //半全场
                orderMap.put("beat", realBeat);
                orderMap.put("let", realLet);
                orderMap.put("vs", vs);
                detailList.add(orderMap);

            } else {
                String winGrap = byMatchId.getWinGrap();
                String winning = byMatchId.getWinning();
                String spread = byMatchId.getSpread();

                //半全场
                String halfName = "";
                Map<String, String> halfMap = getFollowMap(match, halfArray);
                if (halfMap.size() > 0) {
                    vs = halfMap.get("vs");
                    Map<String, String> nameMap = BallGameCals.getHalfWholeNames();
                    String result = halfMap.get("result");
                    String resultArray[] = result.split(",");
                    String trueArray = "";
                    for (String r : resultArray) {
                        String wholeResult = "";
                        String halfResult = "";
                        String[] rArray = r.split("/");
                        wholeResult = nameMap.get(rArray[0]) + "/" + rArray[1];

                        if (StringUtils.isNotEmpty(result)) {
                            halfName += wholeResult + ",";
                        }

                        halfResult = nameMap.get(rArray[0]);
                        if (halfResult.equals(winGrap)) {
                            trueArray = rArray[1];
                        } else {
                            trueArray = "out";
                        }
                    }
                    //2018 5 30　董宏 修改 判断result不为空返回 彩果
                    if (byMatchId != null) {
                        if (StringUtils.isNotEmpty(matchResult)) {
                            halfName += "+" + winGrap + "/" + trueArray;
                        }
                    }
                }


                //胜负平
                Map<String, String> beatMap = getFollowMap(match, beatArray);
                if (beatMap.size() > 0) {
                    vs = beatMap.get("vs");
                }

                String trueBeat = beatMap.get("result");
                String realBeat = "";
                if (StringUtils.isNotEmpty(trueBeat)) {
                    //********
                    String finalBeat1 = trueBeat.replaceAll("3/", "主胜/");
                    String finalBeat2 = finalBeat1.replaceAll("1/", "平/");
                    String finalBeat3 = finalBeat2.replaceAll("0/", "主负/");
                    realBeat = finalBeat3;
                }
                //让球
                Map<String, String> letMap = getFollowMap(match, letArray);
                if (letMap.size() > 0) {
                    vs = letMap.get("vs");
                }
                String trueLet = letMap.get("result");
                String realLet = "";
                if (StringUtils.isNotEmpty(trueLet)) {
                    String finalLet1 = trueLet.replaceAll("3/", "让主胜/");
                    String finalLet2 = finalLet1.replaceAll("1/", "平/");
                    String finalLet3 = finalLet2.replaceAll("0/", "让主负/");
                    realLet = finalLet3;
                }

                //判断胜负 是否猜对
                String[] realBeatSplit = realBeat.split(",");
                String oddResult = "";
                for (int r = 0; r < realBeatSplit.length; r++) {
                    String[] splitReal = realBeatSplit[r].split("/");
                    if (splitReal[0].equals(winning)) {
                        oddResult = splitReal[1];
                    } else {
                        oddResult = "out";
                    }
                }
                //判断让球 是否猜对
                String[] realLetSplit = realLet.split(",");
                String oddLetResult = "";
                for (int x = 0; x < realLetSplit.length; x++) {
                    String[] splitReal = realLetSplit[x].split("/");
                    if (splitReal[0].equals(spread)) {
                        oddLetResult = splitReal[1];
                    } else {
                        oddLetResult = "out";
                    }
                }

                if (StringUtils.isNotEmpty(matchResult)) {
                    if (StringUtils.isNotEmpty(trueBeat)) {
                        realBeat += "+" + winning + "/" + oddResult;
                    }
                    if (StringUtils.isNotEmpty(trueLet)) {
                        realLet += "+" + spread + "/" + oddLetResult;
                    }
                }
                orderMap.put("half", halfName); //半全场
                orderMap.put("beat", realBeat);
                orderMap.put("let", realLet);
                orderMap.put("vs", vs);
                detailList.add(orderMap);
            }
        }
        return detailList;
    }

    public static List getFbSingleList(CdFootballSingleOrder cfs) throws ParseException {
        List detailList = new ArrayList();
        //比分
        String score = cfs.getScore();
        String[] scoreArray = score.split("\\|");
        //进球
        String goal = cfs.getGoal();
        String[] goalArray = goal.split("\\|");
        //半全场
        String half = cfs.getHalf();
        String[] halfArray = half.split("\\|");
        //胜负
        String beat = cfs.getBeat();
        String[] beatArray = beat.split("\\|");
        //让球
        String let = cfs.getLet();
        String[] letArray = let.split("\\|");
        //所有比赛
        String matchIds = cfs.getMatchIds();
        String[] matchIdsArray = matchIds.split(",");
        String vs = "";
        String matchResult = cfs.getResult();//比赛结果
        int i = 0;
        String allMatchTimes = cfs.getAllMatchTimes();
        String[] matchTimesArray = allMatchTimes.split(",");
        int j = 0;

        String createDate = cfs.getCreateDate();
        for (String s : matchIdsArray) {
            List<String> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(matchResult)) {
                String[] mathchResultArray = matchResult.split(",");
                for (String rs : mathchResultArray) {
                    resultList.add(rs);
                }
            }
            //           ------------------------又一次贼他妈精妙-------------------------
            //跟单订单 未开赛直接跳出循环
            if ("2".equals(cfs.getType())) {
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String aTime = matchTimesArray[j];
                j++;
                if (df.parse(aTime).getTime() > day.getTime()) {
                    continue;
                }
            }

            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("matchId", s); //期次

            //比赛结果
            if (resultList.size() > 0) {
                orderMap.put("result", resultList.get(i));
                i++;
            } else {
                orderMap.put("result", "");
            }


            //比分
            Map<String, String> scoreMap = getSingleMap(s, scoreArray);
            if (scoreMap.size() > 0) {
                vs = scoreMap.get("vs"); //队伍
            }
            String trueScore = scoreMap.get("result");
            orderMap.put("score", trueScore); //比分

            //进球
            Map<String, String> goalMap = getSingleMap(s, goalArray);
            if (goalMap.size() > 0) {
                vs = goalMap.get("vs");
            }
            orderMap.put("goal", goalMap.get("result")); //进球


            //2018.5.28 通过 matchId 获取 AwardsService 信息
            CdFootballAwards byMatchId = cdFootballAwardsService.findByMatchId(s);

            //判断时间 超过3天 则不处理结果
            Date nowDate = new Date();
            long nowTime = nowDate.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long orderTime = dateFormat.parse(createDate).getTime();

            if (byMatchId == null || nowTime - orderTime > 259200000) {
                //半全场
                String halfName = "";
                Map<String, String> halfMap = getSingleMap(s, halfArray);
                if (halfMap.size() > 0) {
                    vs = halfMap.get("vs");
                    Map<String, String> nameMap = BallGameCals.getHalfWholeNames();
                    String result = halfMap.get("result");
                    String resultArray[] = result.split(",");
                    String realWinGrap = "";
                    String trueArray = "";
                    for (String r : resultArray) {
                        String wholeResult = "";
                        String[] rArray = r.split("/");
                        wholeResult = nameMap.get(rArray[0]) + "/" + rArray[1];
                        if (StringUtils.isNotEmpty(result)) {
                            halfName += wholeResult + ",";
                        }
                    }
//                    halfName += "+" + realWinGrap + "/" + trueArray + "|";
                }
                orderMap.put("half", halfName); //半全场
                //胜负平
                Map<String, String> beatMap = getSingleMap(s, beatArray);
                if (beatMap.size() > 0) {
                    vs = beatMap.get("vs");
                }

                //2018.5.29 董宏 修改替换字符串BUG 如 3/1.93/3 -> 主胜/1.9主胜//3
                String realBeat = "";
                String trueBeat = beatMap.get("result");
                String finalBeat = "";
                if (StringUtils.isNotEmpty(trueBeat)) {

                    String[] split = trueBeat.split("/");
                    if ("3".equals(split[0])) {
                        finalBeat = trueBeat.replaceFirst("3", "主胜");

                    } else if ("1".equals(split[0])) {
                        finalBeat = trueBeat.replaceFirst("1", "平");
                    } else if ("0".equals(split[0])) {
                        finalBeat = trueBeat.replaceFirst("0", "主负");
                    }
                    realBeat = finalBeat;
                }
                //让球
                Map<String, String> letMap = getSingleMap(s, letArray);
                if (letMap.size() > 0) {
                    vs = letMap.get("vs");
                }
                String realLet = "";
                String trueLet = letMap.get("result");
                String finalLet = "";
                if (StringUtils.isNotEmpty(trueLet)) {
                    String[] split2 = trueLet.split("/");
                    if ("3".equals(split2[0])) {
                        finalLet = trueLet.replaceFirst("3", "让主胜");
                    } else if ("1".equals(split2[0])) {
                        finalLet = trueLet.replaceFirst("1", "平");
                    } else if ("0".equals(split2[0])) {
                        finalLet = trueLet.replaceFirst("0", "让主负");
                    }
                    realLet = finalLet;
                }
                orderMap.put("beat", realBeat); //胜负
                orderMap.put("let", realLet);  //让球
                //orderMap.put("let", letMap.get("result")); //让球
                orderMap.put("vs", vs);
                detailList.add(orderMap);

            } else {
                //胜负
                String winning = byMatchId.getWinning();
                //让胜负
                String spread = byMatchId.getSpread();
                //半场
                String winGrap = byMatchId.getWinGrap();
                //半全场
                String halfName = "";
                Map<String, String> halfMap = getSingleMap(s, halfArray);
                if (halfMap.size() > 0) {
                    vs = halfMap.get("vs");
                    Map<String, String> nameMap = BallGameCals.getHalfWholeNames();
                    String result = halfMap.get("result");
                    String resultArray[] = result.split(",");
                    String trueArray = "";
                    for (String r : resultArray) {
                        String wholeResult = "";
                        String halfResult = "";
                        String[] rArray = r.split("/");
                        wholeResult = nameMap.get(rArray[0]) + "/" + rArray[1];
                        if (StringUtils.isNotEmpty(result)) {
                            halfName += wholeResult + ",";
                        }
                        halfResult = nameMap.get(rArray[0]);
                        if (halfResult.equals(winGrap)) {
                            trueArray = rArray[1];
                        } else {
                            trueArray = "out";
                        }

                    }
                    halfName += "+" + winGrap + "/" + trueArray;
                }
                orderMap.put("half", halfName); //半全场
                //胜负平
                Map<String, String> beatMap = getSingleMap(s, beatArray);
                if (beatMap.size() > 0) {
                    vs = beatMap.get("vs");
                }

                //2018.5.29 董宏 修改替换字符串BUG 如 3/1.93/3 -> 主胜/1.9主胜//3
                String realBeat = "";
                String trueBeat = beatMap.get("result");
                String finalBeat = "";
                if (StringUtils.isNotEmpty(trueBeat)) {

                    String[] split = trueBeat.split("/");
                    if ("3".equals(split[0])) {
                        finalBeat = trueBeat.replaceFirst("3", "主胜");

                    } else if ("1".equals(split[0])) {
                        finalBeat = trueBeat.replaceFirst("1", "平");
                    } else if ("0".equals(split[0])) {
                        finalBeat = trueBeat.replaceFirst("0", "主负");
                    }
                    realBeat = finalBeat;
                }

                //让球
                Map<String, String> letMap = getSingleMap(s, letArray);
                if (letMap.size() > 0) {
                    vs = letMap.get("vs");
                }
                String realLet = "";
                String trueLet = letMap.get("result");
                String finalLet = "";
                if (StringUtils.isNotEmpty(trueLet)) {
                    String[] split2 = trueLet.split("/");
                    if ("3".equals(split2[0])) {
                        finalLet = trueBeat.replaceFirst("3", "让主胜");

                    } else if ("1".equals(split2[0])) {
                        finalLet = trueBeat.replaceFirst("1", "平");
                    } else if ("0".equals(split2[0])) {
                        finalLet = trueBeat.replaceFirst("0", "让主负");
                    }
                    realLet = finalLet;
                }


                //判断胜负 是否猜对
                String[] realBeatSplit = realBeat.split(",");
                String oddResult = "";
                for (int r = 0; r < realBeatSplit.length; r++) {
                    String[] splitReal = realBeatSplit[r].split("/");
                    if (splitReal[0].equals(winning)) {
                        oddResult = splitReal[1] + "/" + splitReal[2];
                    } else {
                        oddResult = "out";
                    }
                }
                //判断让球 是否猜对
                String[] realLetSplit = realLet.split(",");
                String oddLetResult = "";
                for (int x = 0; x < realLetSplit.length; x++) {
                    String[] splitReal = realLetSplit[x].split("/");
                    if (splitReal[0].equals(spread)) {
                        oddLetResult = splitReal[1] + "/" + splitReal[2];
                    } else {
                        oddLetResult = "out";
                    }
                }

                if (StringUtils.isNotEmpty(matchResult)) {
                    if (StringUtils.isNotEmpty(trueBeat)) {
                        realBeat += "+" + winning + "/" + oddResult;
                    }
                    if (StringUtils.isNotEmpty(trueLet)) {
                        realLet += "+" + spread + "/" + oddLetResult;
                    }
                }
                orderMap.put("beat", realBeat); //胜负
                orderMap.put("let", realLet);  //让球
                orderMap.put("vs", vs);
                detailList.add(orderMap);
            }
        }

        return detailList;
    }

    public static List getBbSingleList(CdBasketballSingleOrder cbs) throws ParseException {
        List detailList = new ArrayList();
        //主胜
        String hostWin = cbs.getHostWin();
        String[] winArray = hostWin.split("\\|");
        //主负
        String hostFail = cbs.getHostFail();
        String[] failArray = hostFail.split("\\|");

        //所有比赛
        String matchIds = cbs.getMatchIds();
        String[] matchIdsArray = matchIds.split(",");
        String vs = "";
        String matchResult = cbs.getResult();//比赛结果
        int i = 0;
        String allMatchTimes = cbs.getAllMatchTimes();
        String[] matchTimesArray = allMatchTimes.split(",");
        int k = 0;
        for (String s : matchIdsArray) {

            List<String> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(matchResult)) {
                String[] mathchResultArray = matchResult.split(",");
                for (String rs : mathchResultArray) {
                    //resultList.add(rs);
                    //2018.5.28 董宏 修改篮球订单 比分相反bug
                    //通过 : 分割 比分 前后值
                    String[] rsArray = rs.split(":");
                    String newRs = rsArray[1] + ":" + rsArray[0];
                    resultList.add(newRs);
                }
            }

            //           ------------------------又一次贼他妈精妙-------------------------
            //跟单订单 未开赛直接跳出循环
            if ("2".equals(cbs.getType())) {
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String aTime = matchTimesArray[k];
                k++;
                if (df.parse(aTime).getTime() > day.getTime()) {
                    continue;
                }
            }


            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("matchId", s); //期次
            //比赛结果
            if (resultList.size() > 0) {
                orderMap.put("result", resultList.get(i));
                i++;
            } else {
                orderMap.put("result", "");
            }
            //主胜
            Map<String, String> winMap = getSingleMap(s, winArray);
            if (winMap.size() > 0) {
                vs = winMap.get("vs"); //队伍
            }

            orderMap.put("win", winMap.get("result")); //比分
            //主负
            Map<String, String> failMap = getSingleMap(s, failArray);
            if (failMap.size() > 0) {
                vs = failMap.get("vs");
            }
            orderMap.put("fail", failMap.get("result")); //进球
            orderMap.put("beat", "");
            orderMap.put("let", "");
            orderMap.put("size", "");
            String[] vsArray = vs.split("vs");
            vs = vsArray[1] + "vs" + vsArray[0];
            orderMap.put("vs", vs);
            //map.put("result", cbs.getResult());//彩果
            detailList.add(orderMap);
        }
        return detailList;
    }

    public static List getBbFollowList(CdBasketballFollowOrder cbf) throws ParseException {
        List detailList = new ArrayList();
        //主胜
        String hostWin = cbf.getHostWin();
        String[] winArray = hostWin.split("\\|");
        //主负
        String hostFail = cbf.getHostFail();
        String[] failArray = hostFail.split("\\|");
        //胜负
        String beat = cbf.getBeat();
        String[] beatArray = beat.split("\\|");
        //让球胜负
        String let = cbf.getLet();
        String[] letArray = let.split("\\|");
        //大小分
        String size = cbf.getSize();
        String[] sizeArray = size.split("\\|");
        //所有比赛
        String matchIds = cbf.getDanMatchIds();
        String[] matchIdsArray = matchIds.split(",");
        String vs = "";
        String matchResult = cbf.getResult();//比赛结果
        int j = 0;
        String createDate = cbf.getCreateDate();  //订单创建时间
        String matchTimes = cbf.getAllMatchTimes();//所有比赛时间

        String[] matchTimesArray = matchTimes.split(",");
        int k = 0;

        for (String s : matchIdsArray) {
            List<String> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(matchResult)) {
                String[] mathchResultArray = matchResult.split(",");
                for (String rs : mathchResultArray) {
                    //2018.5.28 董宏 修改篮球订单 比分相反bug
                    //通过 : 分割 比分 前后值
                    String[] rsArray = rs.split(":");
                    String newRs = rsArray[1] + ":" + rsArray[0];
                    resultList.add(newRs);
                }
            }
            //           ------------------------又一次贼他妈精妙-------------------------
            //跟单订单 未开赛直接跳出循环
            if ("2".equals(cbf.getType())) {
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String aTime = matchTimesArray[k];
                k++;
                if (df.parse(aTime).getTime() > day.getTime()) {
                    continue;
                }
            }

            String match = s.split("\\+")[1];
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("matchId", match);

            //比赛结果
            if (resultList.size() > 0) {
                orderMap.put("result", resultList.get(j));
                j++;
            } else {
                orderMap.put("result", "");
            }
            //主胜
            Map<String, String> winMap = getFollowMap(match, winArray);
            if (winMap.size() > 0) {
                //530 董宏 修改 主客场 位置互换
                vs = winMap.get("vs");
                String[] vs1 = vs.split("vs");
                vs = vs1[1] + "vs" + vs1[0];
            }
            orderMap.put("win", winMap.get("result"));
            //主负
            Map<String, String> failMap = getFollowMap(match, failArray);
            if (failMap.size() > 0) {
                vs = failMap.get("vs");
                String[] vs1 = vs.split("vs");
                vs = vs1[1] + "vs" + vs1[0];
            }
            orderMap.put("fail", failMap.get("result"));
            //胜负
            Map<String, String> beatMap = getFollowMap(match, beatArray);
            if (beatMap.size() > 0) {
                vs = beatMap.get("vs");
                String[] vs1 = vs.split("vs");
                vs = vs1[1] + "vs" + vs1[0];
            }

            String realBeat = "";
            String trueBeat = beatMap.get("result");
            if (StringUtils.isNotEmpty(trueBeat)) {
                String finalBeat1 = trueBeat.replaceAll("1/", "主胜/");
                String finalBeat2 = finalBeat1.replaceAll("0/", "主负/");
                realBeat = finalBeat2;
            }

            //让球胜负
            Map<String, String> letMap = getFollowMap(match, letArray);
            if (letMap.size() > 0) {
                vs = letMap.get("vs");
                String[] vs1 = vs.split("vs");
                vs = vs1[1] + "vs" + vs1[0];
            }
            String trueLet = letMap.get("result");
            String realLet = "";
            if (StringUtils.isNotEmpty(trueLet)) {
                String finalLet1 = trueLet.replaceAll("1/", "让主胜/");
                String finalLet2 = finalLet1.replaceAll("0/", "让主负/");
                realLet = finalLet2;
            }

            //2018.5.30 通过 matchId 获取 AwardsService 信息
            CdBasketballAwards byMatchId = cdBasketballAwardsService.findByMatchId(match);

            //判断时间 超过3天 则不处理结果
            Date nowDate = new Date();
            long nowTime = nowDate.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long orderTime = dateFormat.parse(createDate).getTime();

            if (byMatchId == null || nowTime - orderTime > 259200000) {
                orderMap.put("beat", realBeat);
                orderMap.put("let", realLet);
                orderMap.put("vs", vs);
            } else {
                //胜负
                String winning = byMatchId.getWinning();
                //让胜负
                String spread = byMatchId.getSpread();
                //判断胜负 是否猜对
                String[] realBeatSplit = realBeat.split(",");
                String oddResult = "";
                for (int r = 0; r < realBeatSplit.length; r++) {
                    String[] splitReal = realBeatSplit[r].split("/");
                    if (splitReal[0].equals(winning)) {
                        oddResult = splitReal[1];
                    } else {
                        oddResult = "out";
                    }
                }
                //判断让球 是否猜对
                String[] realLetSplit = realLet.split(",");
                String oddLetResult = "";
                for (int x = 0; x < realLetSplit.length; x++) {
                    String[] splitReal = realLetSplit[x].split("/");
                    if (splitReal[0].equals(spread)) {
                        oddLetResult = splitReal[1];
                    } else {
                        oddLetResult = "out";
                    }
                }

                if (StringUtils.isNotEmpty(matchResult)) {
                    if (StringUtils.isNotEmpty(trueBeat)) {
                        realBeat += "+" + winning + "/" + oddResult;
                    }

                    if (StringUtils.isNotEmpty(trueLet)) {
                        realLet += "+" + spread + "/" + oddLetResult;
                    }
                }

                orderMap.put("beat", realBeat);
                orderMap.put("let", realLet);
                orderMap.put("vs", vs);
            }

            //大小分
            String finalSize = "";
            if (StringUtils.isNotEmpty(sizeArray[0])) {
                String sizeCount = cbf.getSizeCount();
                String[] sizeCountArray = sizeCount.split(",");
                for (int i = 0; i < sizeCountArray.length; i++) {
                    String[] aSizeArray = sizeArray[i].split("\\+");
                    String firstMatch = s.split("\\+")[1];
                    String secondMatch = aSizeArray[1];
                    if (firstMatch.equals(secondMatch)) {
                        String[] vs1 = aSizeArray[2].split("vs");
                        vs = vs1[1] + "vs" + vs1[0];
                        orderMap.put("vs", vs);
                        String sizeResult = aSizeArray[3];
                        String[] sizeResultArray = sizeResult.split(",");
                        for (String r : sizeResultArray) {
                            Map<String, String> sizeName = BallGameCals.getSizeNames();
                            String wholeResult = "";
                            String[] rArray = r.split("/");
                            wholeResult = sizeName.get(rArray[0]) + sizeCountArray[i] + "/" + rArray[1];
                            finalSize += wholeResult + ";";
                        }
                        orderMap.put("size", finalSize);
                    }
                }
            }
            detailList.add(orderMap);
        }
        return detailList;
    }


    public static String getLevelPercent(String totalRecharge) {
        List<Double> level = new ArrayList<>();
        level.add(2.00);
        level.add(100.00);
        level.add(500.00);
        level.add(1000.00);
        level.add(5000.00);
        level.add(10000.00);
        level.add(100000.00);
        level.add(200000.00);
        level.add(500000.00);
        level.add(1000000.00);
        double recharge = Double.parseDouble(totalRecharge);
        String percent = "0";
        for (int i = 1; i < level.size(); i++) {
            double charge = level.get(i);
            double theLast = level.get(i - 1);
            if (recharge > theLast & recharge < charge) {
                BigDecimal total = new BigDecimal(recharge);
                BigDecimal nextLevel = new BigDecimal(charge);
                int per = total.divide(nextLevel, 2, 2).multiply(new BigDecimal(100)).intValue();
                percent = String.valueOf(per);
                break;
            }
        }
        return percent + "%";
    }

    /**
     * 中奖增加用户余额
     *
     * @param award
     * @param uid
     */
    public static void addBalanceToUser(String award, String uid) {
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        BigDecimal awardBig = new BigDecimal(award);
        BigDecimal balance = clu.getBalance();
        BigDecimal newBalance = balance.add(awardBig);
        clu.setBalance(newBalance);
        try {
            cdLotteryUserService.save(clu);
        } catch (Exception c) {
            System.out.println("中奖金额到账失败");
        }

    }


    public static List getFbFollowList2(CdFootballFollowOrder cff) {
        List detailList = new ArrayList();
        //比分
        String score = cff.getScore();
        String[] scoreArray = score.split("\\|");
        //进球
        String goal = cff.getGoal();
        String[] goalArray = goal.split("\\|");
        //半全场
        String half = cff.getHalf();
        String[] halfArray = half.split("\\|");
        //胜负
        String beat = cff.getBeat();
        String[] beatArray = beat.split("\\|");
        //让球
        String let = cff.getLet();
        String[] letArray = let.split("\\|");
        //所有比赛
        String matchIds = cff.getDanMatchIds();
        String[] matchIdsArray = matchIds.split(",");
        String vs = "";
        String matchResult = cff.getResult();//比赛结果
        int i = 0;
        String matchTimes = cff.getAllMatchTimes();//所有比赛时间
        String[] matchTimesArray = matchTimes.split(",");
        String letBall = cff.getLetBalls();
        String[] letBallArray = letBall.split(",");
        int j = 0;
        for (String s : matchIdsArray) {
            List<String> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(matchResult)) {
                String[] mathchResultArray = matchResult.split(",");
                for (String rs : mathchResultArray) {
                    resultList.add(rs);
                }
            }

            String[] aMatchArray = s.split("\\+");
            String match = aMatchArray[1];
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("matchId", match);
            orderMap.put("dan", aMatchArray[0]);
            //比赛结果
            if (resultList.size() > 0) {
                orderMap.put("result", resultList.get(i));
                i++;
            } else {
                orderMap.put("result", "");
            }

            //比分
            Map<String, String> scoreMap = getFollowMap(match, scoreArray);
            if (scoreMap.size() > 0) {
                vs = scoreMap.get("vs");

            }

            orderMap.put("score", scoreMap.get("result"));

            //进球
            Map<String, String> goalMap = getFollowMap(match, goalArray);
            if (goalMap.size() > 0) {
                vs = goalMap.get("vs");
            }
            orderMap.put("goal", goalMap.get("result"));


            //半全场
            String halfName = "";
            Map<String, String> halfMap = getFollowMap(match, halfArray);
            if (halfMap.size() > 0) {
                vs = halfMap.get("vs");
                Map<String, String> nameMap = BallGameCals.getHalfWholeNames();
                String result = halfMap.get("result");
                String resultArray[] = result.split(",");
                for (String r : resultArray) {
                    String wholeResult = "";
                    String[] rArray = r.split("/");
                    wholeResult = nameMap.get(rArray[0]) + "/" + rArray[1];
                    halfName += wholeResult + ",";
                }
            }
            orderMap.put("half", halfName); //半全场

            //胜负平
            Map<String, String> beatMap = getFollowMap(match, beatArray);
            if (beatMap.size() > 0) {
                vs = beatMap.get("vs");
            }

            String trueBeat = beatMap.get("result");
            String realBeat = "";
            if (StringUtils.isNotEmpty(trueBeat)) {
                String finalBeat1 = trueBeat.replaceAll("3/", "主胜/");
                String finalBeat2 = finalBeat1.replaceAll("1/", "平/");
                String finalBeat3 = finalBeat2.replaceAll("0/", "客胜/");
                realBeat = finalBeat3;
            }

            orderMap.put("beat", realBeat);
            //让球
            Map<String, String> letMap = getFollowMap(match, letArray);

            if (letMap.size() > 0) {
                vs = letMap.get("vs");
                orderMap.put("letBall", letBallArray[j]);
                j++;
            }
            String trueLet = letMap.get("result");
            String realLet = "";
            if (StringUtils.isNotEmpty(trueLet)) {
                String finalLet1 = trueLet.replaceAll("3/", "让主胜/");
                String finalLet2 = finalLet1.replaceAll("1/", "平/");
                String finalLet3 = finalLet2.replaceAll("0/", "让客胜/");
                realLet = finalLet3;
            }
            orderMap.put("let", realLet);
            orderMap.put("vs", vs);
            //orderMap.put("letBall", letMap.get("letScore"));

            detailList.add(orderMap);
        }
        return detailList;
    }

    public static List getFbSingleList2(CdFootballSingleOrder cfs) throws ParseException {
        List detailList = new ArrayList();
        //比分
        String score = cfs.getScore();
        String[] scoreArray = score.split("\\|");
        //进球
        String goal = cfs.getGoal();
        String[] goalArray = goal.split("\\|");
        //半全场
        String half = cfs.getHalf();
        String[] halfArray = half.split("\\|");
        //胜负
        String beat = cfs.getBeat();
        String[] beatArray = beat.split("\\|");
        //让球
        String let = cfs.getLet();
        String[] letArray = let.split("\\|");
        //所有比赛
        String matchIds = cfs.getMatchIds();
        String[] matchIdsArray = matchIds.split(",");
        String vs = "";
        String matchResult = cfs.getResult();//比赛结果
        int i = 0;
        String allMatchTimes = cfs.getAllMatchTimes();
        String[] matchTimesArray = allMatchTimes.split(",");
        int j = 0;
        String letBall = cfs.getLetBalls();
        String[] letBallArray = letBall.split(",");
        for (String s : matchIdsArray) {
            List<String> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(matchResult)) {
                String[] mathchResultArray = matchResult.split(",");
                for (String rs : mathchResultArray) {
                    resultList.add(rs);
                }
            }


            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("matchId", s); //期次
            orderMap.put("dan", "非");
            //比赛结果
            if (resultList.size() > 0) {
                orderMap.put("result", resultList.get(i));
                i++;
            } else {
                orderMap.put("result", "");
            }
            //比分
            Map<String, String> scoreMap = getSingleMap(s, scoreArray);
            if (scoreMap.size() > 0) {
                vs = scoreMap.get("vs"); //队伍
            }
            String trueScore = scoreMap.get("result");
            orderMap.put("score", trueScore); //比分
            //进球
            Map<String, String> goalMap = getSingleMap(s, goalArray);
            if (goalMap.size() > 0) {
                vs = goalMap.get("vs");
            }
            orderMap.put("goal", goalMap.get("result")); //进球
            //半全场
            String halfName = "";
            Map<String, String> halfMap = getSingleMap(s, halfArray);
            if (halfMap.size() > 0) {
                vs = halfMap.get("vs");
                Map<String, String> nameMap = BallGameCals.getHalfWholeNames();
                String result = halfMap.get("result");
                String resultArray[] = result.split(",");
                for (String r : resultArray) {
                    String wholeResult = "";
                    String[] rArray = r.split("/");
                    wholeResult = nameMap.get(rArray[0]) + "/" + rArray[1];
                    halfName += wholeResult + ",";
                }
            }
            orderMap.put("half", halfName); //半全场
            //胜负平
            Map<String, String> beatMap = getSingleMap(s, beatArray);
            if (beatMap.size() > 0) {
                vs = beatMap.get("vs");
            }
            String realBeat = "";
            String trueBeat = beatMap.get("result");
            if (StringUtils.isNotEmpty(trueBeat)) {
                String[] beatStrs = trueBeat.split("\\,");
                //TODO 2018-05-25 yhw  针对单关暂时这么处理，复杂数据肯定有问题
                if (trueBeat.startsWith("3/")) {
                    realBeat = trueBeat.replaceFirst("3/", "主胜/");
                } else if (trueBeat.startsWith("1/")) {
                    realBeat = trueBeat.replaceFirst("1/", "平/");
                } else if (trueBeat.startsWith("0/")) {
                    realBeat = trueBeat.replaceFirst("0/", "客胜/");
                }

            }

            orderMap.put("beat", realBeat); //胜负
            //让球
            Map<String, String> letMap = getSingleMap2(s, letArray, letBallArray);
            if (letMap.size() > 0) {
                vs = letMap.get("vs");
            }
            String realLet = "";
            String trueLet = letMap.get("result");
            if (StringUtils.isNotEmpty(trueLet)) {
                /*String finalLet1 = trueLet.replaceFirst("3/", "让主胜/");
                String finalLet2 = finalLet1.replaceFirst("1/", "平/");
                String finalLet3 = finalLet2.replaceFirst("0/", "让客胜/");
                realLet = finalLet3;*/
                if (trueBeat.startsWith("3/")) {
                    realLet = trueLet.replaceFirst("3/", "主胜/");
                } else if (trueBeat.startsWith("1/")) {
                    realLet = trueLet.replaceFirst("1/", "平/");
                } else if (trueBeat.startsWith("0/")) {
                    realLet = trueLet.replaceFirst("0/", "客胜/");
                }

            }
            orderMap.put("letBall", letMap.get("letBall"));
            orderMap.put("let", realLet);
            //orderMap.put("let", letMap.get("result")); //让球
            orderMap.put("vs", vs);
            detailList.add(orderMap);
        }
        return detailList;
    }


    public static List getBbFollowList2(CdBasketballFollowOrder cbf) {
        List detailList = new ArrayList();
        //主胜
        String hostWin = cbf.getHostWin();
        String[] winArray = hostWin.split("\\|");
        //主负
        String hostFail = cbf.getHostFail();
        String[] failArray = hostFail.split("\\|");
        //胜负
        String beat = cbf.getBeat();
        String[] beatArray = beat.split("\\|");
        //让球胜负
        String let = cbf.getLet();
        String[] letArray = let.split("\\|");
        //大小分
        String size = cbf.getSize();
        String[] sizeArray = size.split("\\|");
        //所有比赛
        String matchIds = cbf.getDanMatchIds();
        String[] matchIdsArray = matchIds.split(",");
        String vs = "";
        String matchResult = cbf.getResult();//比赛结果
        int j = 0;

        String matchTimes = cbf.getAllMatchTimes();//所有比赛时间
        String[] matchTimesArray = matchTimes.split(",");
        int k = 0;
        String letScore = cbf.getLetScore();
        String[] letScoreArray = letScore.split(",");
        for (String s : matchIdsArray) {
            List<String> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(matchResult)) {
                String[] mathchResultArray = matchResult.split(",");
                for (String rs : mathchResultArray) {
                    resultList.add(rs);
                }
            }

            String[] aMatchArray = s.split("\\+");
            String match = aMatchArray[1];
            Map<String, Object> orderMap = new HashMap<>();


            orderMap.put("matchId", match);
            orderMap.put("dan", aMatchArray[0]);
            //比赛结果
            if (resultList.size() > 0) {
                orderMap.put("result", resultList.get(j));
                j++;
            } else {
                orderMap.put("result", "");
            }
            //主胜
            Map<String, String> winMap = getFollowMap(match, winArray);
            if (winMap.size() > 0) {
                vs = winMap.get("vs");
            }
            orderMap.put("win", winMap.get("result"));
            //主负
            Map<String, String> failMap = getFollowMap(match, failArray);
            if (failMap.size() > 0) {
                vs = failMap.get("vs");
            }
            orderMap.put("fail", failMap.get("result"));
            //胜负
            Map<String, String> beatMap = getFollowMap(match, beatArray);
            if (beatMap.size() > 0) {
                vs = beatMap.get("vs");
            }
            String realBeat = "";
            String trueBeat = beatMap.get("result");
            if (StringUtils.isNotEmpty(trueBeat)) {
                String finalBeat1 = trueBeat.replaceAll("1/", "主胜/");
                String finalBeat2 = finalBeat1.replaceAll("0/", "主负/");
                realBeat = finalBeat2;
            }

            orderMap.put("beat", realBeat);
            //orderMap.put("beat", beatMap.get("result"));
            //让球胜负
            Map<String, String> letMap = getFollowMap(match, letArray);
            if (letMap.size() > 0) {
                vs = letMap.get("vs");
                orderMap.put("letScore", letScoreArray[k]);
                k++;
            }
            String trueLet = letMap.get("result");
            String realLet = "";
            if (StringUtils.isNotEmpty(trueLet)) {
                String finalLet1 = trueLet.replaceAll("1/", "让主胜/");
                String finalLet2 = finalLet1.replaceAll("0/", "让主负/");
                realLet = finalLet2;
            }
            //orderMap.put("letScore", letMap.get("letScore"));
            orderMap.put("let", realLet);
            orderMap.put("vs", vs);

            //大小分
            String finalSize = "";
            if (StringUtils.isNotEmpty(sizeArray[0])) {
                String sizeCount = cbf.getSizeCount();
                String[] sizeCountArray = sizeCount.split(",");
                for (int i = 0; i < sizeCountArray.length; i++) {
                    String[] aSizeArray = sizeArray[i].split("\\+");
                    String firstMatch = s.split("\\+")[1];
                    String secondMatch = aSizeArray[1];
                    if (firstMatch.equals(secondMatch)) {
                        orderMap.put("vs", aSizeArray[2]);
                        String sizeResult = aSizeArray[3];
                        String[] sizeResultArray = sizeResult.split(",");
                        for (String r : sizeResultArray) {
                            Map<String, String> sizeName = BallGameCals.getAnotherSizeNames();
                            String wholeResult = "";
                            String[] rArray = r.split("/");
                            wholeResult = sizeName.get(rArray[0]) + sizeCountArray[i] + "/" + rArray[1];
                            finalSize += wholeResult + ";";
                        }
                        orderMap.put("size", finalSize);
                    }
                }
            }

            detailList.add(orderMap);
        }
        return detailList;
    }


    public static List getBbSingleList2(CdBasketballSingleOrder cbs) {
        List detailList = new ArrayList();
        //主胜
        String hostWin = cbs.getHostWin();
        String[] winArray = hostWin.split("\\|");
        //主负
        String hostFail = cbs.getHostFail();
        String[] failArray = hostFail.split("\\|");

        //所有比赛
        String matchIds = cbs.getMatchIds();
        String[] matchIdsArray = matchIds.split(",");

        String vs = "";
        String matchResult = cbs.getResult();//比赛结果
        int i = 0;
        String allMatchTimes = cbs.getAllMatchTimes();
        String[] matchTimesArray = allMatchTimes.split(",");
        int k = 0;
        for (String s : matchIdsArray) {

            List<String> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(matchResult)) {
                String[] mathchResultArray = matchResult.split(",");
                for (String rs : mathchResultArray) {
                    resultList.add(rs);
                }
            }

            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("matchId", s); //期次
            orderMap.put("dan", "非");
            //比赛结果
            if (resultList.size() > 0) {
                orderMap.put("result", resultList.get(i));
                i++;
            } else {
                orderMap.put("result", "");
            }
            //主胜
            Map<String, String> winMap = getSingleMap(s, winArray);
            if (winMap.size() > 0) {
                vs = winMap.get("vs"); //队伍
            }

            orderMap.put("win", winMap.get("result")); //比分
            //主负
            Map<String, String> failMap = getSingleMap(s, failArray);
            if (failMap.size() > 0) {
                vs = failMap.get("vs");
            }
            orderMap.put("fail", failMap.get("result")); //进球
            orderMap.put("beat", "");
            orderMap.put("let", "");
            orderMap.put("size", "");
            orderMap.put("vs", vs);
            //map.put("result", cbs.getResult());//彩果
            detailList.add(orderMap);
        }
        return detailList;
    }

}
