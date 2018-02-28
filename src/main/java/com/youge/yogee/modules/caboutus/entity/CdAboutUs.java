/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.caboutus.entity;

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
 * 关于我们Entity
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Entity
@Table(name = "cd_about_us")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdAboutUs extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; //
	private String name; //
	private String createDate; //
	private String delFlag; //
	private String remarks; //
	private String logoImg; //logo图片
	private String function; //功能介绍
	private String kefuTel; //客服电话
	private String guanwangHref; //官网链接

	public CdAboutUs() {
		super();
	}

	public CdAboutUs(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_about_us")
	//@SequenceGenerator(name = "seq_cd_about_us", sequenceName = "seq_cd_about_us")
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

	public String getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getKefuTel() {
		return kefuTel;
	}

	public void setKefuTel(String kefuTel) {
		this.kefuTel = kefuTel;
	}

	public String getGuanwangHref() {
		return guanwangHref;
	}

	public void setGuanwangHref(String guanwangHref) {
		this.guanwangHref = guanwangHref;
	}
}


