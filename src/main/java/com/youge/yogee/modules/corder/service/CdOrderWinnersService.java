/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.corder.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.corder.dao.CdOrderWinnersDao;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 中奖订单Service
 *
 * @author ZhaoYiFeng
 * @version 2018-03-14
 */
@Component
@Transactional(readOnly = true)
public class CdOrderWinnersService extends BaseService {

    @Autowired
    private CdOrderWinnersDao cdOrderWinnersDao;

    public CdOrderWinners get(String id) {
        return cdOrderWinnersDao.get(id);
    }

    public Page<CdOrderWinners> find(Page<CdOrderWinners> page, CdOrderWinners cdOrderWinners) {
        DetachedCriteria dc = cdOrderWinnersDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdOrderWinners.getName())) {
            dc.add(Restrictions.eq("winOrderNum", cdOrderWinners.getWinOrderNum()));
        }
        dc.add(Restrictions.eq(CdOrderWinners.FIELD_DEL_FLAG, CdOrderWinners.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdOrderWinnersDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdOrderWinners cdOrderWinners) {

        if (StringUtils.isEmpty(cdOrderWinners.getId())) {
            cdOrderWinners.setId(IdGen.uuid());
            cdOrderWinners.setCreateDate(DateUtils.getDateTime());
            cdOrderWinners.setDelFlag(CdOrderWinners.DEL_FLAG_NORMAL);
        }
        cdOrderWinnersDao.save(cdOrderWinners);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdOrderWinnersDao.deleteById(id);
    }

}
