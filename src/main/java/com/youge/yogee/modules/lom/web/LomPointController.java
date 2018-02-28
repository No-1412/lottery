/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.lom.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.lom.entity.LomPoint;
import com.youge.yogee.modules.lom.service.LomPointService;
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
 * 积分记录Controller
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/lom/lomPoint")
public class LomPointController extends BaseController {

	@Autowired
	private LomPointService lomPointService;
	
	@ModelAttribute
	public LomPoint get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return lomPointService.get(id);
		}else{
			return new LomPoint();
		}
	}
	
	@RequiresPermissions("lom:lomPoint:view")
	@RequestMapping(value = {"list", ""})
	public String list(LomPoint lomPoint, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<LomPoint> page = lomPointService.find(new Page<LomPoint>(request, response), lomPoint); 
        model.addAttribute("page", page);
		return "modules/lom/lomPointList";
	}

	@RequiresPermissions("lom:lomPoint:view")
	@RequestMapping(value = "form")
	public String form(LomPoint lomPoint, Model model) {
		model.addAttribute("lomPoint", lomPoint);
		return "modules/lom/lomPointForm";
	}

	@RequiresPermissions("lom:lomPoint:edit")
	@RequestMapping(value = "save")
	public String save(LomPoint lomPoint, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, lomPoint)){
			return form(lomPoint, model);
		}
		lomPointService.save(lomPoint);
		addMessage(redirectAttributes, "保存积分记录'" + lomPoint.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/lom/lomPoint/?repage";
	}
	
	@RequiresPermissions("lom:lomPoint:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		lomPointService.delete(id);
		addMessage(redirectAttributes, "删除积分记录成功");
		return "redirect:"+Global.getAdminPath()+"/lom/lomPoint/?repage";
	}

}
