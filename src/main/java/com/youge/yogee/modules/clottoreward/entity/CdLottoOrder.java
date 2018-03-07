/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.clottoreward.entity;

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
 * 大乐透订单Entity
 *
 * @author ZhaoYiFeng
 * @version 2018-02-08
 */
@Entity
@Table(name = "cd_lotto_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdLottoOrder extends BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;        // 识别id
    private String name;    // 名称

    private String createDate;    // 创建时间
    private String delFlag;    // 删除标识 (0：未删除；1：已删除)

    private String orderNum;         // 订单号
    private String type;              //1普通 2胆拖
    private String redNums;        // 拖数字
    private String blueNums;        // 胆数字
    private String weekday;        // 期数
    private String acount;        // 注数
    private String price;        //金额
    private String award;        // 奖金
    private String uid;        // 用户id
    private String status;        // 1已提交 2已付款
    private String remarks;      //备注
    private String conType;   //1自购 2追号
    private String times;  //倍数
    private String continuity; //连续期数
    private String weekContinue;//继续买的期数

    public CdLottoOrder() {
        super();
    }

    public CdLottoOrder(String id) {
        this();
        this.id = id;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_lotto_order")
    //@SequenceGenerator(name = "seq_cd_lotto_order", sequenceName = "seq_cd_lotto_order")
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getRedNums() {
        return redNums;
    }

    public void setRedNums(String redNums) {
        this.redNums = redNums;
    }

    public String getBlueNums() {
        return blueNums;
    }

    public void setBlueNums(String blueNums) {
        this.blueNums = blueNums;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getAcount() {
        return acount;
    }

    public void setAcount(String acount) {
        this.acount = acount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getContinuity() {
        return continuity;
    }

    public void setContinuity(String continuity) {
        this.continuity = continuity;
    }

    public String getWeekContinue() {
        return weekContinue;
    }

    public void setWeekContinue(String weekContinue) {
        this.weekContinue = weekContinue;
    }
}


