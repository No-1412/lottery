/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfiveawards.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.youge.yogee.common.persistence.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.validator.constraints.Length;

import com.youge.yogee.modules.sys.entity.User;

/**
 * 排列五订单Entity
 * @author ZhaoYiFeng
 * @version 2018-02-07
 */
@Entity
@Table(name = "cd_five_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdFiveOrder extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;        // 识别id
	private String name;    // 名称

	private String createDate;    // 创建时间
	private String delFlag;    // 删除标识 (0：未删除；1：已删除)

	private String orderNum;        // 订单号
	private String nums;        // 买的数字
	private String allPerhaps;        // 所有可能 |分割
	private String buyWays;        // 购买彩种 1直选 2和值 3组三单式 4组三复式 5组六单式 6组六复式
	private String weekday;        // 期数
	private String acount;        // 注数
	private String price;        //金额
	private String award;        // 奖金
	private String uid;        // 用户id
	private String status;        // 1已提交 2已付款
	private String remarks;      //备注

	public CdFiveOrder() {
		super();
	}

	public CdFiveOrder(String id) {
		this();
		this.id = id;
	}

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_three_order")
	//@SequenceGenerator(name = "seq_cd_three_order", sequenceName = "seq_cd_three_order")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Length(min = 1, max = 200)
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

	public String getNums() {
		return nums;
	}

	public void setNums(String nums) {
		this.nums = nums;
	}

	public String getAllPerhaps() {
		return allPerhaps;
	}

	public void setAllPerhaps(String allPerhaps) {
		this.allPerhaps = allPerhaps;
	}

	public String getBuyWays() {
		return buyWays;
	}

	public void setBuyWays(String buyWays) {
		this.buyWays = buyWays;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}


