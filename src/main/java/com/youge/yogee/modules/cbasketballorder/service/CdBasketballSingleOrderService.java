/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballorder.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cbasketballorder.dao.CdBasketballSingleOrderDao;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 竞彩篮球订单Service
 *
 * @author ZhaoYiFeng
 * @version 2018-02-26
 */
@Component
@Transactional(readOnly = true)
public class CdBasketballSingleOrderService extends BaseService {

    @Autowired
    private CdBasketballSingleOrderDao cdBasketballSingleOrderDao;

    public CdBasketballSingleOrder get(String id) {
        return cdBasketballSingleOrderDao.get(id);
    }

    public Page<CdBasketballSingleOrder> find(Page<CdBasketballSingleOrder> page, CdBasketballSingleOrder cdBasketballSingleOrder) {
        DetachedCriteria dc = cdBasketballSingleOrderDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdBasketballSingleOrder.getOrderNum())) {
            dc.add(Restrictions.eq("orderNum", cdBasketballSingleOrder.getOrderNum()));
        }
        if (StringUtils.isNotEmpty(cdBasketballSingleOrder.getBuyWays())) {
            dc.add(Restrictions.eq("buyWays", cdBasketballSingleOrder.getBuyWays()));
        }
        dc.add(Restrictions.eq(CdBasketballSingleOrder.FIELD_DEL_FLAG, CdBasketballSingleOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.ne("status", "1"));
        dc.addOrder(Order.desc("createDate"));
        return cdBasketballSingleOrderDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdBasketballSingleOrder cdBasketballSingleOrder) {

        if (StringUtils.isEmpty(cdBasketballSingleOrder.getId())) {
            cdBasketballSingleOrder.setId(IdGen.uuid());
            cdBasketballSingleOrder.setCreateDate(DateUtils.getDateTime());
            cdBasketballSingleOrder.setDelFlag(CdBasketballSingleOrder.DEL_FLAG_NORMAL);
        }
        cdBasketballSingleOrderDao.save(cdBasketballSingleOrder);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdBasketballSingleOrderDao.deleteById(id);
    }


    public CdBasketballSingleOrder findOrderByOrderNum(String orderNum) {
        DetachedCriteria dc = cdBasketballSingleOrderDao.createDetachedCriteria();

        dc.add(Restrictions.eq("orderNum", orderNum));
        dc.add(Restrictions.eq(CdBasketballSingleOrder.FIELD_DEL_FLAG, CdBasketballSingleOrder.DEL_FLAG_NORMAL));

        List<CdBasketballSingleOrder> list = cdBasketballSingleOrderDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<CdBasketballSingleOrder> findStatus() {
        DetachedCriteria dc = cdBasketballSingleOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdBasketballSingleOrder.FIELD_DEL_FLAG, CdBasketballSingleOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("status", "3"));
        dc.addOrder(Order.desc("createDate"));
        return cdBasketballSingleOrderDao.find(dc);
    }

    public List<CdBasketballSingleOrder> findNotPay() {
        DetachedCriteria dc = cdBasketballSingleOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdBasketballSingleOrder.FIELD_DEL_FLAG, CdBasketballSingleOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("status", "1"));
        dc.addOrder(Order.desc("createDate"));
        return cdBasketballSingleOrderDao.find(dc);
    }
    @Transactional(readOnly = false)
    public int delById(String id) {
        return cdBasketballSingleOrderDao.update("delete from CdBasketballSingleOrder where id=:p1", new Parameter(id));
    }

}
