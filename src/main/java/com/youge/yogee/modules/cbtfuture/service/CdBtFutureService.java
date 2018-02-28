/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbtfuture.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cbtfuture.dao.CdBtFutureDao;
import com.youge.yogee.modules.cbtfuture.entity.CdBtFuture;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 竞彩篮球未来赛事Service
 * @author RenHaipeng
 * @version 2018-01-18
 */
@Component
@Transactional(readOnly = true)
public class CdBtFutureService extends BaseService {

	@Autowired
	private CdBtFutureDao cdBtFutureDao;
	
	public CdBtFuture get(String id) {
		return cdBtFutureDao.get(id);
	}
	
	public Page<CdBtFuture> find(Page<CdBtFuture> page, CdBtFuture cdBtFuture) {
		DetachedCriteria dc = cdBtFutureDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdBtFuture.getName())){
			dc.add(Restrictions.like("name", "%" + cdBtFuture.getName() + "%"));
		}
		dc.add(Restrictions.eq(CdBtFuture.FIELD_DEL_FLAG, CdBtFuture.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdBtFutureDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdBtFuture cdBtFuture) {

		if(StringUtils.isEmpty(cdBtFuture.getId())){
			cdBtFuture.setId(IdGen.uuid());
			cdBtFuture.setCreateDate(DateUtils.getDateTime());
			cdBtFuture.setDelFlag(CdBtFuture.DEL_FLAG_NORMAL);
		}
		cdBtFutureDao.save(cdBtFuture);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdBtFutureDao.deleteById(id);
	}


	/**
	 * wangsong
	 * 20180105
	 * 清空表
	 */
	@Transactional(readOnly = false)
	public void deleteAll(String name) {
		cdBtFutureDao.update("delete from " + name);
	}

	/**
	 * wangsong
	 * 20180105
	 * 查询球队未来赛事
	 */
	@Transactional(readOnly = false)
	public List<CdBtFuture> findById(String itemid){
		DetachedCriteria dc = cdBtFutureDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdBtFuture.FIELD_DEL_FLAG, CdBtFuture.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("matchId", itemid));
		dc.addOrder(Order.asc("mt"));

		return cdBtFutureDao.find(dc);
	}
}
