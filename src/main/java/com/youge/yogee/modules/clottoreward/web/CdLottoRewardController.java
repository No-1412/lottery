/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.clottoreward.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.clottoreward.entity.CdLottoReward;
import com.youge.yogee.modules.clottoreward.service.CdLottoRewardService;
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

/**
 * 大乐透开奖结果Controller
 * @author RenHaipeng
 * @version 2018-01-05
 */
@Controller
@RequestMapping(value = "${adminPath}/clottoreward/cdLottoReward")
public class CdLottoRewardController extends BaseController {

	@Autowired
	private CdLottoRewardService cdLottoRewardService;
	
	@ModelAttribute
	public CdLottoReward get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdLottoRewardService.get(id);
		}else{
			return new CdLottoReward();
		}
	}
	
	@RequiresPermissions("clottoreward:cdLottoReward:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdLottoReward cdLottoReward, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdLottoReward> page = cdLottoRewardService.find(new Page<CdLottoReward>(request, response), cdLottoReward);
        model.addAttribute("page", page);
		return "modules/clottoreward/cdLottoRewardList";
	}

	@RequiresPermissions("clottoreward:cdLottoReward:view")
	@RequestMapping(value = "form")
	public String form(CdLottoReward cdLottoReward, Model model) {
		model.addAttribute("cdLottoReward", cdLottoReward);
		return "modules/clottoreward/cdLottoRewardForm";
	}

	@RequiresPermissions("clottoreward:cdLottoReward:edit")
	@RequestMapping(value = "save")
	public String save(CdLottoReward cdLottoReward, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdLottoReward)){
			return form(cdLottoReward, model);
		}
		cdLottoRewardService.save(cdLottoReward);
		addMessage(redirectAttributes, "保存大乐透开奖结果'" + cdLottoReward.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/clottoreward/cdLottoReward/?repage";
	}
	
	@RequiresPermissions("clottoreward:cdLottoReward:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdLottoRewardService.delete(id);
		addMessage(redirectAttributes, "删除大乐透开奖结果成功");
		return "redirect:"+ Global.getAdminPath()+"/clottoreward/cdLottoReward/?repage";
	}

}
