/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbblogo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.youge.yogee.modules.cbblogo.entity.CdBbLogo;
import com.youge.yogee.modules.cbblogo.service.CdBbLogoService;

/**
 * 篮球logoController
 * @author ZhaoYiFeng
 * @version 2018-04-01
 */
@Controller
@RequestMapping(value = "${adminPath}/cbblogo/cdBbLogo")
public class CdBbLogoController extends BaseController {

	@Autowired
	private CdBbLogoService cdBbLogoService;
	
	@ModelAttribute
	public CdBbLogo get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBbLogoService.get(id);
		}else{
			return new CdBbLogo();
		}
	}
	
	@RequiresPermissions("cbblogo:cdBbLogo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBbLogo cdBbLogo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBbLogo> page = cdBbLogoService.find(new Page<CdBbLogo>(request, response), cdBbLogo); 
        model.addAttribute("page", page);
		return "modules/cbblogo/cdBbLogoList";
	}

	@RequiresPermissions("cbblogo:cdBbLogo:view")
	@RequestMapping(value = "form")
	public String form(CdBbLogo cdBbLogo, Model model) {
		model.addAttribute("cdBbLogo", cdBbLogo);
		return "modules/cbblogo/cdBbLogoForm";
	}

	@RequiresPermissions("cbblogo:cdBbLogo:edit")
	@RequestMapping(value = "save")
	public String save(CdBbLogo cdBbLogo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBbLogo)){
			return form(cdBbLogo, model);
		}
		cdBbLogoService.save(cdBbLogo);
		addMessage(redirectAttributes, "保存篮球logo'" + cdBbLogo.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cbblogo/cdBbLogo/?repage";
	}
	
	@RequiresPermissions("cbblogo:cdBbLogo:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBbLogoService.delete(id);
		addMessage(redirectAttributes, "删除篮球logo成功");
		return "redirect:"+Global.getAdminPath()+"/cbblogo/cdBbLogo/?repage";
	}

}
