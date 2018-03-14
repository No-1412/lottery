/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbstrengthpk.web;

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
import com.youge.yogee.modules.cbbstrengthpk.entity.CdBbStrengthpkAverage;
import com.youge.yogee.modules.cbbstrengthpk.service.CdBbStrengthpkAverageService;

/**
 * 篮球球员实力Controller
 * @author ZhaoYiFeng
 * @version 2018-03-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cbbstrengthpk/cdBbStrengthpkAverage")
public class CdBbStrengthpkAverageController extends BaseController {

	@Autowired
	private CdBbStrengthpkAverageService cdBbStrengthpkAverageService;
	
	@ModelAttribute
	public CdBbStrengthpkAverage get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBbStrengthpkAverageService.get(id);
		}else{
			return new CdBbStrengthpkAverage();
		}
	}
	
	@RequiresPermissions("cbbstrengthpk:cdBbStrengthpkAverage:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBbStrengthpkAverage cdBbStrengthpkAverage, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBbStrengthpkAverage> page = cdBbStrengthpkAverageService.find(new Page<CdBbStrengthpkAverage>(request, response), cdBbStrengthpkAverage); 
        model.addAttribute("page", page);
		return "modules/cbbstrengthpk/cdBbStrengthpkAverageList";
	}

	@RequiresPermissions("cbbstrengthpk:cdBbStrengthpkAverage:view")
	@RequestMapping(value = "form")
	public String form(CdBbStrengthpkAverage cdBbStrengthpkAverage, Model model) {
		model.addAttribute("cdBbStrengthpkAverage", cdBbStrengthpkAverage);
		return "modules/cbbstrengthpk/cdBbStrengthpkAverageForm";
	}

	@RequiresPermissions("cbbstrengthpk:cdBbStrengthpkAverage:edit")
	@RequestMapping(value = "save")
	public String save(CdBbStrengthpkAverage cdBbStrengthpkAverage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBbStrengthpkAverage)){
			return form(cdBbStrengthpkAverage, model);
		}
		cdBbStrengthpkAverageService.save(cdBbStrengthpkAverage);
		addMessage(redirectAttributes, "保存篮球球员实力'" + cdBbStrengthpkAverage.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cbbstrengthpk/cdBbStrengthpkAverage/?repage";
	}
	
	@RequiresPermissions("cbbstrengthpk:cdBbStrengthpkAverage:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBbStrengthpkAverageService.delete(id);
		addMessage(redirectAttributes, "删除篮球球员实力成功");
		return "redirect:"+Global.getAdminPath()+"/cbbstrengthpk/cdBbStrengthpkAverage/?repage";
	}

}
