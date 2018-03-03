/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballawards.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfootballawards.dao.CdFootballAwardsDao;
import com.youge.yogee.modules.cfootballawards.entity.CdFootballAwards;
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
 * @version 2018-01-09
 */
@Component
@Transactional(readOnly = true)
public class CdFootballAwardsService extends BaseService {

    @Autowired
    private CdFootballAwardsDao cdFootballAwardsDao;

    public CdFootballAwards get(String id) {
        return cdFootballAwardsDao.get(id);
    }

    public Page<CdFootballAwards> find(Page<CdFootballAwards> page, CdFootballAwards cdFootballAwards) {
        DetachedCriteria dc = cdFootballAwardsDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdFootballAwards.getName())) {
            dc.add(Restrictions.like("name", "%" + cdFootballAwards.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdFootballAwards.FIELD_DEL_FLAG, CdFootballAwards.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdFootballAwardsDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdFootballAwards cdFootballAwards) {

        if (StringUtils.isEmpty(cdFootballAwards.getId())) {
            cdFootballAwards.setId(IdGen.uuid());
            cdFootballAwards.setCreateDate(DateUtils.getDateTime());
            cdFootballAwards.setDelFlag(CdFootballAwards.DEL_FLAG_NORMAL);
        }
        cdFootballAwardsDao.save(cdFootballAwards);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdFootballAwardsDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteFootBallAwards() {
        cdFootballAwardsDao.update("delete from CdFootballAwards");
    }

    @Transactional(readOnly = false)
    public List<CdFootballAwards> getFootBallAwards() {
        DetachedCriteria dc = cdFootballAwardsDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFootballAwards.FIELD_DEL_FLAG, CdFootballAwards.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdFootballAwardsDao.find(dc);
    }

    public CdFootballAwards findByMatchId(String matchId) {
        DetachedCriteria dc = cdFootballAwardsDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFootballAwards.FIELD_DEL_FLAG, CdFootballAwards.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("matchId", matchId));
        List<CdFootballAwards> list =  cdFootballAwardsDao.find(dc);
        if (list.size()!=0){
            return list.get(0);
        }else {
            return null;
        }
    }

    public List<String> getAllMatchId() {
        return cdFootballAwardsDao.getAllMatchId();
    }
}
