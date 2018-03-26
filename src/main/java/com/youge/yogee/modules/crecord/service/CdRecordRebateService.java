/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.crecord.service;

import org.hibernate.Criteria;
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
import com.youge.yogee.modules.crecord.entity.CdRecordRebate;
import com.youge.yogee.modules.crecord.dao.CdRecordRebateDao;

import java.util.List;

/**
 * 返利记录Service
 * @author ZhaoYiFeng
 * @version 2018-03-23
 */
@Component
@Transactional(readOnly = true)
public class CdRecordRebateService extends BaseService {

	@Autowired
	private CdRecordRebateDao cdRecordRebateDao;
	
	public CdRecordRebate get(String id) {
		return cdRecordRebateDao.get(id);
	}
	
	public Page<CdRecordRebate> find(Page<CdRecordRebate> page, CdRecordRebate cdRecordRebate) {
		DetachedCriteria dc = cdRecordRebateDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdRecordRebate.getName())){
			dc.add(Restrictions.like("name", "%"+cdRecordRebate.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdRecordRebate.FIELD_DEL_FLAG, CdRecordRebate.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdRecordRebateDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdRecordRebate cdRecordRebate) {

		if(StringUtils.isEmpty(cdRecordRebate.getId())){
			cdRecordRebate.setId(IdGen.uuid());
			cdRecordRebate.setCreateDate(DateUtils.getDateTime());
			cdRecordRebate.setDelFlag(CdRecordRebate.DEL_FLAG_NORMAL);
		}
		cdRecordRebateDao.save(cdRecordRebate);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdRecordRebateDao.deleteById(id);
	}

	public List<CdRecordRebate> findByUid(String uid,String total,String count) {
		DetachedCriteria dc = cdRecordRebateDao.createDetachedCriteria();
		dc.add(Restrictions.eq("uid", uid));
		dc.add(Restrictions.eq(CdRecordRebate.FIELD_DEL_FLAG, CdRecordRebate.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));

		Criteria cri = dc.getExecutableCriteria(cdRecordRebateDao.getSession());
		cri.setFirstResult(Integer.parseInt(total));
		cri.setMaxResults(Integer.parseInt(count));
		return cdRecordRebateDao.find(dc);
	}

}
