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
 * 篮球实力PKEntity
 * @author RenHaipeng
 * @version 2018-01-17
 */
@Entity
@Table(name = "cd_bb_strengthpk")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBbStrengthpk extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; //
	private String averagingStatistics; //
	private String nameHa; //
	private String locationHa; //
	private String stageHa; //
	private String timeHa; //
	private String scoreHa; //
	private String backboardHa; //
	private String assistHa; //
	private String stealHa; //
	private String shootHa; //
	private String trisectionHa; //
	private String penaltyHa; //
	private String closeHa; //
	private String nameHb; //
	private String locationHb; //
	private String stageHb; //
	private String timeHb; //
	private String scoreHb; //
	private String backboardHb; //
	private String assistHb; //
	private String stealHb; //
	private String shootHb; //
	private String trisectionHb; //
	private String penaltyHb; //
	private String closeHb; //
	private String hn; //
	private String vn; //
	private String zid;
	private String itemId;
	private String jstj;
	public CdBbStrengthpk() {
		super();
	}

	public CdBbStrengthpk(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_bb_strengthpk")
	//@SequenceGenerator(name = "seq_cd_bb_strengthpk", sequenceName = "seq_cd_bb_strengthpk")
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

	public String getAveragingStatistics() {
		return averagingStatistics;
	}

	public void setAveragingStatistics(String averagingStatistics) {
		this.averagingStatistics = averagingStatistics;
	}

	public String getNameHa() {
		return nameHa;
	}

	public void setNameHa(String nameHa) {
		this.nameHa = nameHa;
	}

	public String getLocationHa() {
		return locationHa;
	}

	public void setLocationHa(String locationHa) {
		this.locationHa = locationHa;
	}

	public String getStageHa() {
		return stageHa;
	}

	public void setStageHa(String stageHa) {
		this.stageHa = stageHa;
	}

	public String getTimeHa() {
		return timeHa;
	}

	public void setTimeHa(String timeHa) {
		this.timeHa = timeHa;
	}

	public String getScoreHa() {
		return scoreHa;
	}

	public void setScoreHa(String scoreHa) {
		this.scoreHa = scoreHa;
	}

	public String getBackboardHa() {
		return backboardHa;
	}

	public void setBackboardHa(String backboardHa) {
		this.backboardHa = backboardHa;
	}

	public String getAssistHa() {
		return assistHa;
	}

	public void setAssistHa(String assistHa) {
		this.assistHa = assistHa;
	}

	public String getStealHa() {
		return stealHa;
	}

	public void setStealHa(String stealHa) {
		this.stealHa = stealHa;
	}

	public String getShootHa() {
		return shootHa;
	}

	public void setShootHa(String shootHa) {
		this.shootHa = shootHa;
	}

	public String getTrisectionHa() {
		return trisectionHa;
	}

	public void setTrisectionHa(String trisectionHa) {
		this.trisectionHa = trisectionHa;
	}

	public String getPenaltyHa() {
		return penaltyHa;
	}

	public void setPenaltyHa(String penaltyHa) {
		this.penaltyHa = penaltyHa;
	}

	public String getCloseHa() {
		return closeHa;
	}

	public void setCloseHa(String closeHa) {
		this.closeHa = closeHa;
	}

	public String getNameHb() {
		return nameHb;
	}

	public void setNameHb(String nameHb) {
		this.nameHb = nameHb;
	}

	public String getLocationHb() {
		return locationHb;
	}

	public void setLocationHb(String locationHb) {
		this.locationHb = locationHb;
	}

	public String getStageHb() {
		return stageHb;
	}

	public void setStageHb(String stageHb) {
		this.stageHb = stageHb;
	}

	public String getTimeHb() {
		return timeHb;
	}

	public void setTimeHb(String timeHb) {
		this.timeHb = timeHb;
	}

	public String getScoreHb() {
		return scoreHb;
	}

	public void setScoreHb(String scoreHb) {
		this.scoreHb = scoreHb;
	}

	public String getBackboardHb() {
		return backboardHb;
	}

	public void setBackboardHb(String backboardHb) {
		this.backboardHb = backboardHb;
	}

	public String getAssistHb() {
		return assistHb;
	}

	public void setAssistHb(String assistHb) {
		this.assistHb = assistHb;
	}

	public String getStealHb() {
		return stealHb;
	}

	public void setStealHb(String stealHb) {
		this.stealHb = stealHb;
	}

	public String getShootHb() {
		return shootHb;
	}

	public void setShootHb(String shootHb) {
		this.shootHb = shootHb;
	}

	public String getTrisectionHb() {
		return trisectionHb;
	}

	public void setTrisectionHb(String trisectionHb) {
		this.trisectionHb = trisectionHb;
	}

	public String getPenaltyHb() {
		return penaltyHb;
	}

	public void setPenaltyHb(String penaltyHb) {
		this.penaltyHb = penaltyHb;
	}

	public String getCloseHb() {
		return closeHb;
	}

	public void setCloseHb(String closeHb) {
		this.closeHb = closeHb;
	}

	public String getHn() {
		return hn;
	}

	public void setHn(String hn) {
		this.hn = hn;
	}

	public String getVn() {
		return vn;
	}

	public void setVn(String vn) {
		this.vn = vn;
	}

	@Override
	public String toString() {
		return "CdBbStrengthpk{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", createDate='" + createDate + '\'' +
				", delFlag='" + delFlag + '\'' +
				", remarks='" + remarks + '\'' +
				", averagingStatistics='" + averagingStatistics + '\'' +
				", nameHa='" + nameHa + '\'' +
				", locationHa='" + locationHa + '\'' +
				", stageHa='" + stageHa + '\'' +
				", timeHa='" + timeHa + '\'' +
				", scoreHa='" + scoreHa + '\'' +
				", backboardHa='" + backboardHa + '\'' +
				", assistHa='" + assistHa + '\'' +
				", stealHa='" + stealHa + '\'' +
				", shootHa='" + shootHa + '\'' +
				", trisectionHa='" + trisectionHa + '\'' +
				", penaltyHa='" + penaltyHa + '\'' +
				", closeHa='" + closeHa + '\'' +
				", nameHb='" + nameHb + '\'' +
				", locationHb='" + locationHb + '\'' +
				", stageHb='" + stageHb + '\'' +
				", timeHb='" + timeHb + '\'' +
				", scoreHb='" + scoreHb + '\'' +
				", backboardHb='" + backboardHb + '\'' +
				", assistHb='" + assistHb + '\'' +
				", stealHb='" + stealHb + '\'' +
				", shootHb='" + shootHb + '\'' +
				", trisectionHb='" + trisectionHb + '\'' +
				", penaltyHb='" + penaltyHb + '\'' +
				", closeHb='" + closeHb + '\'' +
				", hn='" + hn + '\'' +
				", vn='" + vn + '\'' +
				'}';
	}

	public String getZid() {
		return zid;
	}

	public void setZid(String zid) {
		this.zid = zid;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getJstj() {
		return jstj;
	}

	public void setJstj(String jstj) {
		this.jstj = jstj;
	}
}


