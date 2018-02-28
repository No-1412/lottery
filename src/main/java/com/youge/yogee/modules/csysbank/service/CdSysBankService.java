/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csysbank.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.modules.csysbank.entity.CdSysBank;
import com.youge.yogee.modules.csysbank.dao.CdSysBankDao;

import java.util.List;

/**
 * 系统银行卡Service
 *
 * @author WeiJinChao
 * @version 2017-12-14
 */
@Component
@Transactional(readOnly = true)
public class CdSysBankService extends BaseService {

    @Autowired
    private CdSysBankDao cdSysBankDao;

    public CdSysBank get(String id) {
        return cdSysBankDao.get(id);
    }

    public Page<CdSysBank> find(Page<CdSysBank> page, CdSysBank cdSysBank) {
        DetachedCriteria dc = cdSysBankDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdSysBank.getName())) {
            dc.add(Restrictions.like("name", "%" + cdSysBank.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdSysBank.FIELD_DEL_FLAG, CdSysBank.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdSysBankDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdSysBank cdSysBank) {

        if (StringUtils.isEmpty(cdSysBank.getId())) {
            cdSysBank.setId(IdGen.uuid());
            cdSysBank.setCreateDate(DateUtils.getDateTime());
            cdSysBank.setDelFlag(CdSysBank.DEL_FLAG_NORMAL);
        }
        cdSysBankDao.save(cdSysBank);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdSysBankDao.deleteById(id);
    }

    public List<CdSysBank> getSysBankList() {
        DetachedCriteria dc = cdSysBankDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdSysBank.FIELD_DEL_FLAG, CdSysBank.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdSysBankDao.find(dc);
    }
}
