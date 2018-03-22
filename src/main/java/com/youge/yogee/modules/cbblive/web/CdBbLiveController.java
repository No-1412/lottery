/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbblive.web;

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
import com.youge.yogee.modules.cbblive.entity.CdBbLive;
import com.youge.yogee.modules.cbblive.service.CdBbLiveService;

/**
 * 伤停补时数据Controller
 * @author ZhaoYiFeng
 * @version 2018-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/cbblive/cdBbLive")
public class CdBbLiveController extends BaseController {

	@Autowired
	private CdBbLiveService cdBbLiveService;
	
	@ModelAttribute
	public CdBbLive get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBbLiveService.get(id);
		}else{
			return new CdBbLive();
		}
	}
	
	@RequiresPermissions("cbblive:cdBbLive:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBbLive cdBbLive, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBbLive> page = cdBbLiveService.find(new Page<CdBbLive>(request, response), cdBbLive); 
        model.addAttribute("page", page);
		return "modules/cbblive/cdBbLiveList";
	}

	@RequiresPermissions("cbblive:cdBbLive:view")
	@RequestMapping(value = "form")
	public String form(CdBbLive cdBbLive, Model model) {
		model.addAttribute("cdBbLive", cdBbLive);
		return "modules/cbblive/cdBbLiveForm";
	}

	@RequiresPermissions("cbblive:cdBbLive:edit")
	@RequestMapping(value = "save")
	public String save(CdBbLive cdBbLive, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBbLive)){
			return form(cdBbLive, model);
		}
		cdBbLiveService.save(cdBbLive);
		addMessage(redirectAttributes, "保存伤停补时数据成功");
		return "redirect:"+Global.getAdminPath()+"/cbblive/cdBbLive/?repage";
	}
	
	@RequiresPermissions("cbblive:cdBbLive:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBbLiveService.delete(id);
		addMessage(redirectAttributes, "删除伤停补时数据成功");
		return "redirect:"+Global.getAdminPath()+"/cbblive/cdBbLive/?repage";
	}

}
