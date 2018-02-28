/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballgoal.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfootballgoal.entity.CdFootBallGoal;
import com.youge.yogee.modules.cfootballgoal.service.CdFootBallGoalService;
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
 * 足球进球彩Controller
 * @author RenHaipeng
 * @version 2018-01-04
 */
@Controller
@RequestMapping(value = "${adminPath}/cfootballgoal/cdFootBallGoal")
public class CdFootBallGoalController extends BaseController {

	@Autowired
	private CdFootBallGoalService cdFootBallGoalService;
	
	@ModelAttribute
	public CdFootBallGoal get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFootBallGoalService.get(id);
		}else{
			return new CdFootBallGoal();
		}
	}
	
	@RequiresPermissions("cfootballgoal:cdFootBallGoal:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFootBallGoal cdFootBallGoal, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFootBallGoal> page = cdFootBallGoalService.find(new Page<CdFootBallGoal>(request, response), cdFootBallGoal);
        model.addAttribute("page", page);
		return "modules/cfootballgoal/cdFootBallGoalList";
	}

	@RequiresPermissions("cfootballgoal:cdFootBallGoal:view")
	@RequestMapping(value = "form")
	public String form(CdFootBallGoal cdFootBallGoal, Model model) {
		model.addAttribute("cdFootBallGoal", cdFootBallGoal);
		return "modules/cfootballgoal/cdFootBallGoalForm";
	}

	@RequiresPermissions("cfootballgoal:cdFootBallGoal:edit")
	@RequestMapping(value = "save")
	public String save(CdFootBallGoal cdFootBallGoal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFootBallGoal)){
			return form(cdFootBallGoal, model);
		}
		cdFootBallGoalService.save(cdFootBallGoal);
		addMessage(redirectAttributes, "保存足球进球彩'" + cdFootBallGoal.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cfootballgoal/cdFootBallGoal/?repage";
	}
	
	@RequiresPermissions("cfootballgoal:cdFootBallGoal:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFootBallGoalService.delete(id);
		addMessage(redirectAttributes, "删除足球进球彩成功");
		return "redirect:"+ Global.getAdminPath()+"/cfootballgoal/cdFootBallGoal/?repage";
	}

}
