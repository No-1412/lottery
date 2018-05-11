package com.youge.yogee.interfaces.lottery.util;

import java.math.BigDecimal;

public class test {
    public static void main(String[] args) {

        double priceDouble = Double.parseDouble("20.00");
        BigDecimal priceBig = new BigDecimal("20.00");
        boolean flag = false;
        String rebate = "";
        if (priceDouble > 0.0 && priceDouble < 1000.0) {
            flag = true;
            BigDecimal result = priceBig.multiply(new BigDecimal(0.01));
            rebate = String.valueOf(result.setScale(2,1));
        } else if (priceDouble >= 1000.0 && priceDouble < 10000.0) {
            flag = true;
            BigDecimal result = priceBig.multiply(new BigDecimal(0.02));
            rebate = String.valueOf(result.setScale(2, 2));
        } else if (priceDouble >= 10000.0) {
            flag = true;
            BigDecimal result = priceBig.multiply(new BigDecimal(0.03));
            rebate = String.valueOf(result.setScale(2, 2));
        }
        System.out.println(rebate);
    }


}
