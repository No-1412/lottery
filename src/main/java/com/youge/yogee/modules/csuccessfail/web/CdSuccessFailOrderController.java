/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csuccessfail.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.service.CdOrderService;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFailOrder;
import com.youge.yogee.modules.csuccessfail.service.CdSuccessFailOrderService;
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

/**
 * 胜负彩订单Controller
 *
 * @author ZhaoYiFeng
 * @version 2018-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/csuccessfail/cdSuccessFailOrder")
public class CdSuccessFailOrderController extends BaseController {

    @Autowired
    private CdSuccessFailOrderService cdSuccessFailOrderService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;
    @Autowired
    private CdOrderService cdOrderService;

    @ModelAttribute
    public CdSuccessFailOrder get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return cdSuccessFailOrderService.get(id);
        } else {
            return new CdSuccessFailOrder();
        }
    }

    @RequiresPermissions("csuccessfail:cdSuccessFailOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(CdSuccessFailOrder cdSuccessFailOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();

        Page<CdSuccessFailOrder> page = cdSuccessFailOrderService.find(new Page<CdSuccessFailOrder>(request, response), cdSuccessFailOrder);

//        List<CdSuccessFailOrder> list = page.getList();
//        List<CdSuccessFailOrder> newList = new ArrayList<>();
//        if (list.size() > 0) {
//            for (CdSuccessFailOrder c : list) {
//                CdSuccessFailOrder ccn = new CdSuccessFailOrder();
//                ccn.setId(c.getId());
//                ccn.setOrderNumber(c.getOrderNumber());
//                ccn.setWeekday(c.getWeekday());
//                ccn.setAcount(c.getAcount());
//                ccn.setPrice(c.getPrice());
//                CdLotteryUser clu = cdLotteryUserService.get(c.getUid());
//                ccn.setUid(clu.getReality());
//                ccn.setCreateDate(c.getCreateDate());
//                ccn.setStatus(c.getStatus());
//                ccn.setTimes(c.getTimes());
//                newList.add(ccn);
//            }
//        }
//
//        page.setList(newList);

        model.addAttribute("page", page);
        return "modules/csuccessfail/cdSuccessFailOrderList";
    }

    @RequiresPermissions("csuccessfail:cdSuccessFailOrder:view")
    @RequestMapping(value = "form")
    public String form(CdSuccessFailOrder cdSuccessFailOrder, Model model) {
        List<String> list = new ArrayList<>();
        String uName = "";
        if (cdSuccessFailOrder != null) {
            String detail = cdSuccessFailOrder.getOrderDetail();
            if (StringUtils.isNotEmpty(detail)) {
                String detailStr[] = detail.split("\\|");
                for (String s : detailStr) {
                    list.add(s);
                }
            }
            CdLotteryUser clu = cdLotteryUserService.get(cdSuccessFailOrder.getUid());
            uName = clu.getName();
        }

        model.addAttribute("list", list);
        model.addAttribute("uName", uName);
        model.addAttribute("cdSuccessFailOrder", cdSuccessFailOrder);
        return "modules/csuccessfail/cdSuccessFailOrderForm";
    }

    @RequiresPermissions("csuccessfail:cdSuccessFailOrder:edit")
    @RequestMapping(value = "save")
    public String save(CdSuccessFailOrder cdSuccessFailOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, cdSuccessFailOrder)) {
            return form(cdSuccessFailOrder, model);
        }
        //保存出票时间
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String outTime=df.format(day);
        CdOrder co=cdOrderService.getOrderByOrderNum(cdSuccessFailOrder.getOrderNumber());
        if(co!=null){
            co.setOutTime(outTime);
            cdOrderService.save(co);
        }
        cdSuccessFailOrderService.save(cdSuccessFailOrder);
        addMessage(redirectAttributes, "保存胜负彩订单成功");
        return "redirect:" + Global.getAdminPath() + "/csuccessfail/cdSuccessFailOrder/?repage";
    }

    @RequiresPermissions("csuccessfail:cdSuccessFailOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        cdSuccessFailOrderService.delete(id);
        addMessage(redirectAttributes, "删除胜负彩订单成功");
        return "redirect:" + Global.getAdminPath() + "/csuccessfail/cdSuccessFailOrder/?repage";
    }

}
