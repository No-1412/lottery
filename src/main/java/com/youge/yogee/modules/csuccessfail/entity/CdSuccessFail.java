/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csuccessfail.entity;

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
 * 胜负彩Entity
 * @author RenHaipeng
 * @version 2018-01-04
 */
@Entity
@Table(name = "cd_success_fail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdSuccessFail extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; //
	private String matchId; //场次id
	private String eventName; //
	private String matchDate; //
	private String homeTeam; //
	private String awayTeam; //
	private String winningOdds; //
	private String flatOdds; //
	private String defeatedOdds; //
	private String timeEndSale; //
	private String weekday; //
	public CdSuccessFail() {
		super();
	}

	public CdSuccessFail(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_success_fail")
	//@SequenceGenerator(name = "seq_cd_success_fail", sequenceName = "seq_cd_success_fail")
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

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public String getWinningOdds() {
		return winningOdds;
	}

	public void setWinningOdds(String winningOdds) {
		this.winningOdds = winningOdds;
	}

	public String getFlatOdds() {
		return flatOdds;
	}

	public void setFlatOdds(String flatOdds) {
		this.flatOdds = flatOdds;
	}

	public String getDefeatedOdds() {
		return defeatedOdds;
	}

	public void setDefeatedOdds(String defeatedOdds) {
		this.defeatedOdds = defeatedOdds;
	}

	public String getTimeEndSale() {
		return timeEndSale;
	}

	public void setTimeEndSale(String timeEndSale) {
		this.timeEndSale = timeEndSale;
	}

	public String getWeekday() {
		return weekday;
	}

	@Override
	public String toString() {
		return "CdSuccessFail{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", createDate='" + createDate + '\'' +
				", delFlag='" + delFlag + '\'' +
				", remarks='" + remarks + '\'' +
				", matchId='" + matchId + '\'' +
				", eventName='" + eventName + '\'' +
				", matchDate='" + matchDate + '\'' +
				", homeTeam='" + homeTeam + '\'' +
				", awayTeam='" + awayTeam + '\'' +
				", winningOdds='" + winningOdds + '\'' +
				", flatOdds='" + flatOdds + '\'' +
				", defeatedOdds='" + defeatedOdds + '\'' +
				", timeEndSale='" + timeEndSale + '\'' +
				", weekday='" + weekday + '\'' +
				'}';
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
}


