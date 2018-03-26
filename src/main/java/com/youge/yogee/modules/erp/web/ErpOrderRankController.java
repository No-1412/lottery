/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.erp.entity.ErpOrderRank;
import com.youge.yogee.modules.erp.service.ErpOrderRankService;
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
import java.util.Map;

/**
 * 排行Controller
 * @author RenHaipeng
 * @version 2018-03-08
 */
@Controller
@RequestMapping(value = "${adminPath}/erp/erpOrderRank")
public class ErpOrderRankController extends BaseController {

	@Autowired
	private ErpOrderRankService erpOrderRankService;
	
	@ModelAttribute
	public ErpOrderRank get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return erpOrderRankService.get(id);
		}else{
			return new ErpOrderRank();
		}
	}
	
	@RequiresPermissions("erp:erpOrderRank:view")
	@RequestMapping(value = {"list", ""})
	public String list(ErpOrderRank erpOrderRank, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<ErpOrderRank> page = erpOrderRankService.find(new Page<ErpOrderRank>(request, response), erpOrderRank);
        model.addAttribute("page", page);
		return "modules/erp/erpOrderRankList";
	}
	@RequiresPermissions("erp:erpOrderRank:view")
	@RequestMapping(value = "findRank")
	public String findRank(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();

//        Page<ErpOrderRank> page = erpOrderRankService.find(new Page<ErpOrderRank>(request, response), erpOrderRank);
		Page<Map<String,String>> page = erpOrderRankService.findRank(new Page<Map<String,String>>(request, response), paramMap);
        model.addAttribute("page", page);
		model.addAllAttributes(paramMap);
		return "modules/erp/erpOrderRankList";
	}

	@RequiresPermissions("erp:erpOrderRank:view")
	@RequestMapping(value = "form")
	public String form(ErpOrderRank erpOrderRank, Model model) {
		model.addAttribute("erpOrderRank", erpOrderRank);
		return "modules/erp/erpOrderRankForm";
	}

	@RequiresPermissions("erp:erpOrderRank:edit")
	@RequestMapping(value = "save")
	public String save(ErpOrderRank erpOrderRank, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, erpOrderRank)){
			return form(erpOrderRank, model);
		}
		erpOrderRankService.save(erpOrderRank);
		addMessage(redirectAttributes, "保存排行'" + erpOrderRank.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/erp/erpOrderRank/?repage";
	}
	
	@RequiresPermissions("erp:erpOrderRank:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		erpOrderRankService.delete(id);
		addMessage(redirectAttributes, "删除排行成功");
		return "redirect:"+ Global.getAdminPath()+"/erp/erpOrderRank/?repage";
	}

}
