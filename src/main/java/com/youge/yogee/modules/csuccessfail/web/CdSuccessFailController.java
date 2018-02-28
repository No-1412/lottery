/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csuccessfail.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFail;
import com.youge.yogee.modules.csuccessfail.service.CdSuccessFailService;
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
 * 胜负彩Controller
 * @author RenHaipeng
 * @version 2018-01-04
 */
@Controller
@RequestMapping(value = "${adminPath}/csuccessfail/cdSuccessFail")
public class CdSuccessFailController extends BaseController {

	@Autowired
	private CdSuccessFailService cdSuccessFailService;
	
	@ModelAttribute
	public CdSuccessFail get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdSuccessFailService.get(id);
		}else{
			return new CdSuccessFail();
		}
	}
	
	@RequiresPermissions("csuccessfail:cdSuccessFail:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdSuccessFail cdSuccessFail, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdSuccessFail> page = cdSuccessFailService.find(new Page<CdSuccessFail>(request, response), cdSuccessFail);
        model.addAttribute("page", page);
		return "modules/csuccessfail/cdSuccessFailList";
	}

	@RequiresPermissions("csuccessfail:cdSuccessFail:view")
	@RequestMapping(value = "form")
	public String form(CdSuccessFail cdSuccessFail, Model model) {
		model.addAttribute("cdSuccessFail", cdSuccessFail);
		return "modules/csuccessfail/cdSuccessFailForm";
	}

	@RequiresPermissions("csuccessfail:cdSuccessFail:edit")
	@RequestMapping(value = "save")
	public String save(CdSuccessFail cdSuccessFail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdSuccessFail)){
			return form(cdSuccessFail, model);
		}
		cdSuccessFailService.save(cdSuccessFail);
		addMessage(redirectAttributes, "保存胜负彩'" + cdSuccessFail.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/csuccessfail/cdSuccessFail/?repage";
	}
	
	@RequiresPermissions("csuccessfail:cdSuccessFail:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdSuccessFailService.delete(id);
		addMessage(redirectAttributes, "删除胜负彩成功");
		return "redirect:"+ Global.getAdminPath()+"/csuccessfail/cdSuccessFail/?repage";
	}

}
