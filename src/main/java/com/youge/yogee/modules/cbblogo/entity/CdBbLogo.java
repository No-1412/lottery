/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbblogo.entity;

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
 * 篮球logoEntity
 * @author ZhaoYiFeng
 * @version 2018-04-01
 */
@Entity
@Table(name = "cd_bb_logo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBbLogo extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name;	 //球队名称
	private String logo;	 //logo
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)

	public CdBbLogo() {
		super();
	}

	public CdBbLogo(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_bb_logo")
	//@SequenceGenerator(name = "seq_cd_bb_logo", sequenceName = "seq_cd_bb_logo")
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
}


