/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.caboutus.web;

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
import com.youge.yogee.modules.caboutus.entity.CdAboutUs;
import com.youge.yogee.modules.caboutus.service.CdAboutUsService;

/**
 * 关于我们Controller
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/caboutus/cdAboutUs")
public class CdAboutUsController extends BaseController {

	@Autowired
	private CdAboutUsService cdAboutUsService;
	
	@ModelAttribute
	public CdAboutUs get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdAboutUsService.get(id);
		}else{
			return new CdAboutUs();
		}
	}
	
	@RequiresPermissions("caboutus:cdAboutUs:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdAboutUs cdAboutUs, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdAboutUs> page = cdAboutUsService.find(new Page<CdAboutUs>(request, response), cdAboutUs); 
        model.addAttribute("page", page);
		return "modules/caboutus/cdAboutUsList";
	}

	@RequiresPermissions("caboutus:cdAboutUs:view")
	@RequestMapping(value = "form")
	public String form(CdAboutUs cdAboutUs, Model model) {
		model.addAttribute("cdAboutUs", cdAboutUs);
		return "modules/caboutus/cdAboutUsForm";
	}

	@RequiresPermissions("caboutus:cdAboutUs:edit")
	@RequestMapping(value = "save")
	public String save(CdAboutUs cdAboutUs, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdAboutUs)){
			return form(cdAboutUs, model);
		}
		cdAboutUsService.save(cdAboutUs);
		addMessage(redirectAttributes, "保存关于我们'" + cdAboutUs.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/caboutus/cdAboutUs/?repage";
	}
	
	@RequiresPermissions("caboutus:cdAboutUs:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdAboutUsService.delete(id);
		addMessage(redirectAttributes, "删除关于我们成功");
		return "redirect:"+Global.getAdminPath()+"/caboutus/cdAboutUs/?repage";
	}

}
