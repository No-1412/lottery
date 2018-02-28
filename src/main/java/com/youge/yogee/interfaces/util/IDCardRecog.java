package com.youge.yogee.interfaces.util;

import com.google.common.collect.Maps;
import com.youge.yogee.common.mapper.JsonMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class IDCardRecog {
	
	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type",
	                        "application/x-www-form-urlencoded");

			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	        // 填入apix-key到HTTP header
	        connection.setRequestProperty("apix-key",  "b425fba1eee64efe5a65c219eddda6c1");
	        connection.setDoOutput(true);
	        connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	public static void main(String[] args) {
	    //发送 GET 请求
		String httpUrl = "http://a.apix.cn/apixlab/idcardrecog/idcardimage";

		//String httpArg = "{\"cmd\": \"idcard_front\",\"imgurl\": \"http://images0.cnblogs.com/blog2015/51591/201506/151449465134350.jpg\"}";

		String image =FaceRecognition.encodeImgageToBase64(new File("D:\\svn\\zhaoshi\\target\\zhaoshi-1.0.0\\userfiles\\img\\081dfaebbdfb4bd19041fd3985dd1006.jpg"));
		Map map = Maps.newHashMap();

		map.put("cmd","idcard_front");
		//map.put("imgurl","http://www.51wj.com/uploads/memberPicture/356343/cards/20111114231717101.jpg");
		map.put("pictype","jpg");
		map.put("pic",image);

		String httpArg = JsonMapper.getInstance().toJson(map);

		String jsonResult = request(httpUrl, httpArg);

		Map<?, ?> jsonData = JsonMapper.nonDefaultMapper().fromJson(
				jsonResult, HashMap.class);

		Map mapdata = (Map)jsonData.get("data");

		System.out.println("name----"+mapdata.get("name"));
		System.out.println("nation----"+mapdata.get("nation"));
		System.out.println("number----"+mapdata.get("number"));
		System.out.println("address----"+mapdata.get("address"));
		System.out.println(jsonResult);
	}



	//http://apix.cn/     账号 ccyouge@163.com	  1q2w3e4r5t
	public static String IDCard(String file){

		String httpUrl = "http://a.apix.cn/apixlab/idcardrecog/idcardimage";
		String image =FaceRecognition.encodeImgageToBase64(new File(file));
		Map map = Maps.newHashMap();
		map.put("cmd","idcard_front");
		map.put("pictype","jpg");
		map.put("pic",image);

		String httpArg = JsonMapper.getInstance().toJson(map);
		String jsonResult = request(httpUrl, httpArg);



		return jsonResult;
	}
}
