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
import com.youge.yogee.modules.bm.entity.BmPush;
import com.youge.yogee.modules.bm.service.BmPushService;

/**
 * 推送表Controller
 * @author ZhaoYiFeng
 * @version 2018-03-09
 */
@Controller
@RequestMapping(value = "${adminPath}/bm/bmPush")
public class BmPushController extends BaseController {

	@Autowired
	private BmPushService bmPushService;
	
	@ModelAttribute
	public BmPush get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return bmPushService.get(id);
		}else{
			return new BmPush();
		}
	}
	
	@RequiresPermissions("bm:bmPush:view")
	@RequestMapping(value = {"list", ""})
	public String list(BmPush bmPush, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<BmPush> page = bmPushService.find(new Page<BmPush>(request, response), bmPush); 
        model.addAttribute("page", page);
		return "modules/bm/bmPushList";
	}

	@RequiresPermissions("bm:bmPush:view")
	@RequestMapping(value = "form")
	public String form(BmPush bmPush, Model model) {
		model.addAttribute("bmPush", bmPush);
		return "modules/bm/bmPushForm";
	}

	@RequiresPermissions("bm:bmPush:edit")
	@RequestMapping(value = "save")
	public String save(BmPush bmPush, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bmPush)){
			return form(bmPush, model);
		}
		bmPushService.save(bmPush);
		addMessage(redirectAttributes, "保存推送成功");
		return "redirect:"+Global.getAdminPath()+"/bm/bmPush/?repage";
	}
	
	@RequiresPermissions("bm:bmPush:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		bmPushService.delete(id);
		addMessage(redirectAttributes, "删除推送表成功");
		return "redirect:"+Global.getAdminPath()+"/bm/bmPush/?repage";
	}

}
