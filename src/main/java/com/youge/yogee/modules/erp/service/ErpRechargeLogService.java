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
import java.util.Date;
import java.util.Map;

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
	
	public Page<ErpRechargeLog> find(Page<ErpRechargeLog> page, ErpRechargeLog erpRechargeLog, Map<String, Object> paramMap) {
		DetachedCriteria dc = erpRechargeLogDao.createDetachedCriteria();
		Date beginDate = DateUtils.parseDate(paramMap.get("beginDate"));
		if (beginDate == null){
			beginDate = DateUtils.setDays(new Date(), 1);
			paramMap.put("beginDate", DateUtils.formatDate(beginDate, "yyyy-MM-dd HH:mm:ss"));
		}
		Date endDate = DateUtils.parseDate(paramMap.get("endDate"));
		if (endDate == null){
			endDate = DateUtils.addDays(DateUtils.addMonths(beginDate, 1), -1);
			paramMap.put("endDate", DateUtils.formatDate(endDate, "yyyy-MM-dd HH:mm:ss"));
		}
		String bDate = DateUtils.formatDate(beginDate,"yyyy-MM-dd HH:mm:ss");
		String eDate = DateUtils.formatDate(endDate,"yyyy-MM-dd HH:mm:ss");
		if (erpRechargeLog.getUserId()!=null){
			if (StringUtils.isNotBlank(erpRechargeLog.getUserId().getName())){
				dc.createAlias("userId","userId");
				dc.add(Restrictions.like("userId.name", "%"+erpRechargeLog.getUserId().getName()+"%"));
			}
		}
		dc.add(Restrictions.between("createDate",bDate,eDate));
		dc.add(Restrictions.eq("type","0"));
		dc.add(Restrictions.eq(ErpRechargeLog.FIELD_DEL_FLAG, ErpRechargeLog.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return erpRechargeLogDao.find(page, dc);
	}

	//查询扣款
	public Page<ErpRechargeLog> findWithhold(Page<ErpRechargeLog> page, ErpRechargeLog erpRechargeLog, Map<String, Object> paramMap) {
		DetachedCriteria dc = erpRechargeLogDao.createDetachedCriteria();
		Date beginDate = DateUtils.parseDate(paramMap.get("beginDate"));
		if (beginDate == null){
			beginDate = DateUtils.setDays(new Date(), 1);
			paramMap.put("beginDate", DateUtils.formatDate(beginDate, "yyyy-MM-dd HH:mm:ss"));
		}
		Date endDate = DateUtils.parseDate(paramMap.get("endDate"));
		if (endDate == null){
			endDate = DateUtils.addDays(DateUtils.addMonths(beginDate, 1), -1);
			paramMap.put("endDate", DateUtils.formatDate(endDate, "yyyy-MM-dd HH:mm:ss"));
		}
		String bDate = DateUtils.formatDate(beginDate,"yyyy-MM-dd HH:mm:ss");
		String eDate = DateUtils.formatDate(endDate,"yyyy-MM-dd HH:mm:ss");
		if (erpRechargeLog.getUserId()!=null){
			if (StringUtils.isNotBlank(erpRechargeLog.getUserId().getName())){
				dc.createAlias("userId","userId");
				dc.add(Restrictions.like("userId.name", "%"+erpRechargeLog.getUserId().getName()+"%"));
			}
		}
		dc.add(Restrictions.eq("type","1"));
		dc.add(Restrictions.between("createDate",bDate,eDate));
		dc.add(Restrictions.eq(ErpRechargeLog.FIELD_DEL_FLAG, ErpRechargeLog.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return erpRechargeLogDao.find(page, dc);
	}



	
	@Transactional(readOnly = false)
	public void save(ErpRechargeLog erpRechargeLog, String type) {

		if(StringUtils.isEmpty(erpRechargeLog.getId())){
			erpRechargeLog.setId(IdGen.uuid());
			erpRechargeLog.setCreateDate(DateUtils.getDateTime());
			erpRechargeLog.setDelFlag(ErpRechargeLog.DEL_FLAG_NORMAL);
			erpRechargeLog.setSaleId(UserUtils.getUser());
			erpRechargeLog.setType(type);
		}

		String userId = erpRechargeLog.getUserId().getId();
		ErpUser erpUser = erpUserService.get(userId);
		BigDecimal money2 = erpRechargeLog.getMoney();//充值钱数
		BigDecimal money1 = erpUser.getBalance();//余额
		if (type.equals("0")){//充值
			erpUser.setBalance(money1.add(money2));
		}else {//扣款
			erpUser.setBalance(money1.subtract(money2));
		}
		erpUserService.save(erpUser);
		erpRechargeLogDao.save(erpRechargeLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		erpRechargeLogDao.deleteById(id);
	}
	
}
