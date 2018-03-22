/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbblive.service;

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
import com.youge.yogee.modules.cbblive.entity.CdBbLive;
import com.youge.yogee.modules.cbblive.dao.CdBbLiveDao;

import java.util.List;

/**
 * 伤停补时数据Service
 * @author ZhaoYiFeng
 * @version 2018-03-20
 */
@Component
@Transactional(readOnly = true)
public class CdBbLiveService extends BaseService {

	@Autowired
	private CdBbLiveDao cdBbLiveDao;
	
	public CdBbLive get(String id) {
		return cdBbLiveDao.get(id);
	}
	
	public Page<CdBbLive> find(Page<CdBbLive> page, CdBbLive cdBbLive) {
		DetachedCriteria dc = cdBbLiveDao.createDetachedCriteria();
//		if (StringUtils.isNotEmpty(cdBbLive.getName())){
//			dc.add(Restrictions.like("name", "%"+cdBbLive.getName()+"%"));
//		}
		dc.add(Restrictions.eq(CdBbLive.FIELD_DEL_FLAG, CdBbLive.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdBbLiveDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdBbLive cdBbLive) {

		if(StringUtils.isEmpty(cdBbLive.getId())){
			cdBbLive.setId(IdGen.uuid());
			cdBbLive.setCreateDate(DateUtils.getDateTime());
			cdBbLive.setDelFlag(CdBbLive.DEL_FLAG_NORMAL);
		}
		cdBbLiveDao.save(cdBbLive);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdBbLiveDao.deleteById(id);
	}


	public CdBbLive findByMatchId(String itemid) {
		DetachedCriteria dc = cdBbLiveDao.createDetachedCriteria();
		dc.add(Restrictions.eq("itemid", itemid));
		dc.add(Restrictions.eq(CdBbLive.FIELD_DEL_FLAG, CdBbLive.DEL_FLAG_NORMAL));

		List<CdBbLive> list = cdBbLiveDao.find(dc);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}
}
