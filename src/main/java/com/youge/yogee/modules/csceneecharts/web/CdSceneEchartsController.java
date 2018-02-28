/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csceneecharts.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.csceneecharts.entity.CdSceneEcharts;
import com.youge.yogee.modules.csceneecharts.service.CdSceneEchartsService;
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
 * 足球实况ecahrts图表Controller
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/csceneecharts/cdSceneEcharts")
public class CdSceneEchartsController extends BaseController {

	@Autowired
	private CdSceneEchartsService cdSceneEchartsService;
	
	@ModelAttribute
	public CdSceneEcharts get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdSceneEchartsService.get(id);
		}else{
			return new CdSceneEcharts();
		}
	}
	
	@RequiresPermissions("csceneecharts:cdSceneEcharts:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdSceneEcharts cdSceneEcharts, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdSceneEcharts> page = cdSceneEchartsService.find(new Page<CdSceneEcharts>(request, response), cdSceneEcharts);
        model.addAttribute("page", page);
		return "modules/csceneecharts/cdSceneEchartsList";
	}

	@RequiresPermissions("csceneecharts:cdSceneEcharts:view")
	@RequestMapping(value = "form")
	public String form(CdSceneEcharts cdSceneEcharts, Model model) {
		model.addAttribute("cdSceneEcharts", cdSceneEcharts);
		return "modules/csceneecharts/cdSceneEchartsForm";
	}

	@RequiresPermissions("csceneecharts:cdSceneEcharts:edit")
	@RequestMapping(value = "save")
	public String save(CdSceneEcharts cdSceneEcharts, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdSceneEcharts)){
			return form(cdSceneEcharts, model);
		}
		cdSceneEchartsService.save(cdSceneEcharts);
		addMessage(redirectAttributes, "保存足球实况ecahrts图表'" + cdSceneEcharts.getName() + "'成功");
		return "redirect:"+ Global.getAdminPath()+"/csceneecharts/cdSceneEcharts/?repage";
	}
	
	@RequiresPermissions("csceneecharts:cdSceneEcharts:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdSceneEchartsService.delete(id);
		addMessage(redirectAttributes, "删除足球实况ecahrts图表成功");
		return "redirect:"+ Global.getAdminPath()+"/csceneecharts/cdSceneEcharts/?repage";
	}

}
