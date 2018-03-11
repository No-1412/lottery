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
import com.youge.yogee.modules.cmagicorder.entity.CdMagicFollowOrder;
import com.youge.yogee.modules.cmagicorder.service.CdMagicFollowOrderService;

/**
 * 神单跟买Controller
 * @author ZhaoYiFeng
 * @version 2018-03-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cmagicorder/cdMagicFollowOrder")
public class CdMagicFollowOrderController extends BaseController {

	@Autowired
	private CdMagicFollowOrderService cdMagicFollowOrderService;
	
	@ModelAttribute
	public CdMagicFollowOrder get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdMagicFollowOrderService.get(id);
		}else{
			return new CdMagicFollowOrder();
		}
	}
	
	@RequiresPermissions("cmagicorder:cdMagicFollowOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdMagicFollowOrder cdMagicFollowOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdMagicFollowOrder> page = cdMagicFollowOrderService.find(new Page<CdMagicFollowOrder>(request, response), cdMagicFollowOrder); 
        model.addAttribute("page", page);
		return "modules/cmagicorder/cdMagicFollowOrderList";
	}

	@RequiresPermissions("cmagicorder:cdMagicFollowOrder:view")
	@RequestMapping(value = "form")
	public String form(CdMagicFollowOrder cdMagicFollowOrder, Model model) {
		model.addAttribute("cdMagicFollowOrder", cdMagicFollowOrder);
		return "modules/cmagicorder/cdMagicFollowOrderForm";
	}

	@RequiresPermissions("cmagicorder:cdMagicFollowOrder:edit")
	@RequestMapping(value = "save")
	public String save(CdMagicFollowOrder cdMagicFollowOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdMagicFollowOrder)){
			return form(cdMagicFollowOrder, model);
		}
		cdMagicFollowOrderService.save(cdMagicFollowOrder);
		addMessage(redirectAttributes, "保存神单跟买'" + cdMagicFollowOrder.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cmagicorder/cdMagicFollowOrder/?repage";
	}
	
	@RequiresPermissions("cmagicorder:cdMagicFollowOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdMagicFollowOrderService.delete(id);
		addMessage(redirectAttributes, "删除神单跟买成功");
		return "redirect:"+Global.getAdminPath()+"/cmagicorder/cdMagicFollowOrder/?repage";
	}

}
