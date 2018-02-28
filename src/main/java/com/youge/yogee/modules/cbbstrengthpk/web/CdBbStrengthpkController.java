/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbstrengthpk.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cbbstrengthpk.entity.CdBbStrengthpk;
import com.youge.yogee.modules.cbbstrengthpk.service.CdBbStrengthpkService;
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
 * 篮球实力PKController
 * @author RenHaipeng
 * @version 2018-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/cbbstrengthpk/cdBbStrengthpk")
public class CdBbStrengthpkController extends BaseController {

	@Autowired
	private CdBbStrengthpkService cdBbStrengthpkService;
	
	@ModelAttribute
	public CdBbStrengthpk get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBbStrengthpkService.get(id);
		}else{
			return new CdBbStrengthpk();
		}
	}
	
	@RequiresPermissions("cbbstrengthpk:cdBbStrengthpk:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBbStrengthpk cdBbStrengthpk, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBbStrengthpk> page = cdBbStrengthpkService.find(new Page<CdBbStrengthpk>(request, response), cdBbStrengthpk);
        model.addAttribute("page", page);
		return "modules/cbbstrengthpk/cdBbStrengthpkList";
	}

	@RequiresPermissions("cbbstrengthpk:cdBbStrengthpk:view")
	@RequestMapping(value = "form")
	public String form(CdBbStrengthpk cdBbStrengthpk, Model model) {
		model.addAttribute("cdBbStrengthpk", cdBbStrengthpk);
		return "modules/cbbstrengthpk/cdBbStrengthpkForm";
	}

	@RequiresPermissions("cbbstrengthpk:cdBbStrengthpk:edit")
	@RequestMapping(value = "save")
	public String save(CdBbStrengthpk cdBbStrengthpk, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBbStrengthpk)){
			return form(cdBbStrengthpk, model);
		}
		cdBbStrengthpkService.save(cdBbStrengthpk);
		addMessage(redirectAttributes, "保存篮球实力PK'" + cdBbStrengthpk.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cbbstrengthpk/cdBbStrengthpk/?repage";
	}
	
	@RequiresPermissions("cbbstrengthpk:cdBbStrengthpk:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBbStrengthpkService.delete(id);
		addMessage(redirectAttributes, "删除篮球实力PK成功");
		return "redirect:"+ Global.getAdminPath()+"/cbbstrengthpk/cdBbStrengthpk/?repage";
	}

}
