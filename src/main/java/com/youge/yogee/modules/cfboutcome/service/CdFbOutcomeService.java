/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfboutcome.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfboutcome.dao.CdFbOutcomeDao;
import com.youge.yogee.modules.cfboutcome.entity.CdFbOutcome;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 竞彩足球详情近期赛事Service
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Component
@Transactional(readOnly = true)
public class CdFbOutcomeService extends BaseService {

	@Autowired
	private CdFbOutcomeDao cdFbOutcomeDao;
	
	public CdFbOutcome get(String id) {
		return cdFbOutcomeDao.get(id);
	}
	
	public Page<CdFbOutcome> find(Page<CdFbOutcome> page, CdFbOutcome cdFbOutcome) {
		DetachedCriteria dc = cdFbOutcomeDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdFbOutcome.getName())){
			dc.add(Restrictions.like("name", "%" + cdFbOutcome.getName() + "%"));
		}
		dc.add(Restrictions.eq(CdFbOutcome.FIELD_DEL_FLAG, CdFbOutcome.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdFbOutcomeDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdFbOutcome cdFbOutcome) {

		if(StringUtils.isEmpty(cdFbOutcome.getId())){
			cdFbOutcome.setId(IdGen.uuid());
			cdFbOutcome.setCreateDate(DateUtils.getDateTime());
			cdFbOutcome.setDelFlag(CdFbOutcome.DEL_FLAG_NORMAL);
		}
		cdFbOutcomeDao.save(cdFbOutcome);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdFbOutcomeDao.deleteById(id);
	}


	/**
	 * wangsong
	 * 20180105
	 * 清空表
	 */
	@Transactional(readOnly = false)
	public void deleteAlls(String name) {
		cdFbOutcomeDao.update("delete from "+name );
	}



	/**
	 * wangsong
	 * 20180105
	 * 查询近期战绩10场
	 */
	@Transactional(readOnly = false)
	public List<CdFbOutcome> findById(String itemid, String name,String type){
		DetachedCriteria dc = cdFbOutcomeDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdFbOutcome.FIELD_DEL_FLAG, CdFbOutcome.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("itemid", itemid));
		dc.add(Restrictions.eq("outcomeType", type));//'战绩类型 0近期1历史'
		dc.add(Restrictions.or( Restrictions.eq("hn", name), Restrictions.eq("gn", name) ));
		dc.addOrder(Order.desc("mt"));
		Criteria cri = dc.getExecutableCriteria(cdFbOutcomeDao.getSession());
		cri.setMaxResults(10);
		return cdFbOutcomeDao.find(dc);
	}

	/**
	 * wangsong
	 * 20180105
	 * 查询近期主场战绩
	 */
	/*@Transactional(readOnly = false)
	public List<CdFbOutcome> findByHn(String itemid, String name,String type){
		DetachedCriteria dc = cdFbOutcomeDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdFbOutcome.FIELD_DEL_FLAG, CdFbOutcome.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("itemid", itemid));
		dc.add(Restrictions.eq("outcomeType", type));//'战绩类型 0近期1历史'
		dc.add(Restrictions.eq("hn", name));
		return cdFbOutcomeDao.find(dc);
	}*/


	/**
	 * wangsong
	 * 20180105
	 * 查询历史战绩5场
	 */
	@Transactional(readOnly = false)
	public List<CdFbOutcome> findByOldTime(String itemid){
		DetachedCriteria dc = cdFbOutcomeDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdFbOutcome.FIELD_DEL_FLAG, CdFbOutcome.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("itemid", itemid));
		dc.add(Restrictions.eq("outcomeType", "1"));//'战绩类型 0近期1历史'
		dc.addOrder(Order.desc("mt"));
		Criteria cri = dc.getExecutableCriteria(cdFbOutcomeDao.getSession());
		cri.setMaxResults(5);
		return cdFbOutcomeDao.find(dc);
	}
}
