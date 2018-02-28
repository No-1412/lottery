/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.modules.bm.entity.BmBase;
import org.springframework.stereotype.Repository;

/**
 * 基本信息DAO接口
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Repository
public class BmBaseDao extends BaseDao<BmBase> {
//    public List<String> findTypeList(){
//        return find("select kind from BmBase where delFlag=:p1 group by kind", new Parameter(BmBase.DEL_FLAG_NORMAL));
//    }
}
