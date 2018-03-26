/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.crecord.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.crecord.dao.CdRecordCashDao;
import com.youge.yogee.modules.crecord.entity.CdRecordCash;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 提现记录Service
 *
 * @author ZhaoYiFeng
 * @version 2018-03-26
 */
@Component
@Transactional(readOnly = true)
public class CdRecordCashService extends BaseService {

    @Autowired
    private CdRecordCashDao cdRecordCashDao;

    public CdRecordCash get(String id) {
        return cdRecordCashDao.get(id);
    }

    public Page<CdRecordCash> find(Page<CdRecordCash> page, CdRecordCash cdRecordCash) {
        DetachedCriteria dc = cdRecordCashDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdRecordCash.getOrderNum())) {
            dc.add(Restrictions.like("orderNum", cdRecordCash.getOrderNum()));
        }
        dc.add(Restrictions.eq(CdRecordCash.FIELD_DEL_FLAG, CdRecordCash.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdRecordCashDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdRecordCash cdRecordCash) {

        if (StringUtils.isEmpty(cdRecordCash.getId())) {
            cdRecordCash.setId(IdGen.uuid());
            cdRecordCash.setCreateDate(DateUtils.getDateTime());
            cdRecordCash.setDelFlag(CdRecordCash.DEL_FLAG_NORMAL);
        }
        cdRecordCashDao.save(cdRecordCash);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdRecordCashDao.deleteById(id);
    }

    public List<CdRecordCash> findByUid(String uid, String total, String count) {
        DetachedCriteria dc = cdRecordCashDao.createDetachedCriteria();
        dc.add(Restrictions.eq("uid", uid));
        dc.add(Restrictions.eq(CdRecordCash.FIELD_DEL_FLAG, CdRecordCash.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));

        Criteria cri = dc.getExecutableCriteria(cdRecordCashDao.getSession());
        cri.setFirstResult(Integer.parseInt(total));
        cri.setMaxResults(Integer.parseInt(count));
        return cdRecordCashDao.find(dc);
    }
}
