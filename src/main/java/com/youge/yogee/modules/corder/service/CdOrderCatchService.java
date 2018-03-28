/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.corder.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.corder.dao.CdOrderCatchDao;
import com.youge.yogee.modules.corder.entity.CdOrderCatch;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 追号Service
 *
 * @author ZhaoYiFeng
 * @version 2018-03-27
 */
@Component
@Transactional(readOnly = true)
public class CdOrderCatchService extends BaseService {

    @Autowired
    private CdOrderCatchDao cdOrderCatchDao;

    public CdOrderCatch get(String id) {
        return cdOrderCatchDao.get(id);
    }

    public Page<CdOrderCatch> find(Page<CdOrderCatch> page, CdOrderCatch cdOrderCatch) {
        DetachedCriteria dc = cdOrderCatchDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdOrderCatch.getName())) {
            dc.add(Restrictions.like("name", "%" + cdOrderCatch.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdOrderCatch.FIELD_DEL_FLAG, CdOrderCatch.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdOrderCatchDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdOrderCatch cdOrderCatch) {

        if (StringUtils.isEmpty(cdOrderCatch.getId())) {
            cdOrderCatch.setId(IdGen.uuid());
            cdOrderCatch.setCreateDate(DateUtils.getDateTime());
            cdOrderCatch.setDelFlag(CdOrderCatch.DEL_FLAG_NORMAL);
        }
        cdOrderCatchDao.save(cdOrderCatch);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdOrderCatchDao.deleteById(id);
    }


    public List<CdOrderCatch> findByUid(String uid, String total, String count) {
        DetachedCriteria dc = cdOrderCatchDao.createDetachedCriteria();
        dc.add(Restrictions.eq("uid", uid));
        dc.add(Restrictions.eq(CdOrderCatch.FIELD_DEL_FLAG, CdOrderCatch.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        // 限制条数|分页
        Criteria cri = dc.getExecutableCriteria(cdOrderCatchDao.getSession());
        cri.setMaxResults(Integer.parseInt(count));
        cri.setFirstResult(Integer.parseInt(total));
        return cdOrderCatchDao.find(dc);
    }

    public CdOrderCatch findByOrderNum(String orderNum) {
        DetachedCriteria dc = cdOrderCatchDao.createDetachedCriteria();
        dc.add(Restrictions.eq("orderNum", orderNum));
        dc.add(Restrictions.eq(CdOrderCatch.FIELD_DEL_FLAG, CdOrderCatch.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        List<CdOrderCatch> list = cdOrderCatchDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

}
