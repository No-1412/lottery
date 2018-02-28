/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csceneecharts.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.csceneecharts.dao.CdSceneEchartsDao;
import com.youge.yogee.modules.csceneecharts.entity.CdSceneEcharts;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 足球实况ecahrts图表Service
 *
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Component
@Transactional(readOnly = true)
public class CdSceneEchartsService extends BaseService {

    @Autowired
    private CdSceneEchartsDao cdSceneEchartsDao;

    public CdSceneEcharts get(String id) {
        return cdSceneEchartsDao.get(id);
    }

    public Page<CdSceneEcharts> find(Page<CdSceneEcharts> page, CdSceneEcharts cdSceneEcharts) {
        DetachedCriteria dc = cdSceneEchartsDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdSceneEcharts.getName())) {
            dc.add(Restrictions.like("name", "%" + cdSceneEcharts.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdSceneEcharts.FIELD_DEL_FLAG, CdSceneEcharts.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdSceneEchartsDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdSceneEcharts cdSceneEcharts) {

        if (StringUtils.isEmpty(cdSceneEcharts.getId())) {
            cdSceneEcharts.setId(IdGen.uuid());
            cdSceneEcharts.setCreateDate(DateUtils.getDateTime());
            cdSceneEcharts.setDelFlag(CdSceneEcharts.DEL_FLAG_NORMAL);
        }
        cdSceneEchartsDao.save(cdSceneEcharts);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdSceneEchartsDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteSceneEcharts() {
        cdSceneEchartsDao.update("delete from CdSceneEcharts");
    }

    @Transactional(readOnly = false)
    public List<CdSceneEcharts> getEcharts(String itemId){
        DetachedCriteria dc = cdSceneEchartsDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdSceneEcharts.FIELD_DEL_FLAG, CdSceneEcharts.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("itemId", itemId));
        dc.addOrder(Order.desc("createDate"));
        return cdSceneEchartsDao.find(dc);
    }
}
