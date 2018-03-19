package com.youge.yogee.interfaces.quartz;

import com.youge.yogee.interfaces.util.util;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveAwards;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveOrder;
import com.youge.yogee.modules.cfiveawards.service.CdFiveAwardsService;
import com.youge.yogee.modules.cfiveawards.service.CdFiveOrderService;
import com.youge.yogee.modules.clottoreward.entity.CdLottoOrder;
import com.youge.yogee.modules.clottoreward.entity.CdLottoReward;
import com.youge.yogee.modules.clottoreward.service.CdLottoOrderService;
import com.youge.yogee.modules.clottoreward.service.CdLottoRewardService;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeAwards;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeOrder;
import com.youge.yogee.modules.cthreeawards.service.CdThreeAwardsService;
import com.youge.yogee.modules.cthreeawards.service.CdThreeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component("NotBallOrderFollowQuartz")
public class NotBallOrderFollowQuartz {

    @Autowired
    private CdThreeOrderService cdThreeOrderService;
    @Autowired
    private CdThreeAwardsService cdThreeAwardsService;
    @Autowired
    private CdFiveAwardsService cdFiveAwardsService;
    @Autowired
    private CdFiveOrderService cdFiveOrderService;
    @Autowired
    private CdLottoRewardService cdLottoRewardService;
    @Autowired
    private CdLottoOrderService cdLottoOrderService;

    @Scheduled(cron = "*/59 * * * * ?")//59秒
    public void listThreeOrder() {
        System.out.println("排列三追号");
        CdThreeAwards cta = cdThreeAwardsService.findFirst();
        if (cta != null) {
            String awardDate = cta.getAtime().split(" ")[0]; //开奖时间截取日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date today = new Date();
            String todayStr = sdf.format(today);//当天时间
            if (awardDate.equals(todayStr)) {
                String awardWeekday = cta.getWeekday();
                int nextAwardWeekday = Integer.parseInt(awardWeekday) + 1;
                List<CdThreeOrder> cList = cdThreeOrderService.findAllFollowOrders();
                for (CdThreeOrder c : cList) {
                    String weekCon = c.getWeekContinue();//所有待追具体期数
                    String nextWeekday = weekCon.split(",")[0];
                    int leftCon = Integer.parseInt(c.getContinuity());//追号期数
                    if (nextWeekday.equals(String.valueOf(nextAwardWeekday))) {
                        CdThreeOrder cdThreeOrder = new CdThreeOrder();
                        if (leftCon == 2) {
                            cdThreeOrder.setFollowType("4");//类型 追号不再追号
                            cdThreeOrder.setContinuity("1");//连续期数
                            cdThreeOrder.setWeekContinue("");//连续期数详情
                        } else {
                            cdThreeOrder.setFollowType("3");//追号中
                            cdThreeOrder.setContinuity(String.valueOf(leftCon - 1));//连续期数
                            String newWeekCon = weekCon.replaceAll(nextWeekday + ",", "");
                            cdThreeOrder.setWeekContinue(newWeekCon);//连续期数详情
                        }
                        if ("1".equals(c.getFollowType())) {
                            c.setFollowType("2");
                            cdThreeOrderService.save(c);
                        }
                        if("3".equals(c.getFollowType())){
                            c.setFollowType("5");
                            cdThreeOrderService.save(c);
                        }

                        //生成订单号
                        String orderNum = util.genOrderNo("PLS", util.getFourRandom());
                        cdThreeOrder.setOrderNum(orderNum);//订单号
                        cdThreeOrder.setNums(c.getNums());//订单详情
                        cdThreeOrder.setBuyWays(c.getBuyWays());//玩法
                        cdThreeOrder.setWeekday(nextWeekday);//期数
                        cdThreeOrder.setAcount(c.getAcount());//注数
                        cdThreeOrder.setPrice(c.getPrice());//金额
                        cdThreeOrder.setAward(c.getAward());//奖金
                        cdThreeOrder.setUid(c.getUid());//用户
                        cdThreeOrder.setAllPerhaps(c.getAllPerhaps());//所有可能
                        cdThreeOrder.setStatus("2");//已付款
                        cdThreeOrder.setTimes(c.getTimes());//倍数
                        cdThreeOrder.setType("2");//追号订单
                        cdThreeOrderService.save(cdThreeOrder);
                        System.out.println("生成追号订单" + cdThreeOrder.getOrderNum());
                    }
                }
            }
        }
    }

    @Scheduled(cron = "*/59 * * * * ?")//59秒  //后期改成十点
    public void listFiveOrder() {
        System.out.println("排列五追号");
        CdFiveAwards cfa = cdFiveAwardsService.findFirst();
        if (cfa != null) {
            String awardDate = cfa.getAtime().split(" ")[0]; //开奖时间截取日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date today = new Date();
            String todayStr = sdf.format(today);//当天时间
            if (awardDate.equals(todayStr)) {
                String awardWeekday = cfa.getWeekday();
                int nextAwardWeekday = Integer.parseInt(awardWeekday) + 1;
                List<CdFiveOrder> cList = cdFiveOrderService.findAllFollowOrders();
                for (CdFiveOrder c : cList) {
                    String weekCon = c.getWeekContinue();//所有待追具体期数
                    String nextWeekday = weekCon.split(",")[0];
                    int leftCon = Integer.parseInt(c.getContinuity());//追号期数
                    if (nextWeekday.equals(String.valueOf(nextAwardWeekday))) {
                        CdFiveOrder cdFiveOrder = new CdFiveOrder();
                        if (leftCon == 2) {
                            cdFiveOrder.setFollowType("4");//类型 追号不再追号
                            cdFiveOrder.setContinuity("1");//连续期数
                            cdFiveOrder.setWeekContinue("");//连续期数详情
                        } else {
                            cdFiveOrder.setFollowType("3");//追号中
                            cdFiveOrder.setContinuity(String.valueOf(leftCon - 1));//连续期数
                            String newWeekCon = weekCon.replaceAll(nextWeekday + ",", "");
                            cdFiveOrder.setWeekContinue(newWeekCon);//连续期数详情
                        }
                        if ("1".equals(c.getFollowType())) {
                            c.setFollowType("2");
                            cdFiveOrderService.save(c);
                        }
                        if ("3".equals(c.getFollowType())) {
                            c.setFollowType("5");
                            cdFiveOrderService.save(c);
                        }

                        //生成订单号
                        String orderNum = util.genOrderNo("PLW", util.getFourRandom());
                        cdFiveOrder.setOrderNum(orderNum);//订单号
                        cdFiveOrder.setNums(c.getNums());//订单详情
                        cdFiveOrder.setBuyWays(c.getBuyWays());//玩法
                        cdFiveOrder.setWeekday(nextWeekday);//期数
                        cdFiveOrder.setAcount(c.getAcount());//注数
                        cdFiveOrder.setPrice(c.getPrice());//金额
                        cdFiveOrder.setAward(c.getAward());//奖金
                        cdFiveOrder.setUid(c.getUid());//用户
                        cdFiveOrder.setAllPerhaps(c.getAllPerhaps());//所有可能
                        cdFiveOrder.setStatus("2");//已付款
                        cdFiveOrder.setTimes(c.getTimes());//倍数
                        cdFiveOrder.setType("2");//追号订单
                        cdFiveOrderService.save(cdFiveOrder);
                        System.out.println("生成追号订单" + cdFiveOrder.getOrderNum());
                    }
                }
            }
        }
    }

    @Scheduled(cron = "*/59 * * * * ?")//59秒  //后期改成十点
    public void lotteryOrder() {
        System.out.println("大乐透追号");
        CdLottoReward clr = cdLottoRewardService.findFirst();
        if (clr != null) {
            String awardDate = clr.getOpeningTime().split(" ")[0]; //开奖时间截取日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date today = new Date();
            String todayStr = sdf.format(today);//当天时间
            if (awardDate.equals(todayStr)) {
                String awardWeekday = clr.getMatchId().substring(1, 8);
                int nextAwardWeekday = Integer.parseInt(awardWeekday) + 1;
                List<CdLottoOrder> cList = cdLottoOrderService.findAllFollowOrders();
                for (CdLottoOrder c : cList) {
                    String weekCon = c.getWeekContinue();//所有待追具体期数
                    String nextWeekday = weekCon.split(",")[0];
                    int leftCon = Integer.parseInt(c.getContinuity());//追号期数
                    if (nextWeekday.equals(String.valueOf(nextAwardWeekday))) {
                        CdLottoOrder cdLottoOrder = new CdLottoOrder();
                        if (leftCon == 2) {
                            cdLottoOrder.setFollowType("4");//类型 追号不再追号
                            cdLottoOrder.setContinuity("1");//连续期数
                            cdLottoOrder.setWeekContinue("");//连续期数详情
                        } else {
                            cdLottoOrder.setFollowType("3");//追号中
                            cdLottoOrder.setContinuity(String.valueOf(leftCon - 1));//连续期数
                            String newWeekCon = weekCon.replaceAll(nextWeekday + ",", "");
                            cdLottoOrder.setWeekContinue(newWeekCon);//连续期数详情
                        }
                        if ("1".equals(c.getFollowType())) {
                            c.setFollowType("2");
                            cdLottoOrderService.save(c);
                        }
                        if ("3".equals(c.getFollowType())) {
                            c.setFollowType("5");
                            cdLottoOrderService.save(c);
                        }

                        //生成订单号
                        String orderNum = util.genOrderNo("DLT", util.getFourRandom());
                        cdLottoOrder.setOrderNum(orderNum);//订单号
                        cdLottoOrder.setRedNums(c.getRedNums());//红球
                        cdLottoOrder.setBlueNums(c.getBlueNums());//蓝球
                        cdLottoOrder.setType(c.getType());//玩法
                        cdLottoOrder.setWeekday(nextWeekday);//期数
                        cdLottoOrder.setAcount(c.getAcount());//注数
                        cdLottoOrder.setPrice(c.getPrice());//金额
                        cdLottoOrder.setAward(c.getAward());//奖金
                        cdLottoOrder.setUid(c.getUid());//用户
                        cdLottoOrder.setIsPlus(c.getIsPlus());//追加
                        cdLottoOrder.setStatus("2");//已付款
                        cdLottoOrder.setTimes(c.getTimes());//倍数
                        cdLottoOrder.setConType("2");//追号订单
                        cdLottoOrderService.save(cdLottoOrder);
                        System.out.println("生成追号订单" + cdLottoOrder.getOrderNum());
                    }
                }
            }
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