/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csysbank.entity;

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
 * 系统银行卡Entity
 * @author WeiJinChao
 * @version 2017-12-14
 */
@Entity
@Table(name = "cd_sys_bank")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdSysBank extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;        // 识别id
	private String name;    // 名称

	private String createDate;    // 创建时间
	private String delFlag;    // 删除标识 (0：未删除；1：已删除)
	private String remarks;    // 备注

	private String bankImg;    // 银行图标
	private String kefuTel;  //客服电话
	private String isUse;  //是否启用（0未启用1已启用）
	private String bankNo; //ping++银行编号

	public CdSysBank() {
		super();
	}

	public CdSysBank(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_sys_bank")
	//@SequenceGenerator(name = "seq_cd_sys_bank", sequenceName = "seq_cd_sys_bank")
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

	public String getBankImg() {
		return bankImg;
	}

	public void setBankImg(String bankImg) {
		this.bankImg = bankImg;
	}

	public String getKefuTel() {
		return kefuTel;
	}

	public void setKefuTel(String kefuTel) {
		this.kefuTel = kefuTel;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
}


