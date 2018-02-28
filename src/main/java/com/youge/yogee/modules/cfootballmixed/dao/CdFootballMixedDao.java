/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballmixed.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.modules.cfootballmixed.entity.CdFootballMixed;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 竞彩足球DAO接口
 * @author WeiJinChao
 * @version 2018-01-08
 */
@Repository
public class CdFootballMixedDao extends BaseDao<CdFootballMixed> {
    public List getSize(){
        String sql = "select distinct remarks from cd_football_mixed order by remarks asc";
        return findBySql(sql);
    }


}
