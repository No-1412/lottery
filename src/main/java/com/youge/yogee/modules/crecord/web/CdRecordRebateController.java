/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.crecord.web;

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
import com.youge.yogee.modules.crecord.entity.CdRecordRebate;
import com.youge.yogee.modules.crecord.service.CdRecordRebateService;

/**
 * 返利记录Controller
 * @author ZhaoYiFeng
 * @version 2018-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/crecord/cdRecordRebate")
public class CdRecordRebateController extends BaseController {

	@Autowired
	private CdRecordRebateService cdRecordRebateService;
	
	@ModelAttribute
	public CdRecordRebate get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdRecordRebateService.get(id);
		}else{
			return new CdRecordRebate();
		}
	}
	
	@RequiresPermissions("crecord:cdRecordRebate:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdRecordRebate cdRecordRebate, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdRecordRebate> page = cdRecordRebateService.find(new Page<CdRecordRebate>(request, response), cdRecordRebate); 
        model.addAttribute("page", page);
		return "modules/crecord/cdRecordRebateList";
	}

	@RequiresPermissions("crecord:cdRecordRebate:view")
	@RequestMapping(value = "form")
	public String form(CdRecordRebate cdRecordRebate, Model model) {
		model.addAttribute("cdRecordRebate", cdRecordRebate);
		return "modules/crecord/cdRecordRebateForm";
	}

	@RequiresPermissions("crecord:cdRecordRebate:edit")
	@RequestMapping(value = "save")
	public String save(CdRecordRebate cdRecordRebate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdRecordRebate)){
			return form(cdRecordRebate, model);
		}
		cdRecordRebateService.save(cdRecordRebate);
		addMessage(redirectAttributes, "保存返利记录'" + cdRecordRebate.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/crecord/cdRecordRebate/?repage";
	}
	
	@RequiresPermissions("crecord:cdRecordRebate:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdRecordRebateService.delete(id);
		addMessage(redirectAttributes, "删除返利记录成功");
		return "redirect:"+Global.getAdminPath()+"/crecord/cdRecordRebate/?repage";
	}

}
