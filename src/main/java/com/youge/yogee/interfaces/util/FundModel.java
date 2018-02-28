package com.youge.yogee.interfaces.util;

/**
 * anbo ，2017-10-17， 枚举
 */
public enum FundModel {


	CZ(1,"充值"),
	TX(2,"提现");

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
	
}
