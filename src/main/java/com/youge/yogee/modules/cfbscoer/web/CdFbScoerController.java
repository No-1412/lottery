/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbscoer.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfbscoer.entity.CdFbScoer;
import com.youge.yogee.modules.cfbscoer.service.CdFbScoerService;
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
 * 竞彩足球积分Controller
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cfbscoer/cdFbScoer")
public class CdFbScoerController extends BaseController {

	@Autowired
	private CdFbScoerService cdFbScoerService;
	
	@ModelAttribute
	public CdFbScoer get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFbScoerService.get(id);
		}else{
			return new CdFbScoer();
		}
	}
	
	@RequiresPermissions("cfbscoer:cdFbScoer:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFbScoer cdFbScoer, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFbScoer> page = cdFbScoerService.find(new Page<CdFbScoer>(request, response), cdFbScoer);
        model.addAttribute("page", page);
		return "modules/cfbscoer/cdFbScoerList";
	}

	@RequiresPermissions("cfbscoer:cdFbScoer:view")
	@RequestMapping(value = "form")
	public String form(CdFbScoer cdFbScoer, Model model) {
		model.addAttribute("cdFbScoer", cdFbScoer);
		return "modules/cfbscoer/cdFbScoerForm";
	}

	@RequiresPermissions("cfbscoer:cdFbScoer:edit")
	@RequestMapping(value = "save")
	public String save(CdFbScoer cdFbScoer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFbScoer)){
			return form(cdFbScoer, model);
		}
		cdFbScoerService.save(cdFbScoer);
		addMessage(redirectAttributes, "保存竞彩足球积分'" + cdFbScoer.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cfbscoer/cdFbScoer/?repage";
	}
	
	@RequiresPermissions("cfbscoer:cdFbScoer:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFbScoerService.delete(id);
		addMessage(redirectAttributes, "删除竞彩足球积分成功");
		return "redirect:"+ Global.getAdminPath()+"/cfbscoer/cdFbScoer/?repage";
	}

}
