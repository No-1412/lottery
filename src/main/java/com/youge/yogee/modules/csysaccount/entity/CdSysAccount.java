/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csysaccount.entity;

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
 * 平台系统账户Entity
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Entity
@Table(name = "cd_sys_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdSysAccount extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称

	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; 	// 备注

	private BigDecimal balanceScore; 	// 余额积分
	private BigDecimal frzeeScore; 	// 冻结积分
	private String account; 	// 账号

	private BigDecimal balance; 	// 可用余额
	private BigDecimal frzeeBalance; 	// 冻结余额

	public CdSysAccount() {
		super();
	}

	public CdSysAccount(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_sys_account")
	//@SequenceGenerator(name = "seq_cd_sys_account", sequenceName = "seq_cd_sys_account")
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

	public BigDecimal getBalanceScore() {
		return balanceScore;
	}

	public void setBalanceScore(BigDecimal balanceScore) {
		this.balanceScore = balanceScore;
	}

	public BigDecimal getFrzeeScore() {
		return frzeeScore;
	}

	public void setFrzeeScore(BigDecimal frzeeScore) {
		this.frzeeScore = frzeeScore;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getFrzeeBalance() {
		return frzeeBalance;
	}

	public void setFrzeeBalance(BigDecimal frzeeBalance) {
		this.frzeeBalance = frzeeBalance;
	}
}


