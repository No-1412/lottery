/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbnotfinsh.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cbbnotfinsh.dao.CdBbNotFinshDao;
import com.youge.yogee.modules.cbbnotfinsh.entity.CdBbNotFinsh;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 篮球未完赛Service
 *
 * @author RenHaipeng
 * @version 2018-01-31
 */
@Component
@Transactional(readOnly = true)
public class CdBbNotFinshService extends BaseService {

    @Autowired
    private CdBbNotFinshDao cdBbNotFinshDao;

    public CdBbNotFinsh get(String id) {
        return cdBbNotFinshDao.get(id);
    }

    public Page<CdBbNotFinsh> find(Page<CdBbNotFinsh> page, CdBbNotFinsh cdBbNotFinsh) {
        DetachedCriteria dc = cdBbNotFinshDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdBbNotFinsh.getName())) {
            dc.add(Restrictions.like("name", "%" + cdBbNotFinsh.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdBbNotFinsh.FIELD_DEL_FLAG, CdBbNotFinsh.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdBbNotFinshDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdBbNotFinsh cdBbNotFinsh) {

        if (StringUtils.isEmpty(cdBbNotFinsh.getId())) {
            cdBbNotFinsh.setId(IdGen.uuid());
            cdBbNotFinsh.setCreateDate(DateUtils.getDateTime());
            cdBbNotFinsh.setDelFlag(CdBbNotFinsh.DEL_FLAG_NORMAL);
        }
        cdBbNotFinshDao.save(cdBbNotFinsh);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdBbNotFinshDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public List<CdBbNotFinsh> selectIsExit(String matchId, String day) {
        return cdBbNotFinshDao.findBySql("select * from cd_bb_notfinish where day= '" + day + "' and match_id= '" + matchId + "'");
    }

    @Transactional(readOnly = false)
    public List<CdBbNotFinsh> getBbFinshed(String total, String count) {
        DetachedCriteria dc = cdBbNotFinshDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdBbNotFinsh.FIELD_DEL_FLAG, CdBbNotFinsh.DEL_FLAG_NORMAL));
//        dc.addOrder(Order.asc("day"));
//        dc.addOrder(Order.asc("matchId"));
        dc.addOrder(Order.asc("itemid"));
        // 限制条数|分页
        Criteria cri = dc.getExecutableCriteria(cdBbNotFinshDao.getSession());
        cri.setMaxResults(Integer.parseInt(count));
        cri.setFirstResult(Integer.parseInt(total));
        return cdBbNotFinshDao.find(dc);
    }

    @Transactional(readOnly = false)
    public CdBbNotFinsh getMatchByItemId(String itemId) {
        DetachedCriteria dc = cdBbNotFinshDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdBbNotFinsh.FIELD_DEL_FLAG, CdBbNotFinsh.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("itemid", itemId));
        List<CdBbNotFinsh> list = cdBbNotFinshDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }



    @Transactional(readOnly = false)
    public CdBbNotFinsh getMatchByMatchId(String matchId) {
        DetachedCriteria dc = cdBbNotFinshDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdBbNotFinsh.FIELD_DEL_FLAG, CdBbNotFinsh.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("matchId", matchId));
        List<CdBbNotFinsh> list = cdBbNotFinshDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }


}
