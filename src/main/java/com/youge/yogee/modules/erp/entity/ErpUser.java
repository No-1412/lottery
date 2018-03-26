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
import java.math.BigDecimal;

/**
 * 用户Entity
 * @author RenHaipeng
 * @version 2018-03-07
 */
@Entity
@Table(name = "cd_lottery_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ErpUser extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	private String reality; //真实姓名
	private String account;	//用户账户
	private String password;
	private String email;
	private String isEmailVerified;
	private String mobile;
	private String isMobileVerified;
	private String idNumber;
	private String isRealNameVerified;
	private String isBankVerified;
	private String photo;
	private String sex;
	private String birthday;
	private String qrCode;
	private BigDecimal balance;
	private BigDecimal freeze;
	private BigDecimal score;
	private String isBlock;
	private BigDecimal txFreeze;
	private String memberLevel;
	private String payPwd;
	private String openid;
	private String memberType;
	private String isRecharge;
	private String img;
	private String online;
	private BigDecimal frzeeScore;
	private String isRegisterCharge;
	private String isPay;
	private User saleId;
	private String realityCount;
	private String remarks;
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)

	public ErpUser() {
		super();
	}

	public ErpUser(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_lottery_user")
	//@SequenceGenerator(name = "seq_cd_lottery_user", sequenceName = "seq_cd_lottery_user")
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

	public String getReality() {
		return reality;
	}

	public void setReality(String reality) {
		this.reality = reality;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsEmailVerified() {
		return isEmailVerified;
	}

	public void setIsEmailVerified(String isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIsMobileVerified() {
		return isMobileVerified;
	}

	public void setIsMobileVerified(String isMobileVerified) {
		this.isMobileVerified = isMobileVerified;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIsRealNameVerified() {
		return isRealNameVerified;
	}

	public void setIsRealNameVerified(String isRealNameVerified) {
		this.isRealNameVerified = isRealNameVerified;
	}

	public String getIsBankVerified() {
		return isBankVerified;
	}

	public void setIsBankVerified(String isBankVerified) {
		this.isBankVerified = isBankVerified;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getFreeze() {
		return freeze;
	}

	public void setFreeze(BigDecimal freeze) {
		this.freeze = freeze;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getIsBlock() {
		return isBlock;
	}

	public void setIsBlock(String isBlock) {
		this.isBlock = isBlock;
	}

	public BigDecimal getTxFreeze() {
		return txFreeze;
	}

	public void setTxFreeze(BigDecimal txFreeze) {
		this.txFreeze = txFreeze;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getIsRecharge() {
		return isRecharge;
	}

	public void setIsRecharge(String isRecharge) {
		this.isRecharge = isRecharge;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public BigDecimal getFrzeeScore() {
		return frzeeScore;
	}

	public void setFrzeeScore(BigDecimal frzeeScore) {
		this.frzeeScore = frzeeScore;
	}

	public String getIsRegisterCharge() {
		return isRegisterCharge;
	}

	public void setIsRegisterCharge(String isRegisterCharge) {
		this.isRegisterCharge = isRegisterCharge;
	}

	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	public String getRealityCount() {
		return realityCount;
	}

	public void setRealityCount(String realityCount) {
		this.realityCount = realityCount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@ManyToOne
	@JoinColumn(name="saleId")
	@NotFound(action = NotFoundAction.IGNORE)
	public User getSaleId() {
		return saleId;
	}

	public void setSaleId(User saleId) {
		this.saleId = saleId;
	}

}


