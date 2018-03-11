package com.youge.yogee.interfaces.emay.emay.test.performance;


import com.youge.yogee.interfaces.emay.emay.ResultModel;
import com.youge.yogee.interfaces.emay.emay.eucp.inter.framework.dto.CustomSmsIdAndMobile;
import com.youge.yogee.interfaces.emay.emay.eucp.inter.framework.dto.CustomSmsIdAndMobileAndContent;
import com.youge.yogee.interfaces.emay.emay.eucp.inter.http.v1.dto.request.*;
import com.youge.yogee.interfaces.emay.emay.util.AES;
import com.youge.yogee.interfaces.emay.emay.util.GZIPUtils;
import com.youge.yogee.interfaces.emay.emay.util.JsonHelper;
import com.youge.yogee.interfaces.emay.emay.util.http.*;
import com.youge.yogee.interfaces.emay.request.SmsPersonalityRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class TestPerformance{

	// 线程数
	private static int threadNumber = 5;
	// 每个线程每个APPID发送次数
	private static int totalOnceAppid = 1000;
	// 是否压缩
	private static boolean isGzip = true;
	// 接口地址
	private static String host = "100.100.10.31:8080";
	// 加密算法
	private static String algorithm = "AES/ECB/PKCS5Padding";
	// appids
	private static Map<String,String> appids = new HashMap<String, String>();
	static{
		appids.put("EUCP-EMY-SMS1-MEZMO", "C2BFD065DF731F44");
		appids.put("EUCP-EMY-SMS1-MEZM1", "C2BFD065DF771F42");
		appids.put("EUCP-EMY-SMS1-MEZM2", "C2BFD065DF771F34");
		appids.put("EUCP-EMY-SMS1-MEZM3", "C2BFD065DF771F44");
		appids.put("EUCP-EMY-SMS1-MEZM4", "C2BFD065DF773244");
	}
	// 计数，不用修改
	private static CountDownLatch c = new CountDownLatch(threadNumber);
	// 扩展码
	private static String extendCode = "1234";
	
	public static void main(String[] args) {
		getReport();
		getMo();
		getBalance();
		setSingleSms();
		setBatchOnlySms();
		setBatchSms();
	}
	
	/**
	 * 获取余额
	 *
	 */
	private static void getBalance() {
		Thread[] threads = new Thread[threadNumber];
		for(int i = 0 ; i < threadNumber ; i ++){
			threads[i] = new Thread(){
				public void run() {
					for(int j = 0 ; j < totalOnceAppid ; j ++){
						for(String  appId : appids.keySet()){
							String secretKey = appids.get(appId);
							BalanceRequest pamars = new BalanceRequest();
							ResultModel result = request(appId,secretKey,algorithm,pamars, "http://" + host + "/inter/getBalance",isGzip);
							if(!"SUCCESS".equals(result.getCode())){
								System.err.println("error , stop ! \t" + result.getResult());
							}
						}
					}
					c.countDown();
				};
			};
		}
		long start = System.currentTimeMillis();
		for(int i = 0 ; i < threadNumber ; i ++){
			threads[i].start();
		}
		try {
			c.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("总线程数：" + ( threadNumber));
		System.out.println("每个线程请求数：" + ( totalOnceAppid * appids.size()));
		System.out.println("每个Appid请求数：" + ( totalOnceAppid * threadNumber));
		System.out.println("总请求数：" + ( threadNumber * totalOnceAppid * appids.size() ));
		System.out.println("总耗时：" + (end - start) + "ms");
		System.out.println("TPS：" + threadNumber * totalOnceAppid * appids.size()   * 1000 / ((int)(end - start)));
	}

	/**
	 * 获取状态报告
	 *
	 */
	private static void getReport() {
		Thread[] threads = new Thread[threadNumber];
		for(int i = 0 ; i < threadNumber ; i ++){
			threads[i] = new Thread(){
				public void run() {
					for(int j = 0 ; j < totalOnceAppid ; j ++){
						for(String  appId : appids.keySet()){
							String secretKey = appids.get(appId);
							ReportRequest pamars = new ReportRequest();
							ResultModel result = request(appId,secretKey,algorithm,pamars, "http://" + host + "/inter/getReport",isGzip);
							if(!"SUCCESS".equals(result.getCode())){
								System.err.println("error , stop ! \t" + result.getResult());
							}
						}
					}
					c.countDown();
				};
			};
		}
		long start = System.currentTimeMillis();
		for(int i = 0 ; i < threadNumber ; i ++){
			threads[i].start();
		}
		try {
			c.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("总线程数：" + ( threadNumber));
		System.out.println("每个线程请求数：" + ( totalOnceAppid * appids.size()));
		System.out.println("每个Appid请求数：" + ( totalOnceAppid * threadNumber));
		System.out.println("总请求数：" + ( threadNumber * totalOnceAppid * appids.size() ));
		System.out.println("总耗时：" + (end - start) + "ms");
		System.out.println("TPS：" + threadNumber * totalOnceAppid * appids.size()   * 1000 / ((int)(end - start)));
	}
	
	/**
	 * 获取上行
	 *
	 */
	private static void getMo() {
		Thread[] threads = new Thread[threadNumber];
		for(int i = 0 ; i < threadNumber ; i ++){
			threads[i] = new Thread(){
				public void run() {
					for(int j = 0 ; j < totalOnceAppid ; j ++){
						for(String  appId : appids.keySet()){
							String secretKey = appids.get(appId);
							MoRequest pamars = new MoRequest();
							ResultModel result = request(appId,secretKey,algorithm,pamars, "http://" + host + "/inter/getMo",isGzip);
							if(!"SUCCESS".equals(result.getCode())){
								System.err.println("error , stop ! \t" + result.getResult());
							}
						}
					}
					c.countDown();
				};
			};
		}
		long start = System.currentTimeMillis();
		for(int i = 0 ; i < threadNumber ; i ++){
			threads[i].start();
		}
		try {
			c.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("总线程数：" + ( threadNumber));
		System.out.println("每个线程请求数：" + ( totalOnceAppid * appids.size()));
		System.out.println("每个Appid请求数：" + ( totalOnceAppid * threadNumber));
		System.out.println("总请求数：" + ( threadNumber * totalOnceAppid * appids.size() ));
		System.out.println("总耗时：" + (end - start) + "ms");
		System.out.println("TPS：" + threadNumber * totalOnceAppid * appids.size()  * 1000 / ((int)(end - start)));
	}
	
	/**
	 * 发送单条短信
	 *
	 */
	private static void setSingleSms() {
		// 内容
		final String content = "你好今天天气不错，挺风和日丽的";
		// 手机号
		final String mobile = "18601010291";
		
		final AtomicLong longs = new AtomicLong(0);
		
		Thread[] threads = new Thread[threadNumber];
		for(int i = 0 ; i < threadNumber ; i ++){
			threads[i] = new Thread(){
				public void run() {
					for(int j = 0 ; j < totalOnceAppid ; j ++){
						for(String  appId : appids.keySet()){
							String secretKey = appids.get(appId);
							SmsSingleRequest pamars = new SmsSingleRequest();
							pamars.setContent(content);
							pamars.setCustomSmsId(mobile);
							pamars.setExtendedCode(extendCode);
							pamars.setMobile(mobile);
							long start = System.currentTimeMillis();
							ResultModel result = request(appId,secretKey,algorithm,pamars, "http://" + host + "/inter/sendSingleSMS",isGzip);
							long end = System.currentTimeMillis();
							if(!"SUCCESS".equals(result.getCode())){
								System.err.println("error , stop ! \t" + result.getResult());
							}
							long uui = end - start;
							longs.addAndGet(uui);
						}
					}
					c.countDown();
				};
			};
		}
		long start = System.currentTimeMillis();
		for(int i = 0 ; i < threadNumber ; i ++){
			threads[i].start();
		}
		try {
			c.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("总线程数：" + ( threadNumber));
		System.out.println("每个线程发送数：" + ( totalOnceAppid * appids.size()));
		System.out.println("每个Appid发送数：" + ( totalOnceAppid * threadNumber ));
		System.out.println("总发送数：" + ( threadNumber * totalOnceAppid * appids.size()));
		System.out.println("总耗时：" + (end - start) + "ms");
		System.out.println("TPS：" + threadNumber * totalOnceAppid * appids.size() * 1000 / ((int)(end - start)));
		System.out.println("平均每次耗时：" + ( longs.get() / ( threadNumber * totalOnceAppid * appids.size()) ) + "ms");
	}
	
	/**
	 * 发送批次短信
	 *
	 */
	private static void setBatchOnlySms() {
		// 内容
		final String content = "你好今天天气不错，挺风和日丽的";
		// 手机号
		final String[] onceMoiles = {"18601010291","18601010292","18601010293","18601010294","18601010295"};
		Thread[] threads = new Thread[threadNumber];
		for(int i = 0 ; i < threadNumber ; i ++){
			threads[i] = new Thread(){
				public void run() {
					for(int j = 0 ; j < totalOnceAppid ; j ++){
						for(String  appId : appids.keySet()){
							String secretKey = appids.get(appId);
							SmsBatchOnlyRequest pamars = new SmsBatchOnlyRequest();
							pamars.setMobiles(onceMoiles);
							pamars.setExtendedCode(extendCode);
							pamars.setContent(content);
							ResultModel result = request(appId,secretKey,algorithm,pamars, "http://" + host + "/inter/sendBatchOnlySMS",isGzip);
							if(!"SUCCESS".equals(result.getCode())){
								System.err.println("error , stop ! \t" + result.getResult());
							}
						}
					}
					c.countDown();
				};
			};
		}
		long start = System.currentTimeMillis();
		for(int i = 0 ; i < threadNumber ; i ++){
			threads[i].start();
		}
		try {
			c.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("总线程数：" + ( threadNumber));
		System.out.println("每个线程发送数：" + ( totalOnceAppid * appids.size() * onceMoiles.length));
		System.out.println("每个Appid发送数：" + ( totalOnceAppid * threadNumber  * onceMoiles.length));
		System.out.println("总发送数：" + ( threadNumber * totalOnceAppid * appids.size()  * onceMoiles.length ));
		System.out.println("总耗时：" + (end - start) + "ms");
		System.out.println("TPS：" + threadNumber * totalOnceAppid * appids.size()  * onceMoiles.length * 1000 / (end - start));
	}
	
	/**
	 * 发送批次短信
	 *
	 */
	private static void setBatchSms() {
		// 内容
		final String content = "你好今天天气不错，挺风和日丽的";
		// 手机号
		final String[] onceMoiles = {"18601010291","18601010292","18601010293","18601010294","18601010295"};
		Thread[] threads = new Thread[threadNumber];
		for(int i = 0 ; i < threadNumber ; i ++){
			threads[i] = new Thread(){
				public void run() {
					for(int j = 0 ; j < totalOnceAppid ; j ++){
						for(String  appId : appids.keySet()){
							String secretKey = appids.get(appId);
							CustomSmsIdAndMobile[] css = new CustomSmsIdAndMobile[onceMoiles.length];
							for(int i = 0 ; i < onceMoiles.length ; i ++){
								css[i] = new CustomSmsIdAndMobile(onceMoiles[i], onceMoiles[i]);
							}
							SmsBatchRequest pamars = new SmsBatchRequest();
							pamars.setSmses(css);
							pamars.setExtendedCode(extendCode);
							pamars.setContent(content);
							ResultModel result = request(appId,secretKey,algorithm,pamars, "http://" + host + "/inter/sendBatchSMS",isGzip);
							if(!"SUCCESS".equals(result.getCode())){
								System.err.println("error , stop ! \t" + result.getResult());
							}
						}
					}
					c.countDown();
				};
			};
		}
		long start = System.currentTimeMillis();
		for(int i = 0 ; i < threadNumber ; i ++){
			threads[i].start();
		}
		try {
			c.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("总线程数：" + ( threadNumber));
		System.out.println("每个线程发送数：" + ( totalOnceAppid * appids.size() * onceMoiles.length));
		System.out.println("每个Appid发送数：" + ( totalOnceAppid * threadNumber  * onceMoiles.length));
		System.out.println("总发送数：" + ( threadNumber * totalOnceAppid * appids.size()  * onceMoiles.length ));
		System.out.println("总耗时：" + (end - start) + "ms");
		System.out.println("TPS：" + threadNumber * totalOnceAppid * appids.size()  * onceMoiles.length * 1000 / (end - start));
	}
	

	
	
	public static ResultModel request(String appId,String secretKey,String algorithm,Object content, String url,final boolean isGzip) {
		Map<String, String> headers = new HashMap<String, String>();
		EmayHttpRequestBytes request = null;
		try {
			headers.put("appId", appId);
			String requestJson = JsonHelper.toJsonString(content);
			byte[] bytes = requestJson.getBytes("UTF-8");
			if (isGzip) {
				headers.put("gzip", "on");
				bytes = GZIPUtils.compress(bytes);
			}
			byte[] parambytes = AES.encrypt(bytes, secretKey.getBytes("UTF-8"), algorithm);
			request = new EmayHttpRequestBytes(url, "UTF-8", "POST", headers, null, parambytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		EmayHttpClient client = new EmayHttpClient();
		String code = null;
		String result = null;
		try {
			EmayHttpResponseBytes res = client.service(request, new EmayHttpResponseBytesPraser());
			if (res.getResultCode().equals(EmayHttpResultCode.SUCCESS)) {
				if (res.getHttpCode() == 200) {
					code = res.getHeaders().get("result");
					if (code.equals("SUCCESS")) {
						byte[] data = res.getResultBytes();
						data = AES.decrypt(data, secretKey.getBytes("UTF-8"), algorithm);
						if (isGzip) {
							data = GZIPUtils.decompress(data);
						}
						result = new String(data, "UTF-8");
					}
				} else {
					System.out.println("请求接口异常,请求码:" + res.getHttpCode());
				}
			} else {
				System.out.println("请求接口网络异常:" + res.getResultCode().getCode());
			}
		} catch (Exception e) {
			System.out.println("解析失败");
			e.printStackTrace();
		}
		ResultModel re = new ResultModel(code, result);
		return re;
	}

}
