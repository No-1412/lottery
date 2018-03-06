/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballawards.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cbasketballawards.dao.CdBasketballAwardsDao;
import com.youge.yogee.modules.cbasketballawards.entity.CdBasketballAwards;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 竞彩足球开奖信息Service
 *
 * @author RenHaipeng
 * @version 2018-01-08
 */
@Component
@Transactional(readOnly = true)
public class CdBasketballAwardsService extends BaseService {

    @Autowired
    private CdBasketballAwardsDao cdBasketballAwardsDao;

    public CdBasketballAwards get(String id) {
        return cdBasketballAwardsDao.get(id);
    }

    public Page<CdBasketballAwards> find(Page<CdBasketballAwards> page, CdBasketballAwards cdBasketballAwards) {
        DetachedCriteria dc = cdBasketballAwardsDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdBasketballAwards.getName())) {
            dc.add(Restrictions.like("name", "%" + cdBasketballAwards.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdBasketballAwards.FIELD_DEL_FLAG, CdBasketballAwards.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdBasketballAwardsDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdBasketballAwards cdBasketballAwards) {

        if (StringUtils.isEmpty(cdBasketballAwards.getId())) {
            cdBasketballAwards.setId(IdGen.uuid());
            cdBasketballAwards.setCreateDate(DateUtils.getDateTime());
            cdBasketballAwards.setDelFlag(CdBasketballAwards.DEL_FLAG_NORMAL);
        }
        cdBasketballAwardsDao.save(cdBasketballAwards);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdBasketballAwardsDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteBasketBall(String createDate) {
        cdBasketballAwardsDao.updateBySql("delete from cd_basketball_awards where create_date like '" + createDate + "%'", null);
    }

    @Transactional(readOnly = false)
    public List<CdBasketballAwards> getBallAwards(){
        DetachedCriteria dc = cdBasketballAwardsDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdBasketballAwards.FIELD_DEL_FLAG, CdBasketballAwards.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdBasketballAwardsDao.find(dc);
    }

    public List<String> getAllMatchId() {
        return cdBasketballAwardsDao.getAllMatchId();
    }

    public CdBasketballAwards findByMatchId(String matchId) {
        DetachedCriteria dc = cdBasketballAwardsDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdBasketballAwards.FIELD_DEL_FLAG, CdBasketballAwards.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("matchId", matchId));
        List<CdBasketballAwards> list =  cdBasketballAwardsDao.find(dc);
        if (list.size()!=0){
            return list.get(0);
        }else {
            return null;
        }
    }

}
