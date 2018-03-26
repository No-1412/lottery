/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.modules.erp.entity.ErpUser;
import com.youge.yogee.modules.sys.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户DAO接口
 * @author RenHaipeng
 * @version 2018-03-07
 */
@Repository
public class ErpUserDao extends BaseDao<ErpUser> {
    public List<ErpUser> findAllList() {
        return find("from ErpUser where delFlag=:p1 order by id", new Parameter(User.DEL_FLAG_NORMAL));
    }
}
