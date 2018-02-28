/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.bm.dao.BmHelpDao;
import com.youge.yogee.modules.bm.entity.BmHelp;
import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 新手教程Service
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Component
@Transactional(readOnly = true)
public class BmHelpService extends BaseService {

	@Autowired
	private BmHelpDao bmHelpDao;
	
	public BmHelp get(String id) {
		return bmHelpDao.get(id);
	}
	
	public Page<BmHelp> find(Page<BmHelp> page, BmHelp bmHelp) {
		DetachedCriteria dc = bmHelpDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(bmHelp.getName())){
			dc.add(Restrictions.like("name", "%" + bmHelp.getName() + "%"));
		}
		dc.add(Restrictions.eq(BmHelp.FIELD_DEL_FLAG, BmHelp.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("sort"));
		return bmHelpDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(BmHelp bmHelp) {

		if(StringUtils.isEmpty(bmHelp.getId())){
			bmHelp.setId(IdGen.uuid());
			bmHelp.setCreateDate(DateUtils.getDateTime());
			bmHelp.setDelFlag(BmHelp.DEL_FLAG_NORMAL);
		}
		//富文本中如果包含“”等字符直接保存可能会出现乱码，需要转一下码。
		bmHelp.setContent(StringEscapeUtils.unescapeHtml(bmHelp.getContent()));
		bmHelpDao.save(bmHelp);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		bmHelpDao.deleteById(id);
	}

	/****************************************************接口相关*******************************************************/

	public List<BmHelp> findByType(String type, String total, String count) {
		DetachedCriteria dc = bmHelpDao.createDetachedCriteria();
		dc.add(Restrictions.eq(BmHelp.FIELD_DEL_FLAG, BmHelp.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("sort"));

		// 限制条数|分页
		Criteria cri = dc.getExecutableCriteria(bmHelpDao.getSession());
		cri.setMaxResults(Integer.parseInt(count));
		cri.setFirstResult(Integer.parseInt(total));
		return bmHelpDao.find(dc);
	}
}
