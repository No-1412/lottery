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
import com.youge.yogee.modules.cbbstrengthpk.entity.CdBbStrengthpkInjury;
import com.youge.yogee.modules.cbbstrengthpk.dao.CdBbStrengthpkInjuryDao;

import java.util.List;

/**
 * 篮球伤停Service
 * @author ZhaoYiFeng
 * @version 2018-03-14
 */
@Component
@Transactional(readOnly = true)
public class CdBbStrengthpkInjuryService extends BaseService {

	@Autowired
	private CdBbStrengthpkInjuryDao cdBbStrengthpkInjuryDao;
	
	public CdBbStrengthpkInjury get(String id) {
		return cdBbStrengthpkInjuryDao.get(id);
	}
	
	public Page<CdBbStrengthpkInjury> find(Page<CdBbStrengthpkInjury> page, CdBbStrengthpkInjury cdBbStrengthpkInjury) {
		DetachedCriteria dc = cdBbStrengthpkInjuryDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdBbStrengthpkInjury.FIELD_DEL_FLAG, CdBbStrengthpkInjury.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdBbStrengthpkInjuryDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdBbStrengthpkInjury cdBbStrengthpkInjury) {

		if(StringUtils.isEmpty(cdBbStrengthpkInjury.getId())){
			cdBbStrengthpkInjury.setId(IdGen.uuid());
			cdBbStrengthpkInjury.setCreateDate(DateUtils.getDateTime());
			cdBbStrengthpkInjury.setDelFlag(CdBbStrengthpkInjury.DEL_FLAG_NORMAL);
		}
		cdBbStrengthpkInjuryDao.save(cdBbStrengthpkInjury);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdBbStrengthpkInjuryDao.deleteById(id);
	}


	public List<CdBbStrengthpkInjury> getStrengthPk(String itemId){
		DetachedCriteria dc = cdBbStrengthpkInjuryDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdBbStrengthpkInjury.FIELD_DEL_FLAG, CdBbStrengthpkInjury.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("itemId", itemId));
		dc.addOrder(Order.desc("createDate"));
		return cdBbStrengthpkInjuryDao.find(dc);
	}
}
