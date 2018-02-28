/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cftskill.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cftskill.dao.CdFtSkillDao;
import com.youge.yogee.modules.cftskill.entity.CdFtSkill;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 足球实况技术统计Service
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Component
@Transactional(readOnly = true)
public class CdFtSkillService extends BaseService {

	@Autowired
	private CdFtSkillDao cdFtSkillDao;
	
	public CdFtSkill get(String id) {
		return cdFtSkillDao.get(id);
	}
	
	public Page<CdFtSkill> find(Page<CdFtSkill> page, CdFtSkill cdFtSkill) {
		DetachedCriteria dc = cdFtSkillDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdFtSkill.getName())){
			dc.add(Restrictions.like("name", "%" + cdFtSkill.getName() + "%"));
		}
		dc.add(Restrictions.eq(CdFtSkill.FIELD_DEL_FLAG, CdFtSkill.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdFtSkillDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdFtSkill cdFtSkill) {

		if(StringUtils.isEmpty(cdFtSkill.getId())){
			cdFtSkill.setId(IdGen.uuid());
			cdFtSkill.setCreateDate(DateUtils.getDateTime());
			cdFtSkill.setDelFlag(CdFtSkill.DEL_FLAG_NORMAL);
		}
		cdFtSkillDao.save(cdFtSkill);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdFtSkillDao.deleteById(id);
	}

	@Transactional(readOnly = false)
	public void deleteFtSkill() {
		cdFtSkillDao.update("delete from CdFtSkill");
	}

	@Transactional(readOnly = false)
	public List<CdFtSkill> getFtSkill(String itemId){
		DetachedCriteria dc = cdFtSkillDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdFtSkill.FIELD_DEL_FLAG, CdFtSkill.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		dc.add(Restrictions.eq("itemId", itemId));
		return cdFtSkillDao.find(dc);
	}
}
