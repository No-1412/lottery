/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballorder.entity;

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
 * 竞彩足球订单Entity
 * @author ZhaoYiFeng
 * @version 2018-02-24
 */
@Entity
@Table(name = "cd_football_follow_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdFootballFollowOrder extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)

	private String followNum;        //串数字
	private String orderNum;        // 订单号
	private String orderDetail;     //订单详情  一种玩法用,分割押注 用/分隔比赛 混投用|分割
	private String buyWays;        // 购买彩种 1混投 2胜负平 3猜比分 4总进球 5半全场 6让球

	private String acount;        // 注数
	private String price;        //金额
	private String award;        // 奖金
	private String uid;        // 用户id
	private String stauts;        // 1已提交 2已付款
	private String remarks;      //备注
    private String  times;       //倍数


	private String score;       //比分
	private String goal;        //总进球
	private String half;        //半全场
	private String beat;        //胜负平
	private String let;         //让球胜负平
	private String letBalls;     //让球数 ，分割

	private String danMatchIds; //胆场次

	public CdFootballFollowOrder() {
		super();
	}

	public CdFootballFollowOrder(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_football_follow_order")
	//@SequenceGenerator(name = "seq_cd_football_follow_order", sequenceName = "seq_cd_football_follow_order")
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

	public String getFollowNum() {
		return followNum;
	}

	public void setFollowNum(String followNum) {
		this.followNum = followNum;
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

	public String getStauts() {
		return stauts;
	}

	public void setStauts(String stauts) {
		this.stauts = stauts;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getHalf() {
		return half;
	}

	public void setHalf(String half) {
		this.half = half;
	}

	public String getBeat() {
		return beat;
	}

	public void setBeat(String beat) {
		this.beat = beat;
	}

	public String getLet() {
		return let;
	}

	public void setLet(String let) {
		this.let = let;
	}

	public String getLetBalls() {
		return letBalls;
	}

	public void setLetBalls(String letBalls) {
		this.letBalls = letBalls;
	}


	public String getDanMatchIds() {
		return danMatchIds;
	}

	public void setDanMatchIds(String danMatchIds) {
		this.danMatchIds = danMatchIds;
	}
}


