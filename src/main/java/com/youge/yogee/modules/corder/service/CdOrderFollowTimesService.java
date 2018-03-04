/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.corder.service;

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
import com.youge.yogee.modules.corder.entity.CdOrderFollowTimes;
import com.youge.yogee.modules.corder.dao.CdOrderFollowTimesDao;

/**
 * 竞彩篮球订单Service
 * @author ZhaoYiFeng
 * @version 2018-03-04
 */
@Component
@Transactional(readOnly = true)
public class CdOrderFollowTimesService extends BaseService {

	@Autowired
	private CdOrderFollowTimesDao cdOrderFollowTimesDao;
	
	public CdOrderFollowTimes get(String id) {
		return cdOrderFollowTimesDao.get(id);
	}
	
	public Page<CdOrderFollowTimes> find(Page<CdOrderFollowTimes> page, CdOrderFollowTimes cdOrderFollowTimes) {
		DetachedCriteria dc = cdOrderFollowTimesDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdOrderFollowTimes.getName())){
			dc.add(Restrictions.like("name", "%"+cdOrderFollowTimes.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdOrderFollowTimes.FIELD_DEL_FLAG, CdOrderFollowTimes.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdOrderFollowTimesDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdOrderFollowTimes cdOrderFollowTimes) {

		if(StringUtils.isEmpty(cdOrderFollowTimes.getId())){
			cdOrderFollowTimes.setId(IdGen.uuid());
			cdOrderFollowTimes.setCreateDate(DateUtils.getDateTime());
			cdOrderFollowTimes.setDelFlag(CdOrderFollowTimes.DEL_FLAG_NORMAL);
		}
		cdOrderFollowTimesDao.save(cdOrderFollowTimes);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdOrderFollowTimesDao.deleteById(id);
	}
	
}
