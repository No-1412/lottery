package com.youge.yogee.interfaces.lottery.award;

import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.modules.cbasketballawards.entity.CdBasketballAwards;
import com.youge.yogee.modules.cbasketballawards.service.CdBasketballAwardsService;
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
 * Created by wjc on 2018-1-23 0023.
 */
@Controller
@RequestMapping("${frontPath}")
public class BasketballRewardInterface {
    private static final Logger logger = LoggerFactory.getLogger(BasketballRewardInterface.class);
    @Autowired
    private CdBasketballAwardsService cdBasketballAwardsService;

    /**
     * 篮球开奖信息
     */
    @ResponseBody
    @RequestMapping(value = "basketballReward", method = RequestMethod.POST)
    public String basketballReward(HttpServletRequest request) {
        logger.info("basketballReward  篮球开奖信息---------Start---------");
        List list = new ArrayList();
        List<CdBasketballAwards> dataList = cdBasketballAwardsService.getBallAwards();
        for (CdBasketballAwards str : dataList) {
            Map map = new HashMap();
            map.put("matchId", str.getMatchId());//场次id
            map.put("eventName", str.getEventName());//赛事名称
            map.put("homeTeam", str.getHomeTeam());//主队
            map.put("awayTeam", str.getAwayTeam());//客队
            map.put("hs", str.getHs()); //主队分数
            map.put("vs", str.getVs());  //客队分数
            map.put("zclose", str.getZclose());//大小分
            map.put("winning", str.getWinning());//胜负
            map.put("spread", str.getSpread());//让分
            map.put("winGrap", str.getWinGrap());//胜分差
            map.put("spreadNum", str.getSpreadNum());//让分数
            map.put("mt", str.getMt());//赛事时间
            list.add(map);
        }
        Map dataMap = new HashMap();
        dataMap.put("list", list);
        logger.info("basketballReward  篮球开奖信息---------End---------");
        return HttpResultUtil.successJson(dataMap);
    }

}
