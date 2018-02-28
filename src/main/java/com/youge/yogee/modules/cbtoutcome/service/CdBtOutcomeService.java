/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbtoutcome.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cbtoutcome.dao.CdBtOutcomeDao;
import com.youge.yogee.modules.cbtoutcome.entity.CdBtOutcome;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 竞彩篮球近期赛事Service
 * @author RenHaipeng
 * @version 2018-01-18
 */
@Component
@Transactional(readOnly = true)
public class CdBtOutcomeService extends BaseService {

	@Autowired
	private CdBtOutcomeDao cdBtOutcomeDao;
	
	public CdBtOutcome get(String id) {
		return cdBtOutcomeDao.get(id);
	}
	
	public Page<CdBtOutcome> find(Page<CdBtOutcome> page, CdBtOutcome cdBtOutcome) {
		DetachedCriteria dc = cdBtOutcomeDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdBtOutcome.getName())){
			dc.add(Restrictions.like("name", "%" + cdBtOutcome.getName() + "%"));
		}
		dc.add(Restrictions.eq(CdBtOutcome.FIELD_DEL_FLAG, CdBtOutcome.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdBtOutcomeDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdBtOutcome cdBtOutcome) {

		if(StringUtils.isEmpty(cdBtOutcome.getId())){
			cdBtOutcome.setId(IdGen.uuid());
			cdBtOutcome.setCreateDate(DateUtils.getDateTime());
			cdBtOutcome.setDelFlag(CdBtOutcome.DEL_FLAG_NORMAL);
		}
		cdBtOutcomeDao.save(cdBtOutcome);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdBtOutcomeDao.deleteById(id);
	}

	/**
	 * wangsong
	 * 20180105
	 * 清空表
	 */
	@Transactional(readOnly = false)
	public void deleteAll(String name) {
		cdBtOutcomeDao.update("delete from " + name);
	}

	/**
	 * wangsong
	 * 20180105
	 * 查询篮球近期战绩5场
	 */
	@Transactional(readOnly = false)
	public List<CdBtOutcome> findById(String itemid, String name,String type){
		DetachedCriteria dc = cdBtOutcomeDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdBtOutcome.FIELD_DEL_FLAG, CdBtOutcome.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("matchId", itemid));
		dc.add(Restrictions.eq("outcomeType", type));//'战绩类型 0近期1历史'
		dc.add(Restrictions.or( Restrictions.eq("hn", name), Restrictions.eq("gn", name) ));
		dc.addOrder(Order.desc("mt"));
		Criteria cri = dc.getExecutableCriteria(cdBtOutcomeDao.getSession());
		cri.setMaxResults(5);
		return cdBtOutcomeDao.find(dc);
	}




}
