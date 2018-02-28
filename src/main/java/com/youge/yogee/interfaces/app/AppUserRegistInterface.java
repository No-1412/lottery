package com.youge.yogee.interfaces.app;

import com.google.common.collect.Maps;
import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.vo.DataVo;
import com.youge.yogee.modules.lom.entity.LomPoint;
import com.youge.yogee.modules.lom.service.LomCodeService;
import com.youge.yogee.modules.lom.service.LomPointService;
import com.youge.yogee.modules.sys.service.SystemService;
import com.youge.yogee.modules.usm.entity.UsmInvite;
import com.youge.yogee.modules.usm.entity.UsmUser;
import com.youge.yogee.modules.usm.service.UsmInviteService;
import com.youge.yogee.modules.usm.service.UsmUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("${publicPath}")
public class AppUserRegistInterface {
    private static final Logger logger = LoggerFactory.getLogger(AppUserRegistInterface.class);

    @Autowired
    private UsmUserService usmUserService;

    @Autowired
    private UsmInviteService usmInviteService;

    @Autowired
    private LomCodeService lomCodeService;

    @Autowired
    private LomPointService lomPointService;

    /**
     * 用户注册
     * @param request
     * @return
     */
    @RequestMapping(value = "newUserRegistration", method = RequestMethod.POST)
    @ResponseBody
    public String newUserRegistration(HttpServletRequest request) {
        logger.info("app newUserRegistration---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String phone = (String) jsonData.get("phone");
        if (StringUtils.isEmpty(phone)) {
            logger.error("请填写手机号！");
            return HttpResultUtil.errorJson("请填写手机号!");
        }

        String passWord = (String) jsonData.get("passWord");
        if (StringUtils.isEmpty(passWord)) {
            logger.error("请填写密码！");
            return HttpResultUtil.errorJson("请填写密码!");
        }

        String mobileCode = (String) jsonData.get("mobileCode");
        if (StringUtils.isEmpty(mobileCode)) {

//            logger.error("请填写短信验证码！");
//            return HttpResultUtil.errorJson("请填写短信验证码!");
        }

        String code = (String) jsonData.get("code");
        if (StringUtils.isEmpty(code)) {
            code = "";
        }else{
            code = code.toUpperCase();
        }

        //判断手机号是否存在，避免重复提交。
        UsmUser userCheck = usmUserService.findByPhone(phone);
        if (null != userCheck) {
            return HttpResultUtil.errorJson("当前手机号已注册!");
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

        Map dataMap = Maps.newHashMap();

        //随机姓名
        String charE = "abcdefghijklmnopqrstuvwxyz";
        String charN = "1234567890";
        String randomName = "";
        for (int i = 5; i > randomName.length(); --i) {
            randomName = randomName + charE.charAt((int) (Math.random() * 26));
        }

        for (int i = 10; i > randomName.length(); --i) {
            randomName = randomName + charN.charAt((int) (Math.random() * 10));
        }

        //对密码进行加密
        String pwd = SystemService.entryptPassword(passWord);

        //初始化用户信息并保存
        UsmUser user = new UsmUser();
        user.setName(randomName);
        user.setTelephone(phone);
        user.setPwd(pwd);
        user.setRegType("0");//默认手机注册
        user.setImg("/userfiles/1/images/bm/header/nan1.png");
        user.setAutograph("说点什么，介绍一下你自己~");
        user.setCode(usmUserService.getInviteCode());
        this.usmUserService.save(user);

        //用户邀请处理-不传code不进行此部分逻辑处理
        if (StringUtils.isNotEmpty(code)) {
            UsmUser inviter = usmUserService.findByCode(code);
            if(null != inviter){
                //保存粉丝关系
                UsmInvite inv = new UsmInvite();
                inv.setInviter(inviter);//邀请人id
                inv.setInvitee(user);//受邀人id
                usmInviteService.save(inv);

                //更新邀请人积分+10
                inviter.setPoint(inviter.getPoint() + 10);
                usmUserService.save(inviter);

                //保存积分记录
                LomPoint point = new LomPoint();
                point.setUserId(inviter);
                point.setInvitee(user);
                point.setKind("0");
                point.setName("您从‘用户名称’中获取10积分");//用户信息一致在变，所以用‘用户名称’替代，在显示前进行替换。
                point.setRemarks("");
                point.setStates("0");
                lomPointService.save(point);
            }
        }

        return HttpResultUtil.successJson(dataMap);
    }


}