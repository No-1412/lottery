/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.ccolorreward.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.ccolorreward.dao.CdColorRewardDao;
import com.youge.yogee.modules.ccolorreward.entity.CdColorReward;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 胜负彩开奖结果Service
 *
 * @author RenHaipeng
 * @version 2018-01-05
 */
@Component
@Transactional(readOnly = true)
public class CdColorRewardService extends BaseService {

    @Autowired
    private CdColorRewardDao cdColorRewardDao;

    public CdColorReward get(String id) {
        return cdColorRewardDao.get(id);
    }

    public Page<CdColorReward> find(Page<CdColorReward> page, CdColorReward cdColorReward) {
        DetachedCriteria dc = cdColorRewardDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdColorReward.getName())) {
            dc.add(Restrictions.like("name", "%" + cdColorReward.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdColorReward.FIELD_DEL_FLAG, CdColorReward.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdColorRewardDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdColorReward cdColorReward) {

        if (StringUtils.isEmpty(cdColorReward.getId())) {
            cdColorReward.setId(IdGen.uuid());
            cdColorReward.setCreateDate(DateUtils.getDateTime());
            cdColorReward.setDelFlag(CdColorReward.DEL_FLAG_NORMAL);
        }
        cdColorRewardDao.save(cdColorReward);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdColorRewardDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteColorReward() {
        cdColorRewardDao.update("delete from CdColorReward");
    }
    @Transactional(readOnly = false)
    public List<CdColorReward> getColorReward(){
        DetachedCriteria dc = cdColorRewardDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdColorReward.FIELD_DEL_FLAG, CdColorReward.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdColorRewardDao.find(dc);
    }

    public CdColorReward findByWeekday(String weekday){
        DetachedCriteria dc = cdColorRewardDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdColorReward.FIELD_DEL_FLAG, CdColorReward.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("matchId", "第"+weekday+"期"));
        dc.addOrder(Order.desc("createDate"));
        List<CdColorReward> list =  cdColorRewardDao.find(dc);
        if(list.size() == 0){
            return null;
        }else {
            return list.get(0);
        }
    }
}
