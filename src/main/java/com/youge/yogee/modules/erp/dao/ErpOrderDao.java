/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.dao;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.modules.erp.entity.ErpOrder;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 业绩DAO接口
 * @author RenHaipeng
 * @version 2018-03-07
 */
@Repository
public class ErpOrderDao extends BaseDao<ErpOrder> {

    public Page findPerformanceList(Page<Map<String, String>> page, String userId) {
        String sql = " SELECT z.*,z.zigou+z.fuzhi as total FROM  " +
                "(SELECT a.saleid as saleid,a.zigou*0.024 as zigou,b.fuzhi*0.032 as fuzhi  from " +
                " (SELECT SUM(cdorder.total_price) as zigou,cdorder.sale_id as saleid from cd_order cdorder   " +
                " LEFT JOIN sys_user su on cdorder.sale_id = su.id where cdorder.issue<> '1' and cdorder.sale_id = '"+userId+"' " +
                " AND date(cdorder.create_date) = curdate()) a " +
                "  LEFT JOIN " +
                "  (SELECT SUM(cdorder.total_price) as fuzhi,cdorder.sale_id as saleid from cd_order cdorder " +
                "  LEFT JOIN sys_user su on cdorder.sale_id = su.id where cdorder.issue= '1' and cdorder.sale_id = '"+userId+"' " +
                "  AND date(cdorder.create_date) = curdate()) b" +
                "  ON a.saleid = b.saleid ) z";
        return findBySql(page,sql);
    }
}
