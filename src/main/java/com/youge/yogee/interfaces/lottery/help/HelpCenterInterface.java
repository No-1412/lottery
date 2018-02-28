package com.youge.yogee.interfaces.lottery.help;

import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.modules.chelpcenter.entity.CdHelpCenter;
import com.youge.yogee.modules.chelpcenter.service.CdHelpCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/20.
 */
@Controller
@RequestMapping("${frontPath}")
public class HelpCenterInterface {
    private static final Logger logger = LoggerFactory.getLogger(HelpCenterInterface.class);

    @Autowired
    private CdHelpCenterService cdHelpCenterService;

    /**
     * 查询帮助中心
     * wangsong
     * 2017_12_20
     * @return
     */
    @RequestMapping(value = "listHelpCenter", method = RequestMethod.POST)
    @ResponseBody
    public String listHelpCenter(HttpServletRequest request) {
        logger.info("listHelpCenter  帮助中心查询---------Start---------");

        List<CdHelpCenter> cdHelpCenterList= cdHelpCenterService.findHelpList();//查询
        List list = new ArrayList();
        Map dataMap =  new HashMap();
        for(CdHelpCenter cdHelpCenter : cdHelpCenterList) {
            Map map = new HashMap();
            map.put("createDate",cdHelpCenter.getCreateDate().substring(0,10));//创建时间
            map.put("name", cdHelpCenter.getQuestionContent());//标题
            map.put("answer", cdHelpCenter.getQuestionAnswer());//答案
            list.add(map);
        }
        dataMap.put("List",list);
        logger.info("listHelpCenter  帮助中心查询---------End---------");

        return HttpResultUtil.successJson(dataMap);
    }
}
