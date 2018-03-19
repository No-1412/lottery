/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbnotfinsh.web;

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
import com.youge.yogee.modules.cbbnotfinsh.entity.CdBbNotFinishCollection;
import com.youge.yogee.modules.cbbnotfinsh.service.CdBbNotFinishCollectionService;

/**
 * 篮球比赛收藏Controller
 * @author ZhaoYiFeng
 * @version 2018-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/cbbnotfinsh/cdBbNotFinishCollection")
public class CdBbNotFinishCollectionController extends BaseController {

	@Autowired
	private CdBbNotFinishCollectionService cdBbNotFinishCollectionService;
	
	@ModelAttribute
	public CdBbNotFinishCollection get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBbNotFinishCollectionService.get(id);
		}else{
			return new CdBbNotFinishCollection();
		}
	}
	
	@RequiresPermissions("cbbnotfinsh:cdBbNotFinishCollection:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBbNotFinishCollection cdBbNotFinishCollection, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBbNotFinishCollection> page = cdBbNotFinishCollectionService.find(new Page<CdBbNotFinishCollection>(request, response), cdBbNotFinishCollection); 
        model.addAttribute("page", page);
		return "modules/cbbnotfinsh/cdBbNotFinishCollectionList";
	}

	@RequiresPermissions("cbbnotfinsh:cdBbNotFinishCollection:view")
	@RequestMapping(value = "form")
	public String form(CdBbNotFinishCollection cdBbNotFinishCollection, Model model) {
		model.addAttribute("cdBbNotFinishCollection", cdBbNotFinishCollection);
		return "modules/cbbnotfinsh/cdBbNotFinishCollectionForm";
	}

	@RequiresPermissions("cbbnotfinsh:cdBbNotFinishCollection:edit")
	@RequestMapping(value = "save")
	public String save(CdBbNotFinishCollection cdBbNotFinishCollection, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBbNotFinishCollection)){
			return form(cdBbNotFinishCollection, model);
		}
		cdBbNotFinishCollectionService.save(cdBbNotFinishCollection);
		addMessage(redirectAttributes, "保存篮球比赛收藏'" + cdBbNotFinishCollection.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cbbnotfinsh/cdBbNotFinishCollection/?repage";
	}
	
	@RequiresPermissions("cbbnotfinsh:cdBbNotFinishCollection:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBbNotFinishCollectionService.delete(id);
		addMessage(redirectAttributes, "删除篮球比赛收藏成功");
		return "redirect:"+Global.getAdminPath()+"/cbbnotfinsh/cdBbNotFinishCollection/?repage";
	}

}
