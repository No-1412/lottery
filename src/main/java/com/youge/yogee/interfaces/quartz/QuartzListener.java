package com.youge.yogee.interfaces.quartz;

import com.youge.yogee.interfaces.lottery.util.WinPriceUtil;
import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.interfaces.util.Calculations;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
//    @Scheduled(cron = "*/5 * * * * ?")
//    @Scheduled(cron = "0 0 * * * ?")//1小时
    @Scheduled(cron = "0 0 */1 * * ?")//2小时
    public void footballBestFollowOrder() {
        System.out.println("足球优化串关开奖");
        List<CdFootballFollowOrder> cdFootballFollowOrderList = cdFootballFollowOrderService.findStatusAndType("2");

        //全部可以比赛完的场次
        List<String> awardMatchIdList = cdFootballAwardsService.getAllMatchId();


        for (CdFootballFollowOrder cdFootballFollowOrder : cdFootballFollowOrderList) {

            //获取订单中押的全部场次
            String danMatchIds = cdFootballFollowOrder.getDanMatchIds();
            Set<String> matchIdList = new HashSet<>();
            for (String finishMatchId : danMatchIds.split(",")) {
                matchIdList.add(finishMatchId.substring(2, 7));
            }


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
                //**********************************************************************************

                List<CdFootballBestFollowOrder> CdFootballBestFollowOrderList = cdFootballBestFollowOrderService.findByNum(cdFootballFollowOrder.getOrderNum());
                BigDecimal award = new BigDecimal(0);
                for (CdFootballBestFollowOrder cdFootballBestFollowOrder : CdFootballBestFollowOrderList) {
                    String orderDetail = cdFootballBestFollowOrder.getOrderDetail();
                    String[] detailArray = orderDetail.split("\\|");

                    Boolean flag = true;//如果最终为true则中奖
                    for (String detail : detailArray) {
                        String[] aDetail = detail.split("\\+");
                        String key = aDetail[1];
                        CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aDetail[0]);

                        String orderFinish = StringUtils.split(aDetail[2], "/")[0];

                        String finish = "";
                        switch (key) {
                            case "score":
                                finish = cdFootballAwards.getHs() + ":" + cdFootballAwards.getVs();
                                break;
                            case "goal":
                                finish = cdFootballAwards.getTotalNum();
                                break;
                            case "half":
                                finish = cdFootballAwards.getWinGrap();
                                break;
                            case "beat":
                                finish = cdFootballAwards.getWinning();
                                break;
                            case "let":
                                int hs = Integer.valueOf(cdFootballAwards.getHs());
                                int vs = Integer.valueOf(cdFootballAwards.getVs());
                                //取得出票时让球数
                                int letBall = Integer.valueOf(StringUtils.split(aDetail[3], "/")[1]);

                                if (hs + letBall > vs) {
                                    finish = "让主胜";
                                } else if (hs + letBall == vs) {
                                    finish = "平";
                                } else {
                                    finish = "让客胜";
                                }
                                break;
                        }
                        if (!orderFinish.equals(finish)) {
                            flag = false;
                        }
                    }

                    //如果最终为true则中奖,计算单场奖金
                    if (flag) {
                        BigDecimal pertimes = new BigDecimal(cdFootballBestFollowOrder.getPerTimes());
                        BigDecimal perAward = new BigDecimal(cdFootballBestFollowOrder.getPerAward());
                        award = award.add(pertimes.multiply(perAward));
                    }
                }

                //开奖结果为  未中奖
                if (award.doubleValue() == 0) {
                    cdFootballFollowOrder.setStatus("5");
                    cdFootballFollowOrderService.save(cdFootballFollowOrder);
                    //改变订单总表状态
                    CdOrder co = cdOrderService.getOrderByOrderNum(cdFootballFollowOrder.getOrderNum());
                    if (co != null) {
                        co.setWinPrice("0");//奖金
                        co.setStatus("2");//未中奖
                        cdOrderService.save(co);
                    }
                } else {//开奖结果为  中奖
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

                    //------------------------原开奖逻辑----180517弃用-----------------------------------------
                    //计算跟单佣金 step3
                    //保存中奖纪录 step4
                    //更新用户余额 step5
                    //推送         step6
                    //------------------------------------------------------------------------------------------
                }
            }
        }
    }

    //    "0/10 * * * * ?"
    //@Scheduled(cron = "0/10 * * * * ?")//每10秒触发
    @Scheduled(cron = "0 0 */1 * * ?")//2小时
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
                //**********************************************************************************

                //押对的彩票
                List<String> winList = new ArrayList<>();
                //押对的彩票(用于带胆的彩票)
                List<String> danWinList = new ArrayList<>();

                //***********************************判断押中场次************************************************
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


                //***************************************判断结束*******************************************************

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

                        //*******************************判断是否可以开奖****************************
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

                        //*******************************判断结束****************************

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
                                co.setStatus("2");//中奖
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
                                    cdFootballFollowOrder.setStatus("5");
                                    cdFootballFollowOrderService.save(cdFootballFollowOrder);
                                    //改变订单总表状态
                                    CdOrder co = cdOrderService.getOrderByOrderNum(cdFootballFollowOrder.getOrderNum());
                                    if (co != null) {
                                        co.setWinPrice("0");//奖金
                                        co.setStatus("2");//中奖
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
    }


    @Scheduled(cron = "0 0 */1 * * ?")//2小时
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

}
