/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballawards.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.modules.cfootballawards.entity.CdFootballAwards;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 竞彩足球开奖信息DAO接口
 * @author RenHaipeng
 * @version 2018-01-09
 */
@Repository
public class CdFootballAwardsDao extends BaseDao<CdFootballAwards> {

    public List<String> getAllMatchId(){
        return findBySql("SELECT match_id FROM cd_football_awards");
    }
}
