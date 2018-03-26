package com.youge.yogee.interfaces.lottery.index;

import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.interfaces.util.util;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import com.youge.yogee.modules.clotteryuser.service.CdLotteryUserService;
import com.youge.yogee.modules.crecord.entity.CdRecordCash;
import com.youge.yogee.modules.crecord.service.CdRecordCashService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhaoYifeng on 2018-03-26.
 */
@Controller
@RequestMapping("${frontPath}")
public class RecordInterface {
    private static final Logger logger = LoggerFactory.getLogger(RecordInterface.class);
    @Autowired
    private CdRecordCashService cdRecordCashService;
    @Autowired
    private CdLotteryUserService cdLotteryUserService;

    /**
     * 添加提现记录
     */
    @RequestMapping(value = "addCashRecord", method = RequestMethod.POST)
    @ResponseBody
    public String addCashRecord(HttpServletRequest request) throws ParseException {
        logger.info("addCashRecord--------Start-------------------");
        Map map = new HashMap();
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        if (jsonData == null) {
            return HttpResultUtil.errorJson("json格式错误");
        }
        //提款金额
        String price = (String) jsonData.get("price");
        if (StringUtils.isEmpty(price)) {
            logger.error("price为空");
            return HttpResultUtil.errorJson("price为空");
        }
        //卡号
        String cardNum = (String) jsonData.get("cardNum");
        if (StringUtils.isEmpty(cardNum)) {
            logger.error("cardNum为空");
            return HttpResultUtil.errorJson("cardNum为空");
        }
        //持卡人姓名
        String cardName = (String) jsonData.get("cardName");
        if (StringUtils.isEmpty(cardName)) {
            logger.error("cardName为空");
            return HttpResultUtil.errorJson("cardName为空");
        }
        String uid = (String) jsonData.get("uid");
        if (StringUtils.isEmpty(uid)) {
            logger.error("uid为空");
            return HttpResultUtil.errorJson("uid为空");
        }
        CdLotteryUser clu = cdLotteryUserService.get(uid);
        if (clu == null) {
            return HttpResultUtil.errorJson("用户不存在");
        }
        String orderNum = util.genOrderNo("", util.getFourRandom());
        CdRecordCash crc = new CdRecordCash();
        crc.setCardName(cardName);
        crc.setCardNum(cardNum);
        crc.setPrice(price);
        crc.setUid(uid);
        crc.setUname(clu.getName());
        crc.setOrderNum(orderNum);
        crc.setStatus("1");
        cdRecordCashService.save(crc);
        return HttpResultUtil.successJson(map);
    }
}



