/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfiveawards.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfiveawards.dao.CdFiveAwardsDao;
import com.youge.yogee.modules.cfiveawards.entity.CdFiveAwards;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 排列五开奖信息Service
 *
 * @author RenHaipeng
 * @version 2018-01-10
 */
@Component
@Transactional(readOnly = true)
public class CdFiveAwardsService extends BaseService {

    @Autowired
    private CdFiveAwardsDao cdFiveAwardsDao;

    public CdFiveAwards get(String id) {
        return cdFiveAwardsDao.get(id);
    }

    public Page<CdFiveAwards> find(Page<CdFiveAwards> page, CdFiveAwards cdFiveAwards) {
        DetachedCriteria dc = cdFiveAwardsDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdFiveAwards.getName())) {
            dc.add(Restrictions.like("name", "%" + cdFiveAwards.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdFiveAwards.FIELD_DEL_FLAG, CdFiveAwards.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdFiveAwardsDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdFiveAwards cdFiveAwards) {

        if (StringUtils.isEmpty(cdFiveAwards.getId())) {
            cdFiveAwards.setId(IdGen.uuid());
            cdFiveAwards.setCreateDate(DateUtils.getDateTime());
            cdFiveAwards.setDelFlag(CdFiveAwards.DEL_FLAG_NORMAL);
        }
        cdFiveAwardsDao.save(cdFiveAwards);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdFiveAwardsDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteFiveAwards() {
        cdFiveAwardsDao.update("delete from CdFiveAwards");
    }

    @Transactional(readOnly = false)
    public List<CdFiveAwards> getFiveAwards() {
        DetachedCriteria dc = cdFiveAwardsDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFiveAwards.FIELD_DEL_FLAG, CdFiveAwards.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdFiveAwardsDao.find(dc);
    }

    public CdFiveAwards findFirst() {
        DetachedCriteria dc = cdFiveAwardsDao.createDetachedCriteria();
        dc.addOrder(Order.desc("weekday"));
        List<CdFiveAwards> list = cdFiveAwardsDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
