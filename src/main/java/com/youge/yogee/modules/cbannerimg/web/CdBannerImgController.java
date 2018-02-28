/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbannerimg.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.cbannerimg.entity.CdBannerImg;
import com.youge.yogee.modules.cbannerimg.service.CdBannerImgService;
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
 * 轮播图片Controller
 * @author WeiJinChao
 * @version 2017-12-15
 */
@Controller
@RequestMapping(value = "${adminPath}/cbannerimg/cdBannerImg")
public class CdBannerImgController extends BaseController {

	@Autowired
	private CdBannerImgService cdBannerImgService;
	
	@ModelAttribute
	public CdBannerImg get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBannerImgService.get(id);
		}else{
			return new CdBannerImg();
		}
	}
	
	@RequiresPermissions("cbannerimg:cdBannerImg:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBannerImg cdBannerImg, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBannerImg> page = cdBannerImgService.find(new Page<CdBannerImg>(request, response), cdBannerImg); 
        model.addAttribute("page", page);
		return "modules/cbannerimg/cdBannerImgList";
	}

	@RequiresPermissions("cbannerimg:cdBannerImg:view")
	@RequestMapping(value = "form")
	public String form(CdBannerImg cdBannerImg, Model model) {
		model.addAttribute("cdBannerImg", cdBannerImg);
		return "modules/cbannerimg/cdBannerImgForm";
	}

	@RequiresPermissions("cbannerimg:cdBannerImg:edit")
	@RequestMapping(value = "save")
	public String save(CdBannerImg cdBannerImg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBannerImg)){
			return form(cdBannerImg, model);
		}
		cdBannerImgService.save(cdBannerImg);
		addMessage(redirectAttributes, "保存轮播图片'" + cdBannerImg.getTitle() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cbannerimg/cdBannerImg/?repage";
	}
	
	@RequiresPermissions("cbannerimg:cdBannerImg:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBannerImgService.delete(id);
		addMessage(redirectAttributes, "删除轮播图片成功");
		return "redirect:"+Global.getAdminPath()+"/cbannerimg/cdBannerImg/?repage";
	}

}
