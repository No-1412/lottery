package com.youge.yogee.interfaces.util;

import com.ckfinder.connector.ServletContextFactory;
import com.youge.yogee.common.mapper.JsonMapper;
import com.youge.yogee.common.utils.AES256EncryptionUtils;
import com.youge.yogee.common.utils.Encodes;
import com.youge.yogee.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/23.
 */
public class HttpServletRequestUtils {


    private static Logger logger = LoggerFactory.getLogger(HttpServletRequestUtils.class);
    private static boolean isDebugLogger = logger.isDebugEnabled();

    /**
     * 加密的的数据
     * json解析成string对象
     *
     * @param request 请求的对象
     * @return json解析成map对象
     */
    public static String readJsonSecretData(final HttpServletRequest request) {
        BufferedReader reader;
        String jsonstring = null;
        try {
            reader = request.getReader();

            StringBuffer json = new StringBuffer();
            String temp = "";
            while (null != reader && (temp = reader.readLine()) != null) {
                json.append(temp);
            }
            if (null != json && !json.toString().trim().equals("")) {
                jsonstring = json.toString();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("参数获取错误", e);
        }
        return jsonstring;
    }

    /**
     * json解析成map对象
     *
     * @param request 请求的对象
     * @return json解析成map对象
     */
    public static Map<?, ?> readJsonData(final HttpServletRequest request) {
        BufferedReader reader;
        Map<?, ?> result = null;
        try {
            reader = request.getReader();

            StringBuffer json = new StringBuffer();

            String temp = "";
            String str = null;
            while (null != reader && (temp = reader.readLine()) != null) {
                json.append(temp);
            }
            if (null != json && !json.toString().trim().equals("")) {
                String jsonstring = json.toString();
//                System.out.println(jsonstring);

                if(jsonstring.contains("{")){
                    result = JsonMapper.nonDefaultMapper().fromJson(jsonstring, HashMap.class);
                }else{
                    if(jsonstring.contains("+") || jsonstring.contains("/") || jsonstring.contains("L")
                            || jsonstring.contains("U") || jsonstring.contains("Q") || jsonstring.contains("J")
                            || jsonstring.contains("Y")){
                        byte [] bb = Encodes.decodeBase64(jsonstring);
                        String cJsonstring= AES256EncryptionUtils.parseByte2HexStr(bb);
                        str = AES256EncryptionUtils.decrypt(cJsonstring);
//                    System.out.println("old=========================="+str);
                    }else{
                        str = AES256EncryptionUtils.decrypt(jsonstring);
                        //String  str = URLEncoder.encode(AES256EncryptionUtils.decrypt(jsonstring), "UTF-8");
//                    System.out.println("new=========================="+str);
                    }
                    if(null ==str){
                        return null;
                    }else{
                        result = JsonMapper.nonDefaultMapper().fromJson(str, HashMap.class);
                    }
                }
            }
        } catch (IOException e) {
            result = null;
            logger.error(e.getMessage());
            throw new RuntimeException("参数获取错误", e);
        }

        System.out.println(result);
        return result;
    }

    /**
     * json解析成map对象
     *
     * @param request 请求的对象
     * @return json解析成map对象
     */
    public static Map<?, ?> readJsonDataAes(final HttpServletRequest request) {
        BufferedReader reader;
        Map<?, ?> result = null;
        try {
            reader = request.getReader();

            StringBuffer json = new StringBuffer();

            String temp = "";
            String str = null;
            while (null != reader && (temp = reader.readLine()) != null) {
                json.append(temp);
            }
            if (null != json && !json.toString().trim().equals("")) {
                String jsonstring = json.toString();
//                System.out.println(jsonstring);

                if(jsonstring.contains("{")){
                    result = JsonMapper.nonDefaultMapper().fromJson(jsonstring.replace("{","{\"aes\":\"0\","), HashMap.class);
                }else{
                    if(jsonstring.contains("+") || jsonstring.contains("/") || jsonstring.contains("L") || jsonstring.contains("U") || jsonstring.contains("Q")){
                        byte [] bb = Encodes.decodeBase64(jsonstring);
                        String cJsonstring= AES256EncryptionUtils.parseByte2HexStr(bb);
                        str = AES256EncryptionUtils.decryptM(cJsonstring).replace("{","{\"aes\":\"0\",");
//                    System.out.println("old=========================="+str);
                    }else{
                        str = AES256EncryptionUtils.decryptM(jsonstring);
                        //String  str = URLEncoder.encode(AES256EncryptionUtils.decrypt(jsonstring), "UTF-8");
//                    System.out.println("new=========================="+str);
                    }
                    if(null ==str){
                        return null;
                    }else{
                        result = JsonMapper.nonDefaultMapper().fromJson(str, HashMap.class);
                    }
                }
            }
        } catch (IOException e) {
            result = null;
            logger.error(e.getMessage());
            throw new RuntimeException("参数获取错误", e);
        }

        System.out.println(result);
        return result;
    }
    /**
     * json解析成map对象
     *
     * @param request 请求的对象
     * @return json解析成map对象
     */
    public static Map<?, ?> readJsonDataNormal(final HttpServletRequest request) {
        BufferedReader reader;
        Map<?, ?> result = null;
        try {
            reader = request.getReader();

            StringBuffer json = new StringBuffer();

            String temp = "";
            String str = null;
            while (null != reader && (temp = reader.readLine()) != null) {
                json.append(temp);
            }
            String jsonstring = json.toString();
            result = JsonMapper.nonDefaultMapper().fromJson(jsonstring, HashMap.class);
        } catch (IOException e) {
            result = null;
            logger.error(e.getMessage());
            throw new RuntimeException("参数获取错误", e);
        }

        System.out.println(result);
        return result;
    }

    /**
     * 取得web的url的信息。例如：http://localhost:8080
     *
     * @param request
     * @return
     */
    public static String getRequestURLPath(final HttpServletRequest request) {
        //http://127.0.0.1:8080   不带有上下文-相当于域名。

        //http://portal.keyunxin.com:80
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String url = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(scheme)) {
            url = url + scheme;
        }

        if (StringUtils.isNotEmpty(serverName)) {
            url = url + "://" + serverName;
        }

        if (serverPort != 80) {
            url = url + ":" + serverPort;
        }

        //logger.info("环境中的 url = {} ", url);
        return url;
    }

   /* *//**
     * 得到物理的路径的地址（D:\apache-tomcat-7.0.41\wtpwebapps）    不包括上下文
     *
     * @param request 请求的对象
     * @return
     *//*
    public static String getRealPathSub(final HttpServletRequest request) {
        //url http://localhost:8080/ezineWeb/userfiles/1/images/111.jpg
        // D:\apache-tomcat-7.0.41\wtpwebapps\ezineWeb\
        String realPath = request.getSession().getServletContext().getRealPath(File.separator);
        //剔除\
        realPath = realPath.substring(0, realPath.length() -1);
        String subcontextPath = StringUtils.EMPTY;
        try {
            // /ezineWeb
            String contextPath = ServletContextFactory.getServletContext().getContextPath();
            if (StringUtils.isNotBlank(contextPath) && contextPath.length() > 1) {
                subcontextPath = contextPath.substring(1);
            } else {
                subcontextPath = contextPath;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        //绝对路径
        String realPathSub = realPath.replace(subcontextPath, "");
        logger.debug("文件的物理路径为:{} , ", realPathSub);
        return realPathSub;
    }
    *//**
     * 取得上下文
     *
     * @return
     */
    public static String getPathSub() {
        String subcontextPath = StringUtils.EMPTY;
        try {
            // /ezineWeb
            String contextPath = ServletContextFactory.getServletContext().getContextPath();
//			if (StringUtils.isNotBlank(contextPath) && contextPath.length() > 1) {
//				subcontextPath = contextPath.substring(1);
//			} else {
//				subcontextPath = contextPath;
//			}
            subcontextPath = contextPath;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
//		logger.debug("http 的 uri , ", subcontextPath);
        return subcontextPath;
    }
//	/**
//	 *
//	 * @param absPath
//	 * @return
//	 */
//	private boolean isExistsFile(final String realPathSub, final String imgPath) {
//		String absPath = this.urlDecode(realPathSub, imgPath);
//		boolean existsFile = FileUtils.existUrlPathsFile(absPath);
//		logger.debug("文件的路径为:{} , 文件的是否存在:{} ", absPath, existsFile);
//		return existsFile;
//	}
//
//	/**
//	 *
//	 * @param realPathSub
//	 * @param imgPath
//	 * @return
//	 */
//	private String urlDecode(final String realPathSub, final String imgPath) {
//		//取出图片的文件的名称
//		String urlDecoderPicTemp = imgPath.substring(imgPath.lastIndexOf("/") + 1);
//
//		//取得图片路径的前面部分（不包括图片的名称）
//		String imgPathTemp = imgPath.substring(0, imgPath.lastIndexOf("/")) + File.separator;
//
//		imgPathTemp = imgPathTemp + Encodes.urlDecode(urlDecoderPicTemp);
//
//		//url 的图片取得(文件的地址)
//		String absPath = realPathSub + imgPathTemp;
//		return absPath;
//	}
}
