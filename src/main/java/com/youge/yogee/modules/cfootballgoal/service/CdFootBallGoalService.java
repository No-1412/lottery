/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballgoal.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfootballgoal.dao.CdFootBallGoalDao;
import com.youge.yogee.modules.cfootballgoal.entity.CdFootBallGoal;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 足球进球彩Service
 *
 * @author RenHaipeng
 * @version 2018-01-04
 */
@Component
@Transactional(readOnly = true)
public class CdFootBallGoalService extends BaseService {

    @Autowired
    private CdFootBallGoalDao cdFootBallGoalDao;

    public CdFootBallGoal get(String id) {
        return cdFootBallGoalDao.get(id);
    }

    public Page<CdFootBallGoal> find(Page<CdFootBallGoal> page, CdFootBallGoal cdFootBallGoal) {
        DetachedCriteria dc = cdFootBallGoalDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdFootBallGoal.getName())) {
            dc.add(Restrictions.like("name", "%" + cdFootBallGoal.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdFootBallGoal.FIELD_DEL_FLAG, CdFootBallGoal.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdFootBallGoalDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdFootBallGoal cdFootBallGoal) {

        if (StringUtils.isEmpty(cdFootBallGoal.getId())) {
            cdFootBallGoal.setId(IdGen.uuid());
            cdFootBallGoal.setCreateDate(DateUtils.getDateTime());
            cdFootBallGoal.setDelFlag(CdFootBallGoal.DEL_FLAG_NORMAL);
        }
        cdFootBallGoalDao.save(cdFootBallGoal);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdFootBallGoalDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteFootBallGoal() {
        cdFootBallGoalDao.update("delete from CdFootBallGoal");
    }

    @Transactional(readOnly = false)
    public List<CdFootBallGoal> getfootGoal(){
        DetachedCriteria dc = cdFootBallGoalDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFootBallGoal.FIELD_DEL_FLAG, CdFootBallGoal.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdFootBallGoalDao.find(dc);
    }
}
