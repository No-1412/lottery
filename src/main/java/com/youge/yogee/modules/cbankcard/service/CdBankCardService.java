/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbankcard.service;

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
import com.youge.yogee.modules.cbankcard.entity.CdBankCard;
import com.youge.yogee.modules.cbankcard.dao.CdBankCardDao;

import java.util.List;

/**
 * 用户银行卡Service
 * @author WeiJinChao
 * @version 2017-12-14
 */
@Component
@Transactional(readOnly = true)
public class CdBankCardService extends BaseService {

	@Autowired
	private CdBankCardDao cdBankCardDao;
	
	public CdBankCard get(String id) {
		return cdBankCardDao.get(id);
	}
	
	public Page<CdBankCard> find(Page<CdBankCard> page, CdBankCard cdBankCard) {
		DetachedCriteria dc = cdBankCardDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdBankCard.getName())){
			dc.add(Restrictions.like("name", "%"+cdBankCard.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdBankCard.FIELD_DEL_FLAG, CdBankCard.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdBankCardDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdBankCard cdBankCard) {

		if(StringUtils.isEmpty(cdBankCard.getId())){
			cdBankCard.setId(IdGen.uuid());
			cdBankCard.setCreateDate(DateUtils.getDateTime());
			cdBankCard.setDelFlag(CdBankCard.DEL_FLAG_NORMAL);
		}
		cdBankCardDao.save(cdBankCard);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdBankCardDao.deleteById(id);
	}

	/**
	 * 查询将要设置成默认的银行卡
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<CdBankCard> getWillDefaultCard(String id){
		DetachedCriteria dc = cdBankCardDao.createDetachedCriteria();
		dc.add(Restrictions.eq("id",id));
		dc.add(Restrictions.eq(CdBankCard.FIELD_DEL_FLAG, CdBankCard.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdBankCardDao.find(dc);
	}

	/**
	 * 查询已经设置成默认的银行卡
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<CdBankCard> getDefaultCard(String userId){
		DetachedCriteria dc = cdBankCardDao.createDetachedCriteria();
		dc.add(Restrictions.eq("userId",userId));
		dc.add(Restrictions.eq("isDefault","1"));
		dc.add(Restrictions.eq(CdBankCard.FIELD_DEL_FLAG, CdBankCard.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdBankCardDao.find(dc);
	}
	/**
	 * 获取用户添加银行卡的数量
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Long getBankCount(String userId){
		DetachedCriteria dc = cdBankCardDao.createDetachedCriteria();
		dc.add(Restrictions.eq("userId",userId));
		dc.add(Restrictions.eq(CdBankCard.FIELD_DEL_FLAG, CdBankCard.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdBankCardDao.count(dc);
	}

	@Transactional(readOnly = false)
	public List<CdBankCard> getUserBankList(String userId){
		DetachedCriteria dc = cdBankCardDao.createDetachedCriteria();
		dc.add(Restrictions.eq("userId",userId));
		dc.add(Restrictions.eq(CdBankCard.FIELD_DEL_FLAG, CdBankCard.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("isDefault"));
		return cdBankCardDao.find(dc);
	}
}
