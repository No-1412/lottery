package com.youge.yogee.interfaces.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hanvoncloud.HwCloud;
import com.youge.yogee.common.mapper.JsonMapper;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人脸图片认别
 * Created by Administrator on 2015/12/16.
 */
public class FaceRecognition {

    public static void main(String[] args) throws ParseException {

        String bb = "G:\\huai\\tmp\\1qa.jpg";

        String aa = "G:\\huai\\tmp\\2ws.jpg";

        String cc = "D:\\svn\\zhaoshi\\target\\zhaoshi-1.0.0\\userfiles\\img\\123456.jpg";

       // 1233211.jpg


      //  String aa ="C:\\Users\\Administrator\\Documents\\Tencent Files\\532156686\\FileRecv\\IMG_0932.JPG";
       // register("12332112332123",new File(aa));

        String json = compare(new File(aa),new File(bb));

        Map<?, ?> faceJson = JsonMapper.nonDefaultMapper().fromJson(
                json, HashMap.class);

        Map maps = (Map)faceJson.get("result");
        Map _ret = (Map)maps.get("_ret");
        Map reslist = (Map)_ret.get("reslist");

        String names = (String)reslist.get("name2|name1");


        if( Double.valueOf(names) > 70){
            System.out.println("11111");
        }else{
            System.out.println("2222");
        }

       String img = encodeImgageToBase64(new File("C:\\\\Users\\\\Administrator\\\\Documents\\\\Tencent Files\\\\532156686\\\\FileRecv\\\\IMG_0932.JPG"));

        shitu_image(img);

        String url ="http://api.hanvon.com/rt/ws/v1/ocr/idcard/recg?key=ff99cbd1-db9d-4717-a249-337d13ec761a&code=8d497db3-7341-4f1f-875a-2f5444884515";



        String key = "f151419b-ea48-47cf-a2c4-b1a317aad99e";
        //待识别身份证图片绝对路径
        String path = "G:\\huai\\tmp\\face27.jpg";

        String image =encodeImgageToBase64(new File("G:\\huai\\tmp\\qq-1.jpg"));


        String result = HwCloud.getInstance().recgIdcard(key, path);
        System.out.println(result);

    }





    //http://apistore.baidu.com/     账号suzf8   密码s119119
    public static  Integer register(String id ,File file){


        String httpUrl = "http://apis.baidu.com/idl_baidu/faceverifyservice/face_register";
        String img =encodeImgageToBase64(file);


        Map map = Maps.newHashMap();
        List list = Lists.newArrayList();
        List imglist = Lists.newArrayList();

        imglist.add(img);

        Map map1 = Maps.newHashMap();
        map1.put("username",id);
        map1.put("cmdid","1000");
        map1.put("logid","12345");
        map1.put("appid","02639e9ce32a2f681e2b135f4065d802");
        map1.put("clientip","10.23.34.5");
        map1.put("type","st_groupverify");
        map1.put("groupid","0");
        map1.put("versionnum","1.0.0.1");
        map1.put("images",imglist);

        list.add(map1);

        map.put("params",list);
        map.put("jsonrpc","2.0");
        map.put("method","Register");
        map.put("id","0");


        String httpArg = JsonMapper.nonDefaultMapper().toJson(map);

        String jsonResult = request(httpUrl, httpArg);
        System.out.println(jsonResult);



        Map<?, ?> FacejsonData = JsonMapper.nonDefaultMapper().fromJson(
                jsonResult, HashMap.class);
        Map<String, Object> mapData = new HashMap();
        Map result = (Map)FacejsonData.get("result");
        Map _ret = (Map)result.get("_ret");
        Integer errnum =(Integer)_ret.get("errnum");


        return errnum;

    }


    public static String Verify(String id,File file){


        String httpUrl = "http://apis.baidu.com/idl_baidu/faceverifyservice/face_recognition";

        String img =encodeImgageToBase64(file);


        Map map = Maps.newHashMap();
        List list = Lists.newArrayList();
        List imglist = Lists.newArrayList();



        imglist.add(img);


        Map map1 = Maps.newHashMap();
        map1.put("username",id);
        map1.put("cmdid","1000");
        map1.put("logid","12345");
        map1.put("appid","02639e9ce32a2f681e2b135f4065d802");
        map1.put("clientip","10.23.34.5");
        map1.put("type","st_groupverify");
        map1.put("groupid","0");
        map1.put("versionnum","1.0.0.1");
        map1.put("images",imglist);


        list.add(map1);


        map.put("params",list);
        map.put("jsonrpc","2.0");
        map.put("method","Verify");
        map.put("id","12");


        String httpArg = JsonMapper.nonDefaultMapper().toJson(map);

        String jsonResult = request(httpUrl, httpArg);
        System.out.println(jsonResult);

        return jsonResult;

    }


    public static String compare(File file1,File file2){


        String httpUrl = "http://apis.baidu.com/idl_baidu/faceverifyservice/face_recognition";

        String img1 =encodeImgageToBase64(file1);
        String img2 =encodeImgageToBase64(file2);

        Map map = Maps.newHashMap();
        List list = Lists.newArrayList();

        Map map1 = Maps.newHashMap();

        map1.put("cmdid","1000");
        map1.put("appid","02639e9ce32a2f681e2b135f4065d802");
        map1.put("clientip","10.23.34.5");
        map1.put("type","st_groupverify");
        map1.put("groupid","0");
        map1.put("versionnum","1.0.0.1");

        Map map2 = Maps.newHashMap();
        map2.put("name1","name1");
        map2.put("name2","name2");
        map1.put("usernames",map2);

        Map imgMap = Maps.newHashMap();
        imgMap.put("name2",img2);
        imgMap.put("name1",img1);
        map1.put("images",imgMap);


        Map catesMap = Maps.newHashMap();
        catesMap.put("name1","1");
        catesMap.put("name2","2");

        map1.put("cates",catesMap);
        list.add(map1);

        map.put("params",list);
        map.put("jsonrpc","2.0");
        map.put("method","Compare");
        map.put("id","12");

        String httpArg = JsonMapper.nonDefaultMapper().toJson(map);

        String jsonResult = request(httpUrl, httpArg);
        System.out.println(jsonResult);

        return jsonResult;
    }


    public static String Delete(String id){

        String httpUrl = "http://apis.baidu.com/idl_baidu/faceverifyservice/face_deleteuser";
        Map map = Maps.newHashMap();
        List list = Lists.newArrayList();

        Map map1 = Maps.newHashMap();
        map1.put("username",id);
        map1.put("cmdid","1000");
        map1.put("logid","12345");
        map1.put("appid","02639e9ce32a2f681e2b135f4065d802");
        map1.put("clientip","10.23.34.5");
        map1.put("type","st_groupverify");
        map1.put("groupid","0");
        map1.put("versionnum","1.0.0.1");
        list.add(map1);
        map.put("params",list);
       map.put("jsonrpc","2.0");
        map.put("method","Delete");
        map.put("id","12");


        String httpArg = JsonMapper.nonDefaultMapper().toJson(map);
        System.out.println(httpArg);
        String jsonResult = request(httpUrl, httpArg);
        System.out.println(jsonResult);

        return jsonResult;

    }

    //http://apistore.baidu.com/       账户suzf8  密码 s119119
    public static String shengfen(String id){

        String httpUrl = "http://apis.baidu.com/apistore/idservice/id";

        String jsonResult = requestGet(httpUrl, "id="+id);
        System.out.println(jsonResult);
        return jsonResult;
    }


    public static String shitu_image(String img){

        String httpUrl = "http://apis.baidu.com/image_search/shitu/shitu_image";
      //  String httpArg = "http://www.51wj.com/uploads/memberPicture/356343/cards/20111114231717101.jpg";
        String jsonResult = request(httpUrl, img);
        System.out.println(jsonResult);


        return jsonResult;
    }


/**
 * @param urlAll
 *            :请求接口
 * @param httpArg
 *            :参数
 * @return 返回结果
 */
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
        // 填入apikey到HTTP header
        connection.setRequestProperty("apikey",  "02639e9ce32a2f681e2b135f4065d802");
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


    /**
     * @param urlAll
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    public static String requestGet(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "02639e9ce32a2f681e2b135f4065d802");
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


    public static String encodeImgageToBase64(File imageFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
    }

}
