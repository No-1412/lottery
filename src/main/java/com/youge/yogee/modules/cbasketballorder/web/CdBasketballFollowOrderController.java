/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballorder.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

}
