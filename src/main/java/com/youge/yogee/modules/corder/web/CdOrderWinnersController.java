/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.corder.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
import com.youge.yogee.modules.corder.service.CdOrderWinnersService;
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

/**
 * 中奖订单Controller
 *
 * @author ZhaoYiFeng
 * @version 2018-03-14
 */
@Controller
@RequestMapping(value = "${adminPath}/corder/cdOrderWinners")
public class CdOrderWinnersController extends BaseController {

    @Autowired
    private CdOrderWinnersService cdOrderWinnersService;

    @ModelAttribute
    public CdOrderWinners get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return cdOrderWinnersService.get(id);
        } else {
            return new CdOrderWinners();
        }
    }

    @RequiresPermissions("corder:cdOrderWinners:view")
    @RequestMapping(value = {"list", ""})
    public String list(CdOrderWinners cdOrderWinners, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();

        Page<CdOrderWinners> page = cdOrderWinnersService.find(new Page<CdOrderWinners>(request, response), cdOrderWinners);
        model.addAttribute("page", page);
        return "modules/corder/cdOrderWinnersList";
    }

    @RequiresPermissions("corder:cdOrderWinners:view")
    @RequestMapping(value = "form")
    public String form(CdOrderWinners cdOrderWinners, Model model) {
        model.addAttribute("cdOrderWinners", cdOrderWinners);
        return "modules/corder/cdOrderWinnersForm";
    }

    @RequiresPermissions("corder:cdOrderWinners:edit")
    @RequestMapping(value = "save")
    public String save(CdOrderWinners cdOrderWinners, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, cdOrderWinners)) {
            return form(cdOrderWinners, model);
        }
        cdOrderWinnersService.save(cdOrderWinners);
        addMessage(redirectAttributes, "保存中奖订单成功");
        return "redirect:" + Global.getAdminPath() + "/corder/cdOrderWinners/?repage";
    }

    @RequiresPermissions("corder:cdOrderWinners:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        cdOrderWinnersService.delete(id);
        addMessage(redirectAttributes, "删除中奖订单成功");
        return "redirect:" + Global.getAdminPath() + "/corder/cdOrderWinners/?repage";
    }

}
