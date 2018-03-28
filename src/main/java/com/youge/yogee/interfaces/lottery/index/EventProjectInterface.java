package com.youge.yogee.interfaces.lottery.index;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.ceventproject.entity.CdEventProject;
import com.youge.yogee.modules.ceventproject.service.CdEventProjectService;
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
 * Created by wjc on 2017-12-21 0021.
 */
@Controller
@RequestMapping("${frontPath}")
public class EventProjectInterface {
    private static final Logger logger = LoggerFactory.getLogger(EventProjectInterface.class);
    @Autowired
    private CdEventProjectService cdEventProjectService;

    @RequestMapping(value = "getEventList")
    @ResponseBody
    public String getEventList(HttpServletRequest request) {
        logger.info("getEventList--------------Start-----");
        Map dataMap = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            logger.error("total为空");
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            logger.error("count为空");
            return HttpResultUtil.errorJson("count为空");
        }
        List dataList = new ArrayList();
        List<CdEventProject> list = cdEventProjectService.getEvent(total, count);
        if (list.size() > 0) {
            for (CdEventProject c : list) {
                Map map = new HashMap();
                map.put("id", c.getId());
                map.put("name", c.getName()); //标题
                map.put("img", Global.getConfig("domain.name") + c.getImg()); //图片
                map.put("createDate", c.getCreateDate().substring(0, 10)); //创建时间
                dataList.add(map);
            }
        }
        dataMap.put("dataList", dataList);
        return HttpResultUtil.successJson(dataMap);
    }


    @RequestMapping(value = "getEventDetail")
    @ResponseBody
    public String getEventDetail(HttpServletRequest request) {
        logger.info("getEventDetail--------------Start-----");
        Map dataMap = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String id = (String) jsonData.get("id");
        if (StringUtils.isEmpty(id)) {
            logger.error("id为空");
            return HttpResultUtil.errorJson("id为空");
        }

        CdEventProject cep = cdEventProjectService.get(id);
        if (cep != null) {
            dataMap.put("name", cep.getName());//标题
            dataMap.put("text", cep.getContent());//内容
            dataMap.put("createDate", cep.getCreateDate().substring(0, 10)); //创建时间
        }
        return HttpResultUtil.successJson(dataMap);
    }

}
