/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cforum.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.sys.entity.User;
import com.youge.yogee.modules.sys.utils.UserUtils;
import com.youge.yogee.modules.cforum.entity.CdForumReplay;
import com.youge.yogee.modules.cforum.service.CdForumReplayService;

/**
 * 论坛回复Controller
 * @author ZhaoYiFeng
 * @version 2018-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cforum/cdForumReplay")
public class CdForumReplayController extends BaseController {

	@Autowired
	private CdForumReplayService cdForumReplayService;
	
	@ModelAttribute
	public CdForumReplay get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdForumReplayService.get(id);
		}else{
			return new CdForumReplay();
		}
	}
	
	@RequiresPermissions("cforum:cdForumReplay:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdForumReplay cdForumReplay, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdForumReplay> page = cdForumReplayService.find(new Page<CdForumReplay>(request, response), cdForumReplay); 
        model.addAttribute("page", page);
		return "modules/cforum/cdForumReplayList";
	}

	@RequiresPermissions("cforum:cdForumReplay:view")
	@RequestMapping(value = "form")
	public String form(CdForumReplay cdForumReplay, Model model) {
		model.addAttribute("cdForumReplay", cdForumReplay);
		return "modules/cforum/cdForumReplayForm";
	}

	@RequiresPermissions("cforum:cdForumReplay:edit")
	@RequestMapping(value = "save")
	public String save(CdForumReplay cdForumReplay, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdForumReplay)){
			return form(cdForumReplay, model);
		}
		cdForumReplayService.save(cdForumReplay);
		addMessage(redirectAttributes, "保存论坛回复'" + cdForumReplay.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cforum/cdForumReplay/?repage";
	}
	
	@RequiresPermissions("cforum:cdForumReplay:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdForumReplayService.delete(id);
		addMessage(redirectAttributes, "删除论坛回复成功");
		return "redirect:"+Global.getAdminPath()+"/cforum/cdForumReplay/?repage";
	}

}
