/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfboutcome.entity;

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
 * 竞彩足球详情近期赛事Entity
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Entity
@Table(name = "cd_fb_outcome")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdFbOutcome extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)


	private String remarks; //
	private String matchId; //场次
	private String eventName; //赛事名称
	private String hn; //主队
	private String gn; //客队
	private String hsc; //主队比分
	private String gsc; //客队比分
	private String teamId; //队伍id
	private String mt; //赛事时间
	private String outcomeType; //战绩类型 0近期1历史
	private String teamHid; //主队id
	private String teamAid; //客队id
	private String itemid; //详细场次id
	public CdFbOutcome() {
		super();
	}

	public CdFbOutcome(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_fb_outcome")
	//@SequenceGenerator(name = "seq_cd_fb_outcome", sequenceName = "seq_cd_fb_outcome")
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

	public String getHsc() {
		return hsc;
	}

	public void setHsc(String hsc) {
		this.hsc = hsc;
	}

	public String getGsc() {
		return gsc;
	}

	public void setGsc(String gsc) {
		this.gsc = gsc;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
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

	public String getTeamHid() {
		return teamHid;
	}

	public void setTeamHid(String teamHid) {
		this.teamHid = teamHid;
	}

	public String getTeamAid() {
		return teamAid;
	}

	public void setTeamAid(String teamAid) {
		this.teamAid = teamAid;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
}


