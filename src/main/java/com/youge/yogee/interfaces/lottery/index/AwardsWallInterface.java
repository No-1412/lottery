package com.youge.yogee.interfaces.lottery.index;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
import com.youge.yogee.modules.corder.service.CdOrderWinnersService;
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
    private CdOrderWinnersService cdOrderWinnersService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;

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
        List<CdOrderWinners> list = cdOrderWinnersService.findByWallType("1");
        List<Map<String, String>> textStr = new ArrayList<>();
        if (list.size() > 0) {
            for (int i = 0; i < 10; i++) {
                Map<String, String> map = new HashMap<>();
                CdOrderWinners awards = list.get(i);
                CdLotteryUser clu = cdLotteryUserService.get(awards.getUid());
                String name = clu.getName();
                String result = getWinType(awards.getType());
                String winPrice = awards.getWinPrice();
                String repayPercent = awards.getRepayPercent() + "%";
                String date = awards.getCreateDate().split(" ")[0];
                //String text = name + "恭喜您，您自购的" + result + "喜中" + winPrice + "元奖金，您的回报率为" + repayPercent + "%，特此表彰，以资鼓励！\n" + date;
                String text1 = "恭喜您，您自购的" + result + "喜中";
                String text2 = "元奖金，您的回报率为";
                String text3 = "，特此表彰，以资鼓励！";
                map.put("id", awards.getId()); //用户名
                map.put("name", name); //用户名
                map.put("text1", text1); //前半句
                map.put("winPrice", winPrice); //奖金
                map.put("text2", text2); //中间句
                map.put("repayPercent", repayPercent); //回报率
                map.put("text3", text3); //后半句
                map.put("date", date); //时间
                textStr.add(map);
            }
        }
        dataMap.put("list", textStr);
        logger.info("大奖墙列表接口--------------End--------");
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 大奖墙详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "awardsWallDetail")
    @ResponseBody
    public String awardsWallDetail(HttpServletRequest request) {
        logger.info("大奖墙列表接口--------------Start-----");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map dataMap = new HashMap();
        //id
        String wid = (String) jsonData.get("wid");
        if (StringUtils.isEmpty(wid)) {
            logger.error("wid为空！");
            return HttpResultUtil.errorJson("wid为空!");
        }
        CdOrderWinners cow = cdOrderWinnersService.get(wid);
        if (cow == null) {
            return HttpResultUtil.errorJson("信息错误！");
        }
        dataMap.put("name",cow.getName());
        dataMap.put("winPrice",cow.getWinPrice());
        String orderNum = cow.getWinOrderNum();
        if(orderNum.startsWith("ZDG")){

        }
        logger.info("大奖墙列表接口--------------End--------");
        return HttpResultUtil.successJson(dataMap);
    }




    public String getWinType(String type) {
        String result = "";
        int i = Integer.parseInt(type);
        switch (i) {
            case 1:
                result = "足球单关";
                break;
            case 2:
                result = "足球串关";
                break;
            case 3:
                result = "篮球单关";
                break;
            case 4:
                result = "篮球串关";
                break;
            case 5:
                result = "任选九";
                break;
            case 6:
                result = "胜负彩";
                break;
            case 7:
                result = "排列三";
                break;
            case 8:
                result = "排列五";
                break;
            case 9:
                result = "大乐透";
                break;
        }
        return result;
    }

}
