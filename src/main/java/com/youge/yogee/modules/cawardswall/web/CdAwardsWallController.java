/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cawardswall.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cawardswall.entity.CdAwardsWall;
import com.youge.yogee.modules.cawardswall.service.CdAwardsWallService;
import com.youge.yogee.modules.sys.entity.User;
import com.youge.yogee.modules.sys.utils.UserUtils;
import org.apache.commons.lang.StringEscapeUtils;
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
 * 大奖墙Controller
 * @author WeiJinChao
 * @version 2017-12-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cawardswall/cdAwardsWall")
public class CdAwardsWallController extends BaseController {

	@Autowired
	private CdAwardsWallService cdAwardsWallService;
	
	@ModelAttribute
	public CdAwardsWall get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdAwardsWallService.get(id);
		}else{
			return new CdAwardsWall();
		}
	}
	
	@RequiresPermissions("cawardswall:cdAwardsWall:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdAwardsWall cdAwardsWall, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdAwardsWall> page = cdAwardsWallService.find(new Page<CdAwardsWall>(request, response), cdAwardsWall); 
        model.addAttribute("page", page);
		return "modules/cawardswall/cdAwardsWallList";
	}

	@RequiresPermissions("cawardswall:cdAwardsWall:view")
	@RequestMapping(value = "form")
	public String form(CdAwardsWall cdAwardsWall, Model model) {
		model.addAttribute("cdAwardsWall", cdAwardsWall);
		return "modules/cawardswall/cdAwardsWallForm";
	}

	@RequiresPermissions("cawardswall:cdAwardsWall:edit")
	@RequestMapping(value = "save")
	public String save(CdAwardsWall cdAwardsWall, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdAwardsWall)){
			return form(cdAwardsWall, model);
		}
		cdAwardsWall.setContent(StringEscapeUtils.unescapeHtml(cdAwardsWall.getContent()));
		cdAwardsWallService.save(cdAwardsWall);
		addMessage(redirectAttributes, "保存大奖墙'" + cdAwardsWall.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cawardswall/cdAwardsWall/?repage";
	}
	
	@RequiresPermissions("cawardswall:cdAwardsWall:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdAwardsWallService.delete(id);
		addMessage(redirectAttributes, "删除大奖墙成功");
		return "redirect:"+Global.getAdminPath()+"/cawardswall/cdAwardsWall/?repage";
	}

}
