/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballmixed.web;

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
import com.youge.yogee.modules.cbasketballmixed.entity.CdBasketballMixed;
import com.youge.yogee.modules.cbasketballmixed.service.CdBasketballMixedService;

/**
 * 竞彩蓝球Controller
 * @author WeiJinChao
 * @version 2018-01-22
 */
@Controller
@RequestMapping(value = "${adminPath}/cbasketballmixed/cdBasketballMixed")
public class CdBasketballMixedController extends BaseController {

	@Autowired
	private CdBasketballMixedService cdBasketballMixedService;
	
	@ModelAttribute
	public CdBasketballMixed get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBasketballMixedService.get(id);
		}else{
			return new CdBasketballMixed();
		}
	}
	
	@RequiresPermissions("cbasketballmixed:cdBasketballMixed:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBasketballMixed cdBasketballMixed, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBasketballMixed> page = cdBasketballMixedService.find(new Page<CdBasketballMixed>(request, response), cdBasketballMixed); 
        model.addAttribute("page", page);
		return "modules/cbasketballmixed/cdBasketballMixedList";
	}

	@RequiresPermissions("cbasketballmixed:cdBasketballMixed:view")
	@RequestMapping(value = "form")
	public String form(CdBasketballMixed cdBasketballMixed, Model model) {
		model.addAttribute("cdBasketballMixed", cdBasketballMixed);
		return "modules/cbasketballmixed/cdBasketballMixedForm";
	}

	@RequiresPermissions("cbasketballmixed:cdBasketballMixed:edit")
	@RequestMapping(value = "save")
	public String save(CdBasketballMixed cdBasketballMixed, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBasketballMixed)){
			return form(cdBasketballMixed, model);
		}
		cdBasketballMixedService.save(cdBasketballMixed);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/cbasketballmixed/cdBasketballMixed/?repage";
	}
	
	@RequiresPermissions("cbasketballmixed:cdBasketballMixed:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBasketballMixedService.delete(id);
		addMessage(redirectAttributes, "删除竞彩蓝球成功");
		return "redirect:"+Global.getAdminPath()+"/cbasketballmixed/cdBasketballMixed/?repage";
	}

}
