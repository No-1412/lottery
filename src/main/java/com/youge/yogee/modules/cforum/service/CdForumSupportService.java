/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cforum.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cforum.dao.CdForumSupportDao;
import com.youge.yogee.modules.cforum.entity.CdForumSupport;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 论坛点赞Service
 *
 * @author ZhaoYiFeng
 * @version 2018-04-08
 */
@Component
@Transactional(readOnly = true)
public class CdForumSupportService extends BaseService {

    @Autowired
    private CdForumSupportDao cdForumSupportDao;

    public CdForumSupport get(String id) {
        return cdForumSupportDao.get(id);
    }

    public Page<CdForumSupport> find(Page<CdForumSupport> page, CdForumSupport cdForumSupport) {
        DetachedCriteria dc = cdForumSupportDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdForumSupport.getName())) {
            dc.add(Restrictions.like("name", "%" + cdForumSupport.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdForumSupport.FIELD_DEL_FLAG, CdForumSupport.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdForumSupportDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdForumSupport cdForumSupport) {

        if (StringUtils.isEmpty(cdForumSupport.getId())) {
            cdForumSupport.setId(IdGen.uuid());
            cdForumSupport.setCreateDate(DateUtils.getDateTime());
            cdForumSupport.setDelFlag(CdForumSupport.DEL_FLAG_NORMAL);
        }
        cdForumSupportDao.save(cdForumSupport);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdForumSupportDao.deleteById(id);
    }


    public CdForumSupport findByFidAndUid(String fid, String uid) {
        DetachedCriteria dc = cdForumSupportDao.createDetachedCriteria();
        dc.add(Restrictions.eq("fid", fid));
        dc.add(Restrictions.eq("uid", uid));
        dc.add(Restrictions.eq(CdForumSupport.FIELD_DEL_FLAG, CdForumSupport.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        List<CdForumSupport> list = cdForumSupportDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

}
