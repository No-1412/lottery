/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbnotfinish.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfbnotfinish.dao.CdFbNotFinishCollectionDao;
import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinish;
import com.youge.yogee.modules.cfbnotfinish.entity.CdFbNotFinishCollection;
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
public class CdFbNotFinishCollectionService extends BaseService {

    @Autowired
    private CdFbNotFinishCollectionDao cdFbNotFinishCollectionDao;

    public CdFbNotFinishCollection get(String id) {
        return cdFbNotFinishCollectionDao.get(id);
    }

    public Page<CdFbNotFinishCollection> find(Page<CdFbNotFinishCollection> page, CdFbNotFinishCollection cdFbNotFinishCollection) {
        DetachedCriteria dc = cdFbNotFinishCollectionDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdFbNotFinishCollection.getName())) {
            dc.add(Restrictions.like("name", "%" + cdFbNotFinishCollection.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdFbNotFinishCollection.FIELD_DEL_FLAG, CdFbNotFinishCollection.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdFbNotFinishCollectionDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdFbNotFinishCollection cdFbNotFinishCollection) {

        if (StringUtils.isEmpty(cdFbNotFinishCollection.getId())) {
            cdFbNotFinishCollection.setId(IdGen.uuid());
            cdFbNotFinishCollection.setCreateDate(DateUtils.getDateTime());
            cdFbNotFinishCollection.setDelFlag(CdFbNotFinishCollection.DEL_FLAG_NORMAL);
        }
        cdFbNotFinishCollectionDao.save(cdFbNotFinishCollection);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdFbNotFinishCollectionDao.deleteById(id);
    }


    public CdFbNotFinishCollection findByMatIdAndUid(String sort, String uid) {
        DetachedCriteria dc = cdFbNotFinishCollectionDao.createDetachedCriteria();

        dc.add(Restrictions.eq("sort", sort));
        dc.add(Restrictions.eq("uid", uid));

        dc.add(Restrictions.eq(CdFbNotFinishCollection.FIELD_DEL_FLAG, CdFbNotFinishCollection.DEL_FLAG_NORMAL));
        List<CdFbNotFinishCollection> list = cdFbNotFinishCollectionDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<CdFbNotFinish> findColByUid(String uid) {
        return cdFbNotFinishCollectionDao.findBySql("SELECT * FROM cd_fb_notfinish_collection a LEFT JOIN cd_fb_notfinish b ON a.sort=b.sort WHERE uid=:p1 AND b.type !='4' AND a.del_flag=0", new Parameter(uid), CdFbNotFinish.class);
    }

}
