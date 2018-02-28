/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cdoubleresult.entity;

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
 * 足球半全场开奖信息Entity
 * @author RenHaipeng
 * @version 2018-01-08
 */
@Entity
@Table(name = "cd_double_result")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdDoubleResult extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; //
	private String homeTeam; //
	private String awayTeam; //
	private String weekday; //
	private String currentSales; //
	private String jackpot; //
	private String hs; //
	private String vs; //
	private String notesNum; //
	private String perNoteMoney; //
	private String winNum;//开奖号
	private String result;//进球数
	public CdDoubleResult() {
		super();
	}

	public CdDoubleResult(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_double_result")
	//@SequenceGenerator(name = "seq_cd_double_result", sequenceName = "seq_cd_double_result")
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

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public String getCurrentSales() {
		return currentSales;
	}

	public void setCurrentSales(String currentSales) {
		this.currentSales = currentSales;
	}

	public String getJackpot() {
		return jackpot;
	}

	public void setJackpot(String jackpot) {
		this.jackpot = jackpot;
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

	public String getNotesNum() {
		return notesNum;
	}

	public void setNotesNum(String notesNum) {
		this.notesNum = notesNum;
	}

	public String getPerNoteMoney() {
		return perNoteMoney;
	}

	public void setPerNoteMoney(String perNoteMoney) {
		this.perNoteMoney = perNoteMoney;
	}

	public String getWinNum() {
		return winNum;
	}

	public void setWinNum(String winNum) {
		this.winNum = winNum;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}


