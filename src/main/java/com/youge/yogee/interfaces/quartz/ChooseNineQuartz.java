package com.youge.yogee.interfaces.quartz;

/**
 * Created by liyuan on 2018/3/7.
 */

import com.youge.yogee.common.push.AppPush;
import com.youge.yogee.interfaces.lottery.util.SelOrderUtil;
import com.youge.yogee.interfaces.util.Calculations;
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNine;
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNineOrder;
import com.youge.yogee.modules.cchoosenine.service.CdChooseNineOrderService;
import com.youge.yogee.modules.cchoosenine.service.CdChooseNineService;
import com.youge.yogee.modules.ccolorreward.entity.CdColorReward;
import com.youge.yogee.modules.ccolorreward.service.CdColorRewardService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
import com.youge.yogee.modules.corder.service.CdOrderService;
import com.youge.yogee.modules.corder.service.CdOrderWinnersService;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFailOrder;
import com.youge.yogee.modules.csuccessfail.service.CdSuccessFailOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时任务
 * Created by Liyuan on 2015/12/28.
 */
@Component("ChooseNineQuartz")
public class ChooseNineQuartz {
    @Autowired
    private CdChooseNineOrderService cdChooseNineOrderService;
    @Autowired
    private CdChooseNineService cdChooseNineService;
    @Autowired
    private CdSuccessFailOrderService cdSuccessFailOrderService;
    @Autowired
    private CdColorRewardService cdColorRewardService;
    @Autowired
    private CdOrderWinnersService cdOrderWinnersService;
    @Autowired
    private CdOrderService cdOrderService;

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
//    @Scheduled(cron = "*/59 * * * * ?")//2小时
    public void chooseNineOrder() {
//        System.out.println("任选九开奖");
        List<CdChooseNineOrder> cdBasketballFollowOrderList = cdChooseNineOrderService.findStatus();

        for (CdChooseNineOrder cdChooseNineOrder : cdBasketballFollowOrderList) {
            String orderDetail = cdChooseNineOrder.getOrderDetail();

            String weekday = cdChooseNineOrder.getWeekday();
            CdChooseNine cdChooseNine = cdChooseNineService.findByWeekday(weekday);
            if (cdChooseNine == null) {
                break;
            }
            //判断是否可以开奖
            if (cdChooseNine.getNotesNum().equals("")) {
                break;
            }

            //开奖结果
            String[] numbers = cdChooseNine.getNumber().split(",");
            //记录押中场数
            int sum = 0;
            //胆个数
            int danSum = 0;
            //获取订单中押的全部场次
            String[] detailList = orderDetail.split("\\|");
            for (String aDetail : detailList) {
                String[] aDetailArray = aDetail.split("\\+");
                //判断是否选为胆
                if (aDetailArray[3].equals("1")) {
                    String number = numbers[Integer.valueOf(aDetailArray[0]) + -1];
                    if (aDetailArray[2].contains(number) || number.equals("*")) {
                        danSum += 1;
                    } else {
                        break;
                    }
                } else {
                    String number = numbers[Integer.valueOf(aDetailArray[0]) + -1];
                    if (aDetailArray[2].contains(number) || number.equals("*")) {
                        sum += 1;
                    }
                }
            }
            if (sum + danSum >= 9) {
                int count = Calculations.rs(sum - danSum, 9 - danSum);
                Integer award = Integer.valueOf(cdChooseNineOrder.getTimes()) * count * Integer.valueOf(cdChooseNine.getPerNoteMoney());
                cdChooseNineOrder.setAward(award.toString());
                cdChooseNineOrder.setStatus("4");
                cdChooseNineOrder.setResult(cdChooseNine.getNumber());
                cdChooseNineOrderService.save(cdChooseNineOrder);
                //更新用户余额
                SelOrderUtil.addBalanceToUser(award.toString(), cdChooseNineOrder.getUid());
                //保存中奖纪录
                CdOrderWinners cdOrderWinners = new CdOrderWinners();
                cdOrderWinners.setWinOrderNum(cdChooseNineOrder.getOrderNumber());//中间单号
                cdOrderWinners.setWinPrice(award.toString());//中奖金额
                cdOrderWinners.setUid(cdChooseNineOrder.getUid());//中间用户
                String repayPercent = Calculations.getRepayPercent(award, Double.parseDouble(cdChooseNineOrder.getPrice()));
                cdOrderWinners.setRepayPercent(repayPercent);
                cdOrderWinners.setType("5");
                cdOrderWinners.setWallType("1");
                cdOrderWinners.setResult(cdChooseNineOrder.getResult());
                cdOrderWinnersService.save(cdOrderWinners);
                //改变订单总表状态
                CdOrder co = cdOrderService.getOrderByOrderNum(cdChooseNineOrder.getOrderNumber());
                if (co != null) {
                    co.setWinPrice(award.toString());//奖金
                    co.setStatus("3");//中奖
                    cdOrderService.save(co);
                }
                AppPush.push(cdChooseNineOrder.getUid(), "凯旋彩票", "您购买的任选九获得中奖金额" + award + "元");
            } else {
                cdChooseNineOrder.setResult(cdChooseNine.getNumber());
                cdChooseNineOrder.setStatus("5");
                cdChooseNineOrderService.save(cdChooseNineOrder);
                //改变订单总表状态
                CdOrder co = cdOrderService.getOrderByOrderNum(cdChooseNineOrder.getOrderNumber());
                if (co != null) {
                    co.setWinPrice("0");//奖金
                    co.setStatus("2");//开奖
                    cdOrderService.save(co);
                }

            }
        }
    }


        @Scheduled(cron = "0 0 */2 * * ?")//2小时
//    @Scheduled(cron = "*/59 * * * * ?")//2小时
    public void successFailOrder() {
//        System.out.println("胜负彩开奖");
        List<CdSuccessFailOrder> cdSuccessFailOrderList = cdSuccessFailOrderService.findStatus();

        for (CdSuccessFailOrder cdSuccessFailOrder : cdSuccessFailOrderList) {
            String orderDetail = cdSuccessFailOrder.getOrderDetail();

            String weekday = cdSuccessFailOrder.getWeekday();
            CdColorReward cdColorReward = cdColorRewardService.findByWeekday(weekday);

            //判断是否可以开奖
            if (cdColorReward == null) {
                break;
            }

            //开奖结果
            String[] numbers = cdColorReward.getNumber().split(",");

            //记录押中场数
            int sum = 0;
            //获取订单中押的全部场次
            String[] detailList = orderDetail.split("\\|");
            for (String aDetail : detailList) {
                String[] aDetailArray = aDetail.split("\\+");

                String number = numbers[Integer.valueOf(aDetailArray[0]) + -1];
                if (aDetailArray[2].contains(number) || number.equals("*")) {
                    sum += 1;
                }
            }
            String[] awards = cdColorReward.getPerNoteMoney().split(",");
            if (sum == 13) {
                Integer award = Integer.valueOf(cdSuccessFailOrder.getTimes()) * Integer.valueOf(awards[1]);
                cdSuccessFailOrder.setAward(award.toString());
                cdSuccessFailOrder.setResult(cdColorReward.getNumber());
                cdSuccessFailOrder.setStatus("4");
                cdSuccessFailOrderService.save(cdSuccessFailOrder);
                //更新用户余额
                SelOrderUtil.addBalanceToUser(award.toString(), cdSuccessFailOrder.getUid());
                //保存中奖纪录
                CdOrderWinners cdOrderWinners = new CdOrderWinners();
                cdOrderWinners.setWinOrderNum(cdSuccessFailOrder.getOrderNumber());//中间单号
                cdOrderWinners.setWinPrice(award.toString());//中奖金额
                cdOrderWinners.setUid(cdSuccessFailOrder.getUid());//中间用户
                String repayPercent = Calculations.getRepayPercent(award, Double.parseDouble(cdSuccessFailOrder.getPrice()));
                cdOrderWinners.setRepayPercent(repayPercent);
                cdOrderWinners.setType("6");
                cdOrderWinners.setWallType("1");
                cdOrderWinnersService.save(cdOrderWinners);
                //改变订单总表状态
                CdOrder co = cdOrderService.getOrderByOrderNum(cdSuccessFailOrder.getOrderNumber());
                if (co != null) {
                    co.setWinPrice(award.toString());//奖金
                    co.setStatus("3");//中奖
                    cdOrderService.save(co);
                }
                AppPush.push(cdSuccessFailOrder.getUid(), "凯旋彩票", "您购买的胜负彩获得中奖金额" + award + "元");
            } else if (sum == 14) {
                Integer award = Integer.valueOf(cdSuccessFailOrder.getTimes()) * Integer.valueOf(awards[0]);
                cdSuccessFailOrder.setAward(award.toString());
                cdSuccessFailOrder.setStatus("4");
                cdSuccessFailOrder.setResult(cdColorReward.getNumber());
                cdSuccessFailOrderService.save(cdSuccessFailOrder);
                //更新用户余额
                SelOrderUtil.addBalanceToUser(award.toString(), cdSuccessFailOrder.getUid());
                //保存中奖纪录
                CdOrderWinners cdOrderWinners = new CdOrderWinners();
                cdOrderWinners.setWinOrderNum(cdSuccessFailOrder.getOrderNumber());//中间单号
                cdOrderWinners.setWinPrice(award.toString());//中奖金额
                cdOrderWinners.setUid(cdSuccessFailOrder.getUid());//中间用户
                String repayPercent = Calculations.getRepayPercent(award, Double.parseDouble(cdSuccessFailOrder.getPrice()));
                cdOrderWinners.setRepayPercent(repayPercent);
                cdOrderWinners.setType("6");
                cdOrderWinners.setWallType("1");
                cdOrderWinners.setResult(cdSuccessFailOrder.getResult());
                cdOrderWinnersService.save(cdOrderWinners);
                //改变订单总表状态
                CdOrder co = cdOrderService.getOrderByOrderNum(cdSuccessFailOrder.getOrderNumber());
                if (co != null) {
                    co.setWinPrice(award.toString());//奖金
                    co.setStatus("3");//中奖
                    cdOrderService.save(co);
                }
                AppPush.push(cdSuccessFailOrder.getUid(), "凯旋彩票", "您购买的胜负彩获得中奖金额" + award + "元");
            } else {
                cdSuccessFailOrder.setResult(cdColorReward.getNumber());
                cdSuccessFailOrder.setStatus("5");
                cdSuccessFailOrderService.save(cdSuccessFailOrder);
                //改变订单总表状态
                CdOrder co = cdOrderService.getOrderByOrderNum(cdSuccessFailOrder.getOrderNumber());
                if (co != null) {
                    co.setWinPrice("0");//奖金
                    co.setStatus("2");//开奖
                    cdOrderService.save(co);
                }
            }

        }
    }
}
