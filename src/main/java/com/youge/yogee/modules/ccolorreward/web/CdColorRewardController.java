/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.ccolorreward.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.ccolorreward.entity.CdColorReward;
import com.youge.yogee.modules.ccolorreward.service.CdColorRewardService;
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
 * 胜负彩开奖结果Controller
 * @author RenHaipeng
 * @version 2018-01-05
 */
@Controller
@RequestMapping(value = "${adminPath}/ccolorreward/cdColorReward")
public class CdColorRewardController extends BaseController {

	@Autowired
	private CdColorRewardService cdColorRewardService;
	
	@ModelAttribute
	public CdColorReward get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdColorRewardService.get(id);
		}else{
			return new CdColorReward();
		}
	}
	
	@RequiresPermissions("ccolorreward:cdColorReward:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdColorReward cdColorReward, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdColorReward> page = cdColorRewardService.find(new Page<CdColorReward>(request, response), cdColorReward);
        model.addAttribute("page", page);
		return "modules/ccolorreward/cdColorRewardList";
	}

	@RequiresPermissions("ccolorreward:cdColorReward:view")
	@RequestMapping(value = "form")
	public String form(CdColorReward cdColorReward, Model model) {
		model.addAttribute("cdColorReward", cdColorReward);
		return "modules/ccolorreward/cdColorRewardForm";
	}

	@RequiresPermissions("ccolorreward:cdColorReward:edit")
	@RequestMapping(value = "save")
	public String save(CdColorReward cdColorReward, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdColorReward)){
			return form(cdColorReward, model);
		}
		cdColorRewardService.save(cdColorReward);
		addMessage(redirectAttributes, "保存胜负彩开奖结果'" + cdColorReward.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/ccolorreward/cdColorReward/?repage";
	}
	
	@RequiresPermissions("ccolorreward:cdColorReward:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdColorRewardService.delete(id);
		addMessage(redirectAttributes, "删除胜负彩开奖结果成功");
		return "redirect:"+ Global.getAdminPath()+"/ccolorreward/cdColorReward/?repage";
	}

}
