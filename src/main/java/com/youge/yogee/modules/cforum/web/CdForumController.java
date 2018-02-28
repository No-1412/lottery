/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cforum.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cforum.entity.CdForum;
import com.youge.yogee.modules.cforum.service.CdForumService;
import com.youge.yogee.modules.sys.entity.User;
import com.youge.yogee.modules.sys.utils.UserUtils;
import org.apache.commons.lang.StringEscapeUtils;
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
 * 论坛Controller
 * @author WeiJinChao
 * @version 2017-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cforum/cdForum")
public class CdForumController extends BaseController {

	@Autowired
	private CdForumService cdForumService;
	
	@ModelAttribute
	public CdForum get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdForumService.get(id);
		}else{
			return new CdForum();
		}
	}
	
	@RequiresPermissions("cforum:cdForum:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdForum cdForum, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdForum> page = cdForumService.find(new Page<CdForum>(request, response), cdForum); 
        model.addAttribute("page", page);
		return "modules/cforum/cdForumList";
	}

	@RequiresPermissions("cforum:cdForum:view")
	@RequestMapping(value = "form")
	public String form(CdForum cdForum, Model model) {
		model.addAttribute("cdForum", cdForum);
		return "modules/cforum/cdForumForm";
	}

	@RequiresPermissions("cforum:cdForum:edit")
	@RequestMapping(value = "save")
	public String save(CdForum cdForum, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdForum)){
			return form(cdForum, model);
		}
		//对富文本进行转码
		cdForum.setContent(StringEscapeUtils.unescapeHtml(cdForum.getContent()));
		cdForumService.save(cdForum);
		addMessage(redirectAttributes, "保存论坛'" + cdForum.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cforum/cdForum/?repage";
	}
	
	@RequiresPermissions("cforum:cdForum:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdForumService.delete(id);
		addMessage(redirectAttributes, "删除论坛成功");
		return "redirect:"+Global.getAdminPath()+"/cforum/cdForum/?repage";
	}

}
