/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbstrengthpk.entity;

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
 * 篮球实力PKEntity
 * @author RenHaipeng
 * @version 2018-01-17
 */
@Entity
@Table(name = "cd_bb_strengthpk")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBbStrengthpk extends BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称

	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; //
	private String averagingStatistics; //
	private String hn; //
	private String vn; //
	private String zid;
	private String itemId;
	private String jstj;
	public CdBbStrengthpk() {
		super();
	}

	public CdBbStrengthpk(String id){
		this();
		this.id = id;
	}

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_bb_strengthpk")
	//@SequenceGenerator(name = "seq_cd_bb_strengthpk", sequenceName = "seq_cd_bb_strengthpk")
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

	public String getAveragingStatistics() {
		return averagingStatistics;
	}

	public void setAveragingStatistics(String averagingStatistics) {
		this.averagingStatistics = averagingStatistics;
	}


	public String getHn() {
		return hn;
	}

	public void setHn(String hn) {
		this.hn = hn;
	}

	public String getVn() {
		return vn;
	}

	public void setVn(String vn) {
		this.vn = vn;
	}

	@Override
	public String toString() {
		return "CdBbStrengthpk{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", createDate='" + createDate + '\'' +
				", delFlag='" + delFlag + '\'' +
				", remarks='" + remarks + '\'' +
				", averagingStatistics='" + averagingStatistics + '\'' +
				", hn='" + hn + '\'' +
				", vn='" + vn + '\'' +
				'}';
	}

	public String getZid() {
		return zid;
	}

	public void setZid(String zid) {
		this.zid = zid;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getJstj() {
		return jstj;
	}

	public void setJstj(String jstj) {
		this.jstj = jstj;
	}
}


