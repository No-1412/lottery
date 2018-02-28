/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.crecharge.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.crecharge.dao.CdRechargeDao;
import com.youge.yogee.modules.crecharge.entity.CdRecharge;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 充值记录Service
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Component
@Transactional(readOnly = true)
public class CdRechargeService extends BaseService {

	@Autowired
	private CdRechargeDao cdRechargeDao;
	
	public CdRecharge get(String id) {
		return cdRechargeDao.get(id);
	}
	
	public Page<CdRecharge> find(Page<CdRecharge> page, CdRecharge cdRecharge) {
		DetachedCriteria dc = cdRechargeDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdRecharge.getName())){
			dc.add(Restrictions.like("name", "%"+cdRecharge.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdRecharge.FIELD_DEL_FLAG, CdRecharge.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdRechargeDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdRecharge cdRecharge) {

		if(StringUtils.isEmpty(cdRecharge.getId())){
			cdRecharge.setId(IdGen.uuid());
			cdRecharge.setCreateDate(DateUtils.getDateTime());
			cdRecharge.setDelFlag(CdRecharge.DEL_FLAG_NORMAL);
		}
		cdRechargeDao.save(cdRecharge);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdRechargeDao.deleteById(id);
	}


	/**
	 * anbo
	 * 20171221
	 * 查询最近的充值记录
	 * @param chargeid
	 * @param order_no
	 * @param userId
	 * @return
	 */
	public CdRecharge findOnlyOrder(String chargeid,String order_no,String userId) {
		DetachedCriteria dc = cdRechargeDao.createDetachedCriteria();

		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("result","0"));

		if(StringUtils.isNotEmpty(chargeid)){
			dc.add(Restrictions.eq("chargeId",chargeid));
		}

		if(StringUtils.isNotEmpty(order_no)){
			dc.add(Restrictions.eq("paynumber",order_no));
		}

		dc.add(Restrictions.eq(CdRecharge.FIELD_DEL_FLAG, CdRecharge.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		List<CdRecharge> list = cdRechargeDao.find(dc);
		if (null == list) {
			return null;
		} else if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * wangsong
	 * 20171221
	 * 查询金额充值记录
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<CdRecharge> listUidOrder(String userId, String count, String total) {
		DetachedCriteria dc = cdRechargeDao.createDetachedCriteria();
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("result","1"));
		dc.add(Restrictions.eq(CdRecharge.FIELD_DEL_FLAG, CdRecharge.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		Criteria cri = dc.getExecutableCriteria(cdRechargeDao.getSession());
		cri.setMaxResults(Integer.parseInt(count));
		cri.setFirstResult((Integer.parseInt(total) - 1) * Integer.parseInt(count));
		return cdRechargeDao.find(dc);
	}
}
