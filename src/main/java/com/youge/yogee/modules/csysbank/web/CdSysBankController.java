/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csysbank.web;

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
import com.youge.yogee.modules.csysbank.entity.CdSysBank;
import com.youge.yogee.modules.csysbank.service.CdSysBankService;

/**
 * 系统银行卡Controller
 * @author WeiJinChao
 * @version 2017-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/csysbank/cdSysBank")
public class CdSysBankController extends BaseController {

	@Autowired
	private CdSysBankService cdSysBankService;
	
	@ModelAttribute
	public CdSysBank get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdSysBankService.get(id);
		}else{
			return new CdSysBank();
		}
	}
	
	@RequiresPermissions("csysbank:cdSysBank:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdSysBank cdSysBank, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdSysBank> page = cdSysBankService.find(new Page<CdSysBank>(request, response), cdSysBank); 
        model.addAttribute("page", page);
		return "modules/csysbank/cdSysBankList";
	}

	@RequiresPermissions("csysbank:cdSysBank:view")
	@RequestMapping(value = "form")
	public String form(CdSysBank cdSysBank, Model model) {
		model.addAttribute("cdSysBank", cdSysBank);
		return "modules/csysbank/cdSysBankForm";
	}

	@RequiresPermissions("csysbank:cdSysBank:edit")
	@RequestMapping(value = "save")
	public String save(CdSysBank cdSysBank, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdSysBank)){
			return form(cdSysBank, model);
		}
		cdSysBankService.save(cdSysBank);
		addMessage(redirectAttributes, "保存系统银行卡'" + cdSysBank.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/csysbank/cdSysBank/?repage";
	}
	
	@RequiresPermissions("csysbank:cdSysBank:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdSysBankService.delete(id);
		addMessage(redirectAttributes, "删除系统银行卡成功");
		return "redirect:"+Global.getAdminPath()+"/csysbank/cdSysBank/?repage";
	}

}
