/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.crecord.web;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.web.BaseController;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.crecord.entity.CdRecordCash;
import com.youge.yogee.modules.crecord.service.CdRecordCashService;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 提现记录Controller
 *
 * @author ZhaoYiFeng
 * @version 2018-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/crecord/cdRecordCash")
public class CdRecordCashController extends BaseController {

    @Autowired
    private CdRecordCashService cdRecordCashService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;

    @ModelAttribute
    public CdRecordCash get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return cdRecordCashService.get(id);
        } else {
            return new CdRecordCash();
        }
    }

    @RequiresPermissions("crecord:cdRecordCash:view")
    @RequestMapping(value = {"list", ""})
    public String list(CdRecordCash cdRecordCash, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();

        Page<CdRecordCash> page = cdRecordCashService.find(new Page<CdRecordCash>(request, response), cdRecordCash);
        model.addAttribute("page", page);
        return "modules/crecord/cdRecordCashList";
    }

    @RequiresPermissions("crecord:cdRecordCash:view")
    @RequestMapping(value = "form")
    public String form(CdRecordCash cdRecordCash, Model model) {
        model.addAttribute("cdRecordCash", cdRecordCash);
        return "modules/crecord/cdRecordCashForm";
    }

    @RequiresPermissions("crecord:cdRecordCash:edit")
    @RequestMapping(value = "save")
    public String save(CdRecordCash cdRecordCash, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, cdRecordCash)) {
            return form(cdRecordCash, model);
        }
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = df.format(day);
        cdRecordCash.setDealTime(today);

        //7.2董宏 修改用户提现审核失败 将提现金额返回到用户余额  目前存在如果多次保存 不通过，余额会不断增加的问题
        if ("4".equals(cdRecordCash.getStatus())) {
            CdLotteryUser clu = cdLotteryUserService.get(cdRecordCash.getUid());
            BigDecimal balance = clu.getBalance();
            BigDecimal price = new BigDecimal(cdRecordCash.getPrice());
            clu.setBalance(balance.add(price));
            cdLotteryUserService.save(clu);
        }


        cdRecordCashService.save(cdRecordCash);
        addMessage(redirectAttributes, "保存提现记录成功");
        return "redirect:" + Global.getAdminPath() + "/crecord/cdRecordCash/?repage";
    }

    @RequiresPermissions("crecord:cdRecordCash:edit")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        cdRecordCashService.delete(id);
        addMessage(redirectAttributes, "删除提现记录成功");
        return "redirect:" + Global.getAdminPath() + "/crecord/cdRecordCash/?repage";
    }

}
