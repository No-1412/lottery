/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.chelpcenter.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.chelpcenter.entity.CdHelpCenter;
import com.youge.yogee.modules.chelpcenter.service.CdHelpCenterService;
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
 * 帮助中心Controller
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/chelpcenter/cdHelpCenter")
public class CdHelpCenterController extends BaseController {

	@Autowired
	private CdHelpCenterService cdHelpCenterService;
	
	@ModelAttribute
	public CdHelpCenter get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdHelpCenterService.get(id);
		}else{
			return new CdHelpCenter();
		}
	}
	
	@RequiresPermissions("chelpcenter:cdHelpCenter:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdHelpCenter cdHelpCenter, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdHelpCenter> page = cdHelpCenterService.find(new Page<CdHelpCenter>(request, response), cdHelpCenter); 
        model.addAttribute("page", page);
		return "modules/chelpcenter/cdHelpCenterList";
	}

	@RequiresPermissions("chelpcenter:cdHelpCenter:view")
	@RequestMapping(value = "form")
	public String form(CdHelpCenter cdHelpCenter, Model model) {
		model.addAttribute("cdHelpCenter", cdHelpCenter);
		return "modules/chelpcenter/cdHelpCenterForm";
	}

	@RequiresPermissions("chelpcenter:cdHelpCenter:edit")
	@RequestMapping(value = "save")
	public String save(CdHelpCenter cdHelpCenter, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdHelpCenter)){
			return form(cdHelpCenter, model);
		}
		cdHelpCenterService.save(cdHelpCenter);
		addMessage(redirectAttributes, "保存帮助中心'" + cdHelpCenter.getQuestionContent() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/chelpcenter/cdHelpCenter/?repage";
	}
	
	@RequiresPermissions("chelpcenter:cdHelpCenter:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdHelpCenterService.delete(id);
		addMessage(redirectAttributes, "删除帮助中心成功");
		return "redirect:"+Global.getAdminPath()+"/chelpcenter/cdHelpCenter/?repage";
	}

}
