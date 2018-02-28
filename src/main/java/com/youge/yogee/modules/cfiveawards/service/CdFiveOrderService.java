/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfiveawards.service;

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
import com.youge.yogee.modules.cfiveawards.entity.CdFiveOrder;
import com.youge.yogee.modules.cfiveawards.dao.CdFiveOrderDao;

/**
 * 排列五订单Service
 * @author ZhaoYiFeng
 * @version 2018-02-07
 */
@Component
@Transactional(readOnly = true)
public class CdFiveOrderService extends BaseService {

	@Autowired
	private CdFiveOrderDao cdFiveOrderDao;
	
	public CdFiveOrder get(String id) {
		return cdFiveOrderDao.get(id);
	}
	
	public Page<CdFiveOrder> find(Page<CdFiveOrder> page, CdFiveOrder cdFiveOrder) {
		DetachedCriteria dc = cdFiveOrderDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdFiveOrder.getOrderNum())){
			dc.add(Restrictions.eq("orderNum", cdFiveOrder.getOrderNum()));
		}
		dc.add(Restrictions.eq(CdFiveOrder.FIELD_DEL_FLAG, CdFiveOrder.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdFiveOrderDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdFiveOrder cdFiveOrder) {

		if(StringUtils.isEmpty(cdFiveOrder.getId())){
			cdFiveOrder.setId(IdGen.uuid());
			cdFiveOrder.setCreateDate(DateUtils.getDateTime());
			cdFiveOrder.setDelFlag(CdFiveOrder.DEL_FLAG_NORMAL);
		}
		cdFiveOrderDao.save(cdFiveOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdFiveOrderDao.deleteById(id);
	}
	
}
