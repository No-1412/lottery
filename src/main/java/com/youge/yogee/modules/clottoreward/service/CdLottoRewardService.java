/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.clottoreward.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.clottoreward.dao.CdLottoRewardDao;
import com.youge.yogee.modules.clottoreward.entity.CdLottoReward;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 大乐透开奖结果Service
 *
 * @author RenHaipeng
 * @version 2018-01-05
 */
@Component
@Transactional(readOnly = true)
public class CdLottoRewardService extends BaseService {

    @Autowired
    private CdLottoRewardDao cdLottoRewardDao;

    public CdLottoReward get(String id) {
        return cdLottoRewardDao.get(id);
    }

    public Page<CdLottoReward> find(Page<CdLottoReward> page, CdLottoReward cdLottoReward) {
        DetachedCriteria dc = cdLottoRewardDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdLottoReward.getName())) {
            dc.add(Restrictions.like("name", "%" + cdLottoReward.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdLottoReward.FIELD_DEL_FLAG, CdLottoReward.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdLottoRewardDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdLottoReward cdLottoReward) {

        if (StringUtils.isEmpty(cdLottoReward.getId())) {
            cdLottoReward.setId(IdGen.uuid());
            cdLottoReward.setCreateDate(DateUtils.getDateTime());
            cdLottoReward.setDelFlag(CdLottoReward.DEL_FLAG_NORMAL);
        }
        cdLottoRewardDao.save(cdLottoReward);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdLottoRewardDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteLottoReward() {
        cdLottoRewardDao.update("delete from CdLottoReward");
    }

    @Transactional(readOnly = false)
    public List<CdLottoReward> getLottoReward() {
        DetachedCriteria dc = cdLottoRewardDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdLottoReward.FIELD_DEL_FLAG, CdLottoReward.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("matchId"));
        return cdLottoRewardDao.find(dc);
    }
}
