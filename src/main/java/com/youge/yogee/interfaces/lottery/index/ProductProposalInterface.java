package com.youge.yogee.interfaces.lottery.index;

import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.modules.cproductproposal.service.CdProductProposalService;
import com.youge.yogee.publicutils.AddUtils;
import com.youge.yogee.publicutils.ImgUploudUtlis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wjc on 2017-12-22 0022.
 */
@Controller
@RequestMapping("${frontPath}")
public class ProductProposalInterface {
    private static final Logger logger = LoggerFactory.getLogger(ProductProposalInterface.class);

    @Autowired
    private CdProductProposalService cdProductProposalService;

    /***
     * 产品建议添加
     */
    @RequestMapping(value = "productProposal", method = RequestMethod.POST)
    @ResponseBody
    public String productProposal(HttpServletRequest request,MultipartFile file) {
        logger.info("产品建议productProposal---------- Start--------");
        String content = request.getParameter("content");//发布标题
        Map mapData = new HashMap();
        Map map = ImgUploudUtlis.getUploud(request, file);
        String img = "";
        if(map!=null && map.size()>0){
            //单张图片
            img = (String)map.get("fileNames");
        }
        AddUtils.productProposal(cdProductProposalService, img, content);
        logger.info("产品建议productProposal---------- End--------");
        return HttpResultUtil.successJson(mapData);
    }

}
