/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cmagicorder.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.modules.cmagicorder.entity.CdMagicOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 神单订单DAO接口
 *
 * @author ZhaoYiFeng
 * @version 2018-03-05
 */
@Repository
public class CdMagicOrderDao extends BaseDao<CdMagicOrder> {

    public BigDecimal findJoinFoByNumber(String orderNumber) {
        String sql = "SELECT cmo.charges FROM cd_magic_order cmo JOIN cd_magic_follow_order cmfo ON cmo.id=cmfo.magic_order_id WHERE cmfo.order_num = '" + orderNumber + "'";
        List<CdMagicOrder> lists = new ArrayList<CdMagicOrder>();
        List<String> charges = findBySql(sql);
        if (charges == null) {
            return new BigDecimal("0");
        } else {
            return new BigDecimal(charges.get(0));
        }

    }
}
