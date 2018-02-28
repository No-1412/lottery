/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballorder.service;

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
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballFollowOrder;
import com.youge.yogee.modules.cbasketballorder.dao.CdBasketballFollowOrderDao;

/**
 * 竞彩篮球订单Service
 * @author ZhaoYiFeng
 * @version 2018-02-26
 */
@Component
@Transactional(readOnly = true)
public class CdBasketballFollowOrderService extends BaseService {

	@Autowired
	private CdBasketballFollowOrderDao cdBasketballFollowOrderDao;
	
	public CdBasketballFollowOrder get(String id) {
		return cdBasketballFollowOrderDao.get(id);
	}
	
	public Page<CdBasketballFollowOrder> find(Page<CdBasketballFollowOrder> page, CdBasketballFollowOrder cdBasketballFollowOrder) {
		DetachedCriteria dc = cdBasketballFollowOrderDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdBasketballFollowOrder.getOrderNum())){
			dc.add(Restrictions.like("orderNum", cdBasketballFollowOrder.getOrderNum()));
		}
		if(StringUtils.isNotEmpty(cdBasketballFollowOrder.getBuyWays())){
			dc.add(Restrictions.eq("buyWays", cdBasketballFollowOrder.getBuyWays()));
		}
		dc.add(Restrictions.eq(CdBasketballFollowOrder.FIELD_DEL_FLAG, CdBasketballFollowOrder.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdBasketballFollowOrderDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdBasketballFollowOrder cdBasketballFollowOrder) {

		if(StringUtils.isEmpty(cdBasketballFollowOrder.getId())){
			cdBasketballFollowOrder.setId(IdGen.uuid());
			cdBasketballFollowOrder.setCreateDate(DateUtils.getDateTime());
			cdBasketballFollowOrder.setDelFlag(CdBasketballFollowOrder.DEL_FLAG_NORMAL);
		}
		cdBasketballFollowOrderDao.save(cdBasketballFollowOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdBasketballFollowOrderDao.deleteById(id);
	}
	
}
