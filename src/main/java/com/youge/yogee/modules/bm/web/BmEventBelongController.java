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
import com.youge.yogee.modules.bm.entity.BmEventBelong;
import com.youge.yogee.modules.bm.service.BmEventBelongService;

/**
 * 赛事洲事Controller
 * @author ZhaoYiFeng
 * @version 2018-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/bm/bmEventBelong")
public class BmEventBelongController extends BaseController {

	@Autowired
	private BmEventBelongService bmEventBelongService;
	
	@ModelAttribute
	public BmEventBelong get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return bmEventBelongService.get(id);
		}else{
			return new BmEventBelong();
		}
	}
	
	@RequiresPermissions("bm:bmEventBelong:view")
	@RequestMapping(value = {"list", ""})
	public String list(BmEventBelong bmEventBelong, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<BmEventBelong> page = bmEventBelongService.find(new Page<BmEventBelong>(request, response), bmEventBelong); 
        model.addAttribute("page", page);
		return "modules/bm/bmEventBelongList";
	}

	@RequiresPermissions("bm:bmEventBelong:view")
	@RequestMapping(value = "form")
	public String form(BmEventBelong bmEventBelong, Model model) {
		model.addAttribute("bmEventBelong", bmEventBelong);
		return "modules/bm/bmEventBelongForm";
	}

	@RequiresPermissions("bm:bmEventBelong:edit")
	@RequestMapping(value = "save")
	public String save(BmEventBelong bmEventBelong, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bmEventBelong)){
			return form(bmEventBelong, model);
		}
		bmEventBelongService.save(bmEventBelong);
		addMessage(redirectAttributes, "保存赛事洲事'" + bmEventBelong.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/bm/bmEventBelong/?repage";
	}
	
	@RequiresPermissions("bm:bmEventBelong:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		bmEventBelongService.delete(id);
		addMessage(redirectAttributes, "删除赛事洲事成功");
		return "redirect:"+Global.getAdminPath()+"/bm/bmEventBelong/?repage";
	}

}
