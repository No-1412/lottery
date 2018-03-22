/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbblive.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.youge.yogee.common.persistence.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.validator.constraints.Length;

import com.youge.yogee.modules.sys.entity.User;

/**
 * 伤停补时数据Entity
 * @author ZhaoYiFeng
 * @version 2018-03-20
 */
@Entity
@Table(name = "cd_bb_live")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBbLive extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String hnScore;	 //主队比分
	private String gnScore;	 //客队比分
	private String hnSkill;	 //主队技术统计
	private String gnSkill;	 //客队技术统计
	private String hnPlayer;	 //主队球员统计
	private String gnPlayer;	 //客队球员统计
	private String itemid;	 //
	private String createDate;	 //
	private String delFlag;	 //

	public CdBbLive() {
		super();
	}

	public CdBbLive(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_bb_live")
	//@SequenceGenerator(name = "seq_cd_bb_live", sequenceName = "seq_cd_bb_live")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHnScore() {
		return hnScore;
	}

	public void setHnScore(String hnScore) {
		this.hnScore = hnScore;
	}

	public String getGnScore() {
		return gnScore;
	}

	public void setGnScore(String gnScore) {
		this.gnScore = gnScore;
	}

	public String getHnSkill() {
		return hnSkill;
	}

	public void setHnSkill(String hnSkill) {
		this.hnSkill = hnSkill;
	}

	public String getGnSkill() {
		return gnSkill;
	}

	public void setGnSkill(String gnSkill) {
		this.gnSkill = gnSkill;
	}

	public String getHnPlayer() {
		return hnPlayer;
	}

	public void setHnPlayer(String hnPlayer) {
		this.hnPlayer = hnPlayer;
	}

	public String getGnPlayer() {
		return gnPlayer;
	}

	public void setGnPlayer(String gnPlayer) {
		this.gnPlayer = gnPlayer;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
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
}


