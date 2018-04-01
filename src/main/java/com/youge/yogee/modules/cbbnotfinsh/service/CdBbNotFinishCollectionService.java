/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbnotfinsh.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cbbnotfinsh.dao.CdBbNotFinishCollectionDao;
import com.youge.yogee.modules.cbbnotfinsh.entity.CdBbNotFinishCollection;
import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinishCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 篮球比赛收藏Service
 *
 * @author ZhaoYiFeng
 * @version 2018-03-19
 */
@Component
@Transactional(readOnly = true)
public class CdBbNotFinishCollectionService extends BaseService {

    @Autowired
    private CdBbNotFinishCollectionDao cdBbNotFinishCollectionDao;

    public CdBbNotFinishCollection get(String id) {
        return cdBbNotFinishCollectionDao.get(id);
    }

    public Page<CdBbNotFinishCollection> find(Page<CdBbNotFinishCollection> page, CdBbNotFinishCollection cdBbNotFinishCollection) {
        DetachedCriteria dc = cdBbNotFinishCollectionDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdBbNotFinishCollection.getName())) {
            dc.add(Restrictions.like("name", "%" + cdBbNotFinishCollection.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdBbNotFinishCollection.FIELD_DEL_FLAG, CdBbNotFinishCollection.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdBbNotFinishCollectionDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdBbNotFinishCollection cdBbNotFinishCollection) {

        if (StringUtils.isEmpty(cdBbNotFinishCollection.getId())) {
            cdBbNotFinishCollection.setId(IdGen.uuid());
            cdBbNotFinishCollection.setCreateDate(DateUtils.getDateTime());
            cdBbNotFinishCollection.setDelFlag(CdBbNotFinishCollection.DEL_FLAG_NORMAL);
        }
        cdBbNotFinishCollectionDao.save(cdBbNotFinishCollection);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdBbNotFinishCollectionDao.deleteById(id);
    }

    public CdBbNotFinishCollection findByMatIdAndUid(String zid, String uid) {
        DetachedCriteria dc = cdBbNotFinishCollectionDao.createDetachedCriteria();
        dc.add(Restrictions.eq("zid", zid));
        dc.add(Restrictions.eq("uid", uid));
        dc.add(Restrictions.eq(CdFbNotFinishCollection.FIELD_DEL_FLAG, CdFbNotFinishCollection.DEL_FLAG_NORMAL));
        List<CdBbNotFinishCollection> list = cdBbNotFinishCollectionDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

}
