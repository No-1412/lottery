/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.ctradingrecord.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.ctradingrecord.dao.CdTradingRecordDao;
import com.youge.yogee.modules.ctradingrecord.entity.CdTradingRecord;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 交易记录Service
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Component
@Transactional(readOnly = true)
public class CdTradingRecordService extends BaseService {

	@Autowired
	private CdTradingRecordDao cdTradingRecordDao;
	
	public CdTradingRecord get(String id) {
		return cdTradingRecordDao.get(id);
	}
	
	public Page<CdTradingRecord> find(Page<CdTradingRecord> page, CdTradingRecord cdTradingRecord) {
		DetachedCriteria dc = cdTradingRecordDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdTradingRecord.getName())){
			dc.add(Restrictions.like("name", "%"+cdTradingRecord.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdTradingRecord.FIELD_DEL_FLAG, CdTradingRecord.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdTradingRecordDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdTradingRecord cdTradingRecord) {

		if(StringUtils.isEmpty(cdTradingRecord.getId())){
			cdTradingRecord.setId(IdGen.uuid());
			cdTradingRecord.setCreateDate(DateUtils.getDateTime());
			cdTradingRecord.setDelFlag(CdTradingRecord.DEL_FLAG_NORMAL);
		}
		cdTradingRecordDao.save(cdTradingRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdTradingRecordDao.deleteById(id);
	}




}
