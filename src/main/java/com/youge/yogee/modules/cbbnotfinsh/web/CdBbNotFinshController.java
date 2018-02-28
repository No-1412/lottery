/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbnotfinsh.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cbbnotfinsh.entity.CdBbNotFinsh;
import com.youge.yogee.modules.cbbnotfinsh.service.CdBbNotFinshService;
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
 * 篮球未完赛Controller
 * @author RenHaipeng
 * @version 2018-01-31
 */
@Controller
@RequestMapping(value = "${adminPath}/cbbnotfinsh/cdBbNotFinsh")
public class CdBbNotFinshController extends BaseController {

	@Autowired
	private CdBbNotFinshService cdBbNotFinshService;
	
	@ModelAttribute
	public CdBbNotFinsh get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBbNotFinshService.get(id);
		}else{
			return new CdBbNotFinsh();
		}
	}
	
	@RequiresPermissions("cbbnotfinsh:cdBbNotFinsh:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBbNotFinsh cdBbNotFinsh, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBbNotFinsh> page = cdBbNotFinshService.find(new Page<CdBbNotFinsh>(request, response), cdBbNotFinsh);
        model.addAttribute("page", page);
		return "modules/cbbnotfinsh/cdBbNotFinshList";
	}

	@RequiresPermissions("cbbnotfinsh:cdBbNotFinsh:view")
	@RequestMapping(value = "form")
	public String form(CdBbNotFinsh cdBbNotFinsh, Model model) {
		model.addAttribute("cdBbNotFinsh", cdBbNotFinsh);
		return "modules/cbbnotfinsh/cdBbNotFinshForm";
	}

	@RequiresPermissions("cbbnotfinsh:cdBbNotFinsh:edit")
	@RequestMapping(value = "save")
	public String save(CdBbNotFinsh cdBbNotFinsh, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBbNotFinsh)){
			return form(cdBbNotFinsh, model);
		}
		cdBbNotFinshService.save(cdBbNotFinsh);
		addMessage(redirectAttributes, "保存篮球未完赛'" + cdBbNotFinsh.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cbbnotfinsh/cdBbNotFinsh/?repage";
	}
	
	@RequiresPermissions("cbbnotfinsh:cdBbNotFinsh:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBbNotFinshService.delete(id);
		addMessage(redirectAttributes, "删除篮球未完赛成功");
		return "redirect:"+ Global.getAdminPath()+"/cbbnotfinsh/cdBbNotFinsh/?repage";
	}

}
