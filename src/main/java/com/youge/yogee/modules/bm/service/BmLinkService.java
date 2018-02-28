/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.bm.dao.BmLinkDao;
import com.youge.yogee.modules.bm.entity.BmLink;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 友情链接Service
 * @author RenHaipeng
 * @version 2017-03-11
 */
@Component
@Transactional(readOnly = true)
public class BmLinkService extends BaseService {

	@Autowired
	private BmLinkDao bmLinkDao;
	
	public BmLink get(String id) {
		return bmLinkDao.get(id);
	}
	
	public Page<BmLink> find(Page<BmLink> page, BmLink bmLink) {
		DetachedCriteria dc = bmLinkDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(bmLink.getName())){
			dc.add(Restrictions.like("name", "%" + bmLink.getName() + "%"));
		}
		dc.add(Restrictions.eq(BmLink.FIELD_DEL_FLAG, BmLink.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return bmLinkDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(BmLink bmLink) {

		if(StringUtils.isEmpty(bmLink.getId())){
			bmLink.setId(IdGen.uuid());
			bmLink.setCreateDate(DateUtils.getDateTime());
			bmLink.setDelFlag(BmLink.DEL_FLAG_NORMAL);
		}
		bmLinkDao.save(bmLink);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		bmLinkDao.deleteById(id);
	}


	public List<Object[]> findAll() {
		return bmLinkDao.getLinkAll();
	}
}
