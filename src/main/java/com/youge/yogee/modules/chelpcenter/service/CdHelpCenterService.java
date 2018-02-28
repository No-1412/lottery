/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.chelpcenter.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.chelpcenter.dao.CdHelpCenterDao;
import com.youge.yogee.modules.chelpcenter.entity.CdHelpCenter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 帮助中心Service
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Component
@Transactional(readOnly = true)
public class CdHelpCenterService extends BaseService {

	@Autowired
	private CdHelpCenterDao cdHelpCenterDao;
	
	public CdHelpCenter get(String id) {
		return cdHelpCenterDao.get(id);
	}
	
	public Page<CdHelpCenter> find(Page<CdHelpCenter> page, CdHelpCenter cdHelpCenter) {
		DetachedCriteria dc = cdHelpCenterDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdHelpCenter.getName())){
			dc.add(Restrictions.like("name", "%"+cdHelpCenter.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdHelpCenter.FIELD_DEL_FLAG, CdHelpCenter.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdHelpCenterDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdHelpCenter cdHelpCenter) {

		if(StringUtils.isEmpty(cdHelpCenter.getId())){
			cdHelpCenter.setId(IdGen.uuid());
			cdHelpCenter.setCreateDate(DateUtils.getDateTime());
			cdHelpCenter.setDelFlag(CdHelpCenter.DEL_FLAG_NORMAL);
		}
		cdHelpCenterDao.save(cdHelpCenter);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdHelpCenterDao.deleteById(id);
	}


	/**
	 * wangsong
	 * 20171220
	 * 查询帮助中心接口
	 */
	public List<CdHelpCenter> findHelpList() {
		DetachedCriteria dc = cdHelpCenterDao.createDetachedCriteria();
		dc.add(Restrictions.eq("isUse","0"));
		dc.add(Restrictions.eq(CdHelpCenter.FIELD_DEL_FLAG, CdHelpCenter.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		// 限制条数|分页
		/*Criteria cri = dc.getExecutableCriteria(cdHelpCenterDao.getSession());
		cri.setMaxResults(Integer.parseInt(count));
		cri.setFirstResult(Integer.parseInt(total));*/
		return cdHelpCenterDao.find(dc);
	}
}
