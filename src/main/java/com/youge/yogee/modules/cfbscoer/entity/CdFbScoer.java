/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbscoer.entity;

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
 * 竞彩足球积分Entity
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Entity
@Table(name = "cd_fb_scoer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdFbScoer extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; //
	private String matchId; //场次
	private String teamname; //队伍名称
	private String teamId; //队id
	private String rank; //排名
	private String points; //总分
	private String scoerAll; //全部积分
	private String host; //主队积分
	private String guest; //客队积分
	private String hasdetailrank; //详情排名
	private String teamHid; //主队id
	private String teamAid; //客队id
	private String itemid; //详细场次id

	public CdFbScoer() {
		super();
	}

	public CdFbScoer(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_fb_scoer")
	//@SequenceGenerator(name = "seq_cd_fb_scoer", sequenceName = "seq_cd_fb_scoer")
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

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getScoerAll() {
		return scoerAll;
	}

	public void setScoerAll(String scoerAll) {
		this.scoerAll = scoerAll;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getGuest() {
		return guest;
	}

	public void setGuest(String guest) {
		this.guest = guest;
	}

	public String getHasdetailrank() {
		return hasdetailrank;
	}

	public void setHasdetailrank(String hasdetailrank) {
		this.hasdetailrank = hasdetailrank;
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


