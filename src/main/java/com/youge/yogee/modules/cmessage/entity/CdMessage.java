/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cmessage.entity;

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
 * 短信信息表Entity
 * @author WeiJinChao
 * @version 2017-12-08
 */
@Entity
@Table(name = "cd_message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdMessage extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称

	private String createDate; 	// 发送时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)

	private String remarks; 	// 备注

	private String phone; 	// 手机号
	private String sendNr; 	// 发送内容
	private String sendZt; 	// 发送状态（0成功1失败）
	private String checkZt; 	// 校验状态（0未校验1已校验）
	private String effectiveTime; 	// 有效时间
	private String code; 	// 验证码

	public CdMessage() {
		super();
	}

	public CdMessage(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_message")
	//@SequenceGenerator(name = "seq_cd_message", sequenceName = "seq_cd_message")
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSendNr() {
		return sendNr;
	}

	public void setSendNr(String sendNr) {
		this.sendNr = sendNr;
	}

	public String getSendZt() {
		return sendZt;
	}

	public void setSendZt(String sendZt) {
		this.sendZt = sendZt;
	}

	public String getCheckZt() {
		return checkZt;
	}

	public void setCheckZt(String checkZt) {
		this.checkZt = checkZt;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}


