/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csuccessfail.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFailOrder;
import com.youge.yogee.modules.csuccessfail.dao.CdSuccessFailOrderDao;

import java.util.List;

/**
 * 胜负彩订单Service
 *
 * @author ZhaoYiFeng
 * @version 2018-02-23
 */
@Component
@Transactional(readOnly = true)
public class CdSuccessFailOrderService extends BaseService {

    @Autowired
    private CdSuccessFailOrderDao cdSuccessFailOrderDao;

    public CdSuccessFailOrder get(String id) {
        return cdSuccessFailOrderDao.get(id);
    }

    public Page<CdSuccessFailOrder> find(Page<CdSuccessFailOrder> page, CdSuccessFailOrder cdSuccessFailOrder) {
        DetachedCriteria dc = cdSuccessFailOrderDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdSuccessFailOrder.getOrderNumber())) {
            dc.add(Restrictions.like("orderNumber", cdSuccessFailOrder.getOrderNumber()));
        }
        dc.add(Restrictions.eq(CdSuccessFailOrder.FIELD_DEL_FLAG, CdSuccessFailOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdSuccessFailOrderDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdSuccessFailOrder cdSuccessFailOrder) {

        if (StringUtils.isEmpty(cdSuccessFailOrder.getId())) {
            cdSuccessFailOrder.setId(IdGen.uuid());
            cdSuccessFailOrder.setCreateDate(DateUtils.getDateTime());
            cdSuccessFailOrder.setDelFlag(CdSuccessFailOrder.DEL_FLAG_NORMAL);
        }
        cdSuccessFailOrderDao.save(cdSuccessFailOrder);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdSuccessFailOrderDao.deleteById(id);
    }


    public List<CdSuccessFailOrder> findStatus() {
        DetachedCriteria dc = cdSuccessFailOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdSuccessFailOrder.FIELD_DEL_FLAG, CdSuccessFailOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("status", "3"));
        dc.addOrder(Order.desc("createDate"));
        return cdSuccessFailOrderDao.find(dc);
    }

    public CdSuccessFailOrder findOrderByOrderNum(String orderNumber) {
        DetachedCriteria dc = cdSuccessFailOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdSuccessFailOrder.FIELD_DEL_FLAG, CdSuccessFailOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("orderNumber", orderNumber));
        dc.addOrder(Order.desc("createDate"));
        List<CdSuccessFailOrder> list = cdSuccessFailOrderDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }
}
