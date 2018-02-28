/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.modules.bm.entity.BmLink;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 友情链接DAO接口
 * @author RenHaipeng
 * @version 2017-03-11
 */
@Repository
public class BmLinkDao extends BaseDao<BmLink> {

    //查询所有的友情链接
    public List<Object[]> getLinkAll() {

        StringBuffer sql = new StringBuffer();

        sql.append(" SELECT name, url ");
        sql.append(" FROM bm_link ");
        sql.append(" WHERE del_flag = '0' ");
        sql.append(" ORDER BY create_date DESC ");

        return findBySql(sql.toString());
    }
}
