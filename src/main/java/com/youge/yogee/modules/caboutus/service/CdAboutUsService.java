/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.caboutus.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.caboutus.dao.CdAboutUsDao;
import com.youge.yogee.modules.caboutus.entity.CdAboutUs;
import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 关于我们Service
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Component
@Transactional(readOnly = true)
public class CdAboutUsService extends BaseService {

	@Autowired
	private CdAboutUsDao cdAboutUsDao;
	
	public CdAboutUs get(String id) {
		return cdAboutUsDao.get(id);
	}
	
	public Page<CdAboutUs> find(Page<CdAboutUs> page, CdAboutUs cdAboutUs) {
		DetachedCriteria dc = cdAboutUsDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdAboutUs.getName())){
			dc.add(Restrictions.like("name", "%"+cdAboutUs.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdAboutUs.FIELD_DEL_FLAG, CdAboutUs.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdAboutUsDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdAboutUs cdAboutUs) {

		if(StringUtils.isEmpty(cdAboutUs.getId())){
			cdAboutUs.setId(IdGen.uuid());
			cdAboutUs.setCreateDate(DateUtils.getDateTime());
			cdAboutUs.setDelFlag(CdAboutUs.DEL_FLAG_NORMAL);
		}
		//富文本中如果包含“”等字符直接保存可能会出现乱码，需要转一下码。
		cdAboutUs.setFunction(StringEscapeUtils.unescapeHtml(cdAboutUs.getFunction()));
		cdAboutUsDao.save(cdAboutUs);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdAboutUsDao.deleteById(id);
	}

	@Transactional(readOnly = false)
	public List<CdAboutUs> getByAll(){
		DetachedCriteria dc = cdAboutUsDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdAboutUs.FIELD_DEL_FLAG, CdAboutUs.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdAboutUsDao.find(dc);

	}
}
