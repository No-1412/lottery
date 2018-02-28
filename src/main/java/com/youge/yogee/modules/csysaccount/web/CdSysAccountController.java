/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csysaccount.web;

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
import com.youge.yogee.modules.csysaccount.entity.CdSysAccount;
import com.youge.yogee.modules.csysaccount.service.CdSysAccountService;

/**
 * 平台系统账户Controller
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/csysaccount/cdSysAccount")
public class CdSysAccountController extends BaseController {

	@Autowired
	private CdSysAccountService cdSysAccountService;
	
	@ModelAttribute
	public CdSysAccount get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdSysAccountService.get(id);
		}else{
			return new CdSysAccount();
		}
	}
	
	@RequiresPermissions("csysaccount:cdSysAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdSysAccount cdSysAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdSysAccount> page = cdSysAccountService.find(new Page<CdSysAccount>(request, response), cdSysAccount); 
        model.addAttribute("page", page);
		return "modules/csysaccount/cdSysAccountList";
	}

	@RequiresPermissions("csysaccount:cdSysAccount:view")
	@RequestMapping(value = "form")
	public String form(CdSysAccount cdSysAccount, Model model) {
		model.addAttribute("cdSysAccount", cdSysAccount);
		return "modules/csysaccount/cdSysAccountForm";
	}

	@RequiresPermissions("csysaccount:cdSysAccount:edit")
	@RequestMapping(value = "save")
	public String save(CdSysAccount cdSysAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdSysAccount)){
			return form(cdSysAccount, model);
		}
		cdSysAccountService.save(cdSysAccount);
		addMessage(redirectAttributes, "保存平台系统账户'" + cdSysAccount.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/csysaccount/cdSysAccount/?repage";
	}
	
	@RequiresPermissions("csysaccount:cdSysAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdSysAccountService.delete(id);
		addMessage(redirectAttributes, "删除平台系统账户成功");
		return "redirect:"+Global.getAdminPath()+"/csysaccount/cdSysAccount/?repage";
	}

}
