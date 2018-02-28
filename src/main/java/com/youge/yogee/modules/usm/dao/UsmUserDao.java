/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.usm.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.modules.sys.entity.Dict;
import com.youge.yogee.modules.usm.entity.UsmUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户表DAO接口
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Repository
public class UsmUserDao extends BaseDao<UsmUser> {

    public List getCountByTelephone(String telephone){
        return findBySql("SELECT COUNT(1) FROM usm_user WHERE del_flag=:p1 AND telephone=:p2 GROUP BY telephone ", new Parameter(Dict.DEL_FLAG_NORMAL, telephone));
    }

    public UsmUser getByWechat(String openid){
        return getByHql(" from UsmUser WHERE del_flag=:p1 AND wechatId=:p2 GROUP BY wechatId ", new Parameter(Dict.DEL_FLAG_NORMAL, openid));
    }

    public UsmUser getByQq(String openid){
        return getByHql(" from UsmUser WHERE del_flag=:p1 AND qqId=:p2 GROUP BY qqId ", new Parameter(Dict.DEL_FLAG_NORMAL, openid));
    }

    public UsmUser getBySina(String openid){
        return getByHql(" from UsmUser WHERE del_flag=:p1 AND sinaId=:p2 GROUP BY sinaId ", new Parameter(Dict.DEL_FLAG_NORMAL, openid));
    }
//    public List<Object[]> getOrderByUserId(String user_id, String total, String count){
//        StringBuffer sql = new StringBuffer();
//
//        return findBySql(sql.toString());
//    }


//    public int freezeById(String id){
//        return update("UPDATE UsmUser set freeze=:p1 where id = :p2", new Parameter(Dict.YES,id));
//    }
}
