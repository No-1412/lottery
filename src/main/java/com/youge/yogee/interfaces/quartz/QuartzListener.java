package com.youge.yogee.interfaces.quartz;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.Calculations;
import com.youge.yogee.modules.cfootballawards.entity.CdFootballAwards;
import com.youge.yogee.modules.cfootballawards.service.CdFootballAwardsService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
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
@Component("taskJob")
public class QuartzListener {

    @Autowired
    private CdFootballFollowOrderService cdFootballFollowOrderService;
    @Autowired
    private CdFootballAwardsService cdFootballAwardsService;

    //    private static AppuserService appuserService = SpringContextHolder.getBean(AppuserService.class);
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
    public void job1() {
//        System.out.println("定时任务Demo");
//
//        List<CdFootballFollowOrder> cdFootballFollowOrderList = cdFootballFollowOrderService.findAll();
//
//
//        //全部可以比赛完的场次
//        List<String> awardMatchIdList = cdFootballAwardsService.getAllMatchId();
//
//        for (CdFootballFollowOrder cdFootballFollowOrder : cdFootballFollowOrderList) {
//            //排除已经算完奖金的
//            if (cdFootballFollowOrder.getAward().equals("0")) {
//
//                //获取订单中押的全部场次
//                String danMatchIds = cdFootballFollowOrder.getDanMatchIds();
//                Set<String> matchIdList = new HashSet<>();
//                for (String finishMatchId : danMatchIds.split(",")) {
//                    matchIdList.add(finishMatchId.substring(2, 7));
//                }
//
//                //判断订单所有赛事是否都已经比完
//                if (awardMatchIdList.containsAll(matchIdList)) {
//                    //押对的彩票
//                    List<String> winList = new ArrayList<>();
//
//
//                    //***********************************判断押中场次************************************************
//                    //判断比分
//                    String score = cdFootballFollowOrder.getScore();
//                    String[] scoreArray = score.split("//|");
//                    for (String aScore : scoreArray) {
//                        String[] aScoreArray = aScore.split("//+");
//                        CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aScoreArray[1]);
//                        String finishScore = cdFootballAwards.getHs() + ":" + cdFootballAwards.getVs();
//                        String[] odds = aScore.split(finishScore + "/");
//                        if (odds.length > 1) {
//                            if (odds[1].contains(",")) {
//                                winList.add(odds[1].split(",")[0]);
//                            } else {
//                                winList.add(odds[1]);
//                            }
//                        }
//                    }
//
//                    //判断总进球
//                    String goal = cdFootballFollowOrder.getGoal();
//                    String[] goalArray = goal.split("//|");
//                    for (String aGoal : goalArray) {
//                        String[] aGoalArray = aGoal.split("//+");
//                        CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aGoalArray[1]);
//                        String finishGoal = cdFootballAwards.getTotalNum();
//                        String[] odds = aGoal.split(finishGoal + "/");
//                        if (odds.length > 1) {
//                            if (odds[1].contains(",")) {
//                                winList.add(odds[1].split(",")[0]);
//                            } else {
//                                winList.add(odds[1]);
//                            }
//                        }
//                    }
//
//                    //判断半全场
//                    String half = cdFootballFollowOrder.getHalf();
//                    String[] halfArray = half.split("//|");
//                    for (String aHalf : halfArray) {
//                        String[] aHalfArray = aHalf.split("//+");
//                        CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aHalfArray[1]);
//                        String finishGoal = cdFootballAwards.getWinGrap();
//                        String[] odds = aHalf.split(finishGoal + "/");
//                        if (odds.length > 1) {
//                            if (odds[1].contains(",")) {
//                                winList.add(odds[1].split(",")[0]);
//                            } else {
//                                winList.add(odds[1]);
//                            }
//                        }
//                    }
//
//                    //判断胜负平
//                    String beat = cdFootballFollowOrder.getBeat();
//                    String[] beatArray = beat.split("//|");
//                    for (String aBeat : beatArray) {
//                        String[] aBeatArray = aBeat.split("//+");
//                        CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aBeatArray[1]);
//                        String finishGoal = cdFootballAwards.getWinning();
//                        String[] odds = aBeat.split(finishGoal + "/");
//                        if (odds.length > 1) {
//                            if (odds[1].contains(",")) {
//                                winList.add(odds[1].split(",")[0]);
//                            } else {
//                                winList.add(odds[1]);
//                            }
//                        }
//                    }
//
//                    //判断让球胜负平
//                    String let = cdFootballFollowOrder.getLet();
//                    String[] letArray = let.split("//|");
//                    for (String aLet : letArray) {
//                        String[] aLetArray = aLet.split("//+");
//                        CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aLetArray[1]);
//                        String finishGoal = cdFootballAwards.getSpread();
//                        String[] odds = aLet.split(finishGoal + "/");
//                        if (odds.length > 1) {
//                            if (odds[1].contains(",")) {
//                                winList.add(odds[1].split(",")[0]);
//                            } else {
//                                winList.add(odds[1]);
//                            }
//                        }
//                    }
//
//                    //***************************************判断结束*******************************************************
//
//
//                    if (danMatchIds.contains("胆")) {
//                        //得到所有胆场
//                        List<String> danList = new ArrayList<>();
//                        String[] matchIds = danMatchIds.split(",");
//                        for (String matchId : matchIds) {
//                            if (matchId.contains("胆")) {
//                                danList.add(matchId.substring(2, 7));
//                            }
//                        }
//
//                        for (String danMatchId : danList) {
//
//                        }
//
//
//
//                    } else {
//                        //有中奖彩票
//                        if (winList.size() > 0) {
//                            double ood = 0;
//                            //根据串关计算奖金
//                            String followNum = cdFootballFollowOrder.getFollowNum();
//                            if (followNum.contains(",")) {
//                                for (String num : followNum.split(",")) {
//                                    //TODO 需要用BigDecimal
//                                    ood = Calculations.odds(Integer.parseInt(num), winList);
//                                }
//                            } else {
//                                ood = Calculations.odds(Integer.parseInt(followNum), winList);
//                            }
//                        }
//                    }
//
//
//                }
//
//
//            }
//        }
    }

}
