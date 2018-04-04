/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbnotfinish.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfbnotfinish.dao.CdFbNotFinishDao;
import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinish;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 足球未完赛信息Service
 *
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Component
@Transactional(readOnly = true)
public class CdFbNotFinishService extends BaseService {

    @Autowired
    private CdFbNotFinishDao cdFbNotFinishDao;

    public CdFbNotFinish get(String id) {
        return cdFbNotFinishDao.get(id);
    }

    public Page<CdFbNotFinish> find(Page<CdFbNotFinish> page, CdFbNotFinish cdFbNotFinish) {
        DetachedCriteria dc = cdFbNotFinishDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdFbNotFinish.getName())) {
            dc.add(Restrictions.like("name", "%" + cdFbNotFinish.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdFbNotFinish.FIELD_DEL_FLAG, CdFbNotFinish.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdFbNotFinishDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdFbNotFinish cdFbNotFinish) {

        if (StringUtils.isEmpty(cdFbNotFinish.getId())) {
            cdFbNotFinish.setId(IdGen.uuid());
            cdFbNotFinish.setCreateDate(DateUtils.getDateTime());
            cdFbNotFinish.setDelFlag(CdFbNotFinish.DEL_FLAG_NORMAL);
        }
        cdFbNotFinishDao.save(cdFbNotFinish);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdFbNotFinishDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void fbFinshed() {
        cdFbNotFinishDao.update("delete from CdFbNotFinish");
    }

    @Transactional(readOnly = false)
    public List<CdFbNotFinish> getNotFinish(String total, String count) {
        DetachedCriteria dc = cdFbNotFinishDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFbNotFinish.FIELD_DEL_FLAG, CdFbNotFinish.DEL_FLAG_NORMAL));
        dc.add(Restrictions.ne("type", "4"));
        dc.addOrder(Order.asc("time"));
        dc.addOrder(Order.asc("jn"));

        // 限制条数|分页
        Criteria cri = dc.getExecutableCriteria(cdFbNotFinishDao.getSession());
        cri.setMaxResults(Integer.parseInt(count));
        cri.setFirstResult(Integer.parseInt(total));
        return cdFbNotFinishDao.find(dc);
    }

    @Transactional(readOnly = false)
    public CdFbNotFinish findBySort(String sort) {
        DetachedCriteria dc = cdFbNotFinishDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFbNotFinish.FIELD_DEL_FLAG, CdFbNotFinish.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("sort", sort));
        List<CdFbNotFinish> list = cdFbNotFinishDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }


    @Transactional(readOnly = false)
    public List<CdFbNotFinish> getNotFinish() {
        DetachedCriteria dc = cdFbNotFinishDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFbNotFinish.FIELD_DEL_FLAG, CdFbNotFinish.DEL_FLAG_NORMAL));
        dc.add(Restrictions.ne("type", "4"));
        dc.addOrder(Order.asc("time"));
        dc.addOrder(Order.asc("jn"));
        return cdFbNotFinishDao.find(dc);
    }


}
