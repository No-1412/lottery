/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cscore.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cscore.dao.CdScoreDao;
import com.youge.yogee.modules.cscore.entity.CdScore;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户积分信息Service
 * @author WeiJinChao
 * @version 2017-12-13
 */
@Component
@Transactional(readOnly = true)
public class CdScoreService extends BaseService {

	@Autowired
	private CdScoreDao cdScoreDao;
	
	public CdScore get(String id) {
		return cdScoreDao.get(id);
	}
	
	public Page<CdScore> find(Page<CdScore> page, CdScore cdScore) {
		DetachedCriteria dc = cdScoreDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdScore.getName())){
			dc.add(Restrictions.like("name", "%"+cdScore.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdScore.FIELD_DEL_FLAG, CdScore.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdScoreDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdScore cdScore) {

		if(StringUtils.isEmpty(cdScore.getId())){
			cdScore.setId(IdGen.uuid());
			cdScore.setCreateDate(DateUtils.getDateTime());
			cdScore.setDelFlag(CdScore.DEL_FLAG_NORMAL);
		}
		cdScoreDao.save(cdScore);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdScoreDao.deleteById(id);
	}


	/**
	 * wangsong
	 * 171222
	 * 根据用户id查询积分明细
	 * @param userId
	 * @param count
	 * @param total
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<CdScore> listUidScore(String userId, String count, String total) {
		DetachedCriteria dc = cdScoreDao.createDetachedCriteria();
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq(CdScore.FIELD_DEL_FLAG, CdScore.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		Criteria cri = dc.getExecutableCriteria(cdScoreDao.getSession());
		cri.setMaxResults(Integer.parseInt(count));
		cri.setFirstResult(Integer.parseInt(total));
		return cdScoreDao.find(dc);
	}
}
