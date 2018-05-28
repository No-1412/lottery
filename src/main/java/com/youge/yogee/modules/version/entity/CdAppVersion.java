/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.version.entity;

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
 * 版本信息Entity
 *
 * @author ZhaoYiFeng
 * @version 2018-05-28
 */
@Entity
@Table(name = "cd_app_version")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdAppVersion extends BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;// 		// 识别id
    private String versionCode;//版本号
    private String versionName;//版本名称
    private String versionIsUpgrade;//是否升级；1：升级；0：不升级
    private String versionIsEnforcement;//是否强制升级；1：强制；0：非强制
    private String versionDownloadUrl;//升级下载地址
    private String versionDescription;//升级描述
    private String updateDate;//升级时间
    private String versionChannel;//版本类型；1：IOS;2：Android;
    private String createDate;    // 创建时间
    private String delFlag;    // 删除标识 (0：未删除；1：已删除)

    public CdAppVersion() {
        super();
    }

    public CdAppVersion(String id) {
        this();
        this.id = id;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_app_version")
    //@SequenceGenerator(name = "seq_cd_app_version", sequenceName = "seq_cd_app_version")
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionIsUpgrade() {
        return versionIsUpgrade;
    }

    public void setVersionIsUpgrade(String versionIsUpgrade) {
        this.versionIsUpgrade = versionIsUpgrade;
    }

    public String getVersionIsEnforcement() {
        return versionIsEnforcement;
    }

    public void setVersionIsEnforcement(String versionIsEnforcement) {
        this.versionIsEnforcement = versionIsEnforcement;
    }

    public String getVersionDownloadUrl() {
        return versionDownloadUrl;
    }

    public void setVersionDownloadUrl(String versionDownloadUrl) {
        this.versionDownloadUrl = versionDownloadUrl;
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getVersionChannel() {
        return versionChannel;
    }

    public void setVersionChannel(String versionChannel) {
        this.versionChannel = versionChannel;
    }
}



