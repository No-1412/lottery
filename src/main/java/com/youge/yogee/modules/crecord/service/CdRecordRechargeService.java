/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.crecord.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.crecord.dao.CdRecordRechargeDao;
import com.youge.yogee.modules.crecord.entity.CdRecordRecharge;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 充值记录Service
 *
 * @author ZhaoYiFeng
 * @version 2018-03-23
 */
@Component
@Transactional(readOnly = true)
public class CdRecordRechargeService extends BaseService {

    @Autowired
    private CdRecordRechargeDao cdRecordRechargeDao;

    public CdRecordRecharge get(String id) {
        return cdRecordRechargeDao.get(id);
    }

    public Page<CdRecordRecharge> find(Page<CdRecordRecharge> page, CdRecordRecharge cdRecordRecharge) {
        DetachedCriteria dc = cdRecordRechargeDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdRecordRecharge.getName())) {
            dc.add(Restrictions.like("name", "%" + cdRecordRecharge.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdRecordRecharge.FIELD_DEL_FLAG, CdRecordRecharge.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdRecordRechargeDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdRecordRecharge cdRecordRecharge) {

        if (StringUtils.isEmpty(cdRecordRecharge.getId())) {
            cdRecordRecharge.setId(IdGen.uuid());
            cdRecordRecharge.setCreateDate(DateUtils.getDateTime());
            cdRecordRecharge.setDelFlag(CdRecordRecharge.DEL_FLAG_NORMAL);
        }
        cdRecordRechargeDao.save(cdRecordRecharge);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdRecordRechargeDao.deleteById(id);
    }


    public CdRecordRecharge findByOrderNum(String orderNum) {
        DetachedCriteria dc = cdRecordRechargeDao.createDetachedCriteria();
        dc.add(Restrictions.eq("orderNum", orderNum));
        dc.add(Restrictions.eq(CdRecordRecharge.FIELD_DEL_FLAG, CdRecordRecharge.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        List<CdRecordRecharge> list = cdRecordRechargeDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

}
