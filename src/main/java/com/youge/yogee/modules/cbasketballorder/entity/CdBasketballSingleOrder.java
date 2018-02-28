/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballorder.entity;

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
 * 竞彩篮球订单Entity
 * @author ZhaoYiFeng
 * @version 2018-02-26
 */
@Entity
@Table(name = "cd_basketball_single_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBasketballSingleOrder extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)


	private String orderNum;        // 订单号
	//private String orderDetail;     //订单详情  场次+主队vs客队+押注结果/赔率/注数|
	private String buyWays;        // 购买彩种 1混投

	private String acount;        // 注数
	private String price;        //金额
	private String award;        // 奖金
	private String uid;        // 用户id
	private String status;        // 1已提交 2已付款
	private String remarks;      //备注

	private String hostWin;        // 主胜
	private String hostFail;      //主负

	public CdBasketballSingleOrder() {
		super();
	}

	public CdBasketballSingleOrder(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_basketball_single_order")
	//@SequenceGenerator(name = "seq_cd_basketball_single_order", sequenceName = "seq_cd_basketball_single_order")
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

//	public String getOrderDetail() {
//		return orderDetail;
//	}
//
//	public void setOrderDetail(String orderDetail) {
//		this.orderDetail = orderDetail;
//	}

	public String getBuyWays() {
		return buyWays;
	}

	public void setBuyWays(String buyWays) {
		this.buyWays = buyWays;
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

	public String getHostWin() {
		return hostWin;
	}

	public void setHostWin(String hostWin) {
		this.hostWin = hostWin;
	}

	public String getHostFail() {
		return hostFail;
	}

	public void setHostFail(String hostFail) {
		this.hostFail = hostFail;
	}
}


