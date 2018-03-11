package com.youge.yogee.interfaces.emay.emay;

import com.youge.yogee.interfaces.emay.emay.util.DateUtil;
import com.youge.yogee.interfaces.emay.emay.util.Md5;
import com.youge.yogee.interfaces.emay.emay.util.http.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RetrieveReportExample {

	public static void main(String[] args) {
		// appId
		String appId = "EUCe-EMt-SMS9-XXXXX";// 请联系销售，或者在页面中 获取
		// 密钥
		String secretKey = "1234567893214567";// 请联系销售，或者在页面中 获取
		// 接口地址
		String host = "127.0.0.1:8999";// 请联系销售获取
		// 编码
		String encode = "UTF-8";
		// 时间戳
		String timestamp = DateUtil.toString(new Date(), "yyyyMMddHHmmss");
		// 签名
		String sign = Md5.md5((appId + secretKey + timestamp).getBytes());
		String startTime = "20170101120000";
		String endTime = "20170101120000";
		String smsId = "123123123,321321321";
		getReport(appId, host, encode, timestamp, sign, startTime, endTime, smsId);
	}

	/**
	 * 获取状态报告
	 */
	private static void getReport(String appId, String host, String encode,// 时间戳
			String timestamp, String sign, String startTime, String endTime, String smsId) {
		System.out.println("=============begin getReport==================");
		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", appId);
		params.put("timestamp", timestamp);
		params.put("sign", sign);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		if (null != smsId && !"".equals(smsId)) {
			params.put("smsId", smsId);
		}
		String url = "http://" + host + "/report/retrieveReport";
		String result = request(params, url);
		System.out.println(result);
		System.out.println("=============end getReport==================");
	}

	/**
	 * 公共请求方法
	 */
	public static String request(Map<String, String> params, String url) {
		EmayHttpRequestKV request = new EmayHttpRequestKV(url, "UTF-8", "POST", null, null, params);
		EmayHttpClient client = new EmayHttpClient();
		String json = null;
		try {
			String mapst = "";
			for (String key : params.keySet()) {
				String value = params.get(key);
				mapst += key + "=" + value + "&";
			}
			mapst = mapst.substring(0, mapst.length() - 1);
			System.out.println("request params: " + mapst);
			EmayHttpResponseString res = client.service(request, new EmayHttpResponseStringPraser());
			if (res == null) {
				System.err.println("请求接口异常");
				return null;
			}
			if (res.getResultCode().equals(EmayHttpResultCode.SUCCESS)) {
				if (res.getHttpCode() == 200) {
					json = res.getResultString();
					System.out.println("response json: " + json);
				} else {
					System.out.println("请求接口异常,请求码:" + res.getHttpCode());
				}
			} else {
				System.out.println("请求接口网络异常:" + res.getResultCode().getCode());
			}
		} catch (Exception e) {
			System.err.println("解析失败");
			e.printStackTrace();
		}
		return json;
	}
}
