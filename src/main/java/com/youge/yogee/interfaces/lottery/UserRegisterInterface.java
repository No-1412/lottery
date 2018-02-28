package com.youge.yogee.interfaces.lottery;

import com.google.common.collect.Maps;
import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.cmessage.entity.CdMessage;
import com.youge.yogee.modules.cmessage.service.CdMessageService;
import com.youge.yogee.modules.sys.service.SystemService;
import com.youge.yogee.publicutils.AddUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wjc on 2017-12-13 0013.用户注册接口
 */
public class UserRegisterInterface {

    private static final Logger logger = LoggerFactory.getLogger(UserRegisterInterface.class);

    @Autowired
    private CdLotteryUserService cdLotteryUserService;

    @Autowired
    private CdMessageService cdMessageService;

    private BigDecimal INIT_MONEY = new BigDecimal("0");


    /**
     * anbo ，2017-10-23 ， 用户注册，获取短信验证码
     */
    @RequestMapping(value = "postRegisterMesage")
    @ResponseBody
    public String postRegisterMesage(HttpServletRequest request) {

        logger.info("pc：注册发送短信验证码---------- Start----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String phone = (String) jsonData.get("phone");//    手机号码

        if (StringUtils.isEmpty(phone)) {
            logger.error("手机号不能为空!");
            return HttpResultUtil.errorJson("手机号码不能为空!");
        }
        //查询当前今天已经收到多少验证码,超过20条则不让继续发送
        List listCount = cdMessageService.getCount(phone, DateUtils.getDate());
        if (Integer.valueOf((listCount.get(0)).toString()) >= Global.MESSAGE_COUNT) {
            logger.error("您接收的验证码已超过规定条数!");
            return HttpResultUtil.errorJson("您接收的验证码已超过规定条数!");
        }
        /**********************************************需要等待短信工具********************************************************/
        /*MessageXSendDemo messageXSendDemo = new MessageXSendDemo();
        Map map = messageXSendDemo.sendMessage(phone);
        AddUtils.addMessage(cdMessageService , phone, map.get("content").toString(),
                "0", Global.EFFECTTIVE_TIME_SECOND, "0", map.get("contentCode").toString());*/

        Map dataMap = new HashMap();

        logger.info("pc：注册发送短信验证码---------- End----------");

        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * anbo，2017-09-28 ，用户注册
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "userRegister", method = RequestMethod.POST)
    @ResponseBody
    public String userRegister(HttpServletRequest request) {
        logger.info("pc：用户注册userRegister---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String phone = (String) jsonData.get("phone");//手机号
        String passWord = (String) jsonData.get("passWord");//密码

        String mobileCode = (String) jsonData.get("mobileCode");//短信验证码


        if (StringUtils.isEmpty(phone)) {
            logger.error("请填写手机号!");
            return HttpResultUtil.errorJson("请填写手机号!");
        }

        //判断手机号是否存在，避免重复提交。
        CdLotteryUser abUser = cdLotteryUserService.findByPhone(phone);
        if (abUser != null) {
            return HttpResultUtil.errorJson("当前手机号已注册!");
        }

        if (StringUtils.isEmpty(passWord)) {
            logger.error("请填写密码!");
            return HttpResultUtil.errorJson("请填写密码!");
        }

        if (StringUtils.isEmpty(mobileCode)) {
            logger.error("请填写短信验证码！");
            return HttpResultUtil.errorJson("请填写短信验证码!");
        }

        String str = pdMobileCode(phone, mobileCode);//判断短信验证码是否失效及是否正确

        if (str != null) {
            return str;
        }

        Map dataMap = Maps.newHashMap();

        //对密码进行加密
        String pwd = SystemService.entryptPassword(passWord);

        //初始化用户信息并保存
        CdLotteryUser cdLotteryUser = new CdLotteryUser();
        cdLotteryUser.setName("lottery" + phone.substring(7, 11));
        cdLotteryUser.setAccount("lottery" + phone.substring(7, 11));
        cdLotteryUser.setMobile(phone);
        cdLotteryUser.setPassword(pwd);
        cdLotteryUser.setIsEmailVerified("0");
        cdLotteryUser.setIsMobileVerified("0");
        cdLotteryUser.setIsRealNameVerified("0");
        cdLotteryUser.setIsBankVerified("0");
        cdLotteryUser.setIsBlock("1");//首先是锁定状态（等会员充值之后在改为未锁定状态）
        cdLotteryUser.setIsRecharge("0");//未充值
        cdLotteryUser.setMemberType("2");
        cdLotteryUser.setMemberLevel("0");
        cdLotteryUser.setScore(new BigDecimal("0"));
        cdLotteryUser.setBalance(INIT_MONEY);
        cdLotteryUser.setFreeze(INIT_MONEY);
        cdLotteryUser.setTxFreeze(INIT_MONEY);
        cdLotteryUser.setFrzeeScore(INIT_MONEY);
        cdLotteryUser.setIsRegisterCharge("0");
        cdLotteryUser.setIsPay("0");
        cdLotteryUser.setRealityCount(0);
        cdLotteryUserService.save(cdLotteryUser);

        dataMap.put("uid", AddUtils.createBigSmallLetterStrOrNumberRadom(4) + cdLotteryUser.getId());

        logger.info("pc：用户注册userRegister---------- End----------");

        return HttpResultUtil.successJson(dataMap);
    }


    //判断注册验证是否正确及是否失效
    public String pdMobileCode(String phone, String mobileCode) {

        List<CdMessage> listCodeAndDate = cdMessageService.getMessage(phone);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        if (listCodeAndDate != null && listCodeAndDate.size() > 0) {
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
                if (!mobileCode.equals(listCodeAndDate.get(0).getCode())) {
                    logger.error("短信验证码错误，请重新输入！");
                    return HttpResultUtil.errorJson("短信验证码错误，请重新输入!");
                }
                //变更验证码为已经校验
                listCodeAndDate.get(0).setCheckZt("1");
                cdMessageService.save(listCodeAndDate.get(0));
            }
        } else {
            logger.error("请先获取短信验证码!");
            return HttpResultUtil.errorJson("请先获取短信验证码!");
        }
        return null;

    }
}
