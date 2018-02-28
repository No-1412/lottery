package com.youge.yogee.interfaces.lottery.details;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.lottery.help.HelpCenterInterface;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.crecharge.entity.CdRecharge;
import com.youge.yogee.modules.crecharge.service.CdRechargeService;
import com.youge.yogee.modules.cwithdrawal.entity.CdWithdrawal;
import com.youge.yogee.modules.cwithdrawal.service.CdWithdrawalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/12/21.
 */
@Controller
@RequestMapping("${frontPath}")
public class DetailsInterface {
    private static final Logger logger = LoggerFactory.getLogger(HelpCenterInterface.class);
    @Autowired
    private CdLotteryUserService cdLotteryUserService;

    @Autowired
    private CdRechargeService cdRechargeService;

    @Autowired
    private CdWithdrawalService cdWithdrawalService;
    /**
     * wangsong
     * 20171221
     * 充值明细
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "listRechargeCzDetails", method = RequestMethod.POST)
    public String listRechargeCzDetails(HttpServletRequest request) {
        logger.info("listRechargeCzDetails 充值明细----------------Start-------------------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String uId= (String) jsonData.get("uId");
        String total= (String) jsonData.get("total");
        String count= (String) jsonData.get("count");
        CdLotteryUser user = cdLotteryUserService.get(uId);
        if(user == null){
            logger.error("当前用户不存在或重新登录!");
            return HttpResultUtil.errorJson("当前用户不存在或重新登录!");
        }

        if (StringUtils.isEmpty(total)) {
            total = "1";
        }

        if (StringUtils.isEmpty(count)) {
            count = "12";
        }
        List cdRechargeList = new ArrayList();
        List<CdRecharge> listCdRecharge = cdRechargeService.listUidOrder(user.getId(), count, total);
        Iterator iter = listCdRecharge.iterator();
        while (iter.hasNext()){
            CdRecharge cdRecharge = (CdRecharge) iter.next();
            Map map = new HashMap();
            map.put("money", cdRecharge.getRechargeMoney());//充值金额
            map.put("createDate", cdRecharge.getCreateDate());//充值时间
            map.put("paynumber", cdRecharge.getPaynumber());//充值流水号
            map.put("id", cdRecharge.getId());//充值流水号
            cdRechargeList.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", cdRechargeList);
        logger.info("listRechargeCzDetails 充值明细----------------End-------------------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * wangsong
     * 20171221
     * 提现明细
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listCdWithdrawalDetails", method = RequestMethod.POST)
    public String listCdWithdrawalDetails(HttpServletRequest request) {
        logger.info("listCdWithdrawalDetails 提现明细----------------Start-------------------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String uId= (String) jsonData.get("uId");
        String total= (String) jsonData.get("total");
        String count= (String) jsonData.get("count");
        CdLotteryUser user = cdLotteryUserService.get(uId);
        if(user==null){
            logger.error("当前用户不存在或重新登录!");
            return HttpResultUtil.errorJson("当前用户不存在或重新登录!");
        }

        if (StringUtils.isEmpty(total)) {
            total = "1";
        }

        if (StringUtils.isEmpty(count)) {
            count = "12";
        }

        List cdWithdrawalList = new ArrayList();
        List<CdWithdrawal> listCdWithdrawal = cdWithdrawalService.listUidOrder(user.getId(), count, total);
        Iterator iter = listCdWithdrawal.iterator();
        while (iter.hasNext()){
            CdWithdrawal cdWithdrawal = (CdWithdrawal) iter.next();
            Map map = new HashMap();
            map.put("money", cdWithdrawal.getTxMoney());//提现金额
            map.put("createDate", cdWithdrawal.getCreateDate());//提现时间
            map.put("paynumber", cdWithdrawal.getTxNumber());//提现流水号
            map.put("id", cdWithdrawal.getId());//提现流水号
            cdWithdrawalList.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", cdWithdrawalList);
        logger.info("listCdWithdrawalDetails 提现明细----------------End-------------------");
        return HttpResultUtil.successJson(dataMap);
    }

}
