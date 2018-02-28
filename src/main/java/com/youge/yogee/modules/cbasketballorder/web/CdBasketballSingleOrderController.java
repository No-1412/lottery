/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballorder.web;

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
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.service.CdBasketballSingleOrderService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 竞彩篮球订单Controller
 * @author ZhaoYiFeng
 * @version 2018-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/cbasketballorder/cdBasketballSingleOrder")
public class CdBasketballSingleOrderController extends BaseController {

	@Autowired
	private CdBasketballSingleOrderService cdBasketballSingleOrderService;
	@Autowired
	private CdLotteryUserService cdLotteryUserService;
	@ModelAttribute
	public CdBasketballSingleOrder get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBasketballSingleOrderService.get(id);
		}else{
			return new CdBasketballSingleOrder();
		}
	}
	
	@RequiresPermissions("cbasketballorder:cdBasketballSingleOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBasketballSingleOrder cdBasketballSingleOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBasketballSingleOrder> page = cdBasketballSingleOrderService.find(new Page<CdBasketballSingleOrder>(request, response), cdBasketballSingleOrder); 
        model.addAttribute("page", page);
		return "modules/cbasketballorder/cdBasketballSingleOrderList";
	}

	@RequiresPermissions("cbasketballorder:cdBasketballSingleOrder:view")
	@RequestMapping(value = "form")
	public String form(CdBasketballSingleOrder cdBasketballSingleOrder, Model model) {

		String uid = cdBasketballSingleOrder.getUid();
		String uName = "";
		if (StringUtils.isNotEmpty(uid)) {
			CdLotteryUser clu = cdLotteryUserService.get(uid);
			uName = clu.getReality();
		}
		String hostWin = cdBasketballSingleOrder.getHostWin();
		List<String> wList = new ArrayList<>();

		String hostFail = cdBasketballSingleOrder.getHostFail();
		List<String> fList = new ArrayList<>();


		if (StringUtils.isNotEmpty(hostWin)) {
			String[] winStr = hostWin.split("\\|");
			for (String s : winStr) {
				wList.add(s);
			}
		}

		if (StringUtils.isNotEmpty(hostFail)) {
			String[] failStr = hostFail.split("\\|");
			for (String s : failStr) {
				fList.add(s);
			}
		}

		//获取当前时间
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String today = df.format(day);

		model.addAttribute("today", today);
		model.addAttribute("wList", wList);
		model.addAttribute("fList", fList);
		model.addAttribute("uName", uName);

		model.addAttribute("cdBasketballSingleOrder", cdBasketballSingleOrder);
		return "modules/cbasketballorder/cdBasketballSingleOrderForm";
	}

	@RequiresPermissions("cbasketballorder:cdBasketballSingleOrder:edit")
	@RequestMapping(value = "save")
	public String save(CdBasketballSingleOrder cdBasketballSingleOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBasketballSingleOrder)){
			return form(cdBasketballSingleOrder, model);
		}
		cdBasketballSingleOrderService.save(cdBasketballSingleOrder);
		addMessage(redirectAttributes, "保存竞彩篮球订单成功");
		return "redirect:"+Global.getAdminPath()+"/cbasketballorder/cdBasketballSingleOrder/?repage";
	}
	
	@RequiresPermissions("cbasketballorder:cdBasketballSingleOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBasketballSingleOrderService.delete(id);
		addMessage(redirectAttributes, "删除竞彩篮球订单成功");
		return "redirect:"+Global.getAdminPath()+"/cbasketballorder/cdBasketballSingleOrder/?repage";
	}

}
