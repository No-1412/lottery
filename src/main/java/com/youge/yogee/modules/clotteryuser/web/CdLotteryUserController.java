/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.clotteryuser.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
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
 * 用户注册Controller
 * @author WeiJinChao
 * @version 2017-12-07
 */
@Controller
@RequestMapping(value = "${adminPath}/clotteryuser/cdLotteryUser")
public class CdLotteryUserController extends BaseController {

	@Autowired
	private CdLotteryUserService cdLotteryUserService;
	
	@ModelAttribute
	public CdLotteryUser get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdLotteryUserService.get(id);
		}else{
			return new CdLotteryUser();
		}
	}
	
	@RequiresPermissions("clotteryuser:cdLotteryUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdLotteryUser cdLotteryUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdLotteryUser> page = cdLotteryUserService.find(new Page<CdLotteryUser>(request, response), cdLotteryUser); 
        model.addAttribute("page", page);
		return "modules/clotteryuser/cdLotteryUserList";
	}

	@RequiresPermissions("clotteryuser:cdLotteryUser:view")
	@RequestMapping(value = "form")
	public String form(CdLotteryUser cdLotteryUser, Model model) {
		model.addAttribute("cdLotteryUser", cdLotteryUser);
		return "modules/clotteryuser/cdLotteryUserForm";
	}

	@RequiresPermissions("clotteryuser:cdLotteryUser:edit")
	@RequestMapping(value = "save")
	public String save(CdLotteryUser cdLotteryUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdLotteryUser)){
			return form(cdLotteryUser, model);
		}
		cdLotteryUserService.save(cdLotteryUser);
		addMessage(redirectAttributes, "保存用户注册'" + cdLotteryUser.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/clotteryuser/cdLotteryUser/?repage";
	}
	
	@RequiresPermissions("clotteryuser:cdLotteryUser:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdLotteryUserService.delete(id);
		addMessage(redirectAttributes, "删除用户注册成功");
		return "redirect:"+Global.getAdminPath()+"/clotteryuser/cdLotteryUser/?repage";
	}
	/**
	 * 获取用户信息
	 * @param uid
	 * @return
	 */
	public CdLotteryUser cdLotteryUser(String uid){
		String  userId =uid.substring(4, uid.length());
		CdLotteryUser cdLotteryUser = cdLotteryUserService.get(userId);
		return cdLotteryUser;
	}

}
