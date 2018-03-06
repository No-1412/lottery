/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballdouble.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfootballdouble.dao.CdFootBallDoubleDao;
import com.youge.yogee.modules.cfootballdouble.entity.CdFootBallDouble;
import com.youge.yogee.modules.cfootballgoal.entity.CdFootBallGoal;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFail;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 足球半全场Service
 *
 * @author RenHaipeng
 * @version 2018-01-04
 */
@Component
@Transactional(readOnly = true)
public class CdFootBallDoubleService extends BaseService {

    @Autowired
    private CdFootBallDoubleDao cdFootBallDoubleDao;

    public CdFootBallDouble get(String id) {
        return cdFootBallDoubleDao.get(id);
    }

    public Page<CdFootBallDouble> find(Page<CdFootBallDouble> page, CdFootBallDouble cdFootBallDouble) {
        DetachedCriteria dc = cdFootBallDoubleDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdFootBallDouble.getName())) {
            dc.add(Restrictions.like("name", "%" + cdFootBallDouble.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdFootBallDouble.FIELD_DEL_FLAG, CdFootBallDouble.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdFootBallDoubleDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdFootBallDouble cdFootBallDouble) {

        if (StringUtils.isEmpty(cdFootBallDouble.getId())) {
            cdFootBallDouble.setId(IdGen.uuid());
            cdFootBallDouble.setCreateDate(DateUtils.getDateTime());
            cdFootBallDouble.setDelFlag(CdFootBallDouble.DEL_FLAG_NORMAL);
        }
        cdFootBallDoubleDao.save(cdFootBallDouble);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdFootBallDoubleDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteFootBallDouble() {
        cdFootBallDoubleDao.update("delete from CdFootBallDouble");
    }

    @Transactional(readOnly = false)
    public List<CdFootBallDouble> getFootDouble() {
        DetachedCriteria dc = cdFootBallDoubleDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFootBallDouble.FIELD_DEL_FLAG, CdFootBallDouble.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("weekday"));
        return cdFootBallDoubleDao.find(dc);
    }

    @Transactional(readOnly = false)
    public List<CdFootBallDouble> getFootBallDoubleByWeekDay(String weekday) {
        DetachedCriteria dc = cdFootBallDoubleDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFootBallDouble.FIELD_DEL_FLAG, CdFootBallDouble.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("weekday", weekday));
        dc.add(Restrictions.sqlRestriction("1=1 order by CAST(match_id as SIGNED)"));
        List<CdFootBallDouble> list = cdFootBallDoubleDao.find(dc);
        return list;


    }
}
