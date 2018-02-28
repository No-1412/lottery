/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.usm.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.modules.usm.entity.UsmInvite;
import org.springframework.stereotype.Repository;

/**
 * 用户邀请DAO接口
 * @author RenHaipeng
 * @version 2016-12-16
 */
@Repository
public class UsmInviteDao extends BaseDao<UsmInvite> {

    public String getInvitee(String user_id){
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" u.id, u.number, u.telephone, ");
        sql.append(" SUBSTR(i.create_date,1,10) dates ");
        sql.append(" FROM ");
        sql.append(" usm_invite i");
        sql.append(" LEFT JOIN ");
        sql.append(" usm_user u");
        sql.append(" ON i.invitee_id = u.id");
        sql.append(" WHERE i.del_flag = '0'");
        sql.append(" AND i.inviter_id = '").append(user_id).append("'");
        return  sql.toString();
    }

}
