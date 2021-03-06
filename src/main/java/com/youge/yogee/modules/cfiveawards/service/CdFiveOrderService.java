/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfiveawards.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfiveawards.dao.CdFiveOrderDao;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveOrder;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 排列五订单Service
 *
 * @author ZhaoYiFeng
 * @version 2018-02-07
 */
@Component
@Transactional(readOnly = true)
public class CdFiveOrderService extends BaseService {

    @Autowired
    private CdFiveOrderDao cdFiveOrderDao;

    public CdFiveOrder get(String id) {
        return cdFiveOrderDao.get(id);
    }

    public Page<CdFiveOrder> find(Page<CdFiveOrder> page, CdFiveOrder cdFiveOrder) {
        DetachedCriteria dc = cdFiveOrderDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdFiveOrder.getOrderNum())) {
            dc.add(Restrictions.eq("orderNum", cdFiveOrder.getOrderNum()));
        }
        dc.add(Restrictions.eq(CdFiveOrder.FIELD_DEL_FLAG, CdFiveOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.ne("status", "1"));
        dc.addOrder(Order.desc("createDate"));
        return cdFiveOrderDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdFiveOrder cdFiveOrder) {

        if (StringUtils.isEmpty(cdFiveOrder.getId())) {
            cdFiveOrder.setId(IdGen.uuid());
            cdFiveOrder.setCreateDate(DateUtils.getDateTime());
            cdFiveOrder.setDelFlag(CdFiveOrder.DEL_FLAG_NORMAL);
        }
        cdFiveOrderDao.save(cdFiveOrder);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdFiveOrderDao.deleteById(id);
    }

    public List<CdFiveOrder> findByStatus(String status, String weekday) {
        DetachedCriteria dc = cdFiveOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("status", status));
        dc.add(Restrictions.eq("weekday", weekday));
        dc.add(Restrictions.eq(CdFiveOrder.FIELD_DEL_FLAG, CdFiveOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdFiveOrderDao.find(dc);
    }

    /**
     * 所有追单的订单
     *
     * @return
     */
    public List<CdFiveOrder> findAllFollowOrders() {
        DetachedCriteria dc = cdFiveOrderDao.createDetachedCriteria();
        dc.add(Restrictions.ne("status", "1"));
        dc.add(Restrictions.eq("type", "2"));
        dc.add(Restrictions.or(Restrictions.eq("followType", "1"), Restrictions.eq("followType", "3")));
        dc.add(Restrictions.eq(CdFiveOrder.FIELD_DEL_FLAG, CdFiveOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdFiveOrderDao.find(dc);
    }

    public CdFiveOrder findOrderByOrderNum(String orderNum) {
        DetachedCriteria dc = cdFiveOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("orderNum", orderNum));
        dc.add(Restrictions.eq(CdFiveOrder.FIELD_DEL_FLAG, CdFiveOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        List<CdFiveOrder> list = cdFiveOrderDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }


    /**
     * 用户追单的订单
     *
     * @return
     */
    public List<CdFiveOrder> findAllFollowOrdersByUid(String uid) {
        DetachedCriteria dc = cdFiveOrderDao.createDetachedCriteria();
        dc.add(Restrictions.ne("status", "1"));
        dc.add(Restrictions.eq("type", "2"));
        dc.add(Restrictions.or(Restrictions.eq("followType", "1"), Restrictions.eq("followType", "2")));
        dc.add(Restrictions.eq("uid", uid));
        dc.add(Restrictions.eq(CdFiveOrder.FIELD_DEL_FLAG, CdFiveOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdFiveOrderDao.find(dc);
    }

    /**
     * 用户追单类型
     *
     * @return
     */
    public List<CdFiveOrder> findByFollowCode(String followCode) {
        DetachedCriteria dc = cdFiveOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("followCode", followCode));
        dc.add(Restrictions.eq(CdFiveOrder.FIELD_DEL_FLAG, CdFiveOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("createDate"));
        return cdFiveOrderDao.find(dc);
    }

    public CdFiveOrder findByFollowCodeAndStatus(String followCode) {
        DetachedCriteria dc = cdFiveOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("followCode", followCode));
        dc.add(Restrictions.or(Restrictions.eq("followType", "1"), Restrictions.eq("followType", "3")));
        dc.add(Restrictions.eq(CdFiveOrder.FIELD_DEL_FLAG, CdFiveOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        List<CdFiveOrder> list = cdFiveOrderDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

    /**
     * 未支付订单
     *
     * @return
     */
    public List<CdFiveOrder> findNotPay() {
        DetachedCriteria dc = cdFiveOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("status", "1"));
        dc.add(Restrictions.eq(CdFiveOrder.FIELD_DEL_FLAG, CdFiveOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("createDate"));
        return cdFiveOrderDao.find(dc);
    }


    @Transactional(readOnly = false)
    public int delById(String id) {
        return cdFiveOrderDao.update("delete from CdFiveOrder where id=:p1", new Parameter(id));
    }

    public List<CdFiveOrder> findPerhapsIsNull() {
        DetachedCriteria dc = cdFiveOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("allPerhaps", ""));
        dc.add(Restrictions.eq(CdFiveOrder.FIELD_DEL_FLAG, CdFiveOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("createDate"));
        return cdFiveOrderDao.find(dc);
    }


}
