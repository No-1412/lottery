package com.youge.yogee.modules.cms.web;

import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cms.entity.Site;
import com.youge.yogee.modules.cms.service.FileTplService;
import com.youge.yogee.modules.cms.service.SiteService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 站点Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/template")
public class TemplateController extends BaseController {

    @Autowired
   	private FileTplService fileTplService;
    @Autowired
   	private SiteService siteService;

    @RequiresPermissions("cms:yogee:edit")
   	@RequestMapping(value = "")
   	public String index() {
   		return "modules/cms/tplIndex";
   	}

    @RequiresPermissions("cms:yogee:edit")
   	@RequestMapping(value = "tree")
   	public String tree(Model model) {
        Site site = siteService.get(Site.getCurrentSiteId());
   		model.addAttribute("templateList", fileTplService.getListForEdit(site.getSolutionPath()));
   		return "modules/cms/tplTree";
   	}

    @RequiresPermissions("cms:yogee:edit")
   	@RequestMapping(value = "form")
   	public String form(String name, Model model) {
        model.addAttribute("template", fileTplService.get(name));
   		return "modules/cms/tplForm";
   	}

    @RequiresPermissions("cms:yogee:edit")
   	@RequestMapping(value = "help")
   	public String help() {
   		return "modules/cms/tplHelp";
   	}
}
