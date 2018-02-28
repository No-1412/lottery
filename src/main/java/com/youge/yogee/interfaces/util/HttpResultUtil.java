package com.youge.yogee.interfaces.util;

import com.youge.yogee.common.mapper.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * http请求返回结构 共通类
 *
 * @author BiHaidong
 * @date 2017-2-6
 */
public class HttpResultUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpResultUtil.class);

    /**
     * 成功的JSON
     * @param resultFlag 返回结果
     * @return
     */
    public static String successJson(Map resultFlag) {
        resultFlag.put("result","0");
        Map map = new HashMap();

        map.put(Constant.RESPONSE_DATA_KEY, resultFlag);
        map.put(Constant.RESPONSE_STATE_KEY, Constant.SUCCESS);
        map.put(Constant.RESPONSE_MSG_KEY, Constant.SUCCESS);

        String result = JsonMapper.nonDefaultMapper().toJson(map);

        logger.info("success result ------------" + result);
        return result;
    }

    /**
     * 错误的JSON
     * @param msg 提示信息
     * @return
     */
    public static String errorJson(String msg) {
        Map resultFlag = new HashMap();
        resultFlag.put("result","1");
        Map map = new HashMap();

        map.put(Constant.RESPONSE_DATA_KEY, resultFlag);
        map.put(Constant.RESPONSE_STATE_KEY, Constant.ERROR);
        map.put(Constant.RESPONSE_MSG_KEY, msg);

        String result = JsonMapper.nonDefaultMapper().toJson(map);

        logger.info("error result ------------" + result);
        return result;
    }

    /**
     * 安卓端：逻辑错误但是需要进行页面跳转，需要用success标识
     * @param resultFlag 包含逻辑错误的提示
     * @return
     */
    public static String successJsonSetResult(Map resultFlag) {
        Map map = new HashMap();

        map.put(Constant.RESPONSE_DATA_KEY, resultFlag);
        map.put(Constant.RESPONSE_STATE_KEY, Constant.SUCCESS);
        map.put(Constant.RESPONSE_MSG_KEY, Constant.SUCCESS);

        String result = JsonMapper.nonDefaultMapper().toJson(map);

        logger.info("success result ------------" + result);
        return result;
    }

}
