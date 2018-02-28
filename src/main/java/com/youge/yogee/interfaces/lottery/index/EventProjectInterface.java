package com.youge.yogee.interfaces.lottery.index;

import com.youge.yogee.interfaces.util.HttpResultUtil;
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

    @RequestMapping(value = "event")
    @ResponseBody
    public String event(HttpServletRequest request) {
        logger.info("赛事专题接口--------------Start-----");
        Map dataMap = new HashMap();
        List dataList = new ArrayList();
        List<CdEventProject> list = cdEventProjectService.getEvent();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = new HashMap();
                map.put("name", list.get(i).getName()); //标题
                map.put("content", list.get(i).getContent()); //内容
                map.put("img", list.get(i).getImg()); //图片
                map.put("createDate", list.get(i).getCreateDate().substring(0, 10)); //创建时间
                dataList.add(map);
            }
        }
        dataMap.put("dataList", dataList);
        logger.info("赛事专题接口--------------End--------");
        return HttpResultUtil.successJson(dataMap);
    }

}
