/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballmixed.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.modules.cbasketballmixed.entity.CdBasketballMixed;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 竞彩蓝球DAO接口
 * @author WeiJinChao
 * @version 2018-01-22
 */
@Repository
public class CdBasketballMixedDao extends BaseDao<CdBasketballMixed> {

    public List getSize(){
        String sql = "select distinct remarks from cd_football_mixed order by remarks asc";
        return findBySql(sql);
    }
	
}
