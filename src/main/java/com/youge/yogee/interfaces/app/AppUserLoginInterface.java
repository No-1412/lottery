package com.youge.yogee.interfaces.app;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.mapper.JsonMapper;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.FileUtil;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.vo.DataVo;
import com.youge.yogee.modules.lom.service.LomCodeService;
import com.youge.yogee.modules.sys.service.SystemService;
import com.youge.yogee.modules.usm.entity.UsmUser;
import com.youge.yogee.modules.usm.service.UsmUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("${publicPath}")
public class AppUserLoginInterface {
    private static final Logger logger = LoggerFactory.getLogger(AppUserLoginInterface.class);

    @Autowired
    private UsmUserService usmUserService;

    @Autowired
    private LomCodeService lomCodeService;

    /***
     * 验证手机号是否注册
     * @param request
     * @return
     */
    @RequestMapping(value = "userCheck", method = RequestMethod.POST)
    @ResponseBody
    public String userCheck(HttpServletRequest request) {
        logger.info("app userCheck---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String phone = (String) jsonData.get("phone");
        if (StringUtils.isEmpty(phone)) {
            return HttpResultUtil.errorJson("请填写手机号！");
        }

        Map mapData = new HashMap();

        Long count = this.usmUserService.getCountByPhone(phone);

        if (count.longValue() == 0) {
            // 0：未注册
            mapData.put("state", "0");
            return HttpResultUtil.successJson(mapData);
        } else {
            // 1：已注册
            mapData.put("state", "1");
            return HttpResultUtil.successJson(mapData);
        }
    }

    /**
     * 用户登录
     * @param request
     * @return
     */
    @RequestMapping(value = "userLogin", method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(HttpServletRequest request) {
        logger.info("app userLogin---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String phone = (String) jsonData.get("phone");
        if (StringUtils.isEmpty(phone)) {
            return HttpResultUtil.errorJson("请填写手机号!");
        }

        String passwd = (String) jsonData.get("passWord");
        if (StringUtils.isEmpty(passwd)) {
            return HttpResultUtil.errorJson("请填写密码!");
        }

        UsmUser userPhone = this.usmUserService.findByPhone(phone);
        if (null == userPhone) {
            logger.error("手机号："+phone+"未注册！");
            return HttpResultUtil.errorJson("账号或密码不正确!");
        } else{
            if (!SystemService.validatePassword(passwd, userPhone.getPwd())){
                logger.error("密码错误");
                return HttpResultUtil.errorJson("密码或账号不正确!");
            }

            //判断是否为被冻结账户，freeze字段为“1”表示当前用户被冻结
            if(("1").equals(userPhone.getFreeze())){
                return HttpResultUtil.errorJson("账号已被冻结，请联系客服!");
            }
        }

        Map dataMap = new HashMap();
        DataVo dataVo = new DataVo();
        dataVo.setUserId(userPhone.getId().toString());
        dataVo.setUserName(userPhone.getName());
        dataVo.setPhone(userPhone.getTelephone());
        dataVo.setAutograph(userPhone.getAutograph());
        dataVo.setCode(userPhone.getCode());
        dataVo.setSex(userPhone.getSex());
        dataVo.setProvince(userPhone.getProv());
        dataVo.setCity(userPhone.getCity());
        dataVo.setAddress(userPhone.getAddress());

        if (userPhone.getImg() != null) {
            if(userPhone.getImg().contains("http")){
                dataVo.setUserImg(userPhone.getImg().replace("|", ""));
            }else {
                dataVo.setUserImg(Global.getConfig("domain.name") + userPhone.getImg().replace("|", ""));
            }
        }else{
            dataVo.setUserImg(Global.getConfig("domain.name") + "/userfiles/1/images/bm/header/nan1.png");
        }

        //更新状态为在线
        userPhone.setOnline("0");
        usmUserService.save(userPhone);

        dataMap.put("user", dataVo);

        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 第三方登录
     * @param request
     * @return
     */
    @RequestMapping(value = "otherLogin", method = RequestMethod.POST)
    @ResponseBody
    public String otherLogin(HttpServletRequest request){

        logger.info("app otherLogin---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String openid = (String)jsonData.get("openid");
        if (StringUtils.isEmpty(openid)) {
            return HttpResultUtil.errorJson("第三方信息获取失败!");
        }

        String name = (String)jsonData.get("name");
        if (StringUtils.isEmpty(name)) {
            return HttpResultUtil.errorJson("第三方信息获取失败!");
        }

        String img = (String)jsonData.get("img");
        if (null == img) {
            return HttpResultUtil.errorJson("第三方信息获取失败!");
        }

        //1:微信；2：QQ； 3：微博
        String type = (String)jsonData.get("type");
        if (StringUtils.isEmpty(type)) {
            return HttpResultUtil.errorJson("第三方类型获取失败!");
        }

        Map dataMap = new HashMap();
        UsmUser user = null;

        if (type.equals("1")) {
            //有手机号注册并绑定微信的情况，暂时没有这个业务。
//            UsmUser user2 = this.usmUserService.findByOpenid(openid);
//            if ((null != user2) && ("1".equals(user2.getRegType())) && (null == user2.getWechatId())) {
//                user2.setWechatId(user2.getOpenid());
//                this.usmUserService.save(user2);
//            }
            user = this.usmUserService.getByWechat(openid);
        }else if (type.equals("2")) {
            user = this.usmUserService.getByQq(openid);
        }else if (type.equals("3")) {
            user = this.usmUserService.getBySina(openid);
        }

        if (null == user) {
            UsmUser userNew = new UsmUser();
            userNew.setName(name);
            if(null != img && !"".equals(img)){
                userNew.setImg(img);
            }else{
                userNew.setImg(Global.getConfig("domain.name") + "/userfiles/1/images/bm/header/nan1.png");
            }
            userNew.setRegType(type);

            if (type.equals("1")) {
                userNew.setWechatId(openid);
            }else if (type.equals("2")){
                userNew.setQqId(openid);
            }else if (type.equals("3")){
                userNew.setSinaId(openid);
            }

            userNew.setAutograph("说点什么，介绍一下你自己~");
            userNew.setPoint(Integer.valueOf(0));
            userNew.setMoney(Integer.valueOf(0));
            userNew.setDelFlag("0");
            userNew.setOnline("0");
            userNew.setCode(usmUserService.getInviteCode());//生成邀请码
            usmUserService.save(userNew);//保存信息
            user = userNew;//如果用户没用三方登陆过，将新用户的信息赋给空的用户对象。
        }else{
            //判断是否为被冻结账户，freeze字段为“1”表示当前用户被冻结
            if(("1").equals(user.getFreeze())){
                return HttpResultUtil.errorJson("账号已被冻结!");
            }
        }

        //处理返回数据
        DataVo dataVo = new DataVo();
        dataVo.setUserId(user.getId().toString());
        dataVo.setUserName(user.getName());
        dataVo.setPhone(user.getTelephone());
        dataVo.setAutograph(user.getAutograph());
        dataVo.setCode(user.getCode());
        dataVo.setSex(user.getSex());
        dataVo.setProvince(user.getProv());
        dataVo.setCity(user.getCity());
        dataVo.setAddress(user.getAddress());
        if (user.getImg() != null) {
            dataVo.setUserImg(user.getImg());
        }else{
            dataVo.setUserImg(Global.getConfig("domain.name") + "/userfiles/1/images/bm/header/nan1.png");
        }

        dataMap.put("user", dataVo);

        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 短信登录
     * @param request
     * @return
     */
    @RequestMapping(value = "smsLogin", method = RequestMethod.POST)
    @ResponseBody
    public String smsLogin(HttpServletRequest request) {

        logger.debug("app smsLogin---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String phone = (String) jsonData.get("phone");
        if (StringUtils.isEmpty(phone)) {
            return HttpResultUtil.errorJson("请填写手机号!");
        }

        String mobileCode = (String) jsonData.get("mobileCode");
        if (StringUtils.isEmpty(mobileCode)) {
//            logger.error("请填写短信验证码！");
//            return HttpResultUtil.errorJson("请填写短信验证码!");
        }

        //判断短信验证码
        if (StringUtils.isNotEmpty(mobileCode)) {
            String checkCode = lomCodeService.checkPhoneCode(phone, "0");
            if(StringUtils.isEmpty(checkCode)){
                logger.error("短信验证码已失效，请重新获取！");
                return HttpResultUtil.errorJson("短信验证码已失效，请重新获取!");
            }else if(!(mobileCode).equals(checkCode)){
                logger.error("短信验证码错误，请重新输入！");
                return HttpResultUtil.errorJson("短信验证码错误，请重新输入!");
            }
        }

        Map map = request.getParameterMap();
        Map<String, Object> mapData = new HashMap<String, Object>();
        UsmUser userPhone = usmUserService.findByPhone(phone);
        if (null == userPhone) {
            logger.error("手机号："+phone+"未注册！");
            return HttpResultUtil.errorJson("您的手机号未注册!");
        } else{
            //判断是否为被冻结账户，freeze字段为“1”表示当前用户被冻结
            if(("1").equals(userPhone.getFreeze())){
                return HttpResultUtil.errorJson("账号已被冻结，请联系客服!");
            }
        }

        Map dataMap = new HashMap();
        DataVo dataVo = new DataVo();
        dataVo.setUserId(userPhone.getId().toString());
        dataVo.setUserName(userPhone.getName());
        dataVo.setPhone(userPhone.getTelephone());
        dataVo.setAutograph(userPhone.getAutograph());
        dataVo.setCode(userPhone.getCode());
        dataVo.setSex(userPhone.getSex());
        dataVo.setProvince(userPhone.getProv());
        dataVo.setCity(userPhone.getCity());
        dataVo.setAddress(userPhone.getAddress());
        if (userPhone.getImg() != null) {
            if(userPhone.getImg().contains("http")){
                dataVo.setUserImg(userPhone.getImg().replace("|", ""));
            }else {
                dataVo.setUserImg(Global.getConfig("domain.name") + userPhone.getImg().replace("|", ""));
            }
        }else{
            dataVo.setUserImg(Global.getConfig("domain.name") + "/userfiles/1/images/bm/header/nan1.png");
        }

        //更新状态为在线
        userPhone.setOnline("0");
        usmUserService.save(userPhone);

        dataMap.put("user", dataVo);

        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 用户退出
     * @param request
     * @return
     */
    @RequestMapping(value = "userQuit", method = RequestMethod.POST)
    @ResponseBody
    public String userQuit(HttpServletRequest request) {
        logger.info("app userQuit---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String userId = (String) jsonData.get("userId");
        if (StringUtils.isEmpty(userId)) {
            return HttpResultUtil.errorJson("用户信息提取异常!");
        }

        UsmUser user = usmUserService.get(userId);
        if(null != user){
            user.setOnline("1");
            usmUserService.save(user);
        }

        Map dataMap = new HashMap();
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 忘记密码
     * @param request
     * @return
     */
    @RequestMapping(value = "forgetPassword", method = RequestMethod.POST)
    @ResponseBody
    public String forgetPassword(HttpServletRequest request) {

        logger.debug("app forgetPassword---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String phone = (String) jsonData.get("phone");
        if (StringUtils.isEmpty(phone)) {
            return HttpResultUtil.errorJson("请填写手机号!");
        }

        String mobileCode = (String) jsonData.get("mobileCode");
        if (StringUtils.isEmpty(mobileCode)) {
//            logger.error("请填写短信验证码！");
//            return HttpResultUtil.errorJson("请填写短信验证码!");
        }

        String newPassword = (String) jsonData.get("newPassword");
        if (StringUtils.isEmpty(newPassword)) {
            return HttpResultUtil.errorJson("请填写密码!");
        }

        //判断短信验证码
        if (StringUtils.isNotEmpty(mobileCode)) {
            String checkCode = lomCodeService.checkPhoneCode(phone, "0");
            if(StringUtils.isEmpty(checkCode)){
                logger.error("短信验证码已失效，请重新获取！");
                return HttpResultUtil.errorJson("短信验证码已失效，请重新获取!");
            }else if(!(mobileCode).equals(checkCode)){
                logger.error("短信验证码错误，请重新输入！");
                return HttpResultUtil.errorJson("短信验证码错误，请重新输入!");
            }
        }

        Map map = request.getParameterMap();
        Map<String, Object> mapData = new HashMap<String, Object>();
        UsmUser appuser = usmUserService.findByPhone(phone);

        String pwd = SystemService.entryptPassword(newPassword);

        if (null != appuser) {
            appuser.setPwd(pwd);
            usmUserService.save(appuser);
            return HttpResultUtil.successJson(mapData);
        } else {
            return HttpResultUtil.errorJson("您的用户名不存在!");
        }
    }

    /**
     * 验证用户密码
     * @param request
     * @return
     */
    @RequestMapping(value = "checkPassword", method = RequestMethod.POST)
    @ResponseBody
    public String checkPassword(HttpServletRequest request) {

        logger.debug("app checkPassword---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String userId = (String) jsonData.get("userId");
        if (StringUtils.isEmpty(userId)) {
            return HttpResultUtil.errorJson("用户不存在!");
        }

        String oldPassword = (String) jsonData.get("oldPassword");
        if (StringUtils.isEmpty(oldPassword)) {
            return HttpResultUtil.errorJson("请填写密码!");
        }

        Map map = request.getParameterMap();
        Map<String, Object> mapData = new HashMap<String, Object>();
        UsmUser appuser = usmUserService.get(userId);

        if (null == appuser) {
            return HttpResultUtil.errorJson("您的用户名不存在!");
        } else{
            if (!SystemService.validatePassword(oldPassword, appuser.getPwd())){
                return HttpResultUtil.errorJson("您的原始密码不正确!");
            }
        }
        Map dataMap = new HashMap();
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 修改密码
     * @param request
     * @return
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public String updatePassword(HttpServletRequest request) {

        logger.debug("app updatePassword---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String userId = (String) jsonData.get("userId");
        if (StringUtils.isEmpty(userId)) {
            return HttpResultUtil.errorJson("用户信息获取失败!");
        }

        String newPassword = (String) jsonData.get("newPassword");
        if (StringUtils.isEmpty(newPassword)) {
            return HttpResultUtil.errorJson("请填写密码!");
        }

        Map map = request.getParameterMap();
        Map<String, Object> mapData = new HashMap<String, Object>();
        UsmUser appuser = usmUserService.get(userId);

        String pwd = SystemService.entryptPassword(newPassword);

        if (null != appuser) {
            appuser.setPwd(pwd);
            usmUserService.save(appuser);
            return HttpResultUtil.successJson(mapData);
        } else {
            return HttpResultUtil.errorJson("您的用户名不存在!");
        }
    }

}