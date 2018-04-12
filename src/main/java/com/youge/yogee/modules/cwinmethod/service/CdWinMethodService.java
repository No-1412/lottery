/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cwinmethod.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cwinmethod.dao.CdWinMethodDao;
import com.youge.yogee.modules.cwinmethod.entity.CdWinMethod;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 中奖攻略Service
 *
 * @author WeiJinChao
 * @version 2017-12-21
 */
@Component
@Transactional(readOnly = true)
public class CdWinMethodService extends BaseService {

    @Autowired
    private CdWinMethodDao cdWinMethodDao;

    public CdWinMethod get(String id) {
        return cdWinMethodDao.get(id);
    }

    public Page<CdWinMethod> find(Page<CdWinMethod> page, CdWinMethod cdWinMethod) {
        DetachedCriteria dc = cdWinMethodDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdWinMethod.getName())) {
            dc.add(Restrictions.like("name", "%" + cdWinMethod.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdWinMethod.FIELD_DEL_FLAG, CdWinMethod.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdWinMethodDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdWinMethod cdWinMethod) {

        if (StringUtils.isEmpty(cdWinMethod.getId())) {
            cdWinMethod.setId(IdGen.uuid());
            cdWinMethod.setCreateDate(DateUtils.getDateTime());
            cdWinMethod.setDelFlag(CdWinMethod.DEL_FLAG_NORMAL);
        }
        cdWinMethodDao.save(cdWinMethod);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdWinMethodDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public List<CdWinMethod> getMethodList(String total, String count) {
        DetachedCriteria dc = cdWinMethodDao.createDetachedCriteria();

        dc.add(Restrictions.eq(CdWinMethod.FIELD_DEL_FLAG, CdWinMethod.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        Criteria cri = dc.getExecutableCriteria(cdWinMethodDao.getSession());
        cri.setMaxResults(Integer.parseInt(count));
        cri.setFirstResult(Integer.parseInt(total));
        return cdWinMethodDao.find(dc);
    }

}
