/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cnotice.web;

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
import com.youge.yogee.modules.cnotice.entity.CdNotice;
import com.youge.yogee.modules.cnotice.service.CdNoticeService;

/**
 * 公告信息Controller
 * @author WeiJinChao
 * @version 2017-12-18
 */
@Controller
@RequestMapping(value = "${adminPath}/cnotice/cdNotice")
public class CdNoticeController extends BaseController {

	@Autowired
	private CdNoticeService cdNoticeService;
	
	@ModelAttribute
	public CdNotice get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdNoticeService.get(id);
		}else{
			return new CdNotice();
		}
	}
	
	@RequiresPermissions("cnotice:cdNotice:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdNotice cdNotice, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdNotice> page = cdNoticeService.find(new Page<CdNotice>(request, response), cdNotice); 
        model.addAttribute("page", page);
		return "modules/cnotice/cdNoticeList";
	}

	@RequiresPermissions("cnotice:cdNotice:view")
	@RequestMapping(value = "form")
	public String form(CdNotice cdNotice, Model model) {
		model.addAttribute("cdNotice", cdNotice);
		return "modules/cnotice/cdNoticeForm";
	}

	@RequiresPermissions("cnotice:cdNotice:edit")
	@RequestMapping(value = "save")
	public String save(CdNotice cdNotice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdNotice)){
			return form(cdNotice, model);
		}
		cdNoticeService.save(cdNotice);
		addMessage(redirectAttributes, "保存公告信息'" + cdNotice.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cnotice/cdNotice/?repage";
	}
	
	@RequiresPermissions("cnotice:cdNotice:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdNoticeService.delete(id);
		addMessage(redirectAttributes, "删除公告信息成功");
		return "redirect:"+Global.getAdminPath()+"/cnotice/cdNotice/?repage";
	}

}
