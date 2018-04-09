/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballorder.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.interfaces.util.BallGameCals;
import com.youge.yogee.modules.cfootballmixed.entity.CdFootballMixed;
import com.youge.yogee.modules.cfootballmixed.service.CdFootballMixedService;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 竞彩足球订单Controller
 *
 * @author ZhaoYiFeng
 * @version 2018-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/cfootballorder/cdFootballSingleOrder")
public class CdFootballSingleOrderController extends BaseController {

    @Autowired
    private CdFootballSingleOrderService cdFootballSingleOrderService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdFootballMixedService cdFootballMixedService;

    @ModelAttribute
    public CdFootballSingleOrder get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return cdFootballSingleOrderService.get(id);
        } else {
            return new CdFootballSingleOrder();
        }
    }

    @RequiresPermissions("cfootballorder:cdFootballSingleOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(CdFootballSingleOrder cdFootballSingleOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();

        Page<CdFootballSingleOrder> page = cdFootballSingleOrderService.find(new Page<CdFootballSingleOrder>(request, response), cdFootballSingleOrder);
        model.addAttribute("page", page);
        return "modules/cfootballorder/cdFootballSingleOrderList";
    }

    @RequiresPermissions("cfootballorder:cdFootballSingleOrder:view")
    @RequestMapping(value = "form")
    public String form(CdFootballSingleOrder cdFootballSingleOrder, Model model) {
        String uid = cdFootballSingleOrder.getUid();
        String uName = "";
        if (StringUtils.isNotEmpty(uid)) {
            CdLotteryUser clu = cdLotteryUserService.get(uid);
            uName = clu.getName();
        }
        String score = cdFootballSingleOrder.getScore();
        List<String> sList = new ArrayList<>();
        String goal = cdFootballSingleOrder.getGoal();
        List<String> gList = new ArrayList<>();
        String half = cdFootballSingleOrder.getHalf();
        List<String> hList = new ArrayList<>();
        String beat = cdFootballSingleOrder.getBeat();
        List<String> bList = new ArrayList<>();
        String let = cdFootballSingleOrder.getLet();
        List<String> tList = new ArrayList<>();
        if (StringUtils.isNotEmpty(score)) {
            String[] scoreStr = score.split("\\|");
            for (String s : scoreStr) {
                sList.add(s);
            }
        }

        if (StringUtils.isNotEmpty(goal)) {
            String[] goalStr = goal.split("\\|");
            for (String s : goalStr) {
                gList.add(s);
            }
        }

        if (StringUtils.isNotEmpty(half)) {
            String[] halfStr = half.split("\\|");
            for (String s : halfStr) {
                hList.add(s);
            }
        }

        if (StringUtils.isNotEmpty(beat)) {
            String[] beatStr = beat.split("\\|");
            for (String s : beatStr) {
                bList.add(s);
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
        model.addAttribute("sList", sList);
        model.addAttribute("gList", gList);
        model.addAttribute("hList", hList);
        model.addAttribute("bList", bList);
        model.addAttribute("tList", tList);
        model.addAttribute("uName", uName);
        model.addAttribute("cdFootballSingleOrder", cdFootballSingleOrder);
        return "modules/cfootballorder/cdFootballSingleOrderForm";
    }

    @RequiresPermissions("cfootballorder:cdFootballSingleOrder:edit")
    @RequestMapping(value = "save")
    public String save(CdFootballSingleOrder cdFootballSingleOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, cdFootballSingleOrder)) {
            return form(cdFootballSingleOrder, model);
        }

        //更新到出票赔率
        String newScoreDetail = getNewScore(cdFootballSingleOrder);
        String newGoalDetail = getNewGoal(cdFootballSingleOrder);
        String newHalfDetail = getNewHalf(cdFootballSingleOrder);
        String newBeatDetail = getNewBeat(cdFootballSingleOrder);
        String newLetDetail = getNewLet(cdFootballSingleOrder);

        if ("0".equals(newScoreDetail) || "0".equals(newGoalDetail) || "0".equals(newHalfDetail) || "0".equals(newBeatDetail) || "0".equals(newLetDetail)) {
            cdFootballSingleOrder.setStatus("2");
            cdFootballSingleOrderService.save(cdFootballSingleOrder);
            addMessage(redirectAttributes, "出票失败,比赛可能不存在");
            return "redirect:" + Global.getAdminPath() + "/cfootballorder/cdFootballFollowOrder/?repage";
        }
        //更新赔率
        cdFootballSingleOrder.setScore(newScoreDetail);
        cdFootballSingleOrder.setGoal(newGoalDetail);
        cdFootballSingleOrder.setHalf(newHalfDetail);
        cdFootballSingleOrder.setBeat(newBeatDetail);
        cdFootballSingleOrder.setLet(newLetDetail);

        //更新让球
        String let = cdFootballSingleOrder.getLet();
        if (StringUtils.isNotEmpty(let)) {
            String letScore="";
            String[] letArray = let.split("\\|");
            for (String aLet : letArray) {
                String[] aLetArray=aLet.split("\\+");
                String matchId=aLetArray[0];
                //查询比赛
                CdFootballMixed cfm = cdFootballMixedService.findByMatchId(matchId);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    addMessage(redirectAttributes, "出票失败,比赛可能不存在");
                    return "redirect:" + Global.getAdminPath() + "/cfootballorder/cdFootballSingleOrder/?repage";
                }else {
                    letScore+=cfm.getClose()+",";
                }
            }
            cdFootballSingleOrder.setLetBalls(letScore);
        }

        cdFootballSingleOrderService.save(cdFootballSingleOrder);
        addMessage(redirectAttributes, "保存成功");
        return "redirect:" + Global.getAdminPath() + "/cfootballorder/cdFootballSingleOrder/?repage";
    }

    @RequiresPermissions("cfootballorder:cdFootballSingleOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        cdFootballSingleOrderService.delete(id);
        addMessage(redirectAttributes, "删除竞彩足球订单成功");
        return "redirect:" + Global.getAdminPath() + "/cfootballorder/cdFootballSingleOrder/?repage";
    }


    /**
     * 更新比分赔率
     *
     * @param cdFootballSingleOrder
     * @return
     */
    public String getNewScore(CdFootballSingleOrder cdFootballSingleOrder) {
        String newScoreDetail = "";
        //获取比分结果
        String scoreResult = cdFootballSingleOrder.getScore();
        if (StringUtils.isNotEmpty(scoreResult)) {
            String[] scoreArray = scoreResult.split("\\|");
            for (String aScore : scoreArray) {
                //一条比分的详情
                String[] detail = aScore.split("\\+");
                //查询比赛
                CdFootballMixed cfm = cdFootballMixedService.findByMatchId(detail[0]);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    return "0";
                }
                //获取比分赔率
                String scoreOdds = cfm.getScoreOdds();
                //根据,拆分成数组 取最新赔率
                String[] scoreOddsArray = scoreOdds.split(",");
                //获取比分map
                Map<String, String> scoreMap = BallGameCals.getScoreResults();
                //拿到比分字段 形式如 5:1/1.95,1:2/2.87
                String scoreAndOdd = detail[2];
                //根据，拆分
                String[] scoreAndOddArray = scoreAndOdd.split(",");
                String newScoreOdd = "";
                for (String s : scoreAndOddArray) {
                    String[] sArray = s.split("/");
                    String no = scoreMap.get(sArray[0]);
                    String newOdd = scoreOddsArray[Integer.parseInt(no)];
                    s = sArray[0] + "/" + newOdd + "/" + sArray[2];
                    newScoreOdd += s + ",";
                }
                aScore = detail[0] + "+" + detail[1] + "+" + newScoreOdd;
                newScoreDetail += aScore + "|";
            }
        }
        return newScoreDetail;
    }

    /**
     * 更新总进球赔率
     *
     * @param cdFootballSingleOrder
     * @return
     */
    public String getNewGoal(CdFootballSingleOrder cdFootballSingleOrder) {
        String newGoalDetail = "";
        //获取总进球结果
        String goalResult = cdFootballSingleOrder.getGoal();
        if (StringUtils.isNotEmpty(goalResult)) {
            String[] goalArray = goalResult.split("\\|");
            for (String aGoal : goalArray) {
                //一条进球的详情
                String[] detail = aGoal.split("\\+");
                //查询比赛
                CdFootballMixed cfm = cdFootballMixedService.findByMatchId(detail[0]);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    return "0";
                }
                //获取总进球赔率
                String goalOdds = cfm.getAllOdds();
                //根据,拆分成数组 取最新赔率
                String[] goalOddsArray = goalOdds.split(",");

                //拿到比分字段 形式如 5:1/1.95,1:2/2.87
                String goalAndOdd = detail[2];
                //根据，拆分
                String[] scoreAndOddArray = goalAndOdd.split(",");
                String newScoreOdd = "";
                for (String s : scoreAndOddArray) {
                    String[] sArray = s.split("/");
                    int no = Integer.parseInt(sArray[0]);
                    String newOdd = goalOddsArray[no];
//                    s = sArray[0] + "/" + newOdd;
                    s = sArray[0] + "/" + newOdd + "/" + sArray[2];
                    newScoreOdd += s + ",";
                }
//                aGoal = detail[0] + "+" + detail[1] + "+" + detail[2] + "+" + newScoreOdd;
                aGoal = detail[0] + "+" + detail[1] + "+" + newScoreOdd;
                newGoalDetail += aGoal + "|";
            }
        }
        return newGoalDetail;
    }


    /**
     * 更新半全场赔率
     *
     * @param cdFootballSingleOrder
     * @return
     */
    public String getNewHalf(CdFootballSingleOrder cdFootballSingleOrder) {
        String newHalfDetail = "";
        //获取总进球结果
        String halfResult = cdFootballSingleOrder.getHalf();
        if (StringUtils.isNotEmpty(halfResult)) {
            String[] halfArray = halfResult.split("\\|");
            for (String aHalf : halfArray) {
                //一条进球的详情
                String[] detail = aHalf.split("\\+");
                //查询比赛
                CdFootballMixed cfm = cdFootballMixedService.findByMatchId(detail[0]);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    return "0";
                }
                //获取总进球赔率
                String halfOdds = cfm.getHalfOdds();
                //根据,拆分成数组 取最新赔率
                String[] halfOddsArray = halfOdds.split(",");
                //获取半全场map
                Map<String, String> halfMap = BallGameCals.getHalfWholeResults();
                //拿到比分字段 形式如 33/1.95,31/2.87
                String halfAndOdd = detail[2];
                //根据，拆分
                String[] halfAndOddArray = halfAndOdd.split(",");
                String newGoalOdd = "";
                for (String s : halfAndOddArray) {
                    String[] sArray = s.split("/");
                    String no = halfMap.get(sArray[0]);
                    String newOdd = halfOddsArray[Integer.parseInt(no)];
//                    s = sArray[0] + "/" + newOdd;
                    s = sArray[0] + "/" + newOdd + "/" + sArray[2];
                    newGoalOdd += s + ",";
                }
//              aHalf = detail[0] + "+" + detail[1] + "+" + detail[2] + "+" + newGoalOdd;
                aHalf = detail[0] + "+" + detail[1] + "+" + newGoalOdd;
                newHalfDetail += aHalf + "|";
            }
        }
        return newHalfDetail;
    }


    /**
     * 更新胜负平赔率
     *
     * @param cdFootballSingleOrder
     * @return
     */
    public String getNewBeat(CdFootballSingleOrder cdFootballSingleOrder) {
        String newBeatDetail = "";
        //获取总进球结果
        String beatResult = cdFootballSingleOrder.getBeat();
        if (StringUtils.isNotEmpty(beatResult)) {
            String[] beatArray = beatResult.split("\\|");
            for (String aBeat : beatArray) {
                //一条进球的详情
                String[] detail = aBeat.split("\\+");
                //查询比赛
                CdFootballMixed cfm = cdFootballMixedService.findByMatchId(detail[0]);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    return "0";
                }
                //获取总进球赔率
                String beatOdds = cfm.getNotConcedepointsOdds();
                //根据,拆分成数组 取最新赔率
                String[] beatOddsArray = beatOdds.split(",");
                //获取半全场map
                Map<String, String> halfMap = BallGameCals.getBeatResults();
                //拿到比分字段 形式如 33/1.95,31/2.87
                String beatAndOdd = detail[2];
                //根据，拆分
                String[] beatAndOddArray = beatAndOdd.split(",");
                String newBeatOdd = "";
                for (String s : beatAndOddArray) {
                    String[] sArray = s.split("/");
                    String no = halfMap.get(sArray[0]);
                    String newOdd = beatOddsArray[Integer.parseInt(no)];
//                    s = sArray[0] + "/" + newOdd;
                    s = sArray[0] + "/" + newOdd + "/" + sArray[2];
                    newBeatOdd += s + ",";
                }
//                aBeat = detail[0] + "+" + detail[1] + "+" + detail[2] + "+" + newBeatOdd;
                aBeat = detail[0] + "+" + detail[1] + "+" + newBeatOdd;
                newBeatDetail += aBeat + "|";
            }
        }
        return newBeatDetail;
    }


    /**
     * 更新让球胜负平赔率
     *
     * @param cdFootballSingleOrder
     * @return
     */
    public String getNewLet(CdFootballSingleOrder cdFootballSingleOrder) {
        String newLetDetail = "";
        //获取总进球结果
        String letResult = cdFootballSingleOrder.getLet();
        if (StringUtils.isNotEmpty(letResult)) {
            String[] letArray = letResult.split("\\|");
            for (String aLet : letArray) {
                //一条进球的详情
                String[] detail = aLet.split("\\+");
                //查询比赛
                CdFootballMixed cfm = cdFootballMixedService.findByMatchId(detail[0]);
                if (cfm == null) {
                    //比赛不存在 无法出票
                    return "0";
                }
                //获取让球总进球赔率
                String letOdds = cfm.getConcedepointsOdds();
                //根据,拆分成数组 取最新赔率
                String[] letOddsArray = letOdds.split(",");
                //获取半全场map
                Map<String, String> halfMap = BallGameCals.getBeatResults();
                //拿到比分字段 形式如 33/1.95,31/2.87
                String letAndOdd = detail[2];
                //根据，拆分
                String[] LetAndOddArray = letAndOdd.split(",");
                String newLetOdd = "";
                for (String s : LetAndOddArray) {
                    String[] sArray = s.split("/");
                    String no = halfMap.get(sArray[0]);
                    String newOdd = letOddsArray[Integer.parseInt(no)];
//                    s = sArray[0] + "/" + newOdd;
                    s = sArray[0] + "/" + newOdd + "/" + sArray[2];
                    newLetOdd += s + ",";
                }
//                aLet = detail[0] + "+" + detail[1] + "+" + detail[2] + "+" + newLetOdd;
                aLet = detail[0] + "+" + detail[1] + "+" + newLetOdd;
                newLetDetail += aLet + "|";
            }
        }
        return newLetDetail;
    }

}
