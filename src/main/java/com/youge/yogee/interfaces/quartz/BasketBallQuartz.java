package com.youge.yogee.interfaces.quartz;

import com.youge.yogee.interfaces.util.Calculations;
import com.youge.yogee.modules.cbasketballawards.entity.CdBasketballAwards;
import com.youge.yogee.modules.cbasketballawards.service.CdBasketballAwardsService;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballSingleOrderService;
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

    @Scheduled(cron = "0 0 */2 * * ?")//2小时
    public void basketBallFollowOrder() {


        System.out.println("篮球串关开奖");
        List<CdBasketballFollowOrder> cdBasketballFollowOrderList = cdBasketballFollowOrderService.findStatus();

        //全部可以比赛完的场次
        List<String> awardMatchIdList = cdBasketballAwardsService.getAllMatchId();

        for (CdBasketballFollowOrder cdBasketballFollowOrder : cdBasketballFollowOrderList) {
            //获取订单中押的全部场次
            String danMatchIds = cdBasketballFollowOrder.getDanMatchIds();
            Set<String> matchIdList = new HashSet<>();
            for (String finishMatchId : danMatchIds.split(",")) {
                matchIdList.add(finishMatchId.substring(2, 7));
            }

            //判断订单所有赛事是否都已经比完
            if (awardMatchIdList.containsAll(matchIdList)) {
                //押对的彩票
                List<String> winList = new ArrayList<>();
                //押对的彩票(用于带胆的彩票)
                List<String> danWinList = new ArrayList<>();

                //***********************************判断押中场次************************************************
                //判断主胜
                String hostWin = cdBasketballFollowOrder.getHostWin();
                judgeBaskerballFollow(hostWin, "hostWin", winList, danWinList, cdBasketballFollowOrder);

                //判断主负
                String hostFail = cdBasketballFollowOrder.getHostFail();
                judgeBaskerballFollow(hostFail, "hostFail", winList, danWinList, cdBasketballFollowOrder);

                //判断胜负
                String beat = cdBasketballFollowOrder.getBeat();
                judgeBaskerballFollow(beat, "beat", winList, danWinList, cdBasketballFollowOrder);

                //判断大小分
                String size = cdBasketballFollowOrder.getSize();
                judgeBaskerballFollow(size, "size", winList, danWinList, cdBasketballFollowOrder);

                //判断让分胜负平
                String let = cdBasketballFollowOrder.getLet();
                judgeBaskerballFollow(size, "size", winList, danWinList, cdBasketballFollowOrder);

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

                        List<List<Double>> dList = new ArrayList<>();

                        for (String danMatchId : danList) {
                            List<Double> oobList = new ArrayList<>();
                            for (String danWin : danWinList) {
                                if (danWin.contains(danMatchId)) {
                                    oobList.add(Double.parseDouble(danWin.split(danMatchId)[1]));
                                    danWinList.remove(danWin);
                                }
                            }
                            dList.add(oobList);
                        }

                        //*******************************判断是否可以开奖****************************
                        Boolean isWin = true;

                        for (List<Double> oobList : dList) {
                            if (oobList.size() == 0) {
                                isWin = false;
                                break;
                            }
                        }

                        if (isWin) {
                            String followNum = cdBasketballFollowOrder.getFollowNums();
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
                            for (String oddWin : danOddWinList) {
                                danOddWinList.add(oddWin.substring(5));
                            }

                            //计算完整胜利赔率
                            String followNum = cdBasketballFollowOrder.getFollowNums();
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
                            cdBasketballFollowOrder.setStatus("4");
                        }
                    } else {
                        //根据串关计算奖金
                        String followNum = cdBasketballFollowOrder.getFollowNums();
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
                    Double award = Integer.valueOf(cdBasketballFollowOrder.getTimes()) * oodSum * 2;
                    cdBasketballFollowOrder.setAward(award.toString());
                    cdBasketballFollowOrder.setStatus("4");
                    cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
                    //保存中奖纪录
                    CdOrderWinners cdOrderWinners = new CdOrderWinners();
                    cdOrderWinners.setWinOrderNum(cdBasketballFollowOrder.getOrderNum());//中间单号
                    cdOrderWinners.setWinPrice(award.toString());//中奖金额
                    cdOrderWinners.setUid(cdBasketballFollowOrder.getUid());//中间用户
                    String repayPercent = Calculations.getRepayPercent(award, Double.parseDouble(cdBasketballFollowOrder.getPrice()));
                    cdOrderWinners.setRepayPercent(repayPercent);
                    cdOrderWinners.setType("4");
                    cdOrderWinnersService.save(cdOrderWinners);

                } else {
                    cdBasketballFollowOrder.setStatus("5");
                }
            }
        }

    }


    @Scheduled(cron = "0 0 */2 * * ?")//2小时
    public void footballSingleOrder() {
        System.out.println("篮球单关开奖");

        List<CdBasketballSingleOrder> cdBasketballSingleOrderList = cdBasketballSingleOrderService.findStatus();


        //全部可以比赛完的场次
        List<String> awardMatchIdList = cdBasketballAwardsService.getAllMatchId();

        for (CdBasketballSingleOrder cdBasketballSingleOrder : cdBasketballSingleOrderList) {

            //获取订单中押的全部场次
            String matchIds = cdBasketballSingleOrder.getMatchIds();
            Set<String> matchIdList = new HashSet<>();
            for (String finishMatchId : matchIds.split(",")) {
                matchIdList.add(finishMatchId.substring(2, 7));
            }
            //判断订单所有赛事是否都已经比完
            if (awardMatchIdList.containsAll(matchIdList)) {
                //***********************************判断押中场次************************************************

                //判断主胜
                String hostWin = cdBasketballSingleOrder.getHostWin();
                double hostWinOdds = judgeBasketballSingle(hostWin, "hostWin");

                //判断主负
                String hostFail = cdBasketballSingleOrder.getHostFail();
                double hostFailOdds = judgeBasketballSingle(hostFail, "hostFail");

                //***************************************判断结束*******************************************************


                if (hostWinOdds != -1 && hostFailOdds != -1) {
                    double oddsSum = hostWinOdds + hostFailOdds;
                    if (oddsSum > 0) {
                        Double award = 2 * oddsSum;
                        cdBasketballSingleOrder.setAward(award.toString());
                        cdBasketballSingleOrder.setStatus("4");
                        cdBasketballSingleOrderService.save(cdBasketballSingleOrder);
                        //保存中奖纪录
                        CdOrderWinners cdOrderWinners = new CdOrderWinners();
                        cdOrderWinners.setWinOrderNum(cdBasketballSingleOrder.getOrderNum());//中间单号
                        cdOrderWinners.setWinPrice(award.toString());//中奖金额
                        cdOrderWinners.setUid(cdBasketballSingleOrder.getUid());//中间用户
                        String repayPercent = Calculations.getRepayPercent(award, Double.parseDouble(cdBasketballSingleOrder.getPrice()));
                        cdOrderWinners.setRepayPercent(repayPercent);
                        cdOrderWinners.setType("3");
                        cdOrderWinnersService.save(cdOrderWinners);
                    } else {
                        cdBasketballSingleOrder.setStatus("5");
                        cdBasketballSingleOrderService.save(cdBasketballSingleOrder);
                    }
                }
            }
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
                        finish = finish.substring(0, 2);
                    } else {
                        return ood;
                    }
                    break;
                case "hostFail":
                    finish = cdBasketballAwards.getWinGrap();
                    if (finish.contains("主负")) {
                        finish = finish.substring(0, 2);
                    } else {
                        return ood;
                    }
                    break;
            }
            String[] odds = aMethod.split(finish + "/");
            if (odds.length > 1) {
                String[] a = odds[1].split(",")[0].split("/");
                ood += Double.parseDouble(a[0]) * Double.parseDouble(a[1]);
                ood += Double.parseDouble(odds[1].split(",")[0]);
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
            switch (key) {
                case "hostWin":
                    finish = cdBasketballAwards.getWinGrap();
                    if (finish.contains("主胜")) {
                        finish = finish.substring(0, 2);
                    } else {
                        return;
                    }
                    break;
                case "hostFail":
                    finish = cdBasketballAwards.getWinGrap();
                    if (finish.contains("主负")) {
                        finish = finish.substring(0, 2);
                    } else {
                        return;
                    }
                    break;
                case "beat":
                    finish = cdBasketballAwards.getWinning();
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


}
