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
import com.youge.yogee.modules.cforum.entity.CdForumSupport;
import com.youge.yogee.modules.cforum.service.CdForumSupportService;

/**
 * 论坛点赞Controller
 * @author ZhaoYiFeng
 * @version 2018-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cforum/cdForumSupport")
public class CdForumSupportController extends BaseController {

	@Autowired
	private CdForumSupportService cdForumSupportService;
	
	@ModelAttribute
	public CdForumSupport get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdForumSupportService.get(id);
		}else{
			return new CdForumSupport();
		}
	}
	
	@RequiresPermissions("cforum:cdForumSupport:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdForumSupport cdForumSupport, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdForumSupport> page = cdForumSupportService.find(new Page<CdForumSupport>(request, response), cdForumSupport); 
        model.addAttribute("page", page);
		return "modules/cforum/cdForumSupportList";
	}

	@RequiresPermissions("cforum:cdForumSupport:view")
	@RequestMapping(value = "form")
	public String form(CdForumSupport cdForumSupport, Model model) {
		model.addAttribute("cdForumSupport", cdForumSupport);
		return "modules/cforum/cdForumSupportForm";
	}

	@RequiresPermissions("cforum:cdForumSupport:edit")
	@RequestMapping(value = "save")
	public String save(CdForumSupport cdForumSupport, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdForumSupport)){
			return form(cdForumSupport, model);
		}
		cdForumSupportService.save(cdForumSupport);
		addMessage(redirectAttributes, "保存论坛点赞'" + cdForumSupport.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cforum/cdForumSupport/?repage";
	}
	
	@RequiresPermissions("cforum:cdForumSupport:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdForumSupportService.delete(id);
		addMessage(redirectAttributes, "删除论坛点赞成功");
		return "redirect:"+Global.getAdminPath()+"/cforum/cdForumSupport/?repage";
	}

}
