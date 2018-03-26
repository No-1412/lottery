/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.erp.entity.ErpUser;
import com.youge.yogee.modules.erp.service.ErpUserService;
import com.youge.yogee.modules.sys.entity.User;
import com.youge.yogee.modules.sys.service.SystemService;
import com.youge.yogee.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 * @author RenHaipeng
 * @version 2018-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/erp/user")
public class ErpUserController extends BaseController {

	@Autowired
	private ErpUserService erpUserService;

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public ErpUser get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return erpUserService.get(id);
		}else{
			return new ErpUser();
		}
	}
	
	@RequiresPermissions("erp:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(ErpUser erpUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<ErpUser> page = erpUserService.find(new Page<ErpUser>(request, response), erpUser);
        model.addAttribute("page", page);
		return "modules/erp/userList";
	}

	@RequiresPermissions("erp:user:view")
	@RequestMapping(value = "form")
	public String form(ErpUser user, Model model) {
		model.addAttribute("erpUser", user);
		return "modules/erp/userForm";
	}

	@RequiresPermissions("erp:user:edit")
	@RequestMapping(value = "save")
	public String save(ErpUser user, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		erpUserService.save(user);
		addMessage(redirectAttributes, "保存用户'" + user.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/erp/user/?repage";
	}
	
	@RequiresPermissions("erp:user:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		erpUserService.delete(id);
		addMessage(redirectAttributes, "删除用户成功");
		return "redirect:"+ Global.getAdminPath()+"/erp/user/?repage";
	}


	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) Long extId, @RequestParam(required=false) Long type,
                                              @RequestParam(required=false) Long grade, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
//		User user = UserUtils.getUser();
		List<User> list = systemService.findAllUser();
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}

	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeDatas")
	public List<Map<String, Object>> treeDatas(@RequestParam(required=false) Long extId, @RequestParam(required=false) Long type,
                                               @RequestParam(required=false) Long grade, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
//		User user = UserUtils.getUser();
		List<ErpUser> list = erpUserService.findAllUser();
		for (int i=0; i<list.size(); i++){
			ErpUser e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}

}
