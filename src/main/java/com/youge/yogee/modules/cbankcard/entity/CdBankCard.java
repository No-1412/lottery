/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbankcard.entity;

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
 * 用户银行卡Entity
 * @author WeiJinChao
 * @version 2017-12-14
 */
@Entity
@Table(name = "cd_bank_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBankCard extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称

	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; 	// 备注

	private String userId; 	// 用户id
	private String userName; 	// 用户名
	private String selectbank; 	// 支行
	private String receivebank; // 开户行
	private String selectbankId;  //开户行id
	private String cardno; 	// 卡号
	private String cardMode; 	// ping++银行编号
	private String cardStatus; 	// 卡状态( 默认申请中 1 已绑定 2 申请中 3 失败）
	private String selectProvince; 	// 省
	private String selectCity; 	// 市
	private String idCard;		//身份证
	private String img;		//银行卡图标
	private String kefuTel;  //客服电话
	private String isDefault;  //是否为默认银行卡 (1是,0否)

	public CdBankCard() {
		super();
	}

	public CdBankCard(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_bank_card")
	//@SequenceGenerator(name = "seq_cd_bank_card", sequenceName = "seq_cd_bank_card")
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSelectbank() {
		return selectbank;
	}

	public void setSelectbank(String selectbank) {
		this.selectbank = selectbank;
	}

	public String getReceivebank() {
		return receivebank;
	}

	public void setReceivebank(String receivebank) {
		this.receivebank = receivebank;
	}

	public String getSelectbankId() {
		return selectbankId;
	}

	public void setSelectbankId(String selectbankId) {
		this.selectbankId = selectbankId;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCardMode() {
		return cardMode;
	}

	public void setCardMode(String cardMode) {
		this.cardMode = cardMode;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getSelectProvince() {
		return selectProvince;
	}

	public void setSelectProvince(String selectProvince) {
		this.selectProvince = selectProvince;
	}

	public String getSelectCity() {
		return selectCity;
	}

	public void setSelectCity(String selectCity) {
		this.selectCity = selectCity;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getKefuTel() {
		return kefuTel;
	}

	public void setKefuTel(String kefuTel) {
		this.kefuTel = kefuTel;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
}


