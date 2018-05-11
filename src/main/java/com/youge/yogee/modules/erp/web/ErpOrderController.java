/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.CookieUtils;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.interfaces.lottery.util.SelOrderUtil;
import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballBestFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballSingleOrderService;
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNineOrder;
import com.youge.yogee.modules.cchoosenine.service.CdChooseNineOrderService;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveOrder;
import com.youge.yogee.modules.cfiveawards.service.CdFiveOrderService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballBestFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballBestFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballFollowOrderService;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;
import com.youge.yogee.modules.clottoreward.entity.CdLottoOrder;
import com.youge.yogee.modules.clottoreward.service.CdLottoOrderService;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFailOrder;
import com.youge.yogee.modules.csuccessfail.service.CdSuccessFailOrderService;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeOrder;
import com.youge.yogee.modules.cthreeawards.service.CdThreeOrderService;
import com.youge.yogee.modules.erp.entity.ErpBasketBallDto;
import com.youge.yogee.modules.erp.entity.ErpBestOrderDto;
import com.youge.yogee.modules.erp.entity.ErpFootBallDto;
import com.youge.yogee.modules.erp.entity.ErpOrder;
import com.youge.yogee.modules.erp.service.ErpOrderService;
import com.youge.yogee.modules.sys.entity.User;
import com.youge.yogee.modules.sys.service.SystemService;
import com.youge.yogee.modules.sys.utils.UserUtils;
import org.apache.poi.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 业绩Controller
 *
 * @author RenHaipeng
 * @version 2018-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/erp/erpOrder")
public class ErpOrderController extends BaseController {

    @Autowired
    private ErpOrderService erpOrderService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private CdFootballSingleOrderService cdFootballSingleOrderService;
    @Autowired
    private CdBasketballFollowOrderService cdBasketballFollowOrderService;
    @Autowired
    private CdFootballFollowOrderService cdFootballFollowOrderService;
    @Autowired
    private CdBasketballSingleOrderService cdBasketballSingleOrderService;
    @Autowired
    private CdChooseNineOrderService cdChooseNineOrderService;
    @Autowired
    private CdSuccessFailOrderService cdSuccessFailOrderService;
    @Autowired
    private CdFiveOrderService cdFiveOrderService;
    @Autowired
    private CdThreeOrderService cdThreeOrderService;
    @Autowired
    private CdLottoOrderService cdLottoOrderService;
    @Autowired
    private CdFootballBestFollowOrderService cdFootballBestFollowOrderService;
    @Autowired
    private CdBasketballBestFollowOrderService cdBasketballBestFollowOrderService;

    @ModelAttribute
    public ErpOrder get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return erpOrderService.get(id);
        } else {
            return new ErpOrder();
        }
    }

    @RequiresPermissions("erp:erpOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(ErpOrder erpOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();
        Page<ErpOrder> page = erpOrderService.find(new Page<ErpOrder>(request, response), erpOrder);
        model.addAttribute("page", page);
        return "modules/erp/erpOrderList";
    }

    @ResponseBody
    @RequestMapping(value = "indexa")
    public Map indexa(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getUser();
        // 未登录，则跳转到登录页
        if (user.getId() == null) {
            return null;
        }

        // 登录成功后，获取上次登录的当前站点ID
        UserUtils.putCache("siteId", CookieUtils.getCookie(request, "siteId"));
        //获取充值记录
        String count = "5";
        List rechargeList = systemService.findRechargeList(count);
        //获取注册记录
        List registerList = systemService.findRegisterList(count);
        Map map = new HashMap();
        map.put("registerList", registerList);
        map.put("rechargeList", rechargeList);
        return map;
    }

    @RequiresPermissions("erp:erpOrder:view")
    @RequestMapping(value = "form")
    public String form(ErpOrder erpOrder, Model model) {
        model.addAttribute("erpOrder", erpOrder);
        return "modules/erp/erpOrderForm";
    }

    @RequiresPermissions("erp:erpOrder:edit")
    @RequestMapping(value = "save")
    public String save(ErpOrder erpOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, erpOrder)) {
            return form(erpOrder, model);
        }
        erpOrderService.save(erpOrder);
        addMessage(redirectAttributes, "保存业绩'" + erpOrder.getName() + "'成功");
        return "redirect:" + Global.getAdminPath() + "/erp/erpOrder/?repage";
    }

    @RequiresPermissions("erp:erpOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        erpOrderService.delete(id);
        addMessage(redirectAttributes, "删除业绩成功");
        return "redirect:" + Global.getAdminPath() + "/erp/erpOrder/?repage";
    }


    @RequiresPermissions("erp:erpOrder:view")
    @RequestMapping(value = "performanceList")
    public String performanceList(HttpServletRequest request, HttpServletResponse response, Model model) {
        String userId = UserUtils.getUser().getId();
        Page<Map<String, String>> page = erpOrderService.findRegisterList(new Page<Map<String, String>>(request, response), userId);
        model.addAttribute("page", page);
        return "modules/erp/erpPerformanceList";
    }


    @RequiresPermissions("erp:erpOrder:view")
    @RequestMapping(value = "orderForm")
    public String orderForm(ErpOrder erpOrder, Model model) throws ParseException {
        model.addAttribute("erpOrder", erpOrder);
        String uName = erpOrder.getUserId().getReality();
        String orderNum = erpOrder.getNumber();
        if (orderNum.startsWith("LCG")) {//篮球串关
            CdBasketballFollowOrder cdBasketballFollowOrder = cdBasketballFollowOrderService.findOrderByOrderNum(orderNum);
//            String uid = cdBasketballFollowOrder.getUid();
//            if (StringUtils.isNotEmpty(uid)) {
////            CdLotteryUser clu = cdLotteryUserService.get(uid);
////            uName = clu.getReality();
//            }
//            String hostWin = cdBasketballFollowOrder.getHostWin();
//            List<String> wList = new ArrayList<>();
//
//            String hostFail = cdBasketballFollowOrder.getHostFail();
//            List<String> fList = new ArrayList<>();
//
//            String beat = cdBasketballFollowOrder.getBeat();
//            List<String> bList = new ArrayList<>();
//
//            String size = cdBasketballFollowOrder.getSize();
//            List<String> sList = new ArrayList<>();
//
//            String let = cdBasketballFollowOrder.getLet();
//            List<String> tList = new ArrayList<>();
//            if (StringUtils.isNotEmpty(hostWin)) {
//                String[] winStr = hostWin.split("\\|");
//                for (String s : winStr) {
//                    wList.add(s);
//                }
//            }
//            if (StringUtils.isNotEmpty(hostFail)) {
//                String[] failStr = hostFail.split("\\|");
//                for (String s : failStr) {
//                    fList.add(s);
//                }
//            }
//            if (StringUtils.isNotEmpty(beat)) {
//                String[] beatStr = beat.split("\\|");
//                for (String s : beatStr) {
//                    bList.add(s);
//                }
//            }
//            if (StringUtils.isNotEmpty(size)) {
//                String[] sizeStr = size.split("\\|");
//                for (String s : sizeStr) {
//                    sList.add(s);
//                }
//            }
//            if (StringUtils.isNotEmpty(let)) {
//                String[] letStr = let.split("\\|");
//                for (String s : letStr) {
//                    tList.add(s);
//                }
//            }
            //获取当前时间
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String today = df.format(day);

            model.addAttribute("today", today);
            model.addAttribute("uName", uName);
//            model.addAttribute("wList", wList);
//            model.addAttribute("fList", fList);
//            model.addAttribute("bList", bList);
//            model.addAttribute("sList", sList);
//            model.addAttribute("tList", tList);

            List<ErpBasketBallDto> finalDetailList = new ArrayList<>();
            List detailList = SelOrderUtil.getBbFollowList2(cdBasketballFollowOrder);
            for (int i = 0; i < detailList.size(); i++) {
                //String detail = "";
                Map map = (Map) detailList.get(i);
                ErpBasketBallDto ebbd = new ErpBasketBallDto();
                String matchId = (String) map.get("matchId");
                String vs = (String) map.get("vs");
                String win = (String) map.get("win");
                String fail = (String) map.get("fail");
                String size = (String) map.get("size");
                String beat = (String) map.get("beat");
                String let = (String) map.get("let");
//yhw  修改让问题
                if (StringUtils.isNotEmpty(win)) {
                    win = "让主胜" + win;
                }
                if (StringUtils.isNotEmpty(fail)) {
                    fail = "让主负" + fail;
                }
                ebbd.setMatId(matchId);
                ebbd.setVs(vs);
                ebbd.setBeat(beat);
                ebbd.setSize(size);
                ebbd.setLet(let);
                ebbd.setWin(win);
                ebbd.setFail(fail);
                finalDetailList.add(ebbd);
            }
            model.addAttribute("detailList", finalDetailList);


            model.addAttribute("cdBasketballFollowOrder", cdBasketballFollowOrder);
            return "modules/erp/erpBasketballFollowOrderForm";
        }
        if (orderNum.startsWith("ZDG")) {//足球单关
            CdFootballSingleOrder cdFootballSingleOrder = cdFootballSingleOrderService.findOrderByOrderNum(orderNum);
//            String uid = cdFootballSingleOrder.getUid();
//            if (StringUtils.isNotEmpty(uid)) {
//            }
//            String score = cdFootballSingleOrder.getScore();
//            List<String> sList = new ArrayList<>();
//            String goal = cdFootballSingleOrder.getGoal();
//            List<String> gList = new ArrayList<>();
//            String half = cdFootballSingleOrder.getHalf();
//            List<String> hList = new ArrayList<>();
//            String beat = cdFootballSingleOrder.getBeat();
//            List<String> bList = new ArrayList<>();
//            String let = cdFootballSingleOrder.getLet();
//            List<String> tList = new ArrayList<>();
//            if (StringUtils.isNotEmpty(score)) {
//                String[] scoreStr = score.split("\\|");
//                for (String s : scoreStr) {
//                    sList.add(s);
//                }
//            }
//            if (StringUtils.isNotEmpty(goal)) {
//                String[] goalStr = goal.split("\\|");
//                for (String s : goalStr) {
//                    gList.add(s);
//                }
//            }
//            if (StringUtils.isNotEmpty(half)) {
//                String[] halfStr = half.split("\\|");
//                for (String s : halfStr) {
//                    hList.add(s);
//                }
//            }
//            if (StringUtils.isNotEmpty(beat)) {
//                String[] beatStr = beat.split("\\|");
//                for (String s : beatStr) {
//                    bList.add(s);
//                }
//            }
//            if (StringUtils.isNotEmpty(let)) {
//                String[] letStr = let.split("\\|");
//                for (String s : letStr) {
//                    tList.add(s);
//                }
//            }
            //获取当前时间
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String today = df.format(day);
            model.addAttribute("today", today);
//            model.addAttribute("sList", sList);
//            model.addAttribute("gList", gList);
//            model.addAttribute("hList", hList);
//            model.addAttribute("bList", bList);
//            model.addAttribute("tList", tList);
            model.addAttribute("uName", uName);
            model.addAttribute("cdFootballSingleOrder", cdFootballSingleOrder);

            List<ErpFootBallDto> finalDetailList = new ArrayList<>();
            List detailList = SelOrderUtil.getFbSingleList2(cdFootballSingleOrder);
            for (int i = 0; i < detailList.size(); i++) {
                //String detail = "";
                Map map = (Map) detailList.get(i);
                ErpFootBallDto efbd = new ErpFootBallDto();
                String matchId = (String) map.get("matchId");
                String vs = (String) map.get("vs");
                String score = (String) map.get("score");
                String goal = (String) map.get("goal");
                String half = (String) map.get("half");
                String beat = (String) map.get("beat");
                String let = (String) map.get("let");
                if (StringUtils.isNotEmpty(let)) {
                    if (let.startsWith("平")) {
                        let = "让" + let;
                    }
                }
//                detail += matchId + "   " + vs + "   ";
//                if (StringUtils.isNotEmpty(score)) {
//                    detail += score + "   ";
//                }
//                if (StringUtils.isNotEmpty(goal)) {
//                    detail += goal + "   ";
//                }
//                if (StringUtils.isNotEmpty(half)) {
//                    detail += half + "   ";
//                }
//                if (StringUtils.isNotEmpty(beat)) {
//                    detail += beat + "   ";
//                }
//                if (StringUtils.isNotEmpty(let)) {
//                    detail += let + "   ";
//                }
//                finalDetailList.add(detail);
                efbd.setMatId(matchId);
                efbd.setVs(vs);
                efbd.setBeat(beat);
                efbd.setScore(score);
                efbd.setGoal(goal);
                efbd.setHalf(half);
                efbd.setLet(let);
                finalDetailList.add(efbd);
            }
            model.addAttribute("detailList", finalDetailList);
            return "modules/erp/erpFootballSingleOrderForm";
        }
        if (orderNum.startsWith("LDG")) {//篮球单关
            CdBasketballSingleOrder cdBasketballSingleOrder = cdBasketballSingleOrderService.findOrderByOrderNum(orderNum);
//            String uid = cdBasketballSingleOrder.getUid();
//            if (StringUtils.isNotEmpty(uid)) {
//            }
//            String hostWin = cdBasketballSingleOrder.getHostWin();
//            List<String> wList = new ArrayList<>();
//
//            String hostFail = cdBasketballSingleOrder.getHostFail();
//            List<String> fList = new ArrayList<>();
//
//
//            if (StringUtils.isNotEmpty(hostWin)) {
//                String[] winStr = hostWin.split("\\|");
//                for (String s : winStr) {
//                    wList.add(s);
//                }
//            }
//
//            if (StringUtils.isNotEmpty(hostFail)) {
//                String[] failStr = hostFail.split("\\|");
//                for (String s : failStr) {
//                    fList.add(s);
//                }
//            }

            //获取当前时间
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String today = df.format(day);


//            Date day1 = new Date();
//            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            String today = df.format(day);

            model.addAttribute("today", today);
            model.addAttribute("uName", uName);
//            model.addAttribute("wList", wList);
//            model.addAttribute("fList", fList);
//            model.addAttribute("bList", bList);
//            model.addAttribute("sList", sList);
//            model.addAttribute("tList", tList);

            List<ErpBasketBallDto> finalDetailList = new ArrayList<>();
            List detailList = SelOrderUtil.getBbSingleList2(cdBasketballSingleOrder);
            for (int i = 0; i < detailList.size(); i++) {
                //String detail = "";
                Map map = (Map) detailList.get(i);
                ErpBasketBallDto ebbd = new ErpBasketBallDto();
                String matchId = (String) map.get("matchId");
                String vs = (String) map.get("vs");
                String win = (String) map.get("win");
                String fail = (String) map.get("fail");
//                String size = (String) map.get("size");
//                String beat = (String) map.get("beat");
//                String let = (String) map.get("let");
//yhw  修改让问题
                if (StringUtils.isNotEmpty(win)) {
                    win = "让主胜" + win;
                }
                if (StringUtils.isNotEmpty(fail)) {
                    fail = "让主负" + fail;
                }
                ebbd.setMatId(matchId);
                ebbd.setVs(vs);
//                ebbd.setBeat(beat);
//                ebbd.setSize(size);
//                ebbd.setLet(let);
                ebbd.setWin(win);
                ebbd.setFail(fail);
                finalDetailList.add(ebbd);
            }
            model.addAttribute("detailList", finalDetailList);

            model.addAttribute("today", today);
//            model.addAttribute("wList", wList);
//            model.addAttribute("fList", fList);
            model.addAttribute("uName", uName);

            model.addAttribute("cdBasketballSingleOrder", cdBasketballSingleOrder);
            return "modules/erp/erpBasketballSingleOrderForm";
        }
        if (orderNum.startsWith("ZCG")) {//足球串关
            CdFootballFollowOrder cdFootballFollowOrder = cdFootballFollowOrderService.findOrderByOrderNum(orderNum);
//			String uid = cdFootballFollowOrder.getUid();
//			if (StringUtils.isNotEmpty(uid)) {
//			}
//			String score = cdFootballFollowOrder.getScore();
//			List<String> sList = new ArrayList<>();
//			String goal = cdFootballFollowOrder.getGoal();
//			List<String> gList = new ArrayList<>();
//			String half = cdFootballFollowOrder.getHalf();
//			List<String> hList = new ArrayList<>();
//			String beat = cdFootballFollowOrder.getBeat();
//			List<String> bList = new ArrayList<>();
//			String let = cdFootballFollowOrder.getLet();
//			List<String> tList = new ArrayList<>();
//			if (StringUtils.isNotEmpty(score)) {
//				String[] scoreStr = score.split("\\|");
//				for (String s : scoreStr) {
//					sList.add(s);
//				}
//			}
//
//			if (StringUtils.isNotEmpty(goal)) {
//				String[] goalStr = goal.split("\\|");
//				for (String s : goalStr) {
//					gList.add(s);
//				}
//			}
//
//			if (StringUtils.isNotEmpty(half)) {
//				String[] halfStr = half.split("\\|");
//				for (String s : halfStr) {
//					hList.add(s);
//				}
//			}
//
//			if (StringUtils.isNotEmpty(beat)) {
//				String[] beatStr = beat.split("\\|");
//				for (String s : beatStr) {
//					bList.add(s);
//				}
//			}
//
//			if (StringUtils.isNotEmpty(let)) {
//				String[] letStr = let.split("\\|");
//				for (String s : letStr) {
//					tList.add(s);
//				}
//			}
            //获取当前时间
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String today = df.format(day);
//
            model.addAttribute("today", today);
//			model.addAttribute("sList", sList);
//			model.addAttribute("gList", gList);
//			model.addAttribute("hList", hList);
//			model.addAttribute("bList", bList);
//			model.addAttribute("tList", tList);
            model.addAttribute("uName", uName);
            model.addAttribute("cdFootballFollowOrder", cdFootballFollowOrder);
            List<ErpFootBallDto> finalDetailList = new ArrayList<>();
            List detailList = SelOrderUtil.getFbFollowList2(cdFootballFollowOrder);
            for (int i = 0; i < detailList.size(); i++) {
                //String detail = "";
                Map map = (Map) detailList.get(i);
                ErpFootBallDto efbd = new ErpFootBallDto();
                String matchId = (String) map.get("matchId");
                String vs = (String) map.get("vs");
                String score = (String) map.get("score");
                String goal = (String) map.get("goal");
                String half = (String) map.get("half");
                String beat = (String) map.get("beat");
                String let = (String) map.get("let");
                if (StringUtils.isNotEmpty(let)) {
                    if (let.startsWith("平")) {
                        let = "让" + let;
                    }
                }
//                detail += matchId + "   " + vs + "   ";
//                if (StringUtils.isNotEmpty(score)) {
//                    detail += score + "   ";
//                }
//                if (StringUtils.isNotEmpty(goal)) {
//                    detail += goal + "   ";
//                }
//                if (StringUtils.isNotEmpty(half)) {
//                    detail += half + "   ";
//                }
//                if (StringUtils.isNotEmpty(beat)) {
//                    detail += beat + "   ";
//                }
//                if (StringUtils.isNotEmpty(let)) {
//                    detail += let + "   ";
//                }
//                finalDetailList.add(detail);
                efbd.setMatId(matchId);
                efbd.setVs(vs);
                efbd.setBeat(beat);
                efbd.setScore(score);
                efbd.setGoal(goal);
                efbd.setHalf(half);
                efbd.setLet(let);
                finalDetailList.add(efbd);
            }
            model.addAttribute("detailList", finalDetailList);

            if ("2".equals(cdFootballFollowOrder.getBestType())) {
                List<ErpBestOrderDto> list = new ArrayList<>();
                List<CdFootballBestFollowOrder> bestList = cdFootballBestFollowOrderService.findByOrderNum(cdFootballFollowOrder.getOrderNum());
                for (CdFootballBestFollowOrder cfbf : bestList) {
                    ErpBestOrderDto ebod = new ErpBestOrderDto();
                    String detail = cfbf.getOrderDetail();
                    String[] detailArray = detail.split("\\|");
                    //String[] finalDetailArray = new String[detailArray.length];
                    String aDetailArrayDetail = "";
                    for (String str : detailArray) {
                        String[] aDetailArray = str.split("\\+");
                        String lastDetail = aDetailArray[0] + "[" + aDetailArray[3] + "]" + "(" + aDetailArray[2] + ")" + changeType(aDetailArray[1]);
                        aDetailArrayDetail += lastDetail + "|";
                    }
                    String[] lastDetailArray = aDetailArrayDetail.split("\\|");
                    ebod.setDetail(lastDetailArray);
                    ebod.setFollowNums(String.valueOf(lastDetailArray.length));
                    ebod.setPerTimes(cfbf.getPerTimes());
                    list.add(ebod);
                }
                model.addAttribute("bestDetail", list);
            }

            return "modules/erp/erpFootballFollowOrderForm";
        }
        if (orderNum.startsWith("RXJ")) {//任选九
            CdChooseNineOrder cdChooseNineOrder = cdChooseNineOrderService.findOrderByOrderNum(orderNum);
            List<String> list = new ArrayList<>();
            if (cdChooseNineOrder != null) {
                String detail = cdChooseNineOrder.getOrderDetail();
                if (StringUtils.isNotEmpty(detail)) {
                    String detailStr[] = detail.split("\\|");
                    for (String s : detailStr) {
                        list.add(s);
                    }
                }
            }


            model.addAttribute("uName", uName);
            model.addAttribute("cdChooseNineOrder", cdChooseNineOrder);
            model.addAttribute("list", list);

            return "modules/cchoosenine/cdChooseNineOrderForm";
        }
        if (orderNum.startsWith("SFC")) {//胜负彩
            CdSuccessFailOrder cdSuccessFailOrder = cdSuccessFailOrderService.findOrderByOrderNum(orderNum);
            List<String> list = new ArrayList<>();
            if (cdSuccessFailOrder != null) {
                String detail = cdSuccessFailOrder.getOrderDetail();
                if (StringUtils.isNotEmpty(detail)) {
                    String detailStr[] = detail.split("\\|");
                    for (String s : detailStr) {
                        list.add(s);
                    }
                }
            }

            model.addAttribute("list", list);
            model.addAttribute("uName", uName);
            model.addAttribute("cdSuccessFailOrder", cdSuccessFailOrder);
            return "modules/csuccessfail/cdSuccessFailOrderForm";
        }
        if (orderNum.startsWith("PLS")) {//排列三
            CdThreeOrder cdThreeOrder = cdThreeOrderService.findOrderByOrderNum(orderNum);
            String buyWays = cdThreeOrder.getBuyWays();
            String buyWaysStr = "";
            if ("1".equals(buyWays)) {
                buyWaysStr = "直选";
            }
            if ("2".equals(buyWays)) {
                buyWaysStr = "和值";
            }
            if ("3".equals(buyWays)) {
                buyWaysStr = "组三单式";
            }
            if ("4".equals(buyWays)) {
                buyWaysStr = "组三复式";
            }
            if ("5".equals(buyWays)) {
                buyWaysStr = "组六单式";
            }
            if ("6".equals(buyWays)) {
                buyWaysStr = "组六复式";
            }
            model.addAttribute("buyWaysStr", buyWaysStr);
            model.addAttribute("cdThreeOrder", cdThreeOrder);
            return "modules/cthreeawards/cdThreeOrderForm";
        }
        if (orderNum.startsWith("PLW")) {//排列五
            CdFiveOrder cdFiveOrder = cdFiveOrderService.findOrderByOrderNum(orderNum);
            model.addAttribute("cdFiveOrder", cdFiveOrder);
            return "modules/cfiveawards/cdFiveOrderForm";
        }
        if (orderNum.startsWith("DLT")) {//大乐透
            CdLottoOrder cdLottoOrder = cdLottoOrderService.findOrderByOrderNum(orderNum);
            String type = cdLottoOrder.getType();
            String typeStr = "";
            if ("1".equals(type)) {
                typeStr = "普通";
            } else {
                typeStr = "胆拖";
            }
            model.addAttribute("typeStr", typeStr);
            model.addAttribute("cdLottoOrder", cdLottoOrder);
            return "modules/clottoreward/cdLottoOrderForm";
        }
        return null;
    }

    /***********************************************暂时没用******************************************************/
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

    public Map getDetailMap(String aOrderDetail, List<String> rList) {
        int i = 0;
        String[] aOrderDetailArray = aOrderDetail.split("\\+");
        Map detailMap = new HashMap();
        detailMap.put("no", aOrderDetailArray[0]);//序号
        detailMap.put("vs", aOrderDetailArray[1]);//比赛队伍
        //detailMap.put("codes", aOrderDetailArray[2]);//投注详情
        String aDetail = aOrderDetailArray[2];
        String newAdetail = aDetail.replace("3", "胜");
        newAdetail = newAdetail.replace("1", "平");
        newAdetail = newAdetail.replace("0", "负");
        detailMap.put("codes", newAdetail);//投注详情
        String mResult = "";
        if (rList.size() > 0) {
            mResult = rList.get(i);
            if ("3".equals(mResult)) {
                mResult = "胜";
            } else if ("1".equals(mResult)) {
                mResult = "平";
            } else if ("0".equals(mResult)) {
                mResult = "负";
            } else {
                mResult = "*";
            }
            i++;
        }
        detailMap.put("mResult", mResult);//投注详情

        return detailMap;
    }

    public Map<String, String> getSingleMap(String s, String[] strArray) {
        Map<String, String> map = new HashMap<>();
        if (strArray.length > 0) {
            for (String aStr : strArray) {
                String[] aStrArray = aStr.split("\\+");
                if (s.equals(aStrArray[0])) {
                    map.put("vs", aStrArray[1]);
                    map.put("result", aStrArray[2]);
                }
            }
        }
        return map;
    }

    public Map<String, String> getFollowMap(String s, String[] strArray) {
        Map<String, String> map = new HashMap<>();
        if (strArray.length > 0) {
            for (String aStr : strArray) {
                String[] aStrArray = aStr.split("\\+");
                if (s.equals(aStrArray[1])) {
                    map.put("vs", aStrArray[2]);
                    map.put("result", aStrArray[3]);
                }
            }
        }
        return map;
    }

    public List getFbFollowList(CdFootballFollowOrder cff) {
        List detailList = new ArrayList();
        //比分
        String score = cff.getScore();
        String[] scoreArray = score.split("\\|");
        //进球
        String goal = cff.getGoal();
        String[] goalArray = goal.split("\\|");
        //半全场
        String half = cff.getHalf();
        String[] halfArray = half.split("\\|");
        //胜负
        String beat = cff.getBeat();
        String[] beatArray = beat.split("\\|");
        //让球
        String let = cff.getLet();
        String[] letArray = let.split("\\|");
        //所有比赛
        String matchIds = cff.getDanMatchIds();
        String[] matchIdsArray = matchIds.split(",");
        String vs = "";
        String matchResult = cff.getResult();//比赛结果
        int i = 0;
        for (String s : matchIdsArray) {
            List<String> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(matchResult)) {
                String[] mathchResultArray = matchResult.split(",");
                for (String rs : mathchResultArray) {
                    resultList.add(rs);
                }
            }

            String match = s.split("\\+")[1];
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("matchId", match);
            //比赛结果
            if (resultList.size() > 0) {
                orderMap.put("result", resultList.get(i));
                i++;
            } else {
                orderMap.put("result", "");
            }

            //比分
            Map<String, String> scoreMap = getFollowMap(match, scoreArray);
            if (scoreMap.size() > 0) {
                vs = scoreMap.get("vs");
            }
            orderMap.put("score", scoreMap.get("result"));
            //进球
            Map<String, String> goalMap = getFollowMap(match, goalArray);
            if (goalMap.size() > 0) {
                vs = goalMap.get("vs");
            }
            orderMap.put("goal", goalMap.get("result"));
            //半全场
            String halfName = "";
            Map<String, String> halfMap = getFollowMap(match, halfArray);
            if (halfMap.size() > 0) {
                vs = halfMap.get("vs");
                Map<String, String> nameMap = BallGameCals.getHalfWholeNames();
                String result = halfMap.get("result");
                String resultArray[] = result.split(",");
                for (String r : resultArray) {
                    String wholeResult = "";
                    String[] rArray = r.split("/");
                    wholeResult = nameMap.get(rArray[0]) + "/" + rArray[1];
                    halfName += wholeResult + ",";
                }
            }
            orderMap.put("half", halfName); //半全场
            //胜负平
            Map<String, String> beatMap = getFollowMap(match, beatArray);
            if (beatMap.size() > 0) {
                vs = beatMap.get("vs");
            }
            orderMap.put("beat", beatMap.get("result"));
            //让球
            Map<String, String> letMap = getFollowMap(match, letArray);
            if (letMap.size() > 0) {
                vs = letMap.get("vs");
            }
            orderMap.put("let", letMap.get("result"));
            orderMap.put("vs", vs);

            detailList.add(orderMap);
        }
        return detailList;
    }

    public List getFbSingleList(CdFootballSingleOrder cfs) {
        List detailList = new ArrayList();
        //比分
        String score = cfs.getScore();
        String[] scoreArray = score.split("\\|");
        //进球
        String goal = cfs.getGoal();
        String[] goalArray = goal.split("\\|");
        //半全场
        String half = cfs.getHalf();
        String[] halfArray = half.split("\\|");
        //胜负
        String beat = cfs.getBeat();
        String[] beatArray = beat.split("\\|");
        //让球
        String let = cfs.getLet();
        String[] letArray = let.split("\\|");
        //所有比赛
        String matchIds = cfs.getMatchIds();
        String[] matchIdsArray = matchIds.split(",");
        String vs = "";
        String matchResult = cfs.getResult();//比赛结果
        int i = 0;
        for (String s : matchIdsArray) {
            List<String> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(matchResult)) {
                String[] mathchResultArray = matchResult.split(",");
                for (String rs : mathchResultArray) {
                    resultList.add(rs);
                }
            }

            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("matchId", s); //期次
            //比赛结果
            if (resultList.size() > 0) {
                orderMap.put("result", resultList.get(i));
                i++;
            } else {
                orderMap.put("result", "");
            }
            //比分
            Map<String, String> scoreMap = getSingleMap(s, scoreArray);
            if (scoreMap.size() > 0) {
                vs = scoreMap.get("vs"); //队伍
            }
            orderMap.put("score", scoreMap.get("result")); //比分
            //进球
            Map<String, String> goalMap = getSingleMap(s, goalArray);
            if (goalMap.size() > 0) {
                vs = goalMap.get("vs");
            }
            orderMap.put("goal", goalMap.get("result")); //进球
            //半全场
            String halfName = "";
            Map<String, String> halfMap = getSingleMap(s, halfArray);
            if (halfMap.size() > 0) {
                vs = halfMap.get("vs");
                Map<String, String> nameMap = BallGameCals.getHalfWholeNames();
                String result = halfMap.get("result");
                String resultArray[] = result.split(",");
                for (String r : resultArray) {
                    String wholeResult = "";
                    String[] rArray = r.split("/");
                    wholeResult = nameMap.get(rArray[0]) + "/" + rArray[1];
                    halfName += wholeResult + ",";
                }
            }
            orderMap.put("half", halfName); //半全场
            //胜负平
            Map<String, String> beatMap = getSingleMap(s, beatArray);
            if (beatMap.size() > 0) {
                vs = beatMap.get("vs");
            }
            orderMap.put("beat", beatMap.get("result")); //胜负
            //让球
            Map<String, String> letMap = getSingleMap(s, letArray);
            if (letMap.size() > 0) {
                vs = letMap.get("vs");
            }
            orderMap.put("let", letMap.get("result")); //让球
            orderMap.put("vs", vs);
            detailList.add(orderMap);
        }
        return detailList;
    }

    public List getBbSingleList(CdBasketballSingleOrder cbs) {
        List detailList = new ArrayList();
        //主胜
        String hostWin = cbs.getHostWin();
        String[] winArray = hostWin.split("\\|");
        //主负
        String hostFail = cbs.getHostFail();
        String[] failArray = hostFail.split("\\|");

        //所有比赛
        String matchIds = cbs.getMatchIds();
        String[] matchIdsArray = matchIds.split(",");
        String vs = "";
        String matchResult = cbs.getResult();//比赛结果
        int i = 0;
        for (String s : matchIdsArray) {

            List<String> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(matchResult)) {
                String[] mathchResultArray = matchResult.split(",");
                for (String rs : mathchResultArray) {
                    resultList.add(rs);
                }
            }

            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("matchId", s); //期次
            //比赛结果
            if (resultList.size() > 0) {
                orderMap.put("result", resultList.get(i));
                i++;
            } else {
                orderMap.put("result", "");
            }
            //主胜
            Map<String, String> winMap = getSingleMap(s, winArray);
            if (winMap.size() > 0) {
                vs = winMap.get("vs"); //队伍
            }

            orderMap.put("win", winMap.get("result")); //比分
            //主负
            Map<String, String> failMap = getSingleMap(s, failArray);
            if (failMap.size() > 0) {
                vs = failMap.get("vs");
            }
            orderMap.put("fail", failMap.get("result")); //进球
            orderMap.put("beat", "");
            orderMap.put("let", "");
            orderMap.put("size", "");
            orderMap.put("vs", vs);
            //map.put("result", cbs.getResult());//彩果
            detailList.add(orderMap);
        }
        return detailList;
    }

    public List getBbFollowList(CdBasketballFollowOrder cbf) {
        List detailList = new ArrayList();
        //主胜
        String hostWin = cbf.getHostWin();
        String[] winArray = hostWin.split("\\|");
        //主负
        String hostFail = cbf.getHostFail();
        String[] failArray = hostFail.split("\\|");
        //胜负
        String beat = cbf.getBeat();
        String[] beatArray = beat.split("\\|");
        //让球胜负
        String let = cbf.getBeat();
        String[] letArray = let.split("\\|");
        //大小分
        String size = cbf.getSize();
        String[] sizeArray = size.split("\\|");
        //所有比赛
        String matchIds = cbf.getDanMatchIds();
        String[] matchIdsArray = matchIds.split(",");
        String vs = "";
        String matchResult = cbf.getResult();//比赛结果
        int j = 0;
        for (String s : matchIdsArray) {
            List<String> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(matchResult)) {
                String[] mathchResultArray = matchResult.split(",");
                for (String rs : mathchResultArray) {
                    resultList.add(rs);
                }
            }

            String match = s.split("\\+")[1];
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("matchId", match);
            //比赛结果
            if (resultList.size() > 0) {
                orderMap.put("result", resultList.get(j));
                j++;
            } else {
                orderMap.put("result", "");
            }
            //主胜
            Map<String, String> winMap = getFollowMap(match, winArray);
            if (winMap.size() > 0) {
                vs = winMap.get("vs");
            }
            orderMap.put("win", winMap.get("result"));
            //主负
            Map<String, String> failMap = getFollowMap(match, failArray);
            if (failMap.size() > 0) {
                vs = failMap.get("vs");
            }
            orderMap.put("fail", failMap.get("result"));
            //胜负
            Map<String, String> beatMap = getFollowMap(match, beatArray);
            if (beatMap.size() > 0) {
                vs = beatMap.get("vs");
            }
            orderMap.put("beat", beatMap.get("result"));
            //让球胜负
            Map<String, String> letMap = getFollowMap(match, letArray);
            if (letMap.size() > 0) {
                vs = letMap.get("vs");
            }
            orderMap.put("let", letMap.get("result"));
            //大小分
            String finalSize = "";
            if (sizeArray.length > 0) {
                String sizeCount = cbf.getSizeCount();
                String[] sizeCountArray = sizeCount.split(",");
                for (int i = 0; i < sizeCountArray.length; i++) {
                    String[] aSizeArray = sizeArray[i].split("\\+");
                    if (s.split("\\+")[1].equals(aSizeArray[1])) {
                        orderMap.put("vs", aSizeArray[2]);
                        String sizeResult = aSizeArray[3];
                        String[] sizeResultArray = sizeResult.split(",");
                        for (String r : sizeResultArray) {
                            Map<String, String> sizeName = BallGameCals.getSizeNames();
                            String wholeResult = "";
                            String[] rArray = r.split("/");
                            wholeResult = sizeName.get(rArray[0]) + sizeCountArray[i] + "/" + rArray[1];
                            finalSize += wholeResult + ";";
                        }
                        orderMap.put("size", finalSize);
                    }
                }
            }

            detailList.add(orderMap);
        }
        return detailList;
    }

    private String changeType(String buyWays) {
        String type = "";
        switch (buyWays) {
            case "score":
                type = "_比分";
                break;
            case "half":
                type = "_半全场";
                break;
            case "goal":
                type = "_总进球";
                break;
            case "beat":
                type = "_胜平负";
                break;
            case "let":
                type = "_让分胜平负";
                break;
        }
        return type;
    }

}
