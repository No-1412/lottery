package com.youge.yogee.modules.bonusOptimization.entity;

/**
 * Created by ab on 2018/4/14.
 */
public class PortfolioChilVO {
    /**
     * {
     "name": "格罗兹",
     "sf": "胜",
     "odds": 1.65
     }
     */
    private String name;//参赛队名
    private String sf;//投注结果
    private String odds;//赔率
    private String matchId;//
    private String buyWays;//

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getBuyWays() {
        return buyWays;
    }

    public void setBuyWays(String buyWays) {
        this.buyWays = buyWays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }
}
