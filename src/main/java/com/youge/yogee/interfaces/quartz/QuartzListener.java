package com.youge.yogee.interfaces.quartz;

import com.youge.yogee.interfaces.util.Calculations;
import com.youge.yogee.modules.cfootballawards.entity.CdFootballAwards;
import com.youge.yogee.modules.cfootballawards.service.CdFootballAwardsService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
import com.youge.yogee.modules.corder.service.CdOrderWinnersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    @Scheduled(cron = "0 0 */2 * * ?")//2小时
    public void footballFollowOrder() {
        System.out.println("足球串关开奖");

        List<CdFootballFollowOrder> cdFootballFollowOrderList = cdFootballFollowOrderService.findStatus();


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
                String result = getResultStr(matchIdList);
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
                judgeFootballFollow(score, "score", winList, danWinList);

                //判断总进球
                String goal = cdFootballFollowOrder.getGoal();
                judgeFootballFollow(goal, "goal", winList, danWinList);

                //判断半全场
                String half = cdFootballFollowOrder.getHalf();
                judgeFootballFollow(half, "half", winList, danWinList);

                //判断胜负平
                String beat = cdFootballFollowOrder.getBeat();
                judgeFootballFollow(beat, "beat", winList, danWinList);

                //判断让球胜负平
                String let = cdFootballFollowOrder.getLet();
                //订单让球数
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
                            continue;
                        }
                    } else {
                        //根据串关计算奖金
                        String followNum = cdFootballFollowOrder.getFollowNum();
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
                    cdFootballFollowOrder.setAward(award.toString());
                    cdFootballFollowOrder.setStatus("4");
                    cdFootballFollowOrderService.save(cdFootballFollowOrder);
                    //保存中奖纪录
                    CdOrderWinners cdOrderWinners = new CdOrderWinners();
                    cdOrderWinners.setWinOrderNum(cdFootballFollowOrder.getOrderNum());//中间单号
                    cdOrderWinners.setWinPrice(award.toString());//中奖金额
                    cdOrderWinners.setUid(cdFootballFollowOrder.getUid());//中间用户
                    String repayPercent = Calculations.getRepayPercent(award, Double.parseDouble(cdFootballFollowOrder.getPrice()));
                    cdOrderWinners.setRepayPercent(repayPercent);
                    cdOrderWinners.setType("2");
                    cdOrderWinners.setWallType("1");
                    cdOrderWinners.setResult(cdFootballFollowOrder.getResult());
                    cdOrderWinnersService.save(cdOrderWinners);
                } else {
                    cdFootballFollowOrder.setStatus("5");
                    cdFootballFollowOrderService.save(cdFootballFollowOrder);
                }
            }
        }
    }


    @Scheduled(cron = "0 0 */2 * * ?")//2小时
    public void footballSingleOrder() {
        System.out.println("足球单关开奖");

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
            //  **********************后加的-------------获取押注比赛结果并保存****************************
            String result = getResultStr(matchIdList);
            cdFootballSingleOrder.setResult(result);
            cdFootballSingleOrderService.save(cdFootballSingleOrder);
            //******************************************************************************************


            //***********************************判断押中场次************************************************
            //判断比分
            String score = cdFootballSingleOrder.getScore();
            double scoreOdds = judgeFootballSingle(score, "score");

            //判断总进球
            String goal = cdFootballSingleOrder.getGoal();
            double goalOdds = judgeFootballSingle(goal, "goal");

            //判断半全场
            String half = cdFootballSingleOrder.getHalf();
            double halfOdds = judgeFootballSingle(half, "half");

            //判断胜负平
            String beat = cdFootballSingleOrder.getBeat();
            double beatOdds = judgeFootballSingle(beat, "beat");

            //判断让球胜负平
            String let = cdFootballSingleOrder.getLet();
            double letOdds = judgeFootballSingle(let, "let");


            //***************************************判断结束*******************************************************

            if (scoreOdds != -1 && goalOdds != -1 && halfOdds != -1 && beatOdds != -1 && letOdds != -1) {
                double oddsSum = scoreOdds + goalOdds + halfOdds + beatOdds + letOdds;
                if (oddsSum > 0) {
                    Double award = 2 * oddsSum;
                    cdFootballSingleOrder.setAward(award.toString());
                    cdFootballSingleOrder.setStatus("4");
                    cdFootballSingleOrderService.save(cdFootballSingleOrder);
                    //保存中奖纪录
                    CdOrderWinners cdOrderWinners = new CdOrderWinners();
                    cdOrderWinners.setWinOrderNum(cdFootballSingleOrder.getOrderNum());//中间单号
                    cdOrderWinners.setWinPrice(award.toString());//中奖金额
                    cdOrderWinners.setUid(cdFootballSingleOrder.getUid());//中间用户
                    String repayPercent = Calculations.getRepayPercent(award, Double.parseDouble(cdFootballSingleOrder.getPrice()));
                    cdOrderWinners.setRepayPercent(repayPercent);
                    cdOrderWinners.setType("1");
                    cdOrderWinners.setWallType("1");
                    cdOrderWinners.setResult(cdFootballSingleOrder.getResult());
                    cdOrderWinnersService.save(cdOrderWinners);

                } else {
                    cdFootballSingleOrder.setStatus("5");
                    cdFootballSingleOrderService.save(cdFootballSingleOrder);
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
            if (cdFootballAwards == null) {
                return -1;
            }
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
                    finish = cdFootballAwards.getSpread();
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
                case "half":
                    finish = cdFootballAwards.getWinGrap();
                    break;
                case "beat":
                    finish = cdFootballAwards.getWinning();
                    break;
                case "let":
                    finish = cdFootballAwards.getSpread();
                    break;
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

    public String getResultStr(Set<String> matchIdList) {
        String result = "";
        if (matchIdList.size() > 0) {
            for (String str : matchIdList) {
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
