package com.youge.yogee.modules.bonusOptimization.entity;

import java.util.List;

/**
 * Created by ab on 2018/4/14.
 */
public class PortfolioVO {
    private List<PortfolioChilVO> portfolioChilVOList;
    private double dbjj;
    private int beishu;
    private double jiangjin;

    public List<PortfolioChilVO> getPortfolioChilVOList() {
        return portfolioChilVOList;
    }

    public void setPortfolioChilVOList(List<PortfolioChilVO> portfolioChilVOList) {
        this.portfolioChilVOList = portfolioChilVOList;
    }

    public double getDbjj() {
        return dbjj;
    }

    public void setDbjj(double dbjj) {
        this.dbjj = dbjj;
    }

    public int getBeishu() {
        return beishu;
    }

    public void setBeishu(int beishu) {
        this.beishu = beishu;
    }

    public double getJiangjin() {
        return jiangjin;
    }

    public void setJiangjin(double jiangjin) {
        this.jiangjin = jiangjin;
    }
}
