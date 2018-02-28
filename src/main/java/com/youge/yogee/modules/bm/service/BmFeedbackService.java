/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.bm.dao.BmFeedbackDao;
import com.youge.yogee.modules.bm.entity.BmFeedback;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 意见反馈Service
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Component
@Transactional(readOnly = true)
public class BmFeedbackService extends BaseService {

	@Autowired
	private BmFeedbackDao bmFeedbackDao;
	
	public BmFeedback get(String id) {
		return bmFeedbackDao.get(id);
	}
	
	public Page<BmFeedback> find(Page<BmFeedback> page, BmFeedback bmFeedback) {
		DetachedCriteria dc = bmFeedbackDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(bmFeedback.getDelFlag())){
			dc.createAlias("userId","userId");
			dc.add(Restrictions.like("userId.name", "%" + bmFeedback.getDelFlag() + "%"));
		}
		if (StringUtils.isNotEmpty(bmFeedback.getContent())){
			dc.add(Restrictions.like("content", "%" + bmFeedback.getContent() + "%"));
		}
		dc.add(Restrictions.eq(BmFeedback.FIELD_DEL_FLAG, BmFeedback.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return bmFeedbackDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(BmFeedback bmFeedback) {

		if(StringUtils.isEmpty(bmFeedback.getId())){
			bmFeedback.setId(IdGen.uuid());
			bmFeedback.setCreateDate(DateUtils.getDateTime());
			bmFeedback.setDelFlag(BmFeedback.DEL_FLAG_NORMAL);
		}
		bmFeedbackDao.save(bmFeedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		bmFeedbackDao.deleteById(id);
	}
	
}
