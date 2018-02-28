/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbtoutcome.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cbtoutcome.entity.CdBtOutcome;
import com.youge.yogee.modules.cbtoutcome.service.CdBtOutcomeService;
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
 * 竞彩篮球近期赛事Controller
 * @author RenHaipeng
 * @version 2018-01-18
 */
@Controller
@RequestMapping(value = "${adminPath}/cbtoutcome/cdBtOutcome")
public class CdBtOutcomeController extends BaseController {

	@Autowired
	private CdBtOutcomeService cdBtOutcomeService;
	
	@ModelAttribute
	public CdBtOutcome get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBtOutcomeService.get(id);
		}else{
			return new CdBtOutcome();
		}
	}
	
	@RequiresPermissions("cbtoutcome:cdBtOutcome:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBtOutcome cdBtOutcome, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBtOutcome> page = cdBtOutcomeService.find(new Page<CdBtOutcome>(request, response), cdBtOutcome);
        model.addAttribute("page", page);
		return "modules/cbtoutcome/cdBtOutcomeList";
	}

	@RequiresPermissions("cbtoutcome:cdBtOutcome:view")
	@RequestMapping(value = "form")
	public String form(CdBtOutcome cdBtOutcome, Model model) {
		model.addAttribute("cdBtOutcome", cdBtOutcome);
		return "modules/cbtoutcome/cdBtOutcomeForm";
	}

	@RequiresPermissions("cbtoutcome:cdBtOutcome:edit")
	@RequestMapping(value = "save")
	public String save(CdBtOutcome cdBtOutcome, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBtOutcome)){
			return form(cdBtOutcome, model);
		}
		cdBtOutcomeService.save(cdBtOutcome);
		addMessage(redirectAttributes, "保存竞彩篮球近期赛事'" + cdBtOutcome.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cbtoutcome/cdBtOutcome/?repage";
	}
	
	@RequiresPermissions("cbtoutcome:cdBtOutcome:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBtOutcomeService.delete(id);
		addMessage(redirectAttributes, "删除竞彩篮球近期赛事成功");
		return "redirect:"+ Global.getAdminPath()+"/cbtoutcome/cdBtOutcome/?repage";
	}

}
