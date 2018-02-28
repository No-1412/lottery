/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbalreadyfinsh.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cbbalreadyfinsh.entity.CdBbAlreadyFinsh;
import com.youge.yogee.modules.cbbalreadyfinsh.service.CdBbAlreadyFinshService;
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
 * 篮球已完赛Controller
 * @author WeiJinChao
 * @version 2018-01-30
 */
@Controller
@RequestMapping(value = "${adminPath}/cbbalreadyfinsh/cdBbAlreadyFinsh")
public class CdBbAlreadyFinshController extends BaseController {

	@Autowired
	private CdBbAlreadyFinshService cdBbAlreadyFinshService;
	
	@ModelAttribute
	public CdBbAlreadyFinsh get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBbAlreadyFinshService.get(id);
		}else{
			return new CdBbAlreadyFinsh();
		}
	}
	
	@RequiresPermissions("cbbalreadyfinsh:cdBbAlreadyFinsh:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBbAlreadyFinsh cdBbAlreadyFinsh, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBbAlreadyFinsh> page = cdBbAlreadyFinshService.find(new Page<CdBbAlreadyFinsh>(request, response), cdBbAlreadyFinsh);
        model.addAttribute("page", page);
		return "modules/cbbalreadyfinsh/cdBbAlreadyFinshList";
	}

	@RequiresPermissions("cbbalreadyfinsh:cdBbAlreadyFinsh:view")
	@RequestMapping(value = "form")
	public String form(CdBbAlreadyFinsh cdBbAlreadyFinsh, Model model) {
		model.addAttribute("cdBbAlreadyFinsh", cdBbAlreadyFinsh);
		return "modules/cbbalreadyfinsh/cdBbAlreadyFinshForm";
	}

	@RequiresPermissions("cbbalreadyfinsh:cdBbAlreadyFinsh:edit")
	@RequestMapping(value = "save")
	public String save(CdBbAlreadyFinsh cdBbAlreadyFinsh, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBbAlreadyFinsh)){
			return form(cdBbAlreadyFinsh, model);
		}
		cdBbAlreadyFinshService.save(cdBbAlreadyFinsh);
		addMessage(redirectAttributes, "保存篮球已完赛'" + cdBbAlreadyFinsh.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cbbalreadyfinsh/cdBbAlreadyFinsh/?repage";
	}
	
	@RequiresPermissions("cbbalreadyfinsh:cdBbAlreadyFinsh:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBbAlreadyFinshService.delete(id);
		addMessage(redirectAttributes, "删除篮球已完赛成功");
		return "redirect:"+ Global.getAdminPath()+"/cbbalreadyfinsh/cdBbAlreadyFinsh/?repage";
	}

}
