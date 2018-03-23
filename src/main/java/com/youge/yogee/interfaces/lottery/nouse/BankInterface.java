package com.youge.yogee.interfaces.lottery.nouse;

import com.google.common.collect.Maps;
import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cbankcard.entity.CdBankCard;
import com.youge.yogee.modules.cbankcard.service.CdBankCardService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.cmessage.entity.CdMessage;
import com.youge.yogee.modules.cmessage.service.CdMessageService;
import com.youge.yogee.modules.csysbank.entity.CdSysBank;
import com.youge.yogee.modules.csysbank.service.CdSysBankService;
import com.youge.yogee.publicutils.AddUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017-10-13 .
 * WeiJinChao
 * 个人信息 添加银行卡
 */
@Controller
@RequestMapping("${frontPath}")
public class BankInterface {
    private static final Logger logger = LoggerFactory.getLogger(BankInterface.class);
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdBankCardService cdBankCardService;
    @Autowired
    private CdMessageService cdMessageService;
    @Autowired
    private CdSysBankService cdSysBankService;

    /**
     * anbo,2017-10-26,查询系统银行
     */
    @RequestMapping(value = "querySysBank", method = RequestMethod.POST)
    @ResponseBody
    public String querySysBank(HttpServletRequest request) {
        logger.info("查询系统银行卡 querySysBank ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String uid = (String) jsonData.get("uid");
        Map dataMap = new HashMap();

        List sysBankList = new ArrayList();

        //查询银行卡
        List<CdSysBank> bankList = cdSysBankService.getSysBankList();
        if (bankList != null && bankList.size() > 0) {
            for (CdSysBank bank : bankList) {
                Map map = new HashMap();
                map.put("bankId", bank.getId());
                map.put("bankName", bank.getName());//银行名称
                sysBankList.add(map);
            }
        }
        if(userMessage(request)!=null){
            return userMessage(request);
        }
        CdLotteryUser user = cdLotteryUserService.get(uid); //查询当前登录用户
        String str = cdLotteryUserService.checkUser(user,1);//代表免费

        if(null!=str){
            return str;
        }
//        CdLotteryUser user = cdLotteryUserService.get("wwwwww");
        dataMap.put("name",user.getReality());  //姓名
        dataMap.put("idCard",user.getIdNumber()); //身份证号
        dataMap.put("sysBankList", sysBankList);

        logger.info("查询系统银行卡 querySysBank ----------End--------");

        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 银行卡添加
     */
    @RequestMapping(value = "addBankCard")
    @ResponseBody
    public String addBankCard(HttpServletRequest request) {
        logger.info(" 银行卡添加 ---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String uid = (String) jsonData.get("uid");//id
        String name = (String) jsonData.get("name");//姓名
        String idcard = (String) jsonData.get("idcard");//身份证
        String cardno = (String) jsonData.get("cardno");//储蓄卡卡号
        String receivebank = (String) jsonData.get("receivebank");//开户银行
        String selectbank = (String) jsonData.get("selectbank");//支行
        String cardMode = (String) jsonData.get("cardMode");//卡类
        String cardStatus = (String) jsonData.get("cardStatus");//卡状态
        String selectProvince = (String) jsonData.get("selectProvince");//所在省
        String selectCity = (String) jsonData.get("selectCity");//所在市
        String selectbankId = (String) jsonData.get("selectbankId");//开户行id
        String mobile = (String) jsonData.get("mobile");//手机号
        String code = (String) jsonData.get("code");//手机验证码
        //String sysBankId = (String) jsonData.get("sysBankId");//系统银行ID

        CdLotteryUser user = cdLotteryUserService.get(uid); //查询当前登录用户

        if (user == null) {
            logger.error("当前用户不存在!");
            return HttpResultUtil.errorJson("当前用户不存在!");
        }
        if (user != null) {
            if (user.getIsBlock().equals("1")) {
                logger.error("当前用户已锁定，不允许操作!");
                return HttpResultUtil.errorJson("当前用户已锁定，不允许操作!");
            }
        }
        if ("0".equals(user.getIsRealNameVerified())) {
            logger.error("请先实名认证!");
            return HttpResultUtil.errorJson("请先实名认证!");
        }
        //校验提交信息
        if (StringUtils.isEmpty(name)) {
            logger.error("请输入姓名!");
            return HttpResultUtil.errorJson("请输入姓名!");
        }
        if (StringUtils.isEmpty(idcard)) {
            logger.error("请输入证件号!");
            return HttpResultUtil.errorJson("请输入证件号!");
        }
        if (StringUtils.isEmpty(cardno)) {
            logger.error("请输入储蓄卡卡号!");
            return HttpResultUtil.errorJson("请输入储蓄卡卡号!");
        }

        if (StringUtils.isEmpty(selectbankId)) {
            logger.error("请选择开户银行!");
            return HttpResultUtil.errorJson("请选择开户银行!");
        }
        //判断输入的银行卡号在数据库中是否已经存在
        List listCount = cdBankCardService.getWillDefaultCard(cardno);
        if (listCount.size() > 0) {
            logger.error("您添加的银行卡已经存在!");
            return HttpResultUtil.errorJson("您添加的银行卡已经存在!");
        }

        String bankImg = "";
        String kefuTel = "";
        String bankNo = "";
        CdSysBank CdSysBank = cdSysBankService.get(selectbankId);
        if (CdSysBank != null) {
            bankImg = CdSysBank.getBankImg();
            kefuTel = CdSysBank.getKefuTel() == null ? "" : CdSysBank.getKefuTel();
            bankNo = CdSysBank.getBankNo();
        }


        //校验验证码
        List<CdMessage> listCodeAndDate = cdMessageService.getMessage(mobile);

        if(null!=listCodeAndDate && listCodeAndDate.size()>0){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(listCodeAndDate.get(0).getCreateDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long second = (System.currentTimeMillis() - date.getTime()) / (1000);
            if (second > Long.valueOf(Global.EFFECTTIVE_TIME_SECOND)) {
                logger.error("短信验证码已失效，请重新获取！");
                return HttpResultUtil.errorJson("短信验证码已失效，请重新获取!");
            } else {
                //判断短信验证码 如果不相同返回错误信息
                if (!code.equals(listCodeAndDate.get(0).getCode())) {
                    logger.error("短信验证码错误，请重新输入！");
                    return HttpResultUtil.errorJson("短信验证码错误，请重新输入!");
                }
                //变更验证码为已经校验
                listCodeAndDate.get(0).setCheckZt("1");
                cdMessageService.save(listCodeAndDate.get(0));
            }
        }else{
            logger.error("请点击发送验证码,并重新填写！");
            return HttpResultUtil.errorJson("请点击发送验证码,并重新填写！");
        }

        //校验银行卡,最多可添加三张银行卡
        Long bankCount = cdBankCardService.getBankCount(user.getId());
        if (bankCount >= 3) {
            logger.error("您添加的银行卡已超过三张!");
            return HttpResultUtil.errorJson("您添加的银行卡已超过三张!");
        }
        Map dataMap = Maps.newHashMap();
        //初始化信息并保存
        AddUtils.addBankCard(cdBankCardService, user.getId(), user.getName(), selectbank, receivebank, selectbankId, cardno, selectProvince, selectCity, bankImg, kefuTel, bankNo, idcard);
        logger.info("pc：银行卡添加---------- End----------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * Weijinchao,2017-10-26,用户银行卡查询
     */
    @RequestMapping(value = "queryBank", method = RequestMethod.POST)
    @ResponseBody
    public String queryBank(HttpServletRequest request) {
        logger.info("查询银行卡 queryBank ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String uid = (String) jsonData.get("uid");
        logger.info("+++++++++++++"+uid);
        CdLotteryUser user = cdLotteryUserService.get(uid); //查询当前登录用户

        String str2 = cdLotteryUserService.checkUser(user,1);//2代表共享

        if(null!=str2){
            return str2;
        }

        Map dataMap = new HashMap();
        List profitList = new ArrayList();

        //查询银行卡
        List<CdBankCard> bankList = cdBankCardService.getUserBankList(user.getId());
        if (bankList != null && bankList.size() > 0) {
            for (CdBankCard bank : bankList) {
                Map map = new HashMap();
                String sb = bank.getCardno().substring(bank.getCardno().length() - 4, bank.getCardno().length());
                map.put("id", bank.getId());//主键
                map.put("cardNo", bank.getCardno().substring(0,4)+"*****"+sb);//银行卡号
                map.put("selectbank", bank.getSelectbank());//银行名称(支行)
                map.put("name", bank.getName());//姓名
                map.put("img", Global.getConfig("domain.name") +  bank.getImg());//银行卡图标
                map.put("receiveBank", bank.getReceivebank());//开户行
                map.put("cardMode", bank.getCardMode());//卡类
                map.put("KefuTel", bank.getKefuTel());//客服电话
                map.put("isDefault", bank.getIsDefault());//默认银行
                profitList.add(map);
            }
        }
        dataMap.put("profitList", profitList);
        logger.info("查询银行卡 queryBank ----------End--------");

        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 银行卡删除
     */
    @RequestMapping(value = "deleteBankCard")
    @ResponseBody
    public String deleteBankCard(HttpServletRequest request) {
        logger.info(" 银行卡删除 ---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String id = (String) jsonData.get("id");//获取主键
        String uid = (String) jsonData.get("uid");
        logger.info("+++++++++++++"+uid);
        CdLotteryUser user = cdLotteryUserService.get(uid); //查询当前登录用户

        if (user == null) {
            logger.error("当前用户不存在!");
            return HttpResultUtil.errorJson("当前用户不存在!");
        }
        if (user != null) {
            if (user.getIsBlock().equals("1")) {
                logger.error("当前用户已锁定，不允许操作!");
                return HttpResultUtil.errorJson("当前用户已锁定，不允许操作!");
            }
        }
        CdBankCard CdBankCard = cdBankCardService.get(id);
        if (CdBankCard == null) {
            logger.error("操作异常!");
            return HttpResultUtil.errorJson("操作异常!");
        }
        CdBankCard.setDelFlag("1");
        cdBankCardService.save(CdBankCard);
        Map dataMap = Maps.newHashMap();
        logger.info("pc：银行卡删除---------- End----------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 设置默认银行卡
     */
    @RequestMapping(value = "defaultCard")
    @ResponseBody
    public String defaultCard(HttpServletRequest request) {
        logger.info(" 默认银行卡 ---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String id = (String) jsonData.get("id");//获取银行卡号
        String uid = (String) jsonData.get("uid");
        logger.info("+++++++++++++"+uid);
        CdLotteryUser user = cdLotteryUserService.get(uid); //查询当前登录用户

        if (user == null) {
            logger.error("当前用户不存在!");
            return HttpResultUtil.errorJson("当前用户不存在!");
        }
        if (user != null) {
            if (user.getIsBlock().equals("1")) {
                logger.error("当前用户已锁定，不允许操作!");
                return HttpResultUtil.errorJson("当前用户已锁定，不允许操作!");
            }
        }
        if (id == null) {
            logger.error("id为空!");
            return HttpResultUtil.errorJson("id为空!");
        }
        //查询改用户下是否有已经设置成默认的银行卡
        List<CdBankCard> bankList = cdBankCardService.getDefaultCard(user.getId());
        if (bankList.size() > 0) {
            for (CdBankCard abank : bankList) {
                abank.setIsDefault("0");
                cdBankCardService.save(abank);
            }
        }
        //将此银行卡设置成默认银行卡 1
        CdBankCard bankDefault = cdBankCardService.getWillDefaultCard(id).get(0);
        bankDefault.setIsDefault("1");
        cdBankCardService.save(bankDefault);
        Map dataMap = Maps.newHashMap();
        logger.info("pc：默认银行卡---------- End----------");
        return HttpResultUtil.successJson(dataMap);
    }
    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    public String userMessage(HttpServletRequest request) {
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String uid = (String) jsonData.get("uid");
        logger.info("+++++++++++++"+uid);
        CdLotteryUser user = cdLotteryUserService.get(uid); //查询当前登录用户
        if (user == null) {
            logger.error("当前用户不存在!");
            return HttpResultUtil.errorJson("当前用户不存在!");
        }
        if (user != null) {
            if ("1".equals(user.getIsBlock())) {
                logger.error("当前用户已锁定，不允许操作!");
                return HttpResultUtil.errorJson("当前用户已锁定，不允许操作!");
            }
        }
        return null;
    }
}
