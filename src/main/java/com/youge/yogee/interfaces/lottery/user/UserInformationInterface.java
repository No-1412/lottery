package com.youge.yogee.interfaces.lottery.user;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.service.CdOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的模块
 */
@Controller
@RequestMapping("${frontPath}")
public class UserInformationInterface {
    private static final Logger logger = LoggerFactory.getLogger(UserInformationInterface.class);
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdOrderService cdOrderService;

    /**
     * 我的首页
     */

    @RequestMapping(value = "myHomePage", method = RequestMethod.POST)
    @ResponseBody
    public String myHomePage(HttpServletRequest request) {
        logger.info("myHomePage---------- Start-----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map dataMap = new HashMap();
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        dataMap.put("uid", clu.getId());
        dataMap.put("img", clu.getImg());//头像
        dataMap.put("level", clu.getMemberLevel());//等级
        dataMap.put("name", clu.getName());//昵称
        dataMap.put("balance", clu.getBalance());//余额
        dataMap.put("rebate", clu.getRebate());//返利金额
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 我的订单
     */

    @RequestMapping(value = "myOrderList", method = RequestMethod.POST)
    @ResponseBody
    public String myOrderList(HttpServletRequest request) {
        logger.info("myOrderList---------- Start-----------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map dataMap = new HashMap();
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        String status = (String) jsonData.get("status");
        if (StringUtils.isEmpty(uid)) {
            status = "";
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("count为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        List<CdOrder> list = cdOrderService.listCdOrder(uid, status, total, count);
        List<Map<String, String>> cList = new ArrayList<>();
        for (CdOrder c : list) {
            Map<String, String> map = new HashMap();
            map.put("id", c.getId());//id
            map.put("type", c.getType());//彩票种类 1足球单关 2足球串关 3篮球单关 4篮球串关 5任选九 6胜负彩 7排列三 8排列五 9大乐透
            map.put("price", c.getTotalPrice());//金额
            map.put("win", c.getWinPrice());//中奖金额
            map.put("time", c.getCreateDate());//时间
            cList.add(map);
        }
        dataMap.put("cList", cList);
        return HttpResultUtil.successJson(dataMap);
    }

}
