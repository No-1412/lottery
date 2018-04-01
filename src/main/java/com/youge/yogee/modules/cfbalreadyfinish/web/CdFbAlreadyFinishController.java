/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbalreadyfinish.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfbalreadyfinish.entity.CdFbAlreadyFinish;
import com.youge.yogee.modules.cfbalreadyfinish.service.CdFbAlreadyFinishService;
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
 * 足球已经完赛信息Controller
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cfbalreadyfinsh/cdFbAlreadyFinsh")
public class CdFbAlreadyFinishController extends BaseController {

	@Autowired
	private CdFbAlreadyFinishService cdFbAlreadyFinishService;
	
	@ModelAttribute
	public CdFbAlreadyFinish get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFbAlreadyFinishService.get(id);
		}else{
			return new CdFbAlreadyFinish();
		}
	}
	
	@RequiresPermissions("cfbalreadyfinish:cdFbAlreadyFinish:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFbAlreadyFinish cdFbAlreadyFinish, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFbAlreadyFinish> page = cdFbAlreadyFinishService.find(new Page<CdFbAlreadyFinish>(request, response), cdFbAlreadyFinish);
        model.addAttribute("page", page);
		return "modules/cfbalreadyfinsh/cdFbAlreadyFinshList";
	}

	@RequiresPermissions("cfbalreadyfinish:cdFbAlreadyFinish:view")
	@RequestMapping(value = "form")
	public String form(CdFbAlreadyFinish cdFbAlreadyFinish, Model model) {
		model.addAttribute("cdFbAlreadyFinsh", cdFbAlreadyFinish);
		return "modules/cfbalreadyfinsh/cdFbAlreadyFinshForm";
	}

	@RequiresPermissions("cfbalreadyfinish:cdFbAlreadyFinish:edit")
	@RequestMapping(value = "save")
	public String save(CdFbAlreadyFinish cdFbAlreadyFinish, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFbAlreadyFinish)){
			return form(cdFbAlreadyFinish, model);
		}
		cdFbAlreadyFinishService.save(cdFbAlreadyFinish);
		addMessage(redirectAttributes, "保存足球已经完赛信息'" + cdFbAlreadyFinish.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cfbalreadyfinish/cdFbAlreadyFinish/?repage";
	}
	
	@RequiresPermissions("cfbalreadyfinish:cdFbAlreadyFinish:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFbAlreadyFinishService.delete(id);
		addMessage(redirectAttributes, "删除足球已经完赛信息成功");
		return "redirect:"+ Global.getAdminPath()+"/cfbalreadyfinish/cdFbAlreadyFinish/?repage";
	}

}
