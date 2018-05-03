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
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballSingleOrderService;
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
@RequestMapping(value = "${adminPath}/cbasketballorder/cdBasketballSingleOrder")
public class CdBasketballSingleOrderController extends BaseController {

    @Autowired
    private CdBasketballSingleOrderService cdBasketballSingleOrderService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdBasketballMixedService cdBasketballMixedService;
    @Autowired
    private CdOrderService cdOrderService;

    @ModelAttribute
    public CdBasketballSingleOrder get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return cdBasketballSingleOrderService.get(id);
        } else {
            return new CdBasketballSingleOrder();
        }
    }

    @RequiresPermissions("cbasketballorder:cdBasketballSingleOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(CdBasketballSingleOrder cdBasketballSingleOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();

        Page<CdBasketballSingleOrder> page = cdBasketballSingleOrderService.find(new Page<CdBasketballSingleOrder>(request, response), cdBasketballSingleOrder);
        model.addAttribute("page", page);
        return "modules/cbasketballorder/cdBasketballSingleOrderList";
    }

    @RequiresPermissions("cbasketballorder:cdBasketballSingleOrder:view")
    @RequestMapping(value = "form")
    public String form(CdBasketballSingleOrder cdBasketballSingleOrder, Model model) {

        String uid = cdBasketballSingleOrder.getUid();
        String uName = "";
        if (StringUtils.isNotEmpty(uid)) {
            CdLotteryUser clu = cdLotteryUserService.get(uid);
            uName = clu.getName();
        }
        String hostWin = cdBasketballSingleOrder.getHostWin();
        List<String> wList = new ArrayList<>();

        String hostFail = cdBasketballSingleOrder.getHostFail();
        List<String> fList = new ArrayList<>();


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

        //获取当前时间
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = df.format(day);

        model.addAttribute("today", today);
        model.addAttribute("wList", wList);
        model.addAttribute("fList", fList);
        model.addAttribute("uName", uName);

        model.addAttribute("cdBasketballSingleOrder", cdBasketballSingleOrder);
        return "modules/cbasketballorder/cdBasketballSingleOrderForm";
    }

    @RequiresPermissions("cbasketballorder:cdBasketballSingleOrder:edit")
    @RequestMapping(value = "save")
    public String save(CdBasketballSingleOrder cdBasketballSingleOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, cdBasketballSingleOrder)) {
            return form(cdBasketballSingleOrder, model);
        }


        //更新到出票赔率
        String newWinDetail = getNewWin(cdBasketballSingleOrder);
        String newFailDetail = getNewFail(cdBasketballSingleOrder);


        if ("0".equals(newWinDetail) || "0".equals(newFailDetail)) {
            cdBasketballSingleOrder.setStatus("2");
            cdBasketballSingleOrderService.save(cdBasketballSingleOrder);
            addMessage(redirectAttributes, "出票失败,比赛可能不存在");
            return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballSingleOrder/?repage";
        }
        //更新赔率
        cdBasketballSingleOrder.setHostWin(newWinDetail);
        cdBasketballSingleOrder.setHostFail(newFailDetail);

       //保存出票时间
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String outTime=df.format(day);
        CdOrder co=cdOrderService.getOrderByOrderNum(cdBasketballSingleOrder.getOrderNum());
        if(co!=null){
            co.setOutTime(outTime);
            cdOrderService.save(co);
        }

        cdBasketballSingleOrderService.save(cdBasketballSingleOrder);
        //addMessage(redirectAttributes, "保存竞彩篮球订单成功");
        //return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballSingleOrder/?repage";
        //==================start   2018-04-11   yuhongwei 跳转打印页
        String buy_ways = cdBasketballSingleOrder.getBuyWays();
        String match_ids = cdBasketballSingleOrder.getMatchIds().substring(0,cdBasketballSingleOrder.getMatchIds().length()-1);
        String baseUrl = "modules/print/";
        model.addAttribute("orderNumber",cdBasketballSingleOrder.getOrderNum());
        String returnStr=cdBasketballSingleOrder.getPrice()+"元,";//打印在头部显示
        if("1".equals(buy_ways)){//混投
          returnStr = "竟篮混合,"+returnStr;
            model.addAttribute("returnStr", returnStr);
            if(match_ids.split(",").length<=3){//足彩_3关
                return baseUrl+ "basketball3";
            }else if(match_ids.split(",").length<=6){//足彩_6关
                return baseUrl+  "basketball6";
            }else if(match_ids.split(",").length<=8){//足彩_8关
                return baseUrl+"basketball8";
            }
        }

        addMessage(redirectAttributes, "保存成功,没有模板不能打印");
        return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballSingleOrder/?repage";

        //==================end   2018-04-11   yuhongwei 跳转打印页
    }

    @RequiresPermissions("cbasketballorder:cdBasketballSingleOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        cdBasketballSingleOrderService.delete(id);
        addMessage(redirectAttributes, "删除竞彩篮球订单成功");
        return "redirect:" + Global.getAdminPath() + "/cbasketballorder/cdBasketballSingleOrder/?repage";
    }


    /**
     * 更新主胜赔率
     *
     * @param cdBasketballSingleOrder
     * @return
     */
    public String getNewWin(CdBasketballSingleOrder cdBasketballSingleOrder) {
        String newWinDetail = "";
        //获取主胜结果
        String winResult = cdBasketballSingleOrder.getHostWin();
        if (StringUtils.isNotEmpty(winResult)) {
            String[] winArray = winResult.split("\\|");
            for (String aScore : winArray) {
                //一条比分的详情
                String[] detail = aScore.split("\\+");
                //查询比赛
                CdBasketballMixed cfm = cdBasketballMixedService.findByMatchId(detail[0]);
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
                String scoreAndOdd = detail[2];
                //根据，拆分
                String[] scoreAndOddArray = scoreAndOdd.split(",");
                String newScoreOdd = "";
                for (String s : scoreAndOddArray) {
                    String[] sArray = s.split("/");
                    int no = winMap.get(sArray[0]);
                    String newOdd = winOddsArray[no];
                    s = sArray[0] + "/" + newOdd + "/" + sArray[2];
                    newScoreOdd += s + ",";
                }
                aScore = detail[0] + "+" + detail[1] + "+" + newScoreOdd;
                newWinDetail += aScore + "|";
            }
        }
        return newWinDetail;
    }

    /**
     * 更新主负赔率
     *
     * @param cdBasketballSingleOrder
     * @return
     */
    public String getNewFail(CdBasketballSingleOrder cdBasketballSingleOrder) {
        String newFailDetail = "";
        //获取主胜结果
        String failResult = cdBasketballSingleOrder.getHostFail();
        if (StringUtils.isNotEmpty(failResult)) {
            String[] failArray = failResult.split("\\|");
            for (String aFail : failArray) {
                //一条比分的详情
                String[] detail = aFail.split("\\+");
                //查询比赛
                CdBasketballMixed cfm = cdBasketballMixedService.findByMatchId(detail[0]);
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
                String winAndOdd = detail[2];
                //根据，拆分
                String[] winAndOddArray = winAndOdd.split(",");
                String newScoreOdd = "";
                for (String s : winAndOddArray) {
                    String[] sArray = s.split("/");
                    int no = winMap.get(sArray[0]);
                    String newOdd = failOddsArray[no];
                    s = sArray[0] + "/" + newOdd + "/" + sArray[2];
                    newScoreOdd += s + ",";
                }
                aFail = detail[0] + "+" + detail[1] + "+" + newScoreOdd;
                newFailDetail += aFail + "|";
            }
        }
        return newFailDetail;
    }

}
