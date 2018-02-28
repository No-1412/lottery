/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballawards.entity;

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
 * 竞彩足球开奖信息Entity
 * @author RenHaipeng
 * @version 2018-01-08
 */
@Entity
@Table(name = "cd_basketball_awards")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBasketballAwards extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; //
	private String matchId; //
	private String eventName; //
	private String homeTeam; //
	private String awayTeam; //
	private String hs; //
	private String vs; //
	private String zclose; //
	private String winning; //
	private String spread; //
	private String winGrap; //
	private String spreadNum; //让球数量
	private String mt;
	public CdBasketballAwards() {
		super();
	}

	public CdBasketballAwards(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_basketball_awards")
	//@SequenceGenerator(name = "seq_cd_basketball_awards", sequenceName = "seq_cd_basketball_awards")
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

	public String getHs() {
		return hs;
	}

	public void setHs(String hs) {
		this.hs = hs;
	}

	public String getVs() {
		return vs;
	}

	public void setVs(String vs) {
		this.vs = vs;
	}

	public String getZclose() {
		return zclose;
	}

	public void setZclose(String zclose) {
		this.zclose = zclose;
	}

	public String getWinning() {
		return winning;
	}

	public void setWinning(String winning) {
		this.winning = winning;
	}

	public String getSpread() {
		return spread;
	}

	public void setSpread(String spread) {
		this.spread = spread;
	}

	public String getWinGrap() {
		return winGrap;
	}

	public void setWinGrap(String winGrap) {
		this.winGrap = winGrap;
	}

	public String getSpreadNum() {
		return spreadNum;
	}

	public void setSpreadNum(String spreadNum) {
		this.spreadNum = spreadNum;
	}

	public String getMt() {
		return mt;
	}

	public void setMt(String mt) {
		this.mt = mt;
	}
}


