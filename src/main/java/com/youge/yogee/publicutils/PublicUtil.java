package com.youge.yogee.publicutils;

import com.youge.yogee.modules.clotteryuser.dao.CdLotteryUserDao;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wjc on 2017-12-14 0014.
 */
public class PublicUtil {
    @Autowired
    private static CdLotteryUserDao cdLotteryUserDao;
    @Autowired
    private static CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdLotteryUser cdLotteryUser;


}
