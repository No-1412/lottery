/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cftlogo.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cftlogo.entity.CdFtLogo;
import com.youge.yogee.modules.cftlogo.service.CdFtLogoService;
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
 * 足球logo码表Controller
 * @author RenHaipeng
 * @version 2018-02-01
 */
@Controller
@RequestMapping(value = "${adminPath}/cftlogo/cdFtLogo")
public class CdFtLogoController extends BaseController {

	@Autowired
	private CdFtLogoService cdFtLogoService;
	
	@ModelAttribute
	public CdFtLogo get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFtLogoService.get(id);
		}else{
			return new CdFtLogo();
		}
	}
	
	@RequiresPermissions("cftlogo:cdFtLogo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFtLogo cdFtLogo, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFtLogo> page = cdFtLogoService.find(new Page<CdFtLogo>(request, response), cdFtLogo);
        model.addAttribute("page", page);
		return "modules/cftlogo/cdFtLogoList";
	}

	@RequiresPermissions("cftlogo:cdFtLogo:view")
	@RequestMapping(value = "form")
	public String form(CdFtLogo cdFtLogo, Model model) {
		model.addAttribute("cdFtLogo", cdFtLogo);
		return "modules/cftlogo/cdFtLogoForm";
	}

	@RequiresPermissions("cftlogo:cdFtLogo:edit")
	@RequestMapping(value = "save")
	public String save(CdFtLogo cdFtLogo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFtLogo)){
			return form(cdFtLogo, model);
		}
		cdFtLogoService.save(cdFtLogo);
		addMessage(redirectAttributes, "保存足球logo码表'" + cdFtLogo.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cftlogo/cdFtLogo/?repage";
	}
	
	@RequiresPermissions("cftlogo:cdFtLogo:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFtLogoService.delete(id);
		addMessage(redirectAttributes, "删除足球logo码表成功");
		return "redirect:"+ Global.getAdminPath()+"/cftlogo/cdFtLogo/?repage";
	}

}
