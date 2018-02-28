/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cmessage.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.modules.cmessage.entity.CdMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 短信信息表DAO接口
 * @author WeiJinChao
 * @version 2017-12-08
 */
@Repository
public class CdMessageDao extends BaseDao<CdMessage> {
    public List findmessageCount(String phone, String date){
        String sql = "select count(*) from cd_message where del_flag = 0 and phone = '"+ phone+"' and create_date like '"+date+"%'";
        return findBySql(sql);
    }
    public List findDateAndCode(String phone){
        return findBySql("SELECT create_date,code FROM cd_message WHERE del_flag = 0 AND phone =" +
                " "+phone+" AND check_zt = 0 ORDER BY create_date DESC LIMIT 1 ");

    }
}
