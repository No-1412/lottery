package com.youge.yogee.interfaces.app;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.mapper.JsonMapper;
import com.youge.yogee.common.utils.AES256EncryptionUtils;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.FileUtil;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.SMSUtil;
import com.youge.yogee.modules.bm.entity.BmFeedback;
import com.youge.yogee.modules.bm.service.BmFeedbackService;
import com.youge.yogee.modules.lom.entity.LomCode;
import com.youge.yogee.modules.lom.entity.LomCollect;
import com.youge.yogee.modules.lom.service.LomCodeService;
import com.youge.yogee.modules.lom.service.LomCollectService;
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
public class AppUserOperateInterface {
    private static final Logger logger = LoggerFactory.getLogger(AppUserOperateInterface.class);

    @Autowired
    private UsmUserService usmUserService;

    @Autowired
    private LomCollectService lomCollectService;

    @Autowired
    private LomCodeService lomCodeService;

    @Autowired
    private BmFeedbackService bmFeedbackService;

    /**
     * 更新用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public String updateUserInfo(HttpServletRequest request) {

        logger.debug("app updateUserInfo---------- Start--------");

        Map map = request.getParameterMap();
        String str =request.getParameter("data");
        String userId = "";//用户id
        String name = "";//昵称
        String autograph = "";//签名
        String sex = "";//性别（0：女；1：男）
        String prov = "";//省份
        String city = "";//城市
        String address = "";//地址
        String year = DateUtils.getYear();
        String month = DateUtils.getMonth();
        if (str == null || str.length() == 0) {
            //ios
            userId =request.getParameter("userId");
            name =request.getParameter("name");
            autograph =request.getParameter("autograph");
            sex =request.getParameter("sex");
            prov =request.getParameter("province");
            city =request.getParameter("city");
            address =request.getParameter("address");
        }else{
            //android
            logger.debug("data-----------------------"+str);

            JSONObject jsStr = JSONObject.parseObject(str);
//            Map<?, ?> jsonData  = Maps.newHashMap();

//            if(str.contains("{")){
//                jsonData  = JsonMapper.nonDefaultMapper().fromJson(str, HashMap.class);
//            }else{
//                str= AES256EncryptionUtils.decrypt(str);
//                if(null ==str){
//                    return null;
//                }else{
//                    jsonData  = JsonMapper.nonDefaultMapper().fromJson(str, HashMap.class);
//                }
//            }

            userId = (String) jsStr.get("userId");
            name = (String) jsStr.get("name");
            autograph = (String) jsStr.get("autograph");
            sex = (String) jsStr.get("sex");
            prov = (String) jsStr.get("province");
            city = (String) jsStr.get("city");
            address = (String) jsStr.get("address");
        }

        UsmUser appuser = usmUserService.get(userId);
        if (null == appuser) {
            logger.error("用户不存在！");
            return HttpResultUtil.errorJson("用户不存在!");
        } else{
            //判断是否为被冻结账户，freeze字段为“1”表示当前用户被冻结
            if(("1").equals(appuser.getFreeze())){
                return HttpResultUtil.errorJson("账号已被冻结，请联系客服!");
            }
        }

        String fileNames= "";//头像
        boolean flag = FileUtil.isMultipatr(request);
        if(flag){
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            fileNames = FileUtil.fileUploadUnused(multiRequest, "/userfiles/1/images/usm/info/"+year+"/"+month+"/");
        }

        //传头像了，进行修改。
        if(StringUtils.isNotEmpty(fileNames)){
            appuser.setImg(fileNames.replace("|",""));
        }
        //头像为空，设置默认。
        if(StringUtils.isEmpty(appuser.getImg())){
            appuser.setImg("/userfiles/1/images/usm/info/2017/04/nan1.png");
        }

        appuser.setName(name);
        appuser.setAutograph(autograph);
        appuser.setSex(sex);
        appuser.setProv(prov);
        appuser.setCity(city);
        appuser.setAddress(address);

        Map<String, Object> mapData = new HashMap<>();
        if (null != appuser) {
            usmUserService.save(appuser);
            mapData.put("img", Global.getConfig("domain.name")+appuser.getImg());
            return HttpResultUtil.successJson(mapData);
        } else {
            return HttpResultUtil.errorJson("登陆信息缺失，更新失败!");
        }
    }


    /**
     * 更改用户头像
     * @param request
     * @return
     */
    @RequestMapping(value = "updateUserImg", method = RequestMethod.POST)
    @ResponseBody
    public String updateUserImg(HttpServletRequest request) {

        logger.debug("app updateUserImg---------- Start--------");

        Map map = request.getParameterMap();
        String[] data = (String[]) map.get("data");
        String userId = "";
        if (data == null || data.length == 0) {
            userId = request.getParameter("userId");
            if (StringUtils.isEmpty(userId)) {
                return HttpResultUtil.errorJson("登陆信息缺失，更新失败!");
            }
        } else {
            userId = (String) JsonMapper.nonDefaultMapper().fromJson(data[0], HashMap.class).get("userId");
        }

        String fileNames = StringUtils.EMPTY;
        boolean flag = FileUtil.isMultipatr(request);
        if (flag) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            fileNames = FileUtil.fileUploadUnused(multiRequest, "/userfiles/1/images/usm/user/");
        }

        UsmUser appuser = usmUserService.get(userId);
        appuser.setImg(fileNames.replace("|", ""));

        Map<String, Object> mapData = new HashMap<>();

        if (null != appuser) {
            usmUserService.save(appuser);
            mapData.put("img", Global.getConfig("domain.name") + fileNames.replace("|", ""));
            return HttpResultUtil.successJson(mapData);
        } else {
            return HttpResultUtil.errorJson("登陆信息缺失，更新失败!");
        }
    }

    /**
     * 用户收藏信息
     * @param request
     * @return
     */
    @RequestMapping(value = "userCollection", method = RequestMethod.POST)
    @ResponseBody
    public String userCollection(HttpServletRequest request) {
        logger.info("app userCollection---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String userId = (String) jsonData.get("userId");
        if (StringUtils.isEmpty(userId)) {
            logger.error("用户信息获取失败！");
            return HttpResultUtil.errorJson("用户信息提取异常!");
        }

        String kind = (String) jsonData.get("type");
        if (StringUtils.isEmpty(kind)) {
            kind= "0";
//            logger.error("请选择分类！");
//            return HttpResultUtil.errorJson("请选择分类!");
        }

        String belongId = (String) jsonData.get("belongId");
        if (StringUtils.isEmpty(belongId)) {
            logger.error("收藏信息获取失败！");
            return HttpResultUtil.errorJson("收藏信息获取失败!");
        }

        //根据用户id+信息id+类型，查询是否有数据存在。
        LomCollect collect = lomCollectService.findBybelongIdAndUserid(belongId, userId, kind);

        Map dataMap = Maps.newHashMap();
        if("0".equals(kind)){//分类 0：帖子

            //获取帖子信息，需要对收藏数做处理
//            UsmNote note = usmNoteService.get(belongId);
//            if (null == note) {
//                logger.error("收藏信息获取失败！");
//                return HttpResultUtil.errorJson("收藏信息获取失败!");
//            }
            if (null == collect) {
                //收藏数据为 null，未收藏过，新建。
                LomCollect collect1 = new LomCollect();
                collect1.setBelongId(belongId);
                collect1.setUserId(userId);
                collect1.setKind("0");
                lomCollectService.save(collect1);
                //帖子收藏数+1
//                note.setCollect(note.getCollect() + 1);
                dataMap.put("state","0");
            }else{
                collect.setKind("0");//没啥用
                if("0".equals(collect.getDelFlag())){
                    //之前是收藏状态，再次点击改为取消收藏状态。
                    collect.setDelFlag("1");
                    //帖子收藏数-1
//                    note.setCollect(note.getCollect() - 1);
                    dataMap.put("state","1");
                }else{
                    //之前是取消收藏状态，再次点击改为收藏状态。
                    collect.setDelFlag("0");
                    //帖子收藏数+1
//                    note.setCollect(note.getCollect() + 1);
                    dataMap.put("state","0");
                }
                //保存收藏记录
                lomCollectService.save(collect);
            }
            //保存帖子信息（收藏数已改变）
//            usmNoteService.save(note);
        }
        //state(0：收藏；1：取消收藏)
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 用户意见反馈
     * @param request
     * @return
     */
    @RequestMapping(value = "userFeedback", method = RequestMethod.POST)
    @ResponseBody
    public String userFeedback(HttpServletRequest request) {
        logger.info("app userFeedback---------- Start--------");

        Map map = request.getParameterMap();
        String str =request.getParameter("data");
        String userId = "";
        String content = "";
        String year = DateUtils.getYear();
        String month = DateUtils.getMonth();
        if (str == null || str.length() == 0) {
            //ios
            userId =request.getParameter("userId");
            content =request.getParameter("content");
        }else{
            //android
            logger.debug("data-----------------------"+str);
            Map<?, ?> jsonData  = Maps.newHashMap();

            if(str.contains("{")){
                jsonData  = JsonMapper.nonDefaultMapper().fromJson(str, HashMap.class);
            }else{
                str= AES256EncryptionUtils.decrypt(str);
                if(null ==str){
                    return null;
                }else{
                    jsonData  = JsonMapper.nonDefaultMapper().fromJson(str, HashMap.class);
                }
            }

            userId = (String) jsonData.get("userId");
            content = (String) jsonData.get("content");
        }

        //校验必要参数
        if (StringUtils.isEmpty(userId)) {
            logger.error("用户信息获取失败！");
            return HttpResultUtil.errorJson("用户信息获取失败!");
        }
        if (StringUtils.isEmpty(content)) {
            logger.error("请填写反馈内容！");
            return HttpResultUtil.errorJson("请填写反馈内容!");
        }

        //如果所有参数都正确，再上传文件
        String fileNames= StringUtils.EMPTY;
        boolean flag = FileUtil.isMultipatr(request);
        if(flag){
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            if(StringUtils.isNotEmpty(multiRequest.getFileNames().toString())){
                fileNames = FileUtil.fileUploadUnused(multiRequest, "userfiles/1/images/bm/feedback/"+year+"/"+month+"/");
            }
        }

        Map dataMap = Maps.newHashMap();

        BmFeedback feedback = new BmFeedback();
        feedback.setUserId(usmUserService.get(userId));
        feedback.setContent(content);
        feedback.setImgs(fileNames);
        bmFeedbackService.save(feedback);
//        LomMsg msg = new LomMsg();
//        msg.setUserId(usmUserService.get(userId));
//        msg.setKind("0");
//        msg.setBelongId("");
//        msg.setContent(content);
//        lomMsgService.save(msg);

        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 发送短信验证码
     * @param request
     * @return
     */
    @RequestMapping(value = "sendSMS", method = RequestMethod.POST)
    @ResponseBody
    public String sendSMS(HttpServletRequest request) {
        logger.info("app sendSMS---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String phone = (String) jsonData.get("phone");
        if (StringUtils.isEmpty(phone)) {
            logger.error("未获取到手机号！");
            return HttpResultUtil.errorJson("手机号不能为空!");
        }

        String type = (String) jsonData.get("type");//（0：注册验证；1：修改密码；2：短信登录；）
        if (StringUtils.isEmpty(type)) {
            logger.error("未获取到类别！");
            return HttpResultUtil.errorJson("短信验证码发送失败!");
        }
        boolean checkPhone = usmUserService.checkTelephone(phone);
        if("0".equals(type) && !checkPhone){
            logger.error("手机号码已注册！");
            return HttpResultUtil.errorJson("手机号码已注册!");
        }
        if(!"0".equals(type) && checkPhone){
            logger.error("手机号码未注册！");
            return HttpResultUtil.errorJson("手机号码未注册!");
        }

        Map dataMap = Maps.newHashMap();

        //调用发送短信接口
        String rand_num = StringUtils.getRandNum();
        rand_num = rand_num.length() < 6?StringUtils.getRandNum():rand_num;
        SMSUtil.sendSMS(phone, "1", rand_num, "5");

        //保存短信验证码
        LomCode code = new LomCode();
        code.setPhone(phone);
        code.setCode(rand_num);
//        code.setCode("666666");
        code.setKind(type);
        lomCodeService.save(code);

        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 验证短信短信验证码
     * @param request
     * @return
     */
    @RequestMapping(value = "checkSMS", method = RequestMethod.POST)
    @ResponseBody
    public String checkSMS(HttpServletRequest request) {
        logger.info("app checkSMS---------- Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String phone = (String) jsonData.get("phone");
        if (StringUtils.isEmpty(phone)) {
            logger.error("未获取到手机号！");
            return HttpResultUtil.errorJson("手机号不能为空!");
        }

        String code = (String) jsonData.get("code");
        if (StringUtils.isEmpty(phone)) {
            logger.error("未获取到短信验证码！");
            return HttpResultUtil.errorJson("短信验证码不能为空!");
        }

        String kind = (String) jsonData.get("type");
        if (StringUtils.isEmpty(kind)) {
            logger.error("未获取到类别！");
            return HttpResultUtil.errorJson("短信验证码校验失败!");
        }
        Map dataMap = Maps.newHashMap();

        //判断短信验证码
        String checkCode = lomCodeService.checkPhoneCode(phone, kind);
        if(StringUtils.isEmpty(checkCode)){
            logger.error("短信验证码已失效，请重新获取！");
            return HttpResultUtil.errorJson("短信验证码已失效，请重新获取!");
        }else if(!(code).equals(checkCode)){
            logger.error("短信验证码错误，请重新输入！");
            return HttpResultUtil.errorJson("短信验证码错误，请重新输入!");
        }

        return HttpResultUtil.successJson(dataMap);
    }

}