/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cftskill.entity;

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
 * 足球实况技术统计Entity
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Entity
@Table(name = "cd_ft_skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdFtSkill extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)
	private String remarks; //
	private String itemId; //
	private String shootnuma; //
	private String shootnumb; //
	private String shotsongoala; //
	private String shotsongoalb; //
	private String foulnuma; //
	private String foulnumb; //
	private String cornerkicknuma; //
	private String cornerkicknumb; //
	private String offsidenuma; //
	private String offsidenumb; //
	private String yellowcardnuma; //
	private String yellowcardnumb; //
	private String savesa; //
	private String savesb; //
	private String isFinshed;
	private String hn;
	private String gn;
	private String playera;
	private String playerb;
	private String tbplayera;
	private String tbplayerb;

	public CdFtSkill() {
		super();
	}

	public CdFtSkill(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_ft_skill")
	//@SequenceGenerator(name = "seq_cd_ft_skill", sequenceName = "seq_cd_ft_skill")
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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getShootnuma() {
		return shootnuma;
	}

	public void setShootnuma(String shootnuma) {
		this.shootnuma = shootnuma;
	}

	public String getShootnumb() {
		return shootnumb;
	}

	public void setShootnumb(String shootnumb) {
		this.shootnumb = shootnumb;
	}

	public String getShotsongoala() {
		return shotsongoala;
	}

	public void setShotsongoala(String shotsongoala) {
		this.shotsongoala = shotsongoala;
	}

	public String getShotsongoalb() {
		return shotsongoalb;
	}

	public void setShotsongoalb(String shotsongoalb) {
		this.shotsongoalb = shotsongoalb;
	}

	public String getFoulnuma() {
		return foulnuma;
	}

	public void setFoulnuma(String foulnuma) {
		this.foulnuma = foulnuma;
	}

	public String getFoulnumb() {
		return foulnumb;
	}

	public void setFoulnumb(String foulnumb) {
		this.foulnumb = foulnumb;
	}

	public String getCornerkicknuma() {
		return cornerkicknuma;
	}

	public void setCornerkicknuma(String cornerkicknuma) {
		this.cornerkicknuma = cornerkicknuma;
	}

	public String getCornerkicknumb() {
		return cornerkicknumb;
	}

	public void setCornerkicknumb(String cornerkicknumb) {
		this.cornerkicknumb = cornerkicknumb;
	}

	public String getOffsidenuma() {
		return offsidenuma;
	}

	public void setOffsidenuma(String offsidenuma) {
		this.offsidenuma = offsidenuma;
	}

	public String getOffsidenumb() {
		return offsidenumb;
	}

	public void setOffsidenumb(String offsidenumb) {
		this.offsidenumb = offsidenumb;
	}

	public String getYellowcardnuma() {
		return yellowcardnuma;
	}

	public void setYellowcardnuma(String yellowcardnuma) {
		this.yellowcardnuma = yellowcardnuma;
	}

	public String getYellowcardnumb() {
		return yellowcardnumb;
	}

	public void setYellowcardnumb(String yellowcardnumb) {
		this.yellowcardnumb = yellowcardnumb;
	}

	public String getSavesa() {
		return savesa;
	}

	public void setSavesa(String savesa) {
		this.savesa = savesa;
	}

	public String getSavesb() {
		return savesb;
	}

	public void setSavesb(String savesb) {
		this.savesb = savesb;
	}

	public String getIsFinshed() {
		return isFinshed;
	}

	public void setIsFinshed(String isFinshed) {
		this.isFinshed = isFinshed;
	}

	public String getHn() {
		return hn;
	}

	public void setHn(String hn) {
		this.hn = hn;
	}

	public String getGn() {
		return gn;
	}

	public void setGn(String gn) {
		this.gn = gn;
	}

	public String getPlayera() {
		return playera;
	}

	public void setPlayera(String playera) {
		this.playera = playera;
	}

	public String getPlayerb() {
		return playerb;
	}

	public void setPlayerb(String playerb) {
		this.playerb = playerb;
	}

	public String getTbplayera() {
		return tbplayera;
	}

	public void setTbplayera(String tbplayera) {
		this.tbplayera = tbplayera;
	}

	public String getTbplayerb() {
		return tbplayerb;
	}

	public void setTbplayerb(String tbplayerb) {
		this.tbplayerb = tbplayerb;
	}
}


