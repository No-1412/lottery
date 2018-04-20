package com.youge.yogee.modules.bonusOptimization.entity;

import java.util.List;

/**
 * 奖金优化中的平均、搏冷、博热优化方法参数bean
 * Created by ab on 2018/4/19.
 */
public class ParamVO {

    private int amountBets;//投注金额
   // private int type;//优化方法，1-平均，2-博热，3-搏冷
    private List<PortfolioVO> portfolioVOList;//

    public int getAmountBets() {
        return amountBets;
    }

    public void setAmountBets(int amountBets) {
        this.amountBets = amountBets;
    }

    public List<PortfolioVO> getPortfolioVOList() {
        return portfolioVOList;
    }

    public void setPortfolioVOList(List<PortfolioVO> portfolioVOList) {
        this.portfolioVOList = portfolioVOList;
    }


}
