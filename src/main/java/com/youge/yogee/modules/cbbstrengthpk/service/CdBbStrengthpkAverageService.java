/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbstrengthpk.service;

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
import com.youge.yogee.modules.cbbstrengthpk.entity.CdBbStrengthpkAverage;
import com.youge.yogee.modules.cbbstrengthpk.dao.CdBbStrengthpkAverageDao;

import java.util.List;

/**
 * 篮球球员实力Service
 * @author ZhaoYiFeng
 * @version 2018-03-14
 */
@Component
@Transactional(readOnly = true)
public class CdBbStrengthpkAverageService extends BaseService {

	@Autowired
	private CdBbStrengthpkAverageDao cdBbStrengthpkAverageDao;
	
	public CdBbStrengthpkAverage get(String id) {
		return cdBbStrengthpkAverageDao.get(id);
	}
	
	public Page<CdBbStrengthpkAverage> find(Page<CdBbStrengthpkAverage> page, CdBbStrengthpkAverage cdBbStrengthpkAverage) {
		DetachedCriteria dc = cdBbStrengthpkAverageDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdBbStrengthpkAverage.getName())){
			dc.add(Restrictions.like("name", "%"+cdBbStrengthpkAverage.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdBbStrengthpkAverage.FIELD_DEL_FLAG, CdBbStrengthpkAverage.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdBbStrengthpkAverageDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdBbStrengthpkAverage cdBbStrengthpkAverage) {

		if(StringUtils.isEmpty(cdBbStrengthpkAverage.getId())){
			cdBbStrengthpkAverage.setId(IdGen.uuid());
			cdBbStrengthpkAverage.setCreateDate(DateUtils.getDateTime());
			cdBbStrengthpkAverage.setDelFlag(CdBbStrengthpkAverage.DEL_FLAG_NORMAL);
		}
		cdBbStrengthpkAverageDao.save(cdBbStrengthpkAverage);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdBbStrengthpkAverageDao.deleteById(id);
	}


	public List<CdBbStrengthpkAverage> getStrengthPk(String itemId){
		DetachedCriteria dc = cdBbStrengthpkAverageDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdBbStrengthpkAverage.FIELD_DEL_FLAG, CdBbStrengthpkAverage.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("itemId", itemId));
		dc.addOrder(Order.desc("createDate"));
		return cdBbStrengthpkAverageDao.find(dc);
	}
}
