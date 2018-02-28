/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbtfuture.entity;

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
 * 竞彩篮球未来赛事Entity
 * @author RenHaipeng
 * @version 2018-01-18
 */
@Entity
@Table(name = "cd_bt_future")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBtFuture extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; //

	private String matchId; //场次
	private String teamId; //队伍id
	private String eventName; //赛事名称
	private String hn; //主队
	private String gn; //客队
	private String mt; //赛事时间
	private String outcomeType; //赛事 0主队1客队
	private String later; //相隔天数
	public CdBtFuture() {
		super();
	}

	public CdBtFuture(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_bt_future")
	//@SequenceGenerator(name = "seq_cd_bt_future", sequenceName = "seq_cd_bt_future")
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

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getHn() {
		return hn;
	}

	public void setHn(String hn) {
		this.hn = hn;
	}

	public String getGn() {
		return gn;
	}

	public void setGn(String gn) {
		this.gn = gn;
	}

	public String getMt() {
		return mt;
	}

	public void setMt(String mt) {
		this.mt = mt;
	}

	public String getOutcomeType() {
		return outcomeType;
	}

	public void setOutcomeType(String outcomeType) {
		this.outcomeType = outcomeType;
	}

	public String getLater() {
		return later;
	}

	public void setLater(String later) {
		this.later = later;
	}

	@Override
	public String toString() {
		return "CdBtFuture{" +
				"matchId='" + matchId + '\'' +
				", teamId='" + teamId + '\'' +
				", eventName='" + eventName + '\'' +
				", hn='" + hn + '\'' +
				", gn='" + gn + '\'' +
				", mt='" + mt + '\'' +
				", outcomeType='" + outcomeType + '\'' +
				'}';
	}
}


