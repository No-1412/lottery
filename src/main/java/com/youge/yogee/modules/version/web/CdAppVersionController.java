/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.version.web;

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
import com.youge.yogee.modules.version.entity.CdAppVersion;
import com.youge.yogee.modules.version.service.CdAppVersionService;

/**
 * 版本信息Controller
 * @author ZhaoYiFeng
 * @version 2018-05-28
 */
@Controller
@RequestMapping(value = "${adminPath}/version/cdAppVersion")
public class CdAppVersionController extends BaseController {

	@Autowired
	private CdAppVersionService cdAppVersionService;
	
	@ModelAttribute
	public CdAppVersion get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdAppVersionService.get(id);
		}else{
			return new CdAppVersion();
		}
	}
	
	@RequiresPermissions("version:cdAppVersion:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdAppVersion cdAppVersion, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdAppVersion> page = cdAppVersionService.find(new Page<CdAppVersion>(request, response), cdAppVersion); 
        model.addAttribute("page", page);
		return "modules/version/cdAppVersionList";
	}

	@RequiresPermissions("version:cdAppVersion:view")
	@RequestMapping(value = "form")
	public String form(CdAppVersion cdAppVersion, Model model) {
		model.addAttribute("cdAppVersion", cdAppVersion);
		return "modules/version/cdAppVersionForm";
	}

	@RequiresPermissions("version:cdAppVersion:edit")
	@RequestMapping(value = "save")
	public String save(CdAppVersion cdAppVersion, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdAppVersion)){
			return form(cdAppVersion, model);
		}
		cdAppVersionService.save(cdAppVersion);
		addMessage(redirectAttributes, "保存版本信息'" + cdAppVersion.getVersionName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/version/cdAppVersion/?repage";
	}
	
	@RequiresPermissions("version:cdAppVersion:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdAppVersionService.delete(id);
		addMessage(redirectAttributes, "删除版本信息成功");
		return "redirect:"+Global.getAdminPath()+"/version/cdAppVersion/?repage";
	}

}
