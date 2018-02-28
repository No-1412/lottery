/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.lom.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.lom.entity.LomCollect;
import com.youge.yogee.modules.sys.entity.Dict;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收藏记录DAO接口
 * @author RenHaipeng
 * @version 2017-03-06
 */
@Repository
public class LomCollectDao extends BaseDao<LomCollect> {
    public List<Object[]> getMyCollect(String userId, String total, String count){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append(" note.name ,note.img ,note.id,note.summary ,note.imgs ");
        sql.append(" FROM ( ");
        sql.append(" SELECT un.id, un.user_id, un.summary , un.top_imgs AS imgs, uu.name ,uu.img  FROM  lom_collect lc  ");
        sql.append(" LEFT JOIN usm_note un ");
        sql.append(" ON lc.belong_id = un.id ");
        sql.append(" LEFT JOIN  usm_user uu  ");
        sql.append(" ON un.user_id = uu.id ");
        sql.append(" WHERE lc.del_flag = '0' AND lc.user_id = '").append(userId).append("'");
        sql.append(" ) note");

        if(StringUtils.isNotEmpty(total) && StringUtils.isNotEmpty(count)){
            sql.append(" LIMIT ").append(total).append(",").append(count);
        }

        return findBySql(sql.toString());
    }

    //删除帖子的同时取消所有收藏
    public int cancleCollect(String belongId){
        int x = updateBySql("UPDATE lom_collect SET del_flag=:p1 WHERE belong_id=:p2 ", new Parameter(Dict.DEL_FLAG_DELETE, belongId));
        System.out.println(x);
        return x;
    }
}
