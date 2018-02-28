package com.youge.yogee.common.cloopen.demo;

import com.youge.yogee.common.cloopen.sdk.CCPRestSDK;

import java.util.HashMap;
import java.util.Set;


public class QuerySMSTemplate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, Object> result = null;

		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount("AccountSid", "AccountToken");// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId("AppId");// 初始化应用ID
		result = restAPI.QuerySMSTemplate("模板Id");//模板Id，不带此参数查询全部可用模板 

		System.out.println("QuerySMSTemplate result=" + result);
		
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				System.out.println(key +" = "+object);
			}
		}else{
			//异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}
	}

}
