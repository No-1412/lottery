package com.youge.yogee.interfaces.util;

import java.util.*;

/**
 * 彩票排列三 排列五 算法
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
public class ListThreeWays {

    /**
     * 排列三 组三单式组合
     */
    public static Set<String> singleGroupOfThree(String singleNum, String sameNum) {
        String a = singleNum + "," + sameNum + "," + sameNum;
        String b = sameNum + "," + sameNum + "," + singleNum;
        String c = sameNum + "," + singleNum + "," + sameNum;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        Set<String> tList = new HashSet<>();
        tList.add(a);
        tList.add(b);
        tList.add(c);
        return tList;
    }


    /**
     * 排列三 组三复式组合
     */
    public static Set<String> doubleGroupOfThree(String numList) {
        Set<String> tList = new HashSet<>();
        String[] nums = numList.split(",");
        Set<String> sList1 = singleGroupOfThree(nums[0], nums[1]);
        for (String s : sList1) {
            tList.add(s);
        }
        Set<String> sList2 = singleGroupOfThree(nums[1], nums[0]);
        for (String s : sList2) {
            tList.add(s);
        }

        return tList;
    }


    /**
     * 排列三 组六组合
     */
    public static Set<String> doubleGroupOfSix(String numList) {
        Set<String> tList = new HashSet<>();
        String[] nums = numList.split(",");
        String a = nums[0];
        String b = nums[1];
        String c = nums[2];
        String num1 = a + "," + b + "," + c;
        String num2 = a + "," + c + "," + b;
        String num3 = b + "," + a + "," + c;
        String num4 = b + "," + c + "," + a;
        String num5 = c + "," + a + "," + b;
        String num6 = c + "," + b + "," + a;

        tList.add(num1);
        tList.add(num2);
        tList.add(num3);
        tList.add(num4);
        tList.add(num5);
        tList.add(num6);

        return tList;
    }


    /**
     * 排列三 组三单式组合结果
     */
    public static Set<String> resultOfSingleGroupOfThree(String singleNumList, String sameNumList) {
        Set<String> resultList = new HashSet<>();
        String[] singleNums = singleNumList.split(",");
        String[] sameNums = sameNumList.split(",");
        for (int i = 0; i < singleNums.length; i++) {
            for (int j = 0; j < sameNums.length; j++) {
//                System.out.println(singleNums[i]+","+sameNums[j]);
                Set<String> sList = singleGroupOfThree(singleNums[i], sameNums[j]);
                for (String s : sList) {
                    resultList.add(s);
                }
            }
        }

        return resultList;
    }


    /**
     * 排列三 组三复式组合结果
     */
    public static Set<String> resultOfDoubleGroupOfThree(List<String> list) {
        Set<String> dList = new HashSet<>();
        Set<String> resultList = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {

                String result1 = list.get(i) + "," + list.get(j);
                String result2 = list.get(j) + "," + list.get(i);
                dList.add(result1);
                dList.add(result2);
                Set<String> doubleList = doubleGroupOfThree(result1);
                for (String s : doubleList) {
                    resultList.add(s);
                }
            }

        }
//        System.out.println(dList.size());
        return resultList;
    }


    /**
     * 排列三 组六单式组合结果
     */
    public static Set<String> resultOfSingleGroupOfSix(String num1, String num2, String num3) {
        Set<String> resultList = new HashSet<>();
        String[] nums1 = num1.split(",");
        String[] nums2 = num2.split(",");
        String[] nums3 = num3.split(",");
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                for (int k = 0; k < nums3.length; k++) {
                    String result = nums1[i] + "," + nums2[j] + "," + nums3[k];
                    Set<String> sList = doubleGroupOfSix(result);
                    for (String s : sList) {
                        resultList.add(s);
                    }
                }
            }
        }

        return resultList;
    }


    /**
     * 排列三
     * <p>
     * 组六复试组合结果
     */
    public static Set<String> resultOfDoubleGroupOfSix(String numList) {
        Set<String> set = new HashSet<String>();
        String[] str = numList.split(",");
        Set setqr = new HashSet();

        for (String strq : str) {
            for (String strqq : str) {
                if (!strq.equals(strqq)) {
                    for (String strqqq : str) {
                        if (!strqq.equals(strqqq) && !strq.equals(strqqq)) {
                            String strw = strq + "," + strqq + "," + strqqq;
                            Set<String> setq = new HashSet();
                            setq.add(strq);
                            setq.add(strqq);
                            setq.add(strqqq);
                            int i = setqr.size();
                            setqr.add(setq);
                            if (i < setqr.size()) {
//                                set.add(strw);
//                                System.out.println(strw);
                                Set<String> set1 = doubleGroupOfSix(strw);
                                for (String s : set1) {
                                    set.add(s);
                                }
                            }
                        }
                    }
                }
            }
        }

        return set;

    }

    /**
     * 排列三  直选和值  注数
     *
     * @param sum 和值
     */
    public static int sumOfThreeNums(int sum) {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    int sumOfThree = i + j + k;
                    if (sumOfThree == sum) {
                        count += 1;
                    }

                }
            }
        }
        return count;

    }


    /**
     * 排列三 直选组合结果
     */
    public static Set<String> resultOfRightChoose(String num1, String num2, String num3) {
        Set<String> resultList = new HashSet<>();
        String[] nums1 = num1.split(",");
        String[] nums2 = num2.split(",");
        String[] nums3 = num3.split(",");

        for (int i = 0; i < nums1.length; i++) {

            for (int j = 0; j < nums2.length; j++) {

                for (int k = 0; k < nums3.length; k++) {
                    String result = nums1[i] + "," + nums2[j] + "," + nums3[k];
                    resultList.add(result);
                }
            }
        }

        return resultList;
    }


    /**
     * 排列五 直选组合结果
     */
    public static Set<String> resultOfRightChooseOfFive(String num1, String num2, String num3, String num4, String num5) {
        Set<String> resultList = new HashSet<>();
        String[] nums1 = num1.split(",");
        String[] nums2 = num2.split(",");
        String[] nums3 = num3.split(",");
        String[] nums4 = num4.split(",");
        String[] nums5 = num5.split(",");

        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                for (int k = 0; k < nums3.length; k++) {
                    for (int m = 0; m < nums4.length; m++) {
                        for (int n = 0; n < nums5.length; n++) {
                            String result = nums1[i] + "," + nums2[j] + "," + nums3[k] + "," + nums4[m] + "," + nums5[n];
                            resultList.add(result);
                        }
                    }
                }
            }
        }

        return resultList;
    }


    /**
     * 排列三 直选组合 注数
     */
    public static int countOfRightChoose(String num1, String num2, String num3) {

        String[] nums1 = num1.split(",");
        String[] nums2 = num2.split(",");
        String[] nums3 = num3.split(",");
        int count = nums1.length * nums2.length * nums3.length;
        return count;
    }


    /**
     * 排列五 直选组合 注数
     */
    public static int countOfRightChooseOfFive(String num1, String num2, String num3, String num4, String num5) {

        String[] nums1 = num1.split(",");
        String[] nums2 = num2.split(",");
        String[] nums3 = num3.split(",");
        String[] nums4 = num4.split(",");
        String[] nums5 = num5.split(",");

        int count = nums1.length * nums2.length * nums3.length * nums4.length * nums5.length;
        return count;
    }


    public static Map<String, String> listThree(String nums, String ways) {
//        String allPerhaps = "";
        Map<String, String> map = new HashMap<>();
        int i = Integer.parseInt(ways);
        //1直选 2和值 3组三单式 4组三复式 5组六单式 6组六复式
        switch (i) {
            case 1: {
                String allPerhaps = "";
                String[] numsStr = nums.split("\\|");
                //所有情况
                Set<String> set = resultOfRightChoose(numsStr[0], numsStr[1], numsStr[2]);
                for (String s : set) {
                    allPerhaps += s + "|";
                }
                //注数
                int count = countOfRightChoose(numsStr[0], numsStr[1], numsStr[2]);
                String countStr = String.valueOf(count);
                map.put("allPerhaps", allPerhaps);
                map.put("count", countStr);
                map.put("award", "1040");
                break;
            }
            case 2: {
                //注数
                int sum = 0;
                String allPerhaps = "";
                //所有情况
                allPerhaps = nums;
                String[] numsStr = nums.split("\\|");
                for (int l = 0; l < numsStr.length; l++) {
                    int count = sumOfThreeNums(Integer.parseInt(numsStr[l]));
                    sum += count;
                }

                map.put("allPerhaps", allPerhaps);
                map.put("count", String.valueOf(sum));
                map.put("award", "1040");
                break;
            }
            case 3: {
                String allPerhaps = "";
                String[] numsStr = nums.split("\\|");
                //注数
                String[] numsStrArray1 = numsStr[0].split(",");
                String[] numsStrArray2 = numsStr[1].split(",");
                int count = numsStrArray1.length * numsStrArray2.length;
                //所有可能
                Set<String> set = resultOfSingleGroupOfThree(numsStr[0], numsStr[1]);
                for (String s : set) {
                    allPerhaps += s + "|";
                }
                String countStr = String.valueOf(count);
                map.put("allPerhaps", allPerhaps);
                map.put("count", countStr);
                map.put("award", "346");
                break;
            }
            case 4: {
                String allPerhaps = "";
                String[] numsStr = nums.split("\\|");
                List<String> list = new ArrayList<>();
                for (int j = 0; j < numsStr.length; j++) {
                    list.add(numsStr[j]);
                }
                Set<String> set = resultOfDoubleGroupOfThree(list);
                for (String s : set) {
                    allPerhaps += s + "|";
                }
                int count = set.size() / 3;
                String countStr = String.valueOf(count);
                map.put("allPerhaps", allPerhaps);
                map.put("count", countStr);
                map.put("award", "346");
                break;
            }
            case 5: {
                String allPerhaps = "";
                String[] numsStr = nums.split("\\|");
                //注数
                String[] numsStrArray1 = numsStr[0].split(",");
                String[] numsStrArray2 = numsStr[1].split(",");
                String[] numsStrArray3 = numsStr[2].split(",");
                int count = numsStrArray1.length * numsStrArray2.length * numsStrArray3.length;

                Set<String> set = resultOfSingleGroupOfSix(numsStr[0], numsStr[1], numsStr[2]);
                for (String s : set) {
                    allPerhaps += s + "|";
                }
                String countStr = String.valueOf(count);
                map.put("allPerhaps", allPerhaps);
                map.put("count", countStr);
                map.put("award", "173");
                break;
            }
            case 6: {
                String allPerhaps = "";
                String[] numsStr = nums.split("\\|");
                String num = "";
                for (int j = 0; j < numsStr.length; j++) {
                    num += numsStr[j] + ",";
                }
                Set<String> set = resultOfDoubleGroupOfSix(num);
                for (String s : set) {
                    allPerhaps += s + "|";
                }
                int count = set.size() / 6;
                String countStr = String.valueOf(count);
                map.put("allPerhaps", allPerhaps);
                map.put("count", countStr);
                map.put("award", "173");
                break;
            }

            case 7: {
                String allPerhaps = "";
                String[] numsStr = nums.split("\\|");
                //所有情况
                Set<String> set = resultOfRightChooseOfFive(numsStr[0], numsStr[1], numsStr[2], numsStr[3], numsStr[4]);
                for (String s : set) {
                    allPerhaps += s + "|";
                }
                //注数
                int count = countOfRightChooseOfFive(numsStr[0], numsStr[1], numsStr[2], numsStr[3], numsStr[4]);
                String countStr = String.valueOf(count);
                map.put("allPerhaps", allPerhaps);
                map.put("count", countStr);
                map.put("award", "100000");
                break;
            }

            default:
                break;
        }
        return map;
    }


    public static void main(String[] args) {
//        //复试
//        List<String> list = new ArrayList<>();
//        for (int i = 1; i < 6; i++) {
//            list.add(String.valueOf(i));
//        }
//
//        Set<String> resultList = resultOfDoubleGroupOfThree(list);
//        System.out.println(resultList.size());
//        String award="9,4,6";
//        int count=resultList.size();
//        resultList.add(award);
//        if(resultList.size()>count){
//            System.out.println("未中奖！");
//        }else {
//            System.out.println("中奖！");
//        }
//        singleGroupOfThree("9", "4");
//        List<String> dList= doubleGroupOfThree("1,2");
//        for(String s:dList){
//            System.out.println(s);
//        }
//        System.out.println(dList.size()+"种");
        //String award = "2,6,8";

//        Set<String> list = resultOfSingleGroupOfThree("1", "3,6");
//        for (String s : list) {
//            System.out.println(s);
//        }
//        int count = list.size();
//        //list.add(award);
//        if (list.size() > count) {
//            System.out.println("未中奖！");
//        } else {
//            System.out.println("中奖！");
//        }
//        Set<String> list = resultOfRightChooseOfFive("1,2", "3,7", "2,4", "9,5", "4,8");
//        System.out.println();
//        for (String s : list) {
//            System.out.println(s);
//        }
//        System.out.println(list.size());

        int red = Calculations.combine(0, 5 - 3, 8, 0);
        int blue = Calculations.combine(0, 2 - 0, 4, 0);
        System.out.println(red);
        System.out.println(blue);

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
