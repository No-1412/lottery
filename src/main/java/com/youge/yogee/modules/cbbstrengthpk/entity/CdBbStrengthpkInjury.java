/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbstrengthpk.entity;

import com.youge.yogee.common.persistence.BaseEntity;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 伤停补时数据Entity
 * @author RenHaipeng
 * @version 2018-03-12
 */
@Entity
@Table(name = "cd_bb_strengthpk_injury")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBbStrengthpkInjury extends BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String itemId;	//
	private String team;	 //球队
	private String player;	 //球员
	private String position;	 //位置
	private String reason;	 //原因
	private String date;	 //日期
	private String remarks;	 //备注
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)

	public CdBbStrengthpkInjury() {
		super();
	}

	public CdBbStrengthpkInjury(String id){
		this();
		this.id = id;
	}

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_bb_strengthpk_injury")
	//@SequenceGenerator(name = "seq_cd_bb_strengthpk_injury", sequenceName = "seq_cd_bb_strengthpk_injury")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}


