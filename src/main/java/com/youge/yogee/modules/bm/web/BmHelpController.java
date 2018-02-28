/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.bm.entity.BmHelp;
import com.youge.yogee.modules.bm.service.BmHelpService;
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
 * 新手教程Controller
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/bm/bmHelp")
public class BmHelpController extends BaseController {

	@Autowired
	private BmHelpService bmHelpService;
	
	@ModelAttribute
	public BmHelp get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return bmHelpService.get(id);
		}else{
			return new BmHelp();
		}
	}
	
	@RequiresPermissions("bm:bmHelp:view")
	@RequestMapping(value = {"list", ""})
	public String list(BmHelp bmHelp, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<BmHelp> page = bmHelpService.find(new Page<BmHelp>(request, response), bmHelp); 
        model.addAttribute("page", page);
		return "modules/bm/bmHelpList";
	}

	@RequiresPermissions("bm:bmHelp:view")
	@RequestMapping(value = "form")
	public String form(BmHelp bmHelp, Model model) {
		model.addAttribute("bmHelp", bmHelp);
		return "modules/bm/bmHelpForm";
	}

	@RequiresPermissions("bm:bmHelp:edit")
	@RequestMapping(value = "save")
	public String save(BmHelp bmHelp, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bmHelp)){
			return form(bmHelp, model);
		}
		bmHelpService.save(bmHelp);
		addMessage(redirectAttributes, "保存新手教程'" + bmHelp.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/bm/bmHelp/?repage";
	}
	
	@RequiresPermissions("bm:bmHelp:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		bmHelpService.delete(id);
		addMessage(redirectAttributes, "删除新手教程成功");
		return "redirect:"+Global.getAdminPath()+"/bm/bmHelp/?repage";
	}

}
