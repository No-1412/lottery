/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballorder.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cbasketballorder.dao.CdBasketballBestFollowOrderDao;
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballBestFollowOrder;
import com.youge.yogee.modules.cfootballorder.entity.CdFootballBestFollowOrder;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 篮球优化Service
 *
 * @author ZhaoYiFeng
 * @version 2018-04-18
 */
@Component
@Transactional(readOnly = true)
public class CdBasketballBestFollowOrderService extends BaseService {

    @Autowired
    private CdBasketballBestFollowOrderDao cdBasketballBestFollowOrderDao;

    public CdBasketballBestFollowOrder get(String id) {
        return cdBasketballBestFollowOrderDao.get(id);
    }

    public Page<CdBasketballBestFollowOrder> find(Page<CdBasketballBestFollowOrder> page, CdBasketballBestFollowOrder cdBasketballBestFollowOrder) {
        DetachedCriteria dc = cdBasketballBestFollowOrderDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdBasketballBestFollowOrder.getName())) {
            dc.add(Restrictions.like("name", "%" + cdBasketballBestFollowOrder.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdBasketballBestFollowOrder.FIELD_DEL_FLAG, CdBasketballBestFollowOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdBasketballBestFollowOrderDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdBasketballBestFollowOrder cdBasketballBestFollowOrder) {

        if (StringUtils.isEmpty(cdBasketballBestFollowOrder.getId())) {
            cdBasketballBestFollowOrder.setId(IdGen.uuid());
            cdBasketballBestFollowOrder.setCreateDate(DateUtils.getDateTime());
            cdBasketballBestFollowOrder.setDelFlag(CdBasketballBestFollowOrder.DEL_FLAG_NORMAL);
        }
        cdBasketballBestFollowOrderDao.save(cdBasketballBestFollowOrder);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdBasketballBestFollowOrderDao.deleteById(id);
    }

    public List<CdBasketballBestFollowOrder> findByOrderNum(String orderNum) {
        DetachedCriteria dc = cdBasketballBestFollowOrderDao.createDetachedCriteria();
        dc.add(Restrictions.like("orderNum", orderNum));
        dc.add(Restrictions.eq(CdBasketballBestFollowOrder.FIELD_DEL_FLAG, CdBasketballBestFollowOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdBasketballBestFollowOrderDao.find(dc);
    }

    public List<CdBasketballBestFollowOrder> findByNum(String num) {
        DetachedCriteria dc = cdBasketballBestFollowOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq("orderNum", num));
        dc.add(Restrictions.eq(CdBasketballBestFollowOrder.FIELD_DEL_FLAG, CdBasketballBestFollowOrder.DEL_FLAG_NORMAL));
        return cdBasketballBestFollowOrderDao.find(dc);
    }

}
