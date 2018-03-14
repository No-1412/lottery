/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbstrengthpk.entity;

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
 * 球员场均数据Entity
 * @author RenHaipeng
 * @version 2018-03-12
 */
@Entity
@Table(name = "cd_bb_strengthpk_average")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBbStrengthpkAverage extends BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String itemId;	//球队ID
	private String name;	 //球员名字
	private String location;	 //位置
	private String stage;	 //出场
	private String time;	 //时间
	private String score;	 //得分
	private String backboard;	 //篮板
	private String assist;	 //助攻
	private String steal;	 //抢断
	private String shoot;	 //投篮
	private String trisection;	 //三分
	private String penalty;	 //罚球
	private String close;	 //封盖
	private String team;	 //队伍（1主队,2客队）
	private String createDate;	 //
	private String delFlag;	 //

	public CdBbStrengthpkAverage() {
		super();
	}

	public CdBbStrengthpkAverage(String id){
		this();
		this.id = id;
	}

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_bb_strengthpk_average")
	//@SequenceGenerator(name = "seq_cd_bb_strengthpk_average", sequenceName = "seq_cd_bb_strengthpk_average")
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


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getBackboard() {
		return backboard;
	}

	public void setBackboard(String backboard) {
		this.backboard = backboard;
	}

	public String getAssist() {
		return assist;
	}

	public void setAssist(String assist) {
		this.assist = assist;
	}

	public String getSteal() {
		return steal;
	}

	public void setSteal(String steal) {
		this.steal = steal;
	}

	public String getShoot() {
		return shoot;
	}

	public void setShoot(String shoot) {
		this.shoot = shoot;
	}

	public String getTrisection() {
		return trisection;
	}

	public void setTrisection(String trisection) {
		this.trisection = trisection;
	}

	public String getPenalty() {
		return penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}


