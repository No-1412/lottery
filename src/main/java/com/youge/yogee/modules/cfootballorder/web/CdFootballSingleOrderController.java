/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballorder.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import org.activiti.engine.impl.bpmn.data.Data;
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
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballSingleOrderService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            uName = clu.getReality();
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
        cdFootballSingleOrderService.save(cdFootballSingleOrder);
        addMessage(redirectAttributes, "保存竞彩足球订单'" + cdFootballSingleOrder.getName() + "'成功");
        return "redirect:" + Global.getAdminPath() + "/cfootballorder/cdFootballSingleOrder/?repage";
    }

    @RequiresPermissions("cfootballorder:cdFootballSingleOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        cdFootballSingleOrderService.delete(id);
        addMessage(redirectAttributes, "删除竞彩足球订单成功");
        return "redirect:" + Global.getAdminPath() + "/cfootballorder/cdFootballSingleOrder/?repage";
    }

}
