package com.youge.yogee.interfaces.quartz;

/**
 * Created by liyuan on 2018/3/7.
 */

import com.youge.yogee.modules.cchoosenine.entity.CdChooseNine;
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNineOrder;
import com.youge.yogee.modules.cchoosenine.service.CdChooseNineOrderService;
import com.youge.yogee.modules.cchoosenine.service.CdChooseNineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public void chooseNineOrder() {
        System.out.println("任选九开奖");
        List<CdChooseNineOrder> cdBasketballFollowOrderList = cdChooseNineOrderService.findStatusTwo();

        for (CdChooseNineOrder cdChooseNineOrder : cdBasketballFollowOrderList) {
            String orderDetail = cdChooseNineOrder.getOrderDetail();

            String weekday = cdChooseNineOrder.getWeekday();
            CdChooseNine cdChooseNine = cdChooseNineService.findByWeekday(weekday);
            //开奖结果
            String[] numbers = cdChooseNine.getNumber().split(",");
            //押中结果集
            List<String> winList = new ArrayList<>();
            String[] detailList = orderDetail.split("\\|");




        }
    }
}
