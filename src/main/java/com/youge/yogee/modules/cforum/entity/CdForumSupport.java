/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cforum.entity;

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
 * 论坛点赞Entity
 *
 * @author ZhaoYiFeng
 * @version 2018-04-08
 */
@Entity
@Table(name = "cd_forum_support")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdForumSupport extends BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;        // 识别id
    private String name;    // 名称
    private String remarks;
    private String createDate;    // 创建时间
    private String delFlag;    // 删除标识 (0：未删除；1：已删除)
    private String uid;
    private String fid;
    private String type;//1帖子点赞 2评论点赞

    public CdForumSupport() {
        super();
    }

    public CdForumSupport(String id) {
        this();
        this.id = id;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_forum_support")
    //@SequenceGenerator(name = "seq_cd_forum_support", sequenceName = "seq_cd_forum_support")
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

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


