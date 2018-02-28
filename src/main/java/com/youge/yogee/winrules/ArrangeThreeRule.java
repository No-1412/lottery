package com.youge.yogee.winrules;

/**
 * Created by wjc on 2017-12-18 排列三 奖金计算
 */
public class ArrangeThreeRule {
    /**
     * 传入购买的彩票号码 和中奖号码,给出中奖金额
     *
     * @param threeNumber
     * @param winNumber
     * @return 奖金大小
     */
    public static String ThreeRule(String threeNumber, String winNumber) {
        //获得的奖金总数
        String winMoney = "0";
        //拆分中奖彩票号,和顾客的彩票号
        String threeHundred = threeNumber.substring(0, 1);
        String threeTen = threeNumber.substring(1, 2);
        String threeUnit = threeNumber.substring(2, 3);
        String winHundred = winNumber.substring(0, 1);
        String winThreeTen = winNumber.substring(1, 2);
        String winThreeUnit = winNumber.substring(2, 3);
        //直选
        if (threeNumber.equals(winNumber)) {
            winMoney = "1040"; //设置奖金
        }
        String[] threeArray = new String[]{threeHundred, threeTen, threeUnit};
        String[] winArray = new String[]{winHundred, winThreeTen, winThreeUnit};
        int count = 0;
        //循环比较数字
        for (int i = 0; i < threeArray.length; i++) {
            for (int j = 0; j < winArray.length; j++) {
                if (threeArray[i].equals(winArray[j])) {
                    count++;
                }
                System.out.println(count + "----" + threeArray[i] + "---" + winArray[j]);
            }
        }
        //组三
        if (count == 5) {
            if (threeHundred.equals(threeTen) || threeTen.equals(threeUnit) || threeHundred.equals(threeUnit)) {
                winMoney = "346";
            }
        }
        if (count == 3) {
            //组六
            if (!threeHundred.equals(threeTen) && !threeTen.equals(threeUnit) && !threeHundred.equals(threeUnit)) {
                winMoney = "173";
            }
        }

        return winMoney;
    }

    public static void main(String[] args) {
        String mo = ThreeRule("868", "886");
        System.out.println(mo);
    }
}
