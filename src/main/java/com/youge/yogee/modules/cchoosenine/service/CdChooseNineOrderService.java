/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cchoosenine.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cchoosenine.dao.CdChooseNineOrderDao;
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNineOrder;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任选九订单Service
 *
 * @author ZhaoYiFeng
 * @version 2018-02-22
 */
@Component
@Transactional(readOnly = true)
public class CdChooseNineOrderService extends BaseService {

    @Autowired
    private CdChooseNineOrderDao cdChooseNineOrderDao;

    public CdChooseNineOrder get(String id) {
        return cdChooseNineOrderDao.get(id);
    }

    public Page<CdChooseNineOrder> find(Page<CdChooseNineOrder> page, CdChooseNineOrder cdChooseNineOrder) {
        DetachedCriteria dc = cdChooseNineOrderDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdChooseNineOrder.getOrderNumber())) {
            dc.add(Restrictions.eq("orderNumber", cdChooseNineOrder.getOrderNumber()));
        }
        dc.add(Restrictions.eq(CdChooseNineOrder.FIELD_DEL_FLAG, CdChooseNineOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.ne("status", "1"));
        dc.addOrder(Order.desc("createDate"));
        return cdChooseNineOrderDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdChooseNineOrder cdChooseNineOrder) {

        if (StringUtils.isEmpty(cdChooseNineOrder.getId())) {
            cdChooseNineOrder.setId(IdGen.uuid());
            cdChooseNineOrder.setCreateDate(DateUtils.getDateTime());
            cdChooseNineOrder.setDelFlag(CdChooseNineOrder.DEL_FLAG_NORMAL);
        }
        cdChooseNineOrderDao.save(cdChooseNineOrder);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdChooseNineOrderDao.deleteById(id);
    }


    public List<CdChooseNineOrder> findStatus() {
        DetachedCriteria dc = cdChooseNineOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdChooseNineOrder.FIELD_DEL_FLAG, CdChooseNineOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("status", "3"));
        dc.addOrder(Order.desc("createDate"));
        return cdChooseNineOrderDao.find(dc);
    }

    public CdChooseNineOrder findOrderByOrderNum(String orderNumber) {
        DetachedCriteria dc = cdChooseNineOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdChooseNineOrder.FIELD_DEL_FLAG, CdChooseNineOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("orderNumber", orderNumber));
        dc.addOrder(Order.desc("createDate"));
        List<CdChooseNineOrder> list = cdChooseNineOrderDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<CdChooseNineOrder> findNotPay() {
        DetachedCriteria dc = cdChooseNineOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdChooseNineOrder.FIELD_DEL_FLAG, CdChooseNineOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("status", "1"));
        dc.addOrder(Order.desc("createDate"));
        return cdChooseNineOrderDao.find(dc);
    }

    @Transactional(readOnly = false)
    public int delById(String id) {
        return cdChooseNineOrderDao.update("delete from CdChooseNineOrder where id=:p1", new Parameter(id));
    }

}
