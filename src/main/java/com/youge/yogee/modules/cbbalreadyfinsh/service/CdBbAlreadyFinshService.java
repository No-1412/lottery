/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbalreadyfinsh.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cbbalreadyfinsh.dao.CdBbAlreadyFinshDao;
import com.youge.yogee.modules.cbbalreadyfinsh.entity.CdBbAlreadyFinsh;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 篮球已完赛Service
 *
 * @author WeiJinChao
 * @version 2018-01-30
 */
@Component
@Transactional(readOnly = true)
public class CdBbAlreadyFinshService extends BaseService {

    @Autowired
    private CdBbAlreadyFinshDao cdBbAlreadyFinshDao;

    public CdBbAlreadyFinsh get(String id) {
        return cdBbAlreadyFinshDao.get(id);
    }

    public Page<CdBbAlreadyFinsh> find(Page<CdBbAlreadyFinsh> page, CdBbAlreadyFinsh cdBbAlreadyFinsh) {
        DetachedCriteria dc = cdBbAlreadyFinshDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdBbAlreadyFinsh.getName())) {
            dc.add(Restrictions.like("name", "%" + cdBbAlreadyFinsh.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdBbAlreadyFinsh.FIELD_DEL_FLAG, CdBbAlreadyFinsh.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdBbAlreadyFinshDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdBbAlreadyFinsh cdBbAlreadyFinsh) {

        if (StringUtils.isEmpty(cdBbAlreadyFinsh.getId())) {
            cdBbAlreadyFinsh.setId(IdGen.uuid());
            cdBbAlreadyFinsh.setCreateDate(DateUtils.getDateTime());
            cdBbAlreadyFinsh.setDelFlag(CdBbAlreadyFinsh.DEL_FLAG_NORMAL);
        }
        cdBbAlreadyFinshDao.save(cdBbAlreadyFinsh);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdBbAlreadyFinshDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public List<CdBbAlreadyFinsh> selectIsExit(String matchId, String day) {
        return cdBbAlreadyFinshDao.findBySql("select * from cd_bb_alreadyfinsh where day= '" + day + "' and match_id= '" + matchId + "'");
    }

    @Transactional(readOnly = false)
    public List<CdBbAlreadyFinsh> getBbFinshed(String total, String count) {
        DetachedCriteria dc = cdBbAlreadyFinshDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdBbAlreadyFinsh.FIELD_DEL_FLAG, CdBbAlreadyFinsh.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("day"));
        dc.addOrder(Order.asc("matchId"));
        // 限制条数|分页
        Criteria cri = dc.getExecutableCriteria(cdBbAlreadyFinshDao.getSession());
        cri.setMaxResults(Integer.parseInt(count));
        cri.setFirstResult(Integer.parseInt(total));
        return cdBbAlreadyFinshDao.find(dc);
    }

    @Transactional(readOnly = false)
    public CdBbAlreadyFinsh getMatchByItemId(String itemid) {
        DetachedCriteria dc = cdBbAlreadyFinshDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdBbAlreadyFinsh.FIELD_DEL_FLAG, CdBbAlreadyFinsh.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("itemid", itemid));
        List<CdBbAlreadyFinsh> list = cdBbAlreadyFinshDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }
}
