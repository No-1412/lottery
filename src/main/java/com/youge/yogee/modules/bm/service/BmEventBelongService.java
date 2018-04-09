/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.bm.dao.BmEventBelongDao;
import com.youge.yogee.modules.bm.entity.BmEventBelong;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 赛事洲事Service
 *
 * @author ZhaoYiFeng
 * @version 2018-04-03
 */
@Component
@Transactional(readOnly = true)
public class BmEventBelongService extends BaseService {

    @Autowired
    private BmEventBelongDao bmEventBelongDao;

    public BmEventBelong get(String id) {
        return bmEventBelongDao.get(id);
    }

    public Page<BmEventBelong> find(Page<BmEventBelong> page, BmEventBelong bmEventBelong) {
        DetachedCriteria dc = bmEventBelongDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(bmEventBelong.getName())) {
            dc.add(Restrictions.like("name", "%" + bmEventBelong.getName() + "%"));
        }
        dc.add(Restrictions.eq(BmEventBelong.FIELD_DEL_FLAG, BmEventBelong.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return bmEventBelongDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(BmEventBelong bmEventBelong) {

        if (StringUtils.isEmpty(bmEventBelong.getId())) {
            bmEventBelong.setId(IdGen.uuid());
            bmEventBelong.setCreateDate(DateUtils.getDateTime());
            bmEventBelong.setDelFlag(BmEventBelong.DEL_FLAG_NORMAL);
        }
        bmEventBelongDao.save(bmEventBelong);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        bmEventBelongDao.deleteById(id);
    }

    public BmEventBelong findByEventName(String eventName) {
        DetachedCriteria dc = bmEventBelongDao.createDetachedCriteria();
        dc.add(Restrictions.like("eventName", eventName));
        //dc.add(Restrictions.eq(BmEventBelong.FIELD_DEL_FLAG, BmEventBelong.DEL_FLAG_NORMAL));
        //dc.addOrder(Order.desc("id"));
        List<BmEventBelong> list = bmEventBelongDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }


}
