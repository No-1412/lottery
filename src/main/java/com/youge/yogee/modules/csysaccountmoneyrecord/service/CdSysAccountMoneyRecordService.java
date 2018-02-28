/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csysaccountmoneyrecord.service;

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
import com.youge.yogee.modules.csysaccountmoneyrecord.entity.CdSysAccountMoneyRecord;
import com.youge.yogee.modules.csysaccountmoneyrecord.dao.CdSysAccountMoneyRecordDao;

/**
 * 平台账户资金流水Service
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Component
@Transactional(readOnly = true)
public class CdSysAccountMoneyRecordService extends BaseService {

	@Autowired
	private CdSysAccountMoneyRecordDao cdSysAccountMoneyRecordDao;
	
	public CdSysAccountMoneyRecord get(String id) {
		return cdSysAccountMoneyRecordDao.get(id);
	}
	
	public Page<CdSysAccountMoneyRecord> find(Page<CdSysAccountMoneyRecord> page, CdSysAccountMoneyRecord cdSysAccountMoneyRecord) {
		DetachedCriteria dc = cdSysAccountMoneyRecordDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdSysAccountMoneyRecord.getName())){
			dc.add(Restrictions.like("name", "%"+cdSysAccountMoneyRecord.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdSysAccountMoneyRecord.FIELD_DEL_FLAG, CdSysAccountMoneyRecord.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdSysAccountMoneyRecordDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdSysAccountMoneyRecord cdSysAccountMoneyRecord) {

		if(StringUtils.isEmpty(cdSysAccountMoneyRecord.getId())){
			cdSysAccountMoneyRecord.setId(IdGen.uuid());
			cdSysAccountMoneyRecord.setCreateDate(DateUtils.getDateTime());
			cdSysAccountMoneyRecord.setDelFlag(CdSysAccountMoneyRecord.DEL_FLAG_NORMAL);
		}
		cdSysAccountMoneyRecordDao.save(cdSysAccountMoneyRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdSysAccountMoneyRecordDao.deleteById(id);
	}
	
}
