package com.youge.yogee.interfaces.lottery.order;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.lottery.help.HelpCenterInterface;
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
 * Created by Administrator on 2017/12/22.
 */
@Controller
@RequestMapping("${frontPath}")
public class OrderInterface {
    private static final Logger logger = LoggerFactory.getLogger(HelpCenterInterface.class);

    @Autowired
    private CdLotteryUserService cdLotteryUserService;

    @Autowired
    private CdOrderService cdOrderService;

    /**
     * wangsong
     * 彩票订单
     * 20171222
     * @param request
     * @return
     */
    @RequestMapping(value = "listOrder", method = RequestMethod.POST)
    @ResponseBody
    public String listOrder(HttpServletRequest request) {
        logger.info("listOrder 彩票订单------------Start-----------------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String uId = (String) jsonData.get("uId");
        String total= (String) jsonData.get("total");
        String count= (String) jsonData.get("count");
        String type = (String) jsonData.get("type");//中奖(0待开奖,1中奖2未中奖)
        if (StringUtils.isEmpty(total)) {
            total = "1";
        }

        if (StringUtils.isEmpty(count)) {
            count = "12";
        }

        CdLotteryUser user = cdLotteryUserService.get(uId);
        if(user == null){
            logger.error("当前用户不存在或重新登录!");
            return HttpResultUtil.errorJson("当前用户不存在或重新登录!");
        }
        List list = new ArrayList();
        List<CdOrder> listCdOrder= cdOrderService.listCdOrder(user.getId(), type, count, total);
        for(CdOrder cdOrder:listCdOrder) {
            Map map = new HashMap();
            map.put("id", cdOrder.getId());
            map.put("type",cdOrder.getType());//彩票类型
            map.put("price", cdOrder.getTotalPrice());//总价
            map.put("createDate", cdOrder.getCreateDate().substring(0,10));
            map.put("win", cdOrder.getWin());//中奖(0待开奖,1中奖2未中奖)
            map.put("issue", cdOrder.getIssue());//期号
            map.put("number", cdOrder.getNumber());//彩票号
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("listOrder 彩票订单------------End-----------------");
        return HttpResultUtil.successJson(dataMap);
    }

}
