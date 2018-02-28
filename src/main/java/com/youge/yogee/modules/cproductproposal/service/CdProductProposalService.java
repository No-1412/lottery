/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cproductproposal.service;

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
import com.youge.yogee.modules.cproductproposal.entity.CdProductProposal;
import com.youge.yogee.modules.cproductproposal.dao.CdProductProposalDao;

/**
 * 产品建议Service
 * @author WeiJinChao
 * @version 2017-12-22
 */
@Component
@Transactional(readOnly = true)
public class CdProductProposalService extends BaseService {

	@Autowired
	private CdProductProposalDao cdProductProposalDao;
	
	public CdProductProposal get(String id) {
		return cdProductProposalDao.get(id);
	}
	
	public Page<CdProductProposal> find(Page<CdProductProposal> page, CdProductProposal cdProductProposal) {
		DetachedCriteria dc = cdProductProposalDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdProductProposal.getName())){
			dc.add(Restrictions.like("name", "%"+cdProductProposal.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdProductProposal.FIELD_DEL_FLAG, CdProductProposal.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdProductProposalDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdProductProposal cdProductProposal) {

		if(StringUtils.isEmpty(cdProductProposal.getId())){
			cdProductProposal.setId(IdGen.uuid());
			cdProductProposal.setCreateDate(DateUtils.getDateTime());
			cdProductProposal.setDelFlag(CdProductProposal.DEL_FLAG_NORMAL);
		}
		cdProductProposalDao.save(cdProductProposal);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdProductProposalDao.deleteById(id);
	}
	
}
