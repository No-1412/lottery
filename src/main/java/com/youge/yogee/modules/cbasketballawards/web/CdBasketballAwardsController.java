/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballawards.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cbasketballawards.entity.CdBasketballAwards;
import com.youge.yogee.modules.cbasketballawards.service.CdBasketballAwardsService;
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
 * @version 2018-01-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cbasketballawards/cdBasketballAwards")
public class CdBasketballAwardsController extends BaseController {

	@Autowired
	private CdBasketballAwardsService cdBasketballAwardsService;
	
	@ModelAttribute
	public CdBasketballAwards get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBasketballAwardsService.get(id);
		}else{
			return new CdBasketballAwards();
		}
	}
	
	@RequiresPermissions("cbasketballawards:cdBasketballAwards:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBasketballAwards cdBasketballAwards, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBasketballAwards> page = cdBasketballAwardsService.find(new Page<CdBasketballAwards>(request, response), cdBasketballAwards);
        model.addAttribute("page", page);
		return "modules/cbasketballawards/cdBasketballAwardsList";
	}

	@RequiresPermissions("cbasketballawards:cdBasketballAwards:view")
	@RequestMapping(value = "form")
	public String form(CdBasketballAwards cdBasketballAwards, Model model) {
		model.addAttribute("cdBasketballAwards", cdBasketballAwards);
		return "modules/cbasketballawards/cdBasketballAwardsForm";
	}

	@RequiresPermissions("cbasketballawards:cdBasketballAwards:edit")
	@RequestMapping(value = "save")
	public String save(CdBasketballAwards cdBasketballAwards, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBasketballAwards)){
			return form(cdBasketballAwards, model);
		}
		cdBasketballAwardsService.save(cdBasketballAwards);
		addMessage(redirectAttributes, "保存竞彩足球开奖信息'" + cdBasketballAwards.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cbasketballawards/cdBasketballAwards/?repage";
	}
	
	@RequiresPermissions("cbasketballawards:cdBasketballAwards:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBasketballAwardsService.delete(id);
		addMessage(redirectAttributes, "删除竞彩足球开奖信息成功");
		return "redirect:"+ Global.getAdminPath()+"/cbasketballawards/cdBasketballAwards/?repage";
	}

}
