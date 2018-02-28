/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.usm.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.usm.dao.UsmUserDao;
import com.youge.yogee.modules.usm.entity.UsmUser;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户表Service
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Component
@Transactional(readOnly = true)
public class UsmUserService extends BaseService {

	@Autowired
	private UsmUserDao usmUserDao;
	
	public UsmUser get(String id) {
		return usmUserDao.get(id);
	}
	public UsmUser getByWechat(String openid) {
		return usmUserDao.getByWechat(openid);
	}
	public UsmUser getByQq(String openid) {
		return usmUserDao.getByQq(openid);
	}
	public UsmUser getBySina(String openid) {
		return usmUserDao.getBySina(openid);
	}
	
	public Page<UsmUser> find(Page<UsmUser> page, UsmUser usmUser) {
		DetachedCriteria dc = usmUserDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(usmUser.getName())){
			dc.add(Restrictions.like("name", "%" + usmUser.getName() + "%"));
		}
		dc.add(Restrictions.eq(UsmUser.FIELD_DEL_FLAG, UsmUser.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return usmUserDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(UsmUser usmUser) {
		if(StringUtils.isEmpty(usmUser.getId())){
			usmUser.setId(IdGen.uuid());
			usmUser.setPoint(0);
			usmUser.setMoney(0);
			usmUser.setCreateDate(DateUtils.getDateTime());
			usmUser.setUpdateDate(DateUtils.getDateTime());
			usmUser.setDelFlag(UsmUser.DEL_FLAG_NORMAL);
			usmUser.setFreeze(UsmUser.DEL_FLAG_NORMAL);
		}
		usmUserDao.clear();
		usmUserDao.save(usmUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		usmUserDao.deleteById(id);
	}

//	@Transactional(readOnly = false)
//	public void freeze(String id) {
//		usmUserDao.freezeById(id);
//	}

	public List<UsmUser> findAll() {
		DetachedCriteria dc = usmUserDao.createDetachedCriteria();
		dc.add(Restrictions.eq(UsmUser.FIELD_DEL_FLAG, UsmUser.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("name"));
		return usmUserDao.find(dc);
	}

	/****************************************************接口相关*******************************************************/
	//根据手机号获取用户信息
	public UsmUser findByPhone(String phone) {
		DetachedCriteria dc = usmUserDao.createDetachedCriteria();
		dc.add(Restrictions.eq("telephone", phone));
		dc.add(Restrictions.eq(UsmUser.FIELD_DEL_FLAG, UsmUser.DEL_FLAG_NORMAL));
		List<UsmUser> list = usmUserDao.find(dc);

		if (null == list) {
			return null;
		} else if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 *判断电话号是否存在
	 * */
	public boolean checkTelephone(String telephone){
		boolean flag = false;
		List count = usmUserDao.getCountByTelephone(telephone);
		if(count.size() == 0){
			flag = true;
		}
		return flag;
	}

	//根据手机号获取用户信息
	public UsmUser findByCode(String code) {
		DetachedCriteria dc = usmUserDao.createDetachedCriteria();
		dc.add(Restrictions.eq("code", code));
		dc.add(Restrictions.eq(UsmUser.FIELD_DEL_FLAG, UsmUser.DEL_FLAG_NORMAL));
		List<UsmUser> list = usmUserDao.find(dc);

		if (null == list) {
			return null;
		} else if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	//判断手机号是否注册
	public Long  getCountByPhone(String phone) {
		DetachedCriteria dc = usmUserDao.createDetachedCriteria();
		dc.add(Restrictions.eq("telephone", phone));
		dc.add(Restrictions.eq(UsmUser.FIELD_DEL_FLAG, UsmUser.DEL_FLAG_NORMAL));
		return usmUserDao.count(dc);
	}

	//根据用户的注册顺序生成邀请码（10进制转16进制）
	public String getInviteCode() {
		DetachedCriteria dc = usmUserDao.createDetachedCriteria();
		long sum = 100000001 + usmUserDao.count(dc);
		return Long.toHexString(sum).toUpperCase();
	}

}
