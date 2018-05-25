package com.youge.yogee.common.utils.lottery;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.youge.yogee.modules.cfootballawards.entity.CdFootballAwards;
import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author
 */
public class LotteryUtil {

    private final static List<String> arrayList = new ArrayList<>(Arrays.asList("beat", "let", "half", "goal", "score"));


    public static BigDecimal bestWinningVerify(List<String> list, Map<String, CdFootballAwards> resultMap, String times) {
        //System.out.println(list);
        BigDecimal countBigDecimal = new BigDecimal("0");
        BigDecimal singleBigDecimal = new BigDecimal("0");
        for (int i = 0; i < list.size(); i++) {

            BigDecimal bigDecimal = new BigDecimal("1");
            List<String> group = new ArrayList<String>(Arrays.asList(list.get(i).split("\\|")));

            for (int j = 0; j < group.size(); j++) {
                //System.out.println(group.get(j));

                String[] rt = group.get(j).split("\\+");
                // System.out.println("场次：" + resultList.get(j));
                //System.out.println(rt[k]);
                // 解析0,1,2
                String[] info = rt[2].split("/");
                int index = arrayList.indexOf(rt[1]);
                CdFootballAwards oe = resultMap.get(rt[0]);
                if (index == 0) {
                    if (Strings.isNullOrEmpty(oe.getWinning())) {
                        bigDecimal = bigDecimal.multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_DOWN);
                    } else {
                        if (info[0].equals(oe.getWinning())) {
                            bigDecimal = bigDecimal.multiply(new BigDecimal(info[1])).setScale(2, RoundingMode.HALF_DOWN);
                        } else {
                            bigDecimal = new BigDecimal("0");
                        }
                    }

                   // System.out.println("info" + bigDecimal);

                }
                if (index == 1) {
                    if (Strings.isNullOrEmpty(oe.getSpread())) {
                        bigDecimal = bigDecimal.multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_DOWN);
                    } else {
                        if (info[0].equals(oe.getSpread())) {
                            bigDecimal = bigDecimal.multiply(new BigDecimal(info[1])).setScale(2, RoundingMode.HALF_DOWN);
                        } else {
                            bigDecimal = new BigDecimal("0");
                        }
                    }

                }
                if (index == 2) {
                    if (Strings.isNullOrEmpty(oe.getWinGrap())) {
                        bigDecimal = bigDecimal.multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_DOWN);
                    } else {
                        if (info[0].equals(oe.getWinGrap())) {
                            bigDecimal = bigDecimal.multiply(new BigDecimal(info[1])).setScale(2, RoundingMode.HALF_DOWN);
                        } else {
                            bigDecimal = new BigDecimal("0");
                        }
                    }
                }
                if (index == 3) {
                    if (Strings.isNullOrEmpty(oe.getHs()) || Strings.isNullOrEmpty(oe.getVs())) {
                        bigDecimal = bigDecimal.multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_DOWN);
                    } else {
                        if (info[0].equals(ConvertCount(oe.getHs(), oe.getVs()))) {
                            bigDecimal = bigDecimal.multiply(new BigDecimal(info[1])).setScale(2, RoundingMode.HALF_DOWN);
                        } else {
                            bigDecimal = new BigDecimal("0");
                        }
                    }

                }
                if (index == 4) {
                    if (Strings.isNullOrEmpty(oe.getHs()) || Strings.isNullOrEmpty(oe.getVs())) {
                        bigDecimal = bigDecimal.multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_DOWN);
                    } else {
                        if (info[0].equals(ConvertCompared(oe.getHs(), oe.getVs()))) {
                            bigDecimal = bigDecimal.multiply(new BigDecimal(info[1])).setScale(2, RoundingMode.HALF_DOWN);
                        } else {
                            bigDecimal = new BigDecimal("0");
                        }
                    }
                }
            }
            singleBigDecimal = bigDecimal.multiply(new BigDecimal("2")).setScale(2, RoundingMode.HALF_DOWN);
        }
       // System.out.println("单注：" + singleBigDecimal);
        //System.out.println("倍数：" + times);
        countBigDecimal = countBigDecimal.add(singleBigDecimal.multiply(new BigDecimal(times)).setScale(2, RoundingMode.HALF_DOWN));
       // System.out.println("中奖总金额：" + countBigDecimal);
        return countBigDecimal;
    }

    /**
     * @param list
     * @param resultMap
     * @param followNum
     * @param times
     * @return
     */
    public static BigDecimal WinningVerify(List<String> list, Map<String, CdFootballAwards> resultMap, String followNum, String times) {
        List<String> index = new ArrayList<String>();
        JSONObject sJson = new JSONObject();// 存储购买比赛信息
        int bileCount = 0;
        List<String> bileList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            String rString = list.get(i);
            if (!Strings.isNullOrEmpty(rString)) {
                String[] obj = rString.split("\\|");// 解析玩法分组0-4代表四种玩法
                for (int j = 0; j < obj.length; j++) {
                    String[] info_arr = obj[j].split("\\+");// 解析比赛信息
                    //System.out.print("比赛信息：" + info_arr[1] + "_" + info_arr[2]);
                    //System.out.println(" 胆：" + (info_arr[0].equals("1") ? "已选" : "未选"));
                    if (info_arr[0].equals("1")) {
                        bileCount += 1;
                        bileList.add(info_arr[1] + "_" + info_arr[2]);
                    }
                    // 判断如果存储比赛信息不存在则加入比赛信息
                    if (!sJson.containsKey(info_arr[1] + "_" + info_arr[2])) {
                        index.add(info_arr[1] + "_" + info_arr[2]);
                        sJson.put(info_arr[1] + "_" + info_arr[2], new JSONArray());
                    }
                    String[] options = info_arr[3].split(",");// 获取单场所有玩法分类
                    for (int k = 0; k < options.length; k++) {
                        // 解析结果和赔率
                        String[] odds = options[k].split("/");
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type", i);// 玩法分组0-4代表四种玩法
                        jsonObject.put("result", odds[0]);// 结果
                        jsonObject.put("odds", odds[1]);// 赔率
                        jsonObject.put("bile", info_arr[0]);// 赔率
                        // 保存对战玩法信息
                        JSONArray array = sJson.getJSONArray(info_arr[1] + "_" + info_arr[2]);
                        array.add(jsonObject);
                        sJson.put(info_arr[1] + "_" + info_arr[2], array);
                    }
                }
            }
        }

        //System.out.println(index);
        //System.out.println(sJson);
        ///System.out.println("胆数：" + bileCount);
        //System.out.println("最小串数：" + (bileCount + 1));
        //System.out.println("最大串数：" + index.size());
        //System.out.println("必须包含场次：" + bileList);
        // 串关数
        String[] follow_num = followNum.split(",");
        // List<String> serialNumberList = new ArrayList<String>();
        // serialNumberList.add("2");
        //System.out.println("----------------------------------------------------");
        BigDecimal countBigDecimal = new BigDecimal("0");
        for (String serialNumber : follow_num) {
            if (Integer.valueOf(serialNumber) >= (bileCount + 1) && index.size() >= Integer.valueOf(serialNumber)) {
                //System.out.println("当前计算串关数为：" + serialNumber);
                List<String> group = new ArrayList<String>();
                // 从list中每次取三个元素
                List<List<String>> result = findsort(index, Integer.valueOf(serialNumber));
                for (int i = 0; i < result.size(); i++) {
                    StringBuffer buffer = new StringBuffer();
                    for (int j = 0; j < result.get(i).size(); j++) {
                        buffer.append(result.get(i).get(j) + "#");
                    }
                    //tem.out.println(buffer.toString());
                    group.add(buffer.toString());
                }

                if (bileCount > 0) {
                    String[] strings = new String[bileList.size()];
                    //System.out.println("所有场次：" + group);
                    for (int i = 0; i < group.size(); i++) {
                        boolean containsAll = containsAll(group.get(i).split("#"), bileList.toArray(strings));
                        if (!containsAll) {
                            group.remove(i);
                        }
                    }
                    //System.out.println("移除后：" + group);
                }
                //System.out.println("----------------------------------------------------");

                for (int i = 0; i < group.size(); i++) {
                    String[] gString = group.get(i).split("#");
                    List<List<String>> dimValue = new ArrayList<List<String>>();
                    for (int j = 0; j < gString.length; j++) {
                        String g_info = gString[j];
                        // System.out.println("赛事：" + g_info);
                        JSONArray array = sJson.getJSONArray(g_info);
                        List<String> group_detail = new ArrayList<String>();
                        for (int k = 0; k < array.size(); k++) {
                            JSONObject object = array.getJSONObject(k);
                            String type = object.getString("type");
                            String odds = object.getString("odds");
                            String rt = object.getString("result");
                            group_detail.add(g_info + "_" + type + "_" + rt + "_" + odds);
                            // System.out.println("组合：" + g_info + "_" + type + "_" + rt + "_" + odds);
                        }
                        dimValue.add(group_detail);
                        // System.out.println(group_detail);
                    }

                    List<List<String>> circulateResult = new ArrayList<List<String>>();
                    // System.out.println("赛事组合：");
                    circulate(dimValue, circulateResult);
                    //System.out.println("循环实现笛卡尔乘积: 共 " + circulateResult.size() + " 个结果");
                    List<String> resultList = new ArrayList<String>();
                    for (List<String> list1 : circulateResult) {
                        StringBuffer buffer = new StringBuffer();
                        for (String string : list1) {
                            buffer.append(string + "#");
                        }
                        resultList.add(buffer.toString());
                    }
                    // System.out.println(resultList);

                    //System.out.println("------------------------计算金额----------------------");

                    for (int j = 0; j < resultList.size(); j++) {
                        BigDecimal bigDecimal = new BigDecimal("1");
                        String[] rt = resultList.get(j).split("#");
                        //System.out.println("场次：" + resultList.get(j));
                        for (int k = 0; k < rt.length; k++) {
                            String[] info = rt[k].split("_");
                            CdFootballAwards oe = resultMap.get(info[0]);
                            if (info[2].equals("0")) {
                                if (Strings.isNullOrEmpty(oe.getWinning())) {
                                    bigDecimal = bigDecimal.multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_DOWN);
                                } else {
                                    if (info[3].equals(ConvertInt(oe.getWinning()))) {
                                        bigDecimal = bigDecimal.multiply(new BigDecimal(info[4])).setScale(2, RoundingMode.HALF_DOWN);
                                    } else {
                                        bigDecimal = new BigDecimal("0");
                                    }
                                }

                            }
                            if (info[2].equals("1")) {
                                System.out.println(oe.getMatchId());
                                if (Strings.isNullOrEmpty(oe.getSpread())) {
                                    bigDecimal = bigDecimal.multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_DOWN);
                                } else {
                                    if (info[3].equals(ConvertInt(oe.getSpread()))) {
                                        bigDecimal = bigDecimal.multiply(new BigDecimal(info[4])).setScale(2, RoundingMode.HALF_DOWN);
                                    } else {
                                        bigDecimal = new BigDecimal("0");
                                    }
                                }

                            }
                            if (info[2].equals("2")) {
                                if (Strings.isNullOrEmpty(oe.getWinGrap())) {
                                    bigDecimal = bigDecimal.multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_DOWN);
                                } else {
                                    if (info[3].equals(ConvertInt(oe.getWinGrap()))) {
                                        bigDecimal = bigDecimal.multiply(new BigDecimal(info[4])).setScale(2, RoundingMode.HALF_DOWN);
                                    } else {
                                        bigDecimal = new BigDecimal("0");
                                    }
                                }
                            }
                            if (info[2].equals("3")) {
                                if (Strings.isNullOrEmpty(oe.getHs()) || Strings.isNullOrEmpty(oe.getVs())) {
                                    bigDecimal = bigDecimal.multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_DOWN);
                                } else {
                                    if (info[3].equals(ConvertCount(oe.getHs(), oe.getVs()))) {
                                        bigDecimal = bigDecimal.multiply(new BigDecimal(info[4])).setScale(2, RoundingMode.HALF_DOWN);
                                    } else {
                                        bigDecimal = new BigDecimal("0");
                                    }
                                }

                            }
                            if (info[2].equals("4")) {
                                if (Strings.isNullOrEmpty(oe.getHs()) || Strings.isNullOrEmpty(oe.getVs())) {
                                    bigDecimal = bigDecimal.multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_DOWN);
                                } else {
                                    if (info[3].equals(ConvertCompared(oe.getHs(), oe.getVs()))) {
                                        bigDecimal = bigDecimal.multiply(new BigDecimal(info[4])).setScale(2, RoundingMode.HALF_DOWN);
                                    } else {
                                        bigDecimal = new BigDecimal("0");
                                    }
                                }
                            }
                        }
                        BigDecimal singleBigDecimal = bigDecimal.multiply(new BigDecimal("2")).setScale(2, RoundingMode.HALF_DOWN);
                        countBigDecimal = countBigDecimal.add(singleBigDecimal.multiply(new BigDecimal(times)).setScale(2, RoundingMode.HALF_DOWN));
                        //System.out.println("购买倍数:" + times+"     "+"购买注数:" + acount+"     单注奖金：" + singleBigDecimal+"     中奖金额：" + singleBigDecimal.multiply(new BigDecimal(times)).setScale(2, RoundingMode.HALF_DOWN));
                        //System.out.println("------------------------计算结束------------------------");
                    }
                }
            }

        }
        //System.out.println("中奖总金额：" + countBigDecimal);
        return countBigDecimal;
    }


    public static List<String> queryEvent(List<String> event) {
        List<String> index = new ArrayList<String>();
        //存储购买比赛信息
        JSONObject sJson = new JSONObject();
        for (int i = 0; i < event.size(); i++) {
            String rString = event.get(i);
            if (!Strings.isNullOrEmpty(rString)) {
                //解析玩法分组0-4代表四种玩法
                String[] obj = rString.split("\\|");
                for (int j = 0; j < obj.length; j++) {
                    //解析比赛信息
                    //noinspection AlibabaLowerCamelCaseVariableNaming
                    String[] info_arr = obj[j].split("\\+");
                    // 判断如果存储比赛信息不存在则加入比赛信息
                    if (!sJson.containsKey(info_arr[1])) {
                        index.add(info_arr[1]);
                        //System.out.println(info_arr[1]);
                        sJson.put(info_arr[1], new JSONArray());
                    }
                }
            }
        }
        return index;
    }

    /**
     * 判断 array1是否包含所有的 array2
     */
    public static boolean containsAll(String[] array1, String[] array2) {
        for (String str : array2) {
            if (!ArrayUtils.contains(array1, str)) {
                return false;
            }
        }
        return true;
    }


    private static final String MAIN_WIN = "主胜", LET_MAIN_WIN = "让主胜", MAIN_LOSE = "主负", LET_MAIN_LOSE = "让主负", WIN = "胜", LOSE = "负", FLAT = "平";


    private static String ConvertInt(String s1) {

        System.out.println(s1);
        if (s1.equals(MAIN_WIN) || s1.equals(LET_MAIN_WIN)) {
            return "3";
        }
        if (s1.equals(MAIN_LOSE) || s1.equals(LET_MAIN_LOSE)) {
            return "0";
        }
        if (s1.equals(FLAT)) {
            return "1";
        }
        return s1.replaceAll(WIN, "3").replaceAll(LOSE, "0").replaceAll(FLAT, "1");
    }

    public static String ConvertCount(String s1, String s2) {
        return String.valueOf(Integer.valueOf(s1) + Integer.valueOf(s2));
    }

    public static String ConvertCompared(String s1, String s2) {
        return s1 + ":" + s2;
    }


    // elements为要操作的数据集合，即长度为M的容器，num为每次取的元素个数
    public static List<List<String>> combiner(List<String> elements, int num,
                                              List<List<String>> result) {
        // 当num为1时，即返回结果集
        if (num == 1) {
            return result;
        }
        // result的长度是变化的，故把原始值赋给变量leng
        int leng = result.size();
        // 循环遍历，将 elements每两个元素放到一起，作为result中的一个元素
        for (int i = 0; i < leng; i++) {
            for (int j = 0; j < elements.size(); j++) {
                if (!result.get(i).contains(elements.get(j))) {
                    List<String> list1 = new ArrayList<String>();
                    for (int j2 = 0; j2 < result.get(i).size(); j2++) {
                        list1.add(result.get(i).get(j2));
                    }
                    list1.add(elements.get(j));
                    Collections.sort(list1);
                    result.add(list1);
                }
            }
        }
        // 将result中的循环遍历前的数据删除
        for (int i = 0; i < leng; i++) {
            result.remove(0);
        }
        // 对result进行去重
        Iterator<List<String>> it = result.iterator();
        List<List<String>> listTemp = new ArrayList<List<String>>();
        while (it.hasNext()) {
            List<String> a = it.next();
            if (listTemp.contains(a)) {
                it.remove();
            } else {
                listTemp.add(a);
            }
        }
        // 递归计算，根据num的值来确定递归次数
        combiner(elements, num - 1, result);
        return result;
    }

    // elements为要操作的数据集合，即长度为M的容器，num为每次取的元素个数
    public static List<List<String>> findsort(List<String> elements, int num) {
        List<List<String>> result = new ArrayList<List<String>>();
        // 将elements中的数据取出来，存到新的list中，为后续计算做准备
        for (int i = 0; i < elements.size(); i++) {
            List<String> list = new ArrayList<String>();
            list.add(elements.get(i));
            result.add(list);
        }
        return combiner(elements, num, result);
    }


    public static void circulate(List<List<String>> dimValue, List<List<String>> result) {
        int total = 1;
        for (List<String> list : dimValue) {
            total *= list.size();
        }
        String[] myResult = new String[total];

        int itemLoopNum = 1;
        int loopPerItem = 1;
        int now = 1;
        for (List<String> list : dimValue) {
            now *= list.size();

            int index = 0;
            int currentSize = list.size();

            itemLoopNum = total / now;
            loopPerItem = total / (itemLoopNum * currentSize);
            int myIndex = 0;

            for (String string : list) {
                for (int i = 0; i < loopPerItem; i++) {
                    if (myIndex == list.size()) {
                        myIndex = 0;
                    }

                    for (int j = 0; j < itemLoopNum; j++) {
                        myResult[index] = (myResult[index] == null ? "" : myResult[index] + ",") + list.get(myIndex);
                        index++;
                    }
                    myIndex++;
                }

            }
        }

        List<String> stringResult = Arrays.asList(myResult);
        for (String string : stringResult) {
            String[] stringArray = string.split(",");
            result.add(Arrays.asList(stringArray));
        }
    }

}
