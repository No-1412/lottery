/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballorder.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.modules.cbasketballmixed.entity.CdBasketballMixed;
import com.youge.yogee.modules.cbasketballmixed.service.CdBasketballMixedService;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.sys.entity.User;
import com.youge.yogee.modules.sys.utils.UserUtils;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballFollowOrderService;

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
            uName = clu.getReality();
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
                bList.add(s);
            }
        }
        if (StringUtils.isNotEmpty(size)) {
            String[] sizeStr = size.split("\\|");
            for (String s : sizeStr) {
                sList.add(s);
            }
        }
        if (StringUtils.isNotEmpty(let)) {
            String[] letStr = let.split("\\|");
            for (String s : letStr) {
                tList.add(s);
            }
        }


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

        if ("0".equals(newWinDetail) || "0".equals(newFailDetail)) {
            cdBasketballFollowOrder.setStatus("2");
            cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
            addMessage(redirectAttributes, "出票失败,比赛可能不存在");
            return "redirect:" + Global.getAdminPath() + "/cfootballorder/cdFootballFollowOrder/?repage";
        }
        //更新赔率
        cdBasketballFollowOrder.setHostWin(newWinDetail);
        cdBasketballFollowOrder.setHostFail(newFailDetail);
        cdBasketballFollowOrder.setSize(newSizeDetail);
        cdBasketballFollowOrder.setBeat(newBeatDetail);
        cdBasketballFollowOrder.setLet(newLetDetail);

        String size = cdBasketballFollowOrder.getSize();
        if (StringUtils.isNotEmpty(size)) {
            String sizeCount="";
            String[] sizeArray = size.split("\\|");
            for (String aSize : sizeArray) {
                String[] aSizeArray=aSize.split("\\+");
                String matchId=aSizeArray[1];
                //查询比赛
                CdBasketballMixed cfm = cdBasketballMixedService.findByMatchId(matchId);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    addMessage(redirectAttributes, "出票失败,比赛可能不存在");
                    return "redirect:" + Global.getAdminPath() + "/cfootballorder/cdFootballFollowOrder/?repage";
                }else {
                    sizeCount+=cfm.getZclose()+",";
                }
            }
            cdBasketballFollowOrder.setSizeCount(sizeCount);
        }
        String let = cdBasketballFollowOrder.getLet();
        if (StringUtils.isNotEmpty(let)) {
            String letScore="";
            String[] letArray = let.split("\\|");
            for (String aLet : letArray) {
                String[] aLetArray=aLet.split("\\+");
                String matchId=aLetArray[1];
                //查询比赛
                CdBasketballMixed cfm = cdBasketballMixedService.findByMatchId(matchId);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    addMessage(redirectAttributes, "出票失败,比赛可能不存在");
                    return "redirect:" + Global.getAdminPath() + "/cfootballorder/cdFootballFollowOrder/?repage";
                }else {
                    letScore+=cfm.getClose()+",";
                }
            }
            cdBasketballFollowOrder.setLetScore(letScore);
        }

        cdBasketballFollowOrderService.save(cdBasketballFollowOrder);
        addMessage(redirectAttributes, "保存成功");
        return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballFollowOrder/?repage";
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


}
