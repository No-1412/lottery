/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.lom.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.lom.dao.LomPointDao;
import com.youge.yogee.modules.lom.entity.LomPoint;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 积分记录Service
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Component
@Transactional(readOnly = true)
public class LomPointService extends BaseService {

	@Autowired
	private LomPointDao lomPointDao;
	
	public LomPoint get(String id) {
		return lomPointDao.get(id);
	}
	
	public Page<LomPoint> find(Page<LomPoint> page, LomPoint lomPoint) {
		DetachedCriteria dc = lomPointDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(lomPoint.getDelFlag())){
			dc.createAlias("userId","userId");
			dc.add(Restrictions.like("userId.name", "%" + lomPoint.getDelFlag() + "%"));
		}
		dc.add(Restrictions.eq(LomPoint.FIELD_DEL_FLAG, LomPoint.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return lomPointDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(LomPoint lomPoint) {

		if(StringUtils.isEmpty(lomPoint.getId())){
			lomPoint.setId(IdGen.uuid());
			lomPoint.setCreateDate(DateUtils.getDateTime());
			lomPoint.setDelFlag(LomPoint.DEL_FLAG_NORMAL);
			if(StringUtils.isEmpty(lomPoint.getStates())){
				lomPoint.setStates(LomPoint.DEL_FLAG_NORMAL);//设置默认值和删除标识共用
			}
		}
		lomPointDao.save(lomPoint);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		lomPointDao.deleteById(id);
	}


	/****************************************************接口相关*******************************************************/

	public List<LomPoint> findByUserId(String userId,String total, String count) {
		DetachedCriteria dc = lomPointDao.createDetachedCriteria();
		dc.createAlias("userId","userId");
		dc.add(Restrictions.eq("userId.id", userId));
		dc.add(Restrictions.eq(LomPoint.FIELD_DEL_FLAG, LomPoint.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("createDate"));
		// 限制条数|分页
		Criteria cri = dc.getExecutableCriteria(lomPointDao.getSession());
		cri.setMaxResults(Integer.parseInt(count));
		cri.setFirstResult(Integer.parseInt(total));
		return lomPointDao.find(dc);
	}
}
