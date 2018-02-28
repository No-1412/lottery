/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballgoalresult.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfootballgoalresult.entity.CdFootballGoalResult;
import com.youge.yogee.modules.cfootballgoalresult.service.CdFootballGoalResultService;
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
 * 足球进球彩开奖信息Controller
 * @author RenHaipeng
 * @version 2018-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/cfootballgoalresult/cdFootballGoalResult")
public class CdFootballGoalResultController extends BaseController {

	@Autowired
	private CdFootballGoalResultService cdFootballGoalResultService;
	
	@ModelAttribute
	public CdFootballGoalResult get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFootballGoalResultService.get(id);
		}else{
			return new CdFootballGoalResult();
		}
	}
	
	@RequiresPermissions("cfootballgoalresult:cdFootballGoalResult:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFootballGoalResult cdFootballGoalResult, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFootballGoalResult> page = cdFootballGoalResultService.find(new Page<CdFootballGoalResult>(request, response), cdFootballGoalResult);
        model.addAttribute("page", page);
		return "modules/cfootballgoalresult/cdFootballGoalResultList";
	}

	@RequiresPermissions("cfootballgoalresult:cdFootballGoalResult:view")
	@RequestMapping(value = "form")
	public String form(CdFootballGoalResult cdFootballGoalResult, Model model) {
		model.addAttribute("cdFootballGoalResult", cdFootballGoalResult);
		return "modules/cfootballgoalresult/cdFootballGoalResultForm";
	}

	@RequiresPermissions("cfootballgoalresult:cdFootballGoalResult:edit")
	@RequestMapping(value = "save")
	public String save(CdFootballGoalResult cdFootballGoalResult, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFootballGoalResult)){
			return form(cdFootballGoalResult, model);
		}
		cdFootballGoalResultService.save(cdFootballGoalResult);
		addMessage(redirectAttributes, "保存足球进球彩开奖信息'" + cdFootballGoalResult.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cfootballgoalresult/cdFootballGoalResult/?repage";
	}
	
	@RequiresPermissions("cfootballgoalresult:cdFootballGoalResult:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFootballGoalResultService.delete(id);
		addMessage(redirectAttributes, "删除足球进球彩开奖信息成功");
		return "redirect:"+ Global.getAdminPath()+"/cfootballgoalresult/cdFootballGoalResult/?repage";
	}

}
