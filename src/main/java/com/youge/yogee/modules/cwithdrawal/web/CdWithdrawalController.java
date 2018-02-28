/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cwithdrawal.web;

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
import com.youge.yogee.modules.cwithdrawal.entity.CdWithdrawal;
import com.youge.yogee.modules.cwithdrawal.service.CdWithdrawalService;

/**
 * 提现记录Controller
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/cwithdrawal/cdWithdrawal")
public class CdWithdrawalController extends BaseController {

	@Autowired
	private CdWithdrawalService cdWithdrawalService;
	
	@ModelAttribute
	public CdWithdrawal get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdWithdrawalService.get(id);
		}else{
			return new CdWithdrawal();
		}
	}
	
	@RequiresPermissions("cwithdrawal:cdWithdrawal:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdWithdrawal cdWithdrawal, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdWithdrawal> page = cdWithdrawalService.find(new Page<CdWithdrawal>(request, response), cdWithdrawal); 
        model.addAttribute("page", page);
		return "modules/cwithdrawal/cdWithdrawalList";
	}

	@RequiresPermissions("cwithdrawal:cdWithdrawal:view")
	@RequestMapping(value = "form")
	public String form(CdWithdrawal cdWithdrawal, Model model) {
		model.addAttribute("cdWithdrawal", cdWithdrawal);
		return "modules/cwithdrawal/cdWithdrawalForm";
	}

	@RequiresPermissions("cwithdrawal:cdWithdrawal:edit")
	@RequestMapping(value = "save")
	public String save(CdWithdrawal cdWithdrawal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdWithdrawal)){
			return form(cdWithdrawal, model);
		}
		cdWithdrawalService.save(cdWithdrawal);
		addMessage(redirectAttributes, "保存提现记录'" + cdWithdrawal.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cwithdrawal/cdWithdrawal/?repage";
	}
	
	@RequiresPermissions("cwithdrawal:cdWithdrawal:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdWithdrawalService.delete(id);
		addMessage(redirectAttributes, "删除提现记录成功");
		return "redirect:"+Global.getAdminPath()+"/cwithdrawal/cdWithdrawal/?repage";
	}

}
