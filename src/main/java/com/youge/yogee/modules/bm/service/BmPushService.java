/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.service;

import org.apache.poi.util.StringUtil;
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
import com.youge.yogee.modules.bm.entity.BmPush;
import com.youge.yogee.modules.bm.dao.BmPushDao;

import java.util.List;

/**
 * 推送表Service
 * @author ZhaoYiFeng
 * @version 2018-03-09
 */
@Component
@Transactional(readOnly = true)
public class BmPushService extends BaseService {

	@Autowired
	private BmPushDao bmPushDao;
	
	public BmPush get(String id) {
		return bmPushDao.get(id);
	}
	
	public Page<BmPush> find(Page<BmPush> page, BmPush bmPush) {
		DetachedCriteria dc = bmPushDao.createDetachedCriteria();
		dc.add(Restrictions.eq(BmPush.FIELD_DEL_FLAG, BmPush.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return bmPushDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(BmPush bmPush) {

		if(StringUtils.isEmpty(bmPush.getId())){
			bmPush.setId(IdGen.uuid());
			bmPush.setCreateDate(DateUtils.getDateTime());
			bmPush.setDelFlag(BmPush.DEL_FLAG_NORMAL);
		}
		bmPushDao.save(bmPush);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		bmPushDao.deleteById(id);
	}

	public BmPush findByPushID(String pushID) {
		DetachedCriteria dc = bmPushDao.createDetachedCriteria();
		dc.add(Restrictions.eq("pushid", pushID));
		dc.add(Restrictions.eq(BmPush.FIELD_DEL_FLAG, BmPush.DEL_FLAG_NORMAL));
		List<BmPush> list =  bmPushDao.find(dc);
		if(list.size() == 0){
			return new BmPush();
		}else {
			return list.get(0);
		}
	}

	public BmPush unBindAlias(String userid,String pushid,String type) {

		DetachedCriteria dc = bmPushDao.createDetachedCriteria();
		dc.add(Restrictions.eq("userid", userid));
		dc.add(Restrictions.eq("pushid", pushid));
		dc.add(Restrictions.eq("type", type));
		dc.add(Restrictions.eq(BmPush.FIELD_DEL_FLAG, BmPush.DEL_FLAG_NORMAL));
		List<BmPush> list =  bmPushDao.find(dc);
		if(list.size() == 0){
			return new BmPush();
		}else {
			return list.get(0);
		}
	}


	@Transactional(readOnly = false)
	public void deleteByPushid(String pushid) {
		bmPushDao.deleteByPushid(pushid);
	}

	public BmPush findByUserid(String userid) {

		DetachedCriteria dc = bmPushDao.createDetachedCriteria();
		dc.add(Restrictions.eq("userid", userid));
		dc.add(Restrictions.eq(BmPush.FIELD_DEL_FLAG, BmPush.DEL_FLAG_NORMAL));
		List<BmPush> list =  bmPushDao.find(dc);
		if(list.size() == 0){
			return null;
		}else {
			return list.get(0);
		}
	}

	@Transactional(readOnly = false)
	public List<String> findAllIOS() {
		return bmPushDao.findAllIOS();
	}




	
}
