/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.lom.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.lom.entity.LomCollect;
import com.youge.yogee.modules.lom.service.LomCollectService;
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
 * 收藏记录Controller
 * @author RenHaipeng
 * @version 2017-03-06
 */
@Controller
@RequestMapping(value = "${adminPath}/lom/lomCollect")
public class LomCollectController extends BaseController {

	@Autowired
	private LomCollectService lomCollectService;
	
	@ModelAttribute
	public LomCollect get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return lomCollectService.get(id);
		}else{
			return new LomCollect();
		}
	}
	
	@RequiresPermissions("lom:lomCollect:view")
	@RequestMapping(value = {"list", ""})
	public String list(LomCollect lomCollect, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<LomCollect> page = lomCollectService.find(new Page<LomCollect>(request, response), lomCollect); 
        model.addAttribute("page", page);
		return "modules/lom/lomCollectList";
	}

	@RequiresPermissions("lom:lomCollect:view")
	@RequestMapping(value = "form")
	public String form(LomCollect lomCollect, Model model) {
		model.addAttribute("lomCollect", lomCollect);
		return "modules/lom/lomCollectForm";
	}

	@RequiresPermissions("lom:lomCollect:edit")
	@RequestMapping(value = "save")
	public String save(LomCollect lomCollect, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, lomCollect)){
			return form(lomCollect, model);
		}
		lomCollectService.save(lomCollect);
		addMessage(redirectAttributes, "保存收藏记录成功");
		return "redirect:"+Global.getAdminPath()+"/lom/lomCollect/?repage";
	}
	
	@RequiresPermissions("lom:lomCollect:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		lomCollectService.delete(id);
		addMessage(redirectAttributes, "删除收藏记录成功");
		return "redirect:"+Global.getAdminPath()+"/lom/lomCollect/?repage";
	}

}
