/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbnotfinish.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinishCollection;
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
import com.youge.yogee.modules.cfbnotfinish.service.CdFbNotFinishCollectionService;

/**
 * 足球比赛收藏Controller
 * @author ZhaoYiFeng
 * @version 2018-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/cfbfinshed/cdFbFinishedCollection")
public class CdFbNotFinishCollectionController extends BaseController {

	@Autowired
	private CdFbNotFinishCollectionService cdFbNotFinishCollectionService;
	
	@ModelAttribute
	public CdFbNotFinishCollection get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdFbNotFinishCollectionService.get(id);
		}else{
			return new CdFbNotFinishCollection();
		}
	}
	
	@RequiresPermissions("cfbnotfinish:cdFbNotFinishCollection:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdFbNotFinishCollection cdFbNotFinishCollection, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdFbNotFinishCollection> page = cdFbNotFinishCollectionService.find(new Page<CdFbNotFinishCollection>(request, response), cdFbNotFinishCollection);
        model.addAttribute("page", page);
		return "modules/cfbfinshed/cdFbFinishedCollectionList";
	}

	@RequiresPermissions("cfbnotfinish:cdFbNotFinishCollection:view")
	@RequestMapping(value = "form")
	public String form(CdFbNotFinishCollection cdFbNotFinishCollection, Model model) {
		model.addAttribute("cdFbFinishedCollection", cdFbNotFinishCollection);
		return "modules/cfbfinshed/cdFbFinishedCollectionForm";
	}

	@RequiresPermissions("cfbnotfinish:cdFbNotFinishCollection:edit")
	@RequestMapping(value = "save")
	public String save(CdFbNotFinishCollection cdFbNotFinishCollection, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdFbNotFinishCollection)){
			return form(cdFbNotFinishCollection, model);
		}
		cdFbNotFinishCollectionService.save(cdFbNotFinishCollection);
		addMessage(redirectAttributes, "保存足球比赛收藏成功");
		return "redirect:"+Global.getAdminPath()+"/cfbnotfinish/cdFbNotFinishCollection/?repage";
	}
	
	@RequiresPermissions("cfbnotfinish:cdFbNotFinishCollection:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdFbNotFinishCollectionService.delete(id);
		addMessage(redirectAttributes, "删除足球比赛收藏成功");
		return "redirect:"+Global.getAdminPath()+"/cfbnotfinish/cdFbNotFinishCollection/?repage";
	}

}
