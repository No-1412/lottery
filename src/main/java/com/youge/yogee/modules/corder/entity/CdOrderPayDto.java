package com.youge.yogee.modules.corder.entity;

public class CdOrderPayDto {
    private String id;        // 识别id
    private String createDate;    // 创建时间
    private String orderNum;        // 订单号
    private String nums;        // 买的数字
    private String weekday;        // 期数
    private String acount;        // 注数
    private String price;        //金额
    private String award;        // 奖金
    private String uid;        // 用户id
    private String status;        // 1已提交 2已付款
    private String type;   //1排列三 2排列五 3大乐透
    private String times;  //倍数
    private String continuity; //连续期数
    private String weekContinue;//继续买的期数
    private String result;//比赛结果

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getAcount() {
        return acount;
    }

    public void setAcount(String acount) {
        this.acount = acount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getContinuity() {
        return continuity;
    }

    public void setContinuity(String continuity) {
        this.continuity = continuity;
    }

    public String getWeekContinue() {
        return weekContinue;
    }

    public void setWeekContinue(String weekContinue) {
        this.weekContinue = weekContinue;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
