/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.usm.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.sys.entity.User;
import com.youge.yogee.modules.sys.utils.UserUtils;
import com.youge.yogee.modules.usm.entity.UsmUser;
import com.youge.yogee.modules.usm.service.UsmUserService;
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
 * 用户表Controller
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/usm/usmUser")
public class UsmUserController extends BaseController {

	@Autowired
	private UsmUserService usmUserService;
	
	@ModelAttribute
	public UsmUser get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return usmUserService.get(id);
		}else{
			return new UsmUser();
		}
	}
	
	@RequiresPermissions("usm:usmUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(UsmUser usmUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<UsmUser> page = usmUserService.find(new Page<UsmUser>(request, response), usmUser); 
        model.addAttribute("page", page);
		return "modules/usm/usmUserList";
	}

	@RequiresPermissions("usm:usmUser:view")
	@RequestMapping(value = "form")
	public String form(UsmUser usmUser, Model model) {
		model.addAttribute("usmUser", usmUser);
		return "modules/usm/usmUserForm";
	}

	@RequiresPermissions("usm:usmUser:edit")
	@RequestMapping(value = "save")
	public String save(UsmUser usmUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, usmUser)){
			return form(usmUser, model);
		}
		usmUserService.save(usmUser);
		addMessage(redirectAttributes, "保存用户表'" + usmUser.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/usm/usmUser/?repage";
	}

	@RequiresPermissions("usm:usmUser:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		usmUserService.delete(id);
		addMessage(redirectAttributes, "删除用户表成功");
		return "redirect:"+Global.getAdminPath()+"/usm/usmUser/?repage";
	}

//	@RequiresPermissions("usm:usmUser:view")
//	@RequestMapping(value = "freeze")
//	public String freeze(UsmUser usmUser, Model model) {
//		model.addAttribute("usmUser", usmUser);
//		return "modules/usm/usmUserFreeze";
//	}

	@RequiresPermissions("usm:usmUser:edit")
	@RequestMapping(value = "freeze")
	public String freeze(String id, RedirectAttributes redirectAttributes) {
		UsmUser usmUser = usmUserService.get(id);
		usmUser.setFreeze(usmUser.getFreeze().equals("0") ? "1" : "0" );
		usmUserService.save(usmUser);
		if(usmUser.getFreeze().equals("0")) {
			addMessage(redirectAttributes, "解除冻结成功");
		}else{
			addMessage(redirectAttributes, "冻结用户成功");
		}
		return "redirect:"+Global.getAdminPath()+"/usm/usmUser/?repage";
	}

	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) Long extId, @RequestParam(required=false) Long type,
											  @RequestParam(required=false) Long grade, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
//		User user = UserUtils.getUser();
		List<UsmUser> list = usmUserService.findAll();
		for (int i=0; i<list.size(); i++){
			UsmUser e = list.get(i);
			if ((extId == null || (extId!=null && !extId.equals(e.getId())))){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
//				map.put("pId", !user.isAdmin() && e.getId().equals(user.getOffice().getId())?0:e.getParent()!=null?e.getParent().getId():0);
				map.put("pId",0);
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}
