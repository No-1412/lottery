/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballorder.entity;

import com.youge.yogee.common.persistence.BaseEntity;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 篮球优化Entity
 * @author ZhaoYiFeng
 * @version 2018-04-18
 */
@Entity
@Table(name = "cd_basketball_best_follow_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBasketballBestFollowOrder extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)

	private String orderNum;//订单号
	private String orderDetail;//订单详情
	private String matchIds;//所有比赛
	private String perTimes;//单注倍数
	private String perAward;//奖金


	public CdBasketballBestFollowOrder() {
		super();
	}

	public CdBasketballBestFollowOrder(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_basketball_best_follow_order")
	//@SequenceGenerator(name = "seq_cd_basketball_best_follow_order", sequenceName = "seq_cd_basketball_best_follow_order")
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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getMatchIds() {
		return matchIds;
	}

	public void setMatchIds(String matchIds) {
		this.matchIds = matchIds;
	}

	public String getPerTimes() {
		return perTimes;
	}

	public void setPerTimes(String perTimes) {
		this.perTimes = perTimes;
	}

	public String getPerAward() {
		return perAward;
	}

	public void setPerAward(String perAward) {
		this.perAward = perAward;
	}
}


