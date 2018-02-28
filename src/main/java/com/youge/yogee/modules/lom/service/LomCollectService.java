/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.lom.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.lom.dao.LomCollectDao;
import com.youge.yogee.modules.lom.entity.LomCollect;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收藏记录Service
 * @author RenHaipeng
 * @version 2017-03-06
 */
@Component
@Transactional(readOnly = true)
public class LomCollectService extends BaseService {

	@Autowired
	private LomCollectDao lomCollectDao;
	
	public LomCollect get(String id) {
		return lomCollectDao.get(id);
	}
	
	public Page<LomCollect> find(Page<LomCollect> page, LomCollect lomCollect) {
		DetachedCriteria dc = lomCollectDao.createDetachedCriteria();
//		if (StringUtils.isNotEmpty(lomCollect.getName())){
//			dc.add(Restrictions.like("name", "%"+lomCollect.getName()+"%"));
//		}
		dc.add(Restrictions.eq(LomCollect.FIELD_DEL_FLAG, LomCollect.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return lomCollectDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(LomCollect lomCollect) {

		if(StringUtils.isEmpty(lomCollect.getId())){
			lomCollect.setId(IdGen.uuid());
			lomCollect.setCreateDate(DateUtils.getDateTime());
			lomCollect.setDelFlag(LomCollect.DEL_FLAG_NORMAL);
		}
		lomCollectDao.save(lomCollect);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		lomCollectDao.deleteById(id);
	}

	//根据id取消所有收藏
	@Transactional(readOnly = false)
	public void cancleCollect(String belongId){
		lomCollectDao.cancleCollect(belongId);
	}


	/***************************************************接口***********************************************************/
	public LomCollect findBybelongIdAndUserid(String belongId,String userId,String kind) {
		DetachedCriteria dc = lomCollectDao.createDetachedCriteria();

		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("belongId", belongId));
		dc.add(Restrictions.eq("kind", kind));
		List<LomCollect> list = lomCollectDao.find(dc);
		if(null ==list || list.size() ==0){
			return  null;
		}else{
			return list.get(0);
		}
	}


	public Long checkIsCollect(String belongId, String userId, String kind) {
		DetachedCriteria dc = lomCollectDao.createDetachedCriteria();

		dc.add(Restrictions.eq("belongId", belongId));
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("kind", kind));
		dc.add(Restrictions.eq("delFlag", "0"));
		return lomCollectDao.count(dc);
	}


//	public List<LomCollect> getCollectByUserId(String userId, String total, String count){
//		DetachedCriteria dc = lomCollectDao.createDetachedCriteria();
//		dc.add(Restrictions.eq("userId.id",userId));
//		dc.add(Restrictions.eq(LomCollect.FIELD_DEL_FLAG,LomCollect.DEL_FLAG_NORMAL));
//
//		// 限制条数|分页
//		Criteria cri = dc.getExecutableCriteria(lomCollectDao.getSession());
//		cri.setMaxResults(Integer.parseInt(count));
//		cri.setFirstResult(Integer.parseInt(total));
//		return lomCollectDao.find(dc);
//	}
//获取我的粉丝数据
	public List<Object[]> getMyCollect(String userId, String total, String count) {
		return lomCollectDao.getMyCollect(userId, total, count);
	}
}
