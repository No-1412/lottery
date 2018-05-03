/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfiveawards.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveOrder;
import com.youge.yogee.modules.cfiveawards.service.CdFiveOrderService;
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
import java.util.Date;

/**
 * 排列五订单Controller
 *
 * @author ZhaoYiFeng
 * @version 2018-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/cfiveawards/cdFiveOrder")
public class CdFiveOrderController extends BaseController {

    @Autowired
    private CdFiveOrderService cdFiveOrderService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdOrderService cdOrderService;
    @ModelAttribute
    public CdFiveOrder get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return cdFiveOrderService.get(id);
        } else {
            return new CdFiveOrder();
        }
    }

    @RequiresPermissions("cfiveawards:cdFiveOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(CdFiveOrder cdFiveOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();

        Page<CdFiveOrder> page = cdFiveOrderService.find(new Page<CdFiveOrder>(request, response), cdFiveOrder);
        model.addAttribute("page", page);
        return "modules/cfiveawards/cdFiveOrderList";
    }

    @RequiresPermissions("cfiveawards:cdFiveOrder:view")
    @RequestMapping(value = "form")
    public String form(CdFiveOrder cdFiveOrder, Model model) {
        CdLotteryUser clu = cdLotteryUserService.get(cdFiveOrder.getUid());
        String uName = clu.getName();
        model.addAttribute("uName", uName);
        model.addAttribute("cdFiveOrder", cdFiveOrder);
        return "modules/cfiveawards/cdFiveOrderForm";
    }

    @RequiresPermissions("cfiveawards:cdFiveOrder:edit")
    @RequestMapping(value = "save")
    public String save(CdFiveOrder cdFiveOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, cdFiveOrder)) {
            return form(cdFiveOrder, model);
        }
        //保存出票时间
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String outTime=df.format(day);
        CdOrder co=cdOrderService.getOrderByOrderNum(cdFiveOrder.getOrderNum());
        if(co!=null){
            co.setOutTime(outTime);
            cdOrderService.save(co);
        }
        cdFiveOrderService.save(cdFiveOrder);
        addMessage(redirectAttributes, "保存排列五订单'" + cdFiveOrder.getName() + "'成功");
        return "redirect:" + Global.getAdminPath() + "/cfiveawards/cdFiveOrder/?repage";
    }

    @RequiresPermissions("cfiveawards:cdFiveOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        cdFiveOrderService.delete(id);
        addMessage(redirectAttributes, "删除排列五订单成功");
        return "redirect:" + Global.getAdminPath() + "/cfiveawards/cdFiveOrder/?repage";
    }

}
