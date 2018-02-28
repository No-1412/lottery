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
import com.youge.yogee.modules.cbasketballorder.entity.CdBasketballSingleOrder;
import com.youge.yogee.modules.cbasketballorder.dao.CdBasketballSingleOrderDao;

/**
 * 竞彩篮球订单Service
 * @author ZhaoYiFeng
 * @version 2018-02-26
 */
@Component
@Transactional(readOnly = true)
public class CdBasketballSingleOrderService extends BaseService {

	@Autowired
	private CdBasketballSingleOrderDao cdBasketballSingleOrderDao;
	
	public CdBasketballSingleOrder get(String id) {
		return cdBasketballSingleOrderDao.get(id);
	}
	
	public Page<CdBasketballSingleOrder> find(Page<CdBasketballSingleOrder> page, CdBasketballSingleOrder cdBasketballSingleOrder) {
		DetachedCriteria dc = cdBasketballSingleOrderDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdBasketballSingleOrder.getOrderNum())){
			dc.add(Restrictions.like("orderNum", cdBasketballSingleOrder.getOrderNum()));
		}
		if(StringUtils.isNotEmpty(cdBasketballSingleOrder.getBuyWays())){
			dc.add(Restrictions.eq("buyWays", cdBasketballSingleOrder.getBuyWays()));
		}
		dc.add(Restrictions.eq(CdBasketballSingleOrder.FIELD_DEL_FLAG, CdBasketballSingleOrder.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdBasketballSingleOrderDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdBasketballSingleOrder cdBasketballSingleOrder) {

		if(StringUtils.isEmpty(cdBasketballSingleOrder.getId())){
			cdBasketballSingleOrder.setId(IdGen.uuid());
			cdBasketballSingleOrder.setCreateDate(DateUtils.getDateTime());
			cdBasketballSingleOrder.setDelFlag(CdBasketballSingleOrder.DEL_FLAG_NORMAL);
		}
		cdBasketballSingleOrderDao.save(cdBasketballSingleOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdBasketballSingleOrderDao.deleteById(id);
	}
	
}
