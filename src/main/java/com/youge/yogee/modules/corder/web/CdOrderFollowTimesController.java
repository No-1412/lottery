/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.corder.web;

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
import com.youge.yogee.modules.corder.entity.CdOrderFollowTimes;
import com.youge.yogee.modules.corder.service.CdOrderFollowTimesService;

/**
 * 竞彩篮球订单Controller
 * @author ZhaoYiFeng
 * @version 2018-03-04
 */
@Controller
@RequestMapping(value = "${adminPath}/corder/cdOrderFollowTimes")
public class CdOrderFollowTimesController extends BaseController {

	@Autowired
	private CdOrderFollowTimesService cdOrderFollowTimesService;
	
	@ModelAttribute
	public CdOrderFollowTimes get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdOrderFollowTimesService.get(id);
		}else{
			return new CdOrderFollowTimes();
		}
	}
	
	@RequiresPermissions("corder:cdOrderFollowTimes:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdOrderFollowTimes cdOrderFollowTimes, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdOrderFollowTimes> page = cdOrderFollowTimesService.find(new Page<CdOrderFollowTimes>(request, response), cdOrderFollowTimes); 
        model.addAttribute("page", page);
		return "modules/corder/cdOrderFollowTimesList";
	}

	@RequiresPermissions("corder:cdOrderFollowTimes:view")
	@RequestMapping(value = "form")
	public String form(CdOrderFollowTimes cdOrderFollowTimes, Model model) {
		model.addAttribute("cdOrderFollowTimes", cdOrderFollowTimes);
		return "modules/corder/cdOrderFollowTimesForm";
	}

	@RequiresPermissions("corder:cdOrderFollowTimes:edit")
	@RequestMapping(value = "save")
	public String save(CdOrderFollowTimes cdOrderFollowTimes, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdOrderFollowTimes)){
			return form(cdOrderFollowTimes, model);
		}
		cdOrderFollowTimesService.save(cdOrderFollowTimes);
		addMessage(redirectAttributes, "保存竞彩篮球订单'" + cdOrderFollowTimes.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/corder/cdOrderFollowTimes/?repage";
	}
	
	@RequiresPermissions("corder:cdOrderFollowTimes:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdOrderFollowTimesService.delete(id);
		addMessage(redirectAttributes, "删除竞彩篮球订单成功");
		return "redirect:"+Global.getAdminPath()+"/corder/cdOrderFollowTimes/?repage";
	}

}
