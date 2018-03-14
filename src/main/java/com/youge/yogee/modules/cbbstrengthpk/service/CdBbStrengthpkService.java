/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbbstrengthpk.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cbbstrengthpk.dao.CdBbStrengthpkDao;
import com.youge.yogee.modules.cbbstrengthpk.entity.CdBbStrengthpk;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 篮球实力PKService
 * @author RenHaipeng
 * @version 2018-01-17
 */
@Component
@Transactional(readOnly = true)
public class CdBbStrengthpkService extends BaseService {

	@Autowired
	private CdBbStrengthpkDao cdBbStrengthpkDao;
	
	public CdBbStrengthpk get(String id) {
		return cdBbStrengthpkDao.get(id);
	}
	
	public Page<CdBbStrengthpk> find(Page<CdBbStrengthpk> page, CdBbStrengthpk cdBbStrengthpk) {
		DetachedCriteria dc = cdBbStrengthpkDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdBbStrengthpk.getName())){
			dc.add(Restrictions.like("name", "%" + cdBbStrengthpk.getName() + "%"));
		}
		dc.add(Restrictions.eq(CdBbStrengthpk.FIELD_DEL_FLAG, CdBbStrengthpk.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdBbStrengthpkDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdBbStrengthpk cdBbStrengthpk) {

		if(StringUtils.isEmpty(cdBbStrengthpk.getId())){
			cdBbStrengthpk.setId(IdGen.uuid());
			cdBbStrengthpk.setCreateDate(DateUtils.getDateTime());
			cdBbStrengthpk.setDelFlag(CdBbStrengthpk.DEL_FLAG_NORMAL);
		}
		cdBbStrengthpkDao.save(cdBbStrengthpk);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdBbStrengthpkDao.deleteById(id);
	}

	@Transactional(readOnly = false)
	public void deleteBbStrength() {
		cdBbStrengthpkDao.update("delete from CdBbStrengthpk");
	}

	public CdBbStrengthpk getStrengthPk(String itemId){
		DetachedCriteria dc = cdBbStrengthpkDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdBbStrengthpk.FIELD_DEL_FLAG, CdBbStrengthpk.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("itemId", itemId));
		dc.addOrder(Order.desc("createDate"));
		List<CdBbStrengthpk> list =  cdBbStrengthpkDao.find(dc);
		if(list.size()==0){
			return null;
		}else {
			return list.get(0);
		}
	}
}
