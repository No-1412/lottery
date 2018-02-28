package com.youge.yogee.common.utils;

/**
 * anbo ，2017-10-17， 枚举
 */
public enum FundModel {

	FT_DG_SPF(512,"足球单关胜平负"),
	FT_DG_RQSPF(32,"足球单关让球胜平负"),
	FT_DG_BQC(128,"足球单关半全场"),
	FT_DG_BF(64,"足球单关比分"),
	FT_DG_ZJQ(256,"足球单关总进球"),
	SELL_ZZB(2,"至尊宝收益");
	private int fundNum;
	private String fundDescription;

	FundModel(int fundNum, String fundDescription){
		this.fundNum=fundNum;
		this.fundDescription=fundDescription;
	}
	public static String getFundModelValue(int fundNum){
		for(FundModel fun:values()){
			if(fun.fundNum==fundNum){
				return fun.fundDescription;
			}
		}
		return "";
	}
	public int getFundNum() {
		return fundNum;
	}
	public void setFundNum(int fundNum) {
		this.fundNum = fundNum;
	}
	public String getFundDescription() {
		return fundDescription;
	}
	public void setFundDescription(String fundDescription) {
		this.fundDescription = fundDescription;
	}


	//FundModel.FT_DG_SPF.getFundDescription()
}
