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
import com.youge.yogee.modules.cfootballorder.entity.CdFootballSingleOrder;
import com.youge.yogee.modules.cfootballorder.dao.CdFootballSingleOrderDao;

import java.util.List;

/**
 * 竞彩足球订单Service
 * @author ZhaoYiFeng
 * @version 2018-02-24
 */
@Component
@Transactional(readOnly = true)
public class CdFootballSingleOrderService extends BaseService {

	@Autowired
	private CdFootballSingleOrderDao cdFootballSingleOrderDao;
	
	public CdFootballSingleOrder get(String id) {
		return cdFootballSingleOrderDao.get(id);
	}
	
	public Page<CdFootballSingleOrder> find(Page<CdFootballSingleOrder> page, CdFootballSingleOrder cdFootballSingleOrder) {
		DetachedCriteria dc = cdFootballSingleOrderDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdFootballSingleOrder.getOrderNum())){
			dc.add(Restrictions.eq("orderNum", cdFootballSingleOrder.getOrderNum()));
		}

		dc.add(Restrictions.eq(CdFootballSingleOrder.FIELD_DEL_FLAG, CdFootballSingleOrder.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdFootballSingleOrderDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdFootballSingleOrder cdFootballSingleOrder) {

		if(StringUtils.isEmpty(cdFootballSingleOrder.getId())){
			cdFootballSingleOrder.setId(IdGen.uuid());
			cdFootballSingleOrder.setCreateDate(DateUtils.getDateTime());
			cdFootballSingleOrder.setDelFlag(CdFootballSingleOrder.DEL_FLAG_NORMAL);
		}
		cdFootballSingleOrderDao.save(cdFootballSingleOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdFootballSingleOrderDao.deleteById(id);
	}


	public List<CdFootballSingleOrder> findStatusTwo() {
		DetachedCriteria dc = cdFootballSingleOrderDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdFootballSingleOrder.FIELD_DEL_FLAG, CdFootballSingleOrder.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("stauts", "2"));
		dc.addOrder(Order.desc("createDate"));
		return cdFootballSingleOrderDao.find(dc);
	}
	
}
