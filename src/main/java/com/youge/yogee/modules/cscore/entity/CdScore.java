/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cscore.entity;

import com.youge.yogee.common.persistence.BaseEntity;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户积分信息Entity
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Entity
@Table(name = "cd_score")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdScore extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称

	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; 	// 备注


	private String userId; 	// 用户id
	private String userName; 	// 用户名
	private BigDecimal inScore; 	// 获得积分数量
	private BigDecimal outScore; 	// 使用积分数量
	private String scoreExplain; 	// 积分说明
	private String scoreType; // 积分类型（0获得1消耗）

	public CdScore() {
		super();
	}

	public CdScore(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_score")
	//@SequenceGenerator(name = "seq_cd_score", sequenceName = "seq_cd_score")
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getInScore() {
		return inScore;
	}

	public void setInScore(BigDecimal inScore) {
		this.inScore = inScore;
	}

	public BigDecimal getOutScore() {
		return outScore;
	}

	public void setOutScore(BigDecimal outScore) {
		this.outScore = outScore;
	}

	public String getScoreExplain() {
		return scoreExplain;
	}

	public void setScoreExplain(String scoreExplain) {
		this.scoreExplain = scoreExplain;
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}
}


