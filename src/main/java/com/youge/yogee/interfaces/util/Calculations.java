package com.youge.yogee.interfaces.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *    C（m，k）  A（n,k）
 *    大乐透算法
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
public class Calculations {
    private static int sum = 0;
    private static double doubleSum = 0;
    public static List<String> list = new ArrayList<>();
    private static ArrayList <Integer>tmpArr = new ArrayList<>();

    private static ArrayList <Double>tmpDoubleArr = new ArrayList<>();

    /**
     * 计算C(m,k) 大乐透算法
     *
     * @param index 默认为0
     * @param k     胆拖需要减掉胆数
     * @param m     选中的数字个数
     * @param count 计数初始化 初始为0
     */
    public static int combine(int index, int k, int m, int count) {
        sum = count;
        if (k == 1) {
            for (int i = index; i < m; i++) {
                sum += 1;
            }
        } else if (k > 1) {
            for (int i = index; i <= m - k; i++) {
                combine(i + 1, k - 1, m, sum);
            }
        }
        return sum;
    }

    /**
     * 计算A(n,k) 排列三 组选六复式 组合结果
     * 可惜没有用到╮(╯▽╰)╭
     *
     * @param data
     * @param target
     */
    public static void arrangeSelect(List<String> data, List<String> target) {
        List<String> copyData;
        List<String> copyTarget;
        if (target.size() == 5) {
            String str = "";
            for (String i : target) {
                str += i;
            }
            list.add(str);
        }
        for (int i = 0; i < data.size(); i++) {
            copyData = new ArrayList<>(data);
            copyTarget = new ArrayList<>(target);

            copyTarget.add(copyData.get(i));
            copyData.remove(i);

            arrangeSelect(copyData, copyTarget);
        }
    }


    public static void main(String[] args) {
//        List<String> data = new ArrayList<String>();
//
//        String num = "1,2,3,4,5,6,7";
//        String[] str = num.split(",");
//        List<String> str1 = Arrays.asList(str);
//        arrangeSelect(str1, new ArrayList<String>());
//        System.out.println(list);

        double[] arr = {1,1,3,4,5,6};
        doubleSum = 0;
        combine(0,2,arr);
        System.out.println(doubleSum);

    }


    /**
     * 计算一个数组排列组合结果 衍生C(m,k)
     * 没什么卵用╮(╯▽╰)╭
     *
     */
    public static void combine(int index,int k,double []arr) {
        if(k == 1){
            for (int i = index; i < arr.length; i++) {
                tmpDoubleArr.add(arr[i]);

                doubleSum += tmpDoubleArr.get(0) * tmpDoubleArr.get(1);
                System.out.println(tmpDoubleArr.toString());
                tmpDoubleArr.remove(arr[i]);
            }
        }else{
            for (int i = index; i <= arr.length - k; i++) {
                tmpDoubleArr.add(arr[i]);
                 combine(i + 1,k - 1, arr);
                tmpDoubleArr.remove(arr[i]);
            }
        }
    }
    static List<Double> doubleList = new ArrayList<>();

    public static void combineCollection(int index,int k,double []arr) {
        if(k == 1){
            for (int i = index; i < arr.length; i++) {
                tmpDoubleArr.add(arr[i]);

                doubleList.add(tmpDoubleArr.get(0) * tmpDoubleArr.get(1));
                tmpDoubleArr.remove(arr[i]);
            }
        }else{
            for (int i = index; i <= arr.length - k; i++) {
                tmpDoubleArr.add(arr[i]);
                combine(i + 1,k - 1, arr);
                tmpDoubleArr.remove(arr[i]);
            }
        }
    }

    public static double odds(int k,List<String> arr){

        double[] doubles = new double[arr.size()];
        for(int i=0;i<arr.size();i++){
            doubles[i] = Double.parseDouble(arr.get(i));
        }
        doubleSum = 0;
        combine(0,k,doubles);
        return doubleSum;
    }


    public static List<Double> oddsCollection(int k,List<String> arr){

        double[] doubles = new double[arr.size()];
        for(int i=0;i<arr.size();i++){
            doubles[i] = Double.parseDouble(arr.get(i));
        }

        combine(0,k,doubles);
        return doubleList;
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
