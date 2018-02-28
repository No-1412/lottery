package com.youge.yogee.interfaces.lottery.aboutus;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.interfaces.lottery.help.HelpCenterInterface;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.SubImage;
import com.youge.yogee.modules.caboutus.entity.CdAboutUs;
import com.youge.yogee.modules.caboutus.service.CdAboutUsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/20.
 */
@Controller
@RequestMapping("${frontPath}")
public class AboutUsInterface {
    private static final Logger logger = LoggerFactory.getLogger(HelpCenterInterface.class);

    @Autowired
    private CdAboutUsService cdAboutUsService;

    /**
     * 关于我们
     * wangsong
     * 20171220
     * @param request
     * @return
     */
    @RequestMapping(value = "getAboutUs", method = RequestMethod.POST)
    @ResponseBody
    public String getAboutUs(HttpServletRequest request){
        logger.info("getAboutUs  关于我们---------Start---------");
        List<CdAboutUs> cdAboutUsList=cdAboutUsService.getByAll();
        Map dataMap =  new HashMap();
        dataMap.put("name", cdAboutUsList.get(0).getName());//公司名
        dataMap.put("creatDate",cdAboutUsList.get(0).getCreateDate());//时间
        dataMap.put("img", Global.getConfig("domain.name") + cdAboutUsList.get(0).getLogoImg());//logo
        Map<String,String> mapEditor = SubImage.getEditor(cdAboutUsList.get(0).getFunction());
        dataMap.put("function",mapEditor.get("editor"));//介绍
        dataMap.put("kefuTel",cdAboutUsList.get(0).getKefuTel());//客服电话
        dataMap.put("guanwangHref",cdAboutUsList.get(0).getGuanwangHref());//链接
        logger.info("getAboutUs  关于我们---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }


}
