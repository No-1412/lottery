/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballorder.service;

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
import com.youge.yogee.modules.cfootballorder.entity.CdFootballBestFollowOrder;
import com.youge.yogee.modules.cfootballorder.dao.CdFootballBestFollowOrderDao;

/**
 * 足球优化Service
 * @author ZhaoYiFeng
 * @version 2018-04-18
 */
@Component
@Transactional(readOnly = true)
public class CdFootballBestFollowOrderService extends BaseService {

	@Autowired
	private CdFootballBestFollowOrderDao cdFootballBestFollowOrderDao;
	
	public CdFootballBestFollowOrder get(String id) {
		return cdFootballBestFollowOrderDao.get(id);
	}
	
	public Page<CdFootballBestFollowOrder> find(Page<CdFootballBestFollowOrder> page, CdFootballBestFollowOrder cdFootballBestFollowOrder) {
		DetachedCriteria dc = cdFootballBestFollowOrderDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdFootballBestFollowOrder.getName())){
			dc.add(Restrictions.like("name", "%"+cdFootballBestFollowOrder.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdFootballBestFollowOrder.FIELD_DEL_FLAG, CdFootballBestFollowOrder.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdFootballBestFollowOrderDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdFootballBestFollowOrder cdFootballBestFollowOrder) {

		if(StringUtils.isEmpty(cdFootballBestFollowOrder.getId())){
			cdFootballBestFollowOrder.setId(IdGen.uuid());
			cdFootballBestFollowOrder.setCreateDate(DateUtils.getDateTime());
			cdFootballBestFollowOrder.setDelFlag(CdFootballBestFollowOrder.DEL_FLAG_NORMAL);
		}
		cdFootballBestFollowOrderDao.save(cdFootballBestFollowOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdFootballBestFollowOrderDao.deleteById(id);
	}
	
}
