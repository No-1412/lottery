package com.youge.yogee.publicutils;

import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wjc on 2017-12-11 0011.
 */
public class UserUtil {
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    public String checkUser(CdLotteryUser user,Integer i) {

        /*if (user == null) {
            logger.error("当前用户不存在!");
            return HttpResultUtil.errorJson("当前用户不存在!");
        }
        if (user != null) {
            if (user.getIsBlock().equals("1")) {
                logger.error("当前用户已锁定，不允许操作!");
                return HttpResultUtil.errorJson("当前用户已锁定，不允许操作!");
            }
            if (user.getIsRegisterCharge().equals("0")) {
                logger.error("当前用户未充值入会费!");
                return HttpResultUtil.errorJson("当前用户未充值入会费!");
            }
            if(i==2){
                if (user.getMemberType().equals("0")) {
                    logger.error("当前用户不是永久会员!");
                    return HttpResultUtil.errorJson("当前用户不是永久会员!");
                }
            }*/

        return null;
    }

}
