/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.dao;

import org.springframework.stereotype.Repository;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.modules.bm.entity.BmPush;

import java.util.List;

/**
 * 推送表DAO接口
 * @author ZhaoYiFeng
 * @version 2018-03-09
 */
@Repository
public class BmPushDao extends BaseDao<BmPush> {

    public int deleteByPushid(String pushid){
        return update("delete from BmPush  where pushid = :p1", new Parameter(pushid));
    }

    public List<String> findAllIOS(){
        return findBySql("select pushid from bm_push  where type = 1");
    }



}
