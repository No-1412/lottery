/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.erp.dao.ErpUserDao;
import com.youge.yogee.modules.erp.entity.ErpUser;
import com.youge.yogee.modules.sys.entity.User;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户Service
 * @author RenHaipeng
 * @version 2018-03-07
 */
@Component
@Transactional(readOnly = true)
public class ErpUserService extends BaseService {

	@Autowired
	private ErpUserDao erpUserDao;
	
	public ErpUser get(String id) {
		return erpUserDao.get(id);
	}
	
	public Page<ErpUser> find(Page<ErpUser> page, ErpUser user) {
		DetachedCriteria dc = erpUserDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(user.getName())){
			dc.add(Restrictions.like("name", "%"+user.getName()+"%"));
		}
		dc.add(Restrictions.eq(ErpUser.FIELD_DEL_FLAG, ErpUser.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return erpUserDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(ErpUser user) {

		if(StringUtils.isEmpty(user.getId())){
			user.setId(IdGen.uuid());
			user.setCreateDate(DateUtils.getDateTime());
			user.setDelFlag(ErpUser.DEL_FLAG_NORMAL);
		}
		erpUserDao.save(user);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		erpUserDao.deleteById(id);
	}

	public List<ErpUser> findByUser(User user) {
		DetachedCriteria dc = erpUserDao.createDetachedCriteria();
		dc.createAlias("saleId","saleId");
		dc.add(Restrictions.eq("saleId.id",user.getId()));
		dc.add(Restrictions.eq(ErpUser.FIELD_DEL_FLAG, ErpUser.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return erpUserDao.find(dc);
	}

	public List<ErpUser> findAllUser() {
		return erpUserDao.findAllList();
	}

}
