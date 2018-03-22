package com.youge.yogee.common.push;

import com.youge.yogee.common.utils.SpringContextHolder;
import com.youge.yogee.modules.bm.entity.BmPush;
import com.youge.yogee.modules.bm.service.BmPushService;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiYuan on 2017/12/20.
 */
@Controller
public class AppPush {

    private static BmPushService bmPushService = SpringContextHolder.getBean(BmPushService.class);

    public static void push(String userID, String title, String content) {
        BmPush bmPush = bmPushService.findByUserid(userID);
        if (bmPush != null) {
            if (bmPush.getType().equals("1")) {
                List<String> tokens = new ArrayList<>();
                tokens.add(bmPush.getPushid());
                IphonePushUtil.toUser(tokens, title, content, 1, null);
            } else {
                AppPushUtil.pubshtoSingle(userID, content, content);
            }
        }
    }


    public static void pushAll(String title, String content) {


        IphonePushUtil.toUser(bmPushService.findAllIOS(), title, content, 1, null);


        AppPushUtil.pushtoAPP(title, content);
    }

}
