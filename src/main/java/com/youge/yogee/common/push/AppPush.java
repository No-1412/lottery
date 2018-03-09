package com.youge.yogee.common.push;

import com.youge.yogee.common.utils.SpringContextHolder;
import com.youge.yogee.modules.bm.entity.BmPush;
import com.youge.yogee.modules.bm.service.BmPushService;
import org.springframework.stereotype.Controller;

/**
 * Created by LiYuan on 2017/12/20.
 */
@Controller
public class AppPush {

    private static BmPushService bmPushService = SpringContextHolder.getBean(BmPushService.class);

    public static void push(String userID, String title, String content) {
        BmPush bmPush = bmPushService.findByUserid(userID);
        if (bmPush != null) {
            if(bmPush.getType().equals("1")){
                IphonePushUtil.toUser(bmPush.getPushid(), title, content, 1, null);
            }else {
                AppPushUtil.pubshtoSingle(userID, content, content);
            }

        }

    }

}
