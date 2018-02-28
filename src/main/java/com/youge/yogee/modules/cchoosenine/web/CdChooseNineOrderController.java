/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cchoosenine.web;

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
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNineOrder;
import com.youge.yogee.modules.cchoosenine.service.CdChooseNineOrderService;

import java.util.ArrayList;
import java.util.List;

/**
 * 任选九订单Controller
 * @author ZhaoYiFeng
 * @version 2018-02-22
 */
@Controller
@RequestMapping(value = "${adminPath}/cchoosenine/cdChooseNineOrder")
public class CdChooseNineOrderController extends BaseController {

	@Autowired
	private CdChooseNineOrderService cdChooseNineOrderService;
	@Autowired
	private CdLotteryUserService cdLotteryUserService;
	
	@ModelAttribute
	public CdChooseNineOrder get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdChooseNineOrderService.get(id);
		}else{
			return new CdChooseNineOrder();
		}
	}
	
	@RequiresPermissions("cchoosenine:cdChooseNineOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdChooseNineOrder cdChooseNineOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdChooseNineOrder> page = cdChooseNineOrderService.find(new Page<CdChooseNineOrder>(request, response), cdChooseNineOrder); 
        List<CdChooseNineOrder> list=page.getList();
		List<CdChooseNineOrder> newList=new ArrayList<>();
		for(CdChooseNineOrder c:list){
			CdChooseNineOrder ccn=new CdChooseNineOrder();
			ccn.setId(c.getId());
			ccn.setOrderNumber(c.getOrderNumber());
			ccn.setWeekday(c.getWeekday());
			ccn.setAcount(c.getAcount());
			ccn.setPrice(c.getPrice());
			CdLotteryUser clu=cdLotteryUserService.get(c.getUid());
			ccn.setUid(clu.getReality());
			ccn.setCreateDate(c.getCreateDate());
			ccn.setStatus(c.getStatus());
			newList.add(ccn);
		}
		page.setList(newList);
        model.addAttribute("page", page);
		return "modules/cchoosenine/cdChooseNineOrderList";
	}

	@RequiresPermissions("cchoosenine:cdChooseNineOrder:view")
	@RequestMapping(value = "form")
	public String form(CdChooseNineOrder cdChooseNineOrder, Model model) {
		List<String> list=new ArrayList<>();
		String uName="";
		if(cdChooseNineOrder!=null){
			String detail=cdChooseNineOrder.getOrderDetail();
			if(StringUtils.isNotEmpty(detail)){
				String detailStr[] = detail.split("\\|");
				for(String s:detailStr){
					list.add(s);
				}
			}
			CdLotteryUser clu=cdLotteryUserService.get(cdChooseNineOrder.getUid());
			uName=clu.getReality();
		}


		model.addAttribute("uName", uName);
		model.addAttribute("cdChooseNineOrder", cdChooseNineOrder);
		model.addAttribute("list", list);
		return "modules/cchoosenine/cdChooseNineOrderForm";
	}

	@RequiresPermissions("cchoosenine:cdChooseNineOrder:edit")
	@RequestMapping(value = "save")
	public String save(CdChooseNineOrder cdChooseNineOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdChooseNineOrder)){
			return form(cdChooseNineOrder, model);
		}
		cdChooseNineOrderService.save(cdChooseNineOrder);
		addMessage(redirectAttributes, "保存任选九订单'" + cdChooseNineOrder.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cchoosenine/cdChooseNineOrder/?repage";
	}
	
	@RequiresPermissions("cchoosenine:cdChooseNineOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdChooseNineOrderService.delete(id);
		addMessage(redirectAttributes, "删除任选九订单成功");
		return "redirect:"+Global.getAdminPath()+"/cchoosenine/cdChooseNineOrder/?repage";
	}

}
