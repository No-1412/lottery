/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.usm.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.sys.entity.User;
import com.youge.yogee.modules.sys.utils.UserUtils;
import com.youge.yogee.modules.usm.entity.UsmInvite;
import com.youge.yogee.modules.usm.service.UsmInviteService;
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
 * 用户邀请Controller
 * @author RenHaipeng
 * @version 2016-12-16
 */
@Controller
@RequestMapping(value = "${adminPath}/usm/usmInvite")
public class UsmInviteController extends BaseController {

	@Autowired
	private UsmInviteService usmInviteService;
	
	@ModelAttribute
	public UsmInvite get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return usmInviteService.get(id);
		}else{
			return new UsmInvite();
		}
	}
	
	@RequiresPermissions("usm:usmInvite:view")
	@RequestMapping(value = {"list", ""})
	public String list(UsmInvite usmInvite, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<UsmInvite> page = usmInviteService.find(new Page<UsmInvite>(request, response), usmInvite); 
        model.addAttribute("page", page);
		return "modules/usm/usmInviteList";
	}

	@RequiresPermissions("usm:usmInvite:view")
	@RequestMapping(value = "form")
	public String form(UsmInvite usmInvite, Model model) {
		model.addAttribute("usmInvite", usmInvite);
		return "modules/usm/usmInviteForm";
	}

	@RequiresPermissions("usm:usmInvite:edit")
	@RequestMapping(value = "save")
	public String save(UsmInvite usmInvite, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, usmInvite)){
			return form(usmInvite, model);
		}
		usmInviteService.save(usmInvite);
		addMessage(redirectAttributes, "保存用户邀请成功");
		return "redirect:"+Global.getAdminPath()+"/usm/usmInvite/?repage";
	}
	
	@RequiresPermissions("usm:usmInvite:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		usmInviteService.delete(id);
		addMessage(redirectAttributes, "删除用户邀请成功");
		return "redirect:"+Global.getAdminPath()+"/usm/usmInvite/?repage";
	}

}
