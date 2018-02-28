/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.ctradingrecord.web;

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
import com.youge.yogee.modules.ctradingrecord.entity.CdTradingRecord;
import com.youge.yogee.modules.ctradingrecord.service.CdTradingRecordService;

/**
 * 交易记录Controller
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/ctradingrecord/cdTradingRecord")
public class CdTradingRecordController extends BaseController {

	@Autowired
	private CdTradingRecordService cdTradingRecordService;
	
	@ModelAttribute
	public CdTradingRecord get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdTradingRecordService.get(id);
		}else{
			return new CdTradingRecord();
		}
	}
	
	@RequiresPermissions("ctradingrecord:cdTradingRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdTradingRecord cdTradingRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdTradingRecord> page = cdTradingRecordService.find(new Page<CdTradingRecord>(request, response), cdTradingRecord); 
        model.addAttribute("page", page);
		return "modules/ctradingrecord/cdTradingRecordList";
	}

	@RequiresPermissions("ctradingrecord:cdTradingRecord:view")
	@RequestMapping(value = "form")
	public String form(CdTradingRecord cdTradingRecord, Model model) {
		model.addAttribute("cdTradingRecord", cdTradingRecord);
		return "modules/ctradingrecord/cdTradingRecordForm";
	}

	@RequiresPermissions("ctradingrecord:cdTradingRecord:edit")
	@RequestMapping(value = "save")
	public String save(CdTradingRecord cdTradingRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdTradingRecord)){
			return form(cdTradingRecord, model);
		}
		cdTradingRecordService.save(cdTradingRecord);
		addMessage(redirectAttributes, "保存交易记录'" + cdTradingRecord.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/ctradingrecord/cdTradingRecord/?repage";
	}
	
	@RequiresPermissions("ctradingrecord:cdTradingRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdTradingRecordService.delete(id);
		addMessage(redirectAttributes, "删除交易记录成功");
		return "redirect:"+Global.getAdminPath()+"/ctradingrecord/cdTradingRecord/?repage";
	}

}
