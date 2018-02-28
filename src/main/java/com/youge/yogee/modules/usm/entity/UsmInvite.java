/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.usm.entity;

import com.youge.yogee.common.persistence.BaseEntity;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户邀请Entity
 * @author RenHaipeng
 * @version 2016-12-16
 */
@Entity
@Table(name = "usm_invite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UsmInvite extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private UsmUser inviter;	 //邀请人id
	private UsmUser invitee;	 //被邀请人id
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)

	public UsmInvite() {
		super();
	}

	public UsmInvite(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usm_invite")
	//@SequenceGenerator(name = "seq_usm_invite", sequenceName = "seq_usm_invite")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "inviter")
	@NotFound(action = NotFoundAction.IGNORE)
	public UsmUser getInviter() {
		return inviter;
	}

	public void setInviter(UsmUser inviter) {
		this.inviter = inviter;
	}

	@ManyToOne
	@JoinColumn(name = "invitee")
	@NotFound(action = NotFoundAction.IGNORE)
	public UsmUser getInvitee() {
		return invitee;
	}

	public void setInvitee(UsmUser invitee) {
		this.invitee = invitee;
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


