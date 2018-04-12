/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.corder.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.corder.dao.CdOrderDao;
import com.youge.yogee.modules.corder.entity.CdOrder;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 彩票订单表Service
 *
 * @author WeiJinChao
 * @version 2017-12-08
 */
@Component
@Transactional(readOnly = true)
public class CdOrderService extends BaseService {

    @Autowired
    private CdOrderDao cdOrderDao;

    public CdOrder get(String id) {
        return cdOrderDao.get(id);
    }

    public Page<CdOrder> find(Page<CdOrder> page, CdOrder cdOrder) {
        DetachedCriteria dc = cdOrderDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdOrder.getName())) {
            dc.add(Restrictions.like("name", "%" + cdOrder.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdOrder.FIELD_DEL_FLAG, CdOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdOrderDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdOrder cdOrder) {

        if (StringUtils.isEmpty(cdOrder.getId())) {
            cdOrder.setId(IdGen.uuid());
            cdOrder.setCreateDate(DateUtils.getDateTime());
            cdOrder.setDelFlag(CdOrder.DEL_FLAG_NORMAL);
        }
        cdOrderDao.save(cdOrder);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdOrderDao.deleteById(id);
    }

    /**
     * 根据用户id查询彩票订单
     */
    @Transactional(readOnly = false)
    public List<CdOrder> listCdOrder(String uid, String status, String total, String count) {
        DetachedCriteria dc = cdOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("userId", uid));
        if (StringUtils.isNotEmpty(status)) {
            if ("2".equals(status)) {
                dc.add(Restrictions.ne("status", "1"));
            } else {
                dc.add(Restrictions.eq("status", status));
            }
        }
        dc.add(Restrictions.eq(CdOrder.FIELD_DEL_FLAG, CdOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));

//        Criteria cri = dc.getExecutableCriteria(cdOrderDao.getSession());
//        cri.setMaxResults(Integer.parseInt(count));
//        cri.setFirstResult((Integer.parseInt(total) - 1) * Integer.parseInt(count));
        Criteria cri = dc.getExecutableCriteria(cdOrderDao.getSession());
        cri.setMaxResults(Integer.parseInt(count));
        cri.setFirstResult(Integer.parseInt(total));
        return cdOrderDao.find(dc);
    }

    /**
     * 根据订单号获取订单详情
     *
     * @param orderNum
     * @return
     */
    public CdOrder getOrderByOrderNum(String orderNum) {
        DetachedCriteria dc = cdOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("number", orderNum));
        dc.add(Restrictions.eq(CdOrder.FIELD_DEL_FLAG, CdOrder.DEL_FLAG_NORMAL));
        List<CdOrder> list = cdOrderDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }


}
