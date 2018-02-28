/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cproductproposal.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cproductproposal.entity.CdProductProposal;
import com.youge.yogee.modules.cproductproposal.service.CdProductProposalService;
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
 * 产品建议Controller
 * @author WeiJinChao
 * @version 2017-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/cproductproposal/cdProductProposal")
public class CdProductProposalController extends BaseController {

	@Autowired
	private CdProductProposalService cdProductProposalService;
	
	@ModelAttribute
	public CdProductProposal get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdProductProposalService.get(id);
		}else{
			return new CdProductProposal();
		}
	}
	
	@RequiresPermissions("cproductproposal:cdProductProposal:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdProductProposal cdProductProposal, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdProductProposal> page = cdProductProposalService.find(new Page<CdProductProposal>(request, response), cdProductProposal); 
        model.addAttribute("page", page);
		return "modules/cproductproposal/cdProductProposalList";
	}

	@RequiresPermissions("cproductproposal:cdProductProposal:view")
	@RequestMapping(value = "form")
	public String form(CdProductProposal cdProductProposal, Model model) {
		model.addAttribute("cdProductProposal", cdProductProposal);
		return "modules/cproductproposal/cdProductProposalForm";
	}

	@RequiresPermissions("cproductproposal:cdProductProposal:edit")
	@RequestMapping(value = "save")
	public String save(CdProductProposal cdProductProposal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdProductProposal)){
			return form(cdProductProposal, model);
		}
		cdProductProposalService.save(cdProductProposal);
		addMessage(redirectAttributes, "保存产品建议'" + cdProductProposal.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cproductproposal/cdProductProposal/?repage";
	}
	
	@RequiresPermissions("cproductproposal:cdProductProposal:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdProductProposalService.delete(id);
		addMessage(redirectAttributes, "删除产品建议成功");
		return "redirect:"+Global.getAdminPath()+"/cproductproposal/cdProductProposal/?repage";
	}

}
