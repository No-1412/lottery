/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cthreeawards.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeAwards;
import com.youge.yogee.modules.cthreeawards.service.CdThreeAwardsService;
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
 * 排列三开奖信息Controller
 * @author RenHaipeng
 * @version 2018-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/cthreeawards/cdThreeAwards")
public class CdThreeAwardsController extends BaseController {

	@Autowired
	private CdThreeAwardsService cdThreeAwardsService;
	
	@ModelAttribute
	public CdThreeAwards get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdThreeAwardsService.get(id);
		}else{
			return new CdThreeAwards();
		}
	}
	
	@RequiresPermissions("cthreeawards:cdThreeAwards:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdThreeAwards cdThreeAwards, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdThreeAwards> page = cdThreeAwardsService.find(new Page<CdThreeAwards>(request, response), cdThreeAwards);
        model.addAttribute("page", page);
		return "modules/cthreeawards/cdThreeAwardsList";
	}

	@RequiresPermissions("cthreeawards:cdThreeAwards:view")
	@RequestMapping(value = "form")
	public String form(CdThreeAwards cdThreeAwards, Model model) {
		model.addAttribute("cdThreeAwards", cdThreeAwards);
		return "modules/cthreeawards/cdThreeAwardsForm";
	}

	@RequiresPermissions("cthreeawards:cdThreeAwards:edit")
	@RequestMapping(value = "save")
	public String save(CdThreeAwards cdThreeAwards, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdThreeAwards)){
			return form(cdThreeAwards, model);
		}
		cdThreeAwardsService.save(cdThreeAwards);
		addMessage(redirectAttributes, "保存排列三开奖信息'" + cdThreeAwards.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cthreeawards/cdThreeAwards/?repage";
	}
	
	@RequiresPermissions("cthreeawards:cdThreeAwards:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdThreeAwardsService.delete(id);
		addMessage(redirectAttributes, "删除排列三开奖信息成功");
		return "redirect:"+ Global.getAdminPath()+"/cthreeawards/cdThreeAwards/?repage";
	}

}
