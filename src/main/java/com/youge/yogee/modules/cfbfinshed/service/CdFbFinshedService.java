/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbfinshed.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfbfinshed.dao.CdFbFinshedDao;
import com.youge.yogee.modules.cfbfinshed.entity.CdFbFinshed;
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
public class CdFbFinshedService extends BaseService {

    @Autowired
    private CdFbFinshedDao cdFbFinshedDao;

    public CdFbFinshed get(String id) {
        return cdFbFinshedDao.get(id);
    }

    public Page<CdFbFinshed> find(Page<CdFbFinshed> page, CdFbFinshed cdFbFinshed) {
        DetachedCriteria dc = cdFbFinshedDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdFbFinshed.getName())) {
            dc.add(Restrictions.like("name", "%" + cdFbFinshed.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdFbFinshed.FIELD_DEL_FLAG, CdFbFinshed.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdFbFinshedDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdFbFinshed cdFbFinshed) {

        if (StringUtils.isEmpty(cdFbFinshed.getId())) {
            cdFbFinshed.setId(IdGen.uuid());
            cdFbFinshed.setCreateDate(DateUtils.getDateTime());
            cdFbFinshed.setDelFlag(CdFbFinshed.DEL_FLAG_NORMAL);
        }
        cdFbFinshedDao.save(cdFbFinshed);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdFbFinshedDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void fbFinshed() {
        cdFbFinshedDao.update("delete from CdFbFinshed");
    }

    @Transactional(readOnly = false)
    public List<CdFbFinshed> getNotFinshed(String total, String count) {
        DetachedCriteria dc = cdFbFinshedDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFbFinshed.FIELD_DEL_FLAG, CdFbFinshed.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("time"));
        dc.addOrder(Order.asc("jn"));
        // 限制条数|分页
        Criteria cri = dc.getExecutableCriteria(cdFbFinshedDao.getSession());
        cri.setMaxResults(Integer.parseInt(count));
        cri.setFirstResult(Integer.parseInt(total));
        return cdFbFinshedDao.find(dc);
    }

    @Transactional(readOnly = false)
    public CdFbFinshed findBySort(String sort) {
        DetachedCriteria dc = cdFbFinshedDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFbFinshed.FIELD_DEL_FLAG, CdFbFinshed.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("sort", sort));
        List<CdFbFinshed> list = cdFbFinshedDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

}
