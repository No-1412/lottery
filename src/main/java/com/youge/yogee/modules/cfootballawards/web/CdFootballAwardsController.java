/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballawards.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfootballawards.entity.CdFootballAwards;
import com.youge.yogee.modules.cfootballawards.service.CdFootballAwardsService;
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
 * 竞彩足球开奖信息Controller
 * @author RenHaipeng
 * @version 2018-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/cfootballawards/cdFootballAwards")
public class CdFootballAwardsController extends BaseController {

	@Autowired
	private CdFootballAwardsService cdFootballAwardsService;
	
	@ModelAttribute
	public CdFootballAwards get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFootballAwardsService.get(id);
		}else{
			return new CdFootballAwards();
		}
	}
	
	@RequiresPermissions("cfootballawards:cdFootballAwards:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFootballAwards cdFootballAwards, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFootballAwards> page = cdFootballAwardsService.find(new Page<CdFootballAwards>(request, response), cdFootballAwards);
        model.addAttribute("page", page);
		return "modules/cfootballawards/cdFootballAwardsList";
	}

	@RequiresPermissions("cfootballawards:cdFootballAwards:view")
	@RequestMapping(value = "form")
	public String form(CdFootballAwards cdFootballAwards, Model model) {
		model.addAttribute("cdFootballAwards", cdFootballAwards);
		return "modules/cfootballawards/cdFootballAwardsForm";
	}

	@RequiresPermissions("cfootballawards:cdFootballAwards:edit")
	@RequestMapping(value = "save")
	public String save(CdFootballAwards cdFootballAwards, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFootballAwards)){
			return form(cdFootballAwards, model);
		}
		cdFootballAwardsService.save(cdFootballAwards);
		addMessage(redirectAttributes, "保存竞彩足球开奖信息'" + cdFootballAwards.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cfootballawards/cdFootballAwards/?repage";
	}
	
	@RequiresPermissions("cfootballawards:cdFootballAwards:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFootballAwardsService.delete(id);
		addMessage(redirectAttributes, "删除竞彩足球开奖信息成功");
		return "redirect:"+ Global.getAdminPath()+"/cfootballawards/cdFootballAwards/?repage";
	}

}
