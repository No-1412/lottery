/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cchoosenine.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNine;
import com.youge.yogee.modules.cchoosenine.service.CdChooseNineService;
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
 * 任选九开奖Controller
 * @author RenHaipeng
 * @version 2018-01-05
 */
@Controller
@RequestMapping(value = "${adminPath}/cchoosenine/cdChooseNine")
public class CdChooseNineController extends BaseController {

	@Autowired
	private CdChooseNineService cdChooseNineService;
	
	@ModelAttribute
	public CdChooseNine get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdChooseNineService.get(id);
		}else{
			return new CdChooseNine();
		}
	}
	
	@RequiresPermissions("cchoosenine:cdChooseNine:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdChooseNine cdChooseNine, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdChooseNine> page = cdChooseNineService.find(new Page<CdChooseNine>(request, response), cdChooseNine);
        model.addAttribute("page", page);
		return "modules/cchoosenine/cdChooseNineList";
	}

	@RequiresPermissions("cchoosenine:cdChooseNine:view")
	@RequestMapping(value = "form")
	public String form(CdChooseNine cdChooseNine, Model model) {
		model.addAttribute("cdChooseNine", cdChooseNine);
		return "modules/cchoosenine/cdChooseNineForm";
	}

	@RequiresPermissions("cchoosenine:cdChooseNine:edit")
	@RequestMapping(value = "save")
	public String save(CdChooseNine cdChooseNine, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdChooseNine)){
			return form(cdChooseNine, model);
		}
		cdChooseNineService.save(cdChooseNine);
		addMessage(redirectAttributes, "保存任选九开奖'" + cdChooseNine.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cchoosenine/cdChooseNine/?repage";
	}
	
	@RequiresPermissions("cchoosenine:cdChooseNine:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdChooseNineService.delete(id);
		addMessage(redirectAttributes, "删除任选九开奖成功");
		return "redirect:"+ Global.getAdminPath()+"/cchoosenine/cdChooseNine/?repage";
	}

}
