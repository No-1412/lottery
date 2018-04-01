/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbnotfinish.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinish;
import com.youge.yogee.modules.cfbnotfinish.service.CdFbNotFinishService;
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
public class CdFbNotFinishController extends BaseController {

	@Autowired
	private CdFbNotFinishService cdFbNotFinishService;
	
	@ModelAttribute
	public CdFbNotFinish get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFbNotFinishService.get(id);
		}else{
			return new CdFbNotFinish();
		}
	}
	
	@RequiresPermissions("cfbnotfinish:cdFbNotFinish:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFbNotFinish cdFbNotFinish, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFbNotFinish> page = cdFbNotFinishService.find(new Page<CdFbNotFinish>(request, response), cdFbNotFinish);
        model.addAttribute("page", page);
		return "modules/cfbfinshed/cdFbFinshedList";
	}

	@RequiresPermissions("cfbnotfinish:cdFbNotFinish:view")
	@RequestMapping(value = "form")
	public String form(CdFbNotFinish cdFbNotFinish, Model model) {
		model.addAttribute("cdFbFinshed", cdFbNotFinish);
		return "modules/cfbfinshed/cdFbFinshedForm";
	}

	@RequiresPermissions("cfbnotfinish:cdFbNotFinish:edit")
	@RequestMapping(value = "save")
	public String save(CdFbNotFinish cdFbNotFinish, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFbNotFinish)){
			return form(cdFbNotFinish, model);
		}
		cdFbNotFinishService.save(cdFbNotFinish);
		addMessage(redirectAttributes, "保存足球未完赛信息'" + cdFbNotFinish.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cfbnotfinish/cdFbNotFinish/?repage";
	}
	
	@RequiresPermissions("cfbnotfinish:cdFbNotFinish:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFbNotFinishService.delete(id);
		addMessage(redirectAttributes, "删除足球未完赛信息成功");
		return "redirect:"+ Global.getAdminPath()+"/cfbnotfinish/cdFbNotFinish/?repage";
	}

}
