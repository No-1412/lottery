/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.entity;

import com.youge.yogee.common.persistence.BaseEntity;
import com.youge.yogee.modules.usm.entity.UsmUser;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 意见反馈Entity
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Entity
@Table(name = "bm_feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BmFeedback extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private UsmUser userId;	 //所属用户id
	private String content;	 //详情
	private String imgs;	 //截图
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)

	public BmFeedback() {
		super();
	}

	public BmFeedback(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bm_feedback")
	//@SequenceGenerator(name = "seq_bm_feedback", sequenceName = "seq_bm_feedback")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "userId")
	@NotFound(action = NotFoundAction.IGNORE)
	public UsmUser getUserId() {
		return userId;
	}

	public void setUserId(UsmUser userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
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


