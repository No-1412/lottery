/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cscoreodds.web;

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
import com.youge.yogee.modules.cscoreodds.entity.CdScoreOdds;
import com.youge.yogee.modules.cscoreodds.service.CdScoreOddsService;

/**
 * 比分赔率接口Controller
 * @author WeiJinChao
 * @version 2017-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/cscoreodds/cdScoreOdds")
public class CdScoreOddsController extends BaseController {

	@Autowired
	private CdScoreOddsService cdScoreOddsService;
	
	@ModelAttribute
	public CdScoreOdds get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdScoreOddsService.get(id);
		}else{
			return new CdScoreOdds();
		}
	}
	
	@RequiresPermissions("cscoreodds:cdScoreOdds:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdScoreOdds cdScoreOdds, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdScoreOdds> page = cdScoreOddsService.find(new Page<CdScoreOdds>(request, response), cdScoreOdds); 
        model.addAttribute("page", page);
		return "modules/cscoreodds/cdScoreOddsList";
	}

	@RequiresPermissions("cscoreodds:cdScoreOdds:view")
	@RequestMapping(value = "form")
	public String form(CdScoreOdds cdScoreOdds, Model model) {
		model.addAttribute("cdScoreOdds", cdScoreOdds);
		return "modules/cscoreodds/cdScoreOddsForm";
	}

	@RequiresPermissions("cscoreodds:cdScoreOdds:edit")
	@RequestMapping(value = "save")
	public String save(CdScoreOdds cdScoreOdds, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdScoreOdds)){
			return form(cdScoreOdds, model);
		}
		cdScoreOddsService.save(cdScoreOdds);
		addMessage(redirectAttributes, "保存比分赔率接口'" + cdScoreOdds.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cscoreodds/cdScoreOdds/?repage";
	}
	
	@RequiresPermissions("cscoreodds:cdScoreOdds:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdScoreOddsService.delete(id);
		addMessage(redirectAttributes, "删除比分赔率接口成功");
		return "redirect:"+Global.getAdminPath()+"/cscoreodds/cdScoreOdds/?repage";
	}

}
