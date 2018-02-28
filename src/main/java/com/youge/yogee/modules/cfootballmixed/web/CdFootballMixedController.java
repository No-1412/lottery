/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballmixed.web;

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
import com.youge.yogee.modules.cfootballmixed.entity.CdFootballMixed;
import com.youge.yogee.modules.cfootballmixed.service.CdFootballMixedService;

/**
 * 竞彩足球Controller
 * @author WeiJinChao
 * @version 2018-01-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cfootballmixed/cdFootballMixed")
public class CdFootballMixedController extends BaseController {

	@Autowired
	private CdFootballMixedService cdFootballMixedService;
	
	@ModelAttribute
	public CdFootballMixed get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFootballMixedService.get(id);
		}else{
			return new CdFootballMixed();
		}
	}
	
	@RequiresPermissions("cfootballmixed:cdFootballMixed:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFootballMixed cdFootballMixed, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFootballMixed> page = cdFootballMixedService.find(new Page<CdFootballMixed>(request, response), cdFootballMixed); 
        model.addAttribute("page", page);
		return "modules/cfootballmixed/cdFootballMixedList";
	}

	@RequiresPermissions("cfootballmixed:cdFootballMixed:view")
	@RequestMapping(value = "form")
	public String form(CdFootballMixed cdFootballMixed, Model model) {
		model.addAttribute("cdFootballMixed", cdFootballMixed);
		return "modules/cfootballmixed/cdFootballMixedForm";
	}

	@RequiresPermissions("cfootballmixed:cdFootballMixed:edit")
	@RequestMapping(value = "save")
	public String save(CdFootballMixed cdFootballMixed, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFootballMixed)){
			return form(cdFootballMixed, model);
		}
		cdFootballMixedService.save(cdFootballMixed);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/cfootballmixed/cdFootballMixed/?repage";
	}
	
	@RequiresPermissions("cfootballmixed:cdFootballMixed:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFootballMixedService.delete(id);
		addMessage(redirectAttributes, "删除竞彩足球成功");
		return "redirect:"+Global.getAdminPath()+"/cfootballmixed/cdFootballMixed/?repage";
	}

}
