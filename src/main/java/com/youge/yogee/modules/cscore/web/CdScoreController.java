/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cscore.web;

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
import com.youge.yogee.modules.cscore.entity.CdScore;
import com.youge.yogee.modules.cscore.service.CdScoreService;

/**
 * 用户积分信息Controller
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/cscore/cdScore")
public class CdScoreController extends BaseController {

	@Autowired
	private CdScoreService cdScoreService;
	
	@ModelAttribute
	public CdScore get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdScoreService.get(id);
		}else{
			return new CdScore();
		}
	}
	
	@RequiresPermissions("cscore:cdScore:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdScore cdScore, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdScore> page = cdScoreService.find(new Page<CdScore>(request, response), cdScore); 
        model.addAttribute("page", page);
		return "modules/cscore/cdScoreList";
	}

	@RequiresPermissions("cscore:cdScore:view")
	@RequestMapping(value = "form")
	public String form(CdScore cdScore, Model model) {
		model.addAttribute("cdScore", cdScore);
		return "modules/cscore/cdScoreForm";
	}

	@RequiresPermissions("cscore:cdScore:edit")
	@RequestMapping(value = "save")
	public String save(CdScore cdScore, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdScore)){
			return form(cdScore, model);
		}
		cdScoreService.save(cdScore);
		addMessage(redirectAttributes, "保存用户积分信息'" + cdScore.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cscore/cdScore/?repage";
	}
	
	@RequiresPermissions("cscore:cdScore:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdScoreService.delete(id);
		addMessage(redirectAttributes, "删除用户积分信息成功");
		return "redirect:"+Global.getAdminPath()+"/cscore/cdScore/?repage";
	}

}
