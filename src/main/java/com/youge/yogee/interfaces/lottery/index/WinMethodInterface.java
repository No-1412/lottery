package com.youge.yogee.interfaces.lottery.index;

import com.google.common.collect.Maps;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cwinmethod.entity.CdWinMethod;
import com.youge.yogee.modules.cwinmethod.service.CdWinMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wjc on 2017-12-22 0022.
 */
@Controller
@RequestMapping("${frontPath}")
public class WinMethodInterface {
    private static final Logger logger = LoggerFactory.getLogger(WinMethodInterface.class);
    @Autowired
    private CdWinMethodService cdWinMethodService;
    /**
     * 中奖攻略列表
     */
    @RequestMapping(value = "winMethodList")
    @ResponseBody
    public String winMethodList(HttpServletRequest request) {
        logger.info("中奖攻略列表 ---------- Start--------");
        List dataList = new ArrayList();
        List<CdWinMethod> list = cdWinMethodService.getMethodList(null);
        for(CdWinMethod lst : list){
            Map map = new HashMap();
            map.put("id",lst.getId());
            map.put("name",lst.getName());//标题
            map.put("issue",lst.getIssue());//简介
            map.put("img",lst.getImg());//小图片
            dataList.add(map);
        }
        Map dataMap = Maps.newHashMap();
        dataMap.put("list",dataList);
        logger.info("pc：中奖攻略列表---------- End----------");
        return HttpResultUtil.successJson(dataMap);
    }
    /**
     * 中奖攻略详细信息
     */
    @RequestMapping(value = "winMethodDetail")
    @ResponseBody
    public String winMethodDetail(HttpServletRequest request) {
        logger.info("中奖攻略详细信息 ---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String id = (String) jsonData.get("id");
        List<CdWinMethod> list = cdWinMethodService.getMethodList(id);
        String imgBig  = list.get(0).getImgBig();
        Map dataMap = Maps.newHashMap();
        dataMap.put("imgBig",imgBig); //大图片
        logger.info("pc：中奖攻略详细信息---------- End----------");
        return HttpResultUtil.successJson(dataMap);
    }

}
