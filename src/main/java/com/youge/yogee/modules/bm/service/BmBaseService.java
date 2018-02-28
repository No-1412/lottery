/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.bm.dao.BmBaseDao;
import com.youge.yogee.modules.bm.entity.BmBase;
import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 基本信息Service
 * @author RenHaipeng
 * @version 2017-02-24
 */
@Component
@Transactional(readOnly = true)
public class BmBaseService extends BaseService {

	@Autowired
	private BmBaseDao bmBaseDao;
	
	public BmBase get(String id) {
		return bmBaseDao.get(id);
	}
	
	public Page<BmBase> find(Page<BmBase> page, BmBase bmBase) {
		DetachedCriteria dc = bmBaseDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(bmBase.getName())){
			dc.add(Restrictions.like("name", "%" + bmBase.getName() + "%"));
		}
		if (StringUtils.isNotEmpty(bmBase.getKind())){
			dc.add(Restrictions.like("kind", bmBase.getKind()));
		}

		dc.add(Restrictions.eq(BmBase.FIELD_DEL_FLAG, BmBase.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("name"));
		return bmBaseDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(BmBase bmBase) {
		if(StringUtils.isEmpty(bmBase.getId())){
			bmBase.setId(IdGen.uuid());
			bmBase.setCreateDate(DateUtils.getDateTime());
			bmBase.setDelFlag(BmBase.DEL_FLAG_NORMAL);
		}
		//富文本中如果包含“”等字符直接保存可能会出现乱码，需要转一下码。
		bmBase.setContent(StringEscapeUtils.unescapeHtml(bmBase.getContent()));
		bmBaseDao.save(bmBase);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		bmBaseDao.deleteById(id);
	}

	/**************************************************接口相关*********************************************************/

	public BmBase findByKind(String kind) {
		DetachedCriteria dc = bmBaseDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(kind)){
			dc.add(Restrictions.eq("kind", kind));
		}
		dc.add(Restrictions.eq(BmBase.FIELD_DEL_FLAG, BmBase.DEL_FLAG_NORMAL));

		dc.addOrder(Order.desc("createDate"));
		List<BmBase> infos = bmBaseDao.find(dc);
		if(infos.size() > 0){
			return infos.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 根据分类查找数据
	 * @param type 类型（e.g. A01）
	 * @return
	 */
	public List<BmBase> findByType(String type) {
		DetachedCriteria dc = bmBaseDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(type)){
			dc.add(Restrictions.eq("kind", type));
		}
		dc.add(Restrictions.eq(BmBase.FIELD_DEL_FLAG, BmBase.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		List<BmBase> infos = bmBaseDao.find(dc);

		return infos;
	}
}
