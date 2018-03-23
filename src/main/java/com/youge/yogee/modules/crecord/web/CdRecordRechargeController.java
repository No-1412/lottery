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
import com.youge.yogee.modules.crecord.entity.CdRecordRecharge;
import com.youge.yogee.modules.crecord.service.CdRecordRechargeService;

/**
 * 充值记录Controller
 * @author ZhaoYiFeng
 * @version 2018-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/crecord/cdRecordRecharge")
public class CdRecordRechargeController extends BaseController {

	@Autowired
	private CdRecordRechargeService cdRecordRechargeService;
	
	@ModelAttribute
	public CdRecordRecharge get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdRecordRechargeService.get(id);
		}else{
			return new CdRecordRecharge();
		}
	}
	
	@RequiresPermissions("crecord:cdRecordRecharge:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdRecordRecharge cdRecordRecharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdRecordRecharge> page = cdRecordRechargeService.find(new Page<CdRecordRecharge>(request, response), cdRecordRecharge); 
        model.addAttribute("page", page);
		return "modules/crecord/cdRecordRechargeList";
	}

	@RequiresPermissions("crecord:cdRecordRecharge:view")
	@RequestMapping(value = "form")
	public String form(CdRecordRecharge cdRecordRecharge, Model model) {
		model.addAttribute("cdRecordRecharge", cdRecordRecharge);
		return "modules/crecord/cdRecordRechargeForm";
	}

	@RequiresPermissions("crecord:cdRecordRecharge:edit")
	@RequestMapping(value = "save")
	public String save(CdRecordRecharge cdRecordRecharge, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdRecordRecharge)){
			return form(cdRecordRecharge, model);
		}
		cdRecordRechargeService.save(cdRecordRecharge);
		addMessage(redirectAttributes, "保存充值记录'" + cdRecordRecharge.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/crecord/cdRecordRecharge/?repage";
	}
	
	@RequiresPermissions("crecord:cdRecordRecharge:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdRecordRechargeService.delete(id);
		addMessage(redirectAttributes, "删除充值记录成功");
		return "redirect:"+Global.getAdminPath()+"/crecord/cdRecordRecharge/?repage";
	}

}
