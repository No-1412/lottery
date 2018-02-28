/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cscoreodds.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.modules.cscoreodds.entity.CdScoreOdds;
import com.youge.yogee.modules.cscoreodds.dao.CdScoreOddsDao;

/**
 * 比分赔率接口Service
 * @author WeiJinChao
 * @version 2017-12-12
 */
@Component
@Transactional(readOnly = true)
public class CdScoreOddsService extends BaseService {

	@Autowired
	private CdScoreOddsDao cdScoreOddsDao;
	
	public CdScoreOdds get(String id) {
		return cdScoreOddsDao.get(id);
	}
	
	public Page<CdScoreOdds> find(Page<CdScoreOdds> page, CdScoreOdds cdScoreOdds) {
		DetachedCriteria dc = cdScoreOddsDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdScoreOdds.getName())){
			dc.add(Restrictions.like("name", "%"+cdScoreOdds.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdScoreOdds.FIELD_DEL_FLAG, CdScoreOdds.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdScoreOddsDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdScoreOdds cdScoreOdds) {

		if(StringUtils.isEmpty(cdScoreOdds.getId())){
			cdScoreOdds.setId(IdGen.uuid());
			cdScoreOdds.setCreateDate(DateUtils.getDateTime());
			cdScoreOdds.setDelFlag(CdScoreOdds.DEL_FLAG_NORMAL);
		}
		cdScoreOddsDao.save(cdScoreOdds);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdScoreOddsDao.deleteById(id);
	}
	
}
