/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.clottoreward.entity;

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
 * 大乐透开奖结果Entity
 * @author RenHaipeng
 * @version 2018-01-05
 */
@Entity
@Table(name = "cd_lotto_reward")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdLottoReward extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; //
	private String matchId; //期次
	private String openingTime; //开奖时间
	private String number; //中奖号码
	private String currentSales; //本期销量
	private String jackpot; //累计奖池
	private String notesNum; //
	private String perNoteMoney; //
	public CdLottoReward() {
		super();
	}

	public CdLottoReward(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_lotto_reward")
	//@SequenceGenerator(name = "seq_cd_lotto_reward", sequenceName = "seq_cd_lotto_reward")
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

	public String getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
}


