package com.youge.yogee.interfaces.lottery.user;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.lottery.util.SelOrderUtil;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveOrder;
import com.youge.yogee.modules.cfiveawards.service.CdFiveOrderService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.clottoreward.entity.CdLottoOrder;
import com.youge.yogee.modules.clottoreward.service.CdLottoOrderService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.entity.CdOrderCatch;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
import com.youge.yogee.modules.corder.service.CdOrderCatchService;
import com.youge.yogee.modules.corder.service.CdOrderService;
import com.youge.yogee.modules.corder.service.CdOrderWinnersService;
import com.youge.yogee.modules.crecord.entity.CdRecordCash;
import com.youge.yogee.modules.crecord.entity.CdRecordRebate;
import com.youge.yogee.modules.crecord.entity.CdRecordRecharge;
import com.youge.yogee.modules.crecord.service.CdRecordCashService;
import com.youge.yogee.modules.crecord.service.CdRecordRebateService;
import com.youge.yogee.modules.crecord.service.CdRecordRechargeService;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeOrder;
import com.youge.yogee.modules.cthreeawards.service.CdThreeOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的模块
 */
@Controller
@RequestMapping("${frontPath}")
public class UserInformationInterface {
    private static final Logger logger = LoggerFactory.getLogger(UserInformationInterface.class);
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdOrderService cdOrderService;
    @Autowired
    private CdRecordRebateService cdRecordRebateService;
    @Autowired
    private CdRecordCashService cdRecordCashService;
    @Autowired
    private CdOrderWinnersService cdOrderWinnersService;
    @Autowired
    private CdOrderCatchService cdOrderCatchService;
    @Autowired
    private CdFiveOrderService cdFiveOrderService;
    @Autowired
    private CdThreeOrderService cdThreeOrderService;
    @Autowired
    private CdLottoOrderService cdLottoOrderService;
    @Autowired
    private CdRecordRechargeService cdRecordRechargeService;

    /**
     * 我的首页
     */

    @RequestMapping(value = "myHomePage", method = RequestMethod.POST)
    @ResponseBody
    public String myHomePage(HttpServletRequest request) {
        logger.info("myHomePage---------- Start-----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map dataMap = new HashMap();
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        String recharge = clu.getTotalRecharge();//充值总金额
        String percent = SelOrderUtil.getLevelPercent(recharge);
        dataMap.put("uid", clu.getId());
        dataMap.put("img", clu.getImg());//头像
        dataMap.put("level", clu.getMemberLevel());//等级
        dataMap.put("name", clu.getName());//昵称
        dataMap.put("balance", clu.getBalance().setScale(2).toString());//余额
        dataMap.put("rebate", clu.getRebate());//返利金额
        dataMap.put("percent", percent);//等级百分比
        dataMap.put("tel", clu.getMobile());//等级电话
        dataMap.put("isReal", clu.getIsRealNameVerified());//实名认证 1已认证 0未认证
        dataMap.put("catchTimes", clu.getCatchTimes());//今日提现次数
        dataMap.put("realName", clu.getReality());//真实姓名
        dataMap.put("idCard", clu.getIdNumber());//身份证号
        String continent = clu.getContinent();
        if (StringUtils.isNotEmpty(continent)) {
            String[] continentArray = continent.split(",");
            int asia = 0, europe = 0, america = 0, africa = 0;//亚 欧 美 非
            for (String str : continentArray) {
                if (str.equals("亚洲")) {
                    asia += 1;
                } else if (str.equals("欧洲")) {
                    europe += 1;
                } else if (str.equals("美洲")) {
                    america += 1;
                } else {
                    africa += 1;
                }
            }
            int max = asia;
            dataMap.put("continent", "亚洲");
            if (europe > asia) {
                max = europe;
                dataMap.put("continent", "欧洲");
            }
            if (america > max) {
                max = america;
                dataMap.put("continent", "美洲");
            }
            if (africa > max) {
                max = america;
                dataMap.put("continent", "非洲");
            }
            BigDecimal maxBig = new BigDecimal(max);
            BigDecimal total = new BigDecimal(continentArray.length);
            BigDecimal per = maxBig.divide(total, 2, 2).multiply(new BigDecimal(100));
            dataMap.put("per", per.toString() + "%");
        } else {
            dataMap.put("continent", ""); //大洲
            dataMap.put("per", "0.00%"); //百分比
        }
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 我的订单
     */

    @RequestMapping(value = "myOrderList", method = RequestMethod.POST)
    @ResponseBody
    public String myOrderList(HttpServletRequest request) {
        logger.info("myOrderList---------- Start-----------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map dataMap = new HashMap();
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        String status = (String) jsonData.get("status");
        if (StringUtils.isEmpty(uid)) {
            status = "";
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("count为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        List<CdOrder> list = cdOrderService.listCdOrder(uid, status, total, count);
        List<Map<String, String>> cList = new ArrayList<>();
        for (CdOrder c : list) {
            Map<String, String> map = new HashMap();
            map.put("id", c.getId());//id
            map.put("type", c.getType());//彩票种类 1足球单关 2足球串关 3篮球单关 4篮球串关 5任选九 6胜负彩 7排列三 8排列五 9大乐透
            map.put("price", c.getTotalPrice());//金额
            map.put("win", c.getWinPrice());//中奖金额
            map.put("time", c.getCreateDate());//时间
            String quality = c.getIssue();
            map.put("quality", quality);//0自购 1跟单 2神单
            map.put("orderNum", c.getNumber());//订单号
            map.put("followNum", c.getFollowNum());//神单订单号
            map.put("status", c.getStatus());//1待开奖 2已开奖 3中奖
            cList.add(map);
        }
        dataMap.put("cList", cList);
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 我的追号记录
     */

    @RequestMapping(value = "myFollowOrderList", method = RequestMethod.POST)
    @ResponseBody
    public String myFollowOrderList(HttpServletRequest request) throws ParseException {
        logger.info("myFollowOrderList---------- Start-----------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map dataMap = new HashMap();

        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("count为空");
        }
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }

        List<CdOrderCatch> list = cdOrderCatchService.findByUid(uid, total, count);
        List cList = new ArrayList();
        for (CdOrderCatch c : list) {
            Map<String, String> map = new HashMap<>();
            map.put("id", c.getId());
            map.put("time", c.getCreateDate());
            map.put("status", c.getStatus());//1进行中 2已结束
            map.put("type", c.getType());///1排列三 2排列五 3大乐透
            map.put("continuity", c.getContinuity());//追的期数
            map.put("hasContinue", c.getHasContinue());//已追期数
            map.put("price", c.getPrice());//金额
            map.put("orderNum", c.getOrderNum());//订单号
            cList.add(map);
        }

        dataMap.put("list", cList);
        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 我的追号详情
     */

    @RequestMapping(value = "myFollowOrderDetail", method = RequestMethod.POST)
    @ResponseBody
    public String myFollowOrderDetail(HttpServletRequest request) throws ParseException {
        logger.info("myFollowOrderDetail---------- Start-----------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map dataMap = new HashMap();

        String orderNum = (String) jsonData.get("orderNum");
        if (StringUtils.isEmpty(orderNum)) {
            return HttpResultUtil.errorJson("orderNum为空");
        }
        List hasList = new ArrayList();
        List notList = new ArrayList();
        List finalList = new ArrayList();
        CdOrderCatch coc = cdOrderCatchService.findByOrderNum(orderNum);
        dataMap.put("continuity", coc.getContinuity());//追的期数
        dataMap.put("hasContinue", coc.getHasContinue());//已追期数
        dataMap.put("status", coc.getStatus());//1进行中 2已结束
        dataMap.put("type", coc.getType());///1排列三 2排列五 3大乐透
        dataMap.put("time", coc.getCreateDate());//时间
        if (orderNum.startsWith("PLS")) {
            CdThreeOrder cto = cdThreeOrderService.findOrderByOrderNum(orderNum);
            String followCode = cto.getFollowCode();
            dataMap.put("nums", cto.getNums());//号码
            dataMap.put("followCode", followCode);//追单方案
            dataMap.put("buyWays", cto.getBuyWays());//
            List<CdThreeOrder> cList = cdThreeOrderService.findByFollowCode(followCode);
            for (CdThreeOrder c : cList) {
                Map<String, String> map = new HashMap();
                map.put("weekday", c.getWeekday());
                map.put("result", c.getResult());
                map.put("status", c.getStatus());//状态
                if ("4".equals(c.getStatus())) {
                    map.put("award", c.getAward());
                } else {
                    map.put("award", "0");
                }
                double priceDouble = Double.parseDouble(c.getPrice());
                double continuity = Double.parseDouble(c.getContinuity());
                double perPrice = priceDouble / continuity;
                map.put("price", c.getPrice() + "/" + String.valueOf(perPrice));
                hasList.add(map);
            }
            int conInt = Integer.parseInt(coc.getContinuity());
            int conHas = Integer.parseInt(coc.getHasContinue());
            if (conInt > conHas) {
                int left = conInt - conHas;
                String weekContinue = cto.getWeekContinue();
                String[] weekArray = weekContinue.split(",");
                for (int i = weekArray.length - 1; i > weekArray.length - 1 - left; i--) {
                    notList.add(weekArray[i]);
                }
                for (int i = notList.size() - 1; i >= 0; i--) {
                    finalList.add(notList.get(i));
                }
            }
            dataMap.put("hasList", hasList);
            dataMap.put("notList", finalList);
        } else if (orderNum.startsWith("PLW")) {
            CdFiveOrder cfo = cdFiveOrderService.findOrderByOrderNum(orderNum);
            String followCode = cfo.getFollowCode();
            dataMap.put("nums", cfo.getNums());//号码
            dataMap.put("followCode", followCode);//追单方案
            dataMap.put("buyWays", cfo.getBuyWays());//
            List<CdFiveOrder> cList = cdFiveOrderService.findByFollowCode(followCode);
            if (cList.size() > 0) {
                for (CdFiveOrder c : cList) {
                    Map<String, String> map = new HashMap();
                    map.put("weekday", c.getWeekday());
                    map.put("result", c.getResult());
                    map.put("status", c.getStatus());//状态
                    if ("4".equals(c.getStatus())) {
                        map.put("award", c.getAward());
                    } else {
                        map.put("award", "0");
                    }
                    double priceDouble = Double.parseDouble(c.getPrice());
                    double continuity = Double.parseDouble(c.getContinuity());
                    double perPrice = priceDouble / continuity;
                    map.put("price", c.getPrice() + "/" + String.valueOf(perPrice));
                    hasList.add(map);
                }
            }
            int conInt = Integer.parseInt(coc.getContinuity());
            int conHas = Integer.parseInt(coc.getHasContinue());
            if (conInt > conHas) {
                int left = conInt - conHas;
                String weekContinue = cfo.getWeekContinue();
                String[] weekArray = weekContinue.split(",");
                for (int i = weekArray.length - 1; i > weekArray.length - 1 - left; i--) {
                    notList.add(weekArray[i]);
                }
                for (int i = notList.size() - 1; i >= 0; i--) {
                    finalList.add(notList.get(i));
                }
            }
            dataMap.put("hasList", hasList);
            dataMap.put("notList", finalList);
        } else {
            CdLottoOrder clo = cdLottoOrderService.findOrderByOrderNum(orderNum);
            String followCode = clo.getFollowCode();
            dataMap.put("nums", clo.getRedNums() + "|" + clo.getBlueNums());//号码
            dataMap.put("followCode", followCode);//追单方案
            dataMap.put("buyWays", clo.getType());//
            List<CdLottoOrder> cList = cdLottoOrderService.findByFollowCode(followCode);
            if (cList.size() > 0) {
                for (CdLottoOrder c : cList) {
                    Map<String, String> map = new HashMap();
                    map.put("weekday", c.getWeekday());
                    map.put("result", c.getResult());
                    map.put("status", c.getStatus());//状态
                    if ("4".equals(c.getStatus())) {
                        map.put("award", c.getAward());
                    } else {
                        map.put("award", "0");
                    }
                    double priceDouble = Double.parseDouble(c.getPrice());
                    double continuity = Double.parseDouble(c.getContinuity());
                    double perPrice = priceDouble / continuity;
                    map.put("price", c.getPrice() + "/" + String.valueOf(perPrice));
                    hasList.add(map);
                }
            }
            int conInt = Integer.parseInt(coc.getContinuity());
            int conHas = Integer.parseInt(coc.getHasContinue());
            if (conInt > conHas) {
                int left = conInt - conHas;
                String weekContinue = clo.getWeekContinue();
                String[] weekArray = weekContinue.split(",");
                for (int i = weekArray.length - 1; i > weekArray.length - 1 - left; i--) {
                    notList.add(weekArray[i]);
                }
                for (int i = notList.size() - 1; i >= 0; i--) {
                    finalList.add(notList.get(i));
                }
            }
            dataMap.put("hasList", hasList);
            dataMap.put("notList", finalList);
        }

        return HttpResultUtil.successJson(dataMap);
    }


    /**
     * 停止追号
     */

    @RequestMapping(value = "stopCatchFollowOrder", method = RequestMethod.POST)
    @ResponseBody
    public String stopCatchFollowOrder(HttpServletRequest request) throws ParseException {
        logger.info("stopCatchFollowOrder---------- Start-----------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        Map dataMap = new HashMap();

        String orderNum = (String) jsonData.get("orderNum");
        if (StringUtils.isEmpty(orderNum)) {
            return HttpResultUtil.errorJson("orderNum为空");
        }
        List hasList = new ArrayList();
        List notList = new ArrayList();
        List finalList = new ArrayList();
        CdOrderCatch coc = cdOrderCatchService.findByOrderNum(orderNum);
        if (coc == null) {
            return HttpResultUtil.errorJson("追号单不存在");
        }
        coc.setStatus("2");//已结束
        cdOrderCatchService.save(coc);
        if (orderNum.startsWith("PLS")) {
            CdThreeOrder cto = cdThreeOrderService.findOrderByOrderNum(orderNum);
            String followCode = cto.getFollowCode();
            CdThreeOrder lastOrder = cdThreeOrderService.findByFollowCodeAndStatus(followCode);
            if (lastOrder != null) {
                lastOrder.setFollowType("4");
                cdThreeOrderService.save(lastOrder);
            } else {
                return HttpResultUtil.errorJson("没有在追的单");
            }

        } else if (orderNum.startsWith("PLW")) {
            CdFiveOrder cfo = cdFiveOrderService.findOrderByOrderNum(orderNum);
            String followCode = cfo.getFollowCode();
            CdFiveOrder lastOrder = cdFiveOrderService.findByFollowCodeAndStatus(followCode);
            if (lastOrder != null) {
                lastOrder.setFollowType("4");
                cdFiveOrderService.save(lastOrder);
            } else {
                return HttpResultUtil.errorJson("没有在追的单");
            }

        } else {
            CdLottoOrder clo = cdLottoOrderService.findOrderByOrderNum(orderNum);
            String followCode = clo.getFollowCode();
            CdLottoOrder lastOrder = cdLottoOrderService.findByFollowCodeAndStatus(followCode);
            if (lastOrder != null) {
                lastOrder.setFollowType("4");
                cdLottoOrderService.save(lastOrder);
            } else {
                return HttpResultUtil.errorJson("没有在追的单");
            }
        }

        return HttpResultUtil.successJson(dataMap);
    }


    /***
     * 返利记录
     * @param request
     * @return
     */
    @RequestMapping(value = "getRecordRebate", method = RequestMethod.POST)
    @ResponseBody
    public String getRecordRebate(HttpServletRequest request) {
        logger.info("getRecordRebate---------- Start-----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("count为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }

        List<CdRecordRebate> cdRecordRebateList = cdRecordRebateService.findByUid(uid, total, count);
        List<Map> recordRebateList = new ArrayList<>();
        for (CdRecordRebate cdRecordRebate : cdRecordRebateList) {
            Map<String, Object> cdRecordRebateMap = new HashMap<>();
            cdRecordRebateMap.put("rebate", cdRecordRebate.getRebate());
            cdRecordRebateMap.put("createDate", cdRecordRebate.getCreateDate());
            cdRecordRebateMap.put("type", cdRecordRebate.getType());
            recordRebateList.add(cdRecordRebateMap);
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("recordRebateList", recordRebateList);//等级百分比
        return HttpResultUtil.successJson(dataMap);
    }

    /***
     * 提现记录
     * @param request
     * @return
     */
    @RequestMapping(value = "getRecordCash", method = RequestMethod.POST)
    @ResponseBody
    public String getRecordCash(HttpServletRequest request) {
        logger.info("getRecordCash---------- Start-----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("count为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }

        List<CdRecordCash> list = cdRecordCashService.findByUid(uid, total, count);
        List<Map> recordCashList = new ArrayList<>();
        for (CdRecordCash cdRecordCash : list) {
            Map<String, Object> cdRecordCashMap = new HashMap<>();
            cdRecordCashMap.put("price", cdRecordCash.getPrice());
            cdRecordCashMap.put("orderNum", cdRecordCash.getOrderNum());
            cdRecordCashMap.put("cardNum", cdRecordCash.getCardNum());
            String dealTime = cdRecordCash.getDealTime();
            if (StringUtils.isEmpty(dealTime)) {
                dealTime = cdRecordCash.getCreateDate();
            }
            cdRecordCashMap.put("dealTime", dealTime);
            cdRecordCashMap.put("status", cdRecordCash.getStatus());
            recordCashList.add(cdRecordCashMap);
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("recordCashList", recordCashList);
        return HttpResultUtil.successJson(dataMap);
    }


    /***
     * 中奖记录
     * @param request
     * @return
     */
    @RequestMapping(value = "getOrderWinners", method = RequestMethod.POST)
    @ResponseBody
    public String getOrderWinners(HttpServletRequest request) {
        logger.info("getOrderWinners---------- Start-----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("count为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }

        List<CdOrderWinners> list = cdOrderWinnersService.findByUid(uid, total, count);
        List<Map> orderWinnersList = new ArrayList<>();
        for (CdOrderWinners cdOrderWinners : list) {
            Map<String, Object> orderWinnersMap = new HashMap<>();
            orderWinnersMap.put("type", cdOrderWinners.getType());
            orderWinnersMap.put("orderNum", cdOrderWinners.getWinOrderNum());
            orderWinnersMap.put("price", cdOrderWinners.getWinPrice());
            orderWinnersMap.put("createDate", cdOrderWinners.getCreateDate());
            orderWinnersList.add(orderWinnersMap);
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("orderWinnersList", orderWinnersList);
        return HttpResultUtil.successJson(dataMap);
    }


    /***
     * 充值记录
     * @param request
     * @return
     */
    @RequestMapping(value = "getRecordRecharge", method = RequestMethod.POST)
    @ResponseBody
    public String getRecordRecharge(HttpServletRequest request) {
        logger.info("getRecordRecharge---------- Start-----------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return HttpResultUtil.errorJson("uid为空");
        }
        String total = (String) jsonData.get("total");
        if (StringUtils.isEmpty(total)) {
            return HttpResultUtil.errorJson("total为空");
        }
        String count = (String) jsonData.get("count");
        if (StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("count为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }

        List<CdRecordRecharge> list = cdRecordRechargeService.findByUid(uid, total, count);
        List<Map> rList = new ArrayList<>();
        for (CdRecordRecharge c : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", c.getName());
            map.put("price", c.getPrice());
            map.put("createDate", c.getCreateDate());
            map.put("orderNum", c.getOrderNum());
            rList.add(map);
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("rList", rList);
        return HttpResultUtil.successJson(dataMap);
    }

}
