/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.lom.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.lom.entity.LomCode;
import com.youge.yogee.modules.lom.service.LomCodeService;
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
 * 验证码记录Controller
 * @author RenHaipeng
 * @version 2017-03-10
 */
@Controller
@RequestMapping(value = "${adminPath}/lom/lomCode")
public class LomCodeController extends BaseController {

	@Autowired
	private LomCodeService lomCodeService;
	
	@ModelAttribute
	public LomCode get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return lomCodeService.get(id);
		}else{
			return new LomCode();
		}
	}
	
	@RequiresPermissions("lom:lomCode:view")
	@RequestMapping(value = {"list", ""})
	public String list(LomCode lomCode, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<LomCode> page = lomCodeService.find(new Page<LomCode>(request, response), lomCode); 
        model.addAttribute("page", page);
		return "modules/lom/lomCodeList";
	}

	@RequiresPermissions("lom:lomCode:view")
	@RequestMapping(value = "form")
	public String form(LomCode lomCode, Model model) {
		model.addAttribute("lomCode", lomCode);
		return "modules/lom/lomCodeForm";
	}

	@RequiresPermissions("lom:lomCode:edit")
	@RequestMapping(value = "save")
	public String save(LomCode lomCode, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, lomCode)){
			return form(lomCode, model);
		}
		lomCodeService.save(lomCode);
		addMessage(redirectAttributes, "保存验证码记录成功");
		return "redirect:"+Global.getAdminPath()+"/lom/lomCode/?repage";
	}
	
	@RequiresPermissions("lom:lomCode:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		lomCodeService.delete(id);
		addMessage(redirectAttributes, "删除验证码记录成功");
		return "redirect:"+Global.getAdminPath()+"/lom/lomCode/?repage";
	}

}
