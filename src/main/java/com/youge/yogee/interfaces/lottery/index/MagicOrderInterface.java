package com.youge.yogee.interfaces.lottery.index;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.lottery.util.SelOrderUtil;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.util;
import com.youge.yogee.modules.cbasketballmixed.entity.CdBasketballMixed;
import com.youge.yogee.modules.cbasketballmixed.service.CdBasketballMixedService;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballBestFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballBestFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballSingleOrderService;
import com.youge.yogee.modules.cfootballmixed.entity.CdFootballMixed;
import com.youge.yogee.modules.cfootballmixed.service.CdFootballMixedService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballBestFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballBestFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.cmagicorder.entity.CdMagicFollowOrder;
import com.youge.yogee.modules.cmagicorder.entity.CdMagicOrder;
import com.youge.yogee.modules.cmagicorder.service.CdMagicFollowOrderService;
import com.youge.yogee.modules.cmagicorder.service.CdMagicOrderService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.service.CdOrderFollowTimesService;
import com.youge.yogee.modules.corder.service.CdOrderService;
import com.youge.yogee.modules.crecord.entity.CdRecordRebate;
import com.youge.yogee.modules.crecord.service.CdRecordRebateService;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhaoyifeng on 2018-03-05.
 */
@Controller
@RequestMapping("${frontPath}")
public class MagicOrderInterface {
    private static final Logger logger = LoggerFactory.getLogger(MagicOrderInterface.class);
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
    @Autowired
    private CdMagicFollowOrderService cdMagicFollowOrderService;
    @Autowired
    private CdOrderService cdOrderService;
    @Autowired
    private CdBasketballBestFollowOrderService cdBasketballBestFollowOrderService;
    @Autowired
    private CdFootballBestFollowOrderService cdFootballBestFollowOrderService;
    @Autowired
    private CdRecordRebateService cdRecordRebateService;

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
        String startPrice = "";
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
                double start = Double.parseDouble(cbf.getAcount()) * 2;
                startPrice = String.valueOf(start);
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
                double start = Double.parseDouble(cbs.getAcount()) * 2;
                startPrice = String.valueOf(start);
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
                double start = Double.parseDouble(cff.getAcount()) * 2;
                startPrice = String.valueOf(start);
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
                double start = Double.parseDouble(cfs.getAcount()) * 2;
                startPrice = String.valueOf(start);
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
        BigDecimal times = cdOrderFollowTimesService.get("1").getTimes();
        CdMagicOrder cmo = new CdMagicOrder();
        cmo.setOrderNum(orderNum);//订单号
        cmo.setCharges(charges.replaceAll("%", ""));//佣金百分比
        cmo.setFollowCounts("0");//跟买人数
        cmo.setPrice(price);//金额
        cmo.setType(type);//类型
        cmo.setUid(uId);//uid
        cmo.setuImg(uImg);//头像
        cmo.setuName(uName);//姓名
        cmo.setShutDownTime(shutDownTime);//截止时间
        cmo.setTimes(times.toString());//倍数
        cmo.setStartPrice(startPrice);//起投
        //更改订单总表
        CdOrder co = cdOrderService.getOrderByOrderNum(orderNum);
        if (co != null) {
            co.setIssue("2");//订单属性 0自购 1跟单 2神单
        }
        try {
            cdMagicOrderService.save(cmo);
            cdOrderService.save(co);
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
//            if (c.getuImg().length() > 2) {
//                cMap.put("img", Global.getConfig("domain.url") + c.getuImg());//头像
//            } else {
//                cMap.put("img", c.getuImg());//头像
//            }
            cMap.put("img", c.getuImg());//头像
            cMap.put("price", c.getPrice()); //购买金额
            cMap.put("followCounts", c.getFollowCounts()); //跟买人数
            cMap.put("shutDownTime", c.getShutDownTime()); //截止时间
            cMap.put("charges", c.getCharges() + "%"); //佣金
            cMap.put("times", c.getTimes()); //保字
            cMap.put("startPrice", c.getStartPrice());//起投
            cMap.put("orderNum", c.getOrderNum());//订单号
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
        String followNum = (String) jsonData.get("followNum");
        if (StringUtils.isEmpty(followNum)) {
            logger.error("followNum为空");
            return HttpResultUtil.errorJson("followNum为空");
        }

        CdMagicOrder cmo = cdMagicOrderService.findOrderByNumber(followNum);
        if (cmo == null) {
            return HttpResultUtil.errorJson("神单不存在");
        }

        Map cMap = new HashMap();
        cMap.put("id", cmo.getId());
        cMap.put("uName", cmo.getuName()); //用户名
//        if (cmo.getuImg().length() > 2) {
//            cMap.put("img", Global.getConfig("domain.url") + cmo.getuImg());//头像
//        } else {
//            cMap.put("img", cmo.getuImg());//头像
//        }

        cMap.put("img", cmo.getuImg());//头像
        cMap.put("price", cmo.getPrice()); //购买金额
        cMap.put("followCounts", cmo.getFollowCounts()); //跟买人数
        cMap.put("shutDownTime", cmo.getShutDownTime()); //截止时间
        cMap.put("charges", cmo.getCharges() + "%"); //佣金
        cMap.put("times", cmo.getTimes()); //保字
        cMap.put("limit", cmo.getPrice()); //限购
        cMap.put("startPrice", cmo.getStartPrice());//起投
        map.put("cmo", cMap);

        List<CdMagicFollowOrder> list = cdMagicFollowOrderService.findByMid(cmo.getId());
        List<Map<String, Object>> cList = new ArrayList();
        for (CdMagicFollowOrder cmfo : list) {
            Map<String, Object> aMap = new HashMap<>();
            aMap.put("uName", cmfo.getuName()); //用户名
            aMap.put("uImg", cmfo.getuImg()); //用户头像
            aMap.put("price", cmfo.getPrice()); //跟买金额
            aMap.put("createDate", cmfo.getCreateDate()); //时间
            cList.add(aMap);
        }
        Date day = new Date();
        String shutDownTime = cmo.getShutDownTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date shutTime = df.parse(shutDownTime);
        String orderNum = cmo.getOrderNum();
        //1足球单关 2足球串关 3篮球单关 4篮球串关
        String type = cmo.getType();
        //订单详情

        List orderDetail = new ArrayList();
        if (day.getTime() > shutTime.getTime()) {
            if ("1".equals(type)) {
                CdFootballSingleOrder cfs = cdFootballSingleOrderService.findOrderByOrderNum(orderNum);
                orderDetail = SelOrderUtil.getFbSingleList(cfs);
                map.put("buyWays", cfs.getBuyWays());
                map.put("followNums", "0");
            } else if ("2".equals(type)) {
                CdFootballFollowOrder cff = cdFootballFollowOrderService.findOrderByOrderNum(orderNum);
                orderDetail = SelOrderUtil.getFbFollowList(cff);
                map.put("buyWays", cff.getBuyWays());
                map.put("followNums", cff.getFollowNum());
            } else if ("3".equals(type)) {
                CdBasketballSingleOrder cbs = cdBasketballSingleOrderService.findOrderByOrderNum(orderNum);
                orderDetail = SelOrderUtil.getBbSingleList(cbs);
                map.put("followNums", "0");
                map.put("buyWays", cbs.getBuyWays());
            } else {
                CdBasketballFollowOrder cbf = cdBasketballFollowOrderService.findOrderByOrderNum(orderNum);
                orderDetail = SelOrderUtil.getBbFollowList(cbf);
                map.put("followNums", cbf.getFollowNums());
                map.put("buyWays", cbf.getBuyWays());
            }
        }
        map.put("cList", cList);
        map.put("orderDetail", orderDetail);
        map.put("type", type);

        logger.info("获取神单详情 getMagicOrderDetail---------End---------------------");
        return HttpResultUtil.successJson(map);
    }


    /**
     * 跟买神单
     */
    @RequestMapping(value = "followMagicOrder", method = RequestMethod.POST)
    @ResponseBody
    public String followMagicOrder(HttpServletRequest request) throws ParseException {
        logger.info("跟买神单 followMagicOrder--------Start-------------------");
        logger.debug("interface 请求--followMagicOrder-------- Start--------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        String mid = (String) jsonData.get("mid");
        if (StringUtils.isEmpty(mid)) {
            logger.error("mid为空");
            return HttpResultUtil.errorJson("mid为空");
        }

        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }

        String times = (String) jsonData.get("times");
        if (StringUtils.isEmpty(mid)) {
            logger.error("times为空");
            return HttpResultUtil.errorJson("times为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        CdMagicOrder cmo = cdMagicOrderService.get(mid);
        if (cmo == null) {
            return HttpResultUtil.errorJson("神单不存在");
        }
        //神单彩种 1足球单关 2足球串关 3篮球单关 4篮球串关
        String type = cmo.getType();
        String orderNum = "";
        String price = "";
        //神单订单号
        String magicOrderNum = cmo.getOrderNum();
        if ("1".equals(type)) {
            CdFootballSingleOrder cfs = cdFootballSingleOrderService.findOrderByOrderNum(magicOrderNum);
            if (cfs == null) {
                return HttpResultUtil.errorJson("比赛不存在");
            }
            CdFootballSingleOrder c = new CdFootballSingleOrder();
            orderNum = util.genOrderNo("ZDG", util.getFourRandom());
            c.setOrderNum(orderNum);//订单号
            //押注详情
            c.setScore(cfs.getScore());
            c.setBeat(cfs.getBeat());
            c.setLet(cfs.getLet());
            c.setGoal(cfs.getGoal());
            c.setHalf(cfs.getHalf());
            //购买方式
            c.setBuyWays(cfs.getBuyWays());
            //价格
            double timesDouble = Double.parseDouble(times);
            double startPriceDouble = Double.parseDouble(cmo.getStartPrice());
            price = String.valueOf(timesDouble * startPriceDouble);
            c.setPrice(price);
            //让球 场次
            c.setLetBalls(cfs.getLetBalls());
            c.setMatchIds(cfs.getMatchIds());
            //注数
            double acountDouble = Double.parseDouble(cfs.getAcount());
            String acount = String.valueOf((int) acountDouble * (int) timesDouble);
            c.setAcount(acount);
            c.setUid(uid); //用户id
            c.setType("2");//跟买
            c.setStatus("2");//已付款
            c.setAward("0");//奖金
            c.setAllMatchTimes(cfs.getAllMatchTimes());//比赛时间

            //更改用户余额
            if (!changeUserBalance(uid, price, type)) {
                return HttpResultUtil.errorJson("余额不足");
            }
            cdFootballSingleOrderService.save(c);
        } else if ("2".equals(type)) {
            CdFootballFollowOrder cff = cdFootballFollowOrderService.findOrderByOrderNum(magicOrderNum);
            if (cff == null) {
                return HttpResultUtil.errorJson("比赛不存在");
            }
            CdFootballFollowOrder c = new CdFootballFollowOrder();
            orderNum = util.genOrderNo("ZCG", util.getFourRandom());
            c.setOrderNum(orderNum); //订单号
            c.setAcount(cff.getAcount());//注数
            c.setAward("0"); //奖金
            //cffo.setOrderDetail(orderDetail); //订单详情
            c.setScore(cff.getScore());//比分详情
            c.setHalf(cff.getHalf());//半全场
            c.setGoal(cff.getGoal());//总进球
            c.setBeat(cff.getBeat());//胜负平
            c.setLet(cff.getLet());//让球胜负平
            c.setLetBalls(cff.getLetBalls());//让球数
            //价格
            double timesDouble = Double.parseDouble(times);
            double startPriceDouble = Double.parseDouble(cmo.getStartPrice());
            price = String.valueOf(timesDouble * startPriceDouble);
            c.setPrice(price);//金额
            c.setStatus("2");//已付款
            c.setUid(uid);//用户
            c.setBuyWays(cff.getBuyWays());//玩法 1混投 2胜负平 3猜比分 4总进球 5半全场 6让球
            c.setFollowNum(cff.getFollowNum());//串关数
            c.setTimes(times); //倍数

            c.setDanMatchIds(cff.getDanMatchIds());//胆场次
            c.setType("2"); //0普通订单 1发起的 2跟单的
            c.setBestType(cff.getBestType());
            c.setAllMatchTimes(cff.getAllMatchTimes());
            //更改用户余额
            if (!changeUserBalance(uid, price, type)) {
                return HttpResultUtil.errorJson("余额不足");
            }
            cdFootballFollowOrderService.save(c);
            if ("2".equals(cff.getBestType())) {
                String oldOrderNum = cff.getOrderNum();
                List<CdFootballBestFollowOrder> fList = cdFootballBestFollowOrderService.findByOrderNum(oldOrderNum);
                for (CdFootballBestFollowOrder cfb : fList) {
                    CdFootballBestFollowOrder cfbfo = new CdFootballBestFollowOrder();
                    cfbfo.setOrderNum(orderNum);
                    cfbfo.setPerAward(cfb.getPerAward());//奖金
                    cfbfo.setMatchIds(cfb.getMatchIds());
                    cfbfo.setOrderDetail(cfb.getOrderDetail());//详情
                    cfbfo.setPerTimes(cfb.getPerTimes());//倍数
                    cdFootballBestFollowOrderService.save(cfbfo);
                }
            }

        } else if ("3".equals(type)) {
            CdBasketballSingleOrder cbs = cdBasketballSingleOrderService.findOrderByOrderNum(magicOrderNum);
            if (cbs == null) {
                return HttpResultUtil.errorJson("比赛不存在");
            }
            CdBasketballSingleOrder c = new CdBasketballSingleOrder();
            orderNum = util.genOrderNo("LDG", util.getFourRandom());
            c.setOrderNum(orderNum); //订单号
            c.setAcount(cbs.getAcount());//注数
            c.setAward("0"); //奖金
            //cbso.setOrderDetail(orderDetail); //订单详情
            c.setHostWin(cbs.getHostWin());
            c.setHostFail(cbs.getHostFail());
            //价格
            double timesDouble = Double.parseDouble(times);
            double startPriceDouble = Double.parseDouble(cmo.getStartPrice());
            price = String.valueOf(timesDouble * startPriceDouble);
            c.setPrice(price);//金额
            c.setStatus("2");//已购买
            c.setUid(uid);//用户
            c.setBuyWays("1"); //玩法 1混投
            c.setType("2"); // 0普通订单 1发起的 2跟单的
            c.setMatchIds(cbs.getMatchIds());//所有场次
            c.setAllMatchTimes(cbs.getAllMatchTimes());//所有场次时间
            //更改用户余额
            if (!changeUserBalance(uid, price, type)) {
                return HttpResultUtil.errorJson("余额不足");
            }
            cdBasketballSingleOrderService.save(c);
        } else {
            CdBasketballFollowOrder cbf = cdBasketballFollowOrderService.findOrderByOrderNum(magicOrderNum);
            if (cbf == null) {
                return HttpResultUtil.errorJson("比赛不存在");
            }
            CdBasketballFollowOrder c = new CdBasketballFollowOrder();
            orderNum = util.genOrderNo("LCG", util.getFourRandom());
            c.setOrderNum(orderNum); //订单号

            c.setAcount(cbf.getAcount());//注数
            c.setAward("0"); //奖金
            //cffo.setOrderDetail(orderDetail); //订单详情
            c.setHostFail(cbf.getHostFail());//主负
            c.setHostWin(cbf.getHostWin());//主胜
            c.setSize(cbf.getSize());//大小分
            c.setBeat(cbf.getBeat());//胜负
            c.setLet(cbf.getLet());//让球胜负

            c.setLetScore(cbf.getLetScore());//让分
            c.setSizeCount(cbf.getSizeCount());//大小分
            //价格
            double timesDouble = Double.parseDouble(times);
            double startPriceDouble = Double.parseDouble(cmo.getStartPrice());
            price = String.valueOf(timesDouble * startPriceDouble);
            c.setPrice(price);//金额
            c.setStatus("2");//已购买
            c.setUid(uid);//用户
            c.setBuyWays(cbf.getBuyWays());//玩法 1混投 2胜负平 3猜比分 4总进球 5半全场 6让球
            c.setFollowNums(cbf.getFollowNums());//串关数
            c.setTimes(times); //倍数
            c.setDanMatchIds(cbf.getDanMatchIds());//胆场次
            c.setType("2"); // 0普通订单 1发起的 2跟单的
            c.setBestType(cbf.getBestType());
            c.setAllMatchTimes(cbf.getAllMatchTimes());
            //更改用户余额
            if (!changeUserBalance(uid, price, type)) {
                return HttpResultUtil.errorJson("余额不足");
            }
            cdBasketballFollowOrderService.save(c);
            if ("2".equals(cbf.getBestType())) {
                String oldOrderNum = cbf.getOrderNum();
                List<CdBasketballBestFollowOrder> bList = cdBasketballBestFollowOrderService.findByOrderNum(oldOrderNum);
                for (CdBasketballBestFollowOrder cbb : bList) {
                    CdBasketballBestFollowOrder cbbfo = new CdBasketballBestFollowOrder();
                    cbbfo.setOrderNum(orderNum);
                    cbbfo.setPerAward(cbb.getPerAward());//奖金
                    cbbfo.setMatchIds(cbb.getMatchIds());
                    cbbfo.setOrderDetail(cbb.getOrderDetail());//详情
                    cbbfo.setPerTimes(cbb.getPerTimes());//倍数
                    cdBasketballBestFollowOrderService.save(cbbfo);

                }
            }
        }
        //添加跟单记录
        CdMagicFollowOrder cmfo = new CdMagicFollowOrder();
        cmfo.setMagicOrderId(mid);//神单id
        cmfo.setOrderNum(orderNum);//订单号
        cmfo.setuImg(clu.getImg());//头像
        cmfo.setuName(clu.getName());//用户名
        cmfo.setPrice(price);//金额
        cmfo.setUid(uid);//用户
        cdMagicFollowOrderService.save(cmfo);
        //修改神单跟单人数
        String followNums = cmo.getFollowCounts();
        int newCount = Integer.parseInt(followNums) + 1;
        cmo.setFollowCounts(String.valueOf(newCount));
        cdMagicOrderService.save(cmo);
        //订单总表添加记录
        CdOrder co = new CdOrder();
        co.setIssue("1");//跟单
        co.setWinPrice("0");//奖金
        co.setType(type);//类型
        co.setUserId(uid);//uid
        co.setUserName(clu.getName());//用户名
        co.setNumber(orderNum);//订单号
        co.setTotalPrice(price);//价格
        co.setFollowNum(magicOrderNum);//神单订单号
        co.setStatus("1");//待开奖
        //本单销售id
//        CdOrder cdOrder = cdOrderService.getOrderByOrderNum(cmo.getOrderNum());
//        String saleId = "";
//        if (cdOrder != null) {
//            saleId = cdOrder.getSaleId();
//        }
        co.setWinStatus("0");//未开奖
        co.setSaleId(clu.getSaleId());
        cdOrderService.save(co);
        logger.info("跟买神单 followMagicOrder---------End---------------------");
        return HttpResultUtil.successJson(map);
    }

    /**
     * 获得跟单记录
     */
    @RequestMapping(value = "getMagicOrderFollowRecord", method = RequestMethod.POST)
    @ResponseBody
    public String getMagicOrderFollowRecord(HttpServletRequest request) {
        logger.info("getMagicOrderFollowRecord--------Start-------------------");
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

        List<CdMagicFollowOrder> list = cdMagicFollowOrderService.findAll(total, count);
        List cList = new ArrayList();
        for (CdMagicFollowOrder c : list) {
            Map cMap = new HashMap();
            String mid = c.getMagicOrderId();
            CdMagicOrder cmo = cdMagicOrderService.get(mid);
            if (cmo != null) {
                CdLotteryUser clu = cdLotteryUserService.get(cmo.getUid());
                String followName = c.getuName();
                String startName = clu.getName();
                cMap.put("followName", followName);//跟单者
                cMap.put("startName", startName); //神单发起者
                cMap.put("price", c.getPrice()); //购买金额
                cList.add(cMap);
            }
        }
        map.put("list", cList);
        logger.info("获取神单 getMagicOrderList---------End---------------------");
        return HttpResultUtil.successJson(map);
    }


    private boolean changeUserBalance(String uid, String price, String type) {
        boolean flag = false;
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        BigDecimal balance = clu.getBalance();
        int compare = balance.compareTo(new BigDecimal(price));
        if (compare >= 0) {
            BigDecimal newBalance = balance.subtract(new BigDecimal(price));
            clu.setBalance(newBalance);
            cdLotteryUserService.save(clu);
            saveRebate(price, clu, type);
            flag = true;
        }
        return flag;
    }


    //保存返利
    public void saveRebate(String price, CdLotteryUser cdLotteryUser, String type) {
        double priceDouble = Double.parseDouble(price);
        BigDecimal priceBig = new BigDecimal(price);
        boolean flag = false;
        String rebate = "";
        if (priceDouble > 0.0 && priceDouble < 1000.0) {
            flag = true;
            BigDecimal result = priceBig.multiply(new BigDecimal(0.01));
            rebate = String.valueOf(result.setScale(2, 1));
        } else if (priceDouble >= 1000.0 && priceDouble < 10000.0) {
            flag = true;
            BigDecimal result = priceBig.multiply(new BigDecimal(0.02));
            rebate = String.valueOf(result.setScale(2, 1));
        } else if (priceDouble >= 10000.0) {
            flag = true;
            BigDecimal result = priceBig.multiply(new BigDecimal(0.03));
            rebate = String.valueOf(result.setScale(2, 1));
        }
        if (flag) {
            CdRecordRebate crr = new CdRecordRebate();
            crr.setRebate(rebate);
            crr.setUid(cdLotteryUser.getId());
            crr.setType(type);
            cdRecordRebateService.save(crr);
            //保存用户表返利字段
            String userRebate = cdLotteryUser.getRebate();
            BigDecimal newRebate = new BigDecimal(userRebate).add(new BigDecimal(rebate));
            cdLotteryUser.setRebate(String.valueOf(newRebate));//更新返利
            cdLotteryUserService.save(cdLotteryUser);
        }
    }

}
