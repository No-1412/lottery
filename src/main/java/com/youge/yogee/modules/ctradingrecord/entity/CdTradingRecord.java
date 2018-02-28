/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.ctradingrecord.entity;

import com.youge.yogee.common.persistence.BaseEntity;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 交易记录Entity
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Entity
@Table(name = "cd_trading_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdTradingRecord extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称

	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; 	// 备注


	//积分相关的字段暂时不用了

	private String userId; 	// 用户id
	private String userName; 	// 交易用户姓名
	private String orderUuid; 	// 交易流水号
	private BigDecimal handleSum; 	// 操作金额
	private BigDecimal usableSum; 	// 可用金额
	private BigDecimal cost; 	// 服务费
	private String paybank; 	// 银行名称
	private String fundType; 	// 交易类型（0金额1积分）
	private BigDecimal handleScore; 	// 操作积分
	private BigDecimal usableScore; 	// 可用积分
	private String fundMode;   //操作类型(如充值，提现，消费等,在枚举里创建属性）


	public CdTradingRecord() {
		super();
	}

	public CdTradingRecord(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_trading_record")
	//@SequenceGenerator(name = "seq_cd_trading_record", sequenceName = "seq_cd_trading_record")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Length(min=1, max=200)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderUuid() {
		return orderUuid;
	}

	public void setOrderUuid(String orderUuid) {
		this.orderUuid = orderUuid;
	}

	public BigDecimal getHandleSum() {
		return handleSum;
	}

	public void setHandleSum(BigDecimal handleSum) {
		this.handleSum = handleSum;
	}

	public BigDecimal getUsableSum() {
		return usableSum;
	}

	public void setUsableSum(BigDecimal usableSum) {
		this.usableSum = usableSum;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getPaybank() {
		return paybank;
	}

	public void setPaybank(String paybank) {
		this.paybank = paybank;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public BigDecimal getHandleScore() {
		return handleScore;
	}

	public void setHandleScore(BigDecimal handleScore) {
		this.handleScore = handleScore;
	}

	public BigDecimal getUsableScore() {
		return usableScore;
	}

	public void setUsableScore(BigDecimal usableScore) {
		this.usableScore = usableScore;
	}

	public String getFundMode() {
		return fundMode;
	}

	public void setFundMode(String fundMode) {
		this.fundMode = fundMode;
	}
}


