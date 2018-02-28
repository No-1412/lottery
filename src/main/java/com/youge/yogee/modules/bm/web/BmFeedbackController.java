/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.bm.entity.BmFeedback;
import com.youge.yogee.modules.bm.service.BmFeedbackService;
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
 * 意见反馈Controller
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/bm/bmFeedback")
public class BmFeedbackController extends BaseController {

	@Autowired
	private BmFeedbackService bmFeedbackService;
	
	@ModelAttribute
	public BmFeedback get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return bmFeedbackService.get(id);
		}else{
			return new BmFeedback();
		}
	}
	
	@RequiresPermissions("bm:bmFeedback:view")
	@RequestMapping(value = {"list", ""})
	public String list(BmFeedback bmFeedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<BmFeedback> page = bmFeedbackService.find(new Page<BmFeedback>(request, response), bmFeedback); 
        model.addAttribute("page", page);
		return "modules/bm/bmFeedbackList";
	}

	@RequiresPermissions("bm:bmFeedback:view")
	@RequestMapping(value = "form")
	public String form(BmFeedback bmFeedback, Model model) {
		model.addAttribute("bmFeedback", bmFeedback);
		return "modules/bm/bmFeedbackForm";
	}

	@RequiresPermissions("bm:bmFeedback:edit")
	@RequestMapping(value = "save")
	public String save(BmFeedback bmFeedback, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bmFeedback)){
			return form(bmFeedback, model);
		}
		bmFeedbackService.save(bmFeedback);
		addMessage(redirectAttributes, "保存意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/bm/bmFeedback/?repage";
	}
	
	@RequiresPermissions("bm:bmFeedback:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		bmFeedbackService.delete(id);
		addMessage(redirectAttributes, "删除意见反馈成功");
		return "redirect:"+Global.getAdminPath()+"/bm/bmFeedback/?repage";
	}

}
