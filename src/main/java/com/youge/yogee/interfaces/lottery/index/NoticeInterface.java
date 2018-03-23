package com.youge.yogee.interfaces.lottery.index;

/**
 * Created by wjc on 2017-12-21 0021.
 */

import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.modules.cnotice.entity.CdNotice;
import com.youge.yogee.modules.cnotice.service.CdNoticeService;
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
 * Created by Administrator on 2017-10-18 0018.
 */
@Controller
@RequestMapping("${frontPath}")
public class NoticeInterface {
    private static final Logger logger = LoggerFactory.getLogger(HomePageInterface.class);
    @Autowired
    private CdNoticeService cdNoticeService;
    /**
     * @Author weijinchao
     * 公告接口
     */
    @RequestMapping(value = "notice")
    @ResponseBody
    public String notice(HttpServletRequest request) {
        logger.info("公告信息接口--------------Start-----");
        Map dataMap = new HashMap();
        List dataList = new ArrayList();
        List<CdNotice>  list  = cdNoticeService.getNotice();
        if(list.size()>0){
            for (int i = 0; i <list.size() ; i++) {
                Map map = new HashMap();
                map.put("name",list.get(i).getName()); //公告信息
                map.put("createDate",list.get(i).getCreateDate());
                dataList.add(map);
            }
        }
        dataMap.put("dataList", dataList);
        logger.info("公告信息接口--------------End--------");
        return HttpResultUtil.successJson(dataMap);
    }


}
