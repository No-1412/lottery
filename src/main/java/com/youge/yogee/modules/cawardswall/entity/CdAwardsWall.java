/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cawardswall.entity;

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
 * 大奖墙Entity
 * @author WeiJinChao
 * @version 2017-12-21
 */
@Entity
@Table(name = "cd_awards_wall")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdAwardsWall extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; //
	private String name; //标题
	private String createDate; //时间
	private String delFlag; //
	private String remarks; //
	private String prize; //奖金
	private String issue; //期次
	private String intro; //简介
	private String content; //内容
	private String dianzanCount; //点赞数量

	public CdAwardsWall() {
		super();
	}

	public CdAwardsWall(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_awards_wall")
	//@SequenceGenerator(name = "seq_cd_awards_wall", sequenceName = "seq_cd_awards_wall")
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

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDianzanCount() {
		return dianzanCount;
	}

	public void setDianzanCount(String dianzanCount) {
		this.dianzanCount = dianzanCount;
	}
}


