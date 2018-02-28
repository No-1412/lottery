/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youge.yogee.interfaces.util;

import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.modules.crecharge.entity.CdRecharge;
import com.youge.yogee.modules.crecharge.service.CdRechargeService;
import com.youge.yogee.modules.cscore.entity.CdScore;
import com.youge.yogee.modules.cscore.service.CdScoreService;
import com.youge.yogee.modules.ctradingrecord.entity.CdTradingRecord;
import com.youge.yogee.modules.ctradingrecord.service.CdTradingRecordService;
import com.youge.yogee.modules.cwithdrawal.entity.CdWithdrawal;
import com.youge.yogee.modules.cwithdrawal.service.CdWithdrawalService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * anbo,2017-09-27,添加实体工具类
 */
public class AddEntityUtils {

    /**
     * 产生随机数
     */
    public static int random() {
        Random ran=new Random();
        int r=0;
        m1:while(true){
            int n=ran.nextInt(10000);
            r=n;
            int[] bs=new int[4];
            for(int i=0;i<bs.length;i++){
                bs[i]=n%10;
                n/=10;
            }
            Arrays.sort(bs);
            for(int i=1;i<bs.length;i++){
                if(bs[i-1]==bs[i] || r<1000){
                    continue m1;
                }
            }
            break;
        }
        return r;
    }

    /**
     * 订单序列号
     * 格式[2位固定数]+[6位年月日]+[6位时分秒]+[6位随机数]
     * */
    public static String getSeriesNo(){

        Random rand = new Random();
        String orderNo = "10";
        String date = DateUtils.getDate().replaceAll("-", "").substring(2,8);
        String time = String.format(DateUtils.getTime(), "HHmmss").replaceAll(":", "");
        int randNum = rand.nextInt(999998)+1;
        String serial = String.format("%06d", randNum);
        String seriesNo = orderNo+date+time+serial;
        return seriesNo;
    }

    /**
     * @author wangqing@9fbank.com 2015-6-26 下午2:51:44
     * @function 生成num位的随机字符串(数字、大写字母随机混排)
     * @param num
     * @return
     */
    public static String createBigSmallLetterStrOrNumberRadom(int num) {

        String str = "";
        for(int i=0;i < num;i++){
            int intVal=(int)(Math.random()*58+65);
            if(intVal >= 91 && intVal <= 96){
                i--;
            }
            if(intVal < 91 || intVal > 96){
                if(intVal%2==0){
                    str += (char)intVal;
                }else{
                    str += (int)(Math.random()*10);
                }
            }
        }
        return str;
    }


    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name
     *            cookie名字
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            String str = cookie.getValue().toString().substring(4,cookie.getValue().toString().length());
            return str;
        } else {
            return "";
        }
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }


    /**
     * wangsong,
     * 2017-12-21,
     * 添加充值记录
     */
    public static void addCzRecord(CdRechargeService cdRechargeService, String userId, String userName,
                                   String rechargeType, BigDecimal rechargeMoney, BigDecimal cost, String result,
                                   String paynumber, String chargeId, String isPay){

        CdRecharge r = new CdRecharge();
        r.setUserId(userId);
        r.setName(userName);
        r.setRechargeType(rechargeType);//充值类型(1微信2支付宝3银联)
        r.setRechargeMoney(rechargeMoney);//充值金额
        r.setCost(cost);//手续费用
        r.setResult(result);//处理结果（0，新创建，1，充值成功 2， 充值失败 ）
        r.setPaynumber(paynumber);//支付流水号
        r.setIsPay(isPay);//0初始1确认交费2犹豫期
        r.setChargeId(chargeId);//ping++,id
        cdRechargeService.save(r);
    }


    /**
     * anbo,
     * 2017-0-17,
     * 添加会员积分记录
     */
    public static void addScoreRecord(CdScoreService cdScoreService,String userId,String userName,
                                      BigDecimal inScore,BigDecimal outSore,String scoreExplain,String scoreType){

        CdScore s = new CdScore();
        s.setUserId(userId);
        s.setUserName(userName);
        s.setName(userName);
        s.setInScore(inScore);//获得积分数量
        s.setOutScore(outSore);//使用积分数量
        s.setScoreExplain(scoreExplain);//积分说明
        s.setScoreType(scoreType);//积分类型（0获得1消耗）
        cdScoreService.save(s);
    }


    /**
     * anbo,
     * 2017-10-09,
     * 添加金额交易记录
     */
    public static void addCdTradingRecord(CdTradingRecordService cdTradingRecordService,  String userId, String userName,
                                          String orderUuid, BigDecimal handleSum, BigDecimal usableSum, BigDecimal cost,
                                          String paybank, String fundType, String fundMode, BigDecimal handleScore,
                                          BigDecimal usableScore){

        CdTradingRecord f = new CdTradingRecord();
        f.setUserId(userId);
        f.setUserName(userName);
        f.setName(userName);
        f.setOrderUuid(orderUuid);
        f.setHandleSum(handleSum);
        f.setUsableSum(usableSum);
        f.setCost(cost);
        f.setPaybank(paybank);
        f.setFundType(fundType);
        f.setFundMode(fundMode);
        f.setHandleScore(handleScore);
        f.setUsableScore(usableScore);
        cdTradingRecordService.save(f);
    }



    /**
     * wangsong,
     * 171222,
     * 添加会员积分记录
     */
    public static void addBuyScore(CdScoreService cdScoreService, String userId, String userName,
                                      BigDecimal inScore, BigDecimal outSore, String scoreExplain,
                                      String scoreType){

        CdScore s = new CdScore();
        s.setUserId(userId);
        s.setUserName(userName);
        s.setName(userName);
        s.setInScore(inScore);//获得积分数量
        s.setOutScore(outSore);//使用积分数量
        s.setScoreExplain(scoreExplain);//积分说明（0充值1签到2购彩）
        s.setScoreType(scoreType);//积分类型（0获得1消耗）
        cdScoreService.save(s);
    }


    /**
     * anbo,2017-10-20,添加会员提现记录
     */
    public static void addScoreTxRecord(CdWithdrawalService abWithdrawalService,String userId,String userName,String txCard,
                                        BigDecimal txMoney,BigDecimal syMoney,String txStatus,String bankName,String txNumber,BigDecimal cost,String pingId){

        CdWithdrawal w = new CdWithdrawal();
        w.setUserId(userId);
        w.setName(userName);
        w.setTxCard(txCard);//银行卡号
        w.setTxMoney(txMoney);//提现金额
        w.setSyMoney(syMoney);//剩余金额
        w.setTxStatus(txStatus);//状态
        w.setBankName(bankName);//银行名称
        w.setTxNumber(txNumber);//提现流水号
        w.setCost(cost);//手续费
        w.setPingId(pingId);//pingId1
        abWithdrawalService.save(w);
    }


    /**
     * anbo,2017-10-09,添加金额交易记录
     */
    public static void addTradingRecord(CdTradingRecordService cdTradingRecordService,String userId,String userName,
                                     String orderUuid,BigDecimal handleSum,BigDecimal usableSum,BigDecimal cost,String paybank,
                                     String fundType,String fundMode,BigDecimal handleScore,BigDecimal usableScore){

        CdTradingRecord f = new CdTradingRecord();
        f.setUserId(userId);//用户id
        f.setUserName(userName);//用户名
        f.setName(userName);//用户名
        f.setOrderUuid(orderUuid);//流水号
        f.setHandleSum(handleSum);//操作金额
        f.setUsableSum(usableSum);//可用金额
        f.setCost(cost);//服务费
        f.setPaybank(paybank);//银行名
        f.setFundType(fundType);//交易类型(0金额1积分)
        f.setFundMode(fundMode);//操作类型(充值.提现.消费1)
        f.setHandleScore(handleScore);//操作积分
        f.setUsableScore(usableScore);//可用积分1

        cdTradingRecordService.save(f);
    }

}
