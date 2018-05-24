package com.youge.yogee.interfaces.quartz;

import com.youge.yogee.common.utils.lottery.LotteryUtil;
import com.youge.yogee.interfaces.lottery.util.WinPriceUtil;
import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.modules.cfootballawards.entity.CdFootballAwards;
import com.youge.yogee.modules.cfootballawards.service.CdFootballAwardsService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballBestFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballBestFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.cmagicorder.service.CdMagicFollowOrderService;
import com.youge.yogee.modules.cmagicorder.service.CdMagicOrderService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.service.CdOrderFollowTimesService;
import com.youge.yogee.modules.corder.service.CdOrderService;
import com.youge.yogee.modules.corder.service.CdOrderWinnersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * 定时任务
 * Created by Haipeng.Ren on 2015/12/28.
 */
@Component("QuartzListener")
public class QuartzListener {

    @Autowired
    private CdFootballFollowOrderService cdFootballFollowOrderService;
    @Autowired
    private CdFootballAwardsService cdFootballAwardsService;
    @Autowired
    private CdFootballSingleOrderService cdFootballSingleOrderService;
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
    private CdFootballBestFollowOrderService cdFootballBestFollowOrderService;

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
    @Scheduled(cron = "0 0/30 * * * ?")
//    @Scheduled(cron = "0 0 * * * ?")//1小时
    //@Scheduled(cron = "0 0 */1 * * ?")//2小时
    public void footballBestFollowOrder() {
        System.out.println("足球优化串关开奖");
        List<CdFootballFollowOrder> cdFootballFollowOrderList = cdFootballFollowOrderService.findStatusAndType("2");

        //全部可以比赛完的场次
        List<String> awardMatchIdList = cdFootballAwardsService.getAllMatchId();
        System.out.println(awardMatchIdList);

        for (CdFootballFollowOrder cdFootballFollowOrder : cdFootballFollowOrderList) {

//            System.out.println(cdFootballFollowOrder.getOrderNum());
//            System.out.println(cdFootballFollowOrder.getBestType());
//            System.out.println("胜负平" + cdFootballFollowOrder.getBeat());
//            System.out.println("让球胜负平" + cdFootballFollowOrder.getLet());
//            System.out.println("半全场" + cdFootballFollowOrder.getHalf());
//            System.out.println("总进球" + cdFootballFollowOrder.getGoal());
//            System.out.println("比分" + cdFootballFollowOrder.getScore());

            List<String> cdList = new ArrayList<String>();
            cdList.add(cdFootballFollowOrder.getBeat());
            cdList.add(cdFootballFollowOrder.getLet());
            cdList.add(cdFootballFollowOrder.getHalf());
            cdList.add(cdFootballFollowOrder.getGoal());
            cdList.add(cdFootballFollowOrder.getScore());
            List<String> index = LotteryUtil.queryEvent(cdList);
            String[] strings = new String[index.size()];
            String[] awardMatchIdArray = new String[awardMatchIdList.size()];

            //判断比赛是否全部完成
            boolean containsAll = LotteryUtil.containsAll(awardMatchIdList.toArray(awardMatchIdArray), index.toArray(strings));
            System.out.println(containsAll);
            if (containsAll) {

                String manyMatchIds = cdFootballFollowOrder.getDanMatchIds();
                String[] danMatchIdArray = manyMatchIds.split(",");
                List<String> list = new ArrayList<>();
                for (String str : danMatchIdArray) {
                    String matchId = str.split("\\+")[1];
                    list.add(matchId);
                }
                String result = getResultStr(list);
                cdFootballFollowOrder.setResult(result);
                cdFootballFollowOrderService.save(cdFootballFollowOrder);

                Map<String, CdFootballAwards> resultMap = new HashMap<String, CdFootballAwards>();
                for (int i = 0; i < index.size(); i++) {
                    CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(index.get(i));
                    resultMap.put(index.get(i), cdFootballAwards);
                }
                BigDecimal award = new BigDecimal(0);
                List<CdFootballBestFollowOrder> CdFootballBestFollowOrderList = cdFootballBestFollowOrderService.findByNum(cdFootballFollowOrder.getOrderNum());
                for (CdFootballBestFollowOrder cdFootballBestFollowOrder : CdFootballBestFollowOrderList) {
                    List<String> openList = new ArrayList<String>();
                    openList.add(cdFootballBestFollowOrder.getOrderDetail());
                    award = award.add(LotteryUtil.bestWinningVerify(openList, resultMap, cdFootballBestFollowOrder.getPerTimes())).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                }

                if (award.compareTo(BigDecimal.ZERO) > 0) {
                    System.err.println(cdFootballFollowOrder.getOrderNum() + ":" + award);
                    cdFootballFollowOrder.setAward(award.toString());
                    cdFootballFollowOrder.setStatus("4");
                    cdFootballFollowOrderService.save(cdFootballFollowOrder);
                    //保存中奖大洲  step1
                    String continent = cdFootballFollowOrder.getContinent();
                    CdLotteryUser clu = cdLotteryUserService.get(cdFootballFollowOrder.getUid());
                    String newContinent = clu.getContinent() + continent + ",";//大洲
                    newContinent = newContinent.replaceAll("null,", "");
                    clu.setContinent(newContinent);
                    cdLotteryUserService.save(clu);
                    //改变订单总表状态 step2
                    WinPriceUtil.changeTotalOrder(cdFootballFollowOrder.getOrderNum(), award.toString());
                } else {
                    cdFootballFollowOrder.setStatus("5");
                    cdFootballFollowOrderService.save(cdFootballFollowOrder);
                    //改变订单总表状态
                    CdOrder co = cdOrderService.getOrderByOrderNum(cdFootballFollowOrder.getOrderNum());
                    if (co != null) {
                        co.setWinPrice("0");//奖金
                        co.setStatus("2");//未中奖
                        cdOrderService.save(co);
                    }
                }
            }

            //获取订单中押的全部场次
//            String danMatchIds = cdFootballFollowOrder.getDanMatchIds();
//            Set<String> matchIdList = new HashSet<>();
//            for (String finishMatchId : danMatchIds.split(",")) {
//                matchIdList.add(finishMatchId.substring(2, 7));
//            }
//            //判断订单所有赛事是否都已经比完
//            if (awardMatchIdList.containsAll(matchIdList)) {
//                //  **************************后加的-------------获取押注比赛结果并保存****************
//                String manyMatchIds = cdFootballFollowOrder.getDanMatchIds();
//                String[] danMatchIdArray = manyMatchIds.split(",");
//                List<String> list = new ArrayList<>();
//                for (String str : danMatchIdArray) {
//                    String matchId = str.split("\\+")[1];
//                    list.add(matchId);
//                }
//                String result = getResultStr(list);
//                cdFootballFollowOrder.setResult(result);
//                cdFootballFollowOrderService.save(cdFootballFollowOrder);
//                //**********************************************************************************
//
//                List<CdFootballBestFollowOrder> CdFootballBestFollowOrderList = cdFootballBestFollowOrderService.findByNum(cdFootballFollowOrder.getOrderNum());
//                BigDecimal award = new BigDecimal(0);
//                for (CdFootballBestFollowOrder cdFootballBestFollowOrder : CdFootballBestFollowOrderList) {
//                    String orderDetail = cdFootballBestFollowOrder.getOrderDetail();
//                    String[] detailArray = orderDetail.split("\\|");
//
//                    Boolean flag = true;//如果最终为true则中奖
//                    for (String detail : detailArray) {
//                        String[] aDetail = detail.split("\\+");
//                        String key = aDetail[1];
//                        CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aDetail[0]);
//
//                        String orderFinish = StringUtils.split(aDetail[2], "/")[0];
//
//                        String finish = "";
//                        switch (key) {
//                            case "score":
//                                finish = cdFootballAwards.getHs() + ":" + cdFootballAwards.getVs();
//                                break;
//                            case "goal":
//                                finish = cdFootballAwards.getTotalNum();
//                                break;
//                            case "half":
//                                finish = cdFootballAwards.getWinGrap();
//                                break;
//                            case "beat":
//                                finish = cdFootballAwards.getWinning();
//                                break;
//                            case "let":
//                                int hs = Integer.valueOf(cdFootballAwards.getHs());
//                                int vs = Integer.valueOf(cdFootballAwards.getVs());
//                                //取得出票时让球数
//                                int letBall = Integer.valueOf(StringUtils.split(aDetail[3], "/")[1]);
//
//                                if (hs + letBall > vs) {
//                                    finish = "让主胜";
//                                } else if (hs + letBall == vs) {
//                                    finish = "平";
//                                } else {
//                                    finish = "让客胜";
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
//                        BigDecimal pertimes = new BigDecimal(cdFootballBestFollowOrder.getPerTimes());
//                        BigDecimal perAward = new BigDecimal(cdFootballBestFollowOrder.getPerAward());
//                        award = award.add(pertimes.multiply(perAward));
//                    }
//                }
//
//                //开奖结果为  未中奖
//                if (award.doubleValue() == 0) {
//                    cdFootballFollowOrder.setStatus("5");
//                    cdFootballFollowOrderService.save(cdFootballFollowOrder);
//                    //改变订单总表状态
//                    CdOrder co = cdOrderService.getOrderByOrderNum(cdFootballFollowOrder.getOrderNum());
//                    if (co != null) {
//                        co.setWinPrice("0");//奖金
//                        co.setStatus("2");//未中奖
//                        cdOrderService.save(co);
//                    }
//                } else {//开奖结果为  中奖
//                    cdFootballFollowOrder.setAward(award.toString());
//                    cdFootballFollowOrder.setStatus("4");
//                    cdFootballFollowOrderService.save(cdFootballFollowOrder);
//                    //保存中奖大洲  step1
//                    String continent = cdFootballFollowOrder.getContinent();
//                    CdLotteryUser clu = cdLotteryUserService.get(cdFootballFollowOrder.getUid());
//                    String newContinent = clu.getContinent() + continent + ",";//大洲
//                    newContinent = newContinent.replaceAll("null,", "");
//                    clu.setContinent(newContinent);
//                    cdLotteryUserService.save(clu);
//                    //改变订单总表状态 step2
//                    WinPriceUtil.changeTotalOrder(cdFootballFollowOrder.getOrderNum(), award.toString());
//
//                    //------------------------原开奖逻辑----180517弃用-----------------------------------------
//                    //计算跟单佣金 step3
//                    //保存中奖纪录 step4
//                    //更新用户余额 step5
//                    //推送         step6
//                    //------------------------------------------------------------------------------------------
//                }
//            }
        }
    }

   /* //    "0/10 * * * * ?"
    //@Scheduled(cron = "0/10 * * * * ?")//每10秒触发
    @Scheduled(cron = "0 0 *//*1 * * ?")//2小时
    public void footballFollowOrder() {
        System.out.println("足球串关开奖");
        List<CdFootballFollowOrder> cdFootballFollowOrderList = cdFootballFollowOrderService.findStatusAndType("1");


        //全部可以比赛完的场次
        List<String> awardMatchIdList = cdFootballAwardsService.getAllMatchId();

        out:
        for (CdFootballFollowOrder cdFootballFollowOrder : cdFootballFollowOrderList) {

            //获取订单中押的全部场次
            String danMatchIds = cdFootballFollowOrder.getDanMatchIds();
            Set<String> matchIdList = new HashSet<>();
            for (String finishMatchId : danMatchIds.split(",")) {
                matchIdList.add(finishMatchId.substring(2, 7));
            }

            //System.out.println(cdFootballFollowOrder.getOrderNum());
            //判断订单所有赛事是否都已经比完
            if (awardMatchIdList.containsAll(matchIdList)) {

                //  **************************后加的-------------获取押注比赛结果并保存****************
                String manyMatchIds = cdFootballFollowOrder.getDanMatchIds();
                String[] danMatchIdArray = manyMatchIds.split(",");
                List<String> list = new ArrayList<>();
                for (String str : danMatchIdArray) {
                    String matchId = str.split("\\+")[1];
                    list.add(matchId);
                }
                String result = getResultStr(list);
                cdFootballFollowOrder.setResult(result);
                cdFootballFollowOrderService.save(cdFootballFollowOrder);
                /*//**********************************************************************************

     //押对的彩票
     List<String> winList = new ArrayList<>();
     //押对的彩票(用于带胆的彩票)
     List<String> danWinList = new ArrayList<>();

     /*//***********************************判断押中场次************************************************
     //判断比分
     String score = cdFootballFollowOrder.getScore();
     if (StringUtils.isNotEmpty(score)) {
     judgeFootballFollow(score, "score", winList, danWinList);
     }
     //判断总进球
     String goal = cdFootballFollowOrder.getGoal();
     if (StringUtils.isNotEmpty(goal)) {
     judgeFootballFollow(goal, "goal", winList, danWinList);
     }
     //判断半全场
     String half = cdFootballFollowOrder.getHalf();
     if (StringUtils.isNotEmpty(half)) {
     judgeFootballFollow(half, "half", winList, danWinList);
     }
     //判断胜负平
     String beat = cdFootballFollowOrder.getBeat();
     if (StringUtils.isNotEmpty(beat)) {
     judgeFootballFollow(beat, "beat", winList, danWinList);
     }


     //判断让球胜负平
     String let = cdFootballFollowOrder.getLet();
     //订单让球数
     if (StringUtils.isNotEmpty(let)) {
     String[] letBalls = cdFootballFollowOrder.getLetBalls().split(",");
     String[] methodArray = let.split("\\|");
     for (int i = 0; i < methodArray.length; i++) {
     String[] aMethodArray = methodArray[i].split("\\+");
     CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aMethodArray[1]);
     String finish = "";

     int hs = Integer.valueOf(cdFootballAwards.getHs());
     int vs = Integer.valueOf(cdFootballAwards.getVs());

     int letBall = Integer.valueOf(letBalls[i]);

     if (hs + letBall > vs) {
     finish = "3";
     } else if (hs + letBall == vs) {
     finish = "1";
     } else {
     finish = "0";
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


     /*//***************************************判断结束*******************************************************

     //有中奖彩票
     if (winList.size() > 0) {
     double oodSum = 0;
     if (danMatchIds.contains("胆")) {
     //得到所有胆场
     List<String> danList = new ArrayList<>();
     String[] matchIds = danMatchIds.split(",");
     for (String matchId : matchIds) {
     if (matchId.contains("胆")) {
     danList.add(matchId.substring(2, 7));
     }
     }

     //各个胆场开奖序列的list
     List<List<Double>> dList = new ArrayList<>();
     List<String> danWinListCopy = new ArrayList<>();

     for (String danMatchId : danList) {
     List<Double> oobList = new ArrayList<>();
     for (String danWin : danWinList) {
     if (danWin.contains(danMatchId)) {
     oobList.add(Double.parseDouble(danWin.split(danMatchId)[1]));
     //                                    danWinList.remove(danWin);
     } else {
     danWinListCopy.add(danWin);
     }
     }

     dList.add(oobList);
     }
     danWinList = danWinListCopy;

     /*//*******************************判断是否可以开奖****************************
     Boolean isWin = true;

     for (List<Double> oobList : dList) {
     if (oobList.size() == 0) {
     isWin = false;
     break;
     }
     }

     if (isWin) {
     String followNum = cdFootballFollowOrder.getFollowNum();
     if (followNum.contains(",")) {
     for (String num : followNum.split(",")) {
     Integer numInt = Integer.valueOf(num);
     if (danWinList.size() < numInt - danList.size()) {
     isWin = false;
     }
     }
     } else {
     Integer numInt = Integer.valueOf(followNum);
     if (danWinList.size() < numInt - danList.size()) {
     isWin = false;
     }
     }
     }

     /*/
    /*******************************判断结束****************************

     if (isWin) {
     //计算各个胆场之间赔率相乘
     List<Double> count = new ArrayList<>();
     recursion(0, dList, dList.size() - 1, 1.0, count);

     //计算非胆场之间赔率相乘
     List<String> danOddWinList = new ArrayList<>();
     for (String oddWin : danWinList) {
     danOddWinList.add(oddWin.substring(5));
     }

     //计算完整胜利赔率
     String followNum = cdFootballFollowOrder.getFollowNum();
     if (followNum.contains(",")) {
     for (String num : followNum.split(",")) {
     double ood = 0;
     //TODO 需要用BigDecimal
     List<Double> doubleList = Calculations.oddsCollection(Integer.parseInt(num) - danList.size(), danOddWinList);
     for (Double dan : count) {
     for (Double feiDan : doubleList) {
     ood += dan * feiDan;
     }
     }
     oodSum += ood;
     }
     } else {
     List<Double> doubleList = Calculations.oddsCollection(Integer.parseInt(followNum) - danList.size(), danOddWinList);
     for (Double dan : count) {
     for (Double feiDan : doubleList) {
     oodSum += dan * feiDan;
     }
     }
     }
     } else {
     cdFootballFollowOrder.setStatus("5");
     cdFootballFollowOrderService.save(cdFootballFollowOrder);
     //改变订单总表状态
     CdOrder co = cdOrderService.getOrderByOrderNum(cdFootballFollowOrder.getOrderNum());
     if (co != null) {
     co.setWinPrice("0");//奖金
     co.setStatus("2");//未中奖
     cdOrderService.save(co);
     }
     continue;
     }
     } else {
     String followNum = cdFootballFollowOrder.getFollowNum();
     if (followNum.contains(",")) {
     for (String num : followNum.split(",")) {
     Integer numInt = Integer.valueOf(num);
     if (winList.size() < numInt) {
     cdFootballFollowOrder.setStatus("5");//未中奖
     cdFootballFollowOrderService.save(cdFootballFollowOrder);
     //改变订单总表状态
     CdOrder co = cdOrderService.getOrderByOrderNum(cdFootballFollowOrder.getOrderNum());
     if (co != null) {
     co.setWinPrice("0");//奖金
     co.setStatus("2");//未中奖
     cdOrderService.save(co);
     //2018-05-18 yhw   如果3串1,2串1,3串没中，2串中了， 就会有问题
     continue out;
     }
     }
     }

     }
     //根据串关计算奖金

     if (followNum.contains(",")) {
     for (String num : followNum.split(",")) {
     //TODO 需要用BigDecimal
     oodSum += Calculations.odds(Integer.parseInt(num), winList);
     }
     } else {
     oodSum = Calculations.odds(Integer.parseInt(followNum), winList);
     }
     }

     //所有中奖赔率
     Double award = Integer.valueOf(cdFootballFollowOrder.getTimes()) * oodSum * 2;
     cdFootballFollowOrder.setAward(new BigDecimal(award).setScale(2, 2).toString());
     cdFootballFollowOrder.setStatus("4");
     cdFootballFollowOrderService.save(cdFootballFollowOrder);

     //保存中獎大洲 step1
     String continent = cdFootballFollowOrder.getContinent() + ",";
     CdLotteryUser clu = cdLotteryUserService.get(cdFootballFollowOrder.getUid());
     String oldContinent = clu.getContinent();
     if (StringUtils.isEmpty(oldContinent)) {
     clu.setContinent(continent);
     } else {
     String newContinent = oldContinent + continent;//大洲
     clu.setContinent(newContinent);
     }
     //改变订单总表状态step2
     WinPriceUtil.changeTotalOrder(cdFootballFollowOrder.getOrderNum(), award.toString());
     //                    CdOrder co = cdOrderService.getOrderByOrderNum(cdFootballFollowOrder.getOrderNum());
     //                    if (co != null) {
     //                        BigDecimal finalAward = new BigDecimal(award.toString()).setScale(2, 1);
     //                        co.setWinPrice(finalAward.toString());//奖金 用于反显
     //                        co.setStatus("3");//中奖
     //                        cdOrderService.save(co);
     //                    }

     //------------------------原开奖逻辑----180517弃用-----------------------------------------
     //计算跟单佣金 step3
     //保存中奖纪录 step4
     //更新用户余额 step5
     //推送         step6
     //-------------------------------------------------------------------------------
     } else {
     cdFootballFollowOrder.setStatus("5");
     cdFootballFollowOrderService.save(cdFootballFollowOrder);
     //改变订单总表状态
     CdOrder co = cdOrderService.getOrderByOrderNum(cdFootballFollowOrder.getOrderNum());
     if (co != null) {
     co.setWinPrice("0");//奖金
     co.setStatus("2");//未中奖
     cdOrderService.save(co);
     }
     }
     }
     }
     }*/

    //    "0/10 * * * * ?"
//    @Scheduled(cron = "*/30 * * * * ?")//每10秒触发
    @Scheduled(cron = "0 0/30 * * * ?")//2小时
    public void footballFollowOrder() {
        System.out.println("足球串关开奖");
        List<CdFootballFollowOrder> cdFootballFollowOrderList = cdFootballFollowOrderService.findStatusAndType("1");


        //全部可以比赛完的场次
        List<String> awardMatchIdList = cdFootballAwardsService.getAllMatchId();
        //System.out.println(awardMatchIdList);

        for (CdFootballFollowOrder cdFootballFollowOrder : cdFootballFollowOrderList) {
//            System.out.println("胜负平" + cdFootballFollowOrder.getBeat());
//            System.out.println("让球胜负平" + cdFootballFollowOrder.getLet());
//            System.out.println("半全场" + cdFootballFollowOrder.getHalf());
//            System.out.println("总进球" + cdFootballFollowOrder.getGoal());
//            System.out.println("比分" + cdFootballFollowOrder.getScore());
            List<String> cdList = new ArrayList<String>();
            cdList.add(cdFootballFollowOrder.getBeat());
            cdList.add(cdFootballFollowOrder.getLet());
            cdList.add(cdFootballFollowOrder.getHalf());
            cdList.add(cdFootballFollowOrder.getGoal());
            cdList.add(cdFootballFollowOrder.getScore());
            List<String> index = LotteryUtil.queryEvent(cdList);
            String[] strings = new String[index.size()];
            String[] awardMatchIdArray = new String[awardMatchIdList.size()];

            //判断比赛是否全部完成
            boolean containsAll = LotteryUtil.containsAll(awardMatchIdList.toArray(awardMatchIdArray), index.toArray(strings));
            System.out.println(containsAll);
            if (containsAll) {

                String manyMatchIds = cdFootballFollowOrder.getDanMatchIds();
                String[] danMatchIdArray = manyMatchIds.split(",");
                List<String> list = new ArrayList<>();
                for (String str : danMatchIdArray) {
                    String matchId = str.split("\\+")[1];
                    list.add(matchId);
                }
                String result = getResultStr(list);
                cdFootballFollowOrder.setResult(result);
                cdFootballFollowOrderService.save(cdFootballFollowOrder);


                Map<String, CdFootballAwards> resultMap = new HashMap<String, CdFootballAwards>();
                for (int i = 0; i < index.size(); i++) {
                    CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(index.get(i));
                    resultMap.put(index.get(i), cdFootballAwards);
                }
                //判断是否中奖
                BigDecimal award = LotteryUtil.WinningVerify(cdList, resultMap, cdFootballFollowOrder.getFollowNum(), cdFootballFollowOrder.getTimes());
                if (award.compareTo(BigDecimal.ZERO) > 0) {
                    //System.err.println(cdFootballFollowOrder.getOrderNum() + ":" + award);
                    cdFootballFollowOrder.setAward(award.toString());
                    cdFootballFollowOrder.setStatus("4");
                    cdFootballFollowOrderService.save(cdFootballFollowOrder);
                    //保存中奖大洲  step1
                    String continent = cdFootballFollowOrder.getContinent();
                    CdLotteryUser clu = cdLotteryUserService.get(cdFootballFollowOrder.getUid());
                    String newContinent = clu.getContinent() + continent + ",";//大洲
                    newContinent = newContinent.replaceAll("null,", "");
                    clu.setContinent(newContinent);
                    cdLotteryUserService.save(clu);
                    //改变订单总表状态 step2
                    WinPriceUtil.changeTotalOrder(cdFootballFollowOrder.getOrderNum(), award.toString());
                } else {
                    cdFootballFollowOrder.setStatus("5");
                    cdFootballFollowOrderService.save(cdFootballFollowOrder);
                    //改变订单总表状态
                    CdOrder co = cdOrderService.getOrderByOrderNum(cdFootballFollowOrder.getOrderNum());
                    if (co != null) {
                        co.setWinPrice("0");//奖金
                        co.setStatus("2");//未中奖
                        cdOrderService.save(co);
                    }
                }
                //System.out.println("全部场次:" + resultMap);
            }

        }
    }

    @Scheduled(cron = "0 0/30 * * * ?")//2小时
    public void footballSingleOrder() {
//        System.out.println("足球单关开奖");

        List<CdFootballSingleOrder> cdFootballSingleOrderList = cdFootballSingleOrderService.findStatus();


        //全部可以比赛完的场次
        List<String> awardMatchIdList = cdFootballAwardsService.getAllMatchId();


        for (CdFootballSingleOrder cdFootballSingleOrder : cdFootballSingleOrderList) {

            //获取订单中押的全部场次
            String matchIds = cdFootballSingleOrder.getMatchIds();
            Set<String> matchIdList = new HashSet<>();
            for (String finishMatchId : matchIds.split(",")) {
                matchIdList.add(finishMatchId);
            }

            if (!awardMatchIdList.containsAll(matchIdList)) {
                continue;
            }
            //  **********************后加的-------------获取押注比赛结果并保存****************************
            String manyMatchIds = cdFootballSingleOrder.getMatchIds();
            String[] danMatchIdArray = manyMatchIds.split(",");
            List<String> list = new ArrayList<>();
            for (String str : danMatchIdArray) {
                list.add(str);
            }
            String result = getResultStr(list);
            cdFootballSingleOrder.setResult(result);
            cdFootballSingleOrderService.save(cdFootballSingleOrder);
            //******************************************************************************************


            //***********************************判断押中场次************************************************
            //判断比分
            String score = cdFootballSingleOrder.getScore();
            double scoreOdds = 0;
            if (StringUtils.isNotEmpty(score)) {
                scoreOdds = judgeFootballSingle(score, "score");
            }

            //判断总进球
            String goal = cdFootballSingleOrder.getGoal();
            double goalOdds = 0;
            if (StringUtils.isNotEmpty(goal)) {
                goalOdds = judgeFootballSingle(goal, "goal");
            }

            //判断半全场
            String half = cdFootballSingleOrder.getHalf();
            double halfOdds = 0;
            if (StringUtils.isNotEmpty(half)) {
                halfOdds = judgeFootballSingle(half, "half");
            }

            //判断胜负平
            String beat = cdFootballSingleOrder.getBeat();
            double beatOdds = 0;
            if (StringUtils.isNotEmpty(beat)) {
                beatOdds = judgeFootballSingle(beat, "beat");
            }

            //判断让球胜负平
            String let = cdFootballSingleOrder.getLet();
            double letOdds = 0;
            if (StringUtils.isNotEmpty(let)) {
                letOdds = judgeFootballSingle(let, "let");
            }


            //***************************************判断结束*******************************************************

            double oddsSum = scoreOdds + goalOdds + halfOdds + beatOdds + letOdds;
            if (oddsSum > 0) {
                Double award = 2 * oddsSum;
                cdFootballSingleOrder.setAward(new BigDecimal(award).setScale(2, 2).toString());
                cdFootballSingleOrder.setStatus("4");
                cdFootballSingleOrderService.save(cdFootballSingleOrder);

                //保存中奖大洲 step1
                String continent = cdFootballSingleOrder.getContinent();
                CdLotteryUser clu = cdLotteryUserService.get(cdFootballSingleOrder.getUid());
                String newContinent = clu.getContinent() + continent;//大洲
                clu.setContinent(newContinent);
                cdLotteryUserService.save(clu);

                //改变订单总表状态 step2
                WinPriceUtil.changeTotalOrder(cdFootballSingleOrder.getOrderNum(), award.toString());

                //------------------------原开奖逻辑----180517弃用-----------------------------------------
                //计算跟单佣金 step3
                //保存中奖纪录 step4
                //更新用户余额 step5
                //推送         step6
                //-------------------------------------------------------------------------------


            } else {
                cdFootballSingleOrder.setStatus("5");
                cdFootballSingleOrderService.save(cdFootballSingleOrder);
                //改变订单总表状态
                CdOrder co = cdOrderService.getOrderByOrderNum(cdFootballSingleOrder.getOrderNum());
                if (co != null) {
                    co.setWinPrice("0");//奖金
                    co.setStatus("2");//已开奖
                    cdOrderService.save(co);
                }
            }


        }
    }


    private double judgeFootballSingle(String method, String key) {
        String[] methodArray = method.split("\\|");
        double ood = 0;
        for (String aMethod : methodArray) {
            String[] aMethodArray = aMethod.split("\\+");
            CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aMethodArray[0]);
            String finish = "";
            switch (key) {
                case "score":
                    finish = cdFootballAwards.getHs() + ":" + cdFootballAwards.getVs();
                    break;
                case "goal":
                    finish = cdFootballAwards.getTotalNum();
                    break;
                case "half":
                    finish = BallGameCals.changeFootballSf(cdFootballAwards.getWinGrap());
                    break;
                case "beat":
                    finish = BallGameCals.changeFootballSf(cdFootballAwards.getWinning());
                    break;
                case "let":
                    finish = BallGameCals.changeFootballSf(cdFootballAwards.getSpread());
                    break;
            }
            String[] a = StringUtils.split(aMethodArray[2], ",");
            for (String s : a) {
                String[] split = StringUtils.split(s, "/");
                if (split[0].equals(finish)) {
                    ood += Double.parseDouble(split[1]) * Double.parseDouble(split[2]);
                }
            }
        }
        return ood;
    }


    private void judgeFootballFollow(String method, String key, List<String> winList, List<String> danWinList) {
        String[] methodArray = method.split("\\|");
        for (String aMethod : methodArray) {
            String[] aMethodArray = aMethod.split("\\+");
            CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aMethodArray[1]);
            String finish = "";
            switch (key) {
                case "score":
                    finish = cdFootballAwards.getHs() + ":" + cdFootballAwards.getVs();
                    break;
                case "goal":
                    finish = cdFootballAwards.getTotalNum();
                    break;
                /*case "half":
                    finish = cdFootballAwards.getWinGrap();
                    break;
                case "beat":
                    finish = cdFootballAwards.getWinning();
                    break;
                case "let":
                    finish = cdFootballAwards.getSpread();
                    break;*/
                //***************************************** start 2018-05-16   yhw  修改开奖时系统数据（3、1、0），爬虫数据（胜、平、负）对应不上问题******************
                case "half":
                    finish = BallGameCals.changeFootballSf(cdFootballAwards.getWinGrap());
                    break;
                case "beat":
                    finish = BallGameCals.changeFootballSf(cdFootballAwards.getWinning());
                    break;
                case "let":
                    finish = BallGameCals.changeFootballSf(cdFootballAwards.getSpread());
                    break;
                //***************************************** end   ***********************************************************************************************

            }
            String[] odds = aMethod.split(finish + "/");
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

    public String getResultStr(List<String> matchIds) {
        String result = "";
        if (matchIds.size() > 0) {
            for (String str : matchIds) {
                CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(str);
                if (cdFootballAwards != null) {
                    String scoreResult = cdFootballAwards.getHs() + ":" + cdFootballAwards.getVs();
                    result += scoreResult + ",";
                }
            }
        }
        return result;
    }


    //********************************************2018-05-22 yhw 开奖及计算奖金  start*****************************************************

/*
@Scheduled(cron = "0 0/5 * * * ?")
public void footballFollowOrder2(){
    System.out.println("足球串关开奖2222222");
    List<CdFootballFollowOrder> cdFootballFollowOrderList = cdFootballFollowOrderService.findStatusAndType("1");
    //全部可以比赛完的场次
    List<String> awardMatchIdList = cdFootballAwardsService.getAllMatchId();

    for (CdFootballFollowOrder cdFootballFollowOrder : cdFootballFollowOrderList) {

        //获取订单中押的全部场次
        String danMatchIds = cdFootballFollowOrder.getDanMatchIds();
        Set<String> matchIdList = new HashSet<>();
        List<String> matchIds = new ArrayList<>();
        for (String finishMatchId : danMatchIds.split(",")) {
            matchIdList.add(finishMatchId.substring(2, 7));
            matchIds.add(finishMatchId.substring(2, 7));
        }
        //System.out.println(cdFootballFollowOrder.getOrderNum());
        //判断订单所有赛事是否都已经比完
        if (awardMatchIdList.containsAll(matchIdList)) {
            List<List<String>> detailOrder = new ArrayList<>();//所有比赛压中的赔率集合
            //根据match_id取赛果
            for (int i = 0; i < matchIds.size(); i++) {
                List<String> chilDetailOrder = new ArrayList<>();//每场比赛压中的赔率集合
                CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(matchIds.get(i));
                String beat = cdFootballFollowOrder.getBeat();//胜负平
                String score = cdFootballFollowOrder.getScore();//比分
                String goal = cdFootballFollowOrder.getGoal();//总进球
                String half =cdFootballFollowOrder.getHalf();//半全场
                String let = cdFootballFollowOrder.getLet();//让球胜负平
            /*/
/*******************构建赔率集合开始*****************************
 //胜平负
 if(StringUtils.isNotEmpty(beat)) {
 String[] beatArr = beat.split("\\|");
 for (int j = 0; j < beatArr.length; j++) {
 if(beatArr[j].contains(matchIds.get(i))){
 String beatStr = beatArr[j].split("\\+")[3];//赔率字符串
 if("主胜".equals(cdFootballAwards.getWinning())){
 if(beatStr.contains("3/")){
 String peilv = beatStr.substring(beatStr.indexOf("3/")).split("\\,")[0];
 peilv = peilv.replace("3/","");
 chilDetailOrder.add(peilv);
 }
 }else if("平".equals(cdFootballAwards.getWinning())){
 if(beatStr.contains("1/")){
 String peilv = beatStr.substring(beatStr.indexOf("1/")).split("\\,")[0];
 peilv = peilv.replace("1/","");
 chilDetailOrder.add(peilv);
 }
 }else if("主负".equals(cdFootballAwards.getWinning())){
 if(beatStr.contains("0/")){
 String peilv = beatStr.substring(beatStr.indexOf("0/")).split("\\,")[0];
 peilv = peilv.replace("0/","");
 chilDetailOrder.add(peilv);
 }
 }
 break;
 }
 }
 }
 //让球胜负平
 if(StringUtils.isNotEmpty(let)) {
 String[] letArr = let.split("\\|");
 for (int j = 0; j < letArr.length; j++) {
 if(letArr[j].contains(matchIds.get(i))){
 String letStr = letArr[j].split("\\+")[3];//赔率字符串
 if("让主胜".equals(cdFootballAwards.getSpread())){
 if(letStr.contains("3/")){
 String peilv = letStr.substring(letStr.indexOf("3/")).split("\\,")[0];
 peilv = peilv.replace("3/","");
 chilDetailOrder.add(peilv);
 }
 }else if("平".equals(cdFootballAwards.getSpread())){
 if(letStr.contains("1/")){
 String peilv = letStr.substring(letStr.indexOf("1/")).split("\\,")[0];
 peilv = peilv.replace("1/","");
 chilDetailOrder.add(peilv);
 }
 }else if("让主负".equals(cdFootballAwards.getSpread())){
 if(letStr.contains("0/")){
 String peilv = letStr.substring(letStr.indexOf("0/")).split("\\,")[0];
 peilv = peilv.replace("0/","");
 chilDetailOrder.add(peilv);
 }
 }
 break;
 }
 }
 }
 //比分
 if(StringUtils.isNotEmpty(score)) {
 String[] scoreArr = score.split("\\|");
 for (int j = 0; j < scoreArr.length; j++) {
 if(scoreArr[j].contains(matchIds.get(i))){
 String scoreStr = scoreArr[j].split("\\+")[3];//赔率字符串
 String awardsScore = cdFootballAwards.getHs()+":"+cdFootballAwards.getVs();
 if(scoreStr.contains(awardsScore)){
 String peilv = scoreStr.substring(scoreStr.indexOf(awardsScore+"/")).split("\\,")[0];
 peilv = peilv.replace(awardsScore+"/","");
 chilDetailOrder.add(peilv);
 }
 break;
 }
 }
 }
 //总进球
 if(StringUtils.isNotEmpty(goal)) {
 String[] goalArr = goal.split("\\|");
 for (int j = 0; j < goalArr.length; j++) {
 if(goalArr[j].contains(matchIds.get(i))){
 String goalStr = goalArr[j].split("\\+")[3];//赔率字符串
 String awardsgoal = cdFootballAwards.getTotalNum();
 if(goalStr.contains(awardsgoal)){
 String peilv = goalStr.substring(goalStr.indexOf(awardsgoal+"/")).split("\\,")[0];
 peilv = peilv.replace(awardsgoal+"/","");
 chilDetailOrder.add(peilv);
 }
 break;
 }
 }
 }
 //半全场
 if(StringUtils.isNotEmpty(half)) {
 String[] halfArr = half.split("\\|");
 for (int j = 0; j < halfArr.length; j++) {
 if(halfArr[j].contains(matchIds.get(i))){
 String halfStr = halfArr[j].split("\\+")[3];//赔率字符串
 if("胜胜".equals(cdFootballAwards.getWinGrap())){
 if(halfStr.contains("33/")){
 String peilv = halfStr.substring(halfStr.indexOf("33/")).split("\\,")[0];
 peilv = peilv.replace("33/","");
 chilDetailOrder.add(peilv);
 }
 }else if("胜平".equals(cdFootballAwards.getWinGrap())){
 if(halfStr.contains("31/")){
 String peilv = halfStr.substring(halfStr.indexOf("31/")).split("\\,")[0];
 peilv = peilv.replace("31/","");
 chilDetailOrder.add(peilv);
 }
 }else if("胜负".equals(cdFootballAwards.getWinGrap())){
 if(halfStr.contains("30/")){
 String peilv = halfStr.substring(halfStr.indexOf("30/")).split("\\,")[0];
 peilv = peilv.replace("30/","");
 chilDetailOrder.add(peilv);
 }
 }else if("平胜".equals(cdFootballAwards.getWinGrap())){
 if(halfStr.contains("13/")){
 String peilv = halfStr.substring(halfStr.indexOf("13/")).split("\\,")[0];
 peilv = peilv.replace("13/","");
 chilDetailOrder.add(peilv);
 }
 }else if("平平".equals(cdFootballAwards.getWinGrap())){
 if(halfStr.contains("11/")){
 String peilv = halfStr.substring(halfStr.indexOf("11/")).split("\\,")[0];
 peilv = peilv.replace("11/","");
 chilDetailOrder.add(peilv);
 }
 }else if("平负".equals(cdFootballAwards.getWinGrap())){
 if(halfStr.contains("10/")){
 String peilv = halfStr.substring(halfStr.indexOf("10/")).split("\\,")[0];
 peilv = peilv.replace("10/","");
 chilDetailOrder.add(peilv);
 }
 }else if("负胜".equals(cdFootballAwards.getWinGrap())){
 if(halfStr.contains("03/")){
 String peilv = halfStr.substring(halfStr.indexOf("03/")).split("\\,")[0];
 peilv = peilv.replace("03/","");
 chilDetailOrder.add(peilv);
 }
 }else if("负平".equals(cdFootballAwards.getWinGrap())){
 if(halfStr.contains("01/")){
 String peilv = halfStr.substring(halfStr.indexOf("01/")).split("\\,")[0];
 peilv = peilv.replace("01/","");
 chilDetailOrder.add(peilv);
 }
 }else if("负负".equals(cdFootballAwards.getWinGrap())){
 if(halfStr.contains("00/")){
 String peilv = halfStr.substring(halfStr.indexOf("00/")).split("\\,")[0];
 peilv = peilv.replace("00/","");
 chilDetailOrder.add(peilv);
 }
 }
 break;
 }
 }
 }
 /*/
/*******************构建赔率集合结束*****************************
 detailOrder.add(chilDetailOrder);
 }
 /*/
/************************根据串关方式，计算奖金开始************************************
 boolean status =false;//是否中奖
 double  award = 0.0;//中奖金额
 for (String followNum: cdFootballFollowOrder.getFollowNum().split("\\,")) {
 List<String> childPeilv = madeBeishu01(detailOrder,followNum);
 if(childPeilv.size()>0){
 status = true;
 double childAward = 0.0;
 for (int i = 0; i < childPeilv.size(); i++) {
 childAward += Double.parseDouble(childPeilv.get(i))*2*Double.parseDouble(cdFootballFollowOrder.getTimes());
 }
 award +=childAward;
 }
 }
 cdFootballFollowOrder.setStatus(status?"4":"5");
 cdFootballFollowOrder.setAward(String.format("%.2f",award));
 cdFootballFollowOrderService.save(cdFootballFollowOrder);
 CdOrder cdOrder = cdOrderService.getOrderByOrderNum(cdFootballFollowOrder.getOrderNum());
 if(cdOrder!=null){
 cdOrder.setStatus(status?"3":"2");
 cdOrder.setWinPrice(String.format("%.2f",award));
 cdOrderService.save(cdOrder);
 }

 /*/
/************************根据串关方式，计算奖金结束************************************
 }

 }

 }
 */


    /**
     * 根据串关方式，构建二维数组
     * 第一维数组：有多少概率组合就有多长
     * 第二维数组：长度为2，[0]--AA/主1-5/4.30+BB/主5-1/12.30+...+n(n串1)
     * [1]---4.30*12.30*...*n
     *
     * @param detailArr
     * @param followNum
     * @return
     */
    private List<String> madeBeishu01(List<List<String>> detailArr, String followNum) {
        List<String> arr = new ArrayList<>();
        if ("2".equals(followNum)) {//2串1
            //最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size(); i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            //保留小数点后两位，单倍奖金
                            arr.add(String.format("%.2f", Double.parseDouble(list01Str)
                                    * Double.parseDouble(list02Str)));
                        }
                    }
                }
            }

        } else if ("3".equals(followNum)) {
            //最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size(); i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            for (int m3 = k + 1; m3 < detailArr.size(); m3++) {
                                List<String> list03 = detailArr.get(m3);
                                for (int l3 = 0; l3 < list03.size(); l3++) {
                                    String list03Str = list03.get(l3);
                                    //保留小数点后两位，单倍奖金
                                    arr.add(String.format("%.2f", Double.parseDouble(list01Str)
                                            * Double.parseDouble(list02Str)
                                            * Double.parseDouble(list03Str)));

                                }
                            }

                        }
                    }
                }
            }
        } else if ("4".equals(followNum)) {
//最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size(); i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            for (int m3 = k + 1; m3 < detailArr.size(); m3++) {
                                List<String> list03 = detailArr.get(m3);
                                for (int l3 = 0; l3 < list03.size(); l3++) {
                                    String list03Str = list03.get(l3);
                                    for (int m4 = m3 + 1; m4 < detailArr.size(); m4++) {
                                        List<String> list04 = detailArr.get(m4);
                                        for (int l4 = 0; l4 < list04.size(); l4++) {
                                            String list04Str = list04.get(l4);
                                            //保留小数点后两位，单倍奖金
                                            arr.add(String.format("%.2f", Double.parseDouble(list01Str)
                                                    * Double.parseDouble(list02Str)
                                                    * Double.parseDouble(list03Str)
                                                    * Double.parseDouble(list04Str)));

                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }
        } else if ("5".equals(followNum)) {
//最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size(); i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            for (int m3 = k + 1; m3 < detailArr.size(); m3++) {
                                List<String> list03 = detailArr.get(m3);
                                for (int l3 = 0; l3 < list03.size(); l3++) {
                                    String list03Str = list03.get(l3);
                                    for (int m4 = m3 + 1; m4 < detailArr.size(); m4++) {
                                        List<String> list04 = detailArr.get(m4);
                                        for (int l4 = 0; l4 < list04.size(); l4++) {
                                            String list04Str = list04.get(l4);
                                            for (int m5 = m4 + 1; m5 < detailArr.size(); m5++) {
                                                List<String> list05 = detailArr.get(m5);
                                                for (int l5 = 0; l5 < list05.size(); l5++) {
                                                    String list05Str = list05.get(l5);
                                                    //保留小数点后两位，单倍奖金
                                                    arr.add(String.format("%.2f", Double.parseDouble(list01Str)
                                                            * Double.parseDouble(list02Str)
                                                            * Double.parseDouble(list03Str)
                                                            * Double.parseDouble(list04Str)
                                                            * Double.parseDouble(list05Str)));

                                                }
                                            }

                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }
        } else if ("6".equals(followNum)) {
//最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size(); i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            for (int m3 = k + 1; m3 < detailArr.size(); m3++) {
                                List<String> list03 = detailArr.get(m3);
                                for (int l3 = 0; l3 < list03.size(); l3++) {
                                    String list03Str = list03.get(l3);
                                    for (int m4 = m3 + 1; m4 < detailArr.size(); m4++) {
                                        List<String> list04 = detailArr.get(m4);
                                        for (int l4 = 0; l4 < list04.size(); l4++) {
                                            String list04Str = list04.get(l4);
                                            for (int m5 = m4 + 1; m5 < detailArr.size(); m5++) {
                                                List<String> list05 = detailArr.get(m5);
                                                for (int l5 = 0; l5 < list05.size(); l5++) {
                                                    String list05Str = list05.get(l5);
                                                    for (int m6 = m5 + 1; m6 < detailArr.size(); m6++) {
                                                        List<String> list06 = detailArr.get(m6);
                                                        for (int l6 = 0; l6 < list06.size(); l6++) {
                                                            String list06Str = list06.get(l6);
                                                            //保留小数点后两位，单倍奖金
                                                            arr.add(String.format("%.2f", Double.parseDouble(list01Str)
                                                                    * Double.parseDouble(list02Str)
                                                                    * Double.parseDouble(list03Str)
                                                                    * Double.parseDouble(list04Str)
                                                                    * Double.parseDouble(list05Str)
                                                                    * Double.parseDouble(list06Str)));
                                                        }
                                                    }
                                                }

                                            }

                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }
        } else if ("7".equals(followNum)) {
//最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size(); i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            for (int m3 = k + 1; m3 < detailArr.size(); m3++) {
                                List<String> list03 = detailArr.get(m3);
                                for (int l3 = 0; l3 < list03.size(); l3++) {
                                    String list03Str = list03.get(l3);
                                    for (int m4 = m3 + 1; m4 < detailArr.size(); m4++) {
                                        List<String> list04 = detailArr.get(m4);
                                        for (int l4 = 0; l4 < list04.size(); l4++) {
                                            String list04Str = list04.get(l4);
                                            for (int m5 = m4 + 1; m5 < detailArr.size(); m5++) {
                                                List<String> list05 = detailArr.get(m5);
                                                for (int l5 = 0; l5 < list05.size(); l5++) {
                                                    String list05Str = list05.get(l5);
                                                    for (int m6 = m5 + 1; m6 < detailArr.size(); m6++) {
                                                        List<String> list06 = detailArr.get(m6);
                                                        for (int l6 = 0; l6 < list06.size(); l6++) {
                                                            String list06Str = list06.get(l6);
                                                            for (int m7 = m6 + 1; m7 < detailArr.size(); m7++) {
                                                                List<String> list07 = detailArr.get(m7);
                                                                for (int l7 = 0; l7 < list07.size(); l7++) {
                                                                    String list07Str = list07.get(l7);
                                                                    //保留小数点后两位，单倍奖金
                                                                    arr.add(String.format("%.2f", Double.parseDouble(list01Str)
                                                                            * Double.parseDouble(list02Str)
                                                                            * Double.parseDouble(list03Str)
                                                                            * Double.parseDouble(list04Str)
                                                                            * Double.parseDouble(list05Str)
                                                                            * Double.parseDouble(list06Str)
                                                                            * Double.parseDouble(list07Str)));
                                                                }
                                                            }

                                                        }
                                                    }
                                                }

                                            }

                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }
        } else if ("8".equals(followNum)) {
//最外层循环，直到倒数第二个
            for (int i = 0; i < detailArr.size(); i++) {
                List<String> list01 = detailArr.get(i);
                //循环数组中的数组的第一个
                for (int j = 0; j < list01.size(); j++) {
                    String list01Str = list01.get(j);
                    //再循环最外层，从第二个开始
                    for (int k = i + 1; k < detailArr.size(); k++) {
                        List<String> list02 = detailArr.get(k);
                        //循环数组中的数组的第二个
                        for (int l = 0; l < list02.size(); l++) {
                            String list02Str = list02.get(l);
                            for (int m3 = k + 1; m3 < detailArr.size(); m3++) {
                                List<String> list03 = detailArr.get(m3);
                                for (int l3 = 0; l3 < list03.size(); l3++) {
                                    String list03Str = list03.get(l3);
                                    for (int m4 = m3 + 1; m4 < detailArr.size(); m4++) {
                                        List<String> list04 = detailArr.get(m4);
                                        for (int l4 = 0; l4 < list04.size(); l4++) {
                                            String list04Str = list04.get(l4);
                                            for (int m5 = m4 + 1; m5 < detailArr.size(); m5++) {
                                                List<String> list05 = detailArr.get(m5);
                                                for (int l5 = 0; l5 < list05.size(); l5++) {
                                                    String list05Str = list05.get(l5);
                                                    for (int m6 = m5 + 1; m6 < detailArr.size(); m6++) {
                                                        List<String> list06 = detailArr.get(m6);
                                                        for (int l6 = 0; l6 < list06.size(); l6++) {
                                                            String list06Str = list06.get(l6);
                                                            for (int m7 = m6 + 1; m7 < detailArr.size(); m7++) {
                                                                List<String> list07 = detailArr.get(m7);
                                                                for (int l7 = 0; l7 < list07.size(); l7++) {
                                                                    String list07Str = list07.get(l7);
                                                                    for (int m8 = m7 + 1; m8 < detailArr.size(); m8++) {
                                                                        List<String> list08 = detailArr.get(m8);
                                                                        for (int l8 = 0; l8 < list08.size(); l8++) {
                                                                            String list08Str = list08.get(l8);
                                                                            //保留小数点后两位，单倍奖金
                                                                            arr.add(String.format("%.2f", Double.parseDouble(list01Str)
                                                                                    * Double.parseDouble(list02Str)
                                                                                    * Double.parseDouble(list03Str)
                                                                                    * Double.parseDouble(list04Str)
                                                                                    * Double.parseDouble(list05Str)
                                                                                    * Double.parseDouble(list06Str)
                                                                                    * Double.parseDouble(list07Str)
                                                                                    * Double.parseDouble(list08Str)));

                                                                        }
                                                                    }

                                                                }
                                                            }

                                                        }
                                                    }
                                                }

                                            }

                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }

        }

        return arr;
    }

//********************************************2018-05-22 yhw 开奖及计算奖金    end *****************************************************
}
