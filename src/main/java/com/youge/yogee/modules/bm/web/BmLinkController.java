/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.web;

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
import com.youge.yogee.modules.bm.entity.BmLink;
import com.youge.yogee.modules.bm.service.BmLinkService;

/**
 * 友情链接Controller
 * @author RenHaipeng
 * @version 2017-04-13
 */
@Controller
@RequestMapping(value = "${adminPath}/bm/bmLink")
public class BmLinkController extends BaseController {

	@Autowired
	private BmLinkService bmLinkService;
	
	@ModelAttribute
	public BmLink get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return bmLinkService.get(id);
		}else{
			return new BmLink();
		}
	}
	
	@RequiresPermissions("bm:bmLink:view")
	@RequestMapping(value = {"list", ""})
	public String list(BmLink bmLink, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<BmLink> page = bmLinkService.find(new Page<BmLink>(request, response), bmLink); 
        model.addAttribute("page", page);
		return "modules/bm/bmLinkList";
	}

	@RequiresPermissions("bm:bmLink:view")
	@RequestMapping(value = "form")
	public String form(BmLink bmLink, Model model) {
		model.addAttribute("bmLink", bmLink);
		return "modules/bm/bmLinkForm";
	}

	@RequiresPermissions("bm:bmLink:edit")
	@RequestMapping(value = "save")
	public String save(BmLink bmLink, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bmLink)){
			return form(bmLink, model);
		}
		bmLinkService.save(bmLink);
		addMessage(redirectAttributes, "保存友情链接'" + bmLink.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/bm/bmLink/?repage";
	}
	
	@RequiresPermissions("bm:bmLink:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		bmLinkService.delete(id);
		addMessage(redirectAttributes, "删除友情链接成功");
		return "redirect:"+Global.getAdminPath()+"/bm/bmLink/?repage";
	}

}
