/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballgoalresult.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfootballgoalresult.dao.CdFootballGoalResultDao;
import com.youge.yogee.modules.cfootballgoalresult.entity.CdFootballGoalResult;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 足球进球彩开奖信息Service
 * @author RenHaipeng
 * @version 2018-01-10
 */
@Component
@Transactional(readOnly = true)
public class CdFootballGoalResultService extends BaseService {

	@Autowired
	private CdFootballGoalResultDao cdFootballGoalResultDao;
	
	public CdFootballGoalResult get(String id) {
		return cdFootballGoalResultDao.get(id);
	}
	
	public Page<CdFootballGoalResult> find(Page<CdFootballGoalResult> page, CdFootballGoalResult cdFootballGoalResult) {
		DetachedCriteria dc = cdFootballGoalResultDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdFootballGoalResult.getName())){
			dc.add(Restrictions.like("name", "%" + cdFootballGoalResult.getName() + "%"));
		}
		dc.add(Restrictions.eq(CdFootballGoalResult.FIELD_DEL_FLAG, CdFootballGoalResult.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdFootballGoalResultDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdFootballGoalResult cdFootballGoalResult) {

		if(StringUtils.isEmpty(cdFootballGoalResult.getId())){
			cdFootballGoalResult.setId(IdGen.uuid());
			cdFootballGoalResult.setCreateDate(DateUtils.getDateTime());
			cdFootballGoalResult.setDelFlag(CdFootballGoalResult.DEL_FLAG_NORMAL);
		}
		cdFootballGoalResultDao.save(cdFootballGoalResult);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdFootballGoalResultDao.deleteById(id);
	}

	@Transactional(readOnly = false)
	public void deleteGoalResult() {
		cdFootballGoalResultDao.update("delete from CdFootballGoalResult");
	}
	@Transactional(readOnly = false)
	public List<CdFootballGoalResult> getGoalResultAward(){
		DetachedCriteria dc = cdFootballGoalResultDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdFootballGoalResult.FIELD_DEL_FLAG, CdFootballGoalResult.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdFootballGoalResultDao.find(dc);
	}
}
