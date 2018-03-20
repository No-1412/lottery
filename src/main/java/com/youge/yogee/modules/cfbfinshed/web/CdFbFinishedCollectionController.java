/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbfinshed.web;

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
import com.youge.yogee.modules.cfbfinshed.entity.CdFbFinishedCollection;
import com.youge.yogee.modules.cfbfinshed.service.CdFbFinishedCollectionService;

/**
 * 足球比赛收藏Controller
 * @author ZhaoYiFeng
 * @version 2018-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/cfbfinshed/cdFbFinishedCollection")
public class CdFbFinishedCollectionController extends BaseController {

	@Autowired
	private CdFbFinishedCollectionService cdFbFinishedCollectionService;
	
	@ModelAttribute
	public CdFbFinishedCollection get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFbFinishedCollectionService.get(id);
		}else{
			return new CdFbFinishedCollection();
		}
	}
	
	@RequiresPermissions("cfbfinshed:cdFbFinishedCollection:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFbFinishedCollection cdFbFinishedCollection, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFbFinishedCollection> page = cdFbFinishedCollectionService.find(new Page<CdFbFinishedCollection>(request, response), cdFbFinishedCollection); 
        model.addAttribute("page", page);
		return "modules/cfbfinshed/cdFbFinishedCollectionList";
	}

	@RequiresPermissions("cfbfinshed:cdFbFinishedCollection:view")
	@RequestMapping(value = "form")
	public String form(CdFbFinishedCollection cdFbFinishedCollection, Model model) {
		model.addAttribute("cdFbFinishedCollection", cdFbFinishedCollection);
		return "modules/cfbfinshed/cdFbFinishedCollectionForm";
	}

	@RequiresPermissions("cfbfinshed:cdFbFinishedCollection:edit")
	@RequestMapping(value = "save")
	public String save(CdFbFinishedCollection cdFbFinishedCollection, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFbFinishedCollection)){
			return form(cdFbFinishedCollection, model);
		}
		cdFbFinishedCollectionService.save(cdFbFinishedCollection);
		addMessage(redirectAttributes, "保存足球比赛收藏成功");
		return "redirect:"+Global.getAdminPath()+"/cfbfinshed/cdFbFinishedCollection/?repage";
	}
	
	@RequiresPermissions("cfbfinshed:cdFbFinishedCollection:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFbFinishedCollectionService.delete(id);
		addMessage(redirectAttributes, "删除足球比赛收藏成功");
		return "redirect:"+Global.getAdminPath()+"/cfbfinshed/cdFbFinishedCollection/?repage";
	}

}
