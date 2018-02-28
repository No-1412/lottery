/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfbscoer.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfbscoer.dao.CdFbScoerDao;
import com.youge.yogee.modules.cfbscoer.entity.CdFbScoer;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 竞彩足球积分Service
 * @author RenHaipeng
 * @version 2018-01-15
 */
@Component
@Transactional(readOnly = true)
public class CdFbScoerService extends BaseService {

	@Autowired
	private CdFbScoerDao cdFbScoerDao;
	
	public CdFbScoer get(String id) {
		return cdFbScoerDao.get(id);
	}
	
	public Page<CdFbScoer> find(Page<CdFbScoer> page, CdFbScoer cdFbScoer) {
		DetachedCriteria dc = cdFbScoerDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdFbScoer.getName())){
			dc.add(Restrictions.like("name", "%" + cdFbScoer.getName() + "%"));
		}
		dc.add(Restrictions.eq(CdFbScoer.FIELD_DEL_FLAG, CdFbScoer.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdFbScoerDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdFbScoer cdFbScoer) {

		if(StringUtils.isEmpty(cdFbScoer.getId())){
			cdFbScoer.setId(IdGen.uuid());
			cdFbScoer.setCreateDate(DateUtils.getDateTime());
			cdFbScoer.setDelFlag(CdFbScoer.DEL_FLAG_NORMAL);
		}
		cdFbScoerDao.save(cdFbScoer);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdFbScoerDao.deleteById(id);
	}

	/**
	 * wangsong
	 * 20180105
	 * 清空表
	 */
	@Transactional(readOnly = false)
	public void deleteAlls(String name) {
		cdFbScoerDao.update("delete from "+name );
	}


	/**
	 * wangsong
	 * 20180105
	 * 查询球队积分
	 */
	@Transactional(readOnly = false)
	public List<CdFbScoer> findById(String itemid){
		DetachedCriteria dc = cdFbScoerDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdFbScoer.FIELD_DEL_FLAG, CdFbScoer.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("itemid", itemid));
		dc.addOrder(Order.asc("rank"));
		return cdFbScoerDao.find(dc);
	}
	
}
