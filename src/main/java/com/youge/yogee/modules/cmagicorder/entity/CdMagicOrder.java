/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cmagicorder.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.youge.yogee.common.persistence.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.validator.constraints.Length;

import com.youge.yogee.modules.sys.entity.User;

/**
 * 神单订单Entity
 *
 * @author ZhaoYiFeng
 * @version 2018-03-05
 */
@Entity
@Table(name = "cd_magic_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdMagicOrder extends BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;        // 识别id
    private String name;    // 名称

    private String createDate;    // 创建时间
    private String delFlag;    // 删除标识 (0：未删除；1：已删除)

    private String remarks;
    private String orderNum;//神单 单号
    private String type; //1足球单关 2足球串关 3篮球单关 4篮球串关
    private String followCounts; //跟单人数
    private String price; //金额
    private String uid;//用户id
    private String charges;//佣金百分比
    private String uImg; //用户头像
    private String uName; //用户名
    private String shutDownTime; //截止日期


    public CdMagicOrder() {
        super();
    }

    public CdMagicOrder(String id) {
        this();
        this.id = id;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_magic_order")
    //@SequenceGenerator(name = "seq_cd_magic_order", sequenceName = "seq_cd_magic_order")
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFollowCounts() {
        return followCounts;
    }

    public void setFollowCounts(String followCounts) {
        this.followCounts = followCounts;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getuImg() {
        return uImg;
    }

    public void setuImg(String uImg) {
        this.uImg = uImg;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getShutDownTime() {
        return shutDownTime;
    }

    public void setShutDownTime(String shutDownTime) {
        this.shutDownTime = shutDownTime;
    }
}


