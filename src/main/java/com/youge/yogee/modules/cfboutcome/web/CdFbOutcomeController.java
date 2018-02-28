/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfboutcome.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfboutcome.entity.CdFbOutcome;
import com.youge.yogee.modules.cfboutcome.service.CdFbOutcomeService;
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
 * 竞彩足球详情近期赛事Controller
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cfboutcome/cdFbOutcome")
public class CdFbOutcomeController extends BaseController {

	@Autowired
	private CdFbOutcomeService cdFbOutcomeService;
	
	@ModelAttribute
	public CdFbOutcome get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFbOutcomeService.get(id);
		}else{
			return new CdFbOutcome();
		}
	}
	
	@RequiresPermissions("cfboutcome:cdFbOutcome:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFbOutcome cdFbOutcome, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFbOutcome> page = cdFbOutcomeService.find(new Page<CdFbOutcome>(request, response), cdFbOutcome);
        model.addAttribute("page", page);
		return "modules/cfboutcome/cdFbOutcomeList";
	}

	@RequiresPermissions("cfboutcome:cdFbOutcome:view")
	@RequestMapping(value = "form")
	public String form(CdFbOutcome cdFbOutcome, Model model) {
		model.addAttribute("cdFbOutcome", cdFbOutcome);
		return "modules/cfboutcome/cdFbOutcomeForm";
	}

	@RequiresPermissions("cfboutcome:cdFbOutcome:edit")
	@RequestMapping(value = "save")
	public String save(CdFbOutcome cdFbOutcome, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFbOutcome)){
			return form(cdFbOutcome, model);
		}
		cdFbOutcomeService.save(cdFbOutcome);
		addMessage(redirectAttributes, "保存竞彩足球详情近期赛事'" + cdFbOutcome.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cfboutcome/cdFbOutcome/?repage";
	}
	
	@RequiresPermissions("cfboutcome:cdFbOutcome:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFbOutcomeService.delete(id);
		addMessage(redirectAttributes, "删除竞彩足球详情近期赛事成功");
		return "redirect:"+ Global.getAdminPath()+"/cfboutcome/cdFbOutcome/?repage";
	}

}
