/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbtfuture.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cbtfuture.entity.CdBtFuture;
import com.youge.yogee.modules.cbtfuture.service.CdBtFutureService;
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
 * 竞彩篮球未来赛事Controller
 * @author RenHaipeng
 * @version 2018-01-18
 */
@Controller
@RequestMapping(value = "${adminPath}/cbtfuture/cdBtFuture")
public class CdBtFutureController extends BaseController {

	@Autowired
	private CdBtFutureService cdBtFutureService;
	
	@ModelAttribute
	public CdBtFuture get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBtFutureService.get(id);
		}else{
			return new CdBtFuture();
		}
	}
	
	@RequiresPermissions("cbtfuture:cdBtFuture:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBtFuture cdBtFuture, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBtFuture> page = cdBtFutureService.find(new Page<CdBtFuture>(request, response), cdBtFuture);
        model.addAttribute("page", page);
		return "modules/cbtfuture/cdBtFutureList";
	}

	@RequiresPermissions("cbtfuture:cdBtFuture:view")
	@RequestMapping(value = "form")
	public String form(CdBtFuture cdBtFuture, Model model) {
		model.addAttribute("cdBtFuture", cdBtFuture);
		return "modules/cbtfuture/cdBtFutureForm";
	}

	@RequiresPermissions("cbtfuture:cdBtFuture:edit")
	@RequestMapping(value = "save")
	public String save(CdBtFuture cdBtFuture, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBtFuture)){
			return form(cdBtFuture, model);
		}
		cdBtFutureService.save(cdBtFuture);
		addMessage(redirectAttributes, "保存竞彩篮球未来赛事'" + cdBtFuture.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cbtfuture/cdBtFuture/?repage";
	}
	
	@RequiresPermissions("cbtfuture:cdBtFuture:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBtFutureService.delete(id);
		addMessage(redirectAttributes, "删除竞彩篮球未来赛事成功");
		return "redirect:"+ Global.getAdminPath()+"/cbtfuture/cdBtFuture/?repage";
	}

}
