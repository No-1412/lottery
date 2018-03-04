/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csuccessfail.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.csuccessfail.dao.CdSuccessFailDao;
import com.youge.yogee.modules.csuccessfail.entity.CdSuccessFail;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 胜负彩Service
 *
 * @author RenHaipeng
 * @version 2018-01-04
 */
@Component
@Transactional(readOnly = true)
public class CdSuccessFailService extends BaseService {

    @Autowired
    private CdSuccessFailDao cdSuccessFailDao;

    public CdSuccessFail get(String id) {
        return cdSuccessFailDao.get(id);
    }

    public Page<CdSuccessFail> find(Page<CdSuccessFail> page, CdSuccessFail cdSuccessFail) {
        DetachedCriteria dc = cdSuccessFailDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdSuccessFail.getEventName())) {
//			dc.add(Restrictions.like("eventName", "%" + cdSuccessFail.getEventName() + "%"));
            dc.add(Restrictions.or(Restrictions.like("homeTeam", "%" + cdSuccessFail.getEventName() + "%"), Restrictions.like("awayTeam", "%" + cdSuccessFail.getEventName() + "%")));
        }
        dc.add(Restrictions.eq(CdSuccessFail.FIELD_DEL_FLAG, CdSuccessFail.DEL_FLAG_NORMAL));
//		dc.addOrder(Order.asc("weekday ,CAST(matchId as SIGNED)"));
        dc.add(Restrictions.sqlRestriction("1=1 order by weekday,CAST(match_id as SIGNED)"));
        //dc.addOrder(Order.asc("matchId"));
        return cdSuccessFailDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdSuccessFail cdSuccessFail) {

        if (StringUtils.isEmpty(cdSuccessFail.getId())) {
            cdSuccessFail.setId(IdGen.uuid());
            cdSuccessFail.setCreateDate(DateUtils.getDateTime());
            cdSuccessFail.setDelFlag(CdSuccessFail.DEL_FLAG_NORMAL);
        }
        cdSuccessFailDao.save(cdSuccessFail);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdSuccessFailDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteSuccessFail() {
        cdSuccessFailDao.update("delete from CdSuccessFail");
    }

    @Transactional(readOnly = false)
    public List<CdSuccessFail> getSuccessFail() {
        DetachedCriteria dc = cdSuccessFailDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdSuccessFail.FIELD_DEL_FLAG, CdSuccessFail.DEL_FLAG_NORMAL));
        //dc.addOrder(Order.desc("createDate"));
        dc.add(Restrictions.sqlRestriction("1=1 order by weekday,CAST(match_id as SIGNED)"));
        return cdSuccessFailDao.find(dc);
    }

    @Transactional(readOnly = false)
    public CdSuccessFail getSuccessFailDetail(String mid, String weekday) {
        DetachedCriteria dc = cdSuccessFailDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdSuccessFail.FIELD_DEL_FLAG, CdSuccessFail.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("weekday", weekday));
        dc.add(Restrictions.eq("matchId", mid));
        List<CdSuccessFail> list = cdSuccessFailDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = false)
    public List<CdSuccessFail> getSuccessFailByWeekDay(String weekday) {
        DetachedCriteria dc = cdSuccessFailDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdSuccessFail.FIELD_DEL_FLAG, CdSuccessFail.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("weekday", weekday));
        dc.add(Restrictions.sqlRestriction("1=1 order by CAST(match_id as SIGNED)"));
        List<CdSuccessFail> list = cdSuccessFailDao.find(dc);
        return list;

    }

}
