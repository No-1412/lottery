/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbankcard.web;

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
import com.youge.yogee.modules.cbankcard.entity.CdBankCard;
import com.youge.yogee.modules.cbankcard.service.CdBankCardService;

/**
 * 用户银行卡Controller
 * @author WeiJinChao
 * @version 2017-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cbankcard/cdBankCard")
public class CdBankCardController extends BaseController {

	@Autowired
	private CdBankCardService cdBankCardService;
	
	@ModelAttribute
	public CdBankCard get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return cdBankCardService.get(id);
		}else{
			return new CdBankCard();
		}
	}
	
	@RequiresPermissions("cbankcard:cdBankCard:view")
	@RequestMapping(value = {"list", ""})
	public String list(CdBankCard cdBankCard, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
        Page<CdBankCard> page = cdBankCardService.find(new Page<CdBankCard>(request, response), cdBankCard); 
        model.addAttribute("page", page);
		return "modules/cbankcard/cdBankCardList";
	}

	@RequiresPermissions("cbankcard:cdBankCard:view")
	@RequestMapping(value = "form")
	public String form(CdBankCard cdBankCard, Model model) {
		model.addAttribute("cdBankCard", cdBankCard);
		return "modules/cbankcard/cdBankCardForm";
	}

	@RequiresPermissions("cbankcard:cdBankCard:edit")
	@RequestMapping(value = "save")
	public String save(CdBankCard cdBankCard, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cdBankCard)){
			return form(cdBankCard, model);
		}
		cdBankCardService.save(cdBankCard);
		addMessage(redirectAttributes, "保存用户银行卡'" + cdBankCard.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/cbankcard/cdBankCard/?repage";
	}
	
	@RequiresPermissions("cbankcard:cdBankCard:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		cdBankCardService.delete(id);
		addMessage(redirectAttributes, "删除用户银行卡成功");
		return "redirect:"+Global.getAdminPath()+"/cbankcard/cdBankCard/?repage";
	}

}
