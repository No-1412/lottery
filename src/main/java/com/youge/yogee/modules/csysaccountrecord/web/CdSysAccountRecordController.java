/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csysaccountrecord.web;

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
import com.youge.yogee.modules.csysaccountrecord.entity.CdSysAccountRecord;
import com.youge.yogee.modules.csysaccountrecord.service.CdSysAccountRecordService;

/**
 * 平台账户积分流水Controller
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/csysaccountrecord/cdSysAccountRecord")
public class CdSysAccountRecordController extends BaseController {

	@Autowired
	private CdSysAccountRecordService cdSysAccountRecordService;
	
	@ModelAttribute
	public CdSysAccountRecord get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdSysAccountRecordService.get(id);
		}else{
			return new CdSysAccountRecord();
		}
	}
	
	@RequiresPermissions("csysaccountrecord:cdSysAccountRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdSysAccountRecord cdSysAccountRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdSysAccountRecord> page = cdSysAccountRecordService.find(new Page<CdSysAccountRecord>(request, response), cdSysAccountRecord); 
        model.addAttribute("page", page);
		return "modules/csysaccountrecord/cdSysAccountRecordList";
	}

	@RequiresPermissions("csysaccountrecord:cdSysAccountRecord:view")
	@RequestMapping(value = "form")
	public String form(CdSysAccountRecord cdSysAccountRecord, Model model) {
		model.addAttribute("cdSysAccountRecord", cdSysAccountRecord);
		return "modules/csysaccountrecord/cdSysAccountRecordForm";
	}

	@RequiresPermissions("csysaccountrecord:cdSysAccountRecord:edit")
	@RequestMapping(value = "save")
	public String save(CdSysAccountRecord cdSysAccountRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdSysAccountRecord)){
			return form(cdSysAccountRecord, model);
		}
		cdSysAccountRecordService.save(cdSysAccountRecord);
		addMessage(redirectAttributes, "保存平台账户积分流水'" + cdSysAccountRecord.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/csysaccountrecord/cdSysAccountRecord/?repage";
	}
	
	@RequiresPermissions("csysaccountrecord:cdSysAccountRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdSysAccountRecordService.delete(id);
		addMessage(redirectAttributes, "删除平台账户积分流水成功");
		return "redirect:"+Global.getAdminPath()+"/csysaccountrecord/cdSysAccountRecord/?repage";
	}

}
