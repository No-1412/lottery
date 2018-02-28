/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cwinmethod.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cwinmethod.entity.CdWinMethod;
import com.youge.yogee.modules.cwinmethod.service.CdWinMethodService;
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
 * 中奖攻略Controller
 * @author WeiJinChao
 * @version 2017-12-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cwinmethod/cdWinMethod")
public class CdWinMethodController extends BaseController {

	@Autowired
	private CdWinMethodService cdWinMethodService;
	
	@ModelAttribute
	public CdWinMethod get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdWinMethodService.get(id);
		}else{
			return new CdWinMethod();
		}
	}
	
	@RequiresPermissions("cwinmethod:cdWinMethod:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdWinMethod cdWinMethod, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdWinMethod> page = cdWinMethodService.find(new Page<CdWinMethod>(request, response), cdWinMethod); 
        model.addAttribute("page", page);
		return "modules/cwinmethod/cdWinMethodList";
	}

	@RequiresPermissions("cwinmethod:cdWinMethod:view")
	@RequestMapping(value = "form")
	public String form(CdWinMethod cdWinMethod, Model model) {
		model.addAttribute("cdWinMethod", cdWinMethod);
		return "modules/cwinmethod/cdWinMethodForm";
	}

	@RequiresPermissions("cwinmethod:cdWinMethod:edit")
	@RequestMapping(value = "save")
	public String save(CdWinMethod cdWinMethod, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdWinMethod)){
			return form(cdWinMethod, model);
		}
		cdWinMethodService.save(cdWinMethod);
		addMessage(redirectAttributes, "保存中奖攻略'" + cdWinMethod.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cwinmethod/cdWinMethod/?repage";
	}
	
	@RequiresPermissions("cwinmethod:cdWinMethod:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdWinMethodService.delete(id);
		addMessage(redirectAttributes, "删除中奖攻略成功");
		return "redirect:"+Global.getAdminPath()+"/cwinmethod/cdWinMethod/?repage";
	}

}
