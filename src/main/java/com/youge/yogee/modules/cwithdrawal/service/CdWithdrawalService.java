/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cwithdrawal.service;

import com.youge.yogee.modules.ctradingrecord.entity.CdTradingRecord;
import org.hibernate.Criteria;
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
import com.youge.yogee.modules.cwithdrawal.entity.CdWithdrawal;
import com.youge.yogee.modules.cwithdrawal.dao.CdWithdrawalDao;

import java.util.List;

/**
 * 提现记录Service
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Component
@Transactional(readOnly = true)
public class CdWithdrawalService extends BaseService {

	@Autowired
	private CdWithdrawalDao cdWithdrawalDao;
	
	public CdWithdrawal get(String id) {
		return cdWithdrawalDao.get(id);
	}
	
	public Page<CdWithdrawal> find(Page<CdWithdrawal> page, CdWithdrawal cdWithdrawal) {
		DetachedCriteria dc = cdWithdrawalDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdWithdrawal.getName())){
			dc.add(Restrictions.like("name", "%"+cdWithdrawal.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdWithdrawal.FIELD_DEL_FLAG, CdWithdrawal.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdWithdrawalDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdWithdrawal cdWithdrawal) {

		if(StringUtils.isEmpty(cdWithdrawal.getId())){
			cdWithdrawal.setId(IdGen.uuid());
			cdWithdrawal.setCreateDate(DateUtils.getDateTime());
			cdWithdrawal.setDelFlag(CdWithdrawal.DEL_FLAG_NORMAL);
		}
		cdWithdrawalDao.save(cdWithdrawal);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdWithdrawalDao.deleteById(id);
	}


	/**
	 * wangsong
	 * 20171221
	 * 查询金额提现记录
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<CdWithdrawal> listUidOrder(String userId, String count, String total) {
		DetachedCriteria dc = cdWithdrawalDao.createDetachedCriteria();
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("txStatus", "2"));
		dc.add(Restrictions.eq(CdTradingRecord.FIELD_DEL_FLAG, CdTradingRecord.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		Criteria cri = dc.getExecutableCriteria(cdWithdrawalDao.getSession());
		cri.setMaxResults(Integer.parseInt(count));
		cri.setFirstResult((Integer.parseInt(total) - 1) * Integer.parseInt(count));
		return cdWithdrawalDao.find(dc);
	}


	//根据No,userid查询提现记录
	/**
	 * wangsong
	 * 20171225
	 * 根据No,userid查询提现记录
	 * @param userId
	 * @return
	 */
	public CdWithdrawal getTx(String order_no,String userId) {
		DetachedCriteria dc = cdWithdrawalDao.createDetachedCriteria();
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("txStatus","1"));
		dc.add(Restrictions.eq("txNumber",order_no));
		dc.add(Restrictions.eq(CdWithdrawal.FIELD_DEL_FLAG, CdWithdrawal.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		List<CdWithdrawal> list = cdWithdrawalDao.find(dc);
		if (null == list) {
			return null;
		} else if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
