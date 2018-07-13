/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballorder.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.modules.cbasketballmixed.entity.CdBasketballMixed;
import com.youge.yogee.modules.cbasketballmixed.service.CdBasketballMixedService;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballBestFollowOrder;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballBestFollowOrderService;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.service.CdOrderService;
import com.youge.yogee.modules.sys.entity.User;
import com.youge.yogee.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 竞彩篮球订单Controller
 *
 * @author ZhaoYiFeng
 * @version 2018-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/cbasketballorder/cdBasketballFollowOrder")
public class CdBasketballFollowOrderController extends BaseController {

    @Autowired
    private CdBasketballFollowOrderService cdBasketballFollowOrderService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdBasketballMixedService cdBasketballMixedService;
    @Autowired
    private CdBasketballBestFollowOrderService cdBasketballBestFollowOrderService;
    @Autowired
    private CdOrderService cdOrderService;

    @ModelAttribute
    public CdBasketballFollowOrder get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return cdBasketballFollowOrderService.get(id);
        } else {
            return new CdBasketballFollowOrder();
        }
    }

    @RequiresPermissions("cbasketballorder:cdBasketballFollowOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(CdBasketballFollowOrder cdBasketballFollowOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();

        Page<CdBasketballFollowOrder> page = cdBasketballFollowOrderService.find(new Page<CdBasketballFollowOrder>(request, response), cdBasketballFollowOrder);
        model.addAttribute("page", page);
        return "modules/cbasketballorder/cdBasketballFollowOrderList";
    }

    @RequiresPermissions("cbasketballorder:cdBasketballFollowOrder:view")
    @RequestMapping(value = "form")
    public String form(CdBasketballFollowOrder cdBasketballFollowOrder, Model model) {

        String uid = cdBasketballFollowOrder.getUid();
        String uName = "";
        if (StringUtils.isNotEmpty(uid)) {
            CdLotteryUser clu = cdLotteryUserService.get(uid);
            uName = clu.getName();
        }
        String hostWin = cdBasketballFollowOrder.getHostWin();
        List<String> wList = new ArrayList<>();

        String hostFail = cdBasketballFollowOrder.getHostFail();
        List<String> fList = new ArrayList<>();

        String beat = cdBasketballFollowOrder.getBeat();
        List<String> bList = new ArrayList<>();

        String size = cdBasketballFollowOrder.getSize();
        List<String> sList = new ArrayList<>();

        String let = cdBasketballFollowOrder.getLet();
        List<String> tList = new ArrayList<>();

//        if ("1".equals(cdBasketballFollowOrder.getBestType())) {
        if (StringUtils.isNotEmpty(hostWin)) {
            String[] winStr = hostWin.split("\\|");
            for (String s : winStr) {
                wList.add(s);
            }
        }
        if (StringUtils.isNotEmpty(hostFail)) {
            String[] failStr = hostFail.split("\\|");
            for (String s : failStr) {
                fList.add(s);
            }
        }
        if (StringUtils.isNotEmpty(beat)) {
            String[] beatStr = beat.split("\\|");
            for (String s : beatStr) {
                String newBeat = s.replaceAll("1/", "主胜/");
                String finalBeat = newBeat.replaceAll("0/", "主负/");
                bList.add(finalBeat);
            }
        }
        if (StringUtils.isNotEmpty(size)) {
            String[] sizeStr = size.split("\\|");
            for (String s : sizeStr) {
                String newSize = s.replaceAll("1/", "大于/");
                String finalSize = newSize.replaceAll("0/", "小于/");
                //bList.add(finalSize);
                sList.add(finalSize);
            }
        }
        if (StringUtils.isNotEmpty(let)) {
            String[] letStr = let.split("\\|");
            for (String s : letStr) {
                String newLet = s.replaceAll("1/", "让主胜/");
                String finalLet = newLet.replaceAll("0/", "让主负/");
                //bList.add(finalBeat);
                tList.add(finalLet);
            }
        }
//        } else {
//            String orderNum = cdBasketballFollowOrder.getOrderNum();
//            List<CdBasketballBestFollowOrder> list = cdBasketballBestFollowOrderService.findByOrderNum(orderNum);
//            for (CdBasketballBestFollowOrder cbbfo : list) {
//                String detail = cbbfo.getOrderDetail();//详情
//                String[] detailArray = detail.split("\\|");//拆分详情
//                for (String str : detailArray) {
//                    String[] strArray = str.split("\\+");
//                    String newStr = "0+" + strArray[0] + "+" + strArray[3] + "+" + strArray[2] + ",";
//                    switch (strArray[1]) {
//                        case "hostWin":
//                            wList.add(newStr);
//                            break;
//                        case "hostFail":
//                            fList.add(newStr);
//                            break;
//                        case "beat":
//                            bList.add(newStr);
//                            break;
//                        case "let":
//                            tList.add(newStr);
//                            break;
//                        case "size":
//                            sList.add(newStr);
//                            break;
//                    }
//                }
//            }
//        }


        //获取当前时间
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = df.format(day);

        model.addAttribute("today", today);
        model.addAttribute("uName", uName);
        model.addAttribute("wList", wList);
        model.addAttribute("fList", fList);
        model.addAttribute("bList", bList);
        model.addAttribute("sList", sList);
        model.addAttribute("tList", tList);

        model.addAttribute("cdBasketballFollowOrder", cdBasketballFollowOrder);
        return "modules/cbasketballorder/cdBasketballFollowOrderForm";
    }

    @RequiresPermissions("cbasketballorder:cdBasketballFollowOrder:edit")
    @RequestMapping(value = "save")
    public String save(CdBasketballFollowOrder cdBasketballFollowOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, cdBasketballFollowOrder)) {
            return form(cdBasketballFollowOrder, model);
        }

        //更新到出票赔率
        String newWinDetail = getNewWin(cdBasketballFollowOrder);
        String newFailDetail = getNewFail(cdBasketballFollowOrder);
        String newSizeDetail = getNewSize(cdBasketballFollowOrder);
        String newBeatDetail = getNewBeat(cdBasketballFollowOrder);
        String newLetDetail = getNewLet(cdBasketballFollowOrder);

//        if ("0".equals(newWinDetail) || "0".equals(newFailDetail) ) {
//            cdBasketballFollowOrder.setStatus("2");
//            cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
//            addMessage(redirectAttributes, "出票失败,比赛可能不存在");
//            return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
//        }

        //更新赔率  6.28 dh 赔率更新问题 同足球
        if(!"0".equals(newWinDetail)){ cdBasketballFollowOrder.setHostWin(newWinDetail); }
        if(!"0".equals(newFailDetail)){cdBasketballFollowOrder.setHostFail(newFailDetail);}
        if(!"0".equals(newSizeDetail)){cdBasketballFollowOrder.setSize(newSizeDetail);}
        if(!"0".equals(newBeatDetail)){cdBasketballFollowOrder.setBeat(newBeatDetail);}
        if(!"0".equals(newLetDetail)) {cdBasketballFollowOrder.setLet(newLetDetail);}

        /*
        String size = cdBasketballFollowOrder.getSize();
        if (StringUtils.isNotEmpty(size)) {
            String sizeCount = "";
            String[] sizeArray = size.split("\\|");
            for (String aSize : sizeArray) {
                String[] aSizeArray = aSize.split("\\+");
                String matchId = aSizeArray[1];
                //查询比赛
                CdBasketballMixed cfm = cdBasketballMixedService.findByMatchId(matchId);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    addMessage(redirectAttributes, "出票失败,比赛可能不存在");
                    return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
                } else {
                    sizeCount += cfm.getZclose() + ",";
                }
            }
            cdBasketballFollowOrder.setSizeCount(sizeCount);
        }
        String let = cdBasketballFollowOrder.getLet();
        if (StringUtils.isNotEmpty(let)) {
            String letScore = "";
            String[] letArray = let.split("\\|");
            for (String aLet : letArray) {
                String[] aLetArray = aLet.split("\\+");
                String matchId = aLetArray[1];
                //查询比赛
                CdBasketballMixed cfm = cdBasketballMixedService.findByMatchId(matchId);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    addMessage(redirectAttributes, "出票失败,比赛可能不存在");
                    return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
                } else {
                    letScore += cfm.getClose() + ",";
                }
            }
            cdBasketballFollowOrder.setLetScore(letScore);
        }
        */

        //保存出票时间
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String outTime = df.format(day);
        CdOrder co = cdOrderService.getOrderByOrderNum(cdBasketballFollowOrder.getOrderNum());
        if (co != null) {
            co.setOutTime(outTime);
            cdOrderService.save(co);
        }
        cdBasketballFollowOrderService.save(cdBasketballFollowOrder);

        if ("2".equals(cdBasketballFollowOrder.getBestType())) {
//            cdBasketballFollowOrder.getDanMatchIds();
            String orderNum = cdBasketballFollowOrder.getOrderNum();
            List<CdBasketballBestFollowOrder> bList = cdBasketballBestFollowOrderService.findByOrderNum(orderNum);
            for (CdBasketballBestFollowOrder cbb : bList) {
                String detail = cbb.getOrderDetail();
                String[] detailArray = detail.split("\\|");
                //用户拼回新的|；最后set进去的
                String newDeatail = "";
                for (String d : detailArray) {
                    String[] aDetail = d.split("\\+");
                    //查询比赛
                    CdBasketballMixed cfm = cdBasketballMixedService.findByMatchId(aDetail[0]);
                    if (cfm == null) {
                        //比赛不存在 无法出票
                        addMessage(redirectAttributes, "出票失败,比赛可能不存在");
                        return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
                    }
                    //
                    String newAdetail = "";
                    switch (aDetail[1]) {
                        case "hostWin": {
                            newAdetail = getNewBestHost(cfm, aDetail);
                            break;
                        }
                        case "hostFail": {
                            newAdetail = getNewBestHost(cfm, aDetail);
                            break;
                        }
                        case "beat": {
                            //获取胜负赔率 主负主胜
                            String beatOdds = cfm.getVictoryordefeatOdds();
                            //根据,拆分成数组 取最新赔率
                            String[] beatOddsArray = beatOdds.split(",");
                            //拿到比分字段 形式如 5:1/1.95,1:2/2.87
                            String beatAndOdd = aDetail[2];
                            //根据/拆分
                            String[] beatAndOddArray = beatAndOdd.split("/");
                            String sm = BallGameCals.changeBasketballSf(beatAndOddArray[0]);
                            String newBeatOdd = "";
                            if ("1".equals(sm)) {
                                newBeatOdd = beatOddsArray[1];
                            } else {
                                newBeatOdd = beatOddsArray[0];
                            }
                            String newPlay = beatAndOddArray[0] + "/" + newBeatOdd;
                            String close = cfm.getClose().replaceAll("\\+", "");
                            String letSorce = aDetail[3].split("/")[0] + "/" + close + "/" + cfm.getZclose();
                            newAdetail = aDetail[0] + "+" + aDetail[1] + "+" + newPlay + "+" + letSorce;
                            break;
                        }
                        case "let": {
                            //获取胜负赔率 主负主胜
                            String letOdds = cfm.getSpreadOdds();
                            //根据,拆分成数组 取最新赔率
                            String[] letOddsArray = letOdds.split(",");

                            //拿到比分字段 形式如 5:1/1.95,1:2/2.87
                            String letAndOdd = aDetail[2];
                            //根据/拆分
                            String[] letAndOddArray = letAndOdd.split("/");
                            String sm = BallGameCals.changeBasketballSf(letAndOddArray[0]);
                            String newLetOdd = "";
                            if ("1".equals(sm)) {
                                newLetOdd = letOddsArray[1];
                            } else {
                                newLetOdd = letOddsArray[0];
                            }
                            String newPlay = letAndOddArray[0] + "/" + newLetOdd;
                            String close = cfm.getClose().replaceAll("\\+", "");
                            String letSorce = aDetail[3].split("/")[0] + "/" + close + "/" + cfm.getZclose();
                            newAdetail = aDetail[0] + "+" + aDetail[1] + "+" + newPlay + "+" + letSorce+"+"+aDetail[4];

                            break;
                        }
                        case "size": {
                            //获取大小分赔率 大于,小于
                            String sizeOdds = cfm.getSizeOdds();
                            //根据,拆分成数组 取最新赔率
                            String[] sizeOddsArray = sizeOdds.split(",");
                            //拿到比分字段 形式如 5:1/1.95,1:2/2.87
                            String sizeAndOdd = aDetail[2];
                            //根据/拆分
                            String[] sizeAndOddArray = sizeAndOdd.split("/");
                            String sm = BallGameCals.changeBasketballSf(sizeAndOddArray[0]);
                            String newOdd = "";
                            if ("1".equals(sm)) {
                                newOdd = sizeOddsArray[0];
                            } else {
                                newOdd = sizeOddsArray[1];
                            }
                            String newPlay = sizeAndOddArray[0] + "/" + newOdd;
                            String close = cfm.getClose().replaceAll("\\+", "");
                            String letSorce = aDetail[3].split("/")[0] + "/" + close + "/" + cfm.getZclose();
                            newAdetail = aDetail[0] + "+" + aDetail[1] + "+" + newPlay + "+" + letSorce;
                            break;
                        }
                    }
                    d = newAdetail + "|";
                    newDeatail += d;
                }
                cbb.setOrderDetail(newDeatail);
                String[] newDetailArray = newDeatail.split("\\|");
                String perAwards = "1";
                for (String s : newDetailArray) {
                    String[] aArray = s.split("\\+");
                    String odds = aArray[2].split("/")[1];
                    BigDecimal oddBig = new BigDecimal(odds);
                    perAwards = oddBig.multiply(new BigDecimal(perAwards)).setScale(2, 2).toString();
                }
                String finalAward = new BigDecimal(perAwards).multiply(new BigDecimal(2)).toString();
                cbb.setPerAward(finalAward);
                cdBasketballBestFollowOrderService.save(cbb);
            }
        }


         addMessage(redirectAttributes, "保存成功");
        return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
        /**
         * 2018-06-06 yhw 去掉之前的页面打印功能
         */
        //==================start   2018-04-11   yuhongwei 跳转打印页
       /* String buy_ways = cdBasketballFollowOrder.getBuyWays();
        String match_ids = cdBasketballFollowOrder.getDanMatchIds().substring(0, cdBasketballFollowOrder.getDanMatchIds().length() - 1);
        String baseUrl = "modules/print/";
        model.addAttribute("orderNumber", cdBasketballFollowOrder.getOrderNum());
        String returnStr = cdBasketballFollowOrder.getPrice() + "元," + cdBasketballFollowOrder.getFollowNums() + "串1,";//打印在头部显示
        if ("1".equals(buy_ways)) {//混投
            returnStr = "竟篮混合," + returnStr;
            model.addAttribute("returnStr", returnStr);
            if (match_ids.split(",").length <= 3) {
                return baseUrl + "basketball3";
            } else if (match_ids.split(",").length <= 6) {
                return baseUrl + "basketball6";
            } else if (match_ids.split(",").length <= 8) {
                return baseUrl + "basketball8";
            }
        } else if ("2".equals(buy_ways)) {
            returnStr = "竟篮胜负," + returnStr;
            model.addAttribute("returnStr", returnStr);
            if (match_ids.split(",").length <= 3) {
                return baseUrl + "basketballSF3";
            } else if (match_ids.split(",").length <= 6) {
                //return baseUrl+  "篮球胜负6关";
                addMessage(redirectAttributes, "保存成功,没有模板不能打印");
                return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
            } else if (match_ids.split(",").length <= 8) {
                //return baseUrl+"篮球胜负8关";
                addMessage(redirectAttributes, "保存成功,没有模板不能打印");
                return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
            }
        } else if ("3".equals(buy_ways)) {
            returnStr = "竟篮让分胜负," + returnStr;
            model.addAttribute("returnStr", returnStr);
            if (match_ids.split(",").length <= 3) {
                //return baseUrl+ "篮球让分胜负3关";
                addMessage(redirectAttributes, "保存成功,没有模板不能打印");
                return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
            } else if (match_ids.split(",").length <= 6) {
                // return baseUrl+  "篮球让分胜负6关";
                addMessage(redirectAttributes, "保存成功,没有模板不能打印");
                return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
            } else if (match_ids.split(",").length <= 8) {
                return baseUrl + "basketballRFSF8";
            }
        } else if ("4".equals(buy_ways)) {
            returnStr = "竟篮大小分," + returnStr;
            model.addAttribute("returnStr", returnStr);
            if (match_ids.split(",").length <= 3) {
                //return baseUrl+ "篮球大小分3关";
                addMessage(redirectAttributes, "保存成功,没有模板不能打印");
                return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
            } else if (match_ids.split(",").length <= 6) {
                //return baseUrl+  "篮球大小分6关";
                addMessage(redirectAttributes, "保存成功,没有模板不能打印");
                return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
            } else if (match_ids.split(",").length <= 8) {
                return baseUrl + "basketballDXF8";
            }
        } else if ("5".equals(buy_ways)) {
            returnStr = "竟篮胜负差," + returnStr;
            model.addAttribute("returnStr", returnStr);
            if (match_ids.split(",").length <= 3) {
                //return baseUrl+ "篮球胜负差3关";
                addMessage(redirectAttributes, "保存成功,没有模板不能打印");
                return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
            } else if (match_ids.split(",").length <= 6) {
                //return baseUrl+  "篮球胜负差6关";
                addMessage(redirectAttributes, "保存成功,没有模板不能打印");
                return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
            } else if (match_ids.split(",").length <= 8) {
                //return baseUrl+"篮球胜负差8关";
                addMessage(redirectAttributes, "保存成功,没有模板不能打印");
                return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
            }
        }
        addMessage(redirectAttributes, "保存成功,没有模板不能打印");
        return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
*/
        //==================end   2018-04-11   yuhongwei 跳转打印页


    }

    @RequiresPermissions("cbasketballorder:cdBasketballFollowOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        cdBasketballFollowOrderService.delete(id);
        addMessage(redirectAttributes, "删除竞彩篮球订单成功");
        return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
    }

    /**
     * 更新主胜赔率
     *
     * @param cdBasketballFollowOrder
     * @return
     */
    public String getNewWin(CdBasketballFollowOrder cdBasketballFollowOrder) {
        String newWinDetail = "";
        //获取主胜结果
        String winResult = cdBasketballFollowOrder.getHostWin();
        if (StringUtils.isNotEmpty(winResult)) {
            String[] winArray = winResult.split("\\|");
            for (String aScore : winArray) {
                //一条比分的详情
                String[] detail = aScore.split("\\+");
                //查询比赛
                CdBasketballMixed cfm = cdBasketballMixedService.findByMatchId(detail[1]);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    return "0";
                }
                //获取比分赔率
                String winOdds = cfm.getSurpassScoreGap();
                //根据,拆分成数组 取最新赔率
                String[] winOddsArray = winOdds.split(",");
                //获取比分map
                Map<String, Integer> winMap = BallGameCals.getWinScoreResults();
                //拿到比分字段 形式如 5:1/1.95,1:2/2.87
                String scoreAndOdd = detail[3];
                //根据，拆分
                String[] scoreAndOddArray = scoreAndOdd.split(",");
                String newScoreOdd = "";
                for (String s : scoreAndOddArray) {
                    String[] sArray = s.split("/");
                    int no = winMap.get(sArray[0]);
                    String newOdd = winOddsArray[no];
                    s = sArray[0] + "/" + newOdd;
                    newScoreOdd += s + ",";
                }
                aScore = detail[0] + "+" + detail[1] + "+" + detail[2] + "+" + newScoreOdd;
                newWinDetail += aScore + "|";
            }
        }
        return newWinDetail;
    }

    /**
     * 更新主负赔率
     *
     * @param cdBasketballFollowOrder
     * @return
     */
    public String getNewFail(CdBasketballFollowOrder cdBasketballFollowOrder) {
        String newFailDetail = "";
        //获取主胜结果
        String failResult = cdBasketballFollowOrder.getHostFail();
        if (StringUtils.isNotEmpty(failResult)) {
            String[] failArray = failResult.split("\\|");
            for (String aFail : failArray) {
                //一条比分的详情
                String[] detail = aFail.split("\\+");
                //查询比赛
                CdBasketballMixed cfm = cdBasketballMixedService.findByMatchId(detail[1]);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    return "0";
                }
                //获取比分赔率
                String failOdds = cfm.getSurpassScoreGap();
                //根据,拆分成数组 取最新赔率
                String[] failOddsArray = failOdds.split(",");
                //获取比分map
                Map<String, Integer> winMap = BallGameCals.getFailScoreResults();
                //拿到比分字段 形式如 5:1/1.95,1:2/2.87
                String winAndOdd = detail[3];
                //根据，拆分
                String[] winAndOddArray = winAndOdd.split(",");
                String newScoreOdd = "";
                for (String s : winAndOddArray) {
                    String[] sArray = s.split("/");
                    int no = winMap.get(sArray[0]);
                    String newOdd = failOddsArray[no];
                    s = sArray[0] + "/" + newOdd;
                    newScoreOdd += s + ",";
                }
                aFail = detail[0] + "+" + detail[1] + "+" + detail[2] + "+" + newScoreOdd;
                newFailDetail += aFail + "|";
            }
        }
        return newFailDetail;
    }


    /**
     * 更新大小分赔率
     *
     * @param cdBasketballFollowOrder
     * @return
     */
    public String getNewSize(CdBasketballFollowOrder cdBasketballFollowOrder) {
        String newSizeDetail = "";
        //获取主胜结果
        String sizeResult = cdBasketballFollowOrder.getSize();
        if (StringUtils.isNotEmpty(sizeResult)) {
            String[] sizeArray = sizeResult.split("\\|");
            for (String aSize : sizeArray) {
                //一条比分的详情
                String[] detail = aSize.split("\\+");
                //查询比赛
                CdBasketballMixed cfm = cdBasketballMixedService.findByMatchId(detail[1]);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    return "0";
                }
                //获取大小分赔率 大于,小于
                String sizeOdds = cfm.getSizeOdds();
                //根据,拆分成数组 取最新赔率
                String[] sizeOddsArray = sizeOdds.split(",");

                //拿到比分字段 形式如 5:1/1.95,1:2/2.87
                String sizeAndOdd = detail[3];
                //根据，拆分
                String[] sizeAndOddArray = sizeAndOdd.split(",");
                String newSizeOdd = "";
                for (String s : sizeAndOddArray) {
                    String[] sArray = s.split("/");
                    //新赔率
                    String newOdd = "";
                    if ("1".equals(sArray[0])) {
                        newOdd = sizeOddsArray[0];
                    } else {
                        newOdd = sizeOddsArray[1];
                    }
                    s = sArray[0] + "/" + newOdd;
                    newSizeOdd += s + ",";
                }
                aSize = detail[0] + "+" + detail[1] + "+" + detail[2] + "+" + newSizeOdd;
                newSizeDetail += aSize + "|";
            }
        }
        return newSizeDetail;
    }


    /**
     * 更新胜负赔率
     *
     * @param cdBasketballFollowOrder
     * @return
     */
    public String getNewBeat(CdBasketballFollowOrder cdBasketballFollowOrder) {
        String newBeatDetail = "";
        //获取胜负结果
        String beatResult = cdBasketballFollowOrder.getBeat();
        if (StringUtils.isNotEmpty(beatResult)) {
            String[] beatArray = beatResult.split("\\|");
            for (String aBeat : beatArray) {
                //一条比分的详情
                String[] detail = aBeat.split("\\+");
                //查询比赛
                CdBasketballMixed cfm = cdBasketballMixedService.findByMatchId(detail[1]);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    return "0";
                }
                //获取胜负赔率 主负主胜
                String beatOdds = cfm.getVictoryordefeatOdds();
                //根据,拆分成数组 取最新赔率
                String[] beatOddsArray = beatOdds.split(",");

                //拿到比分字段 形式如 5:1/1.95,1:2/2.87
                String beatAndOdd = detail[3];
                //根据，拆分
                String[] beatAndOddArray = beatAndOdd.split(",");
                String newBeatOdd = "";
                for (String s : beatAndOddArray) {
                    String[] sArray = s.split("/");
                    //新赔率
                    String newOdd = "";
                    if ("1".equals(sArray[0])) {
                        newOdd = beatOddsArray[1];
                    } else {
                        newOdd = beatOddsArray[0];
                    }
                    s = sArray[0] + "/" + newOdd;
                    newBeatOdd += s + ",";
                }
                aBeat = detail[0] + "+" + detail[1] + "+" + detail[2] + "+" + newBeatOdd;
                newBeatDetail += aBeat + "|";
            }
        }
        return newBeatDetail;
    }


    /**
     * 更新让球胜负赔率
     *
     * @param cdBasketballFollowOrder
     * @return
     */
    public String getNewLet(CdBasketballFollowOrder cdBasketballFollowOrder) {
        String newLetDetail = "";
        //获取胜负结果
        String letResult = cdBasketballFollowOrder.getLet();
        if (StringUtils.isNotEmpty(letResult)) {
            String[] letArray = letResult.split("\\|");
            for (String aLet : letArray) {
                //一条比分的详情
                String[] detail = aLet.split("\\+");
                //查询比赛
                CdBasketballMixed cfm = cdBasketballMixedService.findByMatchId(detail[1]);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    return "0";
                }
                //获取胜负赔率 主负主胜
                String letOdds = cfm.getSpreadOdds();
                //根据,拆分成数组 取最新赔率
                String[] letOddsArray = letOdds.split(",");

                //拿到比分字段 形式如 5:1/1.95,1:2/2.87
                String letAndOdd = detail[3];
                //根据，拆分
                String[] letAndOddArray = letAndOdd.split(",");
                String newBeatOdd = "";
                for (String s : letAndOddArray) {
                    String[] sArray = s.split("/");
                    //新赔率
                    String newOdd = "";
                    if ("1".equals(sArray[0])) {
                        newOdd = letOddsArray[1];
                    } else {
                        newOdd = letOddsArray[0];
                    }
                    s = sArray[0] + "/" + newOdd;
                    newBeatOdd += s + ",";
                }
                aLet = detail[0] + "+" + detail[1] + "+" + detail[2] + "+" + newBeatOdd;
                newLetDetail += aLet + "|";
            }
        }
        return newLetDetail;
    }

    public String getNewBestHost(CdBasketballMixed cfm, String[] aDetail) {
        String newAdetail = "";
        String failOdds = cfm.getSurpassScoreGap();//获取比分赔率
        String[] failOddsArray = failOdds.split(","); //根据,拆分成数组 取最新赔率
        Map<String, Integer> winMap = BallGameCals.getFailWinScoreResults();//获取比分map
        String failAndOdd = aDetail[2];//拿到比分字段 形式如 5:1/1.95,1:2/2.87
        String[] fArray = failAndOdd.split("/");
        int no = winMap.get(fArray[0]);
        String newOdd = failOddsArray[no];
        String newPlay = fArray[0] + "/" + newOdd;
        String close = cfm.getClose().replaceAll("\\+", "");
        String letSorce = aDetail[3].split("/")[0] + "/" + close + "/" + cfm.getZclose();
        newAdetail = aDetail[0] + "+" + aDetail[1] + "+" + newPlay + "+" + letSorce;
        return newAdetail;
    }

}
