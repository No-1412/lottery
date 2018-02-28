/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cthreeawards.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
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
import com.youge.yogee.modules.cthreeawards.entity.CdThreeOrder;
import com.youge.yogee.modules.cthreeawards.service.CdThreeOrderService;

/**
 * 排列三订单Controller
 * @author ZhaoYiFeng
 * @version 2018-02-06
 */
@Controller
@RequestMapping(value = "${adminPath}/cthreeawards/cdThreeOrder")
public class CdThreeOrderController extends BaseController {

	@Autowired
	private CdThreeOrderService cdThreeOrderService;
	@Autowired
	private CdLotteryUserService cdLotteryUserService;
	@ModelAttribute
	public CdThreeOrder get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdThreeOrderService.get(id);
		}else{
			return new CdThreeOrder();
		}
	}
	
	@RequiresPermissions("cthreeawards:cdThreeOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdThreeOrder cdThreeOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdThreeOrder> page = cdThreeOrderService.find(new Page<CdThreeOrder>(request, response), cdThreeOrder); 
        model.addAttribute("page", page);
		return "modules/cthreeawards/cdThreeOrderList";
	}

	@RequiresPermissions("cthreeawards:cdThreeOrder:view")
	@RequestMapping(value = "form")
	public String form(CdThreeOrder cdThreeOrder, Model model) {
		String buyWays=cdThreeOrder.getBuyWays();
		String buyWaysStr="";
		if("1".equals(buyWays)){
			buyWaysStr="直选";
		}
		if("2".equals(buyWays)){
			buyWaysStr="和值";
		}
		if("3".equals(buyWays)){
			buyWaysStr="组三单式";
		}
		if("4".equals(buyWays)){
			buyWaysStr="组三复式";
		}
		if("5".equals(buyWays)){
			buyWaysStr="组六单式";
		}
		if("6".equals(buyWays)){
			buyWaysStr="组六复式";
		}
		CdLotteryUser clu = cdLotteryUserService.get(cdThreeOrder.getUid());
		String uName = clu.getReality();
		model.addAttribute("uName", uName);
		model.addAttribute("buyWaysStr", buyWaysStr);
		model.addAttribute("cdThreeOrder", cdThreeOrder);
		return "modules/cthreeawards/cdThreeOrderForm";
	}

	@RequiresPermissions("cthreeawards:cdThreeOrder:edit")
	@RequestMapping(value = "save")
	public String save(CdThreeOrder cdThreeOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdThreeOrder)){
			return form(cdThreeOrder, model);
		}
		cdThreeOrderService.save(cdThreeOrder);
		addMessage(redirectAttributes, "保存排列三订单成功");
		return "redirect:"+Global.getAdminPath()+"/cthreeawards/cdThreeOrder/?repage";
	}
	
	@RequiresPermissions("cthreeawards:cdThreeOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdThreeOrderService.delete(id);
		addMessage(redirectAttributes, "删除排列三订单成功");
		return "redirect:"+Global.getAdminPath()+"/cthreeawards/cdThreeOrder/?repage";
	}

}
