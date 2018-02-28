package com.youge.yogee.winrules;

/**
 * Created by wjc on 2017-12-18 0018.
 * 体育彩票排列五
 */
public class ArrangeFiveRule {
    /**
     * 奖金计算
     * @param fiveNumber
     * @param winNumber
     * @return
     */
    public String fiveRule(String fiveNumber, String winNumber) {
        //获得的奖金总数
        String winMoney = "0";
        //一等奖定位中五个号码
        if (fiveNumber.equals(winNumber)) {
            winMoney = "100000";
        }
        return winMoney;
    }
}
