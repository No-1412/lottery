/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballorder.web;

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
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballBestFollowOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballBestFollowOrderService;

/**
 * 篮球优化Controller
 * @author ZhaoYiFeng
 * @version 2018-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/cbasketballorder/cdBasketballBestFollowOrder")
public class CdBasketballBestFollowOrderController extends BaseController {

	@Autowired
	private CdBasketballBestFollowOrderService cdBasketballBestFollowOrderService;
	
	@ModelAttribute
	public CdBasketballBestFollowOrder get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBasketballBestFollowOrderService.get(id);
		}else{
			return new CdBasketballBestFollowOrder();
		}
	}
	
	@RequiresPermissions("cbasketballorder:cdBasketballBestFollowOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBasketballBestFollowOrder cdBasketballBestFollowOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBasketballBestFollowOrder> page = cdBasketballBestFollowOrderService.find(new Page<CdBasketballBestFollowOrder>(request, response), cdBasketballBestFollowOrder); 
        model.addAttribute("page", page);
		return "modules/cbasketballorder/cdBasketballBestFollowOrderList";
	}

	@RequiresPermissions("cbasketballorder:cdBasketballBestFollowOrder:view")
	@RequestMapping(value = "form")
	public String form(CdBasketballBestFollowOrder cdBasketballBestFollowOrder, Model model) {
		model.addAttribute("cdBasketballBestFollowOrder", cdBasketballBestFollowOrder);
		return "modules/cbasketballorder/cdBasketballBestFollowOrderForm";
	}

	@RequiresPermissions("cbasketballorder:cdBasketballBestFollowOrder:edit")
	@RequestMapping(value = "save")
	public String save(CdBasketballBestFollowOrder cdBasketballBestFollowOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBasketballBestFollowOrder)){
			return form(cdBasketballBestFollowOrder, model);
		}
		cdBasketballBestFollowOrderService.save(cdBasketballBestFollowOrder);
		addMessage(redirectAttributes, "保存篮球优化'" + cdBasketballBestFollowOrder.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cbasketballorder/cdBasketballBestFollowOrder/?repage";
	}
	
	@RequiresPermissions("cbasketballorder:cdBasketballBestFollowOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBasketballBestFollowOrderService.delete(id);
		addMessage(redirectAttributes, "删除篮球优化成功");
		return "redirect:"+Global.getAdminPath()+"/cbasketballorder/cdBasketballBestFollowOrder/?repage";
	}

}
