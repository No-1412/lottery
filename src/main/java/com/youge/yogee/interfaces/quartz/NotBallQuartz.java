package com.youge.yogee.interfaces.quartz;

import com.youge.yogee.common.push.AppPush;
import com.youge.yogee.interfaces.util.Calculations;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveAwards;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveOrder;
import com.youge.yogee.modules.cfiveawards.service.CdFiveAwardsService;
import com.youge.yogee.modules.cfiveawards.service.CdFiveOrderService;
import com.youge.yogee.modules.clottoreward.entity.CdLottoOrder;
import com.youge.yogee.modules.clottoreward.entity.CdLottoReward;
import com.youge.yogee.modules.clottoreward.service.CdLottoOrderService;
import com.youge.yogee.modules.clottoreward.service.CdLottoRewardService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
import com.youge.yogee.modules.corder.service.CdOrderService;
import com.youge.yogee.modules.corder.service.CdOrderWinnersService;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeAwards;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeOrder;
import com.youge.yogee.modules.cthreeawards.service.CdThreeAwardsService;
import com.youge.yogee.modules.cthreeawards.service.CdThreeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component("NotBallQuartz")
public class NotBallQuartz {
    @Autowired
    private CdThreeOrderService cdThreeOrderService;
    @Autowired
    private CdThreeAwardsService cdThreeAwardsService;
    @Autowired
    private CdOrderWinnersService cdOrderWinnersService;
    @Autowired
    private CdFiveAwardsService cdFiveAwardsService;
    @Autowired
    private CdFiveOrderService cdFiveOrderService;
    @Autowired
    private CdLottoRewardService cdLottoRewardService;
    @Autowired
    private CdLottoOrderService cdLottoOrderService;
    @Autowired
    private CdOrderService cdOrderService;

    @Scheduled(cron = "*/59 * * * * ?")//2小时
    public void listThreeOrder() {
//        System.out.println("排列三开奖");
        CdThreeAwards cta = cdThreeAwardsService.findFirst();
        if (cta != null) {
            String awardDate = cta.getAtime().split(" ")[0]; //开奖时间截取日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date today = new Date();
            String todayStr = sdf.format(today);//当天时间
            if (awardDate.equals(todayStr)) {
                String weekday = cta.getWeekday();
                String aCode = cta.getAcode();//开奖号码
                //获取当期所有付款订单
                List<CdThreeOrder> cList = cdThreeOrderService.findByStatus("3", weekday);
                for (CdThreeOrder c : cList) {
                    System.out.println(c.getOrderNum() + "中奖");
                    String orderPerhaps = c.getAllPerhaps();
                    if ("2".equals(c.getBuyWays())) {
                        int sum = 0;
                        String[] aCodeArray = aCode.split(",");
                        for (String s : aCodeArray) {
                            sum += Integer.parseInt(s);
                        }
                        String wantSum = c.getNums();
                        String[] wantSumArray = wantSum.split("\\|");
                        for (String want : wantSumArray) {
                            if (sum == Integer.parseInt(want)) {
                                c.setResult(cta.getAcode());
                                c = threeWinner(c);

                            }
                        }
                    } else if (orderPerhaps.contains(aCode)) {
                        c.setResult(cta.getAcode());
                        c = threeWinner(c);

                    } else {
                        c.setResult(cta.getAcode());
                        c.setStatus("5"); //未中奖
                        c.setAward("0");//奖金为0
                        //改变订单总表状态
                        CdOrder co = cdOrderService.getOrderByOrderNum(c.getOrderNum());
                        if (co != null) {
                            co.setWinPrice("0");//奖金
                            co.setStatus("2");//中奖
                            cdOrderService.save(co);
                        }
                    }
                    cdThreeOrderService.save(c);

                }
            }
        }
    }


    @Scheduled(cron = "*/59 * * * * ?")//2小时
    public void listFiveOrder() {
//        System.out.println("排列五开奖");
        CdFiveAwards cfa = cdFiveAwardsService.findFirst();
        if (cfa != null) {
            String weekday = cfa.getWeekday();
            //获取当期所有付款订单
            List<CdFiveOrder> cList = cdFiveOrderService.findByStatus("3", weekday);

            String awardDate = cfa.getAtime().split(" ")[0]; //开奖时间截取日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date today = new Date();
            String todayStr = sdf.format(today);//当天时间
            if (awardDate.equals(todayStr)) {
                String aCode = cfa.getAcode();//开奖号码
                for (CdFiveOrder c : cList) {

                    String orderPerhaps = c.getAllPerhaps();
                    if (orderPerhaps.contains(aCode)) {
                        c.setResult(aCode);
                        c.setStatus("4");//中奖
                        //保存中奖纪录
                        CdOrderWinners cdOrderWinners = new CdOrderWinners();
                        cdOrderWinners.setWinOrderNum(c.getOrderNum());//中间单号
                        cdOrderWinners.setWinPrice(c.getAward());//中奖金额
                        cdOrderWinners.setUid(c.getUid());//中间用户
                        double awardDouble = Double.parseDouble(c.getAward());
                        String repayPercent = Calculations.getRepayPercent(awardDouble, Double.parseDouble(c.getPrice()));
                        cdOrderWinners.setRepayPercent(repayPercent);
                        cdOrderWinners.setType("8");
                        cdOrderWinners.setWallType("1");
                        cdOrderWinners.setResult(c.getResult());
                        cdOrderWinnersService.save(cdOrderWinners);
                        //改变订单总表状态
                        CdOrder co = cdOrderService.getOrderByOrderNum(c.getOrderNum());
                        if (co != null) {
                            co.setWinPrice(c.getAward());//奖金
                            co.setStatus("3");//中奖
                            cdOrderService.save(co);
                        }
                        AppPush.push(c.getUid(), "凯旋彩票", "您购买的排列五获得中奖金额" + c.getAward() + "元");
                    } else {
                        c.setResult(aCode);
                        c.setStatus("5"); //未中奖
                        c.setAward("0");//奖金为0
                        //改变订单总表状态
                        CdOrder co = cdOrderService.getOrderByOrderNum(c.getOrderNum());
                        if (co != null) {
                            co.setWinPrice("0");//奖金
                            co.setStatus("2");//开奖
                            cdOrderService.save(co);
                        }
                    }
                    cdFiveOrderService.save(c);

                }
            }
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")//2小时
    public void lotteryOrder() {
//        System.out.println("大乐透开奖");
        CdLottoReward clr = cdLottoRewardService.findFirst();
        if (clr != null) {
            String awardDate = clr.getOpeningTime().split(" ")[0]; //开奖时间截取日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date today = new Date();
            String todayStr = sdf.format(today);//当天时间
            if (awardDate.equals(todayStr)) {
                String weekday = clr.getMatchId().substring(1, 8);
                String aCode = clr.getNumber();//开奖号码 |分割
                String[] aCodeArray = aCode.split("\\|");
                String redAwardNums = aCodeArray[0]; //开奖红球
                String[] redAwardArray = redAwardNums.split(",");
                String blueAwardNums = aCodeArray[1]; //开奖蓝球
                String[] blueAwardArray = blueAwardNums.split(",");
                //没注金额
                String allAward = clr.getPerNoteMoney().replaceAll("--,", "");
                String finalAward = allAward.replaceAll(",--", "");

                String[] perNoteMoneyArray = finalAward.split(",");
                //获取当期所有付款订单
                List<CdLottoOrder> cList = cdLottoOrderService.findByStatus("3", weekday);
                for (CdLottoOrder c : cList) {
                    String redNums = c.getRedNums().replaceAll("\\|", ",");
                    String blueNums = c.getBlueNums().replaceAll("\\|", ",");
                    String[] redArray = redNums.split(",");
                    String[] blueArray = blueNums.split(",");
                    int redCounts = 0, blueCounts = 0;
                    for (String red : redArray) {
                        for (String redReward : redAwardArray) {
                            if (red.equals(redReward)) {
                                redCounts++;
                            }
                        }
                    }
                    for (String blue : blueArray) {
                        for (String blueReward : blueAwardArray) {
                            if (blue.equals(blueReward)) {
                                blueCounts++;
                            }
                        }
                    }
                    int result = redCounts * 10 + blueCounts;
                    if (result == 52) {
                        System.out.println(c.getOrderNum() + "一等奖");
//                        double winCount = Double.parseDouble(c.getAcount());//注数
//                        BigDecimal firstAward = BigDecimal.parseDouble(perNoteMoneyArray[0]);//每注金额
                        BigDecimal firstAward = new BigDecimal(perNoteMoneyArray[0]);
                        BigDecimal winCount = new BigDecimal(c.getAcount());
                        BigDecimal award = winCount.multiply(firstAward); //奖金
                        if ("1".equals(c.getIsPlus())) {
                            BigDecimal firstAwardPlus = new BigDecimal(perNoteMoneyArray[6]);//追加每注金额
                            BigDecimal awardPlus = winCount.multiply(firstAwardPlus);
                            award = award.add(awardPlus);
                        }
                        c.setStatus("4");//中奖
                        c.setAward(String.valueOf(award.setScale(2, 2)));//奖金
                        c.setResult(clr.getNumber());
                        cdLottoOrderService.save(c);
                        //保存中奖纪录
                        saveWinnerRecord(c);
                        //更改订单状态
                        changeOrderStatus(c);
//                        AppPush.push(c.getUid(), "凯旋彩票", "您购买的大乐透获得中奖金额" + award + "元");
                        //推送
                        pushMssage(c.getUid(), award);
                        continue;
                    } else if (result == 51) {
                        System.out.println(c.getOrderNum() + "二等奖");
                        BigDecimal firstAward = new BigDecimal(perNoteMoneyArray[1]);
                        BigDecimal winCount = new BigDecimal(c.getAcount());
                        BigDecimal award = winCount.multiply(firstAward); //奖金
                        if ("1".equals(c.getIsPlus())) {
                            BigDecimal firstAwardPlus = new BigDecimal(perNoteMoneyArray[7]);//追加每注金额
                            BigDecimal awardPlus = winCount.multiply(firstAwardPlus);
                            award = award.add(awardPlus);
                        }
                        c.setStatus("4");//中奖
                        c.setAward(String.valueOf(award.setScale(2)));//奖金
                        c.setResult(clr.getNumber());
                        cdLottoOrderService.save(c);
                        //保存中奖纪录
                        saveWinnerRecord(c);
                        //更改订单状态
                        changeOrderStatus(c);
                        //cdLottoOrderService.save(c);
                        //推送
                        pushMssage(c.getUid(), award);
                        continue;
//                        AppPush.push(c.getUid(), "凯旋彩票", "您购买的大乐透获得中奖金额" + award + "元");
                    } else if (result == 50 || result == 42) {
                        System.out.println(c.getOrderNum() + "三等奖");
                        BigDecimal firstAward = new BigDecimal(perNoteMoneyArray[2]);
                        BigDecimal winCount = new BigDecimal(c.getAcount());
                        BigDecimal award = winCount.multiply(firstAward); //奖金
                        if ("1".equals(c.getIsPlus())) {
                            BigDecimal firstAwardPlus = new BigDecimal(perNoteMoneyArray[8]);//追加每注金额
                            BigDecimal awardPlus = winCount.multiply(firstAwardPlus);
                            award = award.add(awardPlus);
                        }
                        c.setStatus("4");//中奖
                        c.setAward(String.valueOf(award.setScale(2)));//奖金
                        c.setResult(clr.getNumber());
                        //cdLottoOrderService.save(c);
                        //保存中奖纪录
                        saveWinnerRecord(c);
                        //更改订单状态
                        changeOrderStatus(c);
                        //cdLottoOrderService.save(c);
                        //推送
                        pushMssage(c.getUid(), award);
                        continue;
//                        AppPush.push(c.getUid(), "凯旋彩票", "您购买的大乐透获得中奖金额" + award + "元");
                    } else if (result == 41 || result == 32) {
                        System.out.println(c.getOrderNum() + "四等奖");
                        BigDecimal firstAward = new BigDecimal(perNoteMoneyArray[3]);
                        BigDecimal winCount = new BigDecimal(c.getAcount());
                        BigDecimal award = winCount.multiply(firstAward); //奖金
                        if ("1".equals(c.getIsPlus())) {
                            BigDecimal firstAwardPlus = new BigDecimal(perNoteMoneyArray[9]);//追加每注金额
                            BigDecimal awardPlus = winCount.multiply(firstAwardPlus);
                            award = award.add(awardPlus);
                        }
                        c.setStatus("4");//中奖
                        c.setAward(String.valueOf(award.setScale(2)));//奖金
                        c.setResult(clr.getNumber());
                        //cdLottoOrderService.save(c);
                        //保存中奖纪录
                        saveWinnerRecord(c);
                        //更改订单状态
                        changeOrderStatus(c);
                        //cdLottoOrderService.save(c);
                        //推送
                        pushMssage(c.getUid(), award);
                        continue;
//                        AppPush.push(c.getUid(), "凯旋彩票", "您购买的大乐透获得中奖金额" + award + "元");
                    } else if (result == 40 || result == 31 || result == 22) {
                        System.out.println(c.getOrderNum() + "五等奖");
                        BigDecimal firstAward = new BigDecimal(perNoteMoneyArray[4]);
                        BigDecimal winCount = new BigDecimal(c.getAcount());
                        BigDecimal award = winCount.multiply(firstAward); //奖金
                        if ("1".equals(c.getIsPlus())) {
                            BigDecimal firstAwardPlus = new BigDecimal(perNoteMoneyArray[10]);//追加每注金额
                            BigDecimal awardPlus = winCount.multiply(firstAwardPlus);
                            award = award.add(awardPlus);
                        }
                        c.setStatus("4");//中奖
                        c.setAward(String.valueOf(award.setScale(2)));//奖金
                        c.setResult(clr.getNumber());
                        cdLottoOrderService.save(c);
                        //保存中奖纪录
                        saveWinnerRecord(c);
                        //更改订单状态
                        changeOrderStatus(c);

                        //推送
                        pushMssage(c.getUid(), award);
                        continue;
//                        AppPush.push(c.getUid(), "凯旋彩票", "您购买的大乐透获得中奖金额" + award + "元");
                    } else if (result == 30 || result == 21 || result == 12 || result == 2) {
                        System.out.println(c.getOrderNum() + "六等奖");
                        BigDecimal firstAward = new BigDecimal(perNoteMoneyArray[5]);
                        BigDecimal winCount = new BigDecimal(c.getAcount());
                        BigDecimal award = winCount.multiply(firstAward); //奖金
                        c.setStatus("4");//中奖
                        c.setAward(String.valueOf(award.setScale(2)));//奖金
                        c.setResult(clr.getNumber());
                        cdLottoOrderService.save(c);
                        //保存中奖纪录
                        saveWinnerRecord(c);
                        //更改订单状态
                        changeOrderStatus(c);

                        //推送
                        pushMssage(c.getUid(), award);
                        continue;
//                        AppPush.push(c.getUid(), "凯旋彩票", "您购买的大乐透获得中奖金额" + award + "元");
                    } else {
                        c.setResult(clr.getNumber());
                        c.setStatus("5");//未中奖
                        c.setAward("0");//奖金0
                        c.setResult(clr.getNumber());
                        cdLottoOrderService.save(c);
                        //改变订单总表状态
                        CdOrder co = cdOrderService.getOrderByOrderNum(c.getOrderNum());
                        if (co != null) {
                            co.setWinPrice("0");//奖金
                            co.setStatus("2");//中奖
                            cdOrderService.save(co);
                        }
                    }
                }
            }
        }
    }

    private void saveWinnerRecord(CdLottoOrder c) {
        //保存中奖纪录
        CdOrderWinners cdOrderWinners = new CdOrderWinners();
        cdOrderWinners.setWinOrderNum(c.getOrderNum());//中间单号
        cdOrderWinners.setWinPrice(c.getAward());//中奖金额
        cdOrderWinners.setUid(c.getUid());//中间用户
        double awardDouble = Double.parseDouble(c.getAward());
        String repayPercent = Calculations.getRepayPercent(awardDouble, Double.parseDouble(c.getPrice()));
        cdOrderWinners.setRepayPercent(repayPercent);
        cdOrderWinners.setType("9");
        cdOrderWinners.setWallType("1");
        cdOrderWinners.setResult(c.getResult());
        cdOrderWinnersService.save(cdOrderWinners);
    }

    public CdThreeOrder threeWinner(CdThreeOrder c) {
        c.setStatus("4");//中奖
        String award = c.getAward();
        double awardDouble = Double.parseDouble(award);
        double timeDouble = Double.parseDouble(c.getTimes());
        String realAward = String.valueOf(awardDouble * timeDouble);
        c.setAward(realAward);

        AppPush.push(c.getUid(), "凯旋彩票", "您购买的排列三获得中奖金额" + award + "元");

        //保存中奖纪录
        CdOrderWinners cdOrderWinners = new CdOrderWinners();
        cdOrderWinners.setWinOrderNum(c.getOrderNum());//中间单号
        cdOrderWinners.setWinPrice(c.getAward());//中奖金额
        cdOrderWinners.setUid(c.getUid());//中奖用户
        double realAwardDouble = Double.parseDouble(realAward);
        String repayPercent = Calculations.getRepayPercent(realAwardDouble, Double.parseDouble(c.getPrice()));
        cdOrderWinners.setRepayPercent(repayPercent);
        cdOrderWinners.setType("7");
        cdOrderWinners.setWallType("1");
        cdOrderWinners.setResult(c.getResult());
        cdOrderWinnersService.save(cdOrderWinners);
        //改变订单总表状态
        CdOrder co = cdOrderService.getOrderByOrderNum(c.getOrderNum());
        if (co != null) {
            co.setWinPrice(realAward);//奖金
            co.setStatus("3");//中奖
            cdOrderService.save(co);
        }
        return c;
    }

    public void changeOrderStatus(CdLottoOrder c) {
        String award = c.getAward();
        BigDecimal awardBig = new BigDecimal(award);
        BigDecimal timeBig = new BigDecimal(c.getTimes());
        BigDecimal result=awardBig.multiply(timeBig);
        String realAward = String.valueOf(result.setScale(2,2));
        //改变订单总表状态
        CdOrder co = cdOrderService.getOrderByOrderNum(c.getOrderNum());
        if (co != null) {
            co.setWinPrice(realAward);//奖金
            co.setStatus("3");//中奖
            cdOrderService.save(co);
        }
    }

    public void pushMssage(String uid, BigDecimal award) {
        try {
            //String awardStr = String.valueOf(award);
            AppPush.push(uid, "凯旋彩票", "您购买的大乐透获得中奖金额" + award + "元");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
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