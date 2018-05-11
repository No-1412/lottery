/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.entity;

import com.youge.yogee.common.persistence.BaseEntity;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 业绩Entity
 * @author RenHaipeng
 * @version 2018-03-07
 */
@Entity
@Table(name = "cd_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ErpOrder extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 用户名称
	private ErpUser userId;	//用户
	private String type;//彩票种类
	private BigDecimal totalPrice;	//购买的金额
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; 	// 备注
	private String number; 	// 彩种订单号
	private String issue="0"; 	// 是否跟单
	private String win; 	// 发起跟单id，本表id
	//add  2018-05-11 yhw
	private String outTime;//出票时间

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public ErpOrder() {
		super();
	}

	public ErpOrder(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_order")
	//@SequenceGenerator(name = "seq_cd_order", sequenceName = "seq_cd_order")
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

	@ManyToOne
	@JoinColumn(name="userId")
	@NotFound(action = NotFoundAction.IGNORE)
	public ErpUser getUserId() {
		return userId;
	}

	public void setUserId(ErpUser userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getWin() {
		return win;
	}

	public void setWin(String win) {
		this.win = win;
	}
}


