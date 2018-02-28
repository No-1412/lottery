package com.youge.yogee.interfaces.lottery;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.cmessage.service.CdMessageService;
import com.youge.yogee.modules.sys.service.SystemService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wjc on 2017-12-13 0013.用户登录
 */
@Controller
@RequestMapping("${frontPath}")
public class UserLoginInterface {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginInterface.class);

    @Autowired
    private CdLotteryUserService cdLotteryUserService;

    @Autowired
    private CdMessageService cdMessageService;

    /***
     * anbo，2017-09-28，验证手机号是否注册
     */
    @RequestMapping(value = "userCheckPhone", method = RequestMethod.POST)
    @ResponseBody
    public String userCheckPhone(HttpServletRequest request) {
        logger.info("pc：用户登录，校验手机是否注册userCheckPhone---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String phone = (String) jsonData.get("mobile");

        if (StringUtils.isEmpty(phone)) {
            return HttpResultUtil.errorJson("请填写手机号!");
        }

        Map mapData = new HashMap();

        Long count = this.cdLotteryUserService.getCountByPhone(phone);

        if (count.longValue() == 0) {
            // 0：未注册
            mapData.put("state", "0");
        } else {
            // 1：已注册
            mapData.put("state", "1");
        }
        logger.info("pc：用户登录，校验手机是否注册userCheckPhone---------- End--------");
        return HttpResultUtil.successJson(mapData);
    }



    /**
     * anbo，2017-09-28，用户登录
     */
    @RequestMapping(value = "userLogin", method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(HttpServletRequest request) {
        logger.info("pc：用户登录userLogin---------- Start-----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String phone = (String) jsonData.get("phone");
        String passwd = (String) jsonData.get("passWord");

        if (StringUtils.isEmpty(phone)) {
            return HttpResultUtil.errorJson("请填写手机号!");
        }

        if (StringUtils.isEmpty(passwd)) {
            return HttpResultUtil.errorJson("请填写密码!");
        }

        CdLotteryUser user = cdLotteryUserService.findByPhone(phone);
        if (user == null) {
            logger.error("手机号：" + phone + "未注册!");
            return HttpResultUtil.errorJson("账号或密码不正确!");
        } else {
            if (!SystemService.validatePassword(passwd, user.getPassword())) {
                logger.error("密码错误");
                return HttpResultUtil.errorJson("账号或密码不正确!");
            }

            //判断是否为被锁定账户，isBlock字段为“1”表示当前用户被锁定
//            if (("1").equals(user.getIsBlock())) {
//                return HttpResultUtil.errorJson("账号已被锁定!");
//            }
//            if("0".equals(user.getIsRegisterCharge())){
//                return HttpResultUtil.errorJson("请先充值注册费用!");
//            }
        }

        Map dataMap = new HashMap();

        dataMap.put("uid", AddUtils.createBigSmallLetterStrOrNumberRadom(4) + user.getId());

        logger.info("pc：用户登录userLogin---------- End----------");
        return HttpResultUtil.successJson(dataMap);

    }
    /**
     * 忘记密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "forgetPassword", method = RequestMethod.POST)
    @ResponseBody
    public String forgetPassword(HttpServletRequest request) {

        logger.debug("app forgetPassword---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String mobile = (String) jsonData.get("mobile");
        String newPassword = (String) jsonData.get("newPassword");
        String code = (String) jsonData.get("code");
        if (StringUtils.isEmpty(mobile)) {
            return HttpResultUtil.errorJson("请填写手机号!");
        }
        Map<String, Object> mapData = new HashMap<String, Object>();
        CdLotteryUser shareUser = cdLotteryUserService.findByPhone(mobile);
        if (null != shareUser) {
            if (StringUtils.isEmpty(code)) {
                logger.error("请填写短信验证码！");
                return HttpResultUtil.errorJson("请填写短信验证码!");
            }

            if (StringUtils.isEmpty(newPassword)) {
                return HttpResultUtil.errorJson("请填写密码!");
            }
            List listCodeAndDate = cdMessageService.findDateAndCode(mobile);
            Object[] codeDate = (Object[]) listCodeAndDate.get(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(codeDate[0].toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long second = (System.currentTimeMillis() - date.getTime()) / (1000);
            if (second > Long.valueOf(Global.EFFECTTIVE_TIME_SECOND) ) {
                logger.error("短信验证码已失效，请重新获取！");
                return HttpResultUtil.errorJson("短信验证码已失效，请重新获取!");
            } else {
                //判断短信验证码 如果不相同返回错误信息
                if (!code.equals(codeDate[1].toString())) {
                    logger.error("短信验证码错误，请重新输入！");
                    return HttpResultUtil.errorJson("短信验证码错误，请重新输入!");
                }
            }

            //在数据库中对新密码进行加密

            String pwd=SystemService.entryptPassword(newPassword);

            shareUser.setPassword(pwd);

            cdLotteryUserService.save(shareUser);
            return HttpResultUtil.successJson(mapData);
        } else {
            return HttpResultUtil.errorJson("您的用户名不存在!");
        }
    }

}
