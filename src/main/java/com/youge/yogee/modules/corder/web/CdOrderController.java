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
import com.youge.yogee.modules.corder.entity.CdOrder;
import com.youge.yogee.modules.corder.service.CdOrderService;

/**
 * 彩票订单表Controller
 * @author WeiJinChao
 * @version 2017-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/corder/cdOrder")
public class CdOrderController extends BaseController {

	@Autowired
	private CdOrderService cdOrderService;
	
	@ModelAttribute
	public CdOrder get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdOrderService.get(id);
		}else{
			return new CdOrder();
		}
	}
	
	@RequiresPermissions("corder:cdOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdOrder cdOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdOrder> page = cdOrderService.find(new Page<CdOrder>(request, response), cdOrder); 
        model.addAttribute("page", page);
		return "modules/corder/cdOrderList";
	}

	@RequiresPermissions("corder:cdOrder:view")
	@RequestMapping(value = "form")
	public String form(CdOrder cdOrder, Model model) {
		model.addAttribute("cdOrder", cdOrder);
		return "modules/corder/cdOrderForm";
	}

	@RequiresPermissions("corder:cdOrder:edit")
	@RequestMapping(value = "save")
	public String save(CdOrder cdOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdOrder)){
			return form(cdOrder, model);
		}
		cdOrderService.save(cdOrder);
		addMessage(redirectAttributes, "保存彩票订单表'" + cdOrder.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/corder/cdOrder/?repage";
	}
	
	@RequiresPermissions("corder:cdOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdOrderService.delete(id);
		addMessage(redirectAttributes, "删除彩票订单表成功");
		return "redirect:"+Global.getAdminPath()+"/corder/cdOrder/?repage";
	}

}
