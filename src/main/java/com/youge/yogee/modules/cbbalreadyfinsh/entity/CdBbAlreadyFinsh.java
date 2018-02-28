/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbalreadyfinsh.entity;

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
 * 篮球已完赛Entity
 * @author WeiJinChao
 * @version 2018-01-30
 */
@Entity
@Table(name = "cd_bb_alreadyfinsh")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBbAlreadyFinsh extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; //
	private String type; //
	private String hf; //
	private String hn; //
	private String gn; //
	private String gf; //
	private String day; //
	private String matchId; //
	private String hnImg;
	private String gnImg;
	private String zid;

	public CdBbAlreadyFinsh() {
		super();
	}

	public CdBbAlreadyFinsh(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_bb_alreadyfinsh")
	//@SequenceGenerator(name = "seq_cd_bb_alreadyfinsh", sequenceName = "seq_cd_bb_alreadyfinsh")
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHf() {
		return hf;
	}

	public void setHf(String hf) {
		this.hf = hf;
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

	public String getGf() {
		return gf;
	}

	public void setGf(String gf) {
		this.gf = gf;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getHnImg() {
		return hnImg;
	}

	public void setHnImg(String hnImg) {
		this.hnImg = hnImg;
	}

	public String getGnImg() {
		return gnImg;
	}

	public void setGnImg(String gnImg) {
		this.gnImg = gnImg;
	}

	public String getZid() {
		return zid;
	}

	public void setZid(String zid) {
		this.zid = zid;
	}
}


