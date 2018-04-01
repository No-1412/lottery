/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbalreadyfinish.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfbalreadyfinish.dao.CdFbAlreadyFinishDao;
import com.youge.yogee.modules.cfbalreadyfinish.entity.CdFbAlreadyFinish;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 足球已经完赛信息Service
 *
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Component
@Transactional(readOnly = true)
public class CdFbAlreadyFinishService extends BaseService {

    @Autowired
    private CdFbAlreadyFinishDao cdFbAlreadyFinishDao;

    public CdFbAlreadyFinish get(String id) {
        return cdFbAlreadyFinishDao.get(id);
    }

    public Page<CdFbAlreadyFinish> find(Page<CdFbAlreadyFinish> page, CdFbAlreadyFinish cdFbAlreadyFinish) {
        DetachedCriteria dc = cdFbAlreadyFinishDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdFbAlreadyFinish.getName())) {
            dc.add(Restrictions.like("name", "%" + cdFbAlreadyFinish.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdFbAlreadyFinish.FIELD_DEL_FLAG, CdFbAlreadyFinish.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdFbAlreadyFinishDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdFbAlreadyFinish cdFbAlreadyFinish) {

        if (StringUtils.isEmpty(cdFbAlreadyFinish.getId())) {
            cdFbAlreadyFinish.setId(IdGen.uuid());
            cdFbAlreadyFinish.setCreateDate(DateUtils.getDateTime());
            cdFbAlreadyFinish.setDelFlag(CdFbAlreadyFinish.DEL_FLAG_NORMAL);
        }
        cdFbAlreadyFinishDao.save(cdFbAlreadyFinish);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdFbAlreadyFinishDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteAlreadyFinsh() {
        cdFbAlreadyFinishDao.update("delete from CdFbAlreadyFinish");
    }

    @Transactional(readOnly = false)
    public List<CdFbAlreadyFinish> getAlreadyFinsh(String total, String count) {
        DetachedCriteria dc = cdFbAlreadyFinishDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFbAlreadyFinish.FIELD_DEL_FLAG, CdFbAlreadyFinish.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("time"));
        dc.addOrder(Order.asc("jn"));
        // 限制条数|分页
        Criteria cri = dc.getExecutableCriteria(cdFbAlreadyFinishDao.getSession());
        cri.setMaxResults(Integer.parseInt(count));
        cri.setFirstResult(Integer.parseInt(total));
        return cdFbAlreadyFinishDao.find(dc);
    }

}
