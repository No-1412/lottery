/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cthreeawards.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveOrder;
import com.youge.yogee.modules.cthreeawards.dao.CdThreeOrderDao;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeOrder;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 排列三订单Service
 *
 * @author ZhaoYiFeng
 * @version 2018-02-06
 */
@Component
@Transactional(readOnly = true)
public class CdThreeOrderService extends BaseService {

    @Autowired
    private CdThreeOrderDao cdThreeOrderDao;

    public CdThreeOrder get(String id) {
        return cdThreeOrderDao.get(id);
    }

    public Page<CdThreeOrder> find(Page<CdThreeOrder> page, CdThreeOrder cdThreeOrder) {
        DetachedCriteria dc = cdThreeOrderDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdThreeOrder.getOrderNum())) {
            dc.add(Restrictions.eq("orderNum", cdThreeOrder.getOrderNum()));
        }
        if (StringUtils.isNotEmpty(cdThreeOrder.getBuyWays())) {
            dc.add(Restrictions.eq("buyWays", cdThreeOrder.getBuyWays()));
        }
        dc.add(Restrictions.eq(CdThreeOrder.FIELD_DEL_FLAG, CdThreeOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.ne("status", "1"));
        dc.addOrder(Order.desc("createDate"));
        return cdThreeOrderDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdThreeOrder cdThreeOrder) {

        if (StringUtils.isEmpty(cdThreeOrder.getId())) {
            cdThreeOrder.setId(IdGen.uuid());
            cdThreeOrder.setCreateDate(DateUtils.getDateTime());
            cdThreeOrder.setDelFlag(CdThreeOrder.DEL_FLAG_NORMAL);
        }
        cdThreeOrderDao.save(cdThreeOrder);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdThreeOrderDao.deleteById(id);
    }

    public List<CdThreeOrder> findByStatus(String status, String weekday) {
        DetachedCriteria dc = cdThreeOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("status", status));
        dc.add(Restrictions.eq("weekday", weekday));
        dc.add(Restrictions.eq(CdThreeOrder.FIELD_DEL_FLAG, CdThreeOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdThreeOrderDao.find(dc);
    }

    public List<CdThreeOrder> findAllFollowOrders() {
        DetachedCriteria dc = cdThreeOrderDao.createDetachedCriteria();
        dc.add(Restrictions.ne("status", "1"));
        dc.add(Restrictions.eq("type", "2"));
        dc.add(Restrictions.or(Restrictions.eq("followType", "1"), Restrictions.eq("followType", "3")));
        dc.add(Restrictions.eq(CdThreeOrder.FIELD_DEL_FLAG, CdThreeOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdThreeOrderDao.find(dc);
    }

    public CdThreeOrder findOrderByOrderNum(String orderNum) {
        DetachedCriteria dc = cdThreeOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("orderNum", orderNum));
        dc.add(Restrictions.eq(CdThreeOrder.FIELD_DEL_FLAG, CdThreeOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        List<CdThreeOrder> list = cdThreeOrderDao.find(dc);
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
    public List<CdThreeOrder> findAllFollowOrdersByUid(String uid) {
        DetachedCriteria dc = cdThreeOrderDao.createDetachedCriteria();
        dc.add(Restrictions.ne("status", "1"));
        dc.add(Restrictions.eq("type", "2"));
        dc.add(Restrictions.or(Restrictions.eq("followType", "1"), Restrictions.eq("followType", "2")));
        dc.add(Restrictions.eq("uid", uid));
        dc.add(Restrictions.eq(CdThreeOrder.FIELD_DEL_FLAG, CdThreeOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdThreeOrderDao.find(dc);
    }

    public List<CdThreeOrder> findByFollowCode(String followCode) {
        DetachedCriteria dc = cdThreeOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("followCode", followCode));
        dc.add(Restrictions.eq(CdThreeOrder.FIELD_DEL_FLAG, CdThreeOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("createDate"));
        return cdThreeOrderDao.find(dc);
    }

    public CdThreeOrder findByFollowCodeAndStatus(String followCode) {
        DetachedCriteria dc = cdThreeOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("followCode", followCode));
        dc.add(Restrictions.or(Restrictions.eq("followType", "1"), Restrictions.eq("followType", "3")));
        dc.add(Restrictions.eq(CdThreeOrder.FIELD_DEL_FLAG, CdThreeOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        List<CdThreeOrder> list = cdThreeOrderDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

    public List<CdThreeOrder> findNotPay() {
        DetachedCriteria dc = cdThreeOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("status", "1"));
        dc.add(Restrictions.eq(CdFiveOrder.FIELD_DEL_FLAG, CdFiveOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("createDate"));
        return cdThreeOrderDao.find(dc);
    }

    @Transactional(readOnly = false)
    public int delById(String id) {
        return cdThreeOrderDao.update("delete from CdThreeOrder where id=:p1", new Parameter(id));
    }

}
