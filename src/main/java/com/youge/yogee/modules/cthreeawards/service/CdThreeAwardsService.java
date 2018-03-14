/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cthreeawards.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cthreeawards.dao.CdThreeAwardsDao;
import com.youge.yogee.modules.cthreeawards.entity.CdThreeAwards;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 排列三开奖信息Service
 *
 * @author RenHaipeng
 * @version 2018-01-10
 */
@Component
@Transactional(readOnly = true)
public class CdThreeAwardsService extends BaseService {

    @Autowired
    private CdThreeAwardsDao cdThreeAwardsDao;

    public CdThreeAwards get(String id) {
        return cdThreeAwardsDao.get(id);
    }

    public Page<CdThreeAwards> find(Page<CdThreeAwards> page, CdThreeAwards cdThreeAwards) {
        DetachedCriteria dc = cdThreeAwardsDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdThreeAwards.getName())) {
            dc.add(Restrictions.like("name", "%" + cdThreeAwards.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdThreeAwards.FIELD_DEL_FLAG, CdThreeAwards.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdThreeAwardsDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdThreeAwards cdThreeAwards) {

        if (StringUtils.isEmpty(cdThreeAwards.getId())) {
            cdThreeAwards.setId(IdGen.uuid());
            cdThreeAwards.setCreateDate(DateUtils.getDateTime());
            cdThreeAwards.setDelFlag(CdThreeAwards.DEL_FLAG_NORMAL);
        }
        cdThreeAwardsDao.save(cdThreeAwards);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdThreeAwardsDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteThreeAwards() {
        cdThreeAwardsDao.update("delete from CdThreeAwards");
    }

    @Transactional(readOnly = false)
    public List<CdThreeAwards> getThreeAwards() {
        DetachedCriteria dc = cdThreeAwardsDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdThreeAwards.FIELD_DEL_FLAG, CdThreeAwards.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdThreeAwardsDao.find(dc);
    }

    public CdThreeAwards findFirst() {
        DetachedCriteria dc = cdThreeAwardsDao.createDetachedCriteria();
        dc.addOrder(Order.desc("weekday"));
        List<CdThreeAwards> list = cdThreeAwardsDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
