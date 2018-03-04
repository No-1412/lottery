/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.corder.entity;

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
 * 竞彩篮球订单Entity
 * @author ZhaoYiFeng
 * @version 2018-03-04
 */
@Entity
@Table(name = "cd_order_follow_times")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdOrderFollowTimes extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)

	private String times;      //跟单倍数
	private String remarks;    //

	public CdOrderFollowTimes() {
		super();
	}

	public CdOrderFollowTimes(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_order_follow_times")
	//@SequenceGenerator(name = "seq_cd_order_follow_times", sequenceName = "seq_cd_order_follow_times")
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

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}


