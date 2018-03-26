/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.erp.entity.ErpRechargeLog;
import com.youge.yogee.modules.erp.service.ErpRechargeLogService;
import com.youge.yogee.modules.sys.entity.User;
import com.youge.yogee.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 销售后台充值记录Controller
 * @author Lxy
 * @version 2018-03-22
 */
@Controller
@RequestMapping(value = "${adminPath}/erp/erpRechargeLog")
public class ErpRechargeLogController extends BaseController {

	@Autowired
	private ErpRechargeLogService erpRechargeLogService;
	
	@ModelAttribute
	public ErpRechargeLog get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return erpRechargeLogService.get(id);
		}else{
			return new ErpRechargeLog();
		}
	}
	
	@RequiresPermissions("erp:erpRechargeLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(ErpRechargeLog erpRechargeLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<ErpRechargeLog> page = erpRechargeLogService.find(new Page<ErpRechargeLog>(request, response), erpRechargeLog);
        model.addAttribute("page", page);
		return "modules/erp/erpRechargeLogList";
	}

	@RequiresPermissions("erp:erpRechargeLog:view")
	@RequestMapping(value = "form")
	public String form(ErpRechargeLog erpRechargeLog, Model model) {
		model.addAttribute("erpRechargeLog", erpRechargeLog);
		return "modules/erp/erpRechargeLogForm";
	}

	@RequiresPermissions("erp:erpRechargeLog:edit")
	@RequestMapping(value = "save")
	public String save(ErpRechargeLog erpRechargeLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, erpRechargeLog)){
			return form(erpRechargeLog, model);
		}
		erpRechargeLogService.save(erpRechargeLog);
		addMessage(redirectAttributes, "保存销售后台充值记录成功");
		return "redirect:"+ Global.getAdminPath()+"/erp/erpRechargeLog/?repage";
	}
	
	@RequiresPermissions("erp:erpRechargeLog:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		erpRechargeLogService.delete(id);
		addMessage(redirectAttributes, "删除销售后台充值记录成功");
		return "redirect:"+ Global.getAdminPath()+"/erp/erpRechargeLog/?repage";
	}



}
