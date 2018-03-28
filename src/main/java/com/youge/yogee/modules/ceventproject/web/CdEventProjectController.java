/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.ceventproject.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.ceventproject.entity.CdEventProject;
import com.youge.yogee.modules.ceventproject.service.CdEventProjectService;
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
 * 赛事专题Controller
 *
 * @author WeiJinChao
 * @version 2017-12-18
 */
@Controller
@RequestMapping(value = "${adminPath}/ceventproject/cdEventProject")
public class CdEventProjectController extends BaseController {

    @Autowired
    private CdEventProjectService cdEventProjectService;

    @ModelAttribute
    public CdEventProject get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return cdEventProjectService.get(id);
        } else {
            return new CdEventProject();
        }
    }

    @RequiresPermissions("ceventproject:cdEventProject:view")
    @RequestMapping(value = {"list", ""})
    public String list(CdEventProject cdEventProject, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();

        Page<CdEventProject> page = cdEventProjectService.find(new Page<CdEventProject>(request, response), cdEventProject);
        model.addAttribute("page", page);
        return "modules/ceventproject/cdEventProjectList";
    }

    @RequiresPermissions("ceventproject:cdEventProject:view")
    @RequestMapping(value = "form")
    public String form(CdEventProject cdEventProject, Model model) {
        model.addAttribute("cdEventProject", cdEventProject);
        return "modules/ceventproject/cdEventProjectForm";
    }

    @RequiresPermissions("ceventproject:cdEventProject:edit")
    @RequestMapping(value = "save")
    public String save(CdEventProject cdEventProject, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, cdEventProject)) {
            return form(cdEventProject, model);
        }
        //对富文本进行转码
        String introduce = StringEscapeUtils.unescapeHtml(cdEventProject.getContent());
        //图片处理
        introduce = introduce.replaceAll("src=\"/userfiles", "src=\"" + Global.getConfig("domain.name") + "/userfiles");
        String reg = "style=\\\"width[^>]*";
        introduce = introduce.replaceAll(reg, "style=\"width:100%\" /");
        if (!introduce.contains(reg)) {
            introduce = introduce.replaceAll("<img", "<img style=\"width:100%\" /");
        }
        cdEventProject.setContent(introduce);
        cdEventProjectService.save(cdEventProject);
        addMessage(redirectAttributes, "保存赛事专题'" + cdEventProject.getName() + "'成功");
        return "redirect:" + Global.getAdminPath() + "/ceventproject/cdEventProject/?repage";
    }

    @RequiresPermissions("ceventproject:cdEventProject:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        cdEventProjectService.delete(id);
        addMessage(redirectAttributes, "删除赛事专题成功");
        return "redirect:" + Global.getAdminPath() + "/ceventproject/cdEventProject/?repage";
    }

}
