/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.push.AppPush;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.bm.entity.BmPushMessage;
import com.youge.yogee.modules.bm.service.BmPushMessageService;
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
 * 推送消息Controller
 *
 * @author ZhaoYiFeng
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/bm/bmPushMessage")
public class BmPushMessageController extends BaseController {

    @Autowired
    private BmPushMessageService bmPushMessageService;

    @ModelAttribute
    public BmPushMessage get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return bmPushMessageService.get(id);
        } else {
            return new BmPushMessage();
        }
    }

    @RequiresPermissions("bm:bmPushMessage:view")
    @RequestMapping(value = {"list", ""})
    public String list(BmPushMessage bmPushMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();

        Page<BmPushMessage> page = bmPushMessageService.find(new Page<BmPushMessage>(request, response), bmPushMessage);
        model.addAttribute("page", page);
        return "modules/bm/bmPushMessageList";
    }

    @RequiresPermissions("bm:bmPushMessage:view")
    @RequestMapping(value = "form")
    public String form(BmPushMessage bmPushMessage, Model model) {
        model.addAttribute("bmPushMessage", bmPushMessage);
        return "modules/bm/bmPushMessageForm";
    }

    @RequiresPermissions("bm:bmPushMessage:edit")
    @RequestMapping(value = "save")
    public String save(BmPushMessage bmPushMessage, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, bmPushMessage)) {
            return form(bmPushMessage, model);
        }
        bmPushMessageService.save(bmPushMessage);
        AppPush.pushAll("凯旋彩票", bmPushMessage.getMessage());
        addMessage(redirectAttributes, "保存推送消息成功");
        return "redirect:" + Global.getAdminPath() + "/bm/bmPushMessage/?repage";
    }

    @RequiresPermissions("bm:bmPushMessage:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        bmPushMessageService.delete(id);
        addMessage(redirectAttributes, "删除推送消息成功");
        return "redirect:" + Global.getAdminPath() + "/bm/bmPushMessage/?repage";
    }

}
