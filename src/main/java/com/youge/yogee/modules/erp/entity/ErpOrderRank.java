/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.entity;

import com.youge.yogee.common.persistence.BaseEntity;
import com.youge.yogee.modules.sys.entity.User;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 排行Entity
 * @author RenHaipeng
 * @version 2018-03-08
 */
@Entity
@Table(name = "cd_order_rank")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ErpOrderRank extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称

	private String createDate; 	// 创建时间
	private String remarks; 	// 备注
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private User userId;
	private String since;
	private String documentary;
	private String and;
	private String documentarycount;
	public ErpOrderRank() {
		super();
	}

	public ErpOrderRank(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_order_rank")
	//@SequenceGenerator(name = "seq_cd_order_rank", sequenceName = "seq_cd_order_rank")
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

	@ManyToOne
	@JoinColumn(name="userId")
	@NotFound(action = NotFoundAction.IGNORE)
	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public String getDocumentary() {
		return documentary;
	}

	public void setDocumentary(String documentary) {
		this.documentary = documentary;
	}

	public String getAnd() {
		return and;
	}

	public void setAnd(String and) {
		this.and = and;
	}

	public String getDocumentarycount() {
		return documentarycount;
	}

	public void setDocumentarycount(String documentarycount) {
		this.documentarycount = documentarycount;
	}
}


