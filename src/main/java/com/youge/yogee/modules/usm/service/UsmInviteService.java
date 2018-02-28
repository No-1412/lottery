/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.usm.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.usm.dao.UsmInviteDao;
import com.youge.yogee.modules.usm.entity.UsmInvite;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户邀请Service
 * @author RenHaipeng
 * @version 2016-12-16
 */
@Component
@Transactional(readOnly = true)
public class UsmInviteService extends BaseService {

	@Autowired
	private UsmInviteDao usmInviteDao;
	
	public UsmInvite get(String id) {
		return usmInviteDao.get(id);
	}
	
	public Page<UsmInvite> find(Page<UsmInvite> page, UsmInvite usmInvite) {
		DetachedCriteria dc = usmInviteDao.createDetachedCriteria();
//		if (StringUtils.isNotEmpty(usmInvite.getName())){
//			dc.add(Restrictions.like("name", "%"+usmInvite.getName()+"%"));
//		}
		dc.add(Restrictions.eq(UsmInvite.FIELD_DEL_FLAG, UsmInvite.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return usmInviteDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(UsmInvite usmInvite) {

		if(StringUtils.isEmpty(usmInvite.getId())){
			usmInvite.setId(IdGen.uuid());
			usmInvite.setCreateDate(DateUtils.getDateTime());
			usmInvite.setDelFlag(UsmInvite.DEL_FLAG_NORMAL);
		}
		usmInviteDao.save(usmInvite);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		usmInviteDao.deleteById(id);
	}

	//	查看的联系方式
	public Page<T> getInvitee(Page<T> page, String user_id) {
//		DetachedCriteria dc = usmInviteDao.createDetachedCriteria();
//		if (StringUtils.isNotEmpty(usmFriend.getName())){
//			dc.add(Restrictions.like("name", "%"+usmFriend.getName()+"%"));
//		}
		String sql = usmInviteDao.getInvitee(user_id);
		return usmInviteDao.findBySql(page, sql);
	}
}
