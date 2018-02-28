/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.clottoreward.service;

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
import com.youge.yogee.modules.clottoreward.entity.CdLottoOrder;
import com.youge.yogee.modules.clottoreward.dao.CdLottoOrderDao;

/**
 * 大乐透订单Service
 * @author ZhaoYiFeng
 * @version 2018-02-08
 */
@Component
@Transactional(readOnly = true)
public class CdLottoOrderService extends BaseService {

	@Autowired
	private CdLottoOrderDao cdLottoOrderDao;
	
	public CdLottoOrder get(String id) {
		return cdLottoOrderDao.get(id);
	}
	
	public Page<CdLottoOrder> find(Page<CdLottoOrder> page, CdLottoOrder cdLottoOrder) {
		DetachedCriteria dc = cdLottoOrderDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdLottoOrder.getOrderNum())){
			dc.add(Restrictions.like("orderNum", cdLottoOrder.getOrderNum()));
		}
		dc.add(Restrictions.eq(CdLottoOrder.FIELD_DEL_FLAG, CdLottoOrder.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdLottoOrderDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdLottoOrder cdLottoOrder) {

		if(StringUtils.isEmpty(cdLottoOrder.getId())){
			cdLottoOrder.setId(IdGen.uuid());
			cdLottoOrder.setCreateDate(DateUtils.getDateTime());
			cdLottoOrder.setDelFlag(CdLottoOrder.DEL_FLAG_NORMAL);
		}
		cdLottoOrderDao.save(cdLottoOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdLottoOrderDao.deleteById(id);
	}
	
}
