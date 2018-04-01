package com.youge.yogee.interfaces.app;

import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.bm.entity.BmBase;
import com.youge.yogee.modules.bm.service.BmBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("${publicPath}")
public class AppBaseInterface {
    private static final Logger logger = LoggerFactory.getLogger(AppBaseInterface.class);

    @Autowired
    private BmBaseService bmBaseService;

    /**
     * 其他详情
     * @param request
     * @return
     */
    @RequestMapping(value = "otherDetail", method = RequestMethod.POST)
    @ResponseBody
    public String otherDetail(HttpServletRequest request) {

        logger.info("app otherDetail ----------Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String type = (String) jsonData.get("type");
        if (StringUtils.isEmpty(type)) {
            logger.error("数据提取失败！");
            return HttpResultUtil.errorJson("数据提取失败，请重新获取!");
        }
        Map dataMap = new HashMap();
        String content = "";
        BmBase info = bmBaseService.findByKind(type);

        if(info != null && info.getContent() != null){
            content = info.getContent().replace("src=\"/", "src=\"" + Global.getConfig("domain.name") + "/");
            String reg = "style=\"width[^>]*";
            content = content.replaceAll(reg, "style=\"width:100%\" /");
        }

        dataMap.put("content", content);

        return HttpResultUtil.successJson(dataMap);
    }

}