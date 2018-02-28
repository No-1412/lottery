/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.csysaccount.service;

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
import com.youge.yogee.modules.csysaccount.entity.CdSysAccount;
import com.youge.yogee.modules.csysaccount.dao.CdSysAccountDao;

/**
 * 平台系统账户Service
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Component
@Transactional(readOnly = true)
public class CdSysAccountService extends BaseService {

	@Autowired
	private CdSysAccountDao cdSysAccountDao;
	
	public CdSysAccount get(String id) {
		return cdSysAccountDao.get(id);
	}
	
	public Page<CdSysAccount> find(Page<CdSysAccount> page, CdSysAccount cdSysAccount) {
		DetachedCriteria dc = cdSysAccountDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdSysAccount.getName())){
			dc.add(Restrictions.like("name", "%"+cdSysAccount.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdSysAccount.FIELD_DEL_FLAG, CdSysAccount.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdSysAccountDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdSysAccount cdSysAccount) {

		if(StringUtils.isEmpty(cdSysAccount.getId())){
			cdSysAccount.setId(IdGen.uuid());
			cdSysAccount.setCreateDate(DateUtils.getDateTime());
			cdSysAccount.setDelFlag(CdSysAccount.DEL_FLAG_NORMAL);
		}
		cdSysAccountDao.save(cdSysAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdSysAccountDao.deleteById(id);
	}
	
}
