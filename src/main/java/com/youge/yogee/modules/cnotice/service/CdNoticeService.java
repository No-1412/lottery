/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cnotice.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cnotice.dao.CdNoticeDao;
import com.youge.yogee.modules.cnotice.entity.CdNotice;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公告信息Service
 * @author WeiJinChao
 * @version 2017-12-18
 */
@Component
@Transactional(readOnly = true)
public class CdNoticeService extends BaseService {

	@Autowired
	private CdNoticeDao cdNoticeDao;
	
	public CdNotice get(String id) {
		return cdNoticeDao.get(id);
	}
	
	public Page<CdNotice> find(Page<CdNotice> page, CdNotice cdNotice) {
		DetachedCriteria dc = cdNoticeDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdNotice.getName())){
			dc.add(Restrictions.like("name", "%"+cdNotice.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdNotice.FIELD_DEL_FLAG, CdNotice.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdNoticeDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdNotice cdNotice) {

		if(StringUtils.isEmpty(cdNotice.getId())){
			cdNotice.setId(IdGen.uuid());
			cdNotice.setCreateDate(DateUtils.getDateTime());
			cdNotice.setDelFlag(CdNotice.DEL_FLAG_NORMAL);
		}
		cdNoticeDao.save(cdNotice);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdNoticeDao.deleteById(id);
	}

	@Transactional(readOnly = false)
	public List<CdNotice> getNotice(){
		DetachedCriteria dc = cdNoticeDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdNotice.FIELD_DEL_FLAG, CdNotice.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdNoticeDao.find(dc);
	}

}
