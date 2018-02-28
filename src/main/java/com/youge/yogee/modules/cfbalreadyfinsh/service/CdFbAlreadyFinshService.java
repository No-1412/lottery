/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbalreadyfinsh.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfbalreadyfinsh.dao.CdFbAlreadyFinshDao;
import com.youge.yogee.modules.cfbalreadyfinsh.entity.CdFbAlreadyFinsh;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 足球已经完赛信息Service
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Component
@Transactional(readOnly = true)
public class CdFbAlreadyFinshService extends BaseService {

	@Autowired
	private CdFbAlreadyFinshDao cdFbAlreadyFinshDao;
	
	public CdFbAlreadyFinsh get(String id) {
		return cdFbAlreadyFinshDao.get(id);
	}
	
	public Page<CdFbAlreadyFinsh> find(Page<CdFbAlreadyFinsh> page, CdFbAlreadyFinsh cdFbAlreadyFinsh) {
		DetachedCriteria dc = cdFbAlreadyFinshDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdFbAlreadyFinsh.getName())){
			dc.add(Restrictions.like("name", "%" + cdFbAlreadyFinsh.getName() + "%"));
		}
		dc.add(Restrictions.eq(CdFbAlreadyFinsh.FIELD_DEL_FLAG, CdFbAlreadyFinsh.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdFbAlreadyFinshDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdFbAlreadyFinsh cdFbAlreadyFinsh) {

		if(StringUtils.isEmpty(cdFbAlreadyFinsh.getId())){
			cdFbAlreadyFinsh.setId(IdGen.uuid());
			cdFbAlreadyFinsh.setCreateDate(DateUtils.getDateTime());
			cdFbAlreadyFinsh.setDelFlag(CdFbAlreadyFinsh.DEL_FLAG_NORMAL);
		}
		cdFbAlreadyFinshDao.save(cdFbAlreadyFinsh);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdFbAlreadyFinshDao.deleteById(id);
	}

	@Transactional(readOnly = false)
	public void deleteAlreadyFinsh() {
		cdFbAlreadyFinshDao.update("delete from CdFbAlreadyFinsh");
	}

	@Transactional(readOnly = false)
	public List<CdFbAlreadyFinsh> getAlreadyFinsh(){
		DetachedCriteria dc = cdFbAlreadyFinshDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdFbAlreadyFinsh.FIELD_DEL_FLAG, CdFbAlreadyFinsh.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdFbAlreadyFinshDao.find(dc);
	}
	
}
