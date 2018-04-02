/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.entity;

import com.youge.yogee.common.persistence.BaseEntity;
import com.youge.yogee.modules.sys.entity.User;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 销售后台充值记录Entity
 * @author Lxy
 * @version 2018-03-22
 */
@Entity
@Table(name = "cd_erp_recharge_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ErpRechargeLog extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private User saleId; 	// 销售员id
	private ErpUser userId; 	// 购彩用户id
	private BigDecimal money; 	// 充值金额

	private String remark; 	// 备注
	private String type; 	// 类型（0充值 1扣款）
	private String createDate; 	// 充值时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)

	public ErpRechargeLog() {
		super();
	}

	public ErpRechargeLog(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_erp_recharge_log")
	//@SequenceGenerator(name = "seq_cd_erp_recharge_log", sequenceName = "seq_cd_erp_recharge_log")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	@JoinColumn(name="sale_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public User getSaleId() {
		return saleId;
	}

	public void setSaleId(User saleId) {
		this.saleId = saleId;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public ErpUser getUserId() {
		return userId;
	}

	public void setUserId(ErpUser userId) {
		this.userId = userId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}


