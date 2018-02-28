/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.crecharge.web;

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
import com.youge.yogee.modules.crecharge.entity.CdRecharge;
import com.youge.yogee.modules.crecharge.service.CdRechargeService;

/**
 * 充值记录Controller
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/crecharge/cdRecharge")
public class CdRechargeController extends BaseController {

	@Autowired
	private CdRechargeService cdRechargeService;
	
	@ModelAttribute
	public CdRecharge get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdRechargeService.get(id);
		}else{
			return new CdRecharge();
		}
	}
	
	@RequiresPermissions("crecharge:cdRecharge:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdRecharge cdRecharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdRecharge> page = cdRechargeService.find(new Page<CdRecharge>(request, response), cdRecharge); 
        model.addAttribute("page", page);
		return "modules/crecharge/cdRechargeList";
	}

	@RequiresPermissions("crecharge:cdRecharge:view")
	@RequestMapping(value = "form")
	public String form(CdRecharge cdRecharge, Model model) {
		model.addAttribute("cdRecharge", cdRecharge);
		return "modules/crecharge/cdRechargeForm";
	}

	@RequiresPermissions("crecharge:cdRecharge:edit")
	@RequestMapping(value = "save")
	public String save(CdRecharge cdRecharge, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdRecharge)){
			return form(cdRecharge, model);
		}
		cdRechargeService.save(cdRecharge);
		addMessage(redirectAttributes, "保存充值记录'" + cdRecharge.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/crecharge/cdRecharge/?repage";
	}
	
	@RequiresPermissions("crecharge:cdRecharge:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdRechargeService.delete(id);
		addMessage(redirectAttributes, "删除充值记录成功");
		return "redirect:"+Global.getAdminPath()+"/crecharge/cdRecharge/?repage";
	}

}
