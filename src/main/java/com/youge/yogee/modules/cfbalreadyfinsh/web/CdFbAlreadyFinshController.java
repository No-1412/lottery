/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbalreadyfinsh.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfbalreadyfinsh.entity.CdFbAlreadyFinsh;
import com.youge.yogee.modules.cfbalreadyfinsh.service.CdFbAlreadyFinshService;
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
 * 足球已经完赛信息Controller
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cfbalreadyfinsh/cdFbAlreadyFinsh")
public class CdFbAlreadyFinshController extends BaseController {

	@Autowired
	private CdFbAlreadyFinshService cdFbAlreadyFinshService;
	
	@ModelAttribute
	public CdFbAlreadyFinsh get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFbAlreadyFinshService.get(id);
		}else{
			return new CdFbAlreadyFinsh();
		}
	}
	
	@RequiresPermissions("cfbalreadyfinsh:cdFbAlreadyFinsh:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFbAlreadyFinsh cdFbAlreadyFinsh, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFbAlreadyFinsh> page = cdFbAlreadyFinshService.find(new Page<CdFbAlreadyFinsh>(request, response), cdFbAlreadyFinsh);
        model.addAttribute("page", page);
		return "modules/cfbalreadyfinsh/cdFbAlreadyFinshList";
	}

	@RequiresPermissions("cfbalreadyfinsh:cdFbAlreadyFinsh:view")
	@RequestMapping(value = "form")
	public String form(CdFbAlreadyFinsh cdFbAlreadyFinsh, Model model) {
		model.addAttribute("cdFbAlreadyFinsh", cdFbAlreadyFinsh);
		return "modules/cfbalreadyfinsh/cdFbAlreadyFinshForm";
	}

	@RequiresPermissions("cfbalreadyfinsh:cdFbAlreadyFinsh:edit")
	@RequestMapping(value = "save")
	public String save(CdFbAlreadyFinsh cdFbAlreadyFinsh, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFbAlreadyFinsh)){
			return form(cdFbAlreadyFinsh, model);
		}
		cdFbAlreadyFinshService.save(cdFbAlreadyFinsh);
		addMessage(redirectAttributes, "保存足球已经完赛信息'" + cdFbAlreadyFinsh.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cfbalreadyfinsh/cdFbAlreadyFinsh/?repage";
	}
	
	@RequiresPermissions("cfbalreadyfinsh:cdFbAlreadyFinsh:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFbAlreadyFinshService.delete(id);
		addMessage(redirectAttributes, "删除足球已经完赛信息成功");
		return "redirect:"+ Global.getAdminPath()+"/cfbalreadyfinsh/cdFbAlreadyFinsh/?repage";
	}

}
