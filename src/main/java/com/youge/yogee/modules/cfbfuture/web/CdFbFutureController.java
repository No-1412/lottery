/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbfuture.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfbfuture.entity.CdFbFuture;
import com.youge.yogee.modules.cfbfuture.service.CdFbFutureService;
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
 * 竞彩足球未来赛事Controller
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cfbfuture/cdFbFuture")
public class CdFbFutureController extends BaseController {

	@Autowired
	private CdFbFutureService cdFbFutureService;
	
	@ModelAttribute
	public CdFbFuture get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFbFutureService.get(id);
		}else{
			return new CdFbFuture();
		}
	}
	
	@RequiresPermissions("cfbfuture:cdFbFuture:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFbFuture cdFbFuture, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFbFuture> page = cdFbFutureService.find(new Page<CdFbFuture>(request, response), cdFbFuture);
        model.addAttribute("page", page);
		return "modules/cfbfuture/cdFbFutureList";
	}

	@RequiresPermissions("cfbfuture:cdFbFuture:view")
	@RequestMapping(value = "form")
	public String form(CdFbFuture cdFbFuture, Model model) {
		model.addAttribute("cdFbFuture", cdFbFuture);
		return "modules/cfbfuture/cdFbFutureForm";
	}

	@RequiresPermissions("cfbfuture:cdFbFuture:edit")
	@RequestMapping(value = "save")
	public String save(CdFbFuture cdFbFuture, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFbFuture)){
			return form(cdFbFuture, model);
		}
		cdFbFutureService.save(cdFbFuture);
		addMessage(redirectAttributes, "保存竞彩足球未来赛事'" + cdFbFuture.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cfbfuture/cdFbFuture/?repage";
	}
	
	@RequiresPermissions("cfbfuture:cdFbFuture:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFbFutureService.delete(id);
		addMessage(redirectAttributes, "删除竞彩足球未来赛事成功");
		return "redirect:"+ Global.getAdminPath()+"/cfbfuture/cdFbFuture/?repage";
	}

}
