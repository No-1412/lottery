package com.youge.yogee.interfaces.app;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.interfaces.util.HttpServletRequestUtils;
import com.youge.yogee.modules.bm.entity.BmBase;
import com.youge.yogee.modules.version.entity.CdAppVersion;
import com.youge.yogee.modules.version.service.CdAppVersionService;
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
public class AppVersionInterface {


    @Autowired
    private CdAppVersionService cdAppVersionService;

    private static final Logger logger = LoggerFactory.getLogger(AppVersionInterface.class);

    /**
     * 其他详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "checkVersion", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String checkVersion(HttpServletRequest request) {

        logger.info("app otherDetail ----------Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);

        String version_code = (String) jsonData.get("version_code");
        if (Strings.isNullOrEmpty(version_code)) {
            logger.error("数据提取失败！");
            return HttpResultUtil.errorJson("数据提取失败，请重新获取!");
        }


        CdAppVersion info = cdAppVersionService.getByVersionCode(version_code);
        if (info == null) {
            return HttpResultUtil.errorJson("无效版本标识!");
        }
//
//        if (info != null && info.getContent() != null) {
//            content = info.getContent().replace("src=\"/", "src=\"" + Global.getConfig("domain.name") + "/");
//            String reg = "style=\"width[^>]*";
//            content = content.replaceAll(reg, "style=\"width:100%\" /");
//        }

        Map dataMap = new HashMap();

        dataMap.put("upgrade_flag", info.getVersionIsUpgrade());
        dataMap.put("enforcement_flag", info.getVersionIsEnforcement());
        dataMap.put("download_url", info.getVersionDownloadUrl());
        dataMap.put("description", info.getVersionDescription());


        return HttpResultUtil.successJson(dataMap);
    }

}
