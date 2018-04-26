package com.youge.yogee.interfaces.quartz;

import com.youge.yogee.common.push.AppPush;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.lottery.util.SelOrderUtil;
import com.youge.yogee.interfaces.util.Calculations;
import com.youge.yogee.modules.cbasketballawards.entity.CdBasketballAwards;
import com.youge.yogee.modules.cbasketballawards.service.CdBasketballAwardsService;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballBestFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballBestFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballSingleOrderService;
import com.youge.yogee.modules.cfootballawards.entity.CdFootballAwards;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballBestFollowOrder;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.cmagicorder.entity.CdMagicFollowOrder;
import com.youge.yogee.modules.cmagicorder.entity.CdMagicOrder;
import com.youge.yogee.modules.cmagicorder.service.CdMagicFollowOrderService;
import com.youge.yogee.modules.cmagicorder.service.CdMagicOrderService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.entity.CdOrderFollowTimes;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
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

    @Scheduled(cron = "0/10 * * * * ?")//10s
//    @Scheduled(cron = "0 0 */1 * * ?")//2小时
    public void basketBallFollowOrder() {


//        System.out.println("篮球串关开奖");
        List<CdBasketballFollowOrder> cdBasketballFollowOrderList = cdBasketballFollowOrderService.findStatusAndType("1");

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

                //  **************************后加的-------------获取押注比赛结果并保存****************
                String result = getResultStr(matchIdList);
                cdBasketballFollowOrder.setResult(result);
                cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
                //**********************************************************************************
                //押对的彩票
                List<String> winList = new ArrayList<>();
                //押对的彩票(用于带胆的彩票)
                List<String> danWinList = new ArrayList<>();

                //***********************************判断押中场次************************************************
                //判断主胜
                String hostWin = cdBasketballFollowOrder.getHostWin();
                if (StringUtils.isNotEmpty(hostWin)) {
                    judgeBaskerballFollow(hostWin, "hostWin", winList, danWinList, cdBasketballFollowOrder);
                }
                //判断主负
                String hostFail = cdBasketballFollowOrder.getHostFail();
                if (StringUtils.isNotEmpty(hostFail)) {
                    judgeBaskerballFollow(hostFail, "hostFail", winList, danWinList, cdBasketballFollowOrder);
                }

                //判断胜负
                String beat = cdBasketballFollowOrder.getBeat();
                if (StringUtils.isNotEmpty(beat)) {
                    judgeBaskerballFollow(beat, "beat", winList, danWinList, cdBasketballFollowOrder);
                }

                //判断大小分
                String size = cdBasketballFollowOrder.getSize();
                if (StringUtils.isNotEmpty(size)) {
                    judgeBaskerballFollow(size, "size", winList, danWinList, cdBasketballFollowOrder);
                }

                //判断让球胜负平
                String let = cdBasketballFollowOrder.getLet();
                if (StringUtils.isNotEmpty(let)) {
                    //订单让球数
                    String[] letBalls = cdBasketballFollowOrder.getLetScore().split(",");
                    String[] methodArray = let.split("\\|");
                    for (int i = 0; i < methodArray.length; i++) {
                        String[] aMethodArray = methodArray[i].split("\\+");
                        CdBasketballAwards cdBasketballAwards = cdBasketballAwardsService.findByMatchId(aMethodArray[1]);
                        String finish = "";

                        int hs = Integer.valueOf(cdBasketballAwards.getHs());
                        int vs = Integer.valueOf(cdBasketballAwards.getVs());

                        Double letBall = Double.valueOf(letBalls[i]);

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
                            cdBasketballFollowOrder.setStatus("5");
                            cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
                            //改变订单总表状态
                            CdOrder co = cdOrderService.getOrderByOrderNum(cdBasketballFollowOrder.getOrderNum());
                            if (co != null) {
                                co.setWinPrice("0");//奖金
                                co.setStatus("2");//中奖
                                cdOrderService.save(co);
                            }
                            continue;

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
                    cdBasketballFollowOrder.setAward(new BigDecimal(award).setScale(2, 2).toString());
                    cdBasketballFollowOrder.setStatus("4");
                    cdBasketballFollowOrderService.save(cdBasketballFollowOrder);

                    //---------------------------------计算跟单佣金-------------------------

                    if (cdBasketballFollowOrder.getType().equals("2")) {
                        CdOrderFollowTimes cdOrderFollowTimes = cdOrderFollowTimesService.get("1");

                        CdMagicFollowOrder cdMagicFollowOrder = cdMagicFollowOrderService.findOrderByNumber(cdBasketballFollowOrder.getOrderNum());
                        CdMagicOrder cdMagicOrder = cdMagicOrderService.get(cdMagicFollowOrder.getMagicOrderId());

                        if (new BigDecimal(cdBasketballFollowOrder.getPrice())
                                .multiply(cdOrderFollowTimes.getTimes())
                                .compareTo(new BigDecimal(award)) == 1) {
                            //全部佣金
                            BigDecimal commission = new BigDecimal(cdMagicOrder.getCharges())
                                    .multiply(new BigDecimal(0.01))
                                    .multiply(new BigDecimal(award));

                            CdLotteryUser cdLotteryUser = cdLotteryUserService.get(cdMagicOrder.getUid());
                            cdLotteryUser.setBalance(cdLotteryUser.getBalance().add(commission.multiply(new BigDecimal(0.8))));
                            cdLotteryUserService.save(cdLotteryUser);
                        }
                    }
                    //保存中奖纪录
                    CdOrderWinners cdOrderWinners = new CdOrderWinners();
                    cdOrderWinners.setWinOrderNum(cdBasketballFollowOrder.getOrderNum());//中间单号
                    cdOrderWinners.setWinPrice(award.toString());//中奖金额
                    cdOrderWinners.setUid(cdBasketballFollowOrder.getUid());//中间用户
                    String repayPercent = Calculations.getRepayPercent(award, Double.parseDouble(cdBasketballFollowOrder.getPrice()));
                    cdOrderWinners.setRepayPercent(repayPercent);
                    cdOrderWinners.setType("4");
                    cdOrderWinners.setWallType("1");
                    cdOrderWinners.setResult(cdBasketballFollowOrder.getResult());
                    cdOrderWinnersService.save(cdOrderWinners);
                    //改变订单总表状态
                    CdOrder co = cdOrderService.getOrderByOrderNum(cdBasketballFollowOrder.getOrderNum());
                    if (co != null) {
                        co.setWinPrice(award.toString());//奖金
                        co.setStatus("3");//中奖
                        cdOrderService.save(co);
                    }
                    AppPush.push(cdBasketballFollowOrder.getUid(), "凯旋彩票", "您购买的竞猜篮球获得中奖金额" + award + "元");

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
            //获取订单中押的全部场次
            String danMatchIds = cdBasketballFollowOrder.getDanMatchIds();
            Set<String> matchIdList = new HashSet<>();
            for (String finishMatchId : danMatchIds.split(",")) {
                matchIdList.add(finishMatchId.substring(2, 7));
            }

            //判断订单所有赛事是否都已经比完
            if (awardMatchIdList.containsAll(matchIdList)) {

                //  **************************后加的-------------获取押注比赛结果并保存****************
                String result = getResultStr(matchIdList);
                cdBasketballFollowOrder.setResult(result);
                cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
                //**********************************************************************************

                List<CdBasketballBestFollowOrder> basketballBestFollowOrderList = cdBasketballBestFollowOrderService.findByNum(cdBasketballFollowOrder.getOrderNum());
                BigDecimal award = new BigDecimal(0);
                for (CdBasketballBestFollowOrder cdBasketballBestFollowOrder : basketballBestFollowOrderList) {
                    String orderDetail = cdBasketballBestFollowOrder.getOrderDetail();
                    String[] detailArray = orderDetail.split("\\|");

                    Boolean flag = true;//如果最终为true则中奖
                    for (String detail : detailArray) {
                        String[] aDetail = detail.split("\\+");
                        String key = aDetail[1];
                        CdBasketballAwards cdBasketballAwards = cdBasketballAwardsService.findByMatchId(aDetail[0]);

                        String orderFinish = StringUtils.split(aDetail[2], "/")[0];
                        String finish = "";
                        switch (key) {
                            case "hostWin":
                                finish = cdBasketballAwards.getWinGrap();
                                break;
                            case "hostFail":
                                finish = cdBasketballAwards.getWinGrap();
                                break;
                            case "beat":
                                finish = cdBasketballAwards.getWinning();
                                break;
                            case "size":
                                //大小分分数
                                Double sizeCount = Double.parseDouble(StringUtils.split(aDetail[3], "/")[2]);
                                Double count = Double.parseDouble(cdBasketballAwards.getHs()) + Integer.valueOf(cdBasketballAwards.getVs());
                                if (count > sizeCount) {
                                    finish = "大分";
                                } else {
                                    finish = "小分";
                                }
                                break;
                            case "let":
                                //订单让球数
                                int hs = Integer.valueOf(cdBasketballAwards.getHs());
                                int vs = Integer.valueOf(cdBasketballAwards.getVs());

                                Double letBall = Double.parseDouble(StringUtils.split(aDetail[3], "/")[1]);

                                if (hs + letBall > vs) {
                                    finish = "让主胜";
                                } else {
                                    finish = "让主负";
                                }
                                break;
                        }
                        if (!orderFinish.equals(finish)) {
                            flag = false;
                        }
                    }

                    //如果最终为true则中奖,计算单场奖金
                    if (flag) {
                        BigDecimal pertimes = new BigDecimal(cdBasketballBestFollowOrder.getPerTimes());
                        BigDecimal perAward = new BigDecimal(cdBasketballBestFollowOrder.getPerAward());
                        award = award.add(pertimes.multiply(perAward));
                    }
                }

                //开奖结果为  未中奖
                if (award.doubleValue() == 0) {
                    cdBasketballFollowOrder.setStatus("5");
                    cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
                    //改变订单总表状态
                    CdOrder co = cdOrderService.getOrderByOrderNum(cdBasketballFollowOrder.getOrderNum());
                    if (co != null) {
                        co.setWinPrice("0");//奖金
                        co.setStatus("2");//未中奖
                        cdOrderService.save(co);
                    }
                } else {
                    //所有中奖赔率
                    cdBasketballFollowOrder.setAward(award.toString());
                    cdBasketballFollowOrder.setStatus("4");
                    cdBasketballFollowOrderService.save(cdBasketballFollowOrder);

                    //---------------------------------计算跟单佣金-------------------------

                    if (cdBasketballFollowOrder.getType().equals("2")) {
                        CdOrderFollowTimes cdOrderFollowTimes = cdOrderFollowTimesService.get("1");

                        CdMagicFollowOrder cdMagicFollowOrder = cdMagicFollowOrderService.findOrderByNumber(cdBasketballFollowOrder.getOrderNum());
                        CdMagicOrder cdMagicOrder = cdMagicOrderService.get(cdMagicFollowOrder.getMagicOrderId());

                        if (new BigDecimal(cdBasketballFollowOrder.getPrice())
                                .multiply(cdOrderFollowTimes.getTimes())
                                .compareTo(award) == 1) {
                            //全部佣金
                            BigDecimal commission = new BigDecimal(cdMagicOrder.getCharges())
                                    .multiply(new BigDecimal(0.01))
                                    .multiply(award);

                            CdLotteryUser cdLotteryUser = cdLotteryUserService.get(cdMagicOrder.getUid());
                            cdLotteryUser.setBalance(cdLotteryUser.getBalance().add(commission.multiply(new BigDecimal(0.8))));
                            cdLotteryUserService.save(cdLotteryUser);
                        }
                    }
                    //保存中奖纪录
                    CdOrderWinners cdOrderWinners = new CdOrderWinners();
                    cdOrderWinners.setWinOrderNum(cdBasketballFollowOrder.getOrderNum());//中间单号
                    cdOrderWinners.setWinPrice(award.toString());//中奖金额
                    cdOrderWinners.setUid(cdBasketballFollowOrder.getUid());//中间用户
                    String repayPercent = Calculations.getRepayPercent(award.doubleValue(), Double.parseDouble(cdBasketballFollowOrder.getPrice()));
                    cdOrderWinners.setRepayPercent(repayPercent);
                    cdOrderWinners.setType("4");
                    cdOrderWinners.setWallType("1");
                    cdOrderWinners.setResult(cdBasketballFollowOrder.getResult());
                    cdOrderWinnersService.save(cdOrderWinners);
                    //改变订单总表状态
                    CdOrder co = cdOrderService.getOrderByOrderNum(cdBasketballFollowOrder.getOrderNum());
                    if (co != null) {
                        co.setWinPrice(award.toString());//奖金
                        co.setStatus("3");//中奖
                        cdOrderService.save(co);
                    }
                    //更新用户余额
                    SelOrderUtil.addBalanceToUser(cdBasketballFollowOrder.getAward(), cdBasketballFollowOrder.getUid());
                    //中奖推送
                    AppPush.push(cdBasketballFollowOrder.getUid(), "凯旋彩票", "您购买的竞猜篮球获得中奖金额" + award + "元");
                }
            }
        }
    }

    //    @Scheduled(cron = "*/5 * * * * ?")
    @Scheduled(cron = "0/10 * * * * ?")//10s
//    @Scheduled(cron = "0 0 */1 * * ?")//2小时
    public void footballSingleOrder() {
//        System.out.println("篮球单关开奖");

        List<CdBasketballSingleOrder> cdBasketballSingleOrderList = cdBasketballSingleOrderService.findStatus();


        //全部可以比赛完的场次
        List<String> awardMatchIdList = cdBasketballAwardsService.getAllMatchId();

        for (CdBasketballSingleOrder cdBasketballSingleOrder : cdBasketballSingleOrderList) {

            //获取订单中押的全部场次
            String matchIds = cdBasketballSingleOrder.getMatchIds();
            Set<String> matchIdList = new HashSet<>(Arrays.asList(matchIds.split(",")));
            //判断订单所有赛事是否都已经比完
            if (awardMatchIdList.containsAll(matchIdList)) {

                //  **********************后加的-------------获取押注比赛结果并保存****************************
                String result = getResultStr(matchIdList);
                cdBasketballSingleOrder.setResult(result);
                cdBasketballSingleOrderService.save(cdBasketballSingleOrder);
                //******************************************************************************************

                //***********************************判断押中场次************************************************
                //判断主胜
                double hostWinOdds = 0;
                String hostWin = cdBasketballSingleOrder.getHostWin();
                if (StringUtils.isNotEmpty(hostWin)) {
                    hostWinOdds = judgeBasketballSingle(hostWin, "hostWin");
                }


                //判断主负
                double hostFailOdds = 0;
                String hostFail = cdBasketballSingleOrder.getHostFail();
                if (StringUtils.isNotEmpty(hostFail)) {
                    hostFailOdds = judgeBasketballSingle(hostFail, "hostFail");
                }


                //***************************************判断结束*******************************************************


                double oddsSum = hostWinOdds + hostFailOdds;
                if (oddsSum > 0) {
                    Double award = 2 * oddsSum;
                    cdBasketballSingleOrder.setAward(new BigDecimal(award).setScale(2, 2).toString());
                    cdBasketballSingleOrder.setStatus("4");
                    cdBasketballSingleOrderService.save(cdBasketballSingleOrder);

                    if (cdBasketballSingleOrder.getType().equals("2")) {
                        CdOrderFollowTimes cdOrderFollowTimes = cdOrderFollowTimesService.get("1");

                        CdMagicFollowOrder cdMagicFollowOrder = cdMagicFollowOrderService.findOrderByNumber(cdBasketballSingleOrder.getOrderNum());
                        CdMagicOrder cdMagicOrder = cdMagicOrderService.get(cdMagicFollowOrder.getMagicOrderId());

                        if (new BigDecimal(cdBasketballSingleOrder.getPrice())
                                .multiply(cdOrderFollowTimes.getTimes())
                                .compareTo(new BigDecimal(award)) == 1) {
                            //全部佣金
                            BigDecimal commission = new BigDecimal(cdMagicOrder.getCharges())
                                    .multiply(new BigDecimal(0.01))
                                    .multiply(new BigDecimal(award));

                            CdLotteryUser cdLotteryUser = cdLotteryUserService.get(cdMagicOrder.getUid());
                            cdLotteryUser.setBalance(cdLotteryUser.getBalance().add(commission.multiply(new BigDecimal(0.8))));
                            cdLotteryUserService.save(cdLotteryUser);
                        }
                    }
                    //保存中奖纪录
                    CdOrderWinners cdOrderWinners = new CdOrderWinners();
                    cdOrderWinners.setWinOrderNum(cdBasketballSingleOrder.getOrderNum());//中间单号
                    cdOrderWinners.setWinPrice(award.toString());//中奖金额
                    cdOrderWinners.setUid(cdBasketballSingleOrder.getUid());//中间用户
                    String repayPercent = Calculations.getRepayPercent(award, Double.parseDouble(cdBasketballSingleOrder.getPrice()));
                    cdOrderWinners.setRepayPercent(repayPercent);
                    cdOrderWinners.setType("3");
                    cdOrderWinners.setWallType("1");
                    cdOrderWinners.setResult(cdBasketballSingleOrder.getResult());
                    cdOrderWinnersService.save(cdOrderWinners);
                    //改变订单总表状态
                    CdOrder co = cdOrderService.getOrderByOrderNum(cdBasketballSingleOrder.getOrderNum());
                    if (co != null) {
                        co.setWinPrice(award.toString());//奖金
                        co.setStatus("3");//中奖
                        cdOrderService.save(co);
                    }
                    //更新用户余额
                    SelOrderUtil.addBalanceToUser(cdBasketballSingleOrder.getAward(), cdBasketballSingleOrder.getUid());
                    //推送
                    AppPush.push(cdBasketballSingleOrder.getUid(), "凯旋彩票", "您购买的竞猜篮球获得中奖金额" + award + "元");
                } else {
                    cdBasketballSingleOrder.setStatus("5");
                    cdBasketballSingleOrderService.save(cdBasketballSingleOrder);
                    //改变订单总表状态
                    CdOrder co = cdOrderService.getOrderByOrderNum(cdBasketballSingleOrder.getOrderNum());
                    if (co != null) {
                        co.setWinPrice("0");//奖金
                        co.setStatus("2");//开奖
                        cdOrderService.save(co);
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
                    if(finish.equals("主胜")){
                        finish = "1";
                    }else {
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

    public String getResultStr(Set<String> matchIdList) {
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
