package com.youge.yogee.interfaces.lottery.user;

import com.google.common.collect.Maps;
import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.CacheUtils;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.emay.emay.Example;
import com.youge.yogee.interfaces.util.FileUtil;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.cmessage.service.CdMessageService;
import com.youge.yogee.modules.sys.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by wjc on 2017-12-13 0013.用户注册接口
 */
@Controller
@RequestMapping("${frontPath}")
public class UserRegisterInterface {

    private static final Logger logger = LoggerFactory.getLogger(UserRegisterInterface.class);

    @Autowired
    private CdLotteryUserService cdLotteryUserService;

    @Autowired
    private CdMessageService cdMessageService;

    private BigDecimal INIT_MONEY = new BigDecimal("0");


    /**
     * 获取短信验证码
     */
    @RequestMapping(value = "postRegisterMessage")
    @ResponseBody
    public String postRegisterMessage(HttpServletRequest request) {
        Map dataMap = new HashMap();
        logger.info("pc：注册发送短信验证码-postRegisterMessage--------- Start----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String phone = (String) jsonData.get("phone");//    手机号码


        if (StringUtils.isEmpty(phone)) {
            logger.error("手机号不能为空!");
            return HttpResultUtil.errorJson("手机号码不能为空!");
        }
        //   1注册 2忘记密码  3修改密码 4修改手机号
        String type = (String) jsonData.get("type");
        if (StringUtils.isEmpty(type)) {
            logger.error("type为空!");
            return HttpResultUtil.errorJson("type为空!");
        }
        //生成短信6位随机验证码
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += random.nextInt(10);
        }
        try {
            if ("1".equals(type)) {
                CacheUtils.put("registeredCode", phone, code);
            } else if ("2".equals(type)) {
                CacheUtils.put("forgetPwdCode", phone, code);
            } else if ("3".equals(type)) {
                CacheUtils.put("changePwdCode", phone, code);
            } else {
                CacheUtils.put("changePhoneCode", phone, code);
            }

            Example.sendCode(code, phone);
            dataMap.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResultUtil.errorJson("验证码发送失败");
        }


        logger.info("pc：注册发送短信验证码---postRegisterMessage------- End----------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 用户注册
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
        if (StringUtils.isEmpty(phone)) {
            logger.error("请填写手机号!");
            return HttpResultUtil.errorJson("请填写手机号!");
        }

        //判断手机号是否存在，避免重复提交。
        CdLotteryUser abUser = cdLotteryUserService.findByPhone(phone);
        if (abUser != null) {
            return HttpResultUtil.errorJson("当前手机号已注册!");
        }
        String passWord = (String) jsonData.get("passWord");//密码
        if (StringUtils.isEmpty(passWord)) {
            logger.error("请填写密码!");
            return HttpResultUtil.errorJson("请填写密码!");
        }
        String mobileCode = (String) jsonData.get("mobileCode");//短信验证码
        if (StringUtils.isEmpty(mobileCode)) {
            logger.error("请填写短信验证码！");
            return HttpResultUtil.errorJson("请填写短信验证码!");
        }

        String saleId = (String) jsonData.get("saleId");//销售邀请码
        if (StringUtils.isEmpty(mobileCode)) {
            logger.error("saleId为空！");
            return HttpResultUtil.errorJson("saleId为空!");
        }

        Object code = CacheUtils.get("registeredCode", phone);
        if (!code.equals(mobileCode)) {
            return HttpResultUtil.errorJson("验证码错误！");
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
        cdLotteryUser.setSaleId(saleId);
        cdLotteryUser.setTotalRecharge("0.00");
        cdLotteryUser.setTotalPay("0.00");
        cdLotteryUser.setRebate("0.00");
        cdLotteryUserService.save(cdLotteryUser);

        dataMap.put("uid", cdLotteryUser.getId());

        logger.info("pc：用户注册userRegister---------- End----------");

        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 用户登录
     */
    @RequestMapping(value = "userLogin", method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(HttpServletRequest request) {
        logger.info("pc：用户登录userLogin---------- Start-----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map dataMap = new HashMap();
        String phone = (String) jsonData.get("phone");
        if (StringUtils.isEmpty(phone)) {
            return HttpResultUtil.errorJson("请填写手机号!");
        }
        String passwd = (String) jsonData.get("passWord");
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


        dataMap.put("uid", user.getId());
        dataMap.put("img", user.getImg());
        dataMap.put("level", user.getMemberLevel());
        dataMap.put("name", user.getName());
        dataMap.put("phone", user.getMobile());
        logger.info("pc：用户登录userLogin---------- End----------");
        return HttpResultUtil.successJson(dataMap);

    }


    /**
     * 修改密码 忘记密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "userChangePwd", method = RequestMethod.POST)
    @ResponseBody
    public String userChangePwd(HttpServletRequest request) {
        logger.info("pc：修改密码userChangePwd---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String phone = (String) jsonData.get("phone");//手机号
        if (StringUtils.isEmpty(phone)) {
            logger.error("请填写手机号!");
            return HttpResultUtil.errorJson("请填写手机号!");
        }
        //2忘记密码  3修改密码
        String type = (String) jsonData.get("type");
        if (StringUtils.isEmpty(phone)) {
            logger.error("type为空!");
            return HttpResultUtil.errorJson("type为空!");
        }

        String mobileCode = (String) jsonData.get("mobileCode");//短信验证码
        if (StringUtils.isEmpty(mobileCode)) {
            logger.error("请填写短信验证码！");
            return HttpResultUtil.errorJson("请填写短信验证码!");
        }

        String passWord = (String) jsonData.get("passWord");//手机号
        if (StringUtils.isEmpty(passWord)) {
            logger.error("passWord为空");
            return HttpResultUtil.errorJson("passWord为空!");
        }

        Object code = "";
        if ("2".equals(type)) {
            code = CacheUtils.get("forgetPwdCode", phone);
        }
        if ("3".equals(type)) {
            code = CacheUtils.get("changePwdCode", phone);
        }
        if (!code.equals(mobileCode)) {
            return HttpResultUtil.errorJson("验证码错误！");
        }

        Map dataMap = Maps.newHashMap();

        //对密码进行加密
        String newPwd = SystemService.entryptPassword(passWord);
        CdLotteryUser cdLotteryUser = cdLotteryUserService.findByPhone(phone);
        if (cdLotteryUser == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        cdLotteryUser.setPassword(newPwd);
        cdLotteryUserService.save(cdLotteryUser);

        dataMap.put("uid", cdLotteryUser.getId());

        logger.info("pc：修改密码userChangePwd---------- End----------");

        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 更换手机
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "userChangePhone", method = RequestMethod.POST)
    @ResponseBody
    public String userChangePhone(HttpServletRequest request) {
        logger.info("pc：userChangePhone---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        //新手机号
        String phone = (String) jsonData.get("phone");
        if (StringUtils.isEmpty(phone)) {
            logger.error("phone为空！");
            return HttpResultUtil.errorJson("phone为空!");
        }
        //验证码
        String mobileCode = (String) jsonData.get("mobileCode");
        if (StringUtils.isEmpty(mobileCode)) {
            logger.error("请填写短信验证码！");
            return HttpResultUtil.errorJson("请填写短信验证码!");
        }
        //缓存获取验证码
        Object code = CacheUtils.get("changePhoneCode", phone);
        if (!code.equals(mobileCode)) {
            return HttpResultUtil.errorJson("验证码错误！");
        }

        Map dataMap = Maps.newHashMap();


        CdLotteryUser cdLotteryUser = cdLotteryUserService.findByPhone(phone);
        if (cdLotteryUser != null) {
            return HttpResultUtil.errorJson("该手机号已被注册");
        }
        cdLotteryUser.setMobile(phone);
        cdLotteryUserService.save(cdLotteryUser);

        dataMap.put("uid", cdLotteryUser.getId());

        logger.info("pc：userChangePhone---------- End----------");

        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 更改信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "userChangeInformation", method = RequestMethod.POST)
    @ResponseBody
    public String userChangeInformation(HttpServletRequest request) {
        logger.info("pc：userChangeInformation---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        //用户id
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空！");
            return HttpResultUtil.errorJson("uid为空!");
        }
        CdLotteryUser cdLotteryUser = cdLotteryUserService.get(uid);
        if (cdLotteryUser == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        //新昵称
        String name = (String) jsonData.get("name");
        if (StringUtils.isNotEmpty(name)) {
            cdLotteryUser.setName(name);
        }
        //头像
        String img = (String) jsonData.get("img");
        if (StringUtils.isNotEmpty(img)) {
            cdLotteryUser.setImg(img);
        }

        Map dataMap = Maps.newHashMap();
        cdLotteryUserService.save(cdLotteryUser);

        dataMap.put("uid", cdLotteryUser.getId());

        logger.info("pc：userChangeInformation---------- End----------");

        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 更改用户头像
     *
     * @param request 头像请求
     * @return 头像url地址
     */
    @RequestMapping(value = "updateUserImg", method = RequestMethod.POST)
    @ResponseBody
    public String updateUserImg(HttpServletRequest request) {

        logger.debug("app updateUserImg---------- Start--------");
        Map<String, Object> mapData = new HashMap<>();
        String fileNames = "";
        boolean flag = FileUtil.isMultipatr(request);
        if (flag) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            fileNames = FileUtil.fileUploadUnused(multiRequest, "/userfiles/1/images/header/");
        } else {
            return HttpResultUtil.errorJson("图片上传异常");
        }


        mapData.put("img", Global.getConfig("domain.name") + fileNames.replace("|", ""));

        return HttpResultUtil.successJson(mapData);
    }


}
