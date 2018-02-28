package com.youge.yogee.interfaces.lottery.index;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.modules.cbannerimg.entity.CdBannerImg;
import com.youge.yogee.modules.cbannerimg.service.CdBannerImgService;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
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
public class HomePageInterface {
    private static final Logger logger = LoggerFactory.getLogger(HomePageInterface.class);
    @Autowired
    private CdBannerImgService cdBannerImgService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;


    /**
     * @Author weijinchao
     * 首页新闻图片展示
     */
    @RequestMapping(value = "homePageImg")
    @ResponseBody
    public String homePageImg(HttpServletRequest request) {
        logger.info("首页轮播图展示--------------Start-----");
        Map dataMap = new HashMap();
        List dataList = new ArrayList();
        List bannerList = new ArrayList();
        List<CdBannerImg> list2 = cdBannerImgService.getBannerImg();
        if(list2!=null && list2.size()>0){
            for(CdBannerImg b : list2){
                Map map = new HashMap();
                map.put("bannerImg", Global.getConfig("domain.name") + b.getImg());
                bannerList.add(map);
            }
        }

        dataMap.put("dataList", dataList);
        dataMap.put("bannerList", bannerList);

        logger.info("首页轮播图展示--------------End--------");
        return HttpResultUtil.successJson(dataMap);
    }




}
