/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.clottoreward.web;

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
import com.youge.yogee.modules.clottoreward.entity.CdLottoOrder;
import com.youge.yogee.modules.clottoreward.service.CdLottoOrderService;

/**
 * 大乐透订单Controller
 * @author ZhaoYiFeng
 * @version 2018-02-08
 */
@Controller
@RequestMapping(value = "${adminPath}/clottoreward/cdLottoOrder")
public class CdLottoOrderController extends BaseController {

	@Autowired
	private CdLottoOrderService cdLottoOrderService;
	@Autowired
	private CdLotteryUserService cdLotteryUserService;
	@ModelAttribute
	public CdLottoOrder get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdLottoOrderService.get(id);
		}else{
			return new CdLottoOrder();
		}
	}
	
	@RequiresPermissions("clottoreward:cdLottoOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdLottoOrder cdLottoOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdLottoOrder> page = cdLottoOrderService.find(new Page<CdLottoOrder>(request, response), cdLottoOrder); 
        model.addAttribute("page", page);
		return "modules/clottoreward/cdLottoOrderList";
	}

	@RequiresPermissions("clottoreward:cdLottoOrder:view")
	@RequestMapping(value = "form")
	public String form(CdLottoOrder cdLottoOrder, Model model) {
		String type=cdLottoOrder.getType();
		String typeStr="";
		if("1".equals(type)){
			typeStr="普通";
		}else {
			typeStr="胆拖";
		}
		CdLotteryUser clu = cdLotteryUserService.get(cdLottoOrder.getUid());
		String uName = clu.getReality();
		model.addAttribute("uName", uName);
		model.addAttribute("typeStr", typeStr);
		model.addAttribute("cdLottoOrder", cdLottoOrder);
		return "modules/clottoreward/cdLottoOrderForm";
	}

	@RequiresPermissions("clottoreward:cdLottoOrder:edit")
	@RequestMapping(value = "save")
	public String save(CdLottoOrder cdLottoOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdLottoOrder)){
			return form(cdLottoOrder, model);
		}
		cdLottoOrderService.save(cdLottoOrder);
		addMessage(redirectAttributes, "保存大乐透订单'" + cdLottoOrder.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/clottoreward/cdLottoOrder/?repage";
	}
	
	@RequiresPermissions("clottoreward:cdLottoOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdLottoOrderService.delete(id);
		addMessage(redirectAttributes, "删除大乐透订单成功");
		return "redirect:"+Global.getAdminPath()+"/clottoreward/cdLottoOrder/?repage";
	}

}
