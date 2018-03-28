package com.youge.yogee.interfaces.util;

import com.youge.yogee.common.mapper.JsonMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class IDCardRecog {

	private static final String appcode = "56366a54460b491aba7cba9cc5696f6e";


	public static Boolean IDCardVerify(String idcard,String name) {
		String host = "https://eid.shumaidata.com";
		String path = "/eid/check";
		String method = "POST";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		//根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		Map<String, String> querys = new HashMap<>();
		Map<String, String> bodys = new HashMap<>();
		bodys.put("idcard", idcard);
		bodys.put("name", name);

		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			System.out.println(response.toString());
			//获取response的body
			String s = EntityUtils.toString(response.getEntity());
			Map<String ,Object> access_token = JsonMapper.getInstance().fromJson(s, Map.class );
			Map<String ,Object> b = (Map<String ,Object>)(access_token.get("result"));
			if(b.get("res").toString().equals("1")){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}





}
