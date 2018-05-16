/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.erp.dao.ErpOrderDao;
import com.youge.yogee.modules.erp.entity.ErpOrder;
import com.youge.yogee.modules.erp.entity.ErpUser;
import com.youge.yogee.modules.sys.utils.UserUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业绩Service
 *
 * @author RenHaipeng
 * @version 2018-03-07
 */
@Component
@Transactional(readOnly = true)
public class ErpOrderService extends BaseService {

    @Autowired
    private ErpOrderDao erpOrderDao;

    public ErpOrder get(String id) {
        return erpOrderDao.get(id);
    }

    public Page<ErpOrder> find(Page<ErpOrder> page, ErpOrder erpOrder) {
        DetachedCriteria dc = erpOrderDao.createDetachedCriteria();
        String userId = UserUtils.getUser().getId();
        dc.createAlias("userId", "userId");
        if (!userId.equals("1")) {
            dc.createAlias("userId.saleId", "saleId");
            dc.add(Restrictions.eq("saleId.id", userId));
        }
        if (StringUtils.isNotEmpty(erpOrder.getName())) {
            dc.add(Restrictions.like("userId.name", "%" + erpOrder.getName() + "%"));
        }
        if (StringUtils.isNotEmpty(erpOrder.getStatus())) {
            dc.add(Restrictions.eq("status", erpOrder.getStatus()));
        }
        if (StringUtils.isNotEmpty(erpOrder.getNumber())) {
            dc.add(Restrictions.eq("number", erpOrder.getNumber()));
        }

        dc.add(Restrictions.eq(ErpOrder.FIELD_DEL_FLAG, ErpOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return erpOrderDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(ErpOrder erpOrder) {

        if (StringUtils.isEmpty(erpOrder.getId())) {
            erpOrder.setId(IdGen.uuid());
            erpOrder.setCreateDate(DateUtils.getDateTime());
            erpOrder.setDelFlag(ErpOrder.DEL_FLAG_NORMAL);
        }
        erpOrderDao.save(erpOrder);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        erpOrderDao.deleteById(id);
    }


    //取制定时间自主消费合
    public Double findOtherSumByErpUserAndDateByAutonomy(ErpUser erpUser, String date) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT SUM(total_price) FROM cd_order WHERE user_id = '" + erpUser.getId() + "' AND issue = '0' AND create_date like ");
        sb.append(" '" + date + "%'");
        List sums = erpOrderDao.findBySql(sb.toString());
        Double sum = (Double) sums.get(0);
        return sum;
    }

    //取制定时间跟单消费合
    public Double findOtherSumByErpUserAndDateByDocumentary(ErpUser erpUser, String date) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT SUM(total_price) FROM cd_order WHERE user_id = '" + erpUser.getId() + "' AND issue = '1' AND create_date like ");
        sb.append(" '" + date + "%'");
        List sums = erpOrderDao.findBySql(sb.toString());
        Double sum = (Double) sums.get(0);
        return sum;
    }

    //取制定时间跟单消费合
    public Double findOtherSumByErpUserAndDate(ErpUser erpUser, String date) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT SUM(total_price) FROM cd_order WHERE user_id = '" + erpUser.getId() + "'  AND create_date like ");
        sb.append(" '" + date + "%'");
        List sums = erpOrderDao.findBySql(sb.toString());
        Double sum = (Double) sums.get(0);
        return sum;
    }

    //当日业绩
    public Page<Map<String, String>> findRegisterList(Page<Map<String, String>> page, String userId) {
        List<Map<String, String>> list = new ArrayList();
        Page performanceList = erpOrderDao.findPerformanceList(page, userId);
        for (int i = 0; i < performanceList.getList().size(); i++) {
            Object[] objects = (Object[]) performanceList.getList().get(i);
            Map data = new HashMap();
            data.put("zigou", objects[1] == null ? "0" : objects[1].toString());
            data.put("fuzhi", objects[2] == null ? "0" : objects[2].toString());
            data.put("shouru", objects[3] == null ? "0" : objects[3].toString());
            list.add(data);
        }
        performanceList.setList(list);
        return performanceList;
    }

    public ErpOrder findByNumber(String number) {
        DetachedCriteria dc = erpOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("number", number));
        dc.add(Restrictions.eq(ErpOrder.FIELD_DEL_FLAG, ErpOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        List<ErpOrder> list = erpOrderDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
