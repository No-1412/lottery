package com.youge.yogee.interfaces.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 体彩多串一 任选n
 */
/*
      ✲✲✲✲✲
         __
       /`__`\
  .=. | ('') |.=.
 /.-. \__)(_/ .-.\
|:   / ╭ ﹀ ╮\   :|
\ :  \ ︶ ︶ / : /    Codes are far away from bugs with busty angel sister
 | :  \_/\_/  : |         天使姐姐保佑,代码无bug
 |:  /|    |\  :|           Created by zhaoyifeng on 2018-02-07.
 \_/` |    |`\_/
      |    |
      |    |
      |~~~~|
      '----'
*/
public class BallGameCals {
    private static double doubleSum = 0;
    private static ArrayList<Double> tmpDoubleArr = new ArrayList<>();
    private static List<String> finalList = new ArrayList<>();

    /**
     * m串一注数 即任选m
     *
     * @param list  押注结果list 包含不是胆的场次
     * @param count 减掉胆数
     * @param type  1足彩串关混投 2其它
     * @return
     */
    public static int countOfFootBall(List<String> list, int count, int type) {

        String str = "";
        if (type == 1) {
            for (String s : list) {
                String[] sInt = s.split(";");
                str += String.valueOf(sInt.length) + ",";
            }
        } else {
            for (String s : list) {
                String[] sInt = s.split(",");
                str += String.valueOf(sInt.length) + ",";
            }
        }
        String[] strArray = str.split(",");
        double[] arr = new double[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            arr[i] = Double.valueOf(strArray[i]);
        }
        combine(0, count, arr);
        //System.out.println(finalList);
        //List<String> resultStr = getCombinationResult(count, str.toString());
        int allSum = 0;
//        for (double d : tmpDoubleArr) {
//            int sum = getSumOfString(string);
//            allSum += sum;
//        }
        for (int i = 0; i < finalList.size(); i++) {
            String aFinalList = finalList.get(i).replaceAll("\\[", "");
            String next = aFinalList.replaceAll("\\]", "");
            String[] nextArray = next.split(",");
            int aResult = 1;
            for (int j = 0; j < nextArray.length; j++) {
                aResult *= Double.parseDouble(nextArray[j]);
            }
            allSum += aResult;
        }
        doubleSum = 0;
        tmpDoubleArr.clear();
        finalList.clear();
        return allSum;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
//        list.add("D|Y");
//        list.add("D|Y");
//        list.add("D|O");
//        list.add("D|N");
        list.add("0");
        list.add("0");
        list.add("N");
        list.add("N");
//        list.add("N");
//        list.add("N");
//        list.add("N");
//        list.add("N");
//        list.add("N");
//      list.add("T|N");
//		list.add("N");

        // String str = "2121";
        // List<String> cr = getCombinationResult(3, str);
        // for (String s : cr) {
        // System.out.println(s.length());
        // }


//        String a="胜胜";
//        Map<String,String> map=BallGameCals.getHalfWholeResults();
//       String c=map.get(a);

        int cr = countOfFootBall(list, 1, 1);
        System.out.println(cr);
//        List<String> wantList = new ArrayList<>();
//        wantList.add("51");
//        wantList.add("10");
//        wantList.add("21");
//        List<String> strings = getCombinationResult(2, "4951");
//        System.out.println(strings);


//        double[] arr = {16, 11, 53, 44, 5, 6};
//        doubleSum = 0;
//        combine(0, 2, arr);
//        System.out.println(finalList);
    }

    /**
     * 对字符串中元素进行重排序 此外还可以在该方法对元素进行去重等
     *
     * @param str 原字符串
     *
     * @return 目标字符串
     */
    // public static String stringFilter(String str){
    // char[] c = str.toCharArray();
    // Arrays.sort(c);
    // return new String(c);
    // }

    /**
     * 得到组合结果
     *
     * @param num 从N个数中选取num个数
     * @param str 包含Ng个元素的字符串
     * @return 组合结果
     */
    public static List<String> getCombinationResult(int num, String str) {
        List<String> result = new ArrayList<String>();
        if (num == 1) {
            for (char c : str.toCharArray()) {
                result.add(String.valueOf(c));
            }
            return result;
        }
        if (num >= str.length()) {
            result.add(str);
            return result;
        }
        int strlen = str.length();
        for (int i = 0; i < (strlen - num + 1); i++) {
            List<String> cr = getCombinationResult(num - 1, str.substring(i + 1));// 从i+1处直至字符串末尾
            char c = str.charAt(i);// 得到上面被去掉的字符，进行组合
            for (String s : cr) {

                result.add(String.valueOf(c + s));
            }
        }
        return result;
    }


    /**
     * 计算一个数组排列组合结果 衍生C(m,k)
     * 没什么卵用╮(╯▽╰)╭
     */


    public static void combine(int index, int k, double[] arr) {
        if (k == 1) {
            for (int i = index; i < arr.length; i++) {
                tmpDoubleArr.add(arr[i]);
                //doubleSum += tmpDoubleArr.get(0) * tmpDoubleArr.get(1);
                finalList.add(tmpDoubleArr.toString());
                //System.out.println(tmpDoubleArr.toString());
                tmpDoubleArr.remove(arr[i]);
            }
        } else {
            for (int i = index; i <= arr.length - k; i++) {
                tmpDoubleArr.add(arr[i]);
                combine(i + 1, k - 1, arr);
                tmpDoubleArr.remove(arr[i]);
            }
        }
    }


    public static int getSumOfString(String str) {
        int sum = 1;
        for (int i = 0; i < str.length(); i++) {

            char item = str.charAt(i);
            int itemInt = Integer.parseInt(String.valueOf(item));
            sum = sum * itemInt;
        }
        return sum;

    }

    /**
     * 猜比分所有比分结果集
     * 用于取对应赔率
     *
     * @return
     */
    public static Map<String, String> getScoreResults() {
        Map<String, String> map = new HashMap<>();
        map.put("1:0", "0");
        map.put("2:0", "1");
        map.put("2:1", "2");
        map.put("3:0", "3");
        map.put("3:1", "4");
        map.put("3:2", "5");
        map.put("4:0", "6");
        map.put("4:1", "7");
        map.put("4:2", "8");
        map.put("5:0", "9");
        map.put("5:1", "10");
        map.put("5:2", "11");
        map.put("胜其它", "12");
        map.put("0:0", "13");
        map.put("1:1", "14");
        map.put("2:2", "15");
        map.put("3:3", "16");
        map.put("平其它", "17");
        map.put("0:1", "18");
        map.put("0:2", "19");
        map.put("1:2", "20");
        map.put("0:3", "21");
        map.put("1:3", "22");
        map.put("2:3", "23");
        map.put("0:4", "24");
        map.put("1:4", "25");
        map.put("2:4", "26");
        map.put("0:5", "27");
        map.put("1:5", "28");
        map.put("2:5", "29");
        map.put("负其它", "30");

        return map;
    }


    /**
     * 半全场所有结果集
     * 用于取对应赔率
     *
     * @return
     */
    public static Map<String, String> getHalfWholeResults() {
        Map<String, String> map = new HashMap<>();
        map.put("33", "0");
        map.put("31", "1");
        map.put("30", "2");
        map.put("13", "3");
        map.put("11", "4");
        map.put("10", "5");
        map.put("03", "6");
        map.put("01", "7");
        map.put("00", "8");

        return map;
    }

    /**
     * 半全场所有结果集
     * 用于取对应赔率
     *
     * @return
     */
    public static Map<String, String> getBeatResults() {
        Map<String, String> map = new HashMap<>();
        map.put("3", "0");
        map.put("1", "1");
        map.put("0", "2");

        return map;
    }


    /**
     * 胜分差 主负 所有结果集
     * 用于取对应赔率
     *
     * @return
     */
    public static Map<String, Integer> getFailScoreResults() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1-5", 0);
        map.put("6-10", 1);
        map.put("11-15", 2);
        map.put("16-20", 3);
        map.put("21-25", 4);
        map.put("26+", 5);

        return map;
    }


    /**
     * 胜分差 主胜 所有结果集
     * 用于取对应赔率
     *
     * @return
     */
    public static Map<String, Integer> getWinScoreResults() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1-5", 6);
        map.put("6-10", 7);
        map.put("11-15", 8);
        map.put("16-20", 9);
        map.put("21-25", 10);
        map.put("26+", 11);

        return map;
    }


    /**
     * 胜分差 主负主胜 所有结果集
     * 用于取对应赔率
     *
     * @return
     */
    public static Map<String, Integer> getFailWinScoreResults() {
        Map<String, Integer> map = new HashMap<>();
        map.put("客胜1-5", 0);
        map.put("客胜6-10", 1);
        map.put("客胜11-15", 2);
        map.put("客胜16-20", 3);
        map.put("客胜21-25", 4);
        map.put("客胜26+", 5);
        map.put("主胜1-5", 6);
        map.put("主胜6-10", 7);
        map.put("主胜11-15", 8);
        map.put("主胜16-20", 9);
        map.put("主胜21-25", 10);
        map.put("主胜26+", 11);

        return map;
    }


    public static Map<String, String> getHalfWholeNames() {
        Map<String, String> map = new HashMap<>();
        map.put("33", "胜胜");
        map.put("31", "胜平");
        map.put("30", "胜负");
        map.put("13", "平胜");
        map.put("11", "平平");
        map.put("10", "平负");
        map.put("03", "负胜");
        map.put("01", "负平");
        map.put("00", "负负");

        return map;
    }

    public static Map<String, String> getSizeNames() {
        Map<String, String> map = new HashMap<>();
        map.put("1", ">");
        map.put("0", "<");


        return map;
    }

}
/*

              .======.
              |      |
              |      |
              |      |
     .========'      '========.
     |   _      INRI      _   |
     |  /_;-.__ / _\  _.-;_\  |
     |     `-._`'`_/'`.-'     |
     '========.`\   /`========'
              | |  / |
              |/-.(  |
              |\_._\ |
              | \ \`;|
              |  > |/|
              | / // |
              | |//  |
              | \(\  |
              |  ``  |
              |      |
              |      |                                                                                         Created by zhaoyifeng on 2018-02-07.
              |      |
              |      |
  \\    _  _\\| \//  |//_   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _   _ \// _
 ^ `^`^ ^`` `^ ^` ``^^`  `^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`^^` `^ `^`*/
