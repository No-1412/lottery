package com.youge.yogee.common.push;


import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.http.IGtPush;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.bm.entity.BmPush;
import com.youge.yogee.modules.bm.service.BmPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 个推 绑定
 * Created by LiYuan on 2015/10/22.
 */
@Controller
@RequestMapping(value = "${frontPath}")
public class AppPushController {

    private static Logger logger = LoggerFactory.getLogger(AppPushController.class);

    private static String appId = "BcEEEQunN87mmPPCNQpsc6";
    private static String appkey = "GJzhoayflq7VCTAAUVwoC4";
    private static String master = "jzIVS4tjtf8bEqvLfXZNi6";

    @Autowired
    private BmPushService bmPushService;

    @RequestMapping(value = "bindAlias")
    @ResponseBody
    public String bindAlias(HttpServletRequest request) {
        logger.debug("app 别名绑定 数据---------- Start--------");

        Map<?, ?> jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("josn格式错误");
        }

        String userid = (String) jsonData.get("userid");
        if (StringUtils.isEmpty(userid)) {
            userid = "";
//            logger.error("userid为空");
//            return HttpResultUtil.errorJson("userid为空");
        }
        String type = (String) jsonData.get("type");//设备类型（1IOS，2Android）
        if (StringUtils.isEmpty(type)) {
            logger.error("type为空");
            return HttpResultUtil.errorJson("type为空");
        }
        String pushid = (String) jsonData.get("pushid");//IOS是token，Android是CID
        if (StringUtils.isEmpty(pushid)) {
            logger.error("pushid为空");
            return HttpResultUtil.errorJson("pushid为空");
        }

//        CdLotteryUser cdLotteryUser = cdLotteryUserService.get(userid);
//        if (null == cdLotteryUser) {
//            return HttpResultUtil.errorJson("用户不存在");
//        }

        BmPush bmPush = bmPushService.findByPushID(pushid);
        bmPush.setPushid(pushid);
        bmPush.setType(type);
        bmPush.setUserid(userid);
        bmPushService.save(bmPush);

        //如果是Android绑定个推
        if(type.equals("2") && StringUtils.isNotEmpty(pushid) && StringUtils.isNotEmpty(userid)){
            IGtPush push = new IGtPush(appkey, master);
            // 单个CID绑定别名
            IAliasResult bindSCid = push.bindAlias(appId, userid, pushid);

            //绑定失败
            if (!bindSCid.getResult()) {
                return HttpResultUtil.errorJson("错误码:" + bindSCid.getErrorMsg());
            }
        }
        Map<String, Object> mapData = new HashMap<>();
        return HttpResultUtil.successJson(mapData);
    }


    @RequestMapping(value = "unBindAlias")
    @ResponseBody
    public String unBindAlias(HttpServletRequest request) {
        logger.debug("app 别名解绑 数据---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("josn格式错误");
        }

        String userid = (String) jsonData.get("userid");
        if (StringUtils.isNotEmpty(userid)) {
            userid = "";



        }
        String type = (String) jsonData.get("type");//设备类型（1IOS，2Android）
        if (StringUtils.isEmpty(type)) {
            logger.error("type为空");
            return HttpResultUtil.errorJson("type为空");
        }
        String pushid = (String) jsonData.get("pushid");//IOS是token，Android是CID
        if (StringUtils.isEmpty(pushid)) {
            logger.error("pushid为空");
            return HttpResultUtil.errorJson("pushid为空");
        }


        if(StringUtils.isNotEmpty(userid)){
            bmPushService.deleteByPushid(pushid);
        }else {
            BmPush bmPush = bmPushService.unBindAlias(userid,pushid,type);
            bmPush.setUserid("");
            bmPushService.save(bmPush);
        }

        if(type.equals("2") && StringUtils.isNotEmpty(userid)){
            IGtPush push = new IGtPush(appkey, master);
            // 单个CID解绑绑定别名
            IAliasResult bindSCid = push.unBindAlias(appId, userid, pushid);

            //绑定失败
            if (!bindSCid.getResult()) {
                return HttpResultUtil.errorJson("错误码:" + bindSCid.getErrorMsg());
            }
        }
        Map<String, Object> mapData = new HashMap<>();
        return HttpResultUtil.successJson(mapData);
    }

}
