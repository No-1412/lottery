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
 * 论坛Entity
 * @author WeiJinChao
 * @version 2017-12-14
 */
@Entity
@Table(name = "cd_forum")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdForum extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; //
	private String name; //论坛标题
	private String createDate; //创建时间
	private String delFlag; //
	private String remarks; //
	private String userId; //
	private String userName; //
	private String dianzanCount; //点赞数量
	private String imgList; // 图片
	private String content; //内容
	private String isUse; // 是否可用 0不可用 1可用
	private String isPosts; //是否是帖子 0回复 1帖子
	private String parentsId; //父id 每个id之间用逗号分隔.
	private String parentsUser; //所有父用户 用逗号分隔
	private String forumType; //球爆单0竞彩足球1竞彩篮球2
	public CdForum() {
		super();
	}

	public CdForum(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_forum")
	//@SequenceGenerator(name = "seq_cd_forum", sequenceName = "seq_cd_forum")
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

	public String getDianzanCount() {
		return dianzanCount;
	}

	public void setDianzanCount(String dianzanCount) {
		this.dianzanCount = dianzanCount;
	}

	public String getImgList() {
		return imgList;
	}

	public void setImgList(String imgList) {
		this.imgList = imgList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getIsPosts() {
		return isPosts;
	}

	public void setIsPosts(String isPosts) {
		this.isPosts = isPosts;
	}

	public String getParentsId() {
		return parentsId;
	}

	public void setParentsId(String parentsId) {
		this.parentsId = parentsId;
	}

	public String getParentsUser() {
		return parentsUser;
	}

	public void setParentsUser(String parentsUser) {
		this.parentsUser = parentsUser;
	}

	public String getForumType() {
		return forumType;
	}

	public void setForumType(String forumType) {
		this.forumType = forumType;
	}
}


