package com.youge.yogee.interfaces.lottery.index;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.*;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.crecord.entity.CdRecordCash;
import com.youge.yogee.modules.crecord.service.CdRecordCashService;
import com.youge.yogee.modules.sys.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhaoYifeng on 2018-03-26.
 */
@Controller
@RequestMapping("${frontPath}")
public class TryCashInterface {
    private static final Logger logger = LoggerFactory.getLogger(TryCashInterface.class);
    @Autowired
    private CdRecordCashService cdRecordCashService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;


    /**
     * 实名认证
     */
    @RequestMapping(value = "realNameCertification", method = RequestMethod.POST)
    @ResponseBody
    public String realNameCertification(HttpServletRequest request) throws ParseException {
        logger.info("realNameCertification--------Start-------------------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }

        //身份证卡号
        String cardNum = (String) jsonData.get("cardNum");
        if (StringUtils.isEmpty(cardNum)) {
            logger.error("cardNum为空");
            return HttpResultUtil.errorJson("cardNum为空");
        }
        //持卡人姓名
        String cardName = (String) jsonData.get("cardName");
        if (StringUtils.isEmpty(cardName)) {
            logger.error("cardName为空");
            return HttpResultUtil.errorJson("cardName为空");
        }
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        boolean flag = IDCardRecog.IDCardVerify(cardNum, cardName);
        if (flag) {
            clu.setIdNumber(cardNum);
            clu.setReality(cardName);
            clu.setIsRealNameVerified("1");
            cdLotteryUserService.save(clu);
            map.put("uid", clu.getId());
            map.put("idCard", cardNum);
            map.put("realName", cardName);
        } else {
            return HttpResultUtil.errorJson("认证失败");
        }
        return HttpResultUtil.successJson(map);
    }


    /**
     * 四要素验证
     */
    @RequestMapping(value = "checkBankOption", method = RequestMethod.POST)
    @ResponseBody
    public String checkBankOption(HttpServletRequest request) throws ParseException {
        logger.info("checkBankOption--------Start-------------------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        //卡号
        String cardNum = (String) jsonData.get("cardNum");
        if (StringUtils.isEmpty(cardNum)) {
            logger.error("cardNum为空");
            return HttpResultUtil.errorJson("cardNum为空");
        }
        //手机号
        String phoneNum = (String) jsonData.get("phoneNum");
        if (StringUtils.isEmpty(phoneNum)) {
            logger.error("phoneNum为空");
            return HttpResultUtil.errorJson("phoneNum为空");
        }
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        String realName = clu.getReality();//用户真实姓名
        String idCard = clu.getIdNumber();//身份证号
        boolean flag = BankUtil.bankVerify(cardNum, idCard, phoneNum, realName);
        if (flag) {
            return HttpResultUtil.successJson(map);
        } else {
            return HttpResultUtil.errorJson("信息不匹配");
        }

    }


    /**
     * 添加提现记录
     */
    @RequestMapping(value = "addCashRecord", method = RequestMethod.POST)
    @ResponseBody
    public String addCashRecord(HttpServletRequest request) throws ParseException {
        logger.info("addCashRecord--------Start-------------------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        //提款金额
        String price = (String) jsonData.get("price");
        if (StringUtils.isEmpty(price)) {
            logger.error("price为空");
            return HttpResultUtil.errorJson("price为空");
        }
        //卡号
        String cardNum = (String) jsonData.get("cardNum");
        if (StringUtils.isEmpty(cardNum)) {
            logger.error("cardNum为空");
            return HttpResultUtil.errorJson("cardNum为空");
        }
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }
        String password = (String) jsonData.get("password");
        if (StringUtils.isEmpty(password)) {
            logger.error("password为空");
            return HttpResultUtil.errorJson("password为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        if (SystemService.validatePassword(password, clu.getPassword())) {
            String orderNum = util.genOrderNo("", util.getFourRandom());
            CdRecordCash crc = new CdRecordCash();
            crc.setCardName(clu.getReality());
            crc.setCardNum(cardNum);
            crc.setPrice(price);
            crc.setUid(uid);
            crc.setUname(clu.getName());
            crc.setOrderNum(orderNum);
            crc.setStatus("1");
            String catchTimes = clu.getCatchTimes();
            int leftTimes = Integer.parseInt(catchTimes) - 1;
            clu.setCatchTimes(String.valueOf(leftTimes));
            BigDecimal priceBig = new BigDecimal(price);
            if (clu.getBalance().compareTo(priceBig) == -1) {
                return HttpResultUtil.errorJson("余额不足");
            } else {
                clu.setBalance(clu.getBalance().subtract(priceBig));
            }
            cdRecordCashService.save(crc);
            cdLotteryUserService.save(clu);
        } else {
            return HttpResultUtil.errorJson("密码不正确");
        }
        return HttpResultUtil.successJson(map);
    }


    /**
     * 返利提现
     */
    @RequestMapping(value = "userCashToBalance", method = RequestMethod.POST)
    @ResponseBody
    public String userCashToBalance(HttpServletRequest request) throws ParseException {
        logger.info("userCashToBalance--------Start-------------------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }

        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        BigDecimal balance = clu.getBalance();
        String rebate = clu.getRebate();
        BigDecimal newBalance = balance.add(new BigDecimal(rebate));
        clu.setBalance(newBalance);//更新余额
        clu.setRebate("0");
        cdLotteryUserService.save(clu);
        return HttpResultUtil.successJson(map);
    }

}



