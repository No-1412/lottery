package com.youge.yogee.modules.bonusOptimization.entity.football;

import java.util.List;

/**
 * 足球
 * Created by ab on 2018/4/14.
 */
public class CaipiaoFootballBean {
    private String  buyWays;
    private String uid;
    private String followNum;
    private String times;
    private List<DetailFootballBean> detail;

    public String getBuyWays() {
        return buyWays;
    }

    public void setBuyWays(String buyWays) {
        this.buyWays = buyWays;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFollowNum() {
        return followNum;
    }

    public void setFollowNum(String followNum) {
        this.followNum = followNum;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public List<DetailFootballBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailFootballBean> detail) {
        this.detail = detail;
    }
}
