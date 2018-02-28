/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbfuture.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfbfuture.dao.CdFbFutureDao;
import com.youge.yogee.modules.cfbfuture.entity.CdFbFuture;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 竞彩足球未来赛事Service
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Component
@Transactional(readOnly = true)
public class CdFbFutureService extends BaseService {

	@Autowired
	private CdFbFutureDao cdFbFutureDao;
	
	public CdFbFuture get(String id) {
		return cdFbFutureDao.get(id);
	}
	
	public Page<CdFbFuture> find(Page<CdFbFuture> page, CdFbFuture cdFbFuture) {
		DetachedCriteria dc = cdFbFutureDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdFbFuture.getName())){
			dc.add(Restrictions.like("name", "%" + cdFbFuture.getName() + "%"));
		}
		dc.add(Restrictions.eq(CdFbFuture.FIELD_DEL_FLAG, CdFbFuture.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdFbFutureDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdFbFuture cdFbFuture) {

		if(StringUtils.isEmpty(cdFbFuture.getId())){
			cdFbFuture.setId(IdGen.uuid());
			cdFbFuture.setCreateDate(DateUtils.getDateTime());
			cdFbFuture.setDelFlag(CdFbFuture.DEL_FLAG_NORMAL);
		}
		cdFbFutureDao.save(cdFbFuture);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdFbFutureDao.deleteById(id);
	}


	/**
	 * wangsong
	 * 20180105
	 * 清空表
	 */
	@Transactional(readOnly = false)
	public void deleteAlls(String name) {
		cdFbFutureDao.update("delete from "+name );
	}

	/**
	 * wangsong
	 * 20180105
	 * 查询球队未来赛事
	 */
	@Transactional(readOnly = false)
	public List<CdFbFuture> findById(String itemid){
		DetachedCriteria dc = cdFbFutureDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdFbFuture.FIELD_DEL_FLAG, CdFbFuture.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("itemid", itemid));
		dc.addOrder(Order.asc("time"));
		return cdFbFutureDao.find(dc);
	}

	/**
	 * wangsong
	 * 20180105
	 * 根据队名查询队id
	 */
	@Transactional(readOnly = false)
	public List<CdFbFuture> findByName(String name){
		DetachedCriteria dc = cdFbFutureDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdFbFuture.FIELD_DEL_FLAG, CdFbFuture.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("host", name));
		return cdFbFutureDao.find(dc);
	}

}
