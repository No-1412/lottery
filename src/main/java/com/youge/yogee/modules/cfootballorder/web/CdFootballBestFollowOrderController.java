/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballorder.web;

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
import com.youge.yogee.modules.cfootballorder.entity.CdFootballBestFollowOrder;
import com.youge.yogee.modules.cfootballorder.service.CdFootballBestFollowOrderService;

/**
 * 足球优化Controller
 * @author ZhaoYiFeng
 * @version 2018-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/cfootballorder/cdFootballBestFollowOrder")
public class CdFootballBestFollowOrderController extends BaseController {

	@Autowired
	private CdFootballBestFollowOrderService cdFootballBestFollowOrderService;
	
	@ModelAttribute
	public CdFootballBestFollowOrder get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFootballBestFollowOrderService.get(id);
		}else{
			return new CdFootballBestFollowOrder();
		}
	}
	
	@RequiresPermissions("cfootballorder:cdFootballBestFollowOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFootballBestFollowOrder cdFootballBestFollowOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFootballBestFollowOrder> page = cdFootballBestFollowOrderService.find(new Page<CdFootballBestFollowOrder>(request, response), cdFootballBestFollowOrder); 
        model.addAttribute("page", page);
		return "modules/cfootballorder/cdFootballBestFollowOrderList";
	}

	@RequiresPermissions("cfootballorder:cdFootballBestFollowOrder:view")
	@RequestMapping(value = "form")
	public String form(CdFootballBestFollowOrder cdFootballBestFollowOrder, Model model) {
		model.addAttribute("cdFootballBestFollowOrder", cdFootballBestFollowOrder);
		return "modules/cfootballorder/cdFootballBestFollowOrderForm";
	}

	@RequiresPermissions("cfootballorder:cdFootballBestFollowOrder:edit")
	@RequestMapping(value = "save")
	public String save(CdFootballBestFollowOrder cdFootballBestFollowOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFootballBestFollowOrder)){
			return form(cdFootballBestFollowOrder, model);
		}
		cdFootballBestFollowOrderService.save(cdFootballBestFollowOrder);
		addMessage(redirectAttributes, "保存足球优化'" + cdFootballBestFollowOrder.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cfootballorder/cdFootballBestFollowOrder/?repage";
	}
	
	@RequiresPermissions("cfootballorder:cdFootballBestFollowOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFootballBestFollowOrderService.delete(id);
		addMessage(redirectAttributes, "删除足球优化成功");
		return "redirect:"+Global.getAdminPath()+"/cfootballorder/cdFootballBestFollowOrder/?repage";
	}

}
