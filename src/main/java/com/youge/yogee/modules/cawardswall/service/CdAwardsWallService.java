/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cawardswall.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cawardswall.dao.CdAwardsWallDao;
import com.youge.yogee.modules.cawardswall.entity.CdAwardsWall;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 大奖墙Service
 * @author WeiJinChao
 * @version 2017-12-21
 */
@Component
@Transactional(readOnly = true)
public class CdAwardsWallService extends BaseService {

	@Autowired
	private CdAwardsWallDao cdAwardsWallDao;
	
	public CdAwardsWall get(String id) {
		return cdAwardsWallDao.get(id);
	}
	
	public Page<CdAwardsWall> find(Page<CdAwardsWall> page, CdAwardsWall cdAwardsWall) {
		DetachedCriteria dc = cdAwardsWallDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdAwardsWall.getName())){
			dc.add(Restrictions.like("name", "%"+cdAwardsWall.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdAwardsWall.FIELD_DEL_FLAG, CdAwardsWall.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdAwardsWallDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdAwardsWall cdAwardsWall) {

		if(StringUtils.isEmpty(cdAwardsWall.getId())){
			cdAwardsWall.setId(IdGen.uuid());
			cdAwardsWall.setCreateDate(DateUtils.getDateTime());
			cdAwardsWall.setDelFlag(CdAwardsWall.DEL_FLAG_NORMAL);
			cdAwardsWall.setDianzanCount("0");
		}
		cdAwardsWallDao.save(cdAwardsWall);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdAwardsWallDao.deleteById(id);
	}

	/**
	 * 获取大奖墙列表信息
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<CdAwardsWall> getAwardsWallList(String id){
		DetachedCriteria dc = cdAwardsWallDao.createDetachedCriteria();
		if(StringUtils.isNotEmpty(id)){
			dc.add(Restrictions.eq("id",id));
		}
		dc.add(Restrictions.eq(CdAwardsWall.FIELD_DEL_FLAG, CdAwardsWall.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdAwardsWallDao.find(dc);
	}
}
