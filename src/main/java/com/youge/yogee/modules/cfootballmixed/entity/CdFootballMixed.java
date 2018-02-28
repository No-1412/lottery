/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballmixed.entity;

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
 * 竞彩足球Entity
 * @author WeiJinChao
 * @version 2018-01-08
 */
@Entity
@Table(name = "cd_football_mixed")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdFootballMixed extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; 		// 识别id
	private String name; 	// 名称
	private String remarks;
	private String createDate; 	// 创建时间
	private String delFlag; 	// 删除标识 (0：未删除；1：已删除)


	private String matchId; //赛事场次
	private String timeEndsale; //停售时间
	private String matchDate; //比赛时间
	private String eventName; //赛事名称
	private String winningName; //主队名称
	private String defeatedName; //客队名称
	private String winningRank; //主场排名
	private String defeatedRank; //客场排名
	private String concedepointsOdds; //让球负平胜赔率
	private String notConcedepointsOdds; //非让球负平胜赔率
	private String close; //加分
	private String scoreOdds; //比分赔率
	private String allOdds; //总进球赔率
	private String halfOdds; //半全场赔率
	private String recentWinningSurpass; //近期战绩主
	private String recentDefeatedSurpass; //近期战绩客
	private String historyWinningSurpass; //历史战绩主
	private String notConcedepointsRatio; //投注比例非让球
	private String concedepointsRatio; //投注比例非让球
	private String matchsDate; //比赛时间按星期排
	private String hot; //热度
	private String isale;//是否是单关

	public CdFootballMixed() {
		super();
	}

	public CdFootballMixed(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_football_mixed")
	//@SequenceGenerator(name = "seq_cd_football_mixed", sequenceName = "seq_cd_football_mixed")
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getConcedepointsOdds() {
		return concedepointsOdds;
	}

	public void setConcedepointsOdds(String concedepointsOdds) {
		this.concedepointsOdds = concedepointsOdds;
	}

	public String getNotConcedepointsOdds() {
		return notConcedepointsOdds;
	}

	public void setNotConcedepointsOdds(String notConcedepointsOdds) {
		this.notConcedepointsOdds = notConcedepointsOdds;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getScoreOdds() {
		return scoreOdds;
	}

	public void setScoreOdds(String scoreOdds) {
		this.scoreOdds = scoreOdds;
	}

	public String getAllOdds() {
		return allOdds;
	}

	public void setAllOdds(String allOdds) {
		this.allOdds = allOdds;
	}

	public String getHalfOdds() {
		return halfOdds;
	}

	public void setHalfOdds(String halfOdds) {
		this.halfOdds = halfOdds;
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

	public String getHistoryWinningSurpass() {
		return historyWinningSurpass;
	}

	public void setHistoryWinningSurpass(String historyWinningSurpass) {
		this.historyWinningSurpass = historyWinningSurpass;
	}

	public String getNotConcedepointsRatio() {
		return notConcedepointsRatio;
	}

	public void setNotConcedepointsRatio(String notConcedepointsRatio) {
		this.notConcedepointsRatio = notConcedepointsRatio;
	}

	public String getConcedepointsRatio() {
		return concedepointsRatio;
	}

	public void setConcedepointsRatio(String concedepointsRatio) {
		this.concedepointsRatio = concedepointsRatio;
	}

	public String getMatchsDate() {
		return matchsDate;
	}

	public void setMatchsDate(String matchsDate) {
		this.matchsDate = matchsDate;
	}

	public String getHot() {
		return hot;
	}

	public void setHot(String hot) {
		this.hot = hot;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIsale() {
		return isale;
	}

	public void setIsale(String isale) {
		this.isale = isale;
	}
}


