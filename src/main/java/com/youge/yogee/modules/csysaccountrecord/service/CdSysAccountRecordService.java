/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csysaccountrecord.service;

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
import com.youge.yogee.modules.csysaccountrecord.entity.CdSysAccountRecord;
import com.youge.yogee.modules.csysaccountrecord.dao.CdSysAccountRecordDao;

/**
 * 平台账户积分流水Service
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Component
@Transactional(readOnly = true)
public class CdSysAccountRecordService extends BaseService {

	@Autowired
	private CdSysAccountRecordDao cdSysAccountRecordDao;
	
	public CdSysAccountRecord get(String id) {
		return cdSysAccountRecordDao.get(id);
	}
	
	public Page<CdSysAccountRecord> find(Page<CdSysAccountRecord> page, CdSysAccountRecord cdSysAccountRecord) {
		DetachedCriteria dc = cdSysAccountRecordDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdSysAccountRecord.getName())){
			dc.add(Restrictions.like("name", "%"+cdSysAccountRecord.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdSysAccountRecord.FIELD_DEL_FLAG, CdSysAccountRecord.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdSysAccountRecordDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdSysAccountRecord cdSysAccountRecord) {

		if(StringUtils.isEmpty(cdSysAccountRecord.getId())){
			cdSysAccountRecord.setId(IdGen.uuid());
			cdSysAccountRecord.setCreateDate(DateUtils.getDateTime());
			cdSysAccountRecord.setDelFlag(CdSysAccountRecord.DEL_FLAG_NORMAL);
		}
		cdSysAccountRecordDao.save(cdSysAccountRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdSysAccountRecordDao.deleteById(id);
	}
	
}
