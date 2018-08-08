/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youge.yogee.common.utils;

import com.google.common.base.Strings;
import org.apache.commons.lang.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ?????????, ???org.apache.commons.lang.time.DateUtils??
 *
 * @author ThinkGem
 * @version 2013-3-15
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {

    /**
     * 示例：2018-02-25
     */
    public static final String YMD = "yyyy-MM-dd";

    /**
     * 示例：2018-02-25 15:01:30
     */
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy-MM-dd HH:mm:ss:SSS", "yyyy-MM-dd HH:mm:ss.0",};

    /**
     * ??????????????? ?????yyyy-MM-dd??
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * ??????????????? ?????yyyy-MM-dd?? pattern???????"yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * ???????????? ???????yyyy-MM-dd?? pattern???????"yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * ?????????????????????????yyyy-MM-dd HH:mm:ss??
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * ?????????????????????????yyyy-MM-dd HH:mm:ss??
     */
    public static String formatDateTimSSS(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss:SSS");
    }


    /**
     * ?????????????????????????yyyy-MM-dd HH:mm??
     */
    public static String formatDateTimeS(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm");
    }


    /**
     * ?????????????? ?????HH:mm:ss??
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    public static String getTimeHao(Date date) {
        return formatDate(date, "HH:mm:ss:SSS");
    }


    /**
     * ??????????????????? ?????yyyy-MM-dd HH:mm:ss??
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * ?????????????? ?????yyyy??
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * ??????????????? ?????MM??
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * ???????????? ?????dd??
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * ??????????????? ?????E???????
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }


    public static String getHaoMiao() {
        return formatDate(new Date(), "SSS");
    }

    /**
     * ??????????????????? ???
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * ????????????
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }


    public static Date getDateStart(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateEnd(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static int getIntervalDays(Date startday, Date endday) {
        if (startday.after(endday)) {
            Date cal = startday;
            startday = endday;
            endday = cal;
        }
        long sl = startday.getTime();
        long el = endday.getTime();
        long ei = el - sl;
        return (int) (ei / (1000 * 60 * 60 * 24));
    }


    public static boolean isDateBefore(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).before(df.parse(date2));
        } catch (ParseException e) {
            System.out.println("dddddd" + e.getMessage());
            return false;
        }
    }


    public static String Countdown(Date startday, Date endDay) {

        //Date date = new Date();
        long l = endDay.getTime() - startday.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        if (day != 0) {
            if (day > 30) {
                return DateUtils.formatDate(startday, "yyyy/MM/dd");
            } else {

                return day + "天前";
            }
        } else {
            if (hour != 0) {

                return hour + "小时前";
            } else {
                if (min != 0) {

                    return min + "分钟前";
                } else {
                    if (s != 0) {

                        return s + "秒前";
                    }
                }
            }
        }
        return DateUtils.formatDate(startday, "yyyy-MM-dd");
    }


    public static String getDateStr(Date startday) {

        Date date = new Date();
        long l = date.getTime() - startday.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        if (day != 0) {
            if (day > 30) {
                return DateUtils.formatDate(startday, "yyyy/MM/dd");
            } else {

                return day + "天前";
            }
        } else {
            if (hour != 0) {

                return hour + "小时前";
            } else {
                if (min != 0) {

                    return min + "分钟前";
                } else {
                    if (s != 0) {

                        return s + "秒前";
                    }
                }
            }
        }
        return DateUtils.formatDate(startday, "yyyy-MM-dd");
    }

    public static String getDateEndStr(Date endday) {

        Date date = new Date();
        long l = endday.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        if (day != 0) {
			/*if (day > 30) {
				return DateUtils.formatDate(endday, "yyyy/MM/dd");
			} else {*/

            return day + "天";
            /*}*/
        } else {
            if (hour != 0) {

                return hour + "小时";
            } else {
                if (min != 0) {

                    return min + "分钟";
                } else {
                    if (s != 0) {

                        return s + "秒";
                    }
                }
            }
        }
        return DateUtils.formatDate(endday, "yyyy-MM-dd");
    }


    public static Long getDateEndInt(Date endday) {

        Date date = new Date();
        long l = endday.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);


        return min;
    }


    public static Long getDateStrInt(Date endday) {

        Date date = new Date();
        long l = date.getTime() - endday.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);


        return min;
    }


			/*if(day > 0)
				sb.append(day+"??");

			System.out.println("day-----" + day);

			if(hour > 0 )
				sb.append(hour+"???");
			System.out.println("hour-----"+hour);
			if(min > 0 )
				sb.append(min+"??");
			System.out.println("min-----"+min);
			sb.append(s+"?? ?");
			System.out.println("s-----"+s);



	}







	/**
	 * @param args
	 * @throws ParseException
	 */

    private final static double PI = 3.14159265358979323; // 圆周率
    private final static double R = 6371229; // 地球的半径

    public static double getDistance(double longt1, double lat1, double longt2, double lat2) {
        double x, y, distance;
        x = (longt2 - longt1) * PI * R
                * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
        y = (lat2 - lat1) * PI * R / 180;
        distance = Math.hypot(x, y);
        return distance;
    }


    /**
     * 时间改变为今天，昨天
     *
     * @param time
     * @return
     */

    public static String formatDateTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return "今天 " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {

            return "昨天 " + time.split(" ")[1];
        } else {
            return time;
        }
    }


    public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy??MM??dd?? E"));
//		long time = new Date().getTime()-parseDate("2015-7-19 12:12:12").getTime();
//		System.out.println(time/(24*60*60*1000));
//		System.out.println(pastDays(DateUtils.parseDate("2015-06-01 12:12:12")));
//		System.out.println(time/(24*60*60*1000));


		/*String aa = DateUtils.getDateStr(DateUtils.parseDate("2015-07-13 17:12:01"));
		System.out.print(aa);
		Date date = new Date();*/

		/*boolean a = DateUtils.isDateBefore("2015-07-12 17:12:01","2015-07-15 17:12:01");
		System.out.println(a);


		int time = DateUtils.getIntervalDays(DateUtils.parseDate("2015-07-23 17:12:01"),DateUtils.parseDate("2015-07-15 17:12:01"));
		System.out.println(time);*/

		/*Long data = new Date().getTime();
		Long time = DateUtils.parseDate("2015-12-29 12:01:00").getTime();


		Date date = new Date();
		long l =    new Date().getTime() -time;
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);*/
		/*Date date=new   Date();//取时间
		Long l = DateUtils.parseDate("2016-01-13 18:41:00").getTime() - date.getTime() ;

		Long aa = l/ (60 * 1000);
		//Long l =getDateStrInt(DateUtils.parseDate("2016-1-4 08:41:00"));

		System.out.println( aa );

		if(aa < 0L){

			System.out.println("111");
		}



		Long data1 = date.getTime() - DateUtils.parseDate("2016-1-12 15:41:00").getTime();
		Long data  = data1 / (60 * 1000);

		System.out.println( data );*/



	/*	Calendar calendar   =   new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime();   //这个时间就是日期往后推一天的结果

		System.out.println(DateUtils.formatDateTime(date));*/

		/*Long data = DateUtils.getDateEndInt(DateUtils.parseDate("2016-1-5 08:41:00"));
		if(data < -10L){
			System.out.println("-------------------}" + data);
			//dataVo.setOrderState("7");
		}
		System.out.println(data);*/

        //System.out.println(min);


	/*	try{
			//���û�id ��password  ȥ����ע������sdk �˺�
			huanXinUtil.postUser(huanXinUtil.getToken(), "123456", "1q2w3e4r");
		} catch (Exception e){
			System.out.println("111111111111111");
		}*/


        //BigDecimal.ROUND_HALF_UP 是4舍5入，BigDecimal.ROUND_DOWN是舍去，BigDecimal.ROUND_FLOOR是向上取整

/*
		int number = new Random().nextInt(10) + 1;
		if(10 ==number){
			int numbers = new Random().nextInt(10) + 1;

		}
		System.out.println(String.valueOf(number));*/


	/*	Date d = new Date();

		if (d.getTime() > DateUtils.parseDate("2015-01-27 11:11:11").getTime()) {
			System.out.println("dt1 在dt2前");

		}*/
		/*String  total = "21";

		String counts = "15";
		String totals = "0";
		if(total.equals("0")){
			counts = "15";
			totals = "0";
		}else if (Integer.valueOf(total) >14  ){
			counts = "15";
			totals = "15";
		}else if (Integer.valueOf(total) >30 ){
			counts = "15";
			totals = "30";
		}else if (Integer.valueOf(total) >45 ){
			counts = "15";
			totals = "45";
		}else if(Integer.valueOf(total) >60){
			counts = "30";
			totals = "60";
		}

		System.out.println(counts);

		System.out.println(totals);*/

        //System.out.println(Double.valueOf("100").intValue());

        Date date = new Date();
        long l = DateUtils.parseDate("2016-01-30 23:40:01").getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        /*	if (day != 0) {
         *//*if (day > 30) {
				return DateUtils.formatDate(endday, "yyyy/MM/dd");
			} else {*//*

			return day + "天";
			*//*}*//*
		} else {
			if (hour != 0) {

				return hour + "小时";
			} else {
				if (min != 0) {

					return min + "分钟";
				} else {
					if (s != 0) {

						return s + "秒";
					}
				}
			}
		}*/

/*
		System.out.println( day+"天" +hour+"小时"+min+"分");


		GatherBeanController g = new GatherBeanController();
		Vo vo = new Vo();
		vo.setAddress("吉林省 长春市 朝阳区 松辉路工程学院家属楼17号楼(王女士)");
		//Vo vo1 = g.setCoordinate(vo,"吉林省 长春市 朝阳区 松辉路工程学院家属楼17号楼(王女士)");

		//System.out.println(vo1.getLat()+"----"+vo1.getLng());*/

        System.out.println(formatDateTime("2016-03-03 12:12"));

    }


    public static String random(int k) {

        Random rand = new Random();

        boolean[] bool = new boolean[k];

        int randInt = 1;
        String str = StringUtils.EMPTY;
        for (int i = 0; i < k; i++) {

            do {

                randInt = rand.nextInt(k);

            } while (bool[randInt]);

            bool[randInt] = true;

            //System.out.println(randInt);
            str = randInt + "," + str;

        }

        return str;
    }


    /**
     * 每次生成的len位数都不相同
     *
     * @param param
     * @return 定长的数字
     */
    public static int getNotSimple(int[] param, int len) {
        Random rand = new Random();
        for (int i = param.length; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = param[index];
            param[index] = param[i - 1];
            param[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < len; i++) {
            result = result * 10 + param[i];
        }
        return result;
    }


    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    /**
     * 判断时间是否在时间段内 *
     *
     * @param date         当前时间 yyyy-MM-dd HH:mm:ss
     * @param strDateBegin 开始时间 00:00:00
     * @param strDateEnd   结束时间 00:05:00
     * @return
     */

    public static boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(date);
        // 截取当前时间时分秒
        int strDateH = Integer.parseInt(strDate.substring(11, 13));
        int strDateM = Integer.parseInt(strDate.substring(14, 16));
        int strDateS = Integer.parseInt(strDate.substring(17, 19));
        // 截取开始时间时分秒
        int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
        int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
        int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
        // 截取结束时间时分秒
        int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
        int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
        int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
        if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
            // 当前时间小时数在开始时间和结束时间小时数之间
            if (strDateH > strDateBeginH && strDateH < strDateEndH) {
                return true;
                // 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
            } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM && strDateM <= strDateEndM) {
                return true;
                // 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
            } else if (strDateH == strDateBeginH && strDateM == strDateBeginM && strDateS >= strDateBeginS && strDateS <= strDateEndS) {
                return true;
            }
            // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
            else if (strDateH >= strDateBeginH && strDateH == strDateEndH
                    && strDateM <= strDateEndM) {
                return true;
                // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
            } else if (strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM == strDateEndM && strDateS <= strDateEndS) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return w;
    }

    /**
     * 将指定日期字符串转换为Date-指定格式
     */
    public static Date parseDate(String date, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(date);
    }

    /**
     * 判断两个日期日期大小
     *
     * @param date1
     * @param date2
     * @return 返回1则dt1>dt2,返回-1则dt1<dt2,否则相等
     */
    public static int compareDate(String date1, String date2, String pattenStr) {
        try {
            Date dt1 = parseDate(date1, pattenStr);
            Date dt2 = parseDate(date2, pattenStr);
            return compareDate(dt1, dt2);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断两个日期日期大小
     *
     * @param dt1
     * @param dt2
     * @return 返回1则dt1>dt2,返回-1则dt1<dt2,否则相等
     */
    public static int compareDate(Date dt1, Date dt2) {
        try {
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String dateFormat() {
        return dateFormat(new Date(), YMDHMS);
    }

    public static String dateFormat(String pattern) {
        return dateFormat(new Date(), pattern);
    }

    public static String dateFormat(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
