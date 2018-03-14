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
import com.youge.yogee.modules.cbbstrengthpk.entity.CdBbStrengthpkInjury;
import com.youge.yogee.modules.cbbstrengthpk.service.CdBbStrengthpkInjuryService;

/**
 * 篮球伤停Controller
 * @author ZhaoYiFeng
 * @version 2018-03-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cbbstrengthpk/cdBbStrengthpkInjury")
public class CdBbStrengthpkInjuryController extends BaseController {

	@Autowired
	private CdBbStrengthpkInjuryService cdBbStrengthpkInjuryService;
	
	@ModelAttribute
	public CdBbStrengthpkInjury get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBbStrengthpkInjuryService.get(id);
		}else{
			return new CdBbStrengthpkInjury();
		}
	}
	
	@RequiresPermissions("cbbstrengthpk:cdBbStrengthpkInjury:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBbStrengthpkInjury cdBbStrengthpkInjury, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBbStrengthpkInjury> page = cdBbStrengthpkInjuryService.find(new Page<CdBbStrengthpkInjury>(request, response), cdBbStrengthpkInjury); 
        model.addAttribute("page", page);
		return "modules/cbbstrengthpk/cdBbStrengthpkInjuryList";
	}

	@RequiresPermissions("cbbstrengthpk:cdBbStrengthpkInjury:view")
	@RequestMapping(value = "form")
	public String form(CdBbStrengthpkInjury cdBbStrengthpkInjury, Model model) {
		model.addAttribute("cdBbStrengthpkInjury", cdBbStrengthpkInjury);
		return "modules/cbbstrengthpk/cdBbStrengthpkInjuryForm";
	}

	@RequiresPermissions("cbbstrengthpk:cdBbStrengthpkInjury:edit")
	@RequestMapping(value = "save")
	public String save(CdBbStrengthpkInjury cdBbStrengthpkInjury, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBbStrengthpkInjury)){
			return form(cdBbStrengthpkInjury, model);
		}
		cdBbStrengthpkInjuryService.save(cdBbStrengthpkInjury);
		addMessage(redirectAttributes, "保存篮球伤停成功");
		return "redirect:"+Global.getAdminPath()+"/cbbstrengthpk/cdBbStrengthpkInjury/?repage";
	}
	
	@RequiresPermissions("cbbstrengthpk:cdBbStrengthpkInjury:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBbStrengthpkInjuryService.delete(id);
		addMessage(redirectAttributes, "删除篮球伤停成功");
		return "redirect:"+Global.getAdminPath()+"/cbbstrengthpk/cdBbStrengthpkInjury/?repage";
	}

}
