/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cmagicorder.web;

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
import com.youge.yogee.modules.cmagicorder.entity.CdMagicOrder;
import com.youge.yogee.modules.cmagicorder.service.CdMagicOrderService;

/**
 * 神单订单Controller
 * @author ZhaoYiFeng
 * @version 2018-03-05
 */
@Controller
@RequestMapping(value = "${adminPath}/cmagicorder/cdMagicOrder")
public class CdMagicOrderController extends BaseController {

	@Autowired
	private CdMagicOrderService cdMagicOrderService;
	
	@ModelAttribute
	public CdMagicOrder get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdMagicOrderService.get(id);
		}else{
			return new CdMagicOrder();
		}
	}
	
	@RequiresPermissions("cmagicorder:cdMagicOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdMagicOrder cdMagicOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdMagicOrder> page = cdMagicOrderService.find(new Page<CdMagicOrder>(request, response), cdMagicOrder); 
        model.addAttribute("page", page);
		return "modules/cmagicorder/cdMagicOrderList";
	}

	@RequiresPermissions("cmagicorder:cdMagicOrder:view")
	@RequestMapping(value = "form")
	public String form(CdMagicOrder cdMagicOrder, Model model) {
		model.addAttribute("cdMagicOrder", cdMagicOrder);
		return "modules/cmagicorder/cdMagicOrderForm";
	}

	@RequiresPermissions("cmagicorder:cdMagicOrder:edit")
	@RequestMapping(value = "save")
	public String save(CdMagicOrder cdMagicOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdMagicOrder)){
			return form(cdMagicOrder, model);
		}
		cdMagicOrderService.save(cdMagicOrder);
		addMessage(redirectAttributes, "保存神单订单成功");
		return "redirect:"+Global.getAdminPath()+"/cmagicorder/cdMagicOrder/?repage";
	}
	
	@RequiresPermissions("cmagicorder:cdMagicOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdMagicOrderService.delete(id);
		addMessage(redirectAttributes, "删除神单订单成功");
		return "redirect:"+Global.getAdminPath()+"/cmagicorder/cdMagicOrder/?repage";
	}

}
