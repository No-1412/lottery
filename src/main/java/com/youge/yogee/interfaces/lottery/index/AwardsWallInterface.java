package com.youge.yogee.interfaces.lottery.index;

import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cawardswall.entity.CdAwardsWall;
import com.youge.yogee.modules.cawardswall.service.CdAwardsWallService;
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
public class AwardsWallInterface {
    private static final Logger logger = LoggerFactory.getLogger(AwardsWallInterface.class);
    @Autowired
    private CdAwardsWallService cdAwardsWallService;

    /**
     * 大奖墙列表接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "awardsWallList")
    @ResponseBody
    public String awardsWallList(HttpServletRequest request) {
        logger.info("大奖墙列表接口--------------Start-----");
        Map dataMap = new HashMap();
        List dataList = new ArrayList();
        List<CdAwardsWall> list = cdAwardsWallService.getAwardsWallList(null);
        if (list.size() > 0) {
            for (CdAwardsWall awards : list) {
                Map map = new HashMap();
                map.put("id", awards.getId()); //主键
                map.put("name", awards.getName()); //标题
                map.put("content", awards.getContent()); //内容
                map.put("prize", awards.getPrize()); //奖金
                map.put("issue", awards.getIssue()); //期次
                map.put("intro", awards.getIntro()); //简介
                map.put("createDate", awards.getCreateDate()); //时间
                dataList.add(map);
            }
        }
        dataMap.put("list", dataList);
        logger.info("大奖墙列表接口--------------End--------");
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 大奖墙明细接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "awardsWallDeatil")
    @ResponseBody
    public String awardsWallDeatil(HttpServletRequest request) {
        logger.info("大奖墙明细接口--------------Start-----");
        Map dataMap = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String id = (String) jsonData.get("id");
        List<CdAwardsWall> list = cdAwardsWallService.getAwardsWallList(id);
        if (list.size() > 0) {
            dataMap.put("name", list.get(0).getName()); //标题
            dataMap.put("content", list.get(0).getContent()); //内容
            dataMap.put("createDate", list.get(0).getCreateDate().substring(0, 10)); //时间
        }
        logger.info("大奖墙明细接口--------------End--------");
        return HttpResultUtil.successJson(dataMap);
    }
}
