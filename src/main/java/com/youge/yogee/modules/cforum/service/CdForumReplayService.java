/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cforum.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cforum.dao.CdForumReplayDao;
import com.youge.yogee.modules.cforum.entity.CdForumReplay;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 论坛回复Service
 *
 * @author ZhaoYiFeng
 * @version 2018-04-08
 */
@Component
@Transactional(readOnly = true)
public class CdForumReplayService extends BaseService {

    @Autowired
    private CdForumReplayDao cdForumReplayDao;

    public CdForumReplay get(String id) {
        return cdForumReplayDao.get(id);
    }

    public Page<CdForumReplay> find(Page<CdForumReplay> page, CdForumReplay cdForumReplay) {
        DetachedCriteria dc = cdForumReplayDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdForumReplay.getName())) {
            dc.add(Restrictions.like("name", "%" + cdForumReplay.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdForumReplay.FIELD_DEL_FLAG, CdForumReplay.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdForumReplayDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdForumReplay cdForumReplay) {

        if (StringUtils.isEmpty(cdForumReplay.getId())) {
            cdForumReplay.setId(IdGen.uuid());
            cdForumReplay.setCreateDate(DateUtils.getDateTime());
            cdForumReplay.setDelFlag(CdForumReplay.DEL_FLAG_NORMAL);
        }
        cdForumReplayDao.save(cdForumReplay);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdForumReplayDao.deleteById(id);
    }

    public List<CdForumReplay> findByFid(String fid) {
        DetachedCriteria dc = cdForumReplayDao.createDetachedCriteria();
        dc.add(Restrictions.like("fid", fid));
        dc.add(Restrictions.eq(CdForumReplay.FIELD_DEL_FLAG, CdForumReplay.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdForumReplayDao.find(dc);
    }

}
