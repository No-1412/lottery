/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.corder.entity;

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
 * 追号Entity
 *
 * @author ZhaoYiFeng
 * @version 2018-03-27
 */
@Entity
@Table(name = "cd_order_catch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdOrderCatch extends BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;        // 识别id
    private String name;    // 名称
    private String remarks;
    private String createDate;    // 创建时间
    private String delFlag;    // 删除标识 (0：未删除；1：已删除)
    private String orderNum; //订单号
    private String price; //金额
    private String continuity;//追的期数
    private String hasContinue;//已追期数
    private String type;//1排列三 2排列五 3大乐透
    private String status;//1进行中 2已结束
    private String uid;//用户id

    public CdOrderCatch() {
        super();
    }

    public CdOrderCatch(String id) {
        this();
        this.id = id;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_order_catch")
    //@SequenceGenerator(name = "seq_cd_order_catch", sequenceName = "seq_cd_order_catch")
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContinuity() {
        return continuity;
    }

    public void setContinuity(String continuity) {
        this.continuity = continuity;
    }

    public String getHasContinue() {
        return hasContinue;
    }

    public void setHasContinue(String hasContinue) {
        this.hasContinue = hasContinue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}


