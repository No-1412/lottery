/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfiveawards.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveAwards;
import com.youge.yogee.modules.cfiveawards.service.CdFiveAwardsService;
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
 * 排列五开奖信息Controller
 * @author RenHaipeng
 * @version 2018-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/cfiveawards/cdFiveAwards")
public class CdFiveAwardsController extends BaseController {

	@Autowired
	private CdFiveAwardsService cdFiveAwardsService;
	
	@ModelAttribute
	public CdFiveAwards get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFiveAwardsService.get(id);
		}else{
			return new CdFiveAwards();
		}
	}
	
	@RequiresPermissions("cfiveawards:cdFiveAwards:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFiveAwards cdFiveAwards, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFiveAwards> page = cdFiveAwardsService.find(new Page<CdFiveAwards>(request, response), cdFiveAwards);
        model.addAttribute("page", page);
		return "modules/cfiveawards/cdFiveAwardsList";
	}

	@RequiresPermissions("cfiveawards:cdFiveAwards:view")
	@RequestMapping(value = "form")
	public String form(CdFiveAwards cdFiveAwards, Model model) {
		model.addAttribute("cdFiveAwards", cdFiveAwards);
		return "modules/cfiveawards/cdFiveAwardsForm";
	}

	@RequiresPermissions("cfiveawards:cdFiveAwards:edit")
	@RequestMapping(value = "save")
	public String save(CdFiveAwards cdFiveAwards, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFiveAwards)){
			return form(cdFiveAwards, model);
		}
		cdFiveAwardsService.save(cdFiveAwards);
		addMessage(redirectAttributes, "保存排列五开奖信息'" + cdFiveAwards.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cfiveawards/cdFiveAwards/?repage";
	}
	
	@RequiresPermissions("cfiveawards:cdFiveAwards:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFiveAwardsService.delete(id);
		addMessage(redirectAttributes, "删除排列五开奖信息成功");
		return "redirect:"+ Global.getAdminPath()+"/cfiveawards/cdFiveAwards/?repage";
	}

}
