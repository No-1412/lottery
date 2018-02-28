package com.youge.yogee.interfaces.quartz;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfootballawards.service.CdFootballAwardsService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 定时任务
 * Created by Haipeng.Ren on 2015/12/28.
 */
@Component("taskJob")
public class QuartzListener  {

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
        System.out.println("定时任务Demo");

        List<CdFootballFollowOrder> cdFootballFollowOrderList = cdFootballFollowOrderService.findAll();

        Set<String> matchIdList = new HashSet<>();

        for (CdFootballFollowOrder cdFootballFollowOrder : cdFootballFollowOrderList) {

            String scoreResult = cdFootballFollowOrder.getScore();
            if (StringUtils.isNotEmpty(scoreResult)) {
                String[] scoreArray = scoreResult.split("\\|");
                String[] detail = scoreArray[0].split("\\+");
                matchIdList.add(detail[1]);
            }
            System.out.println(matchIdList);
        }
        List<String> awardMatchIdList = cdFootballAwardsService.getAllMatchId();

        if(awardMatchIdList.containsAll(matchIdList)){

        }


    }

}
