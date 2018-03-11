package com.youge.yogee.interfaces.emay.emay;

import com.google.gson.reflect.TypeToken;
import com.youge.yogee.interfaces.emay.emay.eucp.inter.http.v1.dto.response.*;
import com.youge.yogee.interfaces.emay.emay.util.DateUtil;
import com.youge.yogee.interfaces.emay.emay.util.JsonHelper;
import com.youge.yogee.interfaces.emay.emay.util.Md5;
import com.youge.yogee.interfaces.emay.emay.util.http.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SimpleExample {

	public static void main(String[] args) {
		// appId
		String appId = "EUCe-EMt-SMS9-XXXXX";// 请联系销售，或者在页面中 获取
		// 密钥
		String secretKey = "1234567893214567";// 请联系销售，或者在页面中 获取
		// 时间戳
		String timestamp = DateUtil.toString(new Date(), "yyyyMMddHHmmss");
		// 签名
		String sign = Md5.md5((appId + secretKey + timestamp).getBytes());
		// 接口地址
		String host = "127.0.0.1:8999";// 请联系销售获取
		// 获取余额
		getBalance(appId, sign, timestamp, host);
		// 获取状态报告
		getReport(appId, sign, timestamp, host);
		// 获取上行
		getMo(appId, sign, timestamp, host);
		// 发送批次短信,定时时间格式yyyyMMddHHmmss
		setSms(appId, sign, timestamp, host, "你好今天天气不错，挺风和日丽的&1=D$", "18001000000,18001000001", "839273940", null, "20170506120000");
		// 发送个性短信
		Map<String, String> mobileAndContents = new HashMap<String, String>();
		mobileAndContents.put("18001000011", "天气不错啊1&32");
		mobileAndContents.put("18001000012", "天气不错啊1&31");
		mobileAndContents.put("18001000013", "天气不错啊1&30");
		setPersonalitySms(appId, sign, timestamp, host, mobileAndContents, "83927391", "012", "20170506120001");
		// 发送语音短信
		setVoiceSms(appId, sign, timestamp, host, "1234", "18001000000", "839273940", null);
		// 获取语音状态报告
		getVoiceReport(appId, sign, timestamp, host);
		// 获取语音余额
		getVoiceBalance(appId, sign, timestamp, host);
	}

	/**
	 * 获取余额
	 */
	private static void getBalance(String appId, String sign, String timestamp, String host) {
		System.out.println("=============begin getBalance==================");
		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", appId);
		params.put("sign", sign);
		params.put("timestamp", timestamp);
		String json = request(params, "http://" + host + "/simpleinter/getBalance");
		if (json != null) {
			ResponseData<BalanceResponse> data = JsonHelper.fromJson(new TypeToken<ResponseData<BalanceResponse>>() {
			}, json);
			String code = data.getCode();
			if ("SUCCESS".equals(code)) {
				System.out.println("result data : " + data.getData().getBalance());
			}
		}
		System.out.println("=============end getBalance==================");
	}

	/**
	 * 获取语音余额
	 */
	private static void getVoiceBalance(String appId, String sign, String timestamp, String host) {
		System.out.println("=============begin getBalance==================");
		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", appId);
		params.put("sign", sign);
		params.put("timestamp", timestamp);
		String json = request(params, "http://" + host + "/voice/getBalance");
		if (json != null) {
			ResponseData<BalanceResponse> data = JsonHelper.fromJson(new TypeToken<ResponseData<BalanceResponse>>() {
			}, json);
			String code = data.getCode();
			if ("SUCCESS".equals(code)) {
				System.out.println("result data : " + data.getData().getBalance());
			}
		}
		System.out.println("=============end getBalance==================");
	}

	/**
	 * 获取状态报告
	 */
	private static void getReport(String appId, String sign, String timestamp, String host) {
		System.out.println("=============begin getReport==================");
		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", appId);
		params.put("sign", sign);
		params.put("timestamp", timestamp);
		params.put("number", "500");
		String json = request(params, "http://" + host + "/simpleinter/getReport");
		if (json != null) {
			ResponseData<ReportResponse[]> data = JsonHelper.fromJson(new TypeToken<ResponseData<ReportResponse[]>>() {
			}, json);
			String code = data.getCode();
			if ("SUCCESS".equals(code)) {
				for (ReportResponse d : data.getData()) {
					System.out.println("result data : " + d.getMobile() + "," + d.getExtendedCode() + "," + d.getMobile() + "," + d.getCustomSmsId() + "," + d.getSmsId() + "," + d.getState() + ","
							+ d.getDesc() + "," + d.getSubmitTime() + "," + d.getReceiveTime());
				}
			}
		}
		System.out.println("=============end getReport==================");
	}

	/**
	 * 获取语音状态报告
	 */
	private static void getVoiceReport(String appId, String sign, String timestamp, String host) {
		System.out.println("=============begin getReport==================");
		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", appId);
		params.put("sign", sign);
		params.put("timestamp", timestamp);
		params.put("number", "500");
		String json = request(params, "http://" + host + "/voice/getReport");
		if (json != null) {
			ResponseData<ReportResponse[]> data = JsonHelper.fromJson(new TypeToken<ResponseData<ReportResponse[]>>() {
			}, json);
			String code = data.getCode();
			if ("SUCCESS".equals(code)) {
				for (ReportResponse d : data.getData()) {
					System.out.println("result data : " + d.getMobile() + "," + d.getExtendedCode() + "," + d.getMobile() + "," + d.getCustomSmsId() + "," + d.getSmsId() + "," + d.getState() + ","
							+ d.getDesc() + "," + d.getSubmitTime() + "," + d.getReceiveTime());
				}
			}
		}
		System.out.println("=============end getReport==================");
	}

	/**
	 * 获取上行
	 */
	private static void getMo(String appId, String sign, String timestamp, String host) {
		System.out.println("=============begin getMo==================");
		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", appId);
		params.put("sign", sign);
		params.put("timestamp", timestamp);
		params.put("number", "500");
		String json = request(params, "http://" + host + "/simpleinter/getMo");
		if (json != null) {
			ResponseData<MoResponse[]> data = JsonHelper.fromJson(new TypeToken<ResponseData<MoResponse[]>>() {
			}, json);
			String code = data.getCode();
			if ("SUCCESS".equals(code)) {
				for (MoResponse d : data.getData()) {
					System.out.println("result data:" + d.getMobile() + "," + d.getExtendedCode() + "," + d.getMobile() + "," + d.getMoTime());
				}
			}
		}
		System.out.println("=============end getMo==================");
	}

	/**
	 * 发送批次短信
	 */
	private static void setSms(String appId, String sign, String timestamp, String host, String content, String mobiles, String customSmsId, String extendedCode, String timerTime) {
		System.out.println("============= setSms==================");
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("appId", appId);
			params.put("sign", sign);
			params.put("timestamp", timestamp);
			params.put("mobiles", mobiles);
			params.put("content", URLEncoder.encode(content, "utf-8"));
			if (customSmsId != null) {
				params.put("customSmsId", customSmsId);
			}
			if (timerTime != null) {
				params.put("timerTime", timerTime);
			}
			if (extendedCode != null) {
				params.put("extendedCode", extendedCode);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String json = request(params, "http://" + host + "/simpleinter/sendSMS");
		if (json != null) {
			ResponseData<SmsResponse[]> data = JsonHelper.fromJson(new TypeToken<ResponseData<SmsResponse[]>>() {
			}, json);
			String code = data.getCode();
			if ("SUCCESS".equals(code)) {
				for (SmsResponse d : data.getData()) {
					System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
				}
			}
		}
		System.out.println("=============end setSms==================");
	}

	// 发送语音短信
	private static void setVoiceSms(String appId, String sign, String timestamp, String host, String content, String mobile, String customSmsId, String extendedCode) {
		System.out.println("============= setSms==================");
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("appId", appId);
			params.put("sign", sign);
			params.put("timestamp", timestamp);
			params.put("mobile", mobile);
			params.put("content", URLEncoder.encode(content, "utf-8"));
			if (customSmsId != null) {
				params.put("customSmsId", customSmsId);
			}
			if (extendedCode != null) {
				params.put("extendedCode", extendedCode);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String json = request(params, "http://" + host + "/voice/sendSMS");
		if (json != null) {
			ResponseData<SmsResponse> data = JsonHelper.fromJson(new TypeToken<ResponseData<SmsResponse>>() {
			}, json);
			String code = data.getCode();
			if ("SUCCESS".equals(code)) {
				System.out.println("data:" + data.getData().getMobile() + "," + data.getData().getSmsId() + "," + data.getData().getCustomSmsId());
			}
		}
		System.out.println("=============end setSms==================");
	}

	/**
	 * 发送个性短信
	 */
	private static void setPersonalitySms(String appId, String sign, String timestamp, String host, Map<String, String> mobileAndContents, String customSmsId, String extendedCode, String timerTime) {
		System.out.println("=============setPersonalitySms ==================");
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("appId", appId);
			params.put("sign", sign);
			params.put("timestamp", timestamp);
			for (String mobile : mobileAndContents.keySet()) {
				params.put(mobile, URLEncoder.encode(mobileAndContents.get(mobile), "utf-8"));
			}
			if (customSmsId != null) {
				params.put("customSmsId", customSmsId);
			}
			if (timerTime != null) {
				params.put("timerTime", timerTime);
			}
			if (extendedCode != null) {
				params.put("extendedCode", extendedCode);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String json = request(params, "http://" + host + "/simpleinter/sendPersonalitySMS");
		if (json != null) {
			ResponseData<SmsResponse[]> data = JsonHelper.fromJson(new TypeToken<ResponseData<SmsResponse[]>>() {
			}, json);
			String code = data.getCode();
			if ("SUCCESS".equals(code)) {
				for (SmsResponse d : data.getData()) {
					System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
				}
			}
		}
		System.out.println("=============end setPersonalitySms==================");
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
