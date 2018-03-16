package com.youge.yogee.interfaces.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("OtherQuartz")
public class OtherQuartz {
    @Scheduled(cron = "*/59 * * * * ?")//59秒  //每天十二点
    public void awardWallUpType() {
        System.out.println("大奖墙更新");

    }
}
