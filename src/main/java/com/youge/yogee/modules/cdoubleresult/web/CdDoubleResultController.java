/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cdoubleresult.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cdoubleresult.entity.CdDoubleResult;
import com.youge.yogee.modules.cdoubleresult.service.CdDoubleResultService;
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
 * 足球半全场开奖信息Controller
 * @author RenHaipeng
 * @version 2018-01-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cdoubleresult/cdDoubleResult")
public class CdDoubleResultController extends BaseController {

	@Autowired
	private CdDoubleResultService cdDoubleResultService;
	
	@ModelAttribute
	public CdDoubleResult get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdDoubleResultService.get(id);
		}else{
			return new CdDoubleResult();
		}
	}
	
	@RequiresPermissions("cdoubleresult:cdDoubleResult:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdDoubleResult cdDoubleResult, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdDoubleResult> page = cdDoubleResultService.find(new Page<CdDoubleResult>(request, response), cdDoubleResult);
        model.addAttribute("page", page);
		return "modules/cdoubleresult/cdDoubleResultList";
	}

	@RequiresPermissions("cdoubleresult:cdDoubleResult:view")
	@RequestMapping(value = "form")
	public String form(CdDoubleResult cdDoubleResult, Model model) {
		model.addAttribute("cdDoubleResult", cdDoubleResult);
		return "modules/cdoubleresult/cdDoubleResultForm";
	}

	@RequiresPermissions("cdoubleresult:cdDoubleResult:edit")
	@RequestMapping(value = "save")
	public String save(CdDoubleResult cdDoubleResult, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdDoubleResult)){
			return form(cdDoubleResult, model);
		}
		cdDoubleResultService.save(cdDoubleResult);
		addMessage(redirectAttributes, "保存足球半全场开奖信息'" + cdDoubleResult.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/cdoubleresult/cdDoubleResult/?repage";
	}
	
	@RequiresPermissions("cdoubleresult:cdDoubleResult:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdDoubleResultService.delete(id);
		addMessage(redirectAttributes, "删除足球半全场开奖信息成功");
		return "redirect:"+ Global.getAdminPath()+"/cdoubleresult/cdDoubleResult/?repage";
	}

}
