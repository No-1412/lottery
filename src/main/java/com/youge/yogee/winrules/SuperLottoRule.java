package com.youge.yogee.winrules;

/**
 * Created by wjc on 2017-12-18 0018.
 * 大乐透奖金计算
 */
public class SuperLottoRule {
    /**
     * 标准投注
     *
     * @param superNumber
     * @param winNumber
     * @return
     */
    public static String SuperRule(String superNumber, String winNumber) {
        //获得的奖金总数
        String winMoney = "0";
        //拆分顾客的彩票号
        String superOne = superNumber.substring(0, 2);
        String superTwo = superNumber.substring(2, 4);
        String superThree = superNumber.substring(4, 6);
        String superFour = superNumber.substring(6, 8);
        String superFive = superNumber.substring(8, 10);
        String superSixBlue = superNumber.substring(10, 12);
        String superSevenBlue = superNumber.substring(12, 14);
        //拆分中奖的彩票号
        String winOne = winNumber.substring(0, 2);
        String winTwo = winNumber.substring(2, 4);
        String winThree = winNumber.substring(4, 6);
        String winFour = winNumber.substring(6, 8);
        String winFive = winNumber.substring(8, 10);
        String winSixBlue = winNumber.substring(10, 12);
        String winSevenBlue = winNumber.substring(12, 14);
        String[] superRedArray = new String[]{superOne, superTwo, superThree, superFour, superFive};
        String[] winRedArray = new String[]{winOne, winTwo, winThree, winFour, winFive};
        String[] superBlueArray = new String[]{superSixBlue, superSevenBlue};
        String[] winBlueArray = new String[]{winSixBlue, winSevenBlue};
        int countRed = 0;
        //循环比较数字
        for (int i = 0; i < superRedArray.length; i++) {
            for (int j = 0; j < winRedArray.length; j++) {
                if (superRedArray[i].equals(winRedArray[j])) {
                    countRed++;
                }
                System.out.println(countRed + "----" + superRedArray[i] + "---" + winRedArray[j]);
            }
        }
        int countBlue = 0;
        //循环比较数字
        for (int i = 0; i < superBlueArray.length; i++) {
            for (int j = 0; j < winBlueArray.length; j++) {
                if (superBlueArray[i].equals(winBlueArray[j])) {
                    countBlue++;
                }
                System.out.println(countBlue + "----" + superBlueArray[i] + "---" + winBlueArray[j]);
            }
        }
        /**
         * 六等奖
         */
        //蓝色球中两个,红球为零或者 两个篮球一个红球
        if (countBlue == 2 && countRed <= 1) {
            winMoney = "5";
        }
        //两个红球,一个蓝色球
        if (countBlue == 1 && countRed == 2) {
            winMoney = "5";
        }
        //红球三个并且蓝色球为0
        if (countRed == 3 && countBlue == 0) {
            winMoney = "5";
        }
        /**
         * 五等奖
         */
        if (countRed == 2 && countBlue == 2) {
            winMoney = "10";
        }
        if (countRed == 3 && countBlue == 1) {
            winMoney = "10";
        }
        if (countRed == 4 && countBlue == 0) {
            winMoney = "10";
        }
        /**
         * 四等奖
         */
        if (countRed == 4 && countBlue == 1) {
            winMoney = "200";
        }
        if (countRed == 3 && countBlue == 2) {
            winMoney = "200";
        }
        /**
         * 三等奖
         */
        if (countRed == 5 && countBlue == 0) {
            //浮动
        }
        if (countRed == 4 && countBlue == 2) {
            //浮动
        }
        /**
         * 二等奖
         */
        if (countRed == 5 && countBlue == 1) {
            //浮动
        }
        /**
         * 一等奖
         */
        if (countRed == 5 && countBlue == 2) {
            //浮动
        }
        return winMoney;
    }

    public static void main(String[] args) {
        String mo = SuperRule("868", "886");
        System.out.println(mo);
    }


}
