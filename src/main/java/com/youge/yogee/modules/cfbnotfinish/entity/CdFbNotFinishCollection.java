/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbnotfinish.entity;

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
 * 足球比赛收藏Entity
 *
 * @author ZhaoYiFeng
 * @version 2018-03-19
 */
@Entity
@Table(name = "cd_fb_notfinish_collection")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdFbNotFinishCollection extends BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;        // 识别id
    private String name;    // 名称
    private String remarks;    //
    private String sort;    //比赛期次
    private String uid;    //用户id

    private String createDate;    // 创建时间
    private String delFlag;    // 删除标识 (0：未删除；1：已删除)

    public CdFbNotFinishCollection() {
        super();
    }

    public CdFbNotFinishCollection(String id) {
        this();
        this.id = id;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_fb_finished_collection")
    //@SequenceGenerator(name = "seq_cd_fb_finished_collection", sequenceName = "seq_cd_fb_finished_collection")
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


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}


