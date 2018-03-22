package com.youge.yogee.interfaces.lottery;

import java.util.ArrayList;
import java.util.List;

//
//import com.youge.yogee.interfaces.util.Calculations;
//import com.youge.yogee.modules.cfootballawards.entity.CdFootballAwards;
//import com.youge.yogee.modules.cfootballawards.service.CdFootballAwardsService;
//import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
//import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.*;
//
///**
// * Created by liyuan on 2018/3/2.
// */
//@Controller
public class TestDemo {

    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        for (String s : list) {
            System.out.println("加强的循环" + s);
        }

        for(int j=0;j<1;j++){
            String ss=list.get(j);
            System.out.println("简单循环" + ss);
        }
    }

//
//    private static final Logger logger = LoggerFactory.getLogger(UserLoginInterface.class);
//
//    @Autowired
//    private CdFootballFollowOrderService cdFootballFollowOrderService;
//    @Autowired
//    private CdFootballAwardsService cdFootballAwardsService;
//
//
//    @RequestMapping(value = "test/demo", method = RequestMethod.POST)
//    @ResponseBody
//    public void job1() {
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
//            if (cdFootballFollowOrder.getStauts().equals("2")) {
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
//                    //押对的彩票(用于带胆的彩票)
//                    List<String> danWinList = new ArrayList<>();
//
//                    //***********************************判断押中场次************************************************
//                    //判断比分
//                    String score = cdFootballFollowOrder.getScore();
//                    judge(score, "score", winList, danWinList);
//
//                    //判断总进球
//                    String goal = cdFootballFollowOrder.getGoal();
//                    judge(goal, "goal", winList, danWinList);
//
//                    //判断半全场
//                    String half = cdFootballFollowOrder.getHalf();
//                    judge(half, "half", winList, danWinList);
//
//                    //判断胜负平
//                    String beat = cdFootballFollowOrder.getBeat();
//                    judge(beat, "beat", winList, danWinList);
//
//                    //判断让球胜负平
//                    String let = cdFootballFollowOrder.getLet();
//                    judge(let, "let", winList, danWinList);
//
//
//                    //***************************************判断结束*******************************************************
//
//                    //有中奖彩票
//                    if (winList.size() > 0) {
//                        double oodSum = 0;
//                        if (danMatchIds.contains("胆")) {
//                            //得到所有胆场
//                            List<String> danList = new ArrayList<>();
//                            String[] matchIds = danMatchIds.split(",");
//                            for (String matchId : matchIds) {
//                                if (matchId.contains("胆")) {
//                                    danList.add(matchId.substring(2, 7));
//                                }
//                            }
//
//                            List<List<Double>> dList = new ArrayList<>();
//
//                            for (String danMatchId : danList) {
//                                List<Double> oobList = new ArrayList<>();
//                                for (String danWin : danWinList) {
//                                    if (danWin.contains(danMatchId)) {
//                                        oobList.add(Double.parseDouble(danWin.split(danMatchId)[1]));
//                                        danWinList.remove(danWin);
//                                    }
//                                }
//                                dList.add(oobList);
//                            }
//
//                            //*******************************判断是否可以开奖****************************
//                            Boolean isWin = true;
//
//                            for (List<Double> oobList : dList) {
//                                if (oobList.size() == 0) {
//                                    isWin = false;
//                                    break;
//                                }
//                            }
//
//                            if (isWin) {
//                                String followNum = cdFootballFollowOrder.getFollowNum();
//                                if (followNum.contains(",")) {
//                                    for (String num : followNum.split(",")) {
//                                        Integer numInt = Integer.valueOf(num);
//                                        if (danWinList.size() < numInt - danList.size()) {
//                                            isWin = false;
//                                        }
//                                    }
//                                } else {
//                                    Integer numInt = Integer.valueOf(followNum);
//                                    if (danWinList.size() < numInt - danList.size()) {
//                                        isWin = false;
//                                    }
//                                }
//                            }
//
//                            //*******************************判断结束****************************
//
//                            if (isWin) {
//                                //计算各个胆场之间赔率相乘
//                                List<Double> count = new ArrayList<>();
//                                recursion(0, dList, dList.size() - 1, 0.0, count);
//
//                                //计算非胆场之间赔率相乘
//                                List<String> danOddWinList = new ArrayList<>();
//                                for (String oddWin : danOddWinList) {
//                                    danOddWinList.add(oddWin.substring(5));
//                                }
//
//                                //计算完整胜利赔率
//                                String followNum = cdFootballFollowOrder.getFollowNum();
//                                if (followNum.contains(",")) {
//                                    for (String num : followNum.split(",")) {
//                                        double ood = 0;
//                                        //TODO 需要用BigDecimal
//                                        List<Double> doubleList = Calculations.oddsCollection(Integer.parseInt(num) - danList.size(), danOddWinList);
//                                        for (Double dan : count) {
//                                            for (Double feiDan : doubleList) {
//                                                ood += dan * feiDan;
//                                            }
//                                        }
//                                        oodSum += ood;
//                                    }
//                                } else {
//                                    List<Double> doubleList = Calculations.oddsCollection(Integer.parseInt(followNum) - danList.size(), danOddWinList);
//                                    for (Double dan : count) {
//                                        for (Double feiDan : doubleList) {
//                                            oodSum += dan * feiDan;
//                                        }
//                                    }
//                                }
//                            }else {
//                                cdFootballFollowOrder.setStauts("4");
//                            }
//                        } else {
//                            //根据串关计算奖金
//                            String followNum = cdFootballFollowOrder.getFollowNum();
//                            if (followNum.contains(",")) {
//                                for (String num : followNum.split(",")) {
//                                    //TODO 需要用BigDecimal
//                                    oodSum += Calculations.odds(Integer.parseInt(num), winList);
//                                }
//                            } else {
//                                oodSum = Calculations.odds(Integer.parseInt(followNum), winList);
//                            }
//                        }
//
//                        //TODO oodSum所有中奖赔率
//                        Double award = Integer.valueOf(cdFootballFollowOrder.getTimes()) * oodSum;
//                        cdFootballFollowOrder.setAward(award.toString());
//                        cdFootballFollowOrder.setStauts("3");
//                        cdFootballFollowOrderService.save(cdFootballFollowOrder);
//
//                    }else {
//                        cdFootballFollowOrder.setStauts("4");
//                    }
//                }
//            }
//        }
//    }
//
//
//    private void judge(String method, String key, List<String> winList, List<String> danWinList) {
//        String[] methodArray = method.split("\\|");
//        for (String aMethod : methodArray) {
//            String[] aMethodArray = aMethod.split("\\+");
//            CdFootballAwards cdFootballAwards = cdFootballAwardsService.findByMatchId(aMethodArray[1]);
//            String finish = "";
//            switch (key) {
//                case "score":
//                    finish = cdFootballAwards.getHs() + ":" + cdFootballAwards.getVs();
//                    break;
//                case "goal":
//                    finish = cdFootballAwards.getTotalNum();
//                    break;
//                case "half":
//                    finish = cdFootballAwards.getWinGrap();
//                    break;
//                case "beat":
//                    finish = cdFootballAwards.getWinning();
//                    break;
//                case "let":
//                    finish = cdFootballAwards.getSpread();
//                    break;
//            }
//            String[] odds = aMethod.split(finish + "/");
//            if (odds.length > 1) {
//                if (odds[1].contains(",")) {
//                    winList.add(odds[1].split(",")[0]);
//                    danWinList.add(aMethodArray[1] + odds[1].split(",")[0]);
//                } else {
//                    winList.add(odds[1]);
//                    danWinList.add(aMethodArray[1] + odds[1]);
//                }
//            }
//        }
//    }
//
//
//    private void recursion(int index, List<List<Double>> doubleList, int end, double sum, List<Double> count) {
//
//        if (index <= end) {
//            List<Double> doubleArray = doubleList.get(index);
//            index += 1;
//            for (Double d : doubleArray) {
//                double sumCopy = sum;
//                sum *= d;
//                if (index > end) {
//                    count.add(sum);
//                }
//                recursion(index, doubleList, end, sum, count);
//                sum = sumCopy;
//            }
//        }
//    }
}

