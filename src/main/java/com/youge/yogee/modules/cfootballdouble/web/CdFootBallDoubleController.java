/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballdouble.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cfootballdouble.entity.CdFootBallDouble;
import com.youge.yogee.modules.cfootballdouble.service.CdFootBallDoubleService;
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
 * 足球半全场Controller
 * @author RenHaipeng
 * @version 2018-01-04
 */
@Controller
@RequestMapping(value = "${adminPath}/cfootballdouble/cdFootBallDouble")
public class CdFootBallDoubleController extends BaseController {

	@Autowired
	private CdFootBallDoubleService cdFootBallDoubleService;
	
	@ModelAttribute
	public CdFootBallDouble get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFootBallDoubleService.get(id);
		}else{
			return new CdFootBallDouble();
		}
	}
	
	@RequiresPermissions("cfootballdouble:cdFootBallDouble:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFootBallDouble cdFootBallDouble, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFootBallDouble> page = cdFootBallDoubleService.find(new Page<CdFootBallDouble>(request, response), cdFootBallDouble);
        model.addAttribute("page", page);
		return "modules/cfootballdouble/cdFootBallDoubleList";
	}

	@RequiresPermissions("cfootballdouble:cdFootBallDouble:view")
	@RequestMapping(value = "form")
	public String form(CdFootBallDouble cdFootBallDouble, Model model) {
		model.addAttribute("cdFootBallDouble", cdFootBallDouble);
		return "modules/cfootballdouble/cdFootBallDoubleForm";
	}

	@RequiresPermissions("cfootballdouble:cdFootBallDouble:edit")
	@RequestMapping(value = "save")
	public String save(CdFootBallDouble cdFootBallDouble, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFootBallDouble)){
			return form(cdFootBallDouble, model);
		}
		cdFootBallDoubleService.save(cdFootBallDouble);
		addMessage(redirectAttributes, "保存足球半全场'" + cdFootBallDouble.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cfootballdouble/cdFootBallDouble/?repage";
	}
	
	@RequiresPermissions("cfootballdouble:cdFootBallDouble:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFootBallDoubleService.delete(id);
		addMessage(redirectAttributes, "删除足球半全场成功");
		return "redirect:"+ Global.getAdminPath()+"/cfootballdouble/cdFootBallDouble/?repage";
	}

}
