/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csysaccountmoneyrecord.web;

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
import com.youge.yogee.modules.csysaccountmoneyrecord.entity.CdSysAccountMoneyRecord;
import com.youge.yogee.modules.csysaccountmoneyrecord.service.CdSysAccountMoneyRecordService;

/**
 * 平台账户资金流水Controller
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/csysaccountmoneyrecord/cdSysAccountMoneyRecord")
public class CdSysAccountMoneyRecordController extends BaseController {

	@Autowired
	private CdSysAccountMoneyRecordService cdSysAccountMoneyRecordService;
	
	@ModelAttribute
	public CdSysAccountMoneyRecord get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdSysAccountMoneyRecordService.get(id);
		}else{
			return new CdSysAccountMoneyRecord();
		}
	}
	
	@RequiresPermissions("csysaccountmoneyrecord:cdSysAccountMoneyRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdSysAccountMoneyRecord cdSysAccountMoneyRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdSysAccountMoneyRecord> page = cdSysAccountMoneyRecordService.find(new Page<CdSysAccountMoneyRecord>(request, response), cdSysAccountMoneyRecord); 
        model.addAttribute("page", page);
		return "modules/csysaccountmoneyrecord/cdSysAccountMoneyRecordList";
	}

	@RequiresPermissions("csysaccountmoneyrecord:cdSysAccountMoneyRecord:view")
	@RequestMapping(value = "form")
	public String form(CdSysAccountMoneyRecord cdSysAccountMoneyRecord, Model model) {
		model.addAttribute("cdSysAccountMoneyRecord", cdSysAccountMoneyRecord);
		return "modules/csysaccountmoneyrecord/cdSysAccountMoneyRecordForm";
	}

	@RequiresPermissions("csysaccountmoneyrecord:cdSysAccountMoneyRecord:edit")
	@RequestMapping(value = "save")
	public String save(CdSysAccountMoneyRecord cdSysAccountMoneyRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdSysAccountMoneyRecord)){
			return form(cdSysAccountMoneyRecord, model);
		}
		cdSysAccountMoneyRecordService.save(cdSysAccountMoneyRecord);
		addMessage(redirectAttributes, "保存平台账户资金流水'" + cdSysAccountMoneyRecord.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/csysaccountmoneyrecord/cdSysAccountMoneyRecord/?repage";
	}
	
	@RequiresPermissions("csysaccountmoneyrecord:cdSysAccountMoneyRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdSysAccountMoneyRecordService.delete(id);
		addMessage(redirectAttributes, "删除平台账户资金流水成功");
		return "redirect:"+Global.getAdminPath()+"/csysaccountmoneyrecord/cdSysAccountMoneyRecord/?repage";
	}

}
