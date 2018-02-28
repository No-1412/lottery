package com.youge.yogee.interfaces.util;

import com.google.common.collect.Maps;
import com.youge.yogee.common.mapper.JsonMapper;
import com.youge.yogee.common.utils.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2015/12/4.
 */
public class util {

    //米转换成公里
    public static String getDistance(Integer distance) {
        if (distance < 1000) {
            return distance + "米";
        } else if (distance > 1000) {
            Integer aa = distance / 1000;
            Integer bb = distance % aa;
            return aa + "公里";
        }
        return String.valueOf(distance);
    }

    /**
     * api 文档 http://developer.baidu.com/map/index.php?title=webapi/guide/webservice-geocoding
     * <p/>
     * http://api.map.baidu.com/geocoder/v2/?ak=69E14058e3f54cb11254e207a119bed7&output=json&address=%E4%B9%9D%E8%B0%B7%E5%8F%A3%E8%87%AA%E7%84%B6%E9%A3%8E%E6%99%AF%E5%8C%BA&city=%E5%8C%97%E4%BA%AC
     * <p/>
     * 设定坐标coordinate
     *
     * @param
     */
    public static Map setCoordinate(String address, String captureType) {
        //lng	float	经度值
        //lat	float	纬度值
        Map map = Maps.newHashMap();
        Connection connectNameType = null;
//		String name = temp.getName();

        //需要把地址输入到相应城市的百度地图获取坐标系统里自动获取坐标，地址带括号及括号里内容去掉进行搜索

        int index = address.indexOf("(");
        if (index > 0) {
            address = address.substring(0, index);
        }
        // logger.debug("取坐标系统中的地址 = " + address);
        System.out.println("取坐标系统中的地址 = " + address);

        //  String dictlable = DictUtils.getDictLabel(captureType, FilmConstants.APP_SHOP_CITY_TYPE, "1");

        if (StringUtils.isNotBlank(address)) {
            try {
                String nameutf8Str = URLEncoder.encode(address, "UTF-8");
                String beijingStr = URLEncoder.encode(captureType, "UTF-8");

                String baiduak = "http://api.map.baidu.com/geocoder/v2/?ak=69E14058e3f54cb11254e207a119bed7&";
                //%E4%B9%9D%E8%B0%B7%E5%8F%A3%E8%87%AA%E7%84%B6%E9%A3%8E%E6%99%AF%E5%8C%BA
                connectNameType = Jsoup.connect(baiduak + "output=json&address=" + nameutf8Str + "&city=" + beijingStr);//
                connectNameType.userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36")//设置urer-agent
                        .timeout(300000); //设置连接超时时间

                Document detailDoc = connectNameType.get();
//		        System.out.println(detailDoc.text());
                Map<?, ?> jsonData = JsonMapper.nonDefaultMapper().fromJson(detailDoc.text(), HashMap.class);

                //status: 0, 返回结果状态值， 成功返回0
                Integer status = (Integer) jsonData.get("status");
                //返回结果状态值， 成功返回0
                if (status.intValue() == 0) {
                    Map<?, ?> resultMap = (Map) jsonData.get("result");
                    Map<?, ?> locationMap = (Map) resultMap.get("location");
//			        System.out.println(locationMap.get("lng"));
//			        System.out.println(locationMap.get("lat"));
                    //坐标		经纬度

                    map.put("lat", locationMap.get("lat").toString());
                    map.put("lng", locationMap.get("lng").toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                connectNameType = null;
            }
        }
        return map;
    }


    public static void main(String[] args) throws Exception {


        Map map = Maps.newHashMap();


        map = util.setCoordinate("吉林省 长春市 朝阳区 普阳街与阳光路交汇规划院宿舍584A蓝天社区门牌号2门301", "长春市");

        System.out.println("---");
    }

    /**
     * 生成订单号
     *
     * @param prefix
     * @param no 随机数 或其他 这里调用的是随机数
     * @return
     */
    public static String genOrderNo(String prefix, String no) {
        String timeMillis = "" + System.currentTimeMillis();
        int leftLen = timeMillis.length() / 2;
        String leftStr = org.apache.commons.lang3.StringUtils.left(timeMillis, leftLen);
        String orderNo = prefix + leftStr + no + org.apache.commons.lang3.StringUtils.right(timeMillis, timeMillis.length() - leftLen);
        return orderNo;
    }
    /**
     * 产生4位随机数(0000-9999)
     *
     * @return 4位随机数
     */
    public static String getFourRandom() {
        Random random = new Random();
        String fourRandom = random.nextInt(100000) + "";
        int randLength = fourRandom.length();
        if (randLength < 5) {
            for (int i = 1; i <= 5 - randLength; i++) {
                fourRandom = "0" + fourRandom;
            }
        }
        return fourRandom;
    }
}
