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
import com.youge.yogee.modules.cfootballorder.entity.CdFootballFollowOrder;
import com.youge.yogee.modules.cfootballorder.dao.CdFootballFollowOrderDao;

import java.util.List;

/**
 * 竞彩足球订单Service
 * @author ZhaoYiFeng
 * @version 2018-02-24
 */
@Component
@Transactional(readOnly = true)
public class CdFootballFollowOrderService extends BaseService {

	@Autowired
	private CdFootballFollowOrderDao cdFootballFollowOrderDao;
	
	public CdFootballFollowOrder get(String id) {
		return cdFootballFollowOrderDao.get(id);
	}
	
	public Page<CdFootballFollowOrder> find(Page<CdFootballFollowOrder> page, CdFootballFollowOrder cdFootballFollowOrder) {
		DetachedCriteria dc = cdFootballFollowOrderDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdFootballFollowOrder.getOrderNum())){
			dc.add(Restrictions.eq("orderNum", cdFootballFollowOrder.getOrderNum()));
		}
		if(StringUtils.isNotEmpty(cdFootballFollowOrder.getBuyWays())){
			dc.add(Restrictions.eq("buyWays", cdFootballFollowOrder.getBuyWays()));
		}
		dc.add(Restrictions.eq(CdFootballFollowOrder.FIELD_DEL_FLAG, CdFootballFollowOrder.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdFootballFollowOrderDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdFootballFollowOrder cdFootballFollowOrder) {

		if(StringUtils.isEmpty(cdFootballFollowOrder.getId())){
			cdFootballFollowOrder.setId(IdGen.uuid());
			cdFootballFollowOrder.setCreateDate(DateUtils.getDateTime());
			cdFootballFollowOrder.setDelFlag(CdFootballFollowOrder.DEL_FLAG_NORMAL);
		}
		cdFootballFollowOrderDao.save(cdFootballFollowOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdFootballFollowOrderDao.deleteById(id);
	}


	public List<CdFootballFollowOrder> findStatusTwo() {
		DetachedCriteria dc = cdFootballFollowOrderDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdFootballFollowOrder.FIELD_DEL_FLAG, CdFootballFollowOrder.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("stauts", "2"));
		dc.addOrder(Order.desc("createDate"));
		return cdFootballFollowOrderDao.find(dc);
	}
}
