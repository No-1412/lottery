/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbfinshed.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfbfinshed.dao.CdFbFinishedCollectionDao;
import com.youge.yogee.modules.cfbfinshed.entity.CdFbFinishedCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 足球比赛收藏Service
 *
 * @author ZhaoYiFeng
 * @version 2018-03-19
 */
@Component
@Transactional(readOnly = true)
public class CdFbFinishedCollectionService extends BaseService {

    @Autowired
    private CdFbFinishedCollectionDao cdFbFinishedCollectionDao;

    public CdFbFinishedCollection get(String id) {
        return cdFbFinishedCollectionDao.get(id);
    }

    public Page<CdFbFinishedCollection> find(Page<CdFbFinishedCollection> page, CdFbFinishedCollection cdFbFinishedCollection) {
        DetachedCriteria dc = cdFbFinishedCollectionDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdFbFinishedCollection.getName())) {
            dc.add(Restrictions.like("name", "%" + cdFbFinishedCollection.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdFbFinishedCollection.FIELD_DEL_FLAG, CdFbFinishedCollection.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdFbFinishedCollectionDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdFbFinishedCollection cdFbFinishedCollection) {

        if (StringUtils.isEmpty(cdFbFinishedCollection.getId())) {
            cdFbFinishedCollection.setId(IdGen.uuid());
            cdFbFinishedCollection.setCreateDate(DateUtils.getDateTime());
            cdFbFinishedCollection.setDelFlag(CdFbFinishedCollection.DEL_FLAG_NORMAL);
        }
        cdFbFinishedCollectionDao.save(cdFbFinishedCollection);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdFbFinishedCollectionDao.deleteById(id);
    }


    public CdFbFinishedCollection findByMatIdAndUid(String sort, String uid) {
        DetachedCriteria dc = cdFbFinishedCollectionDao.createDetachedCriteria();

        dc.add(Restrictions.eq("sort", sort));
        dc.add(Restrictions.eq("uid", uid));

        dc.add(Restrictions.eq(CdFbFinishedCollection.FIELD_DEL_FLAG, CdFbFinishedCollection.DEL_FLAG_NORMAL));
        List<CdFbFinishedCollection> list = cdFbFinishedCollectionDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
