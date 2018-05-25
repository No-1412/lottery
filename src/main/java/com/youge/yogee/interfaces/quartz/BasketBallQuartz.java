package com.youge.yogee.interfaces.quartz;

import com.google.common.base.Strings;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.utils.lottery.LotteryUtil;
import com.youge.yogee.interfaces.lottery.util.WinPriceUtil;
import com.youge.yogee.interfaces.util.Calculations;
import com.youge.yogee.modules.cbasketballawards.entity.CdBasketballAwards;
import com.youge.yogee.modules.cbasketballawards.service.CdBasketballAwardsService;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballBestFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballBestFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballSingleOrderService;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.cmagicorder.service.CdMagicFollowOrderService;
import com.youge.yogee.modules.cmagicorder.service.CdMagicOrderService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.service.CdOrderFollowTimesService;
import com.youge.yogee.modules.corder.service.CdOrderService;
import com.youge.yogee.modules.corder.service.CdOrderWinnersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * 定时任务
 * Created by Liyuan on 2015/12/28.
 */
@Component("BasketBallQuartz")
public class BasketBallQuartz {

    @Autowired
    private CdBasketballFollowOrderService cdBasketballFollowOrderService;
    @Autowired
    private CdBasketballAwardsService cdBasketballAwardsService;
    @Autowired
    private CdBasketballSingleOrderService cdBasketballSingleOrderService;
    @Autowired
    private CdOrderWinnersService cdOrderWinnersService;
    @Autowired
    private CdOrderService cdOrderService;
    @Autowired
    private CdOrderFollowTimesService cdOrderFollowTimesService;
    @Autowired
    private CdMagicOrderService cdMagicOrderService;
    @Autowired
    private CdMagicFollowOrderService cdMagicFollowOrderService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdBasketballBestFollowOrderService cdBasketballBestFollowOrderService;
    //    "0/10 * * * * ?" 每10秒触发
//
//    "0 0 12 * * ?" 每天中午12点触发
//    "0 15 10 ? * *" 每天上午10:15触发
//    "0 15 10 * * ?" 每天上午10:15触发
//    "0 15 10 * * ? *" 每天上午10:15触发
//    "0 15 10 * * ? 2005" 2005年的每天上午10:15触发
//    "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
//    "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
//    "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
//    "0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
//    "0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
//    "0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发
//    "0 15 10 15 * ?" 每月15日上午10:15触发
//    "0 15 10 L * ?" 每月最后一日的上午10:15触发
//    "0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
//    "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
//    "0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
//    每隔5秒执行一次：*/5 * * * * ?
//    每隔1分钟执行一次：0 */1 * * * ?
//    每天23点执行一次：0 0 23 * * ?
//    每天凌晨1点执行一次：0 0 1 * * ?
//    每月1号凌晨1点执行一次：0 0 1 1 * ?
//    每月最后一天23点执行一次：0 0 23 L * ?
//    每周星期天凌晨1点实行一次：0 0 1 ? * L
//    在26分、29分、33分执行一次：0 26,29,33 * * * ?
//    每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
    //定时轮询
//    @Scheduled(cron = "0/20 1 * * * ?")
//    @Scheduled(cron = "0 0 * * * ?")//1小时

    @Scheduled(cron = "*/30 * * * * ?")
    //@Scheduled(cron = "0/10 * * * * ?")//10s
//    @Scheduled(cron = "0 0 */1 * * ?")//2小时
    public void basketBallFollowOrder() {

        System.out.println("篮球串关未优化开奖");
        List<CdBasketballFollowOrder> cdBasketballFollowOrderList = cdBasketballFollowOrderService.findStatusAndType("1");

        //全部可以比赛完的场次
        List<String> awardMatchIdList = cdBasketballAwardsService.getAllMatchId();

        for (CdBasketballFollowOrder cdBasketballFollowOrder : cdBasketballFollowOrderList) {

            List<String> cdList = new ArrayList<String>();
            cdList.add(cdBasketballFollowOrder.getHostWin());
            cdList.add(cdBasketballFollowOrder.getHostFail());
            cdList.add(cdBasketballFollowOrder.getBeat());
            cdList.add(cdBasketballFollowOrder.getSize());
            cdList.add(cdBasketballFollowOrder.getLet());
            List<String> index = LotteryUtil.queryEvent(cdList);
            String[] strings = new String[index.size()];
            String[] awardMatchIdArray = new String[awardMatchIdList.size()];

            //判断比赛是否全部完成
            boolean containsAll = LotteryUtil.containsAll(awardMatchIdList.toArray(awardMatchIdArray), index.toArray(strings));
            //System.out.println(containsAll);
            if (containsAll) {
                //  **************************后加的-------------获取押注比赛结果并保存****************
                String manyMatchIds = cdBasketballFollowOrder.getDanMatchIds();
                String[] danMatchIdArray = manyMatchIds.split(",");
                List<String> list = new ArrayList<>();
                for (String str : danMatchIdArray) {
                    String matchId = str.split("\\+")[1];
                    list.add(matchId);
                }
                String result = getResultStr(list);
                cdBasketballFollowOrder.setResult(result);
                cdBasketballFollowOrderService.save(cdBasketballFollowOrder);


                Map<String, CdBasketballAwards> resultMap = new HashMap<String, CdBasketballAwards>();
                for (int i = 0; i < index.size(); i++) {
                    CdBasketballAwards cdBasketballAwards = cdBasketballAwardsService.findByMatchId(index.get(i));
                    resultMap.put(index.get(i), cdBasketballAwards);
                }

                BigDecimal award = LotteryUtil.basketBallWinningVerify(cdList, resultMap, cdBasketballFollowOrder.getFollowNums(), cdBasketballFollowOrder.getTimes());


                if (award.compareTo(BigDecimal.ZERO) > 0) {
                    System.err.println(cdBasketballFollowOrder.getOrderNum() + ":" + award);
                    cdBasketballFollowOrder.setAward(award.toString());
                    cdBasketballFollowOrder.setStatus("4");
                    cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
                    //改变订单总表状态 step1
                    WinPriceUtil.changeTotalOrder(cdBasketballFollowOrder.getOrderNum(), award.toString());
                } else {
                    //改变订单总表状态
                    CdOrder co = cdOrderService.getOrderByOrderNum(cdBasketballFollowOrder.getOrderNum());
                    if (co != null) {
                        co.setWinPrice("0");//奖金
                        co.setStatus("2");//已开奖
                        cdOrderService.save(co);
                    }
                    cdBasketballFollowOrder.setStatus("5");
                    cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
                }

            }
        }
    }

    @Scheduled(cron = "0 0 */1 * * ?")//2小时
    public void basketBallBestFollowOrder() {
        System.out.println("篮球优化串关开奖");
        List<CdBasketballFollowOrder> cdBasketballFollowOrderList = cdBasketballFollowOrderService.findStatusAndType("2");
        //全部可以比赛完的场次
        List<String> awardMatchIdList = cdBasketballAwardsService.getAllMatchId();

        for (CdBasketballFollowOrder cdBasketballFollowOrder : cdBasketballFollowOrderList) {

            List<String> cdList = new ArrayList<String>();
            cdList.add(cdBasketballFollowOrder.getHostWin());
            cdList.add(cdBasketballFollowOrder.getHostFail());
            cdList.add(cdBasketballFollowOrder.getBeat());
            cdList.add(cdBasketballFollowOrder.getSize());
            cdList.add(cdBasketballFollowOrder.getLet());
            List<String> index = LotteryUtil.queryEvent(cdList);
            String[] strings = new String[index.size()];
            String[] awardMatchIdArray = new String[awardMatchIdList.size()];

            //判断比赛是否全部完成
            boolean containsAll = LotteryUtil.containsAll(awardMatchIdList.toArray(awardMatchIdArray), index.toArray(strings));
            //System.out.println(containsAll);
            if (containsAll) {
                //  **************************后加的-------------获取押注比赛结果并保存****************
                String manyMatchIds = cdBasketballFollowOrder.getDanMatchIds();
                String[] danMatchIdArray = manyMatchIds.split(",");
                List<String> list = new ArrayList<>();
                for (String str : danMatchIdArray) {
                    String matchId = str.split("\\+")[1];
                    list.add(matchId);
                }
                String result = getResultStr(list);
                cdBasketballFollowOrder.setResult(result);
                cdBasketballFollowOrderService.save(cdBasketballFollowOrder);


                Map<String, CdBasketballAwards> resultMap = new HashMap<String, CdBasketballAwards>();
                for (int i = 0; i < index.size(); i++) {
                    CdBasketballAwards cdBasketballAwards = cdBasketballAwardsService.findByMatchId(index.get(i));
                    resultMap.put(index.get(i), cdBasketballAwards);
                }


                BigDecimal award = new BigDecimal(0);
                List<CdBasketballBestFollowOrder> basketballBestFollowOrderList = cdBasketballBestFollowOrderService.findByNum(cdBasketballFollowOrder.getOrderNum());
                for (CdBasketballBestFollowOrder cdFootballBestFollowOrder : basketballBestFollowOrderList) {
                    List<String> openList = new ArrayList<String>();
                    openList.add(cdFootballBestFollowOrder.getOrderDetail());
                    award = award.add(LotteryUtil.basketBallBestWinningVerify(openList, resultMap, cdFootballBestFollowOrder.getPerTimes())).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                }

                if (award.compareTo(BigDecimal.ZERO) > 0) {
                    System.err.println(cdBasketballFollowOrder.getOrderNum() + ":" + award);
                    cdBasketballFollowOrder.setAward(award.toString());
                    cdBasketballFollowOrder.setStatus("4");
                    cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
                    //改变订单总表状态 step1
                    WinPriceUtil.changeTotalOrder(cdBasketballFollowOrder.getOrderNum(), award.toString());
                } else {
                    //改变订单总表状态
                    CdOrder co = cdOrderService.getOrderByOrderNum(cdBasketballFollowOrder.getOrderNum());
                    if (co != null) {
                        co.setWinPrice("0");//奖金
                        co.setStatus("2");//已开奖
                        cdOrderService.save(co);
                    }
                    cdBasketballFollowOrder.setStatus("5");
                    cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
                }
            }

//            //获取订单中押的全部场次
//            String danMatchIds = cdBasketballFollowOrder.getDanMatchIds();
//            Set<String> matchIdList = new HashSet<>();
//            for (String finishMatchId : danMatchIds.split(",")) {
//                matchIdList.add(finishMatchId.substring(2, 7));
//            }
//
//            //判断订单所有赛事是否都已经比完
//            if (awardMatchIdList.containsAll(matchIdList)) {
//
//                //  **************************后加的-------------获取押注比赛结果并保存****************
//                String manyMatchIds = cdBasketballFollowOrder.getDanMatchIds();
//                String[] danMatchIdArray = manyMatchIds.split(",");
//                List<String> list = new ArrayList<>();
//                for (String str : danMatchIdArray) {
//                    String matchId = str.split("\\+")[1];
//                    list.add(matchId);
//                }
//                String result = getResultStr(list);
//                cdBasketballFollowOrder.setResult(result);
//                cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
//                //**********************************************************************************
//
//                List<CdBasketballBestFollowOrder> basketballBestFollowOrderList = cdBasketballBestFollowOrderService.findByNum(cdBasketballFollowOrder.getOrderNum());
//                BigDecimal award = new BigDecimal(0);
//                for (CdBasketballBestFollowOrder cdBasketballBestFollowOrder : basketballBestFollowOrderList) {
//                    String orderDetail = cdBasketballBestFollowOrder.getOrderDetail();
//                    String[] detailArray = orderDetail.split("\\|");
//
//                    Boolean flag = true;//如果最终为true则中奖
//                    for (String detail : detailArray) {
//                        String[] aDetail = detail.split("\\+");
//                        String key = aDetail[1];
//                        CdBasketballAwards cdBasketballAwards = cdBasketballAwardsService.findByMatchId(aDetail[0]);
//
//                        String orderFinish = StringUtils.split(aDetail[2], "/")[0];
//                        String finish = "";
//                        switch (key) {
//                            case "hostWin":
//                                finish = cdBasketballAwards.getWinGrap();
//                                break;
//                            case "hostFail":
//                                finish = cdBasketballAwards.getWinGrap();
//                                break;
//                            case "beat":
//                                finish = cdBasketballAwards.getWinning();
//                                break;
//                            case "size":
//                                //大小分分数
//                                Double sizeCount = Double.parseDouble(StringUtils.split(aDetail[3], "/")[2]);
//                                Double count = Double.parseDouble(cdBasketballAwards.getHs()) + Integer.valueOf(cdBasketballAwards.getVs());
//                                if (count > sizeCount) {
//                                    finish = "大分";
//                                } else {
//                                    finish = "小分";
//                                }
//                                break;
//                            case "let":
//                                //订单让球数
//                                int hs = Integer.valueOf(cdBasketballAwards.getHs());
//                                int vs = Integer.valueOf(cdBasketballAwards.getVs());
//
//                                Double letBall = Double.parseDouble(StringUtils.split(aDetail[3], "/")[1]);
//
//                                if (hs + letBall > vs) {
//                                    finish = "让主胜";
//                                } else {
//                                    finish = "让主负";
//                                }
//                                break;
//                        }
//                        if (!orderFinish.equals(finish)) {
//                            flag = false;
//                        }
//                    }
//
//                    //如果最终为true则中奖,计算单场奖金
//                    if (flag) {
//                        BigDecimal pertimes = new BigDecimal(cdBasketballBestFollowOrder.getPerTimes());
//                        BigDecimal perAward = new BigDecimal(cdBasketballBestFollowOrder.getPerAward());
//                        award = award.add(pertimes.multiply(perAward));
//                    }
//                }
//
//                //开奖结果为  未中奖
//                if (award.doubleValue() == 0) {
//                    cdBasketballFollowOrder.setStatus("5");
//                    cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
//                    //改变订单总表状态
//                    CdOrder co = cdOrderService.getOrderByOrderNum(cdBasketballFollowOrder.getOrderNum());
//                    if (co != null) {
//                        co.setWinPrice("0");//奖金
//                        co.setStatus("2");//未中奖
//                        cdOrderService.save(co);
//                    }
//                } else {
//                    //所有中奖赔率
//                    cdBasketballFollowOrder.setAward(award.toString());
//                    cdBasketballFollowOrder.setStatus("4");
//                    cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
//                    //改变订单总表状态 step1
//                    WinPriceUtil.changeTotalOrder(cdBasketballFollowOrder.getOrderNum(), award.toString());
//
//                    //------------------------原开奖逻辑----180517弃用-----------------------------------------
//                    //计算跟单佣金 step2
//                    //保存中奖纪录 step3
//                    //更新用户余额 step4
//                    //推送         step5
//                    //-------------------------------------------------------------------------------
//                }
//            }
        }
    }

    //        @Scheduled(cron = "*/5 * * * * ?")
    @Scheduled(cron = "0/30 * * * * ?")//10s
//    @Scheduled(cron = "0 0 */1 * * ?")//2小时
    public void footballSingleOrder() {
        System.out.println("篮球单关开奖");

        List<CdBasketballSingleOrder> cdBasketballSingleOrderList = cdBasketballSingleOrderService.findStatus();


        //全部可以比赛完的场次
        List<String> awardMatchIdList = cdBasketballAwardsService.getAllMatchId();
        System.out.println("比赛完的场次:" + awardMatchIdList);
        for (CdBasketballSingleOrder cdBasketballSingleOrder : cdBasketballSingleOrderList) {

            List<String> cdList = new ArrayList<String>();
            List<String> playTypeList = new ArrayList<String>();
            if (Strings.isNullOrEmpty(cdBasketballSingleOrder.getHostWin())) {
                cdList.add(cdBasketballSingleOrder.getHostFail());
                playTypeList.add("1");
            } else {
                cdList.add(cdBasketballSingleOrder.getHostWin());
                playTypeList.add("0");
            }
            List<String> index = LotteryUtil.querySingleEvent(cdList);
            String[] strings = new String[index.size()];
            String[] awardMatchIdArray = new String[awardMatchIdList.size()];

            System.out.println("index:" + index);

            //判断比赛是否全部完成
            boolean containsAll = LotteryUtil.containsAll(awardMatchIdList.toArray(awardMatchIdArray), index.toArray(strings));
            System.out.println(containsAll);
            if (containsAll) {
                //  **************************后加的-------------获取押注比赛结果并保存****************
                //  **********************后加的-------------获取押注比赛结果并保存****************************
                String manyMatchIds = cdBasketballSingleOrder.getMatchIds();
                String[] danMatchIdArray = manyMatchIds.split(",");
                List<String> list = new ArrayList<>();
                for (String str : danMatchIdArray) {
                    //String matchId = str.split("\\+")[1];
                    list.add(str);
                }
                String result = getResultStr(list);
                cdBasketballSingleOrder.setResult(result);
                cdBasketballSingleOrderService.save(cdBasketballSingleOrder);


                Map<String, CdBasketballAwards> resultMap = new HashMap<String, CdBasketballAwards>();
                for (int i = 0; i < index.size(); i++) {
                    CdBasketballAwards cdBasketballAwards = cdBasketballAwardsService.findByMatchId(index.get(i));
                    resultMap.put(index.get(i), cdBasketballAwards);
                }

                BigDecimal award = LotteryUtil.basketBallSingleWinningVerify(cdList, resultMap, playTypeList);


                if (award.compareTo(BigDecimal.ZERO) > 0) {
                    cdBasketballSingleOrder.setAward(award.toString());
                    cdBasketballSingleOrder.setStatus("4");
                    cdBasketballSingleOrderService.save(cdBasketballSingleOrder);
                    //改变订单总表状态 step1
                    WinPriceUtil.changeTotalOrder(cdBasketballSingleOrder.getOrderNum(), award.toString());
                } else {
                    cdBasketballSingleOrder.setStatus("5");
                    cdBasketballSingleOrderService.save(cdBasketballSingleOrder);
                    //改变订单总表状态
                    CdOrder co = cdOrderService.getOrderByOrderNum(cdBasketballSingleOrder.getOrderNum());
                    if (co != null) {
                        co.setWinPrice("0");//奖金
                        co.setStatus("2");//已开奖
                        cdOrderService.save(co);
                    }
                }
            }
//            //获取订单中押的全部场次
//            String matchIds = cdBasketballSingleOrder.getMatchIds();
//            Set<String> matchIdList = new HashSet<>(Arrays.asList(matchIds.split(",")));
//            //判断订单所有赛事是否都已经比完
//            if (awardMatchIdList.containsAll(matchIdList)) {
//
//                //  **********************后加的-------------获取押注比赛结果并保存****************************
//                String manyMatchIds = cdBasketballSingleOrder.getMatchIds();
//                String[] danMatchIdArray = manyMatchIds.split(",");
//                List<String> list = new ArrayList<>();
//                for (String str : danMatchIdArray) {
//                    //String matchId = str.split("\\+")[1];
//                    list.add(str);
//                }
//                String result = getResultStr(list);
//                cdBasketballSingleOrder.setResult(result);
//                cdBasketballSingleOrderService.save(cdBasketballSingleOrder);
//                //******************************************************************************************
//
//                //***********************************判断押中场次************************************************
//                //判断主胜
//                double hostWinOdds = 0;
//                String hostWin = cdBasketballSingleOrder.getHostWin();
//                if (StringUtils.isNotEmpty(hostWin)) {
//                    hostWinOdds = judgeBasketballSingle(hostWin, "hostWin");
//                }
//
//
//                //判断主负
//                double hostFailOdds = 0;
//                String hostFail = cdBasketballSingleOrder.getHostFail();
//                if (StringUtils.isNotEmpty(hostFail)) {
//                    hostFailOdds = judgeBasketballSingle(hostFail, "hostFail");
//                }
//
//
//                //***************************************判断结束*******************************************************
//
//
//                double oddsSum = hostWinOdds + hostFailOdds;
//                if (oddsSum > 0) {
//                    Double award = 2 * oddsSum;
//                    cdBasketballSingleOrder.setAward(new BigDecimal(award).setScale(2, 2).toString());
//                    cdBasketballSingleOrder.setStatus("4");
//                    cdBasketballSingleOrderService.save(cdBasketballSingleOrder);
//                    //改变订单总表状态 step1
//                    WinPriceUtil.changeTotalOrder(cdBasketballSingleOrder.getOrderNum(), award.toString());
//
//                    //------------------------原开奖逻辑----180517弃用-----------------------------------------
//                    //计算跟单佣金 step2
//                    //保存中奖纪录 step3
//                    //更新用户余额 step4
//                    //推送         step5
//                    //-------------------------------------------------------------------------------
//
//                } else {
//                    cdBasketballSingleOrder.setStatus("5");
//                    cdBasketballSingleOrderService.save(cdBasketballSingleOrder);
//                    //改变订单总表状态
//                    CdOrder co = cdOrderService.getOrderByOrderNum(cdBasketballSingleOrder.getOrderNum());
//                    if (co != null) {
//                        co.setWinPrice("0");//奖金
//                        co.setStatus("2");//已开奖
//                        cdOrderService.save(co);
//                    }
//                }
//            }
        }
    }

    private double judgeBasketballSingle(String method, String key) {
        String[] methodArray = method.split("\\|");
        double ood = 0;
        for (String aMethod : methodArray) {
            String[] aMethodArray = aMethod.split("\\+");
            CdBasketballAwards cdBasketballAwards = cdBasketballAwardsService.findByMatchId(aMethodArray[0]);
            String finish = "";
            switch (key) {
                case "hostWin":
                    finish = cdBasketballAwards.getWinGrap();
                    if (finish.contains("主胜")) {
                        finish = finish.substring(2);
                    } else {
                        return ood;
                    }
                    break;
                case "hostFail":
                    finish = cdBasketballAwards.getWinGrap();
                    if (finish.contains("主负")) {
                        finish = finish.substring(2);
                    } else {
                        return ood;
                    }
                    break;
            }
            String[] odds = aMethod.split(finish + "/");
            if (odds.length > 1) {
                String[] a = odds[1].split(",")[0].split("/");
                ood += Double.parseDouble(a[0]) * Double.parseDouble(a[1]);
//                ood += Double.parseDouble(odds[1].split(",")[0]);
            }
        }
        return ood;
    }

    private void judgeBaskerballFollow(String method, String key, List<String> winList, List<String> danWinList, CdBasketballFollowOrder cdBasketballFollowOrder) {
        String[] methodArray = method.split("\\|");
        for (int i = 0; i < methodArray.length; i++) {
            String[] aMethodArray = methodArray[i].split("\\+");
            CdBasketballAwards cdBasketballAwards = cdBasketballAwardsService.findByMatchId(aMethodArray[1]);
            String finish = "";
            String score = "";
            switch (key) {
                case "hostWin":
                    finish = cdBasketballAwards.getWinGrap();
                    score = finish.substring(2);
                    if (finish.contains("主胜") && aMethodArray[3].contains(score)) {
                        finish = score;
                    } else {
                        return;
                    }
                    break;
                case "hostFail":
                    finish = cdBasketballAwards.getWinGrap();
                    score = finish.substring(2);
                    if (finish.contains("客胜") && aMethodArray[3].contains(score)) {
                        finish = score;
                    } else {
                        return;
                    }
                    break;
                case "beat":
                    finish = cdBasketballAwards.getWinning();
                    if (finish.equals("主胜")) {
                        finish = "1";
                    } else {
                        finish = "0";
                    }
                    break;
                case "size":
                    //订单数分
                    String[] sizeCount = cdBasketballFollowOrder.getSizeCount().split(",");
                    int count = Integer.valueOf(cdBasketballAwards.getHs()) + Integer.valueOf(cdBasketballAwards.getVs());
                    if (count > Double.parseDouble(sizeCount[i])) {
                        finish = "1";
                    } else {
                        finish = "0";
                    }
                    break;
                case "let":
                    //订单让球数
                    String[] letBalls = cdBasketballFollowOrder.getLetScore().split(",");
                    int hs = Integer.valueOf(cdBasketballAwards.getHs());
                    int vs = Integer.valueOf(cdBasketballAwards.getVs());

                    int letBall = Integer.valueOf(letBalls[i]);

                    if (hs + letBall > vs) {
                        finish = "3";
                    } else if (hs + letBall == vs) {
                        finish = "1";
                    } else {
                        finish = "0";
                    }
                    break;
            }
            String[] odds = methodArray[i].split(finish + "/");
            if (odds.length > 1) {
                if (odds[1].contains(",")) {
                    winList.add(odds[1].split(",")[0]);
                    danWinList.add(aMethodArray[1] + odds[1].split(",")[0]);
                } else {
                    winList.add(odds[1]);
                    danWinList.add(aMethodArray[1] + odds[1]);
                }
            }
        }
    }


    private void recursion(int index, List<List<Double>> doubleList, int end, double sum, List<Double> count) {

        if (index <= end) {
            List<Double> doubleArray = doubleList.get(index);
            index += 1;
            for (Double d : doubleArray) {
                double sumCopy = sum;
                sum *= d;
                if (index > end) {
                    count.add(sum);
                }
                recursion(index, doubleList, end, sum, count);
                sum = sumCopy;
            }
        }
    }

    public String getResultStr(List<String> matchIdList) {
        String result = "";
        if (matchIdList.size() > 0) {
            for (String str : matchIdList) {
                CdBasketballAwards cdBasketballAwards = cdBasketballAwardsService.findByMatchId(str);
                if (cdBasketballAwards != null) {
                    String scoreResult = cdBasketballAwards.getHs() + ":" + cdBasketballAwards.getVs();
                    result += scoreResult + ",";
                }
            }
        }
        return result;
    }

}
