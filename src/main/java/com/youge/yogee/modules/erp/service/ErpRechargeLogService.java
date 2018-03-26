/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.erp.dao.ErpRechargeLogDao;
import com.youge.yogee.modules.erp.entity.ErpRechargeLog;
import com.youge.yogee.modules.erp.entity.ErpUser;
import com.youge.yogee.modules.sys.utils.UserUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 销售后台充值记录Service
 * @author Lxy
 * @version 2018-03-22
 */
@Component
@Transactional(readOnly = true)
public class ErpRechargeLogService extends BaseService {

	@Autowired
	private ErpRechargeLogDao erpRechargeLogDao;

	@Autowired
	private ErpUserService erpUserService;
	
	public ErpRechargeLog get(String id) {
		return erpRechargeLogDao.get(id);
	}
	
	public Page<ErpRechargeLog> find(Page<ErpRechargeLog> page, ErpRechargeLog erpRechargeLog) {
		DetachedCriteria dc = erpRechargeLogDao.createDetachedCriteria();
		if (erpRechargeLog.getUserId()!=null){
			if (StringUtils.isNotBlank(erpRechargeLog.getUserId().getName())){
				dc.createAlias("userId","userId");
				dc.add(Restrictions.like("userId.name", "%"+erpRechargeLog.getUserId().getName()+"%"));
			}
		}
		dc.add(Restrictions.eq(ErpRechargeLog.FIELD_DEL_FLAG, ErpRechargeLog.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return erpRechargeLogDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ErpRechargeLog erpRechargeLog) {

		if(StringUtils.isEmpty(erpRechargeLog.getId())){
			erpRechargeLog.setId(IdGen.uuid());
			erpRechargeLog.setCreateDate(DateUtils.getDateTime());
			erpRechargeLog.setDelFlag(ErpRechargeLog.DEL_FLAG_NORMAL);
			erpRechargeLog.setSaleId(UserUtils.getUser());
		}
		String userId = erpRechargeLog.getUserId().getId();
		ErpUser erpUser = erpUserService.get(userId);
		BigDecimal money2 = erpRechargeLog.getMoney();
		BigDecimal money1 = erpUser.getBalance();
		erpUser.setBalance(money2.add(money1));
		erpUserService.save(erpUser);
		erpRechargeLogDao.save(erpRechargeLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		erpRechargeLogDao.deleteById(id);
	}
	
}
