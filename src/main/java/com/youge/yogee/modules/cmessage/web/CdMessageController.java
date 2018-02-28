/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cmessage.web;

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
import com.youge.yogee.modules.cmessage.entity.CdMessage;
import com.youge.yogee.modules.cmessage.service.CdMessageService;

/**
 * 短信信息表Controller
 * @author WeiJinChao
 * @version 2017-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/cmessage/cdMessage")
public class CdMessageController extends BaseController {

	@Autowired
	private CdMessageService cdMessageService;
	
	@ModelAttribute
	public CdMessage get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdMessageService.get(id);
		}else{
			return new CdMessage();
		}
	}
	
	@RequiresPermissions("cmessage:cdMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdMessage cdMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdMessage> page = cdMessageService.find(new Page<CdMessage>(request, response), cdMessage); 
        model.addAttribute("page", page);
		return "modules/cmessage/cdMessageList";
	}

	@RequiresPermissions("cmessage:cdMessage:view")
	@RequestMapping(value = "form")
	public String form(CdMessage cdMessage, Model model) {
		model.addAttribute("cdMessage", cdMessage);
		return "modules/cmessage/cdMessageForm";
	}

	@RequiresPermissions("cmessage:cdMessage:edit")
	@RequestMapping(value = "save")
	public String save(CdMessage cdMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdMessage)){
			return form(cdMessage, model);
		}
		cdMessageService.save(cdMessage);
		addMessage(redirectAttributes, "保存短信信息表'" + cdMessage.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cmessage/cdMessage/?repage";
	}
	
	@RequiresPermissions("cmessage:cdMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdMessageService.delete(id);
		addMessage(redirectAttributes, "删除短信信息表成功");
		return "redirect:"+Global.getAdminPath()+"/cmessage/cdMessage/?repage";
	}

}
