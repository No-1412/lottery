/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cftlogo.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cftlogo.dao.CdFtLogoDao;
import com.youge.yogee.modules.cftlogo.entity.CdFtLogo;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 足球logo码表Service
 * @author RenHaipeng
 * @version 2018-02-01
 */
@Component
@Transactional(readOnly = true)
public class CdFtLogoService extends BaseService {

	@Autowired
	private CdFtLogoDao cdFtLogoDao;
	
	public CdFtLogo get(String id) {
		return cdFtLogoDao.get(id);
	}
	
	public Page<CdFtLogo> find(Page<CdFtLogo> page, CdFtLogo cdFtLogo) {
		DetachedCriteria dc = cdFtLogoDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdFtLogo.getName())){
			dc.add(Restrictions.like("name", "%" + cdFtLogo.getName() + "%"));
		}
		dc.add(Restrictions.eq(CdFtLogo.FIELD_DEL_FLAG, CdFtLogo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdFtLogoDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdFtLogo cdFtLogo) {

		if(StringUtils.isEmpty(cdFtLogo.getId())){
			cdFtLogo.setId(IdGen.uuid());
			cdFtLogo.setCreateDate(DateUtils.getDateTime());
			cdFtLogo.setDelFlag(CdFtLogo.DEL_FLAG_NORMAL);
		}
		cdFtLogoDao.save(cdFtLogo);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdFtLogoDao.deleteById(id);
	}


	@Transactional(readOnly = false)
	public List<CdFtLogo> findLogo(String teamName) {
		return cdFtLogoDao.findBySql("select * from cd_ft_logo where team_name= '" + teamName + "'",null,CdFtLogo.class);
	}
}
