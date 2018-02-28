/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.lom.entity;

import com.youge.yogee.common.persistence.BaseEntity;
import com.youge.yogee.modules.usm.entity.UsmUser;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 积分记录Entity
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Entity
@Table(name = "lom_point")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LomPoint extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private UsmUser userId;	 //所属用户id
	private UsmUser invitee;	 //被邀请用户id
	private String kind;	 //分类（0：邀请好友；1：一级分销；2：二级分销； 3：积分兑换）
	private String name;	 //标题
	private String states;	 //状态
	private String points;	 //所得积分（备用，方便核对）
	private String remarks;	 //备注
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)

	public LomPoint() {
		super();
	}

	public LomPoint(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lom_point")
	//@SequenceGenerator(name = "seq_lom_point", sequenceName = "seq_lom_point")
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

	@ManyToOne
	@JoinColumn(name = "userId")
	@NotFound(action = NotFoundAction.IGNORE)
	public UsmUser getUserId() {
		return userId;
	}

	public void setUserId(UsmUser userId) {
		this.userId = userId;
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

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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


