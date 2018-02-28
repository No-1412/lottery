/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cwithdrawal.entity;

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
 * 提现记录Entity
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Entity
@Table(name = "cd_withdrawal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdWithdrawal extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称

	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; 	// 备注

	private String txCard; 	// 提现银行卡
	private BigDecimal txMoney; 	// 提现金额
	private BigDecimal syMoney; 	// 剩余金额
	private String userId; 	// 关联用户id
	private String txStatus; 	// 1新创建（未审核）  2 已提现（成功） 3 处理中（已审核） 4转账中 5提现金额返还6提现失败(撤销)7退票
	private String bankName; 	// 银行名称
	private String txNumber; 	// 提现流水号
	private BigDecimal cost; 	// 手续费
	private String pingId; 	// ping++ Id

	public CdWithdrawal() {
		super();
	}

	public CdWithdrawal(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_withdrawal")
	//@SequenceGenerator(name = "seq_cd_withdrawal", sequenceName = "seq_cd_withdrawal")
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

	public String getTxCard() {
		return txCard;
	}

	public void setTxCard(String txCard) {
		this.txCard = txCard;
	}

	public BigDecimal getTxMoney() {
		return txMoney;
	}

	public void setTxMoney(BigDecimal txMoney) {
		this.txMoney = txMoney;
	}

	public BigDecimal getSyMoney() {
		return syMoney;
	}

	public void setSyMoney(BigDecimal syMoney) {
		this.syMoney = syMoney;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getTxNumber() {
		return txNumber;
	}

	public void setTxNumber(String txNumber) {
		this.txNumber = txNumber;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getPingId() {
		return pingId;
	}

	public void setPingId(String pingId) {
		this.pingId = pingId;
	}
}


