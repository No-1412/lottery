/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.clotteryuser.entity;

import com.youge.yogee.common.persistence.BaseEntity;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户注册Entity
 *
 * @author WeiJinChao
 * @version 2017-12-07
 */
@Entity
@Table(name = "cd_lottery_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdLotteryUser extends BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;        // 识别id
    private String name;    // 名称

    private String createDate;    // 创建时间,注册时间
    private String delFlag;    // 删除标识 (0：未删除；1：已删除)
    private String remarks;    // 备注


    private String reality;    // 真实姓名
    private String account;    // 用户账号
    private String password;    // 登录密码
    private String payPwd;    // 支付密码
    private String email;    // 邮箱
    private String isEmailVerified;    // 箱邮是否验证（0未验证1已验证）
    private String mobile;    // 手机号
    private String isMobileVerified;    // 手机是否验证（0未验证1已验证）
    private String idNumber;    // 身份证号码
    private String isRealNameVerified;    // 是否通过实名认证(未认证0已认证1)
    private String isBankVerified;    // 是否绑定银行卡（0未绑定1已绑定）
    private String photo;    // 头像
    private String sex;    // 性别（0男1女）
    private String birthday;    // 生日
    private String qrCode;    // 二维码
    private BigDecimal balance;    // 余额
    private BigDecimal freeze;    // 冻结金额
    private BigDecimal score;    // 积分
    private String isBlock;    // 是否锁定（0未锁定1已锁定）
    private BigDecimal txFreeze;    // 提现中冻结金额
    private String memberType;    // 会员类型（0普通1永久2非会员）
    private String memberLevel;    // 会员级别
    private String openid;
    private String isRecharge;  //是否充值（0未充值1已充值）
    private String img;  //头像路径
    private String online;     //在线标识(0:在线;1:离线)
    private BigDecimal frzeeScore;     //冻结积分
    private String isRegisterCharge;//是否注册充值（0未充值1已充值）
    private String isPay;//是否犹豫期（0初始1确认交费2犹豫期3作废）
    private String saleId;//销售id
    private Integer realityCount;// 实名认证次数
    private String totalRecharge;//充值总金额
    private String totalPay;//购彩总金额
    private String rebate;//返利金额
    private String catchTimes;//返利金额
    private String continent;//大洲

    public CdLotteryUser() {
        super();
    }

    public CdLotteryUser(String id) {
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

    @Length(min = 1, max = 200)
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

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
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

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public Integer getRealityCount() {
        return realityCount;
    }

    public void setRealityCount(Integer realityCount) {
        this.realityCount = realityCount;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(String totalRecharge) {
        this.totalRecharge = totalRecharge;
    }

    public String getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(String totalPay) {
        this.totalPay = totalPay;
    }

    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    public String getCatchTimes() {
        return catchTimes;
    }
    public void setCatchTimes(String catchTimes) {
        this.catchTimes = catchTimes;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}


