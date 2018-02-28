/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cdoubleresult.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cdoubleresult.dao.CdDoubleResultDao;
import com.youge.yogee.modules.cdoubleresult.entity.CdDoubleResult;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 足球半全场开奖信息Service
 *
 * @author RenHaipeng
 * @version 2018-01-08
 */
@Component
@Transactional(readOnly = true)
public class CdDoubleResultService extends BaseService {

    @Autowired
    private CdDoubleResultDao cdDoubleResultDao;

    public CdDoubleResult get(String id) {
        return cdDoubleResultDao.get(id);
    }

    public Page<CdDoubleResult> find(Page<CdDoubleResult> page, CdDoubleResult cdDoubleResult) {
        DetachedCriteria dc = cdDoubleResultDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdDoubleResult.getName())) {
            dc.add(Restrictions.like("name", "%" + cdDoubleResult.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdDoubleResult.FIELD_DEL_FLAG, CdDoubleResult.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdDoubleResultDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdDoubleResult cdDoubleResult) {

        if (StringUtils.isEmpty(cdDoubleResult.getId())) {
            cdDoubleResult.setId(IdGen.uuid());
            cdDoubleResult.setCreateDate(DateUtils.getDateTime());
            cdDoubleResult.setDelFlag(CdDoubleResult.DEL_FLAG_NORMAL);
        }
        cdDoubleResultDao.save(cdDoubleResult);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdDoubleResultDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteDoubleResult() {
        cdDoubleResultDao.update("delete from CdDoubleResult");
    }

    @Transactional(readOnly = false)
    public List<CdDoubleResult> getDoubleResult(){
        DetachedCriteria dc = cdDoubleResultDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdDoubleResult.FIELD_DEL_FLAG, CdDoubleResult.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdDoubleResultDao.find(dc);
    }
}
