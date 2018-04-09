/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbblogo.service;

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
import com.youge.yogee.modules.cbblogo.entity.CdBbLogo;
import com.youge.yogee.modules.cbblogo.dao.CdBbLogoDao;

import java.util.List;

/**
 * 篮球logoService
 * @author ZhaoYiFeng
 * @version 2018-04-01
 */
@Component
@Transactional(readOnly = true)
public class CdBbLogoService extends BaseService {

	@Autowired
	private CdBbLogoDao cdBbLogoDao;
	
	public CdBbLogo get(String id) {
		return cdBbLogoDao.get(id);
	}
	
	public Page<CdBbLogo> find(Page<CdBbLogo> page, CdBbLogo cdBbLogo) {
		DetachedCriteria dc = cdBbLogoDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdBbLogo.getName())){
			dc.add(Restrictions.like("name", "%"+cdBbLogo.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdBbLogo.FIELD_DEL_FLAG, CdBbLogo.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdBbLogoDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdBbLogo cdBbLogo) {

		if(StringUtils.isEmpty(cdBbLogo.getId())){
			cdBbLogo.setId(IdGen.uuid());
			cdBbLogo.setCreateDate(DateUtils.getDateTime());
			cdBbLogo.setDelFlag(CdBbLogo.DEL_FLAG_NORMAL);
		}
		cdBbLogoDao.save(cdBbLogo);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdBbLogoDao.deleteById(id);
	}

	public String findLogo(String name){
		List<String> list = cdBbLogoDao.findBySql("select logo from cd_bb_logo where name = '"+name+"'");
		if(list.size()>0){
			return list.get(0);
		}else {
			return null;
		}
	}
	
}
