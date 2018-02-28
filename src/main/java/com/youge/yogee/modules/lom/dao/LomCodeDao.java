/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.lom.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.modules.lom.entity.LomCode;
import com.youge.yogee.modules.sys.entity.Dict;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 验证码记录DAO接口
 * @author RenHaipeng
 * @version 2017-03-10
 */
@Repository
public class LomCodeDao extends BaseDao<LomCode> {

    public List getPhoneCode(String phone, String type){
        return findBySql("SELECT code FROM lom_code WHERE del_flag=:p1 AND phone=:p2 AND kind=:p3 AND invalid_date > NOW() ORDER BY invalid_date DESC LIMIT 0,1 ", new Parameter(Dict.DEL_FLAG_NORMAL, phone, type));
    }
}
