/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbfinshed.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfbfinshed.entity.CdFbFinshed;
import com.youge.yogee.modules.cfbfinshed.service.CdFbFinshedService;
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
 * 足球未完赛信息Controller
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cfbfinshed/cdFbFinshed")
public class CdFbFinshedController extends BaseController {

	@Autowired
	private CdFbFinshedService cdFbFinshedService;
	
	@ModelAttribute
	public CdFbFinshed get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFbFinshedService.get(id);
		}else{
			return new CdFbFinshed();
		}
	}
	
	@RequiresPermissions("cfbfinshed:cdFbFinshed:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFbFinshed cdFbFinshed, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFbFinshed> page = cdFbFinshedService.find(new Page<CdFbFinshed>(request, response), cdFbFinshed);
        model.addAttribute("page", page);
		return "modules/cfbfinshed/cdFbFinshedList";
	}

	@RequiresPermissions("cfbfinshed:cdFbFinshed:view")
	@RequestMapping(value = "form")
	public String form(CdFbFinshed cdFbFinshed, Model model) {
		model.addAttribute("cdFbFinshed", cdFbFinshed);
		return "modules/cfbfinshed/cdFbFinshedForm";
	}

	@RequiresPermissions("cfbfinshed:cdFbFinshed:edit")
	@RequestMapping(value = "save")
	public String save(CdFbFinshed cdFbFinshed, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFbFinshed)){
			return form(cdFbFinshed, model);
		}
		cdFbFinshedService.save(cdFbFinshed);
		addMessage(redirectAttributes, "保存足球未完赛信息'" + cdFbFinshed.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cfbfinshed/cdFbFinshed/?repage";
	}
	
	@RequiresPermissions("cfbfinshed:cdFbFinshed:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFbFinshedService.delete(id);
		addMessage(redirectAttributes, "删除足球未完赛信息成功");
		return "redirect:"+ Global.getAdminPath()+"/cfbfinshed/cdFbFinshed/?repage";
	}

}
