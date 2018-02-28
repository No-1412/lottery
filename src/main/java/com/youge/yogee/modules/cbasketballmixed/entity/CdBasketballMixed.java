/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballmixed.entity;

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
 * 竞彩蓝球Entity
 * @author WeiJinChao
 * @version 2018-01-22
 */
@Entity
@Table(name = "cd_basketball_mixed")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdBasketballMixed extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	
	private String createDate; 	// 创建时间

	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)


	private String remarks; //
	private String matchId; //赛事场次
	private String timeEndsale; //停售时间
	private String matchDate; //比赛时间
	private String eventName; //赛事名称
	private String winningName; //主队名称
	private String defeatedName; //客队名称
	private String winningRank; //主场排名
	private String defeatedRank; //客场排名
	private String victoryordefeatOdds; //胜负:主负主胜赔率',
	private String spreadOdds; //让分:主负主胜赔率
	private String close; //加分
	private String zclose; //分数
	private String sizeOdds; //大小赔率
	private String surpassScoreGap; //胜分差主负主胜
	private String recentWinningSurpass; //近期战绩主胜
	private String recentDefeatedSurpass; //近期战绩客胜
	private String recentWinningDefeat; //近期战绩主负
	private String recentDefeatedDefeat; //近期战绩客负
	private String historyWinningSurpass; //历史战绩主胜
	private String historyWinningDefeat; //历史战绩主负
	private String hot; //热度
	private String isale; //是否是单关
	private String itemid; //比赛id
	private String zid; //比赛详细信息传的参数
	private String matchsDate;//比赛时间按星期排

	public CdBasketballMixed() {
		super();
	}

	public CdBasketballMixed(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_basketball_mixed")
	//@SequenceGenerator(name = "seq_cd_basketball_mixed", sequenceName = "seq_cd_basketball_mixed")
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

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getTimeEndsale() {
		return timeEndsale;
	}

	public void setTimeEndsale(String timeEndsale) {
		this.timeEndsale = timeEndsale;
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getWinningName() {
		return winningName;
	}

	public void setWinningName(String winningName) {
		this.winningName = winningName;
	}

	public String getDefeatedName() {
		return defeatedName;
	}

	public void setDefeatedName(String defeatedName) {
		this.defeatedName = defeatedName;
	}

	public String getWinningRank() {
		return winningRank;
	}

	public void setWinningRank(String winningRank) {
		this.winningRank = winningRank;
	}

	public String getDefeatedRank() {
		return defeatedRank;
	}

	public void setDefeatedRank(String defeatedRank) {
		this.defeatedRank = defeatedRank;
	}

	public String getVictoryordefeatOdds() {
		return victoryordefeatOdds;
	}

	public void setVictoryordefeatOdds(String victoryordefeatOdds) {
		this.victoryordefeatOdds = victoryordefeatOdds;
	}

	public String getSpreadOdds() {
		return spreadOdds;
	}

	public void setSpreadOdds(String spreadOdds) {
		this.spreadOdds = spreadOdds;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getZclose() {
		return zclose;
	}

	public void setZclose(String zclose) {
		this.zclose = zclose;
	}

	public String getSizeOdds() {
		return sizeOdds;
	}

	public void setSizeOdds(String sizeOdds) {
		this.sizeOdds = sizeOdds;
	}

	public String getSurpassScoreGap() {
		return surpassScoreGap;
	}

	public void setSurpassScoreGap(String surpassScoreGap) {
		this.surpassScoreGap = surpassScoreGap;
	}

	public String getRecentWinningSurpass() {
		return recentWinningSurpass;
	}

	public void setRecentWinningSurpass(String recentWinningSurpass) {
		this.recentWinningSurpass = recentWinningSurpass;
	}

	public String getRecentDefeatedSurpass() {
		return recentDefeatedSurpass;
	}

	public void setRecentDefeatedSurpass(String recentDefeatedSurpass) {
		this.recentDefeatedSurpass = recentDefeatedSurpass;
	}

	public String getRecentWinningDefeat() {
		return recentWinningDefeat;
	}

	public void setRecentWinningDefeat(String recentWinningDefeat) {
		this.recentWinningDefeat = recentWinningDefeat;
	}

	public String getRecentDefeatedDefeat() {
		return recentDefeatedDefeat;
	}

	public void setRecentDefeatedDefeat(String recentDefeatedDefeat) {
		this.recentDefeatedDefeat = recentDefeatedDefeat;
	}

	public String getHistoryWinningSurpass() {
		return historyWinningSurpass;
	}

	public void setHistoryWinningSurpass(String historyWinningSurpass) {
		this.historyWinningSurpass = historyWinningSurpass;
	}

	public String getHistoryWinningDefeat() {
		return historyWinningDefeat;
	}

	public void setHistoryWinningDefeat(String historyWinningDefeat) {
		this.historyWinningDefeat = historyWinningDefeat;
	}

	public String getHot() {
		return hot;
	}

	public void setHot(String hot) {
		this.hot = hot;
	}

	public String getIsale() {
		return isale;
	}

	public void setIsale(String isale) {
		this.isale = isale;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getZid() {
		return zid;
	}

	public void setZid(String zid) {
		this.zid = zid;
	}

	public String getMatchsDate() {
		return matchsDate;
	}

	public void setMatchsDate(String matchsDate) {
		this.matchsDate = matchsDate;
	}
}


