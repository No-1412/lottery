package com.youge.yogee.interfaces.lottery.order;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.lottery.help.HelpCenterInterface;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.cbasketballmixed.entity.CdBasketballMixed;
import com.youge.yogee.modules.cbasketballmixed.service.CdBasketballMixedService;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballSingleOrderService;
import com.youge.yogee.modules.cfootballmixed.entity.CdFootballMixed;
import com.youge.yogee.modules.cfootballmixed.service.CdFootballMixedService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.cmagicorder.entity.CdMagicOrder;
import com.youge.yogee.modules.cmagicorder.service.CdMagicOrderService;
import com.youge.yogee.modules.corder.service.CdOrderFollowTimesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyifeng on 2018-03-05.
 */
@Controller
@RequestMapping("${frontPath}")
public class MagicOrderInterface {
    private static final Logger logger = LoggerFactory.getLogger(HelpCenterInterface.class);
    @Autowired
    private CdBasketballSingleOrderService cdBasketballSingleOrderService;  //篮球单关
    @Autowired
    private CdBasketballFollowOrderService cdBasketballFollowOrderService;//篮球串关
    @Autowired
    private CdFootballFollowOrderService cdFootballFollowOrderService;//足球串关
    @Autowired
    private CdFootballSingleOrderService cdFootballSingleOrderService;//足球单关
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdMagicOrderService cdMagicOrderService;
    @Autowired
    private CdFootballMixedService cdFootballMixedService;
    @Autowired
    private CdBasketballMixedService cdBasketballMixedService;
    @Autowired
    private CdOrderFollowTimesService cdOrderFollowTimesService;

    /**
     * 神单提交订单
     */
    @RequestMapping(value = "magicOrderCommit", method = RequestMethod.POST)
    @ResponseBody
    public String magicOrderCommit(HttpServletRequest request) throws ParseException {
        logger.info("神单提交 magicOrderCommit--------Start-------------------");
        logger.debug("interface 请求--magicOrderCommit-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }

        String orderNum = (String) jsonData.get("orderNum");
        if (StringUtils.isEmpty(orderNum)) {
            logger.error("orderNum为空");
            return HttpResultUtil.errorJson("orderNum为空");
        }
        //佣金百分比
        String charges = (String) jsonData.get("charges");
        if (StringUtils.isEmpty(charges)) {
            logger.error("charges为空");
            return HttpResultUtil.errorJson("charges为空");
        }

        String uName = "";//用户名
        String uImg = "";//头像
        String price = "";//金额
        String type = "";//1足球单关 2足球串关 3篮球单关 4篮球串关
        String shutDownTime = "";//截止时间
        String uId = "";
        //篮球串关
        if (orderNum.startsWith("LCG")) {
            CdBasketballFollowOrder cbf = cdBasketballFollowOrderService.findOrderByOrderNum(orderNum);
            if (cbf == null) {
                return HttpResultUtil.errorJson("订单不存在");
            } else {
                CdLotteryUser clu = cdLotteryUserService.get(cbf.getUid());
                if (clu == null) {
                    return HttpResultUtil.errorJson("用户不存在");
                }
                uId = cbf.getUid();
                uName = clu.getName();//用户名
                uImg = clu.getImg();//头像
                cbf.setType("1");//发起跟单
                cdBasketballFollowOrderService.save(cbf);
                price = cbf.getPrice();
                type = "4";
                String matchIds = cbf.getDanMatchIds();
                String[] matchIdsArray = matchIds.split(",");
                //场次list
                List<String> list = new ArrayList<>();
                for (String s : matchIdsArray) {
                    String[] aMatch = s.split("\\+");
                    list.add(aMatch[1]);
                }
                String endSale = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < list.size(); i++) {
                    CdBasketballMixed cbm = cdBasketballMixedService.findByMatchId(list.get(i));
                    if (cbm == null) {
                        return HttpResultUtil.errorJson("比赛已结束");
                    }
                    if (i == 0) {
                        endSale = cbm.getTimeEndsale();
                    } else {
                        String time = cbm.getTimeEndsale();
                        if (sdf.parse(time).getTime() < sdf.parse(endSale).getTime()) {
                            endSale = time;
                        }
                    }
                }
                shutDownTime = endSale;

            }
            //篮球单关
        } else if (orderNum.startsWith("LDG")) {
            CdBasketballSingleOrder cbs = cdBasketballSingleOrderService.findOrderByOrderNum(orderNum);
            if (cbs == null) {
                return HttpResultUtil.errorJson("订单不存在");
            } else {
                CdLotteryUser clu = cdLotteryUserService.get(cbs.getUid());
                if (clu == null) {
                    return HttpResultUtil.errorJson("用户不存在");
                }
                uId = cbs.getUid();
                uName = clu.getName();//用户名
                uImg = clu.getImg();//头像
                cbs.setType("1");//发起跟单
                cdBasketballSingleOrderService.save(cbs);
                price = cbs.getPrice();
                type = "3";
                String matchIds = cbs.getMatchIds();
                String[] matchIdsArray = matchIds.split(",");
                String endSale = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < matchIdsArray.length; i++) {
                    CdBasketballMixed cbm = cdBasketballMixedService.findByMatchId(matchIdsArray[i]);
                    if (cbm == null) {
                        return HttpResultUtil.errorJson("比赛已结束");
                    }
                    if (i == 0) {
                        endSale = cbm.getTimeEndsale();
                    } else {
                        String time = cbm.getTimeEndsale();
                        if (sdf.parse(time).getTime() < sdf.parse(endSale).getTime()) {
                            endSale = time;
                        }
                    }
                }
                shutDownTime = endSale;
            }
            //足球串关
        } else if (orderNum.startsWith("ZCG")) {
            CdFootballFollowOrder cff = cdFootballFollowOrderService.findOrderByOrderNum(orderNum);
            if (cff == null) {
                return HttpResultUtil.errorJson("订单不存在");
            } else {
                CdLotteryUser clu = cdLotteryUserService.get(cff.getUid());
                if (clu == null) {
                    return HttpResultUtil.errorJson("用户不存在");
                }
                uId = cff.getUid();
                uName = clu.getName();//用户名
                uImg = clu.getImg();//头像
                cff.setType("1");//发起跟单
                cdFootballFollowOrderService.save(cff);
                price = cff.getPrice();
                type = "2";
                String matchIds = cff.getDanMatchIds();
                String[] matchIdsArray = matchIds.split(",");
                //场次list
                List<String> list = new ArrayList<>();
                for (String s : matchIdsArray) {
                    String[] aMatch = s.split("\\+");
                    list.add(aMatch[1]);
                }
                String endSale = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < list.size(); i++) {
                    CdFootballMixed cfm = cdFootballMixedService.findByMatchId(list.get(i));
                    if (cfm == null) {
                        return HttpResultUtil.errorJson("比赛已结束");
                    }
                    if (i == 0) {
                        endSale = cfm.getTimeEndsale();
                    } else {
                        String time = cfm.getTimeEndsale();
                        if (sdf.parse(time).getTime() < sdf.parse(endSale).getTime()) {
                            endSale = time;
                        }
                    }
                }
                shutDownTime = endSale;
            }
            //足球单关
        } else if (orderNum.startsWith("ZDG")) {
            CdFootballSingleOrder cfs = cdFootballSingleOrderService.findOrderByOrderNum(orderNum);
            if (cfs == null) {
                return HttpResultUtil.errorJson("订单不存在");
            } else {
                CdLotteryUser clu = cdLotteryUserService.get(cfs.getUid());
                if (clu == null) {
                    return HttpResultUtil.errorJson("用户不存在");
                }
                uId = cfs.getUid();
                uName = clu.getName();//用户名
                uImg = clu.getImg();//头像
                cfs.setType("1");//发起跟单
                cdFootballSingleOrderService.save(cfs);
                price = cfs.getPrice();
                type = "1";
                String matchIds = cfs.getMatchIds();
                String[] matchIdsArray = matchIds.split(",");
                String endSale = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < matchIdsArray.length; i++) {
                    CdFootballMixed cfm = cdFootballMixedService.findByMatchId(matchIdsArray[i]);
                    if (cfm == null) {
                        return HttpResultUtil.errorJson("比赛已结束");
                    }
                    if (i == 0) {
                        endSale = cfm.getTimeEndsale();
                    } else {
                        String time = cfm.getTimeEndsale();
                        if (sdf.parse(time).getTime() < sdf.parse(endSale).getTime()) {
                            endSale = time;
                        }
                    }
                }
                shutDownTime = endSale;
            }
        }
        //拿到倍率
        String times = cdOrderFollowTimesService.get("1").getTimes();
        CdMagicOrder cmo = new CdMagicOrder();
        cmo.setOrderNum(orderNum);//订单号
        cmo.setCharges(charges);//佣金百分比
        cmo.setFollowCounts("0");//跟买人数
        cmo.setPrice(price);//金额
        cmo.setType(type);//类型
        cmo.setUid(uId);//uid
        cmo.setuImg(uImg);//头像
        cmo.setuName(uName);//姓名
        cmo.setShutDownTime(shutDownTime);//截止时间
        cmo.setTimes(times);//倍数
        try {
            cdMagicOrderService.save(cmo);
            map.put("flag", "1");
        } catch (Exception e) {
            return HttpResultUtil.errorJson("分享失败");
        }


        logger.info("神单提交 magicOrderCommit---------End---------------------");
        return HttpResultUtil.successJson(map);
    }


    /**
     * 获得神单订单列表
     */
    @RequestMapping(value = "getMagicOrderList", method = RequestMethod.POST)
    @ResponseBody
    public String getMagicOrderList(HttpServletRequest request) throws ParseException {
        logger.info("获取神单 getMagicOrderList--------Start-------------------");
        logger.debug("interface 请求--getMagicOrderList-------- Start--------");
        Map map = new HashMap();
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

        List<CdMagicOrder> list = cdMagicOrderService.getMagicOrder(total, count);
        List cList = new ArrayList();
        for (CdMagicOrder c : list) {
            Map cMap = new HashMap();
            cMap.put("id", c.getId());
            cMap.put("uName", c.getuName()); //用户名
            cMap.put("img", Global.getConfig("domain.url") + c.getuImg());//头像
            cMap.put("price", c.getPrice()); //购买金额
            cMap.put("followCounts", c.getFollowCounts()); //跟买人数
            cMap.put("shutDownTime", c.getShutDownTime()); //截止时间
            cMap.put("charges", c.getCharges() + "%"); //佣金
            cMap.put("times", c.getTimes()); //保字
            cList.add(cMap);
        }
        map.put("list", cList);
        logger.info("获取神单 getMagicOrderList---------End---------------------");
        return HttpResultUtil.successJson(map);
    }

    /**
     * 获得神单详情
     */
    @RequestMapping(value = "getMagicOrderDetail", method = RequestMethod.POST)
    @ResponseBody
    public String getMagicOrderDetail(HttpServletRequest request) throws ParseException {
        logger.info("获取神单详情 getMagicOrderDetail--------Start-------------------");
        logger.debug("interface 请求--getMagicOrderDetail-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String id = (String) jsonData.get("id");
        if (StringUtils.isEmpty(id)) {
            logger.error("id为空");
            return HttpResultUtil.errorJson("id为空");
        }

        CdMagicOrder cmo = cdMagicOrderService.get(id);

        Map cMap = new HashMap();
        cMap.put("id", cmo.getId());
        cMap.put("uName", cmo.getuName()); //用户名
        cMap.put("img", Global.getConfig("domain.url") + cmo.getuImg());//头像
        cMap.put("price", cmo.getPrice()); //购买金额
        cMap.put("followCounts", cmo.getFollowCounts()); //跟买人数
        cMap.put("shutDownTime", cmo.getShutDownTime()); //截止时间
        cMap.put("charges", cmo.getCharges() + "%"); //佣金
        cMap.put("times", cmo.getTimes()); //保字
        cMap.put("limit", cmo.getPrice()); //限购
        map.put("cmo", cMap);
        logger.info("获取神单详情 getMagicOrderDetail---------End---------------------");
        return HttpResultUtil.successJson(map);
    }

}
