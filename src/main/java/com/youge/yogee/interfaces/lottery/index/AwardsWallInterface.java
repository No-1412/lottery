package com.youge.yogee.interfaces.lottery.index;

import com.google.common.base.Strings;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.lottery.util.SelOrderUtil;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.cmagicorder.service.CdMagicOrderService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
import com.youge.yogee.modules.corder.service.CdOrderService;
import com.youge.yogee.modules.corder.service.CdOrderWinnersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
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
    @Autowired
    private CdOrderService cdOrderService;
    @Autowired
    private CdMagicOrderService cdMagicOrderService;


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
            for (CdOrderWinners awards : list) {
                if ("0.0".equals(awards.getWinPrice())) {
                    continue;
                }
                Map<String, String> map = new HashMap<>();
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
                BigDecimal newWinPrcie = new BigDecimal(winPrice).setScale(2, 2);
                map.put("winPrice", newWinPrcie.toString()); //奖金
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
     * 我的订单详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "awardsWallDetail")
    @ResponseBody
    public String awardsWallDetail(HttpServletRequest request) throws ParseException {
        logger.info("大奖墙详情--------------Start-----");
        try {
            Map jsonData = HttpServletRequestUtils.readJsonData(request);
            Map<String, Object> map = new HashMap();
            //orderNum
            String wid = (String) jsonData.get("wid");
            if (StringUtils.isEmpty(wid)) {
                logger.error("wid为空！");
                return HttpResultUtil.errorJson("wid为空!");
            }
            //1大奖墙详情  2我的订单详情
            String type = (String) jsonData.get("type");
            if (StringUtils.isEmpty(type)) {
                logger.error("type为空！");
                return HttpResultUtil.errorJson("type为空!");
            }
            String vo = (String) jsonData.get("vo");
            String issue = "";
            String orderNum = "";
            if ("1".equals(type)) {
                CdOrderWinners cow = cdOrderWinnersService.get(wid);
                if (cow == null) {
                    return HttpResultUtil.errorJson("信息错误!");
                }
                CdLotteryUser clu = cdLotteryUserService.get(cow.getUid());
                map.put("name", clu.getName());
                map.put("img", clu.getImg());
                BigDecimal winPrice = new BigDecimal(cow.getWinPrice());

                map.put("winPrice", winPrice.setScale(2, 2).toString());
                map.put("type", cow.getType());//1足球单关 2足球串关 3篮球单关 4篮球串关 5任选九 6胜负彩 7排列三 8排列五 9大乐透
                orderNum = cow.getWinOrderNum();
            } else {
                CdOrder co = cdOrderService.getOrderByOrderNum(wid);
                if (co == null) {
                    return HttpResultUtil.errorJson("信息错误!");
                }
                CdLotteryUser clu = cdLotteryUserService.get(co.getUserId());
                map.put("name", clu.getName());
                map.put("img", clu.getImg());
                map.put("winPrice", co.getWinPrice());
                map.put("type", co.getType());//1足球单关 2足球串关 3篮球单关 4篮球串关 5任选九 6胜负彩 7排列三 8排列五 9大乐透
                map.put("startTime", co.getCreateDate());//发起时间
                map.put("orderTime", co.getOutTime());//约单时间
                orderNum = co.getNumber();
                map.put("orderNum", orderNum);//订单号
                map.put("price", co.getTotalPrice());//价格
                map.put("status", co.getStatus());//中奖状态1待开奖 2已开奖 3中奖
                issue = co.getIssue();
            }

            map = SelOrderUtil.getOrderDetailMap(orderNum, map);
            if (!Strings.isNullOrEmpty(vo)){
                if ("2".equals(type) && issue.equals("1")) {
                    BigDecimal charges = cdMagicOrderService.findJoinFoByNumber(orderNum);

                    Object oAward = map.get("award");

                    if (oAward != null) {
                        String winPrice = (String) map.get("winPrice");
                        map.put("realAward", new BigDecimal(winPrice).setScale(2, RoundingMode.HALF_DOWN).toString());
                        System.out.println(oAward.toString());
                        BigDecimal awardBigDecimal = new BigDecimal((String) oAward);
                        map.put("winPrice", awardBigDecimal.setScale(2, RoundingMode.HALF_DOWN).toString());

//                    BigDecimal cs = charges.divide(new BigDecimal("100").setScale(2, RoundingMode.HALF_DOWN));
//                    System.out.println("佣金比例：" + cs.toString());
//
//                    BigDecimal ab = awardBigDecimal.multiply(cs).setScale(2, RoundingMode.HALF_DOWN);
//                    System.out.println("佣金：" + ab.toString());
//                    System.out.println("奖金：" + awardBigDecimal.toString());
//
//                    System.out.println(awardBigDecimal.subtract(ab).toString());
//                    BigDecimal realBigDecimal = awardBigDecimal.subtract(ab).setScale(2, RoundingMode.HALF_DOWN);
//                    System.out.println(realBigDecimal.toString());

                    }else{
                        map.put("realAward", new BigDecimal("0.00").toString());
                    }

                } else {
                    map.put("realAward", new BigDecimal("0.00").toString());
                }
            }

            logger.info("大奖墙列表接口--------------End--------");
            return HttpResultUtil.successJson(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return HttpResultUtil.errorJson("网络不稳定请稍后重试!");
        }

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
