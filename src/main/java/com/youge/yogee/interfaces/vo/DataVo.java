package com.youge.yogee.interfaces.vo;

/**
 * Created by Administrator on 2015/10/23.
 */
public class DataVo {

    /**
     * 公共部分
     */
    private String id; 		// 识别id
    private String createDate;	 //注册时间
    private String updateDate;	 //更新时间
    private String delFlag; 	// 删除标识 (0：未删除；1：已删除)

    /**
     * 用户信息
     */
    private String phone;	 //手机号
    private String pwd;	 //密码
    private String userName;	 //姓名
    private String userImg;	 //头像
    private String sex;	 //性别（0：女；1：男；）
    private String autograph;	 //签名
    private String province;	 //省份
    private String city;	 //城市
    private String district;	 //区域
    private String address;	 //地址
    private String code;	 //邀请码
    private String point;	 //积分
    private String money;	 //余额
    private String alipay;	 //支付宝支付
    private String wechat;	 //微信支付
    private String freeze;	 //冻结标识(0:正常;1:冻结)
    private String share;	 //分享达人(0:默认;1:同意)

    /**
     * 用户-银行卡
     */
    private String userId;	 //所属用户id
    private String kind;	 //银行分类
    private String number;	 //银行卡号
    private String states;	 //状态

    /**
     * 用户-帖子
     */
    private String belongId;	 //所属用户id
    private String topImgs;	 //顶部图
    private String summary;	 //简介
    private String content;	 //图文
    private String imgs;	 //图片


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    /******************************************************************************************************************/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getFreeze() {
        return freeze;
    }

    public void setFreeze(String freeze) {
        this.freeze = freeze;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getTopImgs() {
        return topImgs;
    }

    public void setTopImgs(String topImgs) {
        this.topImgs = topImgs;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
}
