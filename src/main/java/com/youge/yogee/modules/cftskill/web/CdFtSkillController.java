/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cftskill.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cftskill.entity.CdFtSkill;
import com.youge.yogee.modules.cftskill.service.CdFtSkillService;
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
 * 足球实况技术统计Controller
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cftskill/cdFtSkill")
public class CdFtSkillController extends BaseController {

	@Autowired
	private CdFtSkillService cdFtSkillService;
	
	@ModelAttribute
	public CdFtSkill get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFtSkillService.get(id);
		}else{
			return new CdFtSkill();
		}
	}
	
	@RequiresPermissions("cftskill:cdFtSkill:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFtSkill cdFtSkill, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFtSkill> page = cdFtSkillService.find(new Page<CdFtSkill>(request, response), cdFtSkill);
        model.addAttribute("page", page);
		return "modules/cftskill/cdFtSkillList";
	}

	@RequiresPermissions("cftskill:cdFtSkill:view")
	@RequestMapping(value = "form")
	public String form(CdFtSkill cdFtSkill, Model model) {
		model.addAttribute("cdFtSkill", cdFtSkill);
		return "modules/cftskill/cdFtSkillForm";
	}

	@RequiresPermissions("cftskill:cdFtSkill:edit")
	@RequestMapping(value = "save")
	public String save(CdFtSkill cdFtSkill, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFtSkill)){
			return form(cdFtSkill, model);
		}
		cdFtSkillService.save(cdFtSkill);
		addMessage(redirectAttributes, "保存足球实况技术统计'" + cdFtSkill.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cftskill/cdFtSkill/?repage";
	}
	
	@RequiresPermissions("cftskill:cdFtSkill:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFtSkillService.delete(id);
		addMessage(redirectAttributes, "删除足球实况技术统计成功");
		return "redirect:"+ Global.getAdminPath()+"/cftskill/cdFtSkill/?repage";
	}

}
