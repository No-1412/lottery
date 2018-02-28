/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cscoreodds.entity;

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
 * 比分赔率接口Entity
 * @author WeiJinChao
 * @version 2017-12-12
 */
@Entity
@Table(name = "cd_score_odds")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CdScoreOdds extends BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; //
	private String name; //
	private String createDate; //
	private String delFlag; //
	private String remarks; //
	private String phase; //期号
	private String officialDate; //正式时间
	private String matchNum; //场次
	private String matchId; //场次id
	private String matchDate; //比赛时间
	private String timeEndsale; //停止时间
	private String homeTeam; //主队名称
	private String awayTeam; //客队名称
	private String finalScore; //全场比分
	private String halfScore; //半场比分
	private String handicap; //让球
	private String weekday; //星期
	private String matchName; //赛事名称
	private String status; //状态
	private String oddsJingcai; //竞彩官方(欧赔)
	private String oddsAvg; //平均欧赔
	private String oddsWlxe; //威廉希尔(欧赔)
	private String oddsAomen; //澳门(欧赔)
	private String oddsLibo; //立博(欧赔)
	private String oddsBet365; //Bet365(欧赔)
	private String oddsBwin; //bwin(欧赔)
	private String oddsWeide; //伟德(欧赔)
	private String oddsHg; //欧冠(欧赔)
	private String oddsYsb; //易胜博(欧赔)
	private String oddsCoral; //Coral(欧赔)
	private String odds12bet; //12Bet(12博)(欧赔)
	private String oddsYapan; //澳门(亚盘)
	private String oddsYapanBet365; //Bet365(亚盘)
	private String oddsYapanHg; //皇冠(亚盘)
	private String odds_yapan_ysb; //易胜博(亚盘)
	private String oddsYapanWeide; //韦德(亚盘)
	private String oddsRangqiu; //竞彩官方(让球)
	private String oddsRangqiuWlxe; //威廉希尔(让球)
	private String oddsRangqiuLibo; //立博(让球)
	private String oddsRangqiuBet365; //Bet365(让球)
	public CdScoreOdds() {
		super();
	}

	public CdScoreOdds(String id){
		this();
		this.id = id;
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cd_score_odds")
	//@SequenceGenerator(name = "seq_cd_score_odds", sequenceName = "seq_cd_score_odds")
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

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getOfficialDate() {
		return officialDate;
	}

	public void setOfficialDate(String officialDate) {
		this.officialDate = officialDate;
	}

	public String getMatchNum() {
		return matchNum;
	}

	public void setMatchNum(String matchNum) {
		this.matchNum = matchNum;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getTimeEndsale() {
		return timeEndsale;
	}

	public void setTimeEndsale(String timeEndsale) {
		this.timeEndsale = timeEndsale;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public String getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}

	public String getHalfScore() {
		return halfScore;
	}

	public void setHalfScore(String halfScore) {
		this.halfScore = halfScore;
	}

	public String getHandicap() {
		return handicap;
	}

	public void setHandicap(String handicap) {
		this.handicap = handicap;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOddsJingcai() {
		return oddsJingcai;
	}

	public void setOddsJingcai(String oddsJingcai) {
		this.oddsJingcai = oddsJingcai;
	}

	public String getOddsAvg() {
		return oddsAvg;
	}

	public void setOddsAvg(String oddsAvg) {
		this.oddsAvg = oddsAvg;
	}

	public String getOddsWlxe() {
		return oddsWlxe;
	}

	public void setOddsWlxe(String oddsWlxe) {
		this.oddsWlxe = oddsWlxe;
	}

	public String getOddsAomen() {
		return oddsAomen;
	}

	public void setOddsAomen(String oddsAomen) {
		this.oddsAomen = oddsAomen;
	}

	public String getOddsLibo() {
		return oddsLibo;
	}

	public void setOddsLibo(String oddsLibo) {
		this.oddsLibo = oddsLibo;
	}

	public String getOddsBet365() {
		return oddsBet365;
	}

	public void setOddsBet365(String oddsBet365) {
		this.oddsBet365 = oddsBet365;
	}

	public String getOddsBwin() {
		return oddsBwin;
	}

	public void setOddsBwin(String oddsBwin) {
		this.oddsBwin = oddsBwin;
	}

	public String getOddsWeide() {
		return oddsWeide;
	}

	public void setOddsWeide(String oddsWeide) {
		this.oddsWeide = oddsWeide;
	}

	public String getOddsHg() {
		return oddsHg;
	}

	public void setOddsHg(String oddsHg) {
		this.oddsHg = oddsHg;
	}

	public String getOddsYsb() {
		return oddsYsb;
	}

	public void setOddsYsb(String oddsYsb) {
		this.oddsYsb = oddsYsb;
	}

	public String getOddsCoral() {
		return oddsCoral;
	}

	public void setOddsCoral(String oddsCoral) {
		this.oddsCoral = oddsCoral;
	}

	public String getOdds12bet() {
		return odds12bet;
	}

	public void setOdds12bet(String odds12bet) {
		this.odds12bet = odds12bet;
	}

	public String getOddsYapan() {
		return oddsYapan;
	}

	public void setOddsYapan(String oddsYapan) {
		this.oddsYapan = oddsYapan;
	}

	public String getOddsYapanBet365() {
		return oddsYapanBet365;
	}

	public void setOddsYapanBet365(String oddsYapanBet365) {
		this.oddsYapanBet365 = oddsYapanBet365;
	}

	public String getOddsYapanHg() {
		return oddsYapanHg;
	}

	public void setOddsYapanHg(String oddsYapanHg) {
		this.oddsYapanHg = oddsYapanHg;
	}

	public String getOdds_yapan_ysb() {
		return odds_yapan_ysb;
	}

	public void setOdds_yapan_ysb(String odds_yapan_ysb) {
		this.odds_yapan_ysb = odds_yapan_ysb;
	}

	public String getOddsYapanWeide() {
		return oddsYapanWeide;
	}

	public void setOddsYapanWeide(String oddsYapanWeide) {
		this.oddsYapanWeide = oddsYapanWeide;
	}

	public String getOddsRangqiu() {
		return oddsRangqiu;
	}

	public void setOddsRangqiu(String oddsRangqiu) {
		this.oddsRangqiu = oddsRangqiu;
	}

	public String getOddsRangqiuWlxe() {
		return oddsRangqiuWlxe;
	}

	public void setOddsRangqiuWlxe(String oddsRangqiuWlxe) {
		this.oddsRangqiuWlxe = oddsRangqiuWlxe;
	}

	public String getOddsRangqiuLibo() {
		return oddsRangqiuLibo;
	}

	public void setOddsRangqiuLibo(String oddsRangqiuLibo) {
		this.oddsRangqiuLibo = oddsRangqiuLibo;
	}

	public String getOddsRangqiuBet365() {
		return oddsRangqiuBet365;
	}

	public void setOddsRangqiuBet365(String oddsRangqiuBet365) {
		this.oddsRangqiuBet365 = oddsRangqiuBet365;
	}
}


