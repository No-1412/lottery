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
 * 论坛回复Entity
 *
 * @author ZhaoYiFeng
 * @version 2018-04-08
 */
@Entity
@Table(name = "cd_forum_replay")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdForumReplay extends BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;        // 识别id
    private String name;    // 名称
    private String remarks;//备注
    private String createDate;    // 创建时间
    private String delFlag;    // 删除标识 (0：未删除；1：已删除)

    private String text;//评论内容
    private String fid;//论坛帖子id
    private String supportCount;//点赞数
    private String uid;//用户id
    private String userName;//用户名
    private String userImg;//用户头像

    public CdForumReplay() {
        super();
    }

    public CdForumReplay(String id) {
        this();
        this.id = id;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_forum_replay")
    //@SequenceGenerator(name = "seq_cd_forum_replay", sequenceName = "seq_cd_forum_replay")
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(String supportCount) {
        this.supportCount = supportCount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
}


