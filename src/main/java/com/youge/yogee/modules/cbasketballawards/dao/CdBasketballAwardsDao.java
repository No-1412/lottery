/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballawards.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.modules.cbasketballawards.entity.CdBasketballAwards;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 竞彩足球开奖信息DAO接口
 * @author RenHaipeng
 * @version 2018-01-08
 */
@Repository
public class CdBasketballAwardsDao extends BaseDao<CdBasketballAwards> {

    public List<String> getAllMatchId(){
        return findBySql("SELECT match_id FROM cd_basketball_awards");
    }

}
