/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cthreeawards.service;

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
import com.youge.yogee.modules.cthreeawards.entity.CdThreeOrder;
import com.youge.yogee.modules.cthreeawards.dao.CdThreeOrderDao;

/**
 * 排列三订单Service
 * @author ZhaoYiFeng
 * @version 2018-02-06
 */
@Component
@Transactional(readOnly = true)
public class CdThreeOrderService extends BaseService {

	@Autowired
	private CdThreeOrderDao cdThreeOrderDao;
	
	public CdThreeOrder get(String id) {
		return cdThreeOrderDao.get(id);
	}
	
	public Page<CdThreeOrder> find(Page<CdThreeOrder> page, CdThreeOrder cdThreeOrder) {
		DetachedCriteria dc = cdThreeOrderDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdThreeOrder.getOrderNum())){
			dc.add(Restrictions.eq("orderNum", cdThreeOrder.getOrderNum()));
		}
		if (StringUtils.isNotEmpty(cdThreeOrder.getBuyWays())){
			dc.add(Restrictions.eq("buyWays", cdThreeOrder.getBuyWays()));
		}
		dc.add(Restrictions.eq(CdThreeOrder.FIELD_DEL_FLAG, CdThreeOrder.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdThreeOrderDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdThreeOrder cdThreeOrder) {

		if(StringUtils.isEmpty(cdThreeOrder.getId())){
			cdThreeOrder.setId(IdGen.uuid());
			cdThreeOrder.setCreateDate(DateUtils.getDateTime());
			cdThreeOrder.setDelFlag(CdThreeOrder.DEL_FLAG_NORMAL);
		}
		cdThreeOrderDao.save(cdThreeOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdThreeOrderDao.deleteById(id);
	}
	
}
