/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.bm.entity.BmBase;
import com.youge.yogee.modules.bm.service.BmBaseService;
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
 * 基本信息Controller
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/bm/bmBase")
public class BmBaseController extends BaseController {

	@Autowired
	private BmBaseService bmBaseService;
	
	@ModelAttribute
	public BmBase get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return bmBaseService.get(id);
		}else{
			return new BmBase();
		}
	}
	
	@RequiresPermissions("bm:bmBase:view")
	@RequestMapping(value = {"list", ""})
	public String list(BmBase bmBase, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		Page<BmBase> page = bmBaseService.find(new Page<BmBase>(request, response), bmBase);

		model.addAttribute("page", page);
		return "modules/bm/bmBaseList";
	}

	@RequiresPermissions("bm:bmBase:view")
	@RequestMapping(value = "form")
	public String form(BmBase bmBase, Model model) {
		model.addAttribute("bmBase", bmBase);
		return "modules/bm/bmBaseForm";
	}

	@RequiresPermissions("bm:bmBase:edit")
	@RequestMapping(value = "save")
	public String save(BmBase bmBase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bmBase)){
			return form(bmBase, model);
		}
		bmBaseService.save(bmBase);
		addMessage(redirectAttributes, "保存基本信息'" + bmBase.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/bm/bmBase/?repage";
	}
	
	@RequiresPermissions("bm:bmBase:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		bmBaseService.delete(id);
		addMessage(redirectAttributes, "删除基本信息成功");
		return "redirect:"+Global.getAdminPath()+"/bm/bmBase/?repage";
	}

}
