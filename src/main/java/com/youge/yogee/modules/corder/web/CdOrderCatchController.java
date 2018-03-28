/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.corder.web;

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
import com.youge.yogee.modules.corder.entity.CdOrderCatch;
import com.youge.yogee.modules.corder.service.CdOrderCatchService;

/**
 * 追号Controller
 * @author ZhaoYiFeng
 * @version 2018-03-27
 */
@Controller
@RequestMapping(value = "${adminPath}/corder/cdOrderCatch")
public class CdOrderCatchController extends BaseController {

	@Autowired
	private CdOrderCatchService cdOrderCatchService;
	
	@ModelAttribute
	public CdOrderCatch get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdOrderCatchService.get(id);
		}else{
			return new CdOrderCatch();
		}
	}
	
	@RequiresPermissions("corder:cdOrderCatch:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdOrderCatch cdOrderCatch, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdOrderCatch> page = cdOrderCatchService.find(new Page<CdOrderCatch>(request, response), cdOrderCatch); 
        model.addAttribute("page", page);
		return "modules/corder/cdOrderCatchList";
	}

	@RequiresPermissions("corder:cdOrderCatch:view")
	@RequestMapping(value = "form")
	public String form(CdOrderCatch cdOrderCatch, Model model) {
		model.addAttribute("cdOrderCatch", cdOrderCatch);
		return "modules/corder/cdOrderCatchForm";
	}

	@RequiresPermissions("corder:cdOrderCatch:edit")
	@RequestMapping(value = "save")
	public String save(CdOrderCatch cdOrderCatch, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdOrderCatch)){
			return form(cdOrderCatch, model);
		}
		cdOrderCatchService.save(cdOrderCatch);
		addMessage(redirectAttributes, "保存追号'" + cdOrderCatch.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/corder/cdOrderCatch/?repage";
	}
	
	@RequiresPermissions("corder:cdOrderCatch:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdOrderCatchService.delete(id);
		addMessage(redirectAttributes, "删除追号成功");
		return "redirect:"+Global.getAdminPath()+"/corder/cdOrderCatch/?repage";
	}

}
